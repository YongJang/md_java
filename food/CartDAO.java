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
			System.out.println("Error : 커넥션 연결 실패");
		}
	}
	
	public void connect(){
		try{
			con = pool.getConnection();
		}catch(Exception e){
			System.out.println("연결 오류 발생");
		}
	}
	
	public void close(){
		try{
			if(pool != null)
				pool.freeConnection(con,ps,rs);
		}catch(Exception e){
			System.out.println("자원 반납 오류");
		}
	}
	
	public boolean isLike(String num,String id) throws SQLException{	// num 은 글번호, id는 회원 id
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
						// 좋아요 해제
			}else{
						// 좋아요 선택
				return true;
			}
			
		}finally{
			close();
		}
	}
	
	public Vector<CartDTO> getFood(String num) throws SQLException{	// num 은 글번호, id는 회원 id
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
	
	public Vector<CartDTO> getCartbyNumandID(String num,String id) throws SQLException{	// num 은 글번호, id는 회원 id
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
	
	public Vector<CartDTO> getCartbyNum(String num) throws SQLException{	// num 은 글번호, id는 회원 id
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
	
	public int getCartbyId(String id) throws SQLException{	// num 은 글번호, id는 회원 id
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
	
	public boolean inOut(String num,String id) throws SQLException{	// num 은 글번호, id는 회원 id
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
						// 좋아요 해제
			}else{
				sql = "insert into Cart (num, id, quantity) values(?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, temp);
				ps.setString(2, id);
				ps.setInt(3, 1);
				int insert = 0;
				insert = ps.executeUpdate();
						// 좋아요 선택
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
		// ResultSet -> Vector<CartTO> 변환
		Vector<CartDTO> list = new Vector<CartDTO>();
		while(rs.next()){
			// 1. CartTO로 포장 준비
			CartDTO tempBean = new CartDTO();
			// 2. rs 데이터 추출
			tempBean.setNum(rs.getInt("num"));
			tempBean.setId(rs.getString("id"));
			tempBean.setQuantity(rs.getInt("quantity"));
			// 3. list에 추가
			list.addElement(tempBean);
		}
		return list;
	}
	
}


