
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class BlastSprite extends Sprite {

    BlastSprite(Image image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);
        defineReferencePixel(frameWidth / 2, frameHeight / 2);
        setVisible(false);
    }

    public void Logic() {
        if (isVisible() == false) {
            return;
        }
        int nFrame = getFrame();
        nFrame++;
        if (nFrame > 7) {
            setVisible(false);
            return;
        }
        setFrame(nFrame);
    }
}
