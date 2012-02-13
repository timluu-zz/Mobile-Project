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
 * @version 1.1.0
 *
 * <p> Revise History
 *
 * 2004.08.12 add 2 member function for save data and read data
 *
 * change data member access level V1.1.0 </p>
 */
package lipeng;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LPSprite {

    public int x;
    public int y;
    public int frameCnt;
    public boolean isHidden;
    protected int timeCnt;
    protected int offset;

    public void action() {
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        isHidden = false;
    }

    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(frameCnt);
        dos.writeBoolean(isHidden);
        dos.writeInt(timeCnt);
        dos.writeInt(offset);
    }

    public void readData(DataInputStream dis) throws IOException {
        x = dis.readInt();
        y = dis.readInt();
        frameCnt = dis.readInt();
        isHidden = dis.readBoolean();
        timeCnt = dis.readInt();
        offset = dis.readInt();
    }
}
