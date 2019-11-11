package com.cvc.crm.model;

import java.io.Serializable;
import java.util.Date;

import com.cvc.crm.util.Util;



public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // 主键ID
	private String username; // 用户名
	private String password; // 密码
	private Integer status; // 状态：0=禁用，1=启用
	private String desc; // 备注
	private String createdBy; // 创建人
	private Date createdDate; // 创建时间
	private String lastModifiedBy; // 最后更新人
	private Date lastModifiedDate; // 最后更新时间

	public Admin() {
	}

	public Admin(Long id, String username, String password, Integer status, String desc, String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
		this.desc = desc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getId() {
		return id;
	}

	public String getIdString() {
		return Util.toString(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusString() {
		String s = Util.toString(status);
		if ("0".equals(s)) {
			return "禁用";
		} else if ("1".equals(s)) {
			return "启用";
		} else {
			return "未知";
		}
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + ", status=" + status + ", desc=" + desc + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

}
