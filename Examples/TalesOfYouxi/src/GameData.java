
import java.io.*;
import java.util.Random;
import java.util.Vector;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

public class GameData {

    public final static int nFlagCount = 128;
    public final static int nMonsterCount = 30;
    public boolean flags[];
    public String curMap;
    public int playerX;
    public int playerY;
    public int HP;
    public int EXP;
    public int LV;
    public int AGI;
    public int ATK;
    public int DEF;
    public int MaxHP;
    public int expNext;
    public int item[];
    public boolean monsters[];
    public int money;
    public boolean sound;
    public Vector enemy;

    public GameData() {
        flags = new boolean[nFlagCount];
        monsters = new boolean[nMonsterCount];
        enemy = new Vector();
        item = new int[3];
    }

    public void newGame() {
        curMap = "/res/main";
        for (int i = 0; i < nFlagCount; i++) {
            flags[i] = false;
        }
        for (int i = 0; i < nMonsterCount; i++) {
            monsters[i] = false;
        }
        playerX = 6;
        playerY = 11;
        HP = 50;
        EXP = 13;
        LV = 0;
        AGI = 20;
        MaxHP = 65;
        ATK = 12;
        DEF = 7;
        expNext = nextLv();
        item[0] = 2;
        money = 50;
    }

    public int nextLv() {
        int e = 30;
        for (int i = 1; i < LV + 1; i++) {
            e = e * 3 / 2;
        }
        return e;
    }

    public boolean checkUP() {
        Random rnd = new Random(System.currentTimeMillis());
        while (EXP >= expNext) {
            LV++;
            expNext = nextLv();
            ATK += Math.abs(rnd.nextInt() % 5);
            DEF += Math.abs(rnd.nextInt() % 4);
            AGI += Math.abs(rnd.nextInt() % 5);
            return true;
        }
        return false;
    }

    public boolean saveData() {
        RecordStore rs = null;
        int i;
        try {
            rs = RecordStore.openRecordStore("GameData", true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (i = 0; i < nFlagCount; i++) {
                dos.writeBoolean(flags[i]);
            }
            dos.writeUTF(curMap);
            dos.writeInt(playerX);
            dos.writeInt(playerY);
            dos.writeInt(HP);
            dos.writeInt(EXP);
            dos.writeInt(LV);
            dos.writeInt(AGI);
            dos.writeInt(ATK);
            dos.writeInt(DEF);
            dos.writeInt(MaxHP);
            dos.writeInt(expNext);

            for (i = 0; i < 3; i++) {
                dos.writeInt(item[i]);
            }
            for (i = 0; i < nMonsterCount; i++) {
                dos.writeBoolean(monsters[i]);
            }
            dos.writeInt(money);
            dos.writeBoolean(sound);
            dos.writeInt(enemy.size());
            for (i = 0; i < enemy.size(); i++) {
                dos.writeInt(((Integer) enemy.elementAt(i)).intValue());
            }
            if (rs.getNextRecordID() > 1) {
                rs.setRecord(1, baos.toByteArray(), 0, baos.size());
            } else {
                rs.addRecord(baos.toByteArray(), 0, baos.size());
            }
            rs.closeRecordStore();
            return true;
        } catch (RecordStoreException rse) {
//			System.out.println (rse.getMessage());
            return false;
        } catch (IOException ioe) {
            return false;
        }
    }

    public static GameData loadData() {
//		System.out.println ("Loading rms data");
        RecordStore rs = null;
        int i;
        GameData gd = new GameData();
        try {
            rs = RecordStore.openRecordStore("GameData", false);
            byte[] data = rs.getRecord(1);
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bais);
            for (i = 0; i < nFlagCount; i++) {
                gd.flags[i] = dis.readBoolean();
            }
            gd.curMap = dis.readUTF();
            gd.playerX = dis.readInt();
            gd.playerY = dis.readInt();
            gd.HP = dis.readInt();
            gd.EXP = dis.readInt();
            gd.LV = dis.readInt();
            gd.AGI = dis.readInt();
            gd.ATK = dis.readInt();
            gd.DEF = dis.readInt();
            gd.MaxHP = dis.readInt();
            gd.expNext = dis.readInt();

            for (i = 0; i < 3; i++) {
                gd.item[i] = dis.readInt();
            }
            for (i = 0; i < nMonsterCount; i++) {
                gd.monsters[i] = dis.readBoolean();
            }
            gd.money = dis.readInt();
            gd.sound = dis.readBoolean();
            int j = dis.readInt();
            for (i = 0; i < j; i++) {
                gd.enemy.addElement(new Integer(dis.readInt()));
            }
            rs.closeRecordStore();
            return gd;
        } catch (RecordStoreNotFoundException rsnfe) {
//			System.out.println ("not found"+rsnfe.getMessage());
            return null;
        } catch (RecordStoreException rse) {
//			System.out.println ("rs"+rse.getMessage());
            return null;
//		}catch(IOException ioe){
//			System.out.println ("io"+ioe.getMessage());
//			return null;
        } catch (Exception e) {
//			System.out.println ("abd"+e.getMessage());
            return null;
        }
    }
}
