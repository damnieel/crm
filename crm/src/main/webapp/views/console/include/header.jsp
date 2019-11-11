<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>

<div class="ym-grid"><div class="ym-gbox">
	<div class="ym-grid">
		<div class="ym-gl"><h3 style="margin-bottom: 10px;">智慧家居管理控制台</h3></div>
		<div class="ym-gr">
			<a href="${basePath}">你好，${sessionScope.SESSION_USER_USERNAME}</a>
			<span class="m8226">&#8226;</span>
			<a href="javascript:;" onclick="doSignout();">退出</a>
		</div>
	</div>
	<hr style="margin-bottom: 20px;">
</div></div>