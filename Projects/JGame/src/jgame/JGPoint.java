package jgame;

/**
 * Minimal replacement of java.awt.Point.
 */
public class JGPoint {

    public int x = 0, y = 0;

    public JGPoint() {
    }

    public JGPoint(JGPoint p) {
        x = p.x;
        y = p.y;
    }

    public JGPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj instanceof JGPoint) {
            JGPoint point = (JGPoint) obj;
            return point.x == x && point.y == y;
        }
        return obj.hashCode() == hashCode();
    }

    public String toString() {
        return "JGPoint(" + x + "," + y + ")";
    }
}
