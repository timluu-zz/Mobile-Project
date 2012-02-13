
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * 非作者授权，请勿用于商业用途。
 *
 * 玩家控制的精灵
 *
 * bruce.fine@gmail.com
 */
public class NPC extends MySprite {
    // up down left ,right
    // x0, y0, x1, y1

    short nUpRectNum = 0;
    short nDownRectNum = 0;
    short nLeftRectNum = 0;
    short nRightRectNum = 0;
    //碰撞处理区域
    short[][][] nRectCollosion = null;
    Anim anim = null;
    boolean isHandleCPC = false;
    // 可放置炸弹数量
    int nBoomNum = 1;

    public NPC() {
        nUpRectNum = 1;
        nDownRectNum = 1;
        nLeftRectNum = 1;
        nRightRectNum = 1;
        nRectCollosion = new short[4][][];

        nRectCollosion[0] = new short[nUpRectNum][4];
        nRectCollosion[1] = new short[nDownRectNum][4];
        nRectCollosion[2] = new short[nLeftRectNum][4];
        nRectCollosion[3] = new short[nRightRectNum][4];

        nRectCollosion[0][0] = new short[]{0, 0, 16, 4};
        nRectCollosion[1][0] = new short[]{0, 12, 16, 16};
        nRectCollosion[2][0] = new short[]{0, 0, 4, 16};
        nRectCollosion[3][0] = new short[]{12, 0, 16, 16};

    }

    public void initAnim(Anim anim) {
        this.anim = anim;

    }
    //
    int nTimerMP = 0;
    int nTimerMPMax = 10;
    int nMPPlus = 1;

    public void update() {

        if (nHpTimer < nHpTimerMax) {
            nHpTimer++;
        }
        // MP 自动恢复：）
        if (nMP < nMPMax) {
            if (nTimerMP < nTimerMPMax) {
                nTimerMP++;
            } else {
                nTimerMP = 0;
                nMP += nMPPlus;

            }
        }
        if (nEX >= nEXMax) {
            nEX = 0;
            // int nt = nEXMax - nEX;
            // nEXMax *= nEXMax;
            // nMPMax *= 2;
            // nHPMax *= 2;
            // nHP = nHPMax;
            // nMP = nMPMax;
            // nEX = nt;
            // nLevel += 1;
            // MyGameCanvas.instance.strInfo = "升级喽：）";
        }
        if (++nFrameCac > nFrameCacMax) {
            nFrameCac = 0;
            if (isMoving) {
                anim.update();
                switch (bDirection) {
                    case Canvas.UP: {
                        nY -= nSpeed;
                        if (isCollsionWithMap(Canvas.UP,
                                getCollosionWithMap(nX, nY))
                                || isCollsionWithCPC(Canvas.UP)) {
                            nY += nSpeed;
                        }
                    }
                    break;
                    case Canvas.DOWN: {
                        nY += nSpeed;
                        if (isCollsionWithMap(Canvas.DOWN, getCollosionWithMap(nX,
                                nY))
                                || isCollsionWithCPC(Canvas.DOWN)) {
                            nY -= nSpeed;
                        }
                    }
                    break;
                    case Canvas.LEFT: {
                        nX -= nSpeed;
                        if (isCollsionWithMap(Canvas.LEFT, getCollosionWithMap(nX,
                                nY))
                                || isCollsionWithCPC(Canvas.LEFT)) {
                            nX += nSpeed;
                        }
                    }
                    break;
                    case Canvas.RIGHT: {
                        nX += nSpeed;
                        if (isCollsionWithMap(Canvas.RIGHT, getCollosionWithMap(nX,
                                nY))
                                || isCollsionWithCPC(Canvas.RIGHT)) {
                            nX -= nSpeed;
                        }
                    }
                    break;

                }
                isMoving = false;
            }
        }

        if (nX < 0) {
            nX = 0;
        }
        if (nX > MyGameCanvas.snMapWidth) {
            nX = MyGameCanvas.snMapWidth;
        }
        if (nY < 0) {
            nY = 0;
        }
        if (nY > MyGameCanvas.snMapHeight) {
            nY = MyGameCanvas.snMapHeight;
        }

        nXInScreen = nX - MyGameCanvas.snMapX + MyGameCanvas.snWindowX;
        nYInScreen = nY - MyGameCanvas.snMapY + MyGameCanvas.snWindowY;
        //
        if (anim != null) {
            if (MyGameCanvas.instance.bGameResult != 0) {
                if (anim.nActionId != 1) {
                    anim.changeAction(Canvas.DOWN);
                }
            }
        }
    }

