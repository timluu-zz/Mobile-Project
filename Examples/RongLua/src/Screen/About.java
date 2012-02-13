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
 * @author QuyetNM1
 */
public class About extends IScreen {

//    private Button buttBack = new Button("Quay lại", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 2, Constant.buttBack_x, Constant.buttBack_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);

    public About(CanvasGame _canvas) {
        super(_canvas);
    }

    public void load_screen() {
        canvas.getResource().loadArray(canvas.getResource().aboutArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(canvas.getResource().aboutArrayImages);
    }

    public void keyEvent(int keycode, int event) {
        if (event == InputKey.KEY_EVENT_DOWN) {
            if (keycode == InputKey.KEY_RIGHT_SOFTKEY) {
                canvas.setScreen(new Menu(canvas));
            }
        }
    }

    public void pointerEvent(int x, int y, int event) {
//        switch (event) {
//            case InputKey.POINTER_EVENT_DOWN:
//                if (canvas.checkButton(x, y, buttBack)) {
//                    buttBack.setState(Button.HOLD_CLICK);
//                }
//                break;
//            case InputKey.POINTER_EVENT_UP:
//                if (canvas.checkButton(x, y, buttBack) && buttBack.getState() == Button.HOLD_CLICK) {
//                    canvas.setScreen(new Menu(canvas));
//                }
//                buttBack.setState(Button.ENABLE);
//                break;
//        }
    }

    public void update() {
    }

    public void paint(Graphics g) {
//        canvas.getImage().drawImage(g, canvas, Resource.IMG_BG_OTHER, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_BG_OTHER, Constant.menu_x, Constant.menu_y);

//        canvas.getImage().drawImage(g, canvas, Resource.IMG_TXT_ABOUT, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_TXT_ABOUT).getWidth()) / 2, Constant.txt_about_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_ABOUT, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_ABOUT).getWidth()) / 2, Constant.about_y);

//         canvas.getImage().drawImage(g, canvas, Resource.IMG_BACK, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_BACK).getWidth()) - 28, (Constant.SCR_H - canvas.getResource().getIMG(Resource.IMG_BACK).getHeight()) - 27);
//        buttBack.drawButton(g, canvas);
    }
}
