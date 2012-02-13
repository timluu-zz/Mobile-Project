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
 * @version 1.0.1
 *
 * <p> Revise History
 *
 * 2004.07.26 in order to change map's content,change final property to normal
 * V1.0.1 </p>
 */
package lipeng;

public class LPMaps {

    public char mapArray[][];
    public int x;
    public int y;
    public int w;
    public int h;
    public int tileSize;
    public final LPImageManage image;
    public String mapId;

    public LPMaps(LPImageManage image, char array[][], int w, int h, int tileSize, String id) {
        this.image = image;
        mapArray = array;
        this.x = 0;
        this.y = 0;
        this.w = w;
        this.h = h;
        this.tileSize = tileSize;
        mapId = id;
    }
}
