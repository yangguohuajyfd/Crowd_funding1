/**
 * 
 */
package com.zht.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Role;
import com.zht.atcrowdfunding.dao.RoleDao;
import com.zht.atcrowdfunding.service.RoleService;

/**
 * @author : ZHT
 * @date : 2018年1月26日
 */

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> queryPageRoles(Map<String, Object> paramMap) {
		return roleDao.queryPageRoles(paramMap);
	}

	@Override
	public Integer queryRoleCount(Map<String, Object> paramMap) {
		return roleDao.queryRoleCount(paramMap);
	}

	@Override
	public void insertRole(Role role) {
		roleDao.insertRole(role);
		
	}

	@Override
	public Role queryById(Integer id) {
		return roleDao.queryById(id);
	}

	@Override
	public int updateRole(Role role) {
		return roleDao.updateRole(role);
	}

	@Override
	public int deleteRoleById(Integer id) {
		return roleDao.deleteRoleById(id);
	}

	@Override
	public void deleteRoles(Datas ds) {
		roleDao.deleteRoles(ds);
		
	}

	@Override
	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	@Override
	public void insertRolePermissions(Map<String, Object> paramMap) {
		roleDao.deleteRolePermissions(paramMap);
		roleDao.insertRolePermissions(paramMap);
		
	}

}
