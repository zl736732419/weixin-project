package com.zheng.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.zheng.weixin.ctx.WeixinConstant;
import com.zheng.weixin.ctx.WeixinContext;
import com.zheng.weixin.json.WeixinUser;
import com.zheng.weixin.kit.JsonKit;
import com.zheng.weixin.kit.WeixinMessageKit;
import com.zheng.weixin.kit.WeixinReqKit;
import com.zheng.weixin.service.IWeixinUserService;

@Service
public class WeixinUserServiceImpl implements IWeixinUserService {

	@Override
	public WeixinUser loadWeixinUserByOpenId(String openId) {
		//获取微信端当前openid对应的用户信息
		String url = WeixinMessageKit.replaceUrlAccessToken(WeixinConstant.QUERY_USER)
				.replaceAll("OPENID", openId);
		String content = WeixinReqKit.reqGet(url);
		WeixinUser wUser = JsonKit.json2Obj(content, WeixinUser.class);
		
		return wUser;
	}

	@Override
	public String loadOpenIdByAuthCode(String code) {
		//根据code获取授权网页Access_Token
		String url = WeixinConstant.AUTH_QUERY_ACCESS_TOKEN_BY_CODE.replaceAll("APPID", WeixinContext.getInstance().getAppId())
			.replaceAll("SECRET", WeixinContext.getInstance().getAppSecret())
			.replaceAll("CODE", code);
		String content = WeixinReqKit.reqGet(url);
		String openId = (String)JsonKit.json2Map(content).get("openid");
		return openId;
	}

}
