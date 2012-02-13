package isoj2me;

import java.util.*;

import javax.microedition.lcdui.*;

/**
 * <p>
 * isoj2me: Utility
 * </p>
 * <p>
 * This class contains utility methods.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * License: Lesser GPL (http://www.gnu.org)
 * </p>
 * <p>
 * Company: <a href=http://www.mondonerd.com target="_blank">Mondonerd.com</a>
 * </p>
 * 
 * @author Massimo Maria Avvisati
 * 		   Giuseppe Perniola
 * @version 0.5
 */

public class Utility {
	
	public static Image[] imagesStaticPool;
	public static Hashtable imagesDynamicPool;
	
	private static int initCapacityStaticPool, initCapacityDynamicPool;
	private static int currImageCodeStaticPool;							// index of first imageCode available 
																		// in imagesCodesStaticPool
	private static String lastImageCodeStaticPool, lastImageCodeDynamicPool;
	private static String[] imagesCodesStaticPool;
	private static Image lastImageStaticPool, lastImageDynamicPool;
	
	/**
	 * This method destroy all resources allocated
	 *
	 */
	
	public static void destroy() {
		
		int i;
		
		imagesStaticPool = null;
		imagesDynamicPool = null;
		lastImageCodeStaticPool = null;
		lastImageCodeDynamicPool = null;
		if (imagesCodesStaticPool != null) {
			for (i = 0; i < imagesCodesStaticPool.length; i ++) {
				imagesCodesStaticPool[i] = null;
			}
			imagesCodesStaticPool = null;
		}
		lastImageStaticPool = null;
		lastImageDynamicPool = null;
		
		System.gc();
		
	} 
	
	/**
	 * This method set the initial capacity of static and dynamic pools
	 * 
	 * @param staticPool
	 *				initial capacity of static pool
	 * @param dynamicPool
	 * 				initial capacity of dynamic pool
	 */
	
	public static void setInitCapacityPools(int staticPool, int dynamicPool) {
		
		initCapacityStaticPool = staticPool;
		initCapacityDynamicPool = dynamicPool;
		currImageCodeStaticPool = 0;
		destroy();
		
		imagesCodesStaticPool = new String[initCapacityStaticPool];
		
	}
	
	// TODO add javadoc
	
	public static Image loadImageStaticPool(String imageCode) {
		
		int i, index = currImageCodeStaticPool;
		
		if (lastImageCodeStaticPool != null && lastImageStaticPool != null) {
			if (imageCode.equals(lastImageCodeStaticPool)) {
				return lastImageStaticPool;
			}
		}
		if (imagesStaticPool == null) {
			imagesStaticPool = new Image[initCapacityStaticPool];
		}
		if (currImageCodeStaticPool >= initCapacityStaticPool) {
			initCapacityStaticPool <<= 1;
			
			String[] tempImagesCodesStaticPool = new String[initCapacityStaticPool];
			Image[] tempImagesStaticPool = new Image[initCapacityStaticPool];
			
			System.arraycopy(imagesCodesStaticPool, 0, tempImagesCodesStaticPool, 0, imagesCodesStaticPool.length);
			System.arraycopy(imagesStaticPool, 0, tempImagesStaticPool, 0, imagesStaticPool.length);
			
			for (i = 0; i < imagesCodesStaticPool.length; i ++) {
				imagesCodesStaticPool[i] = null;
			}
			imagesCodesStaticPool = tempImagesCodesStaticPool;
			for (i = 0; i < imagesStaticPool.length; i ++) {
				imagesStaticPool[i] = null;
			}
			imagesStaticPool = tempImagesStaticPool;
			
			System.gc();
		}
		for (i = 0; i < currImageCodeStaticPool; i ++) {
			if (imagesCodesStaticPool[i].equals(imageCode)) {
				index = i;
				
				break;
			}
		}
		if (imagesCodesStaticPool[index] != null) {
			lastImageCodeStaticPool = imagesCodesStaticPool[index];
			lastImageStaticPool = imagesStaticPool[index];
			
			return lastImageStaticPool;
		} else {
			lastImageCodeStaticPool = imageCode;
			imagesCodesStaticPool[currImageCodeStaticPool] = imageCode;
			try {
				lastImageStaticPool = Image.createImage("/" + imageCode + ".png");
			} catch (Exception e) {
				// TODO
				lastImageStaticPool = Image.createImage(1, 1);
			}
			imagesStaticPool[currImageCodeStaticPool ++] = lastImageStaticPool;
			
			return lastImageStaticPool;
		}
		
	}
	
	/**
	 * This method return an Image loading it from the "hard disk" of the mobile
	 * phone (from the standard directory) or, if the image was already loaded,
	 * it return an image took from an hashtable
	 * 
	 * @param imageCode
	 *            alphanumeric code for this image (filename)
	 * @param imagesHash
	 *            where to store Image objects
	 * @return the requested Image
	 */
	
	public static Image loadImageDynamicPool(String imageCode) {

		
		
		if (lastImageCodeDynamicPool != null && lastImageDynamicPool != null) {
			if (imageCode.equals(lastImageCodeDynamicPool)) {
				return lastImageDynamicPool;
			}
		}
        if (imagesDynamicPool == null) {
            imagesDynamicPool = new Hashtable(initCapacityDynamicPool);
        }
        if (imagesDynamicPool.size() > initCapacityDynamicPool) { //TODO
            imagesDynamicPool.clear();
            
            System.gc();
            //System.out.println("Cache (" + imagesHash.toString() + ") empty");
        }
		if (imagesDynamicPool.containsKey(imageCode)) {
			lastImageCodeDynamicPool = imageCode;
			lastImageDynamicPool = (Image)imagesDynamicPool.get(imageCode);
			
			return lastImageDynamicPool;
		} else {
			lastImageCodeDynamicPool = imageCode;
			try {
				lastImageDynamicPool = Image.createImage("/" + imageCode + ".png");
				imagesDynamicPool.put(imageCode, lastImageDynamicPool);
			} catch (Exception e) {
				// TODO
				lastImageDynamicPool = Image.createImage(1, 1);
			}
			
			return lastImageDynamicPool;
		}
    
	}
    
}