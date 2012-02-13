package game;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author joteitti
 */
public class Midlet extends MIDlet {

    private Game mGame;
    private Thread mThread;
    private Display mDisplay;

    public void startApp() {
        // Create game
        mGame = new Game();

        // Create thread for game
        mThread = new Thread(mGame);
        mThread.start();

        // Set current display to game engines canvas
        mDisplay = Display.getDisplay(this);
        mDisplay.setCurrent(mGame);
    }

    public void pauseApp() {
        // Shut down the game
        mGame.shutdown();
    }

    public void destroyApp(boolean unconditional) {
        mGame.shutdown();
    }
}
