
import java.util.Random;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author personal
 */
public class GameScreen extends GameCanvas implements Runnable {

    GameMIDlet host;
    private final String GRASS_IMAGE = "/grass.png";
    private final String MR_SMITH_IMAGE = "/mrsmith.png";
    private final String BIRD_IMAGE = "/bird.png";
    private final String ROCK_IMAGE = "/rock.png";
    private final String EXPLOSION_IMAGE = "/explosion.png";
    private LayerManager layerManager;
    private int width = getWidth();
    private int height = getHeight();
    private TiledLayer grassBackground;
    Graphics g;
    //Sprite definition
    private Sprite mrSmith;
    private Sprite bird;
    private Sprite rock;
    private boolean birdHitByStone = false;
    private boolean gameIsOn = true;
    private int score;
    private int birdSpeed = 10;
    private boolean rockIsThrown = false;
    private int rockYSpeed;
    private int rockxSpeed;
    private boolean rockDirection;
    private final int gravity = 2;
    private final static boolean DIRECTION_LEFT = false;
    private final static boolean DIRECTION_RIGHT = true;
    private boolean direction;
    private Random random = new Random(System.currentTimeMillis());
    private Sprite explotion;

    public GameScreen(GameMIDlet host) {
        super(false);
        this.host = host;
        setFullScreenMode(true);
        gameIsOn = true;
        birdHitByStone = false;
        score = 0;
    }

    private Image loadImage(String path) {
        Image image = null;
        try {
            image = Image.createImage(path);

        } catch (Exception e) {
        }
        return image;
    }

    //buat background
    private void createBackground() {
        Image backgroundimage = loadImage(GRASS_IMAGE);
        grassBackground = new TiledLayer(8, 1, backgroundimage, 32, 32);
        int numberOfTiles = width / 32 + 1;
        for (int i = 0; i < numberOfTiles; i++) {
            grassBackground.setCell(i, 0, 1);
        }
        grassBackground.setPosition(0, height - grassBackground.getHeight());
        layerManager.append(grassBackground);


    }

    public void createMrSmith() {
        mrSmith = new Sprite(loadImage(MR_SMITH_IMAGE), 30, 30);
        int xPos = width / 2;
        int yPos = height - mrSmith.getHeight() - grassBackground.getHeight();
        mrSmith.setPosition(xPos, yPos);
        mrSmith.defineReferencePixel(15, 15);
        layerManager.append(mrSmith);
    }

    public void createBird() {
        bird = new Sprite(loadImage(BIRD_IMAGE), 33, 31);
        //int xPos = width/2;
        //int yPos = height - mrSmith.getHeight() - grassBackground.getHeight();
        bird.setPosition(0, 30);
        bird.defineReferencePixel(15, 15);
        layerManager.append(bird);
    }

    public void createRock() {
        rock = new Sprite(loadImage(ROCK_IMAGE), 12, 12);
        // rock.setPosition(mrSmith.getX(), mrSmith.getY()+mrSmith.getHeight());
        rock.defineReferencePixel(6, 6);
        rock.setVisible(false);
        layerManager.append(rock);
    }

    private void moveRock() {
        if (rockYSpeed > -25) {
            rockYSpeed = rockYSpeed - gravity;
        }
        if (rockDirection == DIRECTION_LEFT) {
            rock.move(-rockxSpeed, -rockYSpeed);
        } else {
            rock.move(rockxSpeed, -rockYSpeed);
        }

        if (grassBackground.getY() - rock.getRefPixelY() <= 0) {
            rockIsThrown = false;
            rock.setVisible(false);
        }

    }

    public void createEplotion() {
        explotion = new Sprite(loadImage(EXPLOSION_IMAGE), 75, 75);
        explotion.defineReferencePixel(37, 37);
        explotion.setVisible(false);
        layerManager.append(explotion);
    }

