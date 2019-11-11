package com.cvc.crm.util;

import net.sf.json.JSONObject;

public class JSONRender<T> extends Render<T> {

	public JSONRender() {
		super();
	}

	public JSONRender(String code) {
		super(code);
	}

	public JSONRender(String code, T data) {
		super(code, data);
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}