
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public final class Main extends MIDlet implements Runnable {
    //
    // 本身引用

    static Main s_instance;
    // 记录fps值
    static int nFps = 0;
    private long nnRuntime = 0;
    private int nFrames = 0;
    private long nnStartTime = 0;
    // 游戏界面
    private MyGameCanvas myGameCanvas = null;
    // 系统事件处理
    static boolean isAppPaused = false;
    static boolean isLooping = true;
    static String strVersion = "1.0";
    static boolean isSoundOn = false;
    static boolean isMIDletStarted = false;
    Thread thread = null;

    //
    // 构造
    public Main() {
    }

    // 暂停
    final protected void pauseApp() {
        isAppPaused = true;
    } // void pauseApp()

    // 开始
    final protected void startApp()
            throws javax.microedition.midlet.MIDletStateChangeException {
        if (!isMIDletStarted) {
            isMIDletStarted = true;
            s_instance = this;
            try {
                strVersion = getAppProperty("MIDlet-Version");
            } // try
            catch (Exception ex) {
                ex.printStackTrace();
            } // catch
            RMSSystem.init();
            isSoundOn = RMSSystem.isSoundOn();
            myGameCanvas = new MyGameCanvas();
            RMSSystem.getBackId();
            Display.getDisplay(s_instance).setCurrent(myGameCanvas);
            thread = new Thread(s_instance);
            thread.start();
        }

    } // void startApp()

    // 销毁
    final protected void destroyApp(boolean parm1)
            throws javax.microedition.midlet.MIDletStateChangeException {
        /**
         * @todo Implement this javax.microedition.midlet.MIDlet abstract method
         */
        myGameCanvas = null;
    } // void destroyApp

    /**
     * Implement of run() method in Runnable interface.
     */
    final public void run() {
        try {
            nnStartTime = System.currentTimeMillis();
            // while (gameThread == Thread.currentThread()) {
            while (isLooping) {
                if (Consts.SIS_SHOW_FPS) {
                    // 计算FPS
                    nFrames++;
                    nnRuntime += System.currentTimeMillis() - nnStartTime;
                    if (nnRuntime >= 1000) {
                        nFps = nFrames;
                        nFrames = 0;
                        nnRuntime -= 1000;
                    } // if
                } // if

                nnStartTime = System.currentTimeMillis();
                // if (currCanvas != null && currCanvas.isShown()) {
                if (myGameCanvas != null) {
                    myGameCanvas.tick();
                } // if
                else {
                    // System.out.println("Do nothing:)");
                } // else

                long timeTaken = System.currentTimeMillis() - nnStartTime;
                if (timeTaken < Consts.SN_MILLIS_PER_TICK) {
                    synchronized (this) {
                        Thread.sleep(Consts.SN_MILLIS_PER_TICK - timeTaken);
                    } // synchronized
                } // if
                else {
                    Thread.yield();
                } // else
            } // while
            if (myGameCanvas != null) {
                RMSSystem.saveSound(Main.isSoundOn);
                // SOUND
            }
            myGameCanvas = null;
            notifyDestroyed();
        } // try
        catch (InterruptedException ie) {
            ie.printStackTrace();
        } // catch
        catch (Exception e) {
            e.printStackTrace();
        } // catch

    } // run()
} // class
