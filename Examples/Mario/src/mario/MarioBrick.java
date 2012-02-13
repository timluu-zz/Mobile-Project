/**
 * <p>Title: Mario</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file any part without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.0.0
 */
package mario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import lipeng.LPImageManage;
import lipeng.LPRect;
import lipeng.LPSprite;

public class MarioBrick extends LPSprite {

    public MarioBrick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void createGem(MarioGameManage gm) {
    }
}

class MarioBrickHidden extends MarioBrick {

    public MarioBrickHidden(int x, int y) {
        super(x, y);
        isHidden = true;
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        isHidden = true;
    }

    public void createGem(MarioGameManage gm) {
        if (isHidden) {
            isHidden = false;
            gm.createGold(x, y);
            if (++gm.goldNum == 100) {
                gm.goldNum = 0;
            }
            if (gm.menu.isVolOn) {
                gm.beatPlayer.replay();
            }
            frameCnt = 0;
        }
    }
}

class MarioGemHidden extends MarioBrick {

    public MarioGemHidden(int x, int y) {
        super(x, y);
        isHidden = true;
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        isHidden = true;
    }

    public void createGem(MarioGameManage gm) {
        if (isHidden) {
            isHidden = false;
            gm.createGem(x, y);
            frameCnt = 0;
        }
    }
}

class MarioBrickGemHidden extends MarioBrick {

    private boolean hasUsed = false;

    public MarioBrickGemHidden(int x, int y) {
        super(x, y);
        isHidden = true;
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        isHidden = true;
    }

    public void createGem(MarioGameManage gm) {
        if (isHidden) {
            if (!hasUsed) {
                isHidden = false;
                gm.createGemPeople(x, y);
                hasUsed = true;
                frameCnt = 0;
            }
        }
    }
}

class MarioBrickGemQuestion extends MarioBrick {

    public MarioBrickGemQuestion(int x, int y) {
        super(x, y);
        this.frameCnt = 3;
    }

    public void action() {
        if (frameCnt != 0) {
            if (++timeCnt % 3 == 0) {
                frameCnt = 5 - frameCnt;
            }
        }
    }

    public void reInit(int x, int y) {
        super.reInit(x, y);
        frameCnt = 3;
    }

    public void createGem(MarioGameManage gm) {
        if (frameCnt != 0) {
            gm.createGem(x, y);
            frameCnt = 0;
        }
    }
}

class MarioBrickGem extends MarioBrick {

    public MarioBrickGem(int x, int y) {
        super(x, y);
        this.frameCnt = 1;
    }

    public void reInit(int x, int y) {
        super.reInit(x, y);
        frameCnt = 1;
    }

    public void createGem(MarioGameManage gm) {
        if (frameCnt != 0) {
            gm.createGem(x, y);
            frameCnt = 0;
        }
    }
}

class MarioBrickGold extends MarioBrick {

    protected boolean beginCount;

    public MarioBrickGold(int x, int y) {
        super(x, y);
        this.frameCnt = 1;
        beginCount = false;
    }

    public void action() {
        if (frameCnt != 0) {
            if (beginCount) {
                ++timeCnt;
            }
        }
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        beginCount = false;
        frameCnt = 1;
        timeCnt = 0;
    }

    public void createGem(MarioGameManage gm) {
        if (frameCnt != 0) {
            beginCount = true;
            gm.createGold(x, y);
            if (++gm.goldNum == 100) {
                gm.goldNum = 0;
            }
            if (gm.menu.isVolOn) {
                gm.beatPlayer.replay();
            }

            if (timeCnt >= 60) {
                frameCnt = 0;
            }
        }
    }

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeBoolean(beginCount);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        beginCount = dis.readBoolean();
    }
}

class MarioBrickGoldQuestion extends MarioBrick {

    public MarioBrickGoldQuestion(int x, int y) {
        super(x, y);
        frameCnt = 3;
    }

    public void reInit(int x, int y) {
        super.reInit(x, y);
        frameCnt = 3;
    }

