/**
 * 
 * 公共 JS 函数
 * 
 * @author chenfan
 * @version 1.0, 2017/03/21
 * 
 */

// 公共
function isLength(input, n, m) { if (input == null || n < 0 || m < 0 || n > m) { return false; } return input.length >= n && input.length <= m; }
function isNumber(input, n, m) { if (!isLength(input, n, m)) { return false; } return new RegExp(/^[0-9]*$/).test(input); }
function isWord(input, n, m) { if (!isLength(input, n, m)) { return false; } return new RegExp(/^[a-zA-Z_0-9]*$/).test(input); }
function sendRedirect(addr) { window.location.href = addr; }
function redirect(addr) { sendRedirect(basePath + (addr == null ? "" : addr)); }
function jrandom() { var r = new String(Math.random()); return r.length < 10 ? jrandom() : r.substring(2, 10); }

// 弹框
function jpopup(message) {
	
	$("#jpopup_box").remove();
	$("#jpopup_wrapper").remove();
	
	if(message == "x") { return; }
	
	// 插入 body 里面最后
	$("body").append("<div id=\"jpopup_wrapper\"></div><div id=\"jpopup_box\">" + message + "</div>");
	
	// 弹窗宽高
	var obj = $("#jpopup_box");
	var w = obj.width();
	var h = obj.height();
	
	// 重置位置，居中显示
	obj.css("margin", "-" + (h/2) + "px 0 0 -" + (w/2) + "px");
	
}

// 话框窗口
function jdialog(message, callback) {
	if(callback == "x") { callback = "jpopup('x');"; }
	var v = "";
	v += "<form id=\"jdialog_form\" method=\"post\" action=\"" + basePath + "\" class=\"ym-form\" style=\"width: 500px;\">";
		v += "<h6 id=\"jdialog_title\" class=\"ym-fbox-heading\">操作提示</h6>";
		v += "<div id=\"jdialog_message\" class=\"ym-fbox\">" + message + "</div>";
		v += "<div id=\"jdialog_footer\" class=\"ym-fbox-footer\">";
			v += "<button id=\"jdialog_no\" type=\"button\" class=\"ym-button\" onclick=\"jpopup('x');\">取消</button>";
			v += "<button id=\"jdialog_ok\" type=\"button\" class=\"ym-button\" onclick=\"" + callback + "\">确定</button>";
		v += "</div>";
	v += "</form>";
	jpopup(v);
}



// 表单提交
function jformsubmit(action, fields, values) {
	var id = "form_" + (new Date()).getTime();
	var v = "<form id=\"" + id + "\" method=\"post\" action=\"" + action + "\" style=\"display: none;\">";
	for (var i = 0; i < fields.length; i++) {
		v += "<input type=\"hidden\" name=\"" + fields[i] + "\" value=\"" + values[i] + "\">";
	}
	v += "</form>";
	$("body").append(v);
	$("#" + id).submit();
}

// 呈现
function doRender(code, data) {
	
	if(code == "x" || data == "x") { $("#render").empty(); return; }
	
	var cls = "";
	if(cls == "" && (new RegExp(/^[1]{1}[0-9]{4}$/)).test(code)) { cls = "info"; }
	if(cls == "" && (new RegExp(/^[2]{1}[0-9]{4}$/)).test(code)) { cls = "success"; }
	if(cls == "" && (new RegExp(/^[3]{1}[0-9]{4}$/)).test(code)) { cls = "warning"; }
	if(cls == "" && (new RegExp(/^[4]{1}[0-9]{4}$/)).test(code)) { cls = "error"; }
	if(cls == "" && (new RegExp(/^[5]{1}[0-9]{4}$/)).test(code)) { cls = "error"; }
	if(cls == "" && (new RegExp(/^[6]{1}[0-9]{4}$/)).test(code)) { cls = "warning"; }
	if(cls == "" && (new RegExp(/^[7]{1}[0-9]{4}$/)).test(code)) { cls = "info"; }
	if(cls == "" && (new RegExp(/^[8]{1}[0-9]{4}$/)).test(code)) { cls = "info"; }
	if(cls == "" && (new RegExp(/^[9]{1}[0-9]{4}$/)).test(code)) { cls = "info"; }
	if(cls == "" && (new RegExp(/^[0]{1}[0-9]{4}$/)).test(code)) { cls = "info"; }
	
	var v = "";
	v += "<div id=\"render_box\" class=\"box " + cls + "\" onclick=\"$('#render_code').show();\">";
		v += "<div id=\"render_data\">" + data + "</div>";
		v += "<div id=\"render_code\" style=\"display: none;\">代码：" + code + "</div>";
	v += "</div>";
	
	$("#render").empty().html(v);
	
}

// 检索和更改 DOM 属性 checked, selected 或 disabled 状态
function jprop(o, k, v) { $(o).prop(k, v); }

// 设置复选框勾选状态
function setSelected(o, pid, cid) {
	if(o.id == pid) {
		$("input[id^='" + cid + "']:checkbox").prop("checked", o.checked);
	} else {
		if(!o.checked) { $("#" + pid).prop("checked", o.checked); return; }
		var cbs = $("input[id^='" + cid + "']:checkbox");
		for(var i = 0; i < cbs.length; i++) { if(!cbs[i].checked) { return; } }
		$("#" + pid).prop("checked", o.checked);
	}
}

// 分页设置
function doPager(k, v) {
	
	// 上、下页
	if(k == "to") {
		$("#pageNumber").val(v.pageNumber);
		$("#" + v.pageForm).submit();
		return;
	}

	// 设置
	if(k == "do") {
		var msg = "";
		msg += "<form id=\"jpager_form\" method=\"post\" action=\"" + basePath + "\" class=\"ym-form ym-full\" style=\"width: 500px;\">";
			msg += "<h6 id=\"jpager_title\" class=\"ym-fbox-heading\">分页设置</h6>";
			msg += "<div id=\"jpager_message\" class=\"ym-fbox\"><div class=\"ym-fbox-wrap\">";
				msg += "<p>每页&nbsp;&nbsp;<input type=\"text\" id=\"doPageSize\"   value=\"" + v.pageSize   + "\" class=\"ym-inline\" maxlength=\"9\" style=\"width: 200px;\">&nbsp;&nbsp;条</p>";
				msg += "<p>到第&nbsp;&nbsp;<input type=\"text\" id=\"doPageNumber\" value=\"" + v.pageNumber + "\" class=\"ym-inline\" maxlength=\"9\" style=\"width: 200px;\">&nbsp;&nbsp;页</p>";
			msg += "</div></div>";			
			msg += "<div id=\"jpager_footer\" class=\"ym-fbox-footer\">";
				msg += "<button id=\"jpager_no\" type=\"button\" class=\"ym-button\" onclick=\"jpopup('x');\">取消</button>";
				msg += "<button id=\"jpager_ok\" type=\"button\" class=\"ym-button\" onclick=\"doPager('go', {'pageForm':'" + v.pageForm + "','pagePath':'" + v.pagePath + "'});\">确定</button>";
			msg += "</div>";
		msg += "</form>";
		jpopup(msg);
		return;
	}

	// 重置
	if(k == "go") {
		jprop("#jpager_ok", "disabled", true);
		$("#pageNumber").val($("#doPageNumber").val());
		$("#pageSize").val($("#doPageSize").val());
		$("#" + v.pageForm).submit();
		return;
	}
	
}

function doSignout() {
	jdialog("<span style=\"color: red;\">确定要退出吗？</span>", "redirect('/user/signout');");
}