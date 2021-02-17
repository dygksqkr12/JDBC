package member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import common.JDBCTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;

import static common.JDBCTemplate.*;

/**
 * Service
 * 1. DriverClass등록(최초1회)
 * 2. Connection객체생성  url, user, password
 * 2.1 자동커밋 false설정
 * -------Dao 요청-------
 * 6. 트랜잭션처리(DML) commit/rollback
 * 7. 자원반납(conn)
 * 
 * Dao
 * 3. PreparedStatement객체 생성(미완성쿼리)
 * 3.1 ? 값대입
 * 4. 실행 : DML(executeUpdate) => int,  DQL(executeQuery) -> ResultSet
 * 4.1 ResultSet -> Java객체 옮겨담기
 * 5. 자원반납(생성역순 rset - pstmt)
 *
 */
public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	public List<Member> selectAll() {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectAll(conn);
		close(conn);
		
		return list;
	}

	/**
	 * 1. DriverClass등록(최초1회)
	 * 2. Connection객체생성  url, user, password
	 * 2.1 자동커밋 false설정
	 * -------Dao 요청-------
	 * 6. 트랜잭션처리(DML) commit/rollback
	 * 7. 자원반납(conn)
	 */
	public List<Member> selectAll_() {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "student";
		String password = "student";
		Connection conn = null;
		List<Member> list = null;
		
		try {
			//1. DriverClass등록(최초1회)
			Class.forName(driverClass);
			
			//2. Connection객체생성  url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//2.1 자동커밋 false설정
			conn.setAutoCommit(false);
			
			//-------Dao 요청-------
			//Connection객체 전달
			list = memberDao.selectAll(conn);
			//6. 트랜잭션처리(DML) commit/rollback
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//7. 자원반납(conn)
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

}
