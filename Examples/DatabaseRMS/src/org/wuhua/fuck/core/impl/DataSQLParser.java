
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
 * <b>类名：DataSQLParser.java</b> </br> 
 * 编写日期: 2007-2-12 <br/>
 * 程序功能描述：提供对inser， update， delete， select的支持 <br/>
 * 对表格的创建，删除，暂时不提供修改的功能。 <br/>
 * 这个是小型数据操作的引擎.<br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
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
	 * 更新表数据
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
		 
		
		
		
		//查询。并从查询的数据中发现那些数据，并更新
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
				logger.debug("更新的数据是: "  + values);
				DataBaseServer.modifyDataFromId(sqls[1], id, DataBaseServer.getBytes(values.toString()));
			 
			} catch (IOException e) {
				 
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询数据,暂时不支持多字段查询
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
	 * 删除所有数据的原则是，先删除表格，再创建一个新的表格
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
	 * 插入数据语法
	 * insert tablename column1 ... ....
	 *  保存数据的格式是
	 * 然后调用
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
			// TODO 自动生成 catch 块
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

	//创建表格的具体实现
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
	 * 增加一个表，应该重新启动服务，并载入元数据，也就是热启动
	 *
	 */
	private void restartDataServer() {
		try {
			DataBaseServer.restart();
		} catch (RmsAccessException e) {
			// TODO 自动生成 catch 块
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
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
	}


	
}
