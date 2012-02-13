import java.io.IOException;


import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Canvas;


/**
 * Net Intruder: canvas class
 * 
 * This software is released under GNU GPL license. View license.txt for more details.
 * 
 * @version 0.9
 * @author Massimo Maria Avvisati (http://www.mondonerd.com)
 * 
 */

//Use http://antenna.sourceforge.net to use following ifdef/ifndef preprocessing instrucions or decomment by yourself according to your needs

//#ifndef nokia
public class netIntruderCanvas extends Canvas implements Runnable {
//#endif

//#ifdef nokia
//# import com.nokia.mid.ui.FullCanvas;
//# public class netIntruderCanvas extends FullCanvas implements Runnable {
//#endif


    private static final long DELAY = 4;

    MIDlet midlet = null; // Parent midlet

    Thread t = null; // Main thread

    netIntruderUtility util = null; // Utility

    netIntruderBlackBox blackBox = null; // Black Box

    /**
     * Main contructor
     */
    public netIntruderCanvas(MIDlet parentMidlet) {
        super();
        this.midlet = parentMidlet;
        t = new Thread(this);
        t.start();

        // All painting, logic and input are performed by following utility
        // object
        util = new netIntruderUtility(this);
        blackBox = new netIntruderBlackBox(this);

    }

    public short gamePhase = 0;

    public short key = -99;

    public boolean fire = false;

    public boolean clearCanvas = true;

    private long timeSpeed = 1100;

