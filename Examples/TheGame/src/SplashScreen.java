
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author personal
 */
public class SplashScreen extends Canvas implements Runnable {

    private GameMIDlet host;
    Graphics g;

    public SplashScreen(GameMIDlet host) {
        this.host = host;
        new Thread(this).start();
        setFullScreenMode(true);
    }

    protected void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();



        g.setColor(255, 0, 0);
        g.fillRect(0, 0, width, height);

        g.setColor(255, 255, 255);
        g.setStrokeStyle(Graphics.DOTTED);

        g.drawRect(5, 5, width - 10, height - 10);

        g.setColor(255, 255, 0);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_LARGE));
        g.drawString("-= THE GAME =-", width / 2, height / 2, Graphics.BOTTOM | Graphics.HCENTER);



    }

    public void run() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();
    }

    private void dismiss() {
        if (isShown()) {
            host.showMainScreen();
        }
    }
}
