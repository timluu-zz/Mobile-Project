/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Class Resource nằm trong package Common
 * Class cung cấp nguồn ảnh, nguồn nhạc cho game
 */
package ClassFrame;

import GamePlay.CanvasGame;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.media.MediaException;

/**
 *
 * @author QuyetNM1
 */
public class Resource {

    private CanvasGame cParent;
    //Color
    public static final int c_WHITE = 0xffffff;
    public static final int c_BG = 0xFFFFFF;
    public static final int c_Menu = 0xFFFFFF;
    public static final int c_Focus = 0x000000;
    // fonts
    public static final Font f0 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    public static final Font f_NORMAL = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    public static final Font f_MENU = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
    public static final Font f_BOLD = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
    public static final Font f_ITALIC = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_LARGE);
    public static final Font f_BOLD_ITALIC = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD | Font.STYLE_ITALIC, Font.SIZE_LARGE);
    public static final Font f_UNDERLINE = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_LARGE);
    public static final Font bigBoldFont = Font.getFont(Font.FONT_STATIC_TEXT, Font.STYLE_BOLD, Font.SIZE_LARGE);
    // images
    private static int count = 0;
    //Splash
    public static final int IMG_SPLASH = count++;
    public static final int IMG_TXT_SOUND = count++;
    //menu
    public static final int IMG_BG_MENU = count++;
    public static final int IMG_BG_OTHER = count++;
    public static final int IMG_TXT_HIGHSCORE = count++;
    public static final int IMG_NUMBER = count++;
    public static final int IMG_TXT_SETTING = count++;
    public static final int IMG_VOLUME = count++;
    public static final int IMG_SEEK_BAR = count++;
    public static final int IMG_CHANGE = count++;
    public static final int IMG_TXT_GUIDE = count++;
    public static final int IMG_GUIDE = count++;
//    public static final int IMG_TXT_ABOUT           = count++;
    public static final int IMG_ABOUT = count++;
    public static final int IMG_BACK = count++;
    public static final int IMG_EXIT = count++;
    public static final int IMG_SPL = count++;
    //GamePlay
    public static final int IMG_MAIN = count++;
    public static final int IMG_MAIN2 = count++;
    public static final int IMG_MAIN3 = count++;
    public static final int IMG_CHAIEN = count++;
    public static final int IMG_CHAIEN_FAIL = count++;
    public static final int IMG_CHAIEN_SING = count++;
    public static final int IMG_RABBIT = count++;
    public static final int IMG_NOBITA_FAIL = count++;
    public static final int IMG_ITEM = count++;
    public static final int IMG_ITEM1 = count++;
    public static final int IMG_GROUND = count++;
    public static final int IMG_CLOUD = count++;
    public static final int IMG_CLOUD_BREAK = count++;
    public static final int IMG_DIE = count++;
    public static final int IMG_NUMBERBLOOD = count++;
    public static final int IMG_BULLET = count++;
    //pause, next......
    public static final int IMG_BG_FRAME = count++;
//    public static final int IMG_FRAME               = count++;
    public static final int IMG_NEXT = count++;
    public static final int IMG_PAUSE = count++;
    public static final int IMG_WIN = count++;
    public static final int IMG_WIN_TEXT_BOX = count++;
    public static final int IMG_GAME_OVER = count++;
    public static final int IMG_GAME_OVER_TEXT_BOX = count++;
    //button
    public static final int IMG_BUTTON_MENU = count++;
    public static final int IMG_TXT_BUTTON_MENU = count++;
    public static final int IMG_TXT_BUTTON_MENU1 = count++;
