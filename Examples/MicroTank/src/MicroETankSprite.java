import java.io.IOException;


import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class MicroETankSprite
    extends Sprite implements Runnable{
  private int mDirection;
  private int mKX, mKY;
  private int x,y;
  private int delta3;
 
  private int mLastDelta;
    private int efx=4;
    private int esd=2;
  private boolean firsttry=false;
   public    boolean elift=true;
 
  public static BulletsSprite ebullets;  

  private static final int[] kTransformLookup = {
   Sprite.TRANS_MIRROR_ROT270,Sprite.TRANS_MIRROR_ROT270,   
    Sprite.TRANS_ROT90,Sprite.TRANS_NONE, Sprite.TRANS_ROT180 
  };
  
 
  public MicroETankSprite(Image image, int eWidth, int eHeight) {
     super(image, eWidth, eHeight);
    defineReferencePixel(eWidth / 2, eHeight / 2);
	 elift =true;
    try{ebullets=createBullets();
    ebullets.setVisible(false);
     }catch(IOException ioe){
     }catch(Exception e){
     }


    
  }

   private BulletsSprite createBullets() throws IOException {
    Image image = Image.createImage("/bullets.png");
    return new BulletsSprite(image,3,3);
  }

  public void start() {
	 
	  Thread  t = new Thread(this);
         t.start();
	   }
	  
  
  public void run() {
 
   while (elift) {
   	long times_s= System.currentTimeMillis();
     forward(esd,efx);
	 startfire();
 
	long times_e=System.currentTimeMillis();
   	long times=times_e-times_s;
  
   
    
    if( times>80){
	 	times =80;
	}
	  try{
        Thread.sleep( 80-times );
		
      }catch(InterruptedException ie){
        ie.printStackTrace();
      } 
 

	}
  }
  //
  private void forward(int delta,int delta2 ) {
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
  

  private void startfire() {
	  if (this!=null )
	  {	  
      ebullets.start(this.getX(), this.getY(),efx,esd*2,0);
      }
  }


  public void undo() {

     if ((efx>=4)&&firsttry)
	 {efx=0;
	 firsttry=false;
	 efx+=1;}
	else if ((efx>=4)&&(!firsttry))
	 {efx=0;
	 firsttry=true;
	  }else if ((efx==2)&&firsttry)
	  {efx=1;
	  firsttry=false;
	  }else if ((efx==1)&&!firsttry)
	  {efx=3;
	  firsttry=true;
	  }
	 efx+=1; 
	  
  
 

     if (efx==1)
     { esd=-2;
     }else if (efx==2)
     {esd=2;
	  
     }else if (efx==3)
     {esd=-2;
     }else if (efx==4)
     {esd=2;
     } 
	x=x-3;
	y=y-3;
    
  }

  public static void stop(){
 
     Thread.yield() ;
	 
  }
  
  private void fineMove(int kx, int ky) {
 
  x = this.getX();
  y = this.getY();
	x+=kx;
	y+=ky;
     if ( y>=240)
     {y=y-3;
	 undo();}else if(y<=0)
	  {y=y+3;
	 undo();}
		
	 if (x<=0 )
     {x=x+3;
	 undo();}else if (x>=232)
		 {x=x-3;
		 undo();
		 }

     this.setPosition(x, y);

  }
}
