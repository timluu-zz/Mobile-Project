package hujkay;

import hujkay.game.GameCanvas;
import hujkay.help.HelpCanvas;
import hujkay.menu.MenuCanvas;
import hujkay.record.RecordCanvas;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Block extends MIDlet {

    //����������е�״̬
    private Display display;
    private static Block block;
    ///////////////////////
    private RecordCanvas recordcanvas = null;
    private GameCanvas gamecanvas = null;
    private MenuCanvas menucanvas = null;
    private HelpCanvas helpcanvas = null;

    public Block() {
        display = Display.getDisplay(this);
        block = this;
        menucanvas = new MenuCanvas(this);
        display.setCurrent(menucanvas);

    }

    protected void startApp() throws MIDletStateChangeException {
        // TODO Auto-generated method stub
    }

    protected void pauseApp() {
        // TODO Auto-generated method stub
    }

    protected void destroyApp(boolean arg0)/*
     * throws MIDletStateChangeException
     */ {
        // TODO Auto-generated method stub
    }

    public void ErrProcess() {
        destroyApp(false);
        this.notifyDestroyed();
    }

    public void start0(int source) {
        //���������˵�
		/*
         *  * source��?���¼�����Դ 0 �˵� 1 ��Ϸ 2 ��¼ 3 ����
         *
         */
        /*
         * private RecordCanvas recordcanvas = null; private GameCanvas
         * gamecanvas = null; private MenuCanvas menucanvas = null; private
         * HelpCanvas helpcanvas = null;
         */
        /*
         *
         * if(menucanvas != null) menucanvas = null ; if( helpcanvas != null)
         * helpcanvas = null ; if( gamecanvas != null) gamecanvas = null ; if(
         * recordcanvas != null) recordcanvas = null ;
         */
        menucanvas = new MenuCanvas(this);
        display.setCurrent(menucanvas);
        if (helpcanvas != null) {
            helpcanvas = null;
        }
        if (gamecanvas != null) {
            gamecanvas = null;
        }
        if (recordcanvas != null) {
            recordcanvas = null;
        }

    }

    public void start1(int source) {
        /*
         * 0 �����˵� 1 ������Ϸ 2 ������¼ 3 �������� 4	�˳���Ϸ
         *
         * source��?���¼�����Դ 0 �˵� 1 ��Ϸ 2 ��¼ 3 ����
         */
        gamecanvas = new GameCanvas(this);
        display.setCurrent(gamecanvas);
        if (menucanvas != null) {
            menucanvas = null;
        }
        if (helpcanvas != null) {
            helpcanvas = null;
        }
        if (recordcanvas != null) {
            recordcanvas = null;
        }
    }

    public void start2(int source) {
        /*
         * 0 �����˵� 1 ������Ϸ 2 ������¼ 3 �������� 4	�˳���Ϸ
         *
         * source��?���¼�����Դ 0 �˵� 1 ��Ϸ 2 ��¼ 3 ����
         */
        if (source == 0) {
            recordcanvas = new RecordCanvas(this, 0);
        } else {
            recordcanvas = new RecordCanvas(this, gamecanvas.getscore());
        }
        display.setCurrent(recordcanvas);
        if (menucanvas != null) {
            menucanvas = null;
        }
        if (helpcanvas != null) {
            helpcanvas = null;
        }
        if (gamecanvas != null) {
            gamecanvas = null;
        }
        if (recordcanvas != null) {
            recordcanvas = null;
        }
    }

    public void start3(int source) {
        /*
         * 0 �����˵� 1 ������Ϸ 2 ������¼ 3 �������� 4	�˳���Ϸ
         *
         * source��?���¼�����Դ 0 �˵� 1 ��Ϸ 2 ��¼ 3 ����
         */
        helpcanvas = new HelpCanvas(this);
        display.setCurrent(helpcanvas);
        if (menucanvas != null) {
            menucanvas = null;
        }
        if (gamecanvas != null) {
            gamecanvas = null;
        }
        if (recordcanvas != null) {
            recordcanvas = null;
        }
    }

    public void start4(int source) {
        /*
         * 0 �����˵� 1 ������Ϸ 2 ������¼ 3 �������� 4	�˳���Ϸ
         *
         * source��?���¼�����Դ 0 �˵� 1 ��Ϸ 2 ��¼ 3 ����
         */
        this.destroyApp(false);
        this.notifyDestroyed();
    }

    public void mangeaction(int event, int source) {
        //event ����¼�
		/*
         * 0 �����˵� 1 ������Ϸ 2 ������¼ 3 �������� 4	�˳���Ϸ
         *
         * source��?���¼�����Դ 0 �˵� 1 ��Ϸ 2 ��¼ 3 ����
         */
        switch (event) {
            case 0:  //�����˵�menu
                start0(source);
                break;
            case 1: //������Ϸgame
                start1(source);
                break;
            case 2: // ������¼record
                start2(source);
                break;
            case 3://��������
                start3(source);
                break;
            case 4://�˳���Ϸ
                start4(source);
                break;
        }
    }
}