//    public static final int IMG_BUTTONLT            = count++;
//    public static final int IMG_TXT_BUTTONLT        = count++;
//    public static final int IMG_BUTTON_PAUSE        = count++;
    public static final int IMG_SMALL_NUMBER = count++;
    //sound type
    private static final String MIDTYPE = "audio/midi";
    private static final String WAVTYPE = "audio/x-wav";
    private static final String MP3TYPE = "audio/mpeg";
    public static final String[] imgNames = {
        //Splash
        "/PNG/splash.gif",
        "/PNG/sound_txt.gif",
        //menu
        "/PNG/bg_menu.gif",
        "/PNG/bg_other.gif",
        "/PNG/txt_kyluc.gif",
        "/PNG/bo_so.gif",
        "/PNG/txt_thietlap.gif",
        "/PNG/am_luong.gif",
        "/PNG/am_thanh.gif",
        "/PNG/change.gif",
        "/PNG/txt_huongdan.png",
        "/PNG/huong_dan.gif",
        //                "/PNG/txt_gioithieu.png",
        "/PNG/about.gif",
        "/PNG/back.gif",
        "/PNG/thoat.png",
        "/PNG/spl.png",
        //GamePlay
        "/PNG/bg_play.gif",
        "/PNG/bg_play2.png",
        "/PNG/bg_play3.png",
        "/PNG/chaien.png",
        "/PNG/chaien_fail.png",
        "/PNG/chaiensing.png",
        "/PNG/rabbit.gif",
        "/PNG/nobitafail.png",
        "/PNG/item.png",
        "/PNG/item1.png",
        "/PNG/ground.gif",
        "/PNG/cloud.gif",
        "/PNG/cloud_break.gif",
        "/PNG/die.gif",
        "/PNG/so_mang.gif",
        "/PNG/bullet1.gif",
        //pause, next......
        "/PNG/frame/bg_frame.png",
        //                "/PNG/frame/frame.png",
        "/PNG/frame/next.png",
        "/PNG/frame/pause.gif",
        "/PNG/frame/win.png",
        "/PNG/frame/win_textbox.png",
        "/PNG/frame/game_over.gif",
        "/PNG/frame/game_over_textbox.png",
        //button
        "/PNG/button/button_menu.png",
        "/PNG/button/txt_butt_menu.gif",
        "/PNG/button/txt_butt_menu1.gif",
        //                "/PNG/button/buttonlt.png",
        //                "/PNG/button/txt_butt_lt.png",
        //                "/PNG/button/button_pause.png",

        "/PNG/small_num.gif"
    };
    //max imgs
    private static final int MAX_IMG = imgNames.length;
    private Image[] img; // image array
    private static final String[] soundsNames = {"/sound/menu.mid",
        "/sound/asuka.mid",
        "/sound/gameover.mid",
        "/sound/up.wav",
        "/sound/down.wav",
        "/sound/nextstage.mid",
        "/sound/fail.mid"
//        "/sound/win.mid"
    };
    //sounds
    public static final int SOUND_MENU = 0;
    public static final int SOUND_PLAY = 1;
    public static final int SOUND_GAME_OVER = 2;
    public static final int SOUND_MAN_UP = 3;
    public static final int SOUND_MAN_DOWN = 4;
    public static final int SOUND_NEXT_STAGE = 5;
    public static final int SOUND_FAIL = 6;
//    public static final int SOUND_WIN           = 7;
    public static final int MAX_SOUND = soundsNames.length;
    private Sound[] sounds;
    public static final int[] splashArrayImages = {IMG_SPLASH, IMG_TXT_SOUND};
    public static final int[] menuArrayImages = {IMG_BG_MENU, IMG_BUTTON_MENU, IMG_TXT_BUTTON_MENU, IMG_TXT_BUTTON_MENU1};
    public static final int[] guideArrayImages = {IMG_TXT_GUIDE, IMG_GUIDE};
    public static final int[] aboutArrayImages = {IMG_BG_OTHER, IMG_ABOUT, IMG_BACK};
    public static final int[] exitArrayImages = {IMG_SPL, IMG_SPLASH, IMG_EXIT};
    public static final int[] highScoreArrayImages = {IMG_BG_OTHER, IMG_TXT_HIGHSCORE, IMG_NUMBER, IMG_BACK};
    public static final int[] settingArrayImages = {IMG_BG_OTHER, IMG_TXT_SETTING, IMG_VOLUME, IMG_SEEK_BAR, IMG_CHANGE};
    public static final int[] gamePlayArrayImages = {IMG_SMALL_NUMBER, IMG_CHAIEN, IMG_CHAIEN_FAIL, IMG_CHAIEN_SING,
        IMG_RABBIT, IMG_NOBITA_FAIL, IMG_ITEM, IMG_ITEM1,
        IMG_MAIN, IMG_MAIN2, IMG_MAIN3, IMG_GROUND, IMG_CLOUD, IMG_NUMBERBLOOD, IMG_BULLET, IMG_CLOUD_BREAK, IMG_DIE};
    public static final int[] pauseArrayImages = {IMG_PAUSE};
    public static final int[] gameoverArrayImages = {IMG_GAME_OVER, IMG_MAIN, IMG_SMALL_NUMBER, IMG_RABBIT, IMG_GROUND, IMG_CLOUD, IMG_CLOUD_BREAK, IMG_DIE};
    public static final int[] winArrayImages = {IMG_WIN, IMG_BG_FRAME};
    public static final int[] nextlevelArrayImages = {IMG_NEXT, IMG_BG_FRAME};
    public static final int[] TextBoxWinArrayImages = {IMG_BG_FRAME, IMG_WIN_TEXT_BOX};
    public static final int[] TextBoxGameOverArrayImages = {IMG_BG_FRAME, IMG_GAME_OVER_TEXT_BOX};
