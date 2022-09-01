package ch14.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class BoardDao {
	// sigleton
	private static BoardDao instance = new BoardDao();
	private BoardDao() {}
	public static BoardDao getInstance() {
		return instance;
	}
	// database connection pool
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		}catch (Exception e) {
			System.out.println("연결 에러 : "+e.getMessage());
		}
		return conn;
	}
	public List<Board> list(int startRow, int endRow) {
		List<Board> list = new ArrayList<>();
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String sql = "select * from board1 order by num desc"; // 최근에 쓴글부터 목록
//		mysql 최신글부터 읽어서 시작번호 - 1, 부터 10개
//		sql = "select * from board1 order by num desc limit startㄲRow - 1, 10";
//		oracle : topN
//		String sql = "select * from (select rowNum rn, a.* from ( select * from board1 "+
//				" order by num desc)a) where rn between ? and ?";
		String sql = "select * from (select rowNum rn, a.* from ( select * from board1 "+
				" order by ref desc, re_step)a) where rn between ? and ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setPassword(rs.getString("password"));
				board.setRef(rs.getInt("ref"));
				board.setRe_step(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setDel(rs.getString("del"));
				
				list.add(board);
			}
		}catch (Exception e) {  System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return list;
	}
	public int insert(Board board) {
		int result = 0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into board1 values(?,?,?,?,0,?,?,?,?,sysdate,'n')";
//	게시판에서 글번호룰 읽은 후에 가장 큰번호 + 1을 하면 새로룬 게시글 번호, 그 번호가 null이면 0으로 변경
		String sql2 = "select nvl(max(num),0) + 1 num from board1";
//	글을 읽고 ref가 같고 읽은글보다  re_step가 큰값이 있으면  그 글의 re_step +1
		String sqlUp = "update board1 set re_step = re_step+1 where ref=? and re_step > ?";
		try {
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			rs.next();
			int number = rs.getInt(1);   // getInt("num"); 새로운 글 작성 번호
			pstmt.close();
			if (board.getNum() != 0) { // 답변
				pstmt = conn.prepareStatement(sqlUp);
				pstmt.setInt(1, board.getRef());
				pstmt.setInt(2, board.getRe_step());
				pstmt.executeUpdate();
				pstmt.close();
				board.setRe_level(board.getRe_level() + 1);
				board.setRe_step(board.getRe_step() + 1);
			} else board.setRef(number); // 처음 글 쓸때는 ref와 num 값이 같다
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getPassword());
//			답변글 시작
			pstmt.setInt(6, board.getRef());
			pstmt.setInt(7, board.getRe_step());
			pstmt.setInt(8, board.getRe_level());
//			답변글 종료
			result = pstmt.executeUpdate();
		}catch (Exception e) {  System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return result;
	}
	public Board select(int num) {
		Board board = new Board();
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board1 where num=?"; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setPassword(rs.getString("password"));
				board.setRef(rs.getInt("ref"));
				board.setRe_step(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setDel(rs.getString("del"));
			}
		}catch (Exception e) {  System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return board;
	}
//  void는 반환형이 없음
	public void readCountUpdate(int num) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update board1 set readcount = readcount+1 where num=?"; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch (Exception e) {  System.out.println(e.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
	}
	public int update(Board board) {
		int result = 0;
//		미리 table에 있는 암호를 읽어서 화면에 입력한 암호가 비교하기 위해
		Board board2 = select(board.getNum());
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update board1 set subject=?, writer=?, content=? where num=?"; 
		if (board.getPassword().equals(board2.getPassword())) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getWriter());
				pstmt.setString(3, board.getContent());
				pstmt.setInt(4, board.getNum());
				result = pstmt.executeUpdate();
			}catch (Exception e) {  System.out.println(e.getMessage());
			}finally {
				try{
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {		}
			}
		} else result = -1;  // 암호가 일치하지 않음
		return result;
	}
	public int delete(int num, String password) {
		int result = 0;
//		미리 table에 있는 암호를 읽어서 화면에 입력한 암호가 비교하기 위해
		Board board2 = select(num);
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update board1 set del='y' where num=?"; 
		if (password.equals(board2.getPassword())) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
			}catch (Exception e) {  System.out.println(e.getMessage());
			}finally {
				try{
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {		}
			}
		} else result = -1;  // 암호가 일치하지 않음
		return result;
	}
	public int getTotal() {
		int total = 0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from board1"; 
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		}catch (Exception e) {  System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return total;
	}
}