package com.zheng.weixin.service.impl;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zheng.weixin.dao.IWeixinMenuDao;
import com.zheng.weixin.domain.WeixinMenu;
import com.zheng.weixin.kit.DuplicateMessageKit;
import com.zheng.weixin.kit.WeixinBasicKit;
import com.zheng.weixin.kit.WeixinMessageCreateKit;
import com.zheng.weixin.kit.WeixinMessageKit;
import com.zheng.weixin.service.IWeixinService;

@Service
public class WeixinServiceImpl implements IWeixinService {

	@Autowired
	private IWeixinMenuDao menuDao;
	
	
	@Override
	public boolean validate(String token, String timestamp, String nonce, String signature) {
		String[] arr = new String[] {token, timestamp, nonce};
		//1.字典排序
		Arrays.sort(arr);
		//2.拼接成字符串
		String str = StringUtils.join(arr, "");
		//3.sha1加密
		String result = WeixinBasicKit.sha1(str);
		
		return signature.equals(result);
	}

	@Override
	public String handleMsg(HttpServletRequest request) {
		Map<String, Object> msgMap = WeixinMessageKit.req2Map(request);
		if(msgMap.isEmpty()) {
			return null;
		}
		
		//判断当前消息是否是重复的
		if(DuplicateMessageKit.checkDuplicate(msgMap)) {
			return DuplicateMessageKit.getRespMsg(msgMap);
		}
		System.out.println("==============收到微信发送的消息: " + msgMap);
		String event = ((String) msgMap.get("Event")).trim();
		Object eventKeyObj = msgMap.get("EventKey");
		if(eventKeyObj == null) { //地理位置消息就没有eventKey
			return null;
		}
		String eventKey = (String) eventKeyObj;
		//根据eventKey到数据库查询该菜单responseType
		WeixinMenu menu = menuDao.findByKey(eventKey);
		String respMsg = null;
		if("CLICK".equals(event)) {
			
			respMsg = WeixinMessageKit.handleClickEvent(menu, msgMap);
		}
		
		//在获取到响应消息之后加入到消息队列对应的消息中
		DuplicateMessageKit.setRespMsg(msgMap, respMsg);
		
		return respMsg;
	}

}
