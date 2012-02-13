/**
 * <p>Title: lipeng</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.1.1
 *
 * <p> Revise History
 *
 * 2004.07.22 Add a menu entry demoMode V1.1.0
 *
 * 2004.07.22 revise a logic error on init V1.1.1 </p>
 */
package lipeng;

import javax.microedition.lcdui.Canvas;

public class LPMenu {
//method

    public LPMenu(LPIGameManager gm) {
        this.gm = gm;
    }

    private void menuSelect(int direct) {
        switch (direct) {
            case DIRECTION_UP:
                switch (whichActive) {
                    case MENU_NEW_GAME:
                        whichActive = MENU_ABOUT;
                        break;
                    case MENU_LOAD_GAME:
                        whichActive = MENU_NEW_GAME;
                        break;
                    case MENU_VOL:
                        whichActive = MENU_LOAD_GAME;
                        break;
                    case MENU_EXIT:
                        whichActive = MENU_VOL;
                        break;
                    case MENU_ABOUT:
                        whichActive = MENU_EXIT;
                        break;
                }
                break;
            case DIRECTION_DOWN:
                switch (whichActive) {
                    case MENU_NEW_GAME:
                        whichActive = MENU_LOAD_GAME;
                        break;
                    case MENU_LOAD_GAME:
                        whichActive = MENU_VOL;
                        break;
                    case MENU_VOL:
                        whichActive = MENU_EXIT;
                        break;
                    case MENU_EXIT:
                        whichActive = MENU_ABOUT;
                        break;
                    case MENU_ABOUT:
                        whichActive = MENU_NEW_GAME;
                        break;
                }
                break;
        }
    }

