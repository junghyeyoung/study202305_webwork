<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//session scope 에 "nick" 이라는 키값으로 저장된 문자열이 있는지 읽어와 본다.
	String nick=(String)session.getAttribute("nick");
	//만일 저장된 값이 없다면(로그인 하지 않았다면)
	if(nick == null){
		//로그인 페이지로 리다일렉트 이동시킨다.
		String cPath=request.getContextPath();
		response.sendRedirect(cPath+"/index.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/test/play.jsp</title>
</head>
<body>
	<p> <strong><%=nick %></strong>님 신나게 놀아 보아요!</p>
	<a href="${pageContext.request.contextPath }/">인덱스로</a>
</body>
</html>