package com.zht.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.beans.User;

public interface UserService {

	public User queryById(Integer id);

	public User queryUser4Login(User user);

	public List<User> queryPageUsers(Map<String, Object> map);

	public Integer queryUserCount(Map<String, Object> map);

	public void insertUser(User user);

	public int updateUser(User user);

	/**
	 * @param id
	 * @return
	 */
	public int deleteUserById(Integer id);

	/**
	 * @param ds
	 */
	public void deleteUsers(Datas ds);

	/**
	 * @param ds
	 */
	public void batchInsertUsers(Datas ds);

	/**
	 * @param id
	 * @return
	 */
	public List<Integer> queryRoleidsByUserId(Integer id);

	/**
	 * @param paramMap
	 */
	public void insertUserRoles(Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 */
	public void deleteUserRoles(Map<String, Object> paramMap);

	/**
	 * @param dbUser
	 * @return
	 */
	public List<Permission> queryPermissionsByUser(User dbUser);

}
