
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Hero {

    private Image image;

    public Hero() {
    }

    public void load() {
        try {
            image = Image.createImage("/hero.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Sprite getHero() {
        Image tmp = null;
        try {
            tmp = Image.createImage(image, 0, 0, 72, 116, Sprite.TRANS_NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Sprite hero = new Sprite(tmp, 24, 29);
        return hero;
    }
}