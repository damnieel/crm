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
	$("#edit_title").text(editId == "" ? "新建产品" : "编辑产品");
	if(editId != ""){$("#id").attr("readonly","readonly");}
	if(editId == "") { return; }
	$.ajax({
		type: "POST", url: basePath + "/product/load", data: { "id": editId },
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
	$("#prefixLink").val(d.prefixLink);
	$("#productName").val(d.productName);
	$("#price").val(d.price);
	$("#status").val(d.status);
	$("#desc").val(d.desc);
	$("#created").val(d.created);
	
}

function doSubmit() {
	
	/* --- 取参 --- */
	var action = editId == "" ? "new" : "edit";
	var pid = $("#id").val();
	var prefixLink = $("#prefixLink").val();
	var productName = $("#productName").val();
	var price = $("#price").val();
	var status = $("#status").val();
	var desc = $("#desc").val();
	var created = $("#created").val();
	
	/* --- 校验 --- */
	doHandle("x");
	
	/* --- 提交 --- */
	$.ajax({
		type: "POST", url: basePath + "/product/save",
		data: { "action": action, "id": editId, "prefixLink": prefixLink, "id":pid,"productName": productName,"price": price, "status": status,  "desc": desc, "created": created },
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
	if(code == "25010") { redirect("/product/list"); return; }
	
	if(code == "45010") { doRender(code, "操作失败，请刷新后重新尝试！"); return; }
	if(code == "45020") { doRender(code, "链接地址长度为1~128个字符！"); return; }
	if(code == "45050") { doRender(code, "产品ID长度为1~64个字符！"); return; }
	if(code == "45030") { doRender(code, "产品名称长度为1~32个字符！"); return; }
	if(code == "45040") { doRender(code, "价格长度为1~32个字符！"); return; }
	if(code == "45070") { doRender(code, "请选择状态！"); return; }
	if(code == "45090") { doRender(code, "备注长度不能超过90个字符！"); return; }
	if(code == "45403") { doRender(code, "操作受限，请联系技术支持！"); return; }
	
	if(code == "55555") { doRender(code, "操作失败，请联系技术支持！"); return; }
}

$(function() { doLoad(); });

</script>

</head>

<body>

<div class="ym-wrapper"><div class="ym-wbox">
	<div class="ym-grid">
	
		<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
			<div><jsp:include page="/views/console/include/vlist.jsp" flush="true"><jsp:param name="vListActive" value="ProductList" /></jsp:include></div>
		</div></div>
		
		<div class="ym-g80 ym-gr"><div class="ym-gbox-right">

			<div>
				<form method="post" action="${basePath}" onsubmit="return doSubmit();" class="ym-form" >
				
					<h6 class="ym-fbox-heading" id="edit_title">新建产品</h6>
					
					<input type="hidden" id="created" value="" >
					
					<div class="ym-fbox">
						<label for="id">产品名称</label>
						<input type="text" id="name" maxlength="64">
					</div>
					<div class="ym-fbox">
						<label for="category">产品类别</label>
						<input type="text" id="category" maxlength="64">
					</div>
					
					<div class="ym-fbox">
						<label for="style">产品风格</label>
						<input type="text" id="style" maxlength="64">
					</div>
					
					<div class="ym-fbox">
						<label for="material">材质</label>
						<input type="text" id="material" maxlength="64">
					</div>
					
					<div class="ym-fbox">
						<label for="color">颜色</label>
						<input type="text" id="color" maxlength="64">
					</div>
					
					<div class="ym-fbox">
						<label for="model">型号</label>
						<input type="text" id="model" maxlength="64">
					</div>
					
					<div class="ym-fbox">
						<label for="image">图片</label>
						<input type="text" id="image" maxlength="64">
					</div>
					
					<div class="ym-fbox">
						<label for="price">价格</label>
						<input type="text" id="price" maxlength="64">
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
						<textarea rows="5" cols="5" id="desc"></textarea>
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