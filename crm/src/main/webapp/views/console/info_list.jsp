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

function doValue(k, v) {
	$("#" + k).val(v);
	$("#info_form").submit();
}

//设置表格列显
function doVisible(k, v) {
	
	if(k == "on") {
		
		var cols = $("table tbody tr:eq(0) th");
		
		var m = "";
		for(var i = 0; i < cols.length; i++) {
			var o = $(cols[i]);
			m += "<div class=\"ym-fbox\"><div class=\"ym-fbox-wrap\">";
				m += "<input type=\"checkbox\" id=\"visible_" + i + "\" value=\"" + i + "\" onclick=\"doVisible('do', this);\"" + (o.is(":visible") ? " checked=\"checked\"" : "") + ">";
				m += "<label for=\"visible_" + i + "\">" + o.text() + "</label>";
			m += "</div></div>";
		}
		
		jdialog(m, "x");
		
		return;
		
	}
	
	var trObj = $("table tbody tr");
	var index = v.value;
	if(v.checked) {
		trObj.find("th:eq(" + index + ")").show();
		trObj.find("td:eq(" + index + ")").show();
	} else {
		trObj.find("th:eq(" + index + ")").hide();
		trObj.find("td:eq(" + index + ")").hide();
	}
	
	$("table thead tr:eq(0) th:eq(0)").attr("colspan", $("input[id^='visible_']:checkbox:enabled:checked").length);
	
}


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
		type: "POST", url: basePath + "/info/delete", data: { "ids": ids },
		dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { jprop("#jdialog_ok", "disabled", true); },
		success: function(data, textStatus) { $("#info_form").submit(); },
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
		<div><jsp:include page="/views/console/include/vlist.jsp" flush="true"><jsp:param name="vListActive" value="InfoList" /></jsp:include></div>
	</div></div>
	<div class="ym-g80 ym-gr"><div class="ym-gbox-right">

		<div>
			<form id="info_form" method="post" action="${basePath}/info/list" class="ym-form ym-full">
			
				<jsp:include page="/views/console/include/page.jsp" flush="true" />
			
				<div class="ym-fbox">
					<div class="ym-fbox-wrap ym-grid">
					
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="id">产品ID</label>
							<input type="text" id="pid" name="pid" value="${pid}" maxlength="32">
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="username">渠道ID</label>
							<input type="text" id="uid" name="uid" value="${uid}" maxlength="32">
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="username">用户代理</label>
							<input type="text" id="userAgent" name="userAgent" value="${userAgent}" maxlength="64">
						</div></div>
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="ip">访问IP</label>
							<input type="text" id="remoteAddr" name="remoteAddr" value="${remoteAddr}" maxlength="64">
						</div></div>
						
						<div class="ym-g20 ym-gr"><div class="ym-gbox-right">&nbsp;</div></div>
					</div>
				</div>
							<div class="ym-fbox">
					<div class="ym-fbox-wrap ym-grid">
						
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="scanPlace">浏览位置</label>
							<select id="scanPlace" name="scanPlace">
								<option value="">-</option>
								<option value="1"<c:if test="${scanPlace eq '1'}"> selected="selected"</c:if>>产品介绍</option>
								<option value="2"<c:if test="${scanPlace eq '2'}"> selected="selected"</c:if>>表单填写</option>
								<option value="3"<c:if test="${scanPlace eq '3'}"> selected="selected"</c:if>>公司介绍</option>
							</select>
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">
							<label for="scanTime">浏览时间</label>
							<input type="text" id="scanTime" name="scanTime"  placeholder="大于等于多少秒" value="${scanTime}" maxlength="64">
						</div></div>
						
						<div class="ym-g20 ym-gl"><div class="ym-gbox-left">&nbsp;</div></div>
						<div class="ym-g20 ym-gr"><div class="ym-gbox-right">&nbsp;</div></div>
						<div class="ym-g20 ym-gr"><div class="ym-gbox-right">&nbsp;</div></div>
						
					</div>
				</div>
				
				
				<div class="ym-fbox-footer"><button type="submit" class="ym-button">查询</button></div>
				
			</form>
		</div>

		<div>		
			<table>
				<thead><tr><th colspan="12">
					<a href="javascript:;" onclick="doVisible('on', '');">显示</a>
					<span class="m8226">&#8226;</span>
					<a href="javascript:;" onclick="doDelete('ids', '');">删除</a>
				</th></tr></thead>
				<tbody>
					<tr class="tbheader">
						<th style="width: 30px;"><input type="checkbox" id="checkAll" onclick="setSelected(this, 'checkAll', 'item_');"></th>
						<th style="width: 50px;">请求地址</th>
						<th style="width: 150px;">浏览时间（秒）</th>
						<th style="width: 100px;">浏览位置</th>
						<th style="width: 80px;">访问IP</th>
						<th style="width: 60px;">方式</th>
						<th style="width: 50px; display: none;">请求头</th>
						<th style="width: 120px;display: none;" >语言</th>
						<th style="width: 150px;display: none;">编码</th>
						<th style="width: 50px;">协议</th>
						<th style="width: 50px;">用户代理</th>
						<th style="width: 100px;">访问时间</th>
					</tr>
					<c:forEach items="${pager.content}" var="o">
					<tr>
						<td><input type="checkbox" id="item_${o.id}" value="${o.id}" onclick="setSelected(this, 'checkAll', 'item_');"></td>
						<td>${o.requestUri}</td>
						<td><a href="javascript:;" onclick="doValue('scanTime', '${o.scanTime}');">${o.scanTime}</a></td>
						<td>${o.scanPlaceString}</td>
						<td><a href="javascript:;" onclick="doValue('remoteAddr', '${o.remoteAddr}');">${o.remoteAddr}</a></td>
						<td>${o.method}</td>
						<td style="display: none;">${o.accept}</td>
						<td style="display: none;">${o.acceptLanguage}</td>
						<td style="display: none;">${o.acceptEncoding}</td>
						<td>${o.protocol}</td>
						<td >${o.userAgent}</td>
						<td>${o.createdDateStr}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<jsp:include page="/views/console/include/pager.jsp" flush="true">
			<jsp:param name="pageForm" value="info_form" />
			<jsp:param name="pagePath" value="${basePath}/user/list" />
		</jsp:include>
		
		
	</div></div>
</div>
	
<jsp:include page="/views/console/include/footer.jsp" flush="true" />
	
</div></div>

</body>
</html>