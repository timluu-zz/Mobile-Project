package isoj2me;

import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

/**
 * <p> isoj2me: Board </p>
 *
 * <p> This is the main class. It manages a Board where you can add, remove and
 * move Characters and Items. It manages Maps too. This class perform the draw
 * method to paint the Board on the device </p>
 *
 * <p> Copyright: Copyright (c) 2005 </p>
 *
 * <p> License: Lesser GPL (http://www.gnu.org) </p>
 *
 * <p> Company: <a href=http://www.mondonerd.com target="_blank">Mondonerd.com
 * </a> </p>
 *
 * @author Massimo Maria Avvisati
 * @version 0.5b
 */
public class Board {

    public Map map;
    public static Hashtable tiles = new Hashtable(20);
    private int tileWidth, tileHeight;
    private Hashtable charactersPositions = new Hashtable(20);
    private Hashtable itemsPositions = new Hashtable(20);
    public Hashtable characters = new Hashtable();
    public Hashtable items = new Hashtable();
    public String tileSet = "";

    /**
     * Constructor. The tileset basic name will be an empty string.
     */
    public Board() {
    }

    /**
     * Constructor. The tileset basic name will be tileSet and it will be used
     * when loading tiles image files.
     *
     * @param tileSet basic name for tile files
     */
    public Board(String tileSet) {
        this.tileSet = tileSet;
    }

    /**
     * Add a Character to the Board.
     *
     * @param c Character object to add
     */
    public void putCharacter(isoj2me.Character c) {
        charactersPositions.put(c.x + "_" + c.y + "_" + c.z, c);
        characters.put(c.name, c);
    }

    /**
     * Create and then add a Character to the Board.
     *
     * @param name name of the Character
     * @param x x
     * @param y y
     * @param z z
     */
    public void putCharacter(String name, int x, int y, int z) {
        isoj2me.Character c = new isoj2me.Character(name, x, y, z);
        charactersPositions.put(x + "_" + y + "_" + z, c);
        characters.put(c.name, c);
    }

    /**
     * Add an Item to the Board
     *
     * @param i Item object to add
     */
    public void putItem(isoj2me.Item i) {
        itemsPositions.put(i.x + "_" + i.y + "_" + i.z, i);
        items.put(i.name, i);
    }

    /**
     * Create and then add an Item to the Board.
     *
     * @param name name of the Item
     * @param x x
     * @param y y
     * @param z z
     */
    public void putItem(String name, int x, int y, int z) {
        isoj2me.Item i = new isoj2me.Item(name, x, y, z);
        itemsPositions.put(x + "_" + y + "_" + z, i);
        items.put(i.name, i);
    }

    /**
     * Remove a Character from the Board
     *
     * @param c Character object to remove
     */
    public void removeCharacter(isoj2me.Character c) {
        charactersPositions.remove(c.x + "_" + c.y + "_" + c.z);
        characters.remove(c.name);
    }

    /**
     * Remove an Item from the Board
     *
     * @param i Item object to remove
     */
    public void removeItem(isoj2me.Item i) {
        itemsPositions.remove(i.x + "_" + i.y + "_" + i.z);
        items.remove(i.name);
    }

    /**
     * If there is a Character in the x, y, z position of the Board it returns
     * its name. Return null if there is no character.
     *
     * @param x x
     * @param y y
     * @param z z
     * @return Character name
     */
    public String isCharacter(int x, int y, int z) {
        String result = null;
        if (charactersPositions.containsKey(x + "_" + y + "_" + z)) {
            result = charactersPositions.get(x + "_" + y + "_" + z).toString();
        }

        return result;

    }

    /**
     * If there is a Item in the x, y, z position of the Board it returns its
     * name. Return null if there is no item.
     *
     * @param x x
     * @param y y
     * @param z z
     * @return Item name
     */
    public String isItem(int x, int y, int z) {
        String result = null;
        if (itemsPositions.containsKey(x + "_" + y + "_" + z)) {
            result = itemsPositions.get(x + "_" + y + "_" + z).toString();
        }

        return result;

    }

