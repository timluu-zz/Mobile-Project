
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MMRpgMidlet extends MIDlet {

    private MainCanvas m_Canvas = null;

    public MMRpgMidlet() {
        super();
        // TODO �Զ���ɹ��캯����
    }

    protected void startApp() throws MIDletStateChangeException {
        if (m_Canvas == null) {
            m_Canvas = new MainCanvas(this);
        }
        Display.getDisplay(this).setCurrent(m_Canvas);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO �Զ���ɷ������
    }
}
