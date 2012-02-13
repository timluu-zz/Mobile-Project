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
 * @version 1.3.0
 *
 * <p> Revise History
 *
 * 2004.07.12 Revise exception mode and exception description V1.1.0
 *
 * 2004.07.20 Change audio loop mode to thread mode v1.2.0
 *
 * 2004.07.20 revise mode to test pass mode V1.2.1
 *
 * 2004.09.25 add a method replay media,add two data member to load resource,
 * add a method to load resource, v1.3.0 </p>
 */
package lipeng;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class LPAudioPlayer {

    private Player player;
    private String filename;
    private String format;

    public LPAudioPlayer(String filename, String format, boolean isLoad) {
        this.format = format;
        this.filename = filename;
        if (isLoad) {
            loadResource();
        }
    }

    public LPAudioPlayer(String filename, String format) {
        this.format = format;
        this.filename = filename;
    }

    public void loadResource() {
        try {
            InputStream is = getClass().getResourceAsStream("/" + filename);
            player = Manager.createPlayer(is, format);
        } catch (IOException ex) {
            System.out.println("can't load " + filename);
            System.out.println(ex.toString());
        } catch (MediaException ex) {
            System.out.println("can't create audio");
            System.out.println(ex.toString());
        }
    }

    public void setLoop() {
        if (player != null) {
            player.setLoopCount(-1);
        }
    }

    public void stop() {
        if (player != null) {
            try {
                player.stop();
            } catch (MediaException ex) {
                System.out.println("can't stop audio");
                System.out.println(ex.toString());
            }
        }
    }

    public void play() {
        if (player != null) {
            try {
                player.realize();
                player.start();
            } catch (MediaException ex) {
                System.out.println("can't play audio");
                System.out.println(ex.toString());
            }
        }
    }

    public void replay() {
        close();
        System.gc();
        loadResource();
        play();
    }

    public void close() {
        if (player != null) {
            player.close();
            player = null;
        }
    }
}
