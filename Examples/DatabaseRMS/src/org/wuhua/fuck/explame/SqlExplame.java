
package org.wuhua.fuck.explame;

import org.wuhua.fuck.core.Query;
import org.wuhua.fuck.core.ResultSet;
import org.wuhua.fuck.core.SQLException;
import org.wuhua.fuck.core.impl.QueryBuilder;

/**
 * <b>������SqlExplame.java</b> </br> 
 * ��д����: 2007-2-9 <br/>
 * ����������������������Ĵ󲿷ֲ��������� <br/>
 * Demo: <br/>
 * Bug: <br/>
 * 
 * ���������� ��<br/> 
 * ������� ��<br/> 
 * ���˵�� ��<br/>
 * 
 * @author wuhua </br> <a href="mailto:rrq12345@163.com">rrq12345@163.com</a>
 */
public class SqlExplame {
	
	/**
	 * ������;
	 * @param arg
	 * @throws SQLException
	 */
	public static  void  test() throws SQLException{
		
		//Query q = QueryBuilder.builder("delete wuhua ");
		//q.execute();
		//dropTable();
		try{
			createTable();
		}catch(SQLException s){
			s.printStackTrace();
		}
		//insert();
		//select();
		
		//update();
		//delete();
		drop();
	}

	private static void drop() throws SQLException {
		Query q = QueryBuilder.builder("drop wuhua  ");
		q.execute();
		
	}

	private static void createTable() throws SQLException {
		Query q = QueryBuilder.builder("create wuhua f_id f_name f_sex ");
		q.execute();
		
	}
	
	private static void insert() throws SQLException{
		Query q = QueryBuilder.builder("insert wuhua f_id f_sex f_name ");
		q.setString("f_id", "21");
		q.setString("f_sex", "��");
		q.setString("f_name", "·����������");
		q.execute();
	}
	
	private static void select() throws SQLException{
		Query q = QueryBuilder.builder("select wuhua f_name ·���������� ");
		 
		ResultSet rs = q.executeQuery();
		
		while(rs.next()){
			System.out.println("F_ID=" + rs.getString("f_id"));
			System.out.println("F_SEX=" + rs.getString("f_sex"));
			System.out.println("F_NAME=" + rs.getString("f_name"));
			
		}
	}
	
	private static void delete()throws SQLException{
		Query q = QueryBuilder.builder("delete wuhua f_name ·���������� ");
		 
		ResultSet rs = q.executeQuery();
		
		 
	}
	
	private static void update()throws SQLException{
		Query q = QueryBuilder.builder("update wuhua f_name ·���������� ");
		q.setString("f_name", "wuhua");
		q.setString("f_sex", "sex");
		ResultSet rs = q.executeQuery();
		//�������ٲ���
		select();
		 
	}
	
}
