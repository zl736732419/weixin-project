package com.zheng.weixin.service;

import java.util.List;

import com.zheng.weixin.domain.WeixinMenu;
import com.zheng.weixin.dto.WeixinMenuDto;

public interface IWeixinMenuService extends IBaseService<WeixinMenu>{

	/**
	 * 根据key查找菜单
	 *
	 * @author zhenglian
	 * @data 2016年1月6日 下午10:44:27
	 * @param key
	 * @return
	 */
	public WeixinMenu findByKey(String key);
	
	/**
	 * 获取微信菜单树形菜单列表
	 *
	 * @author zhenglian
	 * @data 2016年1月6日 下午10:45:02
	 * @return
	 */
	public List<WeixinMenuDto> tree();

	/**
	 * 发布自定义菜单
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午2:32:18
	 * @param json
	 * @return
	 */
	public String publish();
	
}
