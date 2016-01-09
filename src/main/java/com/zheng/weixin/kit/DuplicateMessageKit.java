package com.zheng.weixin.kit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zheng.weixin.domain.DuplicateMessage;

/**
 * 排重工具类
 * 用于保存消息时间、判断消息是否重复、清空消息
 *
 * @author zhenglian
 * @data 2016年1月9日 下午6:06:21
 */
public class DuplicateMessageKit {
	private static Logger logger = LoggerFactory.getLogger(DuplicateMessageKit.class);
	public static final List<DuplicateMessage> MSGS = new ArrayList<>();
	
	/**
	 * 判断当前接受到的消息是否重复
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午6:16:04
	 * @param map
	 * @return
	 */
	public static boolean checkDuplicate(Map<String, Object> map) {
		DuplicateMessage msg = initDuplicateMessage(map);
		if(MSGS.contains(msg)) {
			return true;
		}else {
			MSGS.add(msg);
			return false;
		}
	}
	
	/**
	 * 获取消息的反馈信息
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午6:31:11
	 * @param map
	 * @return
	 */
	public static String getRespMsg(Map<String, Object> map) {
		DuplicateMessage msg = initDuplicateMessage(map);
		DuplicateMessage m = MSGS.get(MSGS.indexOf(msg));
		return m.getRespMsg();
	}
	
	
	/**
	 * 清空缓存的消息
	 * 注意这是由定时器在另一个线程中执行的，所以可能会存在添加的同时删除，所以用for i 的形式循环遍历
	 * 还有一种情况，可能在消息刚加入到队列中，定时器启动，这时要求被删除的消息必须在加入到列表中20秒之后方可删除
	 * @author zhenglian
	 * @data 2016年1月9日 下午6:16:26
	 */
	public static void clear() {
		DuplicateMessage msg = null;
		logger.info("清空消息之前：消息长度为：" + MSGS.size());
		for(int i = 0; i < MSGS.size(); i++) {
			msg = MSGS.get(i);
			if((System.currentTimeMillis() - msg.getCurTime()) / 1000 > 20) {
				MSGS.remove(msg);
			}
		}
		logger.info("清空消息之后，消息长度为：" + MSGS.size());
	}
	
	public static DuplicateMessage initDuplicateMessage(Map<String, Object> map) {
		String fromUserName = (String)map.get("FromUserName");
		String createTime = (String)map.get("CreateTime");
		DuplicateMessage msg = new DuplicateMessage(fromUserName, createTime);
		
		return msg;
	}

	/**
	 * 设置某消息的消息反馈信息
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午6:34:52
	 * @param msgMap
	 * @param respMsg
	 */
	public static void setRespMsg(Map<String, Object> msgMap, String respMsg) {
		DuplicateMessage msg = initDuplicateMessage(msgMap);
		DuplicateMessage m = MSGS.get(MSGS.indexOf(msg));
		m.setRespMsg(respMsg);
	}
	
}
