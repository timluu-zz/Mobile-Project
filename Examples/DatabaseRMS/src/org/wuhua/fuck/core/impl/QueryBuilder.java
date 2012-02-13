
package org.wuhua.fuck.core.impl;

import org.wuhua.fuck.Logger;
import org.wuhua.fuck.core.Query;
import org.wuhua.fuck.core.SQLException;

/**
 * <b>������QueryBuilder.java</b> </br> 
 * ��д����: 2007-2-8 <br/>
 * ������������ͨ��sql�������һ��Query <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
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
		  sql.trim(); //ȥ��ǰ�������ո�
		  sql.toLowerCase();
 		
		  return new QueryBaseImpl(new DataSQLParser(sql));	
	}

	 
}
