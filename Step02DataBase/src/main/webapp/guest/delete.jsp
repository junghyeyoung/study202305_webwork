<%@page import="test.guest.dao.GuestDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int num=Integer.parseInt(request.getParameter("num"));
	GuestDao.getInstance().delete(num);
	
	String cPath=request.getContextPath();
	response.sendRedirect(cPath+"/guest/list.jsp");

%>