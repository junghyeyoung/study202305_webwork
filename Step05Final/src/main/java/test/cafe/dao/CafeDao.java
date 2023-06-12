package test.cafe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import test.cafe.dto.CafeDto;
import test.util.DbcpBean;

public class CafeDao {
	//static 필드
	private static CafeDao dao;
	/*
		static 메소드는 생성자를 호출하지 않고 클래스명으로 바로 호출을 하기 때문에
		메소드 호출전에 무언가 준비작업을 하고 싶다면 static 블럭안에서 하면된다.
		static 블럭은 해당 클래스를 최초로 사용할 때 한번만 실행되기 때문에 초기화 작업을 하기에 적당한 블럭이다.
	*/
	static {
		//객체를 생성해서 static 필드에 저장해 주면
		dao=new CafeDao();
	}
	
	//외부에서 객체 생성하지 못하도록
	private CafeDao() {}
	
	//static 메소드
	public static CafeDao getInstance() {
		//여기서 리턴해주는 값은 null이 아니다.
		return dao;
	}
	
	//글의 갯수를 리턴하는 메소드
	   public int getCount() {
	      int count=0;
	      //필요한 객체를 담을 지역변수를 미리 만들어 둔다. 
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         //Connection Pool 에서 Connection 객체를 하나 얻어온다.
	         conn = new DbcpBean().getConn();
	         //실행할 sql 문의 뼈대 구성하기
	         String sql = "SELECT MAX(ROWNUM) AS num FROM board_cafe";
	         //sql 문의 ? 에 바인딩 할게 있으면 한다.
	         pstmt = conn.prepareStatement(sql);
	         //SELECT 문을 수행하고 결과값을 받아온다.
	         rs = pstmt.executeQuery();
	         //ResultSet 에서 필요한 값을 얻어낸다.
	         if (rs.next()) {
	            count=rs.getInt("num");
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (rs != null)
	               rs.close();
	            if (pstmt != null)
	               pstmt.close();
	            if (conn != null)
	               conn.close(); //Connection Pool 에 Connection 반납하기
	         } catch (Exception e) {
	         }
	      }
	      return count;
	   }
	   
	//글 조회수 올리는 메소드
	public boolean addViewCount(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "UPDATE board_cafe"
					+ " SET viewCount = viewCount + 1"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			//실행할 sql 문이 미완성이라면 여기서 완성
			pstmt.setInt(1, num);
			//sql 문을 수행하고 변화된(추가, 수정, 삭제된) row 의 갯수 리턴 받기
			rowCount = pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		//만일 변화된 row 의 갯수가 0 보다 크면 작업 성공
		if (rowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	//페이지에 해당하는 목록만 리턴하는 메소드(페이징)
	public List<CafeDto> getList(CafeDto dto){
	      
	List<CafeDto> list=new ArrayList<CafeDto>();
	      
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
	        //select 문의 뼈대 구성하기
	        String sql = "SELECT *"
	               + " FROM"
	               + "   (SELECT result1.*, ROWNUM AS rnum"
	               + "   FROM"
	               + "      (SELECT num,writer,title,viewCount, TO_CHAR(regdate, 'YYYY.MM.DD HH24:MI') AS regdate"
	               + "      FROM board_cafe"
	               + "      ORDER BY num DESC) result1)"
	               + " WHERE rnum BETWEEN ?  AND ?";
	        pstmt = conn.prepareStatement(sql);
	        //? 에 값 바인딩 할게 있으면 하기
	        pstmt.setInt(1, dto.getStartRowNum());
	        pstmt.setInt(2, dto.getEndRowNum());
	        //sql 문 수행하고 ResultSet 객체 얻어내기
	        rs = pstmt.executeQuery();
	         
	        while (rs.next()) {
	           //select 된 row 하나의 정보를 CafeDto 객체를 생성해서 담고 
	           CafeDto tmp=new CafeDto();
	           tmp.setNum(rs.getInt("num"));
	           tmp.setWriter(rs.getString("writer"));
	           tmp.setTitle(rs.getString("title"));
	           tmp.setViewCount(rs.getInt("viewCount"));
	           tmp.setRegdate(rs.getString("regdate"));
	           //CafeDto 객체의 참조값을 List 에 누적 시키기
	           list.add(tmp);
	        }

	     } catch (Exception e) {
	        e.printStackTrace();
	     } finally {
	        try {
	           if (rs != null)
	              rs.close();
	           if (pstmt != null)
	              pstmt.close();
	           if (conn != null)
	              conn.close();
	        } catch (Exception e) {
	       }
	     }
	     return list;
	  }
	//파일 목록을 리턴하는 메소드
	public List<CafeDto> getListAll(){
		List<CafeDto> list=new ArrayList<CafeDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DbcpBean 객체를 이용해서 Connection 객체를 얻어온다(Connection Pool에서 얻어오기)
			conn = new DbcpBean().getConn();
			//실행할 sql문
			String sql = "SELECT num, writer, title, content, viewCount, regdate"
					+ " FROM board_cafe"
					+ " ORDER BY num DESC";
			pstmt = conn.prepareStatement(sql);
			//sql 문이 미완성이라면 여기서 완성

			//select 문 수행하고 결과값 받아오기
			rs = pstmt.executeQuery();
			//반복문 돌면서 ResultSet 에 담긴 내용 추출
			while (rs.next()) {
				CafeDto tmp=new CafeDto();
				tmp.setNum(rs.getInt("num"));
				tmp.setWriter(rs.getString("writer"));
				tmp.setTitle(rs.getString("title"));
				tmp.setContent(rs.getString("content"));
				tmp.setViewCount(rs.getInt("viewCount"));
				tmp.setRegdate(rs.getString("regdate"));
				list.add(tmp);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close(); //Connection 이 Connection Pool 에 반납된다.
			} catch (Exception e) {
			}
		}
		return list;
	}
	//파일 하나의 정보를 리턴해주는 메소드
	public CafeDto getData(int num) {
		CafeDto dto=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DbcpBean 객체를 이용해서 Connection 객체를 얻어온다(Connection Pool에서 얻어오기)
			conn = new DbcpBean().getConn();
			//실행할 sql문
			String sql = "SELECT writer, title, content, viewCount, regdate"
					+ " FROM board_cafe"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			//sql 문이 미완성이라면 여기서 완성
			pstmt.setInt(1, num);
			//select 문 수행하고 결과값 받아오기
			rs = pstmt.executeQuery();
			//반복문 돌면서 ResultSet 에 담긴 내용 추출
			while (rs.next()) {
				dto=new CafeDto();
	            dto.setNum(num);
	            dto.setWriter(rs.getString("writer"));
	            dto.setTitle(rs.getString("title"));
	            dto.setContent(rs.getString("content"));
	            dto.setViewCount(rs.getInt("viewCount"));
	            dto.setRegdate(rs.getString("regdate"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close(); //Connection 이 Connection Pool 에 반납된다.
			} catch (Exception e) {
			}
		}
		return dto;
	}
	//파일을 삭제하는 메소드
	public boolean delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "DELETE FROM board_cafe"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			//실행할 sql 문이 미완성이라면 여기서 완성
			pstmt.setInt(1, num);
			//sql 문을 수행하고 변화된(추가, 수정, 삭제된) row 의 갯수 리턴 받기
			rowCount = pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		//만일 변화된 row 의 갯수가 0 보다 크면 작업 성공
		if (rowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	//번호를 이용해서 제목과 내용을 수정하는 메소드
	public boolean update(CafeDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "UPDATE board_cafe"
					+ " SET title=?, content=?"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			//실행할 sql 문이 미완성이라면 여기서 완성
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			//sql 문을 수행하고 변화된(추가, 수정, 삭제된) row 의 갯수 리턴 받기
			rowCount = pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		//만일 변화된 row 의 갯수가 0 보다 크면 작업 성공
		if (rowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	//업로드 된 파일의 정보를 DB에 저장하는 메소드
	public boolean insert(CafeDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "INSERT INTO board_cafe"
					+ " (num, writer, title, content, viewCount, regdate)"
					+ "	VALUES(board_cafe_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			//실행할 sql 문이 미완성이라면 여기서 완성
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			//sql 문을 수행하고 변화된(추가, 수정, 삭제된) row 의 갯수 리턴 받기
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		//만일 변화된 row 의 갯수가 0 보다 크면 작업 성공
		if (rowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
}
