/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Screen;

import ClassFrame.Button;
import ClassFrame.InputKey;
import ClassFrame.Constant;
import ClassFrame.Image;
import ClassFrame.Lang;
import ClassFrame.Resource;
import ClassFrame.SeekBar;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

/**
 *
 * @author QuyetNM1
 */
public class Setting extends IScreen{
    public int sound_tg;
//    private Button buttOk;
//    private Button buttCancel;
    private SeekBar aSeekBar;
    public Setting(CanvasGame _canvas) {
        super(_canvas);
//        buttOk = new Button("Đồng ý", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 3, Constant.buttAgree_x, Constant.buttAgree_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
//        buttCancel = new Button("Hủy bỏ", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 4, Constant.buttBack_x, Constant.buttBack_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
        
        aSeekBar = new SeekBar(canvas, Resource.IMG_SEEK_BAR, Resource.IMG_VOLUME, 81, 175, SeekBar.MIN_X + (CanvasGame.musicLevel * (SeekBar.MAX_X - SeekBar.MIN_X)) / 100, 175, 27, 28, SeekBar.ENABLE);
        if(this.canvas.getResource().getSound(Resource.SOUND_MENU).getPlayer().getState() != Player.STARTED) {
            canvas.getResource().playSound(Resource.SOUND_MENU, - 1);
        }
    }
    public void load_screen() {
        canvas.getResource().loadArray(canvas.getResource().settingArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(canvas.getResource().settingArrayImages);
    }

    int state_long_press = 0;
    public void keyEvent(int keycode, int event){
        if(event == InputKey.KEY_EVENT_DOWN){
            switch(keycode){
                case InputKey.KEY_RIGHT:
                    CanvasGame.musicLevel += 10;
                    if(CanvasGame.musicLevel > 100)    CanvasGame.musicLevel = 100;
                    for(int i = Resource.SOUND_MENU; i < Resource.MAX_SOUND; i++){
                        canvas.getResource().getSound(i).setVolumeLevel(CanvasGame.musicLevel);
                    }
                    break;
                case InputKey.KEY_LEFT:
                    CanvasGame.musicLevel -= 10;
                    if(CanvasGame.musicLevel < 0)    CanvasGame.musicLevel = 0;
                    for(int i = Resource.SOUND_MENU; i < Resource.MAX_SOUND; i++){
                        canvas.getResource().getSound(i).setVolumeLevel(CanvasGame.musicLevel);
                    }

                    break;
                case InputKey.KEY_NUM4:
                case InputKey.KEY_S:
                case InputKey.KEY_s:
                    state_long_press = 2;
                    break;
                case InputKey.KEY_NUM6:
                case InputKey.KEY_F:
                case InputKey.KEY_f:
                    state_long_press = 1;
                    break;
                case InputKey.KEY_LEFT_SOFTKEY:
                    canvas.setScreen( new Menu(canvas));
                    break;
                case InputKey.KEY_RIGHT_SOFTKEY:
                    canvas.musicLevel = sound_tg;
//                    for(int i = Resource.SOUND_MENU; i < Resource.MAX_SOUND; i++){
//                        canvas.getResource().getSound(i).setVolumeLevel(CanvasGame.musicLevel);
//                    }
                    canvas.setScreen( new Menu(canvas));
                    break;
            }
        }else if(event == InputKey.KEY_EVENT_UP){
            switch(keycode){
                case InputKey.KEY_NUM4:
                case InputKey.KEY_S:
                case InputKey.KEY_s:
                    state_long_press = 0;
                    break;
                case InputKey.KEY_NUM6:
                case InputKey.KEY_F:
                case InputKey.KEY_f:
                    state_long_press = 0;
                    break;
            }
        }
         
    }

    public void pointerEvent(int x, int y, int event) {
//        aSeekBar.pointerEvent(x, y, event);
//        switch (event) {
//            case InputKey.POINTER_EVENT_DOWN:
//                if (canvas.checkButton(x, y, buttOk)) {
//                    buttOk.setState(Button.HOLD_CLICK);
//                } else if (canvas.checkButton(x, y, buttCancel)) {
//                    buttCancel.setState(Button.HOLD_CLICK);
//                }
//                break;
//            case InputKey.POINTER_EVENT_UP:
//                if (canvas.checkButton(x, y, buttOk) && buttOk.getState() == Button.HOLD_CLICK) {
//                    canvas.setScreen( new Menu(canvas));
//                } else if (canvas.checkButton(x, y, buttCancel) && buttCancel.getState() == Button.HOLD_CLICK) {
//                    canvas.musicLevel = sound_tg;
//                    canvas.getResource().getSound(Resource.SOUND_MUSIC).setVolumeLevel(CanvasGame.musicLevel);
//                    canvas.setScreen( new Menu(canvas));
//                }
//                buttOk.setState(Button.ENABLE);
//                buttCancel.setState(Button.ENABLE);
//                break;
//        }
    }
    
    public void update() {
        if(state_long_press == 1){
            CanvasGame.musicLevel++;
            if(CanvasGame.musicLevel > 100)    CanvasGame.musicLevel = 100;
            canvas.getResource().getSound(Resource.SOUND_MENU).setVolumeLevel(CanvasGame.musicLevel);
        }else if(state_long_press == 2){
            CanvasGame.musicLevel--;
            if(CanvasGame.musicLevel < 0)    CanvasGame.musicLevel = 0;
            canvas.getResource().getSound(Resource.SOUND_MENU).setVolumeLevel(CanvasGame.musicLevel);
        }
    }

    public void paint(Graphics g) {
        update();
        
//        canvas.getImage().drawImage(g, canvas, Resource.IMG_BG_OTHER, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_BG_OTHER, Constant.menu_x, Constant.menu_y);
        
        canvas.getImage().drawImage(g, canvas, Resource.IMG_TXT_SETTING, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_TXT_SETTING).getWidth())/2, Constant.txt_setting_y);
//        image.drawImage(g, canvas, Resource.IMG_GUIDE, (canvas.w - canvas.res.getIMG(Resource.IMG_GUIDE).getWidth())/2, Constant.guide_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_CHANGE, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_CHANGE).getWidth())/2, (Constant.SCR_H - canvas.getResource().getIMG(Resource.IMG_CHANGE).getHeight()) - 22);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_SEEK_BAR, Constant.seek_bar_x, Constant.seek_bar_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_VOLUME, Constant.sound_x + (CanvasGame.musicLevel*130)/100, Constant.sound_y);
//        buttOk.drawButton(g, canvas);
//        buttCancel.drawButton(g, canvas);
//        aSeekBar.drawSeekBar(g);
    }
}
