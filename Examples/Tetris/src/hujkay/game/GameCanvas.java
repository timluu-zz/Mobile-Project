package hujkay.game;

import hujkay.Block;
import java.util.Timer;
import javax.microedition.lcdui.*;

public class GameCanvas extends Canvas implements CommandListener {
    //����ͼƬ��λ��

    private int backgroundx, backgroundy;
    //  16 ---- 96 
    //  17 ---- 157
    // 16----96   80/10=8
    ///16---158   (142)/18=8
    //˫����
    private Image offscreen;
    private int currentblockx, currentblocky;
    //��ȡ��Ļ��С
    private int wide, height;
    //��ǰ��״̬
    private int currentstate = GAME_START;
    private static int GAME_START = 0;
    private static int GAME_PAUSE = 1;
    private static int GAME_HIGHLIGHT = 2;
    private static int GAME_EXIT = 3;
    private static int GAME_ERR = 4;
    private static int GAME_OVER = 5;
    private Timer timer;
    private GameTask gametask;
    private Block block;
    //����
    Image imageground;
    //��ǰ�����Ӵ������//6 * 64
    private static int blocklibary[][] = {
        {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,},//����
        {1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},//22
        {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//22
        {0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},//��
    };
    private int currentblocklibx = 0, currentblockliby = 0;
    private int nextblocklibx = 0, nextblockliby = 0;
    private int currentblock[][] = new int[4][4];
    private int nextblock[][] = new int[4][4];
//	���ڴ������10 * 18
    private int chess[][] = new int[10][18];
    private int chesscolor[][] = new int[10][18];
    private int currentcolor = 0;
    private int nextcolor = 0;
    private boolean istimer = false;
    //����
    private int score = 0;
    private int scoreup[] = {1, 3, 6, 12, 20};
    private int level = 3;
    private int Timercount = 0;
    private int MAXCOLORCOUNT = 4;
    private int colorlib[][] = {{207, 51, 5},
        {0, 0, 255},
        {21, 96, 46},
        {118, 58, 10},};
    private Command exitcommand = new Command("Exit", Command.EXIT, 3);
    private Command pausecommand = new Command("Pause", Command.STOP, 1);
    private Command startcommand = new Command("Start", Command.OK, 1);

    public void updatecurrentblock() {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (this.blocklibary[currentblocklibx][ this.currentblockliby * 16 + j + i * 4] == 1) {
                    currentblock[i][j] = 1;
                } else {
                    currentblock[i][j] = 0;
                }
            }
        }

    }

    public int getscore() {
        return this.score;
    }

    public boolean canturn() {
        int i, j, next;

        next = (currentblockliby + 1) % 4;
        //�ж��Ƿ����
        //������
        if (currentblockx < 0)//����
        {
            for (i = 0; i < -this.currentblockx; i++) {
                for (j = 0; j < 4; j++) {
                    if (blocklibary[currentblocklibx][next * 16 + j + 4 * i] == 1) {
                        return false;
                    }
                }
            }
        }
        if (currentblockx + 3 > 9)//�Ҽ��
        {
            for (i = 10 - currentblockx; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    if (blocklibary[currentblocklibx][next * 16 + i * 4 + j] == 1) {
                        return false;
                    }
                }
            }
        }
        if (currentblocky + 3 > 17)//�¼��
        {
            for (i = 0; i < 4; i++) {
                for (j = 18 - currentblocky; j < 4; j++) {
                    if (blocklibary[currentblocklibx][next * 16 + +4 * i + j] == 1) {
                        return false;
                    }
                }
            }
        }
        //�ж��Ƿ��غ�

        for (j = 0; j < 4; j++) //��
        {
            for (i = 0; i < 4; i++)//��
            {
                if (this.currentblock[i][j] == 0) {
                    continue;
                }
                if (i + this.currentblockx < 0 || i + this.currentblockx > 9) {
                    continue;
                }
                if (j + next < 0 || j + next > 17) {
                    continue;
                }

                if (this.blocklibary[this.currentblocklibx][next * 16 + 4 * i + j] == 1 && chess[this.currentblockx + i][this.currentblocky + j] == 1) {
                    return false;
                }

            }
        }
        return true;
    }

    public void turn() {
        currentblockliby = (currentblockliby + 1) % 4;
        if (canturn()) {
            updatecurrentblock();
        }

        repaint();
    }

    public int convertx(int x) {
        return x * 8 + 16 + backgroundx;
    }

    public int converty(int y) {
        return y * 8 + backgroundy + 15;
    }

    public void clearCanvas(Graphics g) {
        g.setColor(0, 240, 240);
        g.fillRect(0, 0, wide, height);
    }

    public boolean readpictures() {
        try {
            imageground = Image.createImage("/block/game/background.png");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void startgame() {
        currentstate = this.GAME_START;
        repaint();
    }

    public void pausegame() {
        currentstate = this.GAME_PAUSE;
        repaint();
    }

    public void createnextblock() {
        long e = System.currentTimeMillis();
        this.nextblocklibx = (int) (e % 6);
        e = System.currentTimeMillis();
        this.nextblockliby = (int) (e % 4);
        this.nextcolor = (int) (System.currentTimeMillis() % MAXCOLORCOUNT);
    }

    public void restorepre() {
        int i, j;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                if (this.currentblock[j][i] == 1) {
                    this.chess[ i + this.currentblockx][ j + this.currentblocky] = 1;
                    this.chesscolor[i + this.currentblockx][j + this.currentblocky] = this.currentcolor;
                }
            }
        }
    }

    public void computescore() {
        int i, j, count = 0, ii, jj;
        for (j = 0; j < 18; j++) {
            for (i = 0; i < 10; i++) {
                if (chess[i][j] == 0) {
                    break;
                }
            }
            if (i == 10) {
                count++;
                //�������������ƶ�
                for (ii = 0; ii < 10; ii++) {
                    chess[ii][0] = 0;
                }
                for (jj = j; jj > 0; jj--) {
                    for (ii = 0; ii < 10; ii++) {
                        chess[ii][jj] = chess[ii][jj - 1];
                        this.chesscolor[ii][jj] = this.chesscolor[ii][jj - 1];
                    }
                }
            }
        }
        this.addscore(this.scoreup[count]);
    }

    public void drawlevel(Graphics g) {
        char Buffer[] = new char[10];
        if (level == 4) {
            Buffer[0] = 'E';
            Buffer[1] = 'A';
            Buffer[2] = 'S';
            Buffer[3] = 'Y';
            Buffer[4] = '\0';
        } else if (level == 2) {
            Buffer[0] = 'N';
            Buffer[1] = 'O';
            Buffer[2] = 'R';
            Buffer[3] = 'M';
            Buffer[4] = 'A';
            Buffer[5] = 'L';
            Buffer[6] = '\0';
        } else {
            Buffer[0] = 'H';
            Buffer[1] = 'A';
            Buffer[2] = 'R';
            Buffer[3] = 'D';
            Buffer[4] = '\0';
        }
        g.drawChars(Buffer, 0, 9, this.backgroundx + 125, this.backgroundy + 55, 0);

    }

    public void drawscore(Graphics g) {
        char Buffer[] = new char[7];
        if (score < 0) {
            score = 0;
        }
        if (score == 0) {
            Buffer[0] = '0';
            Buffer[1] = '\0';
        } else {
            int i = 0, tmp = score, c = 0;
            while (tmp != 0) {
                tmp = tmp / 10;
                c++;
            }

            tmp = score;
            i = c;
            Buffer[i] = '\0';
            while (tmp != 0) {
                Buffer[--i] = (char) (tmp % 10 + '0');
                tmp = tmp / 10;
            }
        }
        g.drawChars(Buffer, 0, 7, 124 + this.backgroundx, 33 + this.backgroundy, 0);
        adjustlevel();
        drawlevel(g);
    }

    public void adjustlevel() {
        if (score > 200) {
            level = 1;
        } else if (score > 100) {
            level = 2;
        } else {
            level = 4;
        }
    }

    public void drawnextblock(Graphics g) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (this.blocklibary[nextblocklibx][ this.nextblockliby * 16 + j + i * 4] == 1) {
                    nextblock[i][j] = 1;
                } else {
                    nextblock[i][j] = 0;
                }
            }
        }
        g.setColor(this.colorlib[this.nextcolor][0], this.colorlib[this.nextcolor][1], this.colorlib[this.nextcolor][2]);
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (nextblock[i][j] == 1) {
                    g.fillRect(convertx(j) + 112, converty(i) + 80, 7, 7);
                }
            }
        }
    }

    public void getnextblock() {

        //System.out.println("���?ʼ1��");
        restorepre();
        computescore();
        this.currentblocklibx = this.nextblocklibx;
        this.currentblockliby = this.nextblockliby;
        this.currentblockx = 3;
        this.currentblocky = 0;
        this.currentcolor = this.nextcolor;
        createnextblock();
        this.updatecurrentblock();

        //����Ƿ���Ϸ����
        //System.out.println("���?ʼ2��");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //System.out.println("No :" + i + " : " + j);
                //	System.out.println("Now :" + (this.currentblockx + j )+ " : " + (this.currentblocky + i ));
                if (chess[j + this.currentblockx][i + this.currentblocky] == 1 && this.currentblock[i][j] == 1) {
                    this.currentstate = this.GAME_OVER;
                }
            }
        }
        //if( currentstate == GAME_OVER)
        //this.print(this.currentblock, 4);
        //System.out.println("������ɣ�" + this.currentstate);

        repaint();
    }

    public void addscore(int number) {
        score += number;
        repaint();
    }

    public void left() {
        int tempx, tempy;
        int i, j;
        tempx = this.currentblockx - 1;
        tempy = this.currentblocky;

        //������û�г�����ߵı߽�

        if (tempx < 0) {
            int outcount = -tempx - 1;//��������һ�в���
            for (j = 0; j < 4; j++) {
                if (this.currentblock[j][outcount] == 1) {
                    return;
                }
            }
        }

        //����û�г���Ļ��ͼ����û�к����������

        for (j = 0; j < 4; j++) //|
        {
            for (i = 0; i < 4; i++)//-
            {
                if (this.currentblock[j][i] == 0) {
                    continue;
                }
                if (tempx + i > 9 || tempx + i < 0) {
                    continue;
                }
                if (tempy + j > 17 || tempy + j < 0) {
                    continue;
                }
                if (chess[tempx + i][tempy + j] == 1) {
                    return;
                }
            }
        }

        //�������û��ʲô����Ļ����Ϳ�ʼ�����ƶ�
        this.currentblockx--;
        repaint();

    }

    public void right() {
        int tempx, tempy;
        int i, j;
        tempx = this.currentblockx + 1;
        tempy = this.currentblocky;

        //������û�г����ұߵı߽�

        if (tempx + 3 > 9) {
            int outcount = 10 - tempx;//��������һ�в���
            for (j = 0; j < 4; j++) {
                if (this.currentblock[j][outcount] == 1) {
                    return;
                }
            }

        }

        //����û�г���Ļ��ͼ����û�к����������

        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                if (this.currentblock[j][i] == 0) {
                    continue;
                }
                if (tempx + i > 9 || tempx + i < 0) {
                    continue;
                }
                if (tempy + j > 17 || tempy + j < 0) {
                    continue;
                }

                if (chess[tempx + i][tempy + j] == 1) {
                    return;
                }
            }
        }

        //�������û��ʲô����Ļ����Ϳ�ʼ�����ƶ�
        this.currentblockx++;
        repaint();
    }

    public void Timerrun() {
        if (this.currentstate != this.GAME_START) {
            return;
        }
        Timercount = (Timercount + 1) % level;
        if (Timercount == 0) {
            if (!down()) {
                this.getnextblock();
            }
        }
    }

    public boolean down() {
        int tempx, tempy;
        int i, j;
        tempx = this.currentblockx;
        tempy = this.currentblocky + 1;

        //������û�г����±ߵı߽�

        if (tempy + 3 > 17) {
            int outcount = 18 - tempy;//��������һ�в���
            for (j = 0; j < 4; j++) {
                if (this.currentblock[outcount][j] == 1) {
                    return false;
                }
            }

        }

        //����û�г���Ļ��ͼ����û�к����������

        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                if (this.currentblock[j][i] == 0) {
                    continue;
                }
                if (tempx + i > 9 || tempx + i < 0) {
                    continue;
                }
                if (tempy + j > 17 || tempy + j < 0) {
                    continue;
                }

                if (chess[tempx + i][tempy + j] == 1) {
                    return false;
                }
            }
        }

        //�������û��ʲô����Ļ����Ϳ�ʼ�����ƶ�
        this.currentblocky++;

        repaint();
        return true;
    }

    public GameCanvas() {



        //��ȡͼƬ���
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 18; j++) {
                chess[i][j] = 0;
            }
        }
        if (!readpictures()) {
            currentstate = this.GAME_ERR;
            return;
        }
        //��ʼ�����
        wide = this.getWidth();
        height = this.getHeight();

        if (wide > imageground.getWidth()) {
            backgroundx = (wide - imageground.getWidth()) / 2;
            backgroundy = (height - imageground.getHeight()) / 2;
        } else {
            backgroundx = backgroundy = 0;
        }

        offscreen = Image.createImage(wide, height);
        //������һ������
        this.createnextblock();
        this.getnextblock();
