<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test_jquery/Step02_example.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" />
</head>
<body>
   <div class="container">
      <h1>ajax 요청을 통해서 받아온 데이터 사용하기</h1>
      <p> 페이지 전환없이 서버에 요청하는것을 ajax 라고 생각하면 된다.</p>
      <div>
         <table>
            <thead>
               <tr>
                  <th>번호</th>
                  <th>작성자</th>
                  <th>제목</th>
               </tr>
            </thead>
            <tbody id="one">
               
            </tbody>
         </table>
      </div>
      <nav>
         <ul class="pagination">
            
         </ul>
      </nav>
   </div>
   <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.0/dist/jquery.min.js"></script>
   <script>
      //매개변수에 전달되는 페이지 번호에 해당하는 정보를 요청하는 함수
      function request(pageNum){
         //일단 clear!
         $("#one").text("");
         $(".pagination").text("");
         
         $.ajax({
            url:"${pageContext.request.contextPath }/cafe/json_list2.jsp",
            method:"get",
            data:{pageNum},
            success:function(data){
               //글목록
               const list=data.list;
               list.forEach(function(item){
                  //item 에는 글 하나의 정보가 들어 있다.
                  const td1=$("<td>").text(item.num);
                  const td2=$("<td>").text(item.writer);
                  const td3=$("<td>").text(item.title);
                  $("<tr>").append(td1).append(td2).append(td3).appendTo("#one");
               });
               
               if(data.startPageNum != 1){
                  let template=`
                     <li class="page-item">
                        <a class="page-link" href="javascript:request(\${data.startPageNum})">Prev</a>
                      </li>
                  `;
                  $(template).appendTo(".pagination");
               }
               //반복문 돌면서 페이지 출력하기 
               for(let i=data.startPageNum; i<=data.endPageNum; i++){
                  let template=`
                     <li class="page-item">
                        <a class="page-link \${pageNum == i ? 'active':''}" href="javascript:request(\${i})">\${i}</a>
                      </li>
                  `;
                  $(template).appendTo(".pagination");
               }
               if(data.endPageNum < data.totalPageCount){
                  let template=`
                     <li class="page-item">
                        <a class="page-link" href="javascript:request(\${data.endPageNum+1})">Next</a>
                      </li>
                  `;
                  $(template).appendTo(".pagination");
               }
            }
         });         
      }
      //페이지 로딩 시점에 1 페이지 내용을 요청한다.
      request(1);
   </script>
</body>
</html>