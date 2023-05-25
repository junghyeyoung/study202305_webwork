<%@page import="test.guest.dao.GuestDao"%>
<%@page import="test.guest.dto.GuestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	int num=Integer.parseInt(request.getParameter("num"));
	String writer=request.getParameter("writer");
	String content=request.getParameter("content");
	
	GuestDto dto=new GuestDto();
	dto.setNum(num);
	dto.setWriter(writer);
	dto.setContent(content);
	boolean isSuccess=GuestDao.getInstance().update(dto);
	
	//패스워드가 맞는지 확인하기
	String successPwd = session.getAttribute("pwd").toString();
	if (!successPwd.equals(dto.getPwd())) {
		String cPath=request.getContextPath();
		response.sendRedirect(cPath+"/guest/list.jsp");
	    return;
	} 
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/guest/update.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container mt-5">
		<h3><strong>알림</strong></h3>
		<%if(isSuccess){ %>
			<p class="alert alert-success">
				<strong><%=num %></strong>번의 방명록을 수정했습니다.
				<a class="alert-link" href="list.jsp">목록보기</a>
			</p>
		<%}else{ %>
			<p class="alert alert-danger">
				수정실패!
				<a class="alert-link" href="updateform.jsp?num=<%=num %>">다시 수정</a>
			</p>
		<%} %>
	</div>
</body>
</html>