    public void action() {
        if (frameCnt != 0) {
            if (++timeCnt % 3 == 0) {
                frameCnt = 5 - frameCnt;
            }
        }
    }

    public void createGem(MarioGameManage gm) {
        if (frameCnt != 0) {
            gm.createGold(x, y);
            frameCnt = 0;
            if (++gm.goldNum == 100) {
                gm.goldNum = 0;
            }
            if (gm.menu.isVolOn) {
                gm.beatPlayer.replay();
            }

        }
    }
}

class Mashroom extends LPSprite {

    private int growCnt;
    private MarioGameManage gm;
    private int dy;
    private LPRect rect1 = new LPRect();
    private LPRect rect2 = new LPRect();
    private int xSpeed;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(growCnt);
        dos.writeInt(dy);
        dos.writeInt(xSpeed);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        growCnt = dis.readInt();
        dy = dis.readInt();
        xSpeed = dis.readInt();
    }

    public Mashroom(MarioGameManage gm) {
        isHidden = true;
        frameCnt = 6;
        this.gm = gm;
        rect1.dx = 16;
        rect1.dy = 16;
        rect2.dx = 16;
        rect2.dy = 16;
    }

    public void changeDirection() {
        dy -= 5;
        xSpeed = -xSpeed;
    }

    private boolean checkTileCollisionHorn() {
        int i;
        rect1.y = this.y + gm.mainSprite.judgeMap.y;
        if (xSpeed < 0) {
            rect1.x = x + gm.mainSprite.judgeMap.x - 4;
            i = (x + gm.mainSprite.judgeMap.x - 4) / gm.mainSprite.judgeMap.tileSize;
        } else {
            rect1.x = x + gm.mainSprite.judgeMap.x + 4;
            i = (x + gm.mainSprite.judgeMap.x + 4) / gm.mainSprite.judgeMap.tileSize + 1;
        }
        if (i < 0 || i > gm.mainSprite.judgeMap.w - 1) {
            return false;
        }
        int yTile1 = (y + gm.mainSprite.judgeMap.y) / gm.mainSprite.judgeMap.tileSize;
        int yTile2 = (y + gm.mainSprite.judgeMap.y) / gm.mainSprite.judgeMap.tileSize + 1;
        if (yTile1 < 0 || yTile2 > gm.mainSprite.judgeMap.h - 1) {
            return false;
        }
        if (((gm.mainSprite.judgeMap.mapArray[yTile1][i] >> 8) & 0x04) == 0x04) {
            rect2.x = i * 16;
            rect2.y = yTile1 * 16;
            if (LPRect.IntersectRect(rect1, rect2)) {
                return true;
            }
        }
        if (((gm.mainSprite.judgeMap.mapArray[yTile2][i] >> 8) & 0x04) == 0x04) {
            rect2.x = i * 16;
            rect2.y = yTile2 * 16;
            if (LPRect.IntersectRect(rect1, rect2)) {
                return true;
            }
        }
        return false;

    }

    private void checkTileCollisionVert() {
        if (this.dy >= 0) {
            for (int i = (y + gm.mainSprite.judgeMap.y)
                    / gm.mainSprite.judgeMap.tileSize + 1;
                    i
                    <= (y + gm.mainSprite.judgeMap.y + dy) / gm.mainSprite.judgeMap.tileSize
                    + 1; i++) {
                if (i < 0 || i > gm.mainSprite.judgeMap.h - 1) {
                    continue;
                }
                int xTile1 = (x + gm.mainSprite.judgeMap.x + 2)
                        / gm.mainSprite.judgeMap.tileSize;
                int xTile2 = (x + gm.mainSprite.judgeMap.x + 14)
                        / gm.mainSprite.judgeMap.tileSize;
                if (xTile2 > gm.mainSprite.judgeMap.w - 1 || xTile1 < 0) {
                    return;
                }
                if (((gm.mainSprite.judgeMap.mapArray[i][xTile1] >> 8) & 0x02)
                        == 0x02) {
                    dy = (i - 1) * gm.mainSprite.judgeMap.tileSize
                            - (y + gm.mainSprite.judgeMap.y);
                    return;
                }
                if (((gm.mainSprite.judgeMap.mapArray[i][xTile2] >> 8) & 0x02)
                        == 0x02) {
                    dy = (i - 1) * gm.mainSprite.judgeMap.tileSize
                            - (y + gm.mainSprite.judgeMap.y);
                    return;
                }
            }
        }
    }

    private void checkSpriteCollisionVert() {
        if (dy >= 0) {
            int i;
            for (i = gm.brick.length - 1; i >= 0; --i) {
                if (!gm.brick[i].isHidden) {
                    if (((y + 16) <= gm.brick[i].y)
                            && ((y + dy + 16) >= gm.brick[i].y)
                            && (x >= gm.brick[i].x - 10) && (x <= gm.brick[i].x + 12)) {
                        dy = gm.brick[i].y - (y + 16);
                        return;
                    }
                }
            }
        }
    }

    public void draw(Graphics g, LPImageManage im) {
        if (!isHidden) {
            if (growCnt < im.frameSize) {
                g.setClip(x,
                        y, im.frameSize,
                        growCnt);
            } else {
                g.setClip(x, y, im.frameSize, im.frameSize);
            }
            g.drawImage(im.image, x,
                    y
                    - frameCnt
                    * im.frameSize, g.LEFT | g.TOP);
            g.setClip(0, 0, MarioMIDlet.gameCanvas.width,
                    MarioMIDlet.gameCanvas.height);
        }
    }

    public void action() {
        if (!isHidden) {
            if (growCnt < 16) {
                y -= 2;
                growCnt += 2;
            } else {
                if (x < -100 || x > gm.canvas.width + 100
                        || y > gm.canvas.height + 50) {
                    isHidden = true;
                    return;
                }
                ++dy;
                checkTileCollisionVert();
                checkSpriteCollisionVert();
                y += dy;
                if (!checkTileCollisionHorn()) {
                    x += xSpeed;
                } else {
                    xSpeed = -xSpeed;
                }
            }
        }
    }

    public void reInit(int x, int y) {
        isHidden = false;
        this.x = x;
        this.y = y;
        growCnt = 0;
        xSpeed = 4;
    }
}

