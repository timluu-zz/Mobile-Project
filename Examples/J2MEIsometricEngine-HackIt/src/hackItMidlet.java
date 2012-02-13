
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class hackItMidlet extends MIDlet {

    hackItCanvas canvas = null;  //Main canvas object

    public hackItMidlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void startApp() throws MIDletStateChangeException {
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current == null) {
            if (canvas == null) {
                canvas = new hackItCanvas(this);
            }
            Display.getDisplay(this).setCurrent(canvas);
        }

    }

    protected void pauseApp() {
        // TODO Auto-generated method stub
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub
    }
}
