
import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

public class BattleCanvas extends Canvas implements Runnable, PlayerListener {

    private GameData theGame = null;
    private Graphics vbuf = null;
    private Image bufImg = null;
    private Image barImg = null;
    private int[] keySeq = new int[12];
    private TiledLayer seq = null;
    public Sprite enemy = null;
    public Random rnd = new Random(System.currentTimeMillis());
    public boolean wait = false;
    private final static byte MENU = 1;
    private final static byte ATK_DOING = 2;
    private final static byte ATK_WAIT = 3;
    private final static byte ATK_ENEMY = 7;
    private final static byte ITEM = 4;
    private final static byte RETREAT = 5;
    private final static byte EXIT = 6;
    private byte curState = EXIT;
    private int menuSel = 0;
    private int curKey;
    private int atkCnt, atk1, atk2;
    private int eHP, eATK, eDEF, eAI, eID, eAGI;
    private RpgCanvas parent;
    public final static int[][] enemyData = {
        //	pic.[0]		HP[1]		ATK[2]		DEF[3]		AI[4]		AGI[5]		EXP[6]		GOLD[7]
        {0, 40, 5, 1, 3, 10, 5, 5},
        {0, 40, 6, 2, 3, 12, 8, 5},
        {0, 60, 8, 4, 5, 17, 10, 8},
        {1, 80, 12, 8, 4, 17, 15, 12},
        {2, 80, 13, 8, 7, 25, 18, 17},
        {3, 50, 15, 20, 4, 18, 20, 20},
        {4, 100, 24, 18, 2, 20, 25, 25},
        {5, 120, 29, 16, 4, 28, 25, 29},
        {6, 150, 33, 21, 5, 31, 30, 29},
        {7, 169, 35, 26, 5, 29, 32, 35},
        {8, 194, 40, 30, 7, 35, 40, 40},
        {9, 175, 40, 20, 5, 30, 38, 38},
        {10, 186, 46, 29, 4, 35, 40, 35},
        {11, 200, 35, 35, 5, 20, 40, 40},
        {12, 205, 43, 30, 6, 20, 55, 44},
        {13, 200, 40, 40, 7, 999, 100, 50},
        {14, 250, 60, 40, 7, 35, 80, 50},
        {15, 230, 46, 47, 4, 35, 90, 50},
        {16, 200, 70, 70, 7, 50, 200, 100}
    };
    public final static String[] name = {
        "怪物1", "怪物2", "怪物3", "怪物4", "怪物5",
        "怪物7", "怪物8", "怪物9", "怪物10", "怪物11",
        "怪物12", "怪物13", "怪物14", "怪物15", "怪物16",
        "怪物17", "怪物18", "怪物19", "怪物20"
    };

    protected void paint(Graphics g) {
        g.drawImage(bufImg, 0, 0, Graphics.TOP | Graphics.LEFT);
    }

    public BattleCanvas(GameData gd, RpgCanvas parentCan) {
        parent = parentCan;
        bufImg = Image.createImage(240, 255);
        vbuf = bufImg.getGraphics();
        theGame = gd;
        barImg = Image.createImage(180, 10);
        Graphics bf = barImg.getGraphics();
        for (int i = 0; i < 180; i++) {
            bf.setColor(i * 255 / 360 * 2, 100 - i * 60 / 180, 0);
            bf.drawLine(i, 0, i, 9);
        }
//		for(int i=120;i<180;i++){
//			bf.setColor(255,255-(i-120)*255/120,(i-120)*255/120);
//			bf.drawLine(i,0,i,14);
//		}
//		bf.setColor(0xff0000);
//		bf.drawLine(100,0,100,15);
//		bf.drawLine(130,0,130,15);
        bf.setColor(0xffffff);
        bf.drawRect(0, 0, 179, 9);
        //barImg=Image.createImage(barImg);
        try {
            seq = new TiledLayer(12, 1, Image.createImage("/res/sword.png"), 16, 16);
            seq.fillCells(0, 0, 12, 1, 0);
            seq.setPosition(10, 100);
            enemy = new Sprite(Image.createImage("/res/enemy.png"), 16, 24);
            enemy.setPosition(5, 10);
        } catch (IOException ioe) {
        }
    }

