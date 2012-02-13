import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

public class Scene 
{
	public TiledLayer m_LyCanPass; 
	public TiledLayer m_LyBulletPass;
	public TiledLayer m_LyCanHit; //¿É´Ý»Ù
	public TiledLayer m_LyNotPass;
	public TiledLayer m_LyHQ;
	Scene(){
		
		try{
			Image image = Image.createImage("/bg.png");
			m_LyCanPass = new TiledLayer( 13, 12, image, 15, 15 );
			m_LyBulletPass = new TiledLayer( 13, 12, image, 15, 15 );
			m_LyCanHit = new TiledLayer( 13, 12, image, 15, 15 );
			m_LyNotPass = new TiledLayer( 13, 12, image, 15, 15 );
			m_LyHQ	= new TiledLayer( 13, 12, image, 15, 15 );
		}
		catch(IOException ioe){}
	    catch(Exception e){}
	}
	public void LoadMap(){
		try{
			InputStream is = getClass().getResourceAsStream("/map.txt");
			int ch = -1;
			for( int nRow = 0; nRow < 12; nRow ++ )
			{
				for( int nCol = 0; nCol < 13; nCol ++ )
				{
					ch = -1;
					while( ( ch < 0 || ch > 6 ) ){
						ch = is.read();
						if( ch == -1 )
							return;
						ch = ch - '0';
					}
					m_LyCanPass.setCell( nCol, nRow, 0 );
					m_LyBulletPass.setCell( nCol, nRow, 0 );
					m_LyCanHit.setCell( nCol, nRow, 0 );
					m_LyNotPass.setCell( nCol, nRow, 0 );
					m_LyHQ.setCell( nCol, nRow, 0 );
					if( ch == 1 )
						m_LyCanPass.setCell( nCol, nRow, ch );
					else if( ch == 2 )
						m_LyBulletPass.setCell( nCol, nRow, ch );
					else if( ch == 3 )
						m_LyNotPass.setCell( nCol, nRow, ch );
					else if( ch == 4 )
						m_LyCanHit.setCell( nCol, nRow, ch );
					else
						m_LyHQ.setCell( nCol, nRow, ch );
				}
			}	
		}
		catch(IOException ioe){}
		catch(Exception e){}
	}

}