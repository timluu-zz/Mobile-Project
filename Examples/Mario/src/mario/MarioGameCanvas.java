/**
 * <p>Title: Mario</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file any part without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.0.0
 */
package mario;

import java.io.*;
import java.util.Random;
import javax.microedition.lcdui.*;
import lipeng.*;

public class MarioGameCanvas
        extends Canvas
        implements Runnable, CommandListener, LPIGameStart {
    // method

    public MarioGameCanvas(MarioMIDlet midlet) {
        this.setFullScreenMode(true);
        this.midlet = midlet;
        this.width = this.getWidth();
        this.height = this.getHeight();
        setCommandListener(this);
        this.addCommand(mainMenu);
        dm = new LPDrawManage(width, height);
        if (record.IsEmpty()) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeInt(1);
                this.record.addRecord(baos.toByteArray());
                dos.close();
                baos.close();
            } catch (Exception e) {
                System.out.println("byte write error");
            }
        }
        loadGameResource(0);
        imMainSmall16 = new LPImageManage("small16.png");
        imMainNormal32 = new LPImageManage("normal32.png");
        imMainFire32 = new LPImageManage("fire32.png");
        imSprite8 = new LPImageManage("sprite8.png");
        imSprite16 = new LPImageManage("sprite16.png");
        imEnemy24 = new LPImageManage("enemy24.png");
        imSprite48 = new LPImageManage("sprite48.png");

        gm = new MarioGameManage(this);
        gm.gameState = MarioGameManage.GAMESTATE_MENU;
    }

    private void loadMap(String fileName, char MapArray[][], int width, int height) {
        InputStream inStream = getClass().getResourceAsStream("/" + fileName);
        DataInputStream in = new DataInputStream(inStream);
        int i;
        try {
            for (i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    MapArray[i][j] = in.readChar();
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("read map file" + fileName + " error!");
            System.out.println(e.toString());
        }
    }

    public void loadGameResource(int level) {
        int i, j;
        imMap = new LPImageManage("level_2_1.png");
        mapData = this.level_1_map;
        map = new LPMaps(imMap, mapData, 32, 20, 16, "level_2_1");
        map.x = 0;
        map.y = 0;
        if (gm != null && gm.mainSprite != null) {
            gm.mainSprite.judgeMap = map;
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == mainMenu) {
            removeCommand(mainMenu);
            addCommand(back);
            this.saveGameState = gm.gameState;
            gm.gameState = MarioGameManage.GAMESTATE_MENU;
            this.isSaveState = true;
            gm.menu.init();
        }
        if (c == back) {
            removeCommand(back);
            addCommand(mainMenu);
            gm.gameState = MarioGameManage.GAMESTATE_GAMELOOP;
        }
    }

    public void run() {
        try {
            Thread currentThread = Thread.currentThread();
            while (currentThread == gameThread) {
                startTime = System.currentTimeMillis();
                switch (gm.gameState) {
                    case MarioGameManage.GAMESTATE_MENU:
                        gm.menu.action();
                        break;
                    case MarioGameManage.GAMESTATE_GAMELOOP:
                        gm.action();
                        break;
                    case MarioGameManage.GAMESTATE_PAUSE:
                        break;
                    case MarioGameManage.GAMESTATE_REINIT:
                        gm.reInit();
                        break;
                }
                repaint(0, 0, width, height);
                serviceRepaints();
                endTime = System.currentTimeMillis();
                if ((endTime - startTime) < FRAME_TIME) {
                    Thread.sleep(FRAME_TIME - (endTime - startTime));
                }
            }
        } catch (InterruptedException ie) {
            System.out.println(ie.toString());
        }
    }

    public void paint(Graphics g) {
        switch (gm.gameState) {
            case MarioGameManage.GAMESTATE_MENU:
                switch (gm.menu.menuState) {
                    case LPMenu.MENU_STATE_MAIN:
                        drawMainMenuScreen(g);
                        break;
                }
                break;
            case MarioGameManage.GAMESTATE_GAMELOOP:
                drawGameLoopScreen(g);
                break;
            case MarioGameManage.GAMESTATE_PAUSE:
                drawPauseScreen(g);
                break;
            case MarioGameManage.GAMESTATE_REINIT:
                drawTryAgainScreen(g);
                break;
        }
    }

    private void drawTryAgainScreen(Graphics g) {
        int offset = (height - font.getHeight() * 2) / 2;
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, width, height);
        g.setFont(font);
        g.setColor(255, 0, 0);
        g.drawString("׼���ã�", (width - font.stringWidth("׼���ã�")) / 2,
                offset + font.getHeight() * 0, g.LEFT | g.TOP);
        g.drawString("����һ��", (width - font.stringWidth("׼���ã�")) / 2,
                offset + font.getHeight() * 1, g.LEFT | g.TOP);
    }

    public void loadSaveState() {
        try {
            int i, j;
            byte[] rec = record.getRecord(1);
            ByteArrayInputStream bais = new ByteArrayInputStream(rec);
            DataInputStream dis = new DataInputStream(bais);
            gm.initGameState();
            gm.goldNum = dis.readInt();
            loadGameResource(1);
            gm.mainSprite.judgeMap.x = dis.readInt();
            gm.mainSprite.judgeMap.y = dis.readInt();

            for (i = gm.mainSprite.judgeMap.mapArray.length - 1; i >= 0; --i) {
                for (j = gm.mainSprite.judgeMap.mapArray[i].length - 1; j >= 0; --j) {
                    gm.mainSprite.judgeMap.mapArray[i][j] = dis.readChar();
                }
            }

            gm.initLevel_1_1();


            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.brick[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.brokenBrick[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.bullet[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.enemy[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.tortoise[i].readData(dis);
            }


            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.bridge[i].readData(dis);
            }

            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.gold[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.mashRooms[i].readData(dis);
            }

            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.flowers[i].readData(dis);
            }

            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.stick[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.staticGold[i].readData(dis);
            }
            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.ugGold[i].readData(dis);
            }

            for (i = dis.readInt() - 1; i >= 0; --i) {
                gm.ugBrick[i].readData(dis);
            }

            gm.mainSprite.readData(dis);

            dis.close();
            bais.close();
        } catch (Exception e) {
            System.out.println("read state error");
        }
    }

    private void drawGameLoopScreen(Graphics g) {
        int i;
        g.setClip(0, 0, gm.canvas.width, gm.canvas.height);
        dm.drawMaps(g, gm.mainSprite.judgeMap);
        dm.drawSprite(g, imSprite8, gm.brokenBrick);
        dm.drawSprite(g, imSprite16, gm.brick);
        dm.drawSprite(g, imSprite16, gm.gold);
        for (i = gm.mashRooms.length - 1; i >= 0; --i) {
            gm.mashRooms[i].draw(g, imSprite16);
        }
        for (i = gm.flowers.length - 1; i >= 0; --i) {
            gm.flowers[i].draw(g, imSprite16);
        }
        dm.drawSprite(g, imEnemy24, gm.tortoise);
        dm.drawSprite(g, imSprite16, gm.staticGold);
        dm.drawSprite(g, imSprite48, gm.stick);
        dm.drawSprite(g, imSprite8, gm.bullet);

        dm.drawSprite(g, imSprite16, gm.bridge);
        switch (gm.mainSprite.mainSpriteState) {
            case MarioMainSprite.SMALL_SPRITE:
            case MarioMainSprite.DEAD_SPRITE:
                dm.drawSprite(g, imMainSmall16, gm.mainSprite);
                break;
            case MarioMainSprite.NORMAL_SPRITE:
                dm.drawSprite(g, imMainNormal32, gm.mainSprite);
                break;
            case MarioMainSprite.FIRE_SPRITE:
                dm.drawSprite(g, imMainFire32, gm.mainSprite);
                break;
            case MarioMainSprite.GROW_SPRITE:
                if (gm.mainSprite.growCnt % 2 == 0) {
                    dm.drawSprite(g, imMainSmall16, gm.mainSprite);
                } else {
                    dm.drawSprite(g, imMainNormal32, gm.mainSprite);
                }
                break;
            case MarioMainSprite.BECOME_SMALL:
                if (gm.mainSprite.becomeCnt % 2 == 0) {
                    dm.drawSprite(g, imMainNormal32, gm.mainSprite);
                } else {
                    dm.drawSprite(g, imMainSmall16, gm.mainSprite);
                }
                break;

            case MarioMainSprite.BECOME_FIRE:
                if (gm.mainSprite.becomeCnt % 2 == 0) {
                    dm.drawSprite(g, imMainNormal32, gm.mainSprite);
                } else {
                    dm.drawSprite(g, imMainFire32, gm.mainSprite);
                }
                break;
        }
        g.setClip(0, 0, gm.canvas.width, gm.canvas.height);
    }

    private void drawMainMenuScreen(Graphics g) {
        int menuOffset = (height - font.getHeight() * 4) / 2;
        int tmpY = 0;
        g.setFont(font);

        g.setClip(0, 0, width, height);
        dm.drawMaps(g, map);
        g.setClip(0, 0, width, height);

        switch (gm.menu.whichActive) {
            case LPMenu.MENU_NEW_GAME:
                g.setColor(255, 0, 0);
                g.drawString("����Ϸ", (width - font.stringWidth("����Ϸ")) / 2,
                        menuOffset + font.getHeight() * 0, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("������", (width - font.stringWidth("������")) / 2,
                        menuOffset + font.getHeight() * 1, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                if (gm.menu.isVolOn) {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                } else {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                }
                g.setColor(255, 255, 255);
                g.drawString("�˳�", (width - font.stringWidth("�˳�")) / 2,
                        menuOffset + font.getHeight() * 3, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("����", (width - font.stringWidth("����")) / 2,
                        menuOffset + font.getHeight() * 4, g.LEFT | g.TOP);
                break;
            case LPMenu.MENU_LOAD_GAME:
                g.setColor(255, 255, 255);
                g.drawString("����Ϸ", (width - font.stringWidth("����Ϸ")) / 2,
                        menuOffset + font.getHeight() * 0, g.LEFT | g.TOP);
                g.setColor(255, 0, 0);
                g.drawString("������", (width - font.stringWidth("������")) / 2,
                        menuOffset + font.getHeight() * 1, g.LEFT | g.TOP);

                g.setColor(255, 255, 255);
                if (gm.menu.isVolOn) {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                } else {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                }
                g.setColor(255, 255, 255);
                g.drawString("�˳�", (width - font.stringWidth("�˳�")) / 2,
                        menuOffset + font.getHeight() * 3, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("����", (width - font.stringWidth("����")) / 2,
                        menuOffset + font.getHeight() * 4, g.LEFT | g.TOP);

                break;
            case LPMenu.MENU_VOL:

                g.setColor(255, 255, 255);
                g.drawString("����Ϸ", (width - font.stringWidth("����Ϸ")) / 2,
                        menuOffset + font.getHeight() * 0, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("������", (width - font.stringWidth("������")) / 2,
                        menuOffset + font.getHeight() * 1, g.LEFT | g.TOP);
                g.setColor(255, 0, 0);
                if (gm.menu.isVolOn) {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                } else {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                }
                g.setColor(255, 255, 255);
                g.drawString("�˳�", (width - font.stringWidth("�˳�")) / 2,
                        menuOffset + font.getHeight() * 3, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("����", (width - font.stringWidth("����")) / 2,
                        menuOffset + font.getHeight() * 4, g.LEFT | g.TOP);

                break;
            case LPMenu.MENU_EXIT:

                g.setColor(255, 255, 255);
                g.drawString("����Ϸ", (width - font.stringWidth("����Ϸ")) / 2,
                        menuOffset + font.getHeight() * 0, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("������", (width - font.stringWidth("������")) / 2,
                        menuOffset + font.getHeight() * 1, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                if (gm.menu.isVolOn) {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                } else {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                }
                g.setColor(255, 0, 0);
                g.drawString("�˳�", (width - font.stringWidth("�˳�")) / 2,
                        menuOffset + font.getHeight() * 3, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("����", (width - font.stringWidth("����")) / 2,
                        menuOffset + font.getHeight() * 4, g.LEFT | g.TOP);

                break;
            case LPMenu.MENU_ABOUT:

                g.setColor(255, 255, 255);
                g.drawString("����Ϸ", (width - font.stringWidth("����Ϸ")) / 2,
                        menuOffset + font.getHeight() * 0, g.LEFT | g.TOP);
                g.setColor(255, 255, 255);
                g.drawString("������", (width - font.stringWidth("������")) / 2,
                        menuOffset + font.getHeight() * 1, g.LEFT | g.TOP);

                g.setColor(255, 255, 255);
                if (gm.menu.isVolOn) {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                } else {
                    g.drawString("������", (width - font.stringWidth("������")) / 2,
                            menuOffset + font.getHeight() * 2, g.LEFT | g.TOP);
                }
                g.setColor(255, 255, 255);
                g.drawString("�˳�", (width - font.stringWidth("�˳�")) / 2,
                        menuOffset + font.getHeight() * 3, g.LEFT | g.TOP);
                g.setColor(255, 0, 0);
                g.drawString("����", (width - font.stringWidth("����")) / 2,
                        menuOffset + font.getHeight() * 4, g.LEFT | g.TOP);

                break;
        }
    }

    private void drawPauseScreen(Graphics g) {
        g.setFont(font);
        g.setClip(0, 0, width, height);
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, width, height);
        g.setColor(255, 0, 0);
        g.drawString("��ͣ�С���", (width - font.stringWidth("��ͣ�С���")) / 2,
                height / 2, g.LEFT | g.TOP);
    }

    private void drawGameOverScreen(Graphics g) {
        dm.drawMaps(g, this.map);
        g.setClip(0, 0, width, height);
        g.setFont(font);
        g.setColor(255, 0, 0);
        g.drawString("��Ϸ����", (width - font.stringWidth("��Ϸ����")) / 2,
                height / 2, g.LEFT | g.TOP);
    }

    public void stop() {
        gameThread = null;
    }

    public void start() {
        gm.menu.allAction = 0;
        switch (gm.gameState) {
            case MarioGameManage.GAMESTATE_MENU:
                if (gm.menu.isVolOn) {
                    gm.bgPlayer.play();
                }
                break;
            case MarioGameManage.GAMESTATE_GAMELOOP:
            case MarioGameManage.GAMESTATE_PAUSE:
            case MarioGameManage.GAMESTATE_REINIT:
                break;
        }
        gameThread = new Thread(this);
        gameThread.start();
    }
    public boolean isPressStar = false;

    protected void keyPressed(int keyCode) {
        if (gm.gameState == MarioGameManage.GAMESTATE_GAMELOOP) {
            if (keyCode == Canvas.KEY_STAR) {
                if (!isPressStar) {
                    isPressStar = true;
                }
            }
        }
        gm.judgeKeyCode(keyCode, getGameAction(keyCode));
    }

    protected void keyReleased(int keyCode) {
        gm.freeKey(keyCode, getGameAction(keyCode));
    }

    public void saveCurrentState() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            int i, j;
            dos.writeInt(gm.goldNum);
            dos.writeInt(gm.mainSprite.judgeMap.x);
            dos.writeInt(gm.mainSprite.judgeMap.y);

            for (i = gm.mainSprite.judgeMap.mapArray.length - 1; i >= 0; --i) {
                for (j = gm.mainSprite.judgeMap.mapArray[i].length - 1; j >= 0; --j) {
                    dos.writeChar(gm.mainSprite.judgeMap.mapArray[i][j]);
                }
            }
            dos.writeInt(gm.brick.length);
            for (i = gm.brick.length - 1; i >= 0; --i) {
                gm.brick[i].writeData(dos);
            }
            dos.writeInt(gm.brokenBrick.length);
            for (i = gm.brokenBrick.length - 1; i >= 0; --i) {
                gm.brokenBrick[i].writeData(dos);
            }
            dos.writeInt(gm.bullet.length);
            for (i = gm.bullet.length - 1; i >= 0; --i) {
                gm.bullet[i].writeData(dos);
            }
            dos.writeInt(gm.enemy.length);
            for (i = gm.enemy.length - 1; i >= 0; --i) {
                gm.enemy[i].writeData(dos);
            }
            dos.writeInt(gm.tortoise.length);
            for (i = gm.tortoise.length - 1; i >= 0; --i) {
                gm.tortoise[i].writeData(dos);
            }


            dos.writeInt(gm.bridge.length);
            for (i = gm.bridge.length - 1; i >= 0; --i) {
                gm.bridge[i].writeData(dos);
            }
            dos.writeInt(gm.gold.length);
            for (i = gm.gold.length - 1; i >= 0; --i) {
                gm.gold[i].writeData(dos);
            }
            dos.writeInt(gm.mashRooms.length);
            for (i = gm.mashRooms.length - 1; i >= 0; --i) {
                gm.mashRooms[i].writeData(dos);
            }
            dos.writeInt(gm.flowers.length);
            for (i = gm.flowers.length - 1; i >= 0; --i) {
                gm.flowers[i].writeData(dos);
            }

            dos.writeInt(gm.stick.length);
            for (i = gm.stick.length - 1; i >= 0; --i) {
                gm.stick[i].writeData(dos);
            }
            dos.writeInt(gm.staticGold.length);
            for (i = gm.staticGold.length - 1; i >= 0; --i) {
                gm.staticGold[i].writeData(dos);
            }
            dos.writeInt(gm.ugGold.length);
            for (i = gm.ugGold.length - 1; i >= 0; --i) {
                gm.ugGold[i].writeData(dos);
            }
            dos.writeInt(gm.ugBrick.length);
            for (i = gm.ugBrick.length - 1; i >= 0; --i) {
                gm.ugBrick[i].writeData(dos);
            }
            gm.mainSprite.writeData(dos);
            this.record.SetRecord(1, baos.toByteArray());
            dos.close();
            baos.close();
        } catch (Exception e) {
            System.out.println("Save Data error");
        }
    }
    public MarioMIDlet midlet;
    public static int width;
    public static int height;
    public static Random rand = new Random(System.currentTimeMillis());
    public LPSaveRecord record = new LPSaveRecord("lipeng_Mario");
    public Command mainMenu = new Command("�˵�", Command.OK, 1);
    public Command back = new Command("����", Command.BACK, 2);
    public boolean isSaveState;
    public int saveGameState;
    public MarioGameManage gm;
    public LPMaps map;
    public char mapData[][] = null;
    //private
    private volatile Thread gameThread = null;
    private long startTime;
    private long endTime;
    private LPDrawManage dm;
    private Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
            Font.SIZE_LARGE);
// image resource
    private LPImageManage imMap;
    private LPImageManage imMainSmall16;
    private LPImageManage imMainNormal32;
    private LPImageManage imMainFire32;
    private LPImageManage imSprite8;
    private LPImageManage imSprite16;
    private LPImageManage imEnemy24;
    private LPImageManage imSprite48;
    //final
    private static final int FRAME_TIME = 80;
    static final char[][] level_1_map = {
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0011, 0x0013, 0x0015, 0x0017, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0012, 0x0014, 0x0016, 0x0018, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0f26, 0x0f26, 0x0f26, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0011, 0x0013, 0x0017, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x0019, 0x0019, 0x0019, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0012, 0x0014, 0x0018, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001a, 0x001a, 0x001a, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0f26, 0x0f26, 0x0f26, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001a, 0x001f, 0x001a, 0x0000, 0x0011, 0x0013, 0x0015, 0x0015, 0x0017, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0702, 0x0702, 0x0702, 0x0702, 0x0702, 0x0702, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001c, 0x001c, 0x001c, 0x0019, 0x0012, 0x0014, 0x0016, 0x0016, 0x0018, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001a, 0x001a, 0x001a, 0x001a, 0x001a, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001a, 0x001a, 0x001a, 0x001f, 0x001a, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001c, 0x001c, 0x001c, 0x001c, 0x001c, 0x0019, 0x0019, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0f26, 0x0f26, 0x0f26, 0x0f26, 0x0f26,},
        {0x001d, 0x001a, 0x001a, 0x001a, 0x001a, 0x001a, 0x001a, 0x0000, 0x0000, 0x0000, 0x0f26, 0x0f26, 0x0f26, 0x0f26, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0f26, 0x0f26, 0x0000, 0x0000, 0x0000, 0x0f26, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001e, 0x001a, 0x001a, 0x001a, 0x001a, 0x001a, 0x001a, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x002b, 0x0000, 0x0703, 0x0705, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001e, 0x001a, 0x001a, 0x001a, 0x001d, 0x001a, 0x001a, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x002c, 0x0000, 0x0704, 0x0706, 0x0000, 0x0029, 0x0029, 0x0029, 0x0029, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000,},
        {0x001e, 0x001a, 0x001a, 0x001a, 0x001e, 0x001a, 0x001a, 0x0000, 0x0000, 0x000d, 0x000e, 0x000f, 0x0010, 0x0000, 0x002a, 0x0000, 0x0704, 0x0706, 0x0000, 0x0029, 0x0029, 0x0029, 0x0029, 0x0000, 0x0000, 0x0000, 0x0000, 0x0027, 0x0027, 0x0027, 0x0027, 0x0027,},
        {0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0701, 0x0228, 0x0228, 0x0228, 0x0228, 0x0228,},};
}
