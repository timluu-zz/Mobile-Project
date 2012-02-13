/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Screen;


import ClassFrame.Image;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public abstract class IScreen {

    public CanvasGame canvas;

    public IScreen(CanvasGame _canvas) {
       this.canvas = _canvas;
    }

//    public abstract void initComponent();
//    public abstract void freeComponenet();

    public abstract void load_screen();
    public abstract void un_load_screen();

    public abstract void keyEvent(int keycode, int event);
    public abstract void pointerEvent(int x, int y, int event);
    public abstract void update();
    public abstract void paint(Graphics g);
//    public abstract void paintRotate(Graphics g);
}
