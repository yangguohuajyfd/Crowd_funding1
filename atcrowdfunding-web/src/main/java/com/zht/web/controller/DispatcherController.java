package com.zht.web.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zht.atcrowdfunding.beans.AjaxResult;
import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.beans.User;
import com.zht.atcrowdfunding.service.UserService;
import com.zht.atcrowdfunding.utils.MD5Util;

@Controller
public class DispatcherController {

	@Autowired
	private UserService userService;

	//去主页面
	@RequestMapping(value = { "", "/", "/index", "/index/" })
	public String getIndex() {
		return "index";
	}

	//去登录页面
	@RequestMapping(value = "/login")
	public String getLogin() {
		return "login";
	}
	
	//注销，跳转到登录页面
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	//去后台主页面
	@RequestMapping(value = "/main")
	public String toMain() {
		return "main";
	}
	
	//去错误页面
	@RequestMapping(value = "/error")
	public String error() {
		return "error";
	}

	//校验用户信息
	@ResponseBody
	@RequestMapping("/checkLogin")
	public Object checkLogin(User user, HttpSession session) {
		AjaxResult result = new AjaxResult();
		// 对密码加密
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		// 通过表单中的用户名和密码到数据库中查找用户信息
		User dbUser = userService.queryUser4Login(user);
		// 判断从数据库中查询到的用户信息是否为空
		if (dbUser == null) {
			result.setSuccess(false);
		}else {
			result.setSuccess(true);
			//查询当前登录用户的权限菜单（许可）
			List<Permission> permissions = userService.queryPermissionsByUser(dbUser);
			//整合权限菜单
			Map<Integer, Permission> permissionMap = new HashMap<>();
			Set<String> userAuthUrlSet = new HashSet<>();
			for (Permission permission : permissions) {
				if(!StringUtils.isEmpty(permission.getUrl())) {
					userAuthUrlSet.add(permission.getUrl());
				}
				permissionMap.put(permission.getId(), permission);
			}
			Permission root = null;
			for (Permission permission : permissions) {
				Permission child = permission;
				if(child.getPid() == 0) {
					root = permission;
				}else {
					Permission parent = permissionMap.get(child.getPid());
					//组合父子菜单关系
					parent.getChildren().add(child);
				}
			}
			
			session.setAttribute("userAuthUrlSet", userAuthUrlSet);
			session.setAttribute("rootPermission", root);
			session.setAttribute("loginUser", dbUser);
		}
		return result;
	}
	//执行登录
	@RequestMapping("/doLogin")
	public String doLogin(User user, Model model) {
		//1.获取表单中传递的参数
		//2.调用服务接口，查询数据
		// 对密码加密
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		System.out.println(user.getUserpswd());
		// 通过表单中的用户名和密码到数据库中查找用户信息
		User user4Login = userService.queryUser4Login(user);
		// 判断从数据库中查询到的用户信息是否为空
		//3.根据返回值判断是否登录成功
		if (user4Login == null) {
			// 用户名或密码不匹配
			//4.1 如果登录不成功（用户不存在），跳转回到登录页面，并且提示错误信息
			String errorMsg = "用户名或密码不正确，请重新输入！";
			model.addAttribute("errorMsg", errorMsg);
			return "redirect:/login";
		} else {
			//4.2 如果登录成功（用户存在），跳转到后台的主页面
			return "index";
		}
	}
}
