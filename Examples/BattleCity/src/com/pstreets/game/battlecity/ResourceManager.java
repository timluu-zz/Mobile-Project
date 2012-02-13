//------------------------------------------------------------------------------
//                         COPYRIGHT 2007 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 15JAN2008  James Shen                 	      Initial Creation
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
import java.io.*;
import java.util.*;
import javax.microedition.media.*;
import javax.microedition.lcdui.*;

//[------------------------------ MAIN CLASS ----------------------------------]
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 15JAN2008  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Resource Manager.A Singleton class used to mananger images and wav/midi 
 * resources.
 * <p>
 * <hr><b>&copy; Copyright 2008 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 15/01/08
 * @author      Guidebee, Inc.
 */
public class ResourceManager {
    
    /**
     * the maximum game level.
     */
    public static final int MAX_GAME_LEVEL=50;
    /**
     * game level index, for simplicity ,the game  use chinese character as
     * the battle field setting.
     */
    public static int[] gameLevels=new int[MAX_GAME_LEVEL];
    
    /**
     * The width/height of each tile.
     */
    public static final int TILE_WIDTH=12;
    
    /**
     * Bonus images.
     */
    public static final int BONUS=0;
    
    /**
     * Explode images.
     */
    public static final int EXPLODE=1;
    
    /**
     * Bullet images.
     */
    public static final int BULLET=2;
    
    /**
     * Player tank images.
     */
    public static final int PLAYER=3;
    
    /**
     * Enemy tank images.
     */
    public static final int ENEMY=4;
    
    /**
     * Splash screen
     */
    public static final int SPLASH=10;
    
    /**
     * Score screen
     */
    public static final int SCORE_SCREEN=11;
    
    /**
     * First player tanks.
     */
    public static final int IP=12;
    
    /**
     * Level 
     */
    public static final int FLAG=13;
    
    /**
     * enemy tanks icon in score bar.
     */
    public static final int ENEMY_ICON=14;
    
    /**
     * white numbers.
     */
    public static final int NUMBER_WHITE=15;
    
    /**
     * red numbers.
     */
    public static final int NUMBER_RED=16;
    
    /**
     * Big game over image
     */
    public static final int GAME_OVER_BIG=17;
    
    /**
     * small game over image
     */
    public static final int GAME_OVER_SMALL=18;
    
    /**
     * small numbers for scores.
     */
    public static final int SCORE=19;
    
    /**
     * game pause
     */
    public static final int PAUSE=20;
    
    /**
     * numbers in black.
     */
    public static final int NUMBER_BLACK=21;
    
    /**
     * Stage image
     */
    public static final int STAGE=22;
    
    /**
     * turn on sound
     */
    public static final int TURN_SOUND=23;
    
    /**
     * game help
     */
    public static final int GAME_HELP=27;
    
    /**
     * Guidebee logo
     */
    public static final int GUIDEBEE_LOGO=28;
    
    /**
     * playing sound ?
     */
    public static boolean isPlayingSound=false;
    
    /**
     * Score board sound.
     */
    public static final int SCORE_SOUND=1;
    
    /**
     * Open game sound
     */
    public static final int OPEN_SOUND=2;
    
    /**
     * Game over sound
     */
    public static final int GAMEOVER_SOUND=0;
    
    /**
     * Explode sound.
     */
    public static final int EXPLODE_SOUND=4;
    
    /**
     * Shooting sound index.
     */
    public static final int SHOOT_SOUND=3;
    
    /**
     * current visiable screen.
     */
    public static Displayable currentScreen=null;
    
    /**
     * the display object.
     */
    public static Display theDisplay=null;
    
    //here using static object to avoid creating screen object each time. 
    /**
     * the start screen object.
     */
    public static StartScreen startScreen=null;
    
    /**
     * the splash screen object.
     */
    public static SplashScreen splashScreen=null;
    
    /**
     * the game scene object.
     */
    public static GameScene gameScene=null;
    
    /**
     * the score board screen object.
     */
    public static ScoreScreen scoreScreen=null;
    
    /**
     * the game over screen.
     */
    public static GameoverScreen gameoverScreen=null;
    
