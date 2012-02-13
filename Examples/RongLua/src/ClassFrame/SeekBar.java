/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

import GamePlay.CanvasGame;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author QuyetNM1
 */
public class SeekBar extends FormItem{
    public static final int MIN_X = 197;
    public static final int MAX_X = 386;
    public static final int SOUND_d = 386;
    private int sound_x;
    private int sound_y;
    private int tg_x;
    public SeekBar(CanvasGame canvas, int imageSeekBar, int imageVolume, int sound_x, int sound_y, int volume_x, int volume_y, int volume_w, int volume_h, int state){
        super(canvas, "", imageSeekBar, imageVolume, 0, volume_x, volume_y, volume_w, volume_h, state);
        this.sound_x = sound_x;
        this.sound_y = sound_y;
    }

    public void pointerEvent(int x, int y, int event) {
        switch (event) {
            case InputKey.POINTER_EVENT_DOWN:
                if (state == ENABLE && canvas.checkRegion(x, y, this.x, this.y, w, h)) {
                    state = HOLD_CLICK;
                    tg_x = y - this.x;
                                        
                }
                break;
            case InputKey.POINTER_EVENT_DRAG:
                if(state == HOLD_CLICK){
                    this.x = y -  tg_x;
                    if(this.x < MIN_X)  this.x = MIN_X;
                    else if(this.x > MAX_X)  this.x = MAX_X;
                    CanvasGame.musicLevel = ((this.x - MIN_X) * (MAX_X - MIN_X))/ 100;
                }
                break;
            case InputKey.POINTER_EVENT_UP:
                if(state == HOLD_CLICK){
                    this.x = y -  tg_x;
                    if(this.x < MIN_X)  this.x = MIN_X;
                    else if(this.x > MAX_X)  this.x = MAX_X;
                    state = ENABLE;
                }
                break;
        }
    }
    public void drawSeekBar(Graphics g){
        canvas.getImage().drawImage(g, canvas, image1, sound_x, sound_y);
        canvas.getImage().drawImage(g, canvas, image2, x, y);
    }
}
