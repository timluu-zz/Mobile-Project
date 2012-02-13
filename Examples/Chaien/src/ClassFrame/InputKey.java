/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Class InputKey nằm trong package Common
 * Class này dùng để map key và định nghĩa các key trong điện thoại
 * Output ra giá trị boolean khi ấn các nút
 */
package ClassFrame;

import GamePlay.CanvasGame;



/**
 *
 * @author QuyetNM1
 */
public class InputKey {

    CanvasGame canvas;
/**
 * a key is released.
 */
    public final static int KEY_EVENT_UP = 1;

/**
 * a key is pressed.
 */
    public final static int KEY_EVENT_DOWN = 2;

    public final static int POINTER_EVENT_UP = 3;

    public final static int POINTER_EVENT_DOWN = 4;

    public final static int POINTER_EVENT_DRAG = 5;
/**
 * Key long press event.
 */
//public final static int KEY_EVENT_LONG_PRESS = 3;

/**
 * Key press repeat event.
 */
//public final static int KEY_EVENT_REPEAT = 4;
/** @} */

    public final static int KEY_UP                                  = -1;
    public final static int KEY_DOWN                                = -2;
    public final static int KEY_LEFT                                = -3;
    public final static int KEY_RIGHT                               = -4;
    public final static int KEY_LEFT_SOFTKEY                        = -6;
    public final static int KEY_RIGHT_SOFTKEY                       = -7;
    public final static int KEY_OK                                  = -5;
    public final static int KEY_CLEAR                               = 8;
    public final static int KEY_BACK                                = -9;
    public final static int VOLUME_UP                               = -13;
    public final static int VOLUME_DOWN                             = -14;
    

    public final static int KEY_NUM0                                = 48;
    public final static int KEY_NUM1                                = 49;
    public final static int KEY_NUM2                                = 50;
    public final static int KEY_NUM3                                = 51;
    public final static int KEY_NUM4                                = 52;
    public final static int KEY_NUM5                                = 53;
    public final static int KEY_NUM6                                = 54;
    public final static int KEY_NUM7                                = 55;
    public final static int KEY_NUM8                                = 56;
    public final static int KEY_NUM9                                = 57;

    /**
     * "#" key.
     */
    public final static int KEY_POUND                               = 35;

    /**
     * "*" key.
     */
    public final static int KEY_STAR                                = 42;

    public final static int KEY_A                                   = 65;
    public final static int KEY_B                                   = 66;
    public final static int KEY_C                                   = 67;
    public final static int KEY_D                                   = 68;
    public final static int KEY_E                                   = 69;
    public final static int KEY_F                                   = 70;
    public final static int KEY_G                                   = 71;
    public final static int KEY_H                                   = 72;
    public final static int KEY_I                                   = 73;
    public final static int KEY_J                                   = 74;
    public final static int KEY_K                                   = 75;
    public final static int KEY_L                                   = 76;
    public final static int KEY_M                                   = 77;
    public final static int KEY_N                                   = 78;
    public final static int KEY_O                                   = 79;
    public final static int KEY_P                                   = 80;
    public final static int KEY_Q                                   = 81;
    public final static int KEY_R                                   = 82;
    public final static int KEY_S                                   = 83;
    public final static int KEY_T                                   = 84;
    public final static int KEY_U                                   = 85;
    public final static int KEY_V                                   = 86;
    public final static int KEY_W                                   = 87;
    public final static int KEY_X                                   = 88;
    public final static int KEY_Y                                   = 89;
    public final static int KEY_Z                                   = 90;
    public final static int KEY_SPACE                               = 32;
    public final static int KEY_ENTER                               = 10;
    public final static int KEY_a                                   = 97;
    public final static int KEY_b                                   = 98;
    public final static int KEY_c                                   = 99;
    public final static int KEY_d                                   = 100;
    public final static int KEY_e                                   = 101;
    public final static int KEY_f                                   = 102;
    public final static int KEY_g                                   = 103;
    public final static int KEY_h                                   = 104;
    public final static int KEY_i                                   = 105;
    public final static int KEY_j                                   = 106;
    public final static int KEY_k                                   = 107;
    public final static int KEY_l                                   = 108;
    public final static int KEY_m                                   = 109;
    public final static int KEY_n                                   = 110;
    public final static int KEY_o                                   = 111;
    public final static int KEY_p                                   = 112;
    public final static int KEY_q                                   = 113;
    public final static int KEY_r                                   = 114;
    public final static int KEY_s                                   = 115;
    public final static int KEY_t                                   = 116;
    public final static int KEY_u                                   = 117;
    public final static int KEY_v                                   = 118;
    public final static int KEY_w                                   = 119;
    public final static int KEY_x                                   = 120;
    public final static int KEY_y                                   = 121;
    public final static int KEY_z                                   = 122;

