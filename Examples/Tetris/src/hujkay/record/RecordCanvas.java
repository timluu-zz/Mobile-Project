package hujkay.record;

import hujkay.Block;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

public class RecordCanvas extends Canvas implements CommandListener {

    private Command okcommand = new Command("OK", Command.OK, 1);
    private Block block;
    private String dbname = "hightscore";
    //ǰ��׵ķ���
    private int score[] = new int[5];
    private int id[] = new int[5];
    private int inputscore = 0;
    private int GAME_ERR = 0;
    private int GAME_ACTIVE = 1;
    private int currentstate;
    private int wide, height;
    private boolean breakrecord;
    private int space = 50;
    private char words[][] = {{'F', 'i', 'r', 's', 't', ' ', ':', ' '},
        {'S', 'e', 'c', 'o', 'n', 'd', ' ', ':', ' '},
        {'T', 'h', 'i', 'r', 'd', ' ', ':', ' '},
        {'F', 'A', 'I', 'L', ' ', 'T', 'O', ' ', 'O', 'P', 'E', 'N', ' ', 'R', 'E', 'C', 'O', 'R', 'D', '!'},
        {'C', 'o', 'n', 'g', 'r', 'a', 't', 'u', 'l', 'a', 't', 'i', 'o', 'n', 's', ' ', 'o', 'n', ' ', 'n', 'e', 'w', ' ', 'r', 'e', 'c', 'o', 'r', 'd', '!'}
    };

    public void initdata() {
        int i;
        for (i = 0; i < 5; i++) {
            score[i] = id[i] = 0;
        }
        wide = this.getWidth();
        height = this.getHeight();
        breakrecord = false;
    }

    public RecordCanvas() {

        this.addCommand(okcommand);
        this.setCommandListener(this);
        start();
    }

    public RecordCanvas(Block block) {
        this.block = block;
        this.addCommand(okcommand);
        this.setCommandListener(this);
        start();
    }

    public RecordCanvas(Block b, int inputscore) {
        this.block = b;
        this.inputscore = inputscore;
        this.addCommand(okcommand);
        this.setCommandListener(this);
        start();
    }

    public RecordStore opendb(String s) {
        //������
        RecordStore rs = null;

        if (s.length() > 32 || s.length() == 0) {
            return null;
        }
        try {
            rs = RecordStore.openRecordStore(s, true);
            return rs;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deletedb(String s) {
        if (s.length() > 32 || s.length() == 0) {
            return false;
        }
        try {
            RecordStore.deleteRecordStore(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public byte[] encode(int m) {
        byte[] s = null;
        int count = 0, tmp;
        if (m == 0) {
            s = new byte[1];
            s[0] = 0;
            return s;
        }

        tmp = m;
        while (tmp != 0) {
            tmp /= 128;
            count++;
        }

        tmp = m;
        s = new byte[count];
        while (count > 0) {
            count--;
            s[count] = (byte) (tmp % 128);
            tmp /= 128;
        }
        return s;
    }

    public int decode(byte[] b) {
        int sum = b[0];
        for (int i = 1; i < b.length; i++) {
            sum = sum * 128 + b[i];
        }

        return sum;
    }

    public int max(int m, int n) {
        return m > n ? m : n;
    }

    public void sort(int m) {
        score[m] = inputscore;
        id[m] = - 1;
        int tmp;

        int i, j;
        for (i = 0; i <= m; i++) {
            for (j = i + 1; j <= m; j++) {
                if (score[i] < score[j]) {
                    tmp = score[i];
                    score[i] = score[j];
                    score[j] = tmp;
                    tmp = id[i];
                    id[i] = id[j];
                    id[j] = tmp;
                }
            }
        }
    }

    public void travelRS(RecordStore rs) {
        int i = 0;
        try {
            RecordEnumeration re = rs.enumerateRecords(null, null, false);
            //System.out.println("There are " + re.numRecords() +	" in RecordStore") ;
            while (re.hasNextElement()) {
                id[i] = re.nextRecordId();
                score[i] = decode(rs.getRecord(id[i]));
                //System.out.print(score[i]+"\n");
                //System.out.println(decode(re.nextRecord()))	;
                i++;
            }
            //����ݽ�������
            sort(i);
            if (re.numRecords() >= 3) {
                //�ͱ���������
                if (score[i] != inputscore)//���һ���ǲ��������ݵĻ�
                //��ô�Ϳ�ʼ������
                {
                    rs.setRecord(id[i], encode(inputscore), 0, encode(inputscore).length);
                    breakrecord = true;//���Ƽ�¼��
                }
            } else {
                //�ͱ����¼������
                rs.addRecord(encode(inputscore), 0, encode(inputscore).length);
                breakrecord = true;//���Ƽ�¼��
            }
        } catch (Exception e) {
        }
        repaint();
    }

    public void start() {
        initdata();
        RecordStore rs = null;
        rs = opendb(dbname);
        currentstate = GAME_ERR;
        if (rs == null) {
            repaint();
            return;
        } else {
            try {
                //byte[] data = encode(300) ;
                //rs.addRecord(data,0,data.length);
                //rs.addRecord(encode(100),0,encode(100).length) ;
                //rs.addRecord(encode(200),0,encode(200).length);

                int k;
                //k = 
                travelRS(rs);
                //��ȡ
                currentstate = GAME_ACTIVE;
            } catch (Exception e) {
            }
        }
        repaint();
    }

    public char[] converttochar(int number, int s) {
        char t[] = new char[20];
        int i, tmp = s;
        if (tmp == 0) {
            t[0] = '0';
            t[1] = '\0';

        } else {
            i = 0;
            while (tmp != 0) {
                tmp /= 10;
                i++;
            }
            tmp = s;
            t[i] = '\0';
            while (tmp != 0) {
                t[--i] = (char) ('0' + tmp % 10);
                tmp /= 10;
            }
        }
        return t;
    }

    public void clear(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(0, 0, 0);
    }

    protected void paint(Graphics g) {
        // TODO Auto-generated method stub
        clear(g);
        if (inputscore == 0) {
            this.breakrecord = false;
        }
        if (this.currentstate != GAME_ACTIVE) {
            g.drawChars(words[3], 0, words[3].length, wide / 7, height / 2, 0);
        } else {
            //�Ƽ�¼��ô��
            char Buffer[];
            for (int i = 0; i < 3; i++) {
                //System.out.println(score[i]);
                Buffer = converttochar(i, score[i]);
                g.drawChars(words[i], 0, words[i].length, wide / 4, height / 6 * (i + 2), 0);
                g.drawChars(Buffer, 0, Buffer.length, wide / 2, height / 6 * (i + 2), 0);
            }

            //�Ƽ�¼��
            if (this.breakrecord) {
                g.drawChars(words[4], 0, words[4].length, wide / 10, height / 6 * 5, 0);
            }
        }

    }

    public void commandAction(Command arg0, Displayable arg1) {
        // TODO Auto-generated method stub
        block.mangeaction(0, 2);

    }
}
