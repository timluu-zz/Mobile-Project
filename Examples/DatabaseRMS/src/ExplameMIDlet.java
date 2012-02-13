import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.wuhua.fuck.DataBaseServer;
import org.wuhua.fuck.core.RmsAccessException;
import org.wuhua.fuck.core.SQLException;
import org.wuhua.fuck.explame.SqlExplame;

/********************************************************************
 * 项目名称				：<b>j2me学习</b>			<br/>
 * 
 * Copyright 2005-2006 Wuhua. All rights reserved
 ********************************************************************/

/**
 * <b>类名：ExplameMIDlet.java</b> </br> 
 * 编写日期: 2007-2-14 <br/>
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
public class ExplameMIDlet extends MIDlet {

	public ExplameMIDlet() {
		try {
			DataBaseServer.start();
		} catch (RmsAccessException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		try {
			SqlExplame.test();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

	protected void pauseApp() {
		// TODO 自动生成方法存根

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

}
