/********************************************************************
 * ��Ŀ����				��<b>j2meѧϰ J2me Wap Explorer</b>			<br/>
 * 
 * Copyright 2005-2006 Wuhua. All rights reserved </br>
 *
 * ������ֻ����ѧϰĿ�ģ�����������ҵĿ�ġ�������Ҫ����ϵ����
 ********************************************************************/
package org.wuhua.fuck;

import java.util.Vector;
 


/**
 * <b>������sdf.java</b> </br> 
 * ��д����: 2006-12-25 <br/>
 * ������������ <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class StringUtil {
	 
	/**
	 * �и�str�ַ���
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] split(String bufferstr, String regex) {

		if(bufferstr == null)
			return null;
		Vector split = new Vector();

		while (true) // ����������ϻ�õ����ݲ�������д���
		{
			int index = bufferstr.indexOf(regex);

			if (index == -1) {
				if (bufferstr != null && !bufferstr.equals(""))
					split.addElement(bufferstr);
				// log.debug("bufferstr=" +bufferstr);s
				break;
			}
			split.addElement(bufferstr.substring(0, index));
			// log.debug("Str=" + bufferstr.substring(0, index));
			bufferstr = bufferstr.substring(index + 1, bufferstr.length());
			// log.debug("bufferstr=" +bufferstr);
		}
		String[] s = new String[split.size()];

		split.copyInto(s);

		return s;

	}

	 

 
}
