
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Player extends Sprite {

    protected static final int RAW_FRAMES = 1;
    protected static int xPos;
    protected static int yPos;
    protected static VaderCanvas gameCanvas;
    protected static VaderManager layerManager;

    public Player(Image image, int width, int height, int xPos, int yPos) {
        super(image, width, height);
        Player.yPos = yPos;
        Player.xPos = xPos;
        setPosition(xPos, yPos);
        gameCanvas = VaderManager.getGameCanvas();
    }

    public static void setLayerManager(VaderManager manager) {
        layerManager = manager;
    }

    public void setY(int y) {
        setPosition(this.getX(), this.getY() + y);
    }

    public void left() {
        if (this.getX() > 0) {
            move(-2, 0);
        }

        // TO DO create a sense of momentum for the UIQ jog dial input method.


    }

    public void right() {
        if ((this.getX() + getWidth()) <= gameCanvas.getWidth()) {
            move(2, 0);
        }

        // TO DO create a sense of momentum for the UIQ jog dial input method.
    }

    public void fire() {
        layerManager.initBullets();
    }
}