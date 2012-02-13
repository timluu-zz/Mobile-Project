
import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.rms.RecordStore;
/*
 * Created on 2006-4-22
 *
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

/**
 * @author ����
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MainCanvas extends GameCanvas implements Runnable, CommandListener {

    private Command ExitCommand;
    private PlaneMidlet midlet;
    private boolean m_bGameEnd = false;
    private int m_nHighScore = 0;
    private int m_nScore = 0;
    private MyPlane m_MyPlane;
    private Enemy m_aEnemy[];
    private BlastSprite m_aBlast[];
    private LayerManager m_LayerManager;
    private Image m_BackImage;
    private Random m_Random;
    public static int m_nScrWidth;
    public static int m_nScrHeight;

    public MainCanvas(PlaneMidlet mMidlet) {
        super(true);
        midlet = mMidlet;
        ExitCommand = new Command("Exit", Command.EXIT, 0);
        addCommand(ExitCommand);
        setCommandListener(this);
        m_nScrWidth = getWidth();
        m_nScrHeight = getHeight();
        m_Random = new Random();
        m_LayerManager = new LayerManager();
        try {
            m_BackImage = Image.createImage("/Back.png");
            Image img = Image.createImage("/Blast.png");
            m_aBlast = new BlastSprite[5];
            for (int n = 0; n < m_aBlast.length; n++) {
                m_aBlast[n] = new BlastSprite(img, 21, 17);
                m_LayerManager.append(m_aBlast[n]);
            }
            img = null;
            img = Image.createImage("/MyPlane.png");
            m_MyPlane = new MyPlane(img, 30, 20);
            int nX = getWidth() / 2;
            int nY = getHeight() - 30;
            m_MyPlane.setRefPixelPosition(nX, nY);
            m_MyPlane.setVisible(true);
            m_LayerManager.append(m_MyPlane);
            m_LayerManager.append(m_MyPlane.m_Bullet);
            img = null;
            img = Image.createImage("/Enemy.png");
            m_aEnemy = new Enemy[8];
            for (int n = 0; n < m_aEnemy.length; n++) {
                m_aEnemy[n] = new Enemy(img, 15, 15);
                m_LayerManager.append(m_aEnemy[n]);
                m_LayerManager.append(m_aEnemy[n].m_Bullet);
            }
        } catch (IOException e) {
        }
        LoadHighScore();
        Thread thread = new Thread(this);
        thread.start();
    }

    public void Input() {
        int keyStates = getKeyStates();
        m_MyPlane.Input(keyStates);
    }

    public void Logic() {
        m_MyPlane.Logic();
        for (int n = 0; n < m_aEnemy.length; n++) {
            m_aEnemy[n].Logic();
        }
        for (int n = 0; n < m_aBlast.length; n++) {
            m_aBlast[n].Logic();
        }
        CreateEnemy();
        CheckCollisions();
    }

    protected void Paint() {
        Graphics g = getGraphics();
        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (m_BackImage != null) {
            int nBgX = (getWidth() - m_BackImage.getWidth()) / 2;
            int nBgY = (getHeight() - m_BackImage.getHeight()) / 2;
            g.drawImage(m_BackImage, nBgX, nBgY, Graphics.TOP | Graphics.LEFT);
        }
        //m_LayerManager.paint( g, 0, 0 );
        for (int n = 0; n < m_aBlast.length; n++) {
            m_aBlast[n].paint(g);
        }
        for (int n = 0; n < m_aEnemy.length; n++) {
            m_aEnemy[n].paint(g);
            m_aEnemy[n].m_Bullet.paint(g);
        }
        m_MyPlane.paint(g);
        m_MyPlane.m_Bullet.paint(g);
        if (m_bGameEnd) {
            Font mFont = g.getFont();
            StringBuffer temp = new StringBuffer();
            temp.append("��Ϸ�����ε÷֣�");
            temp.append(m_nScore);
            int nX = getWidth() / 2;
            int nY = getHeight() / 2 - mFont.getHeight();
            g.drawString(temp.toString(), nX, nY, Graphics.TOP | Graphics.HCENTER);
            temp = null;
            temp = new StringBuffer();
            temp.append("��ʷ��߷֣�");
            temp.append(m_nHighScore);
            nY = getHeight() / 2 + mFont.getHeight();
            g.drawString(temp.toString(), nX, nY, Graphics.TOP | Graphics.HCENTER);
        }
        flushGraphics();
    }

    public void commandAction(Command c, Displayable s) {
        if (c.getCommandType() == Command.EXIT) {
            midlet.notifyDestroyed();
        }
    }

    public void run() {
        long lTime1 = System.currentTimeMillis();
        long lTime2 = lTime1;
        try {
            while (true) {
                lTime2 = System.currentTimeMillis();;
                if (lTime2 - lTime1 > 50) {
                    lTime1 = lTime2;
                    Input();
                    Logic();
                    Paint();
                }
            }
        } catch (Exception e) {
        }
    }

    public void EndGame() {
        m_bGameEnd = true;
        if (m_nScore > m_nHighScore) {
            m_nHighScore = m_nScore;
            SaveHighScore(m_nHighScore);
        }
    }

    private void SaveHighScore(int nHighScore) {
        try {
            byte b[] = new byte[4];
            int temp = nHighScore;
            //��һ��ѭ����һ��int�����(4�ֽ�)�������b[]��
            for (int i = b.length - 1; i >= 0; i--) {
                b[i] = new Integer(temp & 0xff).byteValue();
                temp = temp >> 8;
            }
            RecordStore rs = RecordStore.openRecordStore("BattlePlane", true);
            if (rs.getNumRecords() > 0) {
                int nID = rs.getNextRecordID();
                rs.setRecord(nID, b, 0, b.length);
            } else {
                rs.addRecord(b, 0, b.length);
            }
        } catch (Exception ex) {
        }
    }

    private int LoadHighScore() {
        int nHighScore = 0;
        try {
            RecordStore rs = RecordStore.openRecordStore("BattlePlane", true);
            if (rs.getNumRecords() > 0) {
                int nID = rs.getNextRecordID();
                byte b[] = rs.getRecord(nID);
                if (b != null && b.length == 4) {
                    for (int i = 0; i < 3; i++) {
                        nHighScore += b[i];
                        if (b[i] < 0) {
                            m_nHighScore += 256 + b[i];
                        }
                        nHighScore *= 256;
                    }

                    nHighScore += b[3];
                    if (b[3] < 0) {
                        nHighScore += 256;
                    }
                }
            }
        } catch (Exception ex) {
        }
        return nHighScore;
    }

    private void CreateEnemy() {
        int RS = Math.abs(m_Random.nextInt() % 20);
        if (RS != 0) {
            return;
        }
        for (int n = 0; n < m_aEnemy.length; n++) {
            if (m_aEnemy[n].isVisible()) {
                continue;
            }
            int nX = m_Random.nextInt();
            nX = Math.abs(nX % (getWidth() - 10)) + 5;
            int nSpeedX = m_Random.nextInt() % 2;
            int nSpeedY = Math.abs(m_Random.nextInt() % 2) + 1;
            m_aEnemy[n].SetSpeed(nSpeedX, nSpeedY);
            m_aEnemy[n].setRefPixelPosition(nX, 0);
            m_aEnemy[n].setVisible(true);
            return;
        }
    }

    private void CreateBlast(int nX, int nY) {
        for (int n = 0; n < m_aBlast.length; n++) {
            if (m_aBlast[n].isVisible()) {
                continue;
            }
            m_aBlast[n].setRefPixelPosition(nX, nY);
            m_aBlast[n].setFrame(0);
            m_aBlast[n].setVisible(true);
            return;
        }
        m_aBlast[0].setRefPixelPosition(nX, nY);
        m_aBlast[0].setFrame(0);
        m_aBlast[0].setVisible(true);
    }

    private void CheckCollisions() {
        for (int n = 0; n < m_aEnemy.length; n++) {
            //�ҷ��ɻ�������ڵ���ײ
            if (m_MyPlane.collidesWith(m_aEnemy[n].m_Bullet, true)) {
                CreateBlast(m_MyPlane.getRefPixelX(),
                        m_MyPlane.getRefPixelY());
                m_aEnemy[n].m_Bullet.setVisible(false);
                EndGame();
                return;
            }
            //�ҷ��ڵ��������ײ
            if (m_MyPlane.m_Bullet.collidesWith(m_aEnemy[n], true)) {
                CreateBlast(m_aEnemy[n].getRefPixelX(),
                        m_aEnemy[n].getRefPixelY());
                m_nScore++;
                m_aEnemy[n].setVisible(false);
                m_MyPlane.m_Bullet.setVisible(false);

            }
        }
    }
}
