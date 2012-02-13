/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePlay;

import ClassFrame.Resource;
import Screen.Game;
import Screen.Pause;
import javax.microedition.lcdui.Display;
import javax.microedition.media.MediaException;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author QuyetNM1
 */
public class MIDletGame extends MIDlet {

    static CanvasGame canvas;

    public MIDletGame() {
        //startApp();
    }

    public void startApp() {
        canvas = new CanvasGame(this);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
        exit();
    }

    public void exit() {
        //gamePlay.freeComponents();
        //canvas.getResource().getSound(Resource.SOUND_MUSIC).release();
        canvas = null;

        notifyDestroyed();
    }
}