//    public final int[] lossArrayImages       = { IMG_LOSS, IMG_BG_MENU };

    //Contructor
    public Resource(CanvasGame _canvas) {
        this.cParent = _canvas;
        this.img = new Image[MAX_IMG];
        this.sounds = new Sound[MAX_SOUND];
    }

    //Lấy ảnh từ mảng Image
    public Image getIMG(int _code) {
        return img[_code];
    }

    /**
     * Load resources
     */
    public boolean load(int i) {
        /* TODO: load the images here */
        try {
            img[i] = Image.createImage(imgNames[i]);
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return false;
        }
        return true;
    }

    /**
     * Unload resources
     */
    public void unLoad(int i) {
        /* TODO: make sure the object get's destroyed */
        img[i] = null;
    }

    public void loadArray(int[] imgs) {
        if (imgs == null) {
            return;
        }
        for (int i = 0; i < imgs.length; i++) {
            load(imgs[i]);
        }
    }

    public void unLoadArray(int[] imgs) {
        if (imgs == null) {
            return;
        }
        for (int i = 0; i < imgs.length; i++) {
            unLoad(imgs[i]);
        }
    }

    public void loadSounds() {
        for (int i = 0; i < MAX_SOUND; i++) {
            loadSound(i);
        }
    }

    public void unLoadSounds() {
        for (int i = 0; i < MAX_SOUND; i++) {
            unLoadSound(i);
        }
    }

    private void loadSound(int i) {
//        Sound sound = null;
        if (sounds[i] != null) {
            return;
        }
        try {
            String abc = soundsNames[i].substring(soundsNames[i].length() - 3, soundsNames[i].length());
            if (abc.equals("mid")) {
                sounds[i] = Sound.createSound(getClass().getResourceAsStream(soundsNames[i]), MIDTYPE);
            } else if (abc.equals("wav")) {
                sounds[i] = Sound.createSound(getClass().getResourceAsStream(soundsNames[i]), WAVTYPE);
            } else if (abc.equals("mp3")) {
                sounds[i] = Sound.createSound(getClass().getResourceAsStream(soundsNames[i]), MP3TYPE);
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void unLoadSound(int i) {
        if (sounds[i] != null) {
            sounds[i].release();
            sounds[i] = null;
        }

    }

    public void playSound(int name, int repeat) {
        try {
            String abc = soundsNames[name].substring(soundsNames[name].length() - 3, soundsNames[name].length());
            if (abc.equals("mid")) {
                sounds[name] = null;
            }
            loadSound(name);
            if (abc.equals("mid")) {
                sounds[name].setVolumeLevel(CanvasGame.musicLevel);
            } else if (abc.equals("wav")) {
                if (CanvasGame.musicLevel >= 50) {
                    sounds[name].setVolumeLevel(CanvasGame.musicLevel / 3);
                } else {
                    sounds[name].setVolumeLevel(CanvasGame.musicLevel / 7);
                }
            }

            sounds[name].repeat(repeat);
            sounds[name].start();

        } catch (MediaException ex) {
            System.out.println(ex.toString() + "3");
        }

    }

    public Sound getSound(int code) {
        return sounds[code];
    }

    public void setSounds(Sound sounds, int code) {
        this.sounds[code] = sounds;
    }
}
