
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.*;

public class RPGCanvas extends GameCanvas implements Runnable {

    private static final int X = 0;
    private static final int Y = 1;
    private static final int WIDTH = 2;
    private static final int HEIGHT = 3;
    private static final int STEP = 3;
    private Hero loader;
    private Sprite hero;
    private Thread thread;
    private TiledLayer layer;
    private Map map;
    private int lastState = -1;
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;
    private int[] view = new int[4];
    private boolean initialized = false;
    private boolean paused = false;
    private Object executionLock = new Object();

    public RPGCanvas() {
        super(true);
        setFullScreenMode(true);
    }

    public void showNotify() {
        if (initialized) {
            synchronized (executionLock) {
                if (paused) {
                    paused = false;
                    executionLock.notify();
                }
            }
        }
    }

    public void hideNotify() {
        synchronized (executionLock) {
            paused = true;
        }
    }

    public void initialize() {
        view[X] = 0;
        view[Y] = 0;
        view[WIDTH] = getWidth();
        view[HEIGHT] = getHeight();

        loader = new Hero();
        loader.load();

        hero = loader.getHero();
        hero.defineCollisionRectangle(
                0,
                0,
                hero.getWidth(),
                hero.getHeight());

        map = new Map();
        map.insert(hero, 0);
        map.setViewWindow(0, 0, getWidth(), getHeight());

        int[] size = getWorldSize();
        WORLD_WIDTH = size[0];
        WORLD_HEIGHT = size[1];

        initialized = true;
    }

    private int[] getWorldSize() {
        int width = 0;
        int height = 0;
        for (int i = 0; i < map.getSize(); i++) {
            Layer layer = map.getLayerAt(i);
            if (layer.getWidth() > width) {
                width = layer.getWidth();
            }
            if (layer.getHeight() > height) {
                height = layer.getHeight();
            }
        }
        return new int[]{width, height};
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        initialized = false;

        thread = null;
    }

    public void run() {
        Graphics g = getGraphics();

        while (initialized) {
            synchronized (executionLock) {
                if (paused) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
            }

            int keyState = getKeyStates();

            if ((keyState & LEFT_PRESSED) != 0) {
                if (lastState != LEFT_PRESSED) {
                    lastState = LEFT_PRESSED;
                    hero.setFrameSequence(new int[]{9, 10, 11});
                } else {
                    hero.nextFrame();
                }
                if (hero.getX() - STEP >= 0) {
                    hero.move(-STEP, 0);
                } else {
                    hero.setPosition(0, hero.getY());
                }
            } else if ((keyState & RIGHT_PRESSED) != 0) {
                if (lastState != RIGHT_PRESSED) {
                    lastState = RIGHT_PRESSED;
                    hero.setFrameSequence(new int[]{3, 4, 5});
                } else {
                    hero.nextFrame();
                }
                if (hero.getX() + hero.getWidth() < WORLD_WIDTH) {
                    hero.move(STEP, 0);
                } else {
                    hero.setPosition(WORLD_WIDTH - hero.getWidth(), hero.getY());
                }
            } else if ((keyState & UP_PRESSED) != 0) {
                if (lastState != UP_PRESSED) {
                    lastState = UP_PRESSED;
                    hero.setFrameSequence(new int[]{0, 1, 2});
                } else {
                    hero.nextFrame();
                }

                if (hero.getY() - STEP >= 0) {
                    hero.move(0, -STEP);
                } else {
                    hero.setPosition(hero.getX(), 0);
                }
            } else if ((keyState & DOWN_PRESSED) != 0) {
                if (lastState != DOWN_PRESSED) {
                    lastState = DOWN_PRESSED;
                    hero.setFrameSequence(new int[]{6, 7, 8});
                } else {
                    hero.nextFrame();
                }
                if (hero.getY() + hero.getHeight() < WORLD_HEIGHT) {
                    hero.move(0, STEP);
                } else {
                    hero.setPosition(hero.getX(), WORLD_HEIGHT - hero.getHeight());
                }
            }

            checkCollision(lastState);

            // Next scroll the view if needed

            if (hero.getX() < view[X] + hero.getWidth()) {
                int dx = (view[X] - hero.getX() + hero.getWidth());
                if (view[X] - dx >= 0) {
                    view[X] -= dx;
                }
            } else if (hero.getX() + hero.getWidth()
                    > (view[X] + view[WIDTH]) - hero.getWidth()) {
                int dx =
                        (hero.getX() + hero.getWidth())
                        - (view[X] + view[WIDTH] - hero.getWidth());

                if (view[X] + view[WIDTH] <= WORLD_WIDTH) {
                    view[X] += dx;
                }
            }

            if (hero.getY() < view[Y] + hero.getHeight()) // scoll up
            {
                int dy = (view[Y] - hero.getY() + hero.getHeight());
                if (view[Y] - dy >= 0) {
                    view[Y] -= dy;
                }
            } else if (hero.getY() + hero.getHeight()
                    > (view[Y] + view[HEIGHT]) - hero.getHeight()) // scroll down
            {
                int dy =
                        (hero.getY() + hero.getHeight())
                        - (view[Y] + view[HEIGHT] - hero.getHeight());

                if (view[Y] + view[HEIGHT] <= WORLD_HEIGHT) {
                    view[Y] += dy;
                }
            }

            map.setViewWindow(
                    view[X],
                    view[Y],
                    view[WIDTH],
                    view[HEIGHT]);

            map.paint(g, 0, 0);

            flushGraphics();

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkCollision(int key) {
        if (hero.collidesWith((TiledLayer) map.getLayerAt(1), true)) {
            if (key == LEFT_PRESSED) {
                hero.move(STEP, 0);
            } else if (key == RIGHT_PRESSED) {
                hero.move(-STEP, 0);
            } else if (key == UP_PRESSED) {
                hero.move(0, STEP);
            } else {
                hero.move(0, -STEP);
            }
        }

    }

    public void paint(Graphics g) {
        if (!initialized) {
            initialize();

            if (thread == null) {
                start();
            }

        }
        super.paint(g);
    }
}