    public final static int KEY_EXCLAMATION                         = 33;// !  exclamation mark
    public final static int KEY_DOUBLE_APOSTROPHE                   = 34;// "  double quote
    public final static int KEY_HASH                                = 35;// #  hash
    public final static int KEY_DOLLAR                              = 36;// $  dollar
    public final static int KEY_PERCENT                             = 37;// %  percent
    public final static int KEY_APOSTROPHE                          = 39;// ' quote
    public final static int KEY_OPEN_PARENTHESIS                    = 40;// (  open parenthesis
    public final static int KEY_CLOSE_PARENTHESIS                   = 41;// )  close parenthesis
    public final static int KEY_ASTERISK                            = 42;// *  asterisk
    public final static int KEY_PLUS                                = 43;// +  plus
    public final static int KEY_COMMA                               = 44;// ,  comma
    public final static int KEY_MINUS                               = 45;// -  minus
    public final static int KEY_PERIOD                              = 46;// .  full stop
    public final static int KEY_OBLIQUE_STROKE                      = 47;// /  oblique stroke
    public final static int KEY_COLON                               = 58;// :  colon
    public final static int KEY_SEMICOLON                           = 59;// ;  semicolon
    public final static int KEY_QUESTION                            = 63;// ?  question mark
    public final static int KEY_AT                                  = 64;// @  commercial at
    public final static int KEY_OPEN_SQUARE                         = 91;// [  open square bracket
    public final static int KEY_BACKSLASH                           = 92;// \  backslash
    public final static int KEY_CLOSE_SQUARE                        = 93;// ]  close square bracket
    public final static int KEY_UNDERSCORE                          = 95;// _  underscore
    public final static int KEY_OPEN_CURLY                          = 123;// {  open curly bracket
    public final static int KEY_VERTICAL_BAR                        = 124;// |  vertical bar
    public final static int KEY_CLOSE_CURLY                         = 125;// }  close curly bracket

    public static final int KEY_DELAY = 250;

    private int keyPressed = 0;
    private int keyReleased = 0;
    
    public InputKey(CanvasGame _canvas) {
        this.canvas = _canvas;
    }

    private int mapKeys(int code) {
        int ret = 0;
//        switch (code) {
//            case -5:
//                ret = FIRE;
//                break;
//            case -3:
//                ret = LEFT;
//                break;
//            case -4:
//                ret = RIGHT ;
//                break;
//            case -1:
//                ret = UP;
//                break;
//            case -2:
//                ret = DOWN;
//                break;
//            case -6:
//                ret = LEFTSOFT;
//                break;
//            case -7:
//                ret = RIGHTSOFT;
//                break;
//            case 52:
//                ret = NUM4;
//                break;
//            case 53:
//                ret = NUM5;
//                break;
//            case 54:
//                ret = NUM6;
//                break;
//            case 83:
//                ret = KEYSU;
//                break;
//            case 68:
//                ret = KEYDU;
//                break;
//            case 70:
//                ret = KEYFU;
//                break;
//            case 115:
//                ret = KEYSD;
//                break;
//            case 100:
//                ret = KEYDD;
//                break;
//            case 102:
//                ret = KEYFD;
//                break;
//        }
        return ret;
    }

    public void keyPressed(int keyCode) {
//        keyPressed = mapKeys(keyCode);
    }

    public void keyReleased(int keyCode) {
//        keyReleased = mapKeys(keyCode);
    }

    public boolean isRelease(int keyCode) {
        return keyReleased == keyCode;
    }

//    public boolean isPress(int keyCode) {
//        return (keyPressed == keyCode && !CanvasGame.isPause);
//    }

    public boolean isHold(int keyCode) {
//        return keyState[keyCode] == HOLD;
        return false;
    }

//    public boolean isPressOrHold(int keyCode) {
//        return (isPress(keyCode) || isHold(keyCode));
//    }
}
