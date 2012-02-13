/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Class Lang nằm trong package Common
 * Class định nghĩa các biến language là các mảng String.
 * Nếu muốn thêm ngôn ngữ thì thêm vào cuối mỗi mảng là ok
 */
package ClassFrame;


import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;

/**
 *
 * @author QuyetNM1
 */
public class Lang {
    //0 - English, 1 - Vietnamese
    public static int lang = 0;
    //Error message
    public static final String[] err_LOAD_IMG = {"Can not load image","Không Lấy được ảnh"};
    //Command
    public static final String[] cmdSelect = {"Select","Chọn"};
    public static final String[] cmdOk = {"Ok","Đồng ý"};
    public static final String[] cmdCancel = {"Cancel","Hủy bỏ"};
    public static final String[] cmdClose = {"Close","Đóng"};
    public static final String[] cmdBack = {"Back","Quay lại"};
    public static final String[] cmdYes = {"Yes","Có"};
    public static final String[] cmdNo = {"No","Không"};
    public static final String[] cmdChange = {"Change","Thay đổi"};
    public static final String[] cmdReplay = {"Replay","Chơi lại"};
    public static final String[] cmdContinue = {"Continue","Chơi tiếp"};
    //menu item
    public static final String[] menuAbout = {"About","Giới thiệu"};
    public static final String[] menuPlay = {"Play Game","Chơi Game"};
    public static final String[] menuSetting = {"Setting","Thiết lập"};
    public static final String[] menuGuide = {"Guide","Hướng dẫn"};
    public static final String[] menuExit = {"Exit","Thoát"};
    public static final String[] menuScore = {"High score","Kỷ lục"};
    public static final String[] menuContinue = {"Continue","Tiếp tục"};
    //message
    public static final String[] aboutInfo = {"Version 0.5.1 (C) 2011\nDeveloped by Nguyen Manh Quyet\nstrongdecision@gmail.com",
                                                "Phiên bản 0.5.1 (C) 2011\nPhát triển bởi Nguyễn Mạnh Quyết\nstrongdecision@gmail.com"};
    public static final String[] confirmSound = {"Open sound ?","Bật nhạc"};
    public static final String[] win = {"CONGRATULATE\nYOU HAVE BEATEN HIGH SCORE","XIN CHÚC MỪNG\nBẠN ĐÃ PHÁ ĐẢO"};
    public static final String[] lose = {"YOU ARE NOT BEATEN HIGH SCORE YET","BẠN ĐÃ THUA"};
    //setting screen
    public static final String[] stMusic = {"Music","Nhạc nền"};
    public static final String[] stSoundEffect = {"Sound effect","Hiệu ứng"};
    public static final String[] stLanguage = {"English","Tiếng Việt"};
    //Exit screen
    public static final String[] exit = {"Bye bye\nSee you again!","Tạm biệt\nHẹn gặp lại"};
    //Guide screen
    public static final String[] guideTitle = {"GUIDE", "HƯỚNG DẪN"};
    public static final String[] guideContent = {"Rescue the lovely chicks is being\nbrought to the mouth of old and\nhated wolves by the keys 4, 5,\n6 respectively", "Giải cứu những chú gà con đang bị\nđưa đến miệng con sói già đáng ghét\nbằng các phím 4, 5, 6 tương ứng"};

    public Lang(){}
    public String[] split(String original) {
        Vector nodes = new Vector();
        String separator = "\n";
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while(index>=0) {
            nodes.addElement( original.substring(0, index) );
            original = original.substring(index+separator.length());
            index = original.indexOf(separator);
        }
        // Get the last node
        nodes.addElement( original );

        // Create splitted string array
        String[] result = new String[ nodes.size() ];
        if( nodes.size()>0 ) {
            for(int loop=0; loop<nodes.size(); loop++){
                result[loop] = (String)nodes.elementAt(loop);
            }
        }

        return result;
    }
}
