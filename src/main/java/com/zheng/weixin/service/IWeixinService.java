package com.zheng.weixin.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信业务接口
 *
 * @author zhenglian
 * @data 2015年12月27日 下午4:31:43
 */
public interface IWeixinService {

	/**
	 * 校验请求是否来自微信
	 * 加密/校验流程如下：
	 *	1. 将token、timestamp、nonce三个参数进行字典序排序
	 *	2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 *	3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * @author zhenglian
	 * @data 2015年12月27日 下午4:32:39
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	public boolean validate(String token, String timestamp, String nonce, String signature);

	/**
	 * 处理微信服务器发送过来的消息
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午3:54:55
	 * @param request
	 * @return 发送给微信服务器的响应消息
	 */
	public String handleMsg(HttpServletRequest request);
	
}
