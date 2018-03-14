package com.zht.atcrowdfunding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zht.atcrowdfunding.beans.User;

/**
 * 登录拦截器
 * @author : ZHT
 * @date : 2018年1月30日
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 1、获取用户的登录状态
	 * 2、如果用户已经登录，那么继续执行
	 * 3、如果用户没有登录，那么不能继续执行，跳转回到登录页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//URI : /xxx/xxx
		//URL : http://127.0.0.1:8080/xxx/xxx
		String uri = request.getRequestURI();
		System.out.println("uri = " + uri);
		
		//获取会话范围中保存的用户信息
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(user == null) {
			response.sendRedirect("/login");
			return false;
		}else {
			return true;
		}
	}

	
}
