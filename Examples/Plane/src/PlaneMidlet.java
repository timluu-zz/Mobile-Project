
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PlaneMidlet extends MIDlet {

    public MainCanvas m_MainCanvas;

    public PlaneMidlet() {
        super();
        // TODO �Զ���ɹ��캯����
    }

    protected void startApp() throws MIDletStateChangeException {
        m_MainCanvas = new MainCanvas(this);
        Display.getDisplay(this).setCurrent(m_MainCanvas);
    }

    protected void pauseApp() {
        // TODO �Զ���ɷ������
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO �Զ���ɷ������
    }
}
