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
import lipeng.LPSprite;

public class MarioStaticSprite extends LPSprite {
}

class MarioBrokenBrick extends LPSprite {

    private int xSpeed;
    private int ySpeed;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(xSpeed);
        dos.writeInt(ySpeed);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        xSpeed = dis.readInt();
        ySpeed = dis.readInt();
    }

    public MarioBrokenBrick() {
        isHidden = true;
    }

    public void action() {
        if (isHidden || x < -50 || x > MarioMIDlet.gameCanvas.width + 50
                || y < -50 || y > MarioMIDlet.gameCanvas.height + 50) {
            isHidden = true;
            return;
        }
        x += xSpeed;
        y += ySpeed;
        ySpeed += 2;
    }

    public void init(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        isHidden = false;
        if (xSpeed < 0) {
            frameCnt = 1;
        } else {
            frameCnt = 0;
        }
        this.ySpeed = ySpeed;
    }
}

class PassLevel extends LPSprite {

    public PassLevel(int x, int y) {
        this.x = x;
        this.y = y;
        frameCnt = 8;
    }
}

class Stick extends LPSprite {

    protected int originX;
    protected int originY;
    protected int interval;
    protected MarioGameManage gm;
    protected boolean isLeft;
    public boolean isMainSpriteMove;
    protected int xSpeed;
    protected int ySpeed;
    protected int dy;
    protected boolean isDown;

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(originX);
        dos.writeInt(originY);
        dos.writeInt(interval);
        dos.writeBoolean(isLeft);
        dos.writeBoolean(isMainSpriteMove);
        dos.writeInt(xSpeed);
        dos.writeInt(ySpeed);
        dos.writeInt(dy);
        dos.writeBoolean(isDown);
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        originX = dis.readInt();
        originY = dis.readInt();
        interval = dis.readInt();
        isLeft = dis.readBoolean();
        isMainSpriteMove = dis.readBoolean();
        xSpeed = dis.readInt();
        ySpeed = dis.readInt();
        dy = dis.readInt();
        isDown = dis.readBoolean();
    }

    public Stick() {
        this.frameCnt = 0;
    }

    public void reInit(int x, int y) {
        super.reInit(x, y);
        isMainSpriteMove = false;
    }
}

class StickVertMove extends Stick {

    public StickVertMove(int x, int y, int ySpeed, MarioGameManage gm) {
        this.x = x;
        this.y = y;
        this.ySpeed = ySpeed;
        this.gm = gm;
    }

    public void action() {
        y += ySpeed;
        if (isMainSpriteMove) {
            gm.mainSprite.y += ySpeed;
        }
        if (ySpeed > 0) {
            if (y > gm.canvas.height) {
                y = -10;
            }
        } else {
            if (y < -10) {
                y = gm.canvas.height;
            }
        }
    }
}

class StickVertMoveShake extends Stick {

    public StickVertMoveShake(int x, int y, int interval, MarioGameManage gm) {
        this.x = x;
        this.y = y;
        this.ySpeed = ySpeed;
        this.gm = gm;
        this.originY = y;
        this.interval = interval;
    }

    public void action() {
        if (isLeft) {
            y -= 2;
            if (isMainSpriteMove) {
                gm.mainSprite.y -= 2;
            }
            if (originY - (gm.mainSprite.judgeMap.y + y) > interval) {
                isLeft = false;
            }
        } else {
            y += 2;
            if (isMainSpriteMove) {
                gm.mainSprite.y += 2;
            }
            if ((y + gm.mainSprite.judgeMap.y) - originY > interval) {
                isLeft = true;
            }
        }
    }
}

class StickHornMove extends Stick {

    public StickHornMove(int x, int y, int interval, MarioGameManage gm) {
        this.x = x;
        this.y = y;
        this.interval = interval;
        this.originX = x;
        this.gm = gm;
    }

    public void action() {
        if (isLeft) {
            x -= 2;
            if (isMainSpriteMove) {
                gm.mainSprite.x -= 2;
            }
            if (originX - (gm.mainSprite.judgeMap.x + x) > interval) {
                isLeft = false;
            }
        } else {
            x += 2;
            if (isMainSpriteMove) {
                gm.mainSprite.x += 2;
            }
            if ((x + gm.mainSprite.judgeMap.x) - originX > interval) {
                isLeft = true;
            }
        }
    }
}

class StickFall extends Stick {

    public StickFall(int x, int y, MarioGameManage gm) {
        this.x = x;
        this.y = y;
        this.gm = gm;
        isDown = false;
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        this.frameCnt = 0;
        dy = 0;
        this.isDown = false;
        this.isHidden = false;
    }

    public void action() {
        if (!isHidden) {
            if (isDown) {
                ++timeCnt;
            }
            if (timeCnt > 5) {
                dy += 2;
                y += dy;
                if (y > gm.canvas.width + 20) {
                    isHidden = true;
                }
            }
        }
    }
}

class FallBridge extends LPSprite {

    public FallBridge(int x, int y) {
        this.x = x;
        this.y = y;
        frameCnt = 9;
        isDown = false;
    }

    public void reInit(int x, int y) {
        this.x = x;
        this.y = y;
        this.timeCnt = 0;
        this.isDown = false;
        this.isHidden = false;
        dy = 0;
    }

    public void action() {
        if (!isHidden) {
            if (isDown) {
                ++timeCnt;
            }
            if (timeCnt > 5) {
                dy += 2;
                y += dy;
                if (y > MarioMIDlet.gameCanvas.width + 20) {
                    isHidden = true;
                }
            }
        }
    }

    public void readData(DataInputStream dis) throws IOException {
        super.readData(dis);
        dy = dis.readInt();
        isDown = dis.readBoolean();
    }

    public void writeData(DataOutputStream dos) throws IOException {
        super.writeData(dos);
        dos.writeInt(dy);
        dos.writeBoolean(isDown);
    }
    public boolean isDown;
    private int dy;
}
