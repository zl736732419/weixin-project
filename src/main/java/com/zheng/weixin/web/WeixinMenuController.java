package com.zheng.weixin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zheng.weixin.domain.WeixinMenu;
import com.zheng.weixin.dto.WeixinMenuDto;
import com.zheng.weixin.service.IWeixinMenuService;

@Controller
@RequestMapping(value="/menu")
public class WeixinMenuController extends BaseController{
	
	@Autowired
	private IWeixinMenuService menuService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listPage() {
		List<WeixinMenuDto> tree = menuService.tree();
		List<WeixinMenu> menus = menuService.findAll();
		putRequestContext("menus", menus);
		putRequestContext("tree", tree);
		return "menu/menuList";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addPage() {
		return "menu/menuInput";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(WeixinMenu menu) {
		try {
			menuService.save(menu);
			return "redirect:/menu/list";
		} catch (Exception e) {
			e.printStackTrace();
			putRequestContext("error", e.getMessage());
		}
		
		return "menu/menuInput";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable Integer id) {
		menuService.delete(id);
		return "redirect:/menu/list";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String updatePage(@PathVariable Integer id) {
		WeixinMenu menu = menuService.findById(id);
		putRequestContext("menu", menu);
		return "menu/menuInput";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public String update(@PathVariable Integer id, WeixinMenu menu) {
		WeixinMenu dbMenu = menuService.findById(id);
		
		dbMenu.setContent(menu.getContent());
		dbMenu.setName(menu.getName());
		dbMenu.setType(menu.getType());
		dbMenu.setUrl(menu.getUrl());
		dbMenu.setParentId(menu.getParentId());
		dbMenu.setResponseType(menu.getResponseType());
		menuService.update(dbMenu);
		return "redirect:/menu/list";
	}
	
	@RequestMapping("/publish")
	public String publish() {
		menuService.publish();
		return "redirect:/menu/list";
	}
	
}
