
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class MenuCanvas extends GameCanvas implements Runnable {

    private String[] menuItem = new String[6];
    private Rpg parent;
    private int sel;
    private Font font;
    private Image mback = null;
    private Image title = null;
    private Sprite chr = null;
    private Graphics vbuf = null;
    private byte state = 0;
    //  0: cg		1:	menu
    private static int[][] walkSeq = new int[4][2];

    static {
        walkSeq[0][0] = 0;
        walkSeq[0][1] = 1;
        walkSeq[1][0] = 2;
        walkSeq[1][1] = 3;
        walkSeq[2][0] = 4;
        walkSeq[2][1] = 5;
        walkSeq[3][0] = 6;
        walkSeq[3][1] = 7;
    }

    public MenuCanvas(Rpg p) {
        super(false);
        parent = p;
        sel = 0;
        font = Font.getDefaultFont();
        try {
            mback = Image.createImage("/res/mback.png");
            title = Image.createImage("/res/title.png");
            chr = new Sprite(Image.createImage("/res/chr.png"), 16, 24);
            vbuf = this.getGraphics();
        } catch (IOException ioe) {
        }
        menuItem[0] = "新游戏";
        menuItem[1] = "继续";
        menuItem[2] = "帮助";
        menuItem[3] = "退出";
    }

    public void paint(Graphics g) {
        if (state == 0) {
            flushGraphics();
        } else {
            g.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
            g.drawImage(title, 22, 80, Graphics.BOTTOM | Graphics.LEFT);
            chr.setPosition(74, 100 + sel * 25);
            chr.nextFrame();
            chr.paint(g);
            g.setColor(0x0f0f0f);
            for (int i = 0; i < 4; i++) {
                g.drawString(menuItem[i], 94, 100 + i * 25, Graphics.TOP | Graphics.LEFT);
            }
            g.setColor(244, 224, 34);
            for (int i = 0; i < 4; i++) {
                g.drawString(menuItem[i], 93, 101 + i * 25, Graphics.TOP | Graphics.LEFT);
            }
            g.setColor(0xff0000);
            g.drawString(" For NEC N840", 120, 220, Graphics.TOP | Graphics.LEFT);
        }
    }

    public void keyPressed(int keyCode) {
        if (state == 0) {
            return;
        }
        switch (keyCode) {
            case UP:
                if (sel > 0) {
                    sel -= 1;
                }
                repaint();
                break;
            case DOWN:
                if (sel < 3) {
                    sel += 1;
                }
                repaint();
                break;
            case FIRE:
                state = 3;
                if (sel == 0) {
                    parent.setState((byte) 7);// new
                }
                if (sel == 1) {
                    parent.setState((byte) 2);// continue
                }
                if (sel == 2) {
                    parent.setState((byte) 4);// help
                }
                if (sel == 3) {
                    parent.setState((byte) 6);// exit
                }
        }
    }

    public void run() {
        vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
        repaint();
        chr.setFrameSequence(walkSeq[3]);
        for (int i = 0; i < 15; i++) {
            vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
            chr.setPosition(240 - 6 * i, 50);
            chr.nextFrame();
            chr.paint(vbuf);
            repaint();
            pause(100);
        }
        chr.setFrameSequence(walkSeq[2]);
        vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
        chr.paint(vbuf);
        repaint();
        pause(700);
        chr.setFrameSequence(walkSeq[1]);
        vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
        chr.paint(vbuf);
        repaint();
        pause(700);
        chr.setFrameSequence(walkSeq[3]);
        vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
        chr.paint(vbuf);
        repaint();
        pause(700);
        chr.setFrameSequence(walkSeq[1]);
        for (int i = 0; i < 15; i++) {
            vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
            chr.setPosition(150 + 6 * i, 50);
            chr.nextFrame();
            chr.paint(vbuf);
            repaint();
            pause(20);
        }
        chr.setFrameSequence(walkSeq[3]);
        for (int i = 0; i < 40; i++) {
            vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
            chr.setPosition(240 - 6 * i, 50);
            chr.nextFrame();
            chr.paint(vbuf);
            vbuf.drawImage(title, 256 - 6 * i, 80, Graphics.BOTTOM | Graphics.LEFT);
            repaint();
            pause(50);
        }
        chr.setFrameSequence(walkSeq[2]);
        for (int i = 0; i < 10; i++) {
            vbuf.drawImage(mback, 0, 0, Graphics.TOP | Graphics.LEFT);
            vbuf.drawImage(title, 22, 80, Graphics.BOTTOM | Graphics.LEFT);
            chr.setPosition(6 + i * 7, 50 + i * 5);
            chr.nextFrame();
            chr.paint(vbuf);
            repaint();
            pause(50);
        }
        state = 1;
        repaint();
        int k;
        while (state == 1) {
            k = waitKeyRelease(this.UP_PRESSED | this.DOWN_PRESSED | this.FIRE_PRESSED);
            if ((k & UP_PRESSED) != 0) {
                keyPressed(UP);
            } else if ((k & DOWN_PRESSED) != 0) {
                keyPressed(DOWN);
            } else if ((k & FIRE_PRESSED) != 0) {
                keyPressed(FIRE);
            }
        }
        return;
    }

    private void pause(long nMillis) {
        try {
            Thread.currentThread().sleep(nMillis);
        } catch (InterruptedException ie) {
        }
    }

    private int waitKeyRelease(int keyCode) {
        int ok, ck;
        while ((ok = getKeyStates() & keyCode) != 0);
        while ((ok = getKeyStates() & keyCode) == 0);
        while (true) {
            ck = getKeyStates() & keyCode;
            if (ck < ok) {
                break;
            }
        }
        return ok;
    }
}
