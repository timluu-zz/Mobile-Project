
import java.io.DataInputStream;

import javax.microedition.lcdui.*;

/**
 * 非作者授权，请勿用于商业用途。 动画播放器
 *
 * @author bruce.fine@gmail.com
 *
 */
public class Anim {

    Image image = null;
    short nImagePieceNum = 0;
    int nFps = 0;
    int nfpsc = 0;
    int nFrame = 0;
    int nFrameNums = 0;
    short nActionNum = 0;
    int nActionId = 0;
    boolean isStop = false;
    boolean isLoop = false;
    short[][] word2d = null;
    short[][] word2d1 = null;
    Anim animInited = null;

    public void initParam(int nActionIdA, boolean stopA, boolean loopA) {
        nActionId = nActionIdA;
        switch (nActionId) {
            case Canvas.UP: {
                nActionId = 0;
            }
            break;
            case Canvas.DOWN: {
                nActionId = 1;
            }
            break;
            case Canvas.LEFT: {
                nActionId = 2;
            }
            break;
            case Canvas.RIGHT: {
                nActionId = 3;
            }
            break;

        }
        isStop = stopA;
        isLoop = loopA;
        if (word2d1 != null) {
            nFrameNums = word2d1[nActionId].length / 3;
        }
    }

    public void update() {
        if (!isStop) {
            if (nfpsc >= nFps) {
                nfpsc = 0;
                if (nFrame >= nFrameNums - 1) {
                    if (isLoop) {
                        nFrame = 0;
                    } else {
                        nFrame = 0;
                        isStop = true;
                    }
                } else {
                    nFrame++;
                }
            } else {
                nfpsc++;
            }
        }

    }

    public void draw(Graphics g, int x, int y) {
        try {
            int t_pic_id = nFrame * 3;
            if (word2d1[nActionId][t_pic_id] < word2d.length) {
                //
                int tx = word2d[word2d1[nActionId][t_pic_id]][0];
                int ty = word2d[word2d1[nActionId][t_pic_id]][1];
                int twidth = word2d[word2d1[nActionId][t_pic_id]][2];
                int theight = word2d[word2d1[nActionId][t_pic_id]][3];
                //
                if (x + twidth < 0 || y + theight < 0
                        || x > Consts.SN_SCREEN_WIDTH
                        || y > Consts.SN_SCREEN_HEIGHT) {
                } else {
                    MyGameCanvas.blt(g, image, tx, ty, twidth, theight, x
                            + word2d1[nActionId][t_pic_id + 1], y
                            + word2d1[nActionId][t_pic_id + 2]);
                }

            }

        } catch (Exception ex) {

            Consts.log("failer");
        }
    }

    public void changeAction(int idA) {
        switch (idA) {
            case Canvas.UP: {
                idA = 0;
            }
            break;
            case Canvas.DOWN: {
                idA = 1;
            }
            break;
            case Canvas.LEFT: {
                idA = 2;
            }
            break;
            case Canvas.RIGHT: {
                idA = 3;
            }
            break;

        }
        if (nActionId != idA) {
            if (word2d1 != null) {
                nFrame = 0;
                nActionNum = (short) word2d1.length;
                nActionId = idA;
                nFrameNums = word2d1[nActionId].length / 3;
                isStop = false;
            }
        } else {
            if (isStop) {
                if (word2d1 != null) {
                    nFrame = 0;
                    nActionNum = (short) word2d1.length;
                    nActionId = idA;
                    nFrameNums = word2d1[nActionId].length / 3;
                    isStop = false;
                }
            }
        }
    }

    public void init(MyGameCanvas mc, Image image, String str) {
        try {
            int i1 = 0;
            short aword0[][] = null;
            short aword1[][] = null;
            DataInputStream datainputstream = null;
            i1 = (datainputstream = new DataInputStream(mc.getClass().getResourceAsStream(str))).readInt();
            int j1;
            aword0 = new short[j1 = datainputstream.readInt()][4];
            for (int k1 = 0; k1 < j1; k1++) {
                aword0[k1][0] = datainputstream.readShort();
                aword0[k1][1] = datainputstream.readShort();
                aword0[k1][2] = datainputstream.readShort();
                aword0[k1][3] = datainputstream.readShort();
            }

            int l1;
            aword1 = new short[l1 = datainputstream.readInt()][];
            for (int i2 = 0; i2 < l1; i2++) {
                short word0 = datainputstream.readShort();
                aword1[i2] = new short[word0];
                for (int j2 = 0; j2 < word0; j2++) {
                    aword1[i2][j2] = datainputstream.readShort();
                }

            }

            nFrame = 0;
            this.image = image;
            nFps = i1;

            word2d = aword0;
            if (word2d != null) {
                nImagePieceNum = (short) (word2d.length);
            }

            word2d1 = aword1;
            if (word2d1 != null) {
                nActionNum = (short) word2d1.length;
                nFrameNums = word2d1[nActionId].length / 3;
            }
            datainputstream.close();
            //
            return;
        } catch (Exception ex) {
            //
        }
    }

    public Anim copy() {
        Anim anim = new Anim();
        //
        nFrame = 0;
        anim.image = image;
        anim.nFps = nFps;
        anim.isStop = isStop;
        anim.isLoop = isLoop;
        //
        anim.word2d = new short[word2d.length][word2d[0].length];

        if (anim.word2d != null) {
            anim.nImagePieceNum = (short) (word2d.length);
        }
        //
        for (int i = 0; i < word2d.length; i++) {
            for (int j = 0; j < word2d[i].length; j++) {
                anim.word2d[i][j] = word2d[i][j];
            }
        }
        anim.word2d1 = new short[word2d1.length][word2d1[0].length];

        if (anim.word2d1 != null) {
            anim.nActionNum = (short) word2d1.length;
            anim.nFrameNums = word2d1[nActionId].length / 3;
        }
        //
        //
        for (int i = 0; i < word2d1.length; i++) {
            for (int j = 0; j < word2d1[i].length; j++) {
                anim.word2d1[i][j] = word2d1[i][j];
            }
        }
        return anim;
    }
}
