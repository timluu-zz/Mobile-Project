/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFrame;

import java.util.Random;

/**
 *
 * @author QuyetNM1
 */
public class RandomGenerator{
    public static int random(Random generator, int start, int end) throws Exception {
        if(start > end) {
            throw new Exception("generate radom error : start number bigger than end one");
        }
        
        int range = Math.abs( end - start + 1 );
        try {
            generator.setSeed( System.currentTimeMillis() );
        }
        catch(NullPointerException npe) {
            System.out.println( "generator is null" );
            System.out.println( npe.getMessage() );
        }
        
        int ran = generator.nextInt( range ) + start;
        
        return ran;
    }
}
