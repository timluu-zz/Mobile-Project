
package org.wuhua.fuck.core.impl;

import org.wuhua.fuck.core.ResultSet;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>������SQLParser.java</b> </br> 
 * ��д����: 2007-2-7 <br/>
 * ������������ ����һ��SQL<br/>
 * �Ҷ�SQL�Ķ����ǣ�
 * create table tablename (int 
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
interface  SQLParser {
	
	
	/**
	 * �Դ���ͬ�����в�ͬ����,
	 * @throws SQLException -- �������﷨���ߣ���������ʱ���׳�
	 */
	ResultSet parser() throws SQLException;
	
	void setParameter(Object parameter);
}