    /**
     * the stage screen.
     */
    public static StageScreen stageScreen=null;
    
    /**
     * current game level.
     */
    public static int gameLevel=1;
    
    /**
     * the highest score.
     */
    public static int highScore=20000;
    
    /**
     * Singleton instance
     */
    private static ResourceManager instance=new ResourceManager();
    
    /**
     * image array.
     */
    private static Image[]imageResources=new Image[29];
    
    /**
     * tile image
     */
    private static Image tileImage=null;
    
    /**
     * sound data
     */
    private static byte[][]soundResouces=new byte[5][];
    
    /**
     * Sound player object.
     */
    private static Player player=null;
    
    
    /**
     * load image and sound resouces
     */
    static{
        try{
            int imageIndex=0;
            for(int i=1;i<=5;i++){
                String fileName="/img" +i;
                InputStream is =instance.getClass().getResourceAsStream(fileName);
                DataInputStream dis=new DataInputStream(is);
                while(dis.available()>0){
                    int pngLength=dis.readInt();
                    byte []buffer=new byte[pngLength];
                    dis.read(buffer);
                    imageResources[imageIndex++]=Image.createImage(buffer,
                            0,pngLength);
                }
                dis.close();
                is.close();
             }
            imageResources[GUIDEBEE_LOGO]=Image.createImage(instance.getClass().
                    getResourceAsStream("/guidebee.png"));
            
            tileImage=Image.createImage(72,TILE_WIDTH/2);
            Graphics g=tileImage.getGraphics();
            int imgWidth=0;
            for(int i=5;i<10;i++){
                g.drawImage(imageResources[i],imgWidth,0,0);
                imgWidth+=imageResources[i].getWidth();
            }
            for(int i=5;i<10;i++){
               imageResources[i]=null; 
            }
            System.gc();
            for(int i=1;i<=5;i++){
                String fileName="/snd" +i;
                InputStream is =instance.getClass().getResourceAsStream(fileName);
                int sndLength=is.available();
                soundResouces[i-1]=new byte[sndLength];
                is.read(soundResouces[i-1]);
                is.close();
            } 
            System.gc();
            
        }catch(IOException e){
            System.out.println("Error reading resources");
        }
        //initialize the game index
        Random rnd=new Random();
        for(int i=0;i<MAX_GAME_LEVEL;i++){
           gameLevels[i]=Math.abs(rnd.nextInt()) % 6840;  
        }
        //guidebee soft.
        gameLevels[1]=3384;
        gameLevels[2]=1810;
        gameLevels[3]=821;
        gameLevels[4]=2428;
        gameLevels[5]=1317;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 17JAN2008  James Shen                 	          Initial Creation 
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set an new screen.
     */
    public static void setCurrentScreen(Displayable newScreen){
        currentScreen=newScreen;
        theDisplay.setCurrent(currentScreen);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 17JAN2008  James Shen                 	          Initial Creation 
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Get the tile image.
     * @return the battle field tile image.
     */
    public Image getTileImage(){
        return tileImage;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 17JAN2008  James Shen                 	          Initial Creation 
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Get image resource.
     * @param index the image resource index.
     * @return the image.
     */
    public Image getImage(int index){
        return imageResources[index];
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 17JAN2008  James Shen                 	          Initial Creation 
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Play sound.
     * @param type the sound type.
     */
    public static void playSound(int type){
        
        ByteArrayInputStream inputstream;
        String soundType="audio/midi";
        if(type>2) {
            soundType="audio/x-wav";
            return;
        }
        try{
            inputstream=new ByteArrayInputStream(soundResouces[type]);
            if(player!=null){
                player.close();
            }
            player = Manager.createPlayer(inputstream, soundType);
            player.start();
        } catch(Exception e){
            System.out.println("No wav");
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 15JAN2008  James Shen                 	          Initial Creation 
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Get an unique instance of resource manager.
     * @return an instance of resource manager.
     */
    public static  ResourceManager getInstance(){
        return instance;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 15JAN2008  James Shen                 	          Initial Creation 
    ////////////////////////////////////////////////////////////////////////////
    /**
     * private constructor to avoid to be instnaced directly.
     */
    private ResourceManager(){
        
    }
    
}
