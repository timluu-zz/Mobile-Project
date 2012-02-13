package isoj2me;

import java.util.*;

import javax.microedition.lcdui.*;

/**
 * <p>
 * isoj2me: Board
 * </p>
 * <p>
 * This is the main class. It manages a Board where you can add, remove and move
 * Characters and Items. It manages Maps too. This class perform the draw method
 * to paint the Board on the device
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * License: Lesser GPL (http://www.gnu.org)
 * </p>
 * <p>
 * Company: <a href=http://www.mondonerd.com target="_blank">Mondonerd.com</a>
 * </p>
 * 
 * @author Massimo Maria Avvisati
 * @version 0.5
 */

public class Board {
	public static Hashtable tiles = new Hashtable(12); //TODO this value have to be a variable to optimize the result

	/**
	 * This one of the main method of this class. It paint on the given Graphics
	 * the board with its tiles and characters.
	 * 
	 * @param x
	 *            where to paint on the Graphics (x)
	 * @param y
	 *            where to paint on the Graphics (y)
	 * @param cellX
	 *            starting cell on the map (x)
	 * @param cellY
	 *            starting cell on the mpa (y)
	 * @param width
	 *            width of the map area to be painted (starting from cellX)
	 * @param height
	 *            height of the map area to be painted (starting from cellY)
	 * @param g
	 *            Graphics object where to paint
	 */

	public int cellX = 0;

	public int cellY = 0;
	public int charactersClipWidth = 60;
    public int charactersClipHeight = 60;
	public Hashtable characters = new Hashtable(1);

	private Hashtable charactersPositions = new Hashtable(1);

//	public Hashtable items = new Hashtable();
//
//	private Hashtable itemsPositions = new Hashtable(20);

	public Map map;

	public String tileSet = "";

	public int tileWidth, tileHeight;

	public int width, height;

	/**
	 * Constructor. The tileset basic name will be an empty string.
	 */

	public Board() {
	}

	/**
	 * Constructor. The tileset basic name will be tileSet and it will be used
	 * when loading tiles image files.
	 * 
	 * @param tileSet
	 *            basic name for tile files
	 */
    private Canvas canvas = null;
	public Board(String tileSet, Canvas canvas) {
        this.canvas = canvas;
		this.tileSet = tileSet;
	}

	/**
	 * This method call the Map() constructor passing the base name for the map
	 * files and the number of layers
	 * 
	 * @param baseFilename
	 *            base name for the map files
	 * @param numberOfLayers
	 *            number of layers to load
	 */

	public void createMap(String baseFilename, int numberOfLayers) {
		map = new Map(baseFilename, numberOfLayers);
	}

	boolean lastDrawn = false;

	int initI = 0;

	int lastI = 30;

	public void drawTile(int mapX, int mapY, int x, int y, Graphics g) {
		Image tempImage = null;
        if (map.layers[0][(mapY * map.getWidth()) + mapX] != 48) {
		try {
			String imageCode = ""
					+ map.layers[0][(mapY * map.getWidth()) + mapX];
			tempImage = Utility.loadImage(tileSet + imageCode, tiles);
		} catch (Exception e) {

		}
		if (tempImage != null) {
			g.drawImage(tempImage, x - (tempImage.getWidth() / 2), y
					- tempImage.getHeight() + tileHeight * 2, Graphics.TOP
					| Graphics.LEFT);
		}
        }
	}

