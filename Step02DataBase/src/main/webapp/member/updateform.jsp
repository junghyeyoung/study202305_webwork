<%@page import="test.member.dao.MemberDao"%>
<%@page import="test.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 수정할 회원의 번호를 얻어낸다
	int num=Integer.parseInt(request.getParameter("num"));
	//2. 번호를 이용해서 DB 에 저장된 수정할 회원의 정보를 얻어낸다.
	MemberDto dto=MemberDao.getInstance().getData(num);
	//3. 수정할 양식을 클라이언트에게 응답한다.
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/member/updateform.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h2><strong>회원 정보 수정</strong>양식</h2>
		<form action="update.jsp" method="post">
			<div class="mb-1">
				<label for="num" class="form-label">번호</label>
				<input type="text" class="form-control" id="num" name="num" value="<%=dto.getNum()%>" readonly/>
			</div>
			<div class="mb-1">
				<label for="name" class="form-label">이름</label>
				<input type="text" class="form-control" id="name" name="name" value="<%=dto.getName()%>"/>
			</div>
			<div class="mb-1">
				<lable for="addr" class="form-label">주소</lable>
				<input type="text" class="form-control" id="addr" name="addr" value="<%=dto.getAddr()%>"/>
			</div>
			<button class="btn btn-outline-primary" type="submit">수정확인</button>
			<button class="btn btn-outline-warning" type="reset">취소</button>
		</form>
	</div>
</body>
</html>