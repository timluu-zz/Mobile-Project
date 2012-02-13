/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;


import java.io.IOException;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

/**
 *
 * @author QuyetNM1
 */
public class Sound {
    public static final String controlType = "VolumeControl";

    private Player player;
    private VolumeControl vc;

    public Sound() {
    }
    public static Sound createSound(InputStream stream, String type) throws IOException, MediaException {
        Sound sound = new Sound();
        Player p = Manager.createPlayer(stream, type);
        p.prefetch();
        VolumeControl vc = (VolumeControl) p.getControl(controlType);
        sound.setPlayer(p);
        sound.setVolumeControl(vc);
//        sound.setVolumeLevel(Setting.MIN_VOLUME);
        return sound;
    }



    public void release() {
        vc = null;
        try {
            if (player != null) {
                if (player.getState() == Player.STARTED) {
                    player.stop();
                }
                if (player.getState() == Player.PREFETCHED) {
                    player.deallocate();
                }
                if (player.getState() == Player.REALIZED
                        || player.getState() == Player.UNREALIZED) {
                    player.close();
                }
                player = null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void start() throws MediaException{
        if(player != null) player.start();
    }

    public void stop(){
        try {
             if(player != null) player.stop();
//             start();
        } catch (MediaException ex) {
            System.out.println(ex.toString() + "3");
        }
//        if(player != null) player.stop();
    }

//    public void stopSound(){
//        try {
//             stop();
//             start();
//        } catch (MediaException ex) {
//            System.out.println(ex.toString() + "3");
//        }
////        if(player != null) player.stop();
//    }
    public void repeat(int code){
        player.setLoopCount(code);
    }

//    public void playSound(int repeat){
//        try {
//            if(repeat == -1)
//             repeat(repeat);
//             start();
//        } catch (MediaException ex) {
//            System.out.println(ex.toString() + "3");
//        }
//    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public VolumeControl getVc() {
        return vc;
    }

    public void setVolumeControl(VolumeControl vc) {
        this.vc = vc;
    }

    public int getVolumeLevel() {
        return vc.getLevel();
    }

    public void setVolumeLevel(int volumeLevel) {
        vc.setLevel(volumeLevel);
    }
}
