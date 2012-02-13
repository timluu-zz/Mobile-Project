//------------------------------------------------------------------------------
//                         COPYRIGHT 2008 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 19JAN2008  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//The Software shall be used for Good, not Evil.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.
//Any questions, feel free to drop me a mail at james.shen@guidebee.biz.
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.game.battlecity;

//--------------------------------- IMPORTS ------------------------------------
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

//[------------------------------ MAIN CLASS ----------------------------------]
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 19JAN2008  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Gameover screen.
 * <p>
 * <hr><b>&copy; Copyright 2008 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 19/01/08
 * @author      Guidebee, Inc.
 */
public final class ScoreScreen extends GameCanvas implements Runnable{
    
    /**
     * Score board image.
     */
    private static Image imgScore=null;
    
    /**
     * black number image from 0 to 9
     */
    private static Image imgNumberWhite=null;
    
    /**
     * red number image from 0 to 9
     */
    private static Image imgNumberRed=null;
            
    
    /**
     * reference to player tank
     */
    private static PlayerTank playerTank=null;

    /**
     * picture drawing x offset.
     */
    private int offsetX=0;
    
    /**
     * picture drawing y offset. to make the image center in the screen.
     */
    private int offsetY=0;
    
    /**
     * where to draw high score.
     */
    private int highScoreX=0;
    
    /**
     * where to draw high score.
     */
    private int highScoreY=0;
    
    /**
     * where to draw player score.
     */
    private int playerScoreX=0;
    
    /**
     * where to draw player score.
     */
    private int playerScoreY=0;
    
    /**
     * where to draw total number.
     */
    private int totalNumberX=0;
    
    /**
     * where to draw total number.
     */
    private int totalNumberY=0;
    
    /**
     * where to draw enemy tank count.
     */
    private int[][] tankNumberPos=new int[4][2];
    
    /**
     * is the game over?
     */
    private boolean isGameOver=false;
    
    //Score screen displays GameScene.enemyTanksCount[] .the following 2
    //variables are used to display the score board animation. each step draw
    //one number change.
    /**
     * the enemytype.
     */
    private int enemyType=0;
    
    /**
     * the index of enemy count.
     */
    private int enemyCount=0;
    
    /**
     * total enemy number killed
     */
    private int totalKilled=0;
    
    
    private int totalKilledIndex=0;
    
    /**
     * the animation thread.
     */
    private Thread animationThread=null;
    
    /**
     * switch screen timer.
     */
    private Timer timer=null;
    
