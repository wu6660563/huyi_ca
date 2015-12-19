/**
 * 
 */
package com.biostime.customer.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.biostime.customer.service.TaskFactory;
import com.biostime.report.constant.SystemConstant;

/**
 * 
 * 初始化监听，用于启动 TimerTask和配置文件配置使用
 * 
 * @author wpl
 * @date May 24, 2014 11:57:22 AM
 * @version 1.0
 */
public class InitializeListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent contextEvent) {
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		// 初始化方法
		new TaskFactory();
		System.out.println("初始化完毕!");
		
		SystemConstant.sysPath = contextEvent.getServletContext().getRealPath("");
	}
	
	
	
}
