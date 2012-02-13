/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Class Constant nằm trong package Comment
 * Class dùng để định nghĩa tọa độ của các dòng chữ, các ảnh, định nghĩa chiều
 * dài chiều rộng của ảnh, string...
 */
package ClassFrame;

import javax.microedition.lcdui.Font;

/**
 *
 * @author QuyetNM1
 */
public class Constant {
    public static final Font    fontButton                = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    public static final int SCR_W = 320;
    public static final int SCR_H = 240;
    public static final int SCR_X = 320;
    public static final int SCR_Y = 240;
    //splash
    public static final int ti_DELAY_SPLASH = 2000;
    public static final int txt_sound_x = 60;
    public static final int txt_sound_y = 72;
//    public static final int txt_yes_x = 8;
//    public static final int txt_yes_y = 282;
//    public static final int txt_no_x = 369;
//    public static final int txt_no_y = 282;

//    public static final int buttonlt_w = 102;
//    public static final int buttonlt_h = 32;
    
    //menu    
    public static final int bg_other_y = 0;
    public static final int menu_x = 0;
    public static final int menu_y = 0;
    public static final int menu1_x = 115;
    public static final int menu1_y = 80;
    public static final int menu2_x = 95;
    public static final int menu2_y = 70;
//    public static final int menu_w = 170;
//    public static final int menu_h = 30;
    public static final int menu_d = 21;    
//    public static final int buttBack_x = 344;
//    public static final int buttBack_y = 254;
//    public static final int buttAgree_x = 34;
//    public static final int buttAgree_y = 254;
    //HighScore
    public static final int txt_score_y = 44;
    public static final int score_stt_x = 80;
    public static final int score_name_x = 100;
    public static final int score_number_x = 200;
    public static final int score_y = 92;
    public static final int score_d = 20;
    //Setting
    public static final int MAX_VOLUME = 100;
    public static final int MIN_VOLUME = 0;
    public static final int DEFAULT_VOLUME = 40;
    public static final int txt_setting_y = 45;
    public static final int seek_bar_x = 55;
    public static final int seek_bar_y = 120;
    public static final int seek_bar_d = 130;
    public static final int sound_x = 130;
    public static final int sound_y = 120;
//    public static final int guide_y = 90;
    //guide
    public static final int txt_guide_y = 10;
    public static final int guide_y = 0;
    //about
    public static final int txt_about_y = 10;
    public static final int about_y = 0;
    //exit
    public static final int ti_DELAY_EXIT = 2000;
    public static final int txt_exit_x = 48;
    public static final int txt_exit_y = 72;
    //pause, next, win, game_over
    public static final int frame_x = 30;
    public static final int frame_y = 50;
    public static final int buttFrameRight_x = 328;
    public static final int buttFrameRight_y = 234;
    public static final int buttFrameLeft_x = 52;
    public static final int buttFrameLeft_y = 234;
    public static final int txt_frame_x = 60;
    public static final int txt_frame_y = 80;

    //GamePlay
    public static final int ti_DELAY_WIN = 40;
    public static final int score_bonus = 50;
    public static final int score_bonus_congra = 1000;
    public static final int score_loss = 200;
    public static final int buttPause_x = 373;
    public static final int buttPause_y = 294;
    public static final int buttPause_w = 92;
    public static final int buttPause_h = 26;
//    public static final int screenSizeW = 270;
    public static final int firstPositionH = 175;
    public static final int firstPositionW = 130;
    public static final int sizeStep = 12;  
    public static final int sizeJump = 20;

    //Text box
    public static final int tbox_score_x = 134;
    public static final int tbox_score_y = 51;
    public static final int tbox_name_x = 106;
    public static final int tbox_name_y = 78;
}
