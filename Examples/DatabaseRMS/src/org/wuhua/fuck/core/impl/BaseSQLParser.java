
package org.wuhua.fuck.core.impl;

import java.util.Hashtable;

import org.wuhua.fuck.StringUtil;
import org.wuhua.fuck.core.SQLError;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>类名：BaseSQLParser.java</b> </br> 
 * 编写日期: 2007-2-9 <br/>
 * 程序功能描述： <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
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
