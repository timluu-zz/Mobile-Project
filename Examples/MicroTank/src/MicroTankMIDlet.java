import java.io.IOException;
import java.io.PrintStream;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class MicroTankMIDlet
    extends MIDlet
    {
    MicroTankCanvas mMicroTankCanvas;
  
  
  public void startApp() {
    if (mMicroTankCanvas == null) {
      try {
        mMicroTankCanvas = new MicroTankCanvas(this);
      //  mMicroTankCanvas.start();
        
      }
      catch (IOException ioe) {
        System.out.println(ioe);
      }
    }
    
    Display.getDisplay(this).setCurrent(mMicroTankCanvas);
  }
  
  public void pauseApp() { 
	  mMicroTankCanvas.stop();
}
  
  public void destroyApp(boolean unconditional) {
    mMicroTankCanvas.stop();
}

}
