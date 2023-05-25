<%@page import="test.guest.dao.GuestDao"%>
<%@page import="test.guest.dto.GuestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   //한글 깨지지 않도록
   request.setCharacterEncoding("utf-8");
   //1. 폼 전송되는 수정할 회원의 정보를 얻어온다.
   int num=Integer.parseInt(request.getParameter("num"));
   String writer=request.getParameter("writer");
   String content=request.getParameter("content");
   String pwd=request.getParameter("pwd");
   
   //2. DB 에 수정 반영한다.
	GuestDto dto=new GuestDto(num, writer, content, pwd, null);
	boolean isSuccess=GuestDao.getInstance().update(dto);
   //3. 결과를 응답한다
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/guest/update.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" />
</head>
<body>
   <div class="container mt-5">
      <h1>알림</h1>
      <%if(isSuccess){ %>
         <p class="alert alert-success">
            <strong><%=writer %></strong> 님이 작성한 글이 수정되었습니다.
            <a class="alert-link" href="list.jsp">목록보기</a>
         </p>
      <%}else{ %>
         <p class="alert alert-danger">
            수정실패!
            <a class="alert-link" href="updateform.jsp?num=<%=num %>">다시 작성</a>
         </p>
      <%} %>
   </div>
</body>
</html>