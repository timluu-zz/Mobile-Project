
import java.io.IOException;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

public class SoundPlayer implements PlayerListener {

    public String[] bgmList = {"/res/bgm1.mid", "/res/bgm2.mid", "/res/bgm3.mid"};
    public String vic = "/res/victory.mid";
    private Player player = null;
    private Player splayer = null;
    private boolean enable = false;
    private int curBgm = -1;

    public SoundPlayer() {
        try {
            splayer = Manager.createPlayer(getClass().getResourceAsStream(vic), "audio/midi");
        } catch (IOException ioe) {
        } catch (MediaException me) {
        }
    }

    public void setEnable(boolean e) {
        if (enable == e) {
            return;
        }
        enable = e;
        try {
            if (!enable) {
                if (player != null) {
                    player.stop();
                    enable = false;
                }
                return;
            } else {
                if (curBgm == -1) {
                    return;
                }
                if (player == null) {
                    player = Manager.createPlayer(getClass().getResourceAsStream(bgmList[curBgm]), "audio/midi");
                }
                player.start();
            }
        } catch (IOException ioe) {
        } catch (MediaException me) {
        }
    }

    public void playBgm(int bgm) {
        if (bgm == curBgm) {
            return;
        }
        curBgm = bgm;
        if (bgm == -1) {
            return;
        }
        if (!enable) {
            return;
        }
        try {
            if (player != null) {
                player.close();
                player = null;
            }
            player = Manager.createPlayer(getClass().getResourceAsStream(bgmList[curBgm]), "audio/midi");
            player.start();
        } catch (IOException ioe) {
//			System.out.println (ioe.getMessage());
        } catch (MediaException me) {
//			System.out.println (me.getMessage());
        }
    }

    public void playSE() {
        try {
            splayer.start();
        } catch (MediaException me) {
        }
    }

    public void stopSE() {
        if (splayer != null) {
            try {
                splayer.stop();
                splayer = null;
            } catch (MediaException me) {
            }
        }
    }

    public void playerUpdate(Player p, String s, Object o) {
//		if(p==splayer&&s.equals(END_OF_MEDIA)){
//			try{
//			splayer.stop();
//			notifyAll();
//			}catch(MediaException me){
//			}
//		}
    }

    public void setListener(PlayerListener pl) {
        splayer.addPlayerListener(pl);
    }
}
