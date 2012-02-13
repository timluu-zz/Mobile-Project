
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

public class RpgCanvas extends GameCanvas implements Runnable {

    public final static int scrW = 240;			//	width of the view window (in pixel)
    public final static int scrH = 256;			//	height of the view window (in pixel)
    public final static int scrGW = (scrW / 16);	// 	width of the view window (in 16x16 grid)
    public final static int scrGH = (scrH / 16);	// 	height of the view window (in 16x16 grid)
    private TiledLayer gnd = null, obj = null;		//	ground layer and object layer
    private LayerManager map = null;
    public Sprite mainChr = null;				//	main character
    private GameData theGame;					//	store all game data which need be stored
    //	in RMS. 
    public Rpg parent = null;
    public TiledLayer dlgbox = null;
    private Graphics g = getGraphics();
    private int mapW, mapH;						//	width and height of current map
    private boolean pass[];
    private byte[] hasEvent;
    private Vector script[];
    public int dir = 2;
    private TiledLayer menuBack = null;
    private int stepsLeft;
    private int encAcc = 20;
    public SoundPlayer sp = new SoundPlayer();
    private Vector text;
    private static int[][] walkSeq = new int[4][2];

    static {
        walkSeq[0][0] = 0;
        walkSeq[0][1] = 1;
        walkSeq[1][0] = 2;
        walkSeq[1][1] = 3;
        walkSeq[2][0] = 4;
        walkSeq[2][1] = 5;
        walkSeq[3][0] = 6;
        walkSeq[3][1] = 7;
    }
    private int mapX, mapY;
    public BattleCanvas battle;
    public boolean bExit = false;

    public void render() {
//		System.out.println ("mapW:"+mapW+" mapH"+mapH+"\nplrX:"+theGame.playerX+"plrY"+theGame.playerY);
        g.setColor(0);
        g.fillRect(0, 0, scrW, scrH);
        mainChr.setPosition(theGame.playerX * 16, theGame.playerY * 16 - 8);
        int mx = theGame.playerX - scrGW / 2;
        if (mapW < scrGW) {
            mx = (mapW - scrGW) / 2;
        } else if (mx > (mapW - scrGW)) {
            mx = mapW - scrGW;
        } else if (mx < 0) {
            mx = 0;
        }
        mx *= 16;
        int my = (theGame.playerY - scrGH / 2);
        if (mapH < scrGH) {
            my = (mapH - scrGH) / 2;
        } else if (my > (mapH - scrGH)) {
            my = mapH - scrGH;
        } else if (my < 0) {
            my = 0;
        }
        my *= 16;
//		System.out.println ("x:"+theGame.playerX+" y:"+theGame.playerY);
        map.setViewWindow(mx, my, scrW, scrH);
        map.paint(g, 0, 0);
//		g.setColor(0xff0000);
//		g.fillRect(15,10,50*theGame.HP/theGame.MaxHP,5);
//		g.setColor(0xf0f00);
//		g.fillRect(15,15,50*theGame.EXP/theGame.expNext,5);
//		g.setColor(0xffffff);
//		g.drawRect(15,10,50,10);
        flushGraphics();
        mapX = mx;
        mapY = my;
    }

    public void paint(Graphics gr) {
//		render();
        gr.setColor(0xffffff);
        gr.fillRect(0, 0, 30, 30);
    }

    public RpgCanvas(GameData gd, Rpg r) {
        super(false);
//		Display.getDisplay(parent).vibrate(200);
//		ExceptionCanvas ec=new ExceptionCanvas("begin");
//		Display.getDisplay(r).setCurrent(ec);
        parent = r;
        battle = new BattleCanvas(gd, this);
        theGame = gd;
        text = new Vector();
        script = new Vector[2];
        script[0] = new Vector();
        script[1] = new Vector();
//		ec.setString("battleCanvas OK");
        try {
            Image gndImg = Image.createImage("/res/gnd.png");
//			ec.setString("img and map OK");
            Image objImg = Image.createImage("/res/obj.png");
            Image chrImg = Image.createImage("/res/chr.png");
            Image dlgImg = Image.createImage("/res/box.png");
            mainChr = new Sprite(chrImg, 16, 24);
            gnd = new TiledLayer(40, 40, gndImg, 16, 16);
            obj = new TiledLayer(40, 40, objImg, 16, 16);
            map = new LayerManager();
            gnd.createAnimatedTile(127);
            map.append(mainChr);
            map.append(obj);
            map.append(gnd);
            dlgbox = new TiledLayer(scrW / 16, 4, dlgImg, 16, 16);
            int i, j;
            dlgbox.setCell(0, 0, 1);
            for (i = 1; i < scrW / 16 - 1; i++) {
                dlgbox.setCell(i, 0, 2);
            }
            dlgbox.setCell(scrW / 16 - 1, 0, 3);
            for (j = 1; j < 3; j++) {
                dlgbox.setCell(0, j, 4);
                for (i = 1; i < scrW / 16 - 1; i++) {
                    dlgbox.setCell(i, j, 5);
                }
                dlgbox.setCell(scrW / 16 - 1, j, 6);
            }
            dlgbox.setCell(0, 3, 7);
            for (i = 1; i < scrW / 16 - 1; i++) {
                dlgbox.setCell(i, 3, 8);
            }
            dlgbox.setCell(scrW / 16 - 1, 3, 9);
//			ec.setString("dlgbox OK");
            menuBack = new TiledLayer(scrW / 16, scrH / 16, dlgImg, 16, 16);
            menuBack.fillCells(0, 0, scrW / 16, scrH / 16, 5);
            menuBack.setCell(0, 0, 1);
            for (i = 1; i < scrW / 16 - 1; i++) {
                menuBack.setCell(i, 0, 2);
            }
            menuBack.setCell(scrW / 16 - 1, 0, 3);
            for (i = 1; i < scrH / 16 - 1; i++) {
                menuBack.setCell(0, i, 4);
                menuBack.setCell(scrW / 16 - 1, i, 6);
            }
            menuBack.setCell(0, scrH / 16 - 1, 7);
            for (i = 1; i < scrW / 16 - 1; i++) {
                menuBack.setCell(i, scrH / 16 - 1, 8);
            }
            menuBack.setCell(scrW / 16 - 1, scrH / 16 - 1, 9);
//			ec.setString("menu OK");
        } catch (Throwable ioe) {
//			System.out.println (ioe.getMessage());
            //	Display.getDisplay(r).setCurrent(new ExceptionCanvas(ioe.getMessage()));
        }
    }

