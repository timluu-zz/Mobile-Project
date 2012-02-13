import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;

public class MainCanvas extends GameCanvas
			implements Runnable,CommandListener  {
	private MyTank m_Tank;
	private int	m_nTotalETank	= 20;
	private int	m_nDestroyETank = 0;
	private EnemyTank m_eTank[];
	private Scene m_Scene;
	public static ExplosionSprite m_aExplosion[];
	
	private boolean m_bGameEnd;
	public static LayerManager m_LayerManager;
	public static int m_nScrWidth;
	public static int m_nScrHeight;
	private TankMidlet m_midlet;
	private  Command CommandExit;
	private Random m_Random;
	private MyUI m_UI;
	public MainCanvas(TankMidlet midlet) throws IOException {
	    super(true);
	    m_midlet = midlet;
	    setCommandListener(this);	
	    m_UI = new MyUI( getWidth(), getHeight() );
	    Thread t = new Thread(this);
	    t.start();
	   
	    
	}

	public  void Start() {
		m_UI = null;
		CommandExit = new Command("退出", Command.EXIT, 0);
	    addCommand(CommandExit);
	    m_Random = new Random();
	    m_eTank 	= new EnemyTank[2];
	    m_aExplosion 	= new ExplosionSprite[3];
	    try{ 
			Image image = Image.createImage("/tank.png");
			m_Tank = new MyTank( image, 13, 13 );
			image = Image.createImage("/enemyTank.png");
			for( int n = 0; n < m_eTank.length; n ++ ){
				m_eTank[n] = new EnemyTank( image, 13, 13 );
			}
			image = Image.createImage("/Explosion.png");
			for( int m = 0; m < m_aExplosion.length; m ++ ){
				m_aExplosion[m] = new ExplosionSprite( image, 21, 17 );
				m_aExplosion[m].setVisible(false);
			}
			
		}
	    catch(IOException ioe){}
	    catch(Exception e){}
	    
	    m_Scene = new Scene();
	    m_LayerManager = new LayerManager();
	    for( int m = 0; m < m_aExplosion.length; m ++ ){
	    	m_LayerManager.append(m_aExplosion[m]);
		}
	    m_LayerManager.append(m_Scene.m_LyCanPass);
	    m_LayerManager.append( m_Tank );
	    m_LayerManager.append( m_Tank.m_Bullet );
	    for( int n = 0; n < m_eTank.length; n ++ ){
	    	m_LayerManager.append(m_eTank[n]);
	    	m_LayerManager.append(m_eTank[n].m_Bullet);
		}
	    m_LayerManager.append(m_Scene.m_LyBulletPass);
	    m_LayerManager.append(m_Scene.m_LyCanHit);
	    m_LayerManager.append(m_Scene.m_LyNotPass);
	    m_LayerManager.append(m_Scene.m_LyHQ);
	    
		m_Scene.LoadMap();
		m_Tank.Start( 15 * 3 + 7, 15 * 10 + 7, 0, 2 );
		SetViewWindow();
		
	}
	
	public void run() {	    
		while (true) {
			long times_s= System.currentTimeMillis();
			Input(); 
			Logic();
			Render();
		 
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

	private void Input() {
		int keyStates = getKeyStates();
		if( m_UI != null ){
			if( m_UI.Input( keyStates ) == true ){
				Start();
			}
			return;
		}
		m_Tank.Input( keyStates );
		SetViewWindow();
	}
	
	private void Logic() {
		if( m_UI != null ){
			return;
		}
		for( int m = 0; m < m_aExplosion.length; m ++ ){
			m_aExplosion[m].Logic();
		}
		m_Tank.Logic();
	    for( int n = 0; n < m_eTank.length; n ++ ){
	    	m_eTank[n].Logic();
		}
	    CheckCollision();
	    CreateETank();
	}
	  
	private void Render() {
		Graphics g = getGraphics();
		
	    g.setColor(0x000000);
	    g.fillRect(0, 0, getWidth(), getHeight());
	    if( m_UI != null ){
			m_UI.Paint( g );
			flushGraphics();
			return;
		}
	    
	    m_LayerManager.paint(g, 0, 0);

		if (m_bGameEnd)
		{  
			int w = getWidth();
			int h = getHeight();
			//g.setColor(0x000000);
			//g.fillRect(0, 0, w, h);
			g.setColor(0xffffff);
			g.drawString("GAME OVER",w/2-80,h/2,Graphics.LEFT|Graphics.TOP); 
			g.drawString("Tank 1.0 B",w/2-80,h/2+15,Graphics.LEFT|Graphics.TOP); 
			g.drawString("java手机游戏开发培训教程",w/2-80,h/2+30,Graphics.LEFT|Graphics.TOP); 
		}
		flushGraphics();
	}
	
	public void commandAction(Command c, Displayable s) {
		if (c.getCommandType() == Command.EXIT) {
			m_midlet.notifyDestroyed();
	    }
	}
	public void SetViewWindow(){
		if( m_LayerManager == null )
			return;
		int nX = m_Tank.getRefPixelX() - getWidth()/2;
		int nY = m_Tank.getRefPixelY() - getHeight()/2;
		if( nX < 0 )
			nX = 0;
		else if( nX > 15 * 13 - getWidth() )
			nX = 15 * 13 - getWidth();
		if( nY < 0 )
			nY = 0;
		else if( nY > 15 * 12 - getHeight() )
			nY = 15 * 12 - getHeight();
		m_LayerManager.setViewWindow( nX, nY, getWidth(), getHeight() );
	}
	private void CheckCollision(){
					
		BulletSprite mB = m_Tank.m_Bullet;
		BulletSprite mEB = null;
		for( int n = 0; n < m_eTank.length; n ++ ){
			mEB = m_eTank[n].m_Bullet;
			//敌人子弹和我方坦克碰撞
			if( mEB.collidesWith( m_Tank, false ) ){
				mEB.setVisible(false);
				m_bGameEnd = true;
				break;
			}
			//我方子弹和敌人坦克碰撞
			if( mB.collidesWith( m_eTank[n], false ) ){
				CreateExplosion(mB.getRefPixelX(), mB.getRefPixelY());
				mB.setVisible( false );
				m_eTank[n].setVisible( false );
				m_nDestroyETank ++;
				if( m_nDestroyETank >= 20 ){
					m_bGameEnd = true;
					return;
				}
			}
			//我方坦克和敌人碰撞
			if( m_Tank.collidesWith( m_eTank[n], false ) ){
				m_Tank.MoveBack();
				m_eTank[n].MoveBack();
			}
			//敌人和地图
			if( m_eTank[n].collidesWith( m_Scene.m_LyBulletPass, false ) ){
				m_eTank[n].MoveBack();
			}
			else if( m_eTank[n].collidesWith( m_Scene.m_LyCanHit, false ) ){
				m_eTank[n].MoveBack();
			}
			else if( m_eTank[n].collidesWith( m_Scene.m_LyNotPass, false ) ){
				m_eTank[n].MoveBack();
			}		
			//敌人子弹与地图相撞
			int nERow = mEB.GetCurRow();
			int nECol = mEB.GetCurCol();
			if( nERow < 0 || nERow >= m_Scene.m_LyCanHit.getRows() ||
					nECol < 0 || nECol >= m_Scene.m_LyCanHit.getColumns() ){
				mEB.setVisible(false);
				continue;
			}
			if( mEB.collidesWith( m_Scene.m_LyHQ, false ) ){
				m_Scene.m_LyHQ.setCell( nECol, nERow, 6 );
				CreateExplosion(mEB.getRefPixelX(), mEB.getRefPixelY());
				mEB.setVisible( false );
				m_bGameEnd = true;
				return;
			}
			if( mEB.collidesWith( m_Scene.m_LyCanHit, false ) )
			{
				m_Scene.m_LyCanHit.setCell( nECol, nERow, 0 );
				CreateExplosion(mEB.getRefPixelX(), mEB.getRefPixelY());
				mEB.setVisible( false );
			}
			else if( mEB.collidesWith( m_Scene.m_LyNotPass, false ) ){
				CreateExplosion(mEB.getRefPixelX(), mEB.getRefPixelY());
				mEB.setVisible( false );
			}
		}
//		我方坦克和地图
		if( m_Tank.collidesWith( m_Scene.m_LyBulletPass, false ) ){
			m_Tank.MoveBack();
		}
		else if( m_Tank.collidesWith( m_Scene.m_LyCanHit, false ) ){
			m_Tank.MoveBack();
		}
		else if( m_Tank.collidesWith( m_Scene.m_LyNotPass, false ) ){
			m_Tank.MoveBack();
		}		
		//我方子弹与地图相撞
		
		int nRow = mB.GetCurRow();
		int nCol = mB.GetCurCol();
		if( nRow < 0 || nRow >= m_Scene.m_LyCanHit.getRows() ||
				nCol < 0 || nCol >= m_Scene.m_LyCanHit.getColumns() ){
			mB.setVisible(false);
			return;
		}
			
		if( mB.collidesWith( m_Scene.m_LyHQ, false ) ){
			m_Scene.m_LyHQ.setCell( nCol, nRow, 6 );
			CreateExplosion(mB.getRefPixelX(), mB.getRefPixelY());
			m_bGameEnd = true;
			mB.setVisible( false );
		}
		else if( mB.collidesWith( m_Scene.m_LyCanHit, false ) )
		{
			m_Scene.m_LyCanHit.setCell( nCol, nRow, 0 );
			CreateExplosion(mB.getRefPixelX(), mB.getRefPixelY());
			mB.setVisible( false );
		}
		else if( mB.collidesWith( m_Scene.m_LyNotPass, false ) )
		{
			CreateExplosion(mB.getRefPixelX(), mB.getRefPixelY());
			mB.setVisible( false );
		}
	}
	public void  CreateETank() {
		if( m_nTotalETank <= 0 )
			return;
		int nRs = m_Random.nextInt() % 20;
		if( Math.abs(nRs) != 0 )
			return;
		for( int n = 0; n < m_eTank.length; n ++ ){
	    	if( m_eTank[n].isVisible() )
	    		continue;
	    	m_eTank[n].Start( n * 2 * 15 + 15 + 7, 15 + 7, 2, 1 );
	    	break;
		}
	}
	public void CreateExplosion( int nX, int nY ){
		for( int m = 0; m < m_aExplosion.length; m ++ ){
	    	if( m_aExplosion[m].isVisible() )
	    		continue;
	    	m_aExplosion[m].Start( nX, nY );
	    	break;
		}
	}
	
}