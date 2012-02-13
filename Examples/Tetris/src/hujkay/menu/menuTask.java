package hujkay.menu;

import java.util.TimerTask;

public class menuTask extends TimerTask {

    private MenuCanvas canvas;

    public menuTask(MenuCanvas canvas) {
        this.canvas = canvas;
    }

    public void run() {
        // TODO Auto-generated method stub
        canvas.repaint();
    }
}
