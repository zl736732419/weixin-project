package com.zheng.weixin.web;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zheng.weixin.ctx.WeixinContext;
import com.zheng.weixin.service.IWeixinService;

@Controller
public class WeixinController extends BaseController {

	@Autowired
	private IWeixinService service;

	@RequestMapping(value = "/wget", method = RequestMethod.GET)
	public void validate() {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echoStr = request.getParameter("echostr");

		// 校验微信接口
		boolean result = service.validate(WeixinContext.getInstance()
				.getToken(), timestamp, nonce, signature);
		if (result) { // 验证成功，返回echostr
			try {
				response.getWriter().println(echoStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/wget", method=RequestMethod.POST)
	public void receiveMsg() throws Exception {
		String resp = service.handleMsg(request);
		System.out.println("==============返回响应: " + resp);
		if(!StringUtils.isBlank(resp)) {
			response.setHeader("Content-Type", "text/xml;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(resp);
		}
	}

}
