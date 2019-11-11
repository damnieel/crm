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
.button02{background-color: white; color: green; border-radius: 10px; width: 150px; border-style:solid; border-width:1px; border-color:green; padding: 10px 10px;}

.btnActive { background-color: green; color: white; }

</style>

<script>

</script>

</head>
<body>

<div class="div-wrapper">
	<div class="div-bdtm" style="position:relative;"><div class="img-shade"></div><img class="img-bdtm" src="${basePath}/images/pengai/commom_01.jpg"></div>
	<div class="div-bdtm" id="successDiv" style="margin-top: 20px;">
		<div align="center" ><img class="img-bdtm" style="width: 50px;"  src="${basePath}/images/pengai/succeed_02.png"></div>
		<div id="show" class="font01" style="font-weight: bold;">操作成功！请与客服预约体验</div>		
		<div id="writing02" class="font02" style="padding: 5px 5%;">您可添加下方客服人员微信、预约详细到院体验时间、届时凭姓名、手机号码+订单信息到鹏爱医院（南山/福田）前台验证订单信息，进行体验。</div>
		<center><div style=" margin-top: 20px" class="font02">客服微信号：xulingyun1992</div></center>
		<center><div><img style="width: 200px; margin-top: 10px" class="img-bdtm" src="${basePath}/images/pengai/link.jpg"></div></center>
	    <hr style="background: black; padding: 1px; margin-top: 15px;"> 
		<hr/>
		<div style="font-weight: bold;" align="center"> 体检门店：鹏爱医疗美容医院（南山/福田）</div>
	</div>
	<div class="div-bdtm"><div style="background-color: #f5e5d8; width: 100%; height: 20px">
	</div></div>
	<div class="div-bdtm"><div style="background-color: white; width: 100%; height: 20px">
	</div></div>
	<div class="div-bdtm" style="position:relative;"><div class="img-shade"></div><img class="img-bdtm" src="${basePath}/images/pengai/commom_09.jpg"></div> 
	<div class="div-bdtm" style="position:relative;"><div class="img-shade"></div><img class="img-bdtm" src="${basePath}/images/pengai/commom_11.jpg"></div>
</div>

</body>
</html>
