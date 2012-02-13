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
 * 2004.07.12 Add exception description and change out.dat to lipeng.dat v1.1.0
 * </p>
 */
package lipeng;

import javax.microedition.lcdui.Image;
import java.io.*;

public class LPImageManage {

    public Image image;
    public int frameNum;
    public int frameSize;
    public String imageId;

    public LPImageManage(String fileName) {
        try {
            imageId = fileName;
            image = Image.createImage("/" + fileName);
            frameSize = image.getWidth();
            frameNum = image.getHeight() / frameSize;
        } catch (java.io.IOException e) {
            System.out.println("Image, " + fileName + ", could not be loaded.");
            System.out.println(e.toString());
        }
    }

    public LPImageManage(String fileName, int pos) {
        try {
            imageId = fileName;
            InputStream in = getClass().getResourceAsStream("/lipeng.dat");
            DataInputStream dataInput = new DataInputStream(in);
            dataInput.skip(2 + (pos - 1) * 4 * 2);
            byte d = dataInput.readByte();
            byte c = dataInput.readByte();
            byte b = dataInput.readByte();
            byte a = dataInput.readByte();
            int byteSize = (((a & 0xff) << 24) | ((b & 0xff) << 16)
                    | ((c & 0xff) << 8) | (d & 0xff));
            d = dataInput.readByte();
            c = dataInput.readByte();
            b = dataInput.readByte();
            a = dataInput.readByte();
            int offset = (((a & 0xff) << 24) | ((b & 0xff) << 16)
                    | ((c & 0xff) << 8) | (d & 0xff));

            dataInput.close();
            in = getClass().getResourceAsStream("/lipeng.dat");
            dataInput = new DataInputStream(in);
            byte buffer[] = new byte[byteSize];
            dataInput.skip(offset);
            dataInput.read(buffer);
            image = Image.createImage(buffer, 0, buffer.length);
            frameSize = image.getWidth();
            frameNum = image.getHeight() / frameSize;
            dataInput.close();
            buffer = null;
        } catch (java.io.IOException e) {
            System.out.println("Image, " + fileName + ", could not be loaded.");
            System.out.println(e.toString());
        }
    }
}