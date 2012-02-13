
package org.wuhua.fuck.core;

/**
 * <b>类名：ResultSet.java</b> </br> 
 * 编写日期: 2007-2-7 <br/>
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
public interface ResultSet {
	/**
	 * 下一行数据
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
