/**
 * <p>Title: lipeng</p>
 *
 * <p>Description: You cannot remove this copyright and notice. You cannot use
 * this file without the express permission of the author. All Rights
 * Reserved</p>
 *
 * <p>Copyright: lizhenpeng (c) 2004</p>
 *
 * <p>Company: LP&P</p>
 *
 * @author lizhenpeng
 * @version 1.1.0
 *
 * <p> Revise History
 *
 * 2004.07.12 Add exception self description and revise exception description
 * V1.1.0 </p>
 */
package lipeng;

import javax.microedition.rms.*;

public class LPSaveRecord {

    private RecordStore rs;
    private String rsName;

    public LPSaveRecord(String name) {
        rsName = name;
        try {
            if (existRecordStore(name)) {
                rs = RecordStore.openRecordStore(name, false);
            } else {
                rs = RecordStore.openRecordStore(name, true);
            }
        } catch (RecordStoreNotFoundException e) {
            System.out.println("Open Record Error");
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void addRecord(byte[] rec) {
        try {
            rs.addRecord(rec, 0, rec.length);
        } catch (Exception e) {
            System.out.println("Add Record Error");
            System.out.println(e.toString());
        }
    }

    public void close() {
        try {
            rs.closeRecordStore();
        } catch (Exception e) {
            System.out.println("Close Record Error");
            System.out.println(e.toString());
        }
    }

    public boolean IsEmpty() {
        try {
            if (rs.getNumRecords() > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Get Record Number Error");
            System.out.println(e.toString());
        }
        return true;
    }

    public void SetRecord(int recordId, byte[] buffer) {
        try {
            rs.setRecord(recordId, buffer, 0, buffer.length);
        } catch (Exception e) {
            System.out.println("Set Record Error");
            System.out.println(e.toString());
        }
    }

    public byte[] getRecord(int recordId) {
        byte buffer[];
        try {
            buffer = rs.getRecord(recordId);
            return buffer;
        } catch (Exception e) {
            System.out.println("Get Record Error");
            System.out.println(e.toString());
        }
        return null;
    }

    public void removeAll() {
        try {
            rs.deleteRecordStore(rsName);
        } catch (Exception e) {
            System.out.println("Remove All Record Error");
            System.out.println(e.toString());
        }
    }

    private boolean existRecordStore(String recordName) {
        boolean existRs = true;
        RecordStore rsTemp = null;
        try {
            rsTemp = RecordStore.openRecordStore(recordName, false);
        } catch (Exception e) {
            existRs = false;
            System.out.println("Test Record Exist Error");
            System.out.println(e.toString());
        } finally {
            try {
                rsTemp.closeRecordStore();
            } catch (Exception e) {
                System.out.println("Close Record Error");
                System.out.println(e.toString());
            }
        }
        return existRs;
    }
}