
package org.wuhua.fuck.core.impl;

import org.wuhua.fuck.Logger;
import org.wuhua.fuck.core.Query;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>类名：QueryBuilder.java</b> </br> 
 * 编写日期: 2007-2-8 <br/>
 * 程序功能描述：通过sql语句生成一个Query <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class QueryBuilder {
	private static Logger logger =  Logger.getLogger("QueryBuilder");
	
	
	public static final Query builder(String sql) throws SQLException{
		if(sql == null
				|| sql.length() == 0){
			throw new NullPointerException("sql is not null");
		}
		return builderImpl(sql);
		 
	}

	private static Query builderImpl(String sql) throws SQLException {
		  sql.trim(); //去掉前后两个空格
		  sql.toLowerCase();
 		
		  return new QueryBaseImpl(new DataSQLParser(sql));	
	}

	 
}
