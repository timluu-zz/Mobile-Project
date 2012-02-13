/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
/**
 *
 * @author HTV
 */
public class Star extends Sprite{
    
    private static int WIDTH = 10;
    private static int HEIGHT = 10;
    private static final int FRATE = 15;
    private static final int SPEED =1;
    private static final int[] SEQUENCE = {0} ;
    public Star (Image image, int w, int h, int x, int y) throws java.io.IOException {
        //stars initialize
        super(image, w, h);
        //System.out.println(image.getWidth() + " "  + image.getHeight());
        WIDTH = w;
        HEIGHT = h;
        setFrameSequence(SEQUENCE);
        this.setPosition(x, y);
    };
    public void advance (int ticks, int w, int h) {
        
         if (ticks%FRATE==0) {
                this.move(0, SPEED);
                //this.nextFrame();
         }
         if (this.getRefPixelY()>h)   {
             this.setRefPixelPosition(w, 0);}
    }
     
}
