package com.cvc.crm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 支付宝工具类 <br>
 * 
 * https://doc.open.alipay.com/doc2/detail?treeId=58&articleId=103584&docType=1
 * 
 * @author chenfan
 * @version 1.0, 2015/10/07
 *
 */
public class AlipayCore {

	private static Log log = LogFactory.getLog(AlipayCore.class);

	// 支付宝提供给商户的服务接入网关URL(新)
	public static final String GATEWAY = "https://mapi.alipay.com/gateway.do";

	// 商户的私钥
	public static final String KEY = "occ7v0fb2rqjy8vk8m7dkv1x58cymfy2";

	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static final String PARTNER = "2088011254503326";

	// 收款支付宝账号，一般情况下收款账号就是签约账号
	public static final String SELLER_EMAIL = "xinxinkepay@winyz.com";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static final String INPUT_CHARSET = "utf-8";

	// 签名方式 不需修改
	public static final String SIGN_TYPE = "MD5";

	// 签名时需要忽略的参数集合
	private static List<String> ignore;

	// 初始化
	static {
		ignore = new ArrayList<String>();
		ignore.add("sign");
		ignore.add("sign_type");
	}

	/**
	 * 
	 * 签名
	 * 
	 * @param map
	 * @return
	 * 
	 */
	public static String sign(Map<String, String> map) {

		log.debug("签名参数(map)：" + map);

		// 过滤空参
		if (map == null || map.isEmpty()) {
			return null;
		}

		// 排序参数
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);

		// 过滤空参，组装参数键值
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {

			String key = keys.get(i);
			String value = map.get(key);

			if (isEmpty(key, value) || ignore.contains(key)) {
				continue;
			}

			sb.append(i > 0 ? "&" : "");
			sb.append(key).append("=").append(value);

		}
		sb.append(KEY);

		log.debug("待签名字符串：" + sb.toString());

		// 获取MD5签名
		try {
			return Util.md5(sb.toString());
		} catch (Exception e) {
			log.error("签名发生异常：", e);
			return null;
		}

	}

	/**
	 * 
	 * 空值校验
	 * 
	 * @param input
	 * @return
	 * 
	 */
	private static boolean isEmpty(String... input) {

		if (input == null || input.length == 0) {
			return true;
		}

		for (String s : input) {
			if (s == null || s.length() == 0) {
				return true;
			}
		}

		return false;

	}

	/**
	 * 
	 * 校验
	 * 
	 * @param map
	 * @return
	 * 
	 */
	public static boolean verify(Map<String, String> map) {

		log.debug("校验参数(map)：" + map);

		// 过滤空参
		if (map == null || map.isEmpty()) {
			return false;
		}

		String sign = map.get("sign");
		String signType = map.get("sign_type");
		String notifyId = map.get("notify_id");

		// 过滤不匹配参数
		if (isEmpty(sign, notifyId) || !SIGN_TYPE.equals(signType)) {
			log.debug("参数不匹配！");
			return false;
		}

		// 校验签名
		if (!sign.equals(sign(map))) {
			log.debug("签名不匹配！");
			return false;
		}

		// 校验 notify_id
		String result = "false";
		try {
			URL url = new URL(GATEWAY + "?service=notify_verify&partner=" + PARTNER + "&notify_id=" + notifyId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = br.readLine();
			br.close();
			conn.disconnect();
		} catch (Exception e) {
			log.error("校验(notify_id)发生异常：" + e);
			return false;
		}

		log.debug("校验(notify_id)结果：" + result);

		return "true".equals(result);

	}

}
