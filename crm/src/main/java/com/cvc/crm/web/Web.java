package com.cvc.crm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvc.crm.model.User;
import com.cvc.crm.util.Auth;
import com.cvc.crm.util.JSONRender;
import com.cvc.crm.util.Local;
import com.cvc.crm.util.Pager;
import com.cvc.crm.util.Render;
import com.cvc.crm.util.Util;


/**
 * 
 * Web上下文
 * 
 * @author dengrihui
 * @version 1.0,  2018/10/12
 *
 */
public class Web {

	public static HttpServletRequest getRequest() {
		try {
			return (HttpServletRequest) Local.get("request");
		} catch (Exception e) {
			return null;
		}
	}

	public static HttpServletResponse getResponse() {
		try {
			return (HttpServletResponse) Local.get("response");
		} catch (Exception e) {
			return null;
		}
	}

	public static void setCharacterEncoding(String target, String charset) throws UnsupportedEncodingException {
		if ("request".equalsIgnoreCase(target)) {
			getRequest().setCharacterEncoding(charset);
		} else if ("response".equalsIgnoreCase(target)) {
			getResponse().setCharacterEncoding(charset);
		} else {
			getRequest().setCharacterEncoding(charset);
			getResponse().setCharacterEncoding(charset);
		}
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public static ServletContext getContext() {
		return getRequest().getServletContext();
	}

	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	public static Object getAttribute(String name) {
		return getAttribute("request", name);
	}

	public static Object getAttribute(String scope, String name) {
		if ("request".equalsIgnoreCase(scope)) {
			return getRequest().getAttribute(name);
		} else if ("session".equalsIgnoreCase(scope)) {
			return getSession().getAttribute(name);
		} else if ("context".equalsIgnoreCase(scope)) {
			return getContext().getAttribute(name);
		} else {
			return null;
		}
	}

	public static void setAttribute(String name, Object value) {
		setAttribute("request", name, value);
	}

	public static void setAttribute(String scope, String name, Object value) {
		if ("request".equalsIgnoreCase(scope)) {
			getRequest().setAttribute(name, value);
		} else if ("session".equalsIgnoreCase(scope)) {
			getSession().setAttribute(name, value);
		} else if ("context".equalsIgnoreCase(scope)) {
			getContext().setAttribute(name, value);
		}
	}

	public static void removeAttribute(String name) {
		removeAttribute("request", name);
	}

	public static void removeAttribute(String scope, String name) {
		if ("request".equalsIgnoreCase(scope)) {
			getRequest().removeAttribute(name);
		} else if ("session".equalsIgnoreCase(scope)) {
			getSession().removeAttribute(name);
		} else if ("context".equalsIgnoreCase(scope)) {
			getContext().removeAttribute(name);
		}
	}

	public static void forward(String path) throws ServletException, IOException {
		getRequest().getRequestDispatcher(path).forward(getRequest(), getResponse());
	}

	public static void sendRedirect(String location) throws IOException {
		getResponse().sendRedirect(location);
	}

	public static void redirect(String location) throws IOException {
		sendRedirect(basePath() + (location == null ? "" : location));
	}

	public static void write(String s) throws IOException {
		PrintWriter out = getResponse().getWriter();
		out.write(s);
		out.flush();
	}

	public static String getRequestURI() {
		return getRequest().getRequestURI();
	}

	/**
	 * 
	 * 获取根路径
	 * 
	 * @return
	 * 
	 */
	public static String basePath() {
		return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath();
	}

	

	/**
	 * 
	 * 检查操作是否“暗含”指定权限的操作
	 * 
	 * @param input
	 * @return
	 * 
	 */
	public static boolean implies(String... input) {
		if (input == null || input.length == 0) {
			return false;
		}
		String type = String.valueOf(getAttribute("session", "SESSION_USER_TYPE"));
		for (String s : input) {
			if (s == null || s.length() == 0) {
				continue;
			}
			if (s.equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 获取当前登录用户
	 * 
	 * @return
	 * 
	 */
	public static User getUser() {
		try {
			return (User) getAttribute("session", "SESSION_USER");
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 
	 * 设置当前登录用户
	 * 
	 * @param user
	 * 
	 */
	public static void setUser(User user) {
		setAttribute("session", "SESSION_USER", user);
		setAttribute("session", "SESSION_USER_ID", user.getId());
		setAttribute("session", "SESSION_USER_USERNAME", user.getUsername());
		setAttribute("session", "SESSION_USER_PASSWORD", user.getPassword());
		setAttribute("session", "SESSION_USER_TYPE", user.getType());
		setAttribute("session", "SESSION_USER_STATUS", user.getStatus());
		setAttribute("session", "SESSION_USER_DESC", user.getDesc());
	}

	/**
	 * 
	 * 移除当前登录用户
	 * 
	 */
	public static void removeUser() {
		removeAttribute("session", "SESSION_USER");
		removeAttribute("session", "SESSION_USER_ID");
		removeAttribute("session", "SESSION_USER_USERNAME");
		removeAttribute("session", "SESSION_USER_PASSWORD");
		removeAttribute("session", "SESSION_USER_TYPE");
		removeAttribute("session", "SESSION_USER_STATUS");
		removeAttribute("session", "SESSION_USER_DESC");
		removeAttribute("session", "SESSION_USER_CREATED");
		removeAttribute("session", "SESSION_USER_UPDATED");
	}

	/**
	 * 
	 * 销毁会话(Session)
	 * 
	 */
	public static void invalidate() {
		removeUser();
		getSession().invalidate();
	}

	/**
	 * 
	 * 获取方法
	 * 
	 * @return
	 * 
	 */
	public static String getMethod() {
		return getParameter("method");
	}

	/**
	 * 
	 * 匹配方法
	 * 
	 * @param input
	 * @return
	 * 
	 */
	public static boolean isMethod(String input) {
		if (input == null) {
			return getMethod() == null;
		} else {
			return input.equalsIgnoreCase(getMethod());
		}
	}

	/**
	 * 
	 * 获取动作
	 * 
	 * @return
	 * 
	 */
	public static String getAction() {
		return getParameter("action");
	}

	/**
	 * 
	 * 匹配动作
	 * 
	 * @param input
	 * @return
	 * 
	 */
	public static boolean isAction(String input) {
		if (input == null) {
			return getAction() == null;
		} else {
			return input.equalsIgnoreCase(getAction());
		}
	}

	/**
	 * 
	 * 获取用户IP
	 * 
	 * @return
	 * 
	 */
	public static String getRemoteAddr() {
		String addr = getRequest().getHeader("X-Forwarded-For");
		if (addr == null || addr.length() == 0 || "unknown".equalsIgnoreCase(addr)) {
			addr = getRequest().getHeader("Proxy-Client-IP");
		}
		if (addr == null || addr.length() == 0 || "unknown".equalsIgnoreCase(addr)) {
			addr = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (addr == null || addr.length() == 0 || "unknown".equalsIgnoreCase(addr)) {
			addr = getRequest().getHeader("X-Real-IP");
		}
		if (addr == null || addr.length() == 0 || "unknown".equalsIgnoreCase(addr)) {
			addr = getRequest().getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(addr) ? "127.0.0.1" : addr;
	}

	/**
	 * 
	 * 设置 Render
	 * 
	 * @param code
	 * @param data
	 * 
	 */
	public static void setRender(String code, String data) {
		Render<String> render = new Render<String>();
		render.setCode(code);
		render.setData(data);
		setRender(render);
	}

	/**
	 * 
	 * 设置 Render
	 * 
	 * @param render
	 * 
	 */
	public static void setRender(Render<?> render) {
		setAttribute("request", "render", render);
	}
	
	public static void render(String value) throws IOException {
		render(value, value);
	}

	/**
	 * 
	 * 输出 Render
	 * 
	 * @param code
	 * @param data
	 * @throws IOException
	 * 
	 */
	public static void render(Object code, Object data) throws IOException {
		Render<String> render = new JSONRender<String>();
		render.setCode(String.valueOf(code));
		render.setData(String.valueOf(data));
		render(render);
	}

	/**
	 * 
	 * 输出 Render
	 * 
	 * @param render
	 * @throws IOException
	 * 
	 */
	public static void render(Render<?> render) throws IOException {
		write(render.toString());
	}

	/**
	 * 
	 * 设置 Pager
	 * 
	 * @param pager
	 * 
	 */
	public static void setPager(Pager<?> pager) {
		setAttribute("request", "pager", pager);
	}

	/**
	 * 
	 * 获取页码
	 * 
	 * @return
	 * 
	 */
	public static String getPageNumber() {
		return getParameter("pageNumber");
	}

	/**
	 * 
	 * 获取分页大小
	 * 
	 * @return
	 * 
	 */
	public static String getPageSize() {
		return getParameter("pageSize");
	}

	public static boolean isAttr(boolean ignoreCase, String input, String scope, String name, boolean remove) {
		if (input == null) {
			return false;
		} else {
			String another = String.valueOf(getAttribute(scope, name));
			if (remove) {
				Web.removeAttribute(scope, name);
			}
			return Util.eq(ignoreCase, input, another);
		}
	}

	public static String rootPath() {
		return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath();
	}

	public static Auth getAuth() {
		return null;
	}
}