    public void draw(Graphics g) {
        if (nHpTimer < nHpTimerMax) {
            if (nHpTimer % 2 == 0) {
                anim.draw(g, nXInScreen, nYInScreen - 10);
            }
        } else {
            anim.draw(g, nXInScreen, nYInScreen - 10);
        }

    }

    public void doKey(int key) {
        if (MyGameCanvas.instance.bGameResult != 0) {
            return;
        }
        if (key == 0) {
            // anim.isLoop = false;
        }
        if (nHP <= 0) {
            isMoving = false;
            return;
        }
        if (nX % Consts.SN_TILE_WIDTH != 0 || nY % Consts.SN_TILE_HEIGHT != 0) {
            isMoving = true;
            return;
        } else {
            isMoving = false;
        }
        switch (key) {

            case Canvas.UP: {
                isMoving = true;
                if (bDirection == Canvas.UP) {
                    anim.isStop = false;
                } else {
                    isMoving = false;
                    bDirection = Canvas.UP;
                    anim.changeAction(Canvas.UP);
                }

            }
            break;
            case Canvas.DOWN: {
                isMoving = true;
                if (bDirection == Canvas.DOWN) {
                    anim.isStop = false;
                } else {
                    isMoving = false;
                    bDirection = Canvas.DOWN;
                    anim.changeAction(Canvas.DOWN);
                }
            }
            break;
            case Canvas.LEFT: {
                isMoving = true;
                if (bDirection == Canvas.LEFT) {
                    anim.isStop = false;
                } else {
                    isMoving = false;
                    bDirection = Canvas.LEFT;
                    anim.changeAction(Canvas.LEFT);
                }
            }
            break;
            case Canvas.RIGHT: {
                isMoving = true;
                if (bDirection == Canvas.RIGHT) {
                    anim.isStop = false;
                } else {
                    isMoving = false;
                    bDirection = Canvas.RIGHT;
                    anim.changeAction(Canvas.RIGHT);
                }
            }
            break;
            case Canvas.FIRE: {
                isMoving = false;
                int r1x1 = 0;
                int r1y1 = 0;
                int r1x2 = 0;
                int r1y2 = 0;
                int r2x1 = nX;
                int r2y1 = nY;
                int r2x2 = nX + 16;
                int r2y2 = nY + 16;

                if (!isHandleCPC) {
                    boolean isFighting = true;
                    for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
                        CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
                        if (cpc.bVar == CPC.B_VAR_BOX) {
                            if (!cpc.isHandleByNPC) {
                                // Consts.log("hello");
                                if (r2y1 == cpc.nY) {
                                    if (r2x1 + 16 == cpc.nX) {
                                        if (Canvas.RIGHT == bDirection) {
                                            cpc.upBox();
                                            isHandleCPC = true;
                                        }

                                    }
                                    if (r2x1 - 16 == cpc.nX) {
                                        if (Canvas.LEFT == bDirection) {
                                            cpc.upBox();
                                            isHandleCPC = true;
                                        }

                                    }

                                }
                                if (r2x1 == cpc.nX) {
                                    if (r2y1 + 16 == cpc.nY) {
                                        if (Canvas.DOWN == bDirection) {
                                            cpc.upBox();
                                            isHandleCPC = true;
                                        }

                                    }
                                    if (r2y1 - 16 == cpc.nY) {
                                        if (Canvas.UP == bDirection) {
                                            cpc.upBox();
                                            isHandleCPC = true;
                                        }

                                    }

                                }
                                if (isHandleCPC) {
                                    isFighting = false;
                                    break;
                                }
                            }
                        }
                    }

                    boolean ttdia = false;
                    for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
                        CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
                        if (cpc.bVar == CPC.B_VAR_NPC) {
                            r1x1 = cpc.nX;
                            r1y1 = cpc.nY;
                            r1x2 = cpc.nX + 16;
                            r1y2 = cpc.nY + 16;
                            r2x1 = nX;
                            r2y1 = nY;
                            r2x2 = nX + 16;
                            r2y2 = nY + 16;

                            if (!MyGameCanvas.instance.isDialogWorking) {
                                if (r2y1 == cpc.nY) {
                                    if (r2x1 + 16 == cpc.nX) {
                                        if (bDirection == Canvas.RIGHT) {
                                            if (cpc.bDirection != Canvas.LEFT) {
                                                cpc.anim.changeAction(Canvas.LEFT);
                                                cpc.anim.isStop = true;

                                            }
                                            ttdia = true;
                                        }

                                    }
                                    if (r2x1 - 16 == cpc.nX) {
                                        if (bDirection == Canvas.LEFT) {
                                            if (cpc.bDirection != Canvas.RIGHT) {
                                                cpc.anim.changeAction(Canvas.RIGHT);
                                                cpc.anim.isStop = true;

                                            }
                                            ttdia = true;
                                        }
                                    }

                                }
                                if (r2x1 == cpc.nX) {
                                    if (r2y1 + 16 == cpc.nY) {
                                        if (bDirection == Canvas.DOWN) {
                                            if (cpc.bDirection != Canvas.UP) {
                                                cpc.anim.changeAction(Canvas.UP);
                                                cpc.anim.isStop = true;

                                            }
                                            ttdia = true;
                                        }
                                    }
                                    if (r2y1 - 16 == cpc.nY) {
                                        if (bDirection == Canvas.UP) {
                                            if (cpc.bDirection != Canvas.DOWN) {
                                                cpc.anim.changeAction(Canvas.DOWN);
                                                cpc.anim.isStop = true;

                                            }
                                            ttdia = true;
                                        }
                                    }

                                }

                            }

                        }

                    }
                    if (ttdia) {
                        MyGameCanvas.instance.initDialog();
                        isFighting = false;
                        // Consts.log("激活对画");
                        break;
                    }
                    // 地图转动：）
                    boolean isMapChange = false;
                    for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
                        CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
                        if (cpc.bVar == CPC.B_VAR_DOOR) {
                            r1x1 = cpc.nX;
                            r1y1 = cpc.nY;
                            r1x2 = cpc.nX + 16;
                            r1y2 = cpc.nY + 16;

                            r2x1 = nX;
                            r2y1 = nY;
                            r2x2 = nX + 16;
                            r2y2 = nY + 16;

                            if (r2y1 == cpc.nY) {
                                if (r2x1 + 16 == cpc.nX) {
                                    isMapChange = true;

                                }
                                if (r2x1 - 16 == cpc.nX) {
                                    isMapChange = true;
                                }

                            }
                            if (r2x1 == cpc.nX) {
                                if (r2y1 + 16 == cpc.nY) {
                                    isMapChange = true;
                                }
                                if (r2y1 - 16 == cpc.nY) {
                                    isMapChange = true;
                                }

                            }
                            if (isMapChange) {
                                MyGameCanvas.instance.nMapId = cpc.nMapID;
                                MyGameCanvas.instance.doStateChange(MyGameCanvas.SB_STATE_GAMEING);
                                break;
                            }

                        }

                    }
                    // 宝物箱
                    for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
                        CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
                        if (cpc.bVar == CPC.B_VAR_SAVING) {
                            boolean tff = false;
                            if (!cpc.isOpened) {
                                // Consts.log("hello");
                                if (r2y1 == cpc.nY) {
                                    if (r2x1 + 16 == cpc.nX) {
                                        if (Canvas.RIGHT == bDirection) {
                                            tff = true;
                                        }
                                    }
                                    if (r2x1 - 16 == cpc.nX) {
                                        if (Canvas.LEFT == bDirection) {
                                            tff = true;
                                        }
                                    }

                                }
                                if (r2x1 == cpc.nX) {
                                    if (r2y1 + 16 == cpc.nY) {
                                        if (Canvas.DOWN == bDirection) {
                                            tff = true;
                                        }
                                    }
                                    if (r2y1 - 16 == cpc.nY) {
                                        if (Canvas.UP == bDirection) {
                                            tff = true;
                                        }
                                    }

                                }
                                if (tff) {
                                    cpc.isOpened = true;
                                    // adddomoe
                                    cpc.anim.changeAction(Canvas.DOWN);
                                    cpc.anim.isStop = true;
                                    // Consts.log("heello");
                                    isFighting = false;
                                    nHP = nHPMax;
                                    MyGameCanvas.instance.strInfo = new String(
                                            "生命值全满");
                                    break;
                                }
                            }
                        }
                    }
                    if (isFighting) {

                        int num = 0;
                        for (int i = 0; i < MyGameCanvas.instance.vecticUnits.size(); i++) {
                            CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
                            if (cpc.bVar == CPC.B_VAR_BOMB) {
                                num++;
                            }
                        }
                        if (num < MyGameCanvas.instance.nBombNumCMax) {
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_BOMB);
                            MyGameCanvas.instance.cpc2.initBasic("LISA_BALL", 16,
                                    20, bDirection, 0, true, true, 0);
                            MyGameCanvas.instance.cpc2.initAbsXY(nX, nY);

                            MyGameCanvas.instance.cpc2.initParam(10, 50, 50, 50,
                                    50, 50, nAttackPower, 2);
                            MyGameCanvas.instance.cpc2.initAnim(MyGameCanvas.instance.animBomb.copy());

                            // MyGameCanvas.instance.cpc2.anim.changeAction(bDirection);
                            // MyGameCanvas.instance.cpc2.anim.isStop = true;
                            if (isBoomHere(MyGameCanvas.instance.cpc2.nX,
                                    MyGameCanvas.instance.cpc2.nY)) {
                                // Consts.log("不能放置东西");
                            } else {
                                MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            }

                            MyGameCanvas.instance.cpc2 = null;
                        }