//		��ʼ����ǰ�ķ��飬���Ҳ�����һ������
        this.updatecurrentblock();

        //test	

        timer = new Timer();
        gametask = new GameTask(this);
        timer.schedule(gametask, 300, 300);

        this.addCommand(pausecommand);
        this.addCommand(exitcommand);

        this.setCommandListener(this);

    }

    public GameCanvas(Block block) {
        this.block = block;
//		��ȡͼƬ���
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 18; j++) {
                chess[i][j] = 0;
            }
        }
        if (!readpictures()) {
            currentstate = this.GAME_ERR;
            return;
        }
        //��ʼ�����
        wide = this.getWidth();
        height = this.getHeight();

        if (wide > imageground.getWidth()) {
            backgroundx = (wide - imageground.getWidth()) / 2;
            backgroundy = (height - imageground.getHeight()) / 2;
        } else {
            backgroundx = backgroundy = 0;
        }

        offscreen = Image.createImage(wide, height);
        //������һ������
        this.createnextblock();
        this.getnextblock();
//		��ʼ����ǰ�ķ��飬���Ҳ�����һ������
        this.updatecurrentblock();

        //test	

        timer = new Timer();
        gametask = new GameTask(this);
        timer.schedule(gametask, 300, 300);

        this.addCommand(pausecommand);
        this.addCommand(exitcommand);

        this.setCommandListener(this);
    }

    public void gamestart(Graphics g) {
        int i, j;

        Graphics gg = offscreen.getGraphics();
        this.clearCanvas(gg);
        gg.drawImage(imageground, backgroundx, backgroundy, 0);
        //gg.setColor(175,75,168);
        gg.setColor(this.colorlib[this.currentcolor][0], this.colorlib[this.currentcolor][1], this.colorlib[this.currentcolor][2]);

        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (this.currentblock[i][j] == 1) {
                    gg.fillRect(convertx(this.currentblockx + j), converty(this.currentblocky + i), 7, 7);
                }
            }
        }
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 18; j++) {
                if (chess[i][j] == 1) {
                    gg.setColor(this.colorlib[this.chesscolor[i][j]][0], this.colorlib[this.chesscolor[i][j]][1], this.colorlib[this.chesscolor[i][j]][2]);
                    gg.fillRect(convertx(i), converty(j), 7, 7);
                }
            }
        }

        drawscore(gg);
        drawnextblock(gg);

        g.drawImage(offscreen, 0, 0, 0);
    }

    protected void paint(Graphics g) {
        // TODO Auto-generated method stub
        switch (currentstate) {
            case 0:  // GAME_START
                gamestart(g);
                break;
            case 1://GAME_PAUSE:
                break;
            case 2://GAME_HIGHLIGHT:
                break;

            case 3://GAME_EXIT:
                break;
            case 4://GAME_ERR:
                break;
            case 5: //GAME_OVER
                char Buffer[] = {'G', 'a', 'm', 'e', ' ', 'O', 'v', 'e', 'r', '!'};
                g.drawChars(Buffer, 0, 9, wide / 2 - 20, height / 2, 0);
                this.removeCommand(pausecommand);
        }
    }

    protected synchronized void keyPressed(int keyCode) {
        int i;
        if (this.currentstate != GAME_START) {
            return;
        }
        switch (this.getGameAction(keyCode)) {
            case Canvas.LEFT:
                left();
                break;
            case Canvas.RIGHT:
                right();
                break;
            case Canvas.UP:
                turn();
                break;
            case Canvas.DOWN:
                while (true) {
                    if (!down()) {
                        break;
                    }
                }
                this.getnextblock();


        }


        //System.out.print(this.currentblockx + ":" + this.currentblocky);
        ///System.out.println("AND "+this.currentblocklibx+":"+this.currentblockliby);
    }

    public void pause() {
        this.currentstate = this.GAME_PAUSE;
    }

    public void start() {
        this.currentstate = this.GAME_START;
    }

    public void commandAction(Command c, Displayable d) {
        if (c.getLabel().equals("Start")) {
            this.removeCommand(startcommand);
            this.addCommand(pausecommand);
            this.start();
            repaint();
        } else if (c.getLabel().equals("Pause")) {
            this.pause();
            this.removeCommand(pausecommand);
            this.addCommand(startcommand);
        } else if (c.getLabel().equals("Exit")) {
            block.mangeaction(2, 1);
        }
    }
}
