import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class ExplosionSprite extends  Sprite{
	private Player m_Player;
	ExplosionSprite(Image image, int frameWidth, int frameHeight) {
		super(image, frameWidth, frameHeight);
		defineReferencePixel( frameWidth / 2, frameHeight / 2 );
		setVisible( false );
		try
		{
			InputStream is = this.getClass().getResourceAsStream("/Explosion.wav");
			m_Player = Manager.createPlayer(is,"audio/x-wav");
		}
		catch (IOException e){} 
		catch (MediaException e){}
	}
	public void Start( int nX, int nY ){
		setRefPixelPosition( nX, nY );
		setVisible(true);
		setFrame(0);
		try
		{
			m_Player.start();
		}
		catch (MediaException e){}
	}
	public void Logic(){
		if( !isVisible() )
			return;
		int nFrame = getFrame();
		nFrame ++;
		if( nFrame > 7 ){
			setVisible( false );
			return;
		}
		setFrame( nFrame );
	}
}
