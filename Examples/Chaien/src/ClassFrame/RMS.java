/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassFrame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author QuyetNM1
 */
public class RMS {
    public RecordStore rs;
    
    private ByteArrayOutputStream bos;
    private DataOutputStream dos;
    private ByteArrayInputStream bis;
    private DataInputStream dis;
    
    private byte[] writeByte;
    
    private static final String RECORD_STORE_NAME = "ChayTronChaien";
    private static final int NUM_RECORD           = 5;
    
    public void open() {
        try {
            rs = RecordStore.openRecordStore(RECORD_STORE_NAME, true);
            if(rs.getNumRecords() == 0) {
                Player p ;
                p = new Player(null, Resource.IMG_SMALL_NUMBER, 0, 5, 1000, Player.MAX_TIME, 1, "Quyet");
                insert(p);
                p = new Player(null, Resource.IMG_SMALL_NUMBER, 0, 5, 500, Player.MAX_TIME, 1,  "Biladen");
                insert(p);
                p = new Player(null, Resource.IMG_SMALL_NUMBER, 0, 5, 300, Player.MAX_TIME, 1,  "Obama");
                insert(p);
                p = new Player(null, Resource.IMG_SMALL_NUMBER, 0, 5, 120, Player.MAX_TIME, 1,  "OhMyGod");
                insert(p);
                p = new Player(null, Resource.IMG_SMALL_NUMBER, 0, 5, 40, Player.MAX_TIME, 1,  "FixBug");
                insert(p);
                p = new Player(null, Resource.IMG_SMALL_NUMBER, 0, 5, 0, Player.MAX_TIME, 1,  "");
                insert(p);
            }
        }
        catch(RecordStoreException re) {
            System.out.println("Can't open RMS");
            System.out.println(re.getMessage());
        }
    }
    
    public void close() {
        try {
            rs.closeRecordStore();
        }
        catch(NullPointerException npe) {
            System.out.println("RMS 's reference is null");
            System.out.println(npe.getMessage());
        }
        catch(RecordStoreException e) {
            System.out.println("Can't close RMS");
            System.out.println(e.getMessage());
        }
    }
    
    public void insert(final Player p) {
        try {
            bos = new ByteArrayOutputStream();
            dos = new DataOutputStream(bos);
                    
            dos.writeUTF( p.getName( ) );
            dos.writeInt( p.getScore() );
            dos.writeInt( p.getLevel() );
            dos.writeInt( p.getLife() );
            dos.writeInt( p.getBlood() );
            dos.writeInt( p.getTime() );
            dos.flush();
            
            writeByte = bos.toByteArray();
            
            rs.addRecord(writeByte, 0, writeByte.length);
        }
        catch(IOException ioe) {
            System.out.println("Can't write data to RMS");
            System.out.println(ioe.getMessage());
        }
        catch(RecordStoreException rno) {
            System.out.println("Can't wirte data to RMS becase RMS closing");
            System.out.println(rno.getMessage());
        }
    }
    
    public void replaceWith(final Player p){
        try {
            for(int id = 1; id <= RMS.NUM_RECORD; ++id) {
                bis = new ByteArrayInputStream(rs.getRecord(id));
                dis = new DataInputStream(bis);
                
                String name = dis.readUTF();
                int score = dis.readInt();
                
                if( score < p.getScore() ) {
                    byte[] tbye1;
                    
                    for(int i = RMS.NUM_RECORD; i >= id+1; --i) {
                        tbye1 = rs.getRecord(i-1);
                        rs.setRecord(i, tbye1, 0, tbye1.length);
                    }
                    bos = new ByteArrayOutputStream();
                    dos = new DataOutputStream(bos);
                    
                    dos.writeUTF( p.getName() );
                    dos.writeInt( p.getScore() );
                    dos.writeInt( p.getLevel() );
                    dos.writeInt( p.getLife() );
                    dos.writeInt( p.getBlood() );
                    dos.writeInt( p.getTime() );
            
                    dos.flush();
            
                    writeByte = bos.toByteArray();
            
                    rs.setRecord(id, writeByte, 0, writeByte.length);
                    break;
                }
            }
        }
        catch(IOException ioe) {
            System.out.println("Can't write data to RMS");
            System.out.println(ioe.getMessage());
        }
        catch(RecordStoreException rno) {
            System.out.println("Can't wirte data to RMS becase RMS closing");
            System.out.println(rno.getMessage());
        }
    }
    
    public Vector read() {
        Vector v = new Vector();
        Player p;
        try {
            String name;
            int score;
            int level;
            int life;
            int blood;
            int time;
            
            for(int id = 1; id <= RMS.NUM_RECORD; ++id) {
                bis = new ByteArrayInputStream(rs.getRecord(id));
                dis = new DataInputStream(bis);
                
                name = dis.readUTF();
                score = dis.readInt();
                level = dis.readInt();
                life = dis.readInt();
                blood = dis.readInt();
                time = dis.readInt();
                
                p = new Player(null, Resource.IMG_SMALL_NUMBER, level, life, score, time, blood, name);
                v.addElement(p);
            }
        }
        catch(IOException ioe) {
            System.out.println("Can't write data to RMS");
            System.out.println(ioe.getMessage());
        }
        catch(RecordStoreException rno) {
            System.out.println("Can't read data to RMS becase RMS closing");
            System.out.println(rno.getMessage());
        }
        
        return v;
    }
        
    public boolean is_top(final Player p) {
       boolean result = false;
       
       try {
           bis = new ByteArrayInputStream(rs.getRecord(RMS.NUM_RECORD));
           dis = new DataInputStream(bis);

           String name  = dis.readUTF();
           int score = dis.readInt();
           int level = dis.readInt();
           int life = dis.readInt();
           int blood = dis.readInt();
           int time = dis.readInt();

           if(score <  p.getScore() ) {
               result = true;
           }
       }
       catch(RecordStoreException rse) {
           System.out.println("in is_top method");
           System.out.println(rse.getMessage());
       }
       catch(IOException ioe) {
           System.out.println("error with reading data from input stream");
           System.out.println(ioe.getMessage());
       }
        return result;
    }

    public void saveLevel(Player p,int index){

        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
        try {
            dos.writeUTF( p.getName() );
            dos.writeInt( p.getScore() );
            dos.writeInt( p.getLevel() );
            dos.writeInt( p.getLife() );
            dos.writeInt( p.getBlood() );
            dos.writeInt( p.getTime() );
            dos.flush();
        }
        catch (IOException ex) {

        }
            writeByte = bos.toByteArray();
            try {
                rs.setRecord(index, writeByte, 0, writeByte.length);
            }
            catch(InvalidRecordIDException in) {

            }
            catch(RecordStoreNotOpenException re) {

            }
            catch(RecordStoreException re) {

            }

    }
    public Player readLevel(int index) {

        Player p = null;
        try {
            String name;
            int score;
            int level;
            int life;
            int blood;
            int time;


                bis = new ByteArrayInputStream(rs.getRecord(index));
                dis = new DataInputStream(bis);

                name = dis.readUTF();
                score = dis.readInt();
                level = dis.readInt();
                life = dis.readInt();
                blood = dis.readInt();
                time  = dis.readInt();

                p = new Player(null, Resource.IMG_SMALL_NUMBER ,level, life, score, time, blood, name);
        }
        catch(IOException ioe) {
            System.out.println("Can't write data to RMS");
            System.out.println(ioe.getMessage());
        }
        catch(RecordStoreException rno) {
            System.out.println("Can't read data to RMS becase RMS closing");
            System.out.println(rno.getMessage());
        }

        return p;
    }
}
