package com.cvc.crm.model;

import com.cvc.crm.util.Util;

/**
 * 产品实体类
 * 
 * @author dengrihui
 *
 */
public class Product {

	private String id;// 产品Id 主键
	private String name;// 产品名称
	private String category;// 产品种类
	private String style;// 产品风格
	private String material;// 产品材质
	private String color;// 产品颜色
	private String model;// 产品型号
	private String image;// 产品图片
	private Double price;// 产品名称
	private String status;// 产品状态 0禁用 1启用
	private String desc;
	private String created;
	private String updated;

	public Product() {
	}

	public Product(String id, String name, String category, String style,
			String material, String color, String model, String image,
			Double price, String status, String desc, String created,
			String updated) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.style = style;
		this.material = material;
		this.color = color;
		this.model = model;
		this.image = image;
		this.price = price;
		this.status = status;
		this.desc = desc;
		this.created = created;
		this.updated = updated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getStatusStr() {
		if ("0".equals(status)) {
			return "禁用";
		} else if ("1".equals(status)) {
			return "启用";
		} else {
			return "未知";
		}
	}

	public String getCreatedStr() {
		return Util.datetime(created);
	}

	public String getUpdatedStr() {
		return Util.datetime(updated);
	}

	public String getPriceStr() {
		return "￥" + price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category="
				+ category + ", style=" + style + ", material=" + material
				+ ", color=" + color + ", model=" + model + ", image=" + image
				+ ", price=" + price + ", status=" + status + ", desc=" + desc
				+ ", created=" + created + ", updated=" + updated + "]";
	}

}