    protected void drawAll() {
        vbuf.setColor(0);
        vbuf.fillRect(0, 0, 240, 255);
        enemy.paint(vbuf);
        vbuf.setColor(0xffffff);
        vbuf.drawString("主角优希", 3, 158, Graphics.TOP | Graphics.LEFT);
        vbuf.drawString(name[eID], 3, 38, Graphics.TOP | Graphics.LEFT);
        vbuf.setColor(0xffff);
        vbuf.drawString("HP", 80, 10, Graphics.TOP | Graphics.LEFT);
        vbuf.drawString("Atk", 80, 26, Graphics.TOP | Graphics.LEFT);
        vbuf.drawString("Def", 80, 42, Graphics.TOP | Graphics.LEFT);
        vbuf.drawString("HP", 80, 130, Graphics.TOP | Graphics.LEFT);
        vbuf.drawString("Atk", 80, 146, Graphics.TOP | Graphics.LEFT);
        vbuf.drawString("Def", 80, 162, Graphics.TOP | Graphics.LEFT);
        vbuf.setColor(0xff0000);
        vbuf.fillRect(120, 14, eHP * 100 / enemyData[eID][1], 8);
        vbuf.fillRect(120, 30, eATK * 100 / 100, 8);
        vbuf.fillRect(120, 46, eDEF * 100 / 100, 8);
        vbuf.fillRect(120, 134, theGame.HP * 100 / theGame.MaxHP, 8);
        vbuf.fillRect(120, 150, theGame.ATK * 100 / 100, 8);
        vbuf.fillRect(120, 166, theGame.DEF * 100 / 100, 8);
        vbuf.setColor(0xffffff);
        vbuf.drawRect(120, 14, enemyData[eID][1] * 100 / enemyData[eID][1], 8);
        vbuf.drawRect(120, 30, eATK * 100 / 100, 8);
        vbuf.drawRect(120, 46, eDEF * 100 / 100, 8);
        vbuf.drawRect(120, 134, theGame.HP * 100 / theGame.HP, 8);
        vbuf.drawRect(120, 150, theGame.ATK * 100 / 100, 8);
        vbuf.drawRect(120, 166, theGame.DEF * 100 / 100, 8);
        vbuf.drawImage(barImg, 3, 80, Graphics.TOP | Graphics.LEFT);
        parent.mainChr.setFrameSequence(new int[]{4, 5});
        parent.mainChr.setPosition(5, 130);
        parent.mainChr.paint(vbuf);
        parent.dlgbox.setPosition(0, 200);
        parent.dlgbox.paint(vbuf);
    }

