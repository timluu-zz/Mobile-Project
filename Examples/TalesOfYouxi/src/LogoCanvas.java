import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class LogoCanvas extends GameCanvas implements Runnable {
	private Rpg parent;
	private Graphics vbuf;
	private Image logo;
	public LogoCanvas(Rpg p) {
		super(false);
		parent=p;
		try{
			logo=Image.createImage("/res/logo.png");
			vbuf=this.getGraphics();
		}catch(IOException ioe){
		}
	}

	public void paint(Graphics g) {
	}

	public void run() {
		int i,j;
		for(j=0;j<5;j++){
			vbuf.setColor(0);
			vbuf.fillRect(0,0,getWidth(),getHeight());
			vbuf.drawImage(logo,60,100,Graphics.TOP|Graphics.LEFT);
			for(i=0;i<12;i++){
				vbuf.fillRect(60,100+i*5,120,5-j);
			}
			flushGraphics();
			pause(400);
		}
		vbuf.setColor(0);
		vbuf.fillRect(0,0,getWidth(),getHeight());
		vbuf.drawImage(logo,60,100,Graphics.TOP|Graphics.LEFT);
			flushGraphics();
		pause(1500);
		parent.setState(parent.STATE_MENU);     //  switch to mainmenu
		return;
	}	
	private void pause(long n){
		try{
			Thread.currentThread().sleep(n);
		}catch(InterruptedException ie){
		}
	}
}
