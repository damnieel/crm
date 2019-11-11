<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>
<c:if test="${sessionScope.SESSION_USER_TYPE ne '1'}"><c:redirect url="${basePath}" /></c:if>

<!DOCTYPE html>
<html lang="zh">
<head>

<jsp:include page="/views/console/include/meta.jsp" flush="true" />
<jsp:include page="/views/console/include/style.jsp" flush="true" />
<jsp:include page="/views/console/include/script.jsp" flush="true" />

<script>

var editId = "${editId}";

function doLoad() {
	$("#edit_title").text(editId == "" ? "新建用户" : "编辑用户");
	if(editId == "") {return; }else{ jprop("#id", "disabled", "disabled"); }
// 	$("#password").hide();  $("#password").prev().hide();
	$("#passwordCheckBoxSpan").show();
	$("#password").attr("disabled","disabled");
	$.ajax({
		type: "POST", url: basePath + "/user/load", data: { "id": editId },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { },
		success: function(data, textStatus) { doParse(data); },
		error: function(XMLHttpRequest, textStatus, errorThrown) { doHandle("55555"); },
		complete: function(XMLHttpRequest, textStatus) { }
	});
}

function doParse(data) {
	
	if(data == null) { doHandle("55555"); return; }
	
	var c = data.code; if(c != "25010") { doHandle(c); return; }
	var d = data.data;
	
	$("#id").val(d.id);
	$("#username").val(d.username);
	$("#password").val("*********");
	$("#type").val(d.type);
	$("#status").val(d.status);
	$("#desc").val(d.desc);
	$("#created").val(d.created);
	
}

function doSubmit() {
	
	/* --- 取参 --- */
	var action = editId == "" ? "new" : "edit";
	var id = editId == "" ? $("#id").val() : editId;
	var username = $("#username").val();
	var password = $("#password").val();
	var type = $("#type").val();
	var status = $("#status").val();
	var desc = $("#desc").val();
	var created = $("#created").val();
	
	/* --- 校验 --- */
	doRender("x", "x");
	if(!isWord(id, 1, 64)) { doHandle("45010"); return false; }
	if(!isWord(username, 1, 32)) { doHandle("45020"); return false; }
	if(!isLength(password, 1, 32)) { doHandle("45030"); return false; }
	if(!isNumber(type, 1, 1)) { doHandle("45040"); return false; }
	if(!isNumber(status, 1, 1)) { doHandle("45050"); return false; }
	if(!isLength(desc, 0, 96)) { doHandle("45060"); return false; }
	if( editId !="" && isEdit == 0){password = ""}
	
	/* --- 提交 --- */
	$.ajax({
		type: "POST", url: basePath + "/user/save",
		data: { "action": action, "id": id, "username": username, "password": password, "type": type, "status": status, "desc": desc, "created": created },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { jprop("#ok", "disabled", true); },
		success: function(data, textStatus) { doHandle(data.code); },
		error: function(XMLHttpRequest, textStatus, errorThrown) { doHandle("55555"); },
		complete: function(XMLHttpRequest, textStatus) { jprop("#ok", "disabled", false); }
	});
	
	return false;
	
}

function doHandle(code) {
	if(code == "x") { doRender("x", "x"); return; }
	if(code == "25010") { redirect("/user/list"); return; }
	if(code == "45010") { doRender(code, "用户ID长度为1~64个字符！"); return; }
	if(code == "45020") { doRender(code, "用户名长度为1~32个字符！"); return; }
	if(code == "45030") { doRender(code, "密码长度为1~32个字符！"); return; }
	if(code == "45040") { doRender(code, "请选择类型！"); return; }
	if(code == "45050") { doRender(code, "请选择状态！"); return; }
	if(code == "45060") { doRender(code, "备注长度不能超过96个字符！"); return; }
	if(code == "45403") { doRender(code, "操作受限，请联系技术支持！"); return; }
	if(code == "55010") { doRender(code, "保存失败，用户ID、用户名可能被占用，或联系技术支持！"); return; }
	if(code == "55555") { doRender(code, "操作失败，请联系技术支持！"); return; }
}

var isEdit = 0;
function doEdit(){
	isEdit = isEdit == 0 ? 1 : 0;
	$("#password").val(isEdit == 0 ? "*********" : "").prop("disabled", isEdit == 0);
	$("#passwordCheckBox").prop("checked", isEdit == 1);
}
$(function() { doLoad(); });

</script>

</head>

<body>

<div class="ym-wrapper"><div class="ym-wbox">
	<div class="ym-grid">
	
		<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
			<div><jsp:include page="/views/console/include/vlist.jsp" flush="true"><jsp:param name="vListActive" value="UserList" /></jsp:include></div>
		</div></div>
		
		<div class="ym-g80 ym-gr"><div class="ym-gbox-right">

			<div>
				<form method="post" action="${basePath}" onsubmit="return doSubmit();" class="ym-form">
				
					<h6 class="ym-fbox-heading" id="edit_title">新建用户</h6>
					
					<input type="hidden" id="created" value="">
					<div class="ym-fbox">
						<label for="id">用户ID</label>
						<input type="text" id="id" maxlength="32" >
					</div>
					
					<div class="ym-fbox">
						<label for="username">用户名</label>
						<input type="text" id="username" maxlength="32">
					</div>
					
					<div class="ym-fbox">
						<label for="password">密码&nbsp;&nbsp;<span style="color:#4DA2D8; display: none;" onclick="doEdit();" id="passwordCheckBoxSpan">(&nbsp;<input type="checkbox" id="passwordCheckBox" name="passwordCheckBox" />修改密码)</span></label>
						<input type="text" id="password" maxlength="32" >
					</div>
					
					<div class="ym-fbox">
						<label for="type">类型</label>
						<select id="type">
							<option value="">-</option>
							<option value="0">普通用户</option>
							<option value="1">管理员</option>
						</select>
					</div>
					
					<div class="ym-fbox">
						<label for="status">状态</label>
						<select id="status">
							<option value="">-</option>
							<option value="1">启用</option>
							<option value="0">禁用</option>
						</select>
					</div>
					
					<div class="ym-fbox">
						<label for="desc">备注</label>
						<textarea id="desc" rows="5" cols="5"></textarea>
					</div>
					
					<div class="ym-fbox-footer">
						<div><button type="submit" class="ym-button" id="ok">保存</button></div>
						<div id="render" style="margin-top: 15px;">&nbsp;</div>
					</div>
					
				</form>
			</div>
			
		</div></div>
		
	</div>
</div></div>

</body>
</html>