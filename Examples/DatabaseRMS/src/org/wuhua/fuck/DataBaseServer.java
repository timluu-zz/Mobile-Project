
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
 * <b>������DataBaseServer.java</b> </br> 
 * ��д����: 2007-2-7 <br/>
 * ���������������ݵķ�������������ݿ���� <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public final class DataBaseServer {
	
	private static Logger logger = Logger.getLogger("DataBaseServer");
	
	public final static String DATAMETA = "matameta";
	static RecordStore datameta = null;
	
	/**
	 * �洢Ԫ������Դ
	 */
	static Hashtable hashmeta;
		
	/**
	 * �洢�����Դ
	 * ÿ�������һ������,
	 * ����������Ԫ���ݱ����һ�����뵽���table��
	 */
	static Hashtable table;
	
	/**
	 * ��ʼ����Դ
	 */
	static{
		hashmeta = new Hashtable();
		table = new Hashtable();
	}
	
	/**
	 * �������񣬲�����matameta��
	 * ��ȡԪ���ݣ�����ȡ���ڱ���ֶ�����
	 *
	 */
	public final static  void start() throws RmsAccessException{
		datameta = createTable(DATAMETA);
		table.put(DATAMETA, datameta);
		
		loadMetaData();
	}
	
	/**
	 * ��������
	 */
	public final static void restart()throws RmsAccessException{
		loadMetaData();
	}
	
	/**
	 * �����ݱ���뻺��
	 * @param key
	 * @param rs
	 */
	public final static void putTableToHashtable(String key, RecordStore rs ){
		table.put(key, rs);	
	}
	
	
	/**
	 * �����ݱ���뻺��
	 * @param key
	 * @param rs
	 */
	public final static void removeTableFormHashtable(String key){
		table.remove(key);
	}
	
	/**
	 * ����Ԫ���ݣ���Ϊ���ݱ������byte��ʽ������
	 * �����ڶ��ڵ���ÿ����¼�ĸ�ʽ�� tableName ��column1��coulu2��indexName��colummName����key��keyIndex ��key��keyIndex
	 * @throws RecordStoreNotOpenException 
	 *
	 */
	public  static  void   loadMetaData() {
		logger.debug("�������ݿ⣬������Ԫ���ݸ�����");
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
		logger.debug("�������ݿ�ɹ�������Ԫ���ݳɹ���");
		
	}
	
	public static final void modifyDataFromId(String tableName, int id, byte[] values) throws SQLException{
		try {
			findTable(tableName).setRecord(id, values, 0, values.length);
			logger.debug("���� " + tableName + " id " + id + " �ɹ�" + new String(values));
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (InvalidRecordIDException e) {			
			e.printStackTrace();
			throw SQLError.createSQLException("����ID : " + id + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		} catch (RecordStoreFullException e) {
			 
			e.printStackTrace();
		} catch (RecordStoreException e) {
			 
			e.printStackTrace();
		} 
	}
	/**
	 * ɾ��ָ��id������
	 * @param name
	 * @param id
	 * @throws SQLException
	 */
	public static final void deleteDataFromForId(String tableName, int id) throws SQLException{
		try {
			findTable(tableName).deleteRecord(id);
			logger.debug("�� " + tableName + " ɾ�� " + id + " �ɹ� ");
		} catch (RecordStoreNotOpenException e) {
			 
			e.printStackTrace();
		} catch (InvalidRecordIDException e) {
			throw SQLError.createSQLException("����ID : " + id + " is not exists!", "Sql State : " + SQLError.SQL_STATE_BASE_TABLE_NOT_FOUND);
		} catch (RecordStoreException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �������ݴ�key�� value��Ӧ�����ݼ�
	 * ���ҵ�ԭ���ǣ�
	 * ���ݱ����ʽ�ǣ�data1!:?data2:....
	 * ��metadata�����в�ѯ�Ƿ��������ؼ��֣�������������׳��쳣
	 * �ҵ��ؼ��ֵ�ʱ�򣬲�ѯ����ؼ����ڱ��е�λ������
	 * ����������������������������ֶ�֮���׳��쳣
	 * @param name
	 * @param key
	 * @param value
	 * @return -- ����һ��hashtable ������Ľṹ��1 ���� hashtable��row����2�� 
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

	//�������ݵ�ʵ��
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
					
					//һ���ǳ���Ҫ������
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
	 * ���˸о�������ȡ����ʵ����̫������
	 * Ӧ�ò���ö�ٵķ�ʽ��ȡ
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
	 * ����Ԫ���ݣ������浽ɢ�б���
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
	 * ͨ�������Ʋ�ѯ�˱��Ԫ�����Ƿ����
	 * @param name ���� ����
	 * @return
	 */
	public static final MetaData findTableMetadataByName(String name){
		if(hashmeta == null)
			return null;
		 
		return (MetaData) hashmeta.get(name);
	}
	
	/**
	 * ����һ������󣬼���һ�������һ��RecordStore
	 * @param name
	 * @return
	 * @throws RmsAccessException �������������ʱ���׳��쳣
	 */
	public static final RecordStore createTable(String name) throws RmsAccessException{
		try {
			return  RecordStore.openRecordStore(name, true);
		} catch (RecordStoreFullException e) {
			throw new RmsAccessException("�洢�ռ�����");
			 
		} catch (RecordStoreNotFoundException e) {
			throw new RmsAccessException("���ݿ�û���ҵ�");
		} catch (RecordStoreException e) {
			throw new RmsAccessException("����RMS�쳣");
			 
		}
	}
	
	/**
	 * ɾ�����ݱ��
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
	 * �ṩ�����ݵĲ���
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
	 * ͨ����name����RecordStore
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
			throw new NullPointerException("����Ϊ�գ�");
	}
	
	
	public static final void colse(){
		Enumeration es = table.elements();
		while(es.hasMoreElements()){
			RecordStore rs = (RecordStore) es.nextElement();
			try {
				rs.closeRecordStore();
			} catch (RecordStoreNotOpenException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			} catch (RecordStoreException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
		}
	}
	
	public static final void closeTable(String name) throws SQLException{
		//ΪʲôҪ�������أ�
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
