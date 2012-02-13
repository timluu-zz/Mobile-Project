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

import lipeng.LPAudioPlayer;
import lipeng.LPIGameManager;
import lipeng.LPMenu;
import lipeng.LPRect;

public class MarioGameManage
        implements LPIGameManager {
//method

    public MarioGameManage(MarioGameCanvas canvas) {
        this.canvas = canvas;
        mainSprite = new MarioMainSprite(this, canvas.map);
        int i;
        brokenBrick = new MarioBrokenBrick[16];
        for (i = brokenBrick.length - 1; i >= 0; --i) {
            brokenBrick[i] = new MarioBrokenBrick();
        }

        flowers = new Flower[2];
        for (i = flowers.length - 1; i >= 0; --i) {
            flowers[i] = new Flower();
        }

        mashRooms = new Mashroom[3];
        mashRooms[mashRooms.length - 1] = new MashroomPeople(this);
        for (i = mashRooms.length - 2; i >= 0; --i) {
            mashRooms[i] = new Mashroom(this);
        }
        gold = new Gold[4];
        for (i = gold.length - 1; i >= 0; --i) {
            gold[i] = new Gold();
        }
        bullet = new MarioBullet[6];
        for (i = bullet.length - 1; i >= 0; --i) {
            bullet[i] = new MarioBullet(this);
        }

    }

    public void menuBeginNewGame() {
        //��ʼ��Ϸ
        gameState = GAMESTATE_GAMELOOP;
        initLevel_1_1();
        mainSprite.allAction = 0;
        mainSprite.dy = 0;
        mainSprite.judgeMap = canvas.map;
        mapObjectInitOffset(mainSprite.judgeMap.x, mainSprite.judgeMap.y);
    }

    public void menuLoadGame() {
        canvas.loadSaveState();
        if (!canvas.isSaveState) {
            canvas.addCommand(canvas.mainMenu);
            if (menu.isVolOn) {
                bgPlayer.stop();
                bgPlayer.close();
                bgPlayer = null;
                System.gc();
                beatPlayer = new LPAudioPlayer("beat.mmf", "application/vnd.smaf");
            }
        } else {
            canvas.removeCommand(canvas.back);
            canvas.addCommand(canvas.mainMenu);
            canvas.isSaveState = false;
        }
        gameState = GAMESTATE_GAMELOOP;
    }

    public void menuAudio() {
        if (!canvas.isSaveState) {
            if (menu.isVolOn) {
                if (beatPlayer != null) {
                    beatPlayer.close();
                    beatPlayer = null;
                    System.gc();
                }

                bgPlayer = new LPAudioPlayer("bg.mid", "audio/midi", true);
                bgPlayer.setLoop();
                bgPlayer.play();
            } else {
                bgPlayer.stop();
                bgPlayer.close();
                bgPlayer = null;
                System.gc();
            }
        } else {
            if (menu.isVolOn) {
                if (beatPlayer == null) {
                    beatPlayer = new LPAudioPlayer("beat.mmf", "application/vnd.smaf");
                }
            }
        }
    }

    public void menuAbout() {
    }

    public void menuExitGame() {
        canvas.midlet.exitMIDlet();
    }

    public void reInit() {
        int i;
        if (reInitCnt == 0) {
            for (i = this.brokenBrick.length - 1; i >= 0; --i) {
                brokenBrick[i].isHidden = true;
            }
            for (i = bullet.length - 1; i >= 0; --i) {
                bullet[i].isHidden = true;
            }
            for (i = mashRooms.length - 1; i >= 0; --i) {
                mashRooms[i].isHidden = true;
            }
            for (i = flowers.length - 1; i >= 0; --i) {
                flowers[i].isHidden = true;
            }
            timerCnt = 0;
            mainSprite.init();
            canvas.loadGameResource(1);
            mainSprite.judgeMap = canvas.map;
            mainSprite.judgeMap.x = 0;
            mainSprite.judgeMap.y = 0;
            reInitLevel_1_1();
            mapObjectInitOffset(mainSprite.judgeMap.x, mainSprite.judgeMap.y);
        }
        if (++reInitCnt == 10) {
            reInitCnt = 0;
            gameState = GAMESTATE_GAMELOOP;
        }
    }

    public void mapObjectInitOffset(int offsetX, int offsetY) {
        int i;
        for (i = brick.length - 1; i >= 0; --i) {
            brick[i].x -= offsetX;
            brick[i].y -= offsetY;
        }
        for (i = enemy.length - 1; i >= 0; --i) {
            enemy[i].x -= offsetX;
            enemy[i].y -= offsetY;
        }
        for (i = tortoise.length - 1; i >= 0; --i) {
            tortoise[i].x -= offsetX;
            tortoise[i].y -= offsetY;
        }
        for (i = staticGold.length - 1; i >= 0; --i) {
            staticGold[i].x -= offsetX;
            staticGold[i].y -= offsetY;
        }
        for (i = stick.length - 1; i >= 0; --i) {
            stick[i].x -= offsetX;
            stick[i].y -= offsetY;
        }

        for (i = bridge.length - 1; i >= 0; --i) {
            bridge[i].x -= offsetX;
            bridge[i].y -= offsetY;
        }

    }

    public void reInitBgMedia() {
        if (menu.isVolOn) {
            beatPlayer.close();
            beatPlayer = null;
            System.gc();
            bgPlayer = new LPAudioPlayer("bg.mid", "audio/midi", true);
            bgPlayer.setLoop();
            bgPlayer.play();
        }
    }

    private void checkEnemyCollision() {
        int i;
        int j;
        rect1.dx = 16;
        rect1.dy = 16;
        rect2.dx = 16;
        rect2.dy = 16;
        for (i = tortoise.length - 1; i >= 0; --i) {
            if (!tortoise[i].isHidden) {
                if (tortoise[i].state == 2) {
                    rect1.x = tortoise[i].x;
                    rect1.y = tortoise[i].y + 8;
                    for (j = enemy.length - 1; j >= 0; --j) {
                        if (!enemy[j].isHidden) {
                            if (enemy[j].isCheck) {
                                rect2.x = enemy[j].x;
                                rect2.y = enemy[j].y;
                                if (LPRect.IntersectRect(rect1, rect2)) {
                                    enemy[j].changeDirection();
                                }
                            }
                        }
                    }
                    for (j = tortoise.length - 1; j >= 0; --j) {
                        if (i != j) {
                            if (!tortoise[j].isHidden) {
                                if (tortoise[j].isCheck) {
                                    rect2.x = tortoise[j].x;
                                    rect2.y = tortoise[j].y + 8;
                                    if (LPRect.IntersectRect(rect1, rect2)) {
                                        tortoise[j].changeDirection();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void action() {
        int i;
        mainSprite.action();
        objectOffset();
        checkEnemyCollision();
        for (i = brokenBrick.length - 1; i >= 0; --i) {
            brokenBrick[i].action();
        }
        for (i = brick.length - 1; i >= 0; --i) {
            brick[i].action();
        }
        for (i = mashRooms.length - 1; i >= 0; --i) {
            if (!mashRooms[i].isHidden) {
                mashRooms[i].action();
            }
        }
        for (i = flowers.length - 1; i >= 0; --i) {
            if (!flowers[i].isHidden) {
                flowers[i].action();
            }
        }
        for (i = gold.length - 1; i >= 0; --i) {
            if (!gold[i].isHidden) {
                gold[i].action();
            }
        }
        for (i = enemy.length - 1; i >= 0; --i) {
            if (!enemy[i].isHidden) {
                enemy[i].action();
            }
        }
        for (i = tortoise.length - 1; i >= 0; --i) {
            if (!tortoise[i].isHidden) {
                tortoise[i].action();
            }
        }
        for (i = staticGold.length - 1; i >= 0; --i) {
            if (!staticGold[i].isHidden) {
                staticGold[i].action();
            }
        }
        for (i = stick.length - 1; i >= 0; --i) {
            if (!stick[i].isHidden) {
                stick[i].action();
            }
        }
        for (i = bullet.length - 1; i >= 0; --i) {
            if (!bullet[i].isHidden) {
                bullet[i].action();
            }
        }

        for (i = bridge.length - 1; i >= 0; --i) {
            if (!bridge[i].isHidden) {
                bridge[i].action();
            }
        }
        if (canvas.isPressStar) {
            canvas.saveCurrentState();
            canvas.isPressStar = false;
        }
    }

    public void judgeKeyCode(int keyCode, int gameKeyCode) {
        menu.judgeKeyCode(keyCode, gameKeyCode);
        if (gameState == GAMESTATE_GAMELOOP) {
            mainSprite.judgeKeyCode(keyCode, gameKeyCode);
        }
    }

    public void freeKey(int keyCode, int gameKeyCode) {
        menu.freeKey(keyCode, gameKeyCode);
        if (gameState == GAMESTATE_GAMELOOP) {
            mainSprite.freeKey(keyCode, gameKeyCode);
        }
    }

    private void objectOffset() {
        int i;
        for (i = brokenBrick.length - 1; i >= 0; --i) {
            if (!brokenBrick[i].isHidden) {
                brokenBrick[i].x += mainSprite.bgdx;
                brokenBrick[i].y += mainSprite.bgdy;
            }
        }
        for (i = brick.length - 1; i >= 0; --i) {
            brick[i].x += mainSprite.bgdx;
            brick[i].y += mainSprite.bgdy;
        }

        for (i = flowers.length - 1; i >= 0; --i) {
            if (!flowers[i].isHidden) {
                flowers[i].x += mainSprite.bgdx;
                flowers[i].y += mainSprite.bgdy;
            }
        }

        for (i = mashRooms.length - 1; i >= 0; --i) {
            if (!mashRooms[i].isHidden) {
                mashRooms[i].x += mainSprite.bgdx;
                mashRooms[i].y += mainSprite.bgdy;
            }
        }
        for (i = gold.length - 1; i >= 0; --i) {
            if (!gold[i].isHidden) {
                gold[i].x += mainSprite.bgdx;
                gold[i].y += mainSprite.bgdy;
            }
        }

        for (i = enemy.length - 1; i >= 0; --i) {
            if (!enemy[i].isHidden) {
                enemy[i].x += mainSprite.bgdx;
                enemy[i].y += mainSprite.bgdy;
            }
        }

        for (i = tortoise.length - 1; i >= 0; --i) {
            if (!tortoise[i].isHidden) {
                tortoise[i].x += mainSprite.bgdx;
                tortoise[i].y += mainSprite.bgdy;
            }
        }

        for (i = staticGold.length - 1; i >= 0; --i) {
            if (!staticGold[i].isHidden) {
                staticGold[i].x += mainSprite.bgdx;
                staticGold[i].y += mainSprite.bgdy;
            }
        }
        for (i = stick.length - 1; i >= 0; --i) {
            stick[i].x += mainSprite.bgdx;
            stick[i].y += mainSprite.bgdy;
        }
        for (i = bullet.length - 1; i >= 0; --i) {
            bullet[i].x += mainSprite.bgdx;
            bullet[i].y += mainSprite.bgdy;
        }
        for (i = bridge.length - 1; i >= 0; --i) {
            if (!bridge[i].isHidden) {
                bridge[i].x += mainSprite.bgdx;
                bridge[i].y += mainSprite.bgdy;
            }
        }

    }

    public void reInitLevel_1_1() {
        int i;
        mainSprite.x = 0;
        reInitCreateObject();
        if (mainSprite.mainSpriteState == MarioMainSprite.SMALL_SPRITE) {
            mainSprite.y = 320 - 48;
        } else {
            mainSprite.y = 320 - 64;
        }

        brick[0].reInit(16 * 17, 16 * 10);
        brick[1].reInit(16 * 19, 16 * 10);
        brick[2].reInit(16 * 11, 16 * 10);
        brick[3].reInit(16 * 18, 16 * 6);
        brick[4].reInit(16 * 57, 16 * 10);
        brick[5].reInit(16 * 70, 16 * 10);
        brick[6].reInit(16 * 83, 16 * 6);
        brick[7].reInit(16 * 84, 16 * 6);
        brick[8].reInit(16 * 93, 16 * 10);
        brick[9].reInit(16 * 96, 16 * 10);
        brick[10].reInit(16 * 104, 16 * 6);

        tortoise[0].reInit(16 * 102, 16 * 13 - 8);


    }

    public void initLevel_1_1() {
        int i;
        reInitCreateObject();
        mainSprite.x = 0;
        if (mainSprite.mainSpriteState == MarioMainSprite.SMALL_SPRITE) {
            mainSprite.y = 320 - 48;
        } else {
            mainSprite.y = 320 - 64;
        }

        brick = new MarioBrick[8];
        brick[0] = new MarioBrickGemQuestion(16 * 12, 16 * 15);
        brick[1] = new MarioBrickGoldQuestion(16 * 13, 16 * 12);
        brick[2] = new MarioBrickGoldQuestion(16 * 17, 16 * 12);
        brick[3] = new MarioBrickGold(16 * 19, 16 * 15);
        brick[4] = new MarioBrickGold(16 * 28, 16 * 14);
        brick[5] = new MarioBrickGemQuestion(16 * 29, 16 * 14);
        brick[6] = new MarioBrickGoldQuestion(16 * 30, 16 * 14);
        brick[7] = new MarioBrickGemHidden(16 * 22, 16 * 6);
        enemy = new MarioEnemySprite[0];

        tortoise = new EnemyTortoise[1];
        tortoise[0] = new EnemyTortoise(16 * 26, 16 * 13 - 8, this);

        staticGold = new StaticGold[0];

        stick = new Stick[0];

        bridge = new FallBridge[0];
    }

    public boolean createGold(int x, int y) {
        int i;
        for (i = gold.length - 1; i >= 0; --i) {
            if (gold[i].isHidden) {
                gold[i].reInit(x, y);
                return true;
            }
        }
        return false;
    }

    public void createGemPeople(int x, int y) {
        mashRooms[mashRooms.length - 1].reInit(x, y);
    }

    public void createGem(int x, int y) {
        int i;
        switch (mainSprite.mainSpriteState) {
            case MarioMainSprite.SMALL_SPRITE:
                for (i = mashRooms.length - 2; i >= 0; --i) {
                    if (mashRooms[i].isHidden) {
                        mashRooms[i].reInit(x, y);
                        return;
                    }
                }
                break;
            case MarioMainSprite.NORMAL_SPRITE:
            case MarioMainSprite.FIRE_SPRITE:
                for (i = flowers.length - 1; i >= 0; --i) {
                    if (flowers[i].isHidden) {
                        flowers[i].reInit(x, y);
                        return;
                    }
                }
                break;
        }
    }

    public void initGameState() {
        timerCnt = 0;
        goldNum = 0;
        this.mainSprite.init();
    }

    public void createBrokenBrick(int x, int y, int xSpeed, int ySpeed) {
        int i;
        for (i = brokenBrick.length - 1; i >= 0; --i) {
            if (brokenBrick[i].isHidden) {
                brokenBrick[i].init(x, y, xSpeed, ySpeed);
                return;
            }
        }
    }

    public void reInitCreateObject() {
        int i;
        for (i = brokenBrick.length - 1; i >= 0; --i) {
            brokenBrick[i].isHidden = true;
        }
        for (i = bullet.length - 1; i >= 0; --i) {
            bullet[i].isHidden = true;
        }
        for (i = mashRooms.length - 1; i >= 0; --i) {
            mashRooms[i].isHidden = true;
        }
        for (i = this.flowers.length - 1; i >= 0; --i) {
            flowers[i].isHidden = true;
        }
    }

    public void createBullet(int x, int y, int xSpeed, int ySpeed) {
        int i;
        for (i = bullet.length - 1; i >= 0; --i) {
            if (bullet[i].isHidden) {
                bullet[i].initBullet(x, y, xSpeed, ySpeed);
                return;
            }
        }
    }
//property
    //public game objects
    public MarioBrokenBrick[] brokenBrick;
    public MarioBullet[] bullet;
    public MarioBrick[] brick;
    public Gold[] gold;
    public Mashroom[] mashRooms;
    public Flower[] flowers;
    public MarioEnemySprite[] enemy;
    public EnemyTortoise[] tortoise;
    public StaticGold[] staticGold;
    public Stick[] stick;
    public FallBridge[] bridge;
    public StaticGold[] ugGold;
    public MarioBrick[] ugBrick;
    public LPMenu menu = new LPMenu(this);
    public MarioMainSprite mainSprite;
    public MarioGameCanvas canvas;
// media
    public LPAudioPlayer bgPlayer = new LPAudioPlayer("bg.mid", "audio/midi");
    public LPAudioPlayer beatPlayer = new LPAudioPlayer("beat.mmf", "application/vnd.smaf");
// game logic control
    public int gameState;
    public int goldNum;
    public int time;
    private int timerCnt;
    private int reInitCnt;
    private LPRect rect1 = new LPRect();
    private LPRect rect2 = new LPRect();
// final
    //game state
    public static final int GAMESTATE_MENU = 1;
    public static final int GAMESTATE_GAMELOOP = 2;
    public static final int GAMESTATE_PAUSE = 3;
    public static final int GAMESTATE_REINIT = 6;
}
