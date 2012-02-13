/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GamePlay;


import ClassFrame.Button;
import ClassFrame.Constant;
import ClassFrame.Image;
import ClassFrame.InputKey;
import ClassFrame.Player;
import ClassFrame.RMS;
import ClassFrame.Resource;
import ClassFrame.TransCoordinate;
import Screen.Game;
import Screen.IScreen;
import Screen.Splash;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.MediaException;

/**
 *
 * @author QuyetNM1
 */
public class CanvasGame extends Canvas implements Runnable{

    MIDletGame midletGame;

    public static final Font    font                = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);
    public static final Font    bmFont              = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    public static final Font    bsFont              = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    public static final int     WHITE               = 0xFFFFFF;
    public static final int     BLACK               = 0x000000;
    public static final int     RED                 = 0xFF0000;

    private static final int MAX_CPS = 20;
    private static final int MS_PER_FRAME = 1000 / MAX_CPS;
    public static int currScreen;
    public static boolean isPlaying = false;
    public static boolean running;
    public static boolean active;
    //sound properties
    public static int volume = Constant.DEFAULT_VOLUME;
    public static int musicLevel = Constant.MIN_VOLUME;
    public static int effectLevel = Constant.MIN_VOLUME;
    public static boolean vibration = false;

    //game Screen
    public static final int NONE                = 0;
    public static final int SCR_SPLASH          = 1;
    public static final int SCR_MENU            = 2;
    public static final int SCR_SCORE           = 3;
    public static final int SCR_SETTING         = 4;
    public static final int SCR_GUIDE           = 5;
    public static final int SCR_ABOUT           = 6;
    public static final int SCR_EXIT            = 7;
    public static final int SCR_GAME            = 8;
    public static final int SCR_PAUSE           = 9;
    public static final int SCR_NEXT            = 10;
    public static final int SCR_WIN             = 11;
    public static final int SCR_GAMEOVER        = 12;
    public static int score_temp;
    public static int level_temp;

    private int typeScreen;

    private  Resource res;
    private  IScreen screen;
    private  InputKey inp;
    private RMS       rms;
    private Image     image;
    private  int continuePlay = NONE;
    public static int time;    

    ///////////////////////
    public CanvasGame(MIDletGame midletGame) {
        Runtime.getRuntime().gc();
        this.setFullScreenMode(true);
        this.midletGame = midletGame;
        initCanvas();
        image = new Image();        
        rms = new RMS();
        rms.open();

        Player p;
        p = rms.readLevel(6);
        if(p.getLevel() > 0){
            continuePlay = -5;
            level_temp = p.getLevel();
            score_temp = p.getScore();
        }
        rms.close();

        start();
    }

    private void initCanvas() {
        currScreen = SCR_SPLASH;
        initComponents();
        active = true;
        running = true;
        typeScreen = Image.NORMAL_SCREEN;
    }

    private void initComponents() {        
        res = new Resource(this);
        res.loadSounds();
        inp = new InputKey(this);
        if(screen == null) screen = new Splash(this);
        screen.load_screen();
    }

    public void keyEvent(int keycode, int event){
        screen.keyEvent(keycode, event);
    }



    protected void paint(Graphics g) {
        screen.paint(g);
    }

    private void update() {
        if( Game.class == screen.getClass() )
            screen.update();
    }

    public void run() {
          while (running) {
            long cycleStartTime = System.currentTimeMillis();
            long timeSinceStart = 0;
            if(time==25){
                time=0;
            }else time++;            
            if (active) {
//                update();
                repaint();                
                timeSinceStart = System.currentTimeMillis() - cycleStartTime;
            }
            try {
            if(timeSinceStart < MS_PER_FRAME) {
                Thread.sleep(MS_PER_FRAME - timeSinceStart);
            }
            } catch (InterruptedException ex) {
                //sendError("");
            }
        }
        midletGame.destroyApp(true);
        midletGame = null;
        Runtime.getRuntime().gc();
    }

    public void freeComponents() {
        res.unLoadSounds();
        res = null;
        screen = null;
    }

    public final void start() {
        Thread runner = new Thread(this);
        runner.setPriority(Thread.MAX_PRIORITY);
        runner.start();
    }

    // Catch key-down input and save in to Input Array
    protected void keyPressed(int keyCode) {
        isPlaying = false;
        keyEvent(keyCode, InputKey.KEY_EVENT_DOWN);
    }

    // Catch key-up input and clear from Input Array
    protected void keyReleased(int keyCode) {
        keyEvent(keyCode, InputKey.KEY_EVENT_UP);
    }

    protected void pointerPressed(int x, int y) {
        pointerEvent(x, y, InputKey.POINTER_EVENT_DOWN);
    }

    protected void pointerReleased(int x, int y) {
        pointerEvent(x, y, InputKey.POINTER_EVENT_UP);
    }

    protected void pointerDragged(int x, int y) {
        pointerEvent(x, y, InputKey.POINTER_EVENT_DRAG);
    }

    public void pointerEvent(int x, int y, int event){
        screen.pointerEvent(x, y, event);
    }

    public boolean checkRegion(int point_x, int point_y, int region_X, int region_Y, int region_W, int region_H){
        int region_x = 0;
        int region_y = 0;
        int region_w = 0;
        int region_h = 0;
        if(typeScreen == Image.NORMAL_SCREEN){
            region_x = region_X;
            region_y = region_Y;
            region_w = region_W;
            region_h = region_H;
        }else if(typeScreen == Image.ROTATE_SCREEN){
            region_x = TransCoordinate.getX(region_X, region_Y) - region_H;
            region_y = TransCoordinate.getY(region_X, region_Y);
            region_w = region_H;
            region_h = region_W;
        }
        if(point_x < region_x || point_x > region_x + region_w) return false;
        else if(point_y < region_y || point_y > region_y + region_h) return false;
        else return true;
    }
    
    public boolean checkButton(int point_x, int point_y, Button aButton){
        int region_x = 0;
        int region_y = 0;
        int region_w = 0;
        int region_h = 0;
        if(typeScreen == Image.NORMAL_SCREEN){
            region_x = aButton.getX();
            region_y = aButton.getY();
            region_w = aButton.getW();
            region_h = aButton.getH();
        }else if(typeScreen == Image.ROTATE_SCREEN){
            region_x = TransCoordinate.getX(aButton.getX(), aButton.getY()) - aButton.getH();
            region_y = TransCoordinate.getY(aButton.getX(), aButton.getY());
            region_w = aButton.getH();
            region_h = aButton.getW();
        }
        if(point_x < region_x || point_x > region_x + region_w) return false;
        else if(point_y < region_y || point_y > region_y + region_h) return false;
        else return true;
    }
    




    public void setScreen(final IScreen screen) {
        try {
            this.screen.un_load_screen();
            this.screen = screen;
            this.screen.load_screen();
        }
        catch(NullPointerException npe) {
            System.out.println("screen is null");
            System.out.println( npe.getMessage() );
        }
    }

    public Resource getResource() {
        return this.res;
    }

    public Image getImage() {
        return this.image;
    }

    public RMS getRMS() {
        return this.rms;
    }

    public MIDletGame getMIDletGame() {
        return this.midletGame;
    }

    public IScreen getScreen() {
        return this.screen;
    }

    public int getContinuePlay(){
        return this.continuePlay;
    }

    public void setContinuePlay(int continuePlay){
        this.continuePlay = continuePlay;
    }
    public int getTypeScreen(){
        return typeScreen;
    }
    
}
