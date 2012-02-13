
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class rhythm_room extends MIDlet implements CommandListener {

    private Display display;
    private TempleteCanvas canvas;
    private int state = 0;//ϵͳ״̬
    private Timer timer;
    private ClockTimerTask timerTask;
    private long time1, time2, time3;
    private int flag, enable, start1;
    private long t, interval;
    private int counter;
    private int startNum;
    private RecordStore rs = null;
    private long recordTime;
    private boolean anyRecord = false;
    private Image backImage;
    private Image centerImage;
    private Command exit = new Command("Exit", Command.EXIT, 1);
    private Command enter = new Command("Enter", Command.ITEM, 2);
    private Command help = new Command("help", Command.HELP, 2);
    private Command start = new Command("start", Command.ITEM, 2);
    private Command stop = new Command("stop", Command.ITEM, 2);
    private Command replay = new Command("replay", Command.ITEM, 2);
    private Command record = new Command("record", Command.ITEM, 2);
    private Command OK = new Command("OK", Command.OK, 1);

    public rhythm_room() {
        canvas = new TempleteCanvas();
        display = Display.getDisplay(this);
        try {
            backImage = Image.createImage("/back.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startApp() {
        display.setCurrent(canvas);
        canvas.addCommand(exit);
        canvas.addCommand(enter);
        canvas.addCommand(help);
        //canvas.addCommand(record);
        canvas.setCommandListener(this);
        counter = 0;
        flag = 0;
        enable = 0;
        start1 = 0;
        t = 1000;
        startNum = Math.abs((new Random()).nextInt() % 12);
        try {
            rs = RecordStore.openRecordStore("LOCALRECORD", true);
            if (rs.getNumRecords() != 0) {
                anyRecord = true;
                ByteArrayInputStream bais = new ByteArrayInputStream(rs.getRecord(1));
                DataInputStream dis = new DataInputStream(bais);
                try {
                    //recordPlayer=dis.readUTF();
                    recordTime = dis.readLong();
                } catch (Exception e) {
                }
                rs.closeRecordStore();
            }
        } catch (RecordStoreException e) {
        }
        if (anyRecord) {
            canvas.addCommand(record);
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        if (c == exit) {
            destroyApp(false);
            notifyDestroyed();
        } else if (c == enter) {
            state = 1;
            canvas.repaint();
            //��������Ϸ����
        } else if (c == help) {
            state = 2;
            canvas.repaint();
            //���������
        } else if (c == record) {
            state = 4;
            canvas.repaint();
            //���뱾���¼����
        } else if (c == start) {
            state = 1;
            start1 = 1;
            interval = 0;
            time1 = 0;
            time2 = 0;
            time3 = 0;
            while (interval < 500) {
                interval = Math.abs((new Random()).nextInt() % 2000);
            }
            timer = new Timer();
            timerTask = new ClockTimerTask();
            timer.scheduleAtFixedRate(timerTask, t, interval);
            canvas.repaint();
        } else if (c == replay) {
            state = 3;
            canvas.repaint();
        } else if (c == stop) {
            if (enable == 1) {
                flag = 1;
                time2 = System.currentTimeMillis();
                time3 = time2 - time1 - interval * 6;
                time3 = Math.abs(time3);
                enable = 0;
                canvas.repaint();
            }
        }
    }

    class TempleteCanvas extends Canvas {

        int width = 0;
        int height = 0;

        protected void paint(Graphics g) {
            g.setColor(0, 0, 0);
            g.fillRect(0, 0, width - 1, height - 1);
            //��Ϸ��ʼ����
            if (state == 0) {
                width = getWidth();
                height = getHeight();
                Image coverImage = null;
                try {
                    coverImage = Image.createImage("/cover.png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g.drawImage(coverImage, 0, 0, g.TOP | g.LEFT);
            }
            //��Ϸ������
            if (state == 2) {
                Image helpImage = null;
                try {
                    helpImage = Image.createImage("/result.png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g.drawImage(helpImage, 0, 0, g.TOP | g.LEFT);
                g.setColor(120, 240, 60);
                g.drawString("�뿴���Լ��Ľ���������?", 24, 10, g.TOP | g.LEFT);
                g.drawString("���������뼶�ļ����:)", 24, 30, g.TOP | g.LEFT);
                g.drawString("��Ϸ����:", 24, 50, g.TOP | g.LEFT);
                g.drawString("�㡺start����ť����������ɫ", 24, 70, g.TOP | g.LEFT);
                g.drawString("Բ�㰴һ����Ƶ������ͨ�����", 4, 90, g.TOP | g.LEFT);
                g.drawString("����,�밴�˽������Բ�㵽���", 4, 110, g.TOP | g.LEFT);
                g.drawString("ʮ��������,Ҳ����ԲȦ���ڵ���", 4, 130, g.TOP | g.LEFT);
                g.drawString("���ʱ��,���ڸ�ʱ�̰��¡�stop��", 4, 150, g.TOP | g.LEFT);
                g.drawString("��ť����ῴ������ж���׼ȷ", 4, 170, g.TOP | g.LEFT);
                g.drawString("ʱ�̵����", 4, 190, g.TOP | g.LEFT);
                g.drawString("����Ŷ^_^", 24, 210, g.TOP | g.LEFT);
            }
            //�����¼����
            if (state == 4) {
                Image helpImage = null;
                try {
                    helpImage = Image.createImage("/result.png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g.drawImage(helpImage, 0, 0, g.TOP | g.LEFT);
                g.setColor(120, 240, 60);
                g.drawString("����Ŀǰ��׼ȷ�ļ�¼Ϊ:", 30, 100, g.LEFT | g.TOP);
                g.drawString(String.valueOf(recordTime), 100, 130, g.LEFT | g.TOP);
                g.drawString("(����)", 100, 150, g.LEFT | g.TOP);
            }
            if (state == 3) {
                removeCommand(replay);//ȥ��replay Command;
                addCommand(start); //����start Command;
                counter = 0;
                flag = 0;
                enable = 0;
                width = getWidth();
                height = getHeight();
                g.drawImage(backImage, 0, 0, g.TOP | g.LEFT);
                int r = 5;
                g.setColor(0, 255, 0);
                try {
                    centerImage = Image.createImage("/00.png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                startNum = Math.abs((new Random()).nextInt() % 12);
                switch (startNum) {
                    case 0:
                        g.drawArc(120 - r, 56 - r, 10, 10, 0, 360);
                        g.fillArc(120 - r, 56 - r, 10, 10, 0, 360);
                        break;
                    case 1:
                        g.drawArc(156 - r, 66 - r, 10, 10, 0, 360);
                        g.fillArc(156 - r, 66 - r, 10, 10, 0, 360);
                        break;
                    case 2:
                        g.drawArc(182 - r, 93 - r, 10, 10, 0, 360);
                        g.fillArc(182 - r, 93 - r, 10, 10, 0, 360);
                        break;
                    case 3:
                        g.drawArc(191 - r, 130 - r, 10, 10, 0, 360);
                        g.fillArc(191 - r, 130 - r, 10, 10, 0, 360);
                        break;
                    case 4:
                        g.drawArc(182 - r, 164 - r, 10, 10, 0, 360);
                        g.fillArc(182 - r, 164 - r, 10, 10, 0, 360);
                        break;
                    case 5:
                        g.drawArc(155 - r, 191 - r, 10, 10, 0, 360);
                        g.fillArc(155 - r, 191 - r, 10, 10, 0, 360);
                        break;
                    case 6:
                        g.drawArc(120 - r, 200 - r, 10, 10, 0, 360);
                        g.fillArc(120 - r, 200 - r, 10, 10, 0, 360);
                        break;
                    case 7:
                        g.drawArc(83 - r, 190 - r, 10, 10, 0, 360);
                        g.fillArc(83 - r, 190 - r, 10, 10, 0, 360);
                        break;
                    case 8:
                        g.drawArc(56 - r, 162 - r, 10, 10, 0, 360);
                        g.fillArc(56 - r, 162 - r, 10, 10, 0, 360);
                        break;
                    case 9:
                        g.drawArc(46 - r, 130 - r, 10, 10, 0, 360);
                        g.fillArc(46 - r, 130 - r, 10, 10, 0, 360);
                        break;
                    case 10:
                        g.drawArc(56 - r, 93 - r, 10, 10, 0, 360);
                        g.fillArc(56 - r, 93 - r, 10, 10, 0, 360);
                        break;
                    case 11:
                        g.drawArc(83 - r, 66 - r, 10, 10, 0, 360);
                        g.fillArc(83 - r, 66 - r, 10, 10, 0, 360);
                        break;
                    default:
                        ;
                }
                switch (startNum % 12) {
                    case 0:
                        try {
                            centerImage = Image.createImage("/00.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(120 - r, 56 - r, 10, 10, 0, 360);
                        break;
                    case 1:
                        try {
                            centerImage = Image.createImage("/01.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(156 - r, 66 - r, 10, 10, 0, 360);
                        break;
                    case 2:
                        try {
                            centerImage = Image.createImage("/02.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(182 - r, 93 - r, 10, 10, 0, 360);
                        break;
                    case 3:
                        try {
                            centerImage = Image.createImage("/03.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(191 - r, 130 - r, 10, 10, 0, 360);
                        break;
                    case 4:
                        try {
                            centerImage = Image.createImage("/04.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(182 - r, 164 - r, 10, 10, 0, 360);
                        break;
                    case 5:
                        try {
                            centerImage = Image.createImage("/05.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(155 - r, 191 - r, 10, 10, 0, 360);
                        break;
                    case 6:
                        try {
                            centerImage = Image.createImage("/06.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(120 - r, 200 - r, 10, 10, 0, 360);
                        break;
                    case 7:
                        try {
                            centerImage = Image.createImage("/07.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(83 - r, 190 - r, 10, 10, 0, 360);
                        break;
                    case 8:
                        try {
                            centerImage = Image.createImage("/08.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(56 - r, 162 - r, 10, 10, 0, 360);
                        break;
                    case 9:
                        try {
                            centerImage = Image.createImage("/09.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(46 - r, 130 - r, 10, 10, 0, 360);
                        break;
                    case 10:
                        try {
                            centerImage = Image.createImage("/10.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(56 - r, 93 - r, 10, 10, 0, 360);
                        break;
                    case 11:
                        try {
                            centerImage = Image.createImage("/11.png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                        g.fillArc(83 - r, 66 - r, 10, 10, 0, 360);
                        break;
                    default:
                        ;
                }
            }
            //��Ϸ������
            if (state == 1) {
                if (start1 == 0) {
                    removeCommand(enter); //ȥ��enter Command;
                    removeCommand(help); //ȥ��help Command;
                    removeCommand(replay);//ȥ��replay Command;
                    removeCommand(record);//ȥ��record Command;
                    addCommand(start); //����start Command;
                } else {
                    removeCommand(start); //ȥ��help Command;
                    addCommand(stop); //����start Command;
                }
                width = getWidth();
                height = getHeight();

                if (flag == 0) {
                    g.drawImage(backImage, 0, 0, g.TOP | g.LEFT);
                    int r = 5;
                    g.setColor(0, 255, 0);
                    switch (startNum) {
                        case 0:
                            g.drawArc(120 - r, 56 - r, 10, 10, 0, 360);
                            break;
                        case 1:
                            g.drawArc(156 - r, 66 - r, 10, 10, 0, 360);
                            break;
                        case 2:
                            g.drawArc(182 - r, 93 - r, 10, 10, 0, 360);
                            break;
                        case 3:
                            g.drawArc(191 - r, 130 - r, 10, 10, 0, 360);
                            break;
                        case 4:
                            g.drawArc(182 - r, 164 - r, 10, 10, 0, 360);
                            break;
                        case 5:
                            g.drawArc(155 - r, 191 - r, 10, 10, 0, 360);
                            break;
                        case 6:
                            g.drawArc(120 - r, 200 - r, 10, 10, 0, 360);
                            break;
                        case 7:
                            g.drawArc(83 - r, 190 - r, 10, 10, 0, 360);
                            break;
                        case 8:
                            g.drawArc(56 - r, 162 - r, 10, 10, 0, 360);
                            break;
                        case 9:
                            g.drawArc(46 - r, 130 - r, 10, 10, 0, 360);
                            break;
                        case 10:
                            g.drawArc(56 - r, 93 - r, 10, 10, 0, 360);
                            break;
                        case 11:
                            g.drawArc(83 - r, 66 - r, 10, 10, 0, 360);
                            break;
                        default:
                            ;
                    }
                    switch ((startNum + counter) % 12) {
                        case 0:
                            try {
                                centerImage = Image.createImage("/00.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(120 - r, 56 - r, 10, 10, 0, 360);
                            break;
                        case 1:
                            try {
                                centerImage = Image.createImage("/01.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(156 - r, 66 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 2:
                            try {
                                centerImage = Image.createImage("/02.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(182 - r, 93 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 3:
                            try {
                                centerImage = Image.createImage("/03.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(191 - r, 130 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 4:
                            try {
                                centerImage = Image.createImage("/04.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(182 - r, 164 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 5:
                            try {
                                centerImage = Image.createImage("/05.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(155 - r, 191 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 6:
                            try {
                                centerImage = Image.createImage("/06.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(120 - r, 200 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 7:
                            try {
                                centerImage = Image.createImage("/07.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(83 - r, 190 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 8:
                            try {
                                centerImage = Image.createImage("/08.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(56 - r, 162 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 9:
                            try {
                                centerImage = Image.createImage("/09.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(46 - r, 130 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 10:
                            try {
                                centerImage = Image.createImage("/10.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(56 - r, 93 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        case 11:
                            try {
                                centerImage = Image.createImage("/11.png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            g.drawImage(centerImage, 120, 130, g.VCENTER | g.HCENTER);
                            g.fillArc(83 - r, 66 - r, 10, 10, 0, 360);
                            AlertType.ALARM.playSound(display);
                            break;
                        default:
                            ;
                    }
                } else {
                    removeCommand(stop); //ȥ��help Command;
                    addCommand(replay);
                    //��ʾgame over	
                    Image helpImage = null;
                    try {
                        helpImage = Image.createImage("/result.png");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    g.drawImage(helpImage, 0, 0, g.TOP | g.LEFT);

                    //�����Ϸ��������ʱ�����룩
                    g.setColor(120, 240, 60);
                    g.drawString(canvas.timeOpinion(time3), 24, 40, g.LEFT | g.TOP);
                    g.drawString("��������:", 80, 100, g.LEFT | g.TOP);
                    g.drawString(String.valueOf(time3), 100, 120, g.LEFT | g.TOP);
                    g.drawString("(����)", 100, 140, g.LEFT | g.TOP);
                    if (anyRecord) {
                        if (time3 < recordTime) {
                            g.drawString("ף����������˱����¼��", 20, 180, g.LEFT | g.TOP);
                            try {
                                rs = RecordStore.openRecordStore("LOCALRECORD", true);
                                recordTime = time3;
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                DataOutputStream dos = new DataOutputStream(baos);
                                try {
                                    dos.writeLong(recordTime);
                                } catch (Exception e) {
                                }
                                byte[] data = baos.toByteArray();
                                rs.setRecord(1, data, 0, data.length);
                                rs.closeRecordStore();
                            } catch (RecordStoreException e) {
                            }
                        }
                    } else {
                        try {
                            rs = RecordStore.openRecordStore("LOCALRECORD", true);
                            recordTime = time3;
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            DataOutputStream dos = new DataOutputStream(baos);
                            try {
                                dos.writeLong(recordTime);
                            } catch (Exception e) {
                            }
                            byte[] data = baos.toByteArray();
                            rs.addRecord(data, 0, data.length);
                            rs.closeRecordStore();
                        } catch (RecordStoreException e) {
                        }
                    }
                }
            }
        }

        protected String timeOpinion(long gametime) {
            if (gametime > 2000) {
                return "���ģ���Ľ���С���";
            } else if (gametime > 1000) {
                return "���ͣ���Ŭ�������Ľ����";
            } else if (gametime > 500) {
                return "���?��Ľ���к���";
            } else if (gametime > 200) {
                return "ร���Ľ���кܰ�Ŷ";
            } else if (gametime > 100) {
                return "ף������Ľ���зǳ�ϸ��";
            } else if (gametime > 50) {
                return "�����ų������������У���";
            } else {
                return "���ģ����������𣿣�";
            }
        }
    }

    class ClockTimerTask extends TimerTask {

        public final void run() {
            counter++;
            if (counter == 6) {
                timerTask.cancel();
                enable = 1;
                time1 = System.currentTimeMillis();
            }
            canvas.repaint();
        }
    }
}
