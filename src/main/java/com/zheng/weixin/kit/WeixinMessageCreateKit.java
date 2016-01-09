package com.zheng.weixin.kit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建各种微信消息的工具类
 *
 * @author zhenglian
 * @data 2016年1月9日 下午4:06:15
 */
public class WeixinMessageCreateKit {
	/**
	 * 创建文本消息响应信息
	 *
	 * @author zhenglian
	 * @data 2016年1月2日 上午10:37:11
	 * @param reqMsg 从微信端发送过来的消息
	 * @param map
	 */
	public static Map<String, Object> createTextMsg(Map<String, Object> reqMsg, String content) {
		Map<String, Object> msg = new HashMap<>();
		msg.put("ToUserName", reqMsg.get("FromUserName"));
		msg.put("FromUserName", reqMsg.get("ToUserName"));
		msg.put("CreateTime", new Date().getTime() + "");
		msg.put("MsgType", "text");
		msg.put("Content", content);
		
		return msg;
	}
}
