
package org.wuhua.fuck.core.impl;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.wuhua.fuck.DataBaseServer;
import org.wuhua.fuck.Logger;
import org.wuhua.fuck.core.DataCore;
import org.wuhua.fuck.core.ResultSet;
import org.wuhua.fuck.core.RmsAccessException;
import org.wuhua.fuck.core.SQLError;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>������DataSQLParser.java</b> </br> 
 * ��д����: 2007-2-12 <br/>
 * �������������ṩ��inser�� update�� delete�� select��֧�� <br/>
 * �Ա��Ĵ�����ɾ������ʱ���ṩ�޸ĵĹ��ܡ� <br/>
 * �����С�����ݲ���������.<br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
  class DataSQLParser extends BaseSQLParser{
	private static Logger logger = Logger.getLogger("DataSQLParser");
	
	Hashtable column;

	DataSQLParser(String sql) throws SQLException {
		super(sql);
		//column = DataBaseServer.findTableMetadataByName(sqls[1]).getColumn(); 
	}

	public ResultSet parser() throws SQLException {
	 
		String name = sqls[0].trim();
		if(name.equals("create")){
			crateTable();
		}else if(name.equals("drop")){
			dropTable();
			
		}else if(name.equals("insert")){
			insert();
		}else if(name.equals("delete")){
			delete();
			
		}else if(name.equals("update")){
			update();
		}else if(name.equals("select")){
			return select();
		}else{
			throw SQLError.createSQLException("SQL syntax error!", "Sql State : " + SQLError.SQL_STATE_SYNTAX_ERROR);
		}
		return new ResultSetBaseImpl(null);
	}
	
	/**
	 * ���±�����
	 *
	 */
	private void update() throws SQLException {
		if(DataBaseServer.findTableMetadataByName(sqls[1]) == null
				|| parameter == null ){
			throw SQLError.createSQLException("Table : "+ sqls[1] + " Column is larger or smaller than metadata large!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		}
	 
		
		column = DataBaseServer.findTableMetadataByName(sqls[1]).getColumn(); 
		StringBuffer values = new StringBuffer();
		Enumeration em = column.elements();
		String[] paraName = new String[column.size()];
		int i = 0;
		
		while(em.hasMoreElements()){		
			paraName[i] = (String) em.nextElement() ;
			i++;
		}
		 
		
		
		
		//��ѯ�����Ӳ�ѯ�������з�����Щ���ݣ�������
		ResultSet rs = this.select();
		while(rs.next()){
			int id = Integer.parseInt(rs.getString(DataCore.ROW_INDEX));
			try {
				for(int j=0; j<paraName.length; j++){
					if(parameter.get(paraName[j]) != null){
						values.append(parameter.get(paraName[j]));
					}else{
						values.append(rs.getString((paraName[j])));
					}
					values.append(DataCore.SQIL);
				}
				logger.debug("���µ�������: "  + values);
				DataBaseServer.modifyDataFromId(sqls[1], id, DataBaseServer.getBytes(values.toString()));
			 
			} catch (IOException e) {
				 
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ѯ����,��ʱ��֧�ֶ��ֶβ�ѯ
	 * select name column wuhua;
	 * @throws SQLException 
	 *
	 */
	private ResultSet select() throws SQLException {
		 if( sqls.length != 4){
			throw SQLError.createSQLException("Table : "+ sqls[1] + " Column is larger or smaller than metadata large!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		} 
		
		//logger.debug( sqls[1] + " : " + sqls[2] + " : " + sqls[3] );
		return new ResultSetBaseImpl(DataBaseServer.findDataFromTableToHashtable( sqls[1] ,  sqls[2] ,  sqls[3] ));
	}

	/**
	 * ɾ���������ݵ�ԭ���ǣ���ɾ������ٴ���һ���µı��
	 * @throws SQLException 
	 *
	 */
	private void delete() throws SQLException {
		if(sqls.length == 2){
			dropTable(); //
			crateTable();
		}else{
			deleteWhereId();
		}
		
	}

	private void deleteWhereId() throws SQLException {
		 ResultSet rs = this.select();
		 while(rs.next()){
			 DataBaseServer.deleteDataFromForId(sqls[1], Integer.parseInt(rs.getString(DataCore.ROW_INDEX)));
		 }
		
	}

	/**
	 * ���������﷨
	 * insert tablename column1 ... ....
	 *  �������ݵĸ�ʽ��
	 * Ȼ�����
	 * @see 
	 * @throws SQLException
	 */
	private void insert() throws SQLException{
		
		if(DataBaseServer.findTableMetadataByName(sqls[1]) == null
				|| parameter == null || 
				parameter.size() != (sqls.length-2)){
			throw SQLError.createSQLException("Table : "+ sqls[1] + " Column is larger or smaller than metadata large!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		}
		
		
		StringBuffer values = getContentFromParameter();
		logger.debug("Insert  " + sqls[1] + " values " + values.toString() );
		try {
			DataBaseServer.store(sqls[1], DataBaseServer.getBytes(values.toString()));
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}	
	}

	private StringBuffer getContentFromParameter() {
		column = DataBaseServer.findTableMetadataByName(sqls[1]).getColumn(); 
		StringBuffer values = new StringBuffer();
		Enumeration em = column.elements();
		while(em.hasMoreElements()){
			String value = (String) parameter.get(em.nextElement());
			values.append(value);
			values.append(DataCore.SQIL);
		}
		return values;
	}
	
	private void dropTable() throws SQLException {
		DataBaseServer.closeTable(sqls[1]);
		DataBaseServer.removeTableFormHashtable(sqls[1]); 
		DataBaseServer.delete(sqls[1]);
		 
	}

	//�������ľ���ʵ��
	private void crateTable() throws SQLException {
		logger.debug("create table \"" + sqls[1] + "\"");
		StringBuffer sb = new StringBuffer();
		sb.append(sqls[1] + ":");
		for(int i = 2; i < sqls.length; i++){
			sb.append(sqls[i].trim() + "," );
		}
		
		createTebaleImpl();		
		inserMetaData(sb);
		
		restartDataServer();
		
	}

	/**
	 * ����һ����Ӧ�������������񣬲�����Ԫ���ݣ�Ҳ����������
	 *
	 */
	private void restartDataServer() {
		try {
			DataBaseServer.restart();
		} catch (RmsAccessException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	private void createTebaleImpl() throws SQLException {
		try {
			DataBaseServer.putTableToHashtable(sqls[1], DataBaseServer.createTable(sqls[1]));
		} catch (RmsAccessException e) {
			 
			throw  SQLError.createSQLException(e.getMessage(), "Sql State : " + SQLError.SQL_STATE_OHTER);
		}
	}

	private void inserMetaData(StringBuffer sb) throws SQLException {
		if(DataBaseServer.findTableMetadataByName(sqls[1]) != null)
			throw SQLError.createSQLException("Table : "+ sqls[1] + " exists!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		
			try {
				DataBaseServer.store(DataBaseServer.DATAMETA, DataBaseServer.getBytes(sb.toString()));
			} catch (IOException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
	}


	
}
