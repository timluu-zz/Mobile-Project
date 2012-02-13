
/**
 * <p> Title:RMSSystem </p>
 *
 * <p> Description:手机RMS系统的设计，用来存储分数 </p>
 *
 * <p> Copyright: Copyright (c) 2005 </p>
 *
 * <p> Company: </p> 非作者授权，请勿用于商业用途。
 *
 * @author bruce.fine@gmail.com
 * @version 1.0
 */
import javax.microedition.rms.RecordStore;

public class RMSSystem {

    /**
     * 建立RMSSystem系统 主要存储当前关卡与分数
     */
    public static void init() {
        // 创建N个名次

        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
                // 如果是第一次使用
                // 写入当前关卡数
                int currentLevelId = 0;
                if (Consts.SIS_DEBUG) {
                    currentLevelId = Consts.SN_ABS_MAX_LEVEL;
                }
                byte bytes[] = longToBytes(currentLevelId);
                hsRecorder.addRecord(bytes, 0, bytes.length);
                // 写入每一个关卡的分数
                bytes = new byte[8 * (Consts.SN_ABS_MAX_LEVEL + 1)];
                for (int i = 0; i <= Consts.SN_ABS_MAX_LEVEL; i++) {
                    byte[] bBytes = longToBytes(0);
                    for (int j = 0; j < bBytes.length; j++) {
                        bytes[i * 8 + j] = bBytes[j];
                    }
                }
                hsRecorder.addRecord(bytes, 0, bytes.length);
                // Sound on off
                bytes = new byte[1];
                bytes[0] = 1;
                hsRecorder.addRecord(bytes, 0, bytes.length);
                // 背景
                bytes = new byte[1];
                bytes[0] = 0;
                hsRecorder.addRecord(bytes, 0, bytes.length);
                // 难度
                bytes = new byte[1];
                bytes[0] = 0;
                hsRecorder.addRecord(bytes, 0, bytes.length);
            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms init: " + ex.getMessage());
        }

    }

    //
    public static int getBackId() {
        int flag = 0;
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                byte[] bytes = new byte[1];
                hsRecorder.getRecord(4, bytes, 0);
                flag = bytes[0];
            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            System.out.println("rms s: " + ex.getMessage());
        }
        return flag;
    }

    //
    public static int getEasyHardId() {
        int flag = 0;
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                byte[] bytes = new byte[1];
                hsRecorder.getRecord(5, bytes, 0);
                flag = bytes[0];
            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            System.out.println("rms s: " + ex.getMessage());
        }
        return flag;
    }

    //
    public static boolean isSoundOn() {
        boolean flag = false;
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                byte[] bytes = new byte[1];
                hsRecorder.getRecord(3, bytes, 0);
                if (bytes[0] == 0) {
                    flag = false;
                } else {
                    flag = true;
                }

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            System.out.println("rms s: " + ex.getMessage());
        }
        return flag;
    }

    public static void saveSound(boolean srcSound) {
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {

                byte[] bytes = new byte[1];
                if (srcSound) {
                    bytes[0] = 1;
                } else {
                    bytes[0] = 0;
                }
                hsRecorder.setRecord(3, bytes, 0, bytes.length);

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms slel: " + ex.getMessage());
        }
    }

    public static void saveBackId(int id) {
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {

                byte[] bytes = new byte[1];
                bytes[0] = (byte) id;
                hsRecorder.setRecord(4, bytes, 0, bytes.length);

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms slel: " + ex.getMessage());
        }
    }

    public static void saveEasyHardId(int id) {
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {

                byte[] bytes = new byte[1];
                bytes[0] = (byte) id;
                hsRecorder.setRecord(5, bytes, 0, bytes.length);

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms slel: " + ex.getMessage());
        }
    }

    /**
     * 获得当前打开的关卡
     */
    public static int loadLevelOpened() {

        int bLevelId = 0;
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                byte[] bytes = new byte[8];
                hsRecorder.getRecord(1, bytes, 0);
                bLevelId = (int) bytesToLong(bytes);

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms oplel: " + ex.getMessage());
        }
        return bLevelId;

    }

    /**
     *
     */
    public static void saveLevelOpened(int nLevelId) {
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                byte[] bytes = new byte[8];
                hsRecorder.getRecord(1, bytes, 0);
                long value = bytesToLong(bytes);
                if (value < nLevelId) {
                    bytes = longToBytes(nLevelId);
                    hsRecorder.setRecord(1, bytes, 0, bytes.length);

                }

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms slel: " + ex.getMessage());
        }

    }

    /**
     *
     */
    public static long[] loadHighScoresPerLevel() {
        long longHighScore[] = new long[Consts.SN_ABS_MAX_LEVEL + 1];

        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                byte[] bytes = new byte[(Consts.SN_ABS_MAX_LEVEL + 1) * 8];
                hsRecorder.getRecord(2, bytes, 0);

                for (int i = 0; i <= Consts.SN_ABS_MAX_LEVEL; i++) {
                    byte[] bBytes = new byte[8];
                    for (int j = 0; j < bBytes.length; j++) {
                        bBytes[j] = bytes[i * 8 + j];
                    }
                    longHighScore[i] = bytesToLong(bBytes);
                }

            }
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms ichi: " + ex.getMessage());
        }
        return longHighScore;
    }

    /**
     *
     */
    public static void saveHighScoreByLevel(int levelId, long highScore) {
        byte[] bytes = new byte[(Consts.SN_ABS_MAX_LEVEL + 1) * 8];
        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            if (hsRecorder.getVersion() == 0) {
            } else {
                hsRecorder.getRecord(2, bytes, 0);

            }
            byte[] bBytesTemp = longToBytes(highScore);

            for (int i = 0; i < bBytesTemp.length; i++) {
                bytes[levelId * 8 + i] = bBytesTemp[i];
            }
            hsRecorder.setRecord(2, bytes, 0, bytes.length);
            hsRecorder.closeRecordStore();

        } catch (Exception ex) {
            // System.out.println("rms hl: " + ex.getMessage());
        }

    }

    public static byte[] longToBytes(long seed) {
        byte tempByte[] = new byte[8];
        tempByte[0] = (byte) ((seed >> 56) & 0x00000000000000FF);
        tempByte[1] = (byte) ((seed >> 48) & 0x00000000000000FF);
        tempByte[2] = (byte) ((seed >> 40) & 0x00000000000000FF);
        tempByte[3] = (byte) ((seed >> 32) & 0x00000000000000FF);
        tempByte[4] = (byte) ((seed >> 24) & 0x00000000000000FF);
        tempByte[5] = (byte) ((seed >> 16) & 0x00000000000000FF);
        tempByte[6] = (byte) ((seed >> 8) & 0x00000000000000FF);
        tempByte[7] = (byte) ((seed) & 0x00000000000000FF);
        return tempByte;
    }

    public static long bytesToLong(byte[] seeds) {
        long tempLong = 0x0000000000000000;
        tempLong |= (((long) (seeds[0])) << 56) & 0xFF000000;
        tempLong |= (((long) (seeds[1])) << 48) & 0x00FF0000;
        tempLong |= (((long) (seeds[2])) << 40) & 0x0000FF00;
        tempLong |= (((long) (seeds[3])) << 32) & 0x000000FF;
        tempLong |= (((long) (seeds[4])) << 24) & 0xFF000000;
        tempLong |= (((long) (seeds[5])) << 16) & 0x00FF0000;
        tempLong |= (((long) (seeds[6])) << 8) & 0x0000FF00;
        tempLong |= (((long) (seeds[7]))) & 0x000000FF;
        return tempLong;
    }

    /**
     * 清空
     */
    public static void clearRecords() {

        try {
            RecordStore hsRecorder = RecordStore.openRecordStore(
                    Consts.SSTR_RMS_NAME, true);
            byte bytes[] = longToBytes(0);
            hsRecorder.setRecord(1, bytes, 0, bytes.length);
            // 写入每一个关卡的分数
            bytes = new byte[8 * (Consts.SN_ABS_MAX_LEVEL + 1)];
            for (int i = 0; i <= Consts.SN_ABS_MAX_LEVEL; i++) {
                byte[] bBytes = longToBytes(0);
                for (int j = 0; j < bBytes.length; j++) {
                    bytes[i * 8 + j] = bBytes[j];
                }
            }
            hsRecorder.setRecord(2, bytes, 0, bytes.length);
            hsRecorder.closeRecordStore();
        } catch (Exception ex) {
            // System.out.println("cl rsm:" + ex.getMessage());
        } // try-catch
    }
}