	public void drawCharacter(int mapX, int mapY, int x, int y, Graphics g) {
		// Paint characters

		if (charactersPositions.containsKey(mapX + "_" + mapY + "_0")) {
			isoj2me.Character tempCharacter = (isoj2me.Character) charactersPositions
					.get(mapX + "_" + mapY + "_0");
			int tempCharX = x + (-tempCharacter.getCurrentImage().getWidth())
					/ 2;

			if ("player".equals(tempCharacter.name)) {
				int xMod = 0;
				int yMod = 0;
//                if (tempCharacter.speed > 2) {
//                    tempCharacter.speed = 2;
//                }
				if (tempCharacter.getCurrentAction().indexOf("SW") > 0) {
					// System.out.println("SW");
					if (tempCharacter.speed > 2) {
						// System.out.println("Step 1");
						tempCharacter.speed = 2;
						xMod = +11;
						yMod = -5;

					} else if (tempCharacter.speed > 1) {
						// System.out.println("Step 2");
						tempCharacter.speed = 1;
						xMod = +8;
						yMod = -4;
					} else if (tempCharacter.speed > 0) {
						// System.out.println("Step 3");
						tempCharacter.speed = 0;
						tempCharacter.isMoving = false;
					}
				} else if (tempCharacter.getCurrentAction().indexOf("NW") > 0) {
					// System.out.println("NW");
					if (tempCharacter.speed > 2) {
						// System.out.println("Step 1");
						tempCharacter.speed = 2;
						xMod = +11;
						yMod = +5;

					} else if (tempCharacter.speed > 1) {
						// System.out.println("Step 2");
						tempCharacter.speed = 1;
						xMod = +8;
						yMod = +4;
					} else if (tempCharacter.speed > 0) {
						// System.out.println("Step 3");
						tempCharacter.speed = 0;
						tempCharacter.isMoving = false;
					}
				} else if (tempCharacter.getCurrentAction().indexOf("SE") > 0) {
					// System.out.println("SE");
					if (tempCharacter.speed > 2) {
						// System.out.println("Step 1");
						tempCharacter.speed = 2;
						xMod = -11;
						yMod = -5;

					} else if (tempCharacter.speed > 1) {
						// System.out.println("Step 2");
						tempCharacter.speed = 1;
						xMod = -8;
						yMod = -4;
					} else if (tempCharacter.speed > 0) {
						// System.out.println("Step 3");
						tempCharacter.speed = 0;
						tempCharacter.isMoving = false;
					}
				} else if (tempCharacter.getCurrentAction().indexOf("NE") > 0) {
					// System.out.println("NE");

					if (tempCharacter.speed > 2) {
						// System.out.println("Step 1");
						tempCharacter.speed = 2;
						xMod = -11;
						yMod = +5;

					} else if (tempCharacter.speed > 1) {
						// System.out.println("Step 2");
						tempCharacter.speed = 1;
						xMod = -8;
						yMod = +4;
					} else if (tempCharacter.speed > 0) {
						// System.out.println("Step 3");
						tempCharacter.speed = 0;
						tempCharacter.isMoving = false;
					}
				}
				tempCharacter.draw(tempCharX + xMod, y + yMod, g);
				tempCharacter.lastDrawnX = tempCharX + xMod
						+ tempCharacter.getCurrentImage().getWidth() / 2;
				tempCharacter.lastDrawnY = y + yMod
						- tempCharacter.getCurrentImage().getHeight() / 2
						+ tempCharacter.modifierY;

			} else {
				tempCharacter.draw(tempCharX, y, g);
				tempCharacter.lastDrawnX = tempCharX
						+ tempCharacter.getCurrentImage().getWidth() / 2;
				tempCharacter.lastDrawnY = y
						- tempCharacter.getCurrentImage().getHeight() / 2
						+ tempCharacter.modifierY;
				tempCharacter.speed = 0;
				tempCharacter.isMoving = false;
			}
		}
	}

//	private void drawItem(int mapX, int mapY, int x, int y, Graphics g) {
//		// Paint items
//
//		if (itemsPositions.containsKey(mapX + "_" + mapY + "_0")) {
//			Item tempItem = (Item) itemsPositions.get(mapX + "_" + mapY + "_0");
//			Image tempImage = tempItem.getCurrentImage();
//			g.drawImage(tempImage, x - (tempImage.getWidth() / 2), y
//					- tempImage.getHeight() + tileHeight * 2, Graphics.TOP
//					| Graphics.LEFT);
//		}
//	}

