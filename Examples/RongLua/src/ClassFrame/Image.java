/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author QuyetNM1
 */
public class Image {

    public static final int NORMAL_SCREEN = 0;
    public static final int ROTATE_SCREEN = 1;

    public Image(){
    }

    public void drawImage(Graphics g, CanvasGame canvas, int Image, int x, int y){
        if(canvas.getTypeScreen() == NORMAL_SCREEN){
            drawImageNormal(g, canvas, Image, x, y);
        }else if(canvas.getTypeScreen() == ROTATE_SCREEN){
            drawImageRotate(g, canvas, Image, x, y);
        }
    }
    
    public void drawImageNormal(Graphics g, CanvasGame canvas, int Image, int x, int y){
         g.drawImage(canvas.getResource().getIMG(Image), x, y, Graphics.TOP|Graphics.LEFT);
    }

    public  void drawImageRotate(Graphics g, CanvasGame canvas, int Image, int X, int Y){
        drawImagePartNormalAnchor(g, canvas, Image,
                    TransCoordinate.getX(X, Y) - canvas.getResource().getIMG(Image).getHeight(),
                    TransCoordinate.getY(X, Y),
                    0, 0,
                    canvas.getResource().getIMG(Image).getWidth() ,
                    canvas.getResource().getIMG(Image).getHeight(),
                    Sprite.TRANS_ROT90);
    }

    public void drawImagePart(Graphics g, CanvasGame canvas, int Image, int x, int y, int x_dest, int y_dest, int w, int h){
        if(canvas.getTypeScreen() == NORMAL_SCREEN){
            drawImagePartNormal(g, canvas, Image, x, y, x_dest, y_dest, w, h);
        }else if(canvas.getTypeScreen() == ROTATE_SCREEN){
            drawImagePartRotate(g, canvas, Image, x, y, x_dest, y_dest, w, h);
        }
    }

//    public void drawImagePart(Graphics g, CanvasGame canvas, int Image, int x, int y, int x_dest, int y_dest, int w, int h){
//        g.drawRegion(canvas.getResource().getIMG(Image), x_dest, y_dest, w, h, Sprite.TRANS_NONE , x, y, Graphics.TOP|Graphics.LEFT);
//    }
    public void drawImagePartNormal(Graphics g, CanvasGame canvas, int Image, int x, int y, int x_dest, int y_dest, int w, int h){
        g.drawRegion(canvas.getResource().getIMG(Image), x_dest, y_dest, w, h, Sprite.TRANS_NONE , x, y, Graphics.TOP|Graphics.LEFT);
    }
    public void drawImagePartNormalAnchor(Graphics g, CanvasGame canvas, int Image, int x, int y, int x_dest, int y_dest, int w, int h, int sprite){
        g.drawRegion(canvas.getResource().getIMG(Image), x_dest, y_dest, w, h, sprite , x, y, Graphics.TOP|Graphics.LEFT);
    }

    public  void drawImagePartRotate(Graphics g, CanvasGame canvas, int Image, int X, int Y, int x_dest, int y_dest, int w, int h){
        drawImagePartNormalAnchor(g, canvas, Image,
                    TransCoordinate.getX(X, Y) - h,
                    TransCoordinate.getY(X, Y),
                    x_dest, y_dest,
                    w, h,
                    Sprite.TRANS_ROT90);
    }

    public void drawStaticFrame(Graphics g, CanvasGame canvas, int Image, int x, int y, int line, int column, int w, int h){
        g.drawRegion(canvas.getResource().getIMG(Image), column*w, line*h, w, h, Sprite.TRANS_NONE , x, y, Graphics.TOP|Graphics.LEFT);
    }
    public void drawNumber(Graphics g, CanvasGame canvas, int Image, int number, int numOfChar, int x, int y) {
        int[] num = new int[20];
	int i = 0;
        int j = 0;
        int w = canvas.getResource().getIMG(Image).getWidth() / numOfChar;
        int h = canvas.getResource().getIMG(Image).getHeight();
        int n = number;

	if(n == 0) {
            i = 1;
            num[0] = 0;
	}
	else {
            while(n != 0) {
                if(i == 19) break;
		num[i++] = n%10;
		n = n/10;
            }
	}
        for(j = i-1; j > -1; --j) {
            g.drawRegion(canvas.getResource().getIMG(Image), w*num[j], 0, w, h, Sprite.TRANS_NONE, x + w*( i-j-1 ), y, Graphics.TOP|Graphics.LEFT);
        }
    }
    public void drawNumber(Graphics g, CanvasGame canvas,int score)
    {        
        String strScore=String.valueOf(score);
        for(int i=0;i<strScore.length();i++)
        {   
            int indexNum=Integer.parseInt(strScore.substring(i, i+1));
            canvas.getImage().drawImagePart(g, canvas, Resource.IMG_SMALL_NUMBER, Constant.SCR_W - 170+ i*7, 4, indexNum*8, 0, 8, 7);            
        }
    }


    

//
//    public TransCoordinate getConvertCoor(){
//        return convertCoor;
//    }
}