    /**
     * Move a Character on the Board
     *
     * @param c Character to move
     * @param oldX old x
     * @param oldY old y
     * @param oldZ old z
     * @param newX new x
     * @param newY new y
     * @param newZ new z
     * @param overwrite true if you want to overwrite an existing Character that
     * stays in the new position
     * @return true if the movement was done @todo use the Character c to know
     * oldX, oldY, oldZ and to change c coords.
     */
    public boolean moveCharacter(isoj2me.Character c, int oldX, int oldY,
            int oldZ, int newX, int newY, int newZ, boolean overwrite) {

        if (charactersPositions.remove(oldX + "_" + oldY + "_" + oldZ) != null) {

            if (overwrite != true) {
                if (!charactersPositions.containsKey(newX + "_" + newY + "_"
                        + newZ)) {
                    charactersPositions.put(newX + "_" + newY + "_" + newZ, c);

                    return true;

                } else {
                    return false;
                }
            } else {
                charactersPositions.put(newX + "_" + newY + "_" + newZ,
                        charactersPositions.get(oldX + "_" + oldY + "_"
                        + oldZ));
                charactersPositions.remove(oldX + "_" + oldY + "_" + oldZ);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Move an Item on the Board
     *
     * @param i Item to move
     * @param oldX old x
     * @param oldY old y
     * @param oldZ old z
     * @param newX new x
     * @param newY new y
     * @param newZ new z
     * @param overwrite true if you want to overwrite an existing Item that
     * stays in the new position
     * @return true if the movement was done
     *
     * @todo use the Item i to know oldX, oldY, oldZ and to change c coords.
     */
    public boolean moveItem(isoj2me.Item i, int oldX, int oldY, int oldZ,
            int newX, int newY, int newZ, boolean overwrite) {
        if (itemsPositions.remove(oldX + "_" + oldY + "_" + oldZ) != null) {

            if (overwrite != true) {
                if (!itemsPositions.containsKey(newX + "_" + newY + "_" + newZ)) {
                    itemsPositions.put(newX + "_" + newY + "_" + newZ, i);

                    return true;

                } else {
                    return false;
                }
            } else {
                itemsPositions.put(newX + "_" + newY + "_" + newZ,
                        charactersPositions.get(oldX + "_" + oldY + "_"
                        + oldZ));
                itemsPositions.remove(oldX + "_" + oldY + "_" + oldZ);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method change the shape of the basic tile. This affect the
     * coordinates of the tiles painted by the <b>draw() </b> method
     *
     * @param width the width of the tile
     * @param height the distance between the base of the tile file and the
     * "floor" of the basic tile
     */
    public void setTileSize(int width, int height) {
        tileWidth = width;
        tileHeight = height;

    }

    /**
     * This method call the Map() constructor passing the base name for the map
     * files and the number of layers
     *
     * @param baseFilename base name for the map files
     * @param numberOfLayers number of layers to load
     */
    public void createMap(String baseFilename, int numberOfLayers) {
        map = new Map(baseFilename, numberOfLayers);
    }

    /**
     * This one of the main method of this class. It paint on the given Graphics
     * the board with its tiles and characters.
     *
     * @param x where to paint on the Graphics (x)
     * @param y where to paint on the Graphics (y)
     * @param cellX starting cell on the map (x)
     * @param cellY starting cell on the mpa (y)
     * @param width width of the map area to be painted (starting from cellX)
     * @param height height of the map area to be painted (starting from cellY)
     * @param g Graphics object where to paint
     */
    public void draw(int x, int y, int cellX, int cellY, int width, int height,
            Graphics g) {

        if (cellX < 0) {
            cellX = 0;
        }
        if (cellY < 0) {
            cellY = 0;
        }

        if (cellX > map.getWidth()) {
            cellX = map.getWidth();
        }
        if (cellY > map.getHeight()) {
            cellY = map.getHeight();
        }

        int x_temp, y_temp;
        x_temp = x;

        int x_init = x_temp;

        int y_temp2 = 0;
        for (int i = 0; i < height; i++) { // y cycle
            y_temp = y + tileHeight * i;
            x_temp = x_init - (tileWidth / 2) * i;
            for (int j = 0; j < width; j++) { // x cycle
                y_temp2 = y_temp;
                for (int k = 0; k < map.numberOfLayers; k++) { // layers
                    // cycle
                    y_temp = y_temp2 - (tileHeight * k);
                    if (map.layers[k] != null) {
                        if (i + cellY < map.getHeight()
                                && j + cellX < map.getWidth()) {

                            g.drawImage(
                                    Utility.loadImage(
                                    tileSet
                                    + map.layers[k][((i + cellY) * map.getWidth())
                                    + (j + cellX)],
                                    tiles),
                                    x_temp
                                    - ((Utility.loadImage(
                                    tileSet
                                    + map.layers[k][((i + cellY) * map.getWidth())
                                    + (j + cellX)],
                                    tiles).getWidth() - tileWidth) / 2),
                                    y_temp
                                    - (Utility.loadImage(
                                    tileSet
                                    + map.layers[k][((i + cellY) * map.getWidth())
                                    + (j + cellX)],
                                    tiles).getHeight() - tileHeight),
                                    Graphics.TOP | Graphics.LEFT);
                        }

                    }

                    //Paint characters
                    if (charactersPositions.containsKey((j + cellX) + "_"
                            + (i + cellY) + "_" + k)) {
                        isoj2me.Character temp_character = (isoj2me.Character) charactersPositions.get((j + cellX) + "_" + (i + cellY) + "_" + k);
                        int temp_char_x = x_temp
                                + (tileWidth - temp_character.getCurrentImage().getWidth()) / 2;
                        temp_character.draw(temp_char_x, y_temp - tileHeight, g);
                    }
                    //	Paint items
                    if (itemsPositions.containsKey((j + cellX) + "_"
                            + (i + cellY) + "_" + k)) {
                        isoj2me.Item temp_character = (isoj2me.Item) itemsPositions.get((j + cellX) + "_" + (i + cellY) + "_" + k);
                        int temp_char_x = x_temp
                                + (tileWidth - temp_character.getCurrentImage().getWidth()) / 2;
                        temp_character.draw(temp_char_x, y_temp - tileHeight, g);
                    }

                }

                y_temp = y_temp2;
                x_temp = x_temp + tileWidth / 2;
                y_temp = y_temp + tileHeight;

            }

        }

    }
}
