/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;



import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Mario
 */
public class Obstacle extends Sprite{
    protected boolean appear = true;
    protected boolean destroyable = true;
    private static final int damage = 150;
    private static final int score = 100;
    private static int WIDTH = 50;
    private static int HEIGHT = 41;
    protected int speedX, speedY;
    private int direction;
    protected  int FRATE = 2;
    
    public Obstacle(Image image, int w, int h) throws IOException {
        super(image, w, h);
        WIDTH = w;
        HEIGHT = h;
        defineReferencePixel(WIDTH / 2, HEIGHT / 2);
    }

    public Obstacle( Image image,int w, int h, int x, int y , boolean destroy ) throws IOException
    {
        super(image, w, h);
        WIDTH  = w;
        HEIGHT = h;
        defineReferencePixel(WIDTH / 2, HEIGHT / 2);
        speedX = x;
        speedY = y;
        destroyable =destroy;
    }

    public Obstacle(Image image, int w, int h, int x, int y , boolean destroy, int sx ) throws IOException {
        super(image, w, h);
        WIDTH = w;
        HEIGHT = h;
        defineReferencePixel(WIDTH / 2, HEIGHT / 2);
        speedX = x;
        speedY = y;
        destroyable =destroy;
        this.setRefPixelPosition(sx, - HEIGHT);
    }
    
    public Obstacle( String name, int x, int y , boolean destroy, int sx , int w, int h) throws IOException
    {
        super(Image.createImage(name), w, h);
        defineReferencePixel(w/ 2, h / 2);
        speedX = x;
        speedY = y;
        destroyable =destroy;
        this.setRefPixelPosition(sx, - h);
    }
    //getters for x and y already declared final in layer!

    public static int getWIDTH()
    {
        return WIDTH;
    }

    public static int getHEIGHT()
    {
        return HEIGHT;
    }

    public int getSpeedX()
    {
        return speedX;
    }

    public int getSpeedY()
    {
        return speedY;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setSpeedX(int speedX)
    {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY)
    {
        this.speedY = speedY;
    }

    public void advance (int tick,int x, int y) {
        if (appear) {
            if (tick%FRATE==0)
                this.move(speedX, speedY);
            if ((this.getX()>(x+WIDTH)) ||(x<0) ||(this.getY()>(y+HEIGHT))){
                destroy();
                //this.setTransform(tick%8);
            }
        }

    }

    public void reAppear(int x, int y, boolean d, int sx) {
        speedX = x;
        speedY = y;
        destroyable =d;
        this.setRefPixelPosition(sx, - HEIGHT);
        this.setVisible(true);
        appear = true;
    }

    public void destroy () {
         super.setVisible(false);
         appear = false;
    }

    public boolean isAppear() {
        return appear;
    }
       public int getDamage () {
           return damage;
       }
   public int getScore () {
       return score;
   }
}