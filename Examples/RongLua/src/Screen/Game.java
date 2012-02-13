/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Screen;

import ClassFrame.*;
import GamePlay.CanvasGame;
import Object.Bullet;
import Object.Cloud;
import Object.Coordinate;
import Object.Rabbit;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.List;
import javax.microedition.media.MediaException;
import java.util.Vector;

/**
 *
 * @author QuyetNM1
 */
public class Game extends IScreen {

    private Random random;
    private static Rabbit aRabbit;
    private static Player aPlayer;
    private static int scrRealWidth;
    private Vector listCloud;
    private Vector listBullet;
    private Cloud cl;
    private Cloud newCl;
    private Cloud clCheck;
    private Cloud clCheckFire;
    private Bullet newBullet;
    public static int positionGround;
    private boolean check = true;
    private boolean checkFire = true;
    int role_win;    

    public Game(CanvasGame canvas) {
        super(canvas);
    }

    public void initGame() {
        role_win = 0;
        aPlayer = new Player(canvas, 3, 0);
        CanvasGame.isPlaying = true;
        scrRealWidth = 0;        
    }

    public void initLevel() {
        canvas.getRMS().open();
        canvas.getRMS().saveLevel(aPlayer, 6);
        canvas.getRMS().close();
        scrRealWidth = 0;
        aRabbit = new Rabbit(canvas);
        aRabbit.setDirection(Rabbit.STAND);
        Rabbit.setIsStand(true);
        Rabbit.setIsUp(true);
        positionGround = aRabbit.getY() + 60;
        listCloud = new Vector();
        listBullet = new Vector();
        random = new Random();
    }