    public void run() {
        while (gameIsOn) {

            getUserInput();
            if (rockIsThrown) {
                moveRock();
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }

            updateScreen();
            if (bird.collidesWith(mrSmith, true) && !birdHitByStone) {
                gameIsOn = false;
            }
            if (rock.collidesWith(bird, direction) && !birdHitByStone) {
                birdHitByStone = true;
            }

        }
        host.showMainScreen();
    }

    private void getUserInput() {
        int ks = getKeyStates();
        if ((ks & RIGHT_PRESSED) != 0) {

            if (mrSmith.getX() < getWidth() - mrSmith.getWidth()) {
                mrSmith.move(3, 0);
                mrSmith.setTransform(Sprite.TRANS_NONE);
                mrSmith.nextFrame();
                direction = DIRECTION_RIGHT;
            }
        }
        if ((ks & LEFT_PRESSED) != 0) {
            if (mrSmith.getX() > 0) {

                //--------------------------------------------------------------------
                mrSmith.setTransform(Sprite.TRANS_MIRROR);
                mrSmith.nextFrame();
                mrSmith.move(-3, 0);
                direction = DIRECTION_LEFT;
            }
        }

        if ((ks & FIRE_PRESSED) != 0) {
            if (rockIsThrown == false) {
                rockIsThrown = true;
                rock.setPosition(mrSmith.getX() + rock.getWidth() / 2, mrSmith.getY() + rock.getHeight() / 2);
                rock.setVisible(true);

                rockYSpeed = getRandom(10) + 25;
                rockxSpeed = getRandom(4) + 3;
                rockDirection = direction;
            }
        }
    }

    private void updateScreen() {
        Graphics g = getGraphics();
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, width, height);

        g.setColor(0, 0, 0);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
        g.drawString("Score : " + score, 2, 2, Graphics.TOP | Graphics.LEFT);

        layerManager.paint(g, 0, 0);
        flushGraphics();
    }

    public void start() {
        layerManager = new LayerManager();
        layerManager.setViewWindow(0, 0, width, height);
        createBackground();
        createMrSmith();
        createBird();
        createEplotion();
        birdSpeed = 5;
        createRock();

        g = getGraphics();
        layerManager.paint(g, 0, 0);

        new Thread(this).start();
        new BirdThread().start();


        Thread runner = new Thread(this);
        runner.start();


    }

    private int getRandom(int i) {

        return Math.abs(random.nextInt() % i);
    }

    class BirdThread extends Thread {

        private int birdSpeedInMS = 50;

        public void run() {
            while (gameIsOn) {
                if (birdHitByStone) {
                    explodeBird();
                } else {
                    moveBird();
                }
                try {
                    Thread.sleep(birdSpeedInMS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void explodeBird() {
            if (bird.isVisible()) {
                bird.setVisible(false);
                explotion.setPosition(bird.getX() - bird.getWidth() / 2, bird.getY() - bird.getHeight() / 2);
                explotion.setVisible(true);//=============================================================perubahan
                explotion.setFrame(0);
            } else {
                if (explotion.getFrame() < 11) {
                    explotion.nextFrame();
                    explotion.setVisible(false);
                    birdHitByStone = false;
                    bird.setVisible(true);
                    bird.setPosition(0, 30);
                    bird.setTransform(Sprite.TRANS_NONE);
                    if (birdSpeedInMS >= 20) {
                        birdSpeedInMS -= 5;
                    }
                    if (birdSpeed < 0) {
                        birdSpeed = birdSpeed * -1;
                    }
                    if (birdSpeed < 15) {
                        birdSpeed += 1;
                    }
                    //tambah score
                    score++;
                }
            }
        }

        private void moveBird() {

            if (bird.getX() < 0) {
                birdSpeed *= -1;
                bird.move(0, 10);
                bird.nextFrame();
                bird.setTransform(Sprite.TRANS_NONE);
            }
            if (bird.getX() > getWidth() - bird.getWidth()) {
                birdSpeed *= -1;
                bird.move(0, 10);
                bird.nextFrame();
                bird.setTransform(Sprite.TRANS_MIRROR);
            }

            bird.move(birdSpeed, 0);
            bird.nextFrame();
        }
    }
}
