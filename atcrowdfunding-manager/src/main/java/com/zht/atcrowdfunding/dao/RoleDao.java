/**
 * 
 */
package com.zht.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Role;

/**
 * @author : ZHT
 * @date : 2018年1月26日
 */
public interface RoleDao {

	/**
	 * @param paramMap
	 * @return
	 */
	List<Role> queryPageRoles(Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 * @return
	 */
	Integer queryRoleCount(Map<String, Object> paramMap);

	/**
	 * @param role
	 */
	void insertRole(Role role);

	/**
	 * @param id
	 * @return
	 */
	@Select("select * from t_role where id = #{id}") 
	Role queryById(Integer id);

	/**
	 * @param role
	 * @return
	 */
	int updateRole(Role role);

	/**
	 * @return
	 */
	int deleteRoleById(Integer id);

	/**
	 * @param ds
	 */
	void deleteRoles(Datas ds);

	/**
	 * @return
	 */
	@Select("select * from t_role")
	List<Role> queryAll();

	/**
	 * @param paramMap
	 */
	void insertRolePermissions(Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 */
	void deleteRolePermissions(Map<String, Object> paramMap);

}
