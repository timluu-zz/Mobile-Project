/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Screen;

import ClassFrame.Button;
import ClassFrame.Constant;
import ClassFrame.Image;
import ClassFrame.InputKey;
import ClassFrame.Lang;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class Exit extends IScreen {

    private long startTime;
    private long currTime;
//    private Button buttYes;
//    private Button buttNo;

    public Exit(CanvasGame _canvas) {
        super(_canvas);
        currTime = 0;
//       buttYes = new Button("Có", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 0, Constant.txt_yes_x, Constant.txt_yes_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
//       buttNo = new Button("Không", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 1, Constant.txt_no_x, Constant.txt_no_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
    }

    public void load_screen() {
        canvas.getResource().loadArray(canvas.getResource().exitArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(canvas.getResource().exitArrayImages);
    }

    public void keyEvent(int keycode, int event) {
        if (currTime == 0) {
            if (event == InputKey.KEY_EVENT_DOWN) {
                if (keycode == InputKey.KEY_RIGHT_SOFTKEY) {
                    canvas.setScreen(new Menu(canvas));
                } else if (keycode == InputKey.KEY_LEFT_SOFTKEY) {
                    currTime = 1;
                    canvas.getResource().unLoadSound(Resource.SOUND_MENU);
                    startTime = System.currentTimeMillis();
                }
            }
        }

    }

    public void pointerEvent(int x, int y, int event) {
//        switch (event) {
//            case InputKey.POINTER_EVENT_DOWN:
//                if (canvas.checkButton(x, y, buttYes)) {
//                    buttYes.setState(Button.HOLD_CLICK);
//                } else if (canvas.checkButton(x, y, buttNo)) {
//                    buttNo.setState(Button.HOLD_CLICK);
//                }
//                break;
//            case InputKey.POINTER_EVENT_UP:
//                if (canvas.checkButton(x, y, buttYes) && buttYes.getState() == Button.HOLD_CLICK) {
//                    currTime = 1;
//                    startTime = System.currentTimeMillis();
//                    canvas.getResource().getSound(Resource.SOUND_MUSIC).release();
//                } else if (canvas.checkButton(x, y, buttNo) && buttNo.getState() == Button.HOLD_CLICK) {
//                    canvas.setScreen(new Menu(canvas));
//                }
//                buttYes.setState(Button.ENABLE);
//                buttNo.setState(Button.ENABLE);
//                break;
//        }
    }

    public void update() {
    }

    public void paint(Graphics g) {
        //        canvas.getImage().drawImage(g, canvas, Resource.IMG_SPLASH, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_SPLASH, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_EXIT, Constant.txt_exit_x, Constant.txt_exit_y);
        if (currTime > 0) {
            currTime = System.currentTimeMillis();
//            canvas.getImage().drawImage(g, canvas, Resource.IMG_SPL, Constant.menu_x, Constant.menu_y);
            canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_SPL, Constant.menu_x, Constant.menu_y);
            if (currTime - startTime >= Constant.ti_DELAY_EXIT) {
                canvas.getScreen().un_load_screen();
                ;
//                canvas.screen = null;
                canvas.running = false;
            }
        }
    }
}
