
//�������� 2005-4-25
//��Ϸ����
//�������������ײ������������������������������������������������������������������������������������������
//��������������������������������������������������������������������������������������������
//��������������������������������������������������������������
import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

public class gameScreen extends GameCanvas implements Runnable, CommandListener {

    private Form al;
    public LayerManager lm, lm1;
    TiledLayer b1;
    int height = getHeight();
    int bosscolor = 0;
    int sbosscolor = 0;
    int sbz = 0;//СBOSS AI��ر�־λ
    int sbmove = 0;//СBOSS�ƶ���־λ��0����1��2��3��4��
    int sfire = 0;//SBOOS����
    int smovebz = 0;//SBOSS�ƶ���־
    int sbpzbz = 0;//SBOOS������ӵ���ײ��־
    int slife = 0;//sboss����
    int sbo = 0;//sboss�ͷɻ���ײ����ҷɻ��Զ����ֵı�־λ
    int drawslife = 0;//��SBOSS�����־λ
    int lr = 0;
    int drawadd = 0;//����һ���ɻ����־  
    int pzbz = 0;//��״��ǣ������;���ҵ������ײBUG
    int pzbzover = 0;//�����غ�ĵл��� ��Ȼ��������	
    int playlife = -1;//������������־λ
    int bosslife = 0;//bosslife
    int inputno = 0;//���������־λ
    int position = 0;
    int j1b = 0;//CASE3�����J1���и����������ӵ�
    int jbsz = -1;//boss�ӵ���־
    int sbsz0 = -1;//sboss���ӵ���־
    int sbsz1 = -1;//sboss���ӵ�
    int sbsz2 = -1;//SBOSS����
    int sbsz3 = -1;//SBOSS���ӵ�
    int planepo;//�ɻ��·��������
    int planepoup;//�ɻ��Ϸ��������
    int kkk;
    int gz = 0;//�ϸ��ٱ�־λ��
    int gzks = 0;//���ٿ�ʼ��־λ
    int jiangli = 0;//�����־λ
    int jplaneno;
    int cloundno = 0;
    int right = 0;//BOSS�ƶ���־λ,��ʼ�������ƶ�
    int left = 1;
    int boss = 0;
    int over = 0;
    int ai = 0;
    int planert = -1;//��ʾ�Ƿ���λ�ᶼû��
    int supermen = 0;//��ҹҺ�����޵�
    int overcmd = 0;
    int bossover = 0;
    int jpb = -12;//�����ӵ��Ƿ����б�־λ
    int aipp = 0;
    int jbz = 0;
    int cloudposition;
    Random aik = new Random();
    Random aip = new Random();
    Random aicloud = new Random();
    int jb[] = new int[5];//���˷ɻ���Ƴ��ֱ�־
    int playerno = 3;//���ʣ��ɻ��־��3��2��1��Σ�0���ǹ���
    private mybullets[] huokebullet = new mybullets[9];
    private Sprite jbullet0, jbullet1, jbullet2, bossbullet0, bossbullet1, bossbullet2;
    private Sprite cloud[] = new Sprite[5];//���Ʋ�
    private MenuScreen ms;
    private Sprite c1, sboss, cboss, j0, j1, j2, boss1;  //2DʱΪ��ҷɻ�
    private lzhhdm midlet;
    int s1 = 0;//�޵�ʱ��1
    int s2 = 65;//�޵�ʱ��2
    int s3 = 0;//�޵�ʱ��3
    int row2;
    int row;
    int planecolor = 0;//�Լ��ķɻ��޵е�ʱ��ߺ�
    int planecoco = 0;//������
    public int by1;
    public int y1;

    public gameScreen(lzhhdm midlet) {
        super(true);
        System.gc();
        this.midlet = midlet;
        addCommand(new Command("��ͣ", Command.BACK, 1));
        setCommandListener(this);
        lm = new LayerManager();
        c1 = new Sprite(img("/pic/MyPlaneFrames.png"), 24, 24);//,getWidth(),getHeight()+1000);
        cboss = new Sprite(img("/pic/boss.png"), 65, 50);//�� *��
        jbullet0 = new Sprite(img("/pic/bullet.png"), 6, 6);
        jbullet1 = new Sprite(img("/pic/bullet.png"), 6, 6);
        jbullet2 = new Sprite(img("/pic/bullet.png"), 6, 6);
        bossbullet0 = new Sprite(img("/pic/bullet.png"), 6, 6);
        bossbullet1 = new Sprite(img("/pic/bullet.png"), 6, 6);
        bossbullet2 = new Sprite(img("/pic/bullet.png"), 6, 6);
        sboss = new Sprite(img("/pic/smallboss.png"), 65, 50);
        b1 = createBackGround();//��������
        c1.setPosition(getWidth() / 2, row2 + getHeight() - 25);//�������ʼλ��row2+getHeight()-25=1655
        //������ �������꣬�������Y1�ǻ���Ļ��λ��
        //System.out.println("ffffffffffff");
        planepoup = row2;//��Ļ�Ϸ��߽�
        planepo = row2 + getHeight();//��Ļ�·��߽� 
        j0 = new Sprite(img("/pic/jplane2.png"), 24, 22);//��24����22
        j1 = new Sprite(img("/pic/jplane2.png"), 24, 22);
        j2 = new Sprite(img("/pic/jplane2.png"), 24, 22);
        kkk = getHeight() / 8;

        cboss.setVisible(false);

        //aipp=3;
        ai = aik.nextInt() % 4;
        if (ai < 0) {
            ai = -ai;
        }
        aipp = aip.nextInt() % 3;
        if (aipp == 0)//��̬ȷ����һ��л�λ�õĲ���
        {
            aipp = aip.nextInt() % 3;
        }
        try {
            for (int i = 0; i <= 4; i++) {
                cloud[i] = new Sprite(img("/pic/cloud1.png"), 16, 16);
                lm.append(cloud[i]);
            }
        } catch (Exception e) {
            System.out.println("cloud");
        }
        lm.append(cboss);
        lm.append(j0);
        lm.append(j1);
        lm.append(j2);
        lm.append(bossbullet0);
        lm.append(bossbullet1);
        lm.append(bossbullet2);
        lm.append(jbullet0);
        lm.append(jbullet1);
        lm.append(jbullet2);
        lm.append(sboss);
        jb[0] = 1;
        jb[1] = 1;
        jb[2] = 1;
        jb[3] = 1;

        try {
            for (int i = 0; i < 9; i++) {
                huokebullet[i] = playerbullet("/pic/bullet.png");
            }
        } catch (Exception e) {
        }

        try {
            for (int i = 0; i <= 8; i = i + 3) {
                huokebullet[i].no = 1;//ok
                huokebullet[i].score = 0;
            }
        } catch (Exception e) {
            System.out.println("ffffffffffffff");
        }


        for (int i = 0; i < 9; i++) {
            lm.append(huokebullet[i]);
        }
        lm.append(c1);
        lm.append(b1);
    }

