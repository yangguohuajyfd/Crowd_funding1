package com.zht.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.beans.User;

public interface UserDao {
	
	//通过id查询用户信息
	@Select("select * from t_user where id = #{id}")
	public User queryById(Integer id);

	//通过用户名和密码查询用户信息
	@Select("select * from t_user where loginacct = #{loginacct} and userpswd = #{userpswd}")
	public User queryUser4Login(User user);

	//分页查询用户信息
	public List<User> queryPageUsers(Map<String, Object> map);

	//查询数据库中总记录数
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
	@Select("select roleid from t_user_role where userid = #{userid}")
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
