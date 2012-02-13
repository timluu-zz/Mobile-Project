/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Screen;

import ClassFrame.Button;
import ClassFrame.Constant;
import ClassFrame.InputKey;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author trung duy
 */
public class GameoverTextBox extends IScreen{

    private int scrRealWidth = 0;
//    private Button buttReplay;
//    private Button buttExit;
    public GameoverTextBox(CanvasGame _canvas) {
        super(_canvas);
//        buttReplay = new Button("Chơi lại", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 6, Constant.buttFrameLeft_x, Constant.buttFrameLeft_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
//        buttExit = new Button("Menu", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 7, Constant.buttFrameRight_x, Constant.buttFrameRight_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
    }

    public void load_screen() {
        canvas.getResource().loadArray(Resource.TextBoxGameOverArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(Resource.TextBoxGameOverArrayImages);
    }

    public void keyEvent(int keycode, int event) {
        if(event == InputKey.KEY_EVENT_DOWN) {
            switch(keycode) {
                case InputKey.KEY_LEFT_SOFTKEY:
                    Game.getPlayer().setName( Game.getPlayer().getNameTg() );
                    Game.getPlayer().setNameLength( 0 );
                    canvas.getRMS().open();
                    canvas.getRMS().replaceWith( Game.getPlayer() );
                    canvas.getRMS().close();
                    canvas.setScreen( new Game(canvas));
                    ((Game) canvas.getScreen()).initLevel();
//                    ((Game) canvas.getScreen()).getPlayer().setScore(((Game) canvas.getScreen()).getPlayer().getScore() - 200);
//                    if(((Game) canvas.getScreen()).getPlayer().getScore() < 0)  ((Game) canvas.getScreen()).getPlayer().setScore(0);
                    CanvasGame.isPlaying = true;
                    Game.getPlayer().resetNameTemp();
                    canvas.getResource().unLoadSound(Resource.SOUND_GAME_OVER);
                    canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
                    break;
                case InputKey.KEY_RIGHT_SOFTKEY:
                    Game.getPlayer().setName( Game.getPlayer().getNameTg() );
                    Game.getPlayer().setNameLength( 0 );
                    canvas.getRMS().open();
                    canvas.getRMS().replaceWith( Game.getPlayer() );
                    canvas.getRMS().close();
                    
                    canvas.setScreen( new Menu(canvas));
                    canvas.setContinuePlay(CanvasGame.SCR_GAMEOVER);
                    Game.getPlayer().resetNameTemp();
                    canvas.getResource().unLoadSound(Resource.SOUND_GAME_OVER);
                    canvas.getResource().playSound(Resource.SOUND_MENU, - 1);
                    break;
                default:
                    getKey(keycode, event);
                break;
            }
        }
    }
    
    public void getKey(int keycode, int event) {
        if(event == InputKey.KEY_EVENT_DOWN) {
            if(keycode == InputKey.KEY_CLEAR) {
                if( 0 < Game.getPlayer().getNameLength() ) {
                    Game.getPlayer().removeChar();
                }
            }
            else {
                Game.getPlayer().addChar( (char)keycode );
            }
        }
    }
    public void pointerEvent(int x, int y, int event) {
//        switch (event) {
//            case InputKey.POINTER_EVENT_DOWN:
//                if (canvas.checkButton(x, y, buttReplay)) {
//                    buttReplay.setState(Button.HOLD_CLICK);
//                } else if (canvas.checkButton(x, y, buttExit)) {
//                    buttExit.setState(Button.HOLD_CLICK);
//                }
//                break;
//            case InputKey.POINTER_EVENT_UP:
//                if (canvas.checkButton(x, y, buttReplay) && buttReplay.getState() == Button.HOLD_CLICK) {
//                    Game.getPlayer().setName( Game.getPlayer().getNameTg() );
//                    Game.getPlayer().setNameLength( 0 );
//                    canvas.getRMS().open();
//                    canvas.getRMS().replaceWith( Game.getPlayer() );
//                    canvas.getRMS().close();
//                    canvas.setScreen( new Game(canvas));
//                    ((Game) canvas.getScreen()).initLevel();
//                    Game.getPlayer().resetNameTemp();
//                } else if (canvas.checkButton(x, y, buttExit) && buttExit.getState() == Button.HOLD_CLICK) {
//                    Game.getPlayer().setName( Game.getPlayer().getNameTg() );
//                    Game.getPlayer().setNameLength( 0 );
//                    canvas.getRMS().open();
//                    canvas.getRMS().replaceWith( Game.getPlayer() );
//                    canvas.getRMS().close();
//
//                    canvas.setScreen( new Menu(canvas));
//                    canvas.setContinuePlay(CanvasGame.SCR_GAMEOVER);
//                    Game.getPlayer().resetNameTemp();
//                }
//                buttReplay.setState(Button.ENABLE);
//                buttExit.setState(Button.ENABLE);
//                break;
//        }
    }
    public void paint(Graphics g) {
        scrRealWidth += 3;
        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, 0, 0, scrRealWidth % Constant.SCR_W, 0, Constant.SCR_W - scrRealWidth % Constant.SCR_W, Constant.SCR_H);
        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, Constant.SCR_W - scrRealWidth % Constant.SCR_W, 0, 0, 0, scrRealWidth % Constant.SCR_W, Constant.SCR_H);

//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, 0, 0, 0, scrRealWidth % Constant.SCR_W, Constant.SCR_H, Constant.SCR_W - scrRealWidth % Constant.SCR_W);
//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, 0, Constant.SCR_W - scrRealWidth % Constant.SCR_W, 0, 0, Constant.SCR_H, scrRealWidth % Constant.SCR_W);

//        canvas.getImage().drawImage(g, canvas, Resource.IMG_FRAME, Constant.frame_x, Constant.frame_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_GAME_OVER_TEXT_BOX, Constant.txt_frame_x, Constant.txt_frame_y);
//        buttReplay.drawButton(g, canvas);
//        buttExit.drawButton(g, canvas);
         
         g.setFont(CanvasGame.bmFont);
         g.setColor(CanvasGame.RED);
         g.drawString( String.valueOf(Game.getPlayer().getScore()), Constant.txt_frame_x + Constant.tbox_score_x, Constant.txt_frame_y + Constant.tbox_score_y - 1, Graphics.TOP|Graphics.LEFT);
         g.drawString(new String( Game.getPlayer().getNameTg() ), Constant.txt_frame_x + Constant.tbox_name_x, Constant.txt_frame_y + Constant.tbox_name_y, Graphics.TOP|Graphics.LEFT);
    }

    public void update() {
    }

}
