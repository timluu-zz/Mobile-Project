/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

import Screen.*;
import java.util.Vector;
import javax.microedition.lcdui.Font;

/**
 *
 * @author TanNT
 */
public class StringUtils {

    public static int getStringWidth(String input, Font font) {
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            result += font.charWidth(input.charAt(i));
        }
        return result;
    }

    public static int getStringHeigh(Font font) {
        return  font.getHeight();
    }

    /**
     *
     * @param str
     * @param offset
     * @param x
     * @param y
     * @param f
     * @param screenWidth
     * @param row
     * @return
     */
//    static Vector ret = new Vector();
//    public static Vector splitText(String str, int offset, int x, int y, Font f, int screenWidth, int row) {
//        ret.removeAllElements();
//        String ss = "";
//        int temp = y;
//        int dumoffset = 0;
//        int tempSize = str.length();
//        str = str + "   ";
//        int temp22 = screenWidth - 2 * x - 6;
//        for (int i = 0; i < tempSize; i++) {
//            if (row > 0 && ret.size() == row) {
//                break; // get limited row
//            }
//            if (y == temp) {
//                dumoffset = offset;
//            } else {
//                dumoffset = 0;
//            }
//            if (f.stringWidth(ss) < temp22 - dumoffset) {
//                if ((int) str.charAt(i) == 10) { // enter key
//                    ret.addElement(ss);
//                    ss = "";
//                } else {
//                    ss = ss + str.charAt(i);
//                    if (i == tempSize - 1) {
//                        ret.addElement(ss);
//                    }
//                }
//            } else {
//                i--;
//                int cc = 0;
//                String dd = "";
//                int tempSize2 = ss.length();
//                for (int ee = 0; ee < tempSize2; ee++) {
//                    if (ss.charAt(tempSize2 - 1 - ee) == ' ') {
//                        cc = tempSize2 - 1 - ee;
//                        break;
//                    } else if (ee == tempSize2 - 1) {
//                        for (int ee1 = 0; ee1 < tempSize2; ee1++) {
//                            if ((ss.charAt(tempSize2 - 1 - ee1) == ':'
//                                    || ss.charAt(tempSize2 - 1 - ee1) == ';'
//                                    || ss.charAt(tempSize2 - 1 - ee1) == '/'
//                                    || ss.charAt(tempSize2 - 1 - ee1) == '.'
//                                    || ss.charAt(tempSize2 - 1 - ee1) == ',')) {
//                                cc = tempSize2 - ee1;
//                                break;
//                            } else if (ee1 == tempSize2 - 1) {
//                                cc = tempSize2 - 2;
//                            }
//                        }
//                    }
//                }
//                dd = ss.substring(cc);
//                ss = ss.substring(0, cc);
//                ret.addElement(ss);
//                ss = dd.trim();
//                y = y + f.getHeight();
//            }
//        }
//
//        return ret;
//    }

    public static Vector splitText(String str, Font f, int printWidth, int maxRow) {
        Vector retV = new Vector();
        String ss = "";
        int tempSize = str.length();
        str = str + "   ";
        for (int i = 0; i < tempSize; i++) {
            if (maxRow > 0 && retV.size() == maxRow) {
                break; // get limited row
            }
            if (f.stringWidth(ss) < printWidth) {
                if ((int) str.charAt(i) == 10) { // enter key
                    retV.addElement(ss);
                    ss = "";
                } else {
                    ss = ss + str.charAt(i);
                    if (i == (tempSize - 1)) {
                        retV.addElement(ss);
                    }
                }
            } else {
                i--;
                int cc = 0;
                String dd = "";
                int tempSize2 = ss.length();
                for (int ee = 0; ee < tempSize2; ee++) {
                    if (ss.charAt(tempSize2 - 1 - ee) == ' ') {
                        cc = tempSize2 - 1 - ee;
                        break;
                    } else if (ee == tempSize2 - 1) {
                        for (int ee1 = 0; ee1 < tempSize2; ee1++) {
                            if ((ss.charAt(tempSize2 - 1 - ee1) == ':'
                                    || ss.charAt(tempSize2 - 1 - ee1) == ';'
                                    || ss.charAt(tempSize2 - 1 - ee1) == '/'
                                    || ss.charAt(tempSize2 - 1 - ee1) == '.'
                                    || ss.charAt(tempSize2 - 1 - ee1) == ',')) {
                                cc = tempSize2 - ee1;
                                break;
                            } else if (ee1 == tempSize2 - 1) {
                                cc = tempSize2 - 2;
                            }
                        }
                    }
                }
                dd = ss.substring(cc);
                ss = ss.substring(0, cc);
                retV.addElement(ss);
                ss = dd.trim();
            }
        }
        return retV;
    }
}

