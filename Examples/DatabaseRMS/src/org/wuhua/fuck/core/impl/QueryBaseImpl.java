
package org.wuhua.fuck.core.impl;

import java.util.Hashtable;

import org.wuhua.fuck.core.Query;
import org.wuhua.fuck.core.ResultSet;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>������QueryBaseImpl.java</b> </br> 
 * ��д����: 2007-2-8 <br/>
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
class QueryBaseImpl implements Query {
	SQLParser sqlparser;
	
	private Hashtable parameter;
	
	public QueryBaseImpl(SQLParser sqlparser){
		this.sqlparser = sqlparser;
		this.parameter = new Hashtable();
	}
	public ResultSet executeQuery() throws SQLException {
	
		return 	sqlparser.parser();
	}

	public void execute() throws SQLException {
		sqlparser.parser();
	}

	 
	public void setString(String columnName, String value) throws SQLException {
		parameter.put(columnName, value);
		sqlparser.setParameter(parameter);
		
	}
	 
}
