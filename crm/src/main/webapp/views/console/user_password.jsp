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
	
	/* --- 消息 --- */
	var text = {
		"45010": "原密码不合法！",
		"45020": "新密码不合法！",
		"45021": "确认密码不合法！",
		"45022": "两次密码不一致！",
		"45030": "原密码不正确！",
		"50000": "操作失败，请联系客服！",
		"55010": "操作失败，请联系客服！",
		"55555": "操作失败，请联系客服！"
	};
	
	/* --- 取参 --- */
	var oldpassword = $("#oldpassword").val();
	var newpassword = $("#newpassword").val();
	var repassword = $("#repassword").val();
	
	/* --- 校验 --- */
	if(!isWord(oldpassword, 3, 32)) { doRender("45010", text["45010"]); return false; }
	if(!isWord(newpassword, 3, 32)) { doRender("45020", text["45020"]); return false; }
	if(!isWord(repassword, 3, 32)) { doRender("45021", text["45021"]); return false; }
	if(newpassword != repassword) { doRender("45022", text["45022"]); return false; }
	
	$.ajax({
		type: "POST", url: basePath + "/user/update/password",
		data: { "oldpassword": oldpassword, "newpassword": newpassword }, dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { },
		success: function(data, textStatus) {
			var c = data.code;
			if(c != "25200") { doRender(c, text[c]); } else { jdialog("<div style='color: green;'>密码修改成功！</div>", "redirect('/user/signout');"); }
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { doRender("55555", text["55555"]); },
		complete: function(XMLHttpRequest, textStatus) { }
	});
	
	return false;
	
}

</script>

</head>

<body>

<div class="ym-wrapper"><div class="ym-wbox">

<jsp:include page="/views/console/include/header.jsp" flush="true" />

<div class="ym-grid">
<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
			<div><jsp:include page="/views/console/include/vlist.jsp" flush="true"><jsp:param name="vListActive" value="passwordList" /></jsp:include></div>
	</div></div>
	
	<div class="ym-g80 ym-gr"><div class="ym-gbox-right">
		<h5>修改密码</h5>
		
			<form id="mainform" method="post" action="${basePath}" onsubmit="return doSubmit();" class="ym-form ym-form-noborder">

				<div class="ym-fbox"><div class="ym-fbox-wrap ym-grid">
					<div class="ym-g50 ym-gl"><div class="ym-gbox">
						<label for="oldpassword">原密码</label>
						<input type="password" id="oldpassword" name="oldpassword" maxlength="64">
					</div></div>
					<div class="ym-g50 ym-gr"><div class="ym-gbox">&nbsp;</div></div>
				</div></div>

				<div class="ym-fbox" style="margin-top: 20px;"><div class="ym-fbox-wrap ym-grid">
					<div class="ym-g50 ym-gl"><div class="ym-gbox">
						<label for="newpassword">新密码</label>
						<input type="password" id="newpassword" name="newpassword" maxlength="64">
					</div></div>
					<div class="ym-g50 ym-gr"><div class="ym-gbox">&nbsp;</div></div>
				</div></div>

				<div class="ym-fbox" style="margin-top: 20px;"><div class="ym-fbox-wrap ym-grid">
					<div class="ym-g50 ym-gl"><div class="ym-gbox">
						<label for="repassword">确认密码</label>
						<input type="password" id="repassword" name="repassword" maxlength="64">
					</div></div>
					<div class="ym-g50 ym-gr"><div class="ym-gbox">&nbsp;</div></div>
				</div></div>
				
				<div class="ym-fbox" style="margin-top: 20px;"><div class="ym-fbox-wrap ym-grid">
					<div class="ym-g50 ym-gl"><div class="ym-gbox"><div id="render">&nbsp;</div></div></div>
					<div class="ym-g50 ym-gr"><div class="ym-gbox">&nbsp;</div></div>
				</div></div>
				
				<div class="ym-fbox-footer" style="margin-top: 20px;">
					<button type="button" class="ym-button" onclick="redirect('/member/report');">取消</button>
					<button type="submit" class="ym-button">提交</button>
				</div>
			</form>
		
	</div></div>
	
</div>

<jsp:include page="/views/console/include/footer.jsp" flush="true" />

</div></div>
</body>
</html>