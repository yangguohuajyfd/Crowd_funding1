package com.zht.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.atcrowdfunding.beans.Datas;
import com.zht.atcrowdfunding.beans.Permission;
import com.zht.atcrowdfunding.beans.User;
import com.zht.atcrowdfunding.dao.UserDao;
import com.zht.atcrowdfunding.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    
    public User queryById(Integer id) {
    	System.out.println("Service层");
        return userDao.queryById(id);
    }

	@Override
	public User queryUser4Login(User user) {
		return userDao.queryUser4Login(user);
	}

	@Override
	public List<User> queryPageUsers(Map<String, Object> map) {
		return userDao.queryPageUsers(map);
	}

	@Override
	public Integer queryUserCount(Map<String, Object> map) {
		return userDao.queryUserCount(map);
	}

	@Override
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int deleteUserById(Integer id) {
		return userDao.deleteUserById(id);
	}

	@Override
	public void deleteUsers(Datas ds) {
		userDao.deleteUsers(ds);
		
	}

	@Override
	public void batchInsertUsers(Datas ds) {
		userDao.batchInsertUsers(ds);
		
	}

	@Override
	public List<Integer> queryRoleidsByUserId(Integer id) {
		return userDao.queryRoleidsByUserId(id);
	}

	@Override
	public void insertUserRoles(Map<String, Object> paramMap) {
		userDao.insertUserRoles(paramMap);
		
	}

	@Override
	public void deleteUserRoles(Map<String, Object> paramMap) {
		userDao.deleteUserRoles(paramMap);
	}

	@Override
	public List<Permission> queryPermissionsByUser(User dbUser) {
		return userDao.queryPermissionsByUser(dbUser);
	}

}
