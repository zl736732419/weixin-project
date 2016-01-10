package com.zheng.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zheng.weixin.dao.IBaseDao;
import com.zheng.weixin.dao.IUserDao;
import com.zheng.weixin.domain.User;
import com.zheng.weixin.service.IUserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Override
	protected IBaseDao<User> getBaseDao() {
		return userDao;
	}
	
	@Override
	public User loadByOpenId(String openId) {
		return userDao.loadByOpenId(openId);
	}

	@Override
	public User loadByUserName(String username) {
		return userDao.loadByUserName(username);
	}

	@Override
	public void bindNewUser(User user, String username, String password) {
		user.setBind(1);
		user.setUsername(user.getUsername());
		user.setPassword(user.getPassword());
		
		userDao.update(user);
	}

	@Override
	public void bindExistUser(User dbUser, User sessionUser) {
		dbUser.setBind(1);
		dbUser.setHeadImgUrl(sessionUser.getHeadImgUrl());
		dbUser.setNickname(sessionUser.getNickname());
		dbUser.setOpenId(sessionUser.getOpenId());
		dbUser.setSex(sessionUser.getSex());
		dbUser.setStatus(sessionUser.getStatus());
		
		userDao.update(dbUser);
		userDao.delete(sessionUser.getId());
	}
}
