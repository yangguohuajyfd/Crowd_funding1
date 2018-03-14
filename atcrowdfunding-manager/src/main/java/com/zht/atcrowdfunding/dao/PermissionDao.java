/**
 * 
 */
package com.zht.atcrowdfunding.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.zht.atcrowdfunding.beans.Permission;

/**
 * @author : ZHT
 * @date : 2018年1月29日
 */
public interface PermissionDao {

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
	@Select("select * from t_permission")
	List<Permission> queryAll();

	/**
	 * @param permission
	 */
	void insertPermission(Permission permission);

	/**
	 * @param id
	 * @return
	 */
	@Select("select * from t_permission where id = #{id}")
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
	@Delete("delete from t_permission where id = #{id}")
	int deletePermission(Permission permission);

	/**
	 * @param roleid
	 * @return
	 */
	@Select("select permissionid from t_role_permission where roleid = #{roleid}")
	List<Integer> queryPermissionidsByRoleid(Integer roleid);

}