    private Image img(String pic) {
        Image img = null;
        try {
            img = Image.createImage(pic);
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return img;
    }

    private mybullets playerbullet(String pic)//,int px,int py,int total ,int width,int height)
    {
        Image img = null;
        try {
            img = Image.createImage(pic);
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return new mybullets(img, 6, 6, 21, getWidth(), getHeight());//
    }

    public TiledLayer createBackGround() {
        Image img = null;
        try {
            img = Image.createImage("/pic/beijing.png");

        } catch (Exception exp) {
            System.out.println("layer create image");
        }

        TiledLayer tiledLayer = new TiledLayer(50, 200, img, 16, 16);
        int[] map1 = {3, 1, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 2, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            5, 5, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            5, 7, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
            5, 5, 2, 10, 10, 10, 10, 10, 1, 3, 1, 3, 3, 4, 1,
            5, 2, 2, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1,
            5, 5, 2, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1,
            5, 2, 2, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 4, 1
        };

        for (int i = 0; i < map1.length; i++)//ע�� �˲�����ĺ󱳾�������Ч  
        {
            int column = i % 15;//15����ж����У��ı��ͼ����ӦҲҪ�ı�
            row = (i - column) / 15;//ͬ��һ��ı�
            tiledLayer.setCell(column, row, map1[i]);
        }
        row2 = (row + 1) * 16 - getHeight();//��ͼ�ܵĳ��ȣ�������ΪҪ��һ����Ļ�Ŀ�������
        y1 = -row2;
        System.gc();
        return tiledLayer;
    }
    boolean conti = true;
    int rate = 50;

    public void run() {
        long st = 0;
        long et = 0;
        Graphics g = getGraphics();
        int l = 1350;
        while (conti) {
            st = System.currentTimeMillis();
            input();
            //����ӵ� 
            if (huokebullet[0].isAlive(0)) {
                huokebullet[0].no--;
                if (huokebullet[0].no > 0) {
                    huokebullet[0].newposition(huokebullet, 0, 3, j0, j1, j2, cboss, img("/pic/explosion.png"));

                }
                if (huokebullet[0].no <= 0) {	//�����ӵ�
                    huokebullet[0].setAlive(0);
                    huokebullet[0].clean(0, huokebullet);
                }
            }
            if (huokebullet[3].isAlive(3)) {
                huokebullet[3].no--;
                if (huokebullet[3].no > 0) {
                    huokebullet[3].newposition(huokebullet, 3, 3, j0, j1, j2, cboss, img("/pic/explosion.png"));

                }
                if (huokebullet[3].no <= 0) {	//�����ӵ�
                    huokebullet[3].setAlive(3);
                    huokebullet[3].clean(3, huokebullet);
                }
            }
            if (huokebullet[6].isAlive(6)) {
                huokebullet[6].no--;
                if (huokebullet[6].no > 0) {
                    huokebullet[6].newposition(huokebullet, 6, 3, j0, j1, j2, cboss, img("/pic/explosion.png"));
                }
                if (huokebullet[6].no <= 0) {	//�����ӵ�
                    huokebullet[6].setAlive(6);
                    huokebullet[6].clean(6, huokebullet);
                }

            }


            //����ӵ���ײ
            //huoke0
            if (huokebullet[0].collidesWith(cboss, true)) {
                huokebullet[0].setVisible(false);
                huokebullet[1].setVisible(false);
                huokebullet[2].setVisible(false);
                cboss.setFrame(1);
                //     huokebullet[0].no=0;//****************���´�ѭ��ʮʱ��NO--<=0������ִ��NO<=0�ж����
                if (bosslife <= 55) {
                    bosslife = bosslife + 5;
                    //cboss.setFrame(0);
                }
                huokebullet[0].setAlive(0);
                huokebullet[0].clean(0, huokebullet);

            }
            if (huokebullet[0].collidesWith(sboss, true) && (sbpzbz == 0)) {
                huokebullet[0].setVisible(false);
                huokebullet[1].setVisible(false);
                huokebullet[2].setVisible(false);
                sboss.setFrame(1);
                //huokebullet[3].no=1;
                if (slife <= 75) {
                    slife = slife + 5;
                }
                huokebullet[0].setAlive(0);
                huokebullet[0].clean(0, huokebullet);
            }
            // huoke3
            if (huokebullet[3].collidesWith(cboss, true)) {
                cboss.setFrame(1);
                huokebullet[3].setVisible(false);
                huokebullet[4].setVisible(false);
                huokebullet[5].setVisible(false);
                huokebullet[3].no = 0;
                if (bosslife <= 55) {
                    bosslife = bosslife + 5;
                }
                huokebullet[3].setAlive(3);
                huokebullet[3].clean(3, huokebullet);
            }//end boss
            if (huokebullet[3].collidesWith(sboss, true) && (sbpzbz == 0)) {
                sboss.setFrame(1);
                huokebullet[3].setVisible(false);
                huokebullet[4].setVisible(false);
                huokebullet[5].setVisible(false);
                huokebullet[3].no = 1;
                if (slife <= 75) {
                    slife = slife + 5;
                }
                huokebullet[3].setAlive(3);
                huokebullet[3].clean(3, huokebullet);
            }
            //huoke6
            if (huokebullet[6].collidesWith(cboss, true)) {
                huokebullet[6].setVisible(false);
                huokebullet[7].setVisible(false);
                huokebullet[8].setVisible(false);
                cboss.setFrame(1);
                if (bosslife <= 55) {
                    bosslife = bosslife + 5;
                }
                huokebullet[6].setAlive(6);
                huokebullet[6].clean(6, huokebullet);
            }
            if (huokebullet[6].collidesWith(sboss, true) && (sbpzbz == 0)) {
                sboss.setFrame(1);
                huokebullet[6].setVisible(false);
                huokebullet[7].setVisible(false);
                huokebullet[8].setVisible(false);
                //   huokebullet[6].no=1;
                if (slife <= 75) {
                    slife = slife + 5;
                }
                huokebullet[6].setAlive(6);
                huokebullet[6].clean(6, huokebullet);
            }//end sboss




            if ((y1 > -1350) && (pzbzover == 0))//y1��-1391������C1��GETY����Ϊ����꣬������
            {

                switch (ai) {
                    case 0:
                        if (jb[0] == 1) {
                            jbz = 0;//�޶�����ֻ�ܷ���һ���ӵ�����CASE0  BREAK�������һ��IF�����ʾ
                            j0.setVisible(true);
                            j1.setVisible(true);
                            j2.setVisible(true);
                            j1.setFrame(2);
                            j0.setFrame(2);
                            j2.setFrame(2);
                            j0.setPosition(100 - aipp * 30, planepoup + 24);
                            j1.setPosition(100, planepoup);
                            j2.setPosition(100 + aipp * 30, planepoup - 24);;
                            jb[0] = 2;
                        }
                        if (jb[0] == 2) {
                            j0.move(0, 3);
                            j1.move(0, 3);
                            j2.move(0, 3);
                            kkk = kkk - 1;
                        }
                        if (kkk <= 0)//�ɻ�ʼת��
                        {
                            jb[0] = 3;//��ִ��JB[0]=1��2��
                            if (aipp >= 0) {
                                j1.setFrame(0);
                                j0.setFrame(0);
                                j2.setFrame(0);
                                j0.move(-3, 3);
                                j1.move(-3, 3);
                                j2.move(-3, 3);
                            } else if (aipp < 0) {
                                j1.setFrame(1);
                                j0.setFrame(1);
                                j2.setFrame(1);
                                j0.move(3, 3);
                                j1.move(3, 3);
                                j2.move(3, 3);
                            }
                            if (j2.getY() > planepo)//���˷ɻ��ӵ���ʧ
                            {
                                j0.setVisible(false);
                                j1.setVisible(false);
                                j2.setVisible(false);
                                jbullet0.setVisible(false);
                                jbullet1.setVisible(false);
                                jbullet2.setVisible(false);
                                jpb = -1;//�ӵ���־λ��Ϊ-1��ʹ��SWITCH����ӵ�ѭ����ִ��   	//�ӵ��ȷɻ��
                                ai = aik.nextInt() % 4;
                                aipp = aip.nextInt() % 5;
                                if (aipp == 0) {
                                    aipp = aip.nextInt() % 5;
                                }
                                if (ai < 0) {
                                    ai = ai * (-1);
                                }
                                jb[ai] = 1;
                                kkk = getHeight() / 8;
                            }
                        }
                        if ((j1.getX() <= c1.getX() - 18) & (jbz == 0)) {
                            jpb = 0;
                            if (j0.isVisible()) {
                                jbullet0.setVisible(true);
                            }
                            if (j1.isVisible()) {
                                jbullet1.setVisible(true);
                            }
                            if (j2.isVisible()) {

                                jbullet2.setVisible(true);
                            }
                            jbullet0.setPosition(j0.getX() + 12, j0.getY() + 30);
                            jbullet1.setPosition(j1.getX() + 12, j1.getY() + 30);
                            jbullet2.setPosition(j2.getX() + 12, j2.getY() + 30);
                            jbz = 1;
                        }
                        break;
                    case 1:
                        if (jb[1] == 1) {
                            j0.setVisible(true);
                            j1.setVisible(true);
                            j2.setVisible(true);
                            j1.setFrame(2);
                            j0.setFrame(2);
                            j2.setFrame(2);
                            j0.setPosition(100 - aipp * 30, planepoup + 24);
                            j1.setPosition(100, planepoup);
                            j2.setPosition(100 + aipp * 30, planepoup - 24);;
                            jb[1] = 2;
                        }
                        if (jb[1] == 2) {
                            j0.move(0, 3);
                            j1.move(0, 3);
                            j2.move(0, 3);
                            kkk = kkk - 1;
                        }
                        if (kkk < 0)//�ɻ��ͷ���ӵ����Ϸ����
                        {
                            jb[1] = 3;
                        }
                        if (jb[1] == 3) {
                            jpb = 1;//�ӵ����Ϸ����

                            j1.setFrame(3);
                            j0.setFrame(3);
                            j2.setFrame(3);
                            jb[1] = 4;
                        }
                        if (jb[1] == 4) {
                            j0.move(0, -4);
                            j1.move(0, -4);
                            j2.move(0, -4);
                            if (j2.getY() < planepoup)//�˴��ķɻ������Ϸ��ɵ�
                            {
                                j0.setVisible(false);
                                j1.setVisible(false);
                                j2.setVisible(false);
                                jbullet0.setVisible(false);
                                jbullet1.setVisible(false);
                                jpb = -1;//�ɻ��ӵ��������˶�	   	   
                                jbullet2.setVisible(false);
                                ai = aik.nextInt() % 4;
                                if (ai < 0) {
                                    ai = ai * (-1);
                                }
                                jb[ai] = 1;
                                aipp = aip.nextInt() % 5;
                                if (aipp == 0) {
                                    aipp = aip.nextInt() % 5;
                                }
                                kkk = getHeight() / 8;
                            }
                        }
                        break;

                    case 2:
                        if (jb[2] == 1) {
                            jbz = 0;
                            j0.setVisible(true);
                            j1.setVisible(true);
                            j2.setVisible(true);
                            j1.setFrame(2);
                            j0.setFrame(2);
                            j2.setFrame(2);
                            j0.setPosition(100 - aipp * 30, planepoup + aipp * 30);
                            j1.setPosition(100, planepoup);
                            j2.setPosition(100 + aipp * 30, planepoup + aipp * 30);;
                            jb[2] = 2;
                        }
                        if (jb[2] == 2) {
                            j0.move(0, 3);
                            j1.move(0, 3);
                            j2.move(0, 3);
                            kkk = kkk - 1;
                        }
                        if (kkk <= 0) {
                            jb[2] = 3;
                            j1.setFrame(0);
                            j0.setFrame(0);
                            j2.setFrame(0);
                            j0.move(-3, 3);
                            j1.move(-3, 3);
                            j2.move(-3, 3);

                            if (j2.getY() > planepo) {
                                jpb = -1;
                                jbullet0.setVisible(false);
                                jbullet1.setVisible(false);
                                jbullet2.setVisible(false);
                                j0.setVisible(false);
                                j1.setVisible(false);
                                j2.setVisible(false);
                                ai = aik.nextInt() % 4;
                                if (ai < 0) {
                                    ai = ai * (-1);
                                }
                                jb[ai] = 1;
                                aipp = aip.nextInt() % 5;
                                if (aipp == 0) {
                                    aipp = aip.nextInt() % 5;
                                }
                                kkk = getHeight() / 8;
                            }
                        }
                        if (((j1.getX() <= c1.getX() - 18) || ((j2.getX() - 6) >= c1.getX())) & (jbz == 0)) {
                            if (j0.isVisible()) {
                                jbullet0.setVisible(true);
                            }
                            if (j1.isVisible()) {
                                jbullet1.setVisible(true);
                            }
                            if (j2.isVisible()) {

                                jbullet2.setVisible(true);
                            }
                            jpb = 0;
                            jbullet0.setPosition(j0.getX() + 12, j0.getY() + 30);
                            jbullet1.setPosition(j1.getX() + 12, j1.getY() + 30);
                            jbullet2.setPosition(j2.getX() + 12, j2.getY() + 30);
                            jbz = 1;
                        }
                        break;

                    case 3:
                        // 	 System.out.println("ffffffffffffffffffffffffffff");
                        if (jb[3] == 1) {
                            j1b = 0;
                            jbz = 0;
                            j0.setVisible(true);
                            j1.setVisible(true);
                            j2.setVisible(true);
                            j1.setFrame(2);
                            j0.setFrame(2);
                            j2.setFrame(2);
                            j0.setPosition(200 - aipp * 50, planepoup - aipp * 10);
                            j1.setPosition(100, planepoup);
                            j2.setPosition(100 + aipp * 50, planepoup - aipp * 10);
                            jb[3] = 2;
                        }
                        if (jb[3] == 2) {
                            j0.move(0, 3);
                            j1.move(0, 3);
                            j2.move(0, 3);
                            //����ɻ�J1���и�������
                            if (gzks == 0)//���ɻ��ͷ������GZ=1�������ϲ��ܸ���
                            {
                                if (j1.getX() < c1.getX())//J1��C1�����
                                {
                                    j1.move(2, 2);
                                    j1.setFrame(1);
                                }

                                if (j1.getX() > c1.getX())//j1��C1���ұ�
                                {
                                    j1.setFrame(0);
                                    j1.move(-2, 2);
                                }
                                if ((j1.getX() < c1.getX()) && ((j1.getX() + 48) > c1.getX()) && (j1.getY() < c1.getY()))//���C1��J1�������淶Χ��
                                {
                                    j1.setFrame(2);
                                    j1.move(0, 2);
                                    if (j1b == 0)//�����ӵ�����
                                    {
                                        jbullet1.setVisible(true);
                                        jbullet1.setPosition(j1.getX() + 12, j1.getY() + 30);
                                        j1b = 1;
                                    }
                                }
                                //j1.move(0,3);
                                j0.move(0, 3);
                                jbullet1.move(0, 6);
                                j1.move(0, 3);
                                j2.move(0, 3);


                            }
                            ///////////////////////////////////////////////////////


                            if ((j2.getY() > (planepo + 22)) && (j0.getY() > (planepo + 22)) && ((j1.getY() < (planepoup - 22)) || (j1.getY() > (planepo + 22)))) {//����λ
                                jb[3] = 1;
                                gz = 0;
                                gzks = 0;
                                jbullet1.setVisible(false);
                                jbullet2.setVisible(false);
                                j0.setVisible(false);
                                j1.setVisible(false);
                                j2.setVisible(false);
                                ai = aik.nextInt() % 4;
                                if (ai < 0) {
                                    ai = ai * (-1);
                                }
                                jb[ai] = 1;
                                aipp = aip.nextInt() % 5;
                                if (aipp == 0) {
                                    aipp = aip.nextInt() % 5;
                                }
                                kkk = getHeight() / 8;

                            }

                            if ((j2.getX() <= c1.getX() - 18) & (jbz == 0))//����
                            {
                                jpb = 0;
                                if (j0.isVisible()) {
                                    jbullet0.setVisible(true);
                                }

                                if (j2.isVisible()) {

                                    jbullet2.setVisible(true);
                                }
                                jbullet0.setPosition(j0.getX() + 12, j0.getY() + 30);
                                //jbullet1.setPosition(j1.getX()+12,j1.getY()+30);
                                jbullet2.setPosition(j2.getX() + 12, j2.getY() + 30);
                                jbz = 1;
                            }

                        }
                        break;
                }//end while	
            }//end if
            if (jpb == 0) {//System.out.println("dddddddddd");
                jbullet0.move(0, 5);
                jbullet1.move(0, 5);
                jbullet2.move(0, 5);
            }
            if ((jbullet0.collidesWith(c1, true) || jbullet1.collidesWith(c1, true) || jbullet2.collidesWith(c1, true) || bossbullet0.collidesWith(c1, true) || bossbullet1.collidesWith(c1, true) || bossbullet2.collidesWith(c1, true)) && (pzbz == 0)) {

                c1.setImage(img("/pic/explosion.png"), 32, 32);
                c1.setFrame(3);
                if ((playerno > 0)) {
                    playerno = playerno - 1;
                    planert = 1;

                } else {
                    pzbz = 1;
                    overcmd = 1;
                    over = 1;
                }//ֻ�й���3�κ��ڴ��
            }
            if ((j0.collidesWith(c1, true) && (pzbz == 0))) {
                c1.setImage(img("/pic/explosion.png"), 32, 32);
                c1.setFrame(3);
                if (playerno > 0) {
                    playerno = playerno - 1;
                    planert = 1;
                    playlife = 0;
                } else {
                    overcmd = 1;
                    playlife = 1;

                    over = 1;
                }
                j0.setVisible(false);
                pzbz = 1;
            }
            if ((j1.collidesWith(c1, true) && (pzbz == 0))) {
                c1.setImage(img("/pic/explosion.png"), 32, 32);
                c1.setFrame(3);
                if (playerno > 0) {
                    playerno = playerno - 1;
                    planert = 1;
                    playlife = 0;
                } else {
                    overcmd = 1;
                    playlife = 1;
                    over = 1;
                }
                j1.setVisible(false);
                pzbz = 1;
            }
            if ((j2.collidesWith(c1, true) && (pzbz == 0))) {
                c1.setImage(img("/pic/explosion.png"), 32, 32);
                c1.setFrame(3);

                if (playerno > 0) {
                    playerno = playerno - 1;
                    planert = 1;
                    playlife = 0;
                } else {

                    playlife = 1;
                    if (overcmd == 0) {
                        overcmd = 1;
                    }

                    over = 1;
                }
                j2.setVisible(false);
                pzbz = 1;
            }

            if (overcmd == 1) {
                addCommand(new Command("����", Command.OK, 1));
                overcmd = 2;//ͬ�?�е����̲߳�ֹͣ
            }
            if (boss == 1)//��һ�ع�ͷ
            {
                cboss.setVisible(true);
                if (cboss.getY() < 25) {
                    cboss.move(0, 3);
                } else {
                    lr = 1;
                }
                //�жϷɻ�������
                if (lr == 1) {

                    if (cboss.getX() < 0) {
                        right = 0;
                        left = 1;
                    } else if (cboss.getX() > getWidth() - cboss.getWidth())//��ͷ��Ȼд����getHeight(),�˷�ʱ��
                    {
                        left = 0;
                        right = 1;
                    }
                    if (right == 0) {
                        cboss.move(3, 0);
                    } else if (left == 0) {
                        cboss.move(-3, 0);
                    }

                }
                //�ж�BOSS����
                if (((cboss.getX() <= c1.getX() - 10) || (cboss.getX() <= c1.getX() + 60)) && (jbsz == 0)) {      //jpb=0;
                    bossbullet0.setPosition(cboss.getX() + 6, cboss.getY() + 40);
                    bossbullet1.setPosition(cboss.getX() + 30, cboss.getY() + 52);
                    bossbullet2.setPosition(cboss.getX() + 54, cboss.getY() + 40);
                    jbsz = 1;//��ֹ����ˢ�µ���ǰλ��
                }
                if (jbsz == 1) {
                    bossbullet0.setVisible(true);
                    bossbullet1.setVisible(true);
                    bossbullet2.setVisible(true);
                    bossbullet0.move(0, 5);
                    bossbullet1.move(0, 5);
                    bossbullet2.move(0, 5);
                }
                if (bossbullet2.getY() > getHeight()) {
                    jbsz = 0;
                }
            }
            if (bosslife == 60)//��β
            {
                cboss.setVisible(false);
                j1.setVisible(false);
                j0.setVisible(false);
                j2.setVisible(false);
                bossbullet0.setVisible(false);
                bossbullet1.setVisible(false);
                bossbullet2.setVisible(false);
                jbullet0.setVisible(false);
                jbullet1.setVisible(false);
                jbullet2.setVisible(false);
                pzbz = 1;
                bossover = 1;//����β
                boss = 2;
                bosslife = 65;//��ֹ��ͣˢ��
                pzbzover = 1;//���˷ɻ���
                addCommand(new Command("����", Command.OK, 1));
            }
            if (bosslife == 45) {
                bosscolor = 1;//BOSS��죬��ʾ�����        
            }
            if (slife == 65) {
                sbosscolor = 1;//SBOSS��죬��ʾ�����
            }
            if (y1 < 0) {
                render(g);
                y1 = y1 + 1;//��ͼ�ƶ��ؼ�	.........................................................
                planepoup = planepoup - 1;//����Ϸ�
                planepo = planepo - 1;//����·�
                c1.move(0, -1);//��Ҳ����Ʒɻ��ʱ��ɻ���Բ����񣬴˴��� ָӦ�ú͵�ͼ�ƶ���ֵ���
            }//�����
            if (y1 >= 0) {
                if (boss == 0)//��ֹ��ͣ��ˢ��
                {
                    jbsz = 0;//////////////////////////////////////lollllllllllllllllllllllllll���˲��ܿ�������Ӧ��=0��
                    boss = 1;
                    planepoup = 0;//�ж��ϳ����־ֵ
                    planepo = getHeight();//�ж��³����־ֵ
                    cboss.setPosition(80, -60);
                }
                renderboss(g);
            }//�жϵ�ͼ�Ƿ�ͷ������ͷ		
            if (bosscolor == 0)//�ж�BOSS��ɫ
            {
                cboss.setFrame(0);//����ʱ��ĺ�ɫ�����ڱ��ԭɫ�����߳̽�β���䣬���Դﵽ��ɫ��Ч��
            } else {
                cboss.setFrame(1);//���ʱһֱ��ɫ
            }
            if (sbosscolor == 0)//ԭ��ͬ��
            {
                sboss.setFrame(0);
            } else {
                sboss.setFrame(1);
            }
            //���ƣ�ԭ�?���趨5������ʼλ�ã�Ȼ��ʧһ������һ��
            if (cloundno == 0) {  //��1��Ϊ�˷�ֹ����0�����������Ӧ����1��2��3��4��5
                cloud[0].setPosition(25, planepoup - (65));
                cloud[1].setPosition(80, planepoup - (140));
                cloud[2].setPosition(112, planepoup - (90));
                cloud[3].setPosition(175, planepoup - (200));
                cloud[4].setPosition(223, planepoup - (70));
                cloundno = 1;
            }
            cloud[0].move(0, 1);
            cloud[1].move(0, 1);
            cloud[2].move(0, 1);
            cloud[3].move(0, 1);
            cloud[4].move(0, 1);
            if (cloud[0].getY() > planepo) {
                cloudposition = aicloud.nextInt() % 5;
                if (cloudposition < 0) {
                    cloudposition = cloudposition * (-1);
                }
                cloudposition = cloudposition + 1;
                cloud[0].setPosition(cloudposition * 40, planepoup);
            }
            if (cloud[1].getY() > planepo) {
                cloudposition = aicloud.nextInt() % 5;
                if (cloudposition < 0) {
                    cloudposition = cloudposition * (-1);
                }
                cloudposition = cloudposition + 1;
                cloud[1].setPosition(cloudposition * 30, planepoup);
            }
            if (cloud[2].getY() > planepo) {
                cloudposition = aicloud.nextInt() % 5;
                if (cloudposition < 0) {
                    cloudposition = cloudposition * (-1);
                }
                cloudposition = cloudposition + 1;
                cloud[2].setPosition(cloudposition * 55, planepoup);
            }
            if (cloud[3].getY() > planepo) {
                cloudposition = aicloud.nextInt() % 5;
                if (cloudposition < 0) {
                    cloudposition = cloudposition * (-1);
                }
                cloudposition = cloudposition + 1;
                cloud[03].setPosition(cloudposition * 15, planepoup);
            }
            if (cloud[4].getY() > planepo) {
                cloudposition = aicloud.nextInt() % 5;
                if (cloudposition < 0) {
                    cloudposition = cloudposition * (-1);
                }
                cloudposition = cloudposition + 1;
                cloud[4].setPosition(cloudposition * 22, planepoup);
            }
            //����END         
            //СBOSS���ּ���AI
            if ((y1 == -1000) && (sbz == 0)) {
                sbsz0 = 0;
                sbsz1 = 0;
                sbsz2 = 0;
                sbsz3 = 0;
                drawslife = 1;
                sboss.setVisible(true);
                sboss.setPosition(50, planepoup - 65);
                sbz = 1;
            }
            if (sbz == 1)//�����˷ɻ�����Ļ���棬ֱ������Ļ
            {//System.out.println("dddddddddddddddddddddddddd");
                sboss.move(0, 3);
                if (sboss.getY() > planepoup) {
                    sbz = 2;//�ɻ�������Ȳ����������ҷɻ�ĵ�λ���ж�Ӧ����ô��
                }
            }
            if (sbz == 2)//����
            {

                if (((sboss.getY() - 50) < c1.getY()) && (smovebz == 0)) {
                    sbmove = 1;//����		
                }
                if (((sboss.getX() + 30) < c1.getX()) && (smovebz == 0)) {
                    sbmove = 4;//����
                }
                if (((sboss.getY() + 50) < c1.getY()) && (smovebz == 0)) {
                    sbmove = 2;//����
                }
                if (((sboss.getX() - 30) > c1.getX()) && (smovebz == 0)) {
                    sbmove = 3;//����
                }

                if (sbmove == 1)//����
                {
                    smovebz = 1;
                    sboss.move(0, -2);//֮������ôд��Ϊ�˿�������һֱ�ƶ�

                }
                if (sbmove == 2)//��
                {
                    smovebz = 1;
                    sboss.move(0, 2);
                }

                if (sbmove == 3)//��
                {
                    sboss.move(-2, 0);
                }

                if (sbmove == 4)//��
                {
                    smovebz = 1;
                    sboss.move(2, 0);
                }

                //��  �� ��  ��
                if (sboss.getY() < planepoup) // ||(sboss.getY()>(planepo-65))||(sboss.getX()<0)||(sboss.getX()>getWidth()-65))
                {
                    sboss.setPosition(sboss.getX(), planepoup);
                    smovebz = 0;
                }
                //��
                if (sboss.getY() > (planepo - 65)) {
                    sboss.setPosition(sboss.getX(), planepo - 65);
                    smovebz = 0;
                }
                //��
                if (sboss.getX() < 0) {
                    sboss.setPosition(0, sboss.getY());
                    smovebz = 0;
                }
                //��
                if (sboss.getX() > (getWidth() - 65)) {
                    sboss.setPosition(getWidth() - 65, sboss.getY());
                    smovebz = 0;
                }
                //����
                //����
                if (((sboss.getX() + 40) < c1.getX()) && (sboss.getY() < c1.getY()) && ((sboss.getY() + 65) > c1.getY()) && (sbsz0 == 0)) {
                    bossbullet0.setVisible(true);
                    bossbullet0.setPosition(sboss.getX() + 45, sboss.getY() + 35);
                    sbsz0 = 1;
                }
                if (sbsz0 == 1) {
                    bossbullet0.move(3, 0);
                    if (bossbullet0.getX() > getWidth()) {
                        sbsz0 = 0;
                    }
                }
                //����
                if ((sboss.getX() > c1.getX()) && ((sboss.getY() + 65) > c1.getY()) && (sbsz1 == 0)) {
                    bossbullet1.setPosition(sboss.getX() + 10, sboss.getY() + 35);
                    sbsz1 = 1;
                }
                if (sbsz1 == 1) {
                    bossbullet1.move(-3, 0);
                    if (bossbullet1.getX() < 0) {
                        bossbullet1.setVisible(false);
                        sbsz1 = 0;
                    }
                }
                //����
                if ((sboss.getX() < c1.getX()) && ((sboss.getX() + 50) > (c1.getX())) && (sboss.getY() > c1.getY()) && (sbsz2 == 0)) {
                    bossbullet2.setVisible(true);//֮����ֻ��2SET������Ϊ��Ļ���϶������SET����Ļ����ῴ����ֹ���ӵ�
                    bossbullet2.setPosition(sboss.getX() + 25, sboss.getY());
                    sbsz2 = 1;
                }
                if (sbsz2 == 1) {
                    bossbullet2.move(0, -4);
                    if (bossbullet2.getY() < planepoup) {
                        bossbullet2.setVisible(false);
                        sbsz2 = 0;
                    }
                }

                //����
                if ((sboss.getX() < c1.getX()) && ((sboss.getX() + 50) > (c1.getX())) && (sboss.getY() < c1.getY()) && (sbsz3 == 0)) {
                    bossbullet0.setVisible(true);
                    bossbullet1.setVisible(true);
                    bossbullet2.setVisible(true);
                    bossbullet2.setPosition(sboss.getX() + 10, sboss.getY() + 25);
                    bossbullet1.setPosition(sboss.getX() + 30, sboss.getY() + 50);
                    bossbullet0.setPosition(sboss.getX() + 55, sboss.getY() + 25);
                    sbsz3 = 1;
                }
                if (sbsz3 == 1) {
                    bossbullet0.move(0, 4);
                    bossbullet1.move(0, 4);
                    bossbullet2.move(0, 4);
                    if (bossbullet0.getY() > planepo) {
                        bossbullet0.setVisible(false);
                        bossbullet1.setVisible(false);
                        bossbullet2.setVisible(false);
                        sbsz3 = 0;
                    }
                }
            }//sboss end
            if ((slife == 80)) {
                sboss.setImage(img("/pic/explosion.png"), 32, 32);
                sboss.setFrame(3);
                bossbullet0.setVisible(false);
                bossbullet1.setVisible(false);
                bossbullet2.setVisible(false);
                jiangli = 11;
                slife = 85;
                drawslife = 0;
                playerno = playerno + 1;
                sbz = -1;//sboss�����ӵ��Ƕβ�ִ��
                sbpzbz = 1;//����ӵ���СBOSS��������ײ���
            }
            //�����ҹ��ˣ������ĵ�ͼ�Զ��ߣ���BOSS������ʱ��SBOSS���ٷɳ���Ļ�Ͻ�
            if ((sboss.getY() == getHeight())) {
                sbz = -1;
                sbpzbz = 1;
                drawadd = 21;
            }
            if (drawadd == 21) {
                sboss.move(0, -3);
                if (sboss.getY() < -65) {
                    sboss.setVisible(false);
                    drawadd = 31;
                }
            }
            if (planert == 1) {
                inputno = 1;
                pzbz = 1;
                s2 = 65;
                c1.setImage(img("/pic/MyPlaneFrames.png"), 24, 24);
                c1.setFrame(0);
                c1.setVisible(true);
                c1.setPosition(getWidth() / 2, planepo + 48);
                //pzbz=0;
                //�ɻ����Ļ��ɻ������˹�̼��̲�����
                //��ʱ��ײ�����ã����޵�״̬ 
                planert = 2;
            }//��ҷɻ�С�Һ��ʼλ��
            if (planert == 2) {
                c1.move(0, -2);

                if (c1.getY() < (planepo - 24)) {//System.out.println(c1.getY());
                    //System.out.println(planepo-24);
                    inputno = 0;
                    s1 = 1;
                    planert = 3;
                }
                if (c1.getY() > (planepo + 24)) {
                    c1.move(0, -2);
                }
            }
            et = System.currentTimeMillis();
            if ((et - st) < rate) {
                try {
                    Thread.sleep(rate - (et - st));

                } catch (Exception exp) {
                }
            }

        }


    }

    public void render(Graphics g) {
        System.gc();
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, getWidth(), getHeight());
        lm.setViewWindow(0, 0, getWidth(), getHeight() + 10000);//0,0,��ʼλ�ã��?��,���� �������
        lm.paint(g, 0, y1);//�����ﻭ�ǵ�ͼ�����Ǿ���//�ο�������P376�����еĵ㶼�����ϵĵ㣬����PAINTҪ���ø���//����ʼ=-1400��һ���߳�+1����-1399��-1398��
        // huokebullet.paint(g);
        //c1.setPosition(50,50);//�д��еĻ�����׼���鲻�����͹̶��ڣ�50��50���ˣ�
        //��Ϊ�߳ɵĹ�ϵ�����Դ��и����˹��캯����
        if (over == 1)//c1.getheight=32,��Ϊ�ɻ���к����˱�ըͼƬ
        {
            g.drawString("���ں��������Ͽ��������У�׳��ѳ��,ʱ", c1.getWidth() - 24, 60, 0);//c1.getHeight(),0);
            g.drawString("1937�� 8��14�գ��վ��4�����ξ����Ա��˼", c1.getWidth() - 24, 40, 0);//c1.getHeight()-20,0);
            g.drawString("��21��", c1.getWidth() - 24, 80, 0);//c1.getHeight()+20,0);

            inputno = 1; //���������־λ��GAMEOVER�󣬾Ͳ��ܶ�ȡ���������
        }
        g.drawString("37��8��14�� �������� ս��:" + String.valueOf(huokebullet[0].rscore() + huokebullet[3].rscore() + huokebullet[6].rscore()), c1.getWidth() - 24, c1.getHeight() - 20, 0);//
        if (drawslife == 1)//sboss������
        {
            g.setColor(255, 0, 0);
            g.fillRect(2, 22, 80, 5);//sboss�������������
            g.setColor(255, 255, 255);
            g.fillRect(2, 22, slife, 5);//sboss��������ǰ������
        }
        if (playerno == 3) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 170, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 195, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);

        }
        if (playerno == 2) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 195, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
        }
        if (playerno == 1) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
        }
        if (playerno == 4) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 145, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 170, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 195, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
            g.setColor(255, 0, 0);
        }
        if (jiangli == 11) {
            g.setColor(255, 0, 0);
            g.drawString("Ԯ���", 100, 150, 0);
            if (sboss.getY() > planepo) {
                jiangli = 20;
            }
        }
        if (s1 == 1) {
            g.setColor(255, 255, 255);
            g.fillRect(170, 22, 65, 5);//�޵���������ǰ������
            g.setColor(255, 0, 0);
            g.fillRect(170, 22, s2, 5);//�޵н������������
            g.drawString("�޵�ʱ��", 124, 18, 0);
            drawadd = 1;
            s2 = s2 - 1;
            if (s2 == 0) {
                pzbz = 0;
                s1 = 2;
            }
        }
        flushGraphics();
    }

    public void renderboss(Graphics g) {
        System.gc();
        lm.setViewWindow(0, 0, getWidth(), getHeight());//0,0,��ʼλ�ã��?��,���� �������		
        lm.paint(g, 0, 0);
        if (over == 1)//c1.getheight=32,��Ϊ�ɻ���к����˱�ըͼƬ
        {
            g.drawString("���ں��������Ͽ��������У�׳��ѳ��,��", c1.getWidth() - 24, 60, 0);//c1.getHeight(),0);
            g.drawString("1937�� 8��14�գ��վ��4�����ξ����Ա��˼", c1.getWidth() - 24, 40, 0);//c1.getHeight()-20,0);
            g.drawString("��21��", c1.getWidth() - 24, 80, 0);//c1.getHeight()+20,0);
            inputno = 1; //���������־λ��GAMEOVER�󣬾Ͳ��ܶ�ȡ���������
        }
        g.setColor(255, 0, 0);
        g.fillRect(2, 2, 60, 5);//����������������
        g.setColor(255, 255, 255);
        g.fillRect(2, 2, bosslife, 5);//��������ǰ������
        if ((bossover == 1) && (c1.isVisible())) {
            g.drawString("�˴������ſ�ս��ʵ��ʷս��", c1.getWidth() - 24, 40, 0);//c1.getHeight()-20,0); 
            g.drawString("�˴������ſ�ս��ʵ��ʷս��", c1.getWidth() - 24, 60, 0);//c1.getHeight(),0);
            g.drawString("��21��", c1.getWidth() - 24, 80, 0);//c1.getHeight()+20,0);
        }
        if (playerno == 4) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 145, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 170, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 195, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
        }
        if (playerno == 3) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 170, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 195, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
        }
        if (playerno == 2) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 195, 4, 0);
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
        }
        if (playerno == 1) {
            g.drawImage(img("/pic/playerbiaozhi.png"), 220, 4, 0);
        }
        if (s1 == 1) {
            g.setColor(255, 255, 255);
            g.fillRect(170, 22, 65, 5);//�޵���������ǰ������
            g.setColor(255, 0, 0);
            g.fillRect(170, 22, s2, 5);//�޵н������������
            g.drawString("�޵�", 124, 18, 0);
            s2 = s2 - 1;
            if (s2 == 0) {
                pzbz = 0;
                s1 = 2;
            }
        }
        flushGraphics();//����д�����������BOSSOVER��ʱ�����ˣ��˷���ʱ�� 
    }

    public void input() {
        if (inputno == 0) {
            int keystate = getKeyStates();
            if ((keystate & UP_PRESSED) != 0) {
                moveUp();
            }
            if ((keystate & DOWN_PRESSED) != 0) {
                moveDown();
            }
            if ((keystate & LEFT_PRESSED) != 0) {
                moveLeft();
            }
            if ((keystate & LEFT_PRESSED) == 0) {
                c1.setFrame(0);//�ɻ���ת���Ϊƽ�ɣ�����ֻҪ����ɿ�����ƽ��
            }
            if ((keystate & RIGHT_PRESSED) != 0) {
                moveRight();

            }
            //�� huokebullet[z].noΪ��̣���ÿ���һ���ӵ�Ϊ��־����0��3����6��9��������������18
            if ((keystate & FIRE_PRESSED) != 0) {
                for (int i = 0; i <= 6; i = i + 3) {
                    if (huokebullet[i].no == 1) {
                        for (int z = i; z < i + 3; z++) {   //huokebullet[z].setv(z);				
                            huokebullet[z].initBullets(z);
                            huokebullet[z].no = huokebullet[z].bulletheight;
                        }
                        huokebullet[i].setfirstposition(c1.getX(), c1.getY(), i, huokebullet, img("/pic/bullet.png"));//�����IF����I
                        break;//��Ҫ��ɾ����ֻ�ܴ�һ���ӵ���
                    }
                }
            }
        }
    }

    private void moveDown() {
        c1.move(0, 4);

        if ((c1.getY() + c1.getHeight()) > planepo) {
            c1.setPosition(c1.getX(), planepo - c1.getHeight());//-c1.getHeight()����Ϊ���������ϵĵ㣬����Ҫ��ȥGETHEIGHT��ʹ�÷ɻ����
        }
    }

    private void moveUp() {
        c1.move(0, -4);
        if (c1.getY() < planepoup) {
            c1.setPosition(c1.getX(), planepoup);
        }
    }

    private void moveRight() {

        c1.setFrame(2);
        c1.move(3, 0);
        if (c1.getX() > (getWidth() - c1.getWidth())) {
            c1.setPosition((getWidth() - c1.getWidth()), c1.getY());

        }
    }

    private void moveLeft() {
        c1.move(-3, 0);
        c1.setFrame(1);
        if (c1.getX() <= 0) {
            c1.setPosition(0, c1.getY());
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void commandAction(Command c, Displayable d) {
        if (c.getLabel() == "��ͣ") {
            conti = false;
            removeCommand(c);
            addCommand(new Command("����", Command.OK, 1));
        }
        if (c.getLabel() == "����") {
            conti = true;
            start();//�˴�����Ҫ�����д����RUN������Ͳ�ִ�У�����ԭ�ȵļ������5��30���賿12ʱ22��OK 
            removeCommand(c);
            addCommand(new Command("��ͣ", Command.OK, 2));
        }
        if (c.getLabel() == "����") {
            conti = false;
            midlet.menuscreensecond();//����һ�����˺�����һ�� �˵���һ���Ϊ ���¿�ʼ

        }
    }
}
