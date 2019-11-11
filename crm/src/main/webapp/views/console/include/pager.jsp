<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>

<c:if test="${not empty pager}">
	<div id="showpager">
		<a href="javascript:;" onclick="doPager('do', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'${pager.pageNumber}','pageSize':'${pager.pageSize}'});">共&nbsp;${pager.totalElements}&nbsp;条</a>
		<a href="javascript:;" onclick="doPager('do', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'${pager.pageNumber}','pageSize':'${pager.pageSize}'});">每页&nbsp;${pager.pageSize}&nbsp;条</a>
		<a href="javascript:;" onclick="doPager('to', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'1'});">首页</a>
		<a href="javascript:;" onclick="doPager('to', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'${pager.previous}'});">上一页</a>
		<a href="javascript:;" onclick="doPager('do', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'${pager.pageNumber}','pageSize':'${pager.pageSize}'});">${pager.pageNumber}&nbsp;/&nbsp;${pager.totalPages}</a>
		<a href="javascript:;" onclick="doPager('to', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'${pager.next}'});">下一页</a>
		<a href="javascript:;" onclick="doPager('to', {'pageForm':'${param.pageForm}','pagePath':'${param.pagePath}','pageNumber':'${pager.totalPages}'});">尾页</a>
	</div>
</c:if>