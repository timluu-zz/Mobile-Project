package game;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.IOException;

/**
 * @author joteitti
 */
public class GameGraphics {

//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
private Image Tiles;
private Image tausta;
private TiledLayer Collision;
private TiledLayer Destructable;
private TiledLayer Background;
private Image menu_background;
private TiledLayer menu_bg;
private Image Player;
private Sprite Mario;
public int MarioJumpLeftDelay = 200;
public int[] MarioJumpLeft = {12};
public int MarioLostDelay = 200;
public int[] MarioLost = {6};
public int MarioIdleRightDelay = 200;
public int[] MarioIdleRight = {0};
public int MarioMoveRightDelay = 120;
public int[] MarioMoveRight = {1, 2, 3};
public int MarioMoveLeftDelay = 110;
public int[] MarioMoveLeft = {8, 9, 10};
public int MarioJumpRightDelay = 200;
public int[] MarioJumpRight = {5};
public int MarioIdleLeftDelay = 200;
public int[] MarioIdleLeft = {7};
//</editor-fold>//GEN-END:|fields|0|

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
//</editor-fold>//GEN-END:|methods|0|
public Image getTiles() throws java.io.IOException {//GEN-BEGIN:|1-getter|0|1-preInit
if (Tiles == null) {//GEN-END:|1-getter|0|1-preInit
    // write pre-init user code here
Tiles = Image.createImage("/Tiles.png");//GEN-BEGIN:|1-getter|1|1-postInit
}//GEN-END:|1-getter|1|1-postInit
    // write post-init user code here
return this.Tiles;//GEN-BEGIN:|1-getter|2|
}//GEN-END:|1-getter|2|

public TiledLayer getCollision() throws java.io.IOException {//GEN-BEGIN:|3-getter|0|3-preInit
if (Collision == null) {//GEN-END:|3-getter|0|3-preInit
    // write pre-init user code here
Collision = new TiledLayer(50, 10, getTiles(), 32, 32);//GEN-BEGIN:|3-getter|1|3-midInit
int[][] tiles = {
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 19, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 19, 0, 18, 19, 0, 0, 0, 0, 61, 62, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 61, 62, 0, 61, 62, 0, 0, 37, 38, 39, 62, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 52, 53, 54, 62, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
};//GEN-END:|3-getter|1|3-midInit
    // write mid-init user code here
for (int row = 0; row < 10; row++) {//GEN-BEGIN:|3-getter|2|3-postInit
for (int col = 0; col < 50; col++) {
Collision.setCell(col, row, tiles[row][col]);
}
}
}//GEN-END:|3-getter|2|3-postInit
    // write post-init user code here
	return Collision;//GEN-BEGIN:|3-getter|3|
}//GEN-END:|3-getter|3|

public TiledLayer getDestructable() throws java.io.IOException {//GEN-BEGIN:|5-getter|0|5-preInit
if (Destructable == null) {//GEN-END:|5-getter|0|5-preInit
    // write pre-init user code here
Destructable = new TiledLayer(50, 10, getTiles(), 32, 32);//GEN-BEGIN:|5-getter|1|5-midInit
int[][] tiles = {
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 0, 0, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 120, 0, 120, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 120, 0, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 51, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 131, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 131, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
};//GEN-END:|5-getter|1|5-midInit
    // write mid-init user code here
for (int row = 0; row < 10; row++) {//GEN-BEGIN:|5-getter|2|5-postInit
for (int col = 0; col < 50; col++) {
Destructable.setCell(col, row, tiles[row][col]);
}
}
}//GEN-END:|5-getter|2|5-postInit
    // write post-init user code here
	return Destructable;//GEN-BEGIN:|5-getter|3|
}//GEN-END:|5-getter|3|

public TiledLayer getBackground() throws java.io.IOException {//GEN-BEGIN:|6-getter|0|6-preInit
if (Background == null) {//GEN-END:|6-getter|0|6-preInit
    // write pre-init user code here
Background = new TiledLayer(50, 10, getTiles(), 32, 32);//GEN-BEGIN:|6-getter|1|6-midInit
int[][] tiles = {
{ 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90 },
{ 90, 90, 91, 92, 93, 90, 90, 90, 90, 90, 90, 90, 90, 90, 91, 92, 93, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 91, 92, 93, 90, 90, 90, 90, 91, 92, 92, 93, 90, 90, 90, 90, 90, 91, 92, 93 },
{ 90, 90, 106, 107, 108, 90, 90, 90, 91, 92, 93, 90, 90, 90, 106, 107, 108, 90, 90, 90, 90, 90, 91, 92, 92, 93, 90, 90, 90, 90, 90, 106, 107, 108, 90, 90, 90, 90, 106, 107, 107, 108, 90, 91, 92, 83, 90, 106, 107, 108 },
{ 90, 90, 90, 90, 90, 90, 90, 90, 106, 107, 108, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 106, 107, 107, 108, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 32, 90, 90, 90, 90, 90, 106, 83, 77, 83, 90, 90, 90 },
{ 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 46, 47, 48, 90, 90, 90, 90, 90, 80, 80, 80, 90, 90, 90 },
{ 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 80, 77, 80, 90, 90, 90 },
{ 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 41, 90, 90, 90, 90, 90, 90, 30, 30, 30, 30, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 83, 83, 78, 78, 78, 83, 83, 90 },
{ 90, 90, 90, 90, 90, 30, 30, 30, 30, 90, 90, 90, 90, 90, 90, 90, 90, 90, 56, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 80, 80, 77, 80, 77, 80, 80, 90 },
{ 90, 2, 3, 4, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 71, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 80, 77, 80, 77, 80, 77, 80, 90 },
{ 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90 }
};//GEN-END:|6-getter|1|6-midInit
    // write mid-init user code here
for (int row = 0; row < 10; row++) {//GEN-BEGIN:|6-getter|2|6-postInit
for (int col = 0; col < 50; col++) {
Background.setCell(col, row, tiles[row][col]);
}
}
}//GEN-END:|6-getter|2|6-postInit
    // write post-init user code here
	return Background;//GEN-BEGIN:|6-getter|3|
}//GEN-END:|6-getter|3|

public void updateLayerManagerForScene(LayerManager lm) throws java.io.IOException {//GEN-LINE:|22-updateLayerManager|0|22-preUpdate
    // write pre-update user code here
getCollision().setPosition(0, 0);//GEN-BEGIN:|22-updateLayerManager|1|22-postUpdate
getCollision().setVisible(true);
lm.append(getCollision());
getBackground().setPosition(0, 0);
getBackground().setVisible(true);
lm.append(getBackground());//GEN-END:|22-updateLayerManager|1|22-postUpdate
    // write post-update user code here
}//GEN-LINE:|22-updateLayerManager|2|

public Image getPlayer() throws java.io.IOException {//GEN-BEGIN:|47-getter|0|47-preInit
if (Player == null) {//GEN-END:|47-getter|0|47-preInit
    // write pre-init user code here
Player = Image.createImage("/Player.png");//GEN-BEGIN:|47-getter|1|47-postInit
}//GEN-END:|47-getter|1|47-postInit
    // write post-init user code here
return this.Player;//GEN-BEGIN:|47-getter|2|
}//GEN-END:|47-getter|2|

public Sprite getMario() throws java.io.IOException {//GEN-BEGIN:|63-getter|0|63-preInit
if (Mario == null) {//GEN-END:|63-getter|0|63-preInit
    // write pre-init user code here
Mario = new Sprite(getPlayer(), 24, 48);//GEN-BEGIN:|63-getter|1|63-postInit
Mario.setFrameSequence(MarioIdleRight);//GEN-END:|63-getter|1|63-postInit
    // write post-init user code here
}//GEN-BEGIN:|63-getter|2|
	return Mario;
}//GEN-END:|63-getter|2|

public Image getTausta() throws java.io.IOException {//GEN-BEGIN:|70-getter|0|70-preInit
if (tausta == null) {//GEN-END:|70-getter|0|70-preInit
    // write pre-init user code here
tausta = Image.createImage("/menu_background.png");//GEN-BEGIN:|70-getter|1|70-postInit
}//GEN-END:|70-getter|1|70-postInit
    // write post-init user code here
return this.tausta;//GEN-BEGIN:|70-getter|2|
}//GEN-END:|70-getter|2|

public Image getMenu_background() throws java.io.IOException {//GEN-BEGIN:|72-getter|0|72-preInit
if (menu_background == null) {//GEN-END:|72-getter|0|72-preInit
    // write pre-init user code here
menu_background = Image.createImage("/menu_background.png");//GEN-BEGIN:|72-getter|1|72-postInit
}//GEN-END:|72-getter|1|72-postInit
    // write post-init user code here
return this.menu_background;//GEN-BEGIN:|72-getter|2|
}//GEN-END:|72-getter|2|

public TiledLayer getMenu_bg() throws java.io.IOException {//GEN-BEGIN:|73-getter|0|73-preInit
if (menu_bg == null) {//GEN-END:|73-getter|0|73-preInit
    // write pre-init user code here
menu_bg = new TiledLayer(20, 20, getMenu_background(), 240, 320);//GEN-BEGIN:|73-getter|1|73-midInit
int[][] tiles = {
{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
};//GEN-END:|73-getter|1|73-midInit
    // write mid-init user code here
for (int row = 0; row < 20; row++) {//GEN-BEGIN:|73-getter|2|73-postInit
for (int col = 0; col < 20; col++) {
menu_bg.setCell(col, row, tiles[row][col]);
}
}
}//GEN-END:|73-getter|2|73-postInit
    // write post-init user code here
	return menu_bg;//GEN-BEGIN:|73-getter|3|
}//GEN-END:|73-getter|3|
}
