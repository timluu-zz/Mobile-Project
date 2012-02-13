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
 * @version 1.0.3
 *
 * <p> Revise History
 *
 * 2004.07.12 Revise magic number may reduce speed V1.0.1
 *
 * 2004.07.28 remove setclip back to full screen V1.0.2
 *
 * 2004.08.12 revise function name first letter to small V1.0.3 </p>
 */
package lipeng;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class LPDrawManage {

    private int width;
    private int height;
    private int currentHeight = 0;
    private int currentWidth = 0;
    private int nX = 0;
    private int nY = 0;
    private int beginX = 0;
    private int beginY = 0;
    private int tempX;
    private int tempY;
    private char arrayNum = 0;

    public LPDrawManage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawMaps(Graphics g, LPMaps maps[]) {
        int i = 0;

        for (i = 0; i < maps.length; i++) {
            beginX = 0;
            beginY = 0;
            nX = maps[i].x % maps[i].tileSize;
            nY = maps[i].y % maps[i].tileSize;
            while (true) {
                while (true) {
                    //if array's num is 0xff, map is not draw;
                    tempY = ((beginX + maps[i].x) / maps[i].tileSize) % maps[i].w;
                    tempX = ((beginY + maps[i].y) / maps[i].tileSize) % maps[i].h;
                    if (tempY < 0 || tempX < 0) {
                        beginY += maps[i].tileSize;
                        continue;
                    }
                    arrayNum = (char) (maps[i].mapArray[tempX][tempY] & (0x00FF));
                    if (arrayNum != 0xff) {
                        currentWidth = beginX - nX;
                        currentHeight =
                                beginY - (arrayNum * maps[i].tileSize);
                        g.setClip(currentWidth, beginY - nY, maps[i].tileSize,
                                maps[i].tileSize);
                        g.drawImage(maps[i].image.image, currentWidth, currentHeight - nY,
                                Graphics.LEFT | Graphics.TOP);
                        // g.setClip(0, 0, width, height);
                    }
                    if (beginY < height) {
                        beginY += maps[i].tileSize;
                    } else {
                        break;
                    }
                }
                if (beginX < width) {
                    beginX += maps[i].tileSize;
                    beginY = 0;
                } else {
                    break;
                }
            }
        }
    }

    public void drawMaps(Graphics g, LPMaps maps) {
        currentHeight = 0;
        currentWidth = 0;
        nX = 0;
        nY = 0;
        beginX = 0;
        beginY = 0;
        arrayNum = 0;
        if (maps.x < 0) {
            maps.x += maps.w * maps.tileSize;
        }
        if (maps.y < 0) {
            maps.y += maps.h * maps.tileSize;
        }
        nX = (maps.x) % maps.tileSize;
        nY = (maps.y) % maps.tileSize;
        while (true) {
            while (true) {
                tempY = ((beginX + maps.x) / maps.tileSize) % maps.w;
                tempX = ((beginY + maps.y) / maps.tileSize) % maps.h;
                if (tempY < 0 || tempX < 0) {
                    beginY += maps.tileSize;
                    continue;
                }
                arrayNum = (char) (maps.mapArray[tempX][tempY] & (0x00FF));
                if (arrayNum != 0xFF) {
                    currentWidth = beginX - nX;
                    currentHeight = beginY - (arrayNum * maps.tileSize);
                    g.setClip(currentWidth, beginY - nY, maps.tileSize, maps.tileSize);
                    g.drawImage(maps.image.image, currentWidth, currentHeight - nY,
                            Graphics.LEFT | Graphics.TOP);
                    // g.setClip(0, 0, width, height);
                }
                if (beginY < height) {
                    beginY += maps.tileSize;
                } else {
                    break;
                }
            }
            if (beginX < width) {
                beginX += maps.tileSize;
                beginY = 0;
            } else {
                break;
            }
        }
    }

    public void drawSprite(Graphics g, LPImageManage im, LPSprite sprite) {
        if (!sprite.isHidden) {
            g.setClip(sprite.x,
                    sprite.y, im.frameSize,
                    im.frameSize);
            g.drawImage(im.image, sprite.x,
                    sprite.y
                    - sprite.frameCnt
                    * im.frameSize, g.LEFT | g.TOP);
            // g.setClip(0, 0, width, height);
        }
    }

    public void drawSprite(Graphics g, LPImageManage im, Vector vec) {
        if (vec == null) {
            return;
        }
        for (int i = vec.size() - 1; i >= 0; --i) {
            if (((LPSprite) vec.elementAt(i)).isHidden) {
                continue;
            }
            if (((LPSprite) vec.elementAt(i)).x < -im.frameSize
                    || ((LPSprite) vec.elementAt(i)).x > width
                    || ((LPSprite) vec.elementAt(i)).y < -im.frameSize
                    || ((LPSprite) vec.elementAt(i)).y > height + im.frameSize) {
                continue;
            }
            g.setClip(((LPSprite) vec.elementAt(i)).x, ((LPSprite) vec.elementAt(i)).y, im.frameSize, im.frameSize);
            g.drawImage(im.image, ((LPSprite) vec.elementAt(i)).x,
                    ((LPSprite) vec.elementAt(i)).y - ((LPSprite) vec.elementAt(i)).frameCnt * im.frameSize, g.LEFT | g.TOP);
            //g.setClip(0, 0, width, height);
        }

    }

    public void drawSprite(Graphics g, LPImageManage im, LPSprite[] sprites) {
        if (sprites == null) {
            return;
        }
        for (int i = sprites.length - 1; i >= 0; --i) {
            if (sprites[i].isHidden) {
                continue;
            }
            if (sprites[i].x < -im.frameSize
                    || sprites[i].x > width
                    || sprites[i].y < -im.frameSize
                    || sprites[i].y > height + im.frameSize) {
                continue;
            }
            g.setClip(sprites[i].x, sprites[i].y, im.frameSize, im.frameSize);
            g.drawImage(im.image, sprites[i].x,
                    sprites[i].y - sprites[i].frameCnt * im.frameSize, g.LEFT | g.TOP);
            //g.setClip(0, 0, width, height);
        }
    }
}
