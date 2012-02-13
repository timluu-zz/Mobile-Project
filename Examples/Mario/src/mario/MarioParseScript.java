/**
 * <p>Title: Mario</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file any part without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>,
 *
 * @author lizhenpeng
 * @version 1.0.0
 */
package mario;

import javax.microedition.lcdui.Canvas;

public class MarioParseScript {

    public static class ActionFormat {

        public int action;
        public int actionCnt;
    }

    public static boolean parse(String action, ActionFormat format) {
        boolean result = true;
        int length;
        String subAction;
        // test the format is right
        action = action.toLowerCase();
        if (action.startsWith("<") && action.endsWith(">")) {
            int pos = action.indexOf(',');
            subAction = action.substring(1, pos).trim();
            if (subAction.compareTo("left") == 0) {
                format.action = Canvas.LEFT;
            } else if (subAction.compareTo("right") == 0) {
                format.action = Canvas.RIGHT;
            } else if (subAction.compareTo("jump") == 0) {
                format.action = Canvas.UP;
            } else if (subAction.compareTo("fire") == 0) {
                format.action = Canvas.FIRE;
            } else if (subAction.compareTo("down") == 0) {
                format.action = Canvas.DOWN;
            } else if (subAction.compareTo("stop") == 0) {
                format.action = 0;
            }
            format.actionCnt = Integer.parseInt(action.substring(pos + 1, action.length() - 1).trim());
        } else {
            result = false;
        }
        return result;
    }
}
