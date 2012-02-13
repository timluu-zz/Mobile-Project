import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class MicroTankCanvas
    extends GameCanvas
    implements Runnable,CommandListener  {
  public static boolean mTrucking;
   public static MicroTankSprite mTank;
   public static  MicroETankSprite eTank;
  public static TiledLayer mBoard;
  public static ExplosionSprite explosion;
  public static int num =1;
  public static int enum =5;
  public static int fx;
  public static int sd;
   public static boolean gameover=false;
  public static LayerManager mLayerManager;
  public static  int w;
  public static  int h;
   private MicroTankMIDlet midlet;
    private  Command command ;
 
   private  Command exitCommand = new Command("退出", Command.EXIT, 0);
  public MicroTankCanvas(MicroTankMIDlet midlet) throws IOException {
    super(true);
    
	
   this.midlet=midlet;
  

  
       addCommand(exitCommand);
        setCommandListener(this);

    mBoard = createBoard();
	explosion = createExplosion();
	 explosion.setVisible(false);
     mLayerManager = new LayerManager();
	 mLayerManager.append(explosion);
   
	  createTank();
  createETank(); 
 
 mLayerManager.append(mBoard);
	  splash();

	
  }

 
 public static void  createTank() {
      try{ Image image = Image.createImage("/tank.png");
	
   mTank= new MicroTankSprite(image, 14, 14);
    mLayerManager.append(mTank); 
	mLayerManager.append(mTank.bullets);
	 mTank.setPosition(100, 52); 
	 fx=3;
     sd=-4;
	 }catch(IOException ioe){

    }catch(Exception e){

    }


	
  }

   public static void  createETank() {
    try{ Image images = Image.createImage("/enemyTank.png");
	
    eTank= new MicroETankSprite(images, 14, 14);
    mLayerManager.append(eTank); 
	  mLayerManager.append(eTank.ebullets);
	eTank.setPosition(18, 12); 
	eTank.start();
	 }catch(IOException ioe){

    }catch(Exception e){

    }
  }

 private  ExplosionSprite createExplosion() throws IOException {
	Image image = Image.createImage("/explosion.png");
    return  new ExplosionSprite(image, 3,3 );
 	}

 
  private TiledLayer createBoard() throws IOException {
    Image image = Image.createImage("/bg.png");
    TiledLayer tiledLayer = new TiledLayer(11, 11, image, 15, 15);
    
    int[] map = {
         0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
         0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0,
         0, 3, 3, 3, 3, 4, 4, 4, 4, 4, 0,
         0, 3, 0, 0, 0, 0, 0, 0, 0, 4, 0,
         0, 4, 0, 0, 4, 0, 0, 0, 0, 4, 0,
         0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0,
         0, 4, 0, 1, 1, 1, 1, 1, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
         0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,
         0, 0, 0, 0, 4, 0, 0, 2, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
    };
    
    for (int i = 0; i < map.length; i++) {
      int column = i % 11;
      int row = (i - column) / 11;
      tiledLayer.setCell(column, row, map[i]);

     }
    
    return tiledLayer;
  }

  
  
  public  void start() {
    
	
    Thread t = new Thread(this);
    t.start();
  }
  
  public   void run() {
    Graphics g = getGraphics();
    
     
    
    while (mTrucking) {
   	long times_s= System.currentTimeMillis();
      
       input(); 
	   tick();
     render(g);
	 
   	long times_e=System.currentTimeMillis();
   	long times=times_e-times_s;
	 
    if( times >80 ){
	 	times =80;
	}
	  try{
        Thread.sleep(80- times );
      }catch(InterruptedException ie){
        ie.printStackTrace();
      } 
	}
  }
  
  private void tick() {
    if (mTank.collidesWith(mBoard, true))
	  {mTank.undo();}
  if (eTank.collidesWith(mBoard, true))
	 { eTank.undo();}
  if (eTank.collidesWith(mTank, true))

	   { 
    eTank.setVisible(false);
	   mTank.setVisible(false);
   mTank.setVisible(false);
     mLayerManager.remove(MicroTankCanvas.mTank);
	   eTank.setVisible(false);
     mLayerManager.remove(MicroTankCanvas.eTank);
  eTank.elift=false;

   createTank();
createETank(); 
  
	   
   }

  }
  
  private void input() {
 
     
    int keyStates = getKeyStates();
    if ((keyStates & LEFT_PRESSED) != 0)
	  {mTank.forward(-2,1);
	  fx=1;
	  sd=-5;
	  if ((keyStates & FIRE_PRESSED) != 0) 
	  {	 mTank.bullets.start(mTank.getX(), mTank.getY(),fx,sd,1);
        }}

    else if ((keyStates & RIGHT_PRESSED) != 0) 
	  {mTank.forward(2,2);
	    fx=2;
		sd=5;
		if ((keyStates & FIRE_PRESSED) != 0) 
	  {	 mTank.bullets.start(mTank.getX(), mTank.getY(),fx,sd,1);
        }}

    else if ((keyStates & UP_PRESSED) != 0) 
	  {mTank.forward(-2,3);
	    fx=3;
		sd=-5;
		if ((keyStates & FIRE_PRESSED) != 0) 
	  {	 mTank.bullets.start(mTank.getX(), mTank.getY(),fx,sd,1);
        }}

    else if ((keyStates & DOWN_PRESSED) != 0)
	  {mTank.forward(2,4);
	    fx=4;
		sd=5;
		 if ((keyStates & FIRE_PRESSED) != 0) 
	  {	 mTank.bullets.start(mTank.getX(), mTank.getY(),fx,sd,1);
        }
	}

	 else if ((keyStates & FIRE_PRESSED) != 0) 
	  {	    
		 
		 mTank.bullets.start(mTank.getX(), mTank.getY(),fx,sd,1);
        }
	
  }
  
  private void render(Graphics g) {
   w = getWidth();
   h = getHeight();

    g.setColor(0x000000);
    g.fillRect(0, 0, w, h);

    g.setColor(0xffffff);
 g.drawString("我方",2,250,g.LEFT|g.TOP); 
 g.drawString(String.valueOf(num),30,250,g.LEFT|g.TOP);

  g.drawString("敌方",50,250,g.LEFT|g.TOP); 
 g.drawString(String.valueOf(enum),80,250,g.LEFT|g.TOP);
    
    int x = 1;
    int y = 1;
    
	
  // mLayerManager.setViewWindow(mTank.getX()-200,mTank.getY()-200,mTank.getX()+200,mTank.getY()+200);
    mLayerManager.paint(g, x, y);
    flushGraphics();

	if (gameover)
	{  mTrucking=false;
	 w = getWidth();
    h = getHeight();
    g.setColor(0x000000);
    g.fillRect(0, 0, w, h);
	g.setColor(0xffffff);
    g.drawString("GAME OVER",w/2-80,h/2,g.LEFT|g.TOP); 
	g.drawString("Tank 1.0 B",w/2-80,h/2+15,g.LEFT|g.TOP); 
	g.drawString("www.reasy.net 瑞易科技",w/2-80,h/2+30,g.LEFT|g.TOP); 
 	 flushGraphics();
	}
  }
  

public void splash() {
	Image tanklogo = null;
	try {
	    tanklogo = Image.createImage("/tanklogo.png");
	} catch (IOException e) { }
	Graphics g = getGraphics();
	g.setColor(0);
	g.fillRect(0, 0, getWidth(), getHeight());
	g.drawImage(tanklogo, 0, 0, 0);
	g.setColor(0xffffff);
 
	g.drawString("坦克大战", tanklogo.getWidth(), 20, 0);
	g.drawString("reasy.net", tanklogo.getWidth(), 40, 0);
	 
	g.drawString("瑞易科技", tanklogo.getWidth(), 80, 0);
	g.setColor(0x999999);
	g.drawString("一个演示", tanklogo.getWidth(), 100, 0);
	repaint();
	command = new Command(mTrucking == false ? "开始" : "重新开始", 
			      Command.OK, 1);
	addCommand(command);
    }

 public void commandAction(Command c, Displayable s) {
    if (c.getCommandType() == Command.EXIT) {
      midlet.notifyDestroyed();
    }else if (c.getCommandType() == Command.OK)
    {mTrucking=true;
	start();
    }
  }

  public  void stop() {
    mTrucking = false;
  }
}

