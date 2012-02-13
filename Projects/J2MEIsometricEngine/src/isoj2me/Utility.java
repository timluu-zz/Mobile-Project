package isoj2me;

import java.util.Hashtable;
import javax.microedition.lcdui.Image;

/**
 * <p>isoj2me: Utility</p>
 *
 * <p>This class contains utility methods.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>License: Lesser GPL (http://www.gnu.org)</p>
 *
 * <p>Company: <a href=http://www.mondonerd.com
 * target="_blank">Mondonerd.com</a></p>
 *
 * @author Massimo Maria Avvisati
 * @version 0.5b
 */
public class Utility {

    public Utility() {
    }

    /**
     * This method return an Image loading it from the "hard disk" of the mobile
     * phone (from the standard directory) or, if the image was already loaded,
     * it return an image took from an hashtable
     *
     * @param imageCode alphanumeric code for this image (filename)
     * @param imagesHash where to store Image objects
     * @return the requested Image
     */
    public static Image loadImage(String imageCode, Hashtable imagesHash) {
        if (imagesHash.containsKey(imageCode)) {
            return (Image) imagesHash.get(imageCode);
        } else {
            try {
                Image temp_image = Image.createImage("/" + imageCode + ".png");
                imagesHash.put(imageCode, temp_image);
                return (Image) imagesHash.get(imageCode);
            } catch (Exception ex) {
                System.out.println("loadImage Error can't load " + imageCode + ":" + ex);
            }
        }
        return null;
    }
}