
package org.wuhua.fuck.core;

/**
 * <b>������ResultSet.java</b> </br> 
 * ��д����: 2007-2-7 <br/>
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
public interface ResultSet {
	/**
	 * ��һ������
	 */
	boolean next() throws SQLException;
	
	String getString(String columnName) throws SQLException;
	
//String getString(int columnIndex) throws SQLException;
	
	/*int getInt(String columnName) throws SQLException;
	
	int getInt(int columIndex) throws SQLException;
	
	long getLong(String columnName) throws SQLException;
	
	long getLong(int columIndex) throws SQLException;
	
	short getShort(String columnName) throws SQLException;
	
	short getShort(int columIndex) throws SQLException;
	
	byte[] getBytes(String columnName) throws SQLException;
	
	byte[] getBytes(int columIndex) throws SQLException;
	*/
}
