/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import javax.microedition.lcdui.Image;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

/**
 *
 * @author HTV
 */
public class SpaceShip extends javax.microedition.lcdui.game.Sprite {

    private int RATE = 5;
    //private int level=1;
    protected int speedX = 5;
    protected int speedY = 5;
    private static final int BULLET_SPEED = 10;
    private int HEIGHT  ;
    private int WIDTH ;
    //private static final int MAX_SEQUENCE = 8;
    private static final int[] SEQUENCE = {0,1,2,3,4,5,6,7};
    private static final int MAX_HP = 1000;
    //private static final int MAX_NORMAL_BULLET = 10;
    private static final int DAMAGE_RATE =20;
    //protected Vector
    private int fireTick =0;
    private int current_HP = MAX_HP;
    private int SHOOT_RATE=50;
    private boolean destroyed = false;
    private int damagedTick;

    private Bullet bullet;
    public SpaceShip(Image image, int w, int h) throws java.io.IOException {
        super(image,w ,h);
        //System.out.println(image.getHeight() +" " +image.getWidth());
        WIDTH = w;
        HEIGHT= h;
        setFrameSequence(SEQUENCE);
        defineReferencePixel(WIDTH/2+1,HEIGHT/2);
        this.setTransform(this.TRANS_MIRROR_ROT270);
    }
    
    public void initBullet(Image image, int w, int h) throws java.io.IOException{
        //bullet.destroy();
        bullet = new Bullet(image, w, h );
        
    }
    
    public void advance(int ticks) {
        if ((ticks%RATE==0))
            nextFrame();
    }

    public void moveLeft () {
        if (this.getRefPixelX()>0)
            this.move(-speedX, 0);
    }
    
    public void moveRight (int m) {
        if (this.getRefPixelX() < m)
            this.move(speedX, 0);
    }

    public void moveUp () {
        if (this.getRefPixelY()>0)
            this.move(0, -speedY);
    }
    
    public void moveDown (int m) {
        if (this.getRefPixelY()<m)
            this.move(0, speedY);
    }

    public Bullet fire (int ticks) {

        if (ticks- fireTick > SHOOT_RATE) {
            fireTick = ticks;
            bullet.setSpeed( BULLET_SPEED);
            bullet.shot(this.getRefPixelX(), this.getRefPixelY()+HEIGHT/2);

            return bullet;
        }
        else
            return null;
    }
    
    public void collised (int ticks, int damage) {
        if (!destroyed)
        if (ticks > damagedTick+DAMAGE_RATE ) {
            current_HP -=damage;
            if (current_HP <= 0) destroyed = true;
            damagedTick = ticks;
        }
    }

    public boolean isDestroyed () {
        return destroyed;
    }
    
    public double getHPPercentage() {
        if (!destroyed)
            return ((double)current_HP/(1.0*MAX_HP));
        else return 0;
    }

    public boolean isDamageable(int ticks) {
        return (ticks > damagedTick + DAMAGE_RATE);
    }
    
    public Bullet getBullet() {
        return bullet;
    }
}
