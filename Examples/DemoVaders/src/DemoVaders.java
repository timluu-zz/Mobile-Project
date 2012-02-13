
/**
 * MIDP 2.0 Sample application
 *
 * @author Alan Newman - alan@sensibledevelopment.net
 * @version 1.00 04/05/18
 */
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.io.IOException;

public class DemoVaders extends MIDlet implements CommandListener {

    private Command exit;
    private Command pause;
    private Command resume;
    private VaderCanvas gameCanvas;
    private Display display;
    private Displayable displayable;

    public DemoVaders() {
        display = Display.getDisplay(this);

    }

    protected void startApp() throws MIDletStateChangeException {

        getCanvasDisplay();
        display.setCurrent(displayable);

    }

    protected void pauseApp() {
        if (displayable != null) {
            display.setCurrent(displayable);
        }
        releaseResource();
    }

    public void destroyApp(boolean unconditional) {
        releaseResource();
    }

    private void releaseResource() {
        if (gameCanvas != null) {
            gameCanvas.stop();
        }
    }

    private void getCanvasDisplay() {
        try {
            if (gameCanvas == null) {
                gameCanvas = new VaderCanvas(this);
                //create the commands for both the gameCanvas and pauseCanvas
                exit = new Command("Exit", Command.EXIT, 1);
                pause = new Command("Pause", Command.ITEM, 2);
                gameCanvas.addCommand(exit);
                gameCanvas.addCommand(pause);
                gameCanvas.setCommandListener(this);
            }
            if (!gameCanvas.isRunning()) {
                gameCanvas.start();
            }
            displayable = gameCanvas;
        } catch (IOException ioe) {
        }
    }

    public void commandAction(Command command, Displayable d) {
        if (command == exit) {
            destroyApp(true);
            notifyDestroyed();
        } else if (command == pause) {
            //displayable=pauseCanvas;
            pauseApp();
        } else if (command == resume) {
            try {
                startApp();
            } catch (MIDletStateChangeException msce) {
            }
        }

    }
}
