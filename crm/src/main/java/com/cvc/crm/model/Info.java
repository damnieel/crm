package com.cvc.crm.model;

import java.util.Date;

import com.cvc.crm.util.Util;


public class Info {

	private Long id; // 主键ID 
	private Date createdDate; // 创建时间
	private String remoteAddr; // 主机
	private String requestUri; // 请求地址
	private String userAgent; // 用户代理
	private String protocol; // 协议
	private String method; // 方法
	private String accept; // 请求头
	private String acceptLanguage; // 语言
	private String acceptEncoding; // 编码
	private Integer scanTime; // 浏览时间
	private Integer scanPlace; // 浏览位置 1=产品介绍 2=表单填写 3=公司简介

	


	public String getCreatedDateStr() {
		return Util.format("yyyy-MM-dd HH:mm:ss", this.createdDate);
	}

	public Info() {
	}



	public Info(Long id, Date createdDate, String remoteAddr,
			String requestUri, String userAgent, String protocol,
			String method, String accept, String acceptLanguage,
			String acceptEncoding, Integer scanTime, Integer scanPlace) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.remoteAddr = remoteAddr;
		this.requestUri = requestUri;
		this.userAgent = userAgent;
		this.protocol = protocol;
		this.method = method;
		this.accept = accept;
		this.acceptLanguage = acceptLanguage;
		this.acceptEncoding = acceptEncoding;
		this.scanTime = scanTime;
		this.scanPlace = scanPlace;
	}

	public Integer getScanTime() {
		return scanTime;
	}

	public void setScanTime(Integer scanTime) {
		this.scanTime = scanTime;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public Integer getScanPlace() {
		return scanPlace;
	}

	public void setScanPlace(Integer scanPlace) {
		this.scanPlace = scanPlace;
	}

	public String getScanPlaceString() {
		if (scanPlace == 1) {
			return "产品介绍";
		}
		if (scanPlace == 2) {
			return "表单填写";
		}
		if (scanPlace == 3) {
			return "公司介绍";
		}
		return "产品介绍";
	}

	@Override
	public String toString() {
		return "Info [id=" + id + ", createdDate=" + createdDate
				+ ", remoteAddr=" + remoteAddr + ", requestUri=" + requestUri
				+ ", userAgent=" + userAgent + ", protocol=" + protocol
				+ ", method=" + method + ", accept=" + accept
				+ ", acceptLanguage=" + acceptLanguage + ", acceptEncoding="
				+ acceptEncoding + ", scanTime=" + scanTime + ", scanPlace="
				+ scanPlace + "]";
	}


}
