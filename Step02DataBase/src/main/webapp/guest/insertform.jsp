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
	<div class="container"><br>
		<h3><strong>방명록 작성</strong></h3>
		<form action="${pageContext.request.contextPath }/guest/insert.jsp" method="post">
			<div class="input-group mb-3">
				<span class="input-group-text" id="inputGroup-sizing-default">작성자</span>
				<input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="writer" name="writer"/>
			</div>
			<div class="form-floating">
				<textarea class="form-control" placeholder="내용입력하기..." name="content" id="floatingTextarea"></textarea>
				<label for="floatingTextarea">내용</label>
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text" id="inputGroup-sizing-default">비밀번호</span>
				<input type="password" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="pwd" name="pwd"/>
			</div>
			<br>
			<button type="submit" class="btn btn-outline-dark">작성 완료</button>
		</form>
	</div>
</body>
</html>