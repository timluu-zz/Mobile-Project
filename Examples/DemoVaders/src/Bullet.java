
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Bullet extends Sprite {

    protected static final int RAW_FRAMES = 1;
    protected int xPos;
    protected int yPos;
    private static VaderManager layerManager;
    private static VaderCanvas gameCanvas;

    public Bullet(Image image, int width, int height, int xPos, int yPos) {
        super(image, width, height);
        this.yPos = yPos;
        this.xPos = xPos;
        gameCanvas = VaderManager.getGameCanvas();
        setPosition(xPos, yPos);
    }

    public static void setLayerManager(VaderManager manager) {
        layerManager = manager;
    }

    public void setY(int y) {
        this.setPosition(this.getX(), this.getY() + y);
    }

    private void up() {
        move(0, -5);
        if (this.getY() < 0) {
            layerManager.removeLayer(this);
        }
    }

    public void tick() {
        up();
    }
}