    public void action() {
        switch (menuState) {
            case MENU_STATE_MAIN:
                if ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) != 0) {
                    menuSelect(DIRECTION_UP);
                    allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                } else if ((allAction & LPKeyMask.MASK_KEY_DOWN_FLAG) != 0) {
                    menuSelect(DIRECTION_DOWN);
                    allAction &= ~LPKeyMask.MASK_KEY_DOWN_FLAG;
                } else if ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) != 0) {
                    switch (whichActive) {
                        case MENU_NEW_GAME:
                            gm.menuBeginNewGame();
                            break;
                        case MENU_LOAD_GAME:
                            gm.menuLoadGame();
                            break;
                        case MENU_VOL:
                            isVolOn = !isVolOn;
                            gm.menuAudio();
                            break;
                        case MENU_EXIT:
                            gm.menuExitGame();
                            break;
                        case MENU_ABOUT:

                            //about
                            menuState = MENU_STATE_ABOUT;
                            gm.menuAbout();
                            break;
                    }
                    allAction &= ~LPKeyMask.MASK_KEY_OK_FLAG;
                }
                break;
        }
    }

    public void judgeKeyCode(int keyCode, int GameKeyCode) {
        switch (GameKeyCode) {
            case Canvas.FIRE:
                if (((allAction & LPKeyMask.MASK_KEY_OK) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) == 0)) {
                    allAction |= LPKeyMask.MASK_KEY_OK | LPKeyMask.MASK_KEY_OK_FLAG;
                }
                break;
            case Canvas.UP:
                if (((allAction & LPKeyMask.MASK_KEY_UP) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) == 0)) {
                    allAction |= LPKeyMask.MASK_KEY_UP | LPKeyMask.MASK_KEY_UP_FLAG;
                }
                break;
            case Canvas.DOWN:
                if (((allAction & LPKeyMask.MASK_KEY_DOWN) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_DOWN_FLAG) == 0)) {
                    allAction |= LPKeyMask.MASK_KEY_DOWN | LPKeyMask.MASK_KEY_DOWN_FLAG;
                }
                break;
            case Canvas.LEFT:
                if (((allAction & LPKeyMask.MASK_KEY_LEFT) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_LEFT_FLAG) == 0)) {
                    allAction |= LPKeyMask.MASK_KEY_LEFT | LPKeyMask.MASK_KEY_LEFT_FLAG;
                }
                break;
            case Canvas.RIGHT:
                if (((allAction & LPKeyMask.MASK_KEY_RIGHT) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_RIGHT_FLAG) == 0)) {
                    allAction |= LPKeyMask.MASK_KEY_RIGHT | LPKeyMask.MASK_KEY_RIGHT_FLAG;
                }
                break;
            default:
                switch (keyCode) {
                    case Canvas.KEY_NUM2: //up
                        if (((allAction & LPKeyMask.MASK_KEY_UP) == 0)
                                && ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) == 0)) {
                            allAction |= LPKeyMask.MASK_KEY_UP | LPKeyMask.MASK_KEY_UP_FLAG;
                        }
                        break;
                    case Canvas.KEY_NUM8: //down
                        if (((allAction & LPKeyMask.MASK_KEY_DOWN) == 0)
                                && ((allAction & LPKeyMask.MASK_KEY_DOWN_FLAG) == 0)) {
                            allAction |= LPKeyMask.MASK_KEY_DOWN | LPKeyMask.MASK_KEY_DOWN_FLAG;
                        }

                        break;
                    case Canvas.KEY_NUM4: //left
                        if (((allAction & LPKeyMask.MASK_KEY_LEFT) == 0)
                                && ((allAction & LPKeyMask.MASK_KEY_LEFT_FLAG) == 0)) {
                            allAction |= LPKeyMask.MASK_KEY_LEFT | LPKeyMask.MASK_KEY_LEFT_FLAG;
                        }

                        break;
                    case Canvas.KEY_NUM6: //right
                        if (((allAction & LPKeyMask.MASK_KEY_RIGHT) == 0)
                                && ((allAction & LPKeyMask.MASK_KEY_RIGHT_FLAG) == 0)) {
                            allAction |= LPKeyMask.MASK_KEY_RIGHT | LPKeyMask.MASK_KEY_RIGHT_FLAG;
                        }

                        break;
                    case Canvas.KEY_NUM5: //ok
                        if (((allAction & LPKeyMask.MASK_KEY_OK) == 0)
                                && ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) == 0)) {
                            allAction |= LPKeyMask.MASK_KEY_OK | LPKeyMask.MASK_KEY_OK_FLAG;
                        }
                        break;
                }
                break;
        }
    }

    public void freeKey(int keyCode, int gameKeyCode) {
        switch (gameKeyCode) {
            case Canvas.FIRE:
                allAction &= ~LPKeyMask.MASK_KEY_OK;
                break;
            case Canvas.UP:
                allAction &= ~LPKeyMask.MASK_KEY_UP;
                break;
            case Canvas.DOWN:
                allAction &= ~LPKeyMask.MASK_KEY_DOWN;
                break;
            case Canvas.LEFT:
                allAction &= ~LPKeyMask.MASK_KEY_LEFT;
                break;
            case Canvas.RIGHT:
                allAction &= ~LPKeyMask.MASK_KEY_RIGHT;
                break;
            default:
                switch (keyCode) {
                    case Canvas.KEY_NUM2: //up
                        allAction &= ~LPKeyMask.MASK_KEY_UP;
                        break;
                    case Canvas.KEY_NUM8: //down
                        allAction &= ~LPKeyMask.MASK_KEY_DOWN;
                        break;
                    case Canvas.KEY_NUM4: //left
                        allAction &= ~LPKeyMask.MASK_KEY_LEFT;
                        break;
                    case Canvas.KEY_NUM6: //right
                        allAction &= ~LPKeyMask.MASK_KEY_RIGHT;
                        break;
                    case Canvas.KEY_NUM5:
                        allAction &= ~LPKeyMask.MASK_KEY_OK;
                        break;
                }
                break;
        }
    }

    public void init() {
        menuState = MENU_STATE_MAIN;
        whichActive = MENU_NEW_GAME;
        allAction = 0;
    }
// property
//public
    public int whichActive;
    public int menuState;
    public int allAction;
    public boolean isVolOn = true;
    private LPIGameManager gm;
//final
    public static final int MENU_NEW_GAME = 0;
    public static final int MENU_LOAD_GAME = 1;
    public static final int MENU_VOL = 3;
    public static final int MENU_EXIT = 4;
    public static final int MENU_ABOUT = 5;
    public static final int MENU_STATE_MAIN = 0;
    public static final int MENU_STATE_ABOUT = 2;
    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_DOWN = 1;
}
