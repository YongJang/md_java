package food;


import java.sql.*;
import java.text.*;
import java.util.*;

public class CartDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DBConnectionMgr pool;
	
	public CartDAO(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : Ŀ�ؼ� ���� ����");
		}
	}
	
	public void connect(){
		try{
			con = pool.getConnection();
		}catch(Exception e){
			System.out.println("���� ���� �߻�");
		}
	}
	
	public void close(){
		try{
			if(pool != null)
				pool.freeConnection(con,ps,rs);
		}catch(Exception e){
			System.out.println("�ڿ� �ݳ� ����");
		}
	}
	
	public boolean isLike(String num,String id) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		String sql = "select * from Cart where num=? and id=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			ps.setString(2, id);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			//CartTO regBean = list.get(0);
			if(list.size()!=0){
				return false;
						// ���ƿ� ����
			}else{
						// ���ƿ� ����
				return true;
			}
			
		}finally{
			close();
		}
	}
	
	public Vector<CartDTO> getFood(String num) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		String sql = "select * from Cart where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<CartDTO> getCartbyNumandID(String num,String id) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		String sql = "select * from Cart where num=? and id=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			ps.setString(2, id);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<CartDTO> getCartbyNum(String num) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		String sql = "select * from Cart where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public int getCartbyId(String id) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = id;
		String sql = "select * from Cart where id=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			return list.size();
		}finally{
			close();
		}
	}
	
	public boolean inOut(String num,String id) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		int temp = Integer.parseInt(num);
		String sql = "select * from Cart where num=? and id=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			ps.setString(2, id);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			//CartTO regBean = list.get(0);
			if(list.size()!=0){
				sql = "delete from Cart where num=? and id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, tempNum);
				ps.setString(2, id);
				int delete = 0;
				delete = ps.executeUpdate();
						// ���ƿ� ����
			}else{
				sql = "insert into Cart (num, id, quantity) values(?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, temp);
				ps.setString(2, id);
				ps.setInt(3, 1);
				int insert = 0;
				insert = ps.executeUpdate();
						// ���ƿ� ����
			}
			return true;
			
		}finally{
			close();
		}
	}
	
	public Vector<CartDTO> getLikeList() throws SQLException{
		String sql = "select * from Cart";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<CartDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<CartDTO> makeList(ResultSet rs) throws SQLException{
		// ResultSet -> Vector<CartTO> ��ȯ
		Vector<CartDTO> list = new Vector<CartDTO>();
		while(rs.next()){
			// 1. CartTO�� ���� �غ�
			CartDTO tempBean = new CartDTO();
			// 2. rs ������ ����
			tempBean.setNum(rs.getInt("num"));
			tempBean.setId(rs.getString("id"));
			tempBean.setQuantity(rs.getInt("quantity"));
			// 3. list�� �߰�
			list.addElement(tempBean);
		}
		return list;
	}
	
}


