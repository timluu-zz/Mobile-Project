/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author joteitti
 */
public class GameMenu {

    private static final int MENU_SELECT_PLAY = 0;
    private static final int MENU_SELECT_EXIT = 1;
    private int menuSelection = 0;

    public GameMenu() {
        // Create here all the resources which must create with keyword "new" !
    }

    public void initialize() {
        // TODO Reset all menu resources
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

            // TODO THIS IS A SMALL EXAMPLE
            if (keyEvent != Game.KEY_EVENT_PRESSED) {
                // Process only key press events
                if (keyCode == Game.KEY_CODE_DOWN) {
                    // Move menu selector down
                    if (menuSelection < MENU_SELECT_EXIT) {
                        menuSelection++;
                    }
                } else if (keyCode == Game.KEY_CODE_UP) {
                    // Move menu selector up
                    if (menuSelection > 0) {
                        menuSelection--;
                    }
                } else if (keyCode == Game.KEY_CODE_ACTION || keyCode == Game.KEY_CODE_SOFTKEY_POSITIVE) {
                    // Select current
                    if (menuSelection == MENU_SELECT_PLAY) {
                        return Game.EVENT_PLAY;
                    } else if (menuSelection == MENU_SELECT_EXIT) {
                        return Game.EVENT_SHUTDOWN;
                    }
                } else if (keyCode == Game.KEY_CODE_SOFTKEY_NEGATIVE) {
                    // Go back or exit application
                }
            }
        }

        // TODO Update menu logic

        // return Game.EVENT_PLAY;
        return Game.EVENT_NONE;
    }

    public void draw(Graphics g) {
        int screenWidth = Game.getScreenWidth();
        int screenHeight = Game.getScreenHeight();
        g.setColor(0xccff66);
        g.fillRect(0, 0, screenWidth, screenHeight);
        TiledLayer background = GameResources.getLayer(GameResources.LAYER_MENU_GACKGROUND);
        background.paint(g);

        Font font = g.getFont();
        int fontHeight = font.getHeight();

        // Draw play menu option
        int fontWidth = font.stringWidth("Play");
        if (menuSelection == MENU_SELECT_PLAY) {
            g.setColor(223, 0, 112);
            g.drawRect((screenWidth - fontWidth) / 2 - 6, (screenHeight - fontHeight) / 2 - fontHeight + 6, 3, 3);
        } else {
            g.setColor(0, 223, 112);
        }

        g.setFont(font);
        g.drawString("Play", (screenWidth - fontWidth) / 2,
                (screenHeight - fontHeight) / 2 - fontHeight, Graphics.TOP | Graphics.LEFT);

        // Draw exit menu option
        fontWidth = font.stringWidth("Exit");
        if (menuSelection == MENU_SELECT_EXIT) {
            g.setColor(223, 0, 112);
            g.drawRect((screenWidth - fontWidth) / 2 - 6, (screenHeight - fontHeight) / 2 + fontHeight + 6, 3, 3);
        } else {
            g.setColor(0, 223, 112);
        }
        g.setFont(font);
        g.drawString("Exit", (screenWidth - fontWidth) / 2,
                (screenHeight - fontHeight) / 2 + fontHeight, Graphics.TOP | Graphics.LEFT);
        // TODO Draw menu
    }
}
