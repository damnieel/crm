package com.cvc.crm.util;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

/**
 * 
 * 日志工具类
 * 
 * @author dengrihui
 * @version 1.0, 2018/10/12
 *
 */
public class Log4j {

	// 设置日志输出文件
	public static void setFile(String file) {
		RollingFileAppender a2 = (RollingFileAppender) Logger.getRootLogger().getAppender("A2");
		a2.setFile(file);
		a2.activateOptions();
	}

}
