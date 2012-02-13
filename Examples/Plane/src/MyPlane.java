
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class MyPlane extends PlaneSprite {

    MyPlane(Image img, int nWidth, int nHeight) {
        super(img, nWidth, nHeight);
        m_nSpeedY = 2;
        m_nSpeedX = 2;
    }

    public void Input(int keyStates) {
        if (isVisible() == false) {
            return;
        }
        int nX = getRefPixelX();
        int nY = getRefPixelY();
        if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
            nY -= m_nSpeedY;
        }
        if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
            nX -= m_nSpeedX;
        }
        if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
            nX += m_nSpeedX;
        }
        if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
            nY += m_nSpeedY;
        }
        if ((keyStates & GameCanvas.FIRE_PRESSED) != 0) {
            CreateBullet();
        }
        setRefPixelPosition(nX, nY);
    }

    public void Logic() {
        super.Logic();
    }

    private void CreateBullet() {
        if (m_Bullet.isVisible()) {
            return;
        }
        m_Bullet.setRefPixelPosition(
                getRefPixelX(), getRefPixelY());
        m_Bullet.SetSpeed(0, -3);
        m_Bullet.setVisible(true);
    }
}
