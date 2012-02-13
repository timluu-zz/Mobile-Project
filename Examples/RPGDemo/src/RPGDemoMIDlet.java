
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class RPGDemoMIDlet extends MIDlet {

    private RPGCanvas myCanvas;

    public RPGDemoMIDlet() {
        myCanvas = new RPGCanvas();

    }

    protected void startApp() throws MIDletStateChangeException {
        Display.getDisplay(this).setCurrent(myCanvas);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        myCanvas.stop();
    }
}