
/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * <p> Copyright: Copyright (c) 2005 </p>
 *
 * <p> Company: </p> 非作者授权，请勿用于商业用途。
 *
 * @author bruce.fine@gmail.com
 * @version 1.0
 */
import javax.microedition.lcdui.*;

// import com.nokia.mid.ui.DirectUtils;

import java.io.*;
import java.util.*;

public final class MyGameCanvas extends MyCanvas {
    //
    //

    static Image imageFire = null;
    static Anim animFire = null;
    //
    static Image imageSuc = null;
    static Image imageFailuer = null;
    static Image imageX = null;
    //
    int nPowerCMax = 1;
    int nBombNumCMax = 1;
    // Anim animBullet = null;
    Anim animLisa = null;
    Anim animSaving = null;
    Anim animZZ = null;
    Anim animMouse = null;
    Anim animBf = null;
    //
    Anim animHead = null;
    Anim animBody = null;
    Anim animEnd = null;
    //
    Image imageWugong = null;
    Image imageBf = null;
    static Image imageCi = null;
    //
    static MyGameCanvas instance;
    //
    static final short SN_TEXT_NUM_PER_ROW = 7;
    static short SN_TEXT_ROWS_NUM_MAX = 0;
    static short SN_TEXT_TOP_Y = 0;
    static short SN_CHAR_WIDTH = 0;
    static short SN_CHAR_HEIGHT = 0;
    static short SN_TEXT_TOP_X = 0;
    static short SN_TEXT_DIS = 0;
    // GLOBAL
	/*
     * 状态管理
     */
    static final byte SB_STATE_LOADING = 0;
    static final byte SB_STATE_SPLASH = 1;
    static final byte SB_STATE_MENU = 2;
    static final byte SB_STATE_GAMEING = 3;
    static final byte SB_STATE_STORY_TELLING = 4;
    /**
     * bState状态管理器
     */
    static byte sbState = SB_STATE_LOADING;
    static byte sbStateToLoad = SB_STATE_LOADING;
    // 键盘
    static int snKeyCodePressed = 0;
    static int snKeyCodeReleased = 0;
    // Font
    static Font font = null;
    static int snLevelOpened = 0;
    // ------------------------------------------------------------------------------------------
    // SPLASH 控制管理
    // ------------------------------------------------------------------------------------------
    static int nSlpashTimerC = 0;
    // ------------------------------------------------------------------------------------------
    // LOADING 控制管理
    // ------------------------------------------------------------------------------------------
    // static int nLoadingTimerC = 0;
    // ------------------------------------------------------------------------------------------
    // MENU 控制管理
    // ------------------------------------------------------------------------------------------
    String[] strrTextShows = null;
    String[] strrTextForAbout = null;
    String[] strrTextForHelp = null;
    int nPointerStrrTextShow = 0;
    String[] strrMenu = {"开始游戏", "帮助", "关于", "退出"};
    int nPointerStrrMenu = 0;
    boolean isStrrMenuButtonClicked = false;
    //
    static Image imageUp = null;
    static Image imageDown = null;
    static Image imageLeft = null;
    static Image imageRight = null;
    //
    // ------------------------------------------------------------------------------------------
    // GAME 控制管理
    boolean isGameWin = false;
    boolean isMenuInGameWorked = false;
    boolean isFoorsMenuWorked = false;
    boolean isScriptWorking = false;
    boolean isKeyReponseMenu = false;
    //
    String[] strMenuFoors = {"状态", "道具", "帮助"};
    int nPStrMenuFoors = 0;
    String[] strrFoos = {"蜂蜜", "水果", "蜂王浆", "甘露", "灵芝草", "人参"};
    /**
     *
     * num_foos 分别有： 补血：蜂蜜，水果，蜂王浆 补气：灵芝草，人参，甘露
     *
     */
    int[] num_foos = new int[]{10, 10, 10, 10, 10, 10};
    int nPStrrFoos = 0;
    int nStarPointer = 0;
    int nPoundPointer = 3;
    // 这个也需要记录
    // 非BOSS场景
    String[] strrMenuInGame = {"继续游戏", "回主菜单", "退出游戏"};
    String[] strrMenuInGameResultFailur = {"重新游戏", "回主菜单", "退出游戏"};
    String[] strrMenuInGameResultWin = {"继续挑战", "回主菜单", "退出游戏"};
    // BOSS场景
    String[] strrMenuInGameBOSS = {"继续游戏", "回主菜单", "退出游戏"};
    int nPorintStrrMenuInGame = 0;
    //
    short nMapId = 0;
    //
    boolean isMenuInGameForState = false;
    // 地图控制区域
    // static Image[] imageTiles = null;
    // 地图绝对坐标
    static int snWindowX = 0;
    static int snWindowY = 0;
    //
    static int snMapWidth = 0;
    static int snMapHeight = 0;
    //
    static byte[][] snsTiles = null;
    // static boolean[][] sisTilesNull = null;
    static int snTilesWidthNum = 0;
    static int snTilesHeightNum = 0;
    static int snMapX = 0;
    static int snMapY = 0;
    static int snTilesWidthNumInScreen = 0;
    static int snTilesHeightNumInScreeen = 0;
    static int snMapAreaWidthInScreen = 0;
    static int snMapAreaHeightInScreen = 0;
    // 滚动屏幕
    static boolean isScrollMapLeft = false;
    static boolean isScrollMapRight = false;
    static boolean isScrollMapUp = false;
    static boolean isScrollMapDown = false;
    //
    static Image imageSps = null;
    static Image imageBox = null;
    static Vector vecticUnits = null;
    // static Image imageRabit = null;
    static Image imageSaving = null;
    static Image imageZZ = null;
    static Image imageMouse = null;
    static Image imageBall = null;
    static Image imageDoor = null;
    static Image imageMap = null;
    // static MyImage[] myImage1d = null;
    static Image imageBomb = null;
    Anim animBomb = null;
    //
    int nTimerForDynTiles = 0;
    static final int SN_TIMER_FOR_DYN_TILES_MAX = 2;
    int nPointerDynTiles = 0;
    static final int SN_DYN_TILES_MAX = 3;
    //
    NPC npcLisa = null;
    CPC cpc = null;
    CPC cpc2 = null;
    // 数据控制，各种任务，对话等的标志位
    /**
     * *************************************************************************
     * 门是否被开启 是否找到钥匙
     *
     */
    byte[] data1D = null;
    short[][] dataxy2d = null;
    // 道具
    // 任务用的道具
    int[] num_foos_task = null;
    int n_money = 0;
    // 对话控制
    //
    short nDialogWindowX = 0;
    short nDialogWindowY = 0;
    short nDialogWindowWidth = 0;
    short nDialogWindowHeight = 0;
    short nDialogWindowTextX = 0;
    short nDialogWindowTextY = 0;
    short nFontHeight = 0;
    short nFontDisY = 2;
    //
    boolean isDialogWorking = false;
    int page = 0;
    static final int ROW = 2;
    int lastpage = 0;
    String[] strDialog1D = null;
    //
    // 天气系统
    // 天气状况是否打开
    int nRainOrSnow = 0; // 0 -> null, 1 -> rain, 2 -> snow
    int[][] nRainOrSnowXY = null;
    //
    static boolean isStoryModeWorking = false;
    String[] strStory = null;
    short nLine = 0;
    short nLineMax = 0;
    short nLengthMax = 0;
    short nTimerStory = 0;
    short nTimerStoryMax = 0;
    Random random = null;
    //
    int nTileWidth = Consts.SN_TILE_WIDTH;
    int nTileHeight = Consts.SN_TILE_HEIGHT;
    int nTileWidthForCalc = nTileWidth * 10;
    int nTileHeightForCalc = nTileHeight * 10;

    //
    //
    public void initStory() {

        strStory = new String[]{"Lisa是太阳神", "的第7个女儿，", "她聪明伶俐，生", "活在梦幻之地的",
            "幽幽山谷中。她", "拥有魔法，却爱", "心十足，她心灵", "美好，却不屈服", "于黑暗。一天怪",
            "怪之森的恶魔", "Bruce偷走了月", "亮和星星，太阳", "不得不加班工作", "，天使传信给",
            "Lisa，让她带着", "父亲给她的泡泡", "魔法前去与怪怪", "之森的恶魔", "Bruce战斗，夺",
            "回月亮和星星。"};
        nLine = (short) -SN_TEXT_ROWS_NUM_MAX;
        nLineMax = (short) (strStory.length);
        nTimerStoryMax = 10;
        nTimerStory = 0;
    }

    public void initDialog() {
        page = 0;
        isDialogWorking = true;
        strDialog1D = new String[]{"我就是传说中的上帝", "，现在赐予你力量，", "杀死 Bruce,人们",
            "为你祈福."};
        if (strDialog1D.length % ROW == 0) {
            lastpage = (strDialog1D.length / ROW) - 1;

        } else {
            lastpage = (strDialog1D.length / ROW);
        }
    }

    public void freeStrDialog() {
        if (isScriptWorking) {
            isScriptDialogWorked = true;
        }

        isDialogWorking = false;
        strDialog1D = null;
        lastpage = 0;
        page = 0;
    }
    // 对话控制结束
    /**
     * 内部资源管理类实例
     */
    LoadingGameRes loadingGameRes = null;
    static int nGameScore = 0;

    /**
     * 构造
     */
    public MyGameCanvas() {

        instance = this;
        isFivePressed = false;
        font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM);
        random = new Random();
        //

        SN_CHAR_WIDTH = (short) font.getHeight();
        SN_CHAR_HEIGHT = (short) font.getHeight();
        SN_TEXT_DIS = (short) (font.getHeight() / 6); // 单距
        SN_TEXT_ROWS_NUM_MAX = (short) (Consts.SN_SCREEN_HEIGHT
                / (SN_CHAR_HEIGHT + SN_TEXT_DIS) - 3);
        SN_TEXT_TOP_X = (short) ((Consts.SN_SCREEN_WIDTH - SN_CHAR_WIDTH
                * SN_TEXT_NUM_PER_ROW) >> 1);
        SN_TEXT_TOP_Y = (short) (SN_CHAR_HEIGHT + SN_TEXT_DIS + SN_CHAR_HEIGHT / 2);
        // INITGLOBAL
        sbState = SB_STATE_SPLASH;

