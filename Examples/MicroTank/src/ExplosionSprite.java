

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;


public class ExplosionSprite  extends  Sprite {
 
  public ExplosionSprite(Image image, int frameWidth, int frameHeight) {
    super(image, frameWidth, frameHeight);
	 defineReferencePixel(frameWidth / 2, frameHeight / 2);
  }
  

public void start(int x,int y ) {
	  for (int i=0;i<1 ;i++ )

     {setPosition(x-16,y-16);
	   this.setVisible(true);
	   setFrame(i);
        try{
        Thread.sleep(25);
          }catch(InterruptedException ie){
        ie.printStackTrace();
          } 
     }
	  setVisible(false);
   }
}
  
  