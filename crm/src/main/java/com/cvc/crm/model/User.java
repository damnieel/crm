package com.cvc.crm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cvc.crm.util.Util;


public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // 主键ID
	private String username; // 用户名
	private String password; // 密码
	private Integer type; // 用户类型：1=管理员，0=普通用户
	private Integer status; // 状态：0=禁用，1=启用，2=待审，3=临时，4=驳回
	private String desc; // 备注
	private String createdBy; // 创建人
	private Date createdDate; // 创建时间
	private String lastModifiedBy; // 最后更新人
	private Date lastModifiedDate; // 最后更新时间

	public User() {
	}

	public User(Map<String, String> map) {
		this.id = Util.parseLong(map.get("id"), null);
		this.username = map.get("username");
		this.password = map.get("password");
		this.type = Util.parseInt(map.get("type"), null);
		this.status = Util.parseInt(map.get("status"), null);
		this.desc = map.get("desc");
		this.createdBy = map.get("createdBy");
		this.createdDate = Util.parseDate("yyyy/MM/dd HH:mm:ss", map.get("createdDate"), null);
		this.lastModifiedBy = map.get("lastModifiedBy");
		this.lastModifiedDate = Util.parseDate("yyyy/MM/dd HH:mm:ss", map.get("lastModifiedDate"), null);
	}

	public User(Long id, String username, String password, Integer type, Integer status, String desc, String createdBy,
			Date createdDate, String lastModifiedBy, Date lastModifiedDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
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
	
	public Integer getType() {
		return type;
	}
	public String getTypeString() {
		String s = Util.toString(type);
		if ("0".equals(s)) {
			return "普通用户";
		} else if ("1".equals(s)) {
			return "管理员";
		} else {
			return "普通用户";
		}
	}
	public void setType(Integer type) {
		this.type = type;
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
		} else if ("2".equals(s)) {
			return "待审";
		} else if ("3".equals(s)) {
			return "临时";
		} else if ("4".equals(s)) {
			return "驳回";
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

	public Map<String, String> toMap(boolean trim) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", Util.toString(id, null));
		map.put("username", username);
		map.put("password", password);
		map.put("type", Util.toString(type, null));
		map.put("status", Util.toString(status, null));
		map.put("desc", desc);
		map.put("createdBy", createdBy);
		map.put("createdDate", Util.toString("yyyy/MM/dd HH:mm:ss", createdDate, null));
		map.put("lastModifiedBy", lastModifiedBy);
		map.put("lastModifiedDate", Util.toString("yyyy/MM/dd HH:mm:ss", lastModifiedDate, null));
		return trim ? Util.trim(map) : map;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", status="
				+ status + ", desc=" + desc + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

	

}
