/*
 * BackgroundLayer.java
 *
 * Created on 6. November 2006, 21:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package main;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author salho
 */
public class BackgroundLayer extends TiledLayer{

    public BackgroundLayer(int height) throws IOException{
        super(1,1, Image.createImage("/resource/background.png"),240,320);

    }

    private void init() {
    }

}
