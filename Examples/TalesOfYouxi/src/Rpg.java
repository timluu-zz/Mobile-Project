/*
 * @(#)Rpg.java 1.0 05/04/16
 *
 *  MIDlet Project
 * 
 * 	Modify the code below to finish your midlet
 */

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class Rpg extends MIDlet implements CommandListener {

    public final static byte STATE_MENU = 1;
    public final static byte STATE_CONTINUE = 2;
    public final static byte STATE_SETTING = 3;
    public final static byte STATE_HELP = 4;
    public final static byte STATE_ABOUT = 5;
    public final static byte STATE_EXIT = 6;
    public final static byte STATE_NEW = 7;
    private boolean firstRun = true;
    private byte gameState;

    public Rpg() {
    }

    public void startApp() {
        if (firstRun) {
            firstRun = false;
            LogoCanvas lc = new LogoCanvas(this);
            Display.getDisplay(this).setCurrent(lc);
            Thread lct = new Thread(lc);
            lct.start();
        }
    }

    public void setState(byte newState) {
        gameState = newState;
        switch (gameState) {
            case STATE_MENU:
                MenuCanvas menu = new MenuCanvas(this);
                Display.getDisplay(this).setCurrent(menu);
                Thread mt = new Thread(menu);
                mt.start();
                break;
            case STATE_CONTINUE:
                GameData gd = GameData.loadData();
                if (gd != null) {
                    RpgCanvas rpgCan = new RpgCanvas(gd, this);
                    Display.getDisplay(this).setCurrent(rpgCan);
                    Thread gt = new Thread(rpgCan);
                    gt.start();
                }
                gameState = STATE_MENU;
                break;
            case STATE_NEW:
                GameData g = new GameData();
                g.newGame();
                RpgCanvas gameCan = new RpgCanvas(g, this);
                Display.getDisplay(this).setCurrent(gameCan);
                Thread t = new Thread(gameCan);
                t.start();
                break;
            case STATE_SETTING:
                break;
            case STATE_HELP:
                HelpCanvas help = new HelpCanvas(this);
                Display.getDisplay(this).setCurrent(help);
                break;
            case STATE_ABOUT:
                break;
            case STATE_EXIT:
                notifyDestroyed();
                break;
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
    }
}
