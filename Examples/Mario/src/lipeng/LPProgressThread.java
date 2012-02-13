/**
 * <p>Title: lipeng</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.0.3
 *
 * <p> Revise History
 *
 * 2004.07.15 In MobilePhone the progress time too small change to 300 v1.0.1
 *
 * 2004.07.15 Change thread's priority to min_priority V1.0.2
 *
 * 2004.07.21 Change some public member to private V1.0.3 </p>
 */
package lipeng;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class LPProgressThread extends Canvas
        implements Runnable {

    public LPProgressThread(MIDlet midlet) {
        //setFullScreenMode(true);
        loadProgressIsOver = false;
        this.midlet = midlet;
        this.width = this.getWidth();
        this.height = this.getHeight();
    }

    public void paint(Graphics g) {
        drawGaugeScreen(g);
    }

    private void drawGaugeScreen(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, width, height);

        g.setColor(255, 0, 0);
        g.drawString("��Ϸװ����...", (width - font.stringWidth("��Ϸװ����...")) / 2 + 10,
                height / 2 - 25, g.LEFT | g.TOP);

        g.setColor(255, 0, 0);
        g.drawRect(10, height / 2, width - 20, 20);
        g.setColor(0, 0, 255);
        g.fillRect(10 + 1, height / 2 + 1, gaugeCnt * (width - 20) / PROGRESS_END - 1, 20 - 1);

        g.setColor(255, 0, 0);

        g.drawString(String.valueOf(gaugeCnt * 100 / PROGRESS_END), (width - font.stringWidth("000")) / 2,
                height / 2 + 4, g.LEFT | g.TOP);
        g.drawString("%", (width - font.stringWidth("000")) / 2 + font.stringWidth("00"),
                height / 2 + 4, g.LEFT | g.TOP);

    }

    public void run() {
        try {
            Thread currentThread = Thread.currentThread();
            while (currentThread == progressThread) {
                startTime = System.currentTimeMillis();
                repaint(0, 0, width, height);
                serviceRepaints();
                gaugeCnt += progressInterval;
                if (((LPIGetCanvas) midlet).getCanvas() != null) {
                    progressInterval += 1;
                }
                if (gaugeCnt >= PROGRESS_END) {
                    if (((LPIGetCanvas) midlet).getCanvas() == null) {
                        gaugeCnt -= progressInterval;
                    } else {
                        loadProgressIsOver = true;
                        Display.getDisplay(midlet).setCurrent(((LPIGetCanvas) midlet).getCanvas());
                        ((LPIGameStart) ((LPIGetCanvas) midlet).getCanvas()).start();
                        break;
                    }
                }
                endTime = System.currentTimeMillis();
                if ((endTime - startTime) < FRAME_TIME) {
                    Thread.sleep(FRAME_TIME - (endTime - startTime));
                }
            }
        } catch (InterruptedException ie) {
            System.out.println(ie.toString());
        }
    }

    public void start() {
        progressThread = new Thread(this);
        progressThread.setPriority(Thread.MIN_PRIORITY + 1);
        progressThread.start();
    }

    public void stop() {
        progressThread = null;
    }
    public boolean loadProgressIsOver = false;
    private volatile Thread progressThread = null;
    private int width;
    private int height;
    private long startTime;
    private long endTime;
    private static final int FRAME_TIME = 200;
    private Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
            Font.SIZE_LARGE);
    public int gaugeCnt;
    private int progressInterval = 1;
    private static final int PROGRESS_END = 150;
    private MIDlet midlet;
}
