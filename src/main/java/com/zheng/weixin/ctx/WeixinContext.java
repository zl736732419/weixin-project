package com.zheng.weixin.ctx;

import com.zheng.weixin.json.AccessToken;

/**
 * 微信上下文，存放微信常用的变量
 *
 * @author zhenglian
 * @data 2016年1月3日 下午10:16:48
 */
public class WeixinContext {
	private String appId;
	private String appSecret;
	private String token;
	private String baseUrl;
	private AccessToken accessToken;

	private static WeixinContext instance;

	private WeixinContext() {

	}

	public static WeixinContext getInstance() {
		if (instance == null) {
			instance = new WeixinContext();
		}

		return instance;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