    protected void loadMap(String filename) {
//		System.out.println ("Loading "+filename);
        script[0].removeAllElements();
        script[1].removeAllElements();
        theGame.enemy.removeAllElements();
        text.removeAllElements();
        g.setColor(0);
        g.fillRect(0, 0, scrW, scrH);
        g.setColor(0xffffff);
        g.drawString("Download��...", scrW - Font.getDefaultFont().stringWidth("Download��...����!") - 20, scrH - 30, Graphics.TOP | Graphics.LEFT);
        flushGraphics();
        gnd.fillCells(0, 0, 39, 39, 0);
        obj.fillCells(0, 0, 39, 39, 0);
        int d, d2, d3;
        int[] cnt = new int[2];
        cnt[0] = 0;
        cnt[1] = 0;
        byte[] bt;
        String t = null;
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            mapW = is.read();
            mapH = is.read();
            pass = new boolean[mapW * mapH];
            hasEvent = new byte[mapW * mapH];
            int i, j, k = 0;
            d = is.read();
            d2 = is.read();
            bt = new byte[d * 256 + d2];
            is.read(bt);
            script[1].addElement(bt);
            gnd.fillCells(0, 0, 40, 40, 116);
            for (j = 0; j < mapH; j++) {
                for (i = 0; i < mapW; i++) {
                    d = is.read();
                    if (d == 127) {
                        gnd.setCell(i, j, -1);
                    } else {
                        gnd.setCell(i, j, d);
                    }
                    obj.setCell(i, j, is.read());
                    pass[j * mapW + i] = is.read() == 0 ? false : true;
                    d3 = is.read();
                    if (d3 == 0) {
                        hasEvent[k] = 0;
                        k++;
                        continue;
                    }
                    d3 -= 1;
                    hasEvent[k] = (byte) (d3 << 7);
                    d = is.read();
                    d2 = is.read();
                    bt = new byte[d * 256 + d2];
                    is.read(bt);
                    script[d3].addElement(bt);
                    cnt[d3] += 1;
                    hasEvent[k] |= (cnt[d3] & 0x7f);
                    k++;
                }
            }
//			testScript();
            is.close();
            is = getClass().getResourceAsStream(filename + ".txt");
            is.read();
            is.read();
            StringBuffer sb = new StringBuffer();
            while ((d = is.read()) != -1) {
                d2 = is.read();
                if (d == 0x00 && d2 == 0x0a) {
                    t = new String(sb);
                    text.addElement(t);
                    sb.setLength(0);
                } else {
                    sb.append((char) ((d << 8) | d2));
                }
            }
            is.close();
            g.drawString("Download��...����!", scrW - Font.getDefaultFont().stringWidth("Download��...����!") - 20, scrH - 30, Graphics.TOP | Graphics.LEFT);
            flushGraphics();
        } catch (IOException ioe) {
//			System.out.println (ioe.getMessage());
        }
        runScript((byte[]) script[1].elementAt(0));
    }

    public void run() {
//		setFullScreenMode(true);
//		Display.getDisplay(parent).setCurrent(battle);
//		battle.startBattle(0);
        //System.gc();
        bExit = false;
        sp.setEnable(theGame.sound);
        sp.setListener(battle);
        loadMap(theGame.curMap);
        int key;
        boolean bPaint = true;
        long time, last, waterLast;
        mainChr.setFrameSequence(walkSeq[dir]);
        last = 0;
        waterLast = 0;
        stepsLeft = Math.abs(battle.rnd.nextInt() % (20 - theGame.enemy.size()));
        while (true) {
            time = System.currentTimeMillis();
            if ((time - last) > 300) {
                last = time;
                mainChr.nextFrame();
                bPaint = true;
            }
            if ((time - waterLast) > 605) {
                waterLast = time;
                gnd.setAnimatedTile(-1, gnd.getAnimatedTile(-1) == 127 ? 128 : 127);
                bPaint = true;
            }
            if (bExit) {
                return;
            }
            if (stepsLeft < 0) {
                if (theGame.enemy.size() > 0) {
                    stepsLeft = Math.abs(battle.rnd.nextInt() % theGame.enemy.size());
                    drawText(null, battle.name[((Integer) theGame.enemy.elementAt(stepsLeft)).intValue()] + " �o�Ă���!");
                    Display.getDisplay(parent).setCurrent(battle);
                    if (battle.startBattle(((Integer) theGame.enemy.elementAt(stepsLeft)).intValue()) == 1) {
                        endGame();
                        parent.setState(parent.STATE_MENU);
                        return;
                    } else {
                        Display.getDisplay(parent).setCurrent(this);
                    }
                }
                stepsLeft = 5 + Math.abs(battle.rnd.nextInt() % (20 - theGame.enemy.size()));
            }
            key = getKeyStates();
            if ((key & UP_PRESSED) != 0) {
                if (dir != 0) {
                    dir = 0;
                    mainChr.setFrameSequence(walkSeq[dir]);
                    bPaint = true;
                } else {
                    if ((theGame.playerY > 0) && !pass[(theGame.playerY - 1) * mapW + theGame.playerX]) {
                        theGame.playerY -= 1;
                        stepsLeft--;
                        bPaint = true;
                        handleEvent(true);
                    }
                }
            } else if ((key & DOWN_PRESSED) != 0) {
                if (dir != 2) {
                    dir = 2;
                    mainChr.setFrameSequence(walkSeq[dir]);
                    bPaint = true;
                } else {
                    if ((theGame.playerY < mapH - 1) && !pass[(theGame.playerY + 1) * mapW + theGame.playerX]) {
                        theGame.playerY += 1;
                        stepsLeft--;
                        bPaint = true;
                        handleEvent(true);
                    }
                }
            } else if ((key & LEFT_PRESSED) != 0) {
                if (dir != 3) {
                    dir = 3;
                    mainChr.setFrameSequence(walkSeq[dir]);
                    bPaint = true;
                } else {
                    if ((theGame.playerX > 0) && !pass[theGame.playerY * mapW + theGame.playerX - 1]) {
                        theGame.playerX -= 1;
                        stepsLeft--;
                        bPaint = true;
                        handleEvent(true);
                    }
                }
            } else if ((key & RIGHT_PRESSED) != 0) {
                if (dir != 1) {
                    dir = 1;
                    mainChr.setFrameSequence(walkSeq[dir]);
                    bPaint = true;
                } else {
                    if ((theGame.playerX < mapW - 1) && !pass[theGame.playerY * mapW + theGame.playerX + 1]) {
                        theGame.playerX += 1;
                        stepsLeft--;
                        bPaint = true;
                        handleEvent(true);
                    }
                }
            } else if ((key & FIRE_PRESSED) != 0) {
                handleEvent(false);
            } else if ((key & GAME_A_PRESSED) != 0) {
                drawStatus();
                bPaint = true;
            } else if ((key & GAME_C_PRESSED) != 0) {
                drawMenu();
                bPaint = true;
            } else if ((key & GAME_D_PRESSED) != 0) {
                drawMonster();
                bPaint = true;
            }
            if (bPaint) {
                render();
//				repaint();
                bPaint = false;
            }
            pause(1);
        }
    }

    private void handleEvent(boolean type) {
//		System.out.println ("handleEvent("+type+")");
        int i, j;
        if (type) {		// hit
            i = theGame.playerX + theGame.playerY * mapW;
//			System.out.println (Integer.toBinaryString(hasEvent[i]&0xff));
            if (hasEvent[i] == 0 || (hasEvent[i] & 0x80) != 0) {
                return;
            }
            j = hasEvent[i] & 0x7f;
            byte[] s = (byte[]) (script[0].elementAt(j - 1));
            render();
            runScript(s);
        } else {			// press
            if (dir == 0 && theGame.playerY != 0) {
                i = theGame.playerX + (theGame.playerY - 1) * mapW;
            } else if (dir == 1 && theGame.playerX != mapW - 1) {
                i = theGame.playerX + theGame.playerY * mapW + 1;
            } else if (dir == 2 && theGame.playerY != mapH - 1) {
                i = theGame.playerX + (theGame.playerY + 1) * mapW;
            } else if (dir == 3 && theGame.playerX != 0) {
                i = theGame.playerX - 1 + theGame.playerY * mapW;
            } else {
                return;
            }
//			System.out.println (Integer.toBinaryString(hasEvent[i]&0xff));
            if ((hasEvent[i] & 0x80) == 0) {
                return;
            }
            j = hasEvent[i] & 0x7f;
//			System.out.println ("j="+j);
            byte[] s = (byte[]) (script[1].elementAt(j));
            render();
            runScript(s);
        }
    }

    protected void runScript(byte[] scr) {
        int p = 0, d1, d2, d3, t;
        while (p < scr.length) {
//			System.out.println ("p="+p);
            switch (scr[p++] & 0xff) {
                case 0:	//	IF 
                    if (scr[p] != 0 && !theGame.flags[scr[p] & 0xff]) {
                        p += 1;
                        p = findNext(scr, 255, p) + 1;
//					System.out.println ("IF(false)");
                    } else {
                        p++;/*
                         * System.out.println ("IF(true)");
                         */
                    }
                    break;
                case 1:	//	GND
//				System.out.println ("GND");
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    d3 = scr[p++] & 0xff;
                    gnd.setCell(d1, d2, d3 == 0x7f ? -1 : d3);
                    break;
                case 2:	//	OBJ
//				System.out.println ("OBJ");
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    d3 = scr[p++] & 0xff;
                    obj.setCell(d1, d2, d3 == 0x7f ? -1 : d3);
                    break;
                case 3:	//	SOUND
//				System.out.println ("SOUND");
                    d1 = scr[p++] & 0xff;
                    parent.setState(parent.STATE_MENU);
                    bExit = true;
                    return;
                // todo: play s.e. here
                case 4:	//	BGM
//				System.out.println ("BGM");
                    d1 = scr[p++] & 0xff;
                    sp.playBgm(d1 - 1);
                    // todo: change bgm
                    break;
                case 5:	//	MVSCR
//				System.out.println ("MVSCR");
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    g.setColor(0);
//				g.fillRect(0,0,scrW,scrH);
                    for (d3 = 0; d3 < 30; d3 += 3) {
                        g.fillRect(0, d3, scrW, 3);
                        g.fillRect(0, scrH - d3, scrW, 3);
                        flushGraphics();
                        pause(20);
                    }
                    g.setClip(0, 30, scrW, scrH - 60);
                    for (d3 = 0; d3 < d2 * 4; d3++) {
                        if (d1 == 0) {
                            map.setViewWindow(mapX, mapY -= 4, scrW, scrH);
                        } else if (d1 == 1) {
                            map.setViewWindow(mapX += 4, mapY, scrW, scrH);
                        } else if (d1 == 2) {
                            map.setViewWindow(mapX, mapY += 4, scrW, scrH);
                        } else if (d1 == 3) {
                            map.setViewWindow(mapX -= 4, mapY, scrW, scrH);
                        }
//					System.out.println ("("+mapX+","+mapY+")");
                        map.paint(g, 0, 0);
                        flushGraphics();
                        pause(70);
                    }
                    pause(100);
                    for (d3 = 0; d3 < d2; d3++) {
                        if (d1 == 0) {
                            map.setViewWindow(mapX, mapY += 16, scrW, scrH);
                        } else if (d1 == 1) {
                            map.setViewWindow(mapX -= 16, mapY, scrW, scrH);
                        } else if (d1 == 2) {
                            map.setViewWindow(mapX, mapY -= 16, scrW, scrH);
                        } else if (d1 == 3) {
                            map.setViewWindow(mapX += 16, mapY, scrW, scrH);
                        }
                        map.paint(g, 0, 0);
                        flushGraphics();
                        pause(300);
                    }
                    g.setClip(0, 0, scrW, scrH);
                    break;
                case 6:	//	MVCHR
//				System.out.println ("MVCHR");
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    dir = d1;
                    mainChr.setFrameSequence(walkSeq[dir]);
                    d3 = getKeyStates();
                    for (d3 = 0; d3 < d2; d3++) {
                        if (d1 == 0) {
                            theGame.playerY -= 1;
                        } else if (d1 == 1) {
                            theGame.playerX += 1;
                        } else if (d1 == 2) {
                            theGame.playerY += 1;
                        } else if (d1 == 3) {
                            theGame.playerX -= 1;
                        }
                        mainChr.nextFrame();
                        render();
                        pause(300);
                    }
                    break;
                case 7:	//	SET
//				System.out.println ("SET");
                    theGame.flags[scr[p++] & 0xff] = true;
                    break;
                case 8:	//	RESET
//				System.out.println ("RESET");
                    theGame.flags[scr[p++] & 0xff] = false;
                    break;
                case 9:	//	TEXT
//				System.out.println ("TEXT");
                    //	todo:	display the text
                    d1 = scr[p++] & 0xff;
                    drawText(null, (String) text.elementAt(d1));
                    break;
                case 10:	//	TALK
//				System.out.println ("TALK");
                    //	todo:	display dialog
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    drawText((String) text.elementAt(d1), (String) text.elementAt(d2));
                    break;
                case 11:	// 	SETPASS
//				System.out.println ("SETPASS");
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    d3 = scr[p++] & 0xff;
                    pass[d2 * mapW + d1] = (d3 == 0 ? true : false);
                    break;
                case 12:	// 	CHGMAP
//				System.out.println ("CHGMAP");
                    //	todo:	change map
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    d3 = scr[p++] & 0xff;
                    theGame.playerX = d2;
                    theGame.playerY = d3;
                    theGame.curMap = (String) text.elementAt(d1);
                    loadMap(theGame.curMap);
                    return;
                case 13:	//	FLUSH
//				System.out.println ("FLUSH");
                    render();
                    break;
                case 14:	//	WAIT
//				System.out.println ("WAIT");
                    d1 = scr[p++] & 0xff;
                    pause(d1 * 50);
                    break;
                case 255:	//	END
//				System.out.println ("END");
                    break;
                case 15:	//	IFNOT
                    if (scr[p] != 0 && theGame.flags[scr[p] & 0xff]) {
                        p += 1;
                        p = findNext(scr, 255, p) + 1;
//					System.out.println ("IFNOT(true)");
                    } else {
                        p++;/*
                         * System.out.println ("IFNOT(false)");
                         */
                    }
                    break;
                case 16:	//	BRK
                    return;
                case 17:	//	BATTLE
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    Display.getDisplay(parent).setCurrent(battle);
                    if (battle.startBattle(d1) == 0) {
                        theGame.flags[d2] = true;
                    } else {
                        theGame.flags[d2] = false;
                    }
                    Display.getDisplay(parent).setCurrent(this);
                    break;
                case 18:	//	ADDENEMY
                    d1 = scr[p++] & 0xff;
                    theGame.enemy.addElement(new Integer(d1));
                    break;
                case 19:	//	CHOOSE
                    d1 = scr[p++] & 0xff;
                    doChoose(d1);
                    break;
                case 20:	//	GAMEOVER
                    endGame();
                    parent.setState(parent.STATE_MENU);
                    bExit = true;
                    return;
                case 21:	//	SHOP
                    shop();
                    break;
                case 22:	//	ADD
                    d1 = scr[p++] & 0xff;
                    d2 = scr[p++] & 0xff;
                    switch (d1) {
                        case 1:
                            theGame.money += d2;
                            break;
                        case 2:
                            theGame.HP += d2;
                            if (theGame.HP > theGame.MaxHP) {
                                theGame.HP = theGame.MaxHP;
                            }
                            break;
                        case 3:
                            theGame.ATK += d2;
                            break;
                        case 4:
                            theGame.DEF += d2;
                            break;
                        case 5:
                            theGame.EXP += d2;
                            if (theGame.checkUP()) {
//                                drawText(null, "Hero�\�͌�サ��!");
                            }
                            break;
                        case 6:
                            theGame.AGI += d2;
                            break;
                        case 7:
                            theGame.item[0] += d2;
                            break;
                        case 8:
                            theGame.item[1] += d2;
                            break;
                        case 9:
                            theGame.item[2] += d2;
                            break;
                    }
                    break;
                default:
//				System.out.println ("Undefined command!");
                    break;
            }
        }
    }

    protected int findNext(byte[] b, int val, int nStart) {
        byte v = (byte) (val & 0xff);
        while (nStart < b.length) {
            if (b[nStart] == v) {
                return nStart;
            }
            nStart++;
        }
        return 0xffff;
    }

    public void pause(long nMillis) {
        try {
            Thread.currentThread().sleep(nMillis);
        } catch (InterruptedException ie) {
//			System.out.println (ie.getMessage());
        }
    }
