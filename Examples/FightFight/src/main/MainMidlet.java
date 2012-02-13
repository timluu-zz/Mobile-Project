/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import java.io.IOException;
import java.io.InputStream;

/****************************************************************************
*****************************************************************************
*** Author: HTV *********************************************************
*****************************************************************************
*****************************************************************************
*****************************************************************************
*****************************************************************************
 */
public class MainMidlet extends MIDlet implements CommandListener{
    private SSGameCanvas gameCanvas ;
    private Command exitCommand ;
    private Player player = null;

    
    public void startApp() {
        try {
            //create new game thread
            gameCanvas = new SSGameCanvas();
            gameCanvas.start(); // start game thread
            exitCommand = new Command("Exit",Command.EXIT,1);
            gameCanvas.addCommand(exitCommand);
            gameCanvas.setCommandListener(this);
            Display.getDisplay(this).setCurrent(gameCanvas);
        }
        catch (java.io.IOException e) { e.printStackTrace();}
        try {
            // start sounds
            InputStream in = getClass().getResourceAsStream("/resource/startfly.wav");
            player = Manager.createPlayer(in,"audio/x-wav");
            player.setLoopCount(1);
            player.start();

        } catch (MediaException ex) {
            ex.printStackTrace();
        }
         catch (IOException ex) {
            ex.printStackTrace();
        }  
    }


    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        if (player != null) {
            player.close();
        }
        System.gc();
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == exitCommand) {
            destroyApp(true);
            notifyDestroyed();
        }

    }
}
