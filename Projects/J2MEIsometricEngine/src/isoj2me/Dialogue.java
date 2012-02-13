package isoj2me;

/**
 * <p>isoj2me: Character</p>
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
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class Dialogue {

    public String characterName = "";
    public String phrase = "";
    public int xPortrait, yPortrait;
    public int xText, yText, widthText, heightText;
    public String background = null;
    public int color = 0x000000;
    public int bgColor = 0xffffff;
    private boolean coordsSpecification = false;
    public boolean border = false;

    public Dialogue(String characterName, String phrase) {
        this.characterName = characterName;
        this.phrase = phrase;
    }

    public Dialogue(String characterName, String phrase, int xText, int yText,
            int widthText, int heightText, int xPortrait, int yPortrait,
            int bgColor, int color, String background, boolean border) {
        this.characterName = characterName;
        this.phrase = phrase;
        this.color = color;
        this.bgColor = bgColor;
        this.background = background;
        this.xText = xText;
        this.yText = yText;
        this.xPortrait = xPortrait;
        this.yPortrait = yPortrait;
        this.widthText = widthText;
        this.heightText = heightText;
        this.coordsSpecification = true;
        this.border = border;
    }

    public void draw(Graphics g, Hashtable cueImages) {
        if (background != null) {
            g.drawImage(Utility.loadImage(background, cueImages), 0, 0,
                    Graphics.TOP | Graphics.LEFT);

        } else {
            g.setColor(bgColor);
            g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
        }

        if (coordsSpecification == false) {
            xPortrait = 0;
            yPortrait = 0;
            xText = 3;
            yText = Utility.loadImage(characterName, cueImages).getHeight();
            widthText = g.getClipWidth() - 6;
            heightText = g.getClipHeight()
                    - Utility.loadImage(characterName, cueImages).getHeight();
            border = true;

        }
        g.drawImage(Utility.loadImage(characterName, cueImages), xPortrait,
                yPortrait, Graphics.TOP | Graphics.LEFT);
        g.setColor(color);

        paintMessage(g, phrase, cueImages);
        if (border) {
            g.setColor(color);
            g.drawRect(xText - 2, yText - 2, widthText + 3, heightText + 3);
        }

    }

    private Vector tokenizeString(String s, String separator) {
        Vector list = new Vector();
        StringBuffer tempString = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) != separator.charAt(0)) {
                tempString.append(s.charAt(i));
            } else {
                tempString.append(s.charAt(i));
                String newString = tempString.toString();
                list.addElement(newString);
                tempString.delete(0, tempString.length());
            }

        }
        String newString = tempString.toString();
        list.addElement(newString);

        return list;

    }

    private Vector divideString(String s, int width, Font f) {
        Vector list = new Vector();
        StringBuffer tempString = new StringBuffer("");
        if (f.stringWidth(s) < width) {
            list.addElement(s);
            return list;
        }

        Vector words = tokenizeString(s, " ");

        for (int i = 0; i < words.size(); i++) {

            if (f.stringWidth(tempString.toString() + words.elementAt(i)) < width) {
                tempString.append((String) words.elementAt(i));
            } else {

                String newString = tempString.toString();
                list.addElement(newString);
                tempString.delete(0, tempString.length());
                tempString.append((String) words.elementAt(i));

            }

        }
        String newString = tempString.toString();
        list.addElement(newString);

        return list;
    }

    private void paintMessage(Graphics g, String message, Hashtable cueImages) {
        Vector list = divideString(message, g.getClipWidth(), g.getFont());
        int row = 0;
        int fontHeight = g.getFont().getHeight() + 1;

        for (int i = 0; i < list.size(); i++) {
            g.drawString(list.elementAt(i).toString(), xText, yText
                    + (i * fontHeight), Graphics.TOP | Graphics.LEFT);
        }

    }
}