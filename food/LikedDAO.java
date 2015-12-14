package food;


import java.sql.*;
import java.text.*;
import java.util.*;

public class LikedDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DBConnectionMgr pool;
	
	public LikedDAO(){
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
		String sql = "select * from Liked where num=? and id=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			ps.setString(2, id);
			rs = ps.executeQuery();
			Vector<LikedDTO> list = makeList(rs);
			//LikeDTO regBean = list.get(0);
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
	
	public int getLikes(String num) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		String sql = "select * from Liked where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<LikedDTO> list = makeList(rs);
			return list.size();
		}finally{
			close();
		}
	}
	
	public boolean like(String num,String id) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
		String tempNum = num;
		String sql = "select * from Liked where num=? and id=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			ps.setString(2, id);
			rs = ps.executeQuery();
			Vector<LikedDTO> list = makeList(rs);
			//LikeDTO regBean = list.get(0);
			if(list.size()!=0){
				sql = "delete from Liked where num=? and id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, tempNum);
				ps.setString(2, id);
				int delete = 0;
				delete = ps.executeUpdate();
						// ���ƿ� ����
			}else{
				sql = "insert into Liked (num, id) values(?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1,tempNum);
				ps.setString(2, id);
				int insert = 0;
				insert = ps.executeUpdate();
						// ���ƿ� ����
			}
			return true;
			
		}finally{
			close();
		}
	}
	
	public Vector<LikedDTO> getLikeList() throws SQLException{
		String sql = "select * from Liked";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<LikedDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<LikedDTO> makeList(ResultSet rs) throws SQLException{
		// ResultSet -> Vector<LikeDTO> ��ȯ
		Vector<LikedDTO> list = new Vector<LikedDTO>();
		while(rs.next()){
			// 1. LikeDTO�� ���� �غ�
			LikedDTO tempBean = new LikedDTO();
			// 2. rs ������ ����
			tempBean.setNum(rs.getInt("num"));
			tempBean.setId(rs.getString("id"));
			// 3. list�� �߰�
			list.addElement(tempBean);
		}
		return list;
	}
	
}


