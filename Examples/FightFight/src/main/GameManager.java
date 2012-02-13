/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;


import java.util.Random;
import java.io.IOException;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.Image;
import java.io.InputStream;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.Manager;
/**
 *
 * @author HTV
 */


public class GameManager extends LayerManager {
  
    // game parameters
    // asteroid
    private Image asteroidImage;
    private static final String ASTEROID_IMAGE      =   "/resource/5.png";
    private static final int    ASTEROID_WIDTH      =   50;
    private static final int    ASTEROID_HEIGHT     =   41;
    private static final int MAX_OBS = 5; // max asteroids
    private int OBS_MAX_X_RATE = 3; // asteroid x speed +
    private int OBS_MIN_X_RATE = -3;// asteroid x speed -
    private int OBS_MAX_Y_RATE = 4;
    private int OBS_MIN_Y_RATE = 1;
    //stars
    private Image starImage;
    private static final String STAR_IMAGE      = "/resource/stars.png";
    private static final int    STAR_WIDTH      = 10;
    private static final int    STAR_HEIGHT     = 10;
    private static final int    MAX_STARS       = 30; // max stars displayed
    private static final int    OBS_RATE        = 30; // rate at which ateroids appear
    private int obsAppTick =100; // asteroid timer, control the appearence of asteroid
    // ship
    private Image shipImage;
    private static final String SHIP_IMAGE    = "/resource/blue_ship.png";
    private static final int    SHIP_WIDTH    = 40;
    private static final int    SHIP_HEIGHT   = 33;
    // bullet Image bullet 
    private Image bulletImage ;
    private static final String BULLET_IMAGE    = "/resource/shot.png";
    private static final int    BULLET_WIDTH    = 25;
    private static final int    BULLET_HEIGHT   = 25;


    // game windows
    private int canvasX, canvasY;
    private int leftPosX=0, leftPosY=0;// game windows
    private int height, width;


    // variables and objects
    protected   boolean GameOver    = false; // game condition
    private     int     score       = 0; // score
    private     Random  r           = new Random(System.currentTimeMillis()); // random generator
    protected   SSGameCanvas    gameCanvas;
    //private     BackgroundLayer background;
    private     SpaceShip       ship;
    private     Player          player = null;
    private     Bullet          bullet ;

        // create asteroids array
    private Obstacle [] obs = new Obstacle [MAX_OBS];
    // create stars array
    private Star [] stars = new Star[MAX_STARS];
    // temp asteroid
    private Obstacle t ;
    private Bullet newBullet;


    // constructor
    public GameManager(int x, int y, int height, int width, SSGameCanvas theCanvas) throws IOException{
        super();
        this.canvasX = x;
        this.canvasY = y;
        this.height = height;
        this.width = width;
        this.gameCanvas = theCanvas;
        //setViewWindow();
        setViewWindow(leftPosX,leftPosY,width,height);

        //---------------------------------------------------------------------
        // LOAD SHIP
        //---------------------------------------------------------------------
        // load ship image
        shipImage = Image.createImage(SHIP_IMAGE);
        // create space ship
        ship = new SpaceShip(shipImage, SHIP_WIDTH, SHIP_HEIGHT);
        try {
            InputStream in = getClass().getResourceAsStream("/resource/shoting.wav");
            player = Manager.createPlayer(in,"audio/x-wav");
            player.setLoopCount(1);
            //player.start();

        } catch (MediaException ex) {
            ex.printStackTrace();
        }
        // set it position
        ship.setRefPixelPosition(height/2, width/2);
        this.append(ship);
        bulletImage = Image.createImage(BULLET_IMAGE);
        bullet = new Bullet (bulletImage, BULLET_WIDTH, BULLET_HEIGHT);
        ship.initBullet(bulletImage, BULLET_WIDTH, BULLET_HEIGHT);


        //--------------------------------------------------------------------
        // LOAD STARS
        //--------------------------------------------------------------------
        // create stars
        starImage = Image.createImage(STAR_IMAGE);
        createStars();
        /*
        // create layer
        try {
            this.background = new BackgroundLayer(height);
        }
        catch (java.lang.Exception e) {e.printStackTrace();}
        //this.append(background);
*/
        //-------------------------------------------------------------------
        // LOAD ASTEROIDS
        //-------------------------------------------------------------------
        asteroidImage = Image.createImage(ASTEROID_IMAGE);
        
    }
    
