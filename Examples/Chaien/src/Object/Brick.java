/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Object;

import Screen.Game;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class Brick {

    public static final int brick_w = 30;
    public static final int brick_h = 30;
    public static final int NONE    = 0;
    public static final int BRICK1  = 1;
    public static final int BRICK2  = 2;
    public static final int BRICK3  = 3;
    public static final int BRICK4  = 4;
    public static final int BRICK5  = 5;
    public static final int BRICK6  = 6;
    public static final int BRICK7  = 7;
    public static final int BRICK8  = 8;
    public static final int BRICK9  = 9;
    public static final int BRICK10 = 10;
    public static final int BRICK11 = 11;
    public static final int BRICK12 = 12;
    public static final int BRICK13 = 13;
    public static final int BRICK14 = 14;
    public static final int BRICK15 = 15;
    private int x;
    private int y;
    private int is_active;
    private int realWith;
    private int width;
    private int height;
    private int frame;
    private int type;

    public Brick(int realWith, int y, int type){
        this.realWith = realWith;
        this.y = y;
//        translateY(y);
        this.type = type;
        this.is_active = 1;
        this.frame = 0;
        this.x = Game.getScrRealWidth() - this.realWith;
        this.type = type;
        this.width = brick_w;
        this.height = brick_h;
    }

    //Translate khi su dung touch
    private void translateY(int y){
        switch(y){
            case 0:
                this.y = 0;
                break;
            case 30:
                this.y = 40;
                break;
            case 60:
                this.y = 80;
                break;
            case 90:
                this.y = 120;
                break;
            case 100:
                this.y = 130;
                break;
            case 120:
                this.y = 160;
                break;
            case 200:
                this.y = 270;
                break;
            case 170:
                this.y = 240;
                break;
            case 140:
                this.y = 200;
                break;
            case 110:
                this.y = 160;
                break;
             case 80:
                this.y = 120;
                break;
        }
    }
    
    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getRealWith() {
        return realWith;
    }

    public void setRealWith(int realWith) {
        this.realWith = realWith;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
}
