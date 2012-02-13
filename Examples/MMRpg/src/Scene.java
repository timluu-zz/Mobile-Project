
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;

public class Scene {

    public int m_nCurIndex;
    private TiledLayer m_LyPass;
    private TiledLayer m_LyNotPass;
    private TiledLayer m_LyJump;

    Scene() {
        try {
            Image image = Image.createImage("/map.png");
            m_LyPass = new TiledLayer(10, 10, image, 32, 32);
            m_LyNotPass = new TiledLayer(10, 10, image, 32, 32);
            m_LyJump = new TiledLayer(10, 10, image, 32, 32);
        } catch (IOException e) {
        }
    }

    public void AppendToManager(LayerManager mManager) {
        mManager.append(m_LyPass);
        mManager.append(m_LyNotPass);
        mManager.append(m_LyJump);
    }

    public void EnterScene(int nIndex, Actor mActor, Npc mNpc) {
        m_nCurIndex = nIndex;
        InputStream is = null;
        switch (nIndex) {
            case 0:
                is = getClass().getResourceAsStream("/map0.txt");
                mActor.setRefPixelPosition(150, 150);
                mNpc.setFrame(0);
                mNpc.SetText("��Ѩ�ϲ��и���֪�����ڣ�");
                mNpc.setRefPixelPosition(130, 120);
                break;
            default:
                is = getClass().getResourceAsStream("/map1.txt");
                mActor.setRefPixelPosition(200, 160);
                mNpc.setFrame(1);
                mNpc.SetText("��Ѩ�������²�����½ǡ�");
                mNpc.setRefPixelPosition(150, 50);
                break;
        }
        LoadScene(is);
    }

    public int GetWidth() {
        return m_LyPass.getWidth();
    }

    public int GetHeight() {
        return m_LyPass.getHeight();
    }

    public void CollidesWidth(Actor mActor, Npc mNpc) {
        if (mActor.collidesWith(m_LyJump, false)) {
            mActor.MoveBack();
            if (m_nCurIndex == 0) {
                EnterScene(1, mActor, mNpc);
            } else {
                EnterScene(0, mActor, mNpc);
            }
        } else if (mActor.collidesWith(m_LyNotPass, false)) {
            mActor.MoveBack();
        }
    }

    private void LoadScene(InputStream is) {
        try {
            int ch = -1;
            for (int nRow = 0; nRow < 10; nRow++) {
                for (int nCol = 0; nCol < 10; nCol++) {
                    ch = -1;
                    while ((ch < 0 || ch > 7)) {
                        ch = is.read();
                        if (ch == -1) {
                            return;
                        }
                        ch = ch - '0';
                    }
                    if (ch == 4 || ch == 5) {
                        m_LyPass.setCell(nCol, nRow, 0);
                        m_LyNotPass.setCell(nCol, nRow, ch);
                        m_LyJump.setCell(nCol, nRow, 0);
                    } else if (ch == 6 || ch == 7) {
                        m_LyPass.setCell(nCol, nRow, 0);
                        m_LyNotPass.setCell(nCol, nRow, 0);
                        m_LyJump.setCell(nCol, nRow, ch);
                    } else {
                        m_LyPass.setCell(nCol, nRow, ch);
                        m_LyNotPass.setCell(nCol, nRow, 0);
                        m_LyJump.setCell(nCol, nRow, 0);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
