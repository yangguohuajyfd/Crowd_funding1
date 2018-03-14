/**
 * 
 */
package com.zht.atcrowdfunding.service;

import java.util.List;

import com.zht.atcrowdfunding.beans.Permission;

/**
 * @author : ZHT
 * @date : 2018年1月29日
 */
public interface PermissionService {

	/**
	 * @return
	 */
	Permission queryRootPermission();

	/**
	 * @param id
	 * @return
	 */
	List<Permission> queryChildPermissions(Integer id);

	/**
	 * @return
	 */
	List<Permission> queryAll();

	/**
	 * @param permission
	 */
	void insertPermission(Permission permission);

	/**
	 * @return
	 */
	Permission queryById(Integer id);

	/**
	 * @param permission
	 * @return
	 */
	int updatePermission(Permission permission);

	/**
	 * @param permission
	 * @return
	 */
	int deletePermission(Permission permission);

	/**
	 * @param roleid
	 * @return
	 */
	List<Integer> queryPermissionidsByRoleid(Integer roleid);

}
