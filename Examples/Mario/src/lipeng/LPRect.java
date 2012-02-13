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

public class LPRect {

    public int x;
    public int y;
    public int dx;
    public int dy;

    public static boolean IntersectRect(LPRect rect1, LPRect rect2) {
        if ((rect1.x + rect1.dx > rect2.x) && (rect1.y + rect1.dy > rect2.y)
                && (rect2.x + rect2.dy > rect1.x) && (rect2.y + rect2.dy > rect1.y)) {
            return true;
        }
        return false;

    }
}