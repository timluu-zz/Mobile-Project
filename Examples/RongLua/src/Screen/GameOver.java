/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Screen;


import ClassFrame.Button;
import ClassFrame.Constant;
import ClassFrame.Image;
import ClassFrame.InputKey;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class GameOver extends IScreen {

    private int scrRealWidth = 0;
//    private Button buttReplay;
//    private Button buttExit;
    public GameOver(CanvasGame _canvas) {
        super(_canvas);
//        buttReplay = new Button("Chơi lại", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 6, Constant.buttFrameLeft_x, Constant.buttFrameLeft_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
//        buttExit = new Button("Menu", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 7, Constant.buttFrameRight_x, Constant.buttFrameRight_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
    }
    public void load_screen() {
        canvas.getResource().loadArray(canvas.getResource().gameoverArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(canvas.getResource().gameoverArrayImages);
    }
    public void keyEvent(int keycode, int event){
       if(event == InputKey.KEY_EVENT_DOWN){
            if(keycode == InputKey.KEY_RIGHT_SOFTKEY){
                canvas.setScreen( new Menu(canvas));
                canvas.setContinuePlay(CanvasGame.SCR_GAMEOVER);
                canvas.getResource().unLoadSound(Resource.SOUND_GAME_OVER);
                canvas.getResource().playSound(Resource.SOUND_MENU, - 1);
            }else if(keycode == InputKey.KEY_LEFT_SOFTKEY){
                canvas.setScreen( new Game(canvas));
                ((Game) canvas.getScreen()).initLevel();
                ((Game) canvas.getScreen()).initGame();
                canvas.getResource().unLoadSound(Resource.SOUND_GAME_OVER);
                canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
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
//                    canvas.setScreen( new Game(canvas));
//                    ((Game) canvas.getScreen()).initLevel();
//                } else if (canvas.checkButton(x, y, buttExit) && buttExit.getState() == Button.HOLD_CLICK) {
//                    canvas.setScreen( new Menu(canvas));
//                    canvas.setContinuePlay(CanvasGame.SCR_GAMEOVER);
//                }
//                buttReplay.setState(Button.ENABLE);
//                buttExit.setState(Button.ENABLE);
//                break;
//        }
    }
    
    public void update(){

    }
    public void paint(Graphics g){
//        scrRealWidth += 3;
//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, 0, 0, scrRealWidth % Constant.SCR_W, 0, Constant.SCR_W - scrRealWidth % Constant.SCR_W, Constant.SCR_H);
//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, Constant.SCR_W - scrRealWidth % Constant.SCR_W, 0, 0, 0, scrRealWidth % Constant.SCR_W, Constant.SCR_H);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_MAIN, 0, 0);        
        

//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, 0, 0, 0, scrRealWidth % Constant.SCR_W, Constant.SCR_H, Constant.SCR_W - scrRealWidth % Constant.SCR_W);
//        canvas.getImage().drawImagePartNormal(g, canvas, Resource.IMG_BG_FRAME, 0, Constant.SCR_W - scrRealWidth % Constant.SCR_W, 0, 0, Constant.SCR_H, scrRealWidth % Constant.SCR_W);

//        canvas.getImage().drawImage(g, canvas, Resource.IMG_FRAME, Constant.frame_x, Constant.frame_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_GAME_OVER, Constant.txt_frame_x, Constant.txt_frame_y);
//        buttReplay.drawButton(g, canvas);
//        buttExit.drawButton(g, canvas);

//        g.setFont( CanvasGame.bmFont );
//        g.setColor( CanvasGame.RED );
//        g.drawString( String.valueOf(Game.getPlayer().getScore()), Constant.txt_frame_x + Constant.tbox_score_x, Constant.txt_frame_y + Constant.tbox_score_y + 13, Graphics.TOP|Graphics.LEFT);
    }
}
