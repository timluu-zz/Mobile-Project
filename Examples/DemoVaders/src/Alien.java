
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import java.util.Vector;

public class Alien extends Sprite {

    protected static final int RAW_FRAMES = 2;
    protected static final int START_FRAME = 0;
    protected static final int END_FRAME = 1;
    protected int xPos = 0;
    protected int yPos = 0;
    protected static int currentAlien = 0;
    protected static int rows = 2;
    protected static int cols = 3;
    protected static int colOffset = 3;
    protected static int rowOffset = 3;
    protected static int yOffset = 0;
    protected static VaderCanvas gameCanvas;
    protected static VaderManager layerManager;
    protected static boolean direction = true;
    protected static boolean isEnd = true;

    public Alien(Image image, int width, int height, int xPos, int yPos) {
        super(image, width, height);
        gameCanvas = layerManager.getGameCanvas();
        defineCollisionRectangle(getWidth() / 10, getHeight() / 10, getWidth() - (getWidth() / 10), getHeight() - (getHeight() / 10));
        setPosition(xPos, yPos);
    }

    public static void setDirection(boolean dir) {
        direction = dir;
    }

    public static void setEnd(boolean end) {
        isEnd = end;
    }

    public static int getRows() {
        return rows;
    }

    public static int getCols() {
        return cols;
    }

    public static void setCols(int c) {
        cols = c;
    }

    public static void setRows(int r) {
        rows = r;
    }

    public static void setLayerManager(VaderManager manager) {
        layerManager = manager;
    }

    public void setY(int y) {
        setPosition(getX(), getY() + y);
    }

    public void backwards() {
        prevFrame();
        move(-2, 0);
    }

    public void forwards() {
        nextFrame();
        move(2, 0);

    }

    private void setDirection() {
        if ((getX() + getWidth()) >= gameCanvas.getWidth()) {
            setEnd(false);
            //setYOffset();		
        } else if (getX() < 0) {
            setEnd(true);
            //setYOffset();
        }
    }

    private void setYOffset() {
        yOffset += 3;
        //setPosition(getX(), getY()+yOffset);
        //move(0,yOffset);	  	
    }

    public void move() {
        if (direction) {
            forwards();
        } else {
            backwards();
        }

    }

    public void tick() {
        currentAlien++;
        move();

        int aCount = layerManager.vtrAliens.size();
        if (currentAlien >= aCount) {
            currentAlien = 0;
            setDirection(isEnd);
            //setYOffset();
        }

        int bCount = layerManager.vtrBullets.size();
        Bullet bullet;

        for (int b = 0; b < bCount; b++) {
            bullet = (Bullet) layerManager.vtrBullets.elementAt(b);
            if (collidesWith(bullet, false)) {
                layerManager.removeLayer(this);
                layerManager.removeLayer(bullet);
                layerManager.setScore(layerManager.getScore() + 10);
                if (--bCount <= 0) {
                    break;
                }
            }
        }
        setDirection();
    }
}