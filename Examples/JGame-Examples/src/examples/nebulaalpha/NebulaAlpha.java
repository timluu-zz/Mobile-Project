package examples.nebulaalpha;

import java.util.Hashtable;
import jgame.JGObject;
import jgame.JGTimer;
import jgame.impl.SortedArray;
import jgame.platform.JGEngine;
import vn.topmedia.charactor.Actor;

/**
 * A minimal game using nothing but the JGame base classes.
 */
public class NebulaAlpha extends JGEngine {

    private Hashtable enemies;
    private Hashtable players;
    private Player inPlayer;

    public void initCanvas() {
        setCanvasSettings(20, 15, 32, 32, null, null, null);
    }

    public void initGame() {
        defineMedia("nebula_alpha.tbl");
        setBGImage("bgimg");
        setFrameRate(40, 4);
        setGameState("Title");
        playAudio("music", "mainmusic", true);

        enemies = new Hashtable();
        players = new Hashtable();

        inPlayer = new Player(pfWidth() / 2 - 25, pfHeight() - 113);
        players.put(inPlayer.getName(), inPlayer);
    }
    int timer = 0, score = 0;
    double gamespeed = 1.0;
    int lostHP = 0;

    public void doFrameTitle() {
        if (getKey(' ')) {
            startLevel();
        }
    }

    private void startLevel() {
        setGameState("InGame");
        score = 0;
        gamespeed = 1.0;
    }

    public void doFrameInGame() {
        moveObjects();
        checkCollision(2, 1 + 8); // robots hit player
        checkCollision(4, 2); // bullets hit robots
        timer++;
        if (gamespeed < 2) {
            gamespeed += 0.0001;
        }
        if (timer % (int) (20 / gamespeed) == 0) {
            Robot rb = new Robot(random(0, pfWidth() - 64), -90, (int) random(-1, 1, 2));
            enemies.put(rb.getName(), rb);
        }
    }

    public void paintFrame() {
        String freeMemory = Runtime.getRuntime().freeMemory() / 1024 + "/" + Runtime.getRuntime().totalMemory() / 1024 + " K";
        drawImageString("SCORE " + score + " " + freeMemory, 0, 0, -1, "font_map", 32, 0);
        if (inPlayer != null) {
            drawImageString("HP " + inPlayer.getHP(), 0, 25, -1, "font_map", 32, 0);
        }
        if (lostHP > 0) {
            drawImageString(lostHP + "", inPlayer.getLastX() - 15, inPlayer.getLastY() - 15, -1, "font_map", 32, 0);
            lostHP = 0;
        }
        drawImageString("ONLINE " + players.size(), 0, 50, -1, "font_map", 32, 0);
        drawImageString("ENEMY " + enemies.size(), 0, 75, -1, "font_map", 32, 0);
    }

    public void paintFrameGameOver() {
        drawImageString("GAME OVER", 165, 200, -1, "font_map", 32, 0);
    }

    public void paintFrameTitle() {
        drawImageString("NEBULA ALPHA", 120, 200, -1, "font_map", 32, 0);
    }

    public class Robot extends Actor {

        public Robot(double x, double y, int dir) {
            super("robot", x, y, 2);
        }

        public void move() {
            if (y < pfHeight() - 200) {
                y += 2.0 * gamespeed;
            } else {
                y += (2.0 - (y - (pfHeight() - 200)) / 200.0 * 3.5) * gamespeed;
            }
            if (xdir < 0) {
                setAnim("robot_l");
            } else {
                setAnim("robot_r");
            }
            if (x < 0) {
                xdir = 1;

            }
            if (x > pfWidth() - 64) {
                xdir = -1;
            }
        }

        public void hit(JGObject obj) {
            new JGObject("explo", true, x, y, 0, "explo", 0, 0, 32);
            playAudio("explo");
            remove();
            enemies.remove(getName());
            obj.remove();
            score += 5;
        }
    }

    public class Player extends Actor {

        SortedArray bullets;
        long lastBullet = 0;

        public Player(double x, double y) {
            // Initialize game object by calling an appropriate constructor in the JGObject class.
            super("player",// name by which the object is known
                    false, //true means add a unique ID number after the object name.
                    //If we don't do this, this object will replace any object with the same name.
                    x,// X position
                    y,// Y position (0=top of the screen) 
                    1, // the object's collision ID (used to determine which classes
                    // of objects should collide with each other)
                    "player");// name of sprite or animation to use (null is none) JGObject.expire_off_view);
            setHP(100);
            bullets = new SortedArray(2);
        }

