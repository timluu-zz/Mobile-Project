
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
//import javax.microedition.midlet.*;

public class lzhhdm extends MIDlet implements CommandListener {

    public Display display;
    private Image splashLogo;
    private boolean isSplash = true;
    public Form a;
    private Alert alert;
    int length;
    private MenuScreen menuscreen;
    private gameScreen gamescreen;
    private Command ok, back;
    private byte[] byteInputData;

    public lzhhdm() {
    }

    protected void startApp() throws MIDletStateChangeException {
        display = Display.getDisplay(this);
        //gamescreen=new gameScreen(this);
        menuscreen = new MenuScreen(this);
        if (isSplash) {
            System.gc();
            a = null;
            a = new Form("Vertion 1.0");
            ok = new Command("ok", Command.OK, 1);
            a.append(new StringItem(null, "1937 Gioi thieu......"));
            a.addCommand(ok);
            a.setCommandListener(this);
            display.setCurrent(a);
        }
    }

    protected void menuscreenShow() {
        display.setCurrent(menuscreen);
    }

    protected void menuscreensecond() {

        menuscreen = new MenuScreen(this);
        MenuScreen.mainmenu[0] = "���¿�ʼ";//����һ�����˺�����һ�� �˵���һ���Ϊ ���¿�ʼ
        display.setCurrent(menuscreen);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
    }

    protected void helShow() {
        System.gc();
        a = null;
        a = new Form("���ŷ��V1.0");
        back = new Command("����", Command.BACK, 1);
        a.append(new StringItem(null, "������ʽ���� 2 �� 8 �� 4 �� 6 ���� 5"));
        a.append(new StringItem(null, "��ҩ��һ����������"));
        a.addCommand(ok);
        a.setCommandListener(this);
        display.setCurrent(a);
    }

    protected void renwuShow() {
        System.gc();
        a = null;
        a = new Form("���ŷ��V1.0");
        back = new Command("����", Command.BACK, 1);
        a.append(new StringItem(null, "��Ϸ��ƣ����ŷ��"));
        a.append(new StringItem(null, "�汾�ţ�V1.00"));
        a.append(new StringItem(null, "�����ߣ���Ϣ���Ӽ���ѧԺ01�����5�� ���� ѧ�ţ�7"));
        a.addCommand(ok);
        a.setCommandListener(this);
        display.setCurrent(a);
    }

    protected void gameShow() {
        try {
            System.gc();
            gamescreen = null;
            gamescreen = new gameScreen(this);

            gamescreen.start();

            display.setCurrent(gamescreen);
            gamescreen.conti = true;
        } catch (Exception exp) {
            System.out.println("dfg");
        }
    }

    public void commandAction(Command arg0, Displayable arg1) {
        a = null;
        System.gc();
        this.menuscreenShow();
    }
}
