package com.zheng.weixin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zheng.weixin.dao.IWeixinQrDao;
import com.zheng.weixin.domain.WeixinQr;

@Repository
public class WeixinQrDaoImpl extends BaseDaoImpl<WeixinQr> implements IWeixinQrDao{

	@Override
	public WeixinQr loadBySnum(Integer snum) {
		String hql = "from WeixinQr wq where wq.snum = ?";
		return findSingleByHql(hql, snum);
	}

	@Override
	public List<WeixinQr> loadForeverQr() {
		String hql = "from WeixinQr wq where wq.snum <= " + WeixinQr.MAX_FOREVER_SNUM;
		return findListByHql(hql, new Object[] {});
	}

	@Override
	public List<WeixinQr> loadTempQr() {
		String hql = "from WeixinQr wq where wq.snum > " + WeixinQr.MAX_FOREVER_SNUM;
		return findListByHql(hql, new Object[] {});
	}

}