        public void move() {
            if (getKey(KeyLeft) && x > 14) {
                x -= 14 * gamespeed;
            }
            if (getKey(KeyUp) && y > 14) {
                y -= 14 * gamespeed;
            }
            if (getKey(KeyRight) && x < pfWidth() - 51 - 14) {
                x += 14 * gamespeed;
            }
            if (getKey(KeyDown) && y < pfHeight() - 51 - 14) {
                y += 14 * gamespeed;
            }
            if (getKey(' ')) {
                if (bullets.size < 2) {
                    long timeNow = System.currentTimeMillis();
                    if (timeNow - lastBullet > 500) {
                        JGObject bullet = new JGObject("bullet", true, x - 8, y - 8, 4, "bullet", 0, -14, -2);
                        bullets.put(bullet.getName(), bullet);
                        playAudio("shoot");
                        clearKey(' ');
                        lastBullet = timeNow;
                    }
                }
            }

            for (int i = 0; i < bullets.size; i++) {
                JGObject obj = (JGObject) bullets.values[i];
                if (!obj.isAlive()) {
                    bullets.remove(obj.getName());
                }
            }
        }

        public void hit(JGObject obj) {
            lostHP = 7;
//            drawImageString(lostHP + "", x - 5, y - 5, -1, "font_map", 32, 0);

            int avaiableHP = getHP() - lostHP;
            setHP(avaiableHP > 0 ? avaiableHP : 0);

            new JGObject("explo", true, x, y, 0, "explo", 0, 0, 32);
            if (getHP() <= 0) {
                remove();
                addGameState("GameOver");
                new JGTimer(100, true) {

                    public void alarm() {
                        startLevel();
                    }
                };
            } else if (getHP() < 80 && getHP() > 70) {
                AutoPlayer player = new AutoPlayer(0, pfHeight() - 113);
                players.put(player.getName(), player);
            } else if (getHP() < 40 && getHP() > 30) {
                AutoPlayer player = new AutoPlayer(pfWidth() / 2, pfHeight() - 113);
                players.put(player.getName(), player);
            } else {
                enemies.remove(obj.getName());
                obj.remove();
            }
        }
    }

    public class AutoPlayer extends Actor {

        double xPosition = 0;
        SortedArray bullets;
        long lastBullet = 0;

        public AutoPlayer(double x, double y) {
            // Initialize game object by calling an appropriate constructor in the JGObject class.
            super("autoplayer",// name by which the object is known
                    true, //true means add a unique ID number after the object name.
                    //If we don't do this, this object will replace any object with the same name.
                    x,// X position
                    y,// Y position (0=top of the screen) 
                    8, // the object's collision ID (used to determine which classes
                    // of objects should collide with each other)
                    "player");// name of sprite or animation to use (null is none) JGObject.expire_off_view);
            setHP(100);
            xPosition = x;
            bullets = new SortedArray(2);
        }

        public void move() {
            x += 2.0 * gamespeed * xdir;
            if (x < 0 + xPosition) {
                xdir = 1;
            }
            if (x > pfWidth() / 2 + xPosition - 44) {
                xdir = -1;
            }
            if ((int) x % 20 == 0) {
                if (bullets.size < 3) {
                    long timeNow = System.currentTimeMillis();
                    if (timeNow - lastBullet > 300) {
                        JGObject bullet = new JGObject("bullet", true, x - 8, y - 8, 4, "bullet", 0, -14, -2);
                        bullets.put(bullet.getName(), bullet);
                        playAudio("shoot");
                        lastBullet = timeNow;
                    }
                }
            }

            for (int i = 0; i < bullets.size; i++) {
                JGObject obj = (JGObject) bullets.values[i];
                if (!obj.isAlive()) {
                    bullets.remove(obj.getName());
                }
            }
        }

        public void hit(JGObject obj) {
            lostHP = 10;
//            drawImageString(lostHP + "", x - 5, y - 5, -1, "font_map", 32, 0);

            int avaiableHP = getHP() - lostHP;
            setHP(avaiableHP > 0 ? avaiableHP : 0);

            new JGObject("explo", true, x, y, 0, "explo", 0, 0, 32);
            if (getHP() <= 0) {
                remove();
            } else {
                enemies.remove(obj.getName());
                obj.remove();
            }
        }
    }
}
