package com.zht.atcrowdfunding.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.service.PermissionService;

/**
 * 授权拦截器
 * @author : ZHT
 * @date : 2018年1月30日
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 1、获取用户发送的请求路径
	 * 2、判断当前路径是否需要进行授权验证（验证路径是否存在许可表中）
	 * 3、如果不需要验证，请求继续执行
	 * 4、如果需要验证，判断当前用户是否对请求路径进行授权
	 * 5、如果已经授权，请求继续执行
	 * 6、如果没有授权，跳转到错误页面，提示错误信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		
		List<Permission> permissions = permissionService.queryAll();
		Set<String> authUrlSet = new HashSet<>();
		for (Permission permission : permissions) {
			if(!StringUtils.isEmpty(permission.getUrl())) {
				authUrlSet.add(permission.getUrl());
			}
		}
		
		if(authUrlSet.contains(uri)) {
			//需要验证
			HttpSession session = request.getSession();
			Set<String> userAuthUrlSet = (Set<String>)session.getAttribute("userAuthUrlSet");
			if(userAuthUrlSet.contains(uri)) {
				//已授权
				return true;
			}else {
				//未授权，去错误页面
				response.sendRedirect("/error");
				return false;
			}
		}else {
			//不需要验证
			return true;
		}
	}

	
}
