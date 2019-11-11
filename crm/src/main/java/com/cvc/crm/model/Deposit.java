package com.cvc.crm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cvc.crm.util.Util;


public class Deposit implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // 主键ID
	private Long userId; // 用户ID
	private Integer amount; // 金额（厘：1000厘=100分=10角=1元）
	private Integer before; // 前量（厘：1000厘=100分=10角=1元）
	private Integer after; // 后量（厘：1000厘=100分=10角=1元）
	private Integer tradeType; // 充值类型：1=缴费充值，2=赠送充值，3=返量充值，4=人工扣费
	private Integer paymentType; // 支付类型：0=无，1=支付宝，2=微信，3=银行汇款
	private Integer status; // 状态：0=失败，1=成功，2=待付款
	private String tradeNo; // 交易号
	private Date tradeDate; // 交易时间
	private Integer invoiceIssue; // 发票开具：0=未开具，1=已开具，2=审核中，3=不支持
	private Long invoiceId; // 发票ID
	private String desc; // 备注
	private String createdBy; // 创建人
	private Date createdDate; // 创建时间
	private String lastModifiedBy; // 最后更新人
	private Date lastModifiedDate; // 最后更新时间

	/* --- 扩展 --- */
	private String username; // 用户名

	public Deposit() {
	}

	public Deposit(Long id, Long userId, Integer amount, Integer before, Integer after, Integer tradeType, Integer paymentType, Integer status, String tradeNo, Date tradeDate, Integer invoiceIssue, Long invoiceId, String desc, String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate, String username) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
		this.before = before;
		this.after = after;
		this.tradeType = tradeType;
		this.paymentType = paymentType;
		this.status = status;
		this.tradeNo = tradeNo;
		this.tradeDate = tradeDate;
		this.invoiceIssue = invoiceIssue;
		this.invoiceId = invoiceId;
		this.desc = desc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public String getIdString() {
		return String.valueOf(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserIdString() {
		return String.valueOf(userId);
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getBefore() {
		return before;
	}

	public void setBefore(Integer before) {
		this.before = before;
	}

	public Integer getAfter() {
		return after;
	}

	public void setAfter(Integer after) {
		this.after = after;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public String getTradeTypeString() {
		String s = Util.toString(tradeType);
		if ("1".equals(s)) {
			return "缴费充值";
		} else if ("2".equals(s)) {
			return "赠送充值";
		} else if ("3".equals(s)) {
			return "返量充值";
		} else if ("4".equals(s)) {
			return "人工扣费";
		} else {
			return "未知";
		}
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public String getPaymentTypeString() {
		String s = Util.toString(paymentType);
		if ("0".equals(s)) {
			return "无";
		} else if ("1".equals(s)) {
			return "支付宝";
		} else if ("2".equals(s)) {
			return "微信";
		} else if ("3".equals(s)) {
			return "银行汇款";
		} else {
			return "未知";
		}
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusString() {
		String s = Util.toString(status);
		if ("0".equals(s)) {
			return "失败";
		} else if ("1".equals(s)) {
			return "成功";
		} else if ("2".equals(s)) {
			return "待付款";
		} else {
			return "未知";
		}
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public String getTradeDateStr() {
		return Util.toString("yyyy/MM/dd", tradeDate, null);
	}

	public String getTradeDateString() {
		return Util.toString("yyyy/MM/dd HH:mm:ss", tradeDate, null);
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Integer getInvoiceIssue() {
		return invoiceIssue;
	}

	public String getInvoiceIssueString() {
		String s = Util.toString(invoiceIssue);
		if ("0".equals(s)) {
			return "未开具";
		} else if ("1".equals(s)) {
			return "已开具";
		} else if ("2".equals(s)) {
			return "审核中";
		} else if ("3".equals(s)) {
			return "不支持";
		} else {
			return "未知";
		}
	}

	public void setInvoiceIssue(Integer invoiceIssue) {
		this.invoiceIssue = invoiceIssue;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public String getInvoiceIdString() {
		return String.valueOf(invoiceId);
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedDateStr() {
		return Util.toString("yyyy/MM/dd", createdDate, null);
	}

	public String getCreatedDateString() {
		return Util.toString("yyyy/MM/dd HH:mm:ss", createdDate, null);
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getLastModifiedDateStr() {
		return Util.toString("yyyy/MM/dd", lastModifiedDate, null);
	}

	public String getLastModifiedDateString() {
		return Util.toString("yyyy/MM/dd HH:mm:ss", lastModifiedDate, null);
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, String> toMap(boolean trim) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", Util.toString(id));
		map.put("userId", Util.toString(userId));
		map.put("amount", Util.toString(amount));
		map.put("before", Util.toString(before));
		map.put("after", Util.toString(after));
		map.put("tradeType", Util.toString(tradeType));
		map.put("paymentType", Util.toString(paymentType));
		map.put("status", Util.toString(status));
		map.put("tradeNo", tradeNo);
		map.put("tradeDate", Util.toString("yyyy/MM/dd HH:mm:ss", tradeDate, null));
		map.put("invoiceIssue", Util.toString(invoiceIssue));
		map.put("invoiceId", Util.toString(invoiceId));
		map.put("desc", desc);
		map.put("createdBy", createdBy);
		map.put("createdDate", Util.toString("yyyy/MM/dd HH:mm:ss", createdDate, null));
		map.put("lastModifiedBy", lastModifiedBy);
		map.put("lastModifiedDate", Util.toString("yyyy/MM/dd HH:mm:ss", lastModifiedDate, null));
		
		map.put("username", username);
		
		return trim ? Util.trim(map) : map;
	}
	
	@Override
	public String toString() {
		return "Deposit [id=" + id + ", userId=" + userId + ", amount=" + amount + ", before=" + before + ", after=" + after + ", tradeType=" + tradeType + ", paymentType=" + paymentType + ", status=" + status + ", tradeNo=" + tradeNo + ", tradeDate=" + tradeDate + ", invoiceIssue=" + invoiceIssue + ", invoiceId=" + invoiceId + ", desc=" + desc + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + ", username=" + username + "]";
	}

}
