/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Object;

import ClassFrame.Constant;
import ClassFrame.Image;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import Screen.Game;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class Man extends AbstractObject{

//    public Frog(CanvasGame canvas, int screen_x, int screen_y){
//        super(canvas, screen_x, screen_y);
//    }
    public static final int LAND                =    6;
    public static final int LAND_NOT_CLICKED    =    7;
    public static final int SING                =    8;
    public static final int NOBITA_FAIL         =    9;
    public static final int CHAIEN_FAIL         =    10;

    public static final int MAN_W               =    30;
    public static final int MAN_H               =    38;
    public static final int CHAIEN_SING_W       =    40;
    public static final int CHAIEN_SING_H       =    43;
    public static final int CHAIEN_FAIL_W       =    30;
    public static final int CHAIEN_FAIL_H       =    40;
    public static final int NOBITA_FAIL_W       =    18;
    public static final int NOBITA_FAIL_H       =    22;
    public static final int v_x                 =    6;
    public static final int v_y                 =    7;

    private int realWidth;
    private int jump;
    public Man(CanvasGame canvas, int screen_x, int screen_y, int image,
                       int w, int h, int numFrame, int state, int direction, int vx, int vy, int t_jump, int realWidth, int jump){
        super(canvas, screen_x, screen_y, image, w, h, numFrame, state, direction, vx, vy, t_jump);
        this.realWidth = realWidth;
        this.jump = jump;
    }

    public Man(CanvasGame canvas, int realWidth, int y, int image){
        super(canvas, 0, 0, image, MAN_W, MAN_H, 0, 0, DOWN, v_x, v_y, 0);
        this.realWidth = realWidth;
        this.jump = LAND;
        this.y = y;

    }
    public int getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
    }

    public int getJump() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

   

    public void update() {
        switch (direction)
	{
	case DOWN:
		realWidth += vx;
		y += vy;
		if(realWidth <= 2910)	Game.setScrRealWidth(Game.getScrRealWidth() + vx);
		break;
	case UP:
		realWidth += vx;
		y -= vy;
		if(realWidth <= 2910)	Game.setScrRealWidth(Game.getScrRealWidth() + vx);
		break;
	}

        check();
    }
    
    public void check(){
        if(realWidth < Brick.brick_w)               realWidth = Brick.brick_w;
	if(Game.getScrRealWidth() < 0 )             Game.setScrRealWidth(0);
	if(realWidth > 3160)                        realWidth = 3160;
    }


    public void paint(Graphics g) {
        x = realWidth - Game.getScrRealWidth();
        if (direction == SING) {
            Game.setFrame_sing(Game.getFrame_sing() + 1);
            if (Game.getFrame_sing()/20 >= 5) {
               ((Game) canvas.getScreen()).checkGameOver();
            }
            canvas.getImage().drawImagePart(g, canvas, Resource.IMG_CHAIEN_SING, x, y - CHAIEN_SING_W + MAN_W, ((Game.getFrame_sing() / 2)%4) * CHAIEN_SING_W, 0, CHAIEN_SING_W, CHAIEN_SING_H);
        } else if (direction == NOBITA_FAIL) {
            canvas.getImage().drawImage(g, canvas, Resource.IMG_NOBITA_FAIL, x, y - NOBITA_FAIL_H + MAN_W);
        } else if (direction == CHAIEN_FAIL) {
            Game.setFrame_fail(Game.getFrame_fail() + 1);
            if (Game.getFrame_fail() < 8) {
                Game.getaEnemy().setRealWidth(Game.getaEnemy().getRealWidth() + Game.getaEnemy().getVx()/2);
                if (Game.getaEnemy().getY() != -100) {
                    Game.getaEnemy().setY(Game.getaEnemy().getY() + Game.getaEnemy().getVy());
                }
                if (Game.getaEnemy().getY() > Constant.SCR_H - 2*MAN_W - 10) {
                    Game.getaEnemy().setY(Constant.SCR_H - 2*MAN_W - 10);
                }
                if (Game.getaEnemy().getRealWidth() >= Game.getEnd_way() + Constant.SCR_W - Brick.brick_w) {
                    Game.getaEnemy().setRealWidth(Game.getEnd_way() + Constant.SCR_W - Brick.brick_w);
                }
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_CHAIEN_FAIL, x, y - CHAIEN_FAIL_H + MAN_W, (Game.getFrame_fail() / 3) * CHAIEN_FAIL_W, 0, CHAIEN_FAIL_W, CHAIEN_FAIL_H);
            }else{
            
//            if (Game.getFrame_fail() / 3 >= 3) {
//                Game.setFrame_fail(8);
                if (Game.getaEnemy().getY() != -100) {
                    Game.getaEnemy().setY(Constant.SCR_H - 2*MAN_W - 10);
                }
                canvas.getImage().drawImagePart(g, canvas, Resource.IMG_CHAIEN_FAIL, x, y - CHAIEN_FAIL_H + MAN_W, 2 * CHAIEN_FAIL_W, 0, CHAIEN_FAIL_W, CHAIEN_FAIL_H);
                if (Game.getFrame_fail()/20  >= 5 /*&& state == PLAYING*/) {
                    ((Game) canvas.getScreen()).checkWin();
                }

            }
            
        } else {
            countFrame++;
            if (countFrame / 2 >= 4) {
                countFrame = 0;
            }
            if (jump == LAND || jump == LAND_NOT_CLICKED) {
                canvas.getImage().drawImagePart(g, canvas, image, x, y - MAN_H + MAN_W + direction*(MAN_H - MAN_W), (countFrame / 2) * w, direction * h, w, h);
            } else {
                canvas.getImage().drawImagePart(g, canvas, image, x, y - MAN_H + MAN_W + direction*(MAN_H - MAN_W), 4 * w, direction * h, w, h);
            }
        }

    }

    
    public void setScreenX(int screen_x) {
        this.screen_x = screen_x;
        this.x = Constant.SCR_W  - (Constant.SCR_X - this.screen_x);
    }

    public void setScreenY(int screen_y) {
        this.screen_y = screen_y;
        this.y = Constant.SCR_H - (Constant.SCR_Y - this.screen_y);
    }

    public void setX(int x) {
        this.x = x;
        this.screen_x = this.x + Constant.SCR_X - Constant.SCR_W;
    }

    public void setY(int y) {
        this.y = y;
        this.screen_y = this.y + Constant.SCR_Y - Constant.SCR_H;
    }



}
