/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author joteitti
 */
public class GameObject {

    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_ENEMY = 1;
    public static final int NO_COLLISION = Integer.MAX_VALUE;
    public static final int POSITION_FIX = 1;
    public static final float GRAVITATION = 0.001f;
    public static final float MOVE_SPEED = 0.17f;
    public static final float MAX_SPEED = 0.50f;
    // TODO Kills performance on some devices
    protected float mX;
    protected float mY;
    // TODO Kills performance on some devices
    protected float mSpeedX;
    protected float mSpeedY;
    protected boolean mAllowJumping;
    protected int mWidth;
    protected int mHeight;
    protected int mPivotX;
    protected int mPivotY;
    protected int mType;
    protected int mAnimationIndex;
    protected int mAnimationTimer;

    public GameObject(int x, int y, int type, int index) {
        // Set properties
        setPosition(x, y);
        setType(type);
        setAnimationIndex(index);
        resetAnimationTimer();
    }

    public int logicUpdate(int deltaTime) {
        // Update animation timer
        mAnimationTimer += deltaTime;

        // Update animation index
        updateAnimationIndex();

        // TODO Update object logic
        return Game.EVENT_NONE;
    }

    public boolean updatePhysics(int deltaTime) {
        // Get collision and background layers
        TiledLayer collision = GameResources.getLayer(GameResources.LAYER_COLLISION);
        TiledLayer background = GameResources.getLayer(GameResources.LAYER_BACKGROUND);

        // Calculate new position
        float newX = (float) mX + ((float) deltaTime * mSpeedX);
        float newY = (float) mY + ((float) deltaTime * mSpeedY);

        // Is player out of borders
        boolean objectAlive = true;

        // Test for border collision
        if (newX < 0.0f) {
            newX = 0.0f;
        } else if (newX + mWidth >= background.getWidth()) {
            newX = background.getWidth() - mWidth - POSITION_FIX;
        }

        if (newY < 0.0f) {
            newY = 0.0f;
        } else if (newY + mHeight > background.getHeight()) {
            newY = background.getHeight() - mHeight - POSITION_FIX;

            // Player should lost one live and position reseted
            objectAlive = false;
        }

        // Horizontal movement first
        if (mSpeedX > 0) {
            // Moving to the right
            int verticalCollision = testForVerticalCollision((int) newX + mWidth, (int) mY);
            if (verticalCollision != NO_COLLISION) {
                //System.out.println("Collision right!");
                mX = verticalCollision * collision.getCellWidth() - mWidth - POSITION_FIX;
            } else {
                mX = newX;
            }
        } else if (mSpeedX < 0) {
            // Moving to the left
            int verticalCollision = testForVerticalCollision((int) newX, (int) mY);
            if (verticalCollision != NO_COLLISION) {
                //System.out.println("Collision left!");
                mX = (verticalCollision + 1) * collision.getCellWidth() + POSITION_FIX;
            } else {
                mX = newX;
            }
        }

        // Vertical movement secondly
        if (mSpeedY < 0) {
            // Moving to the top
            int horizontalCollision = testForHorizontalCollision((int) mX, (int) newY);
            if (horizontalCollision != NO_COLLISION) {
                //System.out.println("Collision top!");
                mY = (horizontalCollision + 1) * collision.getCellHeight() + POSITION_FIX;
                mSpeedY = 0.0f;
            } else {
                mY = newY;
                mSpeedY += deltaTime * GRAVITATION;
            }
        } else {
            // Moving to the bottom
            int horizontalCollision = testForHorizontalCollision((int) mX, (int) newY + mHeight);
            if (horizontalCollision != NO_COLLISION) {
                //System.out.println("Collision bottom!");
                mY = horizontalCollision * collision.getCellHeight() - mHeight - POSITION_FIX;

                // TODO Update speed?

                // Allow jumping
                mAllowJumping = true;
            } else {
                // Floating in the air
                mY = newY;
                mSpeedY += deltaTime * GRAVITATION;

                if (mSpeedY > MAX_SPEED) {
                    mSpeedY = MAX_SPEED;
                }
            }
        }

        return objectAlive;
    }

    protected int testForHorizontalCollision(int x, int y) {
        // Get collision layer
        TiledLayer collision = GameResources.getLayer(GameResources.LAYER_COLLISION);

        // Calculate tile x position and end of the test
        int tileX = x - (x % collision.getCellWidth());
        int testXEnd = x + mWidth;

        // Get tile row and column
        int tileRow = y / collision.getCellHeight();
        int tileColumn = tileX / collision.getCellWidth();

        // Per pixel collision check
        while (tileX <= testXEnd) {
            checkForDestruction(tileColumn, tileRow);

            if (collision.getCell(tileColumn, tileRow) != 0) {
                return tileRow;
            }

            // Increase
            tileColumn++;
            tileX += collision.getCellWidth();
        }

        return NO_COLLISION;
    }

    protected int testForVerticalCollision(int x, int y) {
        // Get collision layer
        TiledLayer collision = GameResources.getLayer(GameResources.LAYER_COLLISION);

        // Calculate tile x position and end of the test
        int tileY = y - (y % collision.getCellHeight());
        int testYEnd = y + mHeight;

        // Get tile row and column
        int tileColumn = x / collision.getCellWidth();
        int tileRow = tileY / collision.getCellHeight();

        // Per pixel collision check
        while (tileY <= testYEnd) {
            checkForDestruction(tileColumn, tileRow);

            if (collision.getCell(tileColumn, tileRow) != 0) {
                return tileColumn;
            }

            // Increase
            tileRow++;
            tileY += collision.getCellHeight();
        }

        return NO_COLLISION;
    }

    protected void checkForDestruction(int column, int row) {
        // This is empty for basic objects
    }

    public int getAnimationIndex() {
        return mAnimationIndex;
    }

    public int getAnimationTimer() {
        return mAnimationTimer;
    }

    public void resetAnimationTimer() {
        mAnimationTimer = 0;
    }

    public void updateAnimationIndex() {
        // This will be called when collision occurs
    }

    public void setAnimationIndex(int index) {
        mAnimationIndex = index;

        // Get animation properties
        Sprite sprite = GameResources.getAnimation(index).getSprite();
        mWidth = sprite.getWidth();
        mHeight = sprite.getHeight();
        mPivotX = sprite.getRefPixelX();
        mPivotY = sprite.getRefPixelY();
    }

    public void setType(int type) {
        mType = type;

        // TODO Update animation index
        setAnimationIndex(0);
    }

    public int getType() {
        return mType;
    }

    public void setPosition(float x, float y) {
        mX = x;
        mY = y;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public void setSpeed(float speedX, float speedY) {
        mSpeedX = speedX;
        mSpeedY = speedY;
    }

    public float getSpeedX() {
        return mSpeedX;
    }

    public float getSpeedY() {
        return mSpeedY;
    }

    public int getPivotX() {
        return mPivotX;
    }

    public int getPivotY() {
        return mPivotY;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
