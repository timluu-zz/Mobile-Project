
import java.util.Random;
import javax.microedition.lcdui.Image;

public class Enemy extends PlaneSprite {

    private Random m_Random;

    Enemy(Image img, int nWidth, int nHeight) {
        super(img, nWidth, nHeight);
        m_Random = new Random();
    }

    public void Logic() {
        super.Logic();
        if (isVisible() == false) {
            return;
        }
        int nX = getRefPixelX() + m_nSpeedX;
        int nY = getRefPixelY() + m_nSpeedY;
        setRefPixelPosition(nX, nY);
        if (getRefPixelX() < -10
                || getRefPixelX() > MainCanvas.m_nScrWidth + 10
                || getRefPixelY() < -10
                || getRefPixelY() > MainCanvas.m_nScrHeight + 10) {
            setVisible(false);
        }
        CreateBullet();
    }

    private void CreateBullet() {
        if (m_Bullet.isVisible()) {
            return;
        }
        int RS = Math.abs(m_Random.nextInt() % 30);
        if (RS != 0) {
            return;
        }
        int nSpeedX = m_Random.nextInt() % 2;
        int nSpeedY = Math.abs(m_Random.nextInt() % 2) + 1;
        m_Bullet.SetSpeed(nSpeedX, nSpeedY);
        m_Bullet.setRefPixelPosition(
                getRefPixelX(), getRefPixelY());
        m_Bullet.setVisible(true);
    }
}