    public int startBattle(int enemyID) {
        parent.dir = 2;
        parent.sp.setEnable(false);
        enemy.setPosition(5, 10);
        theGame.monsters[enemyID] = true;
        long time;
        int t;
        enemy.setFrame(enemyData[enemyID][0]);
        eHP = enemyData[enemyID][1];
        eATK = enemyData[enemyID][2];
        eDEF = enemyData[enemyID][3];
        eAI = enemyData[enemyID][4];
        eAGI = enemyData[enemyID][5];
        eID = enemyID;
        atk1 = theGame.ATK - eDEF;
        if (atk1 <= 0) {
            atk1 = 1;
        }
        atk2 = eATK - theGame.DEF;
        if (atk2 <= 0) {
            atk2 = 1;
        }
        curState = MENU;
        while (curState != EXIT) {
            if (curState == MENU) {
                menuSel = 0;
                drawAll();
                vbuf.setColor(0);
                vbuf.drawString("攻击", 20, 204, Graphics.TOP | Graphics.LEFT);
                vbuf.drawString("回复", 120, 204, Graphics.TOP | Graphics.LEFT);
                vbuf.drawString("撤退", 20, 224, Graphics.TOP | Graphics.LEFT);
                vbuf.setColor(0xff0000);
                vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                repaint();
            }

            while (curState == MENU);
            switch (curState) {
                case ATK_WAIT:
                    if (eAGI >= theGame.AGI) {
                        atkCnt = 0;
                        for (int i = 0; i < 12; i++) {
                            keySeq[i] = Math.abs(rnd.nextInt() % 9);
                            seq.setCell(i, 0, keySeq[i] + 1);
                        }
                        seq.paint(vbuf);
                        repaint();
                        drawText(name[eID] + " 开始攻击!", 1000);
                        for (int i = 0; i < 12; i++) {
                            if (Math.abs(rnd.nextInt() % 10) < eAI) {
                                seq.setCell(i, 0, 11);
                                drawText("击中!", 0);
                                atkCnt++;
                            } else {
                                seq.setCell(i, 0, 10);
                                drawText("Miss!", 0);
                            }
                            vbuf.setColor(0);
                            vbuf.fillRect(182 - i * 178 / 11, 81, i * 178 / 11, 8);
                            seq.paint(vbuf);
                            repaint();
                            parent.pause(300);
                        }
                        /////////////////////////////////
                        atkCnt = atk2 * atkCnt / 2;

                        theGame.HP -= atkCnt;
                        drawText("优希损失HP " + atkCnt, 1000);
                        if (theGame.HP <= 0) {
                            theGame.HP = 0;
                            curState = EXIT;
                            drawText("优希失败了!", 1000);
                            return 1;
                        }
                        drawAll();

                        atkCnt = 0;
                        curKey = 0;
                        for (int i = 0; i < 12; i++) {
                            keySeq[i] = Math.abs(rnd.nextInt() % 9);
                            seq.setCell(i, 0, keySeq[i] + 1);
                        }
                        seq.paint(vbuf);
                        repaint();
                        drawText("优希 开始攻击!", 1000);
                        time = System.currentTimeMillis();
                        while (true) {
                            curState = ATK_DOING;
                            while (curState == ATK_DOING) {
                                t = (int) (System.currentTimeMillis() - time);
                                if (t > 5000) {
                                    break;
                                }
                                vbuf.setColor(0);
                                vbuf.fillRect(182 - t * 178 / 5000, 81, t * 178 / 5000, 8);
                                repaint(182 - t * 178 / 5000, 81, t * 178 / 5000, 8);
                                parent.pause(10);
                            }
                            if (curState == ATK_DOING) {
                                break;
                            }
                            seq.setCell(curKey, 0, 10 + keySeq[curKey++]);
                            seq.paint(vbuf);
                            repaint();
                            curState = ATK_DOING;
                            if (curKey == 12) {
                                break;
                            }
                        }
                        curState = ATK_WAIT;
                        atkCnt = atk1 * atkCnt / 2;

                        drawText("完成," + name[eID] + "损失HP " + atkCnt, 3000);
                        eHP -= atkCnt;
                        if (eHP <= 0) {
                            if (theGame.sound) {
                                parent.sp.playSE();
                                wait = true;
                            }
                            wait = true;
                            drawText(name[eID] + "倒下了", 1000);
                            drawText("得到 EXP " + enemyData[eID][6] + "点", 1000);
                            drawText("得到GOLD " + enemyData[eID][7], 1000);
                            atkCnt = Math.abs(rnd.nextInt() % 20);
                            if (atkCnt < 4) {
                                theGame.item[0] += 1;
                                drawText("得到一个面包.", 700);
                            } else if (atkCnt < 6) {
                                theGame.item[1] += 1;
                                drawText("得到药丸.", 700);
                            } else if (atkCnt == 6) {
                                theGame.item[2] += 1;
                                drawText("得到水.", 700);
                            }
                            theGame.EXP += enemyData[eID][6];
                            if (theGame.checkUP()) {
                                drawText("优希的能力提升了!", 700);
                            }
                            while (wait);
                            parent.sp.setEnable(theGame.sound);
                            return 0;
                        }
                    } else {
                        atkCnt = 0;
                        curKey = 0;
                        for (int i = 0; i < 12; i++) {
                            keySeq[i] = Math.abs(rnd.nextInt() % 9);
                            seq.setCell(i, 0, keySeq[i] + 1);
                        }
                        seq.paint(vbuf);
                        repaint();
                        drawText("优希 开始攻击!", 1000);
                        time = System.currentTimeMillis();
                        while (true) {
                            curState = ATK_DOING;
                            while (curState == ATK_DOING) {
                                t = (int) (System.currentTimeMillis() - time);
                                if (t > 5000) {
                                    break;
                                }
                                vbuf.setColor(0);
                                vbuf.fillRect(182 - t * 178 / 5000, 81, t * 178 / 5000, 8);
                                repaint(182 - t * 178 / 5000, 81, t * 178 / 5000, 8);
                                parent.pause(10);
                            }
                            if (curState == ATK_DOING) {
                                break;
                            }
                            seq.setCell(curKey, 0, 10 + keySeq[curKey++]);
                            seq.paint(vbuf);
                            repaint();
                            curState = ATK_DOING;
                            if (curKey == 12) {
                                break;
                            }
                        }
                        curState = ATK_WAIT;
                        atkCnt = atk1 * atkCnt / 2;

                        drawText("完成," + name[eID] + "损失HP " + atkCnt, 3000);
                        eHP -= atkCnt;
                        if (eHP <= 0) {
                            if (theGame.sound) {
                                parent.sp.playSE();
                                wait = true;
                            }
                            drawText(name[eID] + "倒下了", 1000);
                            drawText("得到 EXP " + enemyData[eID][6] + "点", 1000);
                            drawText("得到GOLD " + enemyData[eID][7], 1000);
                            atkCnt = Math.abs(rnd.nextInt() % 20);
                            if (atkCnt < 4) {
                                theGame.item[0] += 1;
                                drawText("得到一个面包.", 700);
                            } else if (atkCnt < 6) {
                                theGame.item[1] += 1;
                                drawText("得到药丸.", 700);
                            } else if (atkCnt == 6) {
                                theGame.item[2] += 1;
                                drawText("得到水.", 700);
                            }
                            theGame.EXP += enemyData[eID][6];
                            if (theGame.checkUP()) {
                                drawText("优希的能力提升了!", 700);
                            }
                            while (wait);
                            parent.sp.setEnable(theGame.sound);
                            return 0;
                        }
                        drawAll();

                        atkCnt = 0;
                        for (int i = 0; i < 12; i++) {
                            keySeq[i] = Math.abs(rnd.nextInt() % 9);
                            seq.setCell(i, 0, keySeq[i] + 1);
                        }
                        seq.paint(vbuf);
                        repaint();
                        drawText(name[eID] + " 开始攻击!", 1000);
                        for (int i = 0; i < 12; i++) {
                            if (Math.abs(rnd.nextInt() % 10) < eAI) {
                                seq.setCell(i, 0, 11);
                                drawText("击中!", 0);
                                atkCnt++;
                            } else {
                                seq.setCell(i, 0, 10);
                                drawText("Miss!", 0);
                            }
                            vbuf.setColor(0);
                            vbuf.fillRect(182 - i * 178 / 11, 81, i * 178 / 11, 8);
                            seq.paint(vbuf);
                            repaint();
                            parent.pause(300);
                        }

                        atkCnt = atk2 * atkCnt / 2;
                        theGame.HP -= atkCnt;
                        drawText("优希损失HP " + atkCnt, 1000);
                        if (theGame.HP <= 0) {
                            theGame.HP = 0;
                            curState = EXIT;
                            drawText("优希失败了!", 1000);
                            return 1;
                        }
                    }
                    curState = MENU;
                    drawAll();
                    vbuf.setColor(0);
                    vbuf.drawString("攻击", 20, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("回复", 120, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("撤退", 20, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.setColor(0xff0000);
                    vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                    repaint();
                    break;
                case RETREAT:
                    curState = EXIT;
                    return -1;
                case ITEM:
                    menuSel = 0;
                    drawAll();
                    vbuf.setColor(0);
                    vbuf.drawString("面包  X " + theGame.item[0], 20, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("药丸  X " + theGame.item[1], 120, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("水  X " + theGame.item[2], 20, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("取消", 120, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.setColor(0xff0000);
                    vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                    repaint();
                    while (curState == ITEM) {
                        parent.pause(10);
                    }
                    break;
            }
            parent.pause(600);

        } // while(curState!=EXIT);
        return 1;
    }

    public void keyPressed(int keyCode) {
        int gameKey = getGameAction(keyCode);
        if (curState == EXIT) {
            return;
        }
        switch (curState) {
            case ATK_DOING:
                if (keyCode == 49 + keySeq[curKey]) {
                    keySeq[curKey] = 1;
                    atkCnt++;
                } else {
                    keySeq[curKey] = 0;
                }
                curState = ATK_WAIT;
                break;
            case MENU:
                if (gameKey == UP || gameKey == LEFT) {
                    if (menuSel != 0) {
                        menuSel -= 1;
                    }
                    parent.dlgbox.paint(vbuf);
                    vbuf.setColor(0);
                    vbuf.drawString("攻击", 20, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("回复", 120, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("撤退", 20, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.setColor(0xff0000);
                    if (menuSel == 0) {
                        vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 1) {
                        vbuf.drawChar('*', 112, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 2) {
                        vbuf.drawChar('*', 12, 224, Graphics.TOP | Graphics.LEFT);
                    }
                    repaint();
                } else if (gameKey == DOWN || gameKey == RIGHT) {
                    if (menuSel != 2) {
                        menuSel += 1;
                    }
                    parent.dlgbox.paint(vbuf);
                    vbuf.setColor(0);
                    vbuf.drawString("攻击", 20, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("回复", 120, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("撤退", 20, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.setColor(0xff0000);
                    if (menuSel == 0) {
                        vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 1) {
                        vbuf.drawChar('*', 112, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 2) {
                        vbuf.drawChar('*', 12, 224, Graphics.TOP | Graphics.LEFT);
                    }
                    repaint();
                } else if (gameKey == FIRE) {
                    if (menuSel == 0) {
                        curState = ATK_WAIT;
                    } else if (menuSel == 1) {
                        curState = ITEM;
                    } else if (menuSel == 2) {
                        if (eAGI == 999) {
                            drawText("不能撤退!", 600);
                        } else {
//						drawText("Hero 准备撤退...",1000);
                            if (Math.abs(rnd.nextInt() % (theGame.AGI + eAGI)) < theGame.AGI) {
                                drawText("优希成功得撤离了!", 500);
                                curState = RETREAT;
                            } else {
                                drawText("怪物紧追不放!", 500);
                                curState = MENU;
                            }
                        }
                    }
                }
                break;
            case ATK_WAIT:
                break;
            case ITEM:
                if (gameKey == UP || gameKey == LEFT) {
                    if (menuSel != 0) {
                        menuSel -= 1;
                    }
                    parent.dlgbox.paint(vbuf);
                    vbuf.setColor(0);
                    vbuf.drawString("面包  X " + theGame.item[0], 20, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("药丸  X " + theGame.item[1], 120, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("水  X " + theGame.item[2], 20, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("取消", 120, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.setColor(0xff0000);
                    if (menuSel == 0) {
                        vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 1) {
                        vbuf.drawChar('*', 112, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 2) {
                        vbuf.drawChar('*', 12, 224, Graphics.TOP | Graphics.LEFT);
                    }
                    repaint();
                } else if (gameKey == DOWN || gameKey == RIGHT) {
                    if (menuSel != 3) {
                        menuSel += 1;
                    }
                    parent.dlgbox.paint(vbuf);
                    vbuf.setColor(0);
                    vbuf.drawString("面包  X " + theGame.item[0], 20, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("药丸  X " + theGame.item[1], 120, 204, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("水  X " + theGame.item[2], 20, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.drawString("取消", 120, 224, Graphics.TOP | Graphics.LEFT);
                    vbuf.setColor(0xff0000);
                    if (menuSel == 0) {
                        vbuf.drawChar('*', 12, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 1) {
                        vbuf.drawChar('*', 112, 204, Graphics.TOP | Graphics.LEFT);
                    } else if (menuSel == 2) {
                        vbuf.drawChar('*', 12, 224, Graphics.TOP | Graphics.LEFT);
                    }
                    repaint();
                } else if (gameKey == FIRE) {
                    if (menuSel == 0 && theGame.item[0] > 0) {
                        theGame.item[0] -= 1;
                        theGame.HP += 60;
                        if (theGame.HP > theGame.MaxHP) {
                            theGame.HP = theGame.MaxHP;
                        }
                        drawText("优希 使用了面包， HP回复了60!", 0);
                    } else if (menuSel == 1 && theGame.item[1] > 0) {
                        theGame.item[1] -= 1;
                        theGame.HP += (theGame.MaxHP / 2);
                        if (theGame.HP > theGame.MaxHP) {
                            theGame.HP = theGame.MaxHP;
                        }
                        drawText("优希  使用了药丸，HP回复了" + theGame.MaxHP / 2 + "!", 0);
                    } else if (menuSel == 2 && theGame.item[2] > 0) {
                        theGame.item[2] -= 1;
                        theGame.HP = theGame.MaxHP;
                        drawText("优希 使用了水， HP完全回复了!", 0);
                    } else {
                        drawText("道具没有了!", 0);
                    }
                    repaint();
                    parent.pause(10);
                    curState = MENU;
                }
                break;
        }
    }

    public void run() {
        startBattle(0);
    }

    private void drawText(String t, long nMillis) {
        vbuf.setColor(0);
        Font font = Font.getDefaultFont();
        parent.dlgbox.setPosition(0, 200);
        int p = 0, l = t.length(), j;
        while (p < l) {
            parent.dlgbox.paint(vbuf);
            j = 1;
            while (p + j < l && font.substringWidth(t, p, j) < RpgCanvas.scrW - 12) {
                j++;
            }
            if (p + j > l) {
                vbuf.drawString(t.substring(p, l - 1), 5, RpgCanvas.scrH - 7 - font.getHeight() * 2, Graphics.TOP | Graphics.LEFT);
                p = l;
            } else {
                vbuf.drawString(t.substring(p, p + j), 5, RpgCanvas.scrH - 7 - font.getHeight() * 2, Graphics.TOP | Graphics.LEFT);
                p += j;
            }
            j = 1;
            while (p + j < l && font.substringWidth(t, p, j) < RpgCanvas.scrW - 12) {
                j++;
            }
            if (p != l) {
                if (p + j > l) {
                    vbuf.drawString(t.substring(p, l - 1), 5, RpgCanvas.scrH - 6 - font.getHeight() * 1, Graphics.TOP | Graphics.LEFT);
                    p = l;
                } else {
                    vbuf.drawString(t.substring(p, p + j), 5, RpgCanvas.scrH - 6 - font.getHeight() * 1, Graphics.TOP | Graphics.LEFT);
                    p += j;
                }
            }
            repaint();
            if (nMillis == 0) {
                return;
            }
            parent.pause(nMillis);
        }
    }

    public void playerUpdate(Player parm1, String parm2, Object parm3) {
        if (parm2.equals(END_OF_MEDIA)) {
            wait = false;
        }
    }
}
