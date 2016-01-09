package com.zheng.weixin.quartz.task;

import org.springframework.stereotype.Component;

import com.zheng.weixin.ctx.WeixinConstant;
import com.zheng.weixin.ctx.WeixinContext;
import com.zheng.weixin.json.AccessToken;
import com.zheng.weixin.kit.JsonKit;
import com.zheng.weixin.kit.WeixinBasicKit;
import com.zheng.weixin.kit.WeixinReqKit;

/**
 * 定时刷新accessToken的任务
 * 微信每隔7200秒会重新生成accessToken，所以需要定时刷新accessToken值
 *
 * @author zhenglian
 * @data 2015年12月27日 下午6:12:11
 */
@Component
public class RefreshAccessTokenTask {
	public void refreshAccessToken() {
		String url = WeixinConstant.ACCESS_TOKEN_URL.replaceAll("APPID", WeixinContext.getInstance().getAppId())
				.replaceAll("APPSECRET", WeixinContext.getInstance().getAppSecret());
		String content = WeixinReqKit.reqGet(url);
		if(content != null) {
			if(WeixinBasicKit.isReqSuc(content)) {
				AccessToken accessToken = JsonKit.json2Obj(content, AccessToken.class);
				WeixinContext.getInstance().setAccessToken(accessToken);
			}else {
				refreshAccessToken();
			}
		}else { //没有获取到accessToken
			refreshAccessToken();
		}
	}
}
