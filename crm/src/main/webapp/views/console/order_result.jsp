<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>鹏爱医疗美容</title>

<jsp:include page="/views/console/include/style.jsp" flush="true" />
<jsp:include page="/views/console/include/script.jsp" flush="true" />
	
<style>

body, .div-wrapper { margin: 0; padding: 0; }
.div-wrapper { max-width: 750px; margin: 0 auto; background-color: #f5e5d8; }
.jpopup-wrapper { max-width: 750px; margin: 0 auto;display: block; }

.div-bdtm { margin: 0; padding: 0; display: block; }
.img-bdtm {max-width: 100%;display: block;} 
.ym-g10{ width: 11%}
.ym-g5{ width: 6.7%}
.img-shade{
    position: absolute;
    z-index: 1;
    width: 100%;
    height: 100%;
    background-color: #335544;
    opacity: 0;
}

.font01{font-size: 20px; margin: 10px; color: green; text-align: center; }
.font02{font-size: 16px; margin-right: 10px; margin-left: 10px; }

.button01{background-color: white; width: 100%; border-radius: 5px; border-style:solid; border-width:1px; border-color:green; padding: 10px 10px;}
.button02{background-color: white; color: green; border-radius: 10px; width: 250px; border-style:solid; border-width:1px; border-color:green; padding: 10px 10px;}
.btnActive { background-color: green; color: white; }

</style>

<script>
var pid = "${pid}";
var uid = "${uid}";
var mid = "${mid}";
var name = "${name}";

function doValue() {
	if (name == "xqp") {$("#xqp").show();};
	if (name == "bdtm") {$("#bdtm").show();};
	if (name == "nmsg") {$("#nmsg").show();};
	if (name == "wzzx") {$("#wzzx").show();};
}

function loadProduct() {
	$.ajax({
		type: "POST", url: basePath + "/product/load",
		data: {"id" : pid}, dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { },
		success: function(data, textStatus) {
			var c = data.code;
			var d = data.data;
			$("#projectName").html(d.productName);
			$("#projectPrice").html(d.priceStr);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { },
		complete: function(XMLHttpRequest, textStatus) { }
	});
}

function wechatpay() {
	
	var text = {
			"45010": "渠道异常，请联系客服!",
			"45020": "产品信息异常，请联系客服!",
			"45030": "单号异常，请联系客服!",
			"45040": "产品名称异常，请联系客服!",
			"45050": "价格异常，请联系客服!",
			"55000": "订单生成失败，请联系客服!",
			"55010": "支付异常，请联系客服!"
	};
	
	var projectName = $("#projectName").text();
	var projectPrice = $("#projectPrice").text();
	
	/* --- 校验 --- */
	if(!isWord(uid, 1, 64)) { r.html(text["45010"]); return false; }
	if(!isWord(pid, 1, 64)) { r.html(text["45020"]); return false; }
	if(!isWord(mid, 1, 64)) { r.html(text["45030"]); return false; }
	if(!isLength(projectName, 1, 32)) { r.html(text["45040"]); return false; }
	if(!isLength(projectPrice, 1, 32)) { r.html(text["45050"]); return false; }
	
	$.ajax({
		type: "POST", url: basePath + "/pay/ht",
		data: {"pid": pid, "projectName": projectName, "projectPrice": projectPrice, "uid": uid, "mid": mid}, dataType: "json", async: true, cache: false,
		beforeSend: function(XMLHttpRequest) { },
		success: function(data, textStatus) {
			var c = data.code; if(c != "25200") { doRender(c, text[c]); return; }
			var d = data.data;
			location.href=d;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { },
		complete: function(XMLHttpRequest, textStatus) { }
	});
}

$(function(){
	doValue()
	loadProduct()
});
</script>

</head>
<body>

<div class="div-wrapper">
	<div class="div-bdtm" style="position:relative;"><div class="img-shade"></div><img class="img-bdtm" src="${basePath}/images/pengai/commom_01.jpg"></div>
	<div class="div-bdtm" id="successDiv" style="margin-top: 20px;">
		<div align="center" ><img class="img-bdtm" style="width: 50px;"  src="${basePath}/images/pengai/succeed_01.png"></div>
		<div id="show" class="font01" style="font-weight: bold;">恭喜您获得活动名额、请支付后完成预约！</div>
		<hr style="background: black; padding: 1px;" />
		<div style="font-weight: bold; padding-left: 30px;">鹏爱医疗美容医院</div>	<hr/>
		
		<div class="ym-grid">
			<div class="ym-g33 ym-gl"><div class="ym-gbox" >
				<div style="display:none;" id="xqp"><img class="img-bdtm"  style="width: 100px; margin-left: 20px;" src="${basePath}/images/pengai/xqp/xqp_01.png"></div>
				<div style="display:none;" id="bdtm"><img class="img-bdtm"  style="width: 100px; margin-left: 20px;" src="${basePath}/images/pengai/bdtm/bdtm_01.png"></div>
				<div style="display:none;" id="nmsg"><img class="img-bdtm"  style="width: 100px; margin-left: 20px;" src="${basePath}/images/pengai/nmsg/nmsg_01.png"></div>
				<div style="display:none;" id="wzzx"><img class="img-bdtm"  style="width: 100px; margin-left: 20px;" src="${basePath}/images/pengai/wzzx/wzzx_01.png"></div>		
			</div></div>
			<div class="ym-g66 ym-gr"><div class="ym-gbox" >						
				<div style="text-align:left; margin-top: 10px;">项目名称：<span id="projectName">&nbsp;</span></div>
				<div style="text-align:left; margin-top: 30px;">项目费用：<span id="projectPrice">&nbsp;</span></div>
			</div></div>
		</div>
		
		<hr/>
		<div style="font-weight: bold;" align="center"> 体检门店：鹏爱医疗美容医院（南山/福田）</div><hr/>
		
		<div style="text-align: center; margin-top: 10px;"><button id="pay" class="button02" onclick="wechatpay()">立即支付</button></div>
		<hr style="background: black; padding: 1px; margin-top: 15px" /><hr/>
		
		<div style="font-weight: bold; padding-left: 30px;">购买须知：</div><hr/>	
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>本活动每人凭手机号限领取一次</div>
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>活动日期：2018.9月1日至2018年10月7日</div>
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>项目预约成功后，有效期为一个月（自预约成功当天开始计算）、逾期将失效，为避免影响您的体验、请合理安排到店时间。</div>
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>预约成功后会有专业客户与您联系、确定具体体验时间及门店，您亦可添加客服微信进行自助预约。</div>
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>本活动不支持退款，请确认后预约</div>
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>到店体验可凭姓名+手机号在前台进行验证、美导将安排专业团队为您服务</div>
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>营业时间为10:00-22:00</div>	
		<div style="padding: 0 20px 5px 30px;"><span>&#8226;&nbsp;</span>活动最终解释权归鹏爱所有</div>
	</div>

	<div class="div-bdtm"><div style="background-color: #f5e5d8; width: 100%; height: 50px">
	</div></div>
	<div class="div-bdtm"><div style="background-color: white; width: 100%; height: 20px">
	</div></div> 
	<div class="div-bdtm" style="position:relative;"><div class="img-shade"></div><img class="img-bdtm" src="${basePath}/images/pengai/commom_09.jpg"></div> 
	<div class="div-bdtm" style="position:relative;"><div class="img-shade"></div><img class="img-bdtm" src="${basePath}/images/pengai/commom_11.jpg"></div>
</div>

</body>
</html>
