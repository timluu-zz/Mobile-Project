/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import ClassFrame.Constant;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import Screen.Game;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author ChungNV4
 */
public class Rabbit extends AbstractObject {

    private static int frame_moveLeft = 0;
    private static int frame_moveRight = 0;
    private static int frame_bloodLeft = 0;
    private static int frame_bloodRight = 0;
    private static int frame_die = 0;
    private static boolean isStand;
    private static boolean isUp;
    private static boolean isFire;
    private static boolean isHit;
    private static boolean isDie=false;
    private static int t = 0;
    private static int vo = 8;
    private Bullet bullet;
    private final int gravity = 1;
    private final int d = 1;

    public Rabbit(CanvasGame canvas) {
        super(canvas, 0, 0, 0, 0, 0, 0, 0, DOWN, 0, 0, 0);
        y = Constant.firstPositionH;
        x = Constant.firstPositionW;
        w = 50;
        h = 60;

//        bullet=new Bullet(canvas, x, y);
    }

    public void update() {
        if (isUp && !isStand) {
            y -= vo;
            vo -= gravity + t / d;
            t++;
            if (t > 5) {
                isStand = true;
            }
        } else {
            t = 0;
            vo = 8;
            y = Game.positionGround - 60;
        }
    }

    public void paint(Graphics g) {
        update();
        if(isDie)
        {
            return;
        }
        else if (isStand) {
            if (direction == LEFT && !isHit) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50, 0, 50, 60);
            } else if (direction == LEFT && isHit) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 5, 0, 50, 60);
            } else if (direction == RIGHT && isHit) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 5, 60, 50, 60);
            } else if (direction == RIGHT && !isHit) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50, 60, 50, 60);
            } else if (isHit) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 5, 60, 50, 60);
            } else {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50, 60, 50, 60);
            }
        } else if (isUp) {
            if (direction == LEFT) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 4, 0, 50, 60);
            } else if (direction == RIGHT) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 4, 60, 50, 60);
            } else {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 4, 60, 50, 60);
            }

        } else if (isHit) {
            if (direction == LEFT) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 5, 0, 50, 60);
            } else if (direction == RIGHT) {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 5, 60, 50, 60);
            } else {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, 50 * 5, 60, 50, 60);
            }
        } else if (direction == RIGHT) {
            canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, getFrame_moveRight() * 50, 60, 50, 60);
            frame_moveLeft = 0;
            if (frame_moveRight == 3) {
                frame_moveRight = 0;
            } else {
                frame_moveRight++;
            }
            if (this.x <= Constant.SCR_W - 55) {
                this.setX(this.getX() + Constant.sizeStep);
            }
        } else if (direction == LEFT) {
            canvas.getImage().drawImagePart(g, canvas, Resource.IMG_RABBIT, x, y, getFrame_moveLeft() * 50, 0, 50, 60);
            frame_moveRight = 0;
            if (frame_moveLeft == 3) {
                frame_moveLeft = 0;
            } else {
                frame_moveLeft++;
            }
            if (this.getX() > 0) {
                this.setX(this.getX() - Constant.sizeStep);
            }
        }
    }

    public boolean collided(final AbstractObject abo) {
        if (this.x > abo.x + abo.w) {
            return false;
        }
        if (this.x + this.w < abo.x) {
            return false;
        }
        if (this.y > abo.y + abo.h) {
            return false;
        }
        if (this.y + this.h < abo.y) {
            return false;
        }

        return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScreenX(int screen_x) {
    }

    public void setScreenY(int screen_y) {
    }

    public static int getFrame_moveLeft() {
        return frame_moveLeft;
    }

    public static void setFrame_moveLeft(int aFrame_move) {
        frame_moveLeft = aFrame_move;
    }

    public static int getFrame_die() {
        return frame_die;
    }

    public static void setFrame_die(int aFrame_die) {
        frame_die = aFrame_die;
    }

    public static int getFrame_bloodRight() {
        return frame_bloodRight;
    }

    public static void setFrame_bloodRight(int aFrame_bloodRight) {
        frame_bloodRight = aFrame_bloodRight;
    }

    public static int getFrame_bloodLeft() {
        return frame_bloodLeft;
    }

    public static void setFrame_bloodLeft(int aFrame_bloodLeft) {
        frame_bloodLeft = aFrame_bloodLeft;
    }

    public static int getFrame_moveRight() {
        return frame_moveRight;
    }

    public static void setFrame_moveRight(int aFrame_moveRight) {
        frame_moveRight = aFrame_moveRight;
    }

    public static boolean isIsStand() {
        return isStand;
    }

    public static void setIsStand(boolean aIsStand) {
        isStand = aIsStand;
    }

    public static boolean isIsUp() {
        return isUp;
    }

    public static void setIsUp(boolean aIsUp) {
        isUp = aIsUp;
    }

    public static boolean isIsFire() {
        return isFire;
    }

    public static void setIsFire(boolean aIsFire) {
        isFire = aIsFire;
    }

    public static boolean isIsHit() {
        return isHit;
    }

    public static void setIsHit(boolean aIsHit) {
        isHit = aIsHit;
    }
}
