<%@page import="test.cafe.dao.CafeDao"%>
<%@page import="test.cafe.dto.CafeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//글 작성자 얻어내기
	String writer=(String)session.getAttribute("id");
	//폼 전송되는 title, content 읽어내기
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	//CafeDto 에 담기
	CafeDto dto=new CafeDto();
	dto.setWriter(writer);
	dto.setTitle(title);
	dto.setContent(content);
	//DB에 저장
	
	boolean isSuccess=CafeDao.getInstance().insert(dto);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/cafe/private/insert.jsp</title>
</head>
<body>
	<div class="container">
		<%if(isSuccess){ %>
			<p>
				<%=writer %> 님의 내용이 저장되었습니다.
				<a href="${pageContext.request.contextPath }/cafe/list.jsp">목록보기</a>
			</p>
		<%}else{ %>
			<p>
				글 저장 실패!
				<a href="insertform.jsp">다시 작성하러 가기</a>
			</p>
		<%} %>
	</div>
</body>
</html>