
import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class MyUI {

    public Sprite m_ButtonSprite;
    public Sprite m_LabelSprite;
    public Image m_LogoImage;

    MyUI(int nWidth, int nHeight) {
        try {
            m_LogoImage = Image.createImage("/tanklogo.png");
            Image image = Image.createImage("/button.PNG");
            m_ButtonSprite = new Sprite(image, 48, 28);
            int nX = nWidth / 2 - 24;
            int nY = nHeight / 2 - 14 + 10;
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
        g.drawImage(m_LogoImage, 0, 0, 0);
        g.setColor(0xffffff);
        g.drawString("̹�˴�ս", m_LogoImage.getWidth(), 20, 0);
        g.drawString("һ����ʾ", m_LogoImage.getWidth(), 40, 0);
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
