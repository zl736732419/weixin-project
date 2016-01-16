package com.zheng.weixin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zheng.weixin.domain.User;
import com.zheng.weixin.web.BaseController;

public class AccessFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		if(uri.indexOf("login") != -1 
				|| uri.indexOf("/wget") != -1) {
			chain.doFilter(request, response);
			return;
		}
		
		User user = (User) req.getSession().getAttribute(BaseController.LOGIN_USER);
		if(user == null) {
			resp.sendRedirect(req.getContextPath() + "/user/login");
			return;
		}else {
			chain.doFilter(request, response);
			return;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

}