        try {
            int tempWidth = (short) (font.stringWidth("高") * SN_TEXT_NUM_PER_ROW);
            nFontHeight = (short) font.getHeight();
            nFontDisY = 4;
            int nFontDisX = 8;
            nDialogWindowWidth = (short) (tempWidth + nFontDisX * 2);
            nDialogWindowHeight = (short) ((nFontHeight + nFontDisY) * ROW + nFontDisY);

            nDialogWindowX = (short) ((Consts.SN_SCREEN_WIDTH - nDialogWindowWidth) / 2);
            nDialogWindowY = (short) ((Consts.SN_SCREEN_HEIGHT - nDialogWindowHeight) - 1);
            nDialogWindowTextX = (short) (nDialogWindowX + nFontDisX);
            nDialogWindowTextY = (short) (nDialogWindowY + nFontDisY);

        } catch (Exception ex) {
            int tempWidth = (short) (16 * SN_TEXT_NUM_PER_ROW);
            nFontHeight = (short) 16;
            nFontDisY = 4;
            int nFontDisX = 8;
            nDialogWindowWidth = (short) (tempWidth + nFontDisX * 2);
            nDialogWindowHeight = (short) ((nFontHeight + nFontDisY) * ROW + nFontDisY);

            nDialogWindowX = (short) ((Consts.SN_SCREEN_WIDTH - nDialogWindowWidth) / 2);
            nDialogWindowY = (short) ((Consts.SN_SCREEN_HEIGHT - nDialogWindowHeight) - 1);
            nDialogWindowTextX = (short) (nDialogWindowX + nFontDisX);
            nDialogWindowTextY = (short) (nDialogWindowY + nFontDisY);
        }
    }

    /**
     * 清理资源
     */
    final void freeAllResource() {
        // All of Resources will be free here
        strrTextShows = null;
        strrTextForHelp = null;
        strrTextForAbout = null;
        //
        imageUp = null;
        imageDown = null;
        imageLeft = null;
        imageRight = null;
        //
        // imageTiles = null;
        //
        imageSps = null;

        vecticUnits = null;
        //
        //
        npcLisa = null;
        cpc = null;
        cpc2 = null;
        imageBox = null;
        // imageRabit = null;
        imageSaving = null;
        imageZZ = null;
        imageMouse = null;
        imageDoor = null;
        imageBomb = null;
        // animBullet = null;
        animBomb = null;
        animLisa = null;
        animSaving = null;
        animZZ = null;
        animBf = null;
        animMouse = null;
        //
        animHead = null;
        animBody = null;
        animEnd = null;
        imageWugong = null;
        imageBf = null;
        vecticUnits = null;
        imageFire = null;
        animFire = null;
        imageSuc = null;
        imageFailuer = null;
        imageX = null;
        //	
    }
    boolean isFivePressed = false;

    /**
     * 载入
     */
    final void initResource() {
        switch (sbStateToLoad) {
            case SB_STATE_SPLASH: {
            }
            break;
            case SB_STATE_MENU: {
                try {
                    //
                    imageUp = Image.createImage("/res/up.png");
                    imageDown = Image.createImage("/res/down.png");
                    imageLeft = Image.createImage("/res/left.png");
                    imageRight = Image.createImage("/res/right.png");
                    //
                    strrTextForAbout = new String[]{"bruce.fine", "@gmail.com:)"};

                    strrTextForHelp = new String[]{"我的梦幻", "炸弹人设计:)"};
                    //
                    nPointerStrrTextShow = 0;
                    nPointerStrrMenu = 0;
                    isStrrMenuButtonClicked = false;
                    //
                    snLevelOpened = RMSSystem.loadLevelOpened();
                    // System.out.println("snLevelOpened:" + snLevelOpened);
                    if (snLevelOpened > Consts.SN_ABS_MAX_LEVEL) {
                        //
                        snLevelOpened = Consts.SN_ABS_MAX_LEVEL;
                        nMapId = 0;
                    } else {
                        //
                        nMapId = (short) (snLevelOpened + 1);
                    }

                    //

                    initWindowShow(10, 5);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            break;
            case SB_STATE_GAMEING: {

                try {
                    // logic
                    // res
                    System.gc();

                    //
                    nGameScore = 0;
                    imageUp = Image.createImage("/res/up.png");
                    imageDown = Image.createImage("/res/down.png");
                    imageLeft = Image.createImage("/res/left.png");
                    imageRight = Image.createImage("/res/right.png");

                    isMenuInGameWorked = false;
                    isGameWin = false;
                    nPorintStrrMenuInGame = 0;
                    //

                    // DataInputStream dis = new DataInputStream(getClass()
                    // .getResourceAsStream("/res/tiles.png"));
                    // int len = dis.available();
                    // byte[] bytes = new byte[len];
                    // for (int i = 0; i < len; i++) {
                    // bytes[i] = dis.readByte();
                    // }
                    // dis.close();
                    // imageMap = Image.createImage("/res/tiles.png");
                    // imageMap = DirectUtils.createImage(bytes, 0, bytes.length);
                    // myImage1d = new MyImage[imageMap.getWidth()
                    // / Consts.SN_TILE_WIDTH * imageMap.getHeight()
                    // / Consts.SN_TILE_HEIGHT];
                    //
                    // for (int i = 0; i < myImage1d.length; i++) {
                    // myImage1d[i] = new MyImage(imageMap, i % 5, i / 5);
                    // }

                    imageMap = Image.createImage("/res/tiles.png");
                    // imageTiles = new Image[128];
                    // for (int i = 0; i < imageTiles.length; i++) {
                    // imageTiles[i] = Image.createImage(16, 16);
                    // Graphics g = imageTiles[i].getGraphics();
                    // blt(g, imageTemp, ( ( (i)) % 15) * Consts.SN_TILE_WIDTH,
                    // ( (i) / 8) * Consts.SN_TILE_HEIGHT,
                    // Consts.SN_TILE_WIDTH, Consts.SN_TILE_HEIGHT, 0, 0);
                    // }
                    // imageTemp = null;
                    // 地图
                    if (nMapId == 3 || nMapId == 6 || nMapId == 9) {
                        snsTiles = getTiles("/res/boss.v");
                    } else {
                        snsTiles = getTiles("/res/m_" + nMapId + ".v");
                    }
                    snTilesWidthNum = snsTiles[0].length;
                    snTilesHeightNum = snsTiles.length;
                    // initTilesOptions();
                    if (nMapId == 1) {
                        // isScriptWorking = true;
                        // isStoryModeWorking = true;
                        // isCommandWorked = true;
                        isScriptWorking = false;
                        isStoryModeWorking = false;
                        isCommandWorked = false;

                    } else {
                        isScriptWorking = false;
                        isStoryModeWorking = false;
                        isCommandWorked = false;

                    }
                    snTilesWidthNum = snsTiles[0].length;
                    snTilesHeightNum = snsTiles.length;

                    snTilesWidthNumInScreen = Consts.SN_WINDOW_WIDTH
                            / Consts.SN_TILE_WIDTH;
                    snTilesHeightNumInScreeen = Consts.SN_WINDOW_HEIGHT
                            / Consts.SN_TILE_HEIGHT;
                    snMapAreaWidthInScreen = snTilesWidthNumInScreen
                            * Consts.SN_TILE_WIDTH;
                    snMapAreaHeightInScreen = snTilesHeightNumInScreeen
                            * Consts.SN_TILE_HEIGHT;
                    snWindowX = 0;
                    snWindowY = 0;
                    snMapWidth = snTilesWidthNum * Consts.SN_TILE_WIDTH;
                    snMapHeight = snTilesHeightNum * Consts.SN_TILE_HEIGHT;
                    //

                    //
                    imageSps = Image.createImage("/res/0.png");
                    imageBox = Image.createImage("/res/box.png");
                    imageSaving = Image.createImage("/res/bbox.png");
                    imageZZ = Image.createImage("/res/zz.png");
                    imageMouse = Image.createImage("/res/mouse.png");
                    imageBall = Image.createImage("/res/ball.png");
                    imageDoor = Image.createImage("/res/door.png");
                    imageBomb = Image.createImage("/res/bomb.png");
                    imageFire = Image.createImage("/res/f.png");
                    imageSuc = Image.createImage("/res/suc.png");
                    imageFailuer = Image.createImage("/res/fail.png");
                    imageX = Image.createImage("/res/x.png");
                    imageCi = Image.createImage("/res/ci.png");
                    imageBf = Image.createImage("/res/bf.png");
                    imageWugong = Image.createImage("/res/wugong.png");
                    //
                    isScrollMapLeft = false;
                    isScrollMapRight = false;
                    // isScrollMapMid = false;
                    // isScrollMapBottom = false;
                    //
                    vecticUnits = new Vector(0, 1);
                    //

                    //
                    // animBullet = new Anim();
                    // animBullet.init(this, imageBall, "/res/sps/ball.a");
                    animLisa = new Anim();
                    animLisa.init(this, imageSps, "/res/sps/lisa.a");
                    animSaving = new Anim();
                    animSaving.init(this, imageSaving, "/res/sps/bbox.a");
                    animZZ = new Anim();
                    animZZ.init(this, imageZZ, "/res/sps/zz.a");
                    animZZ.isStop = false;
                    animZZ.isLoop = true;
                    //
                    animMouse = new Anim();
                    animMouse.init(this, imageMouse, "/res/sps/mouse.a");
                    animMouse.isStop = false;
                    animMouse.isLoop = true;
                    //

                    //
                    animHead = new Anim();
                    animHead.init(this, imageWugong, "/res/sps/head.a");
                    animHead.isStop = false;
                    animHead.isLoop = true;
                    animBody = new Anim();
                    animBody.init(this, imageWugong, "/res/sps/body.a");
                    animHead.isStop = false;
                    animHead.isLoop = true;
                    animEnd = new Anim();
                    animEnd.init(this, imageWugong, "/res/sps/end.a");
                    animEnd.isStop = false;
                    animEnd.isLoop = true;
                    //

                    //
                    animBf = new Anim();
                    animBf.init(this, imageBf, "/res/sps/bf.a");
                    animBf.isStop = false;
                    animBf.isLoop = true;
                    //
                    animBomb = new Anim();
                    animBomb.init(this, imageBomb, "/res/sps/bomb.a");
                    animBomb.isStop = false;
                    animBomb.isLoop = true;
                    //
                    animFire = new Anim();
                    animFire.init(this, imageFire, "/res/f.a");
                    animFire.isStop = false;
                    animFire.isLoop = false;
                    //
                    initParamsForNpc();
                    //
                    initObjects();
                    // hahah

                    initStory();
                    //
                    initCamera();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            break;
        }
        sbState = sbStateToLoad;
    }

    // public void initTilesOptions() {
    // if (snsTiles != null) {
    // sisTilesNull = new boolean[snsTiles.length][snsTiles[0].length];
    // for (int i = 0; i < snsTiles.length; i++) {
    // for (int j = 0; j < snsTiles[i].length; j++) {
    // if (Consts.getTileVar(snsTiles[i][j]) ==
    // Consts.SB_TILE_NULL) {
    // sisTilesNull[i][j] = true;
    // }
    // }
    // }
    // }
    // }
    public void initParamsForNpc() {
        isKeyReponseMenu = false;
        bGameResult = 0;

        nPowerCMax = 1;
        nBombNumCMax = 1;
        nRainOrSnow = 0;
        switch (nMapId) {
            case 1: {
                nPowerCMax = 1;
                nBombNumCMax = 1;
            }
            break;
            case 2: {
                nPowerCMax = 1;
                nBombNumCMax = 1;
                nRainOrSnow = 1;
            }
            break;
            case 3: {
                nPowerCMax = 1;
                nBombNumCMax = 2;
            }
            break;
            case 4: {
                nPowerCMax = 2;
                nBombNumCMax = 2;
                nRainOrSnow = 1;
            }
            break;
            case 5: {
                nPowerCMax = 2;
                nBombNumCMax = 2;
            }
            break;
            case 6: {
                nPowerCMax = 2;
                nBombNumCMax = 2;
            }
            break;
            case 7: {
                nPowerCMax = 3;
                nBombNumCMax = 3;
                nRainOrSnow = 1;
            }
            break;
            case 8: {
                nPowerCMax = 3;
                nBombNumCMax = 3;
            }
            break;
            case 9: {
                nPowerCMax = 3;
                nBombNumCMax = 3;
            }
            break;
        }
    }

    public void initObjects() {
        //

        vecticUnits = new Vector(0, 1);
        //
        switch (nMapId) {
            case 1: {
                addLisa(3, 14, Canvas.DOWN, 3);
                //
                addNoramOgre(11, 6, Canvas.LEFT);
                addNoramOgre(6, 8, (byte) Canvas.DOWN);
                addNoramOgre(8, 9, (byte) Canvas.DOWN);
                addNoramOgre(1, 1, (byte) Canvas.RIGHT);
                addNoramOgre(14, 1, (byte) Canvas.LEFT);
                addBox(4, 6);
                // 木箱，
                addSaving(2, 13);

            }
            break;
            case 2: {
                addLisa(1, 1, Canvas.DOWN, 3);

                // OGRE
                addNoramOgre(6, 4, (byte) Canvas.UP);
                addNoramOgre(9, 1, (byte) Canvas.DOWN);
                addNoramOgre(13, 2, (byte) Canvas.LEFT);
                addNoramOgre(15, 2, (byte) Canvas.RIGHT);
                addNoramOgre(5, 8, (byte) Canvas.RIGHT);
                addNoramOgre(1, 18, (byte) Canvas.RIGHT);
                addNoramOgre(18, 18, (byte) Canvas.RIGHT);

                // SAVING
                addSaving(6, 1);
                addSaving(18, 1);
                //
                addZhen(9, 4);
                addZhen(9, 10);
                addZhen(10, 11);
                addZhen(9, 12);
            }
            break;
            case 4: {
                addLisa(1, 1, Canvas.DOWN, 3);

                // OGRE
                addNoramOgre(3, 3, (byte) Canvas.DOWN);
                addNoramOgre(6, 6, (byte) Canvas.UP);
                addNoramOgre(10, 1, (byte) Canvas.RIGHT);
                addNoramOgre(18, 1, (byte) Canvas.LEFT);
                addNoramOgre(11, 6, (byte) Canvas.RIGHT);
                addNoramOgre(17, 4, (byte) Canvas.LEFT);
                addNoramOgre(12, 12, (byte) Canvas.RIGHT);
                addNoramOgre(12, 14, (byte) Canvas.RIGHT);
                addNoramOgre(3, 13, (byte) Canvas.RIGHT);
                addNoramOgre(3, 15, (byte) Canvas.DOWN);

                // SAVING
                addSaving(8, 1);
                addSaving(13, 8);

                // ZHEN
                addZhen(8, 8);
                addZhen(10, 8);
                addZhen(8, 10);
                addZhen(10, 10);
                addZhen(18, 12);
                addZhen(18, 14);

            }
            break;
            case 5: {
                //
                addLisa(10, 10, Canvas.DOWN, 3);
                // ogre
                addNoramOgre(2, 10, Canvas.RIGHT);
                addNoramOgre(18, 10, Canvas.LEFT);
                addNoramOgre(10, 2, Canvas.DOWN);
                addNoramOgre(10, 18, Canvas.UP);
                // ogre
                addSuperOgre(1, 5, Canvas.DOWN);
                addSuperOgre(18, 1, Canvas.RIGHT);
                addSuperOgre(2, 17, Canvas.RIGHT);
                addSuperOgre(1, 18, Canvas.DOWN);
                addSuperOgre(16, 17, Canvas.RIGHT);
                addSuperOgre(19, 19, Canvas.UP);
                // saving
                addSaving(1, 1);
                addSaving(19, 9);
                // zhen
                addZhen(9, 8);
                addZhen(11, 8);
                addZhen(7, 10);
                addZhen(9, 10);
                addZhen(11, 10);
                addZhen(13, 10);
                addZhen(9, 12);
                addZhen(11, 12);

            }
            break;
            case 7: {
                // lisa
                addLisa(9, 8, Canvas.DOWN, 3);

                // normal
                addNoramOgre(9, 16, Canvas.DOWN);
                addNoramOgre(9, 16, Canvas.DOWN);
                // ogre
                addSuperOgre(9, 12, Canvas.UP);
                addSuperOgre(3, 5, Canvas.DOWN);
                addSuperOgre(16, 5, Canvas.DOWN);
                addSuperOgre(5, 17, Canvas.RIGHT);
                addSuperOgre(13, 17, Canvas.RIGHT);
                // saving
                addSaving(1, 1);
                addSaving(18, 1);
                // zhen
                addZhen(6, 9);
                addZhen(13, 9);
                addZhen(6, 11);
                addZhen(13, 11);

            }
            break;
            case 8: {
                //
                // lisa
                addLisa(1, 1, Canvas.DOWN, 3);

                // normal
                addNoramOgre(7, 8, Canvas.DOWN);
                addNoramOgre(1, 14, Canvas.RIGHT);
                addNoramOgre(13, 20, Canvas.DOWN);
                // ogre
                addSuperOgre(16, 11, Canvas.LEFT);
                addSuperOgre(20, 2, Canvas.LEFT);
                addSuperOgre(21, 3, Canvas.DOWN);
                addSuperOgre(27, 10, Canvas.UP);
                addSuperOgre(1, 27, Canvas.UP);
                addSuperOgre(7, 20, Canvas.DOWN);
                addSuperOgre(20, 14, Canvas.RIGHT);
                // saving

                // zhen
                addZhen(27, 14);
                addZhen(27, 18);
                addZhen(27, 22);
                addZhen(27, 24);
            }
            break;
            case 3: {
                addLisa(5, 1, Canvas.DOWN, 3);
                //
                addBossSnake(1, 1, 0, Canvas.UP);
                addBossSnake(1, 2, 1, Canvas.UP);
                addBossSnake(1, 3, 1, Canvas.UP);
                addBossSnake(1, 4, 2, Canvas.UP);

            }
            break;
            case 9: {
                addLisa(5, 1, Canvas.DOWN, 5);

                addBossSnake(1, 1, 0, Canvas.UP);
                addBossSnake(1, 2, 1, Canvas.UP);
                addBossSnake(1, 3, 1, Canvas.UP);
                addBossSnake(1, 4, 1, Canvas.UP);
                addBossSnake(1, 5, 1, Canvas.UP);
                addBossSnake(1, 6, 1, Canvas.UP);
                addBossSnake(2, 6, 1, Canvas.LEFT);
                addBossSnake(3, 6, 1, Canvas.LEFT);
                addBossSnake(4, 6, 1, Canvas.LEFT);
                addBossSnake(5, 6, 2, Canvas.LEFT);

                //
            }
            break;
            case 6: {
                // npcLisa = new NPC();
                // npcLisa.initBasic("Lisa", 5, 1, (byte) Canvas.DOWN, 4, false,
                // true,
                // 0);
                // npcLisa.initParam(10, 10, 0, 10, 10, 8, 2, 4);
                // npcLisa.nLevel = 1;
                // npcLisa.initAnim(animLisa);
                addLisa(5, 1, Canvas.DOWN, 4);
                cpc2 = new CPC((byte) CPC.B_VAR_BOSS_UFO);
                cpc2.initBasic("b1", 1, 4, (byte) Canvas.UP, 2, true, true, 0);
                cpc2.initParam(8, 2, 2, 8, 2, 4, 2, 2);
                cpc2.initAnim(animBf);
                cpc2.anim.isLoop = true;
                cpc2.anim.isStop = false;
                vecticUnits.addElement(cpc2);

            }
            break;
        }

        // snMapX = 0;
        // snMapY = 0;
        //
        // nRainOrSnow = 0; // rain
        nRainOrSnowXY = new int[20 + getRandomInt(0, 5, 0)][];
        for (int i = 0; i < nRainOrSnowXY.length; i++) {
            nRainOrSnowXY[i] = new int[3];
            nRainOrSnowXY[i][0] = getRandomInt(0, Consts.SN_SCREEN_WIDTH, i);
            nRainOrSnowXY[i][1] = getRandomInt(0, Consts.SN_SCREEN_HEIGHT,
                    i << 2);
            // state;)
            nRainOrSnowXY[i][2] = getRandomInt(0, 3, i << 2);
        }
        // :)
        // snMapX = 0;
        // snMapY = 0;
        // npcLisa.nXInScreen = npcLisa.nX - MyGameCanvas.snMapX;
        // npcLisa.nYInScreen = npcLisa.nY - MyGameCanvas.snMapY;

    }

    public void addLisa(int x, int y, int dir, int hpMax) {
        npcLisa = new NPC();
        npcLisa.initBasic("Lisa", x, y, (byte) dir, 4, false, true, 0);
        npcLisa.initParam(hpMax, 10, 0, hpMax, 10, 8, 2, 4);
        npcLisa.nLevel = 1;
        npcLisa.initAnim(animLisa);
        npcLisa.anim.changeAction(npcLisa.bDirection);
    }

    public void addNoramOgre(int x, int y, int dir) {
        cpc2 = new CPC((byte) CPC.B_VAR_WORM);
        cpc2.initBasic("WORM", x, y, (byte) dir, 2, true, true, 0);
        cpc2.initParam(2, 2, 2, 2, 2, 4, 2, 2);
        cpc2.initAnim(animMouse.copy());
        vecticUnits.addElement(cpc2);
        cpc2 = null;
    }

    public void addSuperOgre(int x, int y, int dir) {
        cpc2 = new CPC((byte) CPC.B_VAR_OGRE);
        cpc2.initBasic("OGRE", x, y, (byte) dir, 20, true, true, 0);
        cpc2.initParam(2, 2, 2, 2, 2, 4, 2, 2);
        cpc2.initAnim(animZZ.copy());
        vecticUnits.addElement(cpc2);
        cpc2 = null;
    }

    public void addSaving(int x, int y) {
        cpc2 = new CPC((byte) CPC.B_VAR_SAVING);
        cpc2.initBasic("baobei", x, y, (byte) Canvas.UP, 0, false, true, 0);
        cpc2.initParam(4, 6, 50, 50, 50, 50, 2, 2);
        cpc2.initAnim(animSaving.copy());
        vecticUnits.addElement(cpc2);
        cpc2 = null;
    }

    public void addZhen(int x, int y) {
        cpc2 = new CPC((byte) CPC.B_VAR_ZHEN);
        cpc2.initBasic("zhen", x, y, (byte) Canvas.UP, 2, true, true, 0);
        cpc2.initParam(2, 2, 2, 2, 2, 4, 2, 2);
        cpc2.initAnim(null);
        vecticUnits.addElement(cpc2);
        cpc2.nTimerMax = 30;
        cpc2 = null;
    }

    public void addBox(int x, int y) {
        cpc2 = new CPC((byte) CPC.B_VAR_BOX);
        cpc2.initBasic("Box", x, y, (byte) Canvas.UP, 0, false, true, 0);
        cpc2.initParam(4, 6, 50, 50, 50, 50, 2, 2);
        cpc2.initAnim(null);
        vecticUnits.addElement(cpc2);
        cpc2 = null;
    }

    public void addBossSnake(int x, int y, int var, int dir) {
        // var 0: 头,身,尾
        cpc2 = new CPC((byte) CPC.B_VAR_BOSS_SNAKE);
        cpc2.initBasic("b1", x, y, (byte) dir, 2, true, true, 0);
        cpc2.initParam(4, 2, 2, 4, 2, 4, 2, 2);
        if (var == 0) {
            cpc2.initAnim(animHead.copy());
        } else if (var == 1) {
            cpc2.initAnim(animBody.copy());
        } else {
            cpc2.initAnim(animEnd.copy());
        }
        cpc2.anim.initParam(cpc2.bDirection, false, true);
        cpc2.anim.changeAction(cpc2.bDirection);
        vecticUnits.addElement(cpc2);
    }

    /**
     * 转换状态
     */
    final void doStateChange(byte aBtateToLoad) {
        // nLoadingTimerC = 0;
        sbState = SB_STATE_LOADING;
        sbStateToLoad = aBtateToLoad;
        loadingGameRes = new LoadingGameRes();
    }

    /**
     * 内部类，多线程资源管理器
     */
    class LoadingGameRes implements Runnable {

        Thread thread = null;
        boolean isWorkComplete = false;

        public LoadingGameRes() {

            isWorkComplete = false;
            thread = new Thread(this);
            thread.start();
        } // LoadingGameRes

        public void run() {
            synchronized (this) {

                freeAllResource();
                initResource();

                isWorkComplete = true;
                System.gc();
                thread = null;
            }

        } // run
    } // inner classs

    /**
     * 循环控制
     */
    public final void tick() {
        if (loadingGameRes == null) {
        } // if
        else {
            if (!loadingGameRes.isWorkComplete) {
                ; // do Nothing when the machine loading resource...
            } // if
        } // else
        synchronized (this) {

            doKey();
            doLogic();

            doPaint();

        }

    } // void tick()

    /**
     * 游戏逻辑控制
     */
    final void doLogic() {
        if (isPaused) {
        } else {
            switch (sbState) {
                case SB_STATE_LOADING: {
                    // nLoadingTimerC = ++nLoadingTimerC % 4;
                }
                break;
                case SB_STATE_SPLASH: {
                    nSlpashTimerC += Consts.SN_MILLIS_PER_TICK;
                    if (nSlpashTimerC >= Consts.SN_SPLASH_TIMER_MAX) {
                        nSlpashTimerC = 0;
                        doStateChange(SB_STATE_MENU);
                    }
                }
                break;
                case SB_STATE_MENU: {
                }
                break;
                case SB_STATE_GAMEING: {
                    //
                    if (isStoryModeWorking) {
                        // nothing
                        if (nTimerStory < nTimerStoryMax) {
                            nTimerStory++;
                        } else {
                            nTimerStory = 0;
                            nLine++;
                            if (nLine > nLineMax) {
                                isStoryModeWorking = false;
                            }
                        }
                    } else if (isFoorsMenuWorked) {
                        // 道具栏打开时？
                    } else {
                        bGameResult = getGameResult();
                        //
                        if (!isMenuInGameWorked) {
                            if (isScriptWorking) {
                                //
                                commandScript();
                                script();

                            } else {
                            }
                            if (isDialogWorking) {
                            } else {
                                if (bGameResult != 0) {
                                    if (nTimerForGameResultShow > 0) {
                                        nTimerForGameResultShow--;
                                        isKeyReponseMenu = false;
                                    } else {
                                        if (isKeyReponseMenu) {
                                            isMenuInGameWorked = true;
                                        }
                                    }
                                }
                                doWeather();
                                mapRefresh();

                                npcLisa.update();
                                try {
                                    unitsRefresh();
                                    info();
                                } catch (Exception ex) {
                                }
                                // mapRefresh();

                            }
                        }
                    }

                }
                break;
            }
        }
    }
    //
    boolean isCommandWorked = true;
    // walk -> w, d -> 对话
    String[] strCommand1D = {"w", "w", "w", "d"};
    short[][] snCommand2D = {{7, (short) Canvas.DOWN},
        {5, (short) Canvas.RIGHT}, {0, (short) Canvas.UP}, {0}};
    int nStrCommand1DP = 0;
    boolean isScriptDoDiaKeyPressed = false;

    public void commandScript() {
        if (isCommandWorked) {
            if (nStrCommand1DP >= strCommand1D.length) {
                isScriptWorking = false;
                strCommand1D = null;
                snCommand2D = null;
            } else {

                if (strCommand1D[nStrCommand1DP].equals("w")) {
                    isScriptWalk = true;
                    nScriptWalkLength = 0;
                    nScriptWalkLengthMax = snCommand2D[nStrCommand1DP][0]
                            * (Consts.SN_TILE_WIDTH / npcLisa.nSpeed);
                    npcLisa.setDirection((byte) snCommand2D[nStrCommand1DP][1]);

                } else if (strCommand1D[nStrCommand1DP].equals("d")) {
                    isScriptDialog = true;
                    isScriptDialogWorked = false;
                    isScriptDoDiaKeyPressed = false;
                }
                //
                nStrCommand1DP++;
                isCommandWorked = false;
            }

        }

    }
    //
    boolean isScriptWalk = false;
    int nScriptWalkLength = 0;
    int nScriptWalkLengthMax = 10;
    //
    boolean isScriptDialog = false;
    boolean isScriptDialogWorked = false;

    public void script() {
        if (isScriptWalk) {
            isCommandWorked = false;
            if (nScriptWalkLength < nScriptWalkLengthMax) {
                nScriptWalkLength++;
                switch (npcLisa.bDirection) {
                    case Canvas.UP: {
                        npcLisa.doKey(UP);
                    }
                    break;
                    case Canvas.DOWN: {
                        npcLisa.doKey(DOWN);
                    }
                    break;
                    case Canvas.LEFT: {
                        npcLisa.doKey(LEFT);
                    }
                    break;
                    case Canvas.RIGHT: {
                        npcLisa.doKey(RIGHT);
                    }
                    break;
                }
            } else {
                isScriptWalk = false;
                nScriptWalkLength = 0;
                isCommandWorked = true;
            }
        } else if (isScriptDialog) {
            // isScriptDialog = true;
            // isScriptDialogWorked = false;
            if (!isScriptDialogWorked) {
                if (!isScriptDoDiaKeyPressed) {
                    npcLisa.doKey(FIRE);
                    isScriptDoDiaKeyPressed = true;
                } else {
                    if (!isDialogWorking) {
                        isScriptDialogWorked = true;
                    }
                }

            } else {
                isScriptDialog = false;
                isCommandWorked = true;
            }

        }
    }

    /**
     * 控制各种物体在地图上做绝对的运动
     */
    public void unitsRefresh() {
        // if (vecticUnits != null) {
        // for (int i = 0; i < vecticUnits.size(); i++) {
        // CPC mf = (CPC) vecticUnits.elementAt(i);
        // if (mf.bVar == CPC.B_VAR_BOMBSFIRE) {
        // mf.update();
        // if (mf.nHP <= 0) {
        // vecticUnits.removeElementAt(i);
        // i--;
        // }
        // }
        // }
        // }
        for (int i = 0; i < vecticUnits.size(); i++) {
            CPC cpc = ((CPC) vecticUnits.elementAt(i));

            if (isScriptWorking) {
                if (cpc.bVar == CPC.B_VAR_NPC) {
                    cpc.update();
                }
            } else {
                cpc.update();
            }

            if (cpc.nHP <= 0) {
                // 掉宝几率控制

                switch (cpc.bVar) {
                    case CPC.B_VAR_RABIT: {
                        // int id = getRandomInt(0, 5, i);
                        // num_foos[id]++;
                        // strInfo = new String("获得" + strrFoos[id]);
                        // npcLisa.nEX += cpc.nEXMax;
                    }
                    break;
                    case CPC.B_VAR_WORM: {
                        int id = getRandomInt(0, 5, i);
                        // num_foos[id]++;
                        if (id == 2) {
                            strInfo = new String("获得" + "生命值");
                            npcLisa.nHP++;
                        }

                    }
                    break;
                    case CPC.B_VAR_MAGIC_CPC_FIRE_BALL: {
                        CPC.bulletNum--;
                    }
                    break;
                }
                vecticUnits.removeElementAt(i);
                i--;
            }

        }
    }

    /**
     * 控制绘制范围，不能使绘制范围超过地图的区域
     */
    public void mapRefresh() {
        if (snWindowX == 0) {
            // X方向全屏
            if (npcLisa.nX >= (Consts.SN_SCREEN_WIDTH >> 1)
                    && npcLisa.nX <= (snMapWidth - (Consts.SN_SCREEN_WIDTH >> 1))) {
                snMapX = npcLisa.nX - (Consts.SN_SCREEN_WIDTH >> 1);
            } else {
                if (npcLisa.nX >= (snMapWidth - (Consts.SN_SCREEN_WIDTH >> 1))) {
                    snMapX = snMapWidth - Consts.SN_SCREEN_WIDTH;
                } else {
                    snMapX = 0;
                }
            }

        }
        if (snWindowY == 0) {
            // X方向全屏
            if (npcLisa.nY >= (Consts.SN_SCREEN_HEIGHT >> 1)
                    && npcLisa.nY <= (snMapHeight - (Consts.SN_SCREEN_HEIGHT >> 1))) {
                snMapY = npcLisa.nY - (Consts.SN_SCREEN_HEIGHT >> 1);
            } else {
                if (npcLisa.nY >= (snMapHeight - (Consts.SN_SCREEN_HEIGHT >> 1))) {
                    snMapY = snMapHeight - Consts.SN_SCREEN_HEIGHT;
                } else {
                    snMapY = 0;
                }
            }

        }
    }
    /**
     * 游戏键盘控制
     */
    boolean isKeyUpWorked = false;

    final void doKey() {

        if (isPaused) {
            if (snKeyCodePressed != 0 || snKeyCodePressed != 0) {
                snKeyCodeReleased = 0;
                snKeyCodePressed = 0;
                isPaused = false;

            }
        } else {
            switch (sbState) {
                case SB_STATE_LOADING: {
                    // NOTHING
                }
                break;
                case SB_STATE_SPLASH: {
                    // NOTHING
                }
                break;
                case SB_STATE_MENU: {
                    doKeyStateMenu();
                }
                break;
                case SB_STATE_GAMEING: {

                    if (isStoryModeWorking) {
                        // nothing ......
                        if (snKeyCodePressed != 0) {

                            if (snKeyCodePressed == KEY_NUM5) {
                                isStoryModeWorking = false;
                            }
                            snKeyCodePressed = 0;
                        }
                    } else if (isFoorsMenuWorked) {
                        int tkey = getKeyPressed(true);
                        switch (tkey) {
                            case Consts.SN_KEY_LEFT_SOFT_KEY: {
                            }
                            break;
                            case Consts.SN_KEY_RIGHT_SOFT_KEY: {
                                // doStateChange(SB_STATE_MENU);
                                isFoorsMenuWorked = false;
                                snKeyCodePressed = 0;

                            }
                            break;
                            case UP: {
                                if (nPStrMenuFoors == 1) {
                                    if (nPStrrFoos > 0) {
                                        nPStrrFoos--;
                                    }
                                }

                            }
                            break;
                            case DOWN: {
                                if (nPStrMenuFoors == 1) {
                                    if (nPStrrFoos < strrFoos.length - 1) {
                                        nPStrrFoos++;
                                    }
                                }
                            }
                            break;
                            case LEFT: {
                                if (nPStrMenuFoors > 0) {
                                    nPStrMenuFoors--;
                                }
                                // Consts.log("heloo");
                            }
                            break;
                            case RIGHT: {
                                if (nPStrMenuFoors < strMenuFoors.length - 1) {
                                    nPStrMenuFoors++;
                                }
                            }
                            break;
                            case FIRE: {
                                // 使用某个道具
                                //
                            }
                            break;
                            case KEY_STAR: {
                                if (nStarPointer == nPoundPointer) {
                                    nPoundPointer = -1;
                                    nStarPointer = nPStrrFoos;
                                } else {
                                    nStarPointer = nPStrrFoos;
                                }
                            }
                            break;
                            case KEY_POUND: {
                                if (nStarPointer == nPoundPointer) {
                                    nStarPointer = -1;
                                    nPoundPointer = nPStrrFoos;
                                } else {
                                    nPoundPointer = nPStrrFoos;
                                }
                            }
                            break;
                            default:

                                break;
                        }

                    } else {
                        if (isMenuInGameWorked) {
                            int tkey = getKeyPressed(true);
                            switch (tkey) {
                                case Consts.SN_KEY_LEFT_SOFT_KEY:
                                case FIRE: {
                                    // doStateChange(SB_STATE_MENU);
                                    isMenuInGameWorked = false;
                                    if (bGameResult == 0) {
                                        if (nPorintStrrMenuInGame == 0) {
                                            ;
                                        } else if (nPorintStrrMenuInGame == 1) {
                                            doStateChange(SB_STATE_MENU);
                                        } else if (nPorintStrrMenuInGame == 2) {
                                            Main.isLooping = false;
                                        }
                                    } else if (bGameResult == 1) {
                                        if (nPorintStrrMenuInGame == 0) {
                                            nMapId += 1;
                                            doStateChange(SB_STATE_GAMEING);
                                        } else if (nPorintStrrMenuInGame == 1) {
                                            doStateChange(SB_STATE_MENU);
                                        } else if (nPorintStrrMenuInGame == 2) {
                                            Main.isLooping = false;
                                        }
                                    } else if (bGameResult == 2) {
                                        if (nPorintStrrMenuInGame == 0) {
                                            doStateChange(SB_STATE_GAMEING);
                                        } else if (nPorintStrrMenuInGame == 1) {
                                            doStateChange(SB_STATE_MENU);
                                        } else if (nPorintStrrMenuInGame == 2) {
                                            Main.isLooping = false;
                                        }
                                    }

                                }
                                break;
                                case UP: {
                                    if (nPorintStrrMenuInGame == 0) {
                                        nPorintStrrMenuInGame = strrMenuInGame.length - 1;
                                    } else {
                                        nPorintStrrMenuInGame--;
                                    }
                                }
                                break;
                                case DOWN: {
                                    if (nPorintStrrMenuInGame == strrMenuInGame.length - 1) {
                                        nPorintStrrMenuInGame = 0;
                                    } else {
                                        nPorintStrrMenuInGame++;
                                    }
                                }
                                break;
                                default:

                                    break;
                            }

                        } else {
                            int tkey = getKeyPressed(false);
                            if (isDialogWorking) {
                                switch (tkey) {
                                    case Consts.SN_KEY_LEFT_SOFT_KEY: {
                                        // doStateChange(SB_STATE_MENU);
                                        nPorintStrrMenuInGame = 0;
                                        isMenuInGameWorked = true;
                                        snKeyCodePressed = 0;
                                    }
                                    break;

                                    case UP: {
                                        page--;
                                        if (page < 0) {
                                            page = 0;
                                        }

                                    }
                                    break;
                                    case DOWN: {
                                        page++;
                                        // 控制翻到相应的页数
                                        if (page > lastpage) {
                                            freeStrDialog();
                                        }

                                    }
                                    break;
                                }
                                snKeyCodePressed = 0;
                            } else {
                                if (bGameResult != 0) {
                                    if (nTimerForGameResultShow <= 0) {
                                        if (FIRE == getKeyPressed(false)) {
                                            if (bGameResult == 1) {
                                                if (nMapId >= Consts.SN_ABS_MAX_LEVEL + 1) {
                                                    doStateChange(SB_STATE_MENU);

                                                }
                                            }
                                            isKeyReponseMenu = true;
                                            snKeyCodePressed = 0;
                                        }

                                        return;
                                    }

                                }
                                switch (tkey) {
                                    case Consts.SN_KEY_LEFT_SOFT_KEY: {
                                        // doStateChange(SB_STATE_MENU);
                                        nPorintStrrMenuInGame = 0;
                                        isMenuInGameWorked = true;
                                        snKeyCodePressed = 0;
                                    }
                                    break;
                                    case Consts.SN_KEY_RIGHT_SOFT_KEY: {
                                        // doStateChange(SB_STATE_MENU);
                                        if (!isScriptWorking) {
                                            // isFoorsMenuWorked = true;
                                            // snKeyCodePressed = 0;
                                            // //
                                            // nPStrMenuFoors = 0;
                                            // nPStrrFoos = 0;
                                            //
                                        }

                                    }
                                    break;
                                    case UP: {
                                        if (!isScriptWorking) {
                                            npcLisa.doKey(Canvas.UP);
                                        }
                                    }
                                    break;
                                    case DOWN: {
                                        if (!isScriptWorking) {
                                            npcLisa.doKey(Canvas.DOWN);
                                        }
                                    }
                                    break;
                                    case LEFT: {
                                        if (!isScriptWorking) {
                                            npcLisa.doKey(Canvas.LEFT);
                                        }
                                    }
                                    break;
                                    case RIGHT: {
                                        if (!isScriptWorking) {
                                            npcLisa.doKey(Canvas.RIGHT);
                                        }
                                    }
                                    break;
                                    case FIRE: {
                                        if (!isScriptWorking) {
                                            npcLisa.doKey(Canvas.FIRE);
                                            snKeyCodePressed = 0;
                                        }
                                    }
                                    break;
                                    case KEY_STAR: {
                                        if (!isScriptWorking) {
                                            // 使用道具
                                            doUseFoorsById(nStarPointer);

                                            npcLisa.doKey(0);
                                            snKeyCodePressed = 0;
                                        }
                                    }
                                    break;
                                    case KEY_POUND: {
                                        if (!isScriptWorking) {
                                            // 使用道具
                                            doUseFoorsById(nPoundPointer);
                                            snKeyCodePressed = 0;
                                            npcLisa.doKey(0);
                                        }
                                    }
                                    break;

                                    default:
                                        if (!isScriptWorking) {
                                            npcLisa.doKey(0);
                                        }
                                        break;
                                }
                            }
                        }
                    }

                }

                break;
            }

        }
    }

    public void doUseFoorsById(int id) {
        if (id < 0) {
            return;
        }
        // 效用~~~~~~~~~~~~~~~~
        // strrFoos = { "蜂蜜", "水果", "蜂王浆", "甘露", "灵芝草", "人参" };
        switch (id) {
            case 0: {
                if (npcLisa != null) {
                    if (npcLisa.nHP < npcLisa.nHPMax) {

                        if (num_foos[id] > 0) {
                            npcLisa.addHP(10);
                            num_foos[id]--;
                        }
                    }

                }
            }
            break;
            case 1: {
                if (npcLisa != null) {
                    if (npcLisa.nHP < npcLisa.nHPMax) {
                        if (num_foos[id] > 0) {
                            npcLisa.addHP(20);
                            num_foos[id]--;
                        }
                    }

                }
            }
            break;
            case 2: {
                if (npcLisa != null) {
                    if (npcLisa.nHP < npcLisa.nHPMax) {
                        if (num_foos[id] > 0) {
                            npcLisa.addHP(1000);
                            num_foos[id]--;
                        }
                    }

                }
            }
            break;
            case 3: {
                if (npcLisa != null) {
                    if (npcLisa.nMP < npcLisa.nMPMax) {
                        if (num_foos[id] > 0) {
                            npcLisa.addMP(10);
                            num_foos[id]--;
                        }
                    }

                }
            }
            break;
            case 4: {
                if (npcLisa != null) {
                    if (npcLisa.nMP < npcLisa.nMPMax) {
                        if (num_foos[id] > 0) {
                            npcLisa.addMP(20);
                            num_foos[id]--;
                        }
                    }

                }
            }
            break;
            case 5: {
                if (npcLisa != null) {
                    if (npcLisa.nMP < npcLisa.nMPMax) {
                        if (num_foos[id] > 0) {
                            npcLisa.addMP(1000);
                            num_foos[id]--;
                        }
                    }

                }
            }
            break;

        }

    }

    /**
     * 菜单控制管理器
     */
    public void doKeyStateMenu() {
        if (isFivePressed == false) {
            if (snKeyCodePressed != 0) {
                isFivePressed = true;
                snKeyCodePressed = 0;
                return;
            }
            return;
        } else {
        }
        if (isStrrMenuButtonClicked) {
            switch (nPointerStrrMenu) {
                case 0: {
                    // doStateChange(SB_STATE_GAMEING);
                    switch (getKeyPressed(true)) {
                        case FIRE:
                        case Consts.SN_KEY_LEFT_SOFT_KEY: {
                            isStrrMenuButtonClicked = false;
                            if (nMapId <= 1) {
                                nMapId = 1;
                            }
                            doStateChange(SB_STATE_GAMEING);

                        }
                        break;
                        case Consts.SN_KEY_RIGHT_SOFT_KEY: {
                            isStrrMenuButtonClicked = false;
                        }
                        break;

                        case UP: {
                            nMapId -= 1;
                            nMapId = (short) ((Consts.SN_ABS_MAX_LEVEL + nMapId) % (Consts.SN_ABS_MAX_LEVEL + 1));
                            nMapId += 1;
                            if (nMapId >= snLevelOpened + 1) {
                                nMapId = (short) (snLevelOpened + 1);
                            }
                        }
                        break;
                        case DOWN: {
                            nMapId -= 1;
                            nMapId = (short) ((Consts.SN_ABS_MAX_LEVEL + nMapId + 2) % (Consts.SN_ABS_MAX_LEVEL + 1));
                            nMapId += 1;
                            if (nMapId >= snLevelOpened + 1) {
                                nMapId = (short) (snLevelOpened + 1);
                            }
                        }
                        break;
                    }

                }
                break;
                case 1: { // HELP
                    switch (getKeyPressed(true)) {
                        case FIRE:
                        case Consts.SN_KEY_LEFT_SOFT_KEY: {
                        }
                        break;
                        case Consts.SN_KEY_RIGHT_SOFT_KEY: {
                            isStrrMenuButtonClicked = false;
                        }
                        break;
                        case UP: {
                            if (nPointerStrrTextShow > 0) {
                                nPointerStrrTextShow -= SN_TEXT_ROWS_NUM_MAX;
                            }
                        }
                        break;
                        case DOWN: {
                            if (nPointerStrrTextShow < strrTextShows.length
                                    - SN_TEXT_ROWS_NUM_MAX) {
                                nPointerStrrTextShow += SN_TEXT_ROWS_NUM_MAX;
                            }
                        }
                        break;
                    }
                }
                break;
                case 2: { // ABOUT
                    switch (getKeyPressed(true)) {
                        case FIRE:
                        case Consts.SN_KEY_LEFT_SOFT_KEY: {
                        }
                        break;
                        case Consts.SN_KEY_RIGHT_SOFT_KEY: {
                            isStrrMenuButtonClicked = false;
                        }
                        break;
                        case UP: {
                            if (nPointerStrrTextShow > 0) {
                                nPointerStrrTextShow -= SN_TEXT_ROWS_NUM_MAX;
                            }
                        }
                        break;
                        case DOWN: {
                            if (nPointerStrrTextShow < strrTextShows.length
                                    - SN_TEXT_ROWS_NUM_MAX) {
                                nPointerStrrTextShow += SN_TEXT_ROWS_NUM_MAX;
                            }
                        }
                        break;
                    }
                }
                break;
                case 3: { // EXIT
                    Main.isLooping = false;
                }
                break;
            }

        } else {
            switch (getKeyPressed(true)) {
                case FIRE:
                case Consts.SN_KEY_LEFT_SOFT_KEY: {
                    switch (nPointerStrrMenu) {
                        case 0: {
                            // doStateChange(SB_STATE_GAMEING);
                            // isStrrMenuButtonClicked = true;
                            // doStateChange(SB_STATE_GAMEING);
                            // nMapId = 9;
                            isStrrMenuButtonClicked = true;
                        }
                        break;

                        case 1: { // HELP
                            nPointerStrrTextShow = 0;
                            strrTextShows = strrTextForHelp;
                            isStrrMenuButtonClicked = true;
                        }
                        break;
                        case 2: { // ABOUT
                            nPointerStrrTextShow = 0;
                            strrTextShows = strrTextForAbout;
                            isStrrMenuButtonClicked = true;
                        }
                        break;
                        case 3: { // EXIT
                            Main.isLooping = false;
                        }
                        break;
                    }
                }
                break;
                case Consts.SN_KEY_RIGHT_SOFT_KEY: {
                    isStrrMenuButtonClicked = false;
                }
                break;
                case LEFT: {
                    if (nPointerStrrMenu > 0) {
                        nPointerStrrMenu--;
                    }
                }
                break;
                case RIGHT: {
                    if (nPointerStrrMenu < strrMenu.length - 1) {
                        nPointerStrrMenu++;
                    }
                }
                break;
            }
        }
    }

    /**
     * 游戏绘制控制
     */
    final void doPaint() {
        doRepaints(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
    }

    /**
     * 双缓冲
     */
    public void paintGamingState(Graphics g) {
        // g.setClip(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
        int tx = snWindowX;
        int ty = snWindowY;
        int twidth = Consts.SN_SCREEN_WIDTH - (tx << 1);
        int theight = Consts.SN_SCREEN_WIDTH - (ty << 1);
        // g.setClip(tx, ty, twidth, theight);
        if (isStoryModeWorking) {

            //
            paintBackground(g);
            paintTiles(g);
            paintUnits(g);
            //

            //
            paintWeather(g);
            //

            g.setColor(0);
            g.fillRect(-1, Consts.SN_TXT_STORY_WIN_Y,
                    Consts.SN_SCREEN_WIDTH + 2, Consts.SN_TXT_STORY_WIN_HEIGHT);
            g.setColor(Consts.COLOR_WHITE);
            g.drawRect(-1, Consts.SN_TXT_STORY_WIN_Y,
                    Consts.SN_SCREEN_WIDTH + 2, Consts.SN_TXT_STORY_WIN_HEIGHT);
            g.setClip(0, Consts.SN_TXT_STORY_WIN_Y + 1, Consts.SN_SCREEN_WIDTH,
                    Consts.SN_TXT_STORY_WIN_HEIGHT - 2);

            for (int i = 0; i < SN_TEXT_ROWS_NUM_MAX; i++) {
                if (strStory != null) {
                    int id = nLine + i;
                    if (id >= 0 && id < strStory.length) {
                        g.drawString(strStory[id], SN_TEXT_TOP_X, SN_TEXT_TOP_Y
                                + i * SN_CHAR_HEIGHT, Graphics.TOP
                                | Graphics.LEFT);
                    }
                }

            }
            g.setClip(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
        } else if (isFoorsMenuWorked) {
            g.setColor(0);
            g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
            g.setColor(0xffffff);
            g.drawString(strMenuFoors[nPStrMenuFoors], 0, 0, Graphics.TOP
                    | Graphics.LEFT);
            switch (nPStrMenuFoors) {
                case 0: {
                    g.drawString("级别 ：" + npcLisa.nLevel, 5, 20, Graphics.TOP
                            | Graphics.LEFT);
                    g.drawString("生命 ：" + npcLisa.nHP, 5, 40, Graphics.TOP
                            | Graphics.LEFT);
                    g.drawString("魔法 ：" + npcLisa.nMP, 5, 60, Graphics.TOP
                            | Graphics.LEFT);
                    g.drawString("攻击 ：" + npcLisa.nAttackPower, 5, 80, Graphics.TOP
                            | Graphics.LEFT);
                    g.drawString("防御 ：" + npcLisa.nDefendPower, 5, 100,
                            Graphics.TOP | Graphics.LEFT);

                }
                break;
                case 1: {
                    for (int i = 0; i < strrFoos.length; i++) {
                        if (nPStrrFoos == i) {
                            g.setColor(0xff0000);
                        } else {
                            g.setColor(0xffffff);
                        }
                        g.drawString("" + strrFoos[i] + " : " + num_foos[i], 5,
                                (i + 1) * 20, Graphics.TOP | Graphics.LEFT);
                        if (nStarPointer == i && nStarPointer != -1) {
                            g.drawString("*键", 85, (i + 1) * 20, Graphics.TOP
                                    | Graphics.LEFT);
                        }
                        if (nPoundPointer == i && nPoundPointer != -1) {
                            g.drawString("#键", 85, (i + 1) * 20, Graphics.TOP
                                    | Graphics.LEFT);
                        }
                    }
                }
                break;
                case 2: {
                    g.drawString("帮助：）", 5, 20, Graphics.TOP | Graphics.LEFT);
                }
                break;
                default:
                    break;
            }
        } else {
            if (isMenuInGameWorked) {
                g.setColor(0);
                g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                        Consts.SN_SCREEN_HEIGHT);

                // g.setColor(0xffffff);
                // g.drawRect(-1, Consts.SN_MENU_IN_GAME_Y_POS_START,
                // Consts.SN_SCREEN_WIDTH + 2,
                // Consts.SN_MENU_IN_GAME_RECT_HEIGHT);

                String[] strTemp1d = null;
                if (bGameResult == 0) {
                    strTemp1d = this.strrMenuInGame;
                } else if (bGameResult == 1) {
                    strTemp1d = this.strrMenuInGameResultWin;
                } else {
                    strTemp1d = this.strrMenuInGameResultFailur;
                }
                for (int i = 0; i < strTemp1d.length; i++) {
                    if (i == nPorintStrrMenuInGame) {
                        // g.setColor(0xaaaaa);
                        // g.drawString(strrMenuInGame[i],
                        // Consts.SN_SCREEN_WIDTH >> 1,
                        // Consts.SN_MENU_STR_IN_GAME_Y_POS[i],
                        // Graphics.TOP | Graphics.HCENTER);
                        g.setColor(0xffffff);
                        g.drawString(strTemp1d[i],
                                (Consts.SN_SCREEN_WIDTH >> 1),
                                Consts.SN_MENU_STR_IN_GAME_Y_POS[i],
                                Graphics.TOP | Graphics.HCENTER);
                    } else {
                        g.setColor(0x888888);
                        g.drawString(strTemp1d[i], Consts.SN_SCREEN_WIDTH >> 1,
                                Consts.SN_MENU_STR_IN_GAME_Y_POS[i],
                                Graphics.TOP | Graphics.HCENTER);

                    }
                }
            } else {
                // paintTiles
                paintBackground(g);
                paintTiles(g);
                paintUnits(g);
                //
                paintWeather(g);
                //
                drawHPMP(g);
                if (bGameResult != 0) {
                    g.setColor(0xff0000);
                    if (bGameResult == 1) {
                        paintWindow(
                                g,
                                imageSuc,
                                (Consts.SN_SCREEN_WIDTH - imageSuc.getWidth()) >> 1,
                                (Consts.SN_SCREEN_HEIGHT - imageSuc.getHeight() >> 1),
                                true);

                        // g.drawString("成功!!!", Consts.SN_SCREEN_WIDTH >> 1,
                        // Consts.SN_SCREEN_HEIGHT >> 1, Graphics.TOP
                        // | Graphics.HCENTER);

                    } else {
                        paintWindow(g, imageFailuer,
                                (Consts.SN_SCREEN_WIDTH - imageFailuer.getWidth()) >> 1,
                                (Consts.SN_SCREEN_HEIGHT
                                - imageFailuer.getHeight() >> 1), true);

                        // g.drawString("失败!!!", Consts.SN_SCREEN_WIDTH >> 1,
                        // Consts.SN_SCREEN_HEIGHT >> 1, Graphics.TOP
                        // | Graphics.HCENTER);

                    }
                } else {
                    if (strInfo != null) {
                        g.setColor(0xff0000);
                        g.drawString(strInfo, Consts.SN_SCREEN_WIDTH >> 1,
                                Consts.SN_SCREEN_HEIGHT >> 1, Graphics.TOP
                                | Graphics.HCENTER);
                    }
                    if (isDialogWorking) {
                        paintDialog(g);
                    }
                }

            }
        }

    }

    public void paintDialog(Graphics g) {
        g.setColor(0xffffff);
        g.fillRoundRect(nDialogWindowX, nDialogWindowY, nDialogWindowWidth,
                nDialogWindowHeight, 8, 8);
        g.setColor(0x0);
        g.drawRoundRect(nDialogWindowX, nDialogWindowY, nDialogWindowWidth,
                nDialogWindowHeight, 8, 8);
        g.setColor(0x0);
        for (int i = 0; i < ROW; i++) {
            int id = page * ROW + i;
            if (id >= strDialog1D.length) {
                break;
            } else {
                g.drawString(strDialog1D[id], nDialogWindowTextX,
                        nDialogWindowTextY + i * (nFontHeight + nFontDisY),
                        Graphics.TOP | Graphics.LEFT);
            }
        }

    }

    public void paintBackground(Graphics g) {
        g.setColor(0);
        g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
    }

    public final void paintUnits(Graphics g) {
        // cpc.draw(g);
        // cpc2.draw(g);
        Vector tempVector = new Vector(0, 1);
        for (int i = 0; i < vecticUnits.size(); i++) {
            CPC cpc = ((CPC) vecticUnits.elementAt(i));
            if (cpc.isInsideScreen()) {
                tempVector.addElement(cpc);
            }
        }
        // 排序。冒泡
        for (int i = 0; i < tempVector.size(); i++) {

            for (int j = 0; j < tempVector.size() - i - 1; j++) {
                CPC cpc1 = (CPC) tempVector.elementAt(j);
                CPC cpc2 = (CPC) tempVector.elementAt(j + 1);
                if (cpc1.nYInScreen >= cpc2.nYInScreen) {
                    tempVector.setElementAt(cpc1, j + 1);
                    tempVector.setElementAt(cpc2, j);
                }
            }
        }

        // DRAW ZHEN
        for (int i = 0; i < tempVector.size(); i++) {
            CPC cpc1 = (CPC) (tempVector.elementAt(i));
            if (cpc1.bVar == CPC.B_VAR_ZHEN) {
                cpc1.draw(g);
            }
        }
        // BOMB
        for (int i = 0; i < tempVector.size(); i++) {
            CPC cpc1 = (CPC) (tempVector.elementAt(i));
            if (cpc1.bVar == CPC.B_VAR_BOMB) {
                cpc1.draw(g);
            }
        }
        // BOMBFIRE
        for (int i = 0; i < tempVector.size(); i++) {
            CPC cpc1 = (CPC) (tempVector.elementAt(i));
            if (cpc1.bVar == CPC.B_VAR_BOMBSFIRE) {
                cpc1.draw(g);
            }
        }
        boolean isLisaDraw = false;
        for (int i = 0; i < tempVector.size(); i++) {
            CPC cpc1 = (CPC) (tempVector.elementAt(i));
            if (cpc1.bVar == CPC.B_VAR_BOSS_UFO) {
                continue;
            }
            if (!isLisaDraw) {
                if (cpc1.nYInScreen > npcLisa.nYInScreen) {
                    isLisaDraw = true;

                    npcLisa.draw(g);
                }
            }
            if (cpc1.bVar == CPC.B_VAR_BOMBSFIRE || cpc1.bVar == CPC.B_VAR_BOMB
                    || cpc1.bVar == CPC.B_VAR_ZHEN) {
            } else {
                cpc1.draw(g);
            }
        }
        if (!isLisaDraw) {
            npcLisa.draw(g);
        }
        //
        for (int i = 0; i < tempVector.size(); i++) {
            CPC cpc1 = (CPC) (tempVector.elementAt(i));
            if (cpc1.bVar == CPC.B_VAR_BOSS_UFO) {
                cpc1.draw(g);
            }
        }

    }

    public void initCamera() {
        snWindowY = 0;
        snWindowX = 0;
        snMapX = 0;
        snMapY = 0;
        if (snMapWidth <= Consts.SN_SCREEN_WIDTH) {
            snWindowX = (Consts.SN_SCREEN_WIDTH - snMapWidth) / 2;

        } else {
            snWindowX = 0;
        }
        if (snMapHeight <= Consts.SN_SCREEN_HEIGHT) {
            snWindowY = (Consts.SN_SCREEN_HEIGHT - snMapHeight) / 2;

        } else {
            snWindowY = 0;

        }
        //
        if (snWindowX == 0) {
            // X方向全屏
            if (npcLisa.nX >= (Consts.SN_SCREEN_WIDTH >> 1)
                    && npcLisa.nX <= (snMapWidth - (Consts.SN_SCREEN_WIDTH >> 1))) {
                snMapX = npcLisa.nX - (Consts.SN_SCREEN_WIDTH >> 1);
            } else {
                if (npcLisa.nX >= (snMapWidth - (Consts.SN_SCREEN_WIDTH >> 1))) {
                    snMapX = snMapWidth - Consts.SN_SCREEN_WIDTH;
                } else {
                    snMapX = 0;
                }
            }

        }
        if (snWindowY == 0) {
            // X方向全屏
            if (npcLisa.nY >= (Consts.SN_SCREEN_HEIGHT >> 1)
                    && npcLisa.nY <= (snMapHeight - (Consts.SN_SCREEN_HEIGHT >> 1))) {
                snMapY = npcLisa.nY - (Consts.SN_SCREEN_HEIGHT >> 1);
            } else {
                if (npcLisa.nY >= (snMapHeight - (Consts.SN_SCREEN_HEIGHT >> 1))) {
                    snMapY = snMapHeight - Consts.SN_SCREEN_HEIGHT;
                } else {
                    snMapY = 0;
                }
            }

        }

    }

    /**
     * 绘制Tiles
     */
    public final void paintTiles(Graphics g) {
        //
        int t_mapX = snMapX;
        int t_mapY = snMapY;
        // g.drawImage(imageMap, 0, 0, Graphics.TOP|Graphics.LEFT);
        int nTilesX = t_mapX / Consts.SN_TILE_WIDTH;
        int nTilesY = t_mapY / Consts.SN_TILE_HEIGHT;
        //
        int nTilesXOffset = -(t_mapX % Consts.SN_TILE_WIDTH);
        int nTilesYOffset = -(t_mapY % Consts.SN_TILE_HEIGHT);
        //
        for (int i = -1; i < snTilesHeightNumInScreeen + 1; i++) {
            for (int j = -1; j < snTilesWidthNumInScreen + 1; j++) {
                //

                int nTileToPaintY = nTilesY + i;
                int nTileToPaintX = nTilesX + j;
                if (isXYInTiles(nTileToPaintX, nTileToPaintY, snsTiles)) {

                    int pic_id = snsTiles[nTileToPaintY][nTileToPaintX];
                    if (pic_id > 0) {
                        // myImage1d[pic_id - 1].drawImage(g, nTilesXOffset
                        // + snCameraX + Consts.SN_TILE_WIDTH * j, nTilesYOffset
                        // +snCameraY + Consts.SN_TILE_HEIGHT * i);
                        blt(
                                g,
                                imageMap,
                                (((pic_id - 1)) % (imageMap.getWidth() / Consts.SN_TILE_WIDTH))
                                * Consts.SN_TILE_WIDTH,
                                (((pic_id - 1)) / (imageMap.getWidth() / Consts.SN_TILE_WIDTH))
                                * Consts.SN_TILE_HEIGHT,
                                Consts.SN_TILE_WIDTH, Consts.SN_TILE_HEIGHT,
                                snWindowX + nTilesXOffset
                                + Consts.SN_TILE_WIDTH * j, snWindowY
                                + nTilesYOffset + Consts.SN_TILE_HEIGHT
                                * i);

                        // g.drawImage(imageTiles[pic_id - 1], nTilesXOffset
                        // + Consts.SN_TILE_WIDTH * j, nTilesYOffset
                        // + Consts.SN_TILE_HEIGHT * i, Graphics.TOP
                        // | Graphics.LEFT);
                    }
                }
                // if (Consts.SIS_DEBUG) {
                // g.setColor(0X00FF00);
                // g.drawRect(nTilesXOffset + Consts.SN_TILE_WIDTH * j,
                // nTilesYOffset + Consts.SN_TILE_HEIGHT * i,
                // Consts.SN_TILE_WIDTH, Consts.SN_TILE_HEIGHT);
                // }

            }
        }
    }

    static boolean isXYInTiles(int x, int y, byte[][] array) {
        if (array == null) {
            return false;
        }
        if (array[0] == null) {
            return false;
        }
        if (x < 0 || y < 0 || x >= array[0].length || y >= array.length) {
            return false;
        }
        return true;
    }

    /**
     * paintText
     */
    public void paintText(Graphics g, int anColor, int outLineColor) {
        g.setColor(outLineColor);
        g.drawRect(-1, SN_TEXT_TOP_Y - 2, Consts.SN_SCREEN_WIDTH + 2,
                SN_TEXT_ROWS_NUM_MAX * (SN_CHAR_HEIGHT + SN_TEXT_DIS) + 2);
        g.setColor(anColor);
        int k = 0;
        int i = nPointerStrrTextShow;
        for (; i < nPointerStrrTextShow + SN_TEXT_ROWS_NUM_MAX; i++, k++) {
            if (i < strrTextShows.length) {
                if (strrTextShows[i] != null) {
                    g.drawString(strrTextShows[i], SN_TEXT_TOP_X, SN_TEXT_TOP_Y
                            + (SN_CHAR_HEIGHT + SN_TEXT_DIS) * k, Graphics.TOP
                            | Graphics.LEFT);
                }

            }

        }
    }

    /**
     * paint
     */
    public void paint(Graphics g) {

        g.setClip(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
        g.setFont(font);
        if (isPaused) {
            g.setColor(0);
            g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
            //
            g.setColor(0xffffff);
            g.drawString("任意键继续", Consts.SN_SCREEN_WIDTH >> 1,
                    (Consts.SN_SCREEN_HEIGHT >> 1), Graphics.HCENTER
                    | Graphics.TOP);
        } else {

            switch (sbState) {
                case SB_STATE_LOADING: {
                    g.setColor(Consts.COLOR_BLACK);
                    g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                            Consts.SN_SCREEN_HEIGHT);
                    // int tr = getRandomInt(0, 255, 1);
                    int tg = getRandomInt(100, 255, 1 << 2);
                    // int tb = getRandomInt(0, 255, 1 << 4);
                    g.setColor(0, tg, 0);
                    g.drawString(Consts.SSTR_LOADING, Consts.SN_SCREEN_WIDTH >> 1,
                            Consts.SN_SCREEN_HEIGHT >> 1, Graphics.TOP
                            | Graphics.HCENTER);
                }
                break;
                case SB_STATE_SPLASH: {
                    g.setColor(Consts.COLOR_WHITE);
                    g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                            Consts.SN_SCREEN_HEIGHT);
                    g.setColor(Consts.COLOR_BLACK);
                    g.drawString("SPLASH " + nSlpashTimerC,
                            Consts.SN_SCREEN_WIDTH >> 1,
                            Consts.SN_SCREEN_HEIGHT >> 1, Graphics.TOP
                            | Graphics.HCENTER);
                    paintSpecString(g, "wenjun.gao@gmail.com", 0, 0, Graphics.TOP
                            | Graphics.LEFT, 0, 0XFFFFFF);
                }
                break;
                case SB_STATE_MENU: {
                    if (!isStrrMenuButtonClicked) {
                        g.setColor(Consts.COLOR_GBLUE);
                        g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                                Consts.SN_SCREEN_HEIGHT);

                        g.setColor(Consts.COLOR_BLACK);
                        g.drawString(Consts.STR_MIDLET_NAME,
                                Consts.SN_SCREEN_WIDTH >> 1, 1, Graphics.TOP
                                | Graphics.HCENTER);
                        if (isFivePressed == false) {
                            int ty = 35;
                            g.setColor(0);
                            g.drawString("按任意键继续", Consts.SN_SCREEN_WIDTH >> 1,
                                    Consts.SN_SCREEN_HEIGHT >> 1, Graphics.TOP
                                    | Graphics.HCENTER);

                        } else {
                            g.setColor(0);
                            int anY = 102;
                            // g.drawString(strrMenu[nPointerStrrMenu],
                            // Consts.SN_SCREEN_WIDTH >> 1, anY, Graphics.TOP
                            // | Graphics.HCENTER);
                            g.drawString(strrMenu[nPointerStrrMenu],
                                    Consts.SN_SCREEN_WIDTH >> 1, anY, Graphics.TOP
                                    | Graphics.HCENTER);
                            int alphaY = 5;
                            int anXIMGL = 27;
                            int anXIMGR = Consts.SN_SCREEN_WIDTH - anXIMGL;
                            g.drawImage(imageLeft, anXIMGL, anY + alphaY,
                                    Graphics.TOP | Graphics.RIGHT);
                            g.drawImage(imageRight, anXIMGR, anY + alphaY,
                                    Graphics.TOP | Graphics.LEFT);
                            // g.setColor(0XFFFFFF);
                            // g.drawString("V:" + Main.strVersion,
                            // Consts.SN_SCREEN_WIDTH - 1, 1, Graphics.TOP
                            // | Graphics.RIGHT);

                            paintLeftRightKeyPointer(g, true, false);
                        }
                        // g.setColor(0Xff0000);
                        // g.fillArc(myPoint.nX, myPoint.nY, 10, 10, 0, 360);

                    } else {
                        switch (nPointerStrrMenu) {
                            case 0: {
                                g.setColor(0);
                                g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                                        Consts.SN_SCREEN_HEIGHT);
                                g.setColor(0xffffff);
                                g.drawString("选择关卡", Consts.SN_SCREEN_WIDTH >> 1, 1,
                                        Graphics.TOP | Graphics.HCENTER);
                                //
                                int menuMaxNum = 5;
                                int t_array[] = new int[menuMaxNum];
                                int t_p_array_id = (menuMaxNum) / 2;
                                t_array[t_p_array_id] = nMapId - 1;

                                // ...
                                int k = 0;
                                for (int i = t_p_array_id - 1; i >= 0; i--) {
                                    k++;
                                    t_array[i] = (((Consts.SN_ABS_MAX_LEVEL + 1)
                                            + t_array[t_p_array_id] - k) % ((Consts.SN_ABS_MAX_LEVEL + 1)));

                                }
                                k = 0;
                                for (int i = t_p_array_id; i < menuMaxNum; i++) {

                                    t_array[i] = ((t_array[t_p_array_id] + k) % ((Consts.SN_ABS_MAX_LEVEL + 1)));
                                    k++;
                                }
                                //
                                int t_y = Consts.SN_SCREEN_HEIGHT
                                        - (font.getHeight() + 2) * menuMaxNum;
                                t_y >>= 1;
                                for (int i = 0; i < t_array.length; i++) {
                                    if (t_array[t_p_array_id] == t_array[i]) {
                                        g.setColor(0xffffff);
                                    } else {
                                        if (t_array[i] < snLevelOpened + 1) {
                                            g.setColor(0x999999);
                                        } else {
                                            g.setColor(0x555555);
                                        }

                                    }
                                    g.drawString("第 " + (t_array[i] + 1) + " 关",
                                            Consts.SN_SCREEN_WIDTH >> 1, t_y + (i)
                                            * (font.getHeight() + 2),
                                            Graphics.TOP | Graphics.HCENTER);
                                }
                                // g.setColor(0xffffff);

                                paintLeftRightKeyPointer(g, true, true);
                            }
                            break;

                            case 1: {
                                g.setColor(Consts.COLOR_GBLUE);
                                g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                                        Consts.SN_SCREEN_HEIGHT);
                                g.setColor(Consts.COLOR_BLACK);
                                g.drawString(strrMenu[nPointerStrrMenu],
                                        Consts.SN_SCREEN_WIDTH >> 1, 1, Graphics.TOP
                                        | Graphics.HCENTER);
                                paintText(g, Consts.COLOR_BLACK, 0XFFFFFF);
                                // DOWN UP
                                int anDownX = (Consts.SN_SCREEN_WIDTH >> 1)
                                        - (imageDown.getWidth());
                                int anUpX = (Consts.SN_SCREEN_WIDTH >> 1);

                                g.drawImage(imageDown, anDownX,
                                        Consts.SN_SCREEN_HEIGHT - 1, Graphics.BOTTOM
                                        | Graphics.LEFT);
                                g.drawImage(imageUp, anUpX,
                                        Consts.SN_SCREEN_HEIGHT - 4, Graphics.BOTTOM
                                        | Graphics.LEFT);
                            }
                            break;
                            case 2: {
                                g.setColor(Consts.COLOR_GBLUE);
                                g.fillRect(0, 0, Consts.SN_SCREEN_WIDTH,
                                        Consts.SN_SCREEN_HEIGHT);
                                g.setColor(Consts.COLOR_BLACK);
                                g.drawString(strrMenu[nPointerStrrMenu],
                                        Consts.SN_SCREEN_WIDTH >> 1, 1, Graphics.TOP
                                        | Graphics.HCENTER);

                                paintText(g, Consts.COLOR_BLACK, 0XFFFFFF);
                                // DOWN UP
                                int anDownX = (Consts.SN_SCREEN_WIDTH >> 1)
                                        - (imageDown.getWidth());
                                int anUpX = (Consts.SN_SCREEN_WIDTH >> 1);
                                g.drawImage(imageDown, anDownX,
                                        Consts.SN_SCREEN_HEIGHT - 1, Graphics.BOTTOM
                                        | Graphics.LEFT);
                                g.drawImage(imageUp, anUpX,
                                        Consts.SN_SCREEN_HEIGHT - 4, Graphics.BOTTOM
                                        | Graphics.LEFT);
                            }
                            break;

                        }
                        paintLeftRightKeyPointer(g, false, true);
                    }
                }
                break;
                case SB_STATE_GAMEING: {
                    paintGamingState(g);

                }
                break;
            }
        }

    }

    public void drawHPMP(Graphics g) {
        // int rect_height_max = 6;
        // int rect_width_max = 40;
        //
        // g.setColor(0xffffff);
        // g.drawRect(0, 120, rect_width_max + 1, rect_height_max + 1);
        // int length = 0;
        // length = npcLisa.nHP * rect_width_max / npcLisa.nHPMax;
        // g.setColor(0xff0000);
        // g.fillRect(0 + 1, 120 + 1, length, rect_height_max);
        //
        // g.setColor(0xffffff);
        // g.drawRect(86, 120, rect_width_max + 1, rect_height_max + 1);
        // length = npcLisa.nMP * rect_width_max / npcLisa.nMPMax;
        // g.setColor(0x0000ff);
        // g.fillRect(86 + 1, 120 + 1, length, rect_height_max);
        //
        // g.setColor(0xffffff);
        // g.drawRect(86, 100, rect_width_max + 1, rect_height_max + 1);
        // length = npcLisa.nEX * rect_width_max / npcLisa.nEXMax;
        // g.setColor(0x00ff00);
        // g.fillRect(86 + 1, 100 + 1, length, rect_height_max);
        //
        // //
        // g.setColor(0xffffff);
        // g.drawString("" + npcLisa.nLevel, 1, 100, Graphics.LEFT |
        // Graphics.TOP);
    }

    final void paintWeather(Graphics g) {
        // :)
        g.setColor(0xeeeeee);
        if (nRainOrSnow == 0) {
        } else if (nRainOrSnow == 1) {
            for (int i = 0; i < nRainOrSnowXY.length; i++) {
                if (nRainOrSnowXY[i][2] >= 5) { // 雨花
                    int height = getRandomInt(0, 5, i);
                    int width = getRandomInt(height, 10, i);
                    int tx = nRainOrSnowXY[i][0] - width / 2;
                    int ty = nRainOrSnowXY[i][1] - height / 2;
                    g.drawArc(tx, ty, width, height, 0, 360);

                } else {
                    g.drawLine(nRainOrSnowXY[i][0], nRainOrSnowXY[i][1],
                            nRainOrSnowXY[i][0], nRainOrSnowXY[i][1]
                            + getRandomInt(0, 3, i));

                }
            }
        }
    }

    public void doWeather() {
        for (int i = 0; i < nRainOrSnowXY.length; i++) {

            if (nRainOrSnowXY[i][2] > 6) {
                nRainOrSnowXY[i][2] = getRandomInt(0, 3, i);
                nRainOrSnowXY[i][0] = getRandomInt(0, Consts.SN_SCREEN_WIDTH, i);
                nRainOrSnowXY[i][1] = getRandomInt(0, Consts.SN_SCREEN_HEIGHT,
                        i << 2);
            } else {
                nRainOrSnowXY[i][1] += getRandomInt(0, 3, i);
                nRainOrSnowXY[i][2] += getRandomInt(0, 3, i);
            }
        }
    }

    /**
     * 画左右软键指示图标
     */
    final void paintLeftRightKeyPointer(Graphics g, boolean aisLeft,
            boolean aisRight) {
        switch (Consts.SB_CURRENT_PHONE_MODE) {
            case Consts.SB_MOTO_MODE: {
                if (aisLeft) {
                    if (imageLeft != null) {
                        g.drawImage(imageLeft, Consts.SN_SCREEN_WIDTH - 2,
                                Consts.SN_SCREEN_HEIGHT - 2, Graphics.RIGHT
                                | Graphics.BOTTOM);
                    }

                }
                if (aisRight) {
                    if (imageDown != null) {
                        g.drawImage(imageDown, 2, Consts.SN_SCREEN_HEIGHT - 2,
                                Graphics.LEFT | Graphics.BOTTOM);
                    }
                }
            }
            break;
            case Consts.SB_NOKIA_MODE: {
                if (aisLeft) {
                    if (imageDown != null) {
                        g.drawImage(imageDown, 2, Consts.SN_SCREEN_HEIGHT - 2,
                                Graphics.LEFT | Graphics.BOTTOM);
                    }
                }
                if (aisRight) {
                    if (imageLeft != null) {
                        g.drawImage(imageLeft, Consts.SN_SCREEN_WIDTH - 2,
                                Consts.SN_SCREEN_HEIGHT - 2, Graphics.RIGHT
                                | Graphics.BOTTOM);
                    }
                }
            }
            break;
        }
    }

    /**
     * 获得文字流中的字符串
     */
    public String[] getStrrFromFiles(String args) throws Exception {

        DataInputStream dis = new DataInputStream(getClass().getResourceAsStream(args));
        String[] strArray = null;
        int length = dis.readInt();
        if (length > 0) {
            strArray = new String[length];
        }
        for (int i = 0; i < length; i++) {
            String string = dis.readUTF();
            strArray[i] = string;
            // Consts.log(string);
        }
        dis.close();
        return strArray;
    }

    /**
     * Detect rectangle intersection
     *
     * @param r1x1 left co-ordinate of first rectangle
     * @param r1y1 top co-ordinate of first rectangle
     * @param r1x2 right co-ordinate of first rectangle
     * @param r1y2 bottom co-ordinate of first rectangle
     * @param r2x1 left co-ordinate of second rectangle
     * @param r2y1 top co-ordinate of second rectangle
     * @param r2x2 right co-ordinate of second rectangle
     * @param r2y2 bottom co-ordinate of second rectangle
     * @return True if there is rectangle intersection
     */
    public static boolean isIntersectRect(int r1x1, int r1y1, int r1x2,
            int r1y2, int r2x1, int r2y1, int r2x2, int r2y2) {
        if (r2x1 >= r1x2 || r2y1 >= r1y2 || r2x2 <= r1x1 || r2y2 <= r1y1) {
            return false;
        } else {
            return true;
        }
    }

    public void keyPressed(int keyCode) {
        // if (hero != null)
        // Consts.log("The hero current state : " + hero.bCurrentActionId);
        snKeyCodePressed = keyCode;
        snKeyCodeReleased = 0;
    }

    public void keyReleased(int keyCode) {
        snKeyCodeReleased = keyCode;
        snKeyCodePressed = 0;
    }

    /**
     * 关闭键盘帧听内存
     */
    public void closeKey() {
        snKeyCodePressed = 0;
        snKeyCodeReleased = 0;
    }

    /**
     * 获得按下键盘值
     */
    public int getKeyPressed(boolean isToCloseKey) {
        int keyCode = snKeyCodePressed;
        if (snKeyCodePressed == 0) {
            return keyCode;
        } else if (snKeyCodePressed == Consts.SN_KEY_LEFT_SOFT_KEY
                || snKeyCodePressed == Consts.SN_KEY_RIGHT_SOFT_KEY
                || snKeyCodePressed == KEY_STAR
                || snKeyCodePressed == KEY_POUND) {
            if (isToCloseKey) {
                snKeyCodePressed = 0;
            }
            return keyCode;
        } else {
            keyCode = getGameAction(snKeyCodePressed);
            if (isToCloseKey) {
                snKeyCodePressed = 0;
            }
            return (keyCode);
        }
    }

    /**
     * 获得松开按下键盘值
     */
    public int getKeyReleased(boolean isToCloseKey) {
        int keyCode = snKeyCodeReleased;
        if (snKeyCodeReleased == 0) {
            return keyCode;
        } else if (snKeyCodeReleased == Consts.SN_KEY_LEFT_SOFT_KEY
                || snKeyCodeReleased == Consts.SN_KEY_RIGHT_SOFT_KEY) {
            if (isToCloseKey) {
                snKeyCodeReleased = 0;
            }
            return keyCode;
        } else {
            keyCode = getGameAction(snKeyCodeReleased);
            snKeyCodeReleased = 0;
            return (keyCode);
        }
    }

    /**
     * 绘制特殊文字样式
     */
    public void paintSpecString(Graphics g, String str, int x, int y, int ac,
            int col1, int col2) {
        g.setColor(col1);
        g.drawString(str, x, y, ac);
        g.setColor(col2);
        g.drawString(str, x + 1, y + 1, ac);
    }

    /**
     * *************************************************************************
     * Reads a file from the BIN file and return data as a byte buffer
     * ************************************************************************
     */
    public byte[] readFile(String binfile, int pos) {
        byte buffer[];
        int len;

        try {

            InputStream is = getClass().getResourceAsStream("/" + binfile);

            is.skip(pos);

            len = (is.read() & 0xFF) << 24;
            len |= (is.read() & 0xFF) << 16;
            len |= (is.read() & 0xFF) << 8;
            len |= (is.read() & 0xFF);

            buffer = new byte[len];

            is.read(buffer, 0, buffer.length);

            is.close();
            is = null;

            System.gc();
        } catch (Exception e) {
            buffer = null;
            e.printStackTrace();
            System.gc();
            return null;
        }

        return buffer;
    }

    /**
     * *************************************************************************
     * Reads a file from the BIN file and return data as an Image
     * ************************************************************************
     */
    public Image readImage(String binfile, long pos) {
        byte buffer[];
        long len;

        try {
            InputStream is = getClass().getResourceAsStream("/" + binfile);

            is.skip(pos);

            len = (is.read() & 0xFF) << 24;
            len |= (is.read() & 0xFF) << 16;
            len |= (is.read() & 0xFF) << 8;
            len |= (is.read() & 0xFF);

            buffer = new byte[(int) len];

            is.read(buffer, 0, buffer.length);

            is.close();
            is = null;

            System.gc();
        } catch (Exception e) {
            buffer = null;
            e.printStackTrace();
            System.gc();
            return null;
        }

        return Image.createImage(buffer, 0, buffer.length);
    }

    /**
     * 粘贴某部分图片到@param aDesX,
     *
     * @param aDesY
     * @param Graphics g:获得的画笔对象
     * @param Image img: 操作的目标Image
     * @param aSrcX, aSrcY, aSrcWidth, aSrcHeight 想显示的图像的矩形区域的标识
     * @return void
     */
    static void blt(Graphics g, Image img, int aSrcX, int aSrcY, int aSrcWidth,
            int aSrcHeight, int aDesX, int aDesY) {
        g.setClip(aDesX, aDesY, aSrcWidth, aSrcHeight);
        g.drawImage(img, aDesX - aSrcX, aDesY - aSrcY, Graphics.TOP
                | Graphics.LEFT);
        g.setClip(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);
    }

    /**
     * 从文件中读地图
     *
     * @param String args : 文件名
     */
    public byte[][] getTiles(String arg) throws Exception {
        byte[][] ansMaps = null;
        DataInputStream dis = new DataInputStream(getClass().getResourceAsStream(arg));
        int length = dis.readInt();
        int height = dis.readInt();

        ansMaps = new byte[length][height];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                ansMaps[i][j] = dis.readByte();
            }
        }
        dis.close();

        return ansMaps;
    }
    public String strInfo = null;
    int nTimerForInfo = 0;

    public void info() {
        if (strInfo == null) {
            return;
        }
        if (nTimerForInfo++ > 20) {
            nTimerForInfo = 0;
            strInfo = null;
        }

    }

    public int getRandomInt(int min, int max, int seed) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis() + max + min + seed);
        return min + Math.abs(random.nextInt() % (max + 1 - min));
    }

    //
    public void showNotify() {
    }
    boolean isPaused = false;

    public void hideNotify() {
        //
        if (sbState == SB_STATE_GAMEING) {
            isPaused = true;
            snKeyCodePressed = 0;
            snKeyCodeReleased = 0;
        }
    }

    //
    //
    public void addFires(int x, int y) {
        x *= nTileWidth;
        y *= nTileHeight;
        if (vecticUnits.size() <= 0) {
            CPC mf = new CPC(CPC.B_VAR_BOMBSFIRE);
            mf.anim = animFire.copy();
            mf.nX = x;
            mf.nY = y;
            mf.nHP = 1;
            vecticUnits.addElement(mf);
        } else {
            boolean isFind = false;
            for (int i = 0; i < vecticUnits.size(); i++) {
                CPC mf = (CPC) vecticUnits.elementAt(i);
                if (mf.bVar == CPC.B_VAR_BOMBSFIRE) {
                    if (mf.nX == x && mf.nY == y) {
                        isFind = true;
                    }
                }
            }
            if (isFind == false) {
                CPC mf = new CPC(CPC.B_VAR_BOMBSFIRE);
                mf.anim = animFire.copy();
                mf.nX = x;
                mf.nY = y;
                mf.nHP = 1;
                vecticUnits.addElement(mf);

            }
        }
    }
    //
    /**
     * 获得游戏的结果
     *
     * @param : 空
     * @return byte 0 正常状态 1游戏胜利 2游戏失败
     */
    byte bGameResult = 0;

    byte getGameResult() {
        //

        if (bGameResult != 0) {
            return bGameResult;
        }//

        // 结果
        byte result = 0;
        // 失败条件
        if (npcLisa != null) {
            if (npcLisa.nHP <= 0) {
                result = 2;
            }
        }
        // 是否成功
        if (result != 2) {
            if (vecticUnits.size() <= 0) {
                result = 1;
            } else {
                int flag = 0;
                for (int i = 0; i < vecticUnits.size(); i++) {
                    CPC cpc = (CPC) vecticUnits.elementAt(i);
                    switch (cpc.bVar) {
                        // 对游戏胜利无影响的一些设置
                        case CPC.B_VAR_BOMB:
                        case CPC.B_VAR_BOMBSFIRE:
                        case CPC.B_VAR_BOX:
                        case CPC.B_VAR_DOOR:
                        case CPC.B_VAR_ZHEN:
                        case CPC.B_VAR_SAVING:
                        case CPC.B_VAR_NPC:
                        case CPC.B_VAR_MAGIC_CPC_FIRE:
                        case CPC.B_VAR_MAGIC_CPC_FIRE_BALL:
                        case CPC.B_VAR_MAGIC_LISA_FIRE: {
                            // flag无变化
                        }
                        break;
                        // 必须全部死亡的设置
                        case CPC.B_VAR_OGRE:
                        case CPC.B_VAR_RABIT:
                        case CPC.B_VAR_WORM: {
                            if (cpc.nHP > 0) {
                                flag = 1;
                            }
                        }
                        break;
                        // 必须剩一条命的设置
                        case CPC.B_VAR_BOSS_SIMPLY:
                        case CPC.B_VAR_BOSS_UFO:
                        case CPC.B_VAR_BOSS_SNAKE: {
                            if (cpc.nHP > 1) {
                                flag = 1;
                            }
                        }
                        break;
                    }
                    if (flag == 1) {
                        break;
                    }

                }
                if (flag == 0) {
                    result = 1;
                }
            }
        }
        //
        // if(true) {
        // result = 2;
        // }
        if (result != 0) {
            nTimerForGameResultShow = nTimerForGameResultShowMax;
            if (result == 1) {
                gameWin();
            } else if (result == 2) {
                ;//
            }
            initWindowShow(8, 5);
        }

        return result;

    }

    public void gameWin() {
        RMSSystem.saveLevelOpened(nMapId);
        if (vecticUnits != null) {
            for (int i = 0; i < vecticUnits.size(); i++) {
                CPC cpc = (CPC) vecticUnits.elementAt(i);
                switch (cpc.bVar) {
                    case CPC.B_VAR_BOMB:
                    case CPC.B_VAR_BOMBSFIRE:
                    case CPC.B_VAR_BOSS_SIMPLY:
                    case CPC.B_VAR_BOSS_SNAKE:
                    case CPC.B_VAR_BOSS_UFO:
                    case CPC.B_VAR_MAGIC_CPC_FIRE:
                    case CPC.B_VAR_MAGIC_CPC_FIRE_BALL:
                    case CPC.B_VAR_MAGIC_LISA_FIRE:
                    case CPC.B_VAR_NPC:
                    case CPC.B_VAR_OGRE:
                    case CPC.B_VAR_RABIT:
                    case CPC.B_VAR_WORM: {
                        vecticUnits.removeElementAt(i);
                        i--;
                    }
                }
            }
        }

    }
    //
    int nTimerForGameResultShow = 0;
    int nTimerForGameResultShowMax = 15;
    // 特效
    int nWindowTimer = 0;
    int nWindowTimerMax = 15;
    int nWindowHeightPer = 5;

    //
    public void paintWindow(Graphics g, Image image, int x, int y, boolean isV) {
        if (image == null) {

            return;
        }

        if (nWindowTimer > nWindowTimerMax) {
            g.drawImage(image, x, y, Graphics.LEFT | Graphics.TOP);
            return;
        } else {
            // System.out.println("kkkk : " + nWindowTimer);
            nWindowTimer++;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        if (isV) {
            for (int i = 0; i <= nWindowHeightPer; i++) {
                int t_h = height / nWindowHeightPer;
                int t_h2 = ((nWindowTimer + nWindowHeightPer)
                        * nWindowHeightPer << 4)
                        / nWindowTimerMax;
                t_h2 >>= 4;
                g.setClip(x, y + i * t_h, width, t_h2);
                g.drawImage(image, x, y, Graphics.LEFT | Graphics.TOP);
            }
        } else {
            for (int i = 0; i <= nWindowHeightPer; i++) {
                int t_h = width / nWindowHeightPer;
                int t_h2 = ((nWindowTimer + nWindowHeightPer)
                        * nWindowHeightPer << 4)
                        / nWindowTimerMax;
                t_h2 >>= 4;
                g.setClip(x + i * t_h, y, t_h2, height);
                g.drawImage(image, x, y, Graphics.LEFT | Graphics.TOP);
            }
        }
        g.setClip(0, 0, Consts.SN_SCREEN_WIDTH, Consts.SN_SCREEN_HEIGHT);

    }

    public void initWindowShow(int timerMax, int windowsPerPage) {
        nWindowTimer = 0;
        nWindowTimerMax = timerMax;
        nWindowHeightPer = windowsPerPage;
    }
    //
    int nTitleTIMER = 0;

    //
    public String[] getFileNames(String fileName) {
        String[] strs = null;
        try {

            // 文件名,长度,坐标:)

            DataInputStream dis = new DataInputStream(this.getClass().getResourceAsStream(fileName));
            //
            int length = dis.readInt();
            // System.out.println("length " + length);
            String[] strName = new String[length];
            int fileDataLength[] = new int[length];
            byte[][] bytes2d = new byte[length][];
            //
            //
            for (int i = 0; i < length; i++) {
                strName[i] = dis.readUTF();
                fileDataLength[i] = dis.readInt();
                bytes2d[i] = new byte[fileDataLength[i]];

            }
            strs = strName;
            dis.close();
            return strs;

        } catch (Exception ex) {
        }

        return strs;
    }

    public Image[] getImages(String fileName) {
        Image[] image1d = null;
        try {

            // 文件名,长度,坐标:)

            DataInputStream dis = new DataInputStream(this.getClass().getResourceAsStream(fileName));
            //
            int length = dis.readInt();
            image1d = new Image[length];
            // System.out.println("length " + length);
            String[] strName = new String[length];
            int fileDataLength[] = new int[length];
            byte[][] bytes2d = new byte[length][];
            for (int i = 0; i < length; i++) {
                strName[i] = dis.readUTF();
                fileDataLength[i] = dis.readInt();
                bytes2d[i] = new byte[fileDataLength[i]];
            }
            // OK

            for (int i = 0; i < length; i++) {
                dis.read(bytes2d[i]);
                image1d[i] = Image.createImage(bytes2d[i], 0, bytes2d[i].length);
            }

            //
            dis.close();
            return image1d;

        } catch (Exception ex) {
        }

        return image1d;
    }

    // /
    public Image getImage(Image[] images, String str1d[], String strGet) {
        if (images == null) {
            Consts.log("can't load " + strGet);
            return null;
        }
        int index = -1;
        for (int i = 0; i < str1d.length; i++) {
            if (str1d[i].equals(strGet)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Consts.log("can't load " + strGet);
        } else {
            return images[index];
        }
        return null;
    }
} // class
