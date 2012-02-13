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

public class MarioBullet
        extends LPSprite {

    protected MarioGameManage gm;
    private LPRect rect1 = new LPRect();
    private LPRect rect2 = new LPRect();
    protected int xSpeed;
    protected int ySpeed;
    protected int dy;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(xSpeed);
        dos.writeInt(ySpeed);
        dos.writeInt(dy);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        xSpeed = dis.readInt();
        ySpeed = dis.readInt();
        dy = dis.readInt();
    }

    public MarioBullet(MarioGameManage gm) {
        isHidden = true;
        rect1.dx = 8;
        rect1.dy = 8;
        rect2.dx = 16;
        rect2.dy = 16;
        this.gm = gm;
    }

    public void initBullet(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.frameCnt = 2;
        this.isHidden = false;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    // protected void
    protected boolean checkTileCollisionHorn() {
        int i;
        rect1.x = x + gm.mainSprite.judgeMap.x + xSpeed;
        rect1.y = y + gm.mainSprite.judgeMap.y;

        i = (x + gm.mainSprite.judgeMap.x + xSpeed + 4) / gm.mainSprite.judgeMap.tileSize;

        if (i < 0 || i > gm.mainSprite.judgeMap.w - 1) {
            return false;
        }
        int yTile1 = (y + gm.mainSprite.judgeMap.y) / gm.mainSprite.judgeMap.tileSize;
        if (yTile1 < 0 || yTile1 > gm.mainSprite.judgeMap.h - 1) {
            return false;
        }
        if (((gm.mainSprite.judgeMap.mapArray[yTile1][i] >> 8) & 0x04) == 0x04) {
            rect2.x = i * 16;
            rect2.y = yTile1 * 16;
            if (LPRect.IntersectRect(rect1, rect2)) {
                isHidden = true;
                return true;
            }
        }
        return false;
    }

    protected void checkTileCollisionVert() {
        if (this.dy >= 0) {
            for (int i = (y + gm.mainSprite.judgeMap.y)
                    / gm.mainSprite.judgeMap.tileSize;
                    i <= (y + gm.mainSprite.judgeMap.y + dy) / gm.mainSprite.judgeMap.tileSize; i++) {
                if (i < 0 || i > gm.mainSprite.judgeMap.h - 1) {
                    continue;
                }
                int xTile1 = (x + gm.mainSprite.judgeMap.x + 4)
                        / gm.mainSprite.judgeMap.tileSize;
                if (xTile1 > gm.mainSprite.judgeMap.w - 1 || xTile1 < 0) {
                    return;
                }
                if (((gm.mainSprite.judgeMap.mapArray[i][xTile1] >> 8) & 0x02)
                        == 0x02) {
                    dy = -8;
                    return;
                }
            }
        }
    }

    protected void checkSpriteCollisionVert() {
        if (dy >= 0) {
            int i;
            switch (gm.gameState) {
                case MarioGameManage.GAMESTATE_GAMELOOP:
                    for (i = gm.brick.length - 1; i >= 0; --i) {
                        if (!gm.brick[i].isHidden) {
                            if (((y + 8) <= gm.brick[i].y)
                                    && ((y + dy + 8) >= gm.brick[i].y)
                                    && (x >= gm.brick[i].x - 2) && (x <= gm.brick[i].x + 14)) {
                                //dy=gm.brick[i].y-(y+16);
                                dy = -8;
                                return;
                            }
                        }
                    }
                    break;
            }
        }
    }

    public void action() {
        if (isHidden) {
            return;
        }
        if (x > gm.canvas.width || x < -32
                || y > gm.canvas.height || y < -32) {
            isHidden = true;
            return;
        }
        dy += 4;
        checkSpriteCollisionVert();
        checkTileCollisionVert();
        y += dy;
        if (!checkTileCollisionHorn()) {
            x += xSpeed;
        }
        ++frameCnt;
        if (frameCnt < 2 || frameCnt > 5) {
            frameCnt = 2;
        }
    }
}
