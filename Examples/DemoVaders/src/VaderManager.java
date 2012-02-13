
/**
 * <p>Title: DemoRacer</p>
 *
 * <p>Description: Game API Demo for Symbian MIDP 2.0 Book.</p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: Symbian Ltd</p>
 *
 * @author Alan Newman - alan@sensibledevelopment.net
 * @version 1.0
 */
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;

public class VaderManager extends LayerManager {

    private BackGround backGround;
    private Alien alien;
    private Player player;
    private Barrier barrier;
    private Image imgBarrier;
    private int xWindow;
    private int yWindow;
    private static VaderCanvas gameCanvas;
    private int currentLayer;
    private int layers;
    protected Vector vtrAliens;
    protected Vector vtrBullets;
    protected Vector vtrBarriers;
    Font font;
    int fHeight = 0;
    private final String STR_OK = "OK";
    private String strLevelOver1 = "Level Over";
    private boolean isLevelOver = false;
    private String strScore = "Score: ";
    private int intScore = 0;
    public String strDebug = "";

    public VaderManager(VaderCanvas gameCanvas) {
        font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        fHeight = font.getHeight();

        this.gameCanvas = gameCanvas;
        vtrAliens = new Vector();
        vtrBullets = new Vector();
        vtrBarriers = new Vector();
        Alien.setLayerManager(this);
        Bullet.setLayerManager(this);
        Player.setLayerManager(this);
        Barrier.setLayerManager(this);
        backGround = createBackground();
        player = createPlayerSprite();
        initBarriers();
        append(player);
        initAliens();
        append(backGround);
    }

