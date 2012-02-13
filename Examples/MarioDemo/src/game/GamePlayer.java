/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author joteitti
 */
public class GamePlayer extends GameObject {

    public static final float JUMP_SPEED = -0.45f;
    protected boolean mFacingRight;

    public GamePlayer(int positionX, int positionY) {
        super(positionX, positionY, GameObject.TYPE_PLAYER, GameResources.ANIMATION_MARIO_IDLE_RIGHT);

        // Player is looking to right at the begin
        mFacingRight = true;
    }

    public boolean updatePhysics(int deltaTime) {
        // Update facing direction
        if (mSpeedX != 0.0f) {
            mFacingRight = mSpeedX > 0 ? true : false;
        }

        // Reset horizontal speed
        mSpeedX = 0.0f;

        // Handle key states
        int keyStates = Game.getCurrentKeyStates();
        if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
            mSpeedX = GameObject.MOVE_SPEED;
        }
        if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
            mSpeedX = -GameObject.MOVE_SPEED;
        }
        if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
            if (mAllowJumping) {
                mSpeedY = JUMP_SPEED;
                mAllowJumping = false;
            }
        }

        return super.updatePhysics(deltaTime);
    }

    public void updateAnimationIndex() {
        if (mAllowJumping && mSpeedX != 0.0f) {
            setAnimationIndex(mFacingRight ? GameResources.ANIMATION_MARIO_MOVE_RIGHT : GameResources.ANIMATION_MARIO_MOVE_LEFT);
        } else if (!mAllowJumping) {
            setAnimationIndex(mFacingRight ? GameResources.ANIMATION_MARIO_JUMP_RIGHT : GameResources.ANIMATION_MARIO_JUMP_LEFT);
        } else {
            setAnimationIndex(mFacingRight ? GameResources.ANIMATION_MARIO_IDLE_RIGHT : GameResources.ANIMATION_MARIO_IDLE_LEFT);
        }
    }

    protected void checkForDestruction(int column, int row) {
        // Get destruction layer
        TiledLayer destructable = GameResources.getLayer(GameResources.LAYER_DESTRUCTABLE);

        boolean destroyCell = false;
        int index = destructable.getCell(column, row);
        switch (index) {
            case GameScene.CELL_END:
                GameScene.changeGameState(GameScene.GAME_STATE_WIN);
                break;

            case GameScene.CELL_COIN:
                GameScene.addScore(GameScene.SCORE_COIN);

                destroyCell = true;
                break;
        }

        if (destroyCell) {
            destructable.setCell(column, row, GameScene.CELL_EMPTY);
        }
    }
}
