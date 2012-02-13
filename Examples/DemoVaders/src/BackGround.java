
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
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

public class BackGround extends TiledLayer {

    public BackGround(int columns, int rows, Image image, int tileWidth, int tileHeight) {
        super(columns, rows, image, tileWidth, tileHeight);

        int tiles = columns * rows;
        for (int i = 0; i < tiles; i++) {
            int c = i % columns;
            int r = (i - c) / columns;
            this.setCell(c, r, 1);
        }
    }
}