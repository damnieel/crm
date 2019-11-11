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

// 删除，单个k=id，多个k=ids
function doDelete(k, v) {
	var ids = v;
	var msg = "";
	if(k == "id") { msg = "确定要删除 <span style=\"color: red;\">" + $("#username_" + v).html() + "(" + v + ")" + "</span> 吗？"; }
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
		type: "POST", url: basePath + "/user/delete", data: { "ids": ids },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { jprop("#jdialog_ok", "disabled", true); },
		success: function(data, textStatus) { $("#user_form").submit(); },
		error: function(XMLHttpRequest, textStatus, errorThrown) { jdialog("操作失败，请联系技术支持！", "x"); },
		complete: function(XMLHttpRequest, textStatus) {jprop("#jdialog_ok", "disabled", false); }
	});
}

function doEdit(id) {
	var action = basePath + "/user/edit"; 
	var fields = ["id"]; 
	var values = [id];
	jformsubmit(action, fields, values);
}

</script>

</head>

<body>

<div class="ym-wrapper"><div class="ym-wbox">

<jsp:include page="/views/console/include/header.jsp" flush="true" />	
	
<div class="ym-grid">
	<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
		<div><jsp:include page="/views/console/include/vlist.jsp" flush="true"><jsp:param name="vListActive" value="UserList" /></jsp:include></div>
	</div></div>
	<div class="ym-g80 ym-gr"><div class="ym-gbox-right">

		<div>
			<form id="user_form" method="post" action="${basePath}/user/list" class="ym-form ym-full">
			
				<jsp:include page="/views/console/include/page.jsp" flush="true" />
			
				<div class="ym-fbox">
					<div class="ym-fbox-wrap ym-grid">
					
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="id">用户ID</label>
							<input type="text" id="id" name="id" value="${id}" maxlength="32">
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="username">用户名</label>
							<input type="text" id="username" name="username" value="${username}" maxlength="32">
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="type">类型</label>
							<select id="type" name="type">
								<option value="">-</option>
								<option value="1"<c:if test="${type eq '1'}"> selected="selected"</c:if>>管理员</option>
								<option value="0"<c:if test="${type eq '0'}"> selected="selected"</c:if>>普通用户</option>
							</select>
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="status">状态</label>
							<select id="status" name="status">
								<option value="">-</option>
								<option value="1"<c:if test="${status eq '1'}"> selected="selected"</c:if>>启用</option>
								<option value="0"<c:if test="${status eq '0'}"> selected="selected"</c:if>>禁用</option>
							</select>
						</div></div>
						
						<div class="ym-g20 ym-gr"><div class="ym-gbox-right">&nbsp;</div></div>
						
					</div>
				</div>
				
				<div class="ym-fbox-footer"><button type="submit" class="ym-button">查询</button></div>
				
			</form>
		</div>

		<div>		
			<table>
				<thead><tr><th colspan="9">
					<a href="${basePath}/user/new">新建</a>
					<span>&nbsp;&#8226;&nbsp;</span>
					<a href="javascript:;" onclick="doDelete('ids', '');">删除</a>
				</th></tr></thead>
				<tbody>
					<tr class="tbheader">
						<th style="width: 30px;"><input type="checkbox" id="checkAll" onclick="setSelected(this, 'checkAll', 'item_');"></th>
						<th style="width: 100px;">用户ID</th>
						<th style="width: 120px;">用户名</th>
						<th style="width: 80px;">类型</th>
						<th style="width: 50px;">状态</th>
						<th>备注</th>
						<th style="width: 150px;">创建时间</th>
						<th style="width: 150px;">更新时间</th>
						<th style="width: 100px;">${ooo}</th>
					</tr>
					<c:forEach items="${pager.content}" var="o">
					<tr>
						<c:if test="${ sessionScope.SESSION_USER_ID ne o.id }">
							<td><input type="checkbox" id="item_${o.id}" value="${o.id}" onclick="setSelected(this, 'checkAll', 'item_');"></td>
						</c:if>
						<c:if test="${ sessionScope.SESSION_USER_ID eq o.id }">
							<td>&nbsp;</td>
						</c:if>
						<td>${o.id}</td>
						<td><div id="username_${o.id}">${o.username}</div></td>
						<td>${o.typeStr}</td>
						<td>${o.statusStr}</td>
						<td>${o.desc}</td>
						<td>${o.createdStr}</td>
						<td>${o.updatedStr}</td>
						<c:if test="${ sessionScope.SESSION_USER_ID ne o.id }">
							<td>
								<a href="javascript:;" onclick="doEdit('${o.id}');">编辑</a>
								<span>&nbsp;&#8226;&nbsp;</span>
								<a href="javascript:;" onclick="doDelete('id', '${o.id}');">删除</a>
							</td>
						</c:if>
						<c:if test="${ sessionScope.SESSION_USER_ID eq o.id }">
							<td>
								<a href="javascript:;" onclick="doEdit('${o.id}');">编辑</a>
							</td>
						</c:if>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<jsp:include page="/views/console/include/pager.jsp" flush="true">
			<jsp:param name="pageForm" value="user_form" />
			<jsp:param name="pagePath" value="${basePath}/user/list" />
		</jsp:include>
		
		
	</div></div>
</div>

<jsp:include page="/views/console/include/footer.jsp" flush="true" />

</div></div>

</body>
</html>