//	private void testScript(){
//		int i,j;
//		for(i=0;i<2;i++){
//			for(j=0;j<script[i].size();j++){
//				System.out.print(((byte[])(script[i].elementAt(j))).length+" ");
//			}
//			System.out.print("\n");
//		}
//	}

    private void drawText(String chr, String t) {
        dlgbox.setPosition(0, scrH - 56);
        Font font = Font.getDefaultFont();
        int p = 0, j;
        int l = t.length();
        for (j = 0; j < 56; j += 5) {
            dlgbox.setPosition(0, scrH - j);
            dlgbox.paint(g);
            flushGraphics();
        }
        if (chr == null) {
            g.setColor(0);
            while (p < l) {
                dlgbox.paint(g);
                j = 1;
                while (p + j < l && font.substringWidth(t, p, j) < scrW - 12) {
                    j++;
                }
                if (p + j > l) {
                    g.drawString(t.substring(p, l - 1), 5, scrH - 3 - font.getHeight() * 2, Graphics.TOP | Graphics.LEFT);
                    p = l;
                } else {
                    g.drawString(t.substring(p, p + j), 5, scrH - 3 - font.getHeight() * 2, Graphics.TOP | Graphics.LEFT);
                    p += j;
                }
                j = 1;
                while (p + j < l && font.substringWidth(t, p, j) < scrW - 12) {
                    j++;
                }
                if (p != l) {
                    if (p + j > l) {
                        g.drawString(t.substring(p, l - 1), 5, scrH - 2 - font.getHeight() * 1, Graphics.TOP | Graphics.LEFT);
                        p = l;
                    } else {
                        g.drawString(t.substring(p, p + j), 5, scrH - 2 - font.getHeight() * 1, Graphics.TOP | Graphics.LEFT);
                        p += j;
                    }
                }
                flushGraphics();
                waitKeyRelease(FIRE_PRESSED);
            }
        } else {
            while (p < l) {
                dlgbox.paint(g);
                g.setColor(0xff0000);
                g.drawString(chr, 6, scrH - 2 - font.getHeight() * 3, Graphics.TOP | Graphics.LEFT);
                g.setColor(0);
                j = 1;
                while (p + j < l && font.substringWidth(t, p, j) < scrW - 12) {
                    j++;
                }
                if (p + j > l) {
                    g.drawString(t.substring(p, l - 1), 5, scrH - 3 - font.getHeight() * 2, Graphics.TOP | Graphics.LEFT);
                    p = l;
                } else {
                    g.drawString(t.substring(p, p + j), 5, scrH - 3 - font.getHeight() * 2, Graphics.TOP | Graphics.LEFT);
                    p += j;
                }
                j = 1;
                while (p + j < l && font.substringWidth(t, p, j) < scrW - 12) {
                    j++;
                }
                if (p != l) {
                    if (p + j > l) {
                        g.drawString(t.substring(p, l - 1), 5, scrH - 2 - font.getHeight() * 1, Graphics.TOP | Graphics.LEFT);
                        p = l;
                    } else {
                        g.drawString(t.substring(p, p + j), 5, scrH - 2 - font.getHeight() * 1, Graphics.TOP | Graphics.LEFT);
                        p += j;
                    }
                }
                flushGraphics();
                waitKeyRelease(FIRE_PRESSED);
            }
        }
        for (j = 56; j > 0; j -= 16) {
            dlgbox.setPosition(0, scrH - j);
            g.setColor(0);
            g.fillRect(0, 0, scrW, scrH);
            map.paint(g, 0, 0);
            dlgbox.paint(g);
            flushGraphics();
        }
    }

    private int waitKeyRelease(int keyCode) {
        int ok, ck;
        while ((ok = getKeyStates() & keyCode) != 0);
        while ((ok = getKeyStates() & keyCode) == 0);
        while (true) {
            ck = getKeyStates() & keyCode;
            if (ck < ok) {
                break;
            }
        }
        return ok;
    }

    private void drawStatus() {
        for (int i = 0; i <= scrW / 2; i += 12) {
//			map.paint(g,0,0);
            g.setClip(scrW / 2 - i, 0, i, scrH);
            menuBack.setPosition(scrW / 2 - i, 0);
            menuBack.paint(g);
            g.setClip(scrW / 2, 0, i, scrH);
            menuBack.setPosition(i - scrW / 2, 0);
            menuBack.paint(g);
            flushGraphics(scrW / 2 - i, 0, i * 2, scrH);
//			pause(2);
        }
        g.setClip(0, 0, scrW, scrH);
        mainChr.setPosition(21, 15);
        dir = 2;
        mainChr.setFrameSequence(walkSeq[2]);
        mainChr.paint(g);
        g.setColor(64, 12, 128);
        g.drawString("Hero", 15, 43, Graphics.TOP | Graphics.LEFT);
        g.drawString("HP", 100, 20, Graphics.TOP | Graphics.LEFT);
        g.drawString("Exp", 100, 40, Graphics.TOP | Graphics.LEFT);
        g.drawString("Next", 100, 60, Graphics.TOP | Graphics.LEFT);
        g.drawString("Atk", 15, 90, Graphics.TOP | Graphics.LEFT);
        g.drawString("Def", 80, 90, Graphics.TOP | Graphics.LEFT);
        g.drawString("Agi", 145, 90, Graphics.TOP | Graphics.LEFT);
        g.drawString("�p��", 15, 135, Graphics.TOP | Graphics.LEFT);
        g.drawString("��", 90, 135, Graphics.TOP | Graphics.LEFT);
        g.drawString("��", 15, 155, Graphics.TOP | Graphics.LEFT);
        if (theGame.flags[4] && !theGame.flags[6]) {
            g.drawString("��", 15, 175, Graphics.TOP | Graphics.LEFT);
        }
        if (theGame.flags[7]) {
            g.drawString("�_���̖{", 90, 155, Graphics.TOP | Graphics.LEFT);
        }
        if (theGame.flags[17] && !theGame.flags[19]) {
            g.drawString("�ݕ�����", 90, 175, Graphics.TOP | Graphics.LEFT);
        }
        if (theGame.flags[17] && theGame.flags[19] && !theGame.flags[18]) {
            g.drawString("?��", 15, 195, Graphics.TOP | Graphics.LEFT);
        }
        if (theGame.flags[23]) {
            g.drawString("����", 90, 195, Graphics.TOP | Graphics.LEFT);
        }
        if (theGame.flags[25]) {
            g.drawString("�ނ�̂���", 15, 215, Graphics.TOP | Graphics.LEFT);
        }
        if (theGame.flags[34]) {
            g.drawString("����", 90, 215, Graphics.TOP | Graphics.LEFT);
        }
        g.setColor(0x00ff00);
        g.drawString("" + theGame.HP, 140, 20, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.EXP, 140, 40, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + (theGame.expNext - theGame.EXP), 140, 60, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.ATK, 45, 90, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.DEF, 110, 90, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.AGI, 175, 90, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.item[0], 50, 135, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.item[1], 125, 135, Graphics.TOP | Graphics.LEFT);
        g.drawString("" + theGame.item[2], 50, 155, Graphics.TOP | Graphics.LEFT);

        g.setColor(0);
        g.drawString("/ " + theGame.MaxHP, 180, 20, Graphics.TOP | Graphics.LEFT);
        g.drawString("LV " + theGame.LV, 12, 60, Graphics.TOP | Graphics.LEFT);
        g.drawLine(10, 120, 160, 120);
        flushGraphics();
        waitKeyRelease(FIRE_PRESSED);
    }

    private void doChoose(int nFlag) {
        int k, t = 0;
        while (nFlag == 999) {
            dlgbox.setPosition(0, scrH - 56);
            dlgbox.paint(g);
            g.setColor(64, 12, 128);
            g.drawString("�{���ɃQ�[����ޏo����?", 35, scrH - 45, Graphics.TOP | Graphics.LEFT);
            g.drawString("������", 40, scrH - 25, Graphics.TOP | Graphics.LEFT);
            g.drawString("�͂�", scrW / 2 + 40, scrH - 25, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xff00000);
            g.drawString("*", scrW / 2 * t + 20, scrH - 25, Graphics.TOP | Graphics.LEFT);
            flushGraphics();
            k = waitKeyRelease(LEFT_PRESSED | RIGHT_PRESSED | FIRE_PRESSED);
            if ((k & LEFT_PRESSED) != 0 || (k & RIGHT_PRESSED) != 0) {
                t = 1 - t;
            } else if ((k & FIRE_PRESSED) != 0) {
                bExit = (t == 0 ? false : true);
                if (bExit) {
                    parent.setState(Rpg.STATE_MENU);
                }
                return;
            }
        }
        while (true) {
            dlgbox.setPosition(0, scrH - 56);
            dlgbox.paint(g);
            g.setColor(64, 12, 128);
            g.drawString("�͂�", 40, scrH - 45, Graphics.TOP | Graphics.LEFT);
            g.drawString("������", scrW / 2 + 40, scrH - 45, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xff00000);
            g.drawString("*", scrW / 2 * t + 20, scrH - 45, Graphics.TOP | Graphics.LEFT);
            flushGraphics();
            k = waitKeyRelease(LEFT_PRESSED | RIGHT_PRESSED | FIRE_PRESSED);
            if ((k & LEFT_PRESSED) != 0 || (k & RIGHT_PRESSED) != 0) {
                t = 1 - t;
            } else if ((k & FIRE_PRESSED) != 0) {
                theGame.flags[nFlag] = (t == 0 ? true : false);
                return;
            }
        }
    }

    private void endGame() {
        drawText("�D��", "����ŏI���ł���?");
    }

    private void drawMonster() {
        for (int i = 0; i <= scrW / 2; i += 12) {
//			map.paint(g,0,0);
            g.setClip(scrW / 2 - i, 0, i, scrH);
            menuBack.setPosition(scrW / 2 - i, 0);
            menuBack.paint(g);
            g.setClip(scrW / 2, 0, i, scrH);
            menuBack.setPosition(i - scrW / 2, 0);
            menuBack.paint(g);
            flushGraphics(scrW / 2 - i, 0, i * 2, scrH);
//			pause(2);
        }
        g.setClip(0, 0, scrW, scrH);
        menuBack.setPosition(0, 0);
        int sel = 0, k, i;
        while (true) {
            menuBack.paint(g);
            g.setColor(0);
            g.drawString("�����m�[�h", scrW / 2 - 30, 20, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xff0f0f);
            g.fillRect(15, 42, scrW - 30, 2);
            g.setClip(20, 70, 200, 120);
            i = 0;
            for (int j = sel - 2; j < sel + 3; j++) {
                if (j < 0) {
                    k = GameData.nMonsterCount + j;
                } else if (j >= GameData.nMonsterCount) {
                    k = j - GameData.nMonsterCount;
                } else {
                    k = j;
                }
                if (theGame.monsters[k]) {
                    battle.enemy.setPosition(25, 60 + i * 30);
                    battle.enemy.setFrame(BattleCanvas.enemyData[k][0]);
                    battle.enemy.paint(g);
                    g.setColor(0);
                    g.drawString("Name:" + BattleCanvas.name[k], 60, 68 + i * 30, Graphics.TOP | Graphics.LEFT);
                } else {
                    g.setColor(0);
                    g.drawRect(25, 60 + i * 30, 16, 24);
                    //g.drawString("?"+k,30,72+i*26,Graphics.TOP|Graphics.LEFT);
                    g.drawString("Name:  ???", 60, 68 + i * 30, Graphics.TOP | Graphics.LEFT);
                }
                i++;
            }
            g.setClip(0, 0, scrW, scrH);
            dlgbox.setPosition(0, scrH - 56);
            dlgbox.paint(g);
            g.setColor(0xff0000);
            g.drawRect(24, 59 + 2 * 30, 190, 26);
            if (theGame.monsters[sel]) {
                g.setColor(0);
                g.drawString("HP", 25, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("Agi", 125, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("Atk", 25, scrH - 28, Graphics.TOP | Graphics.LEFT);
                g.drawString("Def", 125, scrH - 28, Graphics.TOP | Graphics.LEFT);
                g.setColor(64, 12, 128);
                g.drawString("" + BattleCanvas.enemyData[sel][1], 70, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("" + BattleCanvas.enemyData[sel][5], 170, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("" + BattleCanvas.enemyData[sel][2], 70, scrH - 28, Graphics.TOP | Graphics.LEFT);
                g.drawString("" + BattleCanvas.enemyData[sel][3], 170, scrH - 28, Graphics.TOP | Graphics.LEFT);
            } else {
                g.setColor(0);
                g.drawString("HP", 25, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("Agi", 125, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("Atk", 25, scrH - 28, Graphics.TOP | Graphics.LEFT);
                g.drawString("Def", 125, scrH - 28, Graphics.TOP | Graphics.LEFT);
                g.setColor(64, 12, 128);
                g.drawString("???", 70, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("???", 170, scrH - 50, Graphics.TOP | Graphics.LEFT);
                g.drawString("???", 70, scrH - 28, Graphics.TOP | Graphics.LEFT);
                g.drawString("???", 170, scrH - 28, Graphics.TOP | Graphics.LEFT);
            }
            flushGraphics();
            k = waitKeyRelease(FIRE_PRESSED | UP_PRESSED | DOWN_PRESSED);
            if ((k & UP_PRESSED) != 0) {
                if (sel == 0) {
                    sel = theGame.nMonsterCount - 1;
                } else {
                    sel -= 1;
                }
            } else if ((k & DOWN_PRESSED) != 0) {
                if (sel == theGame.nMonsterCount - 1) {
                    sel = 0;
                } else {
                    sel += 1;
                }
            } else if ((k & FIRE_PRESSED) != 0) {
                break;
            }
        }
    }

    private void shop() {
        drawText("���X", "���𔃂������ł���?");
        int sel = 0, k;
        while (true) {
            dlgbox.setPosition(0, scrH - 56);
            dlgbox.paint(g);
            g.setColor(64, 12, 128);
            g.drawString("�p��  30G", 40, scrH - 45, Graphics.TOP | Graphics.LEFT);
            g.drawString("��  100G", scrW / 2 + 40, scrH - 45, Graphics.TOP | Graphics.LEFT);
            g.drawString("��  200G", 40, scrH - 25, Graphics.TOP | Graphics.LEFT);
            g.drawString("�����", scrW / 2 + 40, scrH - 25, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xff00000);
            g.drawString("*", (sel + 1) / 2 == sel / 2 ? 20 : scrW / 2 + 20, sel < 2 ? scrH - 45 : scrH - 25, Graphics.TOP | Graphics.LEFT);
            flushGraphics();
            k = waitKeyRelease(LEFT_PRESSED | RIGHT_PRESSED | FIRE_PRESSED);
            if ((k & LEFT_PRESSED) != 0) {
                if (sel != 0) {
                    sel -= 1;
                }
            } else if ((k & RIGHT_PRESSED) != 0) {
                if (sel != 3) {
                    sel += 1;
                }
            } else if ((k & FIRE_PRESSED) != 0) {
                if (sel == 0 && theGame.money >= 30) {
                    drawText("Hero", "�p���𔃂��܂��傤.");
                    theGame.item[0] += 1;
                    theGame.money -= 30;
                } else if (sel == 1 && theGame.money >= 100) {
                    drawText("Hero", "��𔃂�����.");
                    theGame.item[1] += 1;
                    theGame.money -= 100;
                } else if (sel == 2 && theGame.money >= 200) {
                    drawText("Hero", "���ꂪ�ق����̂�.");
                    theGame.item[2] += 1;
                    theGame.money -= 200;
                } else if (sel == 3) {
                    drawText("Hero", "����Ȃ��ɂ��悤.");
                    return;
                } else {
                    drawText("Hero", "����𔃂��Ȃ�.");
                }
                return;
            }
        }
    }

    private void drawMenu() {
        int sel = 0, k;
        for (int i = 0; i <= scrW / 2; i += 12) {
//			map.paint(g,0,0);
            g.setClip(scrW / 2 - i, 0, i, scrH);
            menuBack.setPosition(scrW / 2 - i, 0);
            menuBack.paint(g);
            g.setClip(scrW / 2, 0, i, scrH);
            menuBack.setPosition(i - scrW / 2, 0);
            menuBack.paint(g);
            flushGraphics(scrW / 2 - i, 0, i * 2, scrH);
//			pause(2);
        }
        g.setClip(0, 0, scrW, scrH);
        menuBack.setPosition(0, 0);
        while (true) {
            menuBack.paint(g);
            g.setColor(0);
            g.drawString("PAUSE", scrW / 2 - 30, 20, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xff0f0f);
            g.fillRect(15, 42, scrW - 30, 2);
            g.setColor(64, 12, 128);
            g.drawString("�Q�[���ɖ߂�", scrW / 2 - 40, 70, Graphics.TOP | Graphics.LEFT);
            g.drawString("�i�x�ۑ�", scrW / 2 - 40, 90, Graphics.TOP | Graphics.LEFT);
            g.drawString("����" + (theGame.sound ? "�߂�" : "�J��"), scrW / 2 - 40, 110, Graphics.TOP | Graphics.LEFT);
            g.drawString("Menu�ɖ߂�", scrW / 2 - 40, 130, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xff0000);
            g.drawString("*", scrW / 2 - 60, 70 + sel * 20, Graphics.TOP | Graphics.LEFT);
            flushGraphics();
            k = waitKeyRelease(UP_PRESSED | DOWN_PRESSED | FIRE_PRESSED);
            if ((k & UP_PRESSED) != 0) {
                if (sel != 0) {
                    sel -= 1;
                }
            } else if ((k & DOWN_PRESSED) != 0) {
                if (sel != 3) {
                    sel += 1;
                }
            } else if ((k & FIRE_PRESSED) != 0) {
                switch (sel) {
                    case 0:
                        return;
                    case 1:
                        if (theGame.saveData()) {
                            drawText(null, "�ۑ��I���!");
                        } else {
                            drawText(null, "Failed!");
                        }
                        return;
                    case 2:
                        theGame.sound = !theGame.sound;
                        sp.setEnable(theGame.sound);
                        break;
                    case 3:
                        doChoose(999);
                        return;
                }
            }
        }
    }
}
