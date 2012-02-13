/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

/**
 *
 * @author QuyetNM1
 */

import ClassFrame.Constant;
import ClassFrame.Image;
import GamePlay.CanvasGame;
import Screen.Game;
import javax.microedition.lcdui.Graphics;

public abstract class AbstractObject {
    public static final int DOWN    = 0;
    public static final int UP      = 1;
    public static final int RIGHT   = 2;
    public static final int LEFT    = 3;
    public static final int STAND   = 4;
    public static final int JUMP    = 5;


    protected CanvasGame canvas;
    
    protected int image;
    
    protected int screen_x;
    protected int screen_y;
    protected int x;
    protected int y;
    
    protected int w;
    protected int h;
    
    protected int numFrame;
    protected int countFrame;
    
    protected int state;
    protected int direction;

    protected int vx;
    protected int vy;
    protected int t_jump;
    
    public AbstractObject(CanvasGame canvas, int screen_x, int screen_y, int image,
                                int w, int h, int numFrame, int state, int direction,
                                         int vx, int vy, int t_jump) {
        this.canvas = canvas;
        
        this.image = image;
        
        this.screen_x = screen_x;
        this.screen_y = screen_y;
        this.x = Constant.SCR_W  - (Constant.SCR_X - this.screen_x);
        this.y = Constant.SCR_H  - (Constant.SCR_Y - this.screen_y);
        
        this.w = w;
        this.h = h;
        
        this.numFrame = numFrame;
        this.countFrame = 0;
        
        this.state = state;
        this.direction = direction;

        this.vx = vx;
        this.vy = vy;
        this.t_jump = t_jump;
    }
    
    public int getImage() {
        return this.image;
    }
    
    public int getWidth() {
        return this.w;
    }
    
    public int getHeight() {
        return this.h;
    }

    public int getCountFrame() {
        return this.countFrame;
    }
    
    public int getNumFrame() {
        return this.numFrame; 
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getScreenX() {
        return this.screen_x;
    }
    
    public int getScreenY() {
        return this.screen_y; 
    }
    
    public CanvasGame getCanvasGame() {
        return this.canvas;
    }
    
    public int getState() {
        return this.state;
    }
    
    public int getDirection() {
        return this.direction;
    }

    public int getVx() {
        return this.vx;
    }

    public int getVy() {
        return this.vy;
    }


    public int getTjump() {
        return this.t_jump;
    }

    public void setNumFrame(final int num) {
        this.numFrame = num;
    }
    
    public void setCountFrame(final int count) {
        this.countFrame = count;
    }

    public void setWidth(final int width) {
        this.w = width;
    }
    
    public void setHeight(final int height) {
        this.h = height;
    }
    
    public void setImage(final int image) {
        this.image = image;
    }
    
    public void setState(final int state) {
        this.state = state;
        this.countFrame = 0;
    }
    
    public void setDirection(final int direction) {
        this.direction = direction;
        this.countFrame = 0;
    }

    public void setVx(final int vx) {
        this.vx = vx;
    }

    public void setVy(final int vy) {
        this.vy = vy;
    }

    public void setTjump(final int t_jump) {
        this.t_jump = t_jump;
    }

//    public void paint(Graphics g, Image ima) {
//        ima.drawImagePart(g, canvas, image, x, y, image_x + currentFrame*w, image_y, w, h);
//
//        ++ countFrame;
//        if(0 == countFrame % 4) {
//            ++ currentFrame;
//            if(currentFrame == numFrame) {
//                currentFrame = 0;
//            }
//        }
//    }
    
    public boolean collided(final AbstractObject abo) {
        if(this.x > abo.x + abo.w)  return false;
        if(this.x + this.w < abo.x) return false;
        if(this.y > abo.y + abo.h)  return false;
        if(this.y + this.h < abo.y) return false;
        
        return true;
    }
            
    public abstract void paint(Graphics g);

    public abstract void setX(final int x);
    
    public abstract void setY(final int y);
    
    public abstract void setScreenX(final int screen_x);
    
    public abstract void setScreenY(final int screen_y);
    
    public abstract void update();
}
