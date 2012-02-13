package examples;
import jgame.*;
import jgame.platform.*;
/** A Gauntlet clone with generated levels. */
public class DungeonsOfHack extends StdGame {
// object cids: 1=player 2=monster 4=bullet 8=monsterbullet
	public static final int WALL_T=1;
	public static final int SHWALL_T=2; // shootable wall
	public static final int DOOR_T=4;
	public static final int PLAYER_T=16;
	public static final int MONSTER_T=32;
	public static final int GEN_T=64; // generator
	public static final int BONUS_T=128;
	public static final int KEY_T=256;
	public static final int HEALTH_T=512;
	public static final int PLAYERBLOCK_T =
		WALL_T|SHWALL_T|DOOR_T|GEN_T;
	public static final int MONSTERBLOCK_T =
		WALL_T|SHWALL_T|DOOR_T|BONUS_T|MONSTER_T|GEN_T|BONUS_T|KEY_T|HEALTH_T;
	public static final int BULLETBLOCK_T =
		WALL_T|DOOR_T|BONUS_T|BONUS_T|KEY_T|HEALTH_T;
	public static void main(String [] args) {
		if (args.length<1) {
			System.out.println(
			"Please supply \"scroll\" or \"noscroll\" as first argument.");
			System.exit(0);
		}
		boolean do_scroll=false;
		if (args[0].equals("scroll")) {
			do_scroll=true;
		}
		new DungeonsOfHack(parseSizeArgs(args,1),do_scroll);
	}
	boolean do_scroll=false;
	public DungeonsOfHack() { initEngineApplet(); }
	public DungeonsOfHack(JGPoint size,boolean do_scroll) {
		this.do_scroll=do_scroll;
		initEngine(size.x,size.y);
	}
	public void initCanvas() {
		if (isApplet()) do_scroll = getParameter("scrolling")!=null;
		if (isMidlet()) do_scroll = true;
		if (isMidlet()) {
			setCanvasSettings(18,18,10,10,null,null,null);
			setScalingPreferences(3.0/4.0,4.0/3.0, 0,4,0,4);
		} else if (do_scroll) {
			setCanvasSettings(20,15,10,10,null,null,null);
		} else {
			setCanvasSettings(60,40,10,10,null,null,null);
		}
	}
	public void initGame() {
		defineMedia("dungeons_of_hack.tbl");
		setVideoSyncedUpdate(true);
		if (isMidlet()) {
			setFrameRate(10,1);
			setGameSpeed(1.8);
		} else {
			setFrameRate(30,4);
		}
		startgame_ticks=1000; // wait for awhile so player can look around
		gameover_ingame=true;
		title_font = new JGFont("Helvetica",JGFont.BOLD,17);
		setPFSize(60,40);
	}
	JGObject player=null;
	int keys=0,health=0, kills=0, gold_left=0;
	public void initNewGame(int level_selected) {
		health=10;
		keys=0;
		level=0;
		kills=0;
	}
	public void defineLevel() {
		fillBG("");
		removeObjects(null,0);
		player=new Player(10,10,5,key_up,key_down,key_left,key_right);
		setViewOffset((int)player.x-8,(int)player.y-8,true);
		// define segment walls
		int [] walls_x, walls_y;
		int [] [] cells; // indicates cells' exits
		//double openness = 0.2;
		double door_rate = random(0.0,1.0);
		double shootablewall_rate = random(0.0,0.6);
		int nr_keys,nr_bonuses,nr_health,nr_demongen,nr_mushroomgen,nr_turrets;
		int turret_firerate,mushroomrate,demonrate,demon_firerate;
		if (random(0,1) > 0.42) {
			// open dungeon
			walls_x = defineWallPos(2,8,pfTilesX()-1,true);
			walls_y = defineWallPos(2,8,pfTilesY()-1,true);
			cells = defineCells(walls_x.length+1,walls_y.length+1,
				random(0.0,0.1) );
			nr_keys = 4;
			nr_bonuses=10;
			nr_health=5;
			nr_demongen=5;
			nr_mushroomgen=10;
			nr_turrets=10;
			mushroomrate=80;
			demonrate=100;
			turret_firerate=200;
			demon_firerate=250;
		} else {
			// maze type dungeon; about 2.5 times more stuff
			walls_x = defineWallPos(9,16,pfTilesX()-1,false);
			walls_y = defineWallPos(9,16,pfTilesY()-1,false);
			cells = defineCells(walls_x.length+1,walls_y.length+1,
				random(0.0,0.15) );
			nr_keys = 10;
			nr_bonuses=25;
			nr_health=12;
			nr_demongen=12;
			nr_mushroomgen=25;
			nr_turrets=25;
			mushroomrate=140;
			demonrate=200;
			// fire faster because it's easier to hide
			turret_firerate=120;
			demon_firerate=180;
		}
		// base walls
		for (int x=0; x<pfTilesX(); x++) {
			// outer walls
			setTile(x,0,"#");
			setTile(x,pfTilesY()-1,"#");
		}
		for (int y=0; y<pfTilesY(); y++) {
			// outer walls
			setTile(0,y,"#");
			setTile(pfTilesX()-1,y,"#");
		}
		// segment boundaries, that is, the bottom and right walls of all
		// segments
		for (int y=0; y<cells[0].length; y++) {
			for (int x=0; x<cells.length; x++) {
				int wx1 = x==0 ? 0 : walls_x[x-1];
				int wx2 = x==walls_x.length ? pfTilesX()-1 : walls_x[x];
				int wy1 = y==0 ? 0 : walls_y[y-1];
				int wy2 = y==walls_y.length ? pfTilesY()-1 : walls_y[y];
				// the corners
				setTile(wx1,wy1,"#");
				setTile(wx1,wy2,"#");
				setTile(wx2,wy1,"#");
				setTile(wx2,wy2,"#");
				// right wall
				if (x < walls_x.length) {
					String tile=random(0,1)<door_rate ? "|" :
					            ( random(0,1)<shootablewall_rate ? "%" : "#" );
					if (!and(cells[x][y],8)) {
						for (int i=wy1+1; i<wy2; i++) {
							setTile(wx2,i,tile);
						}
					}
				}
				// bottom wall
				if (y < walls_y.length) {
					String tile=random(0,1)<door_rate ? "-" :
					            ( random(0,1)<shootablewall_rate ? "%" : "#" );
					if (!and(cells[x][y],2)) {
						for (int i=wx1+1; i<wx2; i++) {
							setTile(i,wy2,tile);
						}
					}
				}
			}
		}
		// place objects
		for (int i=0; i<nr_bonuses; i++) setTile(getFreeTile(),"o");
		for (int i=0; i<nr_keys; i++) setTile(getFreeTile(),"k");
		for (int i=0; i<nr_health; i++) setTile(getFreeTile(),"h");
		// place monsters
		for (int i=0; i<nr_mushroomgen; i++) {
			JGPoint pos = getTileCoord(getFreeTile());
			new Generator(pos.x,pos.y,
				(int)(random(mushroomrate,mushroomrate*1.5)/getGameSpeed()), 0);
		}
		for (int i=0; i<nr_demongen; i++) {
			JGPoint pos = getTileCoord(getFreeTile());
			new Generator(pos.x,pos.y, 
				(int)(random(demonrate,demonrate*1.5)/getGameSpeed()),
				(int)(demon_firerate/getGameSpeed()));
		}
		for (int i=0; i<nr_turrets; i++) {
			JGPoint pos = getTileCoord(getFreeTile());
			new Turret(pos.x,pos.y, 
				(int)(random(turret_firerate/getGameSpeed(),
					turret_firerate*2.5)/getGameSpeed()));
		}
		gold_left = countTiles(BONUS_T);
	}
	public JGPoint getFreeTile() {
		int x,y;
		do {
			x = random(1,pfTilesX()-2,1);
			y = random(1,pfTilesY()-2,1);
		} while ( and(getTileCid(x,y),1+2+4+8+16+32+64+128+256+512)
		||        (x<6 && y<6) );
		return new JGPoint(x,y);
	}
	/** openness between 0 and 0.2 */
	public int [][] defineCells(int xsize,int ysize, double openness) {
		int [] [] cells = new int[xsize][ysize];
		digMazePath(cells,0,0,openness);
		//for (int x=0; x<cells.length; x++) {
		//	for (int y=0; y<cells[0].length; y++) {
		//		System.out.print(" "+cells[x][y]);
		//	}
		//	System.out.println();
		//}

		return cells;
	}
	public void digMazePath(int [][] cells, int x,int y, double openness) {
		// mark that we've been here
		cells[x][y] |= 16;
		// try the four compass directions in random order
		int [] try_dirs = new int [] { 1,2,4,8 };
		// shuffle
		for (int n=0; n<9; n++) {
			int swap1 = random(0,3,1);
			int swap2 = random(0,3,1);
			int try_dirs_swap1_tmp = try_dirs[swap1];
			try_dirs[swap1] = try_dirs[swap2];
			try_dirs[swap2] = try_dirs_swap1_tmp;
		}
		// try the dirs
		for (int i=0; i<4; i++) {
			int xdir=0,ydir=0,otherdir=0;
			if (try_dirs[i]==1) { ydir=-1; otherdir=2; }
			if (try_dirs[i]==2) { ydir= 1; otherdir=1; }
			if (try_dirs[i]==4) { xdir=-1; otherdir=8; }
			if (try_dirs[i]==8) { xdir= 1; otherdir=4; }
			// did we run off the map?
			if (x+xdir<0 || y+ydir<0 
			|| x+xdir>=cells.length || y+ydir>=cells[0].length) continue;
			// is that cell already dug?
			if (and(cells[x+xdir][y+ydir],16)
			&& random(0.001,1.0) > openness ) continue;
			// dig opening
			cells[x][y]           |= try_dirs[i];
			cells[x+xdir][y+ydir] |= otherdir;
			digMazePath(cells,x+xdir,y+ydir,openness);
		}
	}
	public int [] defineWallPos(int minwalls,int maxwalls, int maxpos,
	boolean random_pos){
		int [] wallpos = new int [random(minwalls,maxwalls,1)];
		// set to average position
		for (int i=0; i<wallpos.length; i++) {
			wallpos[i] = (int)( (i+1.0) / (wallpos.length+1.0) * maxpos );
		}
		if (wallpos.length == 0) return wallpos;
		if (!random_pos) return wallpos;
		// vary position according to room left
		for (int n=0; n<wallpos.length*3; n++) {
			int wall = random(0,wallpos.length-1,1);
			int leftpos = (wall == 0) ? 0 : wallpos[wall-1];
			int rightpos= (wall==wallpos.length-1) ? maxpos : wallpos[wall+1];
			wallpos[wall] = random(leftpos+2,rightpos-2,1);
		}
		return wallpos;
	}
	//public void initNewLife() {
	//	removeObjects(null,0);
	//	new Player(16,pfHeight()/2,5,up_key,down_key,left_key,right_key);
	//}
	public void startTitle() { removeObjects(null,0); }
	public void startGameOver() {
		if (player!=null) {
			new JGObject("zexplo",true,player.x,player.y,0,"explo",48);
			player.remove();
		}
	}
	public int nr_mushrooms=0, nr_demons=0;
	public void doFrameInGame() {
		moveObjects();
		checkCollision(4,2); // monsters hit bullets
		checkCollision(4+8,1); // monsters,bullets hit player
		checkBGCollision(BULLETBLOCK_T|SHWALL_T, 2+8); // bg hits bullets
		checkBGCollision(KEY_T+BONUS_T+HEALTH_T,1); // pickups hit player
		nr_mushrooms = countObjects("mushroom",0);
		nr_demons = countObjects("demon",0);
		if (gold_left <= 0) levelDone();
		if (health < 0) gameOver();
		if (player!=null) setViewOffset((int)player.x-8,(int)player.y-8,true);
	}
	public void incrementLevel() {
		level++;
		stage++;
		keys=0;
		if (health <= 5) health = 6;
	}
	public void paintFrame() {
		setFont(status_font);
		setColor(status_color);
		drawString("GP "+score,8,0,-1);
		drawString("Kills "+kills,70,0,-1);
		drawString("Lvl "+(level+1),145,0,-1);
		//drawString("Health: "+health,viewWidth(),0,1);
		if (isMidlet()) {
			drawString("Keys "+keys,8,viewHeight()-15,-1);
			drawString("HP "+health,viewWidth()-8,viewHeight()-15,1);
		} else if (!do_scroll) {
			drawCount(keys, "key",viewWidth()-280,0,12);
			drawCount(health, "health",viewWidth()-10*health,0,10);
		} else {
			drawCount(keys, "key",0,viewHeight()-10,12);
			drawCount(health,"health",viewWidth()-10*health,viewHeight()-10,10);
		}
	}
	public void paintFrameTitle() {
		drawImage(viewWidth()/2-68/*was 97*/,viewHeight()/3-20/*was 46*/,
		"splash_image",false);
		drawString("Press "+getKeyDesc(key_startgame)+" to start",
			viewWidth()/2, 250, 0, title_font,title_color);
	}
	JGFont scoring_font = new JGFont("Arial",0,8);
	public class Turret extends StdDungeonMonster {
		int bulletrate,bulletcount;
		public Turret(double x,double y,int bulletrate) {
			super("turret",true,x,y,4,"turret",32);
			this.bulletrate=bulletrate;
			this.bulletcount=(int)random(0,bulletrate);
			if (isMidlet()) this.expiry = suspend_off_view;
		}
		public void move() {
			super.move();
			if ( (bulletcount--) <= 0) bulletcount=bulletrate;
			if (bulletcount < (int)(50/gamespeed)
			&& (bulletcount%(int)(8/gamespeed))==0) {
				if (player!=null) {
					double angle = atan2(player.x-x,player.y-y);
					new TurretBullet(x, y,
						2.0*Math.sin(angle), 2.0*Math.cos(angle), 70);
				}
			}
		}
	}
	public class TurretBullet extends JGObject {
		public TurretBullet (double x,double y, double xspeed, double yspeed,
		int expiry) {
			super("turretbullet",true,x,y, 8, "bullet",xspeed,yspeed,expiry);
		}
		public void hit_bg(int tilecid) { remove(); }
		public void hit_bg(int tilecid,int tx,int ty) {
			if (and(tilecid,SHWALL_T)) setTile(tx,ty,"");
		}
	}
	public class Generator extends StdDungeonMonster {
		int genspeed,gencount;
		int monster_firerate; // 0 means mushroom
		public Generator(double x,double y,int genspeed,int monster_firerate) {
			super("generator",true,x,y,4,null,GEN_T);
			this.genspeed=genspeed;
			this.gencount=(int)random(0,genspeed);
			this.monster_firerate=monster_firerate;
			if (monster_firerate==0) {
				setGraphic("hole");
			} else {
				setGraphic("coffin");
			}
			if (isMidlet()) this.expiry = suspend_off_view;
		}
		public void move() {
			super.move();
			if (!and(getTileCid(getCenterTile(),0,0),MONSTER_T)) {
				// if we're blocked, stop generating
				if ( (gencount--) <= 0) {
					gencount=genspeed;
					if (monster_firerate==0) { // gen mushrooms
						if (nr_mushrooms < 80) {
							JGObject mushroom = new StdDungeonMonster(
								"mushroom",true,x,y, 4, "mushroom",false,false,
								MONSTERBLOCK_T,MONSTER_T, 1.0,player,false,0.0);
							if (isMidlet()) mushroom.expiry = suspend_off_view;
						}
					} else {
						if (nr_demons < 40) new Demon(x,y,monster_firerate);
					}
				}
			}
		}
	}
	public class Demon extends StdDungeonMonster {
		int firerate,firecount;
		public Demon(double x,double y, int firerate) {
			super("demon",true,x,y,
				4, "face",false,false,
				MONSTERBLOCK_T,MONSTER_T,  1.0, null, false, 0.0);
			home_in=player;
			this.firerate=firerate;
			this.firecount=(int)random(0,firerate);
			if (isMidlet()) this.expiry = suspend_off_view;
		}
		public void move() {
			super.move();
			if (player!=null) {
				if (Math.abs(x-player.x) + Math.abs(y-player.y) > 150) {
					avoid=false;
				} else {
					avoid=true;
				}
			}
			if ( (firecount--) <= 0) {
				firecount=firerate;
				if (player!=null) {
					double angle = atan2(player.x-x,player.y-y);
					new TurretBullet(x, y,
						1.0*Math.sin(angle), 1.0*Math.cos(angle), 120);
				}
			}
		}
	}
	//AudioClip aud = newAudioClip(getClass().getClassLoader().getResource("sounds/spacemusic.au"));
	public class Player extends StdDungeonPlayer {
		public Player(double x,double y,double speed,int upkey,int downkey,
		int leftkey,int rightkey) {
			super("player",x,y,1,"human_l",false,false,
			PLAYERBLOCK_T,PLAYER_T, 2.3,
			upkey,downkey,leftkey,rightkey);
			stopAnim();
		}
		int prevxdir=1,prevydir=0;
		public void move() {
			int checkxdir=0,checkydir=0;
			if (getKey(key_left))  {
				checkxdir = -1;
				prevxdir = -1;
				prevydir = 0;
			}
			if (getKey(key_right)) {
				checkxdir =  1;
				prevxdir = 1;
				prevydir = 0;
			}
			if (getKey(key_up))  {
				checkydir = -1;
				prevxdir = 0;
				prevydir = -1;
			}
			if (getKey(key_down)) {
				checkydir =  1;
				prevxdir = 0;
				prevydir = 1;
			}
			if (getKey(key_fire)) {
				stop_moving=true;
				JGPoint cen = getCenterTile();
				if (and(getTileCid(cen,prevxdir,prevydir),DOOR_T)) {
					// player is pushing against door
					if (keys > 0) {
						openDoor(cen.x+prevxdir,cen.y+prevydir);
						keys--;
					}
				}
			} else {
				stop_moving=false;
			}
			checkxdir=0;
			checkydir=0;
			if (getKey(key_fireleft))  checkxdir = -1;
			if (getKey(key_fireright)) checkxdir =  1;
			if (getKey(key_fireup))   checkydir = -1;
			if (getKey(key_firedown)) checkydir =  1;
			if (countObjects("bullet",0) < 1
			&&  (checkxdir!=0 || checkydir!=0) ) {
				new Bullet(x,y, checkxdir,checkydir, 6.0);
			}
			super.move();
			if (xdir > 0) setGraphic("human_r");
			if (xdir < 0) setGraphic("human_l");
		}
		public void hit_bg(int tilecid, int tx,int ty) {
			if (and(tilecid,BONUS_T)) {
				setTile(tx,ty,"");
				score += 1;
				gold_left--;
			} else if (and(tilecid,KEY_T)) {
				keys++;
				setTile(tx,ty,"");
			} else if (and(tilecid,HEALTH_T)) {
				if (health < 15) {
					health+=2;
					setTile(tx,ty,"");
					new StdScoring("msg",x,y-10,0,-0.5,40,"Potion! +2 health",
						new JGFont("Helvetica",0,10),
							new JGColor[] {JGColor.white,JGColor.red},2);
				}
			}
		}
		public void hit(JGObject obj) {
			if (obj.colid == 4) { // monster
				obj.remove();
				health -= 2;
				new StdScoring("msg",x,y-10,0,-0.5,40,"Hit! -2 health",
					new JGFont("Helvetica",0,10),
						new JGColor[] {JGColor.white,JGColor.magenta},2);
			} else if (obj.colid == 8) { // bullet
				//aud.play();
				obj.remove();
				health -= 1;
				new StdScoring("msg",x,y-10,0,-0.5,40,"Shot! -1 health",
					new JGFont("Helvetica",0,10),
						new JGColor[] {JGColor.white,JGColor.blue},2);
			}
		}
	}
	void openDoor(int x,int y) {
		if (!and(getTileCid(x,y),DOOR_T)) return;
		setTile(x,y,"");
		if (x>0) openDoor(x-1,y);
		if (y>0) openDoor(x,y-1);
		if (x<pfTilesX()-1) openDoor(x+1,y);
		if (y<pfTilesY()-1) openDoor(x,y+1);
		
	}
	public class Bullet extends JGObject {
		public Bullet(double x,double y,int xdir,int ydir,double speed){
			super("bullet",true,x,y,2,"axe",35);
			setDirSpeed(xdir,ydir,speed);
		}
		public void hit(JGObject obj) {
			kills++;
			new JGObject("zexplo",true,obj.x,obj.y,0,"explo",12);
			obj.remove();
			remove();
		}
		public void hit_bg(int tilecid) { remove(); }
		public void hit_bg(int tilecid,int tx,int ty) {
			if (and(tilecid,SHWALL_T)) setTile(tx,ty,"");
		}
	}
}
