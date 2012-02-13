/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Screen;

import ClassFrame.*;
import GamePlay.CanvasGame;
import Object.Brick;
import Object.Coordinate;
import Object.Man;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.MediaException;

/**
 *
 * @author QuyetNM1
 */
public class Game extends IScreen {
//    private Button buttPause = new Button("Tạm dừng", Resource.IMG_BUTTON_PAUSE, -1, 0, Constant.buttPause_x, Constant.buttPause_y, Constant.buttPause_w, Constant.buttPause_h, Button.ENABLE);

    private static Man aMan;
    private static Man aEnemy;
    private static Map map;
    private static Player aPlayer;
    private static DownClock clock;
    private static int scrRealWidth;
    int role_win;
//    private Coordinate[] ArrayPosition = new Coordinate[Coordinate.MAX_COORD];
    private static Vector ArrayPosition;
//    private int position_count = 0;
    private static int end_way = 0;
    private static int frame_sing = 0;
    private static int frame_fail = 0;

    public Game(CanvasGame canvas) {
        super(canvas);
    }

    public void initGame() {
        role_win = 0;
        aPlayer = new Player(canvas);
        map = new Map();
        CanvasGame.isPlaying = true;
        scrRealWidth = 0;
    }

    public void initLevel() {
        canvas.getRMS().open();
        canvas.getRMS().saveLevel(aPlayer, 6);
        canvas.getRMS().close();
        clock = new DownClock(canvas, Resource.IMG_SMALL_NUMBER, 72, 3, Clock.MAX_TIME);
        map.getListBrickUp().removeAllElements();
        map.getListBrickDown().removeAllElements();
        map.getListBricklt().removeAllElements();
        ArrayPosition = new Vector();
        scrRealWidth = 0;
        frame_sing = 0;
        frame_fail = 0;
//	position_count = 0;
        map.level = aPlayer.getLevel();
        switch (aPlayer.getLevel()) {
            case 1:
                aMan = new Man(canvas, 90, 170, Resource.IMG_NOBITA);
                aEnemy = new Man(canvas, 0, 170, Resource.IMG_CHAIEN);
                end_way = 3840;
                break;
            case 2:
                aMan = new Man(canvas, 90, 170, Resource.IMG_NOBITA);
                aEnemy = new Man(canvas, 0, 170, Resource.IMG_CHAIEN);
                end_way = 3690;
                break;
            case 3:
                aMan = new Man(canvas, 90, 140, Resource.IMG_NOBITA);
                aEnemy = new Man(canvas, 0, 140, Resource.IMG_CHAIEN);
                end_way = 2940;
                break;
            case 4:
                aMan = new Man(canvas, 90, 170, Resource.IMG_NOBITA);
                aEnemy = new Man(canvas, 0, 170, Resource.IMG_CHAIEN);
                end_way = 3690;
                break;
            case 5:
                aMan = new Man(canvas, 90, 170, Resource.IMG_NOBITA);
                aEnemy = new Man(canvas, 0, 170, Resource.IMG_CHAIEN);
                end_way = 3840;
                break;
        }
        map.initLevel();
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
                case InputKey.KEY_NUM6:
                case InputKey.KEY_F:
                case InputKey.KEY_f:

                    break;
                case InputKey.KEY_LEFT:
                case InputKey.KEY_NUM4:
                case InputKey.KEY_S:
                case InputKey.KEY_s:

                    break;
                case InputKey.KEY_UP:
                case InputKey.KEY_NUM2:
                case InputKey.KEY_E:
                case InputKey.KEY_e:

                    break;
                case InputKey.KEY_DOWN:
                case InputKey.KEY_NUM8:
                case InputKey.KEY_X:
                case InputKey.KEY_x:

                    break;
                case InputKey.KEY_OK:
                case InputKey.KEY_ENTER:
                case InputKey.KEY_NUM5:
                case InputKey.KEY_D:
                case InputKey.KEY_d:
                    if (aEnemy.getDirection() == Man.SING) {
                        return;
                    }
                    if (aMan.getJump() == Man.JUMP || aMan.getJump() == Man.LAND_NOT_CLICKED) {
                        return;
                    }
                    if (aMan.getDirection() == Man.UP) {
                        ArrayPosition.addElement(new Coordinate(aMan.getRealWidth(), Man.DOWN));
                        aMan.setDirection(Man.DOWN);
                        aMan.setJump(Man.JUMP);
                        canvas.getResource().playSound(Resource.SOUND_MAN_DOWN, 1);
                    } else {
                        ArrayPosition.addElement(new Coordinate(aMan.getRealWidth(), Man.UP));
                        aMan.setDirection(Man.UP);
                        aMan.setJump(Man.JUMP);
                        canvas.getResource().playSound(Resource.SOUND_MAN_UP, 1);
                    }
                    break;
                case InputKey.KEY_LEFT_SOFTKEY:
//                    initLevel();
//                    aPlayer.setScore(aPlayer.getScore() - 200);
//                    if (aPlayer.getScore() <= 0) {
//                        aPlayer.setScore(0);
//                    }
                    break;
                case InputKey.KEY_RIGHT_SOFTKEY:
                    canvas.setScreen(new Pause(canvas));
                    Runtime.getRuntime().gc();
                    break;
//                case InputKey.KEY_PERIOD:
//                case InputKey.KEY_M:
//                case InputKey.KEY_m:
//                    if(aPlayer.getLevel() == 15){
//                        aPlayer.setScore(aPlayer.getScore() + aPlayer.getLevel() * Constant.score_bonus + Constant.score_bonus_congra);
//                        canvas.getRMS().open();
//                        if( canvas.getRMS().is_top( aPlayer ) ) {
//                            canvas.setScreen( new WinTextBox(canvas));
//                        }
//                        else
//                            canvas.setScreen( new Win(canvas) );
//                        canvas.getRMS().close();
//
//                    }else{
//                        canvas.getRMS().open();
//                        canvas.getRMS().saveLevel(aPlayer, 6);
//                        canvas.getRMS().close();
//                        canvas.setScreen( new NextLevel(canvas));
//                        aPlayer.setScore(aPlayer.getScore() + aPlayer.getLevel() * Constant.score_bonus);
//                        aPlayer.setLevel(aPlayer.getLevel() + 1);
//                    }
//                    CanvasGame.isPlaying = false;
//                    break;
            }
        }
    }

    public void pointerEvent(int x, int y, int event) {
//        switch (event) {
//            case InputKey.POINTER_EVENT_DOWN:
//                if(x <= 31){
//                    if (canvas.checkButton(x, y, buttPause)) {
//                    buttPause.setState(Button.HOLD_CLICK);
//                    }
//                    break;
//                }
//                if (aEnemy.getDirection() == Man.SING) {
//                    return;
//                }
//                if (aMan.getJump() == Man.JUMP || aMan.getJump() == Man.LAND_NOT_CLICKED) {
//                    return;
//                }
//                if (aMan.getDirection() == Man.UP) {
//                    ArrayPosition.addElement(new Coordinate(aMan.getRealWidth(), Man.DOWN));
//                    aMan.setDirection(Man.DOWN);
//                    aMan.setJump(Man.JUMP);
//                } else {
//                    ArrayPosition.addElement(new Coordinate(aMan.getRealWidth(), Man.UP));
//                    aMan.setDirection(Man.UP);
//                    aMan.setJump(Man.JUMP);
//                }
//                break;
//            case InputKey.POINTER_EVENT_UP:
//                if (canvas.checkButton(x, y, buttPause) && buttPause.getState() == Button.HOLD_CLICK) {
//                    canvas.setScreen(new Pause(canvas));
//                    Runtime.getRuntime().gc();
//                }
//                buttPause.setState(Button.ENABLE);
//                break;
//        }
    }

    public void update() {
        clock.update();
        check_pan(aMan, 1, 4);
        check_pan(aEnemy, 0, 1);
    }

    public void paint(Graphics g) {
        update();
        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_MAIN + (aPlayer.getLevel() - 1) % 3, 0, 0, scrRealWidth % Constant.SCR_W, 0, Constant.SCR_W - scrRealWidth % Constant.SCR_W, Constant.SCR_H);
        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_MAIN + (aPlayer.getLevel() - 1) % 3, Constant.SCR_W - scrRealWidth % Constant.SCR_W, 0, 0, 0, scrRealWidth % Constant.SCR_W, Constant.SCR_H);

