
import javax.microedition.lcdui.*;

public class MenuScreen extends Canvas implements Runnable, CommandListener {

    Font lowfont = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    Font highfont = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    int lowColor = 0x000000FF;
    int highColor = 0x00FF0000;
    int highBGColor = 0x00CCCCCC;
    int width;
    boolean co;
    int height;
    int startHeight;
    int spacing = highfont.getHeight() / 2;
    public static String[] mainmenu = {"����Ϸ", "����", "����"};
    int menuIndex;
    Thread menuThread;
    private Command ok = new Command("ok", Command.OK, 1);
    private lzhhdm midlet;

    public MenuScreen(lzhhdm midlet) {
        this.midlet = midlet;
        width = getWidth();
        height = getHeight();

        startHeight = (highfont.getHeight() * mainmenu.length) + ((mainmenu.length - 1) * spacing);
        startHeight = (height - startHeight) / 2;
        menuIndex = 0;
        addCommand(ok);
        setCommandListener(this);
        menuThread = new Thread(this);
        menuThread.start();
        co = true;
    }

    public void run() {
        while (co) {
            repaint();
        }
    }

    public void paint(Graphics g) {
        g.setColor(0x00FFFFFF);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < mainmenu.length; i++) {
            if (i == menuIndex) {
                g.setColor(highBGColor);
                g.fillRect(0, startHeight + (i * highfont.getHeight()) + spacing, width, highfont.getHeight());
                g.setFont(highfont);
                g.setColor(highColor);
                g.drawString(mainmenu[i], (width - highfont.stringWidth(mainmenu[i])) / 2, startHeight + (i * highfont.getHeight()) + spacing, 20);
            } else {
                g.setFont(lowfont);
                g.setColor(lowColor);
                g.drawString(mainmenu[i], (width - lowfont.stringWidth(mainmenu[i])) / 2, startHeight + (i * highfont.getHeight()) + spacing, 20);
            }
        }
    }

    public void keyPressed(int code) {
        if (getGameAction(code) == Canvas.UP && menuIndex - 1 >= 0) {
            menuIndex--;
        } else if (getGameAction(code) == Canvas.DOWN && menuIndex + 1 < mainmenu.length) {
            menuIndex++;
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == ok) {
            switch (menuIndex) {
                case 0:
                    co = false;
                    midlet.gameShow();

                    break;
                case 1:
                    midlet.helShow();
                    break;
                case 2:
                    midlet.renwuShow();
                    break;

            }
        }
    }
}
