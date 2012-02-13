import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class MyTank extends TankSprite
{
	MyTank(Image image, int frameWidth, int frameHeight) 
	{
		super(image, frameWidth, frameHeight);
	}
	public void Input( int keyStates )
	{
		if ((keyStates & GameCanvas.UP_PRESSED) != 0)
	    {
			SetDir( 0 );
			setTransform(TRANS_NONE);
			Move( 0, -m_nSpeed );
		}
	    if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) 
	    {
	    	SetDir( 1 );
	    	setTransform(TRANS_ROT90);
	    	Move( m_nSpeed, 0 );
		}
	    if ((keyStates & GameCanvas.DOWN_PRESSED) != 0)
	    {
	    	SetDir( 2 );
	    	setTransform(TRANS_ROT180);
	    	Move( 0, m_nSpeed );
		}
	    if ((keyStates & GameCanvas.LEFT_PRESSED) != 0)
		{
	    	SetDir( 3 );
	    	setTransform(TRANS_MIRROR_ROT270);
	    	Move( -m_nSpeed, 0 );
	    }
	    if ((keyStates & GameCanvas.FIRE_PRESSED) != 0) 
	    {	    
	    	CreateBullet();
	    }
	}

}
