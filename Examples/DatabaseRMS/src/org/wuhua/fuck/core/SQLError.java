
package org.wuhua.fuck.core;

 

/**
 * <b>������SQLError.java</b> </br> 
 * ��д����: 2007-2-8 <br/>
 * ����������������һϵ�еĴ������ <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class SQLError {
	
	/**
	 * ��������ڴ�����Ҫ��CRUDʱ����
	 */
	public static final String SQL_STATE_BASE_TABLE_NOT_FOUND = "S0002"; //$NON-NLS-1$
	
	
	/**
	 * �����Ԫ���ݲ����ڴ�����Ҫ��CRUDʱ����
	 */
	public static final String SQL_STATE_BASE_TABLE_METADATA_NOT_FOUND = "S0002"; //$NON-NLS-1$
	
	/**
	 * ����sql�﷨��������������Լ�����һ���﷨��׼
	 */
	public static final String SQL_STATE_SYNTAX_ERROR = "42000"; //$NON-NLS-1$s
	
	/**
	 * �����ѯ����û���֣�
	 * �����Ҷ���һ���ֶ���������еĹؼ�����name�� ��ѯ��ʱ��һ��Ҫͨ������ؼ��ֽ��в�ѯ����ȻҲ���Զ���ؼ���
	 */
	public static final String SQL_STATE_INDEX_NOT_FOUND = "S0012"; //$NON-NLS-1$
	
	/**
	 * ��������ڴ�����Ҫ��CRUDʱ����
	 */
	public static final String SQL_STATE_COLUMN_NOT_FIND = "S0013"; //$NON-NLS-1$
	
	/**
	 * ��������ڴ�����Ҫ��CRUDʱ����
	 */
	public static final String SQL_STATE_OHTER = "S0000"; //$NON-NLS-1$


	/**
	 * ���ݲ�����ʱ��
	 */
	public static final String SQL_NO_DATA_EXISTS = "S0000"; //$NON-NLS-1$
	
	public static final SQLException createSQLException(String message,
			String sqlState) {
		
		return new SQLException(message, sqlState);

	}




}
