/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

/**
 *
 * @author QuyetNM1
 */
public class TransCoordinate {
    public static final int SCR_W = 320;

    public static int getX(int X, int Y){
        return SCR_W - Y;
    }

    public static int getY(int X, int Y){
        return X;
    }
}