    /**
     * timer task to swith screen.
     */
    private Switch2Screen switchScreen=null;
   
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 20JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructor.
     */
    public ScoreScreen() {
        super(false);
        setFullScreenMode(true);
        imgScore=ResourceManager.getInstance()
            .getImage(ResourceManager.SCORE_SCREEN);
        offsetX=(getWidth()-imgScore.getWidth())/2;
        offsetY=(getHeight()-imgScore.getHeight())/2;
        playerTank=Tank.getPlayerTank();  
        imgNumberWhite=ResourceManager.getInstance()
            .getImage(ResourceManager.NUMBER_WHITE);
        imgNumberRed=ResourceManager.getInstance()
            .getImage(ResourceManager.NUMBER_RED);
        highScoreX=getWidth()/2+16;
        highScoreY=offsetY;
        playerScoreX=getWidth()/4;
        playerScoreY=offsetY+35;
        for(int i=0;i<4;i++){
            tankNumberPos[i][0]=playerScoreX;
            tankNumberPos[i][1]=offsetY+50+i*16-1;
        }
        totalNumberX=playerScoreX+40;
        totalNumberY=offsetY+113;
        timer=new Timer();
        switchScreen=new Switch2Screen();
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 20JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw number in score bar
     * @param g the graphics object
     * @param number the number need to be drawn
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private void drawNumber(Graphics g, int number,int x,int y,boolean isRed){
        Image imageNumber=null;
        if(isRed){
            imageNumber=imgNumberRed;
        }else{
            imageNumber=imgNumberWhite;
        }
        String strNumber=String.valueOf(number);
        int numberWidth=imageNumber.getHeight();
        for(int i=0;i<strNumber.length();i++){
            char ch=strNumber.charAt(i);
            int index=(ch-'0') % 10;
            Image oneNumber=Image.createImage(imageNumber,index*numberWidth,0,
                    numberWidth,numberWidth,0);
            if(isRed){
                g.drawImage(oneNumber,x+i*numberWidth,y,0);
            }else{
                //right alignment
                g.drawImage(oneNumber,x+(i-strNumber.length()+2)*numberWidth,y,0);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 20JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Set the game over value. the game over value decides what's next screen
     * shown after Score board screen is discard, either shows the GameOver 
     * screen or the new game.
     * @param gameover true, game is over.
     */
    public void show(boolean gameOver){
        if(ResourceManager.isPlayingSound){
            ResourceManager.playSound(ResourceManager.SCORE_SOUND);
        }
        isGameOver=gameOver; 
        enemyType=0;
        enemyCount=0;
        totalKilled=0;
        for(int i=0;i<4;i++){
            int value=GameScene.enemyTanksCount[i]*(i+1)*100;
            playerTank.addScore(value);
            totalKilled+=GameScene.enemyTanksCount[i];
        }
        totalKilledIndex=totalKilled;
        animationThread=new Thread(this);
        animationThread.start();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 20JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * paint.
     * @param g the graphics object.
     */
    public void paint(Graphics g){
        //clear background to black
        g.setColor(0x000000);
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(imgScore,offsetX,offsetY,0);
        drawNumber(g,ResourceManager.highScore,highScoreX,highScoreY,true);
        drawNumber(g,playerTank.getScore(),playerScoreX,playerScoreY,true);
        if(enemyType>3){
            enemyType=3;
        }
        for(int i=0;i<enemyType;i++){
            drawNumber(g,GameScene.enemyTanksCount[i]*100,tankNumberPos[i][0],
                    tankNumberPos[i][1],false);
            drawNumber(g,GameScene.enemyTanksCount[i],tankNumberPos[i][0]+35,
                    tankNumberPos[i][1],false);
        }
        drawNumber(g,enemyCount*100,tankNumberPos[enemyType][0],
                tankNumberPos[enemyType][1],false);
        drawNumber(g,enemyCount,tankNumberPos[enemyType][0]+35,
                tankNumberPos[enemyType][1],false);

        drawNumber(g,totalKilled,totalNumberX,totalNumberY,false);
       
        
     }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 20JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     *  step to draw number.
     */
    public void run(){
        Thread thread=Thread.currentThread();
        if(thread==animationThread){
            while(totalKilledIndex>0 || enemyType<3){
                repaint();
                if(enemyCount>=GameScene.enemyTanksCount[enemyType]){
                    enemyCount=0;
                    enemyType++;
                }
                
                
                try{
                    Thread.sleep(200);
                }catch(Exception e){}
                enemyCount++;
                if(enemyType>3) {
                    enemyType=3;
                    break;
                }
                totalKilledIndex--;
            }
            if(switchScreen!=null){
               switchScreen=null; 
            }
            switchScreen=new Switch2Screen();
            switchScreen.setGameover(isGameOver);
            timer.schedule(switchScreen,2000);
            
        }
        
        
    }
    
}

//
class Switch2Screen extends TimerTask{
       
    private boolean isGameOver=false;
    
     
    void setGameover(boolean gameover){
        isGameOver=gameover;
    }
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * run .
     */
    public void run(){
        
         if(isGameOver){
                ResourceManager.gameoverScreen.show();
                ResourceManager.setCurrentScreen(ResourceManager.gameoverScreen);
        }else{
            ResourceManager.gameScene.newGame();
            ResourceManager.setCurrentScreen(ResourceManager.gameScene);
        }
        
    }
}
