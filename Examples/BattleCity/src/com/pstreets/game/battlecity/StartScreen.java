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
 * Startup screen.
 * <p>
 * <hr><b>&copy; Copyright 2008 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 19/01/08
 * @author      Guidebee, Inc.
 */
public final class StartScreen extends GameCanvas{
    
    /**
     * game help image.
     */
    private Image imgGameHelp=null;
    /**
     * turn on sound image.
     */
    private Image imgTurnOnSound=null;
    
    /** 
     * pointer image.
     */
    private Image imgPointer=null;
    
    /**
     * pointer
     */
    private Sprite pointer=null;
    
    /**
     * image origin x,center images.
     */
    private int offsetX=0;
    
    /**
     * image origin y,center images.
     */
    private int offsetY=0;
    
    /**
     * pointer locations.
     */
    private int[][]pointerPos=new int[2][2];
    
    /**
     * current pointer index. 
     */
    private int currentPointerIndex=0;
     
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructor.
     */
    public StartScreen() {
        super(false);
        setFullScreenMode(true);
        imgGameHelp=ResourceManager.getInstance()
            .getImage(ResourceManager.GAME_HELP);
        imgTurnOnSound=ResourceManager.getInstance()
            .getImage(ResourceManager.TURN_SOUND);
        offsetX=(getWidth()-imgGameHelp.getWidth())/2;
        offsetY=(getHeight()-imgGameHelp.getHeight())/2;
        imgPointer=Image.createImage(ResourceManager.getInstance()
            .getImage(ResourceManager.PLAYER),0,12,24,12,0);
        pointer=new Sprite(imgPointer,12,12);
        pointerPos[0][0]=offsetX+imgGameHelp.getWidth()/2-24;
        pointerPos[0][1]=offsetY+imgGameHelp.getHeight()-
               imgTurnOnSound.getHeight()-21;
        pointerPos[1][0]=offsetX+imgGameHelp.getWidth()/2-24;
        pointerPos[1][1]=offsetY+imgGameHelp.getHeight()-
               imgTurnOnSound.getHeight()-1;
        pointer.setPosition(pointerPos[0][0],pointerPos[0][1]);
        ResourceManager.gameScene=new GameScene();
        ResourceManager.splashScreen=new SplashScreen();
        ResourceManager.gameoverScreen=new GameoverScreen();
        ResourceManager.scoreScreen=new ScoreScreen();
        ResourceManager.stageScreen=new StageScreen();

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
        g.drawImage(imgGameHelp,offsetX,offsetY,0);
        g.drawImage(imgTurnOnSound,offsetX,offsetY+imgGameHelp.getHeight()-
               imgTurnOnSound.getHeight()-50,0);
        pointer.paint(g);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 21JAN2008  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * key pressed.
     */
    protected void keyPressed(int keyCode) {
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
                if(currentPointerIndex>1)
                    currentPointerIndex=0;
                break;
            case Canvas.UP:
                currentPointerIndex--;
                if(currentPointerIndex<0)
                    currentPointerIndex=1;
                break;
            case Canvas.FIRE:
                if(currentPointerIndex==0){
                    ResourceManager.isPlayingSound=true;
                }else{
                   ResourceManager.isPlayingSound=false; 
                }
                ResourceManager.splashScreen.show();
                ResourceManager.setCurrentScreen(ResourceManager.splashScreen);
                break;
        }
        pointer.setPosition(pointerPos[currentPointerIndex][0],
                pointerPos[currentPointerIndex][1]);
        repaint(pointerPos[0][0],pointerPos[0][1],40,40);
        
    }
}
