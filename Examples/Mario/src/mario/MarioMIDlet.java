/**
 * <p>Title: Mario</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file any part without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.0.0
 */
package mario;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import lipeng.LPIGetCanvas;
import lipeng.LPProgressThread;

public class MarioMIDlet
        extends MIDlet
        implements LPIGetCanvas {

    public MarioMIDlet() {
        gameLoading = new LPProgressThread(this);
    }

    public void startApp() {
        if (gameLoading.loadProgressIsOver) {
            Display.getDisplay(this).setCurrent(gameCanvas);
            gameCanvas.start();
        } else {
            Display.getDisplay(this).setCurrent(gameLoading);
            gameLoading.start();
            if (gameLoading.gaugeCnt == 0) {
                gameCanvas = new MarioGameCanvas(this);
            }
        }

    }

    public Canvas getCanvas() {
        return gameCanvas;
    }

    public void pauseApp() {
        if (gameLoading.loadProgressIsOver) {
            gameCanvas.stop();
        } else {
            gameLoading.stop();
        }
    }

    public void destroyApp(boolean unconditional) {
        if (gameLoading.loadProgressIsOver) {
            gameCanvas.record.close();
            gameCanvas.stop();
        }
    }

    public void exitMIDlet() {
        destroyApp(false);
        notifyDestroyed();
    }
    static public MarioGameCanvas gameCanvas;
    public LPProgressThread gameLoading;
}
