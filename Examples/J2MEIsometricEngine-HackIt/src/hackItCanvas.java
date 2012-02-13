
import isoj2me.Board;
import isoj2me.Element;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
//#ifdef NOKIA_FULLCANVAS
//# import com.nokia.mid.ui.FullCanvas;
//# 
//# public class hackItCanvas extends FullCanvas implements Runnable {
//#else
public class hackItCanvas extends Canvas implements Runnable {
//#endif

    //#ifndef RAPID_CAMERA
    private static final long DELAY = 10;
    //#else
    //# private static final long DELAY = 1;
    //#endif
    private static final int DELAY_LOGO = 2000;
    private static final int INPUT_DELAY = 50;
    private static final int INPUT_SELECT = Canvas.KEY_NUM5;
    private static final int MENU_EXIT = 2;
    private static final int MENU_CREDITS = 1;
    private static final int MENU_STARTDEMO = 0;
    private static final String[] optionsLabel = {"Start Demo", "Credits",
        "Exit"};
    public static final int STATE_CREDITS = 5;
    public static final int STATE_EXIT = 2;
    public static final int STATE_GAME = 1;
    public static final int STATE_INITGAME = 4;
    public static final int STATE_LOGO = 0;
    public static final int STATE_MENU = 3;
    private static final int ANCHOR_LEFT_TOP = Graphics.TOP | Graphics.LEFT;
    int fps = 0;
    private long logoTimer = System.currentTimeMillis();
    int menuSelection = 0;
    MIDlet midlet = null; // Parent midlet
    int pushedKey = -99;
    long pushedKeyTime = System.currentTimeMillis();
    protected int screen_width, screen_height;
    int state = STATE_LOGO;
    Thread t = null; // Main thread
    Image logo = null;

