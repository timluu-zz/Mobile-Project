/**
 * Net Intruder: canvas class
 * 
 * This software is released under GNU GPL license. View license.txt for more details.
 * 
 * @version 0.9
 * @author Massimo Maria Avvisati (http://www.mondonerd.com)
 * 
 */

public class netIntruderBlackBox {
    long seed = 0;
    netIntruderCanvas canvas = null;
    public netIntruderBlackBox(netIntruderCanvas canvas) {
        super();
        this.canvas = canvas;
        seed = System.currentTimeMillis();
    }
    
    public boolean verify() {
        boolean result = false;
        
        
        return result;
    }
    
    public String generateCode() {
        int keyPhrase = 20011121;
        
        int tempKey =(int) (seed  / 3000);
        
        int tempKey2 = tempKey + 2001;
        //check
        if (System.currentTimeMillis() - seed < 40000) {
            tempKey2 -= 1;
        }
        
        
        String code ="A" + (tempKey2 % canvas.util.NUMBER_OF_LEVELS) + "-" + tempKey2 + "B" + (tempKey ^ keyPhrase);
        //System.out.println(code);
        return code;
    }

}
