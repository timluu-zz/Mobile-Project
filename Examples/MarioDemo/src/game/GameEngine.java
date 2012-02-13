package game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author joteitti
 */
public class GameEngine {

    public static final int STATE_NONE = 0;
    public static final int STATE_MENU = 1;
    public static final int STATE_PLAY = 2;
    //public static final String DEFAULT_MUSIC_ENCODING = "audio/mpeg";
    //public static final String DEFAULT_MUSIC_FILENAME = "/Mario.mp3";
    //public static final int DEFAULT_MUSIC_VOLUME_LEVEL = 35;
    private int mState;
    private GameMenu mMenu;
    private GameScene mScene;
    private static Player smMusic;
    //private static int smMusicVolume = DEFAULT_MUSIC_VOLUME_LEVEL;

    public GameEngine() {
        // Create states
        mScene = new GameScene();
        mMenu = new GameMenu();
    }

    public void initialize(boolean skipMenu) {
        // TODO Initialize game engine
        GameResources.initialize();

        // Load theme music
        /*
         * try { // Get create media player InputStream inputStream =
         * getClass().getResourceAsStream(DEFAULT_MUSIC_FILENAME); smMusic =
         * Manager.createPlayer(inputStream, DEFAULT_MUSIC_ENCODING);
         *
         * // Reduce play latency and set looping smMusic.prefetch();
         * smMusic.setLoopCount(-1);
         *
         * // Set volume level setMusicVolume(smMusicVolume); } catch
         * (IOException e) { System.out.println("Failed to open music resource:
         * " + e.getMessage()); } catch (MediaException e) {
         * System.out.println("Failed to create music player: " +
         * e.getMessage()); }
         *
         */


        // Start from menu
        if (skipMenu) {
            changeState(STATE_PLAY);
        } else {
            changeState(STATE_MENU);
        }
    }

    public void changeState(int newState) {
        if (newState == STATE_MENU) {
            // Initialize menu state
            mMenu.initialize();
        } else if (newState == STATE_PLAY) {
            // Initialize play state
            mScene.initialize(newState != mState);
        }

        // Reset music
        resetMusic();

        mState = newState;
    }

    public int logicUpdate(int deltaTime) {
        int event = Game.EVENT_NONE;
        if (mState == STATE_MENU) {
            event = mMenu.logicUpdate(deltaTime);
        } else if (mState == STATE_PLAY) {
            event = mScene.logicUpdate(deltaTime);
        }

        // Handle events
        if (event == Game.EVENT_PLAY) {
            changeState(STATE_PLAY);
        } else if (event == Game.EVENT_MENU) {
            changeState(STATE_MENU);
        } else if (event == Game.EVENT_MENU) {
            changeState(STATE_PLAY);
        } else if (event == Game.EVENT_SHUTDOWN) {
            return Game.EVENT_SHUTDOWN;
        }

        return Game.EVENT_NONE;
    }

    public void draw(Graphics g) {
        if (mState == STATE_MENU) {
            mMenu.draw(g);
        } else if (mState == STATE_PLAY) {
            mScene.draw(g);
        }

        // TODO Draw game engine (score & time)
    }

    public static void startMusic() {
        if (smMusic != null) {
            try {
                smMusic.start();
            } catch (MediaException e) {
                System.out.println("Failed to play music: " + e.getMessage());
            }
        }
    }

    public static void stopMusic() {
        if (smMusic != null) {
            try {
                smMusic.stop();

                // Set time to zero
                smMusic.setMediaTime(0);
            } catch (MediaException e) {
                System.out.println("Failed to stop music: " + e.getMessage());
            }
        }
    }

    public static void resetMusic() {
        // Stop playing
        stopMusic();

        // Start playing
        startMusic();
    }

    public static void setMusicVolume(int volumeLevel) {
        // Set music player volume level
        if (smMusic != null) {
            VolumeControl volume = (VolumeControl) smMusic.getControl("VolumeControl");
            if (volume != null) {
                volume.setLevel(volumeLevel);
            }
        }

        // Update volume level
        //smMusicVolume = volumeLevel;
    }
    /*
     * public static int getMusicVolume() { return smMusicVolume; }
     */
}
