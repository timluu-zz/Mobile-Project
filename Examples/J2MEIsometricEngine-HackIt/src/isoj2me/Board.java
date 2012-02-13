package isoj2me;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * <p> isoj2me: Board </p>
 *
 * <p> This is the main class. It manages a Board where you can add, remove and
 * move Characters and Items. It manages Maps too. This class perform the draw
 * method to paint the Board on the device </p>
 *
 * <p> Copyright: Copyright (c) 2006 </p>
 *
 * <p> License: Lesser GPL (http://www.gnu.org) </p>
 *
 * <p> Company: <a href=http://www.mondonerd.com
 * target="_blank">Mondonerd.com</a> </p>
 *
 * @author Massimo Maria Avvisati Giuseppe Perniola
 * @version 0.5
 */
public class Board {

    private static final int NUM_VERTEXES_CAMERA = 4;
    private static final int MIN_WALKABLE_TILE = 49, MAX_WALKABLE_TILE = 63;
    public boolean changedCachedBoard = true;
    public int cameraX1 = 0, cameraY1 = 0, cameraX2, cameraY2;
    public Hashtable elements = new Hashtable(1);
    public String tileSet = "";
    public Map map;
    private boolean otherActionEdgeA, otherActionEdgeB;			// for rendering
    private int cellWidth, cellHeight, hCellWidth, hCellHeight, cellTanXFP, cellTanYFP;
    private int startX, startY, startXFP, startYFP;
    private int screenWidth, screenHeight, extraHeight;
    private int initialCellX, initialCellY, numPass;			// for rendering
    private Hashtable elementsPositions = new Hashtable(1);
    private Image cachedBoard = null;
    private Graphics graphicsCachedBoard;
    private Canvas canvas = null;
    private int[] cameraPosOnCellsX, cameraPosOnCellsY;
    public int backgroundBoardColor;

    public Board(String tileSet, String mapFile, Canvas canvas,
            int cellWidth, int cellHeight, int extraHeight, int backgroundBoardColor) {
        this.backgroundBoardColor = backgroundBoardColor;
        this.canvas = canvas;
        this.tileSet = tileSet;

        // Load the map
        map = new Map(mapFile);

        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
        this.extraHeight = extraHeight;
        // Set initial capacity pools
        Utility.setInitCapacityPools(100, 10);
        // Create cached board
        cachedBoard = Image.createImage(screenWidth, screenHeight);
        graphicsCachedBoard = cachedBoard.getGraphics();
        // Create array of pos on cells for camera (for each vertex)
        cameraPosOnCellsX = new int[NUM_VERTEXES_CAMERA];
        cameraPosOnCellsY = new int[NUM_VERTEXES_CAMERA];
        // Initialize tile size and camera
        setCellSize(cellWidth, cellHeight);

    }

    public void setCellSize(int cellWidth, int cellHeight) {

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        hCellWidth = cellWidth >> 1;
        hCellHeight = cellHeight >> 1;
        cellTanXFP = (cellWidth << 8) / cellHeight;			// fixed point (8.8)
        cellTanYFP = (cellHeight << 8) / cellWidth;			// fixed point (8.8)
        startX = hCellWidth * map.height;
        startY = 0;
        startXFP = startX << 8;
        startYFP = startY << 8;
        setCamera(cameraX1, cameraY1);

    }

    public void setCamera(int mapX, int mapY) {

        int otherX, otherY;

        cameraX1 = mapX;
        cameraY1 = mapY;
        cameraX2 = mapX + screenWidth;
        cameraY2 = mapY + screenHeight + extraHeight;
        calcPosOnCellsForCamera(0, cameraX1, cameraY1);
        calcPosOnCellsForCamera(1, cameraX2, cameraY1);
        calcPosOnCellsForCamera(2, cameraX2, cameraY2);
        calcPosOnCellsForCamera(3, cameraX1, cameraY2);
        initialCellX = cameraPosOnCellsX[1];
        initialCellY = cameraPosOnCellsY[1];
        numPass = 0;
        otherX = getPosOnMapXFromCell(cameraPosOnCellsX[1] - 1, cameraPosOnCellsY[1]);
        otherY = getPosOnMapYFromCell(cameraPosOnCellsX[1] - 1, cameraPosOnCellsY[1]) + cellHeight;
        if (otherX < cameraX2 && otherY >= cameraY1) {
            initialCellX--;
            numPass++;
        }
        otherX = getPosOnMapXFromCell(cameraPosOnCellsX[1] + 1, cameraPosOnCellsY[1]) - hCellWidth;
        otherY = getPosOnMapYFromCell(cameraPosOnCellsX[1] + 1, cameraPosOnCellsY[1]);
        if (otherX < cameraX2 && otherY < cameraY2) {
            numPass++;
        }
        otherX = getPosOnMapXFromCell(cameraPosOnCellsX[0], cameraPosOnCellsY[0]);
        if ((cameraX1 <= otherX && (otherX - cameraX1) % cellWidth >= hCellWidth)
                || (cameraX1 > otherX && (cameraX1 - otherX) % cellWidth < hCellWidth)) {
            otherActionEdgeA = true;
        } else {
            otherActionEdgeA = false;
        }
        otherY = getPosOnMapYFromCell(cameraPosOnCellsX[2], cameraPosOnCellsY[2]);
        if ((cameraY2 - otherY) % cellHeight < hCellHeight) {
            otherActionEdgeB = true;
        } else {
            otherActionEdgeB = false;
        }
        changedCachedBoard = true;

    }

    private void calcPosOnCellsForCamera(int numVertex, int mapX, int mapY) {

        int x1, y1, y2;

        mapX <<= 8;
        mapY <<= 8;
        y1 = startYFP + ((startXFP - mapX) * cellTanYFP >> 8);
        y2 = mapY - (mapY - y1 >> 1);
        x1 = startXFP - ((y2 - startYFP) * cellTanXFP >> 8);
        cameraPosOnCellsX[numVertex] = (mapX - x1) / hCellWidth >> 8;
        cameraPosOnCellsY[numVertex] = (y2 - startYFP) / hCellHeight >> 8;

    }

    public void draw(Graphics g) {

        Enumeration charEnum, iter;
        Element elementTemp = null;

        if (changedCachedBoard) {
            changedCachedBoard = false;
            graphicsCachedBoard.setClip(0, 0, screenWidth, screenHeight);
            graphicsCachedBoard.setColor(backgroundBoardColor);
            graphicsCachedBoard.fillRect(0, 0, screenWidth, screenHeight);
            drawBoard(graphicsCachedBoard, null, false, true);
        }
        g.setClip(0, 0, screenWidth, screenHeight);
        g.drawImage(cachedBoard, 0, 0, Graphics.TOP | Graphics.LEFT);
        charEnum = elements.elements();
        for (iter = charEnum; iter.hasMoreElements();) {
            elementTemp = (Element) iter.nextElement();
            initializeDrawElement(elementTemp);
        }
        drawBoard(g, null, true, false);
        charEnum = elements.elements();
        for (iter = charEnum; iter.hasMoreElements();) {
            elementTemp = (Element) iter.nextElement();
            drawBoard(g, elementTemp, false, false);
        }

    }

    private void drawBoard(Graphics g, Element element,
            boolean drawOnlyElements, boolean drawOnlyTiles) {

        boolean exit = false, checkPrevLine = false;
        int y, edgeAX, edgeAY, edgeBX, edgeBY, verseEdgeA, verseEdgeB;
        int posOnMapX, posOnMapY, posOnCellsX, posOnCellsY, tileDeltaHeight;
        int posOnCellsDeltaX, posOnCellsDeltaY;
        Element currElement;

        if (!drawOnlyElements && !drawOnlyTiles) {
            if (element != null && element.isVisible) {
                element.setClip(g);
                y = getPosOnMapYFromCell(element.posOnCellsCurrX, element.posOnCellsCurrY);
                if ((element.posOnMapY - y) % cellHeight < hCellHeight) {
                    checkPrevLine = true;
                }
            } else {
                return;
            }
        }
        edgeAX = initialCellX;
        edgeAY = initialCellY;
        edgeBX = initialCellX + numPass;
        edgeBY = initialCellY;
        verseEdgeA = 0;
        verseEdgeB = 0;
        do {
            posOnCellsY = edgeAY;
            if (posOnCellsY >= 0 && posOnCellsY < map.height) {
                posOnMapX = getPosOnMapXFromCell(edgeAX, posOnCellsY) - cameraX1;
                posOnMapY = getPosOnMapYFromCell(edgeAX, posOnCellsY) - cameraY1;
                if (drawOnlyElements) {
                    for (posOnCellsX = edgeAX; posOnCellsX <= edgeBX; posOnCellsX++, posOnMapX += hCellWidth, posOnMapY += hCellHeight) {
                        if (posOnCellsX >= 0 && posOnCellsX < map.width) {
                            currElement = isElement(posOnCellsX, posOnCellsY);
                            if (currElement != null) {
                                currElement.draw(g);
                            }
                        }
                    }
                } else if (drawOnlyTiles) {
                    for (posOnCellsX = edgeAX; posOnCellsX <= edgeBX; posOnCellsX++, posOnMapX += hCellWidth, posOnMapY += hCellHeight) {
                        if (posOnCellsX >= 0 && posOnCellsX < map.width) {
                            map.setCellDeltaHeight(posOnCellsX, posOnCellsY,
                                    drawTile(g, posOnCellsX, posOnCellsY, posOnMapX, posOnMapY, false));
                        }
                    }
                } else {
                    for (posOnCellsX = edgeAX; posOnCellsX <= edgeBX; posOnCellsX++, posOnMapX += hCellWidth, posOnMapY += hCellHeight) {
                        if (posOnCellsX >= 0 && posOnCellsX < map.width) {
                            posOnCellsDeltaX = element.posOnCellsCurrX - posOnCellsX;
                            posOnCellsDeltaY = element.posOnCellsCurrY - posOnCellsY;
                            if ((posOnCellsDeltaX != 0 || posOnCellsDeltaY != 0)
                                    && (checkPrevLine && posOnCellsDeltaX == -1 && posOnCellsDeltaY == 1)
                                    || (!checkPrevLine && (posOnCellsDeltaX == -1 || posOnCellsDeltaX == -2) && posOnCellsDeltaY == 0)
                                    || (checkPrevLine && posOnCellsDeltaX <= 0 && posOnCellsDeltaY <= 0)
                                    || (!checkPrevLine && posOnCellsDeltaX <= 0 && posOnCellsDeltaY <= -1)) {
                                tileDeltaHeight = map.getCellDeltaHeight(posOnCellsX, posOnCellsY);
                                if (tileDeltaHeight > 0) {
                                    if (isInBoundingClip(g, posOnMapX - hCellWidth, posOnMapY - tileDeltaHeight,
                                            posOnMapX + hCellWidth, posOnMapY + tileDeltaHeight + cellHeight)) {
                                        drawTile(g, posOnCellsX, posOnCellsY, posOnMapX, posOnMapY, true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (verseEdgeA == 1) {
                edgeAX++;
                edgeAY++;
                if (edgeAY > cameraPosOnCellsY[3]) {
                    exit = true;
                }
            } else {
                edgeAX--;
                edgeAY++;
                if (edgeAX < cameraPosOnCellsX[0]) {
                    if (edgeAY > cameraPosOnCellsY[0]) {
                        if (otherActionEdgeA) {
                            edgeAX += 2;
                        } else {
                            edgeAX++;
                        }
                        verseEdgeA = 1;
                    } else {
                        if (otherActionEdgeA) {
                            edgeAX++;
                            verseEdgeA = 1;
                        } else {
                            edgeAX++;
                        }
                    }
                }
            }
            if (verseEdgeB == 1) {
                edgeBX--;
                edgeBY++;
                if (edgeBY > cameraPosOnCellsY[3]) {
                    exit = true;
                }
            } else {
                edgeBX++;
                edgeBY++;
                if (edgeBX > cameraPosOnCellsX[2]) {
                    if (edgeBY > cameraPosOnCellsY[2]) {
                        if (otherActionEdgeB) {
                            edgeBX -= 2;
                        } else {
                            edgeBX--;
                        }
                        verseEdgeB = 1;
                    } else {
                        if (otherActionEdgeB) {
                            edgeBX--;
                            verseEdgeB = 1;
                        } else {
                            edgeBX--;
                        }
                    }
                }
            }
        } while (!exit);

    }

    private void initializeDrawElement(Element tempElement) {

        boolean changeDirection = false;
        int modX = 0, modY = 0, dir = 0;
        int posOnCellsNewX, posOnCellsNewY;

        if (tempElement.isMoving) {
            if (tempElement.modXSW != null && tempElement.getCurrentAction().indexOf("SW") > 0) {
                modX = tempElement.modXSW[tempElement.modCurr];
                modY = tempElement.modYSW[tempElement.modCurr];
                dir = 1;
                tempElement.modCurr++;
                if (tempElement.modCurr >= tempElement.modXSW.length) {
                    tempElement.isMoving = false;
                }
            } else if (tempElement.modXNW != null && tempElement.getCurrentAction().indexOf("NW") > 0) {
                modX = tempElement.modXNW[tempElement.modCurr];
                modY = tempElement.modYNW[tempElement.modCurr];
                dir = 2;
                tempElement.modCurr++;
                if (tempElement.modCurr >= tempElement.modXNW.length) {
                    tempElement.isMoving = false;
                }
            } else if (tempElement.modXSE != null && tempElement.getCurrentAction().indexOf("SE") > 0) {
                modX = tempElement.modXSE[tempElement.modCurr];
                modY = tempElement.modYSE[tempElement.modCurr];
                dir = 3;
                tempElement.modCurr++;
                if (tempElement.modCurr >= tempElement.modXSE.length) {
                    tempElement.isMoving = false;
                }
            } else if (tempElement.modXNE != null && tempElement.getCurrentAction().indexOf("NE") > 0) {
                modX = tempElement.modXNE[tempElement.modCurr];
                modY = tempElement.modYNE[tempElement.modCurr];
                dir = 4;
                tempElement.modCurr++;
                if (tempElement.modCurr >= tempElement.modXNE.length) {
                    tempElement.isMoving = false;
                }
            }
            if (tempElement.modCurr == 1 && dir != 0) {
                posOnCellsNewX = tempElement.posOnCellsCurrX;
                posOnCellsNewY = tempElement.posOnCellsCurrY;
                if (dir == 1) {
                    posOnCellsNewY++;
                } else if (dir == 2) {
                    posOnCellsNewX--;
                } else if (dir == 3) {
                    posOnCellsNewX++;
                } else {
                    posOnCellsNewY--;
                }
                if (canGo(tempElement.posOnCellsCurrX, tempElement.posOnCellsCurrY, posOnCellsNewX, posOnCellsNewY)
                        && moveElement(tempElement, posOnCellsNewX, posOnCellsNewY, false)) {
                    tempElement.posOnCellsCurrX = posOnCellsNewX;
                    tempElement.posOnCellsCurrY = posOnCellsNewY;
                } else {
                    changeDirection = true;
                    tempElement.isMoving = false;
                }
            } else if (dir == 0) {
                changeDirection = true;
                tempElement.isMoving = false;
            }
            if (tempElement.isMoving) {
                tempElement.posOnMapX += modX;
                tempElement.posOnMapY += modY;
            } else {
                if (!changeDirection) {
                    tempElement.posOnMapX += modX;
                    tempElement.posOnMapY += modY;
                    if (tempElement.posOnCellsCurrX != tempElement.posOnCellsFinalX
                            || tempElement.posOnCellsCurrY != tempElement.posOnCellsFinalY) {
                        tempElement.isMoving = true;
                        tempElement.frameCurr = 0;
                        tempElement.modCurr = 0;
                    }
                }
            }
        }
        tempElement.initializeDraw(cameraX1, cameraY1, cameraX2, cameraY2 - extraHeight);

    }

    private int drawTile(Graphics g, int cellX, int cellY, int screenX, int screenY,
            boolean ignoreBoundingClip) {

        int tile, tileDeltaHeight = 0;
        String imageCode = "";
        Image tempImage = null;

        tile = map.getCell(cellX, cellY);
        if (tile != Map.DEFAULT_TILE) {
            try {
                imageCode = Integer.toString(tile);
                tempImage = Utility.loadImageStaticPool(tileSet + imageCode);
            } catch (Exception e) {
            }
            if (tempImage != null) {
                tileDeltaHeight = tempImage.getHeight() - cellHeight;
                screenX -= hCellWidth;
                screenY -= tileDeltaHeight;
                if (ignoreBoundingClip
                        || isInBoundingClip(g, screenX, screenY, screenX + cellWidth, screenY + tempImage.getHeight())) {
                    g.drawImage(tempImage, screenX, screenY, Graphics.TOP | Graphics.LEFT);
                }
            }
        }

        return tileDeltaHeight;

    }

    public int getPosOnMapXFromCell(int cellX, int cellY) {

        return startX - hCellWidth * cellY + hCellWidth * cellX;

    }

    public int getPosOnMapYFromCell(int cellX, int cellY) {

        return startY + hCellHeight * cellY + hCellHeight * cellX;

    }

    public int getPosOnCellsXFromMap(int mapX, int mapY) {

        int x1, y1, y2;

        mapX <<= 8;
        mapY <<= 8;
        y1 = startYFP + ((startXFP - mapX) * cellTanYFP >> 8);
        y2 = mapY - (mapY - y1 >> 1);
        x1 = startXFP - ((y2 - startYFP) * cellTanXFP >> 8);

        return (mapX - x1) / hCellWidth >> 8;
    }

    public int getPosOnCellsYFromMap(int mapX, int mapY) {

        int y1, y2;

        mapX <<= 8;
        mapY <<= 8;
        y1 = startYFP + ((startXFP - mapX) * cellTanYFP >> 8);
        y2 = mapY - (mapY - y1 >> 1);

        return (y2 - startYFP) / hCellHeight >> 8;
    }

    /**
     * If there is a Element in the x and y position of the Board it returns its
     * name. Return null if there is no element.
     *
     * @param cellX x
     * @param cellY y
     * @return Element
     */
    public Element isElement(int cellX, int cellY) {

        if (elementsPositions.containsKey(cellX + "_" + cellY)) {
            return (Element) elementsPositions.get(cellX + "_" + cellY);
        } else {
            return null;
        }

    }

    /**
     * Move a Element on the Board
     *
     * @param c Elenent to move
     * @param posOnCellsNewX new pos x on cells
     * @param posOnCellsNewY new pos y on cells
     * @param overwrite true if you want to overwrite an existing Element that
     * stays in the new position
     * @return true if the movement was done
     */
    public boolean moveElement(Element c, int posOnCellsNewX, int posOnCellsNewY, boolean overwrite) {

        if (elementsPositions.containsKey(c.posOnCellsCurrX + "_" + c.posOnCellsCurrY)) {
            if (!overwrite) {
                if (!elementsPositions.containsKey(posOnCellsNewX + "_" + posOnCellsNewY)) {
                    elementsPositions.put(posOnCellsNewX + "_" + posOnCellsNewY, c);
                    elementsPositions.remove(c.posOnCellsCurrX + "_" + c.posOnCellsCurrY);

                    return true;
                }
            } else {
                elementsPositions.put(posOnCellsNewX + "_" + posOnCellsNewY,
                        elementsPositions.get(c.posOnCellsCurrX + "_" + c.posOnCellsCurrY));
                elementsPositions.remove(c.posOnCellsCurrX + "_" + c.posOnCellsCurrY);

                return true;
            }
        }

        return false;

    }

    private boolean isInBoundingBox(int boxX1, int boxY1, int boxX2, int boxY2,
            int x1, int y1, int x2, int y2) {

        if (((y1 >= boxY1 && y1 < boxY2) || (y2 >= boxY1 && y2 < boxY2) || (y1 < boxY1 && y2 >= boxY2))
                && ((x1 >= boxX1 && x1 < boxX2) || (x2 >= boxX1 && x2 < boxX2) || (x1 < boxX1 && x2 >= boxX2))) {
            return true;
        } else {
            return false;
        }

    }

    private boolean isInBoundingClip(Graphics g, int x1, int y1, int x2, int y2) {

        int boundX1, boundY1, boundX2, boundY2;

        boundX1 = g.getClipX();
        boundY1 = g.getClipY();
        boundX2 = boundX1 + g.getClipWidth();
        boundY2 = boundY1 + g.getClipHeight();
        if (((y1 >= boundY1 && y1 < boundY2) || (y2 >= boundY1 && y2 < boundY2) || (y1 < boundY1 && y2 >= boundY2))
                && ((x1 >= boundX1 && x1 < boundX2) || (x2 >= boundX1 && x2 < boundX2) || (x1 < boundX1 && x2 >= boundX2))) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Add a Element to the Board.
     *
     * @param c Element object to add
     */
    public void putElement(Element c) {

        elementsPositions.put(c.posOnCellsCurrX + "_" + c.posOnCellsCurrY, c);
        elements.put(c.name, c);

    }

    /**
     * Create and then add a Element to the Board.
     *
     * @param name name of the Element
     * @param posOnCellsX pos x on cells
     * @param posOnCellsY pos y on cells
     */
//	public void putElement(String name, int posOnCellsX, int posOnCellsY) {
//		
//		Element c = new Element(name, posOnCellsX, posOnCellsY);
//		
//		elementsPositions.put(posOnCellsX + "_" + posOnCellsY, c);
//		elements.put(c.name, c);
//	
//	}
    /**
     * Remove a Element from the Board
     *
     * @param c Element object to remove
     */
    public void removeElement(Element c) {

        elementsPositions.remove(c.posOnCellsCurrX + "_" + c.posOnCellsCurrY);
        elements.remove(c.name);

    }

    public boolean isWalkableCell(int cellX, int cellY) {

        int cell = map.getCell(cellX, cellY);

        // Range of walkable tiles (ascii codes)
        if (cell >= MIN_WALKABLE_TILE && cell <= MAX_WALKABLE_TILE) {
            return true;
        } else {
            return false;
        }

    }

    public boolean canGo(int oldCellX, int oldCellY, int newCellX, int newCellY) {

        if (newCellX == oldCellX && newCellY == oldCellY) {
            return false;
        }
        // Check if the map cell is a walkable floor or a wall/building
        if (!isWalkableCell(newCellX, newCellY)) {
            return false;
        }
        // Cannot walk trhou another element
        if (isElement(newCellX, newCellY) != null) {
            return false;
        }

        return true;

    }
}