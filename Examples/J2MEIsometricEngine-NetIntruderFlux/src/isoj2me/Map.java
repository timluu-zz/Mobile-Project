package isoj2me;

import java.io.*;

/**
 * <p>isoj2me: Character</p>
 * This class manages Maps with load functions to read ascii map files.</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>License: Lesser GPL (http://www.gnu.org)</p>
 * <p>Company: <a href=http://www.mondonerd.com target="_blank">Mondonerd.com</a></p>
 * @author Massimo Maria Avvisati
 * @version 0.5
 */

public class Map {

    public int[][] layers;

    private int height;

    private int width;

    public int numberOfLayers;

    public short basic_ch = 48; //This is the value used when creating empty

    // layer

    /**
     * This method load the map files and put them into the layers array. <br>
     * The filenames of a layers should be like <b>baseFilenmae <i>0 </i> </b>,
     * <b>baseFilenmae <i>1 </i> </b>, ..., <b>baseFilenmae <i>numberOfLayers
     * </i> </b>
     * 
     * @param baseFilename
     *            name for the map files
     * @param numberOfLayers
     *            number of layers to load
     */

    public Map(String baseFilename, int numberOfLayers) {
        int[] size = getFileShape(baseFilename + "0");
        this.width = size[0];
        this.height = size[1];
        this.numberOfLayers = numberOfLayers;
        layers = new int[numberOfLayers][width * height];

        for (int i = 0; i < numberOfLayers; i++) {
            load(baseFilename + i, i);
        }
    }

    /**
     * Return the value stored in the map array at given position
     * 
     * @param layer
     *            layer to seek (z)
     * @param x
     *            x
     * @param y
     *            y
     * @return value stored (tile)
     */

    public int getCell(int layer, int x, int y) {
        int tile = -1;
        if (layer < 0) {
            layer = 0;
        }
        if (layer >= numberOfLayers) {
            layer = numberOfLayers - 1;
        }
        if (layers[layer] != null) {
            if (y * width + x < height * width && y * width + x >= 0) {
                tile = layers[layer][y * width + x];
            }
        }
        return tile;
    }

    /**
     * Set the value of a cell in the layer specified
     * 
     * @param layer
     *            layer (z)
     * @param x
     *            x
     * @param y
     *            y
     * @param tile
     *            number that identify the tile
     * @throws java.lang.Exception
     */

    public void setCell(int layer, int x, int y, int tile) {
        if (layer < 0) {
            layer = 0;
        }
        if (layer > numberOfLayers) {
            layer = numberOfLayers - 1;
        }

        if (layers[layer] != null) {
            if (y * width + x <= height * width && y * width + x >= 0) {
                layers[layer][y * width + x] = tile;
            }
        }
    }

    /**
     * This load a layer reading it from an ascii file and store it into the
     * layers array
     * 
     * @param filename
     *            file to load
     * @param layer
     *            layer index where to store it
     */

    private void load(String filename, int layer) {
        int ch;
        int counter = 0;
        Class c = this.getClass();
        InputStream is = c.getResourceAsStream(filename);

        try {
            //InputStreamReader isr = new InputStreamReader(is);

            while ((ch = is.read()) > -1) { //It read all the map file
                if (ch != 10) { //If the char is not \n it store it in the map
                    // array
                    layers[layer][counter] = (short) ch; //The char is stored
                    // as a short value
                    counter++; //The next map index
                }
            }
        } catch (Exception ex) {
        	// TODO manage exception
            System.out.println("Cannot read layer " + layer + ":" + ex);
            for (int i = 0; i < height * width; i++) {
                layers[layer][i] = basic_ch;
            }
        }

    }



    /**
     * Print a readeable map of all the layers
     * 
     * @return string to print
     */

    public String toString() {
        String result = "(";
        for (int i = 0; i < numberOfLayers; i++) {
            for (int j = 0; j < (width * height); j++) {
                if (j % width == 0) {
                    result = result + "\n";
                }
                result = result + layers[i][j] + " ";
            }
            result = result + "-----\n";

        }
        result = result + ")";

        return result;
    } // end toString

    /**
     * Simply return the map width
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Simply return the map height
     * 
     * @return height
     */

    public int getHeight() {
        return height;
    }

    /**
     * This is a method to see the "shape" of a map
     * 
     * @param filename
     *            file to analyze
     * @return an array of (width, height)
     */

    private int[] getFileShape(String filename) {
        int counter = 0;
        int counter2 = 0;
        int ch = 0;
        int[] size = new int[2];
        Class c = this.getClass();
        InputStream is = c.getResourceAsStream(filename);

        try {
            //InputStreamReader isr = new InputStreamReader(is);

            while ((ch = is.read()) > -1) { //It read all the map file
                if (ch != 10) {
                    counter++; //Increments the number of chars
                } else {
                    counter2++; //Increments the number of lines
                }
            }

        } catch (Exception ex) {
        }
        counter2++; //The last line has no break line...
        size[0] = counter / counter2; //This is the "width" of the map
        size[1] = counter2; //This is the "height" of the map
        return size;
    }

    /**
     * This method set null a layer. This layer will not be painted at all.
     * 
     * @param layerNumber The layer to clear
     */

    public void clearLayer(int layerNumber) {
        if (layerNumber > 0 && layerNumber < this.numberOfLayers) {
            for (int i = 0; i < height * width; i++) {
                layers[layerNumber][i] = basic_ch;
            }

        }
    }
}