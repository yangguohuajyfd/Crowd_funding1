package com.zht.atcrowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zht.atcrowdfunding.beans.AjaxResult;
import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Page;
import com.zht.atcrowdfunding.beans.Role;
import com.zht.atcrowdfunding.beans.User;
import com.zht.atcrowdfunding.common.BaseController;
import com.zht.atcrowdfunding.service.RoleService;
import com.zht.atcrowdfunding.service.UserService;
import com.zht.atcrowdfunding.utils.ConfigUtil;
import com.zht.atcrowdfunding.utils.MD5Util;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	//后台主页面
	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}
	
	//用户角色关系页面
	@RequestMapping("/assign")
	public String assign(Integer id, Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		
		//查询所有的角色数据
		List<Role> roles = roleService.queryAll();
		//查询当前用户已经分配的角色数据
		List<Integer> roleids = userService.queryRoleidsByUserId(id);
		
		List<Role> assignList = new ArrayList<>();
		List<Role> unassignList = new ArrayList<>();
		
		for(Role role : roles) {
			if(roleids.contains(role.getId())) {
				assignList.add(role);//已分配
			}else {
				unassignList.add(role);//未分配
			}
		}
		
		model.addAttribute("assignList", assignList);
		model.addAttribute("unassignList", unassignList);
		return "user/assign";
	}
	
	//添加用户角色
	@ResponseBody
	@RequestMapping("/assignRole")
	public Object assignRole(Integer userid, Datas ds) {
		start();
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userid", userid);
			paramMap.put("roleids", ds.getIds());
//			System.out.println(userid);
//			System.out.println(ds.getIds());
			userService.insertUserRoles(paramMap);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	//取消用户角色
	@ResponseBody
	@RequestMapping("/unassignRole")
	public Object unassignRole(Integer userid, Datas ds) {
		start();
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userid", userid);
			paramMap.put("roleids", ds.getIds());
			userService.deleteUserRoles(paramMap);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	//添加
	@RequestMapping("/add")
	public String add() {
		return "user/add";
	}
	//批量添加
	@RequestMapping("/addBatch")
	public String addBatch() {
		return "user/batch";
	}
	//编辑
	@RequestMapping("/edit")
	public String edit(Integer id, Model model) {
		System.out.println(id);
		User user = userService.queryById(id);
		System.out.println(user);
		model.addAttribute("user", user);
		return "user/edit";
	}
	//批量删除
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Datas ds) {
		AjaxResult result = new AjaxResult();
		
		try {
			//删除
			userService.deleteUsers(ds);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//删除
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		start();
		
		try {
			int count = userService.deleteUserById(id);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	//更新
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user) {
		start();
		
		try {
			//修改用户信息
			int count = userService.updateUser(user);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	/**
	 * SpringMVC不支持引用数组类型数据接收数据
	 * SpringMVC默认不支持多数据的封装操作
	 * 如果想要让SpringMVC可以支持封装多数据对象，需要特殊操作
	 * 1.增加封装对象的包装类(不支持泛型)，包装类中增加集合属性，并提供set/get方法
	 * 2.修改表单数据提交的方式
	 * 	users[0].id=1&users[1].id=2
	 * 3.在方法的参数中增加包装类即可
	 * 4.封装数据时，可能会有一些无用数据，需要删除
	 */
	@ResponseBody
	@RequestMapping("/batchInsert")
	public Object batchInsert(Datas ds) {
//		public String batchInsert(HttpServletRequest req) {
//		String[] names = req.getParameterValues("username");
//		String name = req.getParameter("username");
//		Map<String, String[]> map = req.getParameterMap();
		
//		for(User user : ds.getUsers()) {
//			if(user.getLoginacct() == null) {
//				ds.getUsers().remove(user);
//			}
//		}
		start();
		//迭代器，集合遍历
		Iterator<User> userIter = ds.getUsers().iterator();
		try {
			while(userIter.hasNext()) {
				User user = userIter.next();
				if(user.getLoginacct() == null) {
					userIter.remove();
				}
				user.setUserpswd(MD5Util.digest(ConfigUtil.getValueByKey("DEFAULT_PASSWORD")));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = sdf.format(new Date());
				user.setCreatetime(time);
			}
			userService.batchInsertUsers(ds);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	//插入
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user) {
		AjaxResult result = new AjaxResult();
		
		try {
			//保存用户信息
			user.setUserpswd(MD5Util.digest(ConfigUtil.getValueByKey("DEFAULT_PASSWORD")));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			user.setCreatetime(time);
			userService.insertUser(user);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//分页查询
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryContent, Integer pageno, Integer pagesize) {
		AjaxResult result = new AjaxResult();
		try {
			
			//分页查询用户信息
			int start = (pageno - 1) * pagesize;
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("start", start);
			paramMap.put("pagesize", pagesize);
			if(!StringUtils.isEmpty(queryContent)) {
				if(queryContent.indexOf("\\") != -1) {
					queryContent = queryContent.replaceAll("\\\\", "\\\\\\\\");
				}
				if(queryContent.indexOf("%") != -1) {
					queryContent = queryContent.replaceAll("%", "\\\\%");
				}
				if(queryContent.indexOf("_") != -1) {
					queryContent = queryContent.replaceAll("_", "\\\\_");
				}
			}
			paramMap.put("queryContent", queryContent);
			List<User> users = userService.queryPageUsers(paramMap);
			int totalPagesize = userService.queryUserCount(paramMap);

			Page<User> userPage = new Page<>();
			userPage.setPageno(pageno);
			userPage.setPagesize(pagesize);
			userPage.setTotalPagesize(totalPagesize);
			userPage.setDatas(users);
			
			result.setData(userPage);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * // 1) 点击菜单，跳转页面 // 2) 调用服务，查询数据 // 3) Service调用DAO，查询数据（limit start, size） //
	 * 4) 返回查询结果 DAO==>Service==>Controller // 5) 保存查询结果（request,） // 6) 跳转页面
	 * 
	 * @param pageno
	 * @param pagesize
	 * @param model
	 * @return
	 */
	// //用户维护
	@RequestMapping("/index1")
	public String index1(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
			@RequestParam(value = "pagesize", required = false, defaultValue = "2") Integer pagesize, Model model) {
		// 每页的起始记录
		int start = (pageno - 1) * pagesize;
		// 创建map，把分页查询条件放进去
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("pagesize", pagesize);
		// 查询每页显示的所有用户信息
		List<User> users = userService.queryPageUsers(paramMap);
		// 获取总记录数
		Integer totalPagesize = userService.queryUserCount(paramMap);

		Page<User> userPage = new Page<>();
		// 把page需要的信息放到page中
		userPage.setPageno(pageno);
		userPage.setPagesize(pagesize);
		userPage.setTotalPagesize(totalPagesize);
		userPage.setDatas(users);
		// 把查询到的用户信息放到request域中
		// model.addAttribute("users", users);
		// 把page对象放到request域中
		model.addAttribute("userPage", userPage);
		return "user/index";
	}

}