                        //

                    }

                } else {
                    for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
                        CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
                        if (cpc.bVar == CPC.B_VAR_BOX) {
                            if (cpc.isHandleByNPC) {
                                cpc.downBox();
                                break;
                            }
                        }
                    }
                }
            }
            break;
            default: {
                isMoving = false;
                anim.isStop = true;
            }
            break;
        }
    }

    public boolean isBoomHere(int x, int y) {
        for (int i = 0; i < MyGameCanvas.instance.vecticUnits.size(); i++) {
            CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
            if (cpc.bVar == cpc.B_VAR_BOMB) {
                if (cpc.nX == x && cpc.nY == y) {
                    return true;
                }
            }

        }
        return false;

    }

    // up down left right ,是否检测墙体？
    // 对于特殊位置的碰撞进行单独的处理
    public boolean[] getCollosionWithMap(int x, int y) {
        boolean[] flags = new boolean[4];

        int tx[] = new int[4];
        int ty[] = new int[4];

        for (int i = 0; i < flags.length; i++) {
            flags[i] = false;
        }
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < nRectCollosion[i].length; j++) {
                boolean flag = false;
                int t_x0 = nRectCollosion[i][j][0] + x;
                int t_y0 = nRectCollosion[i][j][1] + y;

                int t_x1 = nRectCollosion[i][j][2] + x;
                int t_y1 = nRectCollosion[i][j][3] + y;

                tx[0] = t_x0 + 1;
                ty[0] = t_y0 + 1;

                tx[1] = t_x1 - 1;
                ty[1] = t_y0 + 1;

                tx[2] = t_x0 + 1;
                ty[2] = t_y1 - 1;

                tx[3] = t_x1 - 1;
                ty[3] = t_y1 - 1;
                for (int m = 0; m < 4; m++) {

                    int tempx = tx[m] / Consts.SN_TILE_WIDTH;
                    int tempy = ty[m] / Consts.SN_TILE_HEIGHT;

                    if (!MyGameCanvas.isXYInTiles(tempx, tempy,
                            MyGameCanvas.snsTiles)) {
                        flags[i] = true;
                        flag = true;
                        break;

                    } else {
                        byte block_val = Consts.getTileVar((short) MyGameCanvas.snsTiles[tempy][tempx]);
                        if (Consts.SB_TILE_NULL != block_val) {
                            flags[i] = true;
                            flag = true;

                            break;
                        }

                    }
                }
                if (flag) {
                    break;
                }
            }
        }

        return flags;
    }

    public boolean isCollsionWithCPC(int dir) {
        int r1x1 = 0;
        int r1y1 = 0;
        int r1x2 = 0;
        int r1y2 = 0;
        int r2x1 = nX;
        int r2y1 = nY;
        int r2x2 = nX + 16;
        int r2y2 = nY + 16;
        for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
            CPC cpc = (CPC) (MyGameCanvas.vecticUnits.elementAt(i));
            r1x1 = cpc.nX;
            r1y1 = cpc.nY;
            r1x2 = cpc.nX + 16;
            r1y2 = cpc.nY + 16;
            if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2, r2x1,
                    r2y1, r2x2, r2y2)) {

                if (cpc.bVar == CPC.B_VAR_BOX || cpc.bVar == CPC.B_VAR_NPC
                        || cpc.bVar == CPC.B_VAR_DOOR
                        || cpc.bVar == CPC.B_VAR_SAVING) {
                    if (cpc.bVar == CPC.B_VAR_BOX) {
                        if (MyGameCanvas.instance.npcLisa.isHandleCPC) {
                            if (cpc.isHandleByNPC == true) {
                                continue;
                            }
                        }
                    }
                    return true;

                }
                if (isHandleCPC) {
                    if (cpc.bVar == CPC.B_VAR_BOMB
                            || cpc.bVar == CPC.B_VAR_BOSS_SNAKE
                            || cpc.bVar == CPC.B_VAR_ZHEN) {
                        if (cpc.bVar == CPC.B_VAR_BOSS_SNAKE) {
                            delHp(-1);
                        }
                        if (cpc.bVar == CPC.B_VAR_ZHEN) {
                            if (cpc.bBossState == 2) {
                                delHp(-1);
                            }
                        }

                    } else {
                        if (cpc.bVar == CPC.B_VAR_BOMB
                                || cpc.bVar == CPC.B_VAR_BOSS_SNAKE
                                || cpc.bVar == CPC.B_VAR_ZHEN) {
                            delHp(-1);
                        } else {
                            if (cpc.bVar == CPC.B_VAR_MAGIC_CPC_FIRE_BALL
                                    || cpc.bVar == CPC.B_VAR_RABIT
                                    || cpc.bVar == CPC.B_VAR_WORM) {
                                delHp(-1);
                            }
                            // return true;
                        }

                    }
                } else {
                    if (cpc.bVar == CPC.B_VAR_BOMB
                            || cpc.bVar == CPC.B_VAR_BOSS_SNAKE
                            || cpc.bVar == CPC.B_VAR_ZHEN) {
                        if (cpc.bVar == CPC.B_VAR_BOSS_SNAKE) {
                            delHp(-1);
                        }
                        if (cpc.bVar == CPC.B_VAR_ZHEN) {
                            if (cpc.bBossState == 2) {
                                delHp(-1);
                            }
                        }

                    } else {
                        if (cpc.bVar == CPC.B_VAR_MAGIC_CPC_FIRE_BALL
                                || cpc.bVar == CPC.B_VAR_RABIT
                                || cpc.bVar == CPC.B_VAR_WORM) {
                            delHp(-1);
                        }
                        // return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean isCollsionWithMap(int dir, boolean[] br) {
        boolean flag = false;
        switch (dir) {
            case Canvas.UP: {
                flag = br[0];
            }
            break;
            case Canvas.DOWN: {
                flag = br[1];
            }
            break;
            case Canvas.LEFT: {
                flag = br[2];
            }
            break;
            case Canvas.RIGHT: {
                flag = br[3];
            }
            break;
        }
        return flag;
    }

    public void setDirection(byte dir) {
        bDirection = dir;
        anim.changeAction(dir);
    }

    //
    public void addHP(int hp_num) {
        nHP += hp_num;
        if (nHP > nHPMax) {
            nHP = nHPMax;
        }
    }

    public void addMP(int mp_num) {
        nMP += mp_num;
        if (nMP > nMPMax) {
            nMP = nMPMax;
        }
    }
    int nHpTimer = 0;
    int nHpTimerMax = 10;

    public void delHp(int val) {
        if (MyGameCanvas.instance.bGameResult == 0) {
            if (nHpTimer >= nHpTimerMax) {
                nHP += val;
                nHpTimer = 0;
            }
        }
    }
}