    public void paint(Graphics g) {
        // paint graphics
        paint(g,canvasX,canvasY);
        // draw full health bar
        g.setColor(255, 255, 250); // red
        g.fillRect(canvasX, canvasY, width/2, 10);
        // draw current health bar
        g.setColor(125, 255, 10); // yellow
        if (ship.getHPPercentage()>=0)
            g.fillRect(canvasX+1, canvasY+1, convert2Long((ship.getHPPercentage())*(width/2)), 9);

        // draw score
        g.setColor(255, 255, 255);
        g.drawString("" + score, width - 20, 10, g.TOP|g.LEFT);
        //game over?
        if (GameOver) endGame(g);
        // paint background
        //background.paint(g);
    }

    public void advance(int ticks) {
        //if (GameOver) return;
        int keyState = gameCanvas.getKeyStates();
        // turn shift to right
        if ((keyState & GameCanvas.RIGHT_PRESSED) != 0){
            ship.moveRight(width);
        }
        // turn shift to left
        if ((keyState & GameCanvas.LEFT_PRESSED) != 0){
            ship.moveLeft();
        }
        // speeds up
        if ((keyState & GameCanvas.UP_PRESSED) != 0){
            ship.moveUp();
        }
        // slow down
        if ((keyState & GameCanvas.DOWN_PRESSED) != 0){
            ship.moveDown(height);
        }
        // fire
        if ((keyState & GameCanvas.FIRE_PRESSED) != 0){
           try {
              newBullet = ship.fire(ticks); // fire new bullet
              // throw old bullet away
              if (newBullet != null)  {
                try {
                    
                    player.stop();
                    player.setLoopCount(1);
                    player.start();
                }
                catch (MediaException ex) {
                    ex.printStackTrace();
                }
                  this.remove(bullet);
                  bullet = newBullet;
                  this.append(bullet);
              }
           } catch (java.lang.NullPointerException e) { e.printStackTrace();}
            System.out.println(" Object created");
        }

        // create new asteroid if the old has disappeared
        try {
            createObstacle(ticks);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {e.printStackTrace();}

        if (bullet!= null)
            bullet.advance(ticks);
        if (!bullet.isAppear())
            this.remove(bullet);


        // advance stars
        updateStars(ticks);

        // advance ship
        ship.advance(ticks);

        // checking collision
        // ship's collision with asteroid
        t = checkCollisionWithAsteroids(ship);
        if (t!=null) {
            this.remove(t); // remove collided asteroid
            t.destroy(); //destroy asteroid
            // if collised
            ship.collised(ticks, t.getDamage());
        }
        // bullet's collision with asteroid
        t = checkCollisionWithAsteroids(bullet);
        if (t!=null && bullet.isAppear()) {
            score += t.getScore() ; // increase score
            this.remove(t); // remove collided asteroid
            t.destroy(); //destroy asteroid
            bullet.destroy();// remove bullet
            this.remove(bullet);
        }
        //if ship is destroyed
        if (ship.isDestroyed()) {
            this.remove(ship); //
            GameOver= true;
        }
        // scanf for asteroid collision
        for (int i = 0; i<MAX_OBS; i++) {
            // if object is not null
            if ((obs[i]!= null)){
            t= this.checkCollisionWithAsteroids(obs[i]);
            // if there is collision
            if (t!=null)
                if (t!=obs[i])// if it does not collided to itself
                // reversed direction
                if (obs[i].getSpeedX()*t.getSpeedX()>0) {
                    //
                    if ((obs[i].getRefPixelY()+obs[i].getHEIGHT())-(t.getRefPixelY() -t.getHEIGHT())<=0 ) {
                        if (obs[i].getSpeedY() >1 )
                            obs[i].setSpeedY(obs[i].getSpeedY()-1);
                            t.setSpeedY(t.getSpeedY()+1);
                    }

                    if (obs[i].getRefPixelX()>=t.getRefPixelX()) {
                        obs[i].setSpeedX(-obs[i].getSpeedX());
                        t.setSpeedX(t.getSpeedX()+1);
                    } else {
                        t.setSpeedX(-t.getSpeedX());
                        obs[i].setSpeedX(obs[i].getSpeedX()+1);
                    }
                }
                else {
                     obs[i].setSpeedX(-obs[i].getSpeedX());
                     t.setSpeedX(-t.getSpeedX());
                }
            }
        }
    }

    public int getRandom ( int min, int max) {
        return (Math.abs(r.nextInt()))%(max-min) + min;
    }

    public void createObstacle (int ticks) throws java.lang.ArrayIndexOutOfBoundsException{

        int i = 0;
        // check asteroid rate
        while ( (ticks -OBS_RATE) > obsAppTick && (i<MAX_OBS) ) {
            // this could be done by repositioning the asteroid instead of recreating the object
            try {
                if (obs[i]!=null) {
                    // if asteroid disappeared
                    if (!(obs[i].appear)) {
                        this.remove(obs[i]); // remove it
                        //obs[i].destroy(); // destroy it
                        obs[i].reAppear(getRandom(OBS_MIN_X_RATE, OBS_MAX_X_RATE), getRandom(OBS_MIN_Y_RATE,OBS_MAX_Y_RATE), true, getRandom(0,width));
                        //obs[i] = (new Obstacle(, ,true,getRandom(0,width)));// create new one
                        this.append(obs[i]); // add to the phone
                        obsAppTick = ticks; // set new asteroid timer
                    }
                }
                else {
                    // create asteroids
                    obs[i]= new Obstacle (asteroidImage, ASTEROID_WIDTH, ASTEROID_HEIGHT);
                    obs[i].reAppear(getRandom(OBS_MIN_X_RATE, OBS_MAX_X_RATE), getRandom(OBS_MIN_Y_RATE,OBS_MAX_Y_RATE), true, getRandom(0,width));
                    this.append(obs[i]);
                    obsAppTick = ticks;
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                i++;
            }
        }
        for ( i = 0; i < MAX_OBS ; i++) {
            if (obs[i] != null ){

                obs[i].advance(ticks, width, height);
            }
        }
    }

    private void createStars() {
        for (int i = 0; i< MAX_STARS; i++) {
            try {
                stars[i]= new Star(starImage, STAR_WIDTH, STAR_HEIGHT, getRandom(0, width), getRandom(0, height));
            } catch (java.io.IOException e) {e.printStackTrace();}
            this.append(stars[i]);
        }
    }
    private void updateStars(int ticks) {
        for (int i = 0; i< MAX_STARS; i++) {
            stars[i].advance(ticks, getRandom(0,width), height);
        }
    }
    private Obstacle checkCollisionWithAsteroids(Sprite t) {
        for (int i =0; i < MAX_OBS; i++) {
            if (obs[i]!=null)

            if (obs[i].collidesWith(t, true)) {
                return obs[i];
            }
        }
        return null;
    }
    protected void endGame(Graphics g) {
        GameOver=true;
        gameCanvas.stop();
        Font f = g.getFont();
        int h = f.getHeight()+40;
        int w = f.stringWidth("High score")+40;
        g.setColor(250,250,250);
        g.drawString("Score " + score,(width-w)/2,(height-h)/2,g.TOP | g.LEFT);

    }
    public int convert2Long (double a ) {
        try {
            String temp = Double.toString(a);
            return Integer.parseInt((temp.substring(0, temp.indexOf("."))));
        }
        catch (Exception e) { return 0;}
    }

}
