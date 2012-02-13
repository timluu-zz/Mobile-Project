/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author joteitti
 */
public class Game extends GameCanvas implements Runnable {

    public static final int EVENT_NONE = 0;
    public static final int EVENT_SHUTDOWN = 1;
    public static final int EVENT_PLAY = 2;
    public static final int EVENT_RESET = 3;
    public static final int EVENT_MENU = 4;
    public static final int KEY_PARAMETER_EVENT = 0;
    public static final int KEY_PARAMETER_CODE = 1;
    public static final int KEY_EVENT_NONE = 0;
    public static final int KEY_EVENT_PRESSED = 1;
    public static final int KEY_EVENT_RELEASED = 2;
    public static final int KEY_CODE_NONE = 0;
    public static final int KEY_CODE_UP = 1;
    public static final int KEY_CODE_DOWN = 2;
    public static final int KEY_CODE_LEFT = 3;
    public static final int KEY_CODE_RIGHT = 4;
    public static final int KEY_CODE_ACTION = 5;
    public static final int KEY_CODE_SOFTKEY_POSITIVE = 6;
    public static final int KEY_CODE_SOFTKEY_NEGATIVE = 7;
    public static final boolean SHOW_DELTA_TIME = false;
    private static Vector smKeyEvents;
    private boolean mShutdown;
    private GameEngine mGameEngine;
    private static int smScreenHeight;
    private static int smScreenWidth;
    private static Game smGame;

    static {
        // Create key events
        smKeyEvents = new Vector(8);
    }

    public Game() {
        super(false);

        // Create game engine
        mGameEngine = new GameEngine();

        // Get screen size
        smScreenHeight = getHeight();
        smScreenWidth = getWidth();

        smGame = this;
    }

    public static int getScreenWidth() {
        return smScreenWidth;
    }

    public static int getScreenHeight() {
        return smScreenHeight;
    }

    public void run() {
        // Set fullscreen mode on
        setFullScreenMode(true);

        // Get graphics
        Graphics g = getGraphics();

        // Initialize game engine
        mGameEngine.initialize(false);

        long systemTime = System.currentTimeMillis();

        // Clear keys
        resetKeys();

        // Game engine update loop
        int timeBuffer = 0;
        while (!mShutdown) {
            long newSystemTime = System.currentTimeMillis();

            // Calcualte delta time
            int deltaTime = (int) (newSystemTime - systemTime);
            systemTime = newSystemTime;

            // Elpased time must at least 50 ms
            timeBuffer += deltaTime;
            if (timeBuffer < 18) {
                continue;
            }

            // Update screen size
            smScreenHeight = getHeight();
            smScreenWidth = getWidth();

            // Update game logic
            int event = mGameEngine.logicUpdate(timeBuffer);

            // Process event
            if (event == EVENT_SHUTDOWN) {
                shutdown();
                System.out.println("Shutdown!");
            }

            // Clear screen
            g.setColor(0x0000FF);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw game
            mGameEngine.draw(g);

            // Draw delta time
            if (SHOW_DELTA_TIME) {
                g.setColor(0xFFFF00);
                g.drawString(Integer.toString(timeBuffer) + "ms ", 0, 0, Graphics.TOP | Graphics.LEFT);
            }

            // Swap backbuffer
            flushGraphics();

            // Reset time buffer
            timeBuffer = 0;
        }
    }

    public void shutdown() {
        mShutdown = true;
    }

    public void keyPressed(int keyCode) {
        smKeyEvents.addElement(createKeyEvent(KEY_EVENT_PRESSED, keyCode));
    }

    public void keyReleased(int keyCode) {
        smKeyEvents.addElement(createKeyEvent(KEY_EVENT_RELEASED, keyCode));
    }

    public static int getCurrentKeyStates() {
        if (smGame != null) {
            return smGame.getKeyStates();
        } else {
            return 0;
        }
    }

    public static Vector getKeys() {
        return smKeyEvents;
    }

    public static boolean hasKey() {
        return !smKeyEvents.isEmpty();
    }

    public static int[] popKey() {
        // Pop first
        int[] event = (int[]) smKeyEvents.firstElement();
        smKeyEvents.removeElementAt(0);

        return event;
    }

    public static void resetKeys() {
        smKeyEvents.removeAllElements();
    }

    private int[] createKeyEvent(int eventType, int keyCode) {
        // TODO These soft key values wont work on LG, Panasonic, Motorola and Siemenss mobile phones
        final int SOFTKEY_POSITIVE = -6;
        final int SOFTKEY_NEGATIVE = -7;
        final int KEY_UP = -1;
        final int KEY_DOWN = -2;
        final int KEY_LEFT = -3;
        final int KEY_RIGHT = -4;

        int[] event = new int[2];
        event[KEY_PARAMETER_EVENT] = eventType;
        switch (keyCode) {
            case SOFTKEY_POSITIVE:
                event[KEY_PARAMETER_CODE] = KEY_CODE_SOFTKEY_POSITIVE;
                break;

            case SOFTKEY_NEGATIVE:
                event[KEY_PARAMETER_CODE] = KEY_CODE_SOFTKEY_NEGATIVE;
                break;

            case KEY_UP:
            case GameCanvas.UP:
            case GameCanvas.KEY_NUM2:
                event[KEY_PARAMETER_CODE] = KEY_CODE_UP;
                break;

            case KEY_DOWN:
            case GameCanvas.DOWN:
            case GameCanvas.KEY_NUM8:
                event[KEY_PARAMETER_CODE] = KEY_CODE_DOWN;
                break;

            case KEY_LEFT:
            case GameCanvas.LEFT:
            case GameCanvas.KEY_NUM4:
                event[KEY_PARAMETER_CODE] = KEY_CODE_LEFT;
                break;

            case KEY_RIGHT:
            case GameCanvas.RIGHT:
            case GameCanvas.KEY_NUM6:
                event[KEY_PARAMETER_CODE] = KEY_CODE_RIGHT;
                break;

            case GameCanvas.KEY_STAR:
            case GameCanvas.FIRE:
            case GameCanvas.KEY_NUM5:
                event[KEY_PARAMETER_CODE] = KEY_CODE_ACTION;
                break;
        }

        return event;
    }
}
