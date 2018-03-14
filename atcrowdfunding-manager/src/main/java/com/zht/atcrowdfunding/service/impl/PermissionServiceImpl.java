/**
 * 
 */
package com.zht.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.dao.PermissionDao;
import com.zht.atcrowdfunding.service.PermissionService;

/**
 * @author : ZHT
 * @date : 2018年1月29日
 */

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Permission queryRootPermission() {
		return permissionDao.queryRootPermission();
	}

	@Override
	public List<Permission> queryChildPermissions(Integer id) {
		return permissionDao.queryChildPermissions(id);
	}

	@Override
	public List<Permission> queryAll() {
		return permissionDao.queryAll();
	}

	@Override
	public void insertPermission(Permission permission) {
		permissionDao.insertPermission(permission);
		
	}

	@Override
	public Permission queryById(Integer id) {
		return permissionDao.queryById(id);
	}

	@Override
	public int updatePermission(Permission permission) {
		return permissionDao.updatePermission(permission);
	}

	@Override
	public int deletePermission(Permission permission) {
		return permissionDao.deletePermission(permission);
	}

	@Override
	public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
		return permissionDao.queryPermissionidsByRoleid(roleid);
	}

}
