
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class Actor extends Sprite {

    private int m_nLastMoveX;
    private int m_nLastMoveY;

    Actor(Image image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);
    }

    public boolean Input(int keyStates) {
        int nFrame = getFrame();
        if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
            Move(0, -1);
            nFrame++;
            if (nFrame > 1 || nFrame < 0) {
                nFrame = 0;
            }
            setFrame(nFrame);
            setTransform(TRANS_NONE);
        } else if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
            Move(1, 0);
            nFrame++;
            if (nFrame > 3 || nFrame < 2) {
                nFrame = 2;
            }
            setFrame(nFrame);
            setTransform(TRANS_NONE);
        } else if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
            Move(0, 1);
            nFrame++;
            if (nFrame > 5 || nFrame < 4) {
                nFrame = 4;
            }
            setFrame(nFrame);
            setTransform(TRANS_NONE);
        } else if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
            Move(-1, 0);
            nFrame++;
            if (nFrame > 3 || nFrame < 2) {
                nFrame = 2;
            }
            setFrame(nFrame);
            setTransform(TRANS_MIRROR);
        } else {
            return false;
        }
        return true;
    }

    private void Move(int nX, int nY) {
        m_nLastMoveX = nX;
        m_nLastMoveY = nY;
        setRefPixelPosition(getRefPixelX() + nX, getRefPixelY() + nY);
    }

    public void MoveBack() {
        Move(-m_nLastMoveX, -m_nLastMoveY);
        m_nLastMoveX = 0;
        m_nLastMoveY = 0;
    }
}
