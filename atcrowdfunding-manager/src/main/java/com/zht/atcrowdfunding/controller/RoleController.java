package com.zht.atcrowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zht.atcrowdfunding.beans.AjaxResult;
import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Page;
import com.zht.atcrowdfunding.beans.Role;
import com.zht.atcrowdfunding.common.BaseController;
import com.zht.atcrowdfunding.service.RoleService;

/**
 * @author : ZHT
 * @date : 2018年1月26日
 */

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String index() {
		return "/role/index";
	}
	
	@RequestMapping("/assign")
	public String assign(Integer id, Model model) {
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		return "/role/assign";
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer roleid, Datas ds) {
		start();
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("roleid", roleid);
			paramMap.put("permissionids", ds.getIds());
			roleService.insertRolePermissions(paramMap);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	@RequestMapping("/add")
	public String add() {
		return "/role/add";
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id, Model model) {
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		return "role/edit";
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		start();
		
		try {
			int count = roleService.deleteRoleById(id);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Datas ds) {
		start();
		
		try {
			roleService.deleteRoles(ds);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Role role) {
		start();
		
		try {
			int count = roleService.updateRole(role);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	//新增
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Role role) {
		start();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			role.setCreatetime(time);
			roleService.insertRole(role);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	//分页查询，模糊查询
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryContent, Integer pageno, Integer pagesize) {
		AjaxResult result = new AjaxResult();
		
		try {
			int start = (pageno-1)*pagesize;
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
			List<Role> roles = roleService.queryPageRoles(paramMap);
			Integer totalPagesize = roleService.queryRoleCount(paramMap);
			
			Page<Role> rolePage = new Page<>();
			rolePage.setPageno(pageno);
			rolePage.setPagesize(pagesize);
			rolePage.setTotalPagesize(totalPagesize);
			rolePage.setDatas(roles);
			
			result.setData(rolePage);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
}
