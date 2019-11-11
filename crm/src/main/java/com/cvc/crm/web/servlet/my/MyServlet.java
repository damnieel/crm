package com.cvc.crm.web.servlet.my;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.cvc.crm.web.Web;
import com.cvc.crm.web.servlet.BaseServlet;


/**
 * 
 * 我的
 * 
 * @version 1.0, 2018/10/10
 *
 */
@WebServlet(name = "MyServlet", urlPatterns = "/my")
public class MyServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	public static String basePath() {
		return Web.rootPath() + "/my";
	}

	public void redirect(String location) throws IOException {
		Web.sendRedirect(basePath() + (location == null ? "" : location));
	}

	public void forward(String path) throws ServletException, IOException {
		Web.forward("/views/my" + path);
	}
}	
