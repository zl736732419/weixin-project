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

import org.apache.commons.lang3.StringUtils;

import com.zheng.weixin.domain.User;
import com.zheng.weixin.kit.WeixinMessageKit;
import com.zheng.weixin.listener.SpringManager;
import com.zheng.weixin.service.IUserService;
import com.zheng.weixin.service.IWeixinUserService;
import com.zheng.weixin.web.BaseController;

/**
 * 微信授权
 * 流程：
 * 	通过微信访问业务网站时，通过重定向获取授权网页code的链接来获取code,
 *  微信端收到该请求时会重定向到上面传递的redirect_url，同时附带code和上面url中指定的state参数
 *  用户获取到code/state参数再次进行判断，验证获取code是否成功，如果成功，用户通过code获取授权AccessToken
 *  在这里如果是通过snsapi_base方式进行授权，就可以直接获取到openid
 *  得到openid查找数据库中的用户是否存在且被关注，如果不存在就创建用户添加到数据库同时进行关注
 *  最后将用户添加到session中完成微信授权操作
 *
 * @author zhenglian
 * @data 2016年1月10日 下午5:16:57
 */
public class WeixinAuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		IWeixinUserService weixinUserService = (IWeixinUserService) SpringManager.getBean("weixinUserServiceImpl", IWeixinUserService.class);
		// 需要判断用户请求来自微信
		String header = req.getHeader("User-Agent").toLowerCase();
		if (header.indexOf("micromessenger") >= 0) { // 说明是微信登录需要进行授权
			String code = req.getParameter("code");
			String state = req.getParameter("state");
			if (code != null && state != null && "1".equals(state)) {
				//用户通过发送授权请求成功后，微信端重定向到指定redirectUrl返回的响应(微信端响应第一次认证请求)
				String openId = weixinUserService.loadOpenIdByAuthCode(code);
				//根据用户openid到数据库中查找用户，如果没有就需要添加
				if(!StringUtils.isBlank(openId)) {
					IUserService userService = (IUserService) SpringManager.getBean("userServiceImpl", IUserService.class);
					User user = userService.loadByOpenId(openId);
					if(user == null) {
						user = weixinUserService.loadWeixinUserByOpenId(openId).getUser();
						userService.save(user);
					}else {
						if(user.getStatus() == 0) {
							user.setStatus(1);
							userService.update(user);
						}
					}
					
					//将user添加到session中
					req.getSession().setAttribute(BaseController.LOGIN_USER, user);
				}
			}else { //微信第一次访问指定的连接，需要重定向到授权路径提交给微信处理
				String queryString = req.getQueryString();
				String url = req.getRequestURL().toString()
						+ (StringUtils.isBlank(queryString) ? "" : "?"
								+ queryString); // 获取用户访问的链接地址
				WeixinMessageKit.sendWeixinAuth(url, resp);
				return;
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
