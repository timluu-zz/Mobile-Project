
import javax.microedition.lcdui.*;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.midlet.MIDlet;

public class Maze3DMIDlet
        extends MIDlet {

    private final MazeCanvas canvas3D;
    private final Image error;
    private final Image splash;
    private MenuList menuList;

    //Construct the midlet
    public Maze3DMIDlet() {
        error = makeImage("/error.png");
        splash = makeImage("/splash.png");
        canvas3D = new MazeCanvas(this);
        ErrorScreen.init(splash, Display.getDisplay(this));
    }

    public void startApp() {
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current == null) {
            // check that the API is available�
            boolean isApiAvailable =
                    (System.getProperty("microedition.m3g.version") != null);

            menuList = new MenuList(this, isApiAvailable);
            if (!isApiAvailable) {
                quitApp();
            } else {

                Alert splashScreen = new Alert("Maze3D",
                        null,
                        splash,
                        AlertType.INFO);
                splashScreen.setTimeout(3000);

                Display.getDisplay(this).setCurrent(splashScreen, menuList);
            }
        } else {
            // In case the MIDlet has been hidden
            if (current == canvas3D) {
                canvas3D.start();
            }
            Display.getDisplay(this).setCurrent(current);
        }
    }

    public void pauseApp() {
        canvas3D.stop();
    }

    public void destroyApp(boolean unconditional) {
        canvas3D.stop();
    }

    // gets informed when the canvas itself switches the view
    void viewSwitched() {
        menuList.viewSwitched();
    }

    // starts a new ga,e
    void newGame() {
        canvas3D.stop();
        canvas3D.init();
        Display.getDisplay(this).setCurrent(canvas3D);
    }

    // shows the menu thread
    void showMenu() {
        canvas3D.stop();
        Display.getDisplay(this).setCurrent(menuList);
    }

    // quits the app but first stops the game thread
    void quitApp() {
        canvas3D.stop();
        notifyDestroyed();
    }

    // shows the Graphics3D properties list
    void show3DProperties() {
        Graphics3D g3d = Graphics3D.getInstance();
        Display.getDisplay(this).setCurrent(
                new Graphics3DProperties(g3d, this));
    }

    // switches the canvas' view
    void switchView() {
        canvas3D.switchView();
        canvas3D.start();
        Display.getDisplay(this).setCurrent(canvas3D);
    }

    // shows the main canvas
    void showMain() {
        canvas3D.start();
        Display.getDisplay(this).setCurrent(canvas3D);
    }

    // loads a given image by name
    static Image makeImage(String filename) {
        Image image = null;
        try {
            image = Image.createImage(filename);
        } catch (Exception e) {
            System.out.println("error loading image");
        }
        return image;
    }
}
