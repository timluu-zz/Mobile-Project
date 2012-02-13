
package org.wuhua.fuck.core;

import java.util.Hashtable;

import org.wuhua.fuck.Logger;
import org.wuhua.fuck.StringUtil;
 
/**
 * <b>类名：SQLMetaData.java</b> </br> 
 * 编写日期: 2007-2-7 <br/>
 * 程序功能描述：对SQL元数据的支持<br/>
 * 本人的构想是,提供字段,索引的支持<br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class MetaData {
	private static Logger logger = Logger.getLogger("MetaData");
	
	private String name;
	
	/**
	 * 通过散列表的形式保存字段名称
	 * 查询的时候都是通过字段进行查询
	 */
	private Hashtable column;
	
	
	/**
	 * 自定义一个关键自查询，
	 * 比如定义了字段coulumn查询的，则关键字就是这个字段名
	 * 比如select tablename from f_name=?
	 * 则首先检查语法，然后再检查分表，最后查询f_name是否存在
	 * 也就是再index是否存在这个f_name;
	 * 
	 */
	private Hashtable index; 
	
	/**
	 *  我现在对于的是每个记录的格式是 tableName ：column1，coulu2：indexName（colummName）：key，keyIndex ：key，keyIndex
	 * @param bytes
	 * @throws RmsAccessException -- 数据完整性不好时抛出异常
	 */
	public MetaData(byte[] bytes) throws RmsAccessException{
		checkNull(bytes);
		
		String meta = new String(bytes);
		String[] ms = StringUtil.split(meta, ":");
		if(ms.length < 2){
			throw new RmsAccessException("元数据不完整");
		}
		this.name = ms[0].trim();
		putColumnToHashtable(ms[1]);
	//	putIndexKeyToHashtable(ms[2]);
		
		
	}
	
	private void putIndexKeyToHashtable(String key) {
		String[] keys = StringUtil.split(key, ",");
		index = new Hashtable();
		for(int i=0; i < keys.length; i++){
			index.put(keys[i], keys[i]);	 
		}
		
	}

	/**
	 * 记得调用trim();
	 * @param columnStr
	 */
	private void putColumnToHashtable(String columnStr) {
		String[] columns = StringUtil.split(columnStr, ",");
		column = new Hashtable();
		for(int i=0; i < columns.length; i++){
			logger.debug(columns[i]);
			column.put(columns[i].trim(), columns[i].trim());	 
		}
	}

	public void checkNull(byte[] meta) throws RmsAccessException{
		if(meta == null
				&& meta.length<=0)
			throw new RmsAccessException("元数据不能为空");
	}
	public final Hashtable getColumn() {
		return column;
	}



	public final Hashtable getIndex() {
		return index;
	}



	public final String getName() {
		return name;
	}



	public final void setColumn(Hashtable column) {
		this.column = column;
	}



	public final void setIndex(Hashtable index) {
		this.index = index;
	}



	public final void setName(String name) {
		this.name = name;
	}



	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(column.toString());
		//sb.append(index.toString());
		return sb.toString();
	}
}