	public Image cachedBoard = null;

	public boolean changed = true;

	// Image characterBoard = null;
	//Image cuttedBoard = null;

	//int characterCounter = 0;

	private int clipWidth;

	private int clipHeight;

	public void draw(int x, int y, Graphics gInit, boolean centered) {
		Character c = null;
		if (!centered) {
			cachedBoard = Image.createImage(gInit.getClipWidth(), canvas.getHeight());
			drawEmpty(x, y, cachedBoard.getGraphics(), true);
			gInit.drawImage(cachedBoard, 0, 0, Graphics.TOP | Graphics.LEFT);
		} else {
			if (changed || cachedBoard == null) {
				cachedBoard = Image.createImage(gInit.getClipWidth(), canvas.getHeight());
				drawEmpty(x, y, cachedBoard.getGraphics(), false);
				gInit
						.drawImage(cachedBoard, 0, 0, Graphics.TOP
								| Graphics.LEFT);

				changed = false;
			} else {

				gInit
						.drawImage(cachedBoard, 0, 0, Graphics.TOP
								| Graphics.LEFT);
				Enumeration charEnum = characters.elements();
				int charactersCounter = 0;
				for (Enumeration iter = charEnum; iter.hasMoreElements();) {
				
				c = (Character) iter.nextElement();
				//if (c.x >= cellX && c.y >= cellY && c.x < cellX + width && c.y < cellY + height) { //TODO verify
				if (true) {
					charactersCounter++;
					clipWidth = gInit.getClipWidth();
					clipHeight = canvas.getHeight();
					int clipWidthTemp = charactersClipWidth; //TODO configuration
					int clipHeightTemp = charactersClipHeight; //TODO configuration
					
						gInit.setClip(c.lastDrawnX - clipWidthTemp / 2,
				
							c.lastDrawnY - clipHeightTemp / 2, clipWidthTemp,
							clipHeightTemp);

					drawCharacterBoard(c, x, y, gInit);
					// characterboard on
					// draw
					gInit.setClip(0, 0, clipWidth, clipHeight);
				}

				}
			}

		}

	}

	boolean repaint = true;

	public void drawEmpty(int x, int y, Graphics gInit, boolean withPlayer) {
		gInit.setColor(0x000000);
		gInit.fillRect(0, 0, gInit.getClipWidth(), canvas.getHeight());
		boolean firstPainted = false;
		int xTemp = x;
		int yTemp = 0;
		int xInit = x;
		lastDrawn = false;

		//int lastItemp = lastI;
		for (int i = 0; i < height; i++) { // y cycle (counter is "i")

			yTemp = y + tileHeight * i;
			xTemp = xInit - (tileWidth / 2) * i;
			for (int j = 0; j < height; j++) {

				if (onScreen(xTemp - tileWidth / 2, yTemp, gInit)
						&& (j + cellX < map.getWidth())
						&& (i + cellY) < map.getHeight() && (j + cellX >= 0)
						&& (i + cellY >= 0)) { //TODO verify
// && (map.getCell(0, j + cellX, i + cellY) > 65 || "player".equals(isCharacter(j + cellX, i + cellY, 0)))
					if (firstPainted != true) {
						firstPainted = true;
					}

					
					if (withPlayer) {
						drawCharacter(j + cellX, i + cellY, xTemp, yTemp, gInit);
					} else {
						drawTile(j + cellX, i + cellY, xTemp, yTemp, gInit);
					}

					if (!lastDrawn) {
						lastI = i + 1;
					}
				}
				xTemp = xTemp + tileWidth / 2;
				yTemp = yTemp + tileHeight;
			}
		}

		lastDrawn = true;
	}

