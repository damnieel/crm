<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>

<c:if test="${not empty pager}">
	<div id="hidepage">
		<input type="hidden" id="pageNumber" name="pageNumber" value="${pager.pageNumber}">
		<input type="hidden" id="pageSize" name="pageSize" value="${pager.pageSize}">
	</div>
</c:if>