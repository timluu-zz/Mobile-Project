
package org.wuhua.fuck.core;

import org.wuhua.fuck.Logger;

 

/**
 * <b>������SQLException.java</b> </br> 
 * ��д����: 2007-2-7 <br/>
 * ������������ <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
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
