
package org.wuhua.fuck.core.impl;

import java.util.Hashtable;

import org.wuhua.fuck.StringUtil;
import org.wuhua.fuck.core.SQLError;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>������BaseSQLParser.java</b> </br> 
 * ��д����: 2007-2-9 <br/>
 * ������������ <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
  abstract class BaseSQLParser implements SQLParser {
	
	String sql;
	String[] sqls;
	Hashtable parameter;
	
	public final void setParameter(Object parameter) {
		this.parameter = (Hashtable) parameter;
		
	}

	BaseSQLParser(String sql) throws SQLException{
		this.sql = sql;
		ast(sql);
	}
	
	private void ast(String sql) throws SQLException {
	    sqls = StringUtil.split(sql," ");
		if(sqls == null 
			 || sqls.length  < 2){
			throw  SQLError.createSQLException("Sql  syntax error !", "Sql State : " + SQLError.SQL_STATE_SYNTAX_ERROR);
		}
			
		
	}

	public String toString(){
		return sql.toString();
	}
	
	 

}
