package hujkay.menu;

import hujkay.Block;
import java.util.Timer;
import javax.microedition.lcdui.*;

public class MenuCanvas extends Canvas implements CommandListener {

    //�˵�ͼƬ�Ŀ�ʼλ��
    private boolean fistloadpicture = true;
    private int picturex, picturey;
    //�������ҿհ׵ĵط�
    private int blankleft, blanktop;
    //��ǰ��ѡ��
    private int currentchoice;
    private boolean redpicture;
    //��ǰcanvas�Ĵ�С
    private int wide, height;
    //ͼƬ����
    private Image image[] = new Image[9];
    //��ǰ�Ƿ��Ծ
    private boolean active = true;
    //��������ɫ��ɣ�240,240,0��
    private boolean activeerr = false;
    //˫����
    private Image offcanvas;
    private Timer timer;
    private menuTask menuTask;
    private Command selectcommand = new Command("Select", Command.OK, 1);
    private Command exitcommand = new Command("Exit", Command.EXIT, 2);
    private Block block;

    public void restartnow() {
        active = true;
        activeerr = false;
        currentchoice = 0;
        redpicture = false;
        currentchoice = 0;
        redpicture = false;
    }
    //���캯��

    public MenuCanvas() {
        restartnow();
        timer = new Timer();
        menuTask = new menuTask(this);
        timer.schedule(menuTask, 400, 400);
        this.addCommand(selectcommand);
        addCommand(exitcommand);
        this.setCommandListener(this);
    }

    public MenuCanvas(Block b) {
        this.block = b;
        restartnow();
        timer = new Timer();
        menuTask = new menuTask(this);
        timer.schedule(menuTask, 400, 400);
        this.addCommand(selectcommand);
        addCommand(exitcommand);
        this.setCommandListener(this);
    }
//	��ȡͼƬ���

    private boolean readpictures() {
        if (fistloadpicture) {
            fistloadpicture = false;
            try {
                image[0] = Image.createImage("/block/menu/0.png");
                image[1] = Image.createImage("/block/menu/1.png");
                image[2] = Image.createImage("/block/menu/2.png");
                image[3] = Image.createImage("/block/menu/3.png");
                image[4] = Image.createImage("/block/menu/4.png");
                image[5] = Image.createImage("/block/menu/5.png");
                image[6] = Image.createImage("/block/menu/6.png");
                image[7] = Image.createImage("/block/menu/7.png");
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private void Initdata() {
        wide = this.getWidth();
        height = this.getHeight();
        if (wide > image[0].getWidth()) {
            blankleft = (wide - image[0].getWidth()) / 2;
            blanktop = (height - image[0].getHeight()) / 2;
        }
    }

    private void clearcanvas(Graphics g) {
        g.setColor(0, 240, 240);
        g.fillRect(0, 0, wide, height);
    }

    private void paintcanvas(Graphics g) {


        //�����ݺϷ����
        if (currentchoice > 3) {
            currentchoice = 0;
        }
        if (currentchoice < 0) {
            currentchoice = 3;
        }

        //��ͼ��ʹ������˫����

        Graphics gg = offcanvas.getGraphics();
        clearcanvas(gg);
        if (redpicture) {
            gg.drawImage(image[currentchoice * 2 + 1], blankleft, blanktop, Graphics.LEFT | Graphics.TOP);
            redpicture = false;
        } else {
            gg.drawImage(image[currentchoice * 2], blankleft, blanktop, Graphics.LEFT | Graphics.TOP);
            redpicture = true;
        }

        //���뵽��ǰ��Ļ
        g.drawImage(offcanvas, 0, 0, Graphics.LEFT | Graphics.TOP);
        /*
         * try { clearcanvas(g) ;
         * g.drawImage(image[7],0,0,Graphics.LEFT|Graphics.TOP); }
         * catch(Exception e) { }
         */


    }

    protected void paint(Graphics g) {
        // TODO Auto-generated method stub
        if (fistloadpicture) {
            readpictures();
            Initdata();
            offcanvas = Image.createImage(wide, height);
        }

        if (active) {
            if (!activeerr) {
                paintcanvas(g);
            } else {
                g.drawLine(0, 0, 100, 100);
            }
        } else {
            g.drawRect(0, 0, 100, 100);
        }


    }

    protected synchronized void keyPressed(int keyCode) {
        switch (this.getGameAction(keyCode)) {
            case Canvas.UP:
                currentchoice--;
                break;
            case Canvas.DOWN:
                currentchoice++;
                break;
        }
        redpicture = false;
        repaint();
    }

    //�ӿ�
    public void Hide() {
        this.active = false;
    }

    public boolean IsOK() {
        return activeerr;
    }

    public synchronized void commandAction(Command c, Displayable d) {
        if (c.getLabel().equals("Select")) {
            if (this.currentchoice == 0)//������Ϸ
            {
                block.mangeaction(1, 0);
            } else if (this.currentchoice == 1)//������¼
            {
                block.mangeaction(2, 0);
            } else if (this.currentchoice == 2)//��������
            {
                block.mangeaction(3, 0);
            } else if (this.currentchoice == 3)//�˳���Ϸ
            {
                block.mangeaction(4, 0);
            }


        } else if (c.getLabel().equals("Exit")) {
            block.mangeaction(4, 0);
        }
    }
}
