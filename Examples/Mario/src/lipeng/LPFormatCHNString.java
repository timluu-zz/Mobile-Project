/**
 * <p>Title: lipeng</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.0.0
 *
 * <p> Revise History </p>
 */
package lipeng;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class LPFormatCHNString {

    public LPFormatCHNString(String str, int width, int height, Font font) {
        helpStr = str;
        strLen = str.length();
        beginLine = 0;
        this.font = font;

        String benchStr = "��";
        char[] bufferStr = benchStr.toCharArray();
        int charWidth = font.charsWidth(bufferStr, 0, bufferStr.length);
        lineStrLen = width / charWidth;
        strHeightNum = height / font.getHeight();

        endLine = strHeightNum;
        maxLine = strLen / lineStrLen + (strLen % lineStrLen == 0 ? 0 : 1);
    }

    public void paint(Graphics g) {
        int i;
        for (i = beginLine; i < endLine; ++i) {
            if ((i + 1) * lineStrLen <= strLen) {
                g.drawString(helpStr.substring(i * lineStrLen, (i + 1) * lineStrLen), 0,
                        font.getHeight() * (i - beginLine), Graphics.LEFT | Graphics.TOP);
            } else {
                g.drawString(helpStr.substring(i * lineStrLen, strLen), 0,
                        font.getHeight() * (i - beginLine), Graphics.LEFT | Graphics.TOP);
            }
        }
    }

    public void action() {
        if ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) != 0) {
            allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
            if (beginLine != 0) {
                --beginLine;
                --endLine;
            }
        } else if ((allAction & LPKeyMask.MASK_KEY_DOWN_FLAG) != 0) {
            allAction &= ~LPKeyMask.MASK_KEY_DOWN_FLAG;
            if (endLine != maxLine) {
                ++beginLine;
                ++endLine;
            }
        }
    }

    public void judgeKeyCode(int keyCode, int gameKeyCode) {
        switch (gameKeyCode) {
            case Canvas.UP:
                if ((((allAction & LPKeyMask.MASK_KEY_UP) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) == 0))) {
                    allAction |= LPKeyMask.MASK_KEY_UP_FLAG | LPKeyMask.MASK_KEY_UP;
                }
                break;
            case Canvas.DOWN:
                if ((((allAction & LPKeyMask.MASK_KEY_DOWN) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_DOWN_FLAG) == 0))) {
                    allAction |= LPKeyMask.MASK_KEY_DOWN_FLAG | LPKeyMask.MASK_KEY_DOWN;
                }
                break;
        }
    }

    public void reInit() {
        allAction = 0;
        beginLine = 0;
        endLine = strHeightNum;
    }

    public void freeKey(int keyCode, int gameKeyCode) {
        switch (gameKeyCode) {
            case Canvas.UP:
                allAction &= ~LPKeyMask.MASK_KEY_UP;
                break;
            case Canvas.DOWN:
                allAction &= ~LPKeyMask.MASK_KEY_DOWN;
                break;
        }
    }
    private int beginLine;
    private int endLine;
    private int allAction;
    private int lineStrLen;
    private int strHeightNum;
    private String helpStr;
    private int strLen;
    private Font font;
    private int maxLine;
}