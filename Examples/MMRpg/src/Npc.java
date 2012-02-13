
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Npc extends Sprite {

    public boolean m_bDialog = false;
    private String m_strText = null;
    private long m_nDialogStartTime = 0;

    Npc(Image image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);
    }

    public void SetText(String strText) {
        m_strText = null;
        m_strText = new String(strText);
    }

    public void DialogStart() {
        m_nDialogStartTime = System.currentTimeMillis();
        m_bDialog = true;
    }

    public void Logic() {
        if (!m_bDialog) {
            return;
        }
        long lTime = System.currentTimeMillis();
        if (lTime - m_nDialogStartTime > 3000) {
            m_bDialog = false;
        }
    }

    public void DrawText(Graphics g, int nWidth, int nHeight) {
        if (!m_bDialog) {
            return;
        }
        g.setColor(0);
        g.fillRect(0, nHeight - 30, nWidth, 30);
        g.setColor(0xffffff);
        g.drawRect(0, nHeight - 30, nWidth - 1, 29);
        g.drawString(m_strText, 10, nHeight - 25,
                Graphics.TOP | Graphics.LEFT);
    }
}
