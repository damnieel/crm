package com.cvc.crm.util;

public class Render<T> {

	private String code;
	private T data;

	public Render() {
	}

	public Render(String code) {
		this.code = code;
	}

	public Render(String code, T data) {
		this.code = code;
		this.data = data;
	}

	public void set(String code, T data) {
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Render [code=" + code + ", data=" + data + "]";
	}

}
