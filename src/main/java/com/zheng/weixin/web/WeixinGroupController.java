package com.zheng.weixin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zheng.weixin.domain.WeixinGroup;
import com.zheng.weixin.service.IWeixinGroupService;

@Controller
@RequestMapping("/group")
public class WeixinGroupController extends BaseController {
	@Autowired
	private IWeixinGroupService groupService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listPage() {
		List<WeixinGroup> groups = groupService.findAllWGroups();
		putRequestContext("groups", groups);
		
		return "group/groupList";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addPage() {
		return "group/groupAdd";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(String name) {
		WeixinGroup group = new WeixinGroup();
		group.setName(name);
		groupService.createWGroup(group);
		return "redirect:/group/list";
	}
	
	@RequestMapping(value="/findUserGroup", method=RequestMethod.GET)
	public String findUserGroupPage() {
		return "group/findUserGroup";
	}
	
	@RequestMapping(value="/findUserGroup", method=RequestMethod.POST)
	public String findUserGroup(String openId) {
		WeixinGroup group = groupService.findUserWGroup(openId);
		putRequestContext("openId", openId);
		putRequestContext("group", group);
		return "group/findUserGroup";
	}
	
}
