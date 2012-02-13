/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;


import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author Administrator
 */
public class Bullet extends Sprite {

//    private int xPosition;
//    private int yPosition;
    private boolean active = true;
    
    //private int damage = 100;
    private int xDirection = 0;
    private int yDirection = 0;

    //private int ammu=0;
    private static int HEIGHT = 25;
    private static int WIDTH = 25;
    private int[] SEQUENCE;
    private static final int FRATE = 1;
    public Bullet (Image image, int w, int h ) throws IOException{
        super(image,w ,h);
        //System.out.println(image.getHeight()+ " " + image.getWidth());
        WIDTH  = w;
        HEIGHT = h;
        defineReferencePixel(WIDTH/2,HEIGHT/2);
    };
    public Bullet (Image image, int w, int h, int x, int y, int xDirection, int yDirection) throws IOException {
        // initialized bullet
        super(image, w, h);
        WIDTH  = w;
        HEIGHT = h;
        setFrameSequence(SEQUENCE);
        defineReferencePixel(WIDTH/2,HEIGHT/2);
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.setRefPixelPosition(x, y);
        
    }

    public void setSpeed(int speed)
    {
        this.yDirection = speed;
    }

    public int getSpeed()
    {
        return this.yDirection;
    }

    public void advance (int tick) {
        // bullet rate
     if (tick%FRATE==0 && active ==true)
        if (this.getRefPixelY()>0)
            this.move(xDirection, -yDirection);
        else {
            super.setVisible(false);
            active = false;
        }
     }

    public void shot(int w, int h) {
        active = true;
        this.setRefPixelPosition(w, h);
        this.setVisible(true);
    }

     public boolean isAppear () {
         return active;
     }
     public void destroy () {
         super.setVisible(false);
         active = false;
     }



}
