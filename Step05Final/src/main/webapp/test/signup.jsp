<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   //아이디
   String id=request.getParameter("id");
   //비번
   String pwd=request.getParameter("pwd");
   //직업
   String job=request.getParameter("job");
   //성별
   String gender=request.getParameter("gender");
   //취미 :  여러개가 전달 될 수 있기 때문에 [] 타입으로 받아야함 
   //db에 저장할 떄 json으로 저장해서 json.parse로 사용 
   String[] hobby=request.getParameterValues("hobby");
   //취미 ( 아무것도 체크 하지 않앗으면 null 이다 )
   String hobbyResult=""; //빈 문자열을 만들고
   if(hobby != null){
      
      //반복문 돌면서 
      for(int i = 0; i < hobby.length; i++ ){
         //i번째 방에 있는 선택된 취미를 불러온다. 
         String tmp = hobby[i];
         // hobbyResult = hobbyResult+ tmp; 줄이면 아래 
         hobbyResult += tmp; //tmp를 연결한다
         if(i != hobby.length-1){
            hobbyResult += "/";
         }
      }
      //문자열에서 특정 문자("/")로 구분해서 String[] 을 얻어내기
      String[] result = hobbyResult.split("/");
      
      //문자열에 특정 문자가 포함되어 있는지 여부 알아내기
      boolean isContain = hobbyResult.contains("xxx");
      
      
   }

   
   //null이면 체크를 안한것이고 null이 아니면 체크를 한것이다.
   String allowEmail=request.getParameter("allowEmail");
   String allowMessage=request.getParameter("allowMessage");
   /*
   		textarea 에 입력한 문자여은 개행, tab, 공백기호가 모두 포함되어있다.
   		해당 문자열을 DB에 저장했다가 다시 불러와서 textarea 에 출력하고 싶으면
   		textarea 의 innertext 에 출력하면 된다.
   */
   String comment=request.getParameter("comment");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test/signup.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
</head>
<body>
   <div class="container">
      <h1>회원가입 결과</h1>
      <p>회원님의 정보를 성공적으로 저장했습니다.</p>
      
         <form action="signup.jsp" method="post">
            <div class="form-floating  mb-3">
                <input class="form-control" type="text" value="<%=id %>" id="id" name="id" placeholder="아이디 입력">
                <label  for="id">아이디</label>
            </div>
            <div class="form-floating  mb-3">
                <input class="form-control" type="password" value="<%=pwd %>" id="pwd" name="pwd" placeholder="비밀번호 입력">
                <label  for="pwd">비밀번호</label>
            </div>    
            <select id="job" class="form-select mb-4" name="job" >
                <option value="">직업선택</option>
                <option value="programmer" <%=job.equals("programmer") ? "selected" : "" %>>프로그래머</option>
                <option value="doctor" <%=job.equals("doctor") ? "selected" : "" %>>의사</option>
            </select>

            <fieldset>
                <legend>성별 체크</legend>
                <div class="form-check-inline">
                    <input class="form-check-input" value="man" type="radio" id="one" name="gender" <%=gender.equals("man") ? "checked" : "" %>>
                    <label class="form-check-label" for="one">남자</label>
                </div>
                <div class="form-check-inline">
                    <input class="form-check-input" value="woman"  type="radio" id="two" name="gender" <%=gender.equals("woman") ? "checked" : "" %>>
                    <label class="form-check-label" for="two">여자</label>
                </div>
            </fieldset>

            <fieldset>
                <legend>취미 체크</legend>
                <div class="form-check">
                    <input type="checkbox" name="hobby" value="piano" class="form-check-input"
                     id="piano" <%=hobbyResult.contains("piano") ? "checked" : "" %>>
                    <label class="form-check-label" for="piano">피아노</label>
                </div>
                <div class="form-check">
                    <input type="checkbox" name="hobby" value="game" class="form-check-input" 
                    id="game" <%=hobbyResult.contains("game") ? "checked" : "" %>>
                    <label class="form-check-label" for="game">게임</label>
                </div>
                <div class="form-check">
                    <input type="checkbox"  name="hobby"  value="etc" class="form-check-input" 
                    id="etc" <%=hobbyResult.contains("etc") ? "checked" : "" %>>
                    <label class="form-check-label" for="etc">기타</label>
                </div>
            </fieldset>
            <div>
               <label for="comment" class="form-label">하고 싶은 말</label>
               <textarea name="comment" id="comment" class="form-control" rows="10" ><%=comment %></textarea>
            </div>
            <fieldset>
                <legend>개인정보 활용 동의</legend>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox"  id="allowAll" >
                    <label class="form-check-label" for="allowAll">전체동의</label>
                </div>

                <div class="form-check form-switch">
                    <input class="form-check-input allow" type="checkbox"  id="allowEmail" 
                    <%=allowEmail != null ? "checked" :"" %> name="allowEmail" value="email">
                    <label class="form-check-label" for="allowEmail">이메일 수신여부</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input allow" type="checkbox"  id="allowMessage" 
                     <%=allowMessage != null ? "checked" :"" %> name="allowMessage" value="message">
                    <label class="form-check-label" for="allowMessage">광고성 문자 수신여부</label>
                </div>
            </fieldset>
            <button class="btn btn-outline-primary" type="submit">가입</button>
        </form>
      
   </div>
</body>
</html>