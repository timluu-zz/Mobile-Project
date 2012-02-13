
package org.wuhua.fuck.core;

import java.util.Hashtable;

import org.wuhua.fuck.Logger;
import org.wuhua.fuck.StringUtil;
 
/**
 * <b>������SQLMetaData.java</b> </br> 
 * ��д����: 2007-2-7 <br/>
 * ��������������SQLԪ���ݵ�֧��<br/>
 * ���˵Ĺ�����,�ṩ�ֶ�,������֧��<br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class MetaData {
	private static Logger logger = Logger.getLogger("MetaData");
	
	private String name;
	
	/**
	 * ͨ��ɢ�б����ʽ�����ֶ�����
	 * ��ѯ��ʱ����ͨ���ֶν��в�ѯ
	 */
	private Hashtable column;
	
	
	/**
	 * �Զ���һ���ؼ��Բ�ѯ��
	 * ���綨�����ֶ�coulumn��ѯ�ģ���ؼ��־�������ֶ���
	 * ����select tablename from f_name=?
	 * �����ȼ���﷨��Ȼ���ټ��ֱ�����ѯf_name�Ƿ����
	 * Ҳ������index�Ƿ�������f_name;
	 * 
	 */
	private Hashtable index; 
	
	/**
	 *  �����ڶ��ڵ���ÿ����¼�ĸ�ʽ�� tableName ��column1��coulu2��indexName��colummName����key��keyIndex ��key��keyIndex
	 * @param bytes
	 * @throws RmsAccessException -- ���������Բ���ʱ�׳��쳣
	 */
	public MetaData(byte[] bytes) throws RmsAccessException{
		checkNull(bytes);
		
		String meta = new String(bytes);
		String[] ms = StringUtil.split(meta, ":");
		if(ms.length < 2){
			throw new RmsAccessException("Ԫ���ݲ�����");
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
	 * �ǵõ���trim();
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
			throw new RmsAccessException("Ԫ���ݲ���Ϊ��");
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
