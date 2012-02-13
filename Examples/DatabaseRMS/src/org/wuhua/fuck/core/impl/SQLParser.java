
package org.wuhua.fuck.core.impl;

import org.wuhua.fuck.core.ResultSet;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>类名：SQLParser.java</b> </br> 
 * 编写日期: 2007-2-7 <br/>
 * 程序功能描述： 解释一个SQL<br/>
 * 我对SQL的定义是：
 * create table tablename (int 
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
interface  SQLParser {
	
	
	/**
	 * 对待不同语句进行不同处理,
	 * @throws SQLException -- 语句出现语法或者，其他错误时候抛出
	 */
	ResultSet parser() throws SQLException;
	
	void setParameter(Object parameter);
}
