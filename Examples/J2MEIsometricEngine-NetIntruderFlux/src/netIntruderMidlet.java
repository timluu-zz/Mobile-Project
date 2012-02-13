import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Net Intruder: midlet class
 * 
 * This software is released under GNU GPL license. View license.txt for more details.
 * 
 * @version 0.9
 * @author Massimo Maria Avvisati (http://www.mondonerd.com)
 * 
 */


public class netIntruderMidlet extends MIDlet {
    
    netIntruderCanvas canvas = null;
    

    /**
     * Main constructor
     *
     */
    public netIntruderMidlet() {
        super();
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current == null) {
            if (canvas == null) {
                canvas = new netIntruderCanvas(this);
            }
            Display.getDisplay(this).setCurrent(canvas);
        }
        
    }

    protected void startApp() throws MIDletStateChangeException {
        // TODO Auto-generated method stub

    }

    protected void pauseApp() {
        // TODO Auto-generated method stub

    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub

    }

}
