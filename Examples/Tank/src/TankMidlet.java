import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class TankMidlet extends MIDlet {
	private MainCanvas m_TankCanvas;
	public TankMidlet() {
		super();
	}

	protected void startApp() throws MIDletStateChangeException {
		if (m_TankCanvas == null) {
			try {
				m_TankCanvas = new MainCanvas(this);
			} catch (IOException e) {}
		}  
		Display.getDisplay(this).setCurrent(m_TankCanvas);
	}

	protected void pauseApp() {
		// TODO 自动生成方法存根

	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

}
