/**
 * 
 */
package com.zht.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Role;

/**
 * @author : ZHT
 * @date : 2018年1月26日
 */
public interface RoleService {

	public List<Role> queryPageRoles(Map<String, Object> paramMap);
	
	public Integer queryRoleCount(Map<String, Object> paramMap);

	/**
	 * @param role
	 */
	public void insertRole(Role role);

	/**
	 * @param id
	 * @return
	 */
	public Role queryById(Integer id);

	/**
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);

	/**
	 * @param id
	 * @return
	 */
	public int deleteRoleById(Integer id);

	/**
	 * @param ds
	 */
	public void deleteRoles(Datas ds);

	/**
	 * @return
	 */
	public List<Role> queryAll();

	/**
	 * @param paramMap
	 */
	public void insertRolePermissions(Map<String, Object> paramMap);
	
}