    public static VaderCanvas getGameCanvas() {
        return gameCanvas;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector getBarriers() {
        return vtrBarriers;
    }

    public void setScore(int s) {
        intScore = s;
    }

    public int getScore() {
        return intScore;
    }

    public boolean getLevelOver() {
        return isLevelOver;
    }

    public void setLevelOver(boolean isOver) {
        isLevelOver = isOver;
        gameCanvas.setRunning(false);
    }

    public void reDrawDisplay(int yOffset) {
        getPlayer().setY(yOffset);

        int bCount = vtrBarriers.size();
        Barrier barrier;
        for (int b = 0; b < bCount; b++) {
            barrier = (Barrier) vtrBarriers.elementAt(b);
            //barrier.setY(yOffset);
            barrier.move(0, yOffset);
        }
        int aCount = vtrAliens.size();
        Alien alien;
        for (int a = 0; a < aCount; a++) {
            alien = (Alien) vtrAliens.elementAt(a);
            alien.setY((int) yOffset / 2);
        }
        int blCount = vtrBullets.size();
        Bullet bullet;
        for (int bl = 0; bl < blCount; bl++) {
            bullet = (Bullet) vtrBullets.elementAt(bl);
            bullet.setY(yOffset);
        }
        remove(backGround);
        backGround = createBackground();
        append(backGround);
    }

    public void removeLayer(Layer layer) {
        if (layer.getClass().getName().equals("Alien")) {
            vtrAliens.removeElement(layer);
        } else if (layer.getClass().getName().equals("Bullet")) {
            vtrBullets.removeElement(layer);
        }

        layers--;
        currentLayer--;
        remove(layer);
    }

    public void tick() {
        // return the number of layers in the LayerManager.

        if (vtrAliens.size() == 0) {
            setLevelOver(true);
        }
        layers = getSize();

        // create a reference to the Alien and Bullet objects    
        Alien alien;
        Bullet bullet;

        // cycle through the layers until we find the ones 
        // we are interested in.    
        for (currentLayer = 0; currentLayer < layers; currentLayer++) {
            Layer layer = getLayerAt(currentLayer);
            if (layer.getClass().getName().equals("Alien")) {
                alien = (Alien) layer;
                alien.tick();
            } else if (layer.getClass().getName().equals("Bullet")) {
                bullet = (Bullet) layer;
                bullet.tick();
            }
        }
        // TO DO detect collision with Barrier object


    }

    private void initAliens() {
        int r = 0;
        int c = 0;
        int rows = 0;
        int cols = 0;
        int xPos = 0;
        int yPos = 0;
        int height = 0;
        int width = 0;
        int spaceHeight = 0;
        int spaceWidth = 0;
        int fHeight = font.getHeight();

        Alien alien_;
        Image image = null;

        try {
            image = Image.createImage("/alien.png");
            width = image.getWidth() / Alien.RAW_FRAMES;
            height = image.getHeight();

            spaceHeight = (int) (gameCanvas.getCanvasHeight() - ((Barrier) vtrBarriers.elementAt(0)).getX() - (font.getHeight() * 2)) / 2;
            spaceWidth = gameCanvas.getWidth();

            rows = (int) (spaceHeight / height);
            cols = (int) ((spaceWidth * 3 / 4) / width);

            Alien.setRows(rows);
            Alien.setCols(cols);

        } catch (IOException io) {
            io.printStackTrace();
        }

        for (int i = 0; i < rows * cols; i++) {
            c = i % cols;
            r = (i - c) / cols;

            xPos = (width + Alien.colOffset) * c;
            yPos = (fHeight * 2) + (height + Alien.rowOffset) * r;

            alien_ = createAlienSprite(image, width, height, xPos, yPos);
            append(alien_);
            vtrAliens.addElement(alien_);
        }
    }

    // midp 1.0 implementation+Sprite
    private void initBarriers() {
        int col;
        int xPos = 0;
        int yPos = 0;
        int iWidth = 1;
        int iHeight = 1;
        Barrier barrier;
        int tileWidth = 0;
        int tileHeight = 0;

        // assuming two barriers per screen plus two barrier space in between
        // therefore what is a 1/4 of the screen - dependent upon the barrier image dimensions.    
        for (col = 0; col < Barrier.getCols(); col++) {
            barrier = createBarrierSprite();

            xPos = (int) ((barrier.getWidth() / 2) + (barrier.getWidth() * (col * 2)));
            yPos = getPlayer().getY() - barrier.getHeight();
            barrier.setPosition(xPos, yPos);

            append(barrier);
            vtrBarriers.addElement(barrier);
        }
    }

    public void initBullets() {
        Bullet bullet = createBulletSprite();
        insert(bullet, 0);
        vtrBullets.addElement(bullet);
    }

    public void paint(Graphics g) {
        g.setFont(font);
        paint(g, 0, 0);

        g.setColor(255, 255, 0);
        g.drawString(strScore + intScore + " Fps: " + gameCanvas.getAverageFrameRate(), gameCanvas.getWidth() / 2, 0, g.TOP | g.HCENTER);
        g.drawString("Avg Kb:" + gameCanvas.getAverageMemoryUsage() + "%, High: " + gameCanvas.getMemoryHigh() + ", Low: " + gameCanvas.getMemoryLow(), gameCanvas.getWidth() / 2, font.getHeight(), g.TOP | g.HCENTER);

        if (isLevelOver) {
            g.drawString(strLevelOver1, gameCanvas.getWidth() / 2, gameCanvas.getHeight() * 1 / 3, g.TOP | g.HCENTER);
        }

    }

    public void doGameAction(int gameAction) {
        Bullet bullet;

        if (gameAction == GameCanvas.LEFT) {
            player.left();

        } else if (gameAction == GameCanvas.RIGHT) {
            player.right();
        } else if (gameAction == GameCanvas.FIRE) {
            player.fire();
        }
    }

    private BackGround createBackground() {
        Image image = null;
        int rows = 0;
        int columns = 0;
        int width = 0;
        int height = 0;

        try {
            image = Image.createImage("/background.png");
            width = image.getWidth();
            height = image.getHeight();
            columns = (int) (gameCanvas.getWidth() / width) + 1;
            rows = (int) (gameCanvas.getHeight() / height) + 1;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return new BackGround(columns, rows, image, width, height);
    }

    private Barrier createBarrierSprite() {
        Image image = null;
        int height = 0;
        int width = 0;
        int xPos = 0;
        int yPos = 0;

        try {
            image = Image.createImage("/barrier.png");
            width = image.getWidth() / Barrier.RAW_FRAMES;
            height = image.getHeight();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return new Barrier(image, width, height);
    }

    private Player createPlayerSprite() {
        Image image = null;
        int height = 0;
        int width = 0;
        int xPos = 0;
        int yPos = 0;

        try {
            image = Image.createImage("/player.png");
            width = image.getWidth() / Player.RAW_FRAMES;
            height = image.getHeight();
            yPos = gameCanvas.getHeight() - height;
            xPos = (int) gameCanvas.getWidth() / 2;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return new Player(image, width, height, xPos, yPos);
    }

    private Bullet createBulletSprite() {
        Image image = null;
        int height = 0;
        int width = 0;
        int xPos = 0;
        int yPos = 0;

        try {
            image = Image.createImage("/bullet.png");
            width = image.getWidth() / Bullet.RAW_FRAMES;
            height = image.getHeight();
            yPos = (int) player.getY();
            xPos = (int) (player.getX() + (player.getWidth() / 2));
        } catch (IOException io) {
            io.printStackTrace();
        }
        return new Bullet(image, width, height, xPos, yPos);
    }

    public Alien createAlienSprite(Image image, int width, int height, int xPos, int yPos) {
        return new Alien(image, width, height, xPos, yPos);
    }
}