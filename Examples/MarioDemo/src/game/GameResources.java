/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author joteitti
 */
public class GameResources {

    private static GameGraphics smGraphics;
    private static TiledLayer[] smLayer;
    private static GameAnimation[] smAnimations;
    public static final int LAYER_BACKGROUND = 0;
    public static final int LAYER_COLLISION = 1;
    public static final int LAYER_DESTRUCTABLE = 2;
    public static final int LAYER_MENU_GACKGROUND = 3;
    public static final int ANIMATION_MARIO_IDLE_RIGHT = 0;
    public static final int ANIMATION_MARIO_IDLE_LEFT = 1;
    public static final int ANIMATION_MARIO_MOVE_RIGHT = 2;
    public static final int ANIMATION_MARIO_MOVE_LEFT = 3;
    public static final int ANIMATION_MARIO_JUMP_RIGHT = 4;
    public static final int ANIMATION_MARIO_JUMP_LEFT = 5;
    public static final int ANIMATION_MARIO_LOST = 6;

    public static void initialize() {
        // If initialize is called more than once it might cause memory problems
        smGraphics = new GameGraphics();

        try {
            TiledLayer[] newLayers = new TiledLayer[]{
                smGraphics.getBackground(),
                smGraphics.getCollision(),
                smGraphics.getDestructable(),
                smGraphics.getMenu_bg(),};
            smLayer = newLayers;

            GameAnimation[] newAnimations = new GameAnimation[]{
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioIdleRight, smGraphics.MarioIdleRightDelay, true),
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioIdleLeft, smGraphics.MarioIdleLeftDelay, true),
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioMoveRight, smGraphics.MarioMoveRightDelay, true),
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioMoveLeft, smGraphics.MarioMoveLeftDelay, true),
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioJumpRight, smGraphics.MarioJumpRightDelay, true),
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioJumpLeft, smGraphics.MarioJumpLeftDelay, true),
                new GameAnimation(smGraphics.getMario(), smGraphics.MarioLost, smGraphics.MarioLostDelay, true),};
            smAnimations = newAnimations;
        } catch (IOException e) {
            System.out.println("Failed to initialize game resources: " + e.getMessage());
        }
    }

    public static TiledLayer getLayer(int index) {
        return smLayer[index];
    }

    public static GameAnimation getAnimation(int index) {
        return smAnimations[index];
    }
}
