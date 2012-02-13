
/**
 * <p>Title: DemoVaders</p>
 *
 * <p>Description: Building multiple UI Java MIDlets on Symbian OS.</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Symbian Ltd</p>
 *
 * @author Alan Newman - alan@sensibledevelopment.net
 * @version 1.0
 */
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class VaderCanvas extends GameCanvas implements Runnable {

    private Command exit;
    private DemoVaders midlet;
    private VaderManager layerManager;
    private Thread thread;
    private boolean running;
    private final int SLEEP = 10;
    public int height = 0;
    Graphics graphics;
    private long startTime;
    private long totalFrameRate = 0;
    private long averageFrameRate = 0;
    private long totalMemory = 1;
    private long freeMemory = 1;
    private long memoryUsage = 0;
    private long totalMemoryUsage = 0;
    private long memoryHigh = 0;
    private long memoryLow = 0;
    private int loopCount = 1;
    private long averageMemoryUsage = 0;
    private int loops;
    protected final long LOOPS = 10;

    public VaderCanvas(DemoVaders midlet) throws IOException {
        super(false);
        this.midlet = midlet;
        // to set the 'height' variable to circumvent the setViewWindow() bug.
        height = getHeight();
        // full screen cavas removed for performance testing
        // simply uncomment this for full screen mode.	
	/*
         * setFullScreenMode(true);
         */

        // initialize the layer manager
        layerManager = new VaderManager(this);

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public long getAverageFrameRate() {
        return averageFrameRate;
    }

    public long getMemoryHigh() {
        return memoryHigh;
    }

    public long getMemoryLow() {
        return memoryLow;
    }

    public long getAverageMemoryUsage() {
        return averageMemoryUsage;
    }

    public int getCanvasHeight() {
        return height;
    }

    synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    protected void sizeChanged(int width, int height) {
        layerManager.setViewWindow(0, 0, getWidth(), getHeight());
        layerManager.reDrawDisplay(height - this.height);
        graphics.setClip(0, 0, getWidth(), getHeight());
        this.height = height;
    }

    public void run() {
        try {
            loops = 0;
            startTime = System.currentTimeMillis();
            totalMemory = Runtime.getRuntime().totalMemory();
            memoryLow = totalMemory;

            while (running) {
                setPerformance();
                // check for the state of the keys on the phone
                // removed for parity with MIDP 1.0 version.
                inputs();
                // call the method to draw to the display
                repaint();
                // call the method to update the display.
                tick();
                // pause the thread for length of time.
                Thread.sleep(SLEEP);
            }
        } catch (InterruptedException ie) {
            System.out.println(ie.toString());
        }
    }

    synchronized void stop() {
        running = false;
    }

    private void setPerformance() {
        if (++loops >= LOOPS) {
            // get the system now for later comparison
            long now = System.currentTimeMillis();

            // compare 'now' to when the clock was set
            // we have used 10000 instead of 1000, to give us a 'decimal point' (no floating pt!)
            totalFrameRate += (10000 / ((now - startTime) / LOOPS));

            // capture the amount of free memory left
            freeMemory = Runtime.getRuntime().freeMemory();

            //calculate the percentage of used memory
            memoryUsage = (100 - ((freeMemory * 100) / totalMemory));

            // store total memory, and instances, for calculation of 
            // average
            totalMemoryUsage += memoryUsage;


            // work out the memory high and low figures
            if (memoryUsage > memoryHigh) {
                memoryHigh = memoryUsage;
            }
            if (memoryUsage < memoryLow) {
                memoryLow = memoryUsage;
            }

            // calculate the average memory consumption
            averageMemoryUsage = totalMemoryUsage / loopCount;

            // calculate the average frame rate
            averageFrameRate = totalFrameRate / loopCount;

            // increase the number of captures by one
            loopCount++;
            // reset the clock for the next batch of frames
            startTime = System.currentTimeMillis();
            loops = 0;
        }
    }

    private void tick() {
        layerManager.tick();
    }

    public void paint(Graphics g) {
        draw(g);
    }

    private void draw(Graphics g) {
        // paint the sprites and other graphics to the screen
        layerManager.paint(g);
        //flushGraphics();
        serviceRepaints();
    }

    public void inputs() {
        int keyState = getKeyStates();

        // monitor for both types of input from the user
        // both for key pad entry and for jog dial rotation.
        if ((keyState & (LEFT_PRESSED | DOWN_PRESSED)) != 0) {
            layerManager.doGameAction(LEFT);
        } else if ((keyState & (RIGHT_PRESSED | UP_PRESSED)) != 0) {
            layerManager.doGameAction(RIGHT);
        }
    }

    // MIDP 2.1 version
    public void keyPressed(int keyCode) {
        if (getGameAction(keyCode) == FIRE) {
            layerManager.doGameAction(FIRE);
        }
    }
    // from MIDP 1.0 version
  /*
     * public void keyPressed(int keyCode) { if(getGameAction(keyCode)==LEFT) {
     * layerManager.doGameAction(LEFT); } else if(getGameAction(keyCode)==RIGHT)
     * { layerManager.doGameAction(RIGHT); } else
     * if(getGameAction(keyCode)==FIRE) { layerManager.doGameAction(FIRE); }
     *
     * }
     *
     * // from MIDP 1.0 version public void keyRepeated(int keyCode) {
     * if(getGameAction(keyCode)==LEFT && hasRepeatEvents()) {
     * layerManager.doGameAction(LEFT); } else if(getGameAction(keyCode)==RIGHT
     * && hasRepeatEvents()) { layerManager.doGameAction(RIGHT); } }
     */
    /*
     * // this should be removed for the Series 60 version of the app protected
     * void pointerPressed(int x, int y) { layerManager.doGameAction(FIRE);      *
     * }
     */
}
