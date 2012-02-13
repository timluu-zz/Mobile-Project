
/**
 * <p> Title: </p> <p> Description: </p> <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: </p>
 *
 * 非作者授权，请勿用于商业用途。
 *
 * @author bruce.fine@gmail.com
 * @version 1.0
 */
public class Consts {

    // 编译参数区域
    // *****************************************************
    /**
     * 是否进行调试
     */
    static final boolean SIS_DEBUG = true;
    static final boolean SIS_LOG = false;
    /**
     * 是否显示FPS
     */
    static final boolean SIS_SHOW_FPS = true;
    // 键盘定制区域
    // *****************************************************
    // public static final byte DIR_UP = Canvas.UP;
    //
    // public static final byte DIR_DOWN = Canvas.DOWN;
    //
    // public static final byte DIR_LEFT = Canvas.LEFT;
    //
    // public static final byte DIR_RIGHT = Canvas.RIGHT;
    public static final byte DIR_NULL = -1;
    /**
     * NOKIA键盘模式
     */
    static final byte SB_NOKIA_MODE = 1;
    /**
     * MOTO键盘模式
     */
    static final byte SB_MOTO_MODE = 2;
    /**
     * 当前模式
     */
    static final byte SB_CURRENT_PHONE_MODE = SB_NOKIA_MODE;
    /**
     * 左软键
     */
    static final int SN_KEY_LEFT_SOFT_KEY = -6;
    /**
     * 右软键
     */
    static final int SN_KEY_RIGHT_SOFT_KEY = -7;
    // 逻辑区域
    // *****************************************************
    /**
     * 最大关卡控制, MAX - 1
     */
    static final int SN_ABS_MAX_LEVEL = 8; //
    /**
     * 画面的绘图间隔时间
     */
    static final int SN_MILLIS_PER_TICK = 100;
    /**
     * SPLASH的时间长度
     */
    static final int SN_SPLASH_TIMER_MAX = 100;
    // 地图控制
    // *****************************************************
    static final int SN_TILE_WIDTH = 16;
    static final int SN_TILE_HEIGHT = 16;
    // 用来计算的值
    static final int SN_TILE_WIDTH_CALC = 16 * 10;
    static final int SN_TILE_HEIGHT_CALC = 16 * 10;
    // 屏幕控制区域
    // *****************************************************
    static final int SN_SCREEN_WIDTH = 128;
    /**
     * 屏幕实际高度
     */
    static final int SN_SCREEN_HEIGHT = 128;
    /**
     * 窗口绘制左上X起点
     */
    static final int SN_WINDOW_START_X = 0;
    /**
     * 窗口绘制左上Y起点
     */
    static final int SN_WINDOW_START_Y = ((SN_SCREEN_HEIGHT % SN_TILE_HEIGHT) == 0) ? 0
            : -((SN_TILE_HEIGHT - (SN_SCREEN_HEIGHT % SN_TILE_HEIGHT)));
    /**
     * 窗口宽度
     */
    static final int SN_WINDOW_WIDTH = SN_SCREEN_WIDTH;
    /**
     * 窗口高度
     */
    static final int SN_WINDOW_HEIGHT = ((SN_SCREEN_HEIGHT % SN_TILE_HEIGHT) == 0) ? SN_SCREEN_HEIGHT
            : SN_SCREEN_HEIGHT
            + (SN_TILE_HEIGHT - (SN_SCREEN_HEIGHT % SN_TILE_HEIGHT));
    static final String SSTR_RMS_NAME = "GAOGAO";
    // COLOR列表
    // *****************************************************
    static final int COLOR_BLACK = 0X000000;
    static final int COLOR_WHITE = 0XFFFFFF;
    static final int COLOR_GBLUE = 0X00CCFF;

    // /
    /**
     * 调试信息输出
     */
    public static final void log(String str) {
        if (SIS_LOG) {
            System.out.println(str);
        }
    }
    // TILE //控制
    // *****************************************************
    /**
     * 地图TILE的类型
     */
    public static final byte SB_TILE_NULL = 0;
    public static final byte SB_TILE_BRICK = 1;
    public static final byte SB_TILE_STEEL = 2;