    public void load_screen() {
        canvas.getResource().loadArray(Resource.gamePlayArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(Resource.gamePlayArrayImages);
    }

    public void keyEvent(int keycode, int event) {
        int tg;
        if (event == InputKey.KEY_EVENT_DOWN) {
            switch (keycode) {
                case InputKey.KEY_RIGHT:
                    if (aRabbit.getY() == positionGround - 60) {
                        Rabbit.setIsStand(false);
                        Rabbit.setIsUp(false);
                        aRabbit.setDirection(Rabbit.RIGHT);
                    }
                    break;
                case InputKey.KEY_LEFT:
                    if (aRabbit.getY() == positionGround - 60) {
                        Rabbit.setIsStand(false);
                        Rabbit.setIsUp(false);
                        aRabbit.setDirection(Rabbit.LEFT);
                    }
                    break;
                case InputKey.KEY_UP:
                    Rabbit.setIsUp(true);
                    Rabbit.setIsStand(false);
                    Rabbit.setIsFire(true);
                    if (aRabbit.getY() == positionGround-60) {
                        if(aRabbit.getDirection()==Rabbit.LEFT)
                        {
                            newBullet = new Bullet(canvas, aRabbit.getX() + 10, aRabbit.getY());
                        }
                        else if(aRabbit.getDirection()==Rabbit.RIGHT)
                        {
                            newBullet = new Bullet(canvas, aRabbit.getX() + 20, aRabbit.getY());
                        }
                        else
                        {
                            newBullet = new Bullet(canvas, aRabbit.getX() + 20, aRabbit.getY());
                        }
                        listBullet.addElement(newBullet);
                    }
                    break;
                case InputKey.KEY_RIGHT_SOFTKEY:
                    break;
                case InputKey.KEY_LEFT_SOFTKEY:
                    canvas.setScreen(new Pause(canvas));
                    Runtime.getRuntime().gc();
                    break;
            }
        } else if (event == InputKey.KEY_EVENT_UP) {
            if (!Rabbit.isIsUp()) {
                Rabbit.setIsStand(true);
            }
        }
    }

    public void pointerEvent(int x, int y, int event) {
    }

    public void update() {
        if (CanvasGame.time == 0) {
            try {
                cl = new Cloud(canvas, false, 0);
                cl.setSpeed(RandomGenerator.random(random, 3, 3));
                cl.setX(RandomGenerator.random(random, 10, Constant.SCR_W - 30));
//                cl.setX(RandomGenerator.random(random, 10, 60));
                //  cl.setX(150);
                cl.setNumFrame(RandomGenerator.random(random, 0, 8));
                listCloud.addElement(cl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }

    public void paint(Graphics g) {
        update();
        canvas.getImage().drawImage(g, canvas, Resource.IMG_MAIN, 0, 0);
        canvas.getImage().drawNumber(g, canvas, aPlayer.getScore());
        for (int i = 0; i < aPlayer.getBlood(); i++) {
            canvas.getImage().drawImage(g, canvas, Resource.IMG_NUMBERBLOOD, Constant.SCR_W - 50 + i * 15, 4);
        }
        if (listCloud.size() > 0) {
            for (int i = 0; i < listCloud.size(); i++) {
                clCheck = (Cloud) listCloud.elementAt(i);
                if (clCheck.getY() >= positionGround-20&&!clCheck.isBreakCloud()) {
                    positionGround -= 10;
                    aRabbit.setY(positionGround - 60);
                }
                break;
            }
        }
        if (positionGround <= 60 || aPlayer.getBlood() == 0) {
            int frame=aRabbit.getNumFrame();
            if(frame<3)
            {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_DIE, aRabbit.getX(), aRabbit.getY()+10, frame*68, 0, 68, 65);
                frame++;
                aRabbit.setNumFrame(frame);
                return;
            }
            else
            {
                canvas.setScreen(new GameOver(canvas));
            }
        }
        aRabbit.paint(g);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_GROUND, 0, positionGround);
        paintBullet(g);
        paintCloud(g);
        if (listCloud.size() > 0) {
            for (int i = 0; i < listCloud.size(); i++) {
                if (check) {
                    clCheck = (Cloud) listCloud.elementAt(i);                    
                    int posClX = clCheck.getX();
                    int posClY = clCheck.getY();
                    int posRabX = aRabbit.getX();
                    int posRabY = aRabbit.getY();
                    if ((checkCrash(posClX, posClY, posRabX, posRabY, clCheck.getWidth(), clCheck.getHeight(), aRabbit.getWidth(), aRabbit.getHeight()))) {
                        if(!clCheck.isBreakCloud())
                        {
                            int countBlood = aPlayer.getBlood();
                            if (countBlood >= 1) {                                
                                countBlood--;
                                aPlayer.setBlood(countBlood);
                                listCloud.removeElementAt(i);                                
                                Rabbit.setIsHit(true);
                            }
                            check = true;
                            break;
                        }
                        else
                        {
                            int score = aPlayer.getScore();
                            score+=10;
                            aPlayer.setScore(score);
                            System.out.println(aPlayer.getScore());
                            listCloud.removeElementAt(i);
                            Rabbit.setIsHit(false);                            
                        }
                    }
                    else
                    {
                        Rabbit.setIsHit(false);
                    }
                } else {
                    check = false;
                }
            }
        }
    }

    private boolean checkCrash(int xA, int yA, int xB, int yB, int wA, int hA, int wB, int hB) {
        if (((xA > xB - wA+5) && (xA < xB + wB-5)) && ((yA > yB - hA+10) && (yA < yB + hB))) {
            return true;
        }
        return false;
    }

    private boolean checkCollide(int xA, int yA, int xB, int yB, int wA, int hA, int wB, int hB) {        
        if (((xA > xB - wA+5) && (xA < xB + wB-5)) && (yA < yB + hB)) {
            return true;
        }
        return false;
    }


    public void paintBullet(Graphics g) {
        for (int i = 0; i < listBullet.size(); i++) {
            Bullet bullet = (Bullet) listBullet.elementAt(i);
            if (bullet.getY() <= 15) {
                listBullet.removeElementAt(i);
            } else {                
                if (Rabbit.isIsFire() && bullet.getY() > 0) {
                    bullet.paint(g);
                }
            }
            if (listCloud.size() > 0) {
                for (int j = 0; j < listCloud.size(); j++) {
                    if (checkFire) {
                        clCheckFire = (Cloud) listCloud.elementAt(j);
                        int posBulX = bullet.getX();
                        int posBulY = bullet.getY();
                        int posColX = clCheckFire.getX();
                        int posColY = clCheckFire.getY();
                        if (checkCollide(posBulX, posBulY, posColX, posColY, bullet.getWidth(), bullet.getHeight(), clCheckFire.getWidth(), clCheckFire.getHeight())) {
                            clCheckFire.setBreakCloud(true);
                            listBullet.removeElementAt(i);
                            checkFire = true;
                            break;
                        }
                    } else {
                        checkFire = false;
                    }
                }
            }
        }
    }

    public void paintCloud(Graphics g) {
        for (int i = 0; i < listCloud.size(); i++) {
            newCl = (Cloud) listCloud.elementAt(i);
            if (newCl.getY() >= positionGround-20&&!newCl.isBreakCloud()) {
                listCloud.removeElementAt(i);
            } else {                
                newCl.paint(g);
            }
        }
    }

    public void checkGameOver() {
        aPlayer.setScore(aPlayer.getScore() - 20);
        if (aPlayer.getScore() <= 0) {
            aPlayer.setScore(0);
        }
        canvas.getRMS().open();
        if (canvas.getRMS().is_top(aPlayer)) {
        } else {
            canvas.setScreen(new GameOver(canvas));
        }
        canvas.getRMS().close();
        canvas.getResource().unLoadSound(Resource.SOUND_PLAY);
        canvas.getResource().playSound(Resource.SOUND_GAME_OVER, - 1);
        Runtime.getRuntime().gc();
    }    

    public void releaseGame() {
    }

    public static Rabbit getaRabbit() {
        return aRabbit;
    }

    public static void setaMan(Rabbit aRab) {
        Game.aRabbit = aRab;
    }

    public static int getScrRealWidth() {
        return scrRealWidth;
    }

    public static void setScrRealWidth(int scrRealWidth) {
        Game.scrRealWidth = scrRealWidth;
    }

    public static Player getPlayer() {
        return Game.aPlayer;
    }

    public static void setPlayer(Player aPlayer) {
        Game.aPlayer = aPlayer;
    }
}
