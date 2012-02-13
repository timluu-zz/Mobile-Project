
/**
 * BasicIsoGameCanvas Used Preprocessor defines: NOKIA_FULLCANVAS = use
 * com.nokia.mid.ui.FullCanvas instead of Canvas MIDP2_CANVAS = use GameCanvas
 * and call setFullScreenMode(true)
 *
 * APPLET = compile with low framerate in order to publish on web site an
 * emulator as an Applet (MicroEmulator)
 *
 * PRINT_FPS = render framerate on screen DEBUGGING = print debug information
 */
import isoj2me.Board;
import isoj2me.Element;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import javax.microedition.lcdui.Font;

//#ifdef NOKIA_FULLCANVAS
//# import com.nokia.mid.ui.FullCanvas;
//# import javax.microedition.lcdui.Canvas;
//# public class BasicIsoGameCanvas extends FullCanvas implements Runnable {
//#else
//#ifndef MIDP2_CANVAS
import javax.microedition.lcdui.Canvas;

public class BasicIsoGameCanvas extends Canvas implements Runnable {
    //#else
    //#	import javax.microedition.lcdui.game.GameCanvas;
    //# import javax.microedition.lcdui.Canvas;
    //#	public class BasicIsoGameCanvas extends GameCanvas implements Runnable {
    //#endif
//#endif

    //#ifndef APPLET
    private static final long DELAY = 10; //Global thread delay
    //#else
    //# private static final long DELAY = 70;
    //#endif
    private static final int DELAY_LOGO = 2000; //How long the logo have to be displayed
    private static final int INPUT_DELAY = 50; //Delay between two user's input
    private static final int INPUT_SELECT = Canvas.KEY_NUM5;
    /**
     * Menu selection constants and Strings
     */
    private static final int MENU_STARTDEMO = 0;
    private static final int MENU_CONTINUE = 1;
    private static final int MENU_OPTIONS = 2;
    private static final int MENU_HELP = 3;
    private static final int MENU_CREDITS = 4;
    private static final int MENU_EXIT = 5;
    private static final String[] optionsLabel = {"Start Demo", "Continue", "Options", "Help", "Credits", "Exit"};
    /**
     * Options selection constants and Strings
     */
    private static final int OPTIONS_SOUNDS = 0;
    private static final int OPTIONS_VIBRA = 1;
    private static final int BACK = 2;
    private static final String[] selectionLabel = {"Sounds", "Vibra", "Back"};
    private boolean sound = false;
    private boolean vibra = false;
    /**
     * Array that hold help text and info for reading UTF-8 file from jar
     */
    private String[] help;
    private static final int HELP_LINES = 30;
    private static final String HELP_FILE = "/help.txt";
    //scrolling values for help menu
    int scrollValue = 0;
    int screen_lines;
    int scrollBarHeight;
    int fillRectHeight = 5;
    //font
    Font font;
    int fontHeight;
    //credits
    private long creditTimer;
    String credits[] = {"http://www.mondonerd.com 1", "http://www.mondonerd.com 2", "http://www.mondonerd.com 3"};
    int num_credits = 0;
    //Dialog box values
    private int dialog_x;//x-position
    private int dialog_y;//y-position
    private int dialog_w;//box width
    private int dialog_h;//box height
    private String dialog_text;
    private int popup_type;
    private static final int POPUP_ONE = 1;//pop-up with one answer OK
    private static final int POPUP_TWO = 2;//pop-up with two answer YES or NO
    private String answer[] = {"OK", "YES", "NO"};
    private int ans_selected;
    /**
     * Game states constants
     */
    public static final int STATE_CREDITS = 5;
    public static final int STATE_EXIT = 2;
    public static final int STATE_GAME = 1;
    public static final int STATE_INITGAME = 4;
    public static final int STATE_LOGO = 0;
    public static final int STATE_MENU = 3;
    /**
     * Menu states constants
     */
    public static final int STATE_OPTIONS = 6;
    public static final int STATE_HELP = 7;
    private static final int ANCHOR_LEFT_TOP = Graphics.TOP | Graphics.LEFT;
    //#ifdef PRINT_FPS
    //# int fps = 1;
    //#endif
    private long logoTimer = System.currentTimeMillis();
    int menuSelection = 0;
    BasicIsoGameMidlet midlet = null; // Parent midlet
    int pushedKey = -99;
    long pushedKeyTime = System.currentTimeMillis();
    protected int screen_width, screen_height;
    int state = STATE_LOGO;
    Thread t = null; // Main thread
    Image logo = null;
//Suffix that select the tile set used to render the map and to identify map files
    private static final String TILE_SET = "base";
    private static final String BASE_MAP = "/map";
    private static final int TILE_HEIGHT = 16;
    private static final int TILE_WIDTH = 32;
    private static final int NUMBER_OF_ENEMIES = 15; //TODO this is provisional it will be variable
    private static final int NUMBER_OF_ACTIONS = 2;
    private static final String ENEMY_SUFFIX_NAME = "shock";
    private Random rand = new Random();
    private int level = 0;
    private Board board = null; //isoj2me main board object
    Element player = null; //isoj2me element player object
    int cameraX;
    int cameraY;

