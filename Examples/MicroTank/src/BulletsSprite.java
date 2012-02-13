

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.IOException;

public class BulletsSprite  extends  Sprite implements Runnable{
  private int x, y;
  private int fx;
  private int sd;
  public  int user;
  private boolean lift=false;
   
  int  row,column;
  Sprite sprite;
  
  public BulletsSprite(Image image, int frameWidth, int frameHeight) {
    super(image, frameWidth, frameHeight);
  }
  

public void start(int mx,int my,int mfx,int msd,int master) {
	if (!lift)
	{ setPosition(mx+6,my+6);
	  fx=mfx;
	  sd=msd;
	  user=master;
	  lift=true;
	   setVisible(lift);
	  
     Thread t = new Thread(this);
       t.start();}
	   }
	  
  
  public void run() {
   
   while (lift) {
   	long times_s= System.currentTimeMillis();
     forward(sd,fx);
	 //检测子弹碰撞！
	   undo();
	long times_e=System.currentTimeMillis();
   	long times=times_e-times_s;
	 
    if( times>86){
	 	times =86;
	}
	  try{
        Thread.sleep( 86-times );
      }catch(InterruptedException ie){
        ie.printStackTrace();
      } 
	}
  }

  
  public void forward(int b_sd,int b_fx ) {
	  if (b_fx==1 )
	  {fineMove(b_sd,0);}
	  else if (b_fx==2)
	  {fineMove(b_sd,0);}
	  else if (b_fx==3)
	  {fineMove(0,b_sd);}
	  else if (b_fx==4)
	  {fineMove(0,b_sd);}
     }
  
  public void undo() {

  if (this.collidesWith(MicroTankCanvas.mBoard, true)){
 row=(this.getY()+1)/15;  //14是每张贴图（小砖块）的像素（长=宽=14)
 column=(this.getX()+1)/15;
 // MicroTankCanvas.explosion.start(this.getX(),this.getY());//调用爆炸方法
 MicroTankCanvas.mBoard.setCell(column,row,0);
 setVisible(false);
 this.lift=false;
 
   


}else if (  (collidesWith(MicroTankCanvas.eTank, true)) && (user==1))
 {//MicroTankCanvas.explosion.start(getX(),getY());//调用爆炸方法

 MicroTankCanvas.eTank.elift=false;
 MicroTankCanvas.eTank.setVisible(false);
 MicroTankCanvas.mLayerManager.remove(MicroTankCanvas.eTank);
 
  MicroTankCanvas.eTank.stop();

 this.setVisible(false);
 this.lift=false;
  MicroTankCanvas.enum=MicroTankCanvas.enum-1;
  Thread.yield();
 
 if (MicroTankCanvas.enum>0)
{ MicroTankCanvas.createETank();
}else{

//进入下一关

}

 
 }  else if (  (collidesWith(MicroTankCanvas.mTank, true)) && (user==0))
 {// MicroTankCanvas.explosion.start(getX(),getY());//调用爆炸方法
 MicroTankCanvas.mTank.setVisible(false);
 MicroTankCanvas.mLayerManager.remove(MicroTankCanvas.mTank);
 this.setVisible(false);
 this.lift=false;
 MicroTankCanvas.num=MicroTankCanvas.num-1;
 Thread.yield();
if (MicroTankCanvas.num>0)
{ MicroTankCanvas.createTank();
}else{

//结束游戏
     
MicroTankCanvas.gameover=true;
}
 
   
	
 }
 
 
 }

   public void stop() {
    
   Thread.yield();
 
  }
  
  private void fineMove(int kx, int ky) {
   
    int bx = getX();
    int by = getY();
	bx+=kx;
	by+=ky;
     if (by<0 || by>MicroTankCanvas.h)
     {this.setVisible(false);
	 lift=false; 
	  Thread.yield();}
	 if (bx<0 || bx>MicroTankCanvas.w)
     {this.setVisible(false);
	 lift=false;  
	  Thread.yield();}
     setPosition(bx, by);

  }
}
