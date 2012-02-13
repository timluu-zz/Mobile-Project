/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import ClassFrame.Resource;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author ChungNV4
 */
public class Bullet extends AbstractObject{

    private int frame;
    private int currentPositionH;
    public Bullet(CanvasGame canvas, int x, int y)
    {
        super(canvas, x, y, 0, 0, 0, 0, 0, 0, 0, 0, 0);        
        frame = 0;
        currentPositionH=y;
        this.w=23;
        this.h=30;
    }
    
    public void update() {        
        if(getNumFrame()<3&&getNumFrame()>0)
        {
            frame=this.getNumFrame();
            frame++;
            this.setNumFrame(frame);
        }
        else
        {
            this.setNumFrame(0);
        }        
        if(y>15)
        {
            y-=10;
        }        
    }
    
    public void paint(Graphics g) {
        update();
        canvas.getImage().drawImagePart(g, canvas, Resource.IMG_BULLET, x, y, 0, getNumFrame()*23, 23, 27);
    }

    public void setX(int x) {
        this.x = x;        
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScreenX(int screen_x) {
    }

    public void setScreenY(int screen_y) {
    }    
}
