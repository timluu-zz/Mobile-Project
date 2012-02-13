
package org.wuhua.fuck.core;

import org.wuhua.fuck.Logger;

 

/**
 * <b>类名：SQLException.java</b> </br> 
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
public class SQLException extends Exception{
	private Logger logger = Logger.getLogger("SQLException");
	
	private String sqlState;
	 
	public SQLException(String message, String sqlState) {
		super(message);
		this.sqlState = sqlState;
	}

	public SQLException() {
		 
	}
	
	public void printStackTrace(){
		logger.info("SQLException sqlState: " + sqlState);
		super.printStackTrace();
		
	}
}
