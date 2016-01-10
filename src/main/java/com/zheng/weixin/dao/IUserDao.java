package com.zheng.weixin.dao;

import com.zheng.weixin.domain.User;

public interface IUserDao extends IBaseDao<User> {
	
	/**
	 * 根据openid查找用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 上午11:25:08
	 * @param openId
	 * @return
	 */
	public User loadByOpenId(String openId);

	/**
	 * 根据用户名查找用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 上午11:25:02
	 * @param username
	 * @return
	 */
	public User loadByUserName(String username);
}
