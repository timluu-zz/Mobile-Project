import isoj2me.Board;
import isoj2me.Character;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Net Intruder: utility class
 * 
 * This software is released under GNU GPL license. View license.txt for more details.
 * 
 * @version 0.9
 * @author Massimo Maria Avvisati (http://www.mondonerd.com)
 * 
 */
public class netIntruderUtility {
    private static final String BASE_MAP = "/map";

    public static boolean canMove = false;

    private static final String LEVELS_DEF = "/levels.txt";

    private static final int TILE_HEIGHT = 8;

    private static final String TILE_SET = "base";

    private static final int TILE_WIDTH = 32;
    private Image tipImage = null;
    public void paintTip(Graphics g, String keysSuggested) {
        if (tipImage == null) {
            try {
                tipImage = Image.createImage("/ui/tip.png");
            } catch (IOException e) {
                
            }
        }//TODO better solution possible...
        if (keysSuggested.indexOf("1") >= 0) {
            g.setColor(0xffff00);
            g.fillRect(10  + 4,canvas.getHeight() - 5 - tipImage.getHeight() + 5, 9, 9);
        }
        if (keysSuggested.indexOf("3") >= 0) {
            g.setColor(0xffff00);
            g.fillRect(10 + 28, canvas.getHeight() - 5 - tipImage.getHeight() + 5, 9, 9);
        }
        if (keysSuggested.indexOf("0") >= 0) {
            g.setColor(0xffff00);
            g.fillRect(10 + 16, canvas.getHeight() - 5 - tipImage.getHeight() + 41, 9, 9);
        }
        g.drawImage(tipImage, 10, canvas.getHeight() - 5 - tipImage.getHeight(), Graphics.TOP | Graphics.LEFT);
        
    }
    private static boolean moveMemory(int oldX, int oldY, int x, int y,
            Board board) {
        if (!canMove) {
            return false;
        }
        if (oldX < x && x + 1 < board.map.getWidth()) { // from left
            try {
                int tempCellCode = board.map.getCell(0, x + 1, y);
                if (tempCellCode == 49) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x + 1, y, 90);
                    board.changed = true;
                    return true;
                } else if (tempCellCode == 48) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x + 1, y, 49);
                    board.changed = true;
                    return true;
                }
                {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }

        }
        if (oldX > x && x - 1 >= 0) { // from right
            try {
                int tempCellCode = board.map.getCell(0, x - 1, y);
                if (tempCellCode == 49) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x - 1, y, 90);
                    board.changed = true;
                    return true;
                } else if (tempCellCode == 48) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x - 1, y, 49);
                    board.changed = true;
                    return true;
                }
                {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }

        }

        if (oldY > y && y - 1 >= 0) { // from bottom
            try {
                int tempCellCode = board.map.getCell(0, x, y - 1);
                if (tempCellCode == 49) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x, y - 1, 90);
                    board.changed = true;
                    return true;
                } else if (tempCellCode == 48) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x, y - 1, 49);
                    board.changed = true;
                    return true;
                }
                {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
        }

        if (oldY < y && y + 1 < board.map.getHeight()) { // from top
            try {
                int tempCellCode = board.map.getCell(0, x, y + 1);
                if (tempCellCode == 49) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x, y + 1, 90);
                    board.changed = true;
                    return true;
                } else if (tempCellCode == 48) {
                    board.map.setCell(0, x, y, 49);
                    board.map.setCell(0, x, y + 1, 49);
                    board.changed = true;
                    return true;
                }
                {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    Board board = null;

    netIntruderCanvas canvas = null;

    public boolean centered = false;

    int counter = 100;

    private long currentTimeLast = 0;

    public boolean fullscreenMessagePainted = false;

//    private boolean gameStarted = false;

    public int gameTime = 700;

    public int gameTimeInit = gameTime;

    long gameTimeTemp = System.currentTimeMillis();

    private Hashtable introFrames = new Hashtable();

    private String introPhrase = "";

    private Hashtable introSpeech = new Hashtable();

    public long lastMessagePainted = 0;

    int level = 0;

    private long movementTime;

    public String[] msg = {
            "Sicuro?",
            "Per informazioni visitare http://www.yos.it",
            "Benvenuto. Avvicinati alle email e leggi le mie indicazioni premento 0",
            "Sniffing...",
            "Completato: ",
            "Complimenti! Hai completato questo gioco. Sei nell'Elite :) Non dimenticare di visitare http://www.yos.it ed inserire il tuo codice: ",
            "Sei stato lento e ti sei fatto prendere... Sorry, per te il gioco e' finito!",
            "Premi 0...",
            "0 = si, 1 = no",
            "0 = esci, 1 = continua",
            "Blocchi di memoria ripristinati nella posizione iniziale"};
//    public String[] msg = {
//            "Sure?",
//            "For info go to http://www.yos.it",
//            "Benvenuto. Avvicinati alle email e leggi le mie indicazioni premento 0",
//            "Sniffing...",
//            "Complete: ",
//            "Cool! You finished this game. You are in the Elite :) Don't forget to visit http://www.yos.it and use there your code: ",
//            "Too slow... Sorry, the game is over for you!",
//            "Push 0...",
//            "0 = yes, 1 = no",
//            "0 = exit, 1 = continue"};

    public String[] rules = {
            "devi spiare i dati della rete rubando le password e spostare i blocchi di memoria per raggiungere il server.",
            "Non puoi spostare i blocchi di memoria se prima non hai rubato le password. In alto vedi il tempo che ti rimane.",
            "Per leggere le istruzioni contenute nelle mail premi 0. Premi 5 per centrare la telecamera sul giocatore."
    };
    
//    public String[] msgLevel = {
//            "Ci siamo solo riscaldati. Nel prossimo livello non ti ho lasciato suggerimenti: te la caverai... vero?",
//            "Questo era facile. Ma dovrai ancora faticare un bel po'...",
//            "Nessun computer e' sicuro. Ma alcuni sono, come hai visto, piu' sicuri di altri...",
//            "Sapevi che questo gioco che vedi e' fatto tutto in Linux?...",
//            "Che dici? E' facile!? Beh, vediamo come te la cavi con la prossima rete!",
//            "Il tempo corre. Fossi in te da ora farei caso alla barra del tempo in alto!",
//            "Se sai programmare puoi fare cose fichissime. Come ad esempio giochi per cellulari ;)",
//            "Ci siamo ormai...", "Non ho parole... prendi carte e penna..." };
//    public String[] msgLevel = {
//            "Level 1 done", 
//            "Level 2 done", 
//            "Level 3 done", 
//            "Level 4 done", 
//            "Level 5 done", 
//            "Level 6 done", 
//            "Level 7 done", 
//            "Level 8 done",
//            "Level 9 done"};
    
    public String[] msgLevel = {
            "Livello 1 completato", 
            "Livello 2 completato", 
            "Livello 3 completato", 
            "Livello 4 completato", 
            "Livello 5 completato", 
            "Livello 6 completato", 
            "Livello 7 completato", 
            "Livello 8 completato",
            "Livello 9 completato"};

    public final short NUMBER_OF_LEVELS = 9;

    private short numberTilesPerSide = 0;

    String[] packages = {
            "7465 4510 0213 f001 10be 2bff 3dee 212e   .......eFTP...",
            "4510 0213 f001 10be 2bff 3dee 212e f001   ...winzozProt...",
            "4510 0213 f001 10be 2bff 3dee 212e 7465   ...mondonerd.com",
            "10be 2bff 4510 0213 f001 3dee 212e 212e   ...wiEmail..t...",
            "10be 2bff 4510 0213 f001 3dee 212e 212e   USER.imbeciX.",
            "4510 0213 f001 10be 2bff 3dee 212e 7465   ...WWW.....yos.it",
            "10be 2bff 4510 10be 2bff 0213 f001 3dee   ...USER..tonto2006",
            "3dee 212e 4510 0213 10be 2bff 7465 f001   .)...PASS7v.2pL..",
            "4510 0213 f001 10be 2bff 3dee 212e 2bff   E..(:.@.@.Jm....",
            "4510 0213 f001 10be 2bff 3dee 212e 2bff   ..private.mpg....",
            "10be 2bff 4510 0213 f001 3dee 212e 7465   .233.64.in-addr.",
            "4510 0213 f001 10be 2bff 3dee 212e 4510   ...PASS12345678.",
            "4510 0213 f001 10be 2bff 3dee 212e 4510   ...PASS....sex..." };

    Character player = null;

    

    String[] puzzleArrayEmpty = { "", "", "", "", "", "" };
    String[] puzzleArray = puzzleArrayEmpty;

    public int puzzleMark = 0xcccccc;

    public short puzzlePasswordsFound = 0;

    public short puzzlePasswordsTotal = 5;

    public short puzzleSpeed = 900;

    public boolean puzzleStarted = false;

    long puzzleTimer = System.currentTimeMillis();

    public boolean puzzleVerified = false;

    Random randomGen = new Random();

    public boolean simpleImagePainted = false;

    private int startingPointX = 0;

    private int startingPointY = 0;

    private long timer = 0;

    Hashtable tips = null;

    private int yMod;

    public netIntruderUtility(netIntruderCanvas parentCanvas) {
        this.canvas = parentCanvas;
        numberTilesPerSide = (short) calculateBoardSize();
        initBoard();
        initPlayer();
    }

    private int calculateBoardSize() {
        if (canvas.getHeight() > 65) {
            return (canvas.getHeight() / 11 - 1);

        }
        return 9;
    }

    public boolean canGo(int oldX, int oldY, int x, int y, Board board) {
        // Cannot go out of the board
        if (x < 0 || x >= board.map.getWidth()) {
            return false;
        }
        if (y < 0 || y >= board.map.getHeight()) {
            return false;
        }

        // Cannot walk trhou another character or an object (if not walkable)
        if (board.isCharacter(x, y, 0) != null) {

        }
        // Check if the map cell is a walkable floor or a wall/building
        int cell = 999;
        //int oldCell = 999;
        try {
            cell = board.map.getCell(0, x, y);
            //oldCell = board.map.getCell(0, oldX, oldY);
        } catch (Exception ex) {

        }
        if (cell == 90) {
            if (moveMemory(oldX, oldY, x, y, board)) {
                return true;
            } else {
                return false;
            }
        } else if (cell == 59) {
            board.createMap(BASE_MAP + level, 1);
            initCamera();
            canvas.gamePhase = 14;
        }

        if (cell > 48 && cell < 64) {
            // System.out.println(player.x + "," + player.y);
            if (cell == 60) {
                board.map.setCell(0, x, y, 61);
                initCamera();
            }
            if (cell == 61) {
                board.map.setCell(0, x, y, 60);
                initCamera();
                player.setCurrentAction("sprites/shockSW"); // TODO
                gameTime -= 50;
            }
            return true;
        } else {
            return false;
        }
    }

    public void centerBoard() {

        Image imageTemp = Image.createImage(canvas.getWidth(), canvas
                .getHeight());
        Graphics gTemp = imageTemp.getGraphics();

        board.draw(canvas.getWidth() / 2, yMod, gTemp, centered);

        if (!centered) {
            // initialTime = System.currentTimeMillis();
            if (Math.abs(((canvas.getHeight() - canvas.getHeight() / 5) / 2)
                    - player.lastDrawnY) > 30) {
                counter = 30;
            } else if (Math
                    .abs(((canvas.getHeight() - canvas.getHeight() / 5) / 2)
                            - player.lastDrawnY) > 20) {
                counter = 20;
            } else if (Math
                    .abs(((canvas.getHeight() - canvas.getHeight() / 5) / 2)
                            - player.lastDrawnY) > 10) {
                counter = 10;
            } else {
                counter = 1;
            }
            if (player.lastDrawnY > (canvas.getHeight() - canvas.getHeight() / 5) / 2) {

                yMod -= counter;

            } else if (player.lastDrawnY < (canvas.getHeight() - canvas
                    .getHeight() / 5) / 2) {
                yMod += counter;
            } else {
                centered = true;
            }

            board.draw(canvas.getWidth() / 2, yMod, gTemp, centered);
        }
    }

    public void changeLevel() {
        initBoard();
        initPlayer();
        loadLevelConfig(level);
        int oldX = player.x;
        int oldY = player.y;
        player.x = startingPointX;
        player.y = startingPointY;
        // puzzleCounter = 3;
        // puzzleVerified = false;
        canMove = false;
        board
                .moveCharacter(player, oldX, oldY, 0, player.x, player.y, 0,
                        false);
        initCamera();
        board.changed = true;
        puzzlePasswordsFound = 0;
        // board.cachedBoard = null;
        System.gc();
    }

    private Vector divideString(String s, int width, Font f) {
        Vector list = new Vector();
        StringBuffer tempString = new StringBuffer("");
        if (f.stringWidth(s) < width) {
            list.addElement(s);
            return list;
        }

        Vector words = tokenizeString(s, " ");

        for (int i = 0; i < words.size(); i++) {

            if (f.stringWidth(tempString.toString() + words.elementAt(i)) < width
                    && !words.elementAt(i).equals("%")) {
                tempString.append((String) words.elementAt(i) + " ");
            } else {
                String newString = tempString.toString();
                list.addElement(newString);
                tempString.delete(0, tempString.length());
                tempString.append((String) words.elementAt(i) + " ");

            }

        }
        String newString = tempString.toString();
        list.addElement(newString);

        return list;
    }

    private String generateRandomString() {
        return (packages[Math.abs(randomGen.nextInt() % packages.length)]);

    }

    public void initBoard() {
        board = new Board(TILE_SET, canvas);
        // Set tiles standard size. Normaly 32x8.
        board.setTileSize(TILE_WIDTH, TILE_HEIGHT);
        // Load the map
        board.createMap(BASE_MAP + level, 1);
        board.width = numberTilesPerSide;
        board.height = numberTilesPerSide;

    }

    public void initCamera() {
        board.cellX = player.x - numberTilesPerSide / 2;
        board.cellY = player.y - numberTilesPerSide / 2;
        board.changed = true;
        // board.cachedBoard = null;
        System.gc();
    }

    public void initPlayer() {
        player = new Character();
        // Standing sprites
        player.putAction("sprites/playerSW", 2);
        player.putAction("sprites/playerSE", 2);
        player.putAction("sprites/playerNE", 2);
        player.putAction("sprites/playerNW", 2);
        player.putAction("sprites/shockSW", 0);

        player.setCurrentAction("sprites/playerSW");
        // Player coords

        player.x = numberTilesPerSide / 2;
        player.y = numberTilesPerSide / 2;
        player.modifierY = 14;
        // initialModifierY = 14;
        player.z = 0;
        // player.speed = 1;
        player.name = "player";
        board.putCharacter(player); // Place the player on the
        // board
    }

    public void loadLevelConfig(int levelSelected) {
        tips = new Hashtable();

        int ch;

        Class c = this.getClass();
        InputStream is = c.getResourceAsStream(LEVELS_DEF);
        StringBuffer tipsTemp = new StringBuffer();

        try {
            while ((ch = is.read()) > -1) { // It read all the map file
                if (ch != 10) { // If the char is not \n it store it in the map
                    tipsTemp.append((char) ch);
                } else {
                    if (Integer.parseInt(tipsTemp.toString().substring(0, 1)) == levelSelected) {
                        Vector temp = tokenizeString(tipsTemp.toString(), ":");
                        if (((String) temp.elementAt(1)).equals("t")) { // Tip
                            // instruction
                            tips.put((String) temp.elementAt(2) + "x"
                                    + (String) temp.elementAt(3), (String) temp
                                    .elementAt(4));
                            board.map.setCell(0, Integer.parseInt((String) temp
                                    .elementAt(2)), Integer
                                    .parseInt((String) temp.elementAt(3)), 50);
                        } else if (((String) temp.elementAt(1)).equals("p")) { // Puzzle
                            // instruction
                            tips.put((String) temp.elementAt(2) + "x"
                                    + (String) temp.elementAt(3), (String) temp
                                    .elementAt(4));
                            board.map.setCell(0, Integer.parseInt((String) temp
                                    .elementAt(2)), Integer
                                    .parseInt((String) temp.elementAt(3)), 57);
                        } else if (((String) temp.elementAt(1)).equals("s")) { // Starting
                            // Point
                            // instruction
                            startingPointX = Integer.parseInt((String) temp
                                    .elementAt(2));
                            startingPointY = Integer.parseInt((String) temp
                                    .elementAt(3));
                        } else if (((String) temp.elementAt(1)).equals("d")) { // Puzzle
                            // definition
                            // instruction
                            puzzlePasswordsTotal = (short) Integer
                                    .parseInt((String) temp.elementAt(2));
                            puzzleSpeed = (short) Integer
                                    .parseInt((String) temp.elementAt(3));
                        } else if (((String) temp.elementAt(1)).equals("w")) { // Wiki
                            tips.put((String) temp.elementAt(2) + "x"
                                    + (String) temp.elementAt(3), (String) temp
                                    .elementAt(4));
                            board.map.setCell(0, Integer.parseInt((String) temp
                                    .elementAt(2)), Integer
                                    .parseInt((String) temp.elementAt(3)), 62);
                        }
                    }
                    tipsTemp = new StringBuffer();
                }
            }
            if (Integer.parseInt(tipsTemp.toString().substring(0, 1)) == levelSelected) {
                Vector temp = tokenizeString(tipsTemp.toString(), ":");
                if (((String) temp.elementAt(1)).equals("t")) { // Tip
                    // instruction
                    tips.put((String) temp.elementAt(2) + "x"
                            + (String) temp.elementAt(3), (String) temp
                            .elementAt(4));
                    board.map.setCell(0, Integer.parseInt((String) temp
                            .elementAt(2)), Integer.parseInt((String) temp
                            .elementAt(3)), 50);
                } else if (((String) temp.elementAt(1)).equals("p")) { // Puzzle
                    // instruction
                    tips.put((String) temp.elementAt(2) + "x"
                            + (String) temp.elementAt(3), (String) temp
                            .elementAt(4));
                    board.map.setCell(0, Integer.parseInt((String) temp
                            .elementAt(2)), Integer.parseInt((String) temp
                            .elementAt(3)), 57);
                } else if (((String) temp.elementAt(1)).equals("s")) { // Starting
                    // Point
                    // instruction
                    startingPointX = Integer.parseInt((String) temp
                            .elementAt(2));
                    startingPointY = Integer.parseInt((String) temp
                            .elementAt(3));
                } else if (((String) temp.elementAt(1)).equals("d")) { // Puzzle
                    // definition
                    // instruction
                    puzzlePasswordsTotal = (short) Integer
                            .parseInt((String) temp.elementAt(2));
                    puzzleSpeed = (short) Integer.parseInt((String) temp
                            .elementAt(3));
                } else if (((String) temp.elementAt(1)).equals("w")) { // Wiki
                    tips.put((String) temp.elementAt(2) + "x"
                            + (String) temp.elementAt(3), (String) temp
                            .elementAt(4));
                    board.map.setCell(0, Integer.parseInt((String) temp
                            .elementAt(2)), Integer
                            .parseInt((String) temp.elementAt(3)), 62);
                }

            }
        } catch (IOException e) {

        } catch (Exception ex) {

        }

    }

    public void paintFullscreenMessage(Graphics g, String message,
            String continueMessage) {
        if (!fullscreenMessagePainted && !"".equals(message)) {

            g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
                    Font.SIZE_SMALL));
            paintMessageBox(g, message, canvas.getWidth() / 2, canvas
                    .getHeight() / 2, g.getClipWidth() - 6, 0x00cc00, 0x009900,
                    0x00ff00, 0x00ffff);
            fullscreenMessagePainted = true;
            lastMessagePainted = System.currentTimeMillis();

            if (!"".equals(continueMessage)) {
                g.setColor(0xff9900);
                paintMessageBox(g, continueMessage, canvas.getWidth()
                        - g.getFont().stringWidth(continueMessage) / 2 - 4, canvas
                        .getHeight(),
                        g.getFont().stringWidth(continueMessage) + 2, 0xaaaa00,
                        0xffaa00, 0xffffff, 0xcccccc);
            }
        }
    }

    public void paintGamePlay(Graphics g) {

        board.draw(g.getClipWidth() / 2, yMod, g, centered);

        if (canvas.fire && canvas.key != -99 && !player.isMoving
                && System.currentTimeMillis() - movementTime > 100) {
            movementTime = System.currentTimeMillis();
            performMovement(canvas.key);
            updateCamera();

        }
        if (canvas.key == -99) {
            canvas.fire = false;
        }

    }
    public void paintBackground(Graphics g) {
        g.setColor(0x004106);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image temp = null;
        
        try {
            temp = Image.createImage("/ui/bg_left.png");
        } catch (IOException e) {
        }
        
        if (temp != null) {
            g.drawImage(temp, 0, 0, Graphics.TOP | Graphics.LEFT);
        }
        
        try {
            temp = Image.createImage("/ui/bg_right.png");
        } catch (IOException e) {
        }
        
        if (temp != null) {
            g.drawImage(temp, canvas.getWidth(), 0, Graphics.TOP | Graphics.RIGHT);
        }
        try {
            temp = Image.createImage("/ui/bg_bottom.png");
        } catch (IOException e) {
        }
        
        if (temp != null) {
            g.drawImage(temp, canvas.getWidth() / 2, canvas.getHeight(), Graphics.BOTTOM | Graphics.HCENTER);
        }
        
    }

    public void paintIntro(Graphics g) {
        if (timer == 0) {
            timer = System.currentTimeMillis();
            introFrames.put("5", "/introBW/logomondonerd.png");
            introFrames.put("1", "/introBW/freccia.png");
//            introFrames.put("2", "/introBW/2.png");
//            introFrames.put("3", "/introBW/3.png");
//			  introFrames.put("4", "/introBW/4.png");
            
            introFrames.put("11", "/introBW/chair1.png");
            introFrames.put("13", "/introBW/chair2.png");
            introFrames.put("14", "/introBW/chair3.png");
            introFrames.put("15", "/introBW/flux.png");
            introFrames.put("20", "/introBW/smoke2.png");
            introFrames.put("21", "/introBW/smoke1.png");
            introFrames.put("22", "/introBW/smoke2.png");
            introFrames.put("23", "/introBW/smoke1.png");
            introFrames.put("24", "/introBW/smoke2.png");
            introFrames.put("25", "/introBW/smoke1.png");
            introFrames.put("26", "/introBW/smoke2.png");
            introFrames.put("27", "/introBW/smoke1.png");
            introFrames.put("28", "/introBW/smoke2.png");
            introFrames.put("29", "/introBW/smoke1.png");
            introFrames.put("30", "/introBW/smoke1.png");
            introFrames.put("31", "/introBW/hand.png");
            introFrames.put("40", "/introBW/flux.png");
            introFrames.put("49", "/introBW/smoke1.png");
            introFrames.put("50", "/introBW/smoke2.png");
            introFrames.put("51", "/introBW/smoke1.png");
            introFrames.put("52", "/introBW/smoke2.png");
            introFrames.put("53", "/introBW/smoke1.png");
            introFrames.put("54", "/introBW/smoke2.png");
            introFrames.put("55", "/introBW/smoke1.png");
            introFrames.put("56", "/introBW/smoke2.png");
            introFrames.put("57", "/introBW/access.png");

            introFrames.put("69", "/introBW/logo.png");

            introFrames.put("79", "stop");

            //introSpeech.put("5", "test...");

            introSpeech
                    .put("11", "L'hacking e' l'arte di risolvere problemi...");
            introSpeech.put("15", "");
            introSpeech.put("18", "...in modo brillante ed originale!");
            introSpeech.put("22", "");
            introSpeech.put("25",
                    "L'hacking e' quindi una metafora della vita.");
            introSpeech.put("38", "");
            introSpeech.put("42", "Beh...");
            introSpeech.put("48", "");
            introSpeech.put("51", "Questo gioco e' una metafora...");
            introSpeech.put("56", "");
            introSpeech.put("57", "...dell'essere hacker!");
            introSpeech.put("62", "");
        }
        if (currentTimeLast < (System.currentTimeMillis() - timer) / 500) {
            currentTimeLast = (System.currentTimeMillis() - timer) / 500;
//#ifdef nokia
//#            if (currentTimeLast == introFrames.size() / 2) {
//#                com.nokia.mid.ui.DeviceControl.setLights(0, 100); //TODO nokia light
//#            }
//#endif
            if (introFrames.containsKey(currentTimeLast + "")) {

                if ("stop".equals(introFrames.get(currentTimeLast + ""))) {
                    canvas.gamePhase++;
                    return;
                }
                paintBackground(g);
                try {
                    Image temp = Image.createImage((String) introFrames
                            .get(currentTimeLast + ""));
                    g.drawImage(temp, g.getClipWidth() / 2, canvas.getHeight()
                            / 2 - temp.getHeight() / 2, Graphics.TOP
                            | Graphics.HCENTER);

                } catch (IOException e) {
                    
                }
            }

            if (introSpeech.containsKey(currentTimeLast + "")) {
                introPhrase = (String) introSpeech.get(currentTimeLast + "");
            }
            paintIntroSpeech(g);

        }

    }

    private void paintIntroSpeech(Graphics g) {
        if (!"".equals(introPhrase)) {
            g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
                    Font.SIZE_SMALL));
            paintMessageBox(g, introPhrase, g.getClipWidth() / 2, canvas
                    .getHeight(), g.getClipWidth() - 6, 0x000000, 0xffffff,
                    0xff0000, 0xcc0000);
        }
    }

    private int paintMessageBox(Graphics g, String message, int xText,
            int yText, int widthText, int borderColor, int backgroundColor,
            int fontPrimaryColor, int fontSecondary) {

        Vector list = divideString(message, widthText, g.getFont());
        int fontHeight = g.getFont().getHeight() + 1;
        g.setColor(backgroundColor);
        int heightText = list.size() * fontHeight + 4;
        if (yText + heightText / 2 > canvas.getHeight()) {
            yText = canvas.getHeight() - heightText / 2;
        }
        g.fillRect(xText - widthText / 2, yText - heightText / 2, widthText,
                heightText);
        g.setColor(borderColor);
        g.drawRect(xText - widthText / 2, yText - heightText / 2, widthText,
                heightText);

        g.setColor(fontPrimaryColor);
        xText += 2;
        yText += 2;
        for (int i = 0; i < list.size(); i++) {
            g.drawString(list.elementAt(i).toString(), xText - widthText / 2,
                    yText - heightText / 2 + (i * fontHeight), Graphics.TOP
                            | Graphics.LEFT);
        }

        return (list.size() - 1) * fontHeight;

    }

    public void paintPuzzle(Graphics g) {
        if (puzzlePasswordsTotal <= puzzlePasswordsFound) {
            canMove = true;
            canvas.gamePhase = 3;
            return; // Win
        } 
        
        if (!puzzleStarted) {// Init Puzzle
            puzzleStarted = true;
            puzzleMark = 0xcccccc;
            puzzleArray = puzzleArrayEmpty;
            puzzleTimer = System.currentTimeMillis();
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g.setColor(0xffffff);
            g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
                    Font.SIZE_SMALL));
            g.drawString(msg[3], 10, 5, Graphics.TOP | Graphics.LEFT);
            g.setColor(0xffffff);
            g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
                    Font.SIZE_SMALL));
            g
                    .drawString(
                            msg[4]
                                    + (100 / puzzlePasswordsTotal * puzzlePasswordsFound)
                                    + "%", canvas.getWidth() - 10, canvas
                                    .getHeight() / 6 * 5, Graphics.TOP
                                    | Graphics.RIGHT);
        } else {
            if (System.currentTimeMillis() - puzzleTimer > puzzleSpeed) {
                puzzleVerified = false;
                puzzleTimer = System.currentTimeMillis();
                puzzleMark = 0xcccccc;
                runPuzzle();
                if (puzzlePasswordsFound < 0) {
//                  canvas.gamePhase = 3;
//                  canMove = false;
                  puzzlePasswordsFound = 0;
//                  return;// Loose
              }
                
                g.setColor(0x000000);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                g.setColor(0xffffff);
                g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
                        Font.SIZE_SMALL));
                g.drawString(msg[3], 10, 5, Graphics.TOP | Graphics.LEFT);
                for (int i = 0; i < 4; i++) {
                    g.setColor(0x006600);
                    g.drawLine(0, canvas.getHeight() / 6 * (i + 1), canvas
                            .getWidth(), canvas.getHeight() / 6 * (i + 1));
                    g.setColor(0x00cc00);
                    if (puzzleArray[i].indexOf("---  CHECKED  ---") > 0) {
                        g.setColor(0xff0000);
                    }
                    g.setFont(Font.getFont(Font.FACE_MONOSPACE,
                            Font.STYLE_PLAIN, Font.SIZE_SMALL));

                    g.drawString(puzzleArray[i], canvas.getWidth() - 10, canvas
                            .getHeight()
                            / 6 * (i + 1), Graphics.TOP | Graphics.RIGHT);
                }
                g.setColor(0xffffff);
                g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,
                        Font.SIZE_SMALL));
                g.drawString(msg[4] //TODO
                        + (100 / puzzlePasswordsTotal * puzzlePasswordsFound)
                        + "%", canvas.getWidth() - 10,
                        canvas.getHeight() / 6 * 5, Graphics.TOP
                                | Graphics.RIGHT);
            }
            g.setColor(puzzleMark);
            g.drawRect(0, canvas.getHeight() / 6, canvas.getWidth() - 1, canvas
                    .getHeight() / 6);
            if (puzzleMark == 0x00cccc) {
                g.drawString("WoW!", 0, canvas.getHeight() / 6, Graphics.TOP | Graphics.LEFT); 
            } else if (puzzleMark == 0xcc00cc){
                g.drawString("Good!", 0, canvas.getHeight() / 6, Graphics.TOP | Graphics.LEFT);
            } else if (puzzleMark == 0xff0000) {
                g.drawString("Bad!!", 0, canvas.getHeight() / 6, Graphics.TOP | Graphics.LEFT);
            }
            if (puzzleArray[0].indexOf("PASS") > 0) {
                paintTip(g, "1");
            }
            if (puzzleArray[0].indexOf("USER") > 0) {
                paintTip(g, "3");
            }
        }

    }

    public void paintSimpleImage(Graphics g, String fileName) {
        if (!simpleImagePainted) {
            // music.playSound("/netintruder_title.mid", -1);
            Image simpleImageTemp = null;
            try {
                simpleImageTemp = Image.createImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                paintBackground(g);
                g.drawImage(simpleImageTemp, g.getClipWidth() / 2
                        - simpleImageTemp.getWidth() / 2, canvas.getHeight()
                        / 2 - simpleImageTemp.getHeight() / 2, Graphics.TOP
                        | Graphics.LEFT);
                simpleImageTemp = null;
                simpleImagePainted = true;
            }

        }

    }

    public void performMovement(short keyCode) {
        // System.out.println("key: " + keyCode);

        int newX, newY, newZ, oldX, oldY, oldZ;
        oldX = player.x;
        oldY = player.y;
        oldZ = player.z;

        newX = player.x;
        newY = player.y;
        newZ = player.z;

        if (keyCode == Canvas.KEY_NUM3 || keyCode == -1 || keyCode == 38) {
            newY = player.y - 1;
            player.setCurrentAction("sprites/playerNE");
        }
        if (keyCode == Canvas.KEY_NUM1 || keyCode == -3 || keyCode == 37) {
            newX = player.x - 1;
            player.setCurrentAction("sprites/playerNW");

        }

        if (keyCode == Canvas.KEY_NUM9 || keyCode == -4 || keyCode == 39) {
            newX = player.x + 1;
            player.setCurrentAction("sprites/playerSE");

        }

        if (keyCode == Canvas.KEY_NUM7 || keyCode == -2 || keyCode == 40) {
            newY = player.y + 1;
            player.setCurrentAction("sprites/playerSW");

        }

        // canGo is the method to determine if the player can move in that
        // cell from his starting position
        // System.out.println("move");
        if (canGo(oldX, oldY, newX, newY, board)) {
            player.x = newX;
            player.y = newY;
            player.z = newZ;
            // player.isMoving = true;
            // System.out.println("can go");
            board.moveCharacter(player, oldX, oldY, oldZ, player.x, player.y,
                    player.z, false);
        } else {
            board.moveCharacter(player, oldX, oldY, oldZ, oldX, oldY, oldZ,
                    false);
            player.speed = 1;
        }

    }

    private void runPuzzle() {
        String[] temp = new String[puzzleArray.length];
        temp[temp.length - 1] = generateRandomString();
        for (int i = temp.length - 2; i >= 0; i--) {
            temp[i] = puzzleArray[i + 1];
        }
        if (puzzleArray[0].indexOf("PASS") > 0) { // Missed password
            puzzlePasswordsFound--;
        }
        if (puzzleArray[0].indexOf("USER") > 0) { // Missed password
            puzzlePasswordsFound--;
        }

        puzzleArray = temp;

    }

    private Vector tokenizeString(String s, String separator) {
        Vector list = new Vector();
        StringBuffer tempString = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) != separator.charAt(0)) {
                tempString.append(s.charAt(i));
            } else {
                String newString = tempString.toString();
                list.addElement(newString);
                tempString.delete(0, tempString.length());
            }

        }
        String newString = tempString.toString();
        list.addElement(newString);

        return list;

    }

    public void updateCamera() {

        if ((player.lastDrawnX < 10 || player.lastDrawnX > canvas.getWidth() - 10)
                || (player.lastDrawnY < 10
                        || player.lastDrawnY > canvas.getHeight()
                                - player.getCurrentImage().getHeight() || player.lastDrawnX == 0)) {

            initCamera();

        }

    }
}
