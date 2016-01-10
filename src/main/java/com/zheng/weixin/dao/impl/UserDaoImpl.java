package com.zheng.weixin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zheng.weixin.dao.IUserDao;
import com.zheng.weixin.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public User loadByOpenId(String openId) {
		String hql = "from User u where u.openId=?";
		return findSingleByHql(hql, openId);
	}

	@Override
	public User loadByUserName(String username) {
		String hql = "from User u where u.username=?";
		return findSingleByHql(hql, username);
	}

}
