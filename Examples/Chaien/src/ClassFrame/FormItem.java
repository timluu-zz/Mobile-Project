/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFrame;

import GamePlay.CanvasGame;

/**
 *
 * @author QuyetNM1
 */
public abstract class FormItem {
    
    public static final int ENABLE      = 0;
    public static final int HOLD_CLICK  = 1;
    public static final int DISABLE     = 2;
    public static final int CLICKED     = 3;

    protected CanvasGame canvas;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int id;
    protected int state;
    protected String stringName;
    protected int image1;
    protected int image2;
    public FormItem(String stringName, int image1, int image2, int id, int x, int y, int w, int h, int state){
        this.stringName = stringName;
        this.image1 = image1;
        this.image2 = image2;
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.state = state;
    }

    public FormItem(CanvasGame canvas, String stringName, int image1, int image2, int id, int x, int y, int w, int h, int state){
        this.canvas = canvas;
        this.stringName = stringName;
        this.image1 = image1;
        this.image2 = image2;
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.state = state;
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getW(){
        return w;
    }
    public int getH(){
        return h;
    }
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setW(int w){
        this.w = w;
    }
    public void setH(int h){
        this.h = h;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStringName() {
        return stringName;
    }

    public void setStringName(String stringName) {
        this.stringName = stringName;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }


    
}
