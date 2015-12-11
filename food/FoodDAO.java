package food;


import java.sql.*;
import java.text.*;
import java.util.*;

public class FoodDAO{
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DBConnectionMgr pool;

	public FoodDAO(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Eoor : 커넥션 연결 실패");
		}
	}

	public void connect() {
		try {
			con = pool.getConnection();
		} catch (Exception e) {
			System.err.println("연결 오류 발생");
		}
	}

	public void close() {
		try {
			if (pool != null)
				pool.freeConnection(con, ps, rs);
		} catch (Exception e) {
			System.err.println("자원반납 오류");
		}
	}

	public FoodDTO getFood(int num) throws SQLException{
		String tempNum = Integer.toString(num);
		String sql = "select * from food where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			FoodDTO regBean = list.get(0);
			return regBean;
		}finally{
			close();
		}
	}

	public Vector<FoodDTO> makeList(ResultSet rs) throws SQLException {
	      	// ResultSet -> Vector<FoodDTO> 변환
	      	Vector<FoodDTO> list = new Vector<FoodDTO>();
	      	while (rs.next()) {
	        // 1. FoodDTO로 포장 준비
	    	FoodDTO tempBean = new FoodDTO();
	       	// 2. rs데이터 추출
	       	tempBean.setNum(rs.getInt("num"));	         
	       	tempBean.setName(rs.getString("name"));
	       	tempBean.setCost(rs.getString("cost"));
	        tempBean.setImg(rs.getString("img"));
	        tempBean.setFilepath(rs.getString("filepath"));
	        tempBean.setType(rs.getString("type"));
	        tempBean.setRegion(rs.getString("region"));
	        tempBean.setCoupon(rs.getInt("coupon"));
	        tempBean.setRate(rs.getInt("rate"));
		tempBean.setRegdate(rs.getString("regdate"));             
	        // 3. list에 추가
	        list.addElement(tempBean);
	      }
	      return list;
	}

}