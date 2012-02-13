/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

import GamePlay.CanvasGame;

/**
 *
 * @author QuyetNM1
 */
public class UpClock extends Clock{
    
    public UpClock(CanvasGame gameCanvas, int image, int x, int y, int time) {
        super(gameCanvas, image, x, y, time);
    }

    public void update() {
        this.currentTime = System.currentTimeMillis();
        if(this.currentTime - this.startTime >= Clock.SECOND) {
            this.startTime = System.currentTimeMillis();
            this.currentTime = this.startTime;
            ++ this.time;
        }
    }
}
