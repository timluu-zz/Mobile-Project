/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFrame;

import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class Button extends FormItem{
    
    public Button(String stringName, int imageButton, int imageTxtButton, int id, int x, int y, int w, int h, int state){
        super(stringName, imageButton, imageTxtButton, id, x, y, w, h, state);
    }

    public void drawButton(Graphics g, CanvasGame canvas){
        canvas.getImage().drawImagePart(g, canvas, image1, x, y, 0, state*h, w, h);
        if(image2 >= 0){
            canvas.getImage().drawImagePart(g, canvas, image2, x, y, 0, id*h, w, h);
        }
        
//        g.drawString(stringName, x + (w - StringUtils.getStringWidth(stringName, Constant.fontButton))/2, y  + (h - StringUtils.getStringHeigh(Constant.fontButton))/2, Graphics.TOP|Graphics.LEFT);
    }

}
