package com.cvc.crm.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.cvc.crm.util.JSONRender;



public class TaglibTag extends SimpleTagSupport {

	private JspWriter out;

	private String method;
	private String args;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	@Override
	public void doTag() throws JspException, IOException {

		this.out = getJspContext().getOut();

		try {
			this.getClass().getMethod(method).invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(new JSONRender<String>("55010", "55010"));
		}

	}

	// 格式化日期
	public void formatDate() throws IOException {

		int length = args == null ? 0 : args.length();

		if (length < 1) {
			out.print("");
			return;
		}

		if (length > 32) {
			out.print(args);
			return;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			if (i == 4 || i == 6) {
				sb.append("/");
			}
			if (i == 8) {
				sb.append(" ");
			}
			if (i == 10 || i == 12) {
				sb.append(":");
			}
			sb.append(args.charAt(i));
		}

		out.print(sb);

	}

	// 格式化时间
	public void formatTime() throws IOException {

		int length = args == null ? 0 : args.length();

		if (length < 1) {
			out.print("");
			return;
		}

		if (length > 32) {
			out.print(args);
			return;
		}

		args = args.substring(length - 6, length);

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < args.length(); i++) {
			if (i == 2 || i == 4) {
				sb.append(":");
			}
			sb.append(args.charAt(i));
		}

		out.print(sb);

	}

}
