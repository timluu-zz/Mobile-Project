/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFrame;

import GamePlay.CanvasGame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class Player{
    public static final int LEVEL_X = 10;
    public static final int LEVEL_Y = 10;
    public static final int SCORE_X = 172;
    public static final int SCORE_Y = 229;
    public static final int BLOOD_X = 60;
    public static final int BLOOD_Y = 229;
    
    public static final int MAX_NAME_LEN        = 6;
    public static final int MAX_TIME            = 120;
    public static final int MAX_BLOOD           = 5;
    
    private CanvasGame canvas;
    private int image;
    private int level;
    private int life;
    private int blood;
    private int score;
    private int time;
    private int nameLen;
    private String name;
    private char[] nameTemp;

    public Player(CanvasGame gameCanvas) {
        this.canvas = gameCanvas;
        this.image = Resource.IMG_SMALL_NUMBER;
        this.level = 1;
        this.life  = 5;
        this.score = 0;
        this.time  = MAX_TIME;
        this.blood = MAX_BLOOD;
        this.name  = "";
        this.nameLen = 0;
        this.nameTemp = new char[MAX_NAME_LEN];
    }
    public Player(CanvasGame gameCanvas, int image, int level, int life, int score, int time, int blood, String name) {
        this.canvas = gameCanvas;
        this.image = image;
        this.level = level;
        this.life  = life;
        this.score = score;
        this.time  = time;
        this.blood = blood;
        this.name  = name;
        this.nameLen = 0;
        this.nameTemp = new char[MAX_NAME_LEN];
    }


    public void update() {
    }

    public void paint(Graphics g, Image ima) {
       // ima.drawNumber(g, gameCanvas, image, level, 10, LEVEL_X, LEVEL_Y);
        ima.drawNumber(g, canvas, image, score, 10, SCORE_X, SCORE_Y);
        ima.drawNumber(g, canvas, image, blood, 10, BLOOD_X, BLOOD_Y);
       // g.drawString(new String(nameTemp), 100, 100, Graphics.TOP|Graphics.LEFT);
    }
    
    public void setLevel(final int level){
        this.level = level;
    }
    
    public void setScore(final int score) {
        this.score = score;
    }

    public void setTime(final int time) {
        this.time = time;
    }
    
    public void setBlood(final int blood) {
        this.blood = blood;
        if( 0 > blood ) this.blood = 0;
    }
    
    public void setName(final char[] name) {
        this.name = new String(name);
    }
    
    public void setNameLength(final int nameLen){
        this.nameLen = nameLen;
    }
    
    public void addChar(final char c) {
        if( this.nameLen < MAX_NAME_LEN ) {
            if(c >= InputKey.KEY_A && c <= InputKey.KEY_Z || c >= InputKey.KEY_a && c <= InputKey.KEY_z || (c >= InputKey.KEY_NUM0 && c <= InputKey.KEY_NUM9)) {
                this.nameTemp[this.nameLen] = c;
                ++ this.nameLen;
            }
        }
    }

    public void resetNameTemp(){
        for(int i = 0; i < nameTemp.length; i++){
            nameTemp[i] = '\0';
        }
        nameLen = 0;
    }
    public void removeChar() {
        if(this.nameLen >= 0) {
            if(this.nameLen > 0)
                -- this.nameLen;
            this.nameTemp[this.nameLen] = '\0';
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public int getLevel() {
        return this.level;
    }
   
    public int getScore() {
        return this.score;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public int getBlood() {
        return this.blood;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getNameLength() {
        return this.nameLen;
    }
    
    public char[] getNameTg() {
        return this.nameTemp;
    }
}
