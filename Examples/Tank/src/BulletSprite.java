import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class BulletSprite extends  Sprite{
	private int m_nSpeed	= 0;
	private int m_nDir		= 0;
	private Player m_Player;
	
	public BulletSprite(Image image, int frameWidth, int frameHeight) 
	{
		super(image, frameWidth, frameHeight);
		defineReferencePixel( frameWidth / 2, frameHeight / 2 );
		setVisible(false);
		try
		{
			InputStream is = this.getClass().getResourceAsStream("/Bullet.wav");
			m_Player = Manager.createPlayer(is,"audio/x-wav");
		}
		catch (IOException e){} 
		catch (MediaException e){}
	}
	public void Start( int nX, int nY, int nDir, int nSpeed ) {
		m_nSpeed 	= nSpeed;
		m_nDir		= nDir;
		setRefPixelPosition( nX, nY );
		setVisible(true);
		try
		{
			m_Player.start();
		}
		catch (MediaException e){}
	}
	public void Stop() {
		setVisible(false);
	}
	
	public void Logic() {
		if( !isVisible() )
			return;
		int nX = getRefPixelX();
		int nY = getRefPixelY();
		switch( m_nDir ){
		case 0://上方
			nY -= m_nSpeed;
			break;
		case 1://右方
			nX += m_nSpeed;
			break;
		case 2://下方
			nY += m_nSpeed;
			break;
		case 3://左方
			nX -= m_nSpeed;
			break;
		}
		setRefPixelPosition( nX, nY );		
	}
	public int GetCurCol(){
		int nX = getRefPixelX();
		switch( m_nDir ){
		case 0://上方
		case 2://下方
			break;
		case 1://右方
			nX += 1;
			break;
		case 3://左方
			nX -= 1;
			break;
		}
		return nX / 15;
	}
	public int GetCurRow(){
		int nY = getRefPixelY();
		switch( m_nDir ){
		case 0://上方
			nY -= 1;
			break;
		case 2://下方
			nY += 1;
			break;
		case 1://右方
		case 3://左方
			break;
		}
		return nY / 15;
	}
}
