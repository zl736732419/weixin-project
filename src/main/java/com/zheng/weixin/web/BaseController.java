package com.zheng.weixin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	
	public static final String LOGIN_USER = "loginUser"; //登录用户的key
	
	@ModelAttribute
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@ModelAttribute
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	protected Object getSessionInfo(String key) {
		return request.getSession().getAttribute(key);
	}
	
	protected void putRequestContext(String key, Object value) {
		this.request.setAttribute(key, value);
	}

	protected void putSessionContext(String key, Object value) {
		this.request.getSession().setAttribute(key, value);
	}
}
