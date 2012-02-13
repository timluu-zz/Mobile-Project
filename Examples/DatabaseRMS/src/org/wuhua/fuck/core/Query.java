
package org.wuhua.fuck.core;

/**
 * <b>类名：Query.java</b> </br> 
 * 编写日期: 2007-2-7 <br/>
 * 程序功能描述： SQL查询语句接口.<br/>
 * 主要通过工厂的方式生成一个Query对象。
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public interface Query {
	/**
	 * 执行sql语句,这个可以用于任何sql语句
	 * @return
	 * @throws SQLException
	 */
	void execute()throws SQLException;
	
	/**
	 * 用于执行查询语句
	 * @return
	 * @throws SQLException
	 */
	ResultSet executeQuery()throws SQLException;
	
//	void setString(int parameterIndex, String value) throws SQLException;
	void setString(String columnName, String value) throws SQLException;
	//void setBoolean(int parameterIndex, boolean value) throws SQLException;
	//void setInt(int parameterIndex, int value) throws SQLException;
	//void setLong(int parameterIndex, long value) throws SQLException;
	//void setShort(int parameterIndex, short value) throws SQLException;
	//void setBytes(int parameterIndex, byte[] value) throws SQLException;
	
}
