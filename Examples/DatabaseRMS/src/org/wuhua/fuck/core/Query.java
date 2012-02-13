
package org.wuhua.fuck.core;

/**
 * <b>������Query.java</b> </br> 
 * ��д����: 2007-2-7 <br/>
 * ������������ SQL��ѯ���ӿ�.<br/>
 * ��Ҫͨ�������ķ�ʽ����һ��Query����
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public interface Query {
	/**
	 * ִ��sql���,������������κ�sql���
	 * @return
	 * @throws SQLException
	 */
	void execute()throws SQLException;
	
	/**
	 * ����ִ�в�ѯ���
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