    // public static final byte SB_TILE_SCORE = 2;
    //
    // public static final byte SB_TILE_DANGER = 3;
    //
    // public static final byte SB_TILE_FLAG = 4;
    public static byte getTileVar(short id) {
        switch (id) {
            case 10:
            case 15:
            case 30: {
                return SB_TILE_STEEL;
            }
            case 20:

            case 5: {
                return SB_TILE_BRICK;
            }
            default:
                break;
        }
        return SB_TILE_NULL;
    }

    //
    public static boolean isOutOfMap(int nAbsX, int nAbsY) {
        if (nAbsX < 0 || nAbsY < 0 || nAbsX > MyGameCanvas.snMapWidth
                || nAbsY > MyGameCanvas.snMapHeight) {
            return true;
        } else {
            return false;
        }
    }

    //
    public static boolean isOutOfTiles(int nTileX, int nTileY) {
        if (nTileX < 0 || nTileY < 0 || nTileX >= MyGameCanvas.snTilesWidthNum
                || nTileY >= MyGameCanvas.snTilesHeightNum) {
            return true;
        } else {
            return false;
        }
    }

    //
    public static boolean isBesideOfScreen(int x, int y, int width, int height) {
        if (x + width < 0 || y + height < 0 || x > SN_WINDOW_WIDTH
                || y > SN_WINDOW_HEIGHT) {
            return false;
        } else {
            return true;
        }
    }
    // 滚屏控制
    // *****************************************************
    /**
     * 矩形可视区域左边
     */
    public static final int SN_VIEW_RECT_LEFT = (Consts.SN_TILE_WIDTH << 1);
    /**
     * 矩形可视区域右边
     */
    public static final int SN_VIEW_RECT_RIGHT = Consts.SN_WINDOW_WIDTH
            - (Consts.SN_TILE_WIDTH * 3);
    /**
     * 矩形可视区域上边
     */
    public static final int SN_VIEW_RECT_UP = (Consts.SN_TILE_HEIGHT * 2);
    // /**
    // * 矩形可视区域下边
    // */
    public static final int SN_VIEW_RECT_DOWN = Consts.SN_WINDOW_HEIGHT
            - (Consts.SN_TILE_HEIGHT * 3);
    //
    public static final int SN_VIEW_RECT_RIGHT_STOP = (Consts.SN_TILE_WIDTH << 1)
            + Consts.SN_TILE_WIDTH;
    public static final int SN_VIEW_RECT_LEFT_STOP = Consts.SN_WINDOW_WIDTH
            - (Consts.SN_TILE_WIDTH * 4);
    public static final int SN_VIEW_RECT_UP_STOP = (Consts.SN_TILE_HEIGHT << 1)
            + Consts.SN_TILE_HEIGHT;
    public static final int SN_VIEW_RECT_DOWN_STOP = Consts.SN_WINDOW_HEIGHT
            - (Consts.SN_TILE_HEIGHT * 4);
    //
    // 菜单参数控制
    // *****************************************************
    public static final int[] SN_MENU_STR_IN_GAME_Y_POS = {40, 60, 80, 100};
    //
    public static final int SN_MENU_IN_GAME_Y_POS_START = 50;
    //
    public static final int SN_MENU_IN_GAME_RECT_HEIGHT = 60;
    // 其他参数列表
    // 文字区域
    // *****************************************************
    public static final String STR_MIDLET_NAME = "梦幻炸弹人";
    /**
     * 载入画面提示字符
     */
    static final String SSTR_LOADING = "请稍等";
    // 故事窗口
    static final int SN_TXT_STORY_WIN_Y = 30;
    static final int SN_TXT_STORY_WIN_HEIGHT = SN_SCREEN_HEIGHT
            - (SN_TXT_STORY_WIN_Y << 1);
}