	// Image bufferCharacter = null;
	private void drawCharacterBoard(Character c, int x, int y, Graphics gInit) {

		int xTemp = x;
		int yTemp = 0;
		int xInit = x;
		
		int counter = 0;
		for (int i = 0; i < height; i++) { // y cycle (counter is "i")

			yTemp = y + tileHeight * i;
			xTemp = xInit - (tileWidth / 2) * i;

			for (int j = 0; j < height; j++) {

				int xCharTemp = j + cellX;
				int yCharTemp = i + cellY;

				if ((((xCharTemp - c.x <= 3 && xCharTemp - c.x >= 0)  //TODO configuration 3, 3...
						&& ((yCharTemp - c.y <= 3 && yCharTemp - c.y >= 0)) && map.getCell(0, xCharTemp, yCharTemp) >= 77) || 
                        
                        (xCharTemp - c.x <= 2 && xCharTemp - c.x >= 0)
                        && ((yCharTemp - c.y <= 2 && yCharTemp - c.y >= 0))
                
                
                )
                        
                        
                        
						&& (map.getCell(0, xCharTemp, yCharTemp) > 65 || (xCharTemp == c.x && yCharTemp == c.y) || (charactersPositions.containsKey(xCharTemp + "_" + yCharTemp + "_0")))
						&& j + cellX < map.getWidth()
						&& i + cellY < map.getHeight()
						&& onScreen(xTemp - tileWidth / 2, yTemp, -17, -17, //TODO configuration
								clipWidth + 17, clipHeight + tileHeight)) {
                    
					counter++;

					if (!(xCharTemp == c.x && yCharTemp == c.y)) {
					drawTile(j + cellX, i + cellY, xTemp, yTemp, gInit);
					}
					drawCharacter(j + cellX, i + cellY, xTemp, yTemp, gInit);

					// drawItem(j + cellX, i + cellY, xTemp, yTemp, gInit);
				}

				if (!lastDrawn) {
					lastI = i + 1;
				}

				xTemp = xTemp + tileWidth / 2;
				yTemp = yTemp + tileHeight;
			}
		}

		lastDrawn = true;
	}


	/**
	 * If there is a Character in the x, y, z position of the Board it returns
	 * its name. Return null if there is no character.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param z
	 *            z
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
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param z
	 *            z
	 * @return Item name
	 */

//	public String isItem(int x, int y, int z) {
//		String result = null;
//		if (itemsPositions.containsKey(x + "_" + y + "_" + z)) {
//			result = itemsPositions.get(x + "_" + y + "_" + z).toString();
//		}
//
//		return result;
//
//	}

	/**
	 * Move a Character on the Board
	 * 
	 * @param c
	 *            Character to move
	 * @param oldX
	 *            old x
	 * @param oldY
	 *            old y
	 * @param oldZ
	 *            old z
	 * @param newX
	 *            new x
	 * @param newY
	 *            new y
	 * @param newZ
	 *            new z
	 * @param overwrite
	 *            true if you want to overwrite an existing Character that stays
	 *            in the new position
	 * @return true if the movement was done
	 * @todo use the Character c to know oldX, oldY, oldZ and to change c
	 *       coords.
	 */

	//Graphics g = null;

