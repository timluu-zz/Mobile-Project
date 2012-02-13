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
 * Stage screen.
 * <p>
 * <hr><b>&copy; Copyright 2008 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 19/01/08
 * @author      Guidebee, Inc.
 */
public final class StageScreen extends GameCanvas implements Runnable{
    
    /**
     * stage image
     */
    private Image imgStage=null;
    
    /**
     * number image
     */
    private Image imgNumberBlack=null;
    
    /**
     * offset X, where is the origin of the picture.
     */
    private int offsetX=0;
    
    /**
     * offset X,
     */
    private int offsetY=0;
    
    /**
     * splash scroll in start time.
     */
    private long animationStartTime=0;
    
    /**
     * splash scroll in period.
     */
    private static long animationPeriod=5000;
    
    /**
     * anmation postion y
     */ 
    private int imgPosY=0;
    
    /**
     * anmation postion y
     */ 
    private int imgPosX=0;
    
    /**
     * if user click ,stop animation.
     */
    private boolean stopThread=false;
    
    /**
     * the animation thread.
     */
    private Thread animationThread=null;
    
    /**
     * scroll in from top to bottom? or from left to right
     */
    private boolean fromTop2Bottom=false;
     
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructor.
     */
    public StageScreen() {
        super(false);
        setFullScreenMode(true);
        imgStage=ResourceManager.getInstance()
            .getImage(ResourceManager.STAGE);
        imgNumberBlack=ResourceManager.getInstance()
            .getImage(ResourceManager.NUMBER_BLACK);
        
        offsetX=(getWidth()-imgStage.getWidth())/2;
        offsetY=(getHeight()-imgStage.getHeight())/2;
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
    private void drawNumber(Graphics g, int number,int x,int y){
        Image imageNumber=imgNumberBlack;
        String strNumber=String.valueOf(number);
        int numberWidth=imageNumber.getHeight();
        for(int i=0;i<strNumber.length();i++){
            char ch=strNumber.charAt(i);
            int index=(ch-'0') % 10;
            Image oneNumber=Image.createImage(imageNumber,index*numberWidth,0,
                    numberWidth,numberWidth,0);
            g.drawImage(oneNumber,x+i*numberWidth,y,0);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Start the animation.
     */
    public void show(){
        animationStartTime=System.currentTimeMillis();
        imgPosY=getHeight()-offsetY;
        animationThread=new Thread(this);
        animationThread.start();
        stopThread=false;
        fromTop2Bottom=!fromTop2Bottom;
        imgPosY=imgPosX=0;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * animation.
     */
    public void run(){
        Thread thread=Thread.currentThread();
        if(thread==animationThread){
            long tickTime=System.currentTimeMillis();
            while(!stopThread){
                if(tickTime-animationStartTime<animationPeriod){
                    if(fromTop2Bottom){
                        if(imgPosY<getHeight()/2+8) {
                            imgPosY+=8;
                        }else{
                            break;
                        }
                    }else{
                        if(imgPosX<getWidth()/2+8) {
                            imgPosX+=8;
                        }else{
                            break;
                        }
                    }
                    repaint();
                    try{
                        Thread.sleep(50);
                    }catch(Exception e){}
                }else{
                    break;
                }
            }
            stopThread=true;
            imgPosY=getHeight()/2+8;
            imgPosX=getWidth()/2+8;
            repaint();
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * paint.
     */
    public void paint(Graphics g){
        //clear background to black
        g.setColor(0x000000);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(0x808080);
        int length=String.valueOf(ResourceManager.gameLevel).length();
        if(fromTop2Bottom){
            g.fillRect(0,0,getWidth(),imgPosY);
            g.fillRect(0,getHeight()-imgPosY,getWidth(),imgPosY);
        }else{
            g.fillRect(0,0,imgPosX,getHeight());
            g.fillRect(getWidth()-imgPosX,0,imgPosX,getHeight());
        }
        g.drawImage(imgStage,offsetX-(length+1)*imgNumberBlack.getHeight(),offsetY,0);
        drawNumber(g,ResourceManager.gameLevel,imgStage.getWidth()+
                offsetX-length*imgNumberBlack.getHeight()
                ,offsetY);

        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * key press.
     */
    protected void keyPressed(int keyCode) {
        if(!stopThread) 
        {
            stopThread=true;
            return;
        }
        int gameAction=0;
        switch(keyCode){
            case Canvas.KEY_NUM2:
                    gameAction=Canvas.UP;
                    break;
            case Canvas.KEY_NUM8:
                    gameAction=Canvas.DOWN;
                    break;
            case Canvas.KEY_NUM5:
                    gameAction=Canvas.FIRE;
                    break;
                     
            default:
                    gameAction = getGameAction(keyCode);
        }
        switch(gameAction){
            case Canvas.DOWN:
                ResourceManager.gameLevel--;
                if(ResourceManager.gameLevel<1) ResourceManager.gameLevel=1;
                break;
            case Canvas.UP:
                ResourceManager.gameLevel++;
                if(ResourceManager.gameLevel>50) ResourceManager.gameLevel=50;
                break;
            case Canvas.FIRE:
                ResourceManager.gameScene.newGame();
                ResourceManager.setCurrentScreen(ResourceManager.gameScene);
                break;
        }
        repaint(0,offsetY,getWidth(),40);
        
    }
}
