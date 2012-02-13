/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public abstract class Clock{
    public static final int SECOND = 1000;
    public static final int MAX_TIME = 120;
    
    protected int x;
    protected int y;
    
    protected int time;
    protected long startTime;
    protected long currentTime;
    
    protected CanvasGame canvas;
    protected int image;
    
    public Clock(CanvasGame canvas, int image, int x, int y, int time) {
        this.canvas = canvas;
        this.image = image;
        this.x = x;
        this.y = y;
        this.time = time;
        this.startTime = System.currentTimeMillis();
        this.currentTime = this.startTime;
    }
    
    public void paint(Graphics g) {
        canvas.getImage().drawNumber(g, canvas, image, time, 10, x, y);
    }
    
    public abstract void update();

    public int getTime(){
        return this.time;
    }
    public long getStartTime(){
        return this.startTime;
    }
    public long getCurrentTime(){
        return this.currentTime;
    }

    public void setTime(int time){
        this.time = time;
    }
    public void setStartTime(long startTime){
        this.startTime = startTime;
    }
    public void setCurrentTime(long currentTime){
        this.currentTime = currentTime;
    }
}