	public boolean moveCharacter(isoj2me.Character c, int oldX, int oldY,
			int oldZ, int newX, int newY, int newZ, boolean overwrite) {

		c.speed = 16;
		c.isMoving = true;
//TODO verify
//		if ((newX >= cellX && newX <= cellX + width)
//				&& (newY >= cellY && newY <= cellY + height)) {// &&
//		}

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
				charactersPositions
						.put(newX + "_" + newY + "_" + newZ,
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
	 * @param i
	 *            Item to move
	 * @param oldX
	 *            old x
	 * @param oldY
	 *            old y
	 * @param oldZ
	 *            old z
	 * @param newX
	 *            new x
	 * @param newY
	 *            new y
	 * @param newZ
	 *            new z
	 * @param overwrite
	 *            true if you want to overwrite an existing Item that stays in
	 *            the new position
	 * @return true if the movement was done
	 * 
	 * @todo use the Item i to know oldX, oldY, oldZ and to change c coords.
	 */

//	public boolean moveItem(isoj2me.Item i, int oldX, int oldY, int oldZ,
//			int newX, int newY, int newZ, boolean overwrite) {
//		if (itemsPositions.remove(oldX + "_" + oldY + "_" + oldZ) != null) {
//
//			if (overwrite != true) {
//				if (!itemsPositions.containsKey(newX + "_" + newY + "_" + newZ)) {
//					itemsPositions.put(newX + "_" + newY + "_" + newZ, i);
//
//					return true;
//
//				} else {
//					return false;
//				}
//			} else {
//				itemsPositions
//						.put(newX + "_" + newY + "_" + newZ,
//								charactersPositions.get(oldX + "_" + oldY + "_"
//										+ oldZ));
//				itemsPositions.remove(oldX + "_" + oldY + "_" + oldZ);
//			}
//			return true;
//		} else {
//			return false;
//		}
//	}

	private boolean onScreen(int xTemp, int yTemp, int boundMinWidth,
			int boundMinHeight, int boundWidth, int boundHeight) {
		xTemp += 16;
		yTemp += 8;

		if (xTemp > boundMinWidth && yTemp > boundMinHeight
				&& xTemp < boundWidth + 5 && yTemp < boundHeight) {
			return true;
		} else {
			return false;
		}
	}

	private boolean onScreen(int xTemp, int yTemp, Graphics gInit) {
		
		xTemp += 16;  //TODO configuration
		yTemp += 8; 
		if (xTemp > -tileWidth && yTemp > 0 && xTemp < gInit.getClipWidth() + tileWidth
				&& yTemp < canvas.getHeight() + tileHeight * 2) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add a Character to the Board.
	 * 
	 * @param c
	 *            Character object to add
	 */

	public void putCharacter(isoj2me.Character c) {
		charactersPositions.put(c.x + "_" + c.y + "_" + c.z, c);
		characters.put(c.name, c);
	}

	/**
	 * Create and then add a Character to the Board.
	 * 
	 * @param name
	 *            name of the Character
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param z
	 *            z
	 */

	public void putCharacter(String name, int x, int y, int z) {
		isoj2me.Character c = new isoj2me.Character(name, x, y, z);
		charactersPositions.put(x + "_" + y + "_" + z, c);
		characters.put(c.name, c);
	}

	/**
	 * Add an Item to the Board
	 * 
	 * @param i
	 *            Item object to add
	 */

//	public void putItem(isoj2me.Item i) {
//		itemsPositions.put(i.x + "_" + i.y + "_" + i.z, i);
//		items.put(i.name, i);
//	}

	/**
	 * Create and then add an Item to the Board.
	 * 
	 * @param name
	 *            name of the Item
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param z
	 *            z
	 */

//	public void putItem(String name, int x, int y, int z) {
//		isoj2me.Item i = new isoj2me.Item(name, x, y, z);
//		itemsPositions.put(x + "_" + y + "_" + z, i);
//		items.put(i.name, i);
//	}

	/**
	 * Remove a Character from the Board
	 * 
	 * @param c
	 *            Character object to remove
	 */

	public void removeCharacter(isoj2me.Character c) {
		charactersPositions.remove(c.x + "_" + c.y + "_" + c.z);
		characters.remove(c.name);
	}

	/**
	 * Remove an Item from the Board
	 * 
	 * @param i
	 *            Item object to remove
	 */

//	public void removeItem(isoj2me.Item i) {
//		itemsPositions.remove(i.x + "_" + i.y + "_" + i.z);
//		items.remove(i.name);
//	}

	/**
	 * This method change the shape of the basic tile. This affect the
	 * coordinates of the tiles painted by the <b>draw()</b> method
	 * 
	 * @param width
	 *            the width of the tile
	 * @param height
	 *            the distance between the base of the tile file and the "floor"
	 *            of the basic tile
	 */

	public void setTileSize(int width, int height) {
		tileWidth = width;
		tileHeight = height;

	}

}