/********************************************************************
 * 项目名称				：<b>j2me学习 J2me Wap Explorer</b>			<br/>
 * 
 * Copyright 2005-2006 Wuhua. All rights reserved </br>
 *
 * 本程序只用于学习目的，不能用于商业目的。如有需要请联系作者
 ********************************************************************/
package org.wuhua.fuck;

import java.util.Vector;
 


/**
 * <b>类名：sdf.java</b> </br> 
 * 编写日期: 2006-12-25 <br/>
 * 程序功能描述： <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class StringUtil {
	 
	/**
	 * 切割str字符串
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] split(String bufferstr, String regex) {

		if(bufferstr == null)
			return null;
		Vector split = new Vector();

		while (true) // 处理从网络上获得的数据并对其进行处理
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
