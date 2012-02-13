
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

public class Map extends LayerManager {

    int[] GroundLayer_cells = new int[]{
        1, 1, 2, 3, 3, 3, 4, 5, 5, 5,
        3, 3, 6, 3, 3, 7, 8, 5, 5, 5,
        3, 3, 6, 3, 3, 3, 3, 8, 5, 5,
        3, 3, 6, 3, 3, 3, 3, 7, 8, 9,
        3, 3, 6, 3, 3, 3, 3, 3, 7, 3,
        3, 3, 6, 3, 3, 3, 3, 3, 3, 3,
        11, 11, 10, 11, 11, 11, 11, 11, 11, 11,
        3, 3, 6, 3, 3, 18, 3, 7, 3, 3,
        3, 3, 12, 1, 1, 1, 1, 13, 3, 3,
        3, 3, 3, 3, 3, 3, 3, 18, 3, 3};
    int[] TreeLayer_cells = new int[]{
        0, 0, 0, 14, 0, 0, 0, 0, 0, 0,
        0, 15, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 15, 0, 0, 14, 0, 0, 0,
        0, 19, 0, 0, 15, 0, 14, 0, 0, 0,
        0, 0, 0, 20, 0, 14, 0, 0, 0, 0,
        0, 0, 0, 16, 14, 16, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        15, 0, 0, 16, 0, 0, 0, 0, 0, 0,
        19, 15, 0, 0, 0, 0, 0, 0, 17, 0,
        0, 15, 16, 0, 0, 15, 0, 0, 0, 0};
    Image TreeLayer_tiles;
    TiledLayer TreeLayer;
    Image GroundLayer_tiles;
    TiledLayer GroundLayer;

    public Map() {
        try {
            Init();
        } catch (Exception ex) {
        }
    }

    void Init() throws Exception {
        TreeLayer_tiles = Image.createImage("/map.png");
        TreeLayer = new TiledLayer(10, 10, TreeLayer_tiles, 24, 35);
        GroundLayer_tiles = Image.createImage("/map.png");
        GroundLayer = new TiledLayer(10, 10, GroundLayer_tiles, 24, 35);
        fillLayer(TreeLayer, TreeLayer_cells);
        fillLayer(GroundLayer, GroundLayer_cells);
        append(TreeLayer);
        append(GroundLayer);
    }

    void fillLayer(TiledLayer layer, int[] cells) {
        for (int row = 0; row < layer.getRows(); row++) {
            for (int col = 0; col < layer.getColumns(); col++) {
                layer.setCell(col, row, cells[row * layer.getColumns() + col]);
            }
        }
    }
}