    protected void paint(Graphics g) {
        if (clearCanvas) {
            clearCanvas = false;
            util.paintBackground(g);
        }
        switch (gamePhase) {
        case 0: // Intro
            util.paintIntro(g);
            break;
        case 1: // Main menu
            backGamePhase = 1;
            util.paintSimpleImage(g, "/ui/menu.png");
            break;
        case 2: // New Game
            util.paintFullscreenMessage(g, util.msg[2], "");
            if (!util.centered) {
                util.centerBoard();
            } else if (System.currentTimeMillis() - util.lastMessagePainted > 1400) {
                gamePhase = 3;
                util.fullscreenMessagePainted = false;
                clearCanvas = true;
                util.level = 0;
                util.gameTime = util.gameTimeInit;
                util.changeLevel();
                // util.board.cachedBoard = null;
                System.gc();
            }
            break;
        case 3: // Game Play
            backGamePhase = 3;
            util.paintGamePlay(g);
            if (util.board.map.getCell(0, util.player.x, util.player.y) == 58
                    && !util.player.isMoving) { // Level door to next one
                gamePhase = 6;
            }

            break;
        case 4: // Puzzle
            backGamePhase = 4;
            util.paintPuzzle(g);
            break;
        case 5: // Change Level

            util.fullscreenMessagePainted = false;
            clearCanvas = true;
            util.level++;
            if (util.NUMBER_OF_LEVELS > util.level) {
                util.changeLevel();
                gamePhase = 3; // Play next level
            } else {
                gamePhase = 8;// You win the game
            }
            System.gc();
            break;
        case 6: // Completed Level
            util.paintFullscreenMessage(g, util.msgLevel[util.level],
                    util.msg[7]);
            break;
        case 7: // Time over
            util.paintFullscreenMessage(g, util.msg[6], util.msg[7]);
            break;
        case 8: // Victory

            util.paintFullscreenMessage(g, util.msg[5]
                    + blackBox.generateCode(), util.msg[7]);
            break;
        case 9: // Exit screen
            util.paintFullscreenMessage(g, util.msg[0], util.msg[8]);
            break;
        case 10: // About screen
            util.paintFullscreenMessage(g, util.msg[1], util.msg[7]);
            break;
        case 11: // Pause

            break;
        case 12: // Paint Tip
            util.paintFullscreenMessage(g,(String) util.tips.get(util.player.x + "x" + util.player.y),  util.msg[7]);
            break;
            
        case 13: // Paint Rules
            util.paintFullscreenMessage(g, util.rules[rulesCounter], util.msg[9]);
            break;

        case 14: // Paint Reset Message
            util.paintFullscreenMessage(g, util.msg[10], util.msg[7]);
            break;
            
        default:

            break;
        }
        if  (gamePhase == 3 || gamePhase == 4) {
                       
            int time = util.gameTime / (util.gameTimeInit / 100);
            if (running == null) {
                try {
                    running = Image.createImage("/ui/ui_running.png");
                } catch (IOException e) {
                }
            }
            g.setColor(0xcc9900);
            g.drawLine(getWidth() / 2 - 50, running.getHeight() / 2 + 5, getWidth() / 2 + 50 , running.getHeight() / 2 + 5);
            g.drawImage(running, getWidth() / 2 - 50  + time , 2, Graphics.TOP | Graphics.HCENTER);
            
        }
    }
    short rulesCounter = 0;
    Image running = null;
    protected void keyPressed(int keyCode) {
        if (keyCode == KEY_STAR) {
            gamePhase = 9;
        }
        switch (gamePhase) {
        case 0: // Intro
            gamePhase = 1;
            break;
        case 1: // Main menu
            switch (keyCode) {
            case Canvas.KEY_NUM1: // Main menu: start new Game
                gamePhase = 2;
                break;
            case Canvas.KEY_NUM2: // Main menu: view rules
                gamePhase = 13;
                clearCanvas = true;
                break;
            case Canvas.KEY_NUM3: // Main menu: about screen
                gamePhase = 10;
                util.fullscreenMessagePainted = false; // reset the message
                                                        // toggle
                break;
            case Canvas.KEY_NUM4: // Main menu: exit screen
                gamePhase = 9; // Exit Screen
            default:
                break;
            }
            break;
        case 2: // New Game

            break;
        case 3: // Game Play
            if (keyCode != KEY_NUM0 && keyCode != KEY_NUM5 && !fire) {
                fire = true;
                key = (short) keyCode;
            } else {
                if (keyCode == KEY_NUM0
                        && util.board.map.getCell(0, util.player.x,
                                util.player.y) == 57) {
                    util.puzzleStarted = false;
                    gamePhase = 4;
                }
                if (keyCode == KEY_NUM0
                        && util.board.map.getCell(0, util.player.x,
                                util.player.y) == 50 && util.tips.containsKey(util.player.x + "x" + util.player.y)) {
                    gamePhase = 12;
                    util.fullscreenMessagePainted = false; // reset the message
                                                            // toggle
                }
                if (keyCode == KEY_NUM0
                        && util.board.map.getCell(0, util.player.x,
                                util.player.y) == 62 && util.tips.containsKey(util.player.x + "x" + util.player.y)) {
                    gamePhase = 12;
                    util.fullscreenMessagePainted = false; // reset the message
                                                            // toggle
                }
                if (keyCode == KEY_NUM5) {
                    util.initCamera();
                }
            }
            break;
        case 4: // Puzzle
            if (!util.puzzleVerified) {
                util.puzzleVerified = true;
                switch (keyCode) {
                case Canvas.KEY_NUM0:
                    gamePhase = 3;
                    break;
                case Canvas.KEY_NUM1:
                    
                    if (util.puzzleArray[0].indexOf("PASS") > 0) {
                        if (util.puzzlePasswordsTotal > util.puzzlePasswordsFound) {
                            util.puzzleArray[0] = "---------";
                            util.puzzlePasswordsFound++;
                            if (System.currentTimeMillis() - util.puzzleTimer > util.puzzleSpeed / 2) {
                                util.puzzleMark = 0x00cccc;
                            } else {
                                util.puzzleMark = 0xcc00cc;
                                util.gameTime += 1;
                            }
                        }
                    } else {
                        util.puzzleMark = 0xff0000;
                        util.gameTime -= 2;
                        util.puzzlePasswordsFound--;
                        if (util.puzzlePasswordsFound < 0) {
                            util.puzzlePasswordsFound  = 0;
                        }                        
                    }
                    break;
                case Canvas.KEY_NUM3:
                    
                    if (util.puzzleArray[0].indexOf("USER") > 0) {
                        if (util.puzzlePasswordsTotal > util.puzzlePasswordsFound) {
                            util.puzzleArray[0] = "---------";
                            util.puzzlePasswordsFound++;
                            if (System.currentTimeMillis() - util.puzzleTimer > util.puzzleSpeed / 2) {
                                util.puzzleMark = 0x00cccc;
                            } else {
                                util.puzzleMark = 0xcc00cc;
                                util.gameTime += 1;
                            }
                        }
                    } else {
                        util.puzzleMark = 0xff0000;
                        util.gameTime -= 2;
                        util.puzzlePasswordsFound--;
                        if (util.puzzlePasswordsFound < 0) {
                            util.puzzlePasswordsFound  = 0;
                        }
                    }
                    break;
                    default:
                        break;
                }
            }
            break;
        case 5: // Change Level

            break;
        case 6: // Completed Level
            if (keyCode == Canvas.KEY_NUM0) {
                gamePhase = 5;
            }
            break;
        case 7: // Time over
        case 8: // Victory
            if (keyCode == Canvas.KEY_NUM0) {
                gamePhase = 1; // go back to main menu
                util.fullscreenMessagePainted = false; // reset the message
                                                        // toggle
                util.simpleImagePainted = false; // reset the image toggle
            }
            break;
        case 9: // Exit screen
            if (backGamePhase == 1) {
                if (keyCode == KEY_NUM0) {
                    System.gc(); // Free resources
                    midlet.notifyDestroyed(); // Close the game midlet
                }
                if (keyCode == KEY_NUM1) {
                    gamePhase = 1;
                }
            } else {
                if (keyCode == KEY_NUM0) {
                    gamePhase = 1;
                }
                if (keyCode == KEY_NUM1) {
                    gamePhase = backGamePhase;
                }
            }
            util.fullscreenMessagePainted = false; // reset the message
            // toggle
            util.simpleImagePainted = false; // reset the image toggle
            break;
        case 10: // About screen
            if (keyCode == Canvas.KEY_NUM0) {
                gamePhase = 1; // go back to main menu
                util.fullscreenMessagePainted = false; // reset the message
                                                        // toggle
                util.simpleImagePainted = false; // reset the image toggle
            }
            break;
        case 11: // Pause

            break;
        case 12: // Paint Tip
            if (keyCode == Canvas.KEY_NUM0) {
                gamePhase = 3; // go back to game
                util.fullscreenMessagePainted = false; // reset the message
                                                        // toggle
                util.simpleImagePainted = false; // reset the image toggle
            }
            break;
        case 13: // Paint Tip
            if (keyCode == Canvas.KEY_NUM0) {
                gamePhase = 1; // go back to main menu
                rulesCounter = 0; // reset rules messages page
                util.fullscreenMessagePainted = false; // reset the message
                                                        // toggle
                util.simpleImagePainted = false; // reset the image toggle
            } else if (keyCode == Canvas.KEY_NUM1) {
                if (rulesCounter < util.rules.length - 1) {
                    rulesCounter++;
                    
                } else {
                    rulesCounter = 0;
                }
                util.fullscreenMessagePainted = false; // reset the message
                // toggle
util.simpleImagePainted = false; // reset the image toggle
clearCanvas = true;
            }
            break;
            
        case 14: // Paint Reset message
            if (keyCode == Canvas.KEY_NUM0) {
                gamePhase = 3; // go back to game
                util.fullscreenMessagePainted = false; // reset the message
                                                        // toggle
                util.simpleImagePainted = false; // reset the image toggle
            }
            break;
        default:
            break;
        }
    }
    private short backGamePhase = 1;
    protected void keyReleased(int keyCode) {
        fire = false;
        key = -99;
    }

    public void run() {
        Thread current = Thread.currentThread();
        while (current == t) {

            repaint();
            serviceRepaints();
            if ( (gamePhase == 3 || gamePhase == 4 || gamePhase == 12) && System.currentTimeMillis() - util.gameTimeTemp > timeSpeed) {
                util.gameTimeTemp = System.currentTimeMillis();
                util.gameTime -= 1;
            }
            if ((gamePhase == 3  || gamePhase == 5 ||gamePhase == 6 ||gamePhase == 4 || gamePhase == 12) && util.gameTime <= 0) { //Game Over
                if (gamePhase == 12) {
                    gamePhase = 3;
                } else {
                    gamePhase = 7; //Time Over
                }
                
                util.fullscreenMessagePainted = false; // reset the message
                // toggle
                util.simpleImagePainted = false; // reset the image toggle
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
            }

        }

    }

}
