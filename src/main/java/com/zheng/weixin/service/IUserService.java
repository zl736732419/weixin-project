package com.zheng.weixin.service;

import com.zheng.weixin.domain.User;

public interface IUserService extends IBaseService<User>{
	/**
	 * 根据openid获取用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 上午10:58:08
	 * @param openId
	 * @return
	 */
	public User loadByOpenId(String openId);
	
	/**
	 * 根据用户名查找用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 上午11:24:27
	 * @param username
	 * @return
	 */
	public User loadByUserName(String username);
	
	/**
	 * 绑定新用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 下午9:49:36
	 * @param user
	 * @param username
	 * @param password
	 */
	public void bindNewUser(User user, String username, String password);
	
	/**
	 * 绑定已经存在的用户
	 *
	 * 1.需要将session中用户信息更新到要绑定的用户上， 
	 * 2.将session中保存的用户替换为要绑定的用户
	 * 3.删除session用户在数据库中的记录
	 * @author zhenglian
	 * @data 2016年1月10日 下午9:49:56
	 * @param dbUser 要绑定的用户
	 * @param sessionUser session中保存的当前关注的用户
	 */
	public void bindExistUser(User dbUser, User sessionUser);
	
}
