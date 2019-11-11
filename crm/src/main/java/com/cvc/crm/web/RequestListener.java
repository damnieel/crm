package com.cvc.crm.web;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 
 * Servlet Request Listener
 * 
 * @author dengrihui
 * @version 1.0, 2018/10/12
 *
 */
@WebListener
public class RequestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
	}

}
