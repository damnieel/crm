<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/taglib.jsp" %>

<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	request.setAttribute("basePath", basePath);
	request.setAttribute("ooo", "&#8226;&#8226;&#8226;");
%>