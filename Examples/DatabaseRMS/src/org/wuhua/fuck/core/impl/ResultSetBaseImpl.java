
package org.wuhua.fuck.core.impl;

import java.util.Hashtable;

import org.wuhua.fuck.core.ResultSet;
import org.wuhua.fuck.core.SQLError;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>������ResultSetBaseImpl.java</b> </br> 
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
  class ResultSetBaseImpl implements ResultSet {
	private Hashtable data;
	private Hashtable row;
	private java.util.Enumeration em;
	public ResultSetBaseImpl(Hashtable data){
		this.data =data;
		if(data != null){
			em = data.elements();
		}
		
	}
	
	public String getString(String columnName) throws SQLException {
		if(row == null){
			throw SQLError.createSQLException("NO Data exist!", "Sql State : " + SQLError.SQL_NO_DATA_EXISTS);
		}
	 
		 
		return (String) row.get(columnName);
	}

	public boolean next() throws SQLException {
		if(em == null)
			return false;
		boolean hasMore = em.hasMoreElements();
		if(hasMore){
			row = (Hashtable) em.nextElement();
		} 
		
		return hasMore;
	}

	public String getString(int columnIndex) throws SQLException {
		 return null;
	}
 

}
