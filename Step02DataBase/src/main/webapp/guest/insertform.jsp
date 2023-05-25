<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/guest/insertform.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h3><strong>방명록 작성</strong></h3>
		<form action="${pageContext.request.contextPath }/guest/insert.jsp" method="post">
			<div class="mb-1">
				<label for="writer">작성자</label>
				<input type="text" id="writer" name="writer"/>
			</div>
			<div class="mb-1">
				<label for="content">내용</label>
				<br>
				<textarea placeholder="내용입력하기..." name="content" id="content" cols="30" rows="10"></textarea>
			</div>
			<div class="mb-1">
				<label for="pwd">비밀번호</label>
				<input type="password" id="pwd" name="pwd"/>
			</div>
			<br>
			<button type="submit" class="btn btn-outline-dark">작성 완료</button>
		</form>
	</div>
</body>
</html>