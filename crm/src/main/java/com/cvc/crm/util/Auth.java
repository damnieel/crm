package com.cvc.crm.util;

import java.io.Serializable;
import java.util.Date;

import com.cvc.crm.model.Admin;
import com.cvc.crm.model.User;


public class Auth implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // 主键ID
	private String username; // 用户名
	private String password; // 密码
	private Integer type; // 类型：0=管理员，1=普通用户
	private Integer status; // 状态：0=禁用，1=启用
	private String desc; // 备注
	private String createdBy; // 创建人
	private Date createdDate; // 创建时间
	private String lastModifiedBy; // 最后更新人
	private Date lastModifiedDate; // 最后更新时间

	public Auth(Admin o) {
		this.id = o.getId();
		this.username = o.getUsername();
		this.password = o.getPassword();
		this.type = 0;
		this.status = o.getStatus();
		this.desc = o.getDesc();
		this.createdBy = o.getCreatedBy();
		this.createdDate = o.getCreatedDate();
		this.lastModifiedBy = o.getLastModifiedBy();
		this.lastModifiedDate = o.getLastModifiedDate();
	}

	public Auth(User o) {
		this.id = o.getId();
		this.username = o.getUsername();
		this.password = o.getPassword();
		this.type = 1;
		this.status = o.getStatus();
		this.desc = o.getDesc();
		this.createdBy = o.getCreatedBy();
		this.createdDate = o.getCreatedDate();
		this.lastModifiedBy = o.getLastModifiedBy();
		this.lastModifiedDate = o.getLastModifiedDate();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
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

	public boolean isAdmin() {
		return type != null && type == 1;
	}

	public boolean isUser() {
		return type != null && type == 0;
	}

	public User getUser() {

		if (!isUser()) {
			return null;
		}

		User o = new User();
		o.setId(id);
		o.setUsername(username);
		o.setPassword(password);
		o.setStatus(status);
		o.setDesc(desc);
		o.setCreatedBy(createdBy);
		o.setCreatedDate(createdDate);
		o.setLastModifiedBy(lastModifiedBy);
		o.setLastModifiedDate(lastModifiedDate);

		return o;

	}

	@Override
	public String toString() {
		return "Auth [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", status=" + status + ", desc=" + desc + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

}
