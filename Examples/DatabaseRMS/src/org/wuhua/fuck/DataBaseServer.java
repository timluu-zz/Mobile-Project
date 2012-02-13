
package org.wuhua.fuck;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import org.wuhua.fuck.core.DataCore;
import org.wuhua.fuck.core.MetaData;
import org.wuhua.fuck.core.RmsAccessException;
import org.wuhua.fuck.core.SQLError;
import org.wuhua.fuck.core.SQLException;



/**
 * <b>类名：DataBaseServer.java</b> </br> 
 * 编写日期: 2007-2-7 <br/>
 * 程序功能描述：数据的服务对象，启动数据库服务 <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * 程序变更日期 ：<br/> 
 * 变更作者 ：<br/> 
 * 变更说明 ：<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public final class DataBaseServer {
	
	private static Logger logger = Logger.getLogger("DataBaseServer");
	
	public final static String DATAMETA = "matameta";
	static RecordStore datameta = null;
	
	/**
	 * 存储元数据资源
	 */
	static Hashtable hashmeta;
		
	/**
	 * 存储表格资源
	 * 每个表对于一个表名,
	 * 对于索引于元数据表，则第一个插入到这个table中
	 */
	static Hashtable table;
	
	/**
	 * 初始化资源
	 */
	static{
		hashmeta = new Hashtable();
		table = new Hashtable();
	}
	
	/**
	 * 启动服务，并变量matameta表，
	 * 获取元数据，并获取对于表的字段索引
	 *
	 */
	public final static  void start() throws RmsAccessException{
		datameta = createTable(DATAMETA);
		table.put(DATAMETA, datameta);
		
		loadMetaData();
	}
	
	/**
	 * 重新启动
	 */
	public final static void restart()throws RmsAccessException{
		loadMetaData();
	}
	
	/**
	 * 把数据表放入缓存
	 * @param key
	 * @param rs
	 */
	public final static void putTableToHashtable(String key, RecordStore rs ){
		table.put(key, rs);	
	}
	
	
	/**
	 * 把数据表放入缓存
	 * @param key
	 * @param rs
	 */
	public final static void removeTableFormHashtable(String key){
		table.remove(key);
	}
	
	/**
	 * 载入元数据，因为数据保存的是byte格式，所以
	 * 我现在对于的是每个记录的格式是 tableName ：column1，coulu2：indexName（colummName）：key，keyIndex ：key，keyIndex
	 * @throws RecordStoreNotOpenException 
	 *
	 */
	public  static  void   loadMetaData() {
		logger.debug("启动数据库，并载入元数据跟索引");
		int num=0;
		try {
			num = datameta.getNumRecords();
			logger.debug(num);
		} catch (RecordStoreNotOpenException e1) {		 
			e1.printStackTrace();
		}
		
		byte [] bytes = null;
		for(int i=1; i <= num; i++){	
			try {
				bytes = datameta.getRecord(i);
				putMetaToHashtable(bytes);
			} catch (InvalidRecordIDException e) {
				e.printStackTrace();
			} catch (RecordStoreException e) {			 
				e.printStackTrace();
			}
		}
		logger.debug("启动数据库成功，载入元数据成功！");
		
	}
	
	public static final void modifyDataFromId(String tableName, int id, byte[] values) throws SQLException{
		try {
			findTable(tableName).setRecord(id, values, 0, values.length);
			logger.debug("更新 " + tableName + " id " + id + " 成功" + new String(values));
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (InvalidRecordIDException e) {			
			e.printStackTrace();
			throw SQLError.createSQLException("数据ID : " + id + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		} catch (RecordStoreFullException e) {
			 
			e.printStackTrace();
		} catch (RecordStoreException e) {
			 
			e.printStackTrace();
		} 
	}
	/**
	 * 删除指定id的数据
	 * @param name
	 * @param id
	 * @throws SQLException
	 */
	public static final void deleteDataFromForId(String tableName, int id) throws SQLException{
		try {
			findTable(tableName).deleteRecord(id);
			logger.debug("从 " + tableName + " 删除 " + id + " 成功 ");
		} catch (RecordStoreNotOpenException e) {
			 
			e.printStackTrace();
		} catch (InvalidRecordIDException e) {
			throw SQLError.createSQLException("数据ID : " + id + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		} catch (RecordStoreException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查找数据从key， value对应的数据集
	 * 查找的原则是：
	 * 数据保存格式是：data1!:?data2:....
	 * 从metadata数据中查询是否存在这个关键字，如果不存在则抛出异常
	 * 找到关键字的时候，查询这个关键字在表中的位置索引
	 * 如果发现这个索引大于这个表所有字段之和抛出异常
	 * @param name
	 * @param key
	 * @param value
	 * @return -- 返回一个hashtable ，里面的结构是1 －－ hashtable（row），2， 
	 * @throws SQLException
	 */
	public static final Hashtable findDataFromTableToHashtable(String name, String key, String value) throws SQLException{
		logger.debug("Load Data From Table " + name );
		MetaData meta = findTableMetadataByName(name);
		if(meta == null){
			throw SQLError.createSQLException("Table : " + name + " metadata not exists not find!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_METADATA_NOT_FOUND); 
		}
		
		if(meta.getColumn().get(key) == null){
			throw SQLError.createSQLException("Column : " + key + " not find!", "Sql State : " + SQLError.SQL_STATE_COLUMN_NOT_FIND); 
		}
		
		RecordStore table = findTable(name);
		
		if(table == null){
			throw SQLError.createSQLException("Table : " + name + " not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND); 
		}
		
		return findDataImpl(key, value, meta, table);
		
	 
	}

	//查找数据的实现
	private static Hashtable findDataImpl(String key, String value, MetaData meta, RecordStore table) throws SQLException {
		int keyIndex = 0;
		java.util.Enumeration em = meta.getColumn().elements();
		String[] ma = new String[meta.getColumn().size()];
		int size=0;
		while(em.hasMoreElements()){
			String s = (String) em.nextElement();
			ma[size]=s;
			//logger.debug("KEY:" + s);
			//logger.debug("key:" + key);
			if(s.equals(key)){
				keyIndex = size;
			} 
			size++;
			
		}
		
		
		int num=0;
		try {
			num = table.getNumRecords();
		} catch (RecordStoreNotOpenException e) {
			
		}
	
		Hashtable data = new Hashtable();
		
		int dataIndex=0;
		for(int i=1; i <= num; i++){	
			try {
			 
				String datastring = read(table, i);
				
;				String[] ms = StringUtil.split(datastring, DataCore.SQIL);
				//logger.debug("keyIndex=" + keyIndex);
				if(keyIndex>=ms.length){
					throw SQLError.createSQLException("Unkonw error", "Sql State : " + SQLError.SQL_STATE_OHTER); 
				}
			//	logger.debug("DataString: " + datastring);
				//logger.debug(ms[keyIndex] + "=" + value);
				if(ms[keyIndex].equals(value)){
					
					logger.debug("DataString: " + datastring);
					Hashtable row = new Hashtable();
					
					//一个非常重要的索引
					row.put(DataCore.ROW_INDEX, new Integer(i).toString());
					for(int j=0; j<ms.length; j++){
						row.put(ma[j], ms[j]);
					}
					data.put(new Integer(dataIndex), row);
					dataIndex++;
				}
				
			} catch (InvalidRecordIDException e) {
				//e.printStackTrace();
			} catch (RecordStoreException e) {			 
				//e.printStackTrace();
			} catch (IOException e) {
				 
				//e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * 个人感觉这样读取数据实在是太不好了
	 * 应该采用枚举的方式读取
	 * @param table
	 * @param index
	 * @return
	 * @throws IOException
	 * @throws RecordStoreNotOpenException
	 * @throws InvalidRecordIDException
	 * @throws RecordStoreException
	 */
	private static String read(RecordStore table, int index) throws IOException, RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
		byte[] data = table.getRecord(index);
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		String msg = dis.readUTF();
		bais.close();
		dis.close();
		return msg;
	}

	/**
	 * 分析元数据，并保存到散列表中
	 * @param bytes
	 * @throws RmsAccessException 
	 */
	private static final void putMetaToHashtable(byte[] bytes) throws RmsAccessException{	
		checkNull(hashmeta);
		MetaData metadata = new MetaData(bytes);	
		logger.debug("MetaData: " + metadata.toString());
		hashmeta.put(metadata.getName(), metadata);		
		table.put(metadata.getName(), createTable(metadata.getName()));
	}
	
	/**
	 * 通过表名称查询此表格元数据是否存在
	 * @param name －－ 表名
	 * @return
	 */
	public static final MetaData findTableMetadataByName(String name){
		if(hashmeta == null)
			return null;
		 
		return (MetaData) hashmeta.get(name);
	}
	
	/**
	 * 创建一个表对象，即是一个表对于一个RecordStore
	 * @param name
	 * @return
	 * @throws RmsAccessException －－创建表出错时候抛出异常
	 */
	public static final RecordStore createTable(String name) throws RmsAccessException{
		try {
			return  RecordStore.openRecordStore(name, true);
		} catch (RecordStoreFullException e) {
			throw new RmsAccessException("存储空间已慢");
			 
		} catch (RecordStoreNotFoundException e) {
			throw new RmsAccessException("数据库没有找到");
		} catch (RecordStoreException e) {
			throw new RmsAccessException("访问RMS异常");
			 
		}
	}
	
	/**
	 * 删除数据表格
	 * @param name
	 * @throws SQLException
	 */
	public static final void delete(String name) throws SQLException{
		 
			try {
				 
				RecordStore.deleteRecordStore(name);
			} catch (RecordStoreNotFoundException e) {
				e.printStackTrace();
				throw SQLError.createSQLException("Table : " + name + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
			} catch (RecordStoreException e) {
				 
				e.printStackTrace();
				throw SQLError.createSQLException("Table : " + name + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
			}
 
	}
	/**
	 * 提供对数据的插入
	 * @param object
	 * @throws SQLException 
	 */
	public static final void store(String tableName, byte [] values) throws SQLException{
		try {	
			findTable(tableName).addRecord(values, 0, values.length);
		} catch (RecordStoreNotOpenException e) {		 
			e.printStackTrace();
			throw SQLError.createSQLException("Table " + tableName + " Not Open!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		} catch (RecordStoreFullException e) {	 
			e.printStackTrace();
			throw SQLError.createSQLException("Table : " + tableName + " is FULL!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		} catch (RecordStoreException e) { 
			e.printStackTrace();
			throw SQLError.createSQLException("Table : " + tableName + " is Error!", "Sql State : " + SQLError.SQL_STATE_OHTER);
		}  
	}
	
	public static final byte[] getBytes(String value) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		 
		dos.writeUTF(value.toString());
		byte[] bytes = baos.toByteArray();	
		dos.close();
		baos.close();
		return bytes;
	}
	 
	/**
	 * 通过表name查找RecordStore
	 * @param name
	 * @return
	 */
	public static final RecordStore findTable(String name) throws SQLException{
		checkNull(table);
		RecordStore rs = (RecordStore) table.get(name);
		if(rs == null)
			throw SQLError.createSQLException("Table : " + name + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		 
		return rs;
	}
	private static final void checkNull(Object object){
		if(object == null)
			throw new NullPointerException("对象为空！");
	}
	
	
	public static final void colse(){
		Enumeration es = table.elements();
		while(es.hasMoreElements()){
			RecordStore rs = (RecordStore) es.nextElement();
			try {
				rs.closeRecordStore();
			} catch (RecordStoreNotOpenException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (RecordStoreException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public static final void closeTable(String name) throws SQLException{
		//为什么要关两次呢？
		try {
			findTable(name).closeRecordStore();
			findTable(name).closeRecordStore(); 
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
			throw SQLError.createSQLException("Table : " + name + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		} catch (RecordStoreException e) {
			 
			e.printStackTrace();
			throw SQLError.createSQLException("Table : " + name + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		}
	}
	
	
	
}
