package com.zheng.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zheng.weixin.domain.WeixinMenu;

@Repository
public interface IWeixinMenuDao extends IBaseDao<WeixinMenu> {
	/**
	 * 根据key查询某菜单
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 上午9:56:30
	 * @param key
	 * @return
	 */
	public WeixinMenu findByKey(String key);

	/**
	 * 查询父级菜单下的子菜单个数
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 上午9:56:11
	 * @param parentId
	 * @return
	 */
	public Integer findMenuCountByParent(Integer parentId);
	
}
