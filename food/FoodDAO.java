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

	public Vector<FoodDTO> getFoodList() throws SQLException{
		String sql = "select * from food";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public int getRatebyNum(int num) throws SQLException{
		String tempNum = Integer.toString(num);
		String sql = "select * from liked where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list.size();
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyRate() throws SQLException{
		String sql = "select * from food order by rate desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyRegdateDesc() throws SQLException{
		String sql = "select * from food order by regdate desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyRegdateAsc() throws SQLException{
		String sql = "select * from food order by regdate asc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyCostDesc() throws SQLException{
		String sql = "select * from food order by cost desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyCostAsc() throws SQLException{
		String sql = "select * from food order by cost asc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyNameAsc() throws SQLException{
		String sql = "select * from food order by name asc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyNameDesc() throws SQLException{
		String sql = "select * from food order by name desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyTypeDesc() throws SQLException{
		String sql = "select * from food order by type desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyTypeAsc() throws SQLException{
		String sql = "select * from food order by type asc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyRegionAsc() throws SQLException{
		String sql = "select * from food order by region asc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListbyRegionDesc() throws SQLException{
		String sql = "select * from food order by region desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListwithCostunder(int cost) throws SQLException{
		String tempNum = Integer.toString(cost);
		String sql = "select * from food where cost<?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<FoodDTO> getFoodListwithName(String name) throws SQLException{
		String tempName = name;
		String sql = "select * from food where name like '"+"%"+tempName+"%'";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<FoodDTO> list = makeList(rs);
			return list;
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