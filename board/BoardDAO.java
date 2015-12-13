package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.oreilly.servlet.MultipartRequest;

public class BoardDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public BoardDAO(){}//�⺻ ������

	//javax.sql.DataSource ���¸� ������ ����
	static DataSource ds;

	static{//static �׸���� �ʱ� ������ �ϴ� ����
		//context.xml���� ������ �����ͼ� ds�� ����
		try{
			//javax.naming ��Ű���� ����Ž���� �̿�
			Context init = new InitialContext();
			ds = (DataSource)init.lookup
							("java:comp/env/jdbc/mysql");
		}catch(NamingException e){
			System.err.println("���� ã�� ����");
		}
	}
	
	// ���� �޼ҵ� : ����̹��˻� -> �α��α��� ����
	public void connect() {
		try {
			con = ds.getConnection();
		} catch (Exception e) {
			System.err.println("���� ���� �߻�");
		}
	}

	// �ڿ��ݳ� �޼ҵ� : ���� �ڿ��� ��� ���
	public void close() {
		try {
			if (rs != null)		rs.close();
			if (ps != null)		ps.close();
			if (con != null)		con.close();
		} catch (SQLException e) {
			System.err.println("�ڿ��ݳ� ����");
		}
	}
	//boolean result = bdao.replyBoard(bdto);
	public boolean replyBoard(BoardDTO bdto) throws SQLException{
		String sql="select "
						  + "(select min(re_step) from board "
						  +"where ref = ? and re_level = ? and re_step > ?) as MIN, "
						  +"(select max(re_step) from board "
						  +"where ref = ? and re_level > ? and re_step > ?) as MAX "
						  +"from dual";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bdto.getRef());
			ps.setInt(2, bdto.getRe_level());
			ps.setInt(3, bdto.getRe_step());
			ps.setInt(4, bdto.getRef());
			ps.setInt(5, bdto.getRe_level());
			ps.setInt(6, bdto.getRe_step());
			
			ps.executeQuery();
			int re_step = 0; //���� re_step ��
			if(rs.next()){//�����Ͱ� ������ - �ش� �Խù��� ����� �ִ�.
				if(rs.getInt("MIN")>0){//1�� ������ ����� �ִٸ�, (int�� 0�̸� null)
					re_step = rs.getInt("MIN");
					
					//re_step �̻��� �۵��� �� 1�� ����
					sql = "update board set "
							+"re_step = re_step + 1 "
							+"where re_step >= ? and ref = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, re_step);
					ps.setInt(2, bdto.getRef());
					ps.executeUpdate();
				}else{//1�� ������ ����� ���ٸ�
					if(rs.getInt("MAX")==0){//�����Ͱ� ������ - ����� ����.
						re_step = bdto.getRe_step()+1;//������ re_step+1
					}else{
						re_step = rs.getInt("MAX")+1;//��� + 1
					}
				}
			}
			String filename = null;
			int filesize=-1;
			if(bdto.getFilename()!=null || bdto.getFilesize()>-1){
				filename = bdto.getFilename();
				filesize = bdto.getFilesize();
			}
			//insert ����
			sql = "insert into board(title, writer, content, regdate, readcount, "
					+ "replyqty, ref, re_step, re_level, filename, filesize) "
					+ "values(?,?,?,NOW(),0,0,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, bdto.getTitle());
			ps.setString(2, bdto.getContent());
			ps.setString(3, bdto.getWriter());
			ps.setInt(4, bdto.getRef());//���� ref
			ps.setInt(5, re_step);//���� re_step
			ps.setInt(6, bdto.getRe_level()+1);//���� re_level+1
			ps.setString(7, filename);
			ps.setInt(8, filesize);
			int result = ps.executeUpdate();
			if(result>0) return true;
			else return false;
		}finally{
			close();
		}
	}
	public boolean insertBoard(MultipartRequest mr) 
															throws SQLException{
		String sql = "select max(ref) from board";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();//����Ʈ�� ������ ����Ʈ �¿� �����
			int ref;
			if(rs.next()){//�����Ͱ� ������
				ref = rs.getInt(1)+1;	//max(ref)+1
			}else{//�����Ͱ� ������
				ref = 1;	//�� ��
			}
			sql = "insert into board(title, writer, content, regdate, readcount, "
					+ "replyqty, ref, re_step, re_level, filename, filesize) "
					+ "values(?,?,?,NOW(),0,0,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, mr.getParameter("title"));
			ps.setString(2, mr.getParameter("content"));
			ps.setString(3, mr.getParameter("writer"));
			ps.setInt(4, ref);//ref
			ps.setInt(5, 0);//re_step
			ps.setInt(6, 0);//re_level
			ps.setString(7, mr.getFilesystemName("filename"));
			int filesize = 0;
			java.io.File file = mr.getFile("filename");
			if(file != null){
				filesize = (int)file.length();
			}
			ps.setInt(8, filesize);
			int result = ps.executeUpdate();
			if(result>0) return true;
			else return false;
		}finally{
			close();
		}
	}
	//boolean result = bdao.insertBoard(bdto);
	//boolean insertBoard(BoardDTO)
	public boolean insertBoard(BoardDTO bdto)
												throws SQLException{
		String sql = "select max(ref) from board";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();//����Ʈ�� ������ ����Ʈ �¿� �����
			int ref;
			if(rs.next()){//�����Ͱ� ������
				ref = rs.getInt(1)+1;	//max(ref)+1
			}else{//�����Ͱ� ������
				ref = 1;	//�� ��
			}
			String filename = null;
			int filesize=-1;
			if(bdto.getFilename()!=null || bdto.getFilesize()>-1){
				filename = bdto.getFilename();
				filesize = bdto.getFilesize();
			}
			sql = "insert into board(title, writer, content, regdate, readcount, "
					+ "replyqty, ref, re_step, re_level, filename, filesize) "
					+ "values(?,?,?,NOW(),0,0,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, bdto.getTitle());
			ps.setString(2, bdto.getContent());
			ps.setString(3, bdto.getWriter());
			ps.setInt(4, ref);//ref
			ps.setInt(5, 0);//re_step
			ps.setInt(6, 0);//re_level
			ps.setString(7, filename);
			ps.setInt(8, filesize);
			int result = ps.executeUpdate();
			if(result>0) return true;
			else return false;
		}finally{
			close();
		}
	}
	//startRow ���� endRow���� �������� listBoard()
	//list�� ������ ��ȣ�� ���� �ٸ��� ����.
	public ArrayList<BoardDTO> listBoard(int start, int end) throws SQLException{
		String sql = "select * from board "
					+"order by ref desc, re_step asc limit ?, ?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			ArrayList<BoardDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	//ArrayList<BoardDTO> list = listBoard();
	public ArrayList<BoardDTO> listBoard()
												throws SQLException{
		String sql="select * from board order by num desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<BoardDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	public ArrayList<BoardDTO> searchBoard
		(String search, String searchString, int start, int end) throws SQLException{
		String sql = "select * from board where " + search + " like ? "
					+"order by ref desc, re_step asc "
					+"limit ?, ?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+searchString+"%");//����
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			ArrayList<BoardDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	//ArrayList<BoardDTO> list = searchBoard(search, searchString);
	public ArrayList<BoardDTO> searchBoard
		(String search, String searchString) throws SQLException{
		String sql="select * from board "
				+ "where "+search+" like ?"
				+ "order by num desc";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+searchString+"%");//����
			rs = ps.executeQuery();
			ArrayList<BoardDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	//ArrayList<BoardDTO> list = makeList(rs);
	public ArrayList<BoardDTO> makeList(ResultSet rs)
												throws SQLException{
		ArrayList<BoardDTO> list = 
									new ArrayList<BoardDTO>();
		while(rs.next()){
			int num = rs.getInt("num");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String writer = rs.getString("writer");
			String regdate = rs.getString("regdate");			
			int readcount = rs.getInt("readcount");
			int replyqty = rs.getInt("replyqty");
			int ref = rs.getInt("ref");
			int re_step = rs.getInt("re_step");
			int re_level = rs.getInt("re_level");
			String filename = rs.getString("filename");
			int filesize = rs.getInt("filesize");
			
			BoardDTO bdto = new BoardDTO(num , title, 	content, 
					writer, regdate, readcount, replyqty, 
					ref, re_step, re_level, 
					filename, filesize); 
			
			list.add(bdto);
		}
		return list;
	}
	
	//BoardDTO bdto = bdao.getBoard(no);
	public BoardDTO getBoard(int num) throws SQLException{
		String sql="select * from board where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			ArrayList<BoardDTO> list = makeList(rs);
			if(list == null||list.size()==0) return null;
			BoardDTO bdto = list.get(0);
			return bdto;
		}finally{
			close();
		}
	}
	
	//bdao.plusCount(no);
	public void plusCount(int num) throws SQLException{
		String sql="update board set "
				+ "readcount = readcount+1 where num = ?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
		}finally{
			close();
		}
	}
	
	//boolean result = bdao.deleteBoard(no);
	public boolean deleteBoard(int num) throws SQLException{
		String sql = "delete board where num = ?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			int result = ps.executeUpdate();
			if(result>0) return true;
			else return false;
		}finally{
			close();
		}
	}
	
	//boolean result = bdao.editBoard(bdto);
	public boolean editBoard(BoardDTO bdto) throws SQLException{
		String sql ="update board set title=?, content=? "
												+ "where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, bdto.getTitle());
			ps.setString(2, bdto.getContent());
			ps.setInt(3, bdto.getNum());
			int result = ps.executeUpdate();
			if(result>0) return true;
			else return false;
		}finally{
			close();
		}
	}
	public void plusReply(int conum) throws SQLException{
		String sql = "update board set replyqty=replyqty+1 where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, conum);
			ps.executeUpdate();
		}finally{
			close();
		}
	}
	//��� ���
	public boolean insertReply(ReplyDTO rdto) throws SQLException{
		String sql = "insert into reply(name, content, regdate, conum) values("
				+ "?,?,NOW(),?)";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, rdto.getWriter());
			ps.setString(2, rdto.getContent());
			ps.setInt(3, rdto.getConum());
			int result = ps.executeUpdate();
			if(result>0){
				return true;
			}
			else return false;
		}finally{
			close();
		}
	}
	
	//��� ���
	public ArrayList<ReplyDTO> listReply(int conum) throws SQLException{
		String sql = "select * from reply where conum=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setInt(1, conum);
			rs = ps.executeQuery();
			ArrayList<ReplyDTO> list = 
							new ArrayList<ReplyDTO>();
			while(rs.next()){
				int num = rs.getInt(1);
				String writer = rs.getString(2);
				String content = rs.getString(3);
				String regdate = rs.getString(4);
				//int conum2 = rs.getInt(5);
				
				ReplyDTO rdto = new ReplyDTO(num, writer, content, regdate, conum);
				list.add(rdto);
			}
			return list;
		}finally{
			close();
		}
	}
	public int getBoardCount() throws SQLException{
		String sql = "select count(*) from board";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		}finally{
			close();
		}
	}
}























