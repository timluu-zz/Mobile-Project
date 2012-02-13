
/**
 * <p>Title: DemoVader</p>
 *
 * <p>Description: .</p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: Symbian Ltd</p>
 *
 * @author Alan Newman - alan@sensibledevelopment.net
 * @version 1.0
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Barrier extends Sprite {

    protected static int cols = 2;
    int[][] imageMap;
    Image[] imageFrames;
    Image sourceImage;
    final int TILES = 1;
    Image image;
    int imageWidth;
    int imageHeight;
    static int tileWidth;
    static int tileHeight;
    int canvasWidth;
    int canvasHeight;
    protected static final int RAW_FRAMES = 1;
    protected static final String IMAGE_NAME = "barrier";
    protected int xPos;
    protected int yPos;
    protected static VaderCanvas gameCanvas;
    protected static VaderManager layerManager;

    public Barrier(Image image, int width, int height) {
        super(image, width, height);
        sourceImage = image;
        tileWidth = width;
        tileHeight = height;
        gameCanvas = layerManager.getGameCanvas();
        Image sprite = createImage();
        this.setImage(sprite, sprite.getWidth(), sprite.getHeight());
    }

    public static int getCols() {
        return cols;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public Image getImage() {
        return image;
    }

    public static void setLayerManager(VaderManager manager) {
        layerManager = manager;
    }

    private void setCell(int col, int row, int value) {
        imageMap[col][row] = value;
    }

    public Image createImage() {
        int columns = (int) (gameCanvas.getWidth() / 4 / tileWidth) + 1;
        int rows = 20;

        int imageWidth = columns * getTileWidth();
        int imageHeight = rows * getTileHeight();

        Image image = Image.createImage(imageWidth, imageHeight);

        imageMap = new int[columns][rows];

        int tiles = columns * rows;
        for (int i = 0; i < tiles; i++) {
            int c = i % columns;
            int r = (i - c) / columns;
            setCell(c, r, 0);
        }

        return setTileImages(image, columns, rows);
    }

    private Image setTileImages(Image image, int columns, int rows) {
        Graphics g = image.getGraphics();
        int tiles = columns * rows;
        Image tempImage;

        int frameCols = (sourceImage.getWidth() / getTileWidth());
        int frameRows = (sourceImage.getHeight() / getTileHeight());
        int frames = frameCols * frameRows;

        Image[] imageFrames = new Image[frames];

        for (int f = 0; f < frames; f++) {
            int col = f % frameCols;
            int row = (f - col) / frameCols;
            imageFrames[f] = Image.createImage(sourceImage, row, col, getTileWidth(), getTileHeight(), Sprite.TRANS_NONE);
        }

        for (int i = 0; i < tiles; i++) {
            int c = i % columns;
            int r = (i - c) / columns;

            g.drawImage(imageFrames[imageMap[c][r]], c * getTileHeight(), r * getTileWidth(), g.LEFT | g.TOP);
        }

        return image;
    }
}