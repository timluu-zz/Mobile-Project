
import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class PlaneSprite extends Sprite {

    protected int m_nSpeedX = 0;
    protected int m_nSpeedY = 0;
    public BulletSprite m_Bullet = null;

    public PlaneSprite(Image img, int nWidth, int nHeight) {
        super(img, nWidth, nHeight);
        try {
            Image BImg = Image.createImage("/Bullet.png");
            m_Bullet = new BulletSprite(BImg, 5, 5);
        } catch (IOException e) {
        }
        setVisible(false);
        defineReferencePixel(nWidth / 2, nHeight / 2);
    }

    public void SetSpeed(int nSpeedX, int nSpeedY) {
        m_nSpeedX = nSpeedX;
        m_nSpeedY = nSpeedY;
    }

    public void Logic() {
        m_Bullet.Logic();
    }
}
