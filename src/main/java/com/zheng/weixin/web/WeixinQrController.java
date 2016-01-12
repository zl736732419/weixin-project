package com.zheng.weixin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zheng.weixin.domain.WeixinQr;
import com.zheng.weixin.service.IWeixinQrService;

@Controller
@RequestMapping("/qr")
public class WeixinQrController extends BaseController {

	@Autowired
	private IWeixinQrService qrService;
	
	@RequestMapping(value="/foreverList", method=RequestMethod.GET)
	public String listForverQr() {
		List<WeixinQr> list = qrService.loadForeverQr();
		putRequestContext("list", list);
		return "qr/qrList";
	}
	
	@RequestMapping(value="/tempList", method=RequestMethod.GET)
	public String listTempQr() {
		List<WeixinQr> list = qrService.loadTempQr();
		putRequestContext("list", list);
		return "qr/qrList";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addPage() {
		return "qr/qrAdd";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(WeixinQr qr) {
		//场景值必须存在
		if(qr.getSnum() == null || qr.getSnum() <= 0) {
			throw new RuntimeException("失败,二维码场景值为空!");
		}
		qrService.save(qr);
		return "redirect:/qr/foreverList";
	}
	
	
	
}
