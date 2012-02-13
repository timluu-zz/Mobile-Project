
package org.wuhua.fuck.core;

 

/**
 * <b>类名：SQLError.java</b> </br> 
 * 编写日期: 2007-2-8 <br/>
 * 程序功能描述：定义一系列的错误参数 <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class SQLError {
	
	/**
	 * 定义表不存在错误，主要是CRUD时候处理
	 */
	public static final String SQL_STATE_BASE_TABLE_NOT_FOUND = "S0002"; //$NON-NLS-1$
	
	
	/**
	 * 定义表元数据不存在错误，主要是CRUD时候处理
	 */
	public static final String SQL_STATE_BASE_TABLE_METADATA_NOT_FOUND = "S0002"; //$NON-NLS-1$
	
	/**
	 * 定义sql语法错误，在这里，我想自己定义一个语法标准
	 */
	public static final String SQL_STATE_SYNTAX_ERROR = "42000"; //$NON-NLS-1$s
	
	/**
	 * 定义查询索引没发现，
	 * 比如我定义一个字段在这个表中的关键字是name， 查询的时候一定要通过这个关键字进行查询，当然也可以多个关键字
	 */
	public static final String SQL_STATE_INDEX_NOT_FOUND = "S0012"; //$NON-NLS-1$
	
	/**
	 * 定义表不存在错误，主要是CRUD时候处理
	 */
	public static final String SQL_STATE_COLUMN_NOT_FIND = "S0013"; //$NON-NLS-1$
	
	/**
	 * 定义表不存在错误，主要是CRUD时候处理
	 */
	public static final String SQL_STATE_OHTER = "S0000"; //$NON-NLS-1$


	/**
	 * 数据不存在时候
	 */
	public static final String SQL_NO_DATA_EXISTS = "S0000"; //$NON-NLS-1$
	
	public static final SQLException createSQLException(String message,
			String sqlState) {
		
		return new SQLException(message, sqlState);

	}




}