    public hackItCanvas(MIDlet parentMidlet) {
        super();
        this.midlet = parentMidlet;
        //#ifdef MIDP2_CANVAS
//# 		setFullScreenMode(true);
        //#endif
        t = new Thread(this);
        t.start();
        try {
            logo = Image.createImage("/logo.png");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void fillScreen(int color, Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, screen_width, screen_height);
    }
    int ACTIVITY_NONE = 0;
    int ACTIVITY_LISTENING = 1;
    int currentPlayerActivity = ACTIVITY_NONE;
    private String messageString = "Arrestato!";

    private void inputGame(int keyCode) {
        switch (keyCode) {
            // Controls
            case Canvas.KEY_NUM1:
                if (currentPlayerActivity != ACTIVITY_LISTENING) {
                    currentPlayerActivity = ACTIVITY_LISTENING;
                } else {
                    currentPlayerActivity = ACTIVITY_NONE;
                }

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
                selectNextElement();
                callStringPosition = 0;
                break;
            case Canvas.KEY_NUM3:
                arrestElement();
                break;

            // Put here other actions or commands
            default:

                break;
        }
    }

    private void inputMenu(int keyCode) {
        switch (keyCode) {
            // Controls
            case -1:
            case Canvas.KEY_NUM2:
            case Canvas.UP:
                menuSelection--;
                if (menuSelection < 0) {
                    menuSelection = optionsLabel.length - 1;
                }
                break;
            case Canvas.KEY_NUM8:
            case Canvas.DOWN:
            case -2:
                menuSelection++;
                if (menuSelection >= optionsLabel.length) {
                    menuSelection = 0;
                }
                break;
            case INPUT_SELECT:
            case Canvas.FIRE:
            case -5:
                if (menuSelection == MENU_STARTDEMO) {
                    state = STATE_INITGAME;
                } else if (menuSelection == MENU_EXIT) {
                    state = STATE_EXIT;
                } else if (menuSelection == MENU_CREDITS) {
                    state = STATE_CREDITS;
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
        } else if (state == STATE_INITGAME) {
        } else if (state == STATE_GAME) {
            inputGame(keyCode);
        } else if (state == STATE_CREDITS) {
            state = STATE_MENU;
        }
    }

    protected void keyReleased(int keyCode) {
        pushedKey = -99;
    }

    protected void paint(Graphics g) {
        if (state == STATE_LOGO) {
            renderLogo(g);
        } else if (state == STATE_MENU) {
            renderMenu(g);
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
//# 		renderFPS(g);
        //#endif
    }
    long messageTimer = 0;

    void renderMessage(Graphics g) {
//		if (System.currentTimeMillis() - messageTimer < 1500) {
//			g.setColor(0xffcc00);
//			g.drawString(messageString , (screen_width >> 1) - (g.getFont().stringWidth(messageString) >> 1), screen_height >> 1, Graphics.TOP | Graphics.LEFT);
//		}
    }
    private Image icon = null;

    void renderOverlay(Graphics g) {
        if (state == STATE_GAME) {
            Element elem = ((Element) board.elements.get("citizen" + selectedElement));
            if (elem.type == 0) {
                g.setColor(0xff0000);
            } else if (elem.type == 1) {
                g.setColor(0xff00ff);
            } else if (elem.type == 2) {
                g.setColor(0x00ff00);
            } else {
                g.setColor(0x000000);
            }

            if (elem.action == Element.ACTION_CALL) {
                if (icon == null) {
                    try {
                        icon = Image.createImage("/call.png");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                g.drawImage(icon, elem.posOnScreenX - (icon.getWidth() >> 1), elem.posOnScreenY - icon.getHeight(), Graphics.TOP | Graphics.LEFT);

                if (currentPlayerActivity == ACTIVITY_LISTENING) {
                    g.setColor(0xffffff);
                    g.fillRect(0, screen_height - 50, screen_width, Font.getDefaultFont().getHeight() + 6);
                    g.setColor(0x000000);
                    g.drawChars(("Chiamata:" + getCurrentCall(elem.type)).toCharArray(), callStringPosition, 40, 10, screen_height - 50 + 3, Graphics.TOP | Graphics.LEFT);
                    callStringPosition++;
                    if (callStringPosition >= callString.length() - 40) {
                        callStringPosition = 0;
                    }
                }

            }

            g.drawRect(elem.posOnScreenX, elem.posOnScreenY, 6, 13);
        }
    }
    int callStringPosition = 0;
    boolean resetString = true;
    String callString = "...bla...";

    private String getCurrentCall(int type) {
        if (resetString) {
            if (type == 0) {
                callString = "...bla...bomba...bla...fumo...bla...manifastazione...bla...pulotti...bla...linux...bla...hackit..........";
            } else if (type == 2) {
                callString = "...bla...tangente...bla...cupola...bla...corrompere...bla...soldi...bla...legge ad personam...bla........";
            } else {
                callString = "...bla...cd pirata...bla...bla...bla...me lo scarico...bla...maria de filippi...bla...grande fratello....";
            }
            resetString = false;
            callStringPosition = 0;
        }
        return callString;
    }

    private void renderExit(Graphics g) {
    }

    private void renderCredits(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(0, 0, screen_width, screen_height);
        g.setColor(0xffffff);
        g.drawString("http://www.hackmeeting.org", (screen_width >> 1) - (g.getFont().stringWidth("http://www.hackmeeting.org") >> 1), screen_height >> 1, ANCHOR_LEFT_TOP);
    }

    private void renderGame(Graphics g) {
        //TODO implements "shadow mode" for sovversivi
        board.draw(g);

        // util.initCamera();
//		g.setColor(0xff0000);
        g.setClip(0, 0, getWidth(), getHeight());
        //g.setColor(0xff0000);

        //g.drawRect(xTemp + board.cameraX1, yTemp + board.cameraY1, 5, 5);
    }

    //#ifdef PRINT_FPS
//# 	private void renderFPS(Graphics g) {
//# 		g.setColor(0xffffff);
//# 		g.fillRect(0, 0, g.getFont().stringWidth("FPS: " + Integer.toString(fps)), g.getFont().getHeight());
//# 		g.setColor(0x000000);
//# 		g.drawString("FPS: " + Integer.toString(fps), 0, 0, Graphics.LEFT
//# 				| Graphics.TOP);
//# 	}
    //#endif
    private void renderInitGame(Graphics g) {
        fillScreen(0x000000, g);
    }

    private void renderLogo(Graphics g) {
        fillScreen(0x000000, g);
        g.drawImage(logo, (screen_width >> 1) - (logo.getWidth() >> 1), (screen_height >> 1) - (logo.getHeight() >> 1), ANCHOR_LEFT_TOP);
    }

    private void renderMenu(Graphics g) {
        fillScreen(0x000000, g);

        int optionsYStep = screen_height / (optionsLabel.length + 1);

        for (int i = 0; i < optionsLabel.length; i++) {
            if (menuSelection == i) {
                g.setColor(0xff0000);
            } else {
                g.setColor(0xcccccc);
            }
            g.drawString(optionsLabel[i], screen_width >> 1,
                    ((i + 1) * optionsYStep) + (g.getFont().getHeight() >> 1),
                    Graphics.TOP | Graphics.HCENTER);
        }

    }

    public void run() {
        int numFrames = 0;
        long startTime;
        Thread current = Thread.currentThread();

        startTime = System.currentTimeMillis();

        screen_width = getWidth();
        screen_height = getHeight();

        while (current == t) {
            repaint();
            serviceRepaints();
            tick();

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
            }
            numFrames++;
            if (System.currentTimeMillis() - startTime >= 1000) {
                fps = numFrames;
                numFrames = 0;
                startTime = System.currentTimeMillis();
            }
        }

    }

    private void tick() {
        if (state == STATE_LOGO) {
            updateLogo();
        } else if (state == STATE_MENU) {
            updateMenu();
        } else if (state == STATE_INITGAME) {
            updateInitGame();
        } else if (state == STATE_GAME) {
            updateGame();
        } else if (state == STATE_EXIT) {
            updateExit();
        }
    }

    /**
     *
     */
    private void updateExit() {
        midlet.notifyDestroyed();

    }
    private int selectedElement = 0;

    /**
     *
     */
    private void updateGame() {

        int xTemp, yTemp;

        if (pushedKey != -99
                && (System.currentTimeMillis() - pushedKeyTime > INPUT_DELAY)) {
            pushedKeyTime = System.currentTimeMillis();

        }
        updateEnemies();
        Element elem = ((Element) board.elements.get("citizen" + selectedElement));
        xTemp = elem.posOnScreenX;
        yTemp = elem.posOnScreenY;

        if (xTemp < 0 || xTemp >= getWidth() || yTemp < 0 || yTemp >= getHeight()) {
            setCamera(xTemp + board.cameraX1 - (screen_width >> 1),
                    yTemp + board.cameraY1 - (screen_height >> 1));
        }
        updateCamera();

        // activity updates here
        if (currentPlayerActivity != ACTIVITY_LISTENING) {
            resetString = true;
        }

    }

    public void setCamera(int x, int y) {
        cameraX = x;
        cameraY = y;
    }
//	 Suffix that select the tile set used to render the map
    private static final String TILE_SET = "base";
    private static final String BASE_MAP = "/map";
    private static final int TILE_HEIGHT = 16;
    private static final int TILE_WIDTH = 32;
    private static final int NUMBER_OF_ENEMIES = 20;
    private static final int NUMBER_OF_VARIANTS = 2;
    private static final int NUMBER_OF_SOCIAL_TYPE = 3;
    private static final int CRIMINAL = 0;
    private static final int NORMAL = 1;
    private static final int UNTOUCHABLE = 2;
    private static final int UITOPHEIGHT = 10;
    private static final int BARWIDTH = 70;
    private static final int BARHEIGHT = 6;
    private static final int BARX = -59;
    private static final int LIVESX = 19;
    private static final int LIVERADIUS = 8;
    private int livesNumber = 5;
    private int dissidentNumber = 0;
    private static final int NUMBER_OF_ACTIONS = 2;
    private Random rand = new Random();
    private int level = 0;
    private Board board = null;
    int cameraX;
    int cameraY;
    //Image arrow = null;

    private void updateInitGame() {
//		try {
//			arrow = Image.createImage("/arrow.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        board = new Board(TILE_SET, BASE_MAP + level, this, TILE_WIDTH, TILE_HEIGHT, 40, 0x8183ae);

        initEnemies();
        initUI();
        state = STATE_GAME;
    }

    public void initEnemies() {

        int i, x, y, variant, socialType, action, delay;
        Element enemy;

        for (i = 0; i < NUMBER_OF_ENEMIES; i++) {
            do {
                x = Math.abs(rand.nextInt() % board.map.width);
                y = Math.abs(rand.nextInt() % board.map.height);
                delay = 4000 + Math.abs(rand.nextInt() % 3000);
                variant = Math.abs(rand.nextInt() % NUMBER_OF_VARIANTS);
                socialType = Math.abs(rand.nextInt() % NUMBER_OF_SOCIAL_TYPE);
                action = Math.abs(rand.nextInt() % NUMBER_OF_ACTIONS);
            } while (!board.isWalkableCell(x, y) || board.isElement(x, y) != null);

            if (socialType == CRIMINAL) {
                dissidentNumber++;
            }
            enemy = new Element("citizen" + i, x, y, variant, socialType);
            enemy.action = action;
            enemy.delay = delay;
            enemy.posOnMapX = board.getPosOnMapXFromCell(x, y);
            enemy.posOnMapY = board.getPosOnMapYFromCell(x, y) + (TILE_HEIGHT >> 1);
            // Standing sprites
            enemy.putAction("citizen" + variant + "SW", 1);
            enemy.putAction("citizen" + variant + "NW", 1);
            enemy.putAction("citizen" + variant + "SE", 1);
            enemy.putAction("citizen" + variant + "NE", 1);
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


            enemy.setCurrentAction("citizen" + variant + "NE");
            //enemy.posOffsetY = 8;
            enemy.posOffsetY = 0;
            enemy.frameDuration = 0;
            board.putElement(enemy);
        }

    }
    public static final int ENEMY_DIRECTION_NE = 0;
    public static final int ENEMY_DIRECTION_NW = 1;
    public static final int ENEMY_DIRECTION_SE = 2;
    public static final int ENEMY_DIRECTION_SW = 3;
    public static final int ENEMY_DELAY = 500;
    private static final int ENEMY_DIRECTION_NONE = 99;
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
                direction = ENEMY_DIRECTION_NONE;
            } else if (changePos
                    && !elementTemp.isMoving && elementTemp.name.indexOf("citizen") >= 0) {

                direction = Math.abs(rand.nextInt() % 4);



                tempX = elementTemp.posOnCellsCurrX;
                tempY = elementTemp.posOnCellsCurrY;
                if (direction == ENEMY_DIRECTION_NE) {
                    if (isSelectedElement) {
                        tempY--;
                    } else {
                        tempY -= 3;
                    }

                    elementTemp.setCurrentAction("citizen" + elementTemp.variant + "NE");
                } else if (direction == ENEMY_DIRECTION_NW) {
                    if (isSelectedElement) {
                        tempX--;
                    } else {
                        tempX -= 3;
                    }
                    elementTemp.setCurrentAction("citizen" + elementTemp.variant + "NW");
                } else if (direction == ENEMY_DIRECTION_SE) {
                    if (isSelectedElement) {
                        tempX++;
                    } else {
                        tempX += 3;
                    }
                    elementTemp.setCurrentAction("citizen" + elementTemp.variant + "SE");
                } else if (direction == ENEMY_DIRECTION_SW) {
                    if (isSelectedElement) {
                        tempY++;
                    } else {
                        tempY += 3;
                    }
                    elementTemp.setCurrentAction("citizen" + elementTemp.variant + "SW");
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
    Image uiBottom = null;
    //Image uiTop = null;
    private int buttonCounter = 0;

    private void initUI() {
        if (uiBottom == null) {
            try {
                uiBottom = Image.createImage("/ui_down.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//		if (uiTop == null) {
//			try {
//				uiTop = Image.createImage("/ui_up.png");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (arrow == null) {
//			try {
//				arrow = Image.createImage("/arrow.png");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
    }

    private void renderUI(Graphics g) {
        if (state == STATE_LOGO) {
        } else if (state == STATE_MENU) {
        } else if (state == STATE_INITGAME) {
        } else if (state == STATE_GAME) {
            int i;
            g.setColor(0x000000);
            g.fillRect(0, 0, screen_width, UITOPHEIGHT);
            g.fillRect(0, screen_height - uiBottom.getHeight(), screen_width, uiBottom.getHeight());
            g.setColor(0xff0000);
            g.fillRect((screen_width >> 1) + BARX, (UITOPHEIGHT >> 1) - (BARHEIGHT >> 1), BARWIDTH * dissidentNumber * 2 / NUMBER_OF_ENEMIES, BARHEIGHT);
            g.setColor(0x00ff00);
            for (i = 0; i < livesNumber; i++) {
                g.fillArc((screen_width >> 1) + LIVESX + i * (LIVERADIUS + 2), (UITOPHEIGHT >> 1) - (LIVERADIUS >> 1), LIVERADIUS, LIVERADIUS, 0, 360);
            }
            g.fillRect((screen_width >> 1) - (uiBottom.getWidth() >> 1), screen_height - uiBottom.getHeight(), uiBottom.getWidth(), uiBottom.getHeight());
            if (currentPlayerActivity == ACTIVITY_LISTENING) {
                g.setColor(0xff0000);
                g.fillRect((screen_width >> 1) - (uiBottom.getWidth() >> 1), screen_height - uiBottom.getHeight(), 42, uiBottom.getHeight());
            }
            if (buttonCounter > 0) {
                buttonCounter--;
                g.setColor(0xff0000);
                g.fillRect((screen_width >> 1) - (uiBottom.getWidth() >> 1) + 84, screen_height - uiBottom.getHeight(), 42, uiBottom.getHeight());
            }
            //g.drawImage(uiTop, (screen_width >> 1) - (uiTop.getWidth() >> 1), 0, ANCHOR_LEFT_TOP);
            g.drawImage(uiBottom, (screen_width >> 1) - (uiBottom.getWidth() >> 1), screen_height - uiBottom.getHeight(), ANCHOR_LEFT_TOP);


        } else if (state == STATE_EXIT) {
        }

    }

    private void arrestElement() {
        Element elem = ((Element) board.elements.get("citizen" + selectedElement));
        if (elem.type == CRIMINAL) {
            dissidentNumber--;
        } else if (elem.type == UNTOUCHABLE) {
            livesNumber--;
        }
        board.removeElement(elem);
        selectNextElement();
        //messageTimer = System.currentTimeMillis();
        buttonCounter = 10;
    }

    private void selectNextElement() {
        Element tempElem = null;
        while (tempElem == null) {
            selectedElement++;
            selectedElement = selectedElement % NUMBER_OF_ENEMIES;
            resetString = true;
            callStringPosition = 0;
            try {
                tempElem = ((Element) board.elements.get("citizen" + selectedElement));
            } catch (NullPointerException ex) {
            }
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
