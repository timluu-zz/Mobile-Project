// RubikMIDlet.java
// ace@cttgd.com 
// 2005.02.05
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class RubikMIDlet extends MIDlet implements CommandListener {

    private Command cmdExit;
    private Command cmdMenu;
    private Command cmdBack;
    private Command cmd2D;
    private Command cmd3D;
    private RubikCanvas canvas;

    public RubikMIDlet() {
        cmdExit = new Command("�Ƴ�", Command.SCREEN, 2);
        cmdMenu = new Command("MENU", Command.SCREEN, 2);
        cmdBack = new Command("����", Command.SCREEN, 2);
        cmd2D = new Command("2�η�", Command.SCREEN, 2);
        cmd3D = new Command("3�η�", Command.SCREEN, 2);

        canvas = new RubikCanvas();
        canvas.addCommand(cmdMenu);
        canvas.setCommandListener(this);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable s) {
        if (c == cmdMenu) {
            RubikMenu();
        }
        if (c == cmdBack) {
            Display.getDisplay(this).setCurrent(canvas);
        }
        if (c == List.SELECT_COMMAND) {
            List tmp = (List) s;
            switch (tmp.getSelectedIndex()) {
                case 0:
                    RubikHelp();
                    break;
                case 1:
                    notifyDestroyed();
                    break;
                case 2:
                    canvas.ColorRandom();
                    Display.getDisplay(this).setCurrent(canvas);
                    break;
                case 3:
                    canvas.ColorInit();
                    Display.getDisplay(this).setCurrent(canvas);
                    break;
                case 4:
                    RubikAbout();
                    break;
            }
        }
    }

    public void RubikMenu() {
        List l = new List("MENU", Choice.IMPLICIT);
        l.append("Help��", null);
        l.append("�˳�", null);
        l.append("Replay", null);
        l.append("����", null);
        l.append("About��", null);
        l.setCommandListener(this);
        l.addCommand(cmdBack);
        Display.getDisplay(this).setCurrent(l);
        return;
    }

    public void RubikHelp() {
        Form f = new Form("Help (����˵��)");
        f.addCommand(cmdBack);
        f.setCommandListener(this);
        f.append(" [��][��][��][��]�ӵ�ĸı�\n");
        f.append(" [1]��+ [2]��- [3]��- \n");
        f.append(" [4]��- [5]��+ [6]��+ \n");
        f.append(" [7]��+ [8]ǰ+ [9]��- \n");
        f.append(" [*]��- [0]ǰ- [#]��+ \n");
        Display.getDisplay(this).setCurrent(f);
        return;
    }

    public void RubikAbout() {
        Form f = new Form("About...");
        f.addCommand(cmdMenu);
        f.setCommandListener(this);
        f.append(" Rubik \n");
        f.append("API: \n eMail: ace@cttgd.com \n");
        f.append("HomePage: http://ace.gzrail.net \n");
        f.append("Code: 2005 \n");
        f.append("\n!!!ף����ɹ��⿪��!!! \n");
        Display.getDisplay(this).setCurrent(f);
        return;
    }

    public void startApp() {
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
