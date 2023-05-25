<%@page import="test.guest.dao.GuestDao"%>
<%@page import="test.guest.dto.GuestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int num=Integer.parseInt(request.getParameter("num"));
	GuestDto dto=GuestDao.getInstance().getData(num);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/guest/updateform.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h3><strong>방명록 수정</strong></h3>
		<form action="update.jsp" method="post">
			<div class="mb-1">
				<label for="num" class="form-label">번호</label>
				<input type="text" id="num" name="num" value="<%=dto.getNum() %>" readonly />
			</div>
			<div class="mb-1">
				<label for="writer" class="form-label">작성자</label>
				<input type="text" id="writer" name="writer" value="<%=dto.getWriter() %>" />
			</div>
			<div class="mb-1">
				<label for="content">내용</label><br>
				<textarea name="content" id="content" cols="30" rows="10"><%=dto.getContent() %></textarea>
			</div>
			<div class="mb-1">
				<label for="pwd" class="form-label">비밀번호</label>
				<input type="password" id="pwd" name="pwd" />
			</div>
			<button class="btn btn-outline-primary" type="submit">수정완료</button>
			<button class="btn btn-outline-warning" type="reset">취소</button>
		</form>
	</div>
</body>
</html>