class MashroomPeople extends Mashroom {

    public MashroomPeople(MarioGameManage gm) {
        super(gm);
        frameCnt = 8;
    }
}

class Flower
        extends LPSprite {

    private int growCnt;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(growCnt);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        growCnt = dis.readInt();
    }

    public Flower() {
        isHidden = true;
        frameCnt = 7;
    }

    public void draw(Graphics g, LPImageManage im) {
        if (!isHidden) {
            if (growCnt < im.frameSize) {
                g.setClip(x,
                        y, im.frameSize,
                        growCnt);
            } else {
                g.setClip(x, y, im.frameSize, im.frameSize);
            }
            g.drawImage(im.image, x,
                    y
                    - frameCnt
                    * im.frameSize, g.LEFT | g.TOP);
            g.setClip(0, 0, MarioMIDlet.gameCanvas.width,
                    MarioMIDlet.gameCanvas.height);
        }
    }

    public void action() {
        if (!isHidden) {
            if (growCnt < 16) {
                y -= 2;
                growCnt += 2;
            }
        }
    }

    public void reInit(int x, int y) {
        isHidden = false;
        this.x = x;
        this.y = y;
        growCnt = 0;
    }
}

class StaticGold extends LPSprite {

    public StaticGold(int x, int y) {
        this.x = x;
        this.y = y;
        this.frameCnt = 4;
    }

    public void action() {
        if (++timeCnt % 4 == 0) {
            frameCnt = 9 - frameCnt;
        }
    }
}

class Gold extends LPSprite {

    private int dy;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(dy);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        dy = dis.readInt();
    }

    public Gold() {
        isHidden = true;
        frameCnt = 4;
    }

    public void action() {
        if (dy < 5) {
            dy += 2;
            y += dy;
            if (dy >= 5) {
                isHidden = true;
            }
        }
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        this.isHidden = false;
        dy = -12;
    }
}
