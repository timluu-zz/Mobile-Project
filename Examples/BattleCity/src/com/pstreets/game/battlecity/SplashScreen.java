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
 * Splash screen.
 * <p>
 * <hr><b>&copy; Copyright 2008 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 19/01/08
 * @author      Guidebee, Inc.
 */
public final class SplashScreen extends GameCanvas implements Runnable{
    
    /**
     * Splash image.
     */
    private Image imgSplash=null;
    
    /**
     * Guidebee image.
     */
    private Image imgGuidebee=null;
    
    /**
     * pointer image (tank.)
     */
    private Image imgPointer=null;
    
    /**
     * pointer ,user can move this pointer to select 1 player ,2 player etc.
     */
    private Sprite pointer=null;
    
    /**
     * offset X, where is the origin of the picture.
     */
    private int offsetX=0;
    
    /**
     * offset X,
     */
    private int offsetY=0;
    
    /**
     * option position.
     */
    private int[][]pointerPos=new int[3][2];
    
    /**
     * current selection.
     */
    private int currentPointerIndex=0;
    
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
     * if user click ,stop animation.
     */
    private boolean stopThread=false;
    
    /**
     * the animation thread.
     */
    private Thread animationThread=null;
     
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructor.
     */
    public SplashScreen() {
        super(false);
        setFullScreenMode(true);
        imgSplash=ResourceManager.getInstance()
            .getImage(ResourceManager.SPLASH);
        imgGuidebee=ResourceManager.getInstance()
            .getImage(ResourceManager.GUIDEBEE_LOGO);
        
        offsetX=(getWidth()-imgSplash.getWidth())/2;
        offsetY=(getHeight()-imgSplash.getHeight())/2;
        imgPointer=Image.createImage(ResourceManager.getInstance()
            .getImage(ResourceManager.PLAYER),0,12,24,12,0);
        pointer=new Sprite(imgPointer,12,12);
        pointerPos[0][0]=offsetX+imgSplash.getWidth()/2-50;
        pointerPos[0][1]=offsetY+imgSplash.getHeight()-
               imgGuidebee.getHeight()-32;
        pointerPos[1][0]=offsetX+imgSplash.getWidth()/2-50;
        pointerPos[1][1]=offsetY+imgSplash.getHeight()-
               imgGuidebee.getHeight()-22;
        pointerPos[2][0]=offsetX+imgSplash.getWidth()/2-50;
        pointerPos[2][1]=offsetY+imgSplash.getHeight()-
               imgGuidebee.getHeight()-12;
        pointer.setPosition(pointerPos[0][0],pointerPos[0][1]);

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
        pointer.setVisible(false);
        stopThread=false;
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
                    if(imgPosY>0) {
                        imgPosY-=8;
                    }else{
                        break;
                    }
                    repaint();
                    try{
                        Thread.sleep(60);
                    }catch(Exception e){}
                }else{
                    break;
                }
            }
            pointer.setVisible(true);
            stopThread=true;
            imgPosY=0;
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
        g.drawImage(imgSplash,offsetX,offsetY+imgPosY,0);
        g.drawImage(imgGuidebee,offsetX,offsetY+imgSplash.getHeight()-
               imgGuidebee.getHeight()+imgPosY,0);
        pointer.paint(g);
        
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
                currentPointerIndex++;
                if(currentPointerIndex>2)
                    currentPointerIndex=0;
                break;
            case Canvas.UP:
                currentPointerIndex--;
                if(currentPointerIndex<0)
                    currentPointerIndex=2;
                break;
            case Canvas.FIRE:
                ResourceManager.stageScreen.show();
                ResourceManager.setCurrentScreen(ResourceManager.stageScreen);
                break;
        }
        pointer.setPosition(pointerPos[currentPointerIndex][0],
                pointerPos[currentPointerIndex][1]);
        repaint(pointerPos[0][0],pointerPos[0][1],40,40);
        
    }
}
