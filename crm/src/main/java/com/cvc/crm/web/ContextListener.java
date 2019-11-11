package com.cvc.crm.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Servlet Context Listener
 * 
 * @author dengrihui
 * @version 1.0, 2018/10/12
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static Log log = LogFactory.getLog(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.debug("=== IntelHome Context Initialized ===");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.debug("=== IntelHome Context Destroyed ===");
	}

}
