
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class BasicIsoGameMidlet extends MIDlet {

    BasicIsoGameCanvas canvas = null;  //Main canvas object

    public BasicIsoGameMidlet() {
        super();
    }

    protected void startApp() throws MIDletStateChangeException {
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current == null) {
            if (canvas == null) {
                canvas = new BasicIsoGameCanvas(this);
            }
            Display.getDisplay(this).setCurrent(canvas);
        }

    }

    final void SizeChanged(BasicIsoGameCanvas c) {
        try {
            canvas.screen_width = c.getWidth();
            canvas.screen_height = c.getHeight();
            // If you need to force a particular screen size, do it here


            //#ifdef DEBUGGING
            //# System.out.println("[BasicIsoGameMidlet.SizeChanged]: screen_width = " + canvas.screen_width + ", screen_height = " + canvas.screen_height);
            //#endif
        } catch (Exception e) {
            //#ifdef DEBUGGING
            //# System.out.println("[BasicIsoGameMidlet.SizeChanged]: " + e);
            //#endif
        }
    }

    protected void pauseApp() {
        // TODO Pause state
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Clear resources
    }
}
