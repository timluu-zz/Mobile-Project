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
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.MediaException;

/**
 *
 * @author QuyetNM1
 */
public class Splash extends IScreen {

    private long startTime;
    private long currTime;
//    private Button buttYes;
//    private Button buttNo;

    public Splash(CanvasGame _canvas) {
        super(_canvas);
        startTime = System.currentTimeMillis();
        currTime = startTime;
//        buttYes = new Button("Có", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 0, Constant.txt_yes_x, Constant.txt_yes_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
//        buttNo = new Button("Không", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 1, Constant.txt_no_x, Constant.txt_no_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
    }

    public void load_screen() {
        canvas.getResource().loadArray(canvas.getResource().splashArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(canvas.getResource().splashArrayImages);
    }

    public void keyEvent(int keycode, int event) {
        if (currTime - startTime >= Constant.ti_DELAY_SPLASH) {
            if (event == InputKey.KEY_EVENT_DOWN) {
                if (keycode == InputKey.KEY_RIGHT_SOFTKEY) {
                    canvas.setScreen(new Menu(canvas));
                    CanvasGame.musicLevel = 0;
                    for(int i = Resource.SOUND_MENU; i < Resource.MAX_SOUND; i++){
                        canvas.getResource().getSound(i).setVolumeLevel(CanvasGame.musicLevel);
                    }
                } else if (keycode == InputKey.KEY_LEFT_SOFTKEY) {
                    canvas.setScreen(new Menu(canvas));
                    canvas.getResource().playSound(Resource.SOUND_MENU, - 1);

                    CanvasGame.musicLevel = 80;
                    for(int i = Resource.SOUND_MENU; i < Resource.MAX_SOUND; i++){
                        canvas.getResource().getSound(i).setVolumeLevel(CanvasGame.musicLevel);
                    }
                    
                }
            }
        }

    }

    public void pointerEvent(int x, int y, int event) {
//        if (currTime - startTime >= Constant.ti_DELAY_SPLASH) {
//            switch (event) {
//                case InputKey.POINTER_EVENT_DOWN:
//                    if (canvas.checkButton(x, y, buttYes)) {
//                        buttYes.setState(Button.HOLD_CLICK);
//                    } else if (canvas.checkButton(x, y, buttNo)) {
//                        buttNo.setState(Button.HOLD_CLICK);
//                    }
//                    break;
//                case InputKey.POINTER_EVENT_UP:
//                    if (canvas.checkButton(x, y, buttYes) && buttYes.getState() == Button.HOLD_CLICK) {
//                        canvas.setScreen(new Menu(canvas));
//                        canvas.getResource().getSound(Resource.SOUND_MUSIC).repeat(-1);
//                        try {
//                            canvas.getResource().getSound(Resource.SOUND_MUSIC).start();
//                        } catch (MediaException ex) {
//                            System.out.println("Oh shit!!! Can't start music");
//                        }
//                        CanvasGame.musicLevel = 80;
//                        canvas.getResource().getSound(Resource.SOUND_MUSIC).setVolumeLevel(CanvasGame.musicLevel);
//                    } else if (canvas.checkButton(x, y, buttNo) && buttNo.getState() == Button.HOLD_CLICK) {
//                        canvas.setScreen(new Menu(canvas));
//                        CanvasGame.musicLevel = 0;
//                        canvas.getResource().getSound(Resource.SOUND_MUSIC).setVolumeLevel(CanvasGame.musicLevel);
//                    }
//                    buttYes.setState(Button.ENABLE);
//                    buttNo.setState(Button.ENABLE);
//                    break;
//            }
//        }
    }

    public void update() {
    }

    public void paint(Graphics g) {
//        canvas.getImage().drawImage(g, canvas, Resource.IMG_SPLASH, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_SPLASH, Constant.menu_x, Constant.menu_y);
        currTime = System.currentTimeMillis();

        if (currTime - startTime >= Constant.ti_DELAY_SPLASH) {
            canvas.getImage().drawImage(g, canvas, Resource.IMG_TXT_SOUND, Constant.txt_sound_x, Constant.txt_sound_y);
//            buttYes.drawButton(g, canvas);
//            buttNo.drawButton(g, canvas);

        }
    }
}
