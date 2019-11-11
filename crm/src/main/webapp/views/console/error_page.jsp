<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>

<!DOCTYPE html>
<html lang="zh">
<head>

<jsp:include page="/views/console/include/meta.jsp" flush="true" />
<jsp:include page="/views/console/include/style.jsp" flush="true" />
<jsp:include page="/views/console/include/script.jsp" flush="true" />

<script>

function doSubmit() {
	
	/* --- 取参 --- */
	var username = $("#username").val();
	var password = $("#password").val();
	
	/* --- 校验 --- */
	doRender("x", "x");
	if(!isWord(username, 1, 32)) { doHandle("45010"); return false; }
	if(!isWord(password, 1, 32)) { doHandle("45020"); return false; }

	/* --- 提交 --- */
	$.ajax({
		type: "POST", url: basePath + "/user/signin",
		data: { "username": username, "password": password },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { jprop("#ok", "disabled", true); },
		success: function(data, textStatus) { 
			var code = data.code;
			if(code == "25020"){jformsubmit(basePath+"/product/list",['groupBy','userId'],['userId',data.data]);return;}
			doHandle(code); },
		error: function(XMLHttpRequest, textStatus, errorThrown) { doHandle("55555"); },
		complete: function(XMLHttpRequest, textStatus) { jprop("#ok", "disabled", false); }
	});
	
	return false;
	
}

function doHandle(code) {
	if(code == "x") { doRender("x", "x"); return; }
	if(code == "25010") { redirect(null); return; }
	if(code == "45010") { doRender(code, "用户名长度1~32个字符，区分大小写，可使用字母、数字、下划线！"); return; }
	if(code == "45020") { doRender(code, "密码长度1~32个字符，区分大小写，可使用字母、数字、下划线！"); return; }
	if(code == "45030") { doRender(code, "登录失败，用户名或密码错误！"); return; }
	if(code == "45040") { doRender(code, "登录受限，请联系技术支持！"); return; }
	if(code == "45403") { doRender(code, "操作失败，请联系技术支持！"); return; }
	if(code == "55010") { doRender(code, "操作失败，请联系技术支持！"); return; }
	if(code == "55020") { doRender(code, "操作失败，请联系技术支持！"); return; }
	if(code == "55555") { doRender(code, "操作失败，请联系技术支持！"); return; }
}

</script>

</head>

<body>

<div class="ym-wrapper"><div class="ym-wbox">

	<div style="width: 500px; margin: 0 auto;">
		<form method="post" action="${basePath}" onsubmit="return doSubmit();" class="ym-form ym-full">
		
			<h6 class="ym-fbox-heading">错误页面</h6>
			
			<div class="ym-fbox">
				<label for="username">错误页面</label>
				<input type="text" id="username" maxlength="32">
			</div>
			
			<div class="ym-fbox">
				<label for="password">错误页面</label>
				<input type="password" id="password" maxlength="32">
			</div>
			
			<div class="ym-fbox-footer copyright">
				<div><a href="http://www.miitbeian.gov.cn/" target="_blank">湘ICP备88888888号</a></div>
				<div style="color: #888;">Copyright &copy; 2018 <a href="${basePath}">Intelhome</a>. All rights reserved.</div>
			</div>
			
		</form>
	</div>
	
</div></div>

</body>
</html>