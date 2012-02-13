//����ӵ���
//2005-5-4
//�ӵ���Ϊ�ɻ�Ĳ�ͬ��ͬ���������������ӵ����Ϊ�������ٶ�Ϊ3������96�������ӵ����ֻΪ2/3��
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class mybullets extends Sprite {

    public int score = 0;//ս��
    private int[][] bullets;//�ӵ�����
    //[i][0]�ӵ�X���
    //[i][1]�ӵ�Y���
    //[i][2]�ӵ�Y�����ٶ�
    //[i][3]�ӵ����״̬
    private int bulletstotal;//����ĳ��ȣ���һ�ο��Դ�������ڵ����ӷɻ��ͺŲ�ͬ��ͬ
    public int width, bulletheight;//��Ļ�ĸߺͿ�
    public int no = 0;

    public mybullets(Image img, int picwidth, int picheight, int bulletstotal, int width, int height) {
        super(img, picwidth, picheight);
        this.bulletstotal = bulletstotal;
        bullets = new int[bulletstotal][4];
        this.width = width;
        this.bulletheight = height / 7;//��ʼ������3�ӵ����			

    }

    public void initBullets(int i)//��ʼ���ӵ�״̬����//???
    {
        bullets[i][3] = 1;
        bullets[i][2] = 0;


    }

    public void updata(int i) //����ٶȸ����ӵ���һ���λ�ã�������ʧ
    {
        bullets[i][1] += bullets[i][2];
    }

    public void setfirstposition(int x, int y, int nof, Sprite sprite[], Image img)//��һ���ӵ�����ʼλ��
    {

        sprite[nof].setVisible(true);
        sprite[nof + 1].setVisible(true);
        sprite[nof + 2].setVisible(true);
        //nof��F���ӵ���NOT��NOF+2����
        bullets[nof][0] = x + 10;//��һ�ӵ�Xλ��
        bullets[nof][1] = y - 24;//��һ�ӵ���������ģ�Yλ��,24�Ƿɻ����ϵĵ����꣬����Ӧ����㣬�ͺ����ӵ���������
        sprite[nof].setImage(img, 6, 6);
        bullets[nof + 1][1] = bullets[nof][1] + 10;//��N+1�ӵ�Yλ��
        bullets[nof + 1][0] = x + 10;//��N+1�ӵ�Xλ��
        sprite[nof + 1].setImage(img, 6, 6);
        bullets[nof + 2][1] = bullets[nof + 1][1] + 10;//��N+2�ӵ�Yλ��
        bullets[nof + 2][0] = x + 10;//��N+2�ӵ�Xλ��
        sprite[nof + 2].setImage(img, 6, 6);
    }

    public void newposition(Sprite sprite[], int i, int v, Sprite jp0, Sprite jp1, Sprite jp2, Sprite boss, Image img) {
        bullets[i][2] -= 5;//��Ϊ�ӵ��������ߵģ������Ǽ�
        sprite[i].setPosition(bullets[i][0], bullets[i][1] + bullets[i][2]);
        sprite[i + 1].setPosition(bullets[i][0], bullets[i + 1][1] + bullets[i][2]);
        sprite[i + 2].setPosition(bullets[i][0], bullets[i + 2][1] + bullets[i][2]);
        if (sprite[i].collidesWith(jp0, true))//����ӵ��������ײ���
        {
            sprite[i].setImage(img, 32, 32);
            sprite[i + 1].setImage(img, 32, 32);
            sprite[i + 2].setImage(img, 32, 32);
            sprite[i].setFrame(1);
            sprite[i + 1].setFrame(1);
            sprite[i + 2].setFrame(2);
            bullets[i][3] = 0;
            bullets[i + 1][3] = 0;
            bullets[i + 2][3] = 0;
            jp0.setVisible(false);
            no = 1;

            score = score + 1;
            //jp0.setPosition(0,2500);
            ////sprite[i].setPosition(0,-1500);
            //sprite[i+1].setPosition(0,-1500);
            //sprite[i+2].setPosition(0,-1500);
        }

        if (sprite[i].collidesWith(jp1, true)) {
            sprite[i].setImage(img, 32, 32);
            sprite[i + 1].setImage(img, 32, 32);
            sprite[i + 2].setImage(img, 32, 32);
            sprite[i].setFrame(1);
            sprite[i + 1].setFrame(1);
            sprite[i + 2].setFrame(2);
            bullets[i][3] = 0;
            bullets[i + 1][3] = 0;
            bullets[i + 2][3] = 0;
            jp1.setVisible(false);
            no = 1;
            score = score + 1;

            //jp1.setPosition(0,2500);
            ////sprite[i].setPosition(0,-1500);
            //sprite[i+1].setPosition(0,-1500);
            //sprite[i+2].setPosition(0,-1500);
        }

        if (sprite[i].collidesWith(jp2, true)) {
            sprite[i].setImage(img, 32, 32);
            sprite[i + 1].setImage(img, 32, 32);
            sprite[i + 2].setImage(img, 32, 32);
            sprite[i].setFrame(1);
            sprite[i + 1].setFrame(1);
            sprite[i + 2].setFrame(2);
            bullets[i][3] = 0;
            bullets[i + 1][3] = 0;
            bullets[i + 2][3] = 0;
            jp2.setVisible(false);
            no = 1;
            score = score + 1;

            //jp2.setPosition(0,2500);
            ////sprite[i].setPosition(0,-1500);
            //sprite[i+1].setPosition(0,-1500);
            //sprite[i+2].setPosition(0,-1500);
        }



    }

    public boolean isAlive(int i) {
        if (bullets[i][3] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setAlive(int i) {
        for (int z = i; z < i + 3; z++) {
            bullets[i][3] = 0;
        }

    }

    public int rscore() {
        return score;
    }

    public void clean(int i, Sprite sprite[]) {

        for (int z = i; z < i + 3; z++) {
            //sprite[z].setPosition(0,-1500);
            sprite[z].setVisible(false);
            no = 1;
        }

    }
}
