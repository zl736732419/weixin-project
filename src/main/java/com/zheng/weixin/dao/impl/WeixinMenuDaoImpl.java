package com.zheng.weixin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zheng.weixin.dao.IWeixinMenuDao;
import com.zheng.weixin.domain.WeixinMenu;

@Repository
public class WeixinMenuDaoImpl extends BaseDaoImpl<WeixinMenu> implements
		IWeixinMenuDao {
	
	@Override
	public WeixinMenu findByKey(String key) {
		String hql = "from WeixinMenu m where m.menuKey=?";
		return findSingleByHql(hql, key);
	}

	@Override
	public Integer findMenuCountByParent(Integer parentId) {
		String hql = "select count(m) from WeixinMenu m where m.parentId=?";
		Integer count = ((Long)loadObjByHql(hql, parentId)).intValue();
		return count;
	}
}
