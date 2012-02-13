/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author joteitti
 */
public class GameScene {

    public static final int LAYER_BACKGROUND = 1;
    public static final int LAYER_COLLISION = 0;
    public static final int LAYER_DESTRUCTABLE = 0;
    public static final int CELL_EMPTY = 0;
    public static final int CELL_COIN = 120;
    public static final int CELL_BEGIN = -2;
    public static final int CELL_END = 131;
    public static final int GAME_STATE_RUN = 0;
    public static final int GAME_STATE_WIN = 1;
    public static final int GAME_STATE_LOSS = 2;
    public static final int GAME_STATE_RESET = 3;
    public static final int MAX_OBJECTS = 64;
    public static final int PHYSICS_FIXED_TIME = 10;
    public static final int PLAYER_LIVES = 3;
    public static final int SCORE_COIN = 100;
    public static final int LIVES_UP = 1;
    public static final int LIVES_DOWN = -1;
    private GamePlayer mPlayer;
    private GameObject mObjects[];
    private int mPhysicsDeltaTime;
    private static int smScore;
    private static int smLives;
    private static int smGameState;

    public static void addScore(int score) {
        smScore += score;
    }

    public static void addLives(int lives) {
        smLives += lives;
    }

    public static void resetScoreAndLives() {
        smLives = PLAYER_LIVES;
        smScore = 0;
    }

    public static void changeGameState(int gameState) {
        smGameState = gameState;
    }

    public GameScene() {
        // TODO Empty
    }

    public void initialize(boolean resetScoreAndLives) {
        // TODO Reset game graphics

        // Get object layer and read object positions from it
        mPlayer = new GamePlayer(16, 0);

        // Initialize time steps
        mPhysicsDeltaTime = 0;

        // Reset score and lives
        if (resetScoreAndLives) {
            resetScoreAndLives();
        }

        // Change game state
        changeGameState(GAME_STATE_RUN);
    }

    public int logicUpdate(int deltaTime) {
        // TODO Process key events
        while (Game.hasKey()) {
            // Pop key event
            int[] key = Game.popKey();
            if (key == null) {
                continue;
            }

            // Extract key event
            int keyEvent = key[Game.KEY_PARAMETER_EVENT];
            int keyCode = key[Game.KEY_PARAMETER_CODE];

            // TODO Handle softkeys
        }

        // Update player
        mPlayer.logicUpdate(deltaTime);

        // Physics are updated at fixed time step
        mPhysicsDeltaTime += deltaTime;
        while (mPhysicsDeltaTime >= PHYSICS_FIXED_TIME) {
            boolean playerAlive = mPlayer.updatePhysics(PHYSICS_FIXED_TIME);
            if (!playerAlive) {
                addLives(LIVES_DOWN);
                if (smLives < 0) {
                    changeGameState(GAME_STATE_LOSS);
                    break;
                } else {
                    changeGameState(GAME_STATE_RESET);
                    break;
                }
            }

            mPhysicsDeltaTime -= PHYSICS_FIXED_TIME;
        }

        // Check for state change
        return checkForStateChange();
    }

    protected int checkForStateChange() {
        switch (smGameState) {
            case GAME_STATE_WIN:
            case GAME_STATE_LOSS:
                return Game.EVENT_MENU;

            case GAME_STATE_RESET:
                initialize(false);
                break;
        }

        return Game.EVENT_NONE;
    }

    public void draw(Graphics g) {
        int screenWidth = Game.getScreenWidth();
        int screenHeight = Game.getScreenHeight();

        // Get layers
        TiledLayer background = GameResources.getLayer(GameResources.LAYER_BACKGROUND);
        TiledLayer collision = GameResources.getLayer(GameResources.LAYER_COLLISION);
        TiledLayer destructable = GameResources.getLayer(GameResources.LAYER_DESTRUCTABLE);


        // Get scene size
        int sceneWidth = background.getWidth();
        int sceneHeight = background.getHeight();

        // Get player center position
        int playerX = (int) mPlayer.getX();
        int playerY = (int) mPlayer.getY();

        // Get current top left position
        int x = playerX - (screenWidth / 2);
        if (x < 0) {
            x = 0;
        }
        if (x + screenWidth > sceneWidth) {
            x = sceneWidth - screenWidth;
        }

        int y = playerY - (screenHeight / 2);
        if (y < 0) {
            y = 0;
        }
        if (y + screenHeight > sceneHeight) {
            y = sceneHeight - screenHeight;
        }

        // Draw scene
        background.setPosition(0 - x, 0 - y);
        background.paint(g);

        collision.setPosition(0 - x, 0 - y);
        collision.paint(g);

        destructable.setPosition(0 - x, 0 - y);
        destructable.paint(g);

        // Draw objects

        // Draw player
        GameAnimation playerAnimation = GameResources.getAnimation(mPlayer.getAnimationIndex());
        playerAnimation.setElapsed(mPlayer.getAnimationTimer());

        playerAnimation.draw(g, playerX - x, playerY - y);

        // Draw score an lives
        g.setColor(0xFFFF00);
        g.drawString("Score: " + Integer.toString(smScore)
                + " Lives: " + Integer.toString(smLives), 0, 0, Graphics.TOP | Graphics.LEFT);
    }
}
