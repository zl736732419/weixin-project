package com.zheng.weixin.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zheng.weixin.ctx.WeixinConstant;
import com.zheng.weixin.dao.IBaseDao;
import com.zheng.weixin.dao.IWeixinMenuDao;
import com.zheng.weixin.domain.WeixinMenu;
import com.zheng.weixin.dto.WeixinMenuDto;
import com.zheng.weixin.kit.JsonKit;
import com.zheng.weixin.kit.WeixinBasicKit;
import com.zheng.weixin.kit.WeixinMessageKit;
import com.zheng.weixin.kit.WeixinReqKit;
import com.zheng.weixin.service.IWeixinMenuService;

@Service
public class WeixinMenuServiceImpl extends BaseServiceImpl<WeixinMenu>
		implements IWeixinMenuService {
	@Autowired
	private IWeixinMenuDao menuDao;

	@Override
	protected IBaseDao<WeixinMenu> getBaseDao() {
		return menuDao;
	}

	@Override
	public WeixinMenu findByKey(String key) {
		return menuDao.findByKey(key);
	}

	@Override
	public void save(WeixinMenu menu) {
		Integer parentId = menu.getParentId();
		Integer subMenuCount = menuDao.findMenuCountByParent(parentId);
		if (parentId == null || parentId == 0) { // 一级菜单
			if (subMenuCount > 3) {
				throw new RuntimeException("一级菜单数超过了3个!");
			}
		} else {
			if (subMenuCount > 5) {
				throw new RuntimeException("二级菜单数超过了5个!");
			}
		}

		// 生成key
		menu.setMenuKey("KEY_" + System.currentTimeMillis());
		Integer orderNum = menuDao.getMaxOrder(parentId, WeixinMenu.class);
		menu.setOrderNum(orderNum + 1);
		menuDao.save(menu);
		System.out.println("新增menu id: " + menu.getId());
	}

	@Override
	public void delete(Serializable id) {
		List<WeixinMenuDto> menus = tree();
		List<Integer> ids = new ArrayList<>();
		WeixinMenuDto dto = null;
		for(WeixinMenuDto menu : menus) {
			dto = findMenuDtoById(id, menu);
			if(dto != null) {
				break;
			}
		}
		
		if(dto != null) {
			getAllMenuDtoIds(dto, ids);
			menuDao.executeUpdateHql("delete WeixinMenu m where m.id in (:ids)", new String[] {"ids"}, ids);
		}else {
			throw new RuntimeException("异常，删除的菜单对象为空!");
		}
		
		
	}

	/**
	 * 获取当前菜单和其所有子菜单的id
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午4:53:29
	 * @param dto
	 * @param ids
	 */
	private void getAllMenuDtoIds(WeixinMenuDto dto, List<Integer> ids) {
		ids.add(dto.getId());
		for(WeixinMenuDto child : dto.getSub_button()) {
			getAllMenuDtoIds(child, ids);
		}
	}

	/**
	 * 
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午4:44:23
	 * @param id
	 * @return
	 */
	private WeixinMenuDto findMenuDtoById(Serializable id, WeixinMenuDto menu) {
		if (menu.getId() == id) {
			return menu;
		} else {
			for (WeixinMenuDto child : menu.getSub_button()) {
				if (child.getId() == id) {
					return child;
				}else {
					WeixinMenuDto dto = findMenuDtoById(id, child);
					if(dto != null) {
						return dto;
					}
				}
			}

		}

		return null;
	}

	@Override
	public List<WeixinMenuDto> tree() {
		List<WeixinMenu> menus = menuDao.findAll();
		// 根据菜单列表数据生成树形
		List<WeixinMenuDto> mds = new ArrayList<>();

		// 获取父节点
		for (WeixinMenu menu : menus) {
			WeixinMenuDto md = new WeixinMenuDto();
			md.setId(menu.getId());
			md.setKey(menu.getMenuKey());
			md.setName(menu.getName());
			md.setType(menu.getType());
			md.setUrl(menu.getUrl());
			md.setContent(menu.getContent());
			md.setOrderNum(menu.getOrderNum());
			if (menu.getParentId() == null || menu.getParentId() == 0) { // 父节点
				md.setSub_button(new ArrayList<WeixinMenuDto>());
				mds.add(md);
			}
		}

		// 对父节点进行排序
		Collections.sort(mds);

		// 生成菜单树,构建子菜单
		for (WeixinMenuDto menu : mds) {
			recursionMenu(menu, menus);
		}

		// 最后对一级菜单的子菜单排序
		for (WeixinMenuDto menu : mds) {
			Collections.sort(menu.getSub_button());
		}

		return mds;
	}

	/**
	 * 递归构建树型菜单
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午12:37:52
	 * @param menu
	 * @param menus
	 */
	private void recursionMenu(WeixinMenuDto parent, List<WeixinMenu> menus) {
		for (WeixinMenu menu : menus) {
			WeixinMenuDto md = new WeixinMenuDto();
			md.setId(menu.getId());
			md.setKey(menu.getMenuKey());
			md.setName(menu.getName());
			md.setType(menu.getType());
			md.setUrl(menu.getUrl());
			md.setContent(menu.getContent());
			md.setOrderNum(menu.getOrderNum());
			md.setSub_button(new ArrayList<WeixinMenuDto>());
			if (menu.getParentId() == parent.getId()) {
				parent.getSub_button().add(md);
				recursionMenu(md, menus);
			}
		}

		// 对子菜单排序
		Collections.sort(parent.getSub_button());
	}

	@Override
	public String publish() {
		List<WeixinMenuDto> menus = tree();
		Map<String, Object> map = new HashMap<>();
		map.put("button", menus);
		String json = JsonKit.obj2Json(map);
		String url = WeixinBasicKit
				.replaceAccessToken(WeixinConstant.CREATE_MENU);
		String content = WeixinReqKit.reqPostJson(url, json);
		if (WeixinReqKit.isReqSuc(content)) {
			// 从微信服务器上再提取刚创建的自定义菜单
			url = WeixinBasicKit.replaceAccessToken(WeixinConstant.QUERY_MENU);
			content = WeixinReqKit.reqGet(url);
			System.out.println(content);
			if (WeixinReqKit.isReqSuc(content)) {
				return content;
			} else {
				System.out.println(content);
			}
		} else {
			System.out.println(content);
		}

		return null;
	}

}
