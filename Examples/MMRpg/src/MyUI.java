
import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class MyUI {

    public Sprite m_CaptionSprite;
    public Sprite m_ButtonSprite;
    public Sprite m_LabelSprite;

    MyUI(int nWidth, int nHeight) {
        try {
            Image image = Image.createImage("/Caption.png");
            m_CaptionSprite = new Sprite(image, 80, 23);
            int nX = nWidth / 2 - 40;
            int nY = nHeight / 2 - 11 - 30;
            m_CaptionSprite.setPosition(nX, nY);
            image = null;
            image = Image.createImage("/button.PNG");
            m_ButtonSprite = new Sprite(image, 48, 28);
            nX = nWidth / 2 - 24;
            nY = nHeight / 2 - 14 + 10;
            m_ButtonSprite.setPosition(nX, nY);
            image = null;
            image = Image.createImage("/Label.png");
            m_LabelSprite = new Sprite(image, 37, 13);
            nX = nWidth / 2 - 18;
            nY = nHeight / 2 - 6 + 10;
            m_LabelSprite.setPosition(nX, nY);
        } catch (IOException e) {
            // TODO �Զ���� catch ��
            e.printStackTrace();
        }
    }

    public void Paint(Graphics g) {
        m_CaptionSprite.paint(g);
        m_ButtonSprite.paint(g);
        m_LabelSprite.paint(g);
    }
    //����: false---û�а���true---�����˿�ʼ��

    public boolean Input(int keyStates) {
        if ((keyStates & GameCanvas.FIRE_PRESSED) != 0) {
            return true;
        }
        return false;
    }
}
