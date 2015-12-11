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
			System.out.println("Error : Ŀ�ؼ� ���� ����");
		}
	}

	public void connect() {
		try {
			con = pool.getConnection();
		} catch (Exception e) {
			System.err.println("���� ���� �߻�");
		}
	}

	public void close() {
		try {
			if (pool != null)
				pool.freeConnection(con, ps, rs);
		} catch (Exception e) {
			System.err.println("�ڿ��ݳ� ����");
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
			MemberDTO regBean = list.get(0);// 0���� ����
			return regBean;
		} finally {
			close();
		}
	}

	public boolean insertMember(MemberDTO bean) throws SQLException {
		// 1. ���̵� table�� �ִ��� Ȯ��
		// 2. ������ �߰�(������ �ߺ�)
		String sql = "select id from member where id = ?";
		try {
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getId());
			rs = ps.executeQuery();
			// ����� rs�� �ִ�. ���� �ñ��Ѱ� rs�� �����Ͱ� �ֳľ���
			if (rs.next()) {
				return false;// �ߺ�
			}
			// ���� ó��
			sql = "insert into member(id, passwd, name, email, phone, zipcode, address, regdate)"
					+ "values" + "(?,?,?,?,?,?,?,?)";
			// sql���� �ٲ�����Ƿ� ps�� �����
			ps = con.prepareStatement(sql);
			// ����ǥ 9��
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
			// sql��Ű������ Date Ŭ������ �����Ƿ�
			// ���� �����Ͽ� ��ü ����			
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
			if (!rs.next()) {// �����Ͱ� ���ٸ�
				return false;// ��й�ȣ ����
			}
			// �����Ͱ� �ִٸ� - ��й�ȣ ��ġ
			sql = "update member set passwd=?, name=?, "
					+ "email=?, phone=?, zipcode=?, "
					+ "address=?, where id=?";
			ps = con.prepareStatement(sql);
			// ����ǥ 9��
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
	      // ResultSet -> Vector<MemDTO> ��ȯ
	      Vector<MemberDTO> list = new Vector<MemberDTO>();
	      while (rs.next()) {
	         // 1. MemDTO�� ���� �غ�
	    	  MemberDTO tempBean = new MemberDTO();
	         // 2. rs������ ����
	         tempBean.setNum(rs.getInt("num"));	         
	         tempBean.setId(rs.getString("id"));
	         tempBean.setPasswd(rs.getString("passwd"));
	         tempBean.setName(rs.getString("name"));
	         tempBean.setEmail(rs.getString("email"));
	         tempBean.setPhone(rs.getString("phone"));
	         tempBean.setZipcode(rs.getString("zipcode"));
	         tempBean.setAddress(rs.getString("address"));
	         tempBean.setRegdate(rs.getString("regdate"));              

	         // 3. list�� �߰�
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
