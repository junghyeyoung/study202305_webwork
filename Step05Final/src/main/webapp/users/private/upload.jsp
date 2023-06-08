<%@page import="test.users.dao.UsersDao"%>
<%@page import="test.users.dto.UsersDto"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id=(String)session.getAttribute("id");
	
	String realPath=application.getRealPath("/upload");
	MultipartRequest mr=new MultipartRequest(request,
			realPath,
			1024*1024*50,
			"utf-8",
			new DefaultFileRenamePolicy());
	String saveFileName=mr.getFilesystemName("image");
	//DB 에 저장할 이미지 경로 구성
	String imagePath="/upload/"+saveFileName;
	//DB 에 수정반영
	UsersDto dto = new UsersDto();
	dto.setId(id);
	dto.setProfile(imagePath);
	UsersDao.getInstance().updateProfile(dto);
	//리다일렉트 응답
	String cPath=request.getContextPath();
	response.sendRedirect(cPath+"/users/private/info.jsp");
%>