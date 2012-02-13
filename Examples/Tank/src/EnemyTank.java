import java.util.Random;

import javax.microedition.lcdui.Image;

public class EnemyTank extends TankSprite
{
	private Random m_Random;
	EnemyTank(Image image, int frameWidth, int frameHeight) 
	{
		super(image, frameWidth, frameHeight);
		m_Random = new Random();
	}
	public void Logic()
	{
		super.Logic();
		if( !isVisible() )
			return;
		switch( m_nDir ){
		case 0:
			Move( 0, -m_nSpeed );
			break;
		case 1:
			Move( m_nSpeed, 0 );
			break;
		case 2:
			Move( 0, m_nSpeed );
			break;
		default:
			Move( -m_nSpeed, 0 );
			break;
		}		
		int nRs = m_Random.nextInt() % 20;
		if( Math.abs(nRs) == 0 )
			CreateBullet();
	}
	public void Move(int nX, int nY) 
	{
		super.Move(nX, nY);	
		RandomDir();
	}
	private void RandomDir()
	{
		int nX = getRefPixelX();
		int nHalfCellWidth = 15 / 2;
		if( nX % 15 != nHalfCellWidth )
			return;
		int nY = getRefPixelY();
		int nHalfCellHeight = 15 / 2;
		if( nY % 15 != nHalfCellHeight )
			return;
		int nRs = m_Random.nextInt() % 3;
		if( Math.abs(nRs) != 0 )
			return;
		int nDir = Math.abs( m_Random.nextInt() % 4 );
		SetDir( nDir );
	}
}
