import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.wuhua.fuck.DataBaseServer;
import org.wuhua.fuck.core.RmsAccessException;
import org.wuhua.fuck.core.SQLException;
import org.wuhua.fuck.explame.SqlExplame;

/********************************************************************
 * ��Ŀ����				��<b>j2meѧϰ</b>			<br/>
 * 
 * Copyright 2005-2006 Wuhua. All rights reserved
 ********************************************************************/

/**
 * <b>������ExplameMIDlet.java</b> </br> 
 * ��д����: 2007-2-14 <br/>
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
public class ExplameMIDlet extends MIDlet {

	public ExplameMIDlet() {
		try {
			DataBaseServer.start();
		} catch (RmsAccessException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		try {
			SqlExplame.test();
		} catch (SQLException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}

	protected void pauseApp() {
		// TODO �Զ����ɷ������

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}

}