    public BasicIsoGameCanvas(BasicIsoGameMidlet parentMidlet) {


        //#ifdef MIDP2_CANVAS
        //# super(false);
        //# setFullScreenMode(true);
        //#endif

        this.midlet = parentMidlet;

        t = new Thread(this);
        t.start();


    }

//	public int getWidth() {
//		return 176;
//	}
//	
//	public int getHeight() {
//		return 208;
//	}
    private void fillScreen(int color, Graphics g) {
        g.setClip(0, 0, screen_width, screen_height);
        g.setColor(color);
        g.fillRect(0, 0, screen_width, screen_height);
    }

    private void inputGame(int keyCode) {
        switch (keyCode) {
            // Controls
            case Canvas.KEY_NUM1:


                break;
            case Canvas.KEY_NUM0:
                state = STATE_EXIT;

                break;

            case -1:
                pushedKey = Canvas.UP;

                break;
            case -2:
                pushedKey = Canvas.DOWN;

                break;
            case -3:
                pushedKey = Canvas.LEFT;

                break;
            case -4:
                pushedKey = Canvas.RIGHT;

                break;
            case Canvas.UP:
            case Canvas.DOWN:
            case Canvas.LEFT:
            case Canvas.RIGHT:
                pushedKey = keyCode;

                break;
            case Canvas.KEY_NUM2:
                break;
            case Canvas.KEY_NUM3:

                break;

            // Put here other actions or commands
            default:

                break;
        }
    }

    private void inputMenu(int keyCode) {

        String[] label;//label not used when in STATE_HELP

        if (state == STATE_MENU) {
            label = optionsLabel;
        } else /*
         * if(state == STATE_OPTIONS)
         */ {
            label = selectionLabel;
        }

        switch (keyCode) {
            // Controls
            case -1:
            case Canvas.KEY_NUM2:
            case Canvas.UP:
                if (state != STATE_HELP) {
                    menuSelection--;
                    if (menuSelection < 0) {
                        menuSelection = label.length - 1;
                    }
                } else {//scroll help menu UP
                    if (scrollValue > 0) {
                        scrollValue -= 1;
                    }
                }
                break;
            case Canvas.KEY_NUM8:
            case Canvas.DOWN:
            case -2:
                if (state != STATE_HELP) {
                    menuSelection++;
                    if (menuSelection >= label.length) {
                        menuSelection = 0;
                    }
                } else {//scroll help menu DOWN
                    if (scrollValue + screen_lines < HELP_LINES) {
                        scrollValue += 1;
                    }
                }
                break;
            case INPUT_SELECT:
            case Canvas.FIRE:
            case -5:
                switch (state) {
                    case STATE_MENU:
                        if (menuSelection == MENU_STARTDEMO) {
                            state = STATE_INITGAME;
                        } else if (menuSelection == MENU_OPTIONS) {
                            menuSelection = 0;
                            state = STATE_OPTIONS;
                        } else if (menuSelection == MENU_HELP) {
                            menuSelection = 0;
                            help = readUnicodeFile(HELP_FILE);
                            state = STATE_HELP;
                        } else if (menuSelection == MENU_EXIT) {
                            initDialogBox((screen_width >> 1) - ((screen_width >> 1) >> 1), (screen_height >> 1) - ((screen_height >> 1) >> 1), screen_width >> 1, screen_height >> 1, "Exit ?", POPUP_TWO);
                            state = STATE_EXIT;
                        } else if (menuSelection == MENU_CREDITS) {
                            creditTimer = System.currentTimeMillis();
                            num_credits = 0;
                            state = STATE_CREDITS;
                        }
                        break;
                    case STATE_OPTIONS:
                        if (menuSelection == OPTIONS_SOUNDS) {
                            //change sounds state
                            sound = !sound;
                        } else if (menuSelection == OPTIONS_VIBRA) {
                            //change vibra state
                            vibra = !vibra;
                        } else if (menuSelection == BACK) {
                            menuSelection = 0;
                            state = STATE_MENU;
                        }
                        break;
                    case STATE_HELP:
                        menuSelection = 0;
                        state = STATE_MENU;
                        break;
                }
                break;
            //		
            // case -2:
            //		
            // case -4:
            //		
            //		
            // case Canvas.LEFT:
            // case Canvas.RIGHT:
            // pushedKey = keyCode;
            // break;

            // Put here other actions or commands
            default:

                break;
        }
    }

