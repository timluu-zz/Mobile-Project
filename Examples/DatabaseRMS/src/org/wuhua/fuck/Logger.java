/********************************************************************
 * 项目名称				：<b>Figo 移动通信客户端</b>			<br/>
 * 
 * Copyright 2005-2006 Figo. All rights reserved
 ********************************************************************/
package org.wuhua.fuck;


 

/**
 * <b>类名：Logger.java</b> </br> 编写日期: 2006-6-12 <br/>
 * 程序功能描述：负责记录整个客户端系统日志,采用的是输出到屏幕,并未记录到文件中 <br/> Demo: Logger log =
 * Loggerger.getLoggerger(class) <br/> Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 变更作者 ：<br/> 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class Logger {
	private static int level = 4; // 1=info; 2=debug; 3=error; 4=fatal;

	//DateTime time;

	private String className; // 要记录日志的Class

	private Logger(String className) {
		//time = new DateTime(System.currentTimeMillis(), "GMT+8");

		this.className = className;

	}

	public static Logger getLogger(String className) {
		return new Logger(className);
	}

	public void info(Object mg) {
		mg = "Info: " + className.toString() + ": " + mg;
		println(mg);
	}

	public void debug(Object mg) {
		mg = "Debug: " + className.toString() + ": " + mg;
		if (level >= 2)
			println(mg);
	}

	public void debug(boolean mg) {
		Boolean b = new Boolean(mg);
		String mg1 = "Debug: " + className.toString() + ": " + b.toString();
		if (level >= 2)
			println(mg1);
	}

	public void debug(int _mg) {

		Object mg = "Debug: " + className.toString() + ": "
				+ new Integer(_mg).toString();
		if (level >= 2)
			println(mg);
	}

	public void error(Object mg) {
		mg = "Error: " + className.toString() + ": " + mg;
		if (level >= 3)
			println(mg);
	}

	public void error(Exception e, Object mg) {
		mg = "Info: " + className.toString() + ": " + mg;
		if (level >= 3)
			println(e, mg);

	}

	public void fatal(Exception e, Object mg) {
		mg = "Fatal: " + className.toString() + ": " + mg;
		if (level >= 4)
			println(e, mg);
	}

	private void println(Object mg) {
		System.out.println(System.currentTimeMillis() + " : " + mg);
	}

	private void println(Exception e, Object mg) {
		println(mg);
		if (e != null)
			e.printStackTrace();
	}

}
