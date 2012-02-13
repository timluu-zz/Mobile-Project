/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author joteitti
 */
public class GameAnimation {

    private Sprite mSprite;
    private boolean mRepeat;
    private int[] mSequence;
    private int mSequenceDelay;
    private int mElapsedTime;

    public GameAnimation(Sprite sprite, int[] sequence, int sequenceDelay, boolean repeat) {
        // Set sprite
        mSprite = sprite;

        // Set animation properties
        mSequence = sequence;
        mSequenceDelay = sequenceDelay;
        mElapsedTime = 0;
        mRepeat = repeat;
    }

    public int getDuration() {
        return mSequence.length * mSequenceDelay;
    }

    public void setElapsed(int elpasedTime) {
        mElapsedTime = elpasedTime;
    }

    public void draw(Graphics g, int x, int y) {
        // Set sequence
        mSprite.setFrameSequence(mSequence);

        // Go to proper frame
        int currentFrame = 0;
        if (mRepeat) {
            currentFrame = (mElapsedTime / mSequenceDelay) % mSequence.length;
        } else {
            currentFrame = Math.min(mElapsedTime / mSequenceDelay, mSequence.length - 1);
        }
        mSprite.setFrame(Math.max(currentFrame, 0));

        // Set position and visibility
        mSprite.setPosition(x, y);
        mSprite.setVisible(true);

        // Paint sprite
        mSprite.paint(g);
    }

    public Sprite getSprite() {
        return mSprite;
    }
}
