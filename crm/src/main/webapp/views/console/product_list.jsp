<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>
<c:if test="${sessionScope.SESSION_USER_TYPE ne '1'}"><c:redirect url="${basePath}/error/page" /></c:if>

<!DOCTYPE html>
<html lang="zh">
<head>

<jsp:include page="/views/console/include/meta.jsp" flush="true" />
<jsp:include page="/views/console/include/style.jsp" flush="true" />
<jsp:include page="/views/console/include/script.jsp" flush="true" />

<script>

function doValue(k, v) {
	$("#" + k).val(v);
	$("#product_form").submit();
}

// 删除，单个k=id，多个k=ids
function doDelete(k, v) {
	var ids = v;
	var msg = "";
	if(k == "id") { msg = "确定要删除 <span style=\"color: red;\">" + $("#name_" + v).html() + "</span> 吗？"; }
	if(k == "ids") {
		var cbs = $("input[id^='item_']:checkbox:checked");
		for(var i = 0; i < cbs.length; i++) { ids += $(cbs[i]).val() + ","; }
		msg = "确定要批量删除 <span style=\"color: red;\">" + cbs.length + "</span> 条记录吗？";
	}
	if(ids == "") { jdialog("未选中任何记录！", "x"); return; }
	jdialog(msg, "doDeleteAjax('" + ids + "');");
}

function doDeleteAjax(ids) {
	$.ajax({
		type: "POST", url: basePath + "/product/delete", data: { "ids": ids },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { jprop("#jdialog_ok", "disabled", true); },
		success: function(data, textStatus) { $("#product_form").submit(); },
		error: function(XMLHttpRequest, textStatus, errorThrown) { jdialog("操作失败，请联系技术支持！", "x"); },
		complete: function(XMLHttpRequest, textStatus) { }
	});
}

function doEdit(id) {
	var action = basePath + "/product/edit"; 
	var fields = ["id"]; 
	var values = [id];
	jformsubmit(action, fields, values);
}

function doUpdate() {
	
	var cbs = $("input[id^='item_']:checkbox:checked");
	if(cbs.length == 0) { jdialog("未选中任何记录！", "x"); return; }
	
	var ids = "";
	for(var i = 0; i < cbs.length; i++) { ids += $(cbs[i]).val() + ","; }
	
	var msg = "<p>确定要批量更新 <span style=\"color: red;\">" + cbs.length + "</span> 条记录吗？</p>";
	
	msg += "<p>";
		msg += "<label for=\"update_status\">状态</label>";
		msg += "<select id=\"update_status\"><option value=\"1\">启用</option><option value=\"0\">禁用</option></select>";
	msg += "</p>";
	
	jdialog(msg, "doUpdateAjax('" + ids + "');");
	
}

function doUpdateAjax(ids) {
	var status = $("#update_status").val();
	$.ajax({
		type: "POST", url: basePath + "/product/update", 
		data: { "ids": ids, "status": status },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { jprop("#jdialog_ok", "disabled", true); },
		success: function(data, textStatus) { $("#product_form").submit(); },
		error: function(XMLHttpRequest, textStatus, errorThrown) { jdialog("操作失败，请联系技术支持！", "x"); },
		complete: function(XMLHttpRequest, textStatus) { }
	});
}


</script>

</head>

<body>

<div class="ym-wrapper"><div class="ym-wbox">

	<jsp:include page="/views/console/include/header.jsp" flush="true" />

	<div class="ym-grid">
		<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
			<div><jsp:include page="/views/console/include/vlist.jsp" flush="true"><jsp:param name="vListActive" value="ProductList" /></jsp:include></div>
		</div></div>
		<div class="ym-g80 ym-gr"><div class="ym-gbox-right">

			<div>
				<form id="product_form" method="post" action="${basePath}/product/list" class="ym-form ym-full">
				
					<jsp:include page="/views/console/include/page.jsp" flush="true" />
				
					<div class="ym-fbox">
						<div class="ym-fbox-wrap ym-grid">
							<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
								<label for="id">产品ID</label>
								<input type="text" id="id" name="id" value="${id}" maxlength="64">
							</div></div>
