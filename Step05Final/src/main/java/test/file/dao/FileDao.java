package test.file.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import test.file.dto.FileDto;
import test.util.DbcpBean;

public class FileDao {
	private static FileDao dao;
	private FileDao() {}
	public static FileDao getInstance() {
		if(dao==null) {
			dao=new FileDao();
		}
		return dao;
	}
	//전체 글의 갯수를 리턴해주는 메소드
	public int getCount() {
		//글의 갯수를 담을 지역변수
		int count=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DbcpBean 객체를 이용해서 Connection 객체를 얻어온다(Connection Pool에서 얻어오기)
			conn = new DbcpBean().getConn();
			//실행할 sql문
			String sql = "SELECT MAX(ROWNUM) AS num FROM board_file";
			pstmt = conn.prepareStatement(sql);
			//sql 문이 미완성이라면 여기서 완성

			//select 문 수행하고 결과값 받아오기
			rs = pstmt.executeQuery();
			//반복문 돌면서 ResultSet 에 담긴 내용 추출
			while (rs.next()) {
				count=rs.getInt("num");
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
		return count;
	}
	//페이지에 해당하는 목록만 리턴하는 메소드
	public List<FileDto> getList(FileDto dto){
		//파일 목록을 담을 ArrayList 객체 생성
		List<FileDto> list=new ArrayList<FileDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DbcpBean 객체를 이용해서 Connection 객체를 얻어온다(Connection Pool에서 얻어오기)
			conn = new DbcpBean().getConn();
			//실행할 sql문
			String sql = "SELECT * FROM (SELECT result1.*, ROWNUM AS rnum"
					+ " FROM (SELECT num, writer, title, orgFileName, fileSize, regdate"
					+ " FROM board_file"
					+ " ORDER BY num DESC) result1)"
					+ " WHERE rnum BETWEEN ? AND ? ";
			pstmt = conn.prepareStatement(sql);
			//sql 문이 미완성이라면 여기서 완성
			pstmt.setInt(1, dto.getStartRowNum());
			pstmt.setInt(2, dto.getEndRowNum());
			//select 문 수행하고 결과값 받아오기
			rs = pstmt.executeQuery();
			//반복문 돌면서 ResultSet 에 담긴 내용 추출
			while (rs.next()) {
		           //FileDto 객체에 select 된 row 하나의 정보를 담고
		           FileDto tmp=new FileDto();
		           tmp.setNum(rs.getInt("num"));
		           tmp.setWriter(rs.getString("writer"));
		           tmp.setTitle(rs.getString("title"));
		           tmp.setOrgFileName(rs.getString("orgFileName"));
		           tmp.setFileSize(rs.getLong("fileSize"));
		           tmp.setRegdate(rs.getString("regdate"));
		           //ArrayList 객체에 누적 시킨다.
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
	//파일을 삭제하는 메소드
	public boolean delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "DELETE FROM board_file"
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
	//파일 하나의 정보를 리턴해주는 메소드
	   public FileDto getData(int num) {
	      FileDto dto=null;
	      //필요한 객체의 참조값을 담을 지역변수 미리 만들기
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         //DbcpBean 객체를 이용해서 Connection 객체를 얻어온다(Connection Pool 에서 얻어오기)
	         conn = new DbcpBean().getConn();
	         //실행할 sql 문 
	         String sql = "SELECT writer, title, orgFileName, saveFileName, fileSize, regdate"
	               + " FROM board_file"
	               + " WHERE num=?";
	         pstmt = conn.prepareStatement(sql);
	         //sql 문이 미완성이라면 여기서 완성
	         pstmt.setInt(1, num);
	         //select 문 수행하고 결과값 받아오기
	         rs = pstmt.executeQuery();
	         //반복문 돌면서 ResultSet 에 담긴 내용 추출
	         while (rs.next()) {
	            dto=new FileDto();
	            dto.setNum(num);
	            dto.setWriter(rs.getString("writer"));
	            dto.setTitle(rs.getString("title"));
	            dto.setOrgFileName(rs.getString("orgFileName"));
	            dto.setSaveFileName(rs.getString("saveFileName"));
	            dto.setFileSize(rs.getLong("fileSize"));
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
	//파일 목록을 리턴하는 메소드
	public List<FileDto> getListAll(){
		//파일 목록을 담을 ArrayList 객체 생성
		List<FileDto> list=new ArrayList<FileDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DbcpBean 객체를 이용해서 Connection 객체를 얻어온다(Connection Pool에서 얻어오기)
			conn = new DbcpBean().getConn();
			//실행할 sql문
			String sql = "SELECT num, writer, title, orgFileName, saveFileName, fileSize, regdate"
					+ " FROM board_file"
					+ " ORDER BY num DESC";
			pstmt = conn.prepareStatement(sql);
			//sql 문이 미완성이라면 여기서 완성

			//select 문 수행하고 결과값 받아오기
			rs = pstmt.executeQuery();
			//반복문 돌면서 ResultSet 에 담긴 내용 추출
			while (rs.next()) {
	            //FileDto 객체에 select 된 row 하나의 정보를 담고
	            FileDto tmp=new FileDto();
	            tmp.setNum(rs.getInt("num"));
	            tmp.setWriter(rs.getString("writer"));
	            tmp.setTitle(rs.getString("title"));
	            tmp.setOrgFileName(rs.getString("orgFileName"));
	            tmp.setFileSize(rs.getLong("fileSize"));
	            tmp.setRegdate(rs.getString("regdate"));
	            //ArrayList 객체에 누적 시킨다.
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
	
	//업로드된 파일 정보를 DB 에 저장하는 메소드
	public boolean insert(FileDto dto) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      int rowCount = 0;
	      try {
	         conn = new DbcpBean().getConn();
	         String sql = "INSERT INTO board_file"
	               + " (num, writer, title, orgFileName, saveFileName, fileSize, regdate)"
	               + " VALUES(board_file_seq.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
	         pstmt = conn.prepareStatement(sql);
	         // ? 에 바인딩할게 있으면 해주고
	         pstmt.setString(1, dto.getWriter());
	         pstmt.setString(2, dto.getTitle());
	         pstmt.setString(3, dto.getOrgFileName());
	         pstmt.setString(4, dto.getSaveFileName());
	         pstmt.setLong(5, dto.getFileSize());
	         // INSERT OR UPDATE OR DELETE 문을 수행하고 수정되거나, 삭제되거나, 추가된 ROW 의 갯수 리턴 받기
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
	      if (rowCount > 0) {
	         return true;
	      } else {
	         return false;
	      }
	   }
}
