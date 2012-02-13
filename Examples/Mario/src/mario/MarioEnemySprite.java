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
import lipeng.LPRect;
import lipeng.LPSprite;

public class MarioEnemySprite
        extends LPSprite {

    protected MarioGameManage gm;
    protected int dy;
    protected static LPRect rect1 = new LPRect();
    protected static LPRect rect2 = new LPRect();
    protected int xSpeed;
    public int state;
    public boolean isCheck;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(dy);
        dos.writeInt(xSpeed);
        dos.writeInt(state);
        dos.writeBoolean(isCheck);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        dy = dis.readInt();
        xSpeed = dis.readInt();
        state = dis.readInt();
        isCheck = dis.readBoolean();
    }

    public MarioEnemySprite(MarioGameManage gm) {
        this.gm = gm;
        isCheck = true;
        rect1.dx = 16;
        rect1.dy = 16;
        rect2.dx = 16;
        rect2.dy = 16;
    }

    protected boolean checkTileCollisionHorn() {
        int i;
        rect1.y = y + gm.mainSprite.judgeMap.y;

        rect1.x = x + gm.mainSprite.judgeMap.x + xSpeed;
        if (xSpeed < 0) {
            i = (x + gm.mainSprite.judgeMap.x + xSpeed) / gm.mainSprite.judgeMap.tileSize;
        } else {
            i = (x + gm.mainSprite.judgeMap.x + xSpeed) / gm.mainSprite.judgeMap.tileSize + 1;
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

    protected boolean checkTileCollisionVert() {
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
                    return false;
                }
                if (((gm.mainSprite.judgeMap.mapArray[i][xTile1] >> 8) & 0x02)
                        == 0x02) {
                    dy = (i - 1) * gm.mainSprite.judgeMap.tileSize
                            - (y + gm.mainSprite.judgeMap.y);
                    return true;
                }
                if (((gm.mainSprite.judgeMap.mapArray[i][xTile2] >> 8) & 0x02)
                        == 0x02) {
                    dy = (i - 1) * gm.mainSprite.judgeMap.tileSize
                            - (y + gm.mainSprite.judgeMap.y);
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean checkSpriteCollisionVert() {
        if (dy >= 0) {
            int i;
            for (i = gm.brick.length - 1; i >= 0; --i) {
                if (!gm.brick[i].isHidden) {
                    if (((y + 16) <= gm.brick[i].y)
                            && ((y + dy + 16) >= gm.brick[i].y)
                            && (x >= gm.brick[i].x - 10) && (x <= gm.brick[i].x + 12)) {
                        dy = gm.brick[i].y - (y + 16);
                        return true;
                    }
                }
            }
            for (i = gm.bridge.length - 1; i >= 0; --i) {
                if (!gm.bridge[i].isHidden) {
                    if (((y + 16) <= gm.bridge[i].y)
                            && ((y + dy + 16) >= gm.bridge[i].y)
                            && (x >= gm.bridge[i].x - 10) && (x <= gm.bridge[i].x + 12)) {
                        dy = gm.bridge[i].y - (y + 16);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean checkSpriteCollisionHorn() {
        return false;
    }

    public void changeDirection() {
    }

    public void changeState() {
    }
}

class EnemyTortoise extends MarioEnemySprite {

    public boolean isPushable;
    protected int originX;
    protected int originY;
    protected boolean isLeft;
    protected int interval;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(originX);
        dos.writeInt(originY);
        dos.writeBoolean(isLeft);
        dos.writeInt(interval);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        originX = dis.readInt();
        originY = dis.readInt();
        isLeft = dis.readBoolean();
        interval = dis.readInt();
    }

    public EnemyTortoise(int x, int y, MarioGameManage gm) {
        super(gm);
        this.x = x;
        this.y = y;
        state = 0;
        xSpeed = -2;
        isPushable = false;
        isCheck = true;
        originX = x;
        originY = y;
    }

    public void action() {
        if (!isHidden) {
            if (state == 0) {
                if (x > gm.canvas.width + 50 || x < -50
                        || y < 0 || y > gm.canvas.height + 30) {
                    return;
                }
                ++dy;
                y += 8;
                checkSpriteCollisionVert();
                checkTileCollisionVert();

                y += dy;
                if (!checkTileCollisionHorn()) {
                    x += xSpeed;
                } else {
                    xSpeed = -xSpeed;
                }
                y -= 8;
                ++frameCnt;
                if (frameCnt > 1 + offset || frameCnt < 0 + offset) {
                    frameCnt = 0 + offset;
                }
                if (xSpeed > 0) {
                    offset = 0;
                } else {
                    offset = 2;
                }
            } else if (state == 1) {
                ++dy;
                y += 8;
                checkSpriteCollisionVert();
                checkTileCollisionVert();

                y += dy;
                y -= 8;

                if (++timeCnt == 200) {
                    state = 0;
                    frameCnt = 0;
                    timeCnt = 0;
                    isPushable = false;
                    isCheck = true;
                }
            } else if (state == 2) {
                ++dy;
                y += 8;
                checkSpriteCollisionVert();
                checkTileCollisionVert();
                y += dy;
                if (!checkTileCollisionHorn()) {
                    x += xSpeed;
                } else {
                    xSpeed = -xSpeed;
                }
                y -= 8;
            } else if (state == 3) {
                ++dy;
                y += dy;
                if (y > gm.canvas.height) {
                    isHidden = true;
                }
            }
        }
    }

    public void reInit(int x, int y) {
        super.reInit(x, y);
        state = 0;
        xSpeed = -2;
        isPushable = false;
        isCheck = true;
    }

    public void changeState() {
        if (state == 0) {
            state = 1;
            frameCnt = 4;
            isPushable = true;
            isCheck = false;
        } else if (state == 1) {
            state = 2;
            isPushable = false;
            isCheck = true;
            if (gm.mainSprite.x < x) {
                xSpeed = 10;
            } else {
                xSpeed = -10;
            }
            x += xSpeed;
        } else if (state == 2) {
            state = 1;
            frameCnt = 4;
            isPushable = true;
            xSpeed = 2;
            isCheck = false;
        }
    }

    public void changeDirection() {
        frameCnt = 5;
        state = 3;
        isCheck = false;
    }
}

class EnemyTortoiseHorn extends EnemyTortoise {

    public EnemyTortoiseHorn(int x, int y, int interval, MarioGameManage gm) {
        super(x, y, gm);
        state = 0;
        this.interval = interval;
    }

    public void reInit(int x, int y) {
        super.reInit(x, y);
        state = 0;
    }

    public void action() {
        if (!isHidden) {
            if (state == 0) {
                if (isLeft) {
                    x -= 2;
                    if (originX - (gm.mainSprite.judgeMap.x + x) > interval) {
                        isLeft = false;
                        offset = 0;
                    }
                } else {
                    x += 2;
                    if ((x + gm.mainSprite.judgeMap.x) - originX > interval) {
                        isLeft = true;
                        offset = 2;
                    }
                }
                ++frameCnt;
                if (frameCnt > 1 + offset || frameCnt < 0 + offset) {
                    frameCnt = 0 + offset;
                }
            } else {
                super.action();
            }
        }
    }
}
