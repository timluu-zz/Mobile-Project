package hujkay.game;

import java.util.TimerTask;

public class GameTask extends TimerTask {

    GameCanvas canvas;

    public GameTask(GameCanvas canvas) {
        this.canvas = canvas;
    }

    public void run() {
        // TODO Auto-generated method stub
        canvas.Timerrun();
    }
}
