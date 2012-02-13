import java.io.IOException;


import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class MicroTankSprite
    extends Sprite {
  private int mDirection;
  private int mKX, mKY;
  private int x,y;
  private int delta3;
  private int mLastDelta;
  public static BulletsSprite bullets;  


  private static final int[] kTransformLookup = {
   Sprite.TRANS_MIRROR_ROT270,Sprite.TRANS_MIRROR_ROT270,   
    Sprite.TRANS_ROT90,Sprite.TRANS_NONE, Sprite.TRANS_ROT180 
  };
  
  

  public MicroTankSprite(Image image, int frameWidth, int frameHeight) {
    super(image, frameWidth, frameHeight);
    defineReferencePixel(frameWidth / 2, frameHeight / 2);
  try{bullets=createBullets();

   bullets.setVisible(false);
   // MicroTankCanvas.mLayerManager.append(bullets);
   }catch(IOException ioe){
     }catch(Exception e){
     }
	}

   public BulletsSprite createBullets() throws IOException {
    Image image = Image.createImage("/bullets.png");
    return new BulletsSprite(image,3,3);
  }

 
  public void forward(int delta,int delta2 ) {
	  if (delta2==1 )
	  {fineMove(delta,0);}
	  else if (delta2==2)
	  {fineMove(delta,0);}
	  else if (delta2==3)
	  {fineMove(0,delta);}
	  else if (delta2==4)
	  {fineMove(0,delta);}


	setTransform(kTransformLookup[delta2]);
	delta3=delta2;
	 
    mLastDelta = delta;
   
  }
  
  public void undo() {
     
      forward(-mLastDelta,delta3 );
  }
  
  private void fineMove(int kx, int ky) {
 
  x = getX();
  y = getY();
	x+=kx;
	y+=ky;
     if (y<0 || y>240)
     {y-=ky;}
	 if (x<0 || x>232)
     {x-=kx;}

    setPosition(x, y);

  }
}
