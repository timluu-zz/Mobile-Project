/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Object;

/**
 *
 * @author QuyetNM1
 */
public class Coordinate {

    public static final int MAX_COORD = 10;
    private int realWidth;
    private int state;

    public Coordinate(){
        realWidth = 0;
        state = 0;
    }
    public Coordinate(int realWidth, int state){
        this.realWidth = realWidth;
        this.state = state;
    }

    public int getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
}
