package isoj2me;

import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * <p> isoj2me: Element </p>
 *
 * <p> This class manages Elements. </p>
 *
 * <p> Copyright: Copyright (c) 2004 </p>
 *
 * <p> License: Lesser GPL (http://www.gnu.org) </p>
 *
 * <p> Company: <a href=http://www.mondonerd.com
 * target="_blank">Mondonerd.com</a> </p>
 *
 * @author Massimo Maria Avvisati Giuseppe Perniola
 * @version 0.5
 */
public class Element {

    private static final int ACTION_NONE = 0;
    public static final int ACTION_CALL = 1;
    public boolean isMoving = false, isVisible = false;		// is it in movement?
    // is it visible in camera?
    public String name = "";
    public int posOnCellsCurrX = 0, posOnCellsCurrY = 0;	// Current pos on cells
    public int posOnCellsFinalX = 0, posOnCellsFinalY = 0;	// Final pos on cells (for movement)
    public int posOnMapX = 0, posOnMapY = 0;			// Pos on map (in pixel)
    public int posOnScreenX = 0, posOnScreenY = 0;		// Pos on screen (in pixel)
    public int posOffsetX = 0, posOffsetY = 0; 			// Offsets for pos on screen (in pixel)
    public int frameCurr = 0;							// Current frame
    public int frameDuration = 500;						// Duration of a frame
    public int modCurr = 0;								// Current movement translate factor
    public int currWidth = 0, currHeight = 0;
    public int[] modXSW, modYSW;						// Movement traslate factors
    public int[] modXNW, modYNW;
    public int[] modXSE, modYSE;
    public int[] modXNE, modYNE;
    private long startTime = System.currentTimeMillis(), endTime = 0;
    private String currentAction = null;
    private Hashtable actions = new Hashtable(6);
    private Hashtable frames = new Hashtable();
    private Image currImage = null;
    public int variant;
    public int type = 0;
    public int action = 0;
    public long timer = System.currentTimeMillis();
    public long delay = 3000;

    public Element() {
    }

    /**
     * Create a new element.
     *
     * @param name Name/code of the element
     */
    public Element(String name) {

        this.name = name;
        Utility.imagesDynamicPool = frames;

    }

    /**
     * Create a new element
     *
     * @param name Name or code of the element
     * @param posOnCellsX pos x on cells
     * @param posOnCellsX pos y on cells
     */
    public Element(String name, int posOnCellsX, int posOnCellsY, int variant, int type) {

        this.name = name;
        this.variant = variant;
        this.type = type;
        this.posOnCellsCurrX = this.posOnCellsFinalX = posOnCellsX;
        this.posOnCellsCurrY = this.posOnCellsFinalY = posOnCellsY;
        Utility.imagesDynamicPool = frames;
        action = ACTION_NONE;

    }

    public void initializeDraw(int cameraX1, int cameraY1, int cameraX2, int cameraY2) {

        long deltaTime = 0;
        String tempFrame = "";

        endTime = System.currentTimeMillis();
        if (currentAction != null && actions.containsKey(currentAction)) {
            if (frameCurr >= ((Integer) actions.get(currentAction)).intValue() || frameDuration == 0) {
                frameCurr = 0;
            }
            if (frameCurr > 0) {
                tempFrame = Integer.toString(frameCurr);
            }
            currImage = Utility.loadImageDynamicPool(currentAction + tempFrame);
            currWidth = currImage.getWidth();
            currHeight = currImage.getHeight();
            posOnScreenX = posOnMapX - (currWidth >> 1) + posOffsetX;
            posOnScreenY = posOnMapY - currHeight + posOffsetY;
            if (posOnScreenX <= cameraX1 - currWidth
                    || posOnScreenY <= cameraY1 - currHeight
                    || posOnScreenX >= cameraX2 || posOnScreenY >= cameraY2) {
                isVisible = false;
            } else {
                isVisible = true;
            }
            posOnScreenX -= cameraX1;
            posOnScreenY -= cameraY1;
            deltaTime = endTime - startTime;
            if (deltaTime >= frameDuration) {
                changeFrame();
                startTime = System.currentTimeMillis();
            }
        } else {
            isVisible = false;
            currWidth = currHeight = 0;
        }

    }

    public void setClip(Graphics g) {

        if (isVisible) {
            g.setClip(posOnScreenX, posOnScreenY, currWidth, currHeight);
        }

    }

    public void draw(Graphics g) {

        if (currImage != null && isVisible) {
            g.drawImage(currImage, posOnScreenX, posOnScreenY, Graphics.TOP | Graphics.LEFT);
        }

    }

    /**
     * Store an action into this character action list.
     *
     * @param action basic string used to load frames
     * @param frames number of frames for this action
     */
    public void putAction(String action, int frames) {

        actions.put(action, new Integer(frames));

    }

    /**
     * This return the name that identify the action that is performed in this
     * moment by the character
     *
     * @return basic string for this action
     */
    public String getCurrentAction() {

        return currentAction;

    }

    /**
     * Set the current action for this character. The action have to be already
     * in the actions list
     *
     * @param action name of the action to perform
     * @throws Exception
     */
    public void setCurrentAction(String action) {

        if (actions.containsKey(action)) {
            currentAction = action;
        }

    }

    /**
     * Return the name of the Element
     *
     * @return name of the Element
     */
    public String toString() {

        return this.name;

    }

    private void changeFrame() {

        int framesNum;

        if (actions.containsKey(currentAction)) {
            framesNum = ((Integer) actions.get(currentAction)).intValue();
            frameCurr++;
            if (frameCurr >= framesNum) {
                frameCurr = 0;
            }
        } else {
            frameCurr = 0;
        }

    }
}