package com.zht.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 服务器启动监听器
 * @author Administrator
 *
 */
public class ServerStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//获取web应用对象
		ServletContext application = sce.getServletContext();
		//获取web应用路径
		String path = application.getContextPath();
		//将path放到application域中
		application.setAttribute("APP_PATH", path);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
