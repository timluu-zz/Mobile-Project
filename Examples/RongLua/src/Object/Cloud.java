/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import ClassFrame.Constant;
import ClassFrame.RandomGenerator;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import Screen.Game;
import java.util.Random;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author ChungNV4
 */
public class Cloud extends AbstractObject {

    private int speed;
    private boolean breakCloud;
    private boolean toGround;    

    public Cloud(CanvasGame canvas, boolean breakCloud, int speed) {
        super(canvas, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        this.breakCloud = breakCloud;
        this.speed = speed;
        this.w = 23;
        this.h = 21;
        toGround = false;
    }

    public void update() {

        if (y >= Game.positionGround-20) {
            toGround = true;
        }
        if (toGround) {
            if(y>Game.positionGround-30)
            {
                y-=10;
            }
            else
            {
                y+=10;
            }
        } else {
            if (y < Game.positionGround) {
                y = y + this.getSpeed();
            }
        }
    }

    public void paint(Graphics g) {
        try {
            update();
            if (this.isBreakCloud()) {
                this.setSpeed(25);
                int frame = this.getNumFrame();
                int nextFrame = 0;
                if (CanvasGame.time % 2 == 0) {
                    if (nextFrame >= 0 && nextFrame <= 8) {
                        nextFrame++;
                    } else {
                        nextFrame = 0;
                    }
                }
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_CLOUD_BREAK, this.x, this.y, nextFrame * 16, frame * 16, 16, 16);
            } else {
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_CLOUD, this.x, this.y, numFrame * 23, 0, 23, 21);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isBreakCloud() {
        return breakCloud;
    }

    public void setBreakCloud(boolean breakCloud) {
        this.breakCloud = breakCloud;
    }

    public boolean isToGround() {
        return toGround;
    }

    public void setToGround(boolean toGround) {
        this.toGround = toGround;
    }
}
