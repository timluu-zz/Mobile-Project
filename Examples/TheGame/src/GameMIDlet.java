/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;

/**
 * @author personal
 */
public class GameMIDlet extends MIDlet implements CommandListener {

    private Display display;
    private List startuplist;
    private GameScreen theGame;
    private Command exit;
    private String[] ELEMENTS = {"New", "High Score"};
    private SplashScreen splash;

    public GameMIDlet() {
        exit = new Command("Exit", Command.EXIT, 0);
        startuplist = new List("The Game", List.IMPLICIT, ELEMENTS, null);
        startuplist.addCommand(exit);
        startuplist.setCommandListener(this);
        splash = new SplashScreen(this);

    }

    public void startApp() {
        if (display == null) {
            display = Display.getDisplay(this);
        }
        display.setCurrent(splash);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exit && d == startuplist) {
            destroyApp(false);
            notifyDestroyed();
        } else if (c == List.SELECT_COMMAND && d == startuplist) {
            String chosen = startuplist.getString(startuplist.getSelectedIndex());
            if (chosen.equals("New")) {
                theGame = new GameScreen(this);
                display.setCurrent(theGame);
                theGame.start();
            } else {
                //lihat high score
            }
        }
    }

    public void showMainScreen() {
        display.setCurrent(startuplist);
    }
}
