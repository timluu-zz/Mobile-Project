
import java.util.Date;
import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class RubikCanvas extends Canvas {

    private int n;
    private int r;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    int eyeshot = 0;
    int ForwardX[] = new int[10];
    int UpX[] = new int[10];
    int LeftX[] = new int[10];
    int BackX[] = new int[10];
    int DownX[] = new int[10];
    int RightX[] = new int[10];
    int ForwardY[] = new int[10];
    int UpY[] = new int[10];
    int LeftY[] = new int[10];
    int BackY[] = new int[10];
    int DownY[] = new int[10];
    int RightY[] = new int[10];
    int ForwardRx[] = new int[10];
    int UpRx[] = new int[10];
    int LeftRx[] = new int[10];
    int BackRx[] = new int[10];
    int DownRx[] = new int[10];
    int RightRx[] = new int[10];
    int ForwardRy[] = new int[10];
    int UpRy[] = new int[10];
    int LeftRy[] = new int[10];
    int BackRy[] = new int[10];
    int DownRy[] = new int[10];
    int RightRy[] = new int[10];
    int ForwardColor[] = new int[10];
    int UpColor[] = new int[10];
    int LeftColor[] = new int[10];
    int BackColor[] = new int[10];
    int DownColor[] = new int[10];
    int RightColor[] = new int[10];

    public RubikCanvas() {

        ForwardColor[0] = 0x00404040;
        if (getWidth() * 5 < getHeight() * 9) {
            r = getWidth() / 18;
        } else {
            r = getHeight() / 10;
        }
        x1 = getWidth() / 2 - r * 8;
        y1 = getHeight() / 2 - r * 8;
        x2 = getWidth() / 2 - r * 8;
        y2 = getHeight() / 2 - r * 8;
        ColorInit();
        ForwardLeftUp();
        ColorRandom();
    }

    protected void paint(Graphics g) {
        String imei = System.getProperty("IMEI");
        g.setColor(127, 127, 127);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0, 255, 255);
        g.drawString("pixel " + getWidth() + " X " + getHeight(), getWidth() / 2, 0, Graphics.TOP | Graphics.HCENTER);
        g.drawString("step��: " + n, 0, getHeight(), Graphics.BOTTOM | Graphics.LEFT);
        int i;

        if (ForwardColor[0] == 1) {
            for (i = 1; i < 10; i++) {
                g.setColor(ForwardColor[i]);
                g.fillArc(ForwardX[i], ForwardY[i], ForwardRx[i], ForwardRy[i], 0, 360);
            }
        } else {
            for (i = 1; i < 10; i++) {
                g.setColor(ForwardColor[i]);
                g.drawArc(ForwardX[i], ForwardY[i], ForwardRx[i], ForwardRy[i], 0, 360);
            }
        }
        if (UpColor[0] == 1) {
            for (i = 1; i < 10; i++) {
                g.setColor(UpColor[i]);
                g.fillArc(UpX[i], UpY[i], UpRx[i], UpRy[i], 0, 360);
            }
        } else {
            for (i = 1; i < 10; i++) {
                g.setColor(UpColor[i]);
                g.drawArc(UpX[i], UpY[i], UpRx[i], UpRy[i], 0, 360);
            }
        }
        if (LeftColor[0] == 1) {
            for (i = 1; i < 10; i++) {
                g.setColor(LeftColor[i]);
                g.fillArc(LeftX[i], LeftY[i], LeftRx[i], LeftRy[i], 0, 360);
            }
        } else {
            for (i = 1; i < 10; i++) {
                g.setColor(LeftColor[i]);
                g.drawArc(LeftX[i], LeftY[i], LeftRx[i], LeftRy[i], 0, 360);
            }
        }
        if (BackColor[0] == 1) {
            for (i = 1; i < 10; i++) {
                g.setColor(BackColor[i]);
                g.fillArc(BackX[i], BackY[i], BackRx[i], BackRy[i], 0, 360);
            }
        } else {
            for (i = 1; i < 10; i++) {
                g.setColor(BackColor[i]);
                g.drawArc(BackX[i], BackY[i], BackRx[i], BackRy[i], 0, 360);
            }
        }
        if (DownColor[0] == 1) {
            for (i = 1; i < 10; i++) {
                g.setColor(DownColor[i]);
                g.fillArc(DownX[i], DownY[i], DownRx[i], DownRy[i], 0, 360);
            }
        } else {
            for (i = 1; i < 10; i++) {
                g.setColor(DownColor[i]);
                g.drawArc(DownX[i], DownY[i], DownRx[i], DownRy[i], 0, 360);
            }
        }
        if (RightColor[0] == 1) {
            for (i = 1; i < 10; i++) {
                g.setColor(RightColor[i]);
                g.fillArc(RightX[i], RightY[i], RightRx[i], RightRy[i], 0, 360);
            }
        } else {
            for (i = 1; i < 10; i++) {
                g.setColor(RightColor[i]);
                g.drawArc(RightX[i], RightY[i], RightRx[i], RightRy[i], 0, 360);
            }
        }

        g.setColor(ForwardColor[5]);
        g.drawLine(ForwardX[1] + r * 0, ForwardY[1] + r * 0, ForwardX[1] + r * 6, ForwardY[1] + r * 0);
        g.drawLine(ForwardX[1] + r * 0, ForwardY[1] + r * 2, ForwardX[4] + r * 6, ForwardY[1] + r * 2);
        g.drawLine(ForwardX[1] + r * 0, ForwardY[1] + r * 4, ForwardX[7] + r * 6, ForwardY[1] + r * 4);
        g.drawLine(ForwardX[1] + r * 0, ForwardY[1] + r * 6, ForwardX[1] + r * 6, ForwardY[1] + r * 6);
        g.drawLine(ForwardX[1] + r * 0, ForwardY[1] + r * 0, ForwardX[1] + r * 0, ForwardY[1] + r * 6);
        g.drawLine(ForwardX[1] + r * 2, ForwardY[1] + r * 0, ForwardX[1] + r * 2, ForwardY[1] + r * 6);
        g.drawLine(ForwardX[1] + r * 4, ForwardY[1] + r * 0, ForwardX[1] + r * 4, ForwardY[1] + r * 6);
        g.drawLine(ForwardX[1] + r * 6, ForwardY[1] + r * 0, ForwardX[1] + r * 6, ForwardY[1] + r * 6);
    }

    public void ColorInit() {
        n = 250 - 2;
        int i;
        for (i = 1; i < 10; i++) {
            ForwardColor[i] = 0x00FF0000;
            UpColor[i] = 0x000000FF;
            LeftColor[i] = 0x00000000;
            BackColor[i] = 0x0000FF00;
            DownColor[i] = 0x00cc9900;
            RightColor[i] = 0x00FFFFFF;
        }
    }

    public void ColorRandom() {
        n = 0;
        Random ran = new Random((new Date()).getTime());
        int t;
        int i;
        for (i = 0; i < 24; i++) {
            t = ran.nextInt() % 6;
            t = Math.abs(t);
            switch (t) {
                case 0:
                    ForwardContrarotate();
                    break;
                case 1:
                    BackContrarotate();
                    break;
                case 2:
                    LeftContrarotate();
                    break;
                case 3:
                    RightContrarotate();
                    break;
                case 4:
                    UpContrarotate();
                    break;
                case 5:
                    DownContrarotate();
                    break;
            }
        }
    }

    public void ForwardTurnDown() {
        int MyColor[] = new int[10];
        int i;
        for (i = 1; i < 10; i++) {
            MyColor[i] = ForwardColor[i];
            ForwardColor[i] = UpColor[i];
            UpColor[i] = BackColor[i];
            BackColor[i] = DownColor[i];
            DownColor[i] = MyColor[i];
        }
        for (i = 1; i < 10; i++) {
            MyColor[i] = LeftColor[i];
        }
        LeftColor[1] = MyColor[7];
        LeftColor[2] = MyColor[4];
        LeftColor[3] = MyColor[1];
        LeftColor[4] = MyColor[8];
        LeftColor[5] = MyColor[5];
        LeftColor[6] = MyColor[2];
        LeftColor[7] = MyColor[9];
        LeftColor[8] = MyColor[6];
        LeftColor[9] = MyColor[3];
        for (i = 1; i < 10; i++) {
            MyColor[i] = RightColor[i];
        }
        RightColor[1] = MyColor[3];
        RightColor[2] = MyColor[6];
        RightColor[3] = MyColor[9];
        RightColor[4] = MyColor[2];
        RightColor[5] = MyColor[5];
        RightColor[6] = MyColor[8];
        RightColor[7] = MyColor[1];
        RightColor[8] = MyColor[4];
        RightColor[9] = MyColor[7];
    }

    public void ForwardTurnLeft() {
        int MyColor[] = new int[10];
        int i;
        for (i = 1; i < 10; i++) {
            MyColor[i] = ForwardColor[i];
            ForwardColor[i] = LeftColor[i];
        }
        for (i = 1; i < 10; i++) {
            LeftColor[i] = BackColor[10 - i];
        }
        for (i = 1; i < 10; i++) {
            BackColor[10 - i] = RightColor[i];
        }
        for (i = 1; i < 10; i++) {
            RightColor[i] = MyColor[i];
        }
        for (i = 1; i < 10; i++) {
            MyColor[i] = UpColor[i];
        }
        UpColor[1] = MyColor[3];
        UpColor[2] = MyColor[6];
        UpColor[3] = MyColor[9];
        UpColor[4] = MyColor[2];
        UpColor[5] = MyColor[5];
        UpColor[6] = MyColor[8];
        UpColor[7] = MyColor[1];
        UpColor[8] = MyColor[4];
        UpColor[9] = MyColor[7];
        for (i = 1; i < 10; i++) {
            MyColor[i] = DownColor[i];
        }
        DownColor[1] = MyColor[7];
        DownColor[2] = MyColor[4];
        DownColor[3] = MyColor[1];
        DownColor[4] = MyColor[8];
        DownColor[5] = MyColor[5];
        DownColor[6] = MyColor[2];
        DownColor[7] = MyColor[9];
        DownColor[8] = MyColor[6];
        DownColor[9] = MyColor[3];
    }

    public void ForwardContrarotate() {
        int MyColor[] = new int[10];
        int i;
        for (i = 1; i < 10; i++) {
            MyColor[i] = ForwardColor[i];
        }
        ForwardColor[1] = MyColor[3];
        ForwardColor[2] = MyColor[6];
        ForwardColor[3] = MyColor[9];
        ForwardColor[4] = MyColor[2];
        ForwardColor[5] = MyColor[5];
        ForwardColor[6] = MyColor[8];
        ForwardColor[7] = MyColor[1];
        ForwardColor[8] = MyColor[4];
        ForwardColor[9] = MyColor[7];
        for (i = 1; i < 10; i++) {
            MyColor[i] = UpColor[i];
        }
        UpColor[7] = RightColor[1];
        UpColor[8] = RightColor[4];
        UpColor[9] = RightColor[7];
        RightColor[1] = DownColor[3];
        RightColor[4] = DownColor[2];
        RightColor[7] = DownColor[1];
        DownColor[1] = LeftColor[3];
        DownColor[2] = LeftColor[6];
        DownColor[3] = LeftColor[9];
        LeftColor[3] = MyColor[9];
        LeftColor[6] = MyColor[8];
        LeftColor[9] = MyColor[7];
    }

    public void BackContrarotate() {
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardContrarotate();
        ForwardTurnDown();
        ForwardTurnDown();
    }

    public void LeftContrarotate() {
        ForwardTurnLeft();
        ForwardContrarotate();
        ForwardTurnLeft();
        ForwardTurnLeft();
        ForwardTurnLeft();
    }

    public void RightContrarotate() {
        ForwardTurnLeft();
        ForwardTurnLeft();
        ForwardTurnLeft();
        ForwardContrarotate();
        ForwardTurnLeft();
    }

    public void UpContrarotate() {
        ForwardTurnDown();
        ForwardContrarotate();
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardTurnDown();
    }

    public void DownContrarotate() {
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardContrarotate();
        ForwardTurnDown();
    }

    public void ForwardClockwise() {
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardContrarotate();
    }

    public void BackClockwise() {
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardTurnDown();
        ForwardTurnDown();
    }

    public void LeftClockwise() {
        ForwardTurnLeft();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardTurnLeft();
        ForwardTurnLeft();
        ForwardTurnLeft();
    }

    public void RightClockwise() {
        ForwardTurnLeft();
        ForwardTurnLeft();
        ForwardTurnLeft();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardTurnLeft();
    }

    public void UpClockwise() {
        ForwardTurnDown();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardTurnDown();
    }

    public void DownClockwise() {
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardTurnDown();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardContrarotate();
        ForwardTurnDown();
    }

    public void ForwardLeftUp() {
        ForwardColor[0] = 1;
        UpColor[0] = 1;
        LeftColor[0] = 1;
        BackColor[0] = 0;
        DownColor[0] = 0;
        RightColor[0] = 0;
        int i;
        for (i = 1; i < 10; i++) {
            ForwardRx[i] = r * 2;
            ForwardRy[i] = 2 * r;
        }
        ForwardX[1] = getWidth() / 2 - r * 6;
        ForwardX[2] = getWidth() / 2 - r * 4;
        ForwardX[3] = getWidth() / 2 - r * 2;
        ForwardX[4] = getWidth() / 2 - r * 6;
        ForwardX[5] = getWidth() / 2 - r * 4;
        ForwardX[6] = getWidth() / 2 - r * 2;
        ForwardX[7] = getWidth() / 2 - r * 6;
        ForwardX[8] = getWidth() / 2 - r * 4;
        ForwardX[9] = getWidth() / 2 - r * 2;
        ForwardY[1] = getHeight() / 2 + r * 0 - r * 2;
        ForwardY[2] = getHeight() / 2 + r * 0 - r * 2;
        ForwardY[3] = getHeight() / 2 + r * 0 - r * 2;
        ForwardY[4] = getHeight() / 2 + r * 2 - r * 2;
        ForwardY[5] = getHeight() / 2 + r * 2 - r * 2;
        ForwardY[6] = getHeight() / 2 + r * 2 - r * 2;
        ForwardY[7] = getHeight() / 2 + r * 4 - r * 2;
        ForwardY[8] = getHeight() / 2 + r * 4 - r * 2;
        ForwardY[9] = getHeight() / 2 + r * 4 - r * 2;
        for (i = 1; i < 10; i++) {
            UpRx[i] = r * 8 / 4;
            UpRy[i] = 1 * r;
        }
        UpX[1] = getWidth() / 2 - r / 3 - r * 8;
        UpX[2] = getWidth() / 2 - r / 3 - r * 6;
        UpX[3] = getWidth() / 2 - r / 3 - r * 4;
        UpX[4] = getWidth() / 2 - r / 3 - r * 7;
        UpX[5] = getWidth() / 2 - r / 3 - r * 5;
        UpX[6] = getWidth() / 2 - r / 3 - r * 3;
        UpX[7] = getWidth() / 2 - r / 3 - r * 6;
        UpX[8] = getWidth() / 2 - r / 3 - r * 4;
        UpX[9] = getWidth() / 2 - r / 3 - r * 2;
        UpY[1] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[2] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[3] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[4] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[5] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[6] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[7] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[8] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[9] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        for (i = 1; i < 10; i++) {
            LeftRx[i] = r * 1;
            LeftRy[i] = r * 8 / 4;
        }
        LeftX[1] = getWidth() / 2 - r * 6 - r * 3;
        LeftX[2] = getWidth() / 2 - r * 6 - r * 2;
        LeftX[3] = getWidth() / 2 - r * 6 - r * 1;
        LeftX[4] = getWidth() / 2 - r * 6 - r * 3;
        LeftX[5] = getWidth() / 2 - r * 6 - r * 2;
        LeftX[6] = getWidth() / 2 - r * 6 - r * 1;
        LeftX[7] = getWidth() / 2 - r * 6 - r * 3;
        LeftX[8] = getWidth() / 2 - r * 6 - r * 2;
        LeftX[9] = getWidth() / 2 - r * 6 - r * 1;
        LeftY[1] = getHeight() / 2 - r / 3 + r * 0 - r * 2 - r * 2;
        LeftY[2] = getHeight() / 2 - r / 3 + r * 0 - r * 1 - r * 2;
        LeftY[3] = getHeight() / 2 - r / 3 + r * 0 - r * 0 - r * 2;
        LeftY[4] = getHeight() / 2 - r / 3 + r * 2 - r * 2 - r * 2;
        LeftY[5] = getHeight() / 2 - r / 3 + r * 2 - r * 1 - r * 2;
        LeftY[6] = getHeight() / 2 - r / 3 + r * 2 - r * 0 - r * 2;
        LeftY[7] = getHeight() / 2 - r / 3 + r * 4 - r * 2 - r * 2;
        LeftY[8] = getHeight() / 2 - r / 3 + r * 4 - r * 1 - r * 2;
        LeftY[9] = getHeight() / 2 - r / 3 + r * 4 - r * 0 - r * 2;
        for (i = 1; i < 10; i++) {
            BackRx[i] = r * 2;
            BackRy[i] = 2 * r;
        }
        BackX[1] = getWidth() / 2 + r * 0;
        BackX[2] = getWidth() / 2 + r * 2;
        BackX[3] = getWidth() / 2 + r * 4;
        BackX[4] = getWidth() / 2 + r * 0;
        BackX[5] = getWidth() / 2 + r * 2;
        BackX[6] = getWidth() / 2 + r * 4;
        BackX[7] = getWidth() / 2 + r * 0;
        BackX[8] = getWidth() / 2 + r * 2;
        BackX[9] = getWidth() / 2 + r * 4;
        BackY[1] = getHeight() / 2 - r * 2 + r * 2;
        BackY[2] = getHeight() / 2 - r * 2 + r * 2;
        BackY[3] = getHeight() / 2 - r * 2 + r * 2;
        BackY[4] = getHeight() / 2 - r * 4 + r * 2;
        BackY[5] = getHeight() / 2 - r * 4 + r * 2;
        BackY[6] = getHeight() / 2 - r * 4 + r * 2;
        BackY[7] = getHeight() / 2 - r * 6 + r * 2;
        BackY[8] = getHeight() / 2 - r * 6 + r * 2;
        BackY[9] = getHeight() / 2 - r * 6 + r * 2;
        for (i = 1; i < 10; i++) {
            DownRx[i] = r * 2;
            DownRy[i] = 1 * r;
        }
        DownX[1] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        DownX[2] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        DownX[3] = getWidth() / 2 + r / 3 + r * 8 - r * 2;
        DownX[4] = getWidth() / 2 + r / 3 + r * 8 - r * 7;
        DownX[5] = getWidth() / 2 + r / 3 + r * 8 - r * 5;
        DownX[6] = getWidth() / 2 + r / 3 + r * 8 - r * 3;
        DownX[7] = getWidth() / 2 + r / 3 + r * 8 - r * 8;
        DownX[8] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        DownX[9] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        DownY[1] = getHeight() / 2 + r * 2 + r * 2;
        DownY[2] = getHeight() / 2 + r * 2 + r * 2;
        DownY[3] = getHeight() / 2 + r * 2 + r * 2;
        DownY[4] = getHeight() / 2 + r * 1 + r * 2;
        DownY[5] = getHeight() / 2 + r * 1 + r * 2;
        DownY[6] = getHeight() / 2 + r * 1 + r * 2;
        DownY[7] = getHeight() / 2 + r * 0 + r * 2;
        DownY[8] = getHeight() / 2 + r * 0 + r * 2;
        DownY[9] = getHeight() / 2 + r * 0 + r * 2;
        for (i = 1; i < 10; i++) {
            RightRx[i] = r * 1;
            RightRy[i] = 2 * r;
        }
        RightX[1] = getWidth() / 2 + r * 6 + r * 2;
        RightX[2] = getWidth() / 2 + r * 6 + r * 1;
        RightX[3] = getWidth() / 2 + r * 6 + r * 0;
        RightX[4] = getWidth() / 2 + r * 6 + r * 2;
        RightX[5] = getWidth() / 2 + r * 6 + r * 1;
        RightX[6] = getWidth() / 2 + r * 6 + r * 0;
        RightX[7] = getWidth() / 2 + r * 6 + r * 2;
        RightX[8] = getWidth() / 2 + r * 6 + r * 1;
        RightX[9] = getWidth() / 2 + r * 6 + r * 0;
        RightY[1] = getHeight() / 2 + r / 3 - r * 4 + r * 0 - r * 0 + r * 2;
        RightY[2] = getHeight() / 2 + r / 3 - r * 4 + r * 0 - r * 1 + r * 2;
        RightY[3] = getHeight() / 2 + r / 3 - r * 4 + r * 0 - r * 2 + r * 2;
        RightY[4] = getHeight() / 2 + r / 3 - r * 4 + r * 2 - r * 0 + r * 2;
        RightY[5] = getHeight() / 2 + r / 3 - r * 4 + r * 2 - r * 1 + r * 2;
        RightY[6] = getHeight() / 2 + r / 3 - r * 4 + r * 2 - r * 2 + r * 2;
        RightY[7] = getHeight() / 2 + r / 3 - r * 4 + r * 4 - r * 0 + r * 2;
        RightY[8] = getHeight() / 2 + r / 3 - r * 4 + r * 4 - r * 1 + r * 2;
        RightY[9] = getHeight() / 2 + r / 3 - r * 4 + r * 4 - r * 2 + r * 2;
    }

    public void ForwardLeftDown() {
        ForwardColor[0] = 1;
        UpColor[0] = 0;
        LeftColor[0] = 1;
        BackColor[0] = 0;
        DownColor[0] = 1;
        RightColor[0] = 0;
        int i;
        for (i = 1; i < 10; i++) {
            ForwardRx[i] = r * 2;
            ForwardRy[i] = 2 * r;
        }
        ForwardX[1] = getWidth() / 2 - r * 6;
        ForwardX[2] = getWidth() / 2 - r * 4;
        ForwardX[3] = getWidth() / 2 - r * 2;
        ForwardX[4] = getWidth() / 2 - r * 6;
        ForwardX[5] = getWidth() / 2 - r * 4;
        ForwardX[6] = getWidth() / 2 - r * 2;
        ForwardX[7] = getWidth() / 2 - r * 6;
        ForwardX[8] = getWidth() / 2 - r * 4;
        ForwardX[9] = getWidth() / 2 - r * 2;
        ForwardY[1] = getHeight() / 2 + r * 0 - r * 4;
        ForwardY[2] = getHeight() / 2 + r * 0 - r * 4;
        ForwardY[3] = getHeight() / 2 + r * 0 - r * 4;
        ForwardY[4] = getHeight() / 2 + r * 2 - r * 4;
        ForwardY[5] = getHeight() / 2 + r * 2 - r * 4;
        ForwardY[6] = getHeight() / 2 + r * 2 - r * 4;
        ForwardY[7] = getHeight() / 2 + r * 4 - r * 4;
        ForwardY[8] = getHeight() / 2 + r * 4 - r * 4;
        ForwardY[9] = getHeight() / 2 + r * 4 - r * 4;
        for (i = 1; i < 10; i++) {
            BackRx[i] = r * 2;
            BackRy[i] = 2 * r;
        }
        BackX[1] = getWidth() / 2 + r * 0;
        BackX[2] = getWidth() / 2 + r * 2;
        BackX[3] = getWidth() / 2 + r * 4;
        BackX[4] = getWidth() / 2 + r * 0;
        BackX[5] = getWidth() / 2 + r * 2;
        BackX[6] = getWidth() / 2 + r * 4;
        BackX[7] = getWidth() / 2 + r * 0;
        BackX[8] = getWidth() / 2 + r * 2;
        BackX[9] = getWidth() / 2 + r * 4;
        BackY[1] = getHeight() / 2 - r * 2 + r * 4;
        BackY[2] = getHeight() / 2 - r * 2 + r * 4;
        BackY[3] = getHeight() / 2 - r * 2 + r * 4;
        BackY[4] = getHeight() / 2 - r * 4 + r * 4;
        BackY[5] = getHeight() / 2 - r * 4 + r * 4;
        BackY[6] = getHeight() / 2 - r * 4 + r * 4;
        BackY[7] = getHeight() / 2 - r * 6 + r * 4;
        BackY[8] = getHeight() / 2 - r * 6 + r * 4;
        BackY[9] = getHeight() / 2 - r * 6 + r * 4;
        for (i = 1; i < 10; i++) {
            UpRx[i] = r * 8 / 4;
            UpRy[i] = 1 * r;
        }
        UpX[7] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        UpX[8] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        UpX[9] = getWidth() / 2 + r / 3 + r * 8 - r * 2;
        UpX[4] = getWidth() / 2 + r / 3 + r * 8 - r * 7;
        UpX[5] = getWidth() / 2 + r / 3 + r * 8 - r * 5;
        UpX[6] = getWidth() / 2 + r / 3 + r * 8 - r * 3;
        UpX[1] = getWidth() / 2 + r / 3 + r * 8 - r * 8;
        UpX[2] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        UpX[3] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        UpY[7] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[8] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[9] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[4] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[5] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[6] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[1] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[2] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[3] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        for (i = 1; i < 10; i++) {
            DownRx[i] = r * 2;
            DownRy[i] = 1 * r;
        }
        DownX[7] = getWidth() / 2 - r / 3 - r * 8;
        DownX[8] = getWidth() / 2 - r / 3 - r * 6;
        DownX[9] = getWidth() / 2 - r / 3 - r * 4;
        DownX[4] = getWidth() / 2 - r / 3 - r * 7;
        DownX[5] = getWidth() / 2 - r / 3 - r * 5;
        DownX[6] = getWidth() / 2 - r / 3 - r * 3;
        DownX[1] = getWidth() / 2 - r / 3 - r * 6;
        DownX[2] = getWidth() / 2 - r / 3 - r * 4;
        DownX[3] = getWidth() / 2 - r / 3 - r * 2;
        DownY[7] = getHeight() / 2 + r * 2 + r * 2;
        DownY[8] = getHeight() / 2 + r * 2 + r * 2;
        DownY[9] = getHeight() / 2 + r * 2 + r * 2;
        DownY[4] = getHeight() / 2 + r * 1 + r * 2;
        DownY[5] = getHeight() / 2 + r * 1 + r * 2;
        DownY[6] = getHeight() / 2 + r * 1 + r * 2;
        DownY[1] = getHeight() / 2 + r * 0 + r * 2;
        DownY[2] = getHeight() / 2 + r * 0 + r * 2;
        DownY[3] = getHeight() / 2 + r * 0 + r * 2;
        for (i = 1; i < 10; i++) {
            LeftRx[i] = r * 1;
            LeftRy[i] = r * 8 / 4;
        }
        LeftX[1] = getWidth() / 2 - r * 6 - r * 3;
        LeftX[2] = getWidth() / 2 - r * 6 - r * 2;
        LeftX[3] = getWidth() / 2 - r * 6 - r * 1;
        LeftX[4] = getWidth() / 2 - r * 6 - r * 3;
        LeftX[5] = getWidth() / 2 - r * 6 - r * 2;
        LeftX[6] = getWidth() / 2 - r * 6 - r * 1;
        LeftX[7] = getWidth() / 2 - r * 6 - r * 3;
        LeftX[8] = getWidth() / 2 - r * 6 - r * 2;
        LeftX[9] = getWidth() / 2 - r * 6 - r * 1;
        LeftY[1] = getHeight() / 2 + r / 3 + r * 0 - r * 0 - r * 2;
        LeftY[2] = getHeight() / 2 + r / 3 + r * 0 - r * 1 - r * 2;
        LeftY[3] = getHeight() / 2 + r / 3 + r * 0 - r * 2 - r * 2;
        LeftY[4] = getHeight() / 2 + r / 3 + r * 2 - r * 0 - r * 2;
        LeftY[5] = getHeight() / 2 + r / 3 + r * 2 - r * 1 - r * 2;
        LeftY[6] = getHeight() / 2 + r / 3 + r * 2 - r * 2 - r * 2;
        LeftY[7] = getHeight() / 2 + r / 3 + r * 4 - r * 0 - r * 2;
        LeftY[8] = getHeight() / 2 + r / 3 + r * 4 - r * 1 - r * 2;
        LeftY[9] = getHeight() / 2 + r / 3 + r * 4 - r * 2 - r * 2;
        for (i = 1; i < 10; i++) {
            RightRx[i] = r * 1;
            RightRy[i] = 2 * r;
        }
        RightX[1] = getWidth() / 2 + r * 6 + r * 2;
        RightX[2] = getWidth() / 2 + r * 6 + r * 1;
        RightX[3] = getWidth() / 2 + r * 6 + r * 0;
        RightX[4] = getWidth() / 2 + r * 6 + r * 2;
        RightX[5] = getWidth() / 2 + r * 6 + r * 1;
        RightX[6] = getWidth() / 2 + r * 6 + r * 0;
        RightX[7] = getWidth() / 2 + r * 6 + r * 2;
        RightX[8] = getWidth() / 2 + r * 6 + r * 1;
        RightX[9] = getWidth() / 2 + r * 6 + r * 0;
        RightY[1] = getHeight() / 2 - r / 3 - r * 4 + r * 0 - r * 2 + r * 2;
        RightY[2] = getHeight() / 2 - r / 3 - r * 4 + r * 0 - r * 1 + r * 2;
        RightY[3] = getHeight() / 2 - r / 3 - r * 4 + r * 0 - r * 0 + r * 2;
        RightY[4] = getHeight() / 2 - r / 3 - r * 4 + r * 2 - r * 2 + r * 2;
        RightY[5] = getHeight() / 2 - r / 3 - r * 4 + r * 2 - r * 1 + r * 2;
        RightY[6] = getHeight() / 2 - r / 3 - r * 4 + r * 2 - r * 0 + r * 2;
        RightY[7] = getHeight() / 2 - r / 3 - r * 4 + r * 4 - r * 2 + r * 2;
        RightY[8] = getHeight() / 2 - r / 3 - r * 4 + r * 4 - r * 1 + r * 2;
        RightY[9] = getHeight() / 2 - r / 3 - r * 4 + r * 4 - r * 0 + r * 2;
    }

    public void ForwardRightDown() {
        ForwardColor[0] = 1;
        UpColor[0] = 0;
        LeftColor[0] = 0;
        BackColor[0] = 0;
        DownColor[0] = 1;
        RightColor[0] = 1;
        int i;
        for (i = 1; i < 10; i++) {
            ForwardRx[i] = r * 2;
            ForwardRy[i] = 2 * r;
        }
        ForwardX[1] = getWidth() / 2 - r * 6 - r * 3;
        ForwardX[2] = getWidth() / 2 - r * 4 - r * 3;
        ForwardX[3] = getWidth() / 2 - r * 2 - r * 3;
        ForwardX[4] = getWidth() / 2 - r * 6 - r * 3;
        ForwardX[5] = getWidth() / 2 - r * 4 - r * 3;
        ForwardX[6] = getWidth() / 2 - r * 2 - r * 3;
        ForwardX[7] = getWidth() / 2 - r * 6 - r * 3;
        ForwardX[8] = getWidth() / 2 - r * 4 - r * 3;
        ForwardX[9] = getWidth() / 2 - r * 2 - r * 3;
        ForwardY[1] = getHeight() / 2 + r * 0 - r * 4;
        ForwardY[2] = getHeight() / 2 + r * 0 - r * 4;
        ForwardY[3] = getHeight() / 2 + r * 0 - r * 4;
        ForwardY[4] = getHeight() / 2 + r * 2 - r * 4;
        ForwardY[5] = getHeight() / 2 + r * 2 - r * 4;
        ForwardY[6] = getHeight() / 2 + r * 2 - r * 4;
        ForwardY[7] = getHeight() / 2 + r * 4 - r * 4;
        ForwardY[8] = getHeight() / 2 + r * 4 - r * 4;
        ForwardY[9] = getHeight() / 2 + r * 4 - r * 4;
        for (i = 1; i < 10; i++) {
            BackRx[i] = r * 2;
            BackRy[i] = 2 * r;
        }
        BackX[1] = getWidth() / 2 + r * 0 + r * 3;
        BackX[2] = getWidth() / 2 + r * 2 + r * 3;
        BackX[3] = getWidth() / 2 + r * 4 + r * 3;
        BackX[4] = getWidth() / 2 + r * 0 + r * 3;
        BackX[5] = getWidth() / 2 + r * 2 + r * 3;
        BackX[6] = getWidth() / 2 + r * 4 + r * 3;
        BackX[7] = getWidth() / 2 + r * 0 + r * 3;
        BackX[8] = getWidth() / 2 + r * 2 + r * 3;
        BackX[9] = getWidth() / 2 + r * 4 + r * 3;
        BackY[1] = getHeight() / 2 - r * 2 + r * 4;
        BackY[2] = getHeight() / 2 - r * 2 + r * 4;
        BackY[3] = getHeight() / 2 - r * 2 + r * 4;
        BackY[4] = getHeight() / 2 - r * 4 + r * 4;
        BackY[5] = getHeight() / 2 - r * 4 + r * 4;
        BackY[6] = getHeight() / 2 - r * 4 + r * 4;
        BackY[7] = getHeight() / 2 - r * 6 + r * 4;
        BackY[8] = getHeight() / 2 - r * 6 + r * 4;
        BackY[9] = getHeight() / 2 - r * 6 + r * 4;
        for (i = 1; i < 10; i++) {
            UpRx[i] = r * 8 / 4;
            UpRy[i] = 1 * r;
        }
        UpX[1] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        UpX[2] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        UpX[3] = getWidth() / 2 + r / 3 + r * 8 - r * 2;
        UpX[4] = getWidth() / 2 + r / 3 + r * 8 - r * 7;
        UpX[5] = getWidth() / 2 + r / 3 + r * 8 - r * 5;
        UpX[6] = getWidth() / 2 + r / 3 + r * 8 - r * 3;
        UpX[7] = getWidth() / 2 + r / 3 + r * 8 - r * 8;
        UpX[8] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        UpX[9] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        UpY[1] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[2] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[3] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[4] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[5] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[6] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[7] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[8] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[9] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        for (i = 1; i < 10; i++) {
            DownRx[i] = r * 2;
            DownRy[i] = 1 * r;
        }
        DownX[1] = getWidth() / 2 - r / 3 - r / 3 - r * 8;
        DownX[2] = getWidth() / 2 - r / 3 - r / 3 - r * 6;
        DownX[3] = getWidth() / 2 - r / 3 - r / 3 - r * 4;
        DownX[4] = getWidth() / 2 - r / 3 - r / 3 - r * 7;
        DownX[5] = getWidth() / 2 - r / 3 - r / 3 - r * 5;
        DownX[6] = getWidth() / 2 - r / 3 - r / 3 - r * 3;
        DownX[7] = getWidth() / 2 - r / 3 - r / 3 - r * 6;
        DownX[8] = getWidth() / 2 - r / 3 - r / 3 - r * 4;
        DownX[9] = getWidth() / 2 - r / 3 - r / 3 - r * 2;
        DownY[1] = getHeight() / 2 + r * 0 + r * 2;
        DownY[2] = getHeight() / 2 + r * 0 + r * 2;
        DownY[3] = getHeight() / 2 + r * 0 + r * 2;
        DownY[4] = getHeight() / 2 + r * 1 + r * 2;
        DownY[5] = getHeight() / 2 + r * 1 + r * 2;
        DownY[6] = getHeight() / 2 + r * 1 + r * 2;
        DownY[7] = getHeight() / 2 + r * 2 + r * 2;
        DownY[8] = getHeight() / 2 + r * 2 + r * 2;
        DownY[9] = getHeight() / 2 + r * 2 + r * 2;
        for (i = 1; i < 10; i++) {
            LeftRx[i] = r * 1;
            LeftRy[i] = r * 8 / 4;
        }
        LeftX[1] = getWidth() / 2 + r * 6 - r * 1 - r * 3;
        LeftX[2] = getWidth() / 2 + r * 6 - r * 2 - r * 3;
        LeftX[3] = getWidth() / 2 + r * 6 - r * 3 - r * 3;
        LeftX[4] = getWidth() / 2 + r * 6 - r * 1 - r * 3;
        LeftX[5] = getWidth() / 2 + r * 6 - r * 2 - r * 3;
        LeftX[6] = getWidth() / 2 + r * 6 - r * 3 - r * 3;
        LeftX[7] = getWidth() / 2 + r * 6 - r * 1 - r * 3;
        LeftX[8] = getWidth() / 2 + r * 6 - r * 2 - r * 3;
        LeftX[9] = getWidth() / 2 + r * 6 - r * 3 - r * 3;
        LeftY[1] = getHeight() / 2 - r / 3 + r * 0 - r * 0 - r * 2;
        LeftY[2] = getHeight() / 2 - r / 3 + r * 0 - r * 1 - r * 2;
        LeftY[3] = getHeight() / 2 - r / 3 + r * 0 - r * 2 - r * 2;
        LeftY[4] = getHeight() / 2 - r / 3 + r * 2 - r * 0 - r * 2;
        LeftY[5] = getHeight() / 2 - r / 3 + r * 2 - r * 1 - r * 2;
        LeftY[6] = getHeight() / 2 - r / 3 + r * 2 - r * 2 - r * 2;
        LeftY[7] = getHeight() / 2 - r / 3 + r * 4 - r * 0 - r * 2;
        LeftY[8] = getHeight() / 2 - r / 3 + r * 4 - r * 1 - r * 2;
        LeftY[9] = getHeight() / 2 - r / 3 + r * 4 - r * 2 - r * 2;
        for (i = 1; i < 10; i++) {
            RightRx[i] = r * 1;
            RightRy[i] = 2 * r;
        }
        RightX[1] = getWidth() / 2 - r * 6 + r * 0 + r * 3;
        RightX[2] = getWidth() / 2 - r * 6 + r * 1 + r * 3;
        RightX[3] = getWidth() / 2 - r * 6 + r * 2 + r * 3;
        RightX[4] = getWidth() / 2 - r * 6 + r * 0 + r * 3;
        RightX[5] = getWidth() / 2 - r * 6 + r * 1 + r * 3;
        RightX[6] = getWidth() / 2 - r * 6 + r * 2 + r * 3;
        RightX[7] = getWidth() / 2 - r * 6 + r * 0 + r * 3;
        RightX[8] = getWidth() / 2 - r * 6 + r * 1 + r * 3;
        RightX[9] = getWidth() / 2 - r * 6 + r * 2 + r * 3;
        RightY[1] = getHeight() / 2 + r / 3 - r * 4 + r * 0 - r * 2 + r * 2;
        RightY[2] = getHeight() / 2 + r / 3 - r * 4 + r * 0 - r * 1 + r * 2;
        RightY[3] = getHeight() / 2 + r / 3 - r * 4 + r * 0 - r * 0 + r * 2;
        RightY[4] = getHeight() / 2 + r / 3 - r * 4 + r * 2 - r * 2 + r * 2;
        RightY[5] = getHeight() / 2 + r / 3 - r * 4 + r * 2 - r * 1 + r * 2;
        RightY[6] = getHeight() / 2 + r / 3 - r * 4 + r * 2 - r * 0 + r * 2;
        RightY[7] = getHeight() / 2 + r / 3 - r * 4 + r * 4 - r * 2 + r * 2;
        RightY[8] = getHeight() / 2 + r / 3 - r * 4 + r * 4 - r * 1 + r * 2;
        RightY[9] = getHeight() / 2 + r / 3 - r * 4 + r * 4 - r * 0 + r * 2;
    }

    public void ForwardRightUp() {
        ForwardColor[0] = 1;
        UpColor[0] = 1;
        LeftColor[0] = 0;
        BackColor[0] = 0;
        DownColor[0] = 0;
        RightColor[0] = 1;
        int i;
        for (i = 1; i < 10; i++) {
            ForwardRx[i] = r * 2;
            ForwardRy[i] = 2 * r;
        }
        ForwardX[1] = getWidth() / 2 - r * 6 - r * 3;
        ForwardX[2] = getWidth() / 2 - r * 4 - r * 3;
        ForwardX[3] = getWidth() / 2 - r * 2 - r * 3;
        ForwardX[4] = getWidth() / 2 - r * 6 - r * 3;
        ForwardX[5] = getWidth() / 2 - r * 4 - r * 3;
        ForwardX[6] = getWidth() / 2 - r * 2 - r * 3;
        ForwardX[7] = getWidth() / 2 - r * 6 - r * 3;
        ForwardX[8] = getWidth() / 2 - r * 4 - r * 3;
        ForwardX[9] = getWidth() / 2 - r * 2 - r * 3;
        ForwardY[1] = getHeight() / 2 + r * 0 - r * 2;
        ForwardY[2] = getHeight() / 2 + r * 0 - r * 2;
        ForwardY[3] = getHeight() / 2 + r * 0 - r * 2;
        ForwardY[4] = getHeight() / 2 + r * 2 - r * 2;
        ForwardY[5] = getHeight() / 2 + r * 2 - r * 2;
        ForwardY[6] = getHeight() / 2 + r * 2 - r * 2;
        ForwardY[7] = getHeight() / 2 + r * 4 - r * 2;
        ForwardY[8] = getHeight() / 2 + r * 4 - r * 2;
        ForwardY[9] = getHeight() / 2 + r * 4 - r * 2;
        for (i = 1; i < 10; i++) {
            BackRx[i] = r * 2;
            BackRy[i] = 2 * r;
        }
        BackX[1] = getWidth() / 2 + r * 0 + r * 3;
        BackX[2] = getWidth() / 2 + r * 2 + r * 3;
        BackX[3] = getWidth() / 2 + r * 4 + r * 3;
        BackX[4] = getWidth() / 2 + r * 0 + r * 3;
        BackX[5] = getWidth() / 2 + r * 2 + r * 3;
        BackX[6] = getWidth() / 2 + r * 4 + r * 3;
        BackX[7] = getWidth() / 2 + r * 0 + r * 3;
        BackX[8] = getWidth() / 2 + r * 2 + r * 3;
        BackX[9] = getWidth() / 2 + r * 4 + r * 3;
        BackY[1] = getHeight() / 2 - r * 2 + r * 2;
        BackY[2] = getHeight() / 2 - r * 2 + r * 2;
        BackY[3] = getHeight() / 2 - r * 2 + r * 2;
        BackY[4] = getHeight() / 2 - r * 4 + r * 2;
        BackY[5] = getHeight() / 2 - r * 4 + r * 2;
        BackY[6] = getHeight() / 2 - r * 4 + r * 2;
        BackY[7] = getHeight() / 2 - r * 6 + r * 2;
        BackY[8] = getHeight() / 2 - r * 6 + r * 2;
        BackY[9] = getHeight() / 2 - r * 6 + r * 2;
        for (i = 1; i < 10; i++) {
            UpRx[i] = r * 8 / 4;
            UpRy[i] = 1 * r;
        }
        UpX[7] = getWidth() / 2 - r / 3 - r * 8;
        UpX[8] = getWidth() / 2 - r / 3 - r * 6;
        UpX[9] = getWidth() / 2 - r / 3 - r * 4;
        UpX[4] = getWidth() / 2 - r / 3 - r * 7;
        UpX[5] = getWidth() / 2 - r / 3 - r * 5;
        UpX[6] = getWidth() / 2 - r / 3 - r * 3;
        UpX[1] = getWidth() / 2 - r / 3 - r * 6;
        UpX[2] = getWidth() / 2 - r / 3 - r * 4;
        UpX[3] = getWidth() / 2 - r / 3 - r * 2;
        UpY[1] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[2] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[3] = getHeight() / 2 - r * 3 + r * 0 - r * 2;
        UpY[4] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[5] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[6] = getHeight() / 2 - r * 3 + r * 1 - r * 2;
        UpY[7] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[8] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        UpY[9] = getHeight() / 2 - r * 3 + r * 2 - r * 2;
        for (i = 1; i < 10; i++) {
            DownRx[i] = r * 2;
            DownRy[i] = 1 * r;
        }
        DownX[7] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        DownX[8] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        DownX[9] = getWidth() / 2 + r / 3 + r * 8 - r * 2;
        DownX[4] = getWidth() / 2 + r / 3 + r * 8 - r * 7;
        DownX[5] = getWidth() / 2 + r / 3 + r * 8 - r * 5;
        DownX[6] = getWidth() / 2 + r / 3 + r * 8 - r * 3;
        DownX[1] = getWidth() / 2 + r / 3 + r * 8 - r * 8;
        DownX[2] = getWidth() / 2 + r / 3 + r * 8 - r * 6;
        DownX[3] = getWidth() / 2 + r / 3 + r * 8 - r * 4;
        DownY[1] = getHeight() / 2 + r * 2 + r * 2;
        DownY[2] = getHeight() / 2 + r * 2 + r * 2;
        DownY[3] = getHeight() / 2 + r * 2 + r * 2;
        DownY[4] = getHeight() / 2 + r * 1 + r * 2;
        DownY[5] = getHeight() / 2 + r * 1 + r * 2;
        DownY[6] = getHeight() / 2 + r * 1 + r * 2;
        DownY[7] = getHeight() / 2 + r * 0 + r * 2;
        DownY[8] = getHeight() / 2 + r * 0 + r * 2;
        DownY[9] = getHeight() / 2 + r * 0 + r * 2;
        for (i = 1; i < 10; i++) {
            LeftRx[i] = r * 1;
            LeftRy[i] = r * 8 / 4;
        }
        LeftX[1] = getWidth() / 2 + r * 6 - r * 1 - r * 3;
        LeftX[2] = getWidth() / 2 + r * 6 - r * 2 - r * 3;
        LeftX[3] = getWidth() / 2 + r * 6 - r * 3 - r * 3;
        LeftX[4] = getWidth() / 2 + r * 6 - r * 1 - r * 3;
        LeftX[5] = getWidth() / 2 + r * 6 - r * 2 - r * 3;
        LeftX[6] = getWidth() / 2 + r * 6 - r * 3 - r * 3;
        LeftX[7] = getWidth() / 2 + r * 6 - r * 1 - r * 3;
        LeftX[8] = getWidth() / 2 + r * 6 - r * 2 - r * 3;
        LeftX[9] = getWidth() / 2 + r * 6 - r * 3 - r * 3;
        LeftY[1] = getHeight() / 2 + r / 3 + r * 0 - r * 2 - r * 2;
        LeftY[2] = getHeight() / 2 + r / 3 + r * 0 - r * 1 - r * 2;
        LeftY[3] = getHeight() / 2 + r / 3 + r * 0 - r * 0 - r * 2;
        LeftY[4] = getHeight() / 2 + r / 3 + r * 2 - r * 2 - r * 2;
        LeftY[5] = getHeight() / 2 + r / 3 + r * 2 - r * 1 - r * 2;
        LeftY[6] = getHeight() / 2 + r / 3 + r * 2 - r * 0 - r * 2;
        LeftY[7] = getHeight() / 2 + r / 3 + r * 4 - r * 2 - r * 2;
        LeftY[8] = getHeight() / 2 + r / 3 + r * 4 - r * 1 - r * 2;
        LeftY[9] = getHeight() / 2 + r / 3 + r * 4 - r * 0 - r * 2;
        for (i = 1; i < 10; i++) {
            RightRx[i] = r * 1;
            RightRy[i] = 2 * r;
        }
        RightX[1] = getWidth() / 2 - r * 6 + r * 0 + r * 3;
        RightX[2] = getWidth() / 2 - r * 6 + r * 1 + r * 3;
        RightX[3] = getWidth() / 2 - r * 6 + r * 2 + r * 3;
        RightX[4] = getWidth() / 2 - r * 6 + r * 0 + r * 3;
        RightX[5] = getWidth() / 2 - r * 6 + r * 1 + r * 3;
        RightX[6] = getWidth() / 2 - r * 6 + r * 2 + r * 3;
        RightX[7] = getWidth() / 2 - r * 6 + r * 0 + r * 3;
        RightX[8] = getWidth() / 2 - r * 6 + r * 1 + r * 3;
        RightX[9] = getWidth() / 2 - r * 6 + r * 2 + r * 3;
        RightY[1] = getHeight() / 2 - r / 3 - r * 4 + r * 0 - r * 0 + r * 2;
        RightY[2] = getHeight() / 2 - r / 3 - r * 4 + r * 0 - r * 1 + r * 2;
        RightY[3] = getHeight() / 2 - r / 3 - r * 4 + r * 0 - r * 2 + r * 2;
        RightY[4] = getHeight() / 2 - r / 3 - r * 4 + r * 2 - r * 0 + r * 2;
        RightY[5] = getHeight() / 2 - r / 3 - r * 4 + r * 2 - r * 1 + r * 2;
        RightY[6] = getHeight() / 2 - r / 3 - r * 4 + r * 2 - r * 2 + r * 2;
        RightY[7] = getHeight() / 2 - r / 3 - r * 4 + r * 4 - r * 0 + r * 2;
        RightY[8] = getHeight() / 2 - r / 3 - r * 4 + r * 4 - r * 1 + r * 2;
        RightY[9] = getHeight() / 2 - r / 3 - r * 4 + r * 4 - r * 2 + r * 2;
    }

    protected void keyPressed(int keyCode) {
        String key = "";
        switch (keyCode) {
            case Canvas.KEY_NUM1:
                key = "1";
                n = n + 1;
                UpClockwise();
                break;
            case Canvas.KEY_NUM2:
                key = "2";
                n = n + 1;
                BackContrarotate();
                break;
            case Canvas.KEY_NUM3:
                key = "3";
                n = n + 1;
                UpContrarotate();
                break;
            case Canvas.KEY_NUM4:
                key = "4";
                n = n + 1;
                LeftContrarotate();
                break;
            case Canvas.KEY_NUM5:
                key = "5";
                n = n + 1;
                BackClockwise();
                break;
            case Canvas.KEY_NUM6:
                key = "6";
                n = n + 1;
                RightClockwise();
                break;
            case Canvas.KEY_NUM7:
                key = "7";
                n = n + 1;
                LeftClockwise();
                break;
            case Canvas.KEY_NUM8:
                key = "8";
                n = n + 1;
                ForwardClockwise();
                break;
            case Canvas.KEY_NUM9:
                key = "9";
                n = n + 1;
                RightContrarotate();
                break;
            case Canvas.KEY_STAR:
                key = "*";
                n = n + 1;
                DownContrarotate();
                break;
            case Canvas.KEY_NUM0:
                key = "0";
                n = n + 1;
                ForwardContrarotate();
                break;
            case Canvas.KEY_POUND:
                key = "#";
                n = n + 1;
                DownClockwise();
                break;
            default:
                break;
        }
        if (key == "") {
            switch (getGameAction(keyCode)) {
                case Canvas.UP:
                    key = "UP";
                    switch (eyeshot) {
                        case 0:
                            ForwardLeftDown();
                            eyeshot = 1;
                            break;
                        case 1:
                            ForwardTurnDown();
                            ForwardTurnDown();
                            ForwardTurnDown();
                            break;
                        case 2:
                            ForwardTurnDown();
                            ForwardTurnDown();
                            ForwardTurnDown();
                            break;
                        case 3:
                            ForwardRightDown();
                            eyeshot = 2;
                            break;
                        default:
                            break;
                    }
                    break;
                case Canvas.DOWN:
                    key = "DOWN";
                    switch (eyeshot) {
                        case 0:
                            ForwardTurnDown();
                            break;
                        case 1:
                            ForwardLeftUp();
                            eyeshot = 0;
                            break;
                        case 2:
                            ForwardRightUp();
                            eyeshot = 3;
                            break;
                        case 3:
                            ForwardTurnDown();
                            break;
                        default:
                            break;
                    }
                    break;
                case Canvas.LEFT:
                    key = "LEFT";
                    switch (eyeshot) {
                        case 0:
                            ForwardRightUp();
                            eyeshot = 3;
                            break;
                        case 1:
                            ForwardRightDown();
                            eyeshot = 2;
                            break;
                        case 2:
                            ForwardTurnLeft();
                            ForwardTurnLeft();
                            ForwardTurnLeft();
                            break;
                        case 3:
                            ForwardTurnLeft();
                            ForwardTurnLeft();
                            ForwardTurnLeft();
                            break;
                        default:
                            break;
                    }
                    break;
                case Canvas.RIGHT:
                    key = "RIGHT";
                    switch (eyeshot) {
                        case 0:
                            ForwardTurnLeft();
                            break;
                        case 1:
                            ForwardTurnLeft();
                            break;
                        case 2:
                            ForwardLeftDown();
                            eyeshot = 1;
                            break;
                        case 3:
                            ForwardLeftUp();
                            eyeshot = 0;
                            break;
                        default:
                            break;
                    }
                    break;
                case Canvas.FIRE:
                    key = "FIRE";
                    n = n + 1;
                    ForwardContrarotate();
                    break;
            }
        }
        repaint();
    }
}
