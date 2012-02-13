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
import javax.microedition.lcdui.Canvas;
import lipeng.LPKeyMask;
import lipeng.LPMaps;
import lipeng.LPRect;
import lipeng.LPSprite;

public class MarioMainSprite
        extends LPSprite {

    public MarioMainSprite(MarioGameManage gm, LPMaps map) {
        this.gm = gm;
        this.judgeMap = map;
        mainSpriteState = SMALL_SPRITE;
        this.gm = gm;
    }

//method
    public void action() {
        bgdx = 0;
        bgdy = 0;
        switch (mainSpriteState) {
            case GROW_SPRITE:
                if (++growCnt == 10) {
                    mainSpriteState = NORMAL_SPRITE;
                    growCnt = 0;
                } else {
                    if (growCnt % 2 == 0) {
                        y += 16;
                    } else {
                        y -= 16;
                    }
                }
                break;
            case BECOME_FIRE:
                if (++becomeCnt == 10) {
                    mainSpriteState = FIRE_SPRITE;
                    becomeCnt = 0;
                }
                break;
            case DEAD_SPRITE:
                if (deadCnt == 0) {
                    dy = -10;
                    frameCnt = 10;
                }
                frameCnt = 10;
                ++dy;
                y += dy;
                if (++deadCnt >= 20) {
                    deadCnt = 0;
                    mainSpriteState = SMALL_SPRITE;
                    gm.gameState = MarioGameManage.GAMESTATE_REINIT;
                }
                break;
            case BECOME_SMALL:
                if (++becomeCnt == 10) {
                    mainSpriteState = SMALL_SPRITE;
                    becomeCnt = 0;
                }
                break;
            default: {
                dy += 2;
                checkSpriteCollisionVertical();
                checkTileCollisionVertical();
                mainSpriteDamage();
                y += dy;

                if (y + this.judgeMap.y < 0) {
                    y = 0;
                    dy = 0;
                }
                if (y <= MarioMIDlet.gameCanvas.height / 4
                        || ((this.judgeMap.y
                        != this.judgeMap.tileSize * this.judgeMap.h
                        - MarioMIDlet.gameCanvas.height)
                        && y >= MarioMIDlet.gameCanvas.height / 4)) {
                    this.bgdy = (MarioMIDlet.gameCanvas.height / 4 - y) >> 2;

                    if (bgdy - this.judgeMap.y
                            < MarioMIDlet.gameCanvas.height
                            - this.judgeMap.tileSize * this.judgeMap.h) {
                        bgdy = MarioMIDlet.gameCanvas.height
                                - this.judgeMap.tileSize * this.judgeMap.h + this.judgeMap.y;
                    }

                    if ((this.judgeMap.y <= 0) && y < MarioMIDlet.gameCanvas.height / 4) {
                        this.judgeMap.y = 0;
                        bgdy = 0;
                    }
                } else {
                    bgdy = 0;
                }

                if ((allAction & LPKeyMask.MASK_KEY_LEFT) != 0) {
                    this.isLeft = true;
                    if (--dx < -MOVE_SPEED) {
                        dx = -MOVE_SPEED;
                    }
                    if (checkTileCollisionHorizon()) {
                        dx = 0;
                    }
                } else if ((allAction & LPKeyMask.MASK_KEY_RIGHT) != 0) {
                    this.isLeft = false;
                    if (++dx > MOVE_SPEED) {
                        dx = MOVE_SPEED;
                    }
                    if (checkTileCollisionHorizon()) {
                        dx = 0;
                    }
                } else {
                    if (dx > 0) {
                        if (checkTileCollisionHorizon()) {
                            dx = 0;
                        } else {
                            --dx;
                        }
                    } else if (dx < 0) {
                        if (checkTileCollisionHorizon()) {
                            dx = 0;
                        } else {
                            ++dx;
                        }
                    }
                }
                if (!checkSpriteCollisionHorizon()) {
                    x += dx;
                    if (dx < 0) {
                        if (distance >= -gm.canvas.width) {
                            distance += dx;
                        }
                    }
                    if (distance < 0) {
                        if (dx > 0) {
                            distance += dx;
                        }
                    }
                } else {
                    dx = 0;
                }

                if (this.x != MarioMIDlet.gameCanvas.width / 4) {
                    this.bgdx = MarioMIDlet.gameCanvas.width / 4 - x;
                    if (distance < -(gm.canvas.width - MarioMIDlet.gameCanvas.width / 4)) {
                        bgdx = 0;
                    }
                } else {
                    bgdx = 0;
                }
                if (judgeMap.x - bgdx < 0
                        || judgeMap.x - bgdx
                        > judgeMap.tileSize * judgeMap.w - MarioMIDlet.gameCanvas.width) {
                    bgdx = 0;
                }

                if (mainSpriteState == FIRE_SPRITE) {
                    if ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) != 0) {
                        fire();
                    }
                    checkBulletDamage();
                }

                x += this.bgdx;
                judgeMap.x -= this.bgdx;

                y += this.bgdy;
                judgeMap.y -= this.bgdy;

                int tmpDy = this.bgdy;
                if (judgeMap.y < 0) {
                    tmpDy = bgdy + judgeMap.y;
                    this.bgdy = -judgeMap.y;
                    judgeMap.y = 0;
                    y += this.bgdy;
                }
                bgdy = tmpDy;

                if (x < 0) {
                    x = 0;
                }
                if (this.x >= MarioMIDlet.gameCanvas.width - 32) {
                    this.x = MarioMIDlet.gameCanvas.width - 32;
                }
                if (isLeft) {
                    offset = 5;
                } else {
                    offset = 0;
                }

                if (dy != 0) {
                    frameCnt = offset + 3;
                } else if (((allAction & LPKeyMask.MASK_KEY_LEFT) != 0)
                        || ((allAction & LPKeyMask.MASK_KEY_RIGHT) != 0)) {
                    walk();
                } else if (dy == 0 && (allAction & LPKeyMask.MASK_KEY_UP_FLAG) == 0) {
                    frameCnt = offset;
                }
                if (isProtected) {
                    isHidden = !isHidden;
                    if (protectCnt++ == protectInterval) {
                        protectCnt = 0;
                        isProtected = false;
                        isHidden = false;
                    }
                } else {
                    checkEnemyCollision();
                }
                if ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) != 0) {
                    if (mainSpriteState == FIRE_SPRITE) {
                        frameCnt = offset + 1;
                    }
                    allAction &= ~LPKeyMask.MASK_KEY_OK_FLAG;
                }

                checkStaticSpriteCollision();

                if (y > this.judgeMap.tileSize * this.judgeMap.h) {
                    if (gm.menu.isVolOn) {
                        gm.beatPlayer.replay();
                    }
                    gm.gameState = MarioGameManage.GAMESTATE_REINIT;
                }
            }
            break;
        }
    }

    private void spriteDeadAction() {
        if (gm.menu.isVolOn) {
            gm.beatPlayer.replay();
        }
        if (mainSpriteState == SMALL_SPRITE) {
            mainSpriteState = DEAD_SPRITE;
            return;
        } else {
            mainSpriteState = BECOME_SMALL;
            isProtected = true;
            this.protectCnt = 0;
            this.protectInterval = 20;
            return;
        }
    }

    private void checkEnemyCollision() {
        int i;
        switch (mainSpriteState) {
            case SMALL_SPRITE:
                rect1.x = x + 2;
                rect1.y = y;
                rect1.dx = 12;
                rect1.dy = 16;
                break;
            case NORMAL_SPRITE:
            case FIRE_SPRITE:
                rect1.x = x + 6;
                rect1.y = y + 2;
                rect1.dx = 20;
                rect1.dy = 30;
                break;
        }
        rect2.dx = 14;
        rect2.dy = 14;
        for (i = gm.enemy.length - 1; i >= 0; --i) {
            if (!gm.enemy[i].isHidden) {
                if (gm.enemy[i].isCheck) {
                    rect2.x = gm.enemy[i].x;
                    rect2.y = gm.enemy[i].y;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        spriteDeadAction();
                        return;
                    }
                }
            }
        }

        for (i = gm.tortoise.length - 1; i >= 0; --i) {
            if (!gm.tortoise[i].isHidden) {
                if (gm.tortoise[i].isCheck) {
                    rect2.x = gm.tortoise[i].x;
                    rect2.y = gm.tortoise[i].y + 8;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        if (Math.abs(rect1.y + rect1.dy - (rect2.y)) > 6) {
                            spriteDeadAction();
                            return;
                        }
                    }
                }
            }
        }

    }

    private void mainSpriteDamage() {
        if (dy >= 0) {
            int i;
            rect1.y = y;
            rect2.dx = 16;
            rect2.dy = 16;
            if (mainSpriteState == SMALL_SPRITE) {
                rect1.x = x;
                rect1.dx = 16;
                rect1.dy = 16;
            } else {
                rect1.x = 8;
                rect1.dx = 20;
                rect1.dy = 32;
            }

            for (i = gm.enemy.length - 1; i >= 0; --i) {
                if (!gm.enemy[i].isHidden) {
                    if (gm.enemy[i].isCheck) {
                        if ((gm.enemy[i].y >= y + rect1.dy)
                                && (gm.enemy[i].y <= y + dy + rect1.dy)
                                && (x > gm.enemy[i].x - 13) && (x <= gm.enemy[i].x + 10)) {
                            gm.enemy[i].changeState();
                            if (gm.menu.isVolOn) {
                                gm.beatPlayer.replay();
                            }
                        }
                    }
                }
            }
            for (i = gm.tortoise.length - 1; i >= 0; --i) {
                if (!gm.tortoise[i].isHidden) {
                    if ((gm.tortoise[i].y + 8 >= y + rect1.dy)
                            && (gm.tortoise[i].y + 8 <= y + dy + rect1.dy)
                            && (x > gm.tortoise[i].x - 13) && (x <= gm.tortoise[i].x + 10)) {
                        dy = -8;
                        gm.tortoise[i].changeState();
                        if (gm.menu.isVolOn) {
                            gm.beatPlayer.replay();
                        }
                    }
                }
            }
        }
    }

    private boolean checkStaticSpriteCollision() {
        int i;
        rect2.dx = 16;
        rect2.dy = 16;
        switch (mainSpriteState) {
            case SMALL_SPRITE:
                rect1.x = x + 2;
                rect1.y = y;
                rect1.dx = 14;
                rect1.dy = 16;
                break;
            default:
                rect1.x = x + 8;
                rect1.y = y + 1;
                rect1.dx = 16;
                rect1.dy = 31;
                break;
        }

        rect2.dx = 14;
        rect2.dy = 16;

        for (i = gm.mashRooms.length - 1; i >= 0; --i) {
            if (!gm.mashRooms[i].isHidden) {
                switch (mainSpriteState) {
                    case SMALL_SPRITE:
                        rect2.x = gm.mashRooms[i].x;
                        rect2.y = gm.mashRooms[i].y;
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            if (gm.menu.isVolOn) {
                                gm.beatPlayer.replay();
                            }
                            if (gm.mashRooms[i].frameCnt == 6) {
                                gm.mashRooms[i].isHidden = true;
                                mainSpriteState = GROW_SPRITE;
                                growCnt = 0;
                                return true;
                            } else if (gm.mashRooms[i].frameCnt == 8) {
                                // add a person
                                gm.mashRooms[i].isHidden = true;
                                return true;
                            }
                        }
                        break;
                    case NORMAL_SPRITE:
                    case FIRE_SPRITE:
                        rect2.x = gm.mashRooms[i].x;
                        rect2.y = gm.mashRooms[i].y;
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            if (gm.mashRooms[i].frameCnt == 8) {

                                if (gm.menu.isVolOn) {
                                    gm.beatPlayer.replay();
                                }
                                // add a person
                                gm.mashRooms[i].isHidden = true;
                                return true;
                            }
                        }
                        break;
                }
            }
        }

        rect2.dy = 10;
        rect2.dx = 10;
        for (i = gm.flowers.length - 1; i >= 0; --i) {
            if (!gm.flowers[i].isHidden) {
                rect2.x = gm.flowers[i].x + 3;
                rect2.y = gm.flowers[i].y + 2;
                switch (mainSpriteState) {
                    case SMALL_SPRITE:
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            if (gm.menu.isVolOn) {
                                gm.beatPlayer.replay();
                            }
                            gm.flowers[i].isHidden = true;
                            mainSpriteState = GROW_SPRITE;
                            return true;
                        }
                        break;
                    case NORMAL_SPRITE:
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            if (gm.menu.isVolOn) {
                                gm.beatPlayer.replay();
                            }
                            gm.flowers[i].isHidden = true;
                            mainSpriteState = BECOME_FIRE;
                            return true;
                        }
                        break;
                    case FIRE_SPRITE:
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            if (gm.menu.isVolOn) {
                                gm.beatPlayer.replay();
                            }
                            gm.flowers[i].isHidden = true;
                            mainSpriteState = FIRE_SPRITE;
                            return true;
                        }
                        break;
                }
            }
        }
        rect2.dx = 14;
        rect2.dy = 14;
        switch (gm.gameState) {
            case MarioGameManage.GAMESTATE_GAMELOOP:
                for (i = gm.staticGold.length - 1; i >= 0; --i) {
                    if (!gm.staticGold[i].isHidden) {
                        rect2.x = gm.staticGold[i].x;
                        rect2.y = gm.staticGold[i].y;
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            gm.staticGold[i].isHidden = true;
                            if (++gm.goldNum == 100) {
                                gm.goldNum = 0;
                            }
                            if (gm.menu.isVolOn) {
                                gm.beatPlayer.replay();
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    public void judgeKeyCode(int keyCode, int gameKeyCode) {
        switch (gameKeyCode) {
            case Canvas.UP:
                if (dy == 0) {
                    if ((((allAction & LPKeyMask.MASK_KEY_UP) == 0)
                            && ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) == 0))) {
                        allAction |= LPKeyMask.MASK_KEY_UP_FLAG | LPKeyMask.MASK_KEY_UP;
                        dy -= 18;
                    }
                }
                break;
            case Canvas.FIRE:
                if ((((allAction & LPKeyMask.MASK_KEY_OK) == 0)
                        && ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) == 0))) {
                    allAction |= LPKeyMask.MASK_KEY_OK | LPKeyMask.MASK_KEY_OK_FLAG;
                }
                break;
            case Canvas.LEFT:
                allAction |= LPKeyMask.MASK_KEY_LEFT;
                break;
            case Canvas.RIGHT:
                allAction |= LPKeyMask.MASK_KEY_RIGHT;
                break;
            default:
                switch (keyCode) {
                    case Canvas.KEY_NUM2:
                        if (dy == 0) {
                            if ((((allAction & LPKeyMask.MASK_KEY_UP) == 0)
                                    && ((allAction & LPKeyMask.MASK_KEY_UP_FLAG) == 0))) {
                                allAction |= LPKeyMask.MASK_KEY_UP_FLAG | LPKeyMask.MASK_KEY_UP;
                                dy -= 18;
                            }
                        }
                        break;
                    case Canvas.KEY_NUM4: //left
                        allAction |= LPKeyMask.MASK_KEY_LEFT;
                        break;
                    case Canvas.KEY_NUM6: //right
                        allAction |= LPKeyMask.MASK_KEY_RIGHT;
                        break;
                    case Canvas.KEY_NUM5:
                        if ((((allAction & LPKeyMask.MASK_KEY_OK) == 0)
                                && ((allAction & LPKeyMask.MASK_KEY_OK_FLAG) == 0))) {
                            allAction |= LPKeyMask.MASK_KEY_OK | LPKeyMask.MASK_KEY_OK_FLAG;
                        }
                        break;
                }
                break;
        }
    }

    public void freeKey(int keyCode, int gameKeyCode) {
        switch (gameKeyCode) {
            case Canvas.FIRE:
                allAction &= ~LPKeyMask.MASK_KEY_OK;
                break;
            case Canvas.UP:
                allAction &= ~LPKeyMask.MASK_KEY_UP;
                break;
            case Canvas.DOWN:
                allAction &= ~LPKeyMask.MASK_KEY_DOWN;
                break;
            case Canvas.LEFT:
                allAction &= ~LPKeyMask.MASK_KEY_LEFT;
                break;
            case Canvas.RIGHT:
                allAction &= ~LPKeyMask.MASK_KEY_RIGHT;
                break;
            default:
                switch (keyCode) {
                    case Canvas.KEY_NUM4: //left
                        allAction &= ~LPKeyMask.MASK_KEY_LEFT;
                        break;
                    case Canvas.KEY_NUM6:
                        allAction &= ~LPKeyMask.MASK_KEY_RIGHT; //right
                        break;
                    case Canvas.KEY_NUM2:
                        allAction &= ~LPKeyMask.MASK_KEY_UP; //jump
                        break;
                    case Canvas.KEY_NUM8:
                        allAction &= ~LPKeyMask.MASK_KEY_DOWN;
                        break;
                    case Canvas.KEY_NUM5:
                        allAction &= ~LPKeyMask.MASK_KEY_OK;
                        break;
                }
                break;
        }
    }

    private void walk() {
        frameCnt++;
        if (this.frameCnt > 3 + offset || this.frameCnt < 1 + offset) {
            this.frameCnt = 1 + offset;
        }
    }

    private boolean checkTileCollisionVertical() {
        if (mainSpriteState == SMALL_SPRITE) {
            // 16X16
            if (this.dy > 0) {
                isJumpOver = true;
                for (int i = (y + this.judgeMap.y + 15)
                        / this.judgeMap.tileSize + 1;
                        i <= (y + this.judgeMap.y + dy) / this.judgeMap.tileSize + 1; i++) {
                    if (i < 0 || i > judgeMap.h - 1) {
                        continue;
                    }
                    int xTile1 = (x + this.judgeMap.x + 2) / this.judgeMap.tileSize;
                    int xTile2 = (x + this.judgeMap.x + 14) / this.judgeMap.tileSize;
                    if (xTile2 > this.judgeMap.w - 1 || xTile1 < 0) {
                        return false;
                    }
                    if (((this.judgeMap.mapArray[i][xTile1] >> 8) & 0x02) == 0x02) {
                        dy = (i - 1) * this.judgeMap.tileSize - (y + this.judgeMap.y);
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                    if (((this.judgeMap.mapArray[i][xTile2] >> 8) & 0x02) == 0x02) {
                        dy = (i - 1) * this.judgeMap.tileSize - (y + this.judgeMap.y);
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                }
            } else if (dy < 0) {
                for (int i = (y + this.judgeMap.y + dy) / this.judgeMap.tileSize;
                        i <= (y + this.judgeMap.y) / this.judgeMap.tileSize; i++) {
                    if (i < 0 || i > judgeMap.h - 1) {
                        continue;
                    }
                    int xTile1 = (x + this.judgeMap.x + 2) / this.judgeMap.tileSize;
                    int xTile2 = (x + this.judgeMap.x + 14) / this.judgeMap.tileSize;
                    if (xTile2 > this.judgeMap.w - 1 || xTile1 < 0) {
                        return false;
                    }
                    if (((this.judgeMap.mapArray[i][xTile1] >> 8) & 0x08) == 0x08) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        if (isJumpOver) {
                            changeUpSpriteState();
                            isJumpOver = false;
                        }
                        return true;
                    }
                    if (((this.judgeMap.mapArray[i][xTile2] >> 8) & 0x08) == 0x08) {
                        dy = 0;
                        if (isJumpOver) {
                            changeUpSpriteState();
                            isJumpOver = false;
                        }
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }

                    if (((this.judgeMap.mapArray[i][xTile1] >> 8) & 0x01) == 0x01) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                    if (((this.judgeMap.mapArray[i][xTile2] >> 8) & 0x01) == 0x01) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                }
            } else {
                isJumpOver = true;
            }
        } else {
            //32 X 32
            if (this.dy > 0) {
                isJumpOver = true;
                for (int i = (y + this.judgeMap.y + 15)
                        / this.judgeMap.tileSize + 2;
                        i <= (y + this.judgeMap.y + dy) / this.judgeMap.tileSize + 2; i++) {
                    if (i < 0 || i > judgeMap.h - 1) {
                        continue;
                    }
                    int xTile1 = (x + this.judgeMap.x + 12) / this.judgeMap.tileSize;
                    int xTile2 = (x + this.judgeMap.x + 20) / this.judgeMap.tileSize;
                    if (xTile2 > this.judgeMap.w - 1 || xTile1 < 0) {
                        return false;
                    }

                    if (((this.judgeMap.mapArray[i][xTile1] >> 8) & 0x02) == 0x02) {
                        dy = (i - 2) * this.judgeMap.tileSize - (y + this.judgeMap.y);
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                    if (((this.judgeMap.mapArray[i][xTile2] >> 8) & 0x02) == 0x02) {
                        dy = (i - 2) * this.judgeMap.tileSize - (y + this.judgeMap.y);
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                }
            } else if (dy < 0) {
                for (int i = (y + this.judgeMap.y + dy) / this.judgeMap.tileSize;
                        i <= (y + this.judgeMap.y) / this.judgeMap.tileSize; i++) {
                    if (i < 0 || i > judgeMap.h - 1) {
                        continue;
                    }
                    int xTile1 = (x + this.judgeMap.x + 12) / this.judgeMap.tileSize;
                    int xTile2 = (x + this.judgeMap.x + 20) / this.judgeMap.tileSize;
                    if (xTile2 > this.judgeMap.w - 1 || xTile1 < 0) {
                        return false;
                    }
                    if (((this.judgeMap.mapArray[i][xTile1] >> 8) & 0x08) == 0x08) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        if (isJumpOver) {
                            judgeMap.mapArray[i][xTile1] = judgeMap.mapArray[0][0];
                            gm.createBrokenBrick(x + 10, y - 5, -5, -12);
                            gm.createBrokenBrick(x + 10, y - 5, 5, -12);
                            gm.createBrokenBrick(x + 10, y - 5, -5, -6);
                            gm.createBrokenBrick(x + 10, y - 5, 5, -6);
                            isJumpOver = false;
                        }

                        return true;
                    }
                    if (((this.judgeMap.mapArray[i][xTile2] >> 8) & 0x08) == 0x08) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        if (isJumpOver) {
                            judgeMap.mapArray[i][xTile2] = judgeMap.mapArray[0][0];
                            gm.createBrokenBrick(x + 10, y - 5, -5, -12);
                            gm.createBrokenBrick(x + 10, y - 5, 5, -12);
                            gm.createBrokenBrick(x + 10, y - 5, -5, -6);
                            gm.createBrokenBrick(x + 10, y - 5, 5, -6);
                            isJumpOver = false;
                        }
                        return true;
                    }

                    if (((this.judgeMap.mapArray[i][xTile1] >> 8) & 0x01) == 0x01) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                    if (((this.judgeMap.mapArray[i][xTile2] >> 8) & 0x01) == 0x01) {
                        dy = 0;
                        allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                        return true;
                    }
                }
            } else {
                isJumpOver = true;
            }
        }
        return false;
    }

    private boolean checkTileCollisionHorizon() {
        int i;
        if (mainSpriteState == SMALL_SPRITE) {
            rect1.y = this.y + this.judgeMap.y;
            rect1.dx = 16;
            rect1.dy = 16;

            rect2.dx = 16;
            rect2.dy = 16;

            rect1.x = x + judgeMap.x + dx;
            if (dx < 0) {
                i = (x + judgeMap.x + dx) / judgeMap.tileSize;
            } else {
                i = (x + judgeMap.x + dx) / judgeMap.tileSize + 1;
            }
            if (i < 0 || i > this.judgeMap.w - 1) {
                return false;
            }
            int yTile1 = (y + judgeMap.y) / judgeMap.tileSize;
            int yTile2 = (y + judgeMap.y) / judgeMap.tileSize + 1;
            if (yTile1 < 0 || yTile2 > judgeMap.h - 1) {
                return false;
            }
            if (dx > 0) {
                if (((this.judgeMap.mapArray[yTile1][i] >> 8) & 0x10) == 0x10) {
                    rect2.x = i * 16;
                    rect2.y = yTile1 * 16;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        return true;
                    }
                }
            }
            if (((this.judgeMap.mapArray[yTile1][i] >> 8) & 0x04) == 0x04) {
                rect2.x = i * 16;
                rect2.y = yTile1 * 16;
                if (LPRect.IntersectRect(rect1, rect2)) {
                    return true;
                }
            }
            if (((this.judgeMap.mapArray[yTile2][i] >> 8) & 0x04) == 0x04) {
                rect2.x = i * 16;
                rect2.y = yTile2 * 16;
                if (LPRect.IntersectRect(rect1, rect2)) {
                    return true;
                }
            }
        } else {
            rect1.y = this.y + this.judgeMap.y + 1;
            rect1.dx = 16;
            rect1.dy = 31;

            rect2.dx = 16;
            rect2.dy = 16;

            if (dx < 0) {
                rect1.x = x + judgeMap.x + 6 + dx;
                i = (x + judgeMap.x + 6 + dx) / judgeMap.tileSize;
            } else {
                rect1.x = x + judgeMap.x + 20 + dx;
                i = (x + judgeMap.x + 20 + dx) / judgeMap.tileSize;
            }
            if (i < 0 || i > this.judgeMap.w - 1) {
                return false;
            }
            int yTile1 = (y + judgeMap.y) / judgeMap.tileSize;
            int yTile2 = (y + judgeMap.y) / judgeMap.tileSize + 1;
            int yTile3 = (y + judgeMap.y) / judgeMap.tileSize + 2;
            if (yTile1 < 0 || yTile3 > judgeMap.h - 1) {
                return false;
            }

            if (dx > 0) {
                boolean isCheck = false;;
                if (((this.judgeMap.mapArray[yTile1][i] >> 8) & 0x10) == 0x10) {
                    rect2.x = i * 16;
                    rect2.y = yTile1 * 16;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        isCheck = true;
                    }
                }
                if (isCheck) {
                    if (((this.judgeMap.mapArray[yTile2][i] >> 8) & 0x10) == 0x10) {
                        rect2.x = i * 16;
                        rect2.y = yTile2 * 16;
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            return true;
                        }
                    }
                }
            }
            if (((this.judgeMap.mapArray[yTile1][i] >> 8) & 0x04) == 0x04) {
                rect2.x = i * 16;
                rect2.y = yTile1 * 16;
                if (LPRect.IntersectRect(rect1, rect2)) {
                    return true;
                }
            }
            if (((this.judgeMap.mapArray[yTile2][i] >> 8) & 0x04) == 0x04) {
                rect2.x = i * 16;
                rect2.y = yTile2 * 16;
                if (LPRect.IntersectRect(rect1, rect2)) {
                    return true;
                }
            }
            if (((this.judgeMap.mapArray[yTile3][i] >> 8) & 0x04) == 0x04) {
                rect2.x = i * 16;
                rect2.y = yTile3 * 16;
                if (LPRect.IntersectRect(rect1, rect2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void fire() {
        if (isLeft) {
            gm.createBullet(x + 16, y + 16, -(MOVE_SPEED + 4), 0);
        } else {
            gm.createBullet(x + 16, y + 16, (MOVE_SPEED + 4), 0);
        }
    }

    private boolean checkSpriteCollisionVertical() {
        int i;
        if (mainSpriteState == SMALL_SPRITE) {
            if (dy > 0) {
                isJumpOver = true;
                switch (gm.gameState) {
                    case MarioGameManage.GAMESTATE_GAMELOOP:
                        for (i = gm.brick.length - 1; i >= 0; --i) {
                            if (!gm.brick[i].isHidden) {
                                if (((y + 16) <= gm.brick[i].y)
                                        && ((y + dy + 16) >= gm.brick[i].y)
                                        && (x >= gm.brick[i].x - 13) && (x <= gm.brick[i].x + 10)) {
                                    dy = gm.brick[i].y - (y + 16);
                                    allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                    return true;
                                }
                            }
                        }
                        for (i = gm.stick.length - 1; i >= 0; --i) {
                            if (!gm.stick[i].isHidden) {
                                if (((y + 16) <= gm.stick[i].y)
                                        && ((y + dy + 16) >= gm.stick[i].y)
                                        && (x >= gm.stick[i].x - 13) && (x <= gm.stick[i].x + 42)) {
                                    gm.stick[i].isMainSpriteMove = true;
                                    gm.stick[i].isDown = true;
                                    dy = gm.stick[i].y - (y + 16);
                                    this.allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                    return true;
                                } else {
                                    gm.stick[i].isMainSpriteMove = false;
                                    gm.stick[i].isDown = false;
                                }
                            }
                        }
                        for (i = gm.bridge.length - 1; i >= 0; --i) {
                            if (!gm.bridge[i].isHidden) {
                                if (((y + 16) <= gm.bridge[i].y)
                                        && ((y + dy + 16) >= gm.bridge[i].y)
                                        && (x >= gm.bridge[i].x - 13) && (x <= gm.bridge[i].x + 10)) {
                                    gm.bridge[i].isDown = true;
                                    dy = gm.bridge[i].y - (y + 16);
                                    allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                    return true;
                                }
                            }
                        }
                        break;
                }
            } else if (dy < 0) {
                switch (gm.gameState) {
                    case MarioGameManage.GAMESTATE_GAMELOOP:
                        for (i = gm.brick.length - 1; i >= 0; --i) {
                            if (((y) >= gm.brick[i].y + 16)
                                    && ((y + dy) <= gm.brick[i].y + 16)
                                    && (x >= gm.brick[i].x - 13) && (x <= gm.brick[i].x + 10)) {
                                dy = gm.brick[i].y + 16 - y;
                                if (isJumpOver) {
                                    if (gm.brick[i].frameCnt != 0) {
                                        changeUpSpriteState();
                                    }
                                    gm.brick[i].createGem(gm);
                                    isJumpOver = false;
                                }
                                allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                return true;
                            }
                        }
                        break;
                }
            } else {
                isJumpOver = true;
            }
        } else {
            if (dy > 0) {
                isJumpOver = true;
                switch (gm.gameState) {
                    case MarioGameManage.GAMESTATE_GAMELOOP:
                        for (i = gm.brick.length - 1; i >= 0; --i) {
                            if (!gm.brick[i].isHidden) {
                                if (((y + 32) <= gm.brick[i].y)
                                        && ((y + dy + 32) >= gm.brick[i].y)
                                        && (x >= gm.brick[i].x - 20) && (x <= gm.brick[i].x + 6)) {
                                    dy = gm.brick[i].y - (y + 32);
                                    allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                    return true;
                                }
                            }
                        }
                        for (i = gm.stick.length - 1; i >= 0; --i) {
                            if (!gm.stick[i].isHidden) {
                                if (((y + 32) <= gm.stick[i].y)
                                        && ((y + dy + 32) >= gm.stick[i].y)
                                        && (x >= gm.stick[i].x - 20) && (x <= gm.stick[i].x + 38)) {
                                    gm.stick[i].isMainSpriteMove = true;
                                    dy = gm.stick[i].y - (y + 32);
                                    this.allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                    return true;
                                } else {
                                    gm.stick[i].isMainSpriteMove = false;
                                }
                            }
                        }
                        for (i = gm.bridge.length - 1; i >= 0; --i) {
                            if (!gm.bridge[i].isHidden) {
                                if (((y + 32) <= gm.bridge[i].y)
                                        && ((y + dy + 32) >= gm.bridge[i].y)
                                        && (x >= gm.bridge[i].x - 20) && (x <= gm.bridge[i].x + 6)) {
                                    gm.bridge[i].isDown = true;
                                    dy = gm.bridge[i].y - (y + 32);
                                    allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                    return true;
                                }
                            }
                        }
                        break;
                }
            } else if (dy < 0) {
                switch (gm.gameState) {
                    case MarioGameManage.GAMESTATE_GAMELOOP:
                        for (i = gm.brick.length - 1; i >= 0; --i) {
                            if (((y) >= gm.brick[i].y + 16)
                                    && ((y + dy) <= gm.brick[i].y + 16)
                                    && (x >= gm.brick[i].x - 20) && (x <= gm.brick[i].x + 6)) {
                                dy = gm.brick[i].y + 16 - y;
                                if (isJumpOver) {
                                    gm.brick[i].createGem(gm);
                                    isJumpOver = false;
                                }
                                allAction &= ~LPKeyMask.MASK_KEY_UP_FLAG;
                                return true;
                            }
                        }
                        break;
                }
            } else {
                isJumpOver = true;
            }
        }
        return false;
    }

    private boolean checkSpriteCollisionHorizon() {
        if (gm.gameState == MarioGameManage.GAMESTATE_GAMELOOP) {
            int i;
            if (mainSpriteState == SMALL_SPRITE) {
                rect1.x = x;
                rect1.y = y;
                rect1.dx = 16;
                rect1.dy = 16;
            } else {
                rect1.x = x + 6;
                rect1.y = y;
                rect1.dx = 16;
                rect1.dy = 32;
            }
            for (i = gm.tortoise.length - 1; i >= 0; --i) {
                if (!gm.tortoise[i].isHidden) {
                    if (gm.tortoise[i].isPushable) {
                        rect2.x = gm.tortoise[i].x;
                        rect2.y = gm.tortoise[i].y + 8;
                        rect2.dx = 16;
                        rect2.dy = 16;
                        if (LPRect.IntersectRect(rect1, rect2)) {
                            gm.tortoise[i].changeState();
                        }
                    }
                }
            }
            rect1.x += dx;
            for (i = gm.brick.length - 1; i >= 0; --i) {
                if (!gm.brick[i].isHidden) {
                    rect2.x = gm.brick[i].x;
                    rect2.y = gm.brick[i].y;
                    rect2.dx = 16;
                    rect2.dy = 16;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkBulletDamage() {
        int i, j;
        rect1.dx = 8;
        rect1.dy = 8;
        rect2.dx = 16;
        rect2.dy = 16;
        for (i = gm.bullet.length - 1; i >= 0; --i) {
            label_1:
            if (!gm.bullet[i].isHidden) {
                rect1.x = gm.bullet[i].x;
                rect1.y = gm.bullet[i].y;
                for (j = gm.enemy.length - 1; j >= 0; --j) {
                    if (!gm.enemy[j].isHidden) {
                        if (gm.enemy[j].isCheck) {
                            rect2.x = gm.enemy[j].x;
                            rect2.y = gm.enemy[j].y;
                            if (LPRect.IntersectRect(rect1, rect2)) {
                                gm.enemy[j].changeDirection();
                                gm.bullet[i].isHidden = true;
                                break label_1;
                            }
                        }
                    }
                }
                for (j = gm.tortoise.length - 1; j >= 0; --j) {
                    if (!gm.tortoise[j].isHidden) {
                        if (gm.tortoise[j].isCheck) {
                            rect2.x = gm.tortoise[j].x;
                            rect2.y = gm.tortoise[j].y + 8;
                            if (LPRect.IntersectRect(rect1, rect2)) {
                                gm.tortoise[j].changeDirection();
                                gm.bullet[i].isHidden = true;
                                break label_1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void init() {
        x = 0;
        y = 0;
        dy = 0;
        dx = 0;
        bgdx = 0;
        bgdy = 0;
        isLeft = false;
        allAction = 0;
        mainSpriteState = SMALL_SPRITE;
        distance = 0;
        isJumpOver = true;
        isProtected = false;
        isHidden = false;
    }

    private void changeUpSpriteState() {
        int i;
        rect1.x = x;
        rect1.y = y - 20;

        rect1.dx = 16;
        rect1.dy = 16;
        rect2.dx = 16;
        rect2.dy = 16;
        for (i = gm.mashRooms.length - 1; i >= 0; --i) {
            if (!gm.mashRooms[i].isHidden) {
                rect2.x = gm.mashRooms[i].x;
                rect2.y = gm.mashRooms[i].y;
                if (LPRect.IntersectRect(rect1, rect2)) {
                    gm.mashRooms[i].changeDirection();
                }
            }
        }
        for (i = gm.enemy.length - 1; i >= 0; --i) {
            if (!gm.enemy[i].isHidden) {
                if (gm.enemy[i].isCheck) {
                    rect2.x = gm.enemy[i].x;
                    rect2.y = gm.enemy[i].y;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        gm.enemy[i].changeDirection();
                    }
                }
            }
        }
        for (i = gm.tortoise.length - 1; i >= 0; --i) {
            if (!gm.tortoise[i].isHidden) {
                if (gm.tortoise[i].isCheck) {
                    rect2.x = gm.tortoise[i].x;
                    rect2.y = gm.tortoise[i].y;
                    if (LPRect.IntersectRect(rect1, rect2)) {
                        gm.tortoise[i].changeDirection();
                    }
                }
            }
        }
    }

    public void readData(DataInputStream dis)
            throws java.io.IOException {
        super.readData(dis);
        dx = dis.readInt();
        dy = dis.readInt();
        bgdx = dis.readInt();
        bgdy = dis.readInt();
        growCnt = dis.readInt();
        //allAction = dis.readInt();
        mainSpriteState = dis.readInt();
        becomeCnt = dis.readInt();
        protectInterval = dis.readInt();
        protectCnt = dis.readInt();
        isLeft = dis.readBoolean();
        isProtected = dis.readBoolean();
        deadCnt = dis.readInt();
        distance = dis.readInt();
    }

    public void writeData(DataOutputStream dos)
            throws java.io.IOException {
        super.writeData(dos);
        dos.writeInt(dx);
        dos.writeInt(dy);
        dos.writeInt(bgdx);
        dos.writeInt(bgdy);
        dos.writeInt(growCnt);
        dos.writeInt(mainSpriteState);
        dos.writeInt(becomeCnt);
        dos.writeInt(protectInterval);
        dos.writeInt(protectCnt);
        dos.writeBoolean(isLeft);
        dos.writeBoolean(isProtected);
        dos.writeInt(deadCnt);
        dos.writeInt(distance);
    }
// property
//pubilc
    public int dx;
    public int dy;
    public int bgdx;
    public int bgdy;
    public LPMaps judgeMap;
    public int growCnt;
    public int allAction;
    public int mainSpriteState;
    public int becomeCnt;
//private
    private LPRect rect1 = new LPRect();
    private LPRect rect2 = new LPRect();
    private boolean isJumpOver;
    private int protectInterval;
    private int protectCnt;
    private MarioGameManage gm;
    private boolean isLeft;
    private boolean isProtected;
    private int deadCnt;
    private int distance;
    //final
    private static final int MOVE_SPEED = 6;
    // main sprite's state
    public static final int SMALL_SPRITE = 0x01;
    public static final int NORMAL_SPRITE = 0x02;
    public static final int FIRE_SPRITE = 0x04;
    public static final int GROW_SPRITE = 0x08;
    public static final int BECOME_FIRE = 0x10;
    public static final int DEAD_SPRITE = 0x20;
    public static final int BECOME_SMALL = 0x40;
}