    protected void keyPressed(int keyCode) {
        if (state == STATE_LOGO) {
            state = STATE_MENU;
        } else if (state == STATE_MENU) {
            inputMenu(keyCode);
        } else if (state == STATE_OPTIONS) {
            inputMenu(keyCode);
        } else if (state == STATE_HELP) {
            inputMenu(keyCode);
        } else if (state == STATE_INITGAME) {
        } else if (state == STATE_GAME) {
            inputGame(keyCode);
        } else if (state == STATE_CREDITS) {
            state = STATE_MENU;
        } else if (state == STATE_EXIT) {
            inputDialog(keyCode);
        }
    }

    protected void keyReleased(int keyCode) {
        pushedKey = -99;
    }

    private void inputDialog(int keyCode) {
        switch (popup_type) {
            case POPUP_TWO:
                switch (keyCode) {
                    case Canvas.FIRE:
                    case INPUT_SELECT:
                    case -5:
                        if (ans_selected == 1) {//if YES selected exit
                            midlet.notifyDestroyed();
                        } else if (ans_selected == 2) {//if YES selected exit
                            state = STATE_MENU;
                        }
                        break;
                    case Canvas.LEFT:
                    case -2:
                    case Canvas.KEY_NUM4:
                        if (ans_selected > 1) {
                            ans_selected--;
                        }
                        break;

                    case Canvas.RIGHT:
                    case Canvas.KEY_NUM6:
                    case -4:
                        if (ans_selected < 2) {
                            ans_selected++;
                        }
                        break;
                }
                break;
        }
    }

    private void initDialogBox(int x, int y, int w, int h, String q, int t) {
        dialog_x = x;
        dialog_y = y;
        dialog_w = w;
        dialog_h = h;
        dialog_text = q;
        popup_type = t;
        if (popup_type == POPUP_TWO) {
            ans_selected = 1;
        }
    }