<!-- 							<div class="ym-g20 ym-gl"><div class="ym-gbox-left"> -->
<!-- 								<label for="prefixLink">链接地址</label> -->
<%-- 								<input type="text" id="prefixLink" name="prefixLink" value="${prefixLink}" maxlength="64"> --%>
<!-- 							</div></div> -->
							
							<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
								<label for="name">产品名称</label>
								<input type="text" id="productName" name="productName" value="${productName}" maxlength="64">
							</div></div>
							
								
							<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
								<label for="status">状态</label>
								<select id="status" name="status">
									<option value="">-</option>
									<option value="1"<c:if test="${status eq '1'}"> selected="selected"</c:if>>启用</option>
									<option value="0"<c:if test="${status eq '0'}"> selected="selected"</c:if>>禁用</option>
								</select>
							</div></div>
							<div class="ym-g20 ym-gl"><div class="ym-gbox-right">&nbsp;</div></div>
							<div class="ym-g20 ym-gr"><div class="ym-gbox-right">&nbsp;</div></div>
							
						</div>
					</div>

					<div class="ym-fbox-footer"><button type="submit" class="ym-button">查询</button></div>
					
				</form>
			</div>

			<div>		
				<table>
					<thead><tr><th colspan="11">
						<a href="${basePath}/product/new">新建</a>
						<span>&nbsp;&#8226;&nbsp;</span>
						<a href="javascript:;" onclick="doUpdate();">更新</a>
						<span>&nbsp;&#8226;&nbsp;</span>
						<a href="javascript:;" onclick="doDelete('ids', '');">删除</a>
					</th></tr></thead>
					<tbody>
						<tr class="tbheader">
							<th style="width: 30px;"><input type="checkbox" id="checkAll" onclick="setSelected(this, 'checkAll', 'item_');"></th>
							<th style="width: 100px;">产品名称</th>
							<th style="width: 80px;">产品种类</th>
							<th style="width: 80px;">产品风格</th>
							<th style="width: 70px;">材质</th>
							<th style="width: 70px;">颜色</th>
							<th style="width: 70px;">型号</th>
							<th style="width: 50px;">图片</th>
							<th style="width: 50px;">价格</th>
							<th style="width: 100px;">备注</th>
							<th style="width: 150px;">${ooo}</th>
						</tr>
						<c:forEach items="${pager.content}" var="o">
						<tr>
							<td><input type="checkbox" id="item_${o.id}" value="${o.id}" onclick="setSelected(this, 'checkAll', 'item_');"></td>
							<td><div id="name_${o.id}"><a href="javascript:;" onclick="doValue('name', '${o.name}');">${o.name}</a></div></td>
							<td><a href="javascript:;" onclick="doValue('categoryId', '${o.category}');">${o.category}</a></td>
							<td>${o.style}</td>
							<td>${o.material}</td>
							<td>${o.color}</td>
							<td>${o.model}</td>
							<td>${o.image}</td>
							<td>${o.price}</td>
							<td>${o.desc}</td>
							<td>
								<a href="javascript:;" onclick="doEdit('${o.id}');">编辑</a>
								<span>&nbsp;&#8226;&nbsp;</span>
								<a href="javascript:;" onclick="doDelete('id', '${o.id}');">删除</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<jsp:include page="/views/console/include/pager.jsp" flush="true">
				<jsp:param name="pageForm" value="product_form" />
				<jsp:param name="pagePath" value="${basePath}/product/list" />
			</jsp:include>
			
		</div></div>
	</div>
	
	<jsp:include page="/views/console/include/footer.jsp" flush="true" />
	
</div></div>

</body>
</html>