/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Screen;


import ClassFrame.Button;
import ClassFrame.Clock;
import ClassFrame.Constant;
import ClassFrame.Image;
import ClassFrame.InputKey;
import ClassFrame.Lang;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.MediaException;

/**
 *
 * @author QuyetNM1
 */
public class Menu extends IScreen {

    public static final int CHOI_GAME =  0;
    public static final int CHOI_TIEP =  1;
    public static final int KI_LUC =  2;
    public static final int THIET_LAP =  3;
    public static final int HUONG_DAN =  4;
    public static final int GIOI_THIEU =  5;
    public static final int THOAT =  6;
    //max menu
    public static final int MAX_MENU = 7;
    private static int current_select = 0;
//    private Button[] ArrayButton = new Button[MAX_MENU] ;
    private String[] stringMenu = {
        "Chơi Game", "Chơi tiếp", "Kỉ lục", "Thiếp lập", "Hướng dẫn", "Giới thiệu", "Thoát"
    };

    public Menu(CanvasGame _canvas) {
        super(_canvas);
//        for(int i = 0; i < MAX_MENU; i++){
//            ArrayButton[i] = new Button(stringMenu[i], Resource.IMG_BUTTON_MENU, Resource.IMG_TXT_BUTTON_MENU, i, Constant.menu2_x, Constant.menu2_y + i*Constant.menu_h, Constant.menu_w, Constant.menu_h, Button.ENABLE);
//        }
    }

    public void load_screen() {
        canvas.getResource().loadArray(Resource.menuArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(Resource.menuArrayImages);
    }
    
    public void resetButtonState(){
//        for(int i = 0; i < MAX_MENU; i++){
//            if(i == CHOI_TIEP){
//               if(ArrayButton[i].getState() != Button.DISABLE)   ArrayButton[i].setState(Button.ENABLE);
//            }else{
//                ArrayButton[i].setState(Button.ENABLE);
//            }
//        }
    }

    public void keyEvent(int keycode, int event){
        if(event == InputKey.KEY_EVENT_DOWN){
            switch(keycode){
                case InputKey.KEY_UP:
                    current_select = (current_select + MAX_MENU - 1) % MAX_MENU;
                    break;
                case InputKey.KEY_DOWN:
                    current_select = (current_select + 1) % MAX_MENU;
                    break;
                case InputKey.KEY_OK:
                    ActonClick(current_select);
                    break;
            }
        }
    }


    public void pointerEvent(int x, int y, int event) {
//        switch(event){
//             case InputKey.POINTER_EVENT_DOWN:
//                 for(int i = 0; i < MAX_MENU; i++){
//                    if(i == CHOI_TIEP){
//                        if(canvas.checkButton(x, y, ArrayButton[i]) && ArrayButton[i].getState() != Button.DISABLE){
//                            ArrayButton[i].setState(Button.HOLD_CLICK);
//                            break;
//                        }
//                    }else{
//                        if(canvas.checkButton(x, y, ArrayButton[i])){
//                            ArrayButton[i].setState(Button.HOLD_CLICK);
//                            break;
//                        }
//                    }
//                 }
//                 break;
//             case InputKey.POINTER_EVENT_UP:
//                 int i;
//                 for(i = 0; i < MAX_MENU; i++){
//                     if(canvas.checkButton(x, y, ArrayButton[i]) && ArrayButton[i].getState() == Button.HOLD_CLICK){
//                         break;
//                     }
//                 }
//                 if(i < MAX_MENU){
//                     ActonClick(i);
//                 }
//                 resetButtonState();
//                 break;
//             }
    }


    public void ActonClick(int i){
        switch(i){
         case CHOI_GAME:
            canvas.setScreen( new Game(canvas));
            ((Game) canvas.getScreen()).initGame();
            ((Game) canvas.getScreen()).initLevel();
            canvas.setContinuePlay(CanvasGame.SCR_GAME);
            canvas.getResource().unLoadSound(Resource.SOUND_MENU);
            canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
            break;
        case CHOI_TIEP:            
            switch(canvas.getContinuePlay()){
                case -5:
                    canvas.setScreen( new Game(canvas));
                    ((Game) canvas.getScreen()).initGame();
                    ((Game) canvas.getScreen()).getPlayer().setLevel(CanvasGame.level_temp);
                    ((Game) canvas.getScreen()).getPlayer().setScore(CanvasGame.score_temp);
                    ((Game) canvas.getScreen()).initLevel();
                    canvas.setContinuePlay(CanvasGame.SCR_GAME);
                    canvas.getResource().unLoadSound(Resource.SOUND_MENU);
                    canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
                    break;
                case CanvasGame.SCR_GAME:
                    canvas.setScreen( new Game(canvas));
                    canvas.getResource().unLoadSound(Resource.SOUND_MENU);
                    canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
                    break;
                case CanvasGame.SCR_NEXT:
                    canvas.setScreen( new Game(canvas));
                    ((Game) canvas.getScreen()).initLevel();
                    canvas.getResource().unLoadSound(Resource.SOUND_MENU);
                    canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
                    break;
                case CanvasGame.SCR_GAMEOVER:
                    canvas.setScreen( new Game(canvas));
                    ((Game) canvas.getScreen()).initLevel();
                    ((Game) canvas.getScreen()).initGame();
                    canvas.getResource().unLoadSound(Resource.SOUND_MENU);
                    canvas.getResource().playSound(Resource.SOUND_PLAY, - 1);
                    break;
            }
            break;
        case KI_LUC:
            canvas.setScreen( new Score(canvas));
            break;
        case THIET_LAP:
            canvas.setScreen( new Setting(canvas) );
            ((Setting) canvas.getScreen()).sound_tg = canvas.musicLevel;
            break;
        case HUONG_DAN:
            canvas.setScreen( new Guide(canvas) );
            break;
        case GIOI_THIEU:
            canvas.setScreen( new About(canvas) );
            break;
        case THOAT:
            canvas.setScreen( new Exit(canvas) );
            break;
     }
    }
    
    public void update() {
        
    }

    public void paint(Graphics g) {
//        canvas.getImage().drawImage(g, canvas, Resource.IMG_BG_MENU, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_BG_MENU, Constant.menu_x, Constant.menu_y);


        canvas.getImage().drawImage(g, canvas, Resource.IMG_TXT_BUTTON_MENU, Constant.menu1_x, Constant.menu1_y);
//        canvas.getImage().drawImagePart(g, canvas, Resource.IMG_TXT_BUTTON_MENU1, Constant.menu1_x, Constant.menu1_y, Constant.menu1_x, Constant.menu_d*current_select, 21, 91);
        canvas.getImage().drawImagePart(g, canvas, Resource.IMG_TXT_BUTTON_MENU1, Constant.menu1_x, Constant.menu1_y + Constant.menu_d*current_select, 0, Constant.menu_d*current_select, 91, 21);
//        canvas.getImage().drawImage(g, canvas, Resource.IMG_BUTTON_MENU, Constant.menu2_x, Constant.menu2_y + Constant.menu_d * current_select);
//        for(int i = 0; i < MAX_MENU; i++){
//            ArrayButton[i].drawButton(g, canvas);
//        }        
    }
}
