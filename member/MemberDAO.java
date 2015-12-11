package member;

import java.sql.*;
import java.text.*;
import java.util.*;

import member.*;

public class MemberDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DBConnectionMgr pool;

	public MemberDAO() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			System.out.println("Error : 커넥션 연결 실패");
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

	public boolean PassCheck(String cust_id, String cust_passwd) {
		boolean result = false;
		try {
			connect();
			String query = "select count(*) from member where id = ? and passwd = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, cust_id);
			ps.setString(2, cust_passwd);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0)
				result = true;
		} catch (Exception ex) {
			System.out.println("Exception" + ex);
		} finally {
			close();
		}
		return result;
	}

	public MemberDTO getMember(String id) throws SQLException {
		String sql = "select * from member where id=?";
		try {
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			Vector<MemberDTO> list = makeList(rs);
			MemberDTO regBean = list.get(0);// 0번방 추출
			return regBean;
		} finally {
			close();
		}
	}

	public boolean insertMember(MemberDTO bean) throws SQLException {
		// 1. 아이디가 table에 있는지 확인
		// 2. 없으면 추가(있으면 중복)
		String sql = "select id from member where id = ?";
		try {
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getId());
			rs = ps.executeQuery();
			// 결과는 rs에 있다. 지금 궁금한건 rs에 데이터가 있냐없냐
			if (rs.next()) {
				return false;// 중복
			}
			// 가입 처리
			sql = "insert into member(id, passwd, name, email, phone, zipcode, address, regdate)"
					+ "values" + "(?,?,?,?,?,?,?,?)";
			// sql문이 바뀌었으므로 ps를 재생성
			ps = con.prepareStatement(sql);
			// 물음표 9개
			/*	private int num; //auto increment
				private String id;
				private String passwd;
				private String name;
				private	String email;
				private String phone;
				private String zipcode;					
				private String address;
				private String regdate;*/
			ps.setString(1, bean.getId());
			ps.setString(2, bean.getPasswd());	
			ps.setString(3, bean.getName());
			ps.setString(4, bean.getEmail());
			ps.setString(5, bean.getPhone());	
			ps.setString(6, bean.getZipcode());
			ps.setString(7, bean.getAddress());
			// sql패키지에도 Date 클래스가 있으므로
			// 직접 지정하여 객체 생성			
			java.util.Date date = new java.util.Date(); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
			String joindate = sdf.format(date);			 
			ps.setString(8, joindate);
					
			
			

			int result = ps.executeUpdate();
			if (result > 0)
				return true;
			else
				return false;
		} finally {
			close();
		}
	}

	public boolean updateMemberList(MemberDTO bean) throws SQLException {
		String sql = "select * from member " + "where id=?";
		try {
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getId());
			rs = ps.executeQuery();
			if (!rs.next()) {// 데이터가 없다면
				return false;// 비밀번호 오류
			}
			// 데이터가 있다면 - 비밀번호 일치
			sql = "update member set passwd=?, name=?, "
					+ "email=?, phone=?, zipcode=?, "
					+ "address=?, where id=?";
			ps = con.prepareStatement(sql);
			// 물음표 9개
			/*	private int num; //auto increment
			private String id;
			private String passwd;
			private String name;
			private	String email;
			private String phone;
			private String zipcode;					
			private String address;
			private String regdate;*/
			ps.setString(1, bean.getPasswd());
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getEmail());
			ps.setString(4, bean.getPhone());
			ps.setString(5, bean.getZipcode());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getId());
			int result = ps.executeUpdate();
			if (result > 0)
				return true;
			else
				return false;
		} finally {
			close();
		}
	}

	public Vector<MemberDTO> listMember() throws SQLException {
		String sql = "select * from member order by id asc";
		try {
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<MemberDTO> list = makeList(rs);
			return list;
		} finally {
			close();
		}
	}
	public Vector<MemberDTO> makeList(ResultSet rs) throws SQLException {
	      // ResultSet -> Vector<MemDTO> 변환
	      Vector<MemberDTO> list = new Vector<MemberDTO>();
	      while (rs.next()) {
	         // 1. MemDTO로 포장 준비
	    	  MemberDTO tempBean = new MemberDTO();
	         // 2. rs데이터 추출
	         tempBean.setNum(rs.getInt("num"));	         
	         tempBean.setId(rs.getString("id"));
	         tempBean.setPasswd(rs.getString("passwd"));
	         tempBean.setName(rs.getString("name"));
	         tempBean.setEmail(rs.getString("email"));
	         tempBean.setPhone(rs.getString("phone"));
	         tempBean.setZipcode(rs.getString("zipcode"));
	         tempBean.setAddress(rs.getString("address"));
	         tempBean.setRegdate(rs.getString("regdate"));              

	         // 3. list에 추가
	         list.addElement(tempBean);
	      }
	      return list;
	   }

	public boolean deleteMember(String id, String passwd) throws SQLException {
		String sql = "delete from member where id = ? and passwd = ?";
		try {
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, passwd);
			int result = ps.executeUpdate();
			if (result > 0)
				return true;
			else
				return false;
		} finally {
			close();
		}
	}
}
