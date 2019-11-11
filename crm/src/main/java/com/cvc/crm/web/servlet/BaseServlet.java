package com.cvc.crm.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.util.Local;
import com.cvc.crm.web.Web;


/**
 * 
 * Base Servlet
 * 
 * @author dengrihui
 * @version 1.0, 2018/10/17
 *
 */
public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(BaseServlet.class);

	/**
	 * 
	 * 业务入口
	 * 
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

		Local.put("request", req);
		Local.put("response", res);
		setCharacterEncoding();

		log.debug(Web.getRequestURI() + ", " + Web.getMethod() + ", " + Web.getAction() + ", " + Web.getRemoteAddr());

		service();

	}

	/**
	 * 
	 * 设置编码
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public void setCharacterEncoding() throws UnsupportedEncodingException {
		Web.setCharacterEncoding("", "UTF-8");
	}

	/**
	 * 
	 * 业务处理
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public void service() throws ServletException, IOException {

		if (Web.isMethod("service")) {
			exception(new IllegalArgumentException("40000"));
			return;
		}

		try {
			this.getClass().getMethod(Web.getMethod()).invoke(this);
		} catch (Exception e) {
			exception(e);
		}

	}

	/**
	 * 
	 * 异常处理
	 * 
	 * @param e
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public void exception(Exception e) throws ServletException, IOException {
		log.error("Exception: ", e);
		Web.render("50000", "50000");
	}

}
