package isoj2me;

import java.io.InputStream;

/**
 * <p> isoj2me: Map </p>
 *
 * <p>This class manages Maps with load functions to read ascii map files. </p>
 *
 * <p> Copyright: Copyright (c) 2004 </p>
 *
 * <p> License: Lesser GPL (http://www.gnu.org) </p>
 *
 * <p> Company: <a href=http://www.mondonerd.com
 * target="_blank">Mondonerd.com</a> </p>
 *
 * @author Massimo Maria Avvisati
 * @version 0.5
 */
public class Map {

    public static final int DEFAULT_TILE = 48; // This is the value used when creating
    public int width, height;
    private int length;		   			// length = width * height
    private short[] cells;
    private int[] cellsDeltaHeight;		// For rendering elements on map	

    /**
     * This method load the map files and put them into the layers array. <br>
     * The filenames of a layers should be like <b>baseFilenmae <i>0 </i> </b>,
     * <b>baseFilenmae <i>1 </i> </b>, ..., <b>baseFilenmae <i>numberOfLayers
     * </i> </b>
     *
     * @param baseFilename name for the map files
     * @param numberOfLayers number of layers to load
     */
    public Map(String baseFilename) {

        short[] size = getFileShape(baseFilename);

        width = size[0];
        height = size[1];
        length = width * height;

        cells = new short[length];
        cellsDeltaHeight = new int[length];

        load(baseFilename);

        System.gc();

    }

    public void destroy() {

        cells = null;
        cellsDeltaHeight = null;

        System.gc();

    }

    /**
     * This method set null a layer. This layer will not be painted at all.
     *
     * @param layerNumber The layer to clear
     */
    public void clear() {

        int i;

        for (i = 0; i < cells.length; i++) {
            cells[i] = DEFAULT_TILE;
        }

    }

    /**
     * This is a method to see the "shape" of a map
     *
     * @param filename file to analyze
     * @return an array of (width, height)
     */
    private short[] getFileShape(String filename) {

        short counter = 0;
        short counter2 = 0;
        int ch = 0;
        short[] size = new short[2];
        Class c = this.getClass();
        InputStream is = c.getResourceAsStream(filename);

        try {
            // InputStreamReader isr = new InputStreamReader(is);

            while ((ch = is.read()) > -1) { // It read all the map file
                if (ch != 10) {
                    counter++; // Increments the number of chars
                } else {
                    counter2++; // Increments the number of lines
                }
            }

        } catch (Exception ex) {
        }
        counter2++; // The last line has no break line...
        size[0] = (short) (counter / counter2); // This is the "width" of the
        // map
        size[1] = counter2; // This is the "height" of the map

        return size;

    }

    /**
     * This load a layer reading it from an ascii file and store it into the
     * layers array
     *
     * @param filename file to load
     */
    private void load(String filename) {

        int ch;
        int counter = 0;
        Class c = this.getClass();
        InputStream is = c.getResourceAsStream(filename);

        try {
            // InputStreamReader isr = new InputStreamReader(is);

            while ((ch = is.read()) > -1) { // It read all the map file
                if (ch != 10) { // If the char is not \n it store it in the map
                    // array
                    cells[counter] = (short) ch; // The char is stored
                    // as a short value
                    counter++; // The next map index
                }
            }
        } catch (Exception ex) {
            // TODO manage exception
            System.out.println("Cannot read map " + ":" + ex);
            clear();
        }

    }

    /**
     * Return the value stored in the map array at given position
     *
     * @param layer layer to seek (z)
     * @param cellX x
     * @param cellY y
     * @return stored value (tile)
     */
    public int getCell(int cellX, int cellY) {

        int tile = -1, offset;

        if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
            offset = cellY * width + cellX;

            tile = cells[offset];
        }

        return tile;

    }

    /**
     * Set the value of a cell in the layer specified
     *
     * @param cellX x
     * @param cellY y
     * @param tile number that identify the tile
     * @throws java.lang.Exception
     */
    public void setCell(int cellX, int cellY, short tile) {

        int offset;

        if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
            offset = cellY * width + cellX;

            cells[offset] = tile;
        }

    }

    public int getCellDeltaHeight(int cellX, int cellY) {

        int offset;

        if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
            offset = cellY * width + cellX;

            return cellsDeltaHeight[offset];
        } else {
            return 0;
        }

    }

    public void setCellDeltaHeight(int cellX, int cellY, int deltaHeight) {

        int offset;

        if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
            offset = cellY * width + cellX;

            cellsDeltaHeight[offset] = deltaHeight;
        }

    }
}