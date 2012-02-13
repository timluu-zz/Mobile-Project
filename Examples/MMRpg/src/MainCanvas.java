
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

public class MainCanvas extends GameCanvas
        implements Runnable, CommandListener {

    private LayerManager m_Manager;
    private Actor m_Actor;
    private Npc m_Npc;
    private Scene m_Scene;
    private boolean m_bGameEnd = false;
    private Command CommandExit;
    private MMRpgMidlet m_Midlet;
    private Player m_Player;////////////
    private MyUI m_UI;

    protected MainCanvas(MMRpgMidlet mMidlet) {
        super(true);
        m_Midlet = mMidlet;
        m_UI = new MyUI(getWidth(), getHeight());
        Thread t = new Thread(this);
        t.start();
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

    public void Start() {
        m_UI = null;
        try {
            Image image = Image.createImage("/MM.PNG");
            m_Actor = new Actor(image, 16, 32);
            m_Actor.defineCollisionRectangle(0, 16, 16, 16);
            m_Actor.defineReferencePixel(8, 24);
            image = Image.createImage("/NPC.png");
            m_Npc = new Npc(image, 16, 32);
            m_Npc.defineCollisionRectangle(0, 16, 16, 16);
            m_Npc.defineReferencePixel(8, 24);
        } catch (IOException e) {
        }
        m_Scene = new Scene();
        m_Manager = new LayerManager();
        m_Manager.append(m_Actor);
        m_Manager.append(m_Npc);
        m_Scene.AppendToManager(m_Manager);

        m_Scene.EnterScene(0, m_Actor, m_Npc);

        SetViewWindow();
        PlayWav();
        CommandExit = new Command("�˳�", Command.EXIT, 0);
        addCommand(CommandExit);
        setCommandListener(this);
    }

    public void Input() {
        int keyStates = getKeyStates();
        if (m_UI != null) {
            if (m_UI.Input(keyStates) == true) {
                Start();
            }
            return;
        }
        if (m_bGameEnd) {
            return;
        }

        if (m_Npc.m_bDialog) {
            return;
        }

        if (!m_Actor.Input(keyStates)) {
            return;
        }

        if (m_Scene.m_nCurIndex == 0
                && m_Actor.getRefPixelX() > 250
                && m_Actor.getRefPixelY() > 250) {
            m_bGameEnd = true;
            return;
        }
        CheckCollisions();
        SetViewWindow();
    }

    private void SetViewWindow() {
        int nX = m_Actor.getRefPixelX() - getWidth() / 2;
        int nY = m_Actor.getRefPixelY() - getHeight() / 2;
        if (nX < 0) {
            nX = 0;
        } else if (nX > m_Scene.GetWidth() - getWidth()) {
            nX = m_Scene.GetWidth() - getWidth();
        }
        if (nY < 0) {
            nY = 0;
        } else if (nY > m_Scene.GetHeight() - getHeight()) {
            nY = m_Scene.GetHeight() - getHeight();
        }

        m_Manager.setViewWindow(nX, nY, getWidth(), getHeight());
    }

    private void Logic() {
        if (m_UI != null) {
            return;
        }
        m_Npc.Logic();
    }

    private void Paint() {
        Graphics g = getGraphics();
        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (m_UI != null) {
            m_UI.Paint(g);
            flushGraphics();
            return;
        }
        m_Manager.paint(g, 0, 0);
        if (m_Npc.m_bDialog) {
            m_Npc.DrawText(g, getWidth(), getHeight());
        }
        if (m_bGameEnd) {
            g.setColor(0xffffff);
            g.drawString("MM�����߳��˶�Ѩ��", getWidth() / 2,
                    getHeight() / 2, Graphics.TOP | Graphics.HCENTER);
        }
        flushGraphics();
    }

    public void PlayWav() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Back.wav");
            m_Player = Manager.createPlayer(is, "audio/x-wav");
            m_Player.setLoopCount(-1);
            m_Player.start();
        } catch (Exception e) {
        }
    }

    public void CheckCollisions() {
        if (m_Actor.collidesWith(m_Npc, false)) {
            m_Actor.MoveBack();
            m_Npc.DialogStart();
            return;
        }
        m_Scene.CollidesWidth(m_Actor, m_Npc);
    }

    public void commandAction(Command arg0, Displayable arg1) {
        if (arg0.getCommandType() == Command.EXIT) {
            m_Midlet.notifyDestroyed();
        }
    }
}
