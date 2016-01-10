package com.zheng.weixin.service;

import com.zheng.weixin.json.WeixinUser;

public interface IWeixinUserService {

	/**
	 * 根据openid获取微信用户信息
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 下午6:14:37
	 * @param openId
	 * @return
	 */
	public WeixinUser loadWeixinUserByOpenId(String openId);
	
	/**
	 * 根据授权code获取微信用户openId
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 下午6:14:25
	 * @param code
	 * @return
	 */
	public String loadOpenIdByAuthCode(String code);
	
}
