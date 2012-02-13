import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class TankSprite extends Sprite {
	protected int m_nDir;
	protected int m_nLastMovX	= 0;
	protected int m_nLastMovY	= 0;
	protected int m_nSpeed		= 0;
	public BulletSprite m_Bullet;  
	public TankSprite(Image image, int frameWidth, int frameHeight) {
		super(image, frameWidth, frameHeight);
		defineReferencePixel(frameWidth / 2, frameHeight / 2);
		setVisible( false );
		try{
			Image img = Image.createImage("/bullets.png");
			m_Bullet = new BulletSprite(img,3,3);
		}
		catch(IOException ioe){}
		catch(Exception e){}
	}
	public void Start( int nX, int nY, int nDir, int nSpeed ){
		SetDir( nDir );
		setRefPixelPosition( nX, nY );
		m_nSpeed = nSpeed;
		setVisible( true );
	}
	public void Stop() {
		setVisible(false);
	}
	
	public void Logic(){
		m_Bullet.Logic();
	}
	
	public void SetDir( int nDir ){
		m_nDir = nDir;
		switch( m_nDir ){
		case 0:
			setTransform(TRANS_NONE);
			break;
		case 1:
			setTransform(TRANS_ROT90);
			break;
		case 2:
			setTransform(TRANS_ROT180);
			break;
		default:
			setTransform(TRANS_MIRROR_ROT270);
			break;
		}
	}
	public void Move(int nX, int nY) {
		int nToX = getRefPixelX() + nX;
		int nToY = getRefPixelY() + nY;
		m_nLastMovX = nX;
		m_nLastMovY = nY;
		setRefPixelPosition( nToX, nToY );
	}
	public void MoveBack() {
		Move( -m_nLastMovX, -m_nLastMovY );
		m_nLastMovX = 0;
		m_nLastMovY = 0;
	}
	public void CreateBullet(){
	    if( m_Bullet.isVisible() )
	    	return;
	    m_Bullet.setVisible(true);
	    int nX = getRefPixelX();
	    int nY = getRefPixelY();
	    m_Bullet.Start( nX, nY, m_nDir, 3 );
	}
}