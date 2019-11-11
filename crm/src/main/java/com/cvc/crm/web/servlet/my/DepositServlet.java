package com.cvc.crm.web.servlet.my;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.Deposit;
import com.cvc.crm.service.DepositService;
import com.cvc.crm.util.AlipayCore;
import com.cvc.crm.util.Auth;
import com.cvc.crm.util.HttpRequest;
import com.cvc.crm.util.Local;
import com.cvc.crm.util.QRCodeGeneration;
import com.cvc.crm.util.RDSUtils;
import com.cvc.crm.util.Util;
import com.cvc.crm.util.WXPayCore;
import com.cvc.crm.web.Web;



@WebServlet(name = "MyDepositServlet", urlPatterns = "/my/deposit")
public class DepositServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(DepositServlet.class);

	private DepositService depositService;

	@Override
	public void init() throws ServletException {
		depositService = new DepositService();
	}

	public void query() throws IOException, ServletException {

		/* --- 鉴权 --- */
		Auth auth = Web.getAuth();
		if (auth == null || !auth.isUser()) {
			super.redirect("/signin");
			return;
		}

		/* --- 取参 --- */
		String userId = auth.getIdString();
		String status = Web.getParameter("status");
		String tradeType = Web.getParameter("tradeType");
		String paymentType = Web.getParameter("paymentType");
		String tradeNo = Web.getParameter("tradeNo");
		String tradeDate = Web.getParameter("tradeDate");
		String invoiceIssue = Web.getParameter("invoiceIssue");
		String invoiceId = Web.getParameter("invoiceId");
		String orderBy = Web.getParameter("orderBy");
		String orderOn = Web.getParameter("orderOn");

		/* --- 校验 --- */
		if (!Util.equals(status, "0", "1", "2")) {
			status = null;
		}
		if (!Util.equals(tradeType, "1", "2", "3", "4")) {
			tradeType = null;
		}
		if (!Util.equals(paymentType, "0", "1", "2", "3")) {
			paymentType = null;
		}
		if (!Util.isWord(tradeNo, 1, 64)) {
			tradeNo = null;
		}
		if (!Util.isDate(tradeDate)) {
			tradeDate = null;
		}
		if (!Util.equals(invoiceIssue, "0", "1", "2", "3")) {
			invoiceIssue = null;
		}
		if (!Util.isNumber(invoiceId, 1, 32)) {
			invoiceId = null;
		}
		if (!Util.equals(orderBy, "0", "1", "2", "3", "4")) {
			orderBy = "0";
		}
		if (!Util.equals(orderOn, "1", "2")) {
			orderOn = "2";
		}

		/* --- 回显 --- */
		Web.setAttribute("status", status);
		Web.setAttribute("tradeType", tradeType);
		Web.setAttribute("paymentType", paymentType);
		Web.setAttribute("tradeNo", tradeNo);
		Web.setAttribute("tradeDate", tradeDate);
		Web.setAttribute("invoiceIssue", invoiceIssue);
		Web.setAttribute("invoiceId", invoiceId);
		Web.setAttribute("orderBy", orderBy);
		Web.setAttribute("orderOn", orderOn);

		/* --- 封装 --- */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", status);
		map.put("tradeType", tradeType);
		map.put("paymentType", paymentType);
		map.put("tradeNo", tradeNo);
		map.put("tradeDate", tradeDate);
		map.put("invoiceIssue", invoiceIssue);
		map.put("invoiceId", invoiceId);
		map.put("orderBy", orderBy);
		map.put("orderOn", orderOn);
		map.put("pageNumber", Web.getPageNumber());
		map.put("pageSize", Web.getPageSize());

		/* --- 查询 --- */
		try {
			Web.setPager(depositService.query(map));
		} catch (Exception e) {
			log.error(e);
		}

		super.forward("/deposit_list.jsp");

	}


	public void order() throws IOException {

		/* --- 鉴权 --- */
		Auth auth = Web.getAuth();
		if (auth == null || !auth.isUser()) {
			Web.render("45403");
			return;
		}

		/* --- 取参 --- */
		String amount = Web.getParameter("amount"); // 充值金额（元）
		String paymentType = Web.getParameter("paymentType"); // 支付方式

		/* --- 校验 --- */
		if (!Util.isNumber(amount, 3, 5, 100, 99999)) {
			Web.render("45010");
			return;
		}
		if (!Util.equals(paymentType, "1", "2")) {
			Web.render("45020");
			return;
		}

		/* --- 封装 --- */
		Deposit o = new Deposit();
		o.setId(Util.id());
		o.setUserId(auth.getId());
		o.setAmount(Util.parseInt(amount, null) * 1000);
		o.setBefore(0);
		o.setAfter(0);
		o.setTradeType(1);
		o.setPaymentType(Util.parseInt(paymentType, null));
		o.setStatus(2);
		o.setTradeNo(Util.uuid());
		o.setTradeDate(new Date());
		o.setInvoiceIssue(0);
		o.setInvoiceId(0L);
		o.setDesc(null);
		o.setCreatedBy(auth.getUsername());
		o.setCreatedDate(new Date());
		o.setLastModifiedBy(auth.getUsername());
		o.setLastModifiedDate(new Date());

		/* --- 封装 --- */
		try {
			int row = depositService.insert(o);
			
			//如果是微信支付  存入redis
			if(2 == Integer.valueOf(paymentType)){
				String result = RDSUtils.hmset(o.getIdString(), o.toMap(true));
				log.info("订单ID"+o.getIdString()+"存入redis结果:"+result);
				//设置过期时间为5分钟
				RDSUtils.expire(o.getIdString(), 5*60);
			}
			Web.render("25200", row > 0 ? o.getIdString() : "25204");
		} catch (Exception e) {
			log.error(e);
			Web.render("55010");
		}

	}

	public void pay() throws ServletException, IOException {

		/* --- 鉴权 --- */
		Auth auth = Web.getAuth();
		if (auth == null || !auth.isUser()) {
			super.forward("/deposit_online.jsp");
			return;
		}

		/* --- 取参 --- */
		String id = Web.getParameter("id");

		/* --- 校验 --- */
		if (!Util.isNumber(id, 1, 32)) {
			super.forward("/deposit_online.jsp");
			return;
		}

		Deposit o = null;
		try {
			o = depositService.load(id);
		} catch (Exception e) {
			log.error(e);
			super.forward("/deposit_online.jsp");
			return;
		}

		if (o == null) {
			super.forward("/deposit_online.jsp");
			return;
		}

		if (!Util.equals(auth.getIdString(), o.getUserIdString())) {
			super.forward("/deposit_online.jsp");
			return;
		}

		if (!Util.equals(o.getStatus(), "2")) {
			super.forward("/deposit_online.jsp");
			return;
		}

		/* --- 参数 --- */
		Map<String, String> map = new HashMap<String, String>();
		map.put("_input_charset", AlipayCore.INPUT_CHARSET); // 编码
		map.put("service", "create_direct_pay_by_user");

		map.put("partner", AlipayCore.PARTNER); // 合作身份者ID
		map.put("seller_email", AlipayCore.SELLER_EMAIL); // 收款支付宝账号

		map.put("subject", "信信客充值"); // 订单名称
		map.put("body", "信信客充值"); // 订单描述

		map.put("out_trade_no", o.getTradeNo()); // 商户订单号
		map.put("total_fee", String.valueOf(o.getAmount() / 1000)); // 付款金额
		map.put("payment_type", "1"); // 支付类型，必填，不能修改

		map.put("show_url", Web.rootPath() + "/my/deposit/list"); // 商品展示地址
		map.put("notify_url", Web.rootPath() + "/pay/alipay/notify"); // 服务器异步通知
		map.put("return_url", Web.rootPath() + "/pay/alipay/return"); // 页面跳转同步通知

		// 注意：以下参数需在签名生成后加入
		map.put("sign", AlipayCore.sign(map));
		map.put("sign_type", AlipayCore.SIGN_TYPE); // 签名方式 不需修改
		map.put("gateway", AlipayCore.GATEWAY); // 支付宝提供给商户的服务接入网关URL(新)

		log.debug(Local.id() + ", " + map);

		Web.setAttribute("alipay", map);
		Web.forward("/views/pay/alipay/alipay.jsp");

	}

	// 可索取发票总额
	public void invoiceTotal() throws IOException {

		/* --- 鉴权 --- */
		Auth auth = Web.getAuth();
		if (auth == null || !auth.isUser()) {
			Web.render("45403");
			return;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", auth.getIdString());

		try {
			BigDecimal total = depositService.invoiceTotal(map);
			Web.render("25200", total == null ? 0 : total.intValue());
		} catch (Exception e) {
			log.error(e);
			Web.render("55010");
		}

	}
	
	public void weixinPay() throws ServletException, IOException{
		
		/* --- 鉴权 --- */
		Auth auth = Web.getAuth();
		if (auth == null || !auth.isUser()) {
			super.forward("/deposit_online.jsp");
			return;
		}

		/* --- 取参 --- */
		String id = Web.getParameter("id");

		/* --- 校验 --- */
		if (!Util.isNumber(id, 1, 32)) {
			super.forward("/deposit_online.jsp");
			return;
		}

		Deposit o = null;
		try {
			o = depositService.load(id);
		} catch (Exception e) {
			log.error(e);
			super.forward("/deposit_online.jsp");
			return;
		}

		if (o == null) {
			super.forward("/deposit_online.jsp");
			return;
		}

		if (!Util.equals(auth.getIdString(), o.getUserIdString())) {
			super.forward("/deposit_online.jsp");
			return;
		}

		if (!Util.equals(o.getStatus(), "2")) {
			super.forward("/deposit_online.jsp");
			return;
		}
		
		/* --- 参数 --- */
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", WXPayCore.APP_ID); 
		map.put("mch_id", WXPayCore.MCH_ID); 
		map.put("nonce_str", WXPayCore.generateNonceStr()); 
		map.put("body", "信信客充值"); 
		map.put("out_trade_no", o.getTradeNo()); 
		/*map.put("total_fee", String.valueOf(o.getAmount() / 1000*100)); //微信金额单位为分 所以还要*100*/
		map.put("total_fee", String.valueOf(1)); //测试用:1分钱
		map.put("spbill_create_ip", WXPayCore.getIpAddress(Web.getRequest())); 
		map.put("notify_url", Web.rootPath() + "/pay/wxpay/notify"); 
		map.put("trade_type", WXPayCore.TRADE_TYPE); 
		
		//生成签名
		try {
			String sign = WXPayCore.generateSignature(map, WXPayCore.PATERNER_KEY);
			map.put("sign", sign);
		} catch (Exception e) {
			log.error("生成签名异常:",e);
			Web.render("55070");
			return;
		}
		
		//转xml
		String xml = null;
		try {
			 xml = WXPayCore.mapToXml(map);
		} catch (Exception e) {
			log.error("生成XMl异常:",e);
			Web.render("55071");
			return;
		}
		
		//获取报文
		String result = null;
		try {
			 result = HttpRequest.post(WXPayCore.UFDODER_URL, xml);
		} catch (Exception e) {
			log.error("获取包含二维码的报文异常:",e);
			Web.render("55072");
			return;
		}
		
		//转map
		Map<String, String> mapresult = null;
		try {
			 mapresult = WXPayCore.xmlToMap(result);
		} catch (Exception e) {
			log.error("XML转Map异常:",e);
			Web.render("55073");
			return;
		}
		
		//获取二维码链接
		String urlCode = (String) mapresult.get("code_url");
		
		if(Util.isEmpty(urlCode)){
			super.forward("/deposit_online.jsp");
			return;
		}
		
		//转BASE64编码
		String qrcode = null;
		try {
			   qrcode = QRCodeGeneration.url2BASE64Code(urlCode);
		} catch (Exception e) {
			log.error("URL转BASE64编码异常:",e);
			Web.render("55074");
			return;
		}
		
		Web.render("25200", qrcode);
	}

	public void getOrderStatus() throws IOException, ServletException {

		/* --- 鉴权 --- */
		Auth auth = Web.getAuth();
		if (auth == null || !auth.isUser()) {
			Web.render("45403");
			return;
		}

		/* --- 取参 --- */
		String id = Web.getParameter("id"); // 订单id

		/* --- 校验 --- */
		if (!Util.isNumber(id, 1, 32)) {
			super.forward("/deposit_online.jsp");
			return;
		}
		
		Map<String, String>  map = RDSUtils.hgetAll(id);
		
		//map为空 此订单失效或者无效
		if(map.isEmpty()||Util.isEmpty(map.get("status"))){
			Web.render("25200", 3 );
			return;
		}
		
		String status = map.get("status");
		
		//为1的时候 表示付款成功  
		if(1 == Integer.valueOf(status)){
			Web.render("25200", 1 );
		}else{
			//为0 或者 2 的 表示付款未成功
			Web.render("25200", 0 );
		}
		
	}
}
