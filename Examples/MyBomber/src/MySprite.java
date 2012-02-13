
import javax.microedition.lcdui.Graphics;

/**
 * 非作者授权，请勿用于商业用途。
 *
 * bruce.fine@gmail.com
 */
public class MySprite {
    // 名字

    public String strName;
    // 地图上的位置
    public int nX = 0;
    public int nY = 0;
    // 坐标
    public int nXInScreen;
    public int nYInScreen;
    // 方向
    public byte bDirection;
    //
    public int nHP;
    public int nMP;
    public int nEX;
    // 增加的经验值
    public int nCurrentExValue;
    public int nHPMax;
    public int nMPMax;
    public int nEXMax;
    public int nAttackPower;
    public int nDefendPower;
    public int nVisorLevel;
    public int nClothLevel;
    public int nWeaponLevel;
    public int nNombrilLevel;
    public int nPowerLevel;
    public int nHPDes;
    //
    public int nSpeed;
    public boolean isMoving;
    /**
     * 是否参与了战斗
     */
    public boolean isFighted;
    /**
     * 是不是进攻型的NPC
     */
    public boolean isAbleToFighting;
    int nFrameCac = 0;
    int nFrameCacMax = 0;
    int nLevel = 0;
    int yCalc = 0;
    int xCalc = 0;

    public MySprite() {
    }

    public void initBasic(String name, int x, int y, byte dir, int speed,
            boolean isMoving, boolean isAbleToFighting,
            int frameMax) {
        strName = name;
        this.nX = x * Consts.SN_TILE_WIDTH;
        this.nY = y * Consts.SN_TILE_HEIGHT;
        this.yCalc = nY * 10;
        this.xCalc = nX * 10;
        this.bDirection = dir;
        this.nSpeed = speed;
        this.isMoving = isMoving;
        this.isAbleToFighting = isAbleToFighting;
        nFrameCacMax = frameMax;
    }

    public void initParam(int hp, int mp, int ex, int hpMax, int mpMax,
            int exMax, int attackP, int defendP) {
        this.nHP = hp;
        this.nMP = mp;
        this.nEX = ex;
        this.nHPMax = hpMax;
        this.nMPMax = mpMax;
        this.nEXMax = exMax;
        this.nAttackPower = attackP;
        this.nDefendPower = defendP;
    }

    public void update() {
        if (++nFrameCac > nFrameCacMax) {
            nFrameCac = 0;

        }
    }

    public void draw(Graphics g) {
    }

    public boolean isInsideScreen() {
        if (nXInScreen <= -16 || nXInScreen >= Consts.SN_SCREEN_WIDTH + 16
                || nYInScreen <= -24
                || nYInScreen >= Consts.SN_SCREEN_HEIGHT + 24) {
            return false;
        }

        return true;
    }
}
