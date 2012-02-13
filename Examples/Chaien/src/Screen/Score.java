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
import ClassFrame.Player;
import ClassFrame.Resource;
import GamePlay.CanvasGame;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class Score extends IScreen{
    public Vector v = null;
//    private Button buttBack = new Button("Quay lại", Resource.IMG_BUTTONLT, Resource.IMG_TXT_BUTTONLT, 2, Constant.buttBack_x, Constant.buttBack_y, Constant.buttonlt_w, Constant.buttonlt_h, Button.ENABLE);
    public Score(CanvasGame _canvas) {
        super(_canvas);
        canvas.getRMS().open();
        v = canvas.getRMS().read();
        canvas.getRMS().close();
    }

    public void load_screen() {
        canvas.getResource().loadArray(canvas.getResource().highScoreArrayImages);
    }

    public void un_load_screen() {
        canvas.getResource().unLoadArray(canvas.getResource().highScoreArrayImages);
    }

    public void keyEvent(int keycode, int event){
        if(event == InputKey.KEY_EVENT_DOWN){
            if(keycode == InputKey.KEY_RIGHT_SOFTKEY){
                canvas.setScreen( new Menu(canvas));
            }
        }
    }


    public void pointerEvent(int x, int y, int event) {
//        switch(event){
//             case InputKey.POINTER_EVENT_DOWN:
//                 if(canvas.checkButton(x, y, buttBack)){
//                    buttBack.setState(Button.HOLD_CLICK);
//                 }
//                 break;
//             case InputKey.POINTER_EVENT_UP:
//                 if(canvas.checkButton(x, y, buttBack) && buttBack.getState() == Button.HOLD_CLICK){
//                    canvas.setScreen( new Menu(canvas) );
//                 }
//                 buttBack.setState(Button.ENABLE);
//                 break;
//         }
    }
    
    public void update() {
        
    }

    public void paint(Graphics g) {
//        canvas.getImage().drawImage(g, canvas, Resource.IMG_BG_OTHER, Constant.menu_x, Constant.menu_y);
        canvas.getImage().drawImageNormal(g, canvas, Resource.IMG_BG_OTHER, Constant.menu_x, Constant.menu_y);


        canvas.getImage().drawImage(g, canvas, Resource.IMG_TXT_HIGHSCORE, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_TXT_HIGHSCORE).getWidth())/2, Constant.txt_score_y);
        canvas.getImage().drawImage(g, canvas, Resource.IMG_BACK, (Constant.SCR_W - canvas.getResource().getIMG(Resource.IMG_BACK).getWidth()) - 28, (Constant.SCR_H - canvas.getResource().getIMG(Resource.IMG_BACK).getHeight()) - 27);
//        buttBack.drawButton(g, canvas);

        g.setFont(CanvasGame.font);
        g.setColor(CanvasGame.BLACK);

        for(int i = 0; i < v.size(); ++i) {
            System.out.println(v.size());
            g.drawString(String.valueOf(i + 1) + ".  ", 50, 80+25*i, Graphics.TOP|Graphics.LEFT);
            g.drawString(((Player)v.elementAt(i)).getName(), 70, 80+25*i, Graphics.TOP|Graphics.LEFT);
            g.drawString(String.valueOf(((Player)v.elementAt(i)).getScore()), 210, 80+25*i, Graphics.TOP|Graphics.LEFT);
        }
    }
}
