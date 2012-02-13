/**
 * <p>Title: lipeng</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file without the express permission of the author. All Rights
 * Reserved</p>
 *
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

import java.io.*;

public class LPReadScriptFile {

    private StringBuffer readBuffer = new StringBuffer();
    private InputStream is;
    private DataInputStream dis;
    private boolean isEnd = false;

    public void openFile(String fileName) {
        isEnd = false;
        is = getClass().getResourceAsStream("/" + fileName);
        dis = new DataInputStream(is);
    }

    public void closeFile() {
        if (dis != null) {
            try {
                dis.close();
            } catch (Exception e) {
            }
            dis = null;
        }
    }

    public boolean isEOF() {
        return isEnd;
    }

    public String readLine() {
        //"\n" 0D,0A
        int b = 0;
        readBuffer.delete(0, readBuffer.length());
        try {
            while (true) {
                b = dis.readUnsignedByte();
                if (b == 0x0d) {
                    break;
                } else {
                    readBuffer.append((char) b);
                }
            }
            dis.readUnsignedByte();
            return readBuffer.toString();
        } catch (EOFException e) {
            isEnd = true;
            return readBuffer.toString();
        } catch (IOException e) {
            System.out.println(e);
        }
        return readBuffer.toString();
    }
}