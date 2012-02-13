
import java.util.Vector;

import javax.microedition.lcdui.*;

/**
 * 非作者授权，请勿用于商业用途。 电脑控制的精灵
 *
 * @author bruce.fine@gmail.com
 *
 */
public class CPC extends MySprite {
    //

    int nHpTimer = 0;
    int nHpTimerMax = 6;

    public void delHp(int val) {
        if (nHpTimer >= nHpTimerMax) {
            nHP += val;
            nHpTimer = 0;
        }
    }
    // up down left ,right
    // x0, y0, x1, y1
    short nUpRectNum = 0;
    short nDownRectNum = 0;
    short nLeftRectNum = 0;
    short nRightRectNum = 0;
    short[][][] nRectCollosion = null;
    static final byte B_VAR_BOX = 1;
    /**
     * 跳跳的小兔子
     */
    static final byte B_VAR_RABIT = 2;
    /**
     * NPC
     */
    static final byte B_VAR_NPC = 3;
    /**
     * 魔法，属于LISA
     */
    static final byte B_VAR_MAGIC_LISA_FIRE = 4;
    /**
     * 魔法，属于坏蛋的：）
     */
    static final byte B_VAR_MAGIC_CPC_FIRE = 5;
    /**
     * 魔法，属于坏蛋的：）,普通球：）
     */
    static final byte B_VAR_MAGIC_CPC_FIRE_BALL = 6;
    /**
     * 储箱
     */
    static final byte B_VAR_SAVING = 7;
    /**
     * 虫
     */
    static final byte B_VAR_WORM = 8;
    /**
     * 地图旋转，作用，可以为空也可以不为空~~~
     */
    static final byte B_VAR_DOOR = 9;
    /**
     * 智商稍微高的怪物
     */
    static final byte B_VAR_OGRE = 10;
    /**
     * 炸弹
     *
     */
    static final byte B_VAR_BOMB = 11;
    // 攻击范围
    int nAttackByBombTileNumUp = 0;
    int nAttackByBombTileNumDown = 0;
    int nAttackByBombTileNumLeft = 0;
    int nAttackByBombTileNumRight = 0;
    //
    static final byte B_BOMB_STATE_STATIC = 0; // 没有引爆的炸弹
    static final byte B_BOMB_STATE_BOMBING = 1; // 被引爆的炸弹
    byte bBombState = B_BOMB_STATE_STATIC;
    //
    int nPower = 0; // 炸弹的能量
    static final byte B_BOMB_VAR_NORMAL = 0; // 普通型
    static final byte B_BOMB_VAR_SUPER = 1; // 超级炸弹
    byte bBombVar = 0; // 炸弹的类型
    //
    int nTimer = 0;
    int nTimerMax = 30;
    //
    /**
     * 炸弹火焰
     *
     */
    static final byte B_VAR_BOMBSFIRE = 12;
    int nBombFireToDisappear = 0;
    int nBombFireToDisappearMax = 4;
    //
    /**
     * BOSS在此
     */
    static final byte B_VAR_BOSS_SIMPLY = 14;
    static final byte B_BOSS_STATE_ALIVE = 0;
    static final byte B_BOSS_STATE_DIE = 1;
    // 如果bBossSate == 100,则bBoss为不死之身
    byte bBossState = 0;
    //
    static final byte B_VAR_BOSS_SNAKE = 15;
    boolean isLinkForBone = false;// 是否是骨节
    //
    static final byte B_VAR_BOSS_UFO = 16;
    //
    static final byte B_VAR_ZHEN = 17;
    //
    short nMapID = 0;
    // 使用某个值作为MAPID
    //
    boolean isOpened = false;
    int n_foos_id = 0;
    int n_foos_num = 0;
    //
    short N_RABIT_TRAC_LENGTH = 20;
    static final short N_SLEEP_TIME = 30;
    boolean isHandleByNPC = false;
    byte bVar = 0;
    // 在一个方向上前进的步数
    int nLength = 0;
    int nSleepTime = 0;
    static final int N_LENGTH_ATTACK = 30;
    // int nAttackTimer = 0;
    //
    // int nAttackTimerMax = 40;
    int nLisaAttackLength = 30;
    int nHurtTimer = 0;
    int HURT_TIMER_MAX = 8;
    //
    static int bulletNum = 0;

    public CPC(byte var) {
        // if (var == B_VAR_MAGIC_LISA_FIRE) {
        // Consts.log("lisa fire");
        // }
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
        bVar = var;
        switch (bVar) {
            case B_VAR_BOMB: {
                bBombState = B_BOMB_STATE_STATIC;
                bBombVar = B_BOMB_VAR_NORMAL;
                nPower = MyGameCanvas.instance.nPowerCMax; // 炸弹的能量
                nAttackByBombTileNumUp = 0;
                nAttackByBombTileNumDown = 0;
                nAttackByBombTileNumLeft = 0;
                nAttackByBombTileNumRight = 0;
                nTimer = 0;

            }
            break;
            case B_VAR_BOSS_SIMPLY: {
                nTimer = 0;
                nTimerMax = 30;
            }
            break;
            case B_VAR_MAGIC_CPC_FIRE_BALL: {
                bulletNum++;
            }
            break;

        }
    }
    Anim anim = null;

    public void initAnim(Anim anim) {
        this.anim = anim;

    }

    public void update() {
        if (nHpTimer < nHpTimerMax) {
            nHpTimer++;
        }
        if (nHP <= 0) {
            return;
        }
        if (anim != null) {
            anim.update();
        }

        switch (bVar) {
            case B_VAR_ZHEN: {
                if (nTimer < nTimerMax) {
                    // 0 -> 封闭, 1->长出来 2 -> 绽放, 3-> 回缩
                    if (nTimer == nTimerMax - 3) {
                        if (bBossState == 0) {
                            bBossState = 1;
                        } else if (bBossState == 2) {
                            bBossState = 3;
                        } else if (bBossState == 3) {
                            bBossState = 0;
                        }
                    }
                    nTimer++;

                } else {
                    nTimer = 0;
                    if (bBossState == 1) {
                        bBossState = 2;
                    } else if (bBossState == 3) {
                        bBossState = 0;
                    }

                }
                if (bBossState == 2) {
                    int r1x1 = MyGameCanvas.instance.npcLisa.nX;
                    int r1y1 = MyGameCanvas.instance.npcLisa.nY;
                    int r1x2 = r1x1 + 16;
                    int r1y2 = r1y1 + 16;
                    //
                    int r2x1 = nX;
                    int r2y1 = nY;
                    int r2x2 = r2x1 + 16;
                    int r2y2 = r2y1 + 16;

                    if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2, r2x1,
                            r2y1, r2x2, r2y2)) {
                        MyGameCanvas.instance.npcLisa.delHp(-1);
                    }

                }
            }
            break;
            case B_VAR_BOSS_UFO: {
                if (nX % Consts.SN_TILE_WIDTH == 0
                        && nY % Consts.SN_TILE_HEIGHT == 0) {
                    switch (bDirection) {
                        case Canvas.UP: {
                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH),
                                    (nY / Consts.SN_TILE_HEIGHT) - 1)) {
                                bDirection = Canvas.RIGHT;
                            }
                        }
                        break;
                        case Canvas.DOWN: {
                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH),
                                    ((nY + 16) / Consts.SN_TILE_HEIGHT) + 1)) {
                                bDirection = Canvas.LEFT;
                            }
                        }
                        break;
                        case Canvas.LEFT: {

                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH) - 1,
                                    (nY / Consts.SN_TILE_HEIGHT))) {
                                bDirection = Canvas.UP;
                            }
                        }
                        break;
                        case Canvas.RIGHT: {
                            if (!isTilesNullForSnake(
                                    ((nX + 16) / Consts.SN_TILE_WIDTH) + 1,
                                    (nY / Consts.SN_TILE_HEIGHT))) {
                                bDirection = Canvas.DOWN;
                            }
                        }
                        break;

                    }

                }
                switch (bDirection) {
                    case Canvas.UP: {
                        nY -= nSpeed;
                        nX += nSpeed;
                    }
                    break;
                    case Canvas.RIGHT: {
                        nY += nSpeed;
                        nX += nSpeed;
                    }
                    break;
                    case Canvas.DOWN: {
                        nX -= nSpeed;
                        nY += nSpeed;
                    }
                    break;
                    case Canvas.LEFT: {
                        nX -= nSpeed;
                        nY -= nSpeed;
                    }
                    break;
                }
                //
                if (bBombState == B_BOSS_STATE_ALIVE && nHP > 1) {
                    if (nTimer < nTimerMax) {
                        nTimer++;
                    } else {
                        nTimer = 0;
                        if (true) {
                            // up
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);

                            MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                    (byte) Canvas.UP, 4, true, true, 0);
                            MyGameCanvas.instance.cpc2.initAbsXY(nX + 4, nY + 9);
                            MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50,
                                    50, 50, 2, 2);
                            MyGameCanvas.instance.cpc2.initAnim(null);
                            MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            //
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);
                            MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                    (byte) Canvas.DOWN, 4, true, true, 0);
                            MyGameCanvas.instance.cpc2.initAbsXY(nX + 4, nY + 16);
                            MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50,
                                    50, 50, 2, 2);
                            MyGameCanvas.instance.cpc2.initAnim(null);
                            MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            //
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);
                            MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                    (byte) Canvas.LEFT, 4, true, true, 0);
                            MyGameCanvas.instance.cpc2.initAbsXY(nX - 9, nY + 4);
                            MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50,
                                    50, 50, 2, 2);
                            MyGameCanvas.instance.cpc2.initAnim(null);
                            MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            //
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);
                            MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                    (byte) Canvas.RIGHT, 4, true, true, 0);
                            MyGameCanvas.instance.cpc2.initAbsXY(nX + 16, nY + 4);
                            MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50,
                                    50, 50, 2, 2);
                            MyGameCanvas.instance.cpc2.initAnim(null);
                            MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            MyGameCanvas.instance.cpc2 = null;

                        }
                    }
                }
            }
            break;
            case B_VAR_BOSS_SNAKE: {
                if (nX % Consts.SN_TILE_WIDTH == 0
                        && nY % Consts.SN_TILE_HEIGHT == 0) {
                    int b = bDirection;
                    switch (bDirection) {
                        case Canvas.UP: {
                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH),
                                    (nY / Consts.SN_TILE_HEIGHT) - 1)) {
                                bDirection = Canvas.RIGHT;
                            }
                        }
                        break;
                        case Canvas.DOWN: {
                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH),
                                    (nY / Consts.SN_TILE_HEIGHT) + 1)) {
                                bDirection = Canvas.LEFT;
                            }
                        }
                        break;
                        case Canvas.LEFT: {

                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH) - 1,
                                    (nY / Consts.SN_TILE_HEIGHT))) {
                                bDirection = Canvas.UP;
                            }
                        }
                        break;
                        case Canvas.RIGHT: {
                            if (!isTilesNullForSnake((nX / Consts.SN_TILE_WIDTH) + 1,
                                    (nY / Consts.SN_TILE_HEIGHT))) {
                                bDirection = Canvas.DOWN;
                            }
                        }
                        break;

                    }
                    if (b != bDirection) {
                        anim.changeAction(bDirection);
                    }
                }
                switch (bDirection) {
                    case Canvas.UP: {
                        nY -= nSpeed;
                    }
                    break;
                    case Canvas.DOWN: {
                        nY += nSpeed;
                    }
                    break;
                    case Canvas.LEFT: {
                        nX -= nSpeed;
                    }
                    break;
                    case Canvas.RIGHT: {
                        nX += nSpeed;
                    }
                    break;
                }
                //
                if (bBombState == B_BOSS_STATE_ALIVE && nHP > 1) {
                    if (nTimer < nTimerMax) {
                        nTimer++;
                    } else {
                        nTimer = 0;
                        if (MyGameCanvas.instance.getRandomInt(0, 4, nX) == 0
                                && CPC.bulletNum < 5) {
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);
                            int dir = 0;
                            if (Canvas.RIGHT == bDirection) {
                                dir = Canvas.DOWN;
                            } else if (Canvas.LEFT == bDirection) {
                                dir = Canvas.UP;
                            } else if (Canvas.UP == bDirection) {
                                dir = Canvas.RIGHT;
                            } else {
                                dir = Canvas.LEFT;
                            }
                            MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                    (byte) dir, 4, true, true, 0);

                            switch (dir) {
                                case Canvas.UP: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX + 4, nY + 9);
                                }
                                break;
                                case Canvas.DOWN: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX + 4,
                                            nY + 16);
                                }
                                break;
                                case Canvas.LEFT: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX - 9, nY + 4);
                                }
                                break;
                                case Canvas.RIGHT: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX + 16,
                                            nY + 4);
                                }
                                break;
                            }
                            MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50,
                                    50, 50, 2, 2);
                            MyGameCanvas.instance.cpc2.initAnim(null);
                            MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            MyGameCanvas.instance.cpc2 = null;
                        }
                    }
                }
            }
            break;
            case B_VAR_BOSS_SIMPLY: {
                if (bBossState == B_BOSS_STATE_ALIVE) {
                    if (nHP == 1) {
                        bBossState = B_BOSS_STATE_DIE;
                        break;
                    }
                    if (nTimer < nTimerMax) {
                        nTimer++;

                    } else {
                        //
                        nTimer = 0;
                        MyGameCanvas.instance.cpc2 = new CPC(
                                (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);
                        MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                bDirection, 4, true, true, 0);
                        switch (bDirection) {
                            case Canvas.UP: {
                                MyGameCanvas.instance.cpc2.initAbsXY(nX + 4, nY + 9);
                            }
                            break;
                            case Canvas.DOWN: {
                                MyGameCanvas.instance.cpc2.initAbsXY(nX + 4, nY + 16);
                            }
                            break;
                            case Canvas.LEFT: {
                                MyGameCanvas.instance.cpc2.initAbsXY(nX - 9, nY + 4);
                            }
                            break;
                            case Canvas.RIGHT: {
                                MyGameCanvas.instance.cpc2.initAbsXY(nX + 16, nY + 4);
                            }
                            break;
                        }
                        MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50, 50,
                                50, 2, 2);
                        MyGameCanvas.instance.cpc2.initAnim(null);
                        MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                        MyGameCanvas.instance.cpc2 = null;

                    }
                }
            }
            break;
            case B_VAR_BOX: {
                if (isHandleByNPC) {
                    nX = MyGameCanvas.instance.npcLisa.nX;
                    nY = MyGameCanvas.instance.npcLisa.nY;
                }

            }
            break;

            case B_VAR_WORM: {
                if (nSleepTime > 0) {
                    nSleepTime--;
                    // FIRE
                    if (isLisaNear(40)) {
                        // setSleep(0);
                        nTimerMax = 10;
                        if (nTimer++ > nTimerMax) {
                            nTimer = 0;
                            // FIRE,更细致的刻画
                            MyGameCanvas.instance.cpc2 = new CPC(
                                    (byte) CPC.B_VAR_MAGIC_CPC_FIRE_BALL);
                            MyGameCanvas.instance.cpc2.initBasic("BALL", 16, 20,
                                    bDirection, 4, true, true, 0);
                            switch (bDirection) {
                                case Canvas.UP: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX + 4, nY + 9);
                                }
                                break;
                                case Canvas.DOWN: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX + 4,
                                            nY + 16);
                                }
                                break;
                                case Canvas.LEFT: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX - 9, nY + 4);
                                }
                                break;
                                case Canvas.RIGHT: {
                                    MyGameCanvas.instance.cpc2.initAbsXY(nX + 16,
                                            nY + 4);
                                }
                                break;
                            }
                            MyGameCanvas.instance.cpc2.initParam(50, 50, 50, 50,
                                    50, 50, 2, 2);
                            MyGameCanvas.instance.cpc2.initAnim(null);
                            MyGameCanvas.instance.vecticUnits.addElement(MyGameCanvas.instance.cpc2);
                            MyGameCanvas.instance.cpc2 = null;
                        }
                    }
                } else {
                    int t_x = nX;
                    int t_y = nY;
                    switch (bDirection) {
                        case Canvas.UP: {
                            t_y -= nSpeed;
                        }
                        break;
                        case Canvas.DOWN: {
                            t_y += nSpeed;
                        }
                        break;
                        case Canvas.LEFT: {
                            t_x -= nSpeed;
                        }
                        break;
                        case Canvas.RIGHT: {
                            t_x += nSpeed;
                        }
                        break;

                    }

                    if (!isCollosionAnything(t_x, t_y)
                            && nLength < N_RABIT_TRAC_LENGTH
                            + MyGameCanvas.instance.getRandomInt(0, 5, 0)) {
                        nX = t_x;
                        nY = t_y;
                        nLength++;
                        if (t_x % Consts.SN_TILE_WIDTH == 0
                                && t_y % Consts.SN_TILE_HEIGHT == 0) {
                            if (N_RABIT_TRAC_LENGTH - nLength < Consts.SN_TILE_WIDTH) {
                                nLength = N_RABIT_TRAC_LENGTH;
                                setSleep(1);
                                nLength = 0;
                                if (nSpeed != 0) {
                                    N_RABIT_TRAC_LENGTH = (short) ((Consts.SN_TILE_HEIGHT / nSpeed) * MyGameCanvas.instance.getRandomInt(1, 6, (int) nX + nY));
                                    if (bDirection == Canvas.LEFT
                                            || bDirection == Canvas.RIGHT) {
                                        N_RABIT_TRAC_LENGTH += Consts.SN_TILE_WIDTH
                                                - (nX % Consts.SN_TILE_WIDTH);
                                    } else if (bDirection == Canvas.UP
                                            || bDirection == Canvas.DOWN) {
                                        N_RABIT_TRAC_LENGTH += Consts.SN_TILE_HEIGHT
                                                - (nX % Consts.SN_TILE_HEIGHT);
                                    }

                                } else {
                                    N_RABIT_TRAC_LENGTH = 20;
                                }
                                boolean flags[] = getDirCanGo(nX, nY);
                                int tl = 0;
                                byte dir[] = new byte[4];
                                for (int i = 0; i < flags.length; i++) {

                                    if (flags[i]) {
                                        if (i == 0) {
                                            dir[tl] = Canvas.UP;
                                        }
                                        if (i == 1) {
                                            dir[tl] = Canvas.DOWN;
                                        }
                                        if (i == 2) {
                                            dir[tl] = Canvas.LEFT;
                                        }
                                        if (i == 3) {
                                            dir[tl] = Canvas.RIGHT;
                                        }

                                        tl++;
                                    }
                                }
                                // 随机
                                if (tl == 0) {
                                } else {
                                    int key = MyGameCanvas.instance.getRandomInt(0,
                                            tl - 1, 1);
                                    bDirection = dir[key];
                                    anim.changeAction(bDirection);
                                }

                            }
                        }

                    } else {
                        setSleep(1);
                        nLength = 0;
                        if (nSpeed != 0) {
                            N_RABIT_TRAC_LENGTH = (short) ((Consts.SN_TILE_HEIGHT / nSpeed) * MyGameCanvas.instance.getRandomInt(1, 6, (int) nX + nY));
                            if (bDirection == Canvas.LEFT
                                    || bDirection == Canvas.RIGHT) {
                                N_RABIT_TRAC_LENGTH += Consts.SN_TILE_WIDTH
                                        - (nX % Consts.SN_TILE_WIDTH);
                            } else if (bDirection == Canvas.UP
                                    || bDirection == Canvas.DOWN) {
                                N_RABIT_TRAC_LENGTH += Consts.SN_TILE_HEIGHT
                                        - (nX % Consts.SN_TILE_HEIGHT);
                            }

                        } else {
                            N_RABIT_TRAC_LENGTH = 20;
                        }
                        boolean flags[] = getDirCanGo(nX, nY);
                        int tl = 0;
                        byte dir[] = new byte[4];
                        for (int i = 0; i < flags.length; i++) {

                            if (flags[i]) {
                                if (i == 0) {
                                    dir[tl] = Canvas.UP;
                                }
                                if (i == 1) {
                                    dir[tl] = Canvas.DOWN;
                                }
                                if (i == 2) {
                                    dir[tl] = Canvas.LEFT;
                                }
                                if (i == 3) {
                                    dir[tl] = Canvas.RIGHT;
                                }

                                tl++;
                            }
                        }
                        // 随机
                        if (tl == 0) {
                        } else {
                            int key = MyGameCanvas.instance.getRandomInt(0, tl - 1,
                                    1);
                            bDirection = dir[key];
                            anim.changeAction(bDirection);
                        }

                    }

                }

            }
            break;
            case B_VAR_MAGIC_CPC_FIRE_BALL: {
                if (nLength > 128) {
                    nHP = 0;
                }
                if (nX <= 0 || nY <= 0 || nX >= MyGameCanvas.snMapWidth
                        || nY >= MyGameCanvas.snMapHeight) {
                    nHP = 0;
                } else {
                    if (!isTileXYNull(nX / Consts.SN_TILE_WIDTH, nY
                            / Consts.SN_TILE_HEIGHT)) {
                        nHP = 0;
                    }
                }

                isCollosionAnything(nX, nY);
                switch (bDirection) {
                    case Canvas.UP: {

                        nY -= nSpeed;

                    }
                    break;
                    case Canvas.DOWN: {

                        nY += nSpeed;
                    }
                    break;
                    case Canvas.LEFT: {

                        nX -= nSpeed;
                    }
                    break;
                    case Canvas.RIGHT: {

                        nX += nSpeed;
                    }
                    break;

                }
                nLength++;

            }
            break;
            case B_VAR_RABIT: {
                if (nSleepTime > 0) {
                    nSleepTime--;
                } else {
                    int t_x = nX;
                    int t_y = nY;
                    switch (bDirection) {
                        case Canvas.UP: {
                            t_y -= nSpeed;
                        }
                        break;
                        case Canvas.DOWN: {
                            t_y += nSpeed;
                        }
                        break;
                        case Canvas.LEFT: {
                            t_x -= nSpeed;
                        }
                        break;
                        case Canvas.RIGHT: {
                            t_x += nSpeed;
                        }
                        break;

                    }

                    if (!isCollosionAnything(t_x, t_y)
                            && nLength < N_RABIT_TRAC_LENGTH) {
                        nX = t_x;
                        nY = t_y;
                        nLength++;
                        if (t_x % Consts.SN_TILE_WIDTH == 0
                                && t_y % Consts.SN_TILE_HEIGHT == 0) {
                            if (N_RABIT_TRAC_LENGTH - nLength < Consts.SN_TILE_WIDTH) {
                                nLength = N_RABIT_TRAC_LENGTH;

                                setSleep(1);
                                nLength = 0;
                                N_RABIT_TRAC_LENGTH = (short) ((Consts.SN_TILE_HEIGHT / nSpeed) * MyGameCanvas.instance.getRandomInt(1, 6, (int) nX + nY));

                                boolean flags[] = getDirCanGo(nX, nY);
                                if (bDirection == Canvas.UP) {
                                    if (flags[3]) {
                                        bDirection = Canvas.RIGHT;
                                    } else {
                                        boolean flag1 = false;
                                        if (flags[1]) {
                                            flag1 = true;
                                            bDirection = Canvas.DOWN;
                                        }
                                        if (flags[2]) {
                                            flag1 = true;
                                            bDirection = Canvas.LEFT;
                                        }
                                        if (!flag1) {
                                            setSleep(0);
                                        }

                                    }
                                } else if (bDirection == Canvas.DOWN) {
                                    if (flags[2]) {
                                        bDirection = Canvas.LEFT;
                                    } else {
                                        boolean flag1 = false;
                                        if (flags[0]) {
                                            flag1 = true;
                                            bDirection = Canvas.UP;
                                        }

                                        if (flags[3]) {
                                            flag1 = true;
                                            bDirection = Canvas.RIGHT;
                                        }

                                        if (!flag1) {
                                            setSleep(0);
                                        }

                                    }
                                } else if (bDirection == Canvas.LEFT) {

                                    if (flags[0]) {
                                        bDirection = Canvas.UP;
                                    } else {
                                        boolean flag1 = false;
                                        if (flags[1]) {
                                            flag1 = true;
                                            bDirection = Canvas.DOWN;
                                        }
                                        if (flags[3]) {
                                            flag1 = true;
                                            bDirection = Canvas.RIGHT;
                                        }

                                        if (!flag1) {
                                            setSleep(0);
                                        }

                                    }
                                } else if (bDirection == Canvas.RIGHT) {
                                    if (flags[1]) {
                                        bDirection = Canvas.DOWN;
                                    } else {
                                        boolean flag1 = false;
                                        if (flags[0]) {
                                            flag1 = true;
                                            bDirection = Canvas.UP;
                                        }

                                        if (flags[2]) {
                                            flag1 = true;
                                            bDirection = Canvas.LEFT;
                                        }

                                        if (!flag1) {
                                            setSleep(0);
                                        }

                                    }
                                }
                                anim.changeAction(bDirection);

                            }
                        }
                    } else {
                        setSleep(1);
                        nLength = 0;
                        N_RABIT_TRAC_LENGTH = (short) ((Consts.SN_TILE_HEIGHT / nSpeed) * MyGameCanvas.instance.getRandomInt(1, 6, (int) nX + nY));

                        boolean flags[] = getDirCanGo(nX, nY);
                        if (bDirection == Canvas.UP) {
                            if (flags[3]) {
                                bDirection = Canvas.RIGHT;
                            } else {
                                boolean flag1 = false;
                                if (flags[1]) {
                                    flag1 = true;
                                    bDirection = Canvas.DOWN;
                                }
                                if (flags[2]) {
                                    flag1 = true;
                                    bDirection = Canvas.LEFT;
                                }
                                if (!flag1) {
                                    setSleep(0);
                                }

                            }
                        } else if (bDirection == Canvas.DOWN) {
                            if (flags[2]) {
                                bDirection = Canvas.LEFT;
                            } else {
                                boolean flag1 = false;
                                if (flags[0]) {
                                    flag1 = true;
                                    bDirection = Canvas.UP;
                                }

                                if (flags[3]) {
                                    flag1 = true;
                                    bDirection = Canvas.RIGHT;
                                }

                                if (!flag1) {
                                    setSleep(0);
                                }

                            }
                        } else if (bDirection == Canvas.LEFT) {
                            // Consts.log("left ...");
                            if (flags[0]) {
                                bDirection = Canvas.UP;
                            } else {
                                boolean flag1 = false;
                                if (flags[1]) {
                                    flag1 = true;
                                    bDirection = Canvas.DOWN;
                                }
                                if (flags[3]) {
                                    flag1 = true;
                                    bDirection = Canvas.RIGHT;
                                }

                                if (!flag1) {
                                    setSleep(0);
                                }

                            }
                        } else if (bDirection == Canvas.RIGHT) {
                            if (flags[1]) {
                                bDirection = Canvas.DOWN;
                            } else {
                                boolean flag1 = false;
                                if (flags[0]) {
                                    flag1 = true;
                                    bDirection = Canvas.UP;
                                }

                                if (flags[2]) {
                                    flag1 = true;
                                    bDirection = Canvas.LEFT;
                                }

                                if (!flag1) {
                                    setSleep(0);
                                }

                            }
                        }
                        anim.changeAction(bDirection);
                    }
                }

            }
            break;
            case B_VAR_MAGIC_LISA_FIRE: {
                anim.update();
                switch (bDirection) {
                    case Canvas.UP: {
                        nY -= nSpeed;

                    }
                    break;
                    case Canvas.DOWN: {
                        nY += nSpeed;
                    }
                    break;
                    case Canvas.LEFT: {
                        nX -= nSpeed;
                    }
                    break;
                    case Canvas.RIGHT: {
                        nX += nSpeed;
                    }
                    break;

                }
                nLength++;
                if (nLength > 128) {
                    nHP = 0;
                }
                if (nX <= 0 || nY <= 0 || nX >= MyGameCanvas.snMapWidth
                        || nY >= MyGameCanvas.snMapHeight) {
                    nHP = 0;
                }
                isCollosionAnything(nX, nY);
            }
            break;
            case B_VAR_SAVING: {
            }
            break;
            case B_VAR_DOOR: {
            }
            break;
            case B_VAR_OGRE: {
                // ogre
                // 1 -> up
                // 6 -> down
                // 2 -> left
                // 5 -> right
                //
                int t_direct = bDirection;
                if ((bDirection == Canvas.UP || bDirection == Canvas.DOWN)
                        && yCalc % MyGameCanvas.instance.nTileHeightForCalc == 0
                        || (bDirection == Canvas.LEFT || bDirection == Canvas.RIGHT)
                        && xCalc % MyGameCanvas.instance.nTileWidthForCalc == 0
                        || bDirection == -1) {
                    // 拐角
                    byte t_direction_1d[] = new byte[5];
                    t_direction_1d[0] = -1;
                    int p_direction1d = 1;
                    int t_ai_alpha = 0;
                    int t_ogre_x = nX / MyGameCanvas.instance.nTileWidth;
                    int t_ogre_y = nY / MyGameCanvas.instance.nTileHeight;
                    if (isTileXYNull(t_ogre_x, t_ogre_y - 1)
                            && isCanOGREMOVETO(t_ogre_x, t_ogre_y - 1)) {
                        // up
                        int t_var = 30;
                        if (bDirection == Canvas.DOWN) { // down
                            t_var -= 20;
                        } else if (nY - MyGameCanvas.instance.npcLisa.nY <= 0) { // 在目标区域上方
                            t_var -= 10;
                        }
                        if (t_var > t_ai_alpha) { // up
                            t_ai_alpha = t_var;
                            t_direction_1d[0] = Canvas.UP;
                            p_direction1d = 1;
                        } else if (t_var == t_ai_alpha) { //
                            t_direction_1d[p_direction1d] = 1;
                            p_direction1d++;
                        }
                    }
                    if (isTileXYNull(t_ogre_x, t_ogre_y + 1)
                            && isCanOGREMOVETO(t_ogre_x, t_ogre_y + 1)) { // down

                        int t_var = 30;
                        if (bDirection == Canvas.UP) { // up
                            t_var -= 20;
                        } else if (MyGameCanvas.instance.npcLisa.nY - nY <= 0) { // 在目标上面
                            t_var -= 10;
                        }
                        if (t_var > t_ai_alpha) {
                            t_ai_alpha = t_var;
                            t_direction_1d[0] = Canvas.DOWN; // down
                            p_direction1d = 1;
                        } else if (t_var == t_ai_alpha) { // down

                            t_direction_1d[p_direction1d] = Canvas.DOWN;
                            p_direction1d++;
                        }
                    }
                    if (isTileXYNull(t_ogre_x - 1, t_ogre_y)
                            && isCanOGREMOVETO(t_ogre_x - 1, t_ogre_y)) {
                        // left
                        int t_var = 30;
                        if (bDirection == Canvas.RIGHT) { // right
                            t_var -= 20;
                        } else if (nX - MyGameCanvas.instance.npcLisa.nX <= 0) {
                            t_var -= 10;
                        }
                        if (t_var > t_ai_alpha) {
                            t_ai_alpha = t_var;
                            t_direction_1d[0] = Canvas.LEFT; // left
                            p_direction1d = 1;
                        } else if (t_var == t_ai_alpha) {

                            t_direction_1d[p_direction1d] = Canvas.LEFT; // left
                            p_direction1d++;

                        }
                    }
                    if (isTileXYNull(t_ogre_x + 1, t_ogre_y)
                            && isCanOGREMOVETO(t_ogre_x + 1, t_ogre_y)) {
                        // right
                        int t_var = 30;
                        if (bDirection == Canvas.LEFT) { // left
                            t_var -= 20;
                        } else if (MyGameCanvas.instance.npcLisa.nX - nX <= 0) {
                            t_var -= 10;
                        }
                        if (t_var > t_ai_alpha) {
                            t_ai_alpha = t_var;
                            t_direction_1d[0] = Canvas.RIGHT; // right
                            p_direction1d = 1;
                        } else if (t_var == t_ai_alpha) {

                            t_direction_1d[p_direction1d] = Canvas.RIGHT; // right
                            p_direction1d++;

                        }
                    }
                    if (p_direction1d == 1) {
                        // 目标方向
                        bDirection = t_direction_1d[0];
                    } else {
                        // 随机方向
                        bDirection = (byte) t_direction_1d[Math.abs(MyGameCanvas.instance.random.nextInt())
                                % p_direction1d];

                    }
                }
                if (t_direct != bDirection) {
                    anim.changeAction(bDirection);
                }
                switch (bDirection) {
                    case Canvas.UP: {
                        yCalc -= nSpeed;
                    }
                    break;
                    case Canvas.DOWN: {
                        yCalc += nSpeed;
                    }
                    break;
                    case Canvas.LEFT: {
                        xCalc -= nSpeed;
                    }
                    break;
                    case Canvas.RIGHT: {
                        xCalc += nSpeed;
                    }
                    break;

                }
                nX = xCalc / 10;
                nY = yCalc / 10;
                int r1x1 = MyGameCanvas.instance.npcLisa.nX;
                int r1y1 = MyGameCanvas.instance.npcLisa.nY;
                int r1x2 = r1x1 + 16;
                int r1y2 = r1y1 + 16;
                //
                int r2x1 = nX;
                int r2y1 = nY;
                int r2x2 = r2x1 + 16;
                int r2y2 = r2y1 + 16;

                if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2, r2x1,
                        r2y1, r2x2, r2y2)) {
                    MyGameCanvas.instance.npcLisa.delHp(-1);
                }
            }
            break;
            case B_VAR_BOMB: {
                switch (bBombState) {
                    case B_BOMB_STATE_STATIC: {
                        if (nTimer < nTimerMax) {
                            nTimer++;
                            break;
                        }
                        setBoom();
                    }
                    break;
                    case B_BOMB_STATE_BOMBING: {
                    }
                    break;
                }
            }
            break;
            case B_VAR_BOMBSFIRE: {

                int r1x1 = nX;
                int r1y1 = nY;
                int r1x2 = nX + Consts.SN_TILE_WIDTH;
                int r1y2 = nY + Consts.SN_TILE_HEIGHT;

                if (nBombFireToDisappear < nBombFireToDisappearMax) {
                    //
                    // System.out.println("nBombFireToDisappear : " +
                    // nBombFireToDisappear);
                    if (nBombFireToDisappear == 0) {
                        //
                        if (Consts.SB_TILE_BRICK == Consts.getTileVar(MyGameCanvas.snsTiles[nY
                                / Consts.SN_TILE_HEIGHT][nX
                                / Consts.SN_TILE_WIDTH])) {
                            // make the bricks to can be bricked tiles
                            MyGameCanvas.snsTiles[nY / Consts.SN_TILE_HEIGHT][nX
                                    / Consts.SN_TILE_WIDTH] = (byte) 25;
                        }
                        // cpc
                        for (int i = 0; i < MyGameCanvas.instance.vecticUnits.size(); i++) {
                            CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
                            if (cpc.bVar == B_VAR_BOSS_SIMPLY
                                    || cpc.bVar == B_VAR_OGRE
                                    || cpc.bVar == B_VAR_WORM
                                    || cpc.bVar == B_VAR_RABIT
                                    || cpc.bVar == B_VAR_BOSS_SNAKE
                                    || cpc.bVar == B_VAR_BOSS_UFO) {
                                int r2x1 = cpc.nX;
                                int r2y1 = cpc.nY;
                                int r2x2 = cpc.nX + Consts.SN_TILE_WIDTH;
                                int r2y2 = cpc.nY + Consts.SN_TILE_HEIGHT;
                                if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2,
                                        r1y2, r2x1, r2y1, r2x2, r2y2)) {

                                    // cpc.nHP--;
                                    cpc.delHp(-1);
                                    if (cpc.bVar == B_VAR_BOSS_SIMPLY
                                            || cpc.bVar == B_VAR_BOSS_SNAKE
                                            || cpc.bVar == B_VAR_BOSS_UFO) {
                                        if (cpc.nHP <= 0) {
                                            cpc.nHP = 1;
                                        }
                                    }
                                }
                            }

                        }
                        //
                        // npc
                        if (MyGameCanvas.instance.npcLisa.nHpTimer < MyGameCanvas.instance.npcLisa.nHpTimerMax) {
                        } else {
                            int r2x1 = MyGameCanvas.instance.npcLisa.nX;
                            int r2y1 = MyGameCanvas.instance.npcLisa.nY;
                            int r2x2 = r2x1 + Consts.SN_TILE_WIDTH;
                            int r2y2 = r2y1 + Consts.SN_TILE_HEIGHT;
                            if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2,
                                    r1y2, r2x1, r2y1, r2x2, r2y2)) {
                                MyGameCanvas.instance.npcLisa.delHp(-1);
                            }
                        }

                    }
                    nBombFireToDisappear++;

                    break;
                }
                nHP = 0;
            }
            break;
        }

        //
        nXInScreen = nX - MyGameCanvas.snMapX + MyGameCanvas.snWindowX;
        nYInScreen = nY - MyGameCanvas.snMapY + MyGameCanvas.snWindowY;
    }

    public void draw(Graphics g) {

        if (isInScreen()) {
            int t_x = nXInScreen;
            int t_y = nYInScreen;
            boolean isDrawHP = false;
            switch (bVar) {
                case B_VAR_ZHEN: {
                    if (bBossState == 0) {
                        MyGameCanvas.blt(g, MyGameCanvas.imageCi, 0, 0, 16, 16,
                                t_x, t_y);
                    } else if (bBossState == 1 || bBossState == 3) {
                        MyGameCanvas.blt(g, MyGameCanvas.imageCi, 16, 0, 16, 16,
                                t_x, t_y);

                    } else {
                        MyGameCanvas.blt(g, MyGameCanvas.imageCi, 32, 0, 16, 16,
                                t_x, t_y);

                    }
                    // g.fillRect(t_x, t_y, 16, 16);

                }
                break;
                case B_VAR_BOX: {
                    if (isHandleByNPC) {
                        t_y = nYInScreen - 24;
                    }
                    g.drawImage(MyGameCanvas.imageBox, t_x, t_y, Graphics.LEFT
                            | Graphics.TOP);

                }
                break;
                case B_VAR_MAGIC_CPC_FIRE: {
                }
                break;
                case B_VAR_MAGIC_CPC_FIRE_BALL: {
                    g.drawImage(MyGameCanvas.imageBall, t_x, t_y, Graphics.LEFT
                            | Graphics.TOP);

                }
                break;
                case B_VAR_MAGIC_LISA_FIRE: {
                }
                break;
                case B_VAR_NPC: {
                    t_y = nYInScreen - 10;
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }

                }
                break;
                case B_VAR_RABIT: {
                    // isDrawHP = true;
                    t_y = nYInScreen;
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }
                }
                break;
                case B_VAR_SAVING: {

                    anim.draw(g, t_x, t_y);

                }
                break;
                case B_VAR_WORM: {
                    t_y = nYInScreen;
                    isDrawHP = true;
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }
                }
                break;
                case B_VAR_DOOR: {
                }
                break;
                case B_VAR_OGRE: {
                    isDrawHP = true;
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }

                }
                break;
                case B_VAR_BOMB: {
                    // g.drawImage(MyGameCanvas.instance.imageBomb, t_x, t_y,
                    // Graphics.TOP | Graphics.LEFT);

                    anim.draw(g, t_x, t_y);

                }
                break;

                case B_VAR_BOMBSFIRE: {
                    // g.setColor(0xfff000);
                    // g.fillRect(t_x, t_y, 16, 16);
                    // if (anim == null) {
                    // System.out.println("null");
                    // }
                    anim.draw(g, t_x, t_y);
                    // g.drawImage(MyGameCanvas.imageFire, t_x, t_y, Graphics.TOP
                    // | Graphics.LEFT);

                }
                break;
                case B_VAR_BOSS_SIMPLY: {
                    isDrawHP = true;
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }

                }
                break;
                case B_VAR_BOSS_UFO: {
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }
                }
                break;

                case B_VAR_BOSS_SNAKE: {
                    // isDrawHP = true;
                    if (anim != null) {
                        if (nHpTimer < nHpTimerMax) {
                            if (nHpTimer % 2 == 0) {
                                anim.draw(g, t_x, t_y);
                            }
                        } else {
                            anim.draw(g, t_x, t_y);
                        }

                    }
                    if (super.nHP <= 1) {
                        int a_x = 0;
                        int a_y = 0;
                        switch (bDirection) {
                            case Canvas.UP: {
                                a_x = 4;
                                a_y = 8;
                            }
                            break;
                            case Canvas.DOWN: {
                                a_x = 4;
                                a_y = 1;
                            }
                            break;
                            case Canvas.LEFT: {
                                a_x = 4;
                                a_y = 3;
                            }
                            break;
                            case Canvas.RIGHT: {
                                a_x = 1;
                                a_y = 3;
                            }
                            break;
                        }
                        g.drawImage(MyGameCanvas.imageX, t_x + a_x, t_y + a_y,
                                Graphics.TOP | Graphics.LEFT);
                    }

                    // if (isDrawHP) {
                    // if (nHpTimer < nHpTimerMax) {
                    // if (nHpTimer % 2 == 0) {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    // } else {
                    // drawHP(g, t_x, t_y - 10, 16, 2);
                    // }
                    //
                    // }
                }
                break;

            }

            //
        }

    }

    // public void drawHP(Graphics g, int x, int y, int width, int height) {
    // // DRAW HP
    // g.setColor(0xffffff);
    // g.drawRect(x, y, width + 1, height + 1);
    // int length = 0;
    // length = nHP * width / nHPMax;
    // g.setColor(0xff0000);
    // g.fillRect(x + 1, y + 1, length, height);
    // }
    public boolean isInScreen() {
        return true;
    }

    public void upBox() {
        //
        isHandleByNPC = true;
        nX = MyGameCanvas.instance.npcLisa.nX;
        nY = MyGameCanvas.instance.npcLisa.nY;
    }

    public void downBox() {

        int tempx = nX;
        int tempy = nY;

        switch (MyGameCanvas.instance.npcLisa.bDirection) {
            case Canvas.UP: {
                nX = MyGameCanvas.instance.npcLisa.nX;
                nY = MyGameCanvas.instance.npcLisa.nY - 16;
            }
            break;
            case Canvas.DOWN: {
                nX = MyGameCanvas.instance.npcLisa.nX;
                nY = MyGameCanvas.instance.npcLisa.nY + 16;
            }
            break;
            case Canvas.LEFT: {
                nX = MyGameCanvas.instance.npcLisa.nX - 16;
                nY = MyGameCanvas.instance.npcLisa.nY;
            }
            break;
            case Canvas.RIGHT: {
                nX = MyGameCanvas.instance.npcLisa.nX + 16;
                nY = MyGameCanvas.instance.npcLisa.nY;
            }
            break;
        }
        //
        // Consts.log("判断是否能落下箱子：）");
        if (nX % Consts.SN_TILE_WIDTH != 0 || nY % Consts.SN_TILE_HEIGHT != 0) {
            // 不能落下
            // Consts.log("判断是否能落下箱子：）1");
            nX = tempx;
            nY = tempy;
            return;
        }
        // 探测地图
        if (Consts.SB_TILE_NULL != Consts.getTileVar(MyGameCanvas.snsTiles[nY
                / Consts.SN_TILE_HEIGHT][nX / Consts.SN_TILE_WIDTH])) {
            // Consts.log("判断是否能落下箱子：）2");
            nX = tempx;
            nY = tempy;
            return;
        }
        for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
            CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
            int r1x1 = cpc.nX;
            int r1y1 = cpc.nY;
            int r1x2 = cpc.nX + 16;
            int r1y2 = cpc.nY + 16;
            int r2x1 = nX;
            int r2y1 = nY;
            int r2x2 = nX + 16;
            int r2y2 = nY + 16;
            if (cpc.equals(this)) {
                // 不和自己比较：）
            } else {
                if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2, r2x1,
                        r2y1, r2x2, r2y2)) {
                    // Consts.log("判断是否能落下箱子：）3");
                    nX = tempx;
                    nY = tempy;
                    return;
                }
            }

        }
        // 当能放下箱子的时候改变：）
        isHandleByNPC = false;
        MyGameCanvas.instance.npcLisa.isHandleCPC = false;

    }

    public boolean isLisaNear(int dis) {

        boolean flag = false;
        if (Math.abs(nX - MyGameCanvas.instance.npcLisa.nX) < dis
                && Math.abs(nY - MyGameCanvas.instance.npcLisa.nY) < dis) {
            flag = true;
        }
        return flag;
    }

    public boolean isCollosionAnything(int ax, int ay) {
        boolean flag = false;
        boolean[] br = getCollosionWithMap(ax, ay);
        if (br[0] || br[1] || br[2] || br[3]) {
            flag = true;
        }
        if (flag) {
            return flag;
        } else {
            // CPC
            for (int i = 0; i < MyGameCanvas.vecticUnits.size(); i++) {
                CPC cpc = (CPC) MyGameCanvas.vecticUnits.elementAt(i);
                if (cpc.bVar == B_VAR_MAGIC_CPC_FIRE
                        || cpc.bVar == B_VAR_MAGIC_CPC_FIRE_BALL
                        || cpc.bVar == B_VAR_MAGIC_CPC_FIRE) {
                    continue;
                }

                int r1x1 = cpc.nX;
                int r1y1 = cpc.nY;
                int r1x2 = cpc.nX + 16;
                int r1y2 = cpc.nY + 16;
                if (bVar == B_VAR_BOSS_UFO) {
                    r1x1 = cpc.nX + 4;
                    r1y1 = cpc.nY + 4;
                    r1x2 = cpc.nX + 24;
                    r1y2 = cpc.nY + 24;
                }
                if (bVar == B_VAR_MAGIC_LISA_FIRE) {
                    int r2x1 = ax;
                    int r2y1 = ay;
                    int r2x2 = ax + 8;
                    int r2y2 = ay + 8;
                    if (cpc.equals(this)) {
                        // 不和自己比较：）
                    } else {

                        if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2,
                                r1y2, r2x1, r2y1, r2x2, r2y2)) {
                            switch (cpc.bVar) {
                                case B_VAR_BOX:
                                case B_VAR_NPC:
                                case B_VAR_DOOR:
                                case B_VAR_SAVING:
                                case B_VAR_ZHEN: {
                                    if (cpc.bVar == B_VAR_ZHEN) {
                                        if (bBossState == 2) {
                                            flag = true;
                                        }
                                    } else {
                                        flag = true;
                                    }
                                }
                                break;
                                default: {
                                    // cpc.nHP -= nAttackPower; //
                                    // nHP = 0;
                                    // flag = true;
                                    // // 通知那个人睡眠，自己不睡眠
                                    // cpc.setSleep(i << 1);
                                    // nHP = 0;
                                    flag = false;
                                }
                                break;
                            }
                        }

                    }
                } else if (bVar == B_VAR_MAGIC_CPC_FIRE
                        || bVar == B_VAR_MAGIC_CPC_FIRE_BALL) {
                    int r2x1 = ax;
                    int r2y1 = ay;
                    int r2x2 = ax + 8;
                    int r2y2 = ay + 8;
                    if (cpc.equals(this)) {
                        // 不和自己比较：）
                    } else {

                        if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2,
                                r1y2, r2x1, r2y1, r2x2, r2y2)) {
                            switch (cpc.bVar) {
                                case B_VAR_BOX:
                                case B_VAR_NPC:
                                case B_VAR_DOOR:
                                case B_VAR_SAVING: {
                                    nHP = 0;
                                    flag = true;
                                }

                                break;

                            }
                        }

                    }
                } else {
                    int r2x1 = ax;
                    int r2y1 = ay;
                    int r2x2 = ax + 16;
                    int r2y2 = ay + 16;
                    if (cpc.equals(this)) {
                        // 不和自己比较：）
                    } else {
                        if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2,
                                r1y2, r2x1, r2y1, r2x2, r2y2)) {
                            flag = false;
                            switch (cpc.bVar) {
                                case B_VAR_BOX:
                                case B_VAR_NPC:
                                case B_VAR_DOOR:
                                case B_VAR_SAVING:
                                case B_VAR_ZHEN: {
                                    if (cpc.bVar == CPC.B_VAR_ZHEN) {
                                        if (bBossState == 2) {
                                            flag = true;
                                        }
                                    } else {
                                        flag = true;
                                    }
                                }

                                break;

                            }

                            // 通知那个人睡眠，自己不睡眠
                            // cpc.setSleep(i << 1);

                        }

                    }
                }

            }
            if (flag) {
                return flag;
            } else {
                int r1x1 = MyGameCanvas.instance.npcLisa.nX + 2;
                int r1y1 = MyGameCanvas.instance.npcLisa.nY + 2;
                int r1x2 = r1x1 + 16 - 4;
                int r1y2 = r1y1 + 16 - 4;
                if (bVar == B_VAR_MAGIC_LISA_FIRE) {
                    //
                } else if (bVar == B_VAR_MAGIC_CPC_FIRE
                        || bVar == B_VAR_MAGIC_CPC_FIRE_BALL) {
                    int r2x1 = ax;
                    int r2y1 = ay;
                    int r2x2 = ax + 8;
                    int r2y2 = ay + 9;
                    if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2,
                            r2x1, r2y1, r2x2, r2y2)) {
                        MyGameCanvas.instance.npcLisa.delHp(-1);
                        nHP = 0;
                        // NPC
                        flag = true;

                    }
                } else {
                    int r2x1 = ax;
                    int r2y1 = ay;
                    int r2x2 = ax + 16;
                    int r2y2 = ay + 16;

                    if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2,
                            r2x1, r2y1, r2x2, r2y2)) {
                        // NPC
                        if (bVar == B_VAR_BOSS_SIMPLY
                                || bVar == B_VAR_BOSS_SNAKE
                                || bVar == B_VAR_BOSS_UFO || bVar == B_VAR_OGRE
                                || bVar == B_VAR_OGRE || bVar == B_VAR_WORM) {

                            MyGameCanvas.instance.npcLisa.delHp(-1);
                        } else if (bVar == B_VAR_ZHEN) {
                            if (bBossState == 2) {
                                MyGameCanvas.instance.npcLisa.delHp(-1);
                            }

                        }
                        flag = false;

                    }
                }
                return flag;
            }

        }

    }

    //
    public boolean[] getDirCanGo(int x, int y) {
        boolean flags[] = new boolean[4];
        int t_x = 0;
        int t_y = 0;
        // up
        t_x = x;
        t_y = y - nSpeed;
        if (isCollosionAnything(t_x, t_y)) {
            flags[0] = false; // 不能走
        } else {
            flags[0] = true; // 能走
        }
        // down
        t_x = x;
        t_y = y + nSpeed;
        if (isCollosionAnything(t_x, t_y)) {
            flags[1] = false; // 不能走
        } else {
            flags[1] = true; // 能走
        }
        // left
        t_x = x - nSpeed;
        t_y = y;
        if (isCollosionAnything(t_x, t_y)) {
            flags[2] = false; // 不能走
        } else {
            flags[2] = true; // 能走
        }
        // right
        t_x = nX + nSpeed;
        t_y = nY;
        if (isCollosionAnything(t_x, t_y)) {
            flags[3] = false; // 不能走
        } else {
            flags[3] = true; // 能走
        }
        return flags;
    }

    //
    public boolean[] getCollosionWithMap(int ax, int ay) {

        boolean[] flags = new boolean[4];

        int tx[] = new int[4];
        int ty[] = new int[4];

        for (int i = 0; i < flags.length; i++) {
            flags[i] = false;
        }
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < nRectCollosion[i].length; j++) {
                boolean flag = false;
                int t_x0 = nRectCollosion[i][j][0] + ax;
                int t_y0 = nRectCollosion[i][j][1] + ay;

                int t_x1 = nRectCollosion[i][j][2] + ax;
                int t_y1 = nRectCollosion[i][j][3] + ay;

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

    public void setSleep(int seed) {
        nSleepTime = MyGameCanvas.instance.getRandomInt(1, N_SLEEP_TIME, seed);
    }

    public void initAbsXY(int x, int y) {
        nX = x;
        nY = y;

        xCalc = x * 10;
        yCalc = y * 10;

    }

    public void setBoom() {
        // Consts.log("setBoom()");
        nTimer = 0;
        bBombState = B_BOMB_STATE_BOMBING;
        nHP = 0;
        //
        // 计算
        int tx = nX / Consts.SN_TILE_WIDTH;
        int ty = nY / Consts.SN_TILE_HEIGHT;
        // up down
        if (tx > 0 && tx < MyGameCanvas.snTilesWidthNum - 1) {
            int t_var = ty;
            while (t_var > 0) {
                t_var--;
                byte key = getMapsPre(tx, t_var);
                if (key == (byte) 0) {
                    nAttackByBombTileNumUp++;
                } else if (key == 2 || key == 3) {
                    nAttackByBombTileNumUp++;
                    break;
                } else {
                    break;
                }
                if (nAttackByBombTileNumUp >= nPower) {
                    break;
                }
            }
            t_var = ty;
            while (t_var < MyGameCanvas.snTilesHeightNum - 1) {
                t_var++;
                byte key = getMapsPre(tx, t_var);
                if (key == 0) {
                    nAttackByBombTileNumDown++;
                } else if (key == 2 || key == 3) {
                    nAttackByBombTileNumDown++;
                    break;
                } else {
                    break;
                }
                if (nAttackByBombTileNumDown >= nPower) {
                    break;
                }
            }
        }
        // left right

        if (ty > 0 && ty < MyGameCanvas.snTilesHeightNum - 1) {
            int t_var = tx;
            while (t_var > 0) {
                t_var--;
                byte key = getMapsPre(t_var, ty);
                if (key == 0) {
                    nAttackByBombTileNumLeft++;
                } else if (key == 2 || key == 3) {
                    nAttackByBombTileNumLeft++;
                    break;
                } else {
                    break;
                }
                if (nAttackByBombTileNumLeft >= nPower) {
                    break;
                }
            }
            t_var = tx;
            while (t_var < MyGameCanvas.snTilesWidthNum - 1) {
                t_var++;
                byte key = getMapsPre(t_var, ty);
                if (key == 0) {
                    nAttackByBombTileNumRight++;
                } else if (key == 2 || key == 3) {
                    nAttackByBombTileNumRight++;
                    break;
                } else {
                    break;
                }
                if (nAttackByBombTileNumRight >= nPower) {
                    break;
                }
            }
        }
        //
        // Consts.log("nAttackByBombTileNumLeft " + nAttackByBombTileNumLeft);
        // 放置火焰
        MyGameCanvas.instance.addFires(tx, ty);
        // up
        for (int m = 1; m <= nAttackByBombTileNumUp; m++) {
            MyGameCanvas.instance.addFires(tx, ty - m);
        }
        // down
        for (int m = 1; m <= nAttackByBombTileNumDown; m++) {
            MyGameCanvas.instance.addFires(tx, ty + m);
        }
        // left
        for (int m = 1; m <= nAttackByBombTileNumLeft; m++) {
            MyGameCanvas.instance.addFires(tx - m, ty);
        }
        // right
        for (int m = 1; m <= nAttackByBombTileNumRight; m++) {
            MyGameCanvas.instance.addFires(tx + m, ty);
        }

        // 引爆周围的炸弹
        if (MyGameCanvas.instance.vecticUnits != null) {
            int size = MyGameCanvas.instance.vecticUnits.size();
            //
            for (int i = 0; i < size; i++) {
                CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
                if (cpc.bVar == B_VAR_BOMB) {
                    if (!cpc.equals(this)) {
                        if (cpc.bBombState == B_BOMB_STATE_STATIC) {
                            // up
                            for (int m = 1; m <= nAttackByBombTileNumUp; m++) {
                                if (tx == cpc.nX / Consts.SN_TILE_WIDTH
                                        && ty - m == cpc.nY
                                        / Consts.SN_TILE_HEIGHT) {
                                    cpc.setBoom();
                                }
                            }
                            // down
                            for (int m = 1; m <= nAttackByBombTileNumDown; m++) {
                                if (tx == cpc.nX / Consts.SN_TILE_WIDTH
                                        && ty + m == cpc.nY
                                        / Consts.SN_TILE_HEIGHT) {
                                    cpc.setBoom();
                                }
                            }
                            // left
                            for (int m = 1; m <= nAttackByBombTileNumLeft; m++) {
                                if (tx - m == cpc.nX / Consts.SN_TILE_WIDTH
                                        && ty == cpc.nY / Consts.SN_TILE_HEIGHT) {
                                    cpc.setBoom();
                                }
                            }
                            // right
                            for (int m = 1; m <= nAttackByBombTileNumRight; m++) {
                                if (tx + m == cpc.nX / Consts.SN_TILE_WIDTH
                                        && ty == cpc.nY / Consts.SN_TILE_HEIGHT) {
                                    cpc.setBoom();
                                }
                            }

                        }
                    }
                }
            }
        }
        //
    }

    public boolean isTileXYNull(int x, int y) {
        if (x < 0 || x >= MyGameCanvas.snTilesWidthNum || y < 0
                || y >= MyGameCanvas.snTilesHeightNum) {
            return false;
        }
        boolean isFlag = true;
        if (Consts.getTileVar(MyGameCanvas.snsTiles[y][x]) != Consts.SB_TILE_NULL) {
            isFlag = false;
        }
        if (isFlag) {
            int r1x1 = x * Consts.SN_TILE_WIDTH;
            int r1y1 = y * Consts.SN_TILE_HEIGHT;
            int r1x2 = r1x1 + Consts.SN_TILE_WIDTH;
            int r1y2 = r1y1 + Consts.SN_TILE_HEIGHT;
            for (int i = 0; i < MyGameCanvas.instance.vecticUnits.size(); i++) {
                CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
                if (cpc.bVar == CPC.B_VAR_BOX || (cpc.bVar == CPC.B_VAR_ZHEN)) {
                    if (cpc.bVar == B_VAR_ZHEN) {
                        if (bBossState != 2) {
                            continue;
                        }
                    }
                    int r2x1 = cpc.nX;
                    int r2y1 = cpc.nY;
                    int r2x2 = cpc.nX + Consts.SN_TILE_WIDTH;
                    int r2y2 = cpc.nY + Consts.SN_TILE_HEIGHT;
                    if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2,
                            r2x1, r2y1, r2x2, r2y2)) {

                        return false;
                    }
                }
            }
        }
        return isFlag;
    }

    public boolean isTilesNullForSnake(int x, int y) {
        try {
            if (Consts.getTileVar(MyGameCanvas.snsTiles[y][x]) == Consts.SB_TILE_NULL) {
                return true;

            }
        } catch (Exception ex) {
        }
        return false;
    }

    public byte getMapsPre(int x, int y) {
        // 0 -> 可以通行 1 -> 地图不能行走, 2 -> 有物体 3 -> 地图为可以爆炸的砖块
        if (x < 0 || x >= MyGameCanvas.snTilesWidthNum || y < 0
                || y >= MyGameCanvas.snTilesHeightNum) {
            return (byte) 1;
        }
        boolean isFlag = true;
        if (Consts.getTileVar(MyGameCanvas.snsTiles[y][x]) != Consts.SB_TILE_NULL) {
            if (Consts.getTileVar(MyGameCanvas.snsTiles[y][x]) == Consts.SB_TILE_BRICK) {
                return (byte) 3;
            }
            return (byte) 1;
        }
        if (isFlag) {

            int r1x1 = x * Consts.SN_TILE_WIDTH;
            int r1y1 = y * Consts.SN_TILE_HEIGHT;
            int r1x2 = r1x1 + Consts.SN_TILE_WIDTH;
            int r1y2 = r1y1 + Consts.SN_TILE_HEIGHT;
            for (int i = 0; i < MyGameCanvas.instance.vecticUnits.size(); i++) {
                CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
                if (cpc.bVar == CPC.B_VAR_SAVING || cpc.bVar == CPC.B_VAR_BOX
                        || cpc.bVar == CPC.B_VAR_BOSS_SIMPLY
                        || cpc.bVar == CPC.B_VAR_BOSS_SNAKE) {
                    int r2x1 = cpc.nX;
                    int r2y1 = cpc.nY;
                    int r2x2 = cpc.nX + Consts.SN_TILE_WIDTH;
                    int r2y2 = cpc.nY + Consts.SN_TILE_HEIGHT;
                    if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2,
                            r2x1, r2y1, r2x2, r2y2)) {
                        return (byte) 2;
                    }
                }
            }
            // lisa
            int r2x1 = MyGameCanvas.instance.npcLisa.nX;
            int r2y1 = MyGameCanvas.instance.npcLisa.nY;
            int r2x2 = r2x1 + Consts.SN_TILE_WIDTH;
            int r2y2 = r2y1 + Consts.SN_TILE_HEIGHT;
            if (MyGameCanvas.isIntersectRect(r1x1, r1y1, r1x2, r1y2, r2x1,
                    r2y1, r2x2, r2y2)) {
                return (byte) 2;
            }
        }
        return (byte) 0;
    }

    public boolean isCanOGREMOVETO(int x, int y) {
        for (int i = 0; i < MyGameCanvas.instance.vecticUnits.size(); i++) {
            CPC cpc = (CPC) MyGameCanvas.instance.vecticUnits.elementAt(i);
            if (cpc.bVar == CPC.B_VAR_BOMB || cpc.bVar == CPC.B_VAR_BOX
                    || cpc.bVar == CPC.B_VAR_DOOR || cpc.bVar == CPC.B_VAR_NPC
                    || cpc.bVar == CPC.B_VAR_RABIT
                    || cpc.bVar == CPC.B_VAR_SAVING) {
                if (cpc.nX / Consts.SN_TILE_WIDTH == x
                        && cpc.nY / Consts.SN_TILE_HEIGHT == y) {
                    return false;
                }
            }
        }
        return true;
    }
}
