package com.zheng.weixin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zheng.weixin.ctx.WeixinConstant;
import com.zheng.weixin.domain.WeixinGroup;
import com.zheng.weixin.kit.JsonKit;
import com.zheng.weixin.kit.WeixinMessageKit;
import com.zheng.weixin.kit.WeixinReqKit;
import com.zheng.weixin.service.IWeixinGroupService;

@Service
public class WGroupServiceImpl implements IWeixinGroupService {

	@Override
	public String createWGroup(WeixinGroup group) {
		String url = WeixinConstant.GROUP_CREATE;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		Map<String, WeixinGroup> map = new HashMap<>();
		map.put("group", group);
		String data = JsonKit.obj2Json(map);
		String result = WeixinReqKit.reqPost(url, data, "application/json");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeixinGroup> findAllWGroups() {
		String url = WeixinConstant.GROUP_FIND_ALL;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		String result = WeixinReqKit.reqGet(url);
		Map<String, List<Map<String, Object>>> map = JsonKit.json2Obj(result, Map.class);
		List<Map<String, Object>> list = map.get("groups");
		List<WeixinGroup> groups = new ArrayList<>();
		for(Map<String, Object> m : list) {
			WeixinGroup group = new WeixinGroup();
			group.setId(((Double)m.get("id")).intValue());
			group.setName((String)m.get("name"));
			group.setCount(((Double)m.get("count")).intValue());
			groups.add(group);
		}
		
		return groups;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WeixinGroup findUserWGroup(String openId) {
		List<WeixinGroup> groups = findAllWGroups();
		String url = WeixinConstant.GROUP_FIND_USER_GROUP;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		Map<String, Object> map = new HashMap<>();
		map.put("openid", openId);
		String data = JsonKit.obj2Json(map);
		String result = WeixinReqKit.reqPost(url, data, "application/json");
		Map<String, Object> m = JsonKit.json2Obj(result, Map.class);
		Integer gid = ((Double) m.get("groupid")).intValue();
		WeixinGroup group = null;
		for(WeixinGroup g : groups) {
			if(g.getId() == gid) {
				group = g;
				break;
			}
		}
		
		return group;
	}

	@Override
	public String updateWGroupName(Integer gid, String name) {
		String url = WeixinConstant.GROUP_UPDATE_NAME;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		Map<String, WeixinGroup> map = new HashMap<>();
		WeixinGroup group = new WeixinGroup();
		group.setId(gid);
		group.setName(name);
		map.put("group", group);
		String data = JsonKit.obj2Json(map);
		String result = WeixinReqKit.reqPost(url, data, "application/json");
		
		return result;
	}

	@Override
	public String moveUserToWGroup(String openId, Integer gid) {
		String url = WeixinConstant.GROUP_MOVE_USER_TO_GROUP;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		Map<String, Object> map = new HashMap<>();
		map.put("openid", openId);
		map.put("to_groupid", gid);
		String data = JsonKit.obj2Json(map);
		String result = WeixinReqKit.reqPost(url, data, "application/json");
		return result;
	}

	@Override
	public String moveUsersToWGroup(List<String> openIds, Integer gid) {
		String url = WeixinConstant.GROUP_MOVE_USERS_TO_GROUP;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		Map<String, Object> map = new HashMap<>();
		map.put("openid_list", openIds);
		map.put("to_groupid", gid);
		String data = JsonKit.obj2Json(map);
		String result = WeixinReqKit.reqPost(url, data, "application/json");
		return result;
	}

	@Override
	public String deleteWGroup(Integer gid) {
		String url = WeixinConstant.GROUP_DELETE;
		url = WeixinMessageKit.replaceUrlAccessToken(url);
		Map<String, Object> map = new HashMap<>();
		WeixinGroup group = new WeixinGroup();
		group.setId(gid);
		map.put("group", group);
		String data = JsonKit.obj2Json(map);
		String result = WeixinReqKit.reqPost(url, data, "application/json");
		return result;
	}

}