    public String[] readUnicodeFile(String filename) {
        String[] tempHelp = new String[HELP_LINES];
        StringBuffer buffer = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            Class c = this.getClass();
            is = c.getResourceAsStream(filename);
            if (is == null) {
                throw new Exception("File Does Not Exist");
            }

            try {
                isr = new InputStreamReader(is, "UTF-8");
            } catch (Exception e1) {
                try {
                    isr = new InputStreamReader(is, "UTF8");
                } catch (Exception e2) {
                    try {
                        isr = new InputStreamReader(is, "utf-8");
                    } catch (Exception e3) {
                        isr = new InputStreamReader(is, "utf8");
                    }
                }
            }

            buffer = new StringBuffer();
            int ch;
            int i = 0;
            while ((ch = isr.read()) > -1) {
                if ((char) ch == '\n') {
                    tempHelp[i] = buffer.toString();
                    i++;
                    //buffer.delete(0,buffer.length());
                    buffer = new StringBuffer();
                } else {
                    buffer.append((char) ch);
                }
            }
            tempHelp[i] = buffer.toString();

            if (isr != null) {
                isr.close();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempHelp;
    }

    public void paint(Graphics g) {
        if (state == STATE_LOGO) {
            renderLogo(g);
        } else if (state == STATE_MENU || state == STATE_OPTIONS) {
            renderMenu(g);
        } else if (state == STATE_HELP) {
            renderHelp(g);
        } else if (state == STATE_INITGAME) {
            renderInitGame(g);
        } else if (state == STATE_GAME) {
            renderGame(g);
        } else if (state == STATE_EXIT) {
            renderExit(g);
        } else if (state == STATE_CREDITS) {
            renderCredits(g);
        }
        renderOverlay(g);
        renderUI(g);
        //#ifdef PRINT_FPS
        //# renderFPS(g);
        //#endif
    }
    long messageTimer = 0;

    void renderOverlay(Graphics g) {
    }

    private void paintDialogBox(Graphics g) {
        g.setColor(0x000000);
        g.fillRoundRect(dialog_x, dialog_y, dialog_w, dialog_h, 16, 16);
        g.setColor(0xffffff);
        g.drawRoundRect(dialog_x, dialog_y, dialog_w, dialog_h, 16, 16);


        g.drawString(dialog_text, (screen_width >> 1) - (g.getFont().stringWidth(dialog_text) >> 1), dialog_y + (g.getFont().getHeight() + 5),
                Graphics.TOP | Graphics.LEFT);
        if (popup_type == POPUP_TWO) {
            if (ans_selected == 1) {
                g.setColor(0xff0000);
            } else {
                g.setColor(0xffffff);
            }
            g.drawString(answer[1], dialog_x + (dialog_w >> 2), dialog_y + dialog_h - (g.getFont().getHeight() + 5),
                    Graphics.TOP | Graphics.HCENTER);
            if (ans_selected == 2) {
                g.setColor(0xff0000);
            } else {
                g.setColor(0xffffff);
            }
            g.drawString(answer[2], (dialog_x + dialog_w) - (dialog_w >> 2), dialog_y + dialog_h - (g.getFont().getHeight() + 5),
                    Graphics.TOP | Graphics.HCENTER);
        }
    }

    private void renderExit(Graphics g) {
        //g.setColor(0x000000);
        //g.fillRect(0, 0, screen_width, screen_height);
        paintDialogBox(g);
    }

    private void renderCredits(Graphics g) {
        //TODO create a sliding show or a scrolling text to show credits
        g.setColor(0x000000);
        g.fillRect(0, 0, screen_width, screen_height);
        g.setColor(0xffffff);
        g.drawString(credits[num_credits], (screen_width >> 1) - (g.getFont().stringWidth(credits[num_credits]) >> 1), screen_height >> 1, ANCHOR_LEFT_TOP);
        //g.drawString("http://www.mondonerd.com", (screen_width >> 1) - (g.getFont().stringWidth("http://www.hackmeeting.org") >> 1), screen_height >> 1, ANCHOR_LEFT_TOP);
    }

    private void renderGame(Graphics g) {
        board.draw(g);
        g.setClip(0, 0, screen_width, screen_height);
    }

    //#ifdef PRINT_FPS
    //# private void renderFPS(Graphics g) {
    //#	g.setColor(0xffffff);
    //#	g.fillRect(0, 0, g.getFont().stringWidth("FPS: " + Integer.toString(fps)), g.getFont().getHeight());
    //#	g.setColor(0x000000);
    //# g.drawString("FPS: " + fps + " TILES: " + totalTiles + "(" + (totalTiles / fps) + ")", 0, 0, Graphics.LEFT
    //#			| Graphics.TOP);
    //# }
    //#endif
    private void renderInitGame(Graphics g) {
        fillScreen(0x000000, g);
    }

    private void renderLogo(Graphics g) {
        fillScreen(0x000000, g);
        if (logo == null) {
            try {
                logo = Image.createImage("/logo.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        g.drawImage(logo, (screen_width >> 1) - (logo.getWidth() >> 1), (screen_height >> 1) - (logo.getHeight() >> 1), ANCHOR_LEFT_TOP);
    }

    private void renderMenu(Graphics g) {

        String[] tempLabel;

        if (state == STATE_MENU) {
            tempLabel = optionsLabel;
        } else /*
         * if(state == STATE_OPTIONS)
         */ {
            tempLabel = selectionLabel;
        }

        fillScreen(0x000000, g);

        int optionsYStep = screen_height / (tempLabel.length + 1);

        for (int i = 0; i < tempLabel.length; i++) {
            if (menuSelection == i) {
                g.setColor(0xff0000);
            } else {
                g.setColor(0xcccccc);
            }
            if (state != STATE_OPTIONS) {
                g.drawString(tempLabel[i], screen_width >> 1,
                        ((i + 1) * optionsYStep) + (g.getFont().getHeight() >> 1),
                        Graphics.TOP | Graphics.HCENTER);
            } else {
                String temp;
                if (i == 0) {//check the sound settings
                    if (sound) {
                        temp = tempLabel[i] + " ON";
                    } else {
                        temp = tempLabel[i] + " OFF";
                    }
                } else if (i == 1) {//check the vibration settings
                    if (vibra) {
                        temp = tempLabel[i] + " ON";
                    } else {
                        temp = tempLabel[i] + " OFF";
                    }
                } else {
                    temp = tempLabel[i];
                }
                g.drawString(temp, screen_width >> 1,
                        ((i + 1) * optionsYStep) + (g.getFont().getHeight() >> 1),
                        Graphics.TOP | Graphics.HCENTER);
            }

        }

    }

    private void renderHelp(Graphics g) {

        fillScreen(0x000000, g);

        font = g.getFont();
        fontHeight = font.getHeight();
        screen_lines = (screen_height / fontHeight) - 1;//number of text lines rendered on screen
        scrollBarHeight = fillRectHeight * (HELP_LINES - (screen_lines - 1));

        if (screen_lines < HELP_LINES) {
            g.setColor(0xcc0000);
            g.drawRect(screen_width - 5, (screen_height >> 1) - (scrollBarHeight >> 1), 3, scrollBarHeight);
            g.fillRect(screen_width - 5, (screen_height >> 1) - (scrollBarHeight >> 1) + scrollValue * fillRectHeight, 3, fillRectHeight);
        }

        g.setColor(0xcccccc);
        for (int i = scrollValue; i < scrollValue + screen_lines; i++) {

            g.drawString(help[i], screen_width >> 1, (((i + 1) - scrollValue) * fontHeight),
                    Graphics.TOP | Graphics.HCENTER);
        }
    }

    public void run() {
//#ifdef PRINT_FPS
//#		int numFrames = 0;
//#		long startTime;
//#		startTime = System.currentTimeMillis();
//#endif
        Thread current = Thread.currentThread();
        midlet.SizeChanged(this);
        while (current == t) {
            repaint();
            serviceRepaints();
            tick();

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
            }
//#ifdef PRINT_FPS
//#			numFrames++;
//#			if (System.currentTimeMillis() - startTime >= 1000) {
//#				fps = numFrames;
//#				numFrames = 0;
//#				startTime = System.currentTimeMillis();
//#				if (board != null) {
//#					totalTiles = board.totalTilesPainted;
//#					board.totalTilesPainted = 0;
//#				}
//#			}
//#endif
        }

    }
    //#ifdef PRINT_FPS
    //# int totalTiles = 0;
    //#endif

    private void tick() {
        //#ifdef DEBUGGING
        //# try {
        //#endif
        if (state == STATE_LOGO) {
            updateLogo();
        } else if (state == STATE_MENU) {
            updateMenu();
        } else if (state == STATE_INITGAME) {
            updateInitGame();
        } else if (state == STATE_GAME) {
            updateGame();
        } else if (state == STATE_CREDITS) {
            updateCredits();
        } else if (state == STATE_EXIT) {
            updateExit();
        }


        //#ifdef DEBUGGING
        //# }
        //# catch (Exception e) {
        //# 	System.out.println("[STATE = " + state + "]:" + e);
        //# }
        //#endif
    }

    /**
     *
     */
    private void updateExit() {
        //midlet.notifyDestroyed();
    }

    /**
     *
     */
    private void updateGame() {

        int xTemp, yTemp;
        int direction = DIRECTION_NONE;
        if ((System.currentTimeMillis() - pushedKeyTime > INPUT_DELAY)) {
            pushedKeyTime = System.currentTimeMillis();

            if (pushedKey == Canvas.DOWN) {
                direction = DIRECTION_SE;
            } else if (pushedKey == Canvas.UP) {
                direction = DIRECTION_NW;
            } else if (pushedKey == Canvas.LEFT) {
                direction = DIRECTION_SW;
            } else if (pushedKey == Canvas.RIGHT) {
                direction = DIRECTION_NE;
            }
            updatePlayer(direction);

        }

        updateEnemies();

        xTemp = player.posOnScreenX;
        yTemp = player.posOnScreenY;

        if (xTemp < 0 || xTemp >= screen_width || yTemp < 0 || yTemp >= screen_height) {
            setCamera(xTemp + board.cameraX1 - (screen_width >> 1),
                    yTemp + board.cameraY1 - (screen_height >> 1));

        }
        updateCamera();
    }

    private void updatePlayer(int direction) {
        if (direction == DIRECTION_NONE) { //Don't do nothing end exit
            player.frameDuration = 0;
            return;
        }

        Element elementTemp = player;

        if (!elementTemp.isMoving) { //If the player is not already moving move him/her to new position on the board
            int tempX = elementTemp.posOnCellsCurrX;
            int tempY = elementTemp.posOnCellsCurrY;
            if (direction == DIRECTION_NE) {
                tempY--;
                elementTemp.setCurrentAction("playerNE");
            } else if (direction == DIRECTION_NW) {
                tempX--;
                elementTemp.setCurrentAction("playerNW");
            } else if (direction == DIRECTION_SE) {
                tempX++;
                elementTemp.setCurrentAction("playerSE");
            } else if (direction == DIRECTION_SW) {
                tempY++;
                elementTemp.setCurrentAction("playerSW");
            }
            elementTemp.isMoving = true;
            elementTemp.posOnCellsFinalX = tempX;
            elementTemp.posOnCellsFinalY = tempY;
            elementTemp.frameCurr = 0;
            elementTemp.frameDuration = 25;
            elementTemp.modCurr = 0;

        }

    }

    public void setCamera(int x, int y) {
        cameraX = x;
        cameraY = y;
    }

    private void updateInitGame() {
        board = new Board(TILE_SET, BASE_MAP + level, screen_width, screen_height, TILE_WIDTH, TILE_HEIGHT, 50, 0x8183ae);
        initEnemies();
        initPlayer();
        state = STATE_GAME;
    }

    private void initPlayer() {
        int x, y;
        do {
            x = Math.abs(rand.nextInt() % board.map.width);
            y = Math.abs(rand.nextInt() % board.map.height);

        } while (!board.isWalkableCell(x, y) || board.isElement(x, y) != null);
        x = 0;
        y = 0;
        player = new Element("player", x, y, 0, 0);
        player.action = 0;
        player.delay = 1000;
        player.posOnMapX = board.getPosOnMapXFromCell(x, y);
        player.posOnMapY = board.getPosOnMapYFromCell(x, y) + (TILE_HEIGHT >> 1);
        // Standing sprites
        player.putAction("playerSW", 3);
        player.putAction("playerNW", 3);
        player.putAction("playerSE", 3);
        player.putAction("playerNE", 3);
        // Movement traslate factors
        player.modXSW = new int[]{
            -3, -5, -8
        };
        player.modYSW = new int[]{
            1, 2, 5
        };
        player.modXNW = new int[]{
            -3, -5, -8
        };
        player.modYNW = new int[]{
            -1, -2, -5
        };
        player.modXSE = new int[]{
            3, 5, 8
        };
        player.modYSE = new int[]{
            1, 2, 5
        };
        player.modXNE = new int[]{
            3, 5, 8
        };
        player.modYNE = new int[]{
            -1, -2, -5
        };


        player.setCurrentAction("playerSE"); //Initial position
        player.posOffsetY = 0; //If the player's sprite needs to be printed higher or below the automatic rendering
        player.frameDuration = 0;

        board.putElement(player); //Put on the board the player

    }

    private void initEnemies() {

        int i, x, y, delay;
        Element enemy;

        for (i = 0; i < NUMBER_OF_ENEMIES; i++) {
            do {
                x = Math.abs(rand.nextInt() % board.map.width);
                y = Math.abs(rand.nextInt() % board.map.height);
                delay = 4000 + Math.abs(rand.nextInt() % 3000);
            } while (!board.isWalkableCell(x, y) || board.isElement(x, y) != null);

            //Create a new enemy Element object
            enemy = new Element(ENEMY_SUFFIX_NAME + i, x, y, 0, 0);
            enemy.delay = delay;
            enemy.posOnMapX = board.getPosOnMapXFromCell(x, y);
            enemy.posOnMapY = board.getPosOnMapYFromCell(x, y) + (TILE_HEIGHT >> 1);
            // Standing sprites
            enemy.putAction(ENEMY_SUFFIX_NAME + "SW", 1);
            enemy.putAction(ENEMY_SUFFIX_NAME + "SE", 1);
            enemy.putAction(ENEMY_SUFFIX_NAME + "NW", 1);
            enemy.putAction(ENEMY_SUFFIX_NAME + "NE", 1);
            // Movement traslate factors
            enemy.modXSW = new int[]{
                -3, -5, -8
            };
            enemy.modYSW = new int[]{
                1, 2, 5
            };
            enemy.modXNW = new int[]{
                -3, -5, -8
            };
            enemy.modYNW = new int[]{
                -1, -2, -5
            };
            enemy.modXSE = new int[]{
                3, 5, 8
            };
            enemy.modYSE = new int[]{
                1, 2, 5
            };
            enemy.modXNE = new int[]{
                3, 5, 8
            };
            enemy.modYNE = new int[]{
                -1, -2, -5
            };


            enemy.setCurrentAction(ENEMY_SUFFIX_NAME + "SW");
            //enemy.posOffsetY = 8;
            enemy.posOffsetY = 0;
            enemy.frameDuration = 500;
            board.putElement(enemy);
        }

    }
    public static final int DIRECTION_NE = 0;
    public static final int DIRECTION_NW = 1;
    public static final int DIRECTION_SE = 2;
    public static final int DIRECTION_SW = 3;
    public static final int ENEMY_DELAY = 500;
    private static final int DIRECTION_NONE = 99;
    private long enemyTimer = System.currentTimeMillis();

    private void updateEnemies() {

        boolean changePos, isSelectedElement = false;
        int direction = 0, tempX, tempY;
        Enumeration charEnum, iter;
        Element elementTemp = null;

        if (System.currentTimeMillis() - enemyTimer > ENEMY_DELAY) {
            enemyTimer = System.currentTimeMillis();
            changePos = true;
        } else {
            changePos = false;
        }
        charEnum = board.elements.elements();
        for (iter = charEnum; iter.hasMoreElements();) {
            elementTemp = (Element) iter.nextElement();
            if (System.currentTimeMillis() - elementTemp.timer > elementTemp.delay) {
                elementTemp.timer = System.currentTimeMillis();
                elementTemp.action = Math.abs(rand.nextInt() % NUMBER_OF_ACTIONS);
            }
            if (elementTemp.action == Element.ACTION_CALL) {
                direction = DIRECTION_NONE;
            } else if (changePos
                    && !elementTemp.isMoving && elementTemp.name.indexOf(ENEMY_SUFFIX_NAME) >= 0) {

                direction = Math.abs(rand.nextInt() % 4);



                tempX = elementTemp.posOnCellsCurrX;
                tempY = elementTemp.posOnCellsCurrY;
                if (direction == DIRECTION_NE) {
                    if (isSelectedElement) {
                        tempY--;
                    } else {
                        tempY -= 3;
                    }

                    elementTemp.setCurrentAction(ENEMY_SUFFIX_NAME + "NE");
                } else if (direction == DIRECTION_NW) {
                    if (isSelectedElement) {
                        tempX--;
                    } else {
                        tempX -= 3;
                    }
                    elementTemp.setCurrentAction(ENEMY_SUFFIX_NAME + "NW");
                } else if (direction == DIRECTION_SE) {
                    if (isSelectedElement) {
                        tempX++;
                    } else {
                        tempX += 3;
                    }
                    elementTemp.setCurrentAction(ENEMY_SUFFIX_NAME + "SE");
                } else if (direction == DIRECTION_SW) {
                    if (isSelectedElement) {
                        tempY++;
                    } else {
                        tempY += 3;
                    }
                    elementTemp.setCurrentAction(ENEMY_SUFFIX_NAME + "SW");
                }
                elementTemp.isMoving = true;
                elementTemp.posOnCellsFinalX = tempX;
                elementTemp.posOnCellsFinalY = tempY;
                elementTemp.frameCurr = 0;
                elementTemp.frameDuration = 500;
                elementTemp.modCurr = 0;

            }
        }
    }

    public void updateCamera() {
        int diffX = board.cameraX1 - cameraX;
        int diffY = board.cameraY1 - cameraY;
        int incX = 0;
        int incY = 0;
        if (diffX == 0 && diffY == 0) {
            return;
        }

        if (Math.abs(diffX) > 64) {
            if (diffX > 0) {
                incX = -64;
            } else if (diffX < 0) {
                incX = 64;
            }
        } else if (Math.abs(diffX) > 16) {
            if (diffX > 0) {
                incX = -16;
            } else if (diffX < 0) {
                incX = 16;
            }
        } else if (Math.abs(diffX) > 8) {
            if (diffX > 0) {
                incX = -4;
            } else if (diffX < 0) {
                incX = 4;
            }
        } else if (Math.abs(diffX) >= 1) {
            if (diffX > 0) {
                incX = -1;
            } else if (diffX < 0) {
                incX = 1;
            }
        }

        if (Math.abs(diffY) > 64) {
            if (diffY > 0) {
                incY = -64;
            } else if (diffY < 0) {
                incY = 64;
            }
        } else if (Math.abs(diffY) > 16) {
            if (diffY > 0) {
                incY = -16;
            } else if (diffY < 0) {
                incY = 16;
            }
        } else if (Math.abs(diffY) > 8) {
            if (diffY > 0) {
                incY = -4;
            } else if (diffY < 0) {
                incY = 4;
            }
        } else if (Math.abs(diffY) >= 1) {
            if (diffY > 0) {
                incY = -1;
            } else if (diffY < 0) {
                incY = 1;
            }
        }

        // center the camera
        //#ifndef RAPID_CAMERA
        board.setCamera(board.cameraX1 + incX, board.cameraY1 + incY);
        //#else
//# 		board.setCamera(cameraX, cameraY);
        //#endif
        // it's a good moment to do this :)
        //System.gc();
    }

    private void updateLogo() {
        // TODO Auto-generated method stub
        if (System.currentTimeMillis() - logoTimer > DELAY_LOGO) {
            state = STATE_MENU;
        }
    }

    private void updateCredits() {
        // TODO Auto-generated method stub
        if (System.currentTimeMillis() - creditTimer > DELAY_LOGO) {
            if ((num_credits + 1) < credits.length) {
                num_credits++;
                creditTimer = System.currentTimeMillis();
            } else {
                state = STATE_MENU;
            }
        }
    }

    private void renderUI(Graphics g) {
        if (state == STATE_LOGO) {
        } else if (state == STATE_MENU) {
        } else if (state == STATE_INITGAME) {
        } else if (state == STATE_GAME) {
        } else if (state == STATE_EXIT) {
        }

    }

    /**
     *
     */
    private void updateMenu() {
        // TODO Auto-generated method stub
        // state = STATE_INITGAME;
    }
}
