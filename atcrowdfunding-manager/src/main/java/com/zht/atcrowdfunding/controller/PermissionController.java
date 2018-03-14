/**
 * 
 */
package com.zht.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.common.BaseController;
import com.zht.atcrowdfunding.service.PermissionService;

/**
 * @author : ZHT
 * @date : 2018年1月29日
 */

@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

	@Autowired
	private PermissionService permissionService;
	//许可主页面
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}
	//许可添加页面
	@RequestMapping("/add")
	public String add() {
		return "permission/add";
	}
	//许可编辑页面
	@RequestMapping("/edit")
	public String edit(Integer id, Model model) {
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		return "permission/edit";
	}
	//更新许可
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Permission permission) {
		start();
		
		try {
			//更新许可
			int count = permissionService.updatePermission(permission);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	//删除许可
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Permission permission) {
		start();
		
		try {
			int count = permissionService.deletePermission(permission);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	//插入许可
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Permission permission) {
		start();
		
		try {
			permissionService.insertPermission(permission);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
		return end();
	}
	//加载异步数据
	//异步查询时，ztree不需要准备数据，初始化后，由组件自动读取
	@ResponseBody
	@RequestMapping("/loadAsyncData")
	public Object loadAsyncData() {
			
		List<Permission> permissions = new ArrayList<>();
		List<Permission> dbPermissions = permissionService.queryAll();
		Map<Integer, Permission> permissionMap = new HashMap<>();
		for (Permission permission : dbPermissions) {
			permissionMap.put(permission.getId(), permission);
		}
		
		for (Permission permission : dbPermissions) {
			//子菜单
			Permission child = permission;
			if(child.getPid() == 0) {
				permissions.add(permission);
			}else {
				//父菜单
				Permission parent = permissionMap.get(child.getPid());
				//组合父子菜单的关系
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	//异步加载分配许可数据
	@ResponseBody
	@RequestMapping("/loadAssignAsyncData")
	public Object loadAssignAsyncData(Integer roleid) {
		
		List<Permission> permissions = new ArrayList<>();
		List<Permission> dbPermissions = permissionService.queryAll();
		//查询当前角色已经分配过的许可数据
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		
		Map<Integer, Permission> permissionMap = new HashMap<>();
		for (Permission permission : dbPermissions) {
			if(permissionids.contains(permission.getId())) {
				permission.setChecked(true);
			}
			permissionMap.put(permission.getId(), permission);
		}
		
		for (Permission permission : dbPermissions) {
			//子菜单
			Permission child = permission;
			if(child.getPid() == 0) {
				permissions.add(permission);
			}else {
				//父菜单
				Permission parent = permissionMap.get(child.getPid());
				//组合父子菜单的关系
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	//通过ajax加载许可数据
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData() {
		start();
		
		try {
			
			// List ==> []
			// Map  ==> {}
			// User ==> {}
			//根节点，id为0
			List<Permission> permissions = new ArrayList<>();
			/* 临时数据
			Permission p1 = new Permission();
			p1.setName("Test1");
			Permission p2 = new Permission();
			p2.setName("Test2");
			Permission p3 = new Permission();
			p3.setName("Test3");
			
			p1.getChildren().add(p2);
			p1.getChildren().add(p3);
			
			permissions.add(p1);
			*/
			//***********************************
			/*方式一
			// 父菜单
			Permission root = permissionService.queryRootPermission();
			System.out.println("root="+root);
			permissions.add(root);
			
			//查询子菜单
			List<Permission> childPermissions = permissionService.queryChildPermissions(root.getId());
			
			for(Permission childPermission : childPermissions) {
				//查询子菜单的子菜单
				List<Permission> childChildPermissions = permissionService.queryChildPermissions(childPermission.getId());
				childPermission.setChildren(childChildPermissions);
			}
			
			//整合父子菜单关系
			root.setChildren(childPermissions);
			*/
			//**************************************
			/*方式二
			//递归查询菜单数据
			Permission temp = new Permission();
			temp.setId(0);
			
			queryChildPermisson(temp);
			*/
			//***************************************
			//查询一次数据库，准备好树形结构的数据
			//使用for循环的嵌套查询数据
//			List<Permission> dbPermissions = permissionService.queryAll();
			
			/*方式三
			for (Permission permission : dbPermissions) {
				//子菜单
				Permission child = permission;
				if(child.getPid() == 0) {
					permissions.add(permission);
				}else {
					for (Permission innerPermission : dbPermissions) {
						if(innerPermission.getId() == child.getPid()) {
							//父菜单
							Permission parent = innerPermission;
							//组合父子菜单的关系
							parent.getChildren().add(child);
							break;
						}
					}
				}
			}
			*/
			//********************************
			//方式四 ：使用Map集合整合菜单之间的关系
			//查询所有许可数据
			List<Permission> dbPermissions = permissionService.queryAll();
			Map<Integer, Permission> permissionMap = new HashMap<>();
			//循环遍历每一个许可数据
			for (Permission permission : dbPermissions) {
				//把每一个许可数据放到map集合中，id作为key，许可对象作为value
				permissionMap.put(permission.getId(), permission);
			}
			//在此循环遍历每一个许可数据
			for (Permission permission : dbPermissions) {
				//子菜单，先暂时把每个节点当成子节点
				Permission child = permission;
				//如果子节点的pid为0，代表它的上一级是根节点
				if(child.getPid() == 0) {
					//把当前节点放到根节点后，该节点id为1
					permissions.add(permission);
				}else {
					//父菜单，通过子节点的pid获取父节点的id，再通过父节点的id获取父节点，通过子节点查找父节点
					Permission parent = permissionMap.get(child.getPid());
					//组合父子菜单的关系
					parent.getChildren().add(child);
				}
			}
			
			data(permissions);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	/**
	 * 递归的要素：
	 * 1、逻辑相同，方法自己调用自己
	 * 2、方法调用时，数据之间应该有规律
	 * 3、递归的方法一定要有跳出的逻辑
	 * @param parent
	 */
	private void queryChildPermisson(Permission parent) {
		//查询子菜单
		List<Permission> childPermissions = 
				permissionService.queryChildPermissions(parent.getId());
		
		for (Permission childPermission : childPermissions) {
			queryChildPermisson(childPermission);
		}
		
		//组合父子菜单的关系
		parent.setChildren(childPermissions);
	}
	
	
}