//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_MAIN, 0, 0, 0, scrRealWidth % Constant.SCR_W, Constant.SCR_H, Constant.SCR_W - scrRealWidth % Constant.SCR_W);
//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_MAIN, 0, Constant.SCR_W - scrRealWidth % Constant.SCR_W, 0, 0, Constant.SCR_H, scrRealWidth % Constant.SCR_W);

        drawBrick(g, map.getListBrickUp());
        drawBrick(g, map.getListBrickDown());
        drawBrick(g, map.getListBricklt());
        aMan.paint(g);
        aEnemy.paint(g);

        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_MAIN + (aPlayer.getLevel() - 1) % 3, 0, 219, 0, 219, Constant.SCR_W, 21);
//        buttPause.drawButton(g, canvas);
//        clock.paint(g);
        canvas.getImage().drawNumber(g, canvas, Resource.IMG_SMALL_NUMBER, aPlayer.getLevel(), 10, 57 - 4 * (String.valueOf(aPlayer.getLevel()).length()), 225);
        canvas.getImage().drawNumber(g, canvas, Resource.IMG_SMALL_NUMBER, aPlayer.getScore(), 10, 183 - 4 * (String.valueOf(aPlayer.getScore()).length()), 225);
    }

    private void drawBrick(Graphics g, Vector listBrick) {
        int brick_x, k = -1;
        for (int i = 0; i < listBrick.size(); i++) {
            Brick aBrick = (Brick) listBrick.elementAt(i);
            brick_x = aBrick.getRealWith() - scrRealWidth;
            if (brick_x <= Constant.SCR_W) {
                if (aBrick.getType() != Brick.NONE) {
                    if (aBrick.getType() == Brick.BRICK14 || aBrick.getType() == Brick.BRICK15) {
                        aBrick.setFrame(aBrick.getFrame() + 1);
                        if (aBrick.getFrame() >= 8) {
                            aBrick.setFrame(0);
                        }
                        canvas.getImage().drawImagePart(g, canvas, Resource.IMG_ITEM1, brick_x, aBrick.getY(), aBrick.getFrame() * aBrick.getWidth(), 0, aBrick.getWidth(), aBrick.getHeight());
                    } else {
                        canvas.getImage().drawImagePart(g, canvas, Resource.IMG_ITEM, brick_x, aBrick.getY(), (aBrick.getType() - 1) * aBrick.getWidth(), 0, aBrick.getWidth(), aBrick.getHeight());
                    }

                }
                if (brick_x < -Brick.brick_w) {
                    k = i;
                    listBrick.removeElementAt(i);
                    i--;
                }
            } else {
                return;
            }
        }
//        if(k > 0)   listBrick.removeElementAt(k);
    }

    public void checkGameOver() {
        aPlayer.setScore(aPlayer.getScore() - 20);
        if (aPlayer.getScore() <= 0) {
            aPlayer.setScore(0);
        }
        canvas.getRMS().open();
        if (canvas.getRMS().is_top(aPlayer)) {
            canvas.setScreen(new GameoverTextBox(canvas));
        } else {
            canvas.setScreen(new GameOver(canvas));
        }
        canvas.getRMS().close();
        canvas.getResource().unLoadSound(Resource.SOUND_PLAY);
        canvas.getResource().playSound(Resource.SOUND_GAME_OVER, - 1);
        Runtime.getRuntime().gc();

    }

    public void checkWin() {
        role_win++;
        if (role_win >= Constant.ti_DELAY_WIN) {
            if (aPlayer.getLevel() == 5) {
                aPlayer.setScore(aPlayer.getScore() + Constant.score_bonus * aPlayer.getLevel() + Constant.score_bonus_congra);
                canvas.getRMS().open();
                if (canvas.getRMS().is_top(aPlayer)) {
                    canvas.setScreen(new WinTextBox(canvas));
                } else {
                    canvas.setScreen(new Win(canvas));
                }
                canvas.getRMS().close();
                canvas.getResource().playSound(Resource.SOUND_GAME_OVER, - 1);
            } else {
                aPlayer.setScore(aPlayer.getScore() + Constant.score_bonus * aPlayer.getLevel());

                aPlayer.setLevel(aPlayer.getLevel() + 1);

                canvas.getRMS().open();
                canvas.getRMS().saveLevel(aPlayer, 6);
                canvas.getRMS().close();
                canvas.setScreen(new NextLevel(canvas));


            }
            role_win = 0;
            CanvasGame.isPlaying = false;
            Runtime.getRuntime().gc();
        }
    }

    private boolean check_win() {
//        if (map.getListLeaf().size() == 1) {
//            return true;
//        }
        return false;
    }

    void check_pan(Man aPeople, int state_screen, int step) {
        int k;
        Brick aPan0;
        Brick aPan;
        Brick aPan1;

        switch (aPeople.getDirection()) {
            case Man.DOWN:
                aPan0 = (Brick) map.getListBrickDown().elementAt(0);

                k = (aPeople.getRealWidth() - scrRealWidth) / Brick.brick_w + 1;
                if (k < 0) {
                    return;
                }
                aPan = (Brick) map.getListBrickDown().elementAt(k);
                aPan1 = (Brick) map.getListBrickDown().elementAt(k + 1);
                if (state_screen == 1 && aPan.getType() == Brick.BRICK14 && aPan0.getRealWith() - scrRealWidth == -Brick.brick_w) {
                    if (aMan.getVx() == 6) {
                        aMan.setVx(10);
                        aMan.setVy(10);
                        aEnemy.setVx(10);
                        aEnemy.setVy(10);
                        aEnemy.setRealWidth(aEnemy.getRealWidth() - aEnemy.getRealWidth() % 10);
                    } else if (aMan.getVx() == 10) {
                        aMan.setVx(15);
                        aMan.setVy(15);
                        aEnemy.setVx(15);
                        aEnemy.setVy(15);
                        aEnemy.setRealWidth(aEnemy.getRealWidth() - aEnemy.getRealWidth() % 15);
                    }
                }
                if (state_screen == 1 && aPan0.getRealWith() - scrRealWidth == -Brick.brick_w && aPan.getY() > aPan1.getY() && aPan.getY() < Constant.SCR_H && aPeople.getY() <= aPan.getY() - Brick.brick_h && aPeople.getY() > aPan1.getY() - Brick.brick_h) {
                    aPeople.setY(aPeople.getY() + aPeople.getVy());
                } else {
                    aPeople.setRealWidth(aPeople.getRealWidth() + aPeople.getVx());
                    aPeople.setY(aPeople.getY() + aPeople.getVy());
                    if (aPeople.getRealWidth() <= 7000 && state_screen == 1) {
                        scrRealWidth += aPeople.getVx();
                    }
                }
                if (aPan.getType() == Brick.NONE) {
                    aPeople.setJump(Man.JUMP);
                } else {
                    if (aPeople.getY() >= aPan.getY() - Brick.brick_h && aPeople.getY() <= aPan.getY()) {
                        aPeople.setY(aPan.getY() - Brick.brick_h);
                        aPeople.setJump(Man.LAND);
                    } else {
                        if (aPeople.getJump() == Man.LAND) {
                            aPeople.setJump(Man.LAND_NOT_CLICKED);
                        }
                    }
                }
                break;
            case Man.UP:
                aPan0 = (Brick) map.getListBrickUp().elementAt(0);

                k = (aPeople.getRealWidth() - scrRealWidth) / Brick.brick_w + 1;
                if (k < 0) {
                    return;
                }
                aPan = (Brick) map.getListBrickUp().elementAt(k);
                aPan1 = (Brick) map.getListBrickUp().elementAt(k + 1);
                if (state_screen == 1 && aPan.getType() == Brick.BRICK14 && aPan0.getRealWith() - scrRealWidth == -Brick.brick_w) {
                    if (aMan.getVx() == 6) {
                        aMan.setVx(10);
                        aMan.setVy(10);
                        aEnemy.setVx(10);
                        aEnemy.setVy(10);
                        aEnemy.setRealWidth(aEnemy.getRealWidth() - aEnemy.getRealWidth() % 10);
                    } else if (aMan.getVx() == 10) {
                        aMan.setVx(15);
                        aMan.setVy(15);
                        aEnemy.setVx(15);
                        aEnemy.setVy(15);
                        aEnemy.setRealWidth(aEnemy.getRealWidth() - aEnemy.getRealWidth() % 15);
                    }
                }

                if (state_screen == 1 && aPan0.getRealWith() - scrRealWidth == -Brick.brick_w && aPan.getY() < aPan1.getY() && aPan.getY() >= 0 && aPeople.getY() >= aPan.getY() + Brick.brick_h && aPeople.getY() < aPan1.getY() + Brick.brick_h) {
                    aPeople.setY(aPeople.getY() - aPeople.getVy());
                } else {
                    aPeople.setRealWidth(aPeople.getRealWidth() + aPeople.getVx());
                    aPeople.setY(aPeople.getY() - aPeople.getVy());
                    if (aPeople.getRealWidth() <= 7000 && state_screen == 1) {
                        scrRealWidth += aPeople.getVx();
                    }
                }

                if (aPan.getType() == Brick.NONE) {
                    aPeople.setJump(Man.JUMP);
                } else {
                    if (aPeople.getY() <= aPan.getY() + Brick.brick_h && aPeople.getY() >= aPan.getY()) {
                        aPeople.setY(aPan.getY() + Brick.brick_h);
                        aPeople.setJump(Man.LAND);
                    } else {
                        if (aPeople.getJump() == Man.LAND) {
                            aPeople.setJump(Man.LAND_NOT_CLICKED);
                        }
                    }
                }

                break;
        }
        if (state_screen == 0 && ArrayPosition.size() > 0) {
            Coordinate aCoordinate = (Coordinate) ArrayPosition.elementAt(0);
            if (aPeople.getRealWidth() >= aCoordinate.getRealWidth()) {
                aPeople.setRealWidth(aCoordinate.getRealWidth());
                aPeople.setDirection(aCoordinate.getState());
                aPeople.setJump(Man.JUMP);
//                        if(aCoordinate.getState() == Man.DOWN){
//                            canvas.getResource().getSound(Resource.SOUND_MAN_DOWN).playSound(1);
//                        }else if(aCoordinate.getState() == Man.UP){
//                            canvas.getResource().getSound(Resource.SOUND_MAN_UP).playSound(1);
//                        }
                ArrayPosition.removeElementAt(0);
            }
        }


        if (aPeople.getRealWidth() < 0) {
            aPeople.setRealWidth(0);
        }
        if (scrRealWidth < 0) {
            scrRealWidth = 0;
        }
        if (aPeople.getRealWidth() < scrRealWidth) {
            aPeople.setRealWidth(scrRealWidth);
        }

        if (scrRealWidth > end_way) {
            scrRealWidth = end_way;
        }
        if (aPeople.getRealWidth() > end_way + Constant.SCR_W + 20) {
            aPeople.setRealWidth(end_way + Constant.SCR_W + 20);
            if (aEnemy.getDirection() != Man.CHAIEN_FAIL) {
                canvas.getResource().unLoadSound(Resource.SOUND_PLAY);
                canvas.getResource().playSound(Resource.SOUND_NEXT_STAGE, 1);
            }
            aEnemy.setDirection(Man.CHAIEN_FAIL);
        }

        if (state_screen == 0) {
            if (aPeople.getY() == -100) {
                if (aMan.getY() <= - 50 || aMan.getY() >= 240) {
                    checkGameOver();
                }
            } else if (aPeople.getY() <= - 50 || aPeople.getY() >= 240) {
                if (aMan.getY() <= - 50 || aMan.getY() >= 240) {
                    checkGameOver();
                } else {
                    aPeople.setY(-100);
                }

            } else if (aPeople.getRealWidth() >= aMan.getRealWidth() - Brick.brick_w + 5) {
                aPeople.setDirection(Man.SING);
                aPeople.setY(aMan.getY());
                if (aMan.getDirection() != Man.NOBITA_FAIL) {
                    canvas.getResource().unLoadSound(Resource.SOUND_PLAY);
                    canvas.getResource().playSound(Resource.SOUND_FAIL, 1);
                }

                aMan.setDirection(Man.NOBITA_FAIL);
            }
        }
    }

    public void releaseGame() {
    }

    public static Man getaEnemy() {
        return aEnemy;
    }

    public static void setaEnemy(Man aEnemy) {
        Game.aEnemy = aEnemy;
    }

    public static Man getaMan() {
        return aMan;
    }

    public static void setaMan(Man aMan) {
        Game.aMan = aMan;
    }

    public static int getScrRealWidth() {
        return scrRealWidth;
    }

    public static void setScrRealWidth(int scrRealWidth) {
        Game.scrRealWidth = scrRealWidth;
    }

    public static int getEnd_way() {
        return end_way;
    }

    public static void setEnd_way(int end_way) {
        Game.end_way = end_way;
    }

    public static int getFrame_fail() {
        return frame_fail;
    }

    public static void setFrame_fail(int frame_fail) {
        Game.frame_fail = frame_fail;
    }

    public static int getFrame_sing() {
        return frame_sing;
    }

    public static void setFrame_sing(int frame_sing) {
        Game.frame_sing = frame_sing;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static Player getPlayer() {
        return Game.aPlayer;
    }

    public static void setPlayer(Player aPlayer) {
        Game.aPlayer = aPlayer;
    }

    public DownClock getClock() {
        return this.clock;
    }
}
