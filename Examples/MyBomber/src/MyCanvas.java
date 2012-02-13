
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
public class MyCanvas extends javax.microedition.lcdui.Canvas {
//	 public class MyCanvas extends com.nokia.mid.ui.FullCanvas {
    // public class MyCanvas extends javax.microedition.lcdui.game.GameCanvas {
    //

    public int getKeyID(int ak) {

        int key = 0;
        if (ak == 0) {
            return 0;
        }
        if (Consts.SB_CURRENT_PHONE_MODE == Consts.SB_MOTO_MODE) {
            if (Math.abs(ak) == 22) {
                key = Consts.SN_KEY_LEFT_SOFT_KEY;
            } else if (Math.abs(ak) == 21) {
                key = Consts.SN_KEY_RIGHT_SOFT_KEY;
            } else {
                key = getGameAction(ak);
            }

        } else if (Consts.SB_CURRENT_PHONE_MODE == Consts.SB_NOKIA_MODE) {
            if (ak == -6) {
                key = Consts.SN_KEY_LEFT_SOFT_KEY;
            } else if (ak == -7) {
                key = Consts.SN_KEY_RIGHT_SOFT_KEY;
            } else {
                key = getGameAction(ak);
            }
        }
        return key;
    }

    public MyCanvas() {
        // super(false);
        // setFullScreenMode(true);
    } // public MyCanvas()

    public void paint(javax.microedition.lcdui.Graphics g) {
    } // void paint

    public void tick() {
    } // tick

    public void doRepaints(int x, int y, int width, int height) {
        repaint();
        serviceRepaints();
    }
}
