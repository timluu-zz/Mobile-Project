
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class BulletSprite extends Sprite {

    private int m_nSpeedX = 0;
    private int m_nSpeedY = 0;

    BulletSprite(Image image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);
        defineReferencePixel(frameWidth / 2, frameHeight / 2);
        setVisible(false);
    }

    public void SetSpeed(int nSpeedX, int nSpeedY) {
        m_nSpeedX = nSpeedX;
        m_nSpeedY = nSpeedY;
    }

    public void Logic() {
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
    }
}
