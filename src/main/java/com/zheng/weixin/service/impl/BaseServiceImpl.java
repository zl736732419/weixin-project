package com.zheng.weixin.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zheng.weixin.dao.IBaseDao;
import com.zheng.weixin.service.IBaseService;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {

	protected abstract IBaseDao<T> getBaseDao();
	
	@Override
	public void save(T t) {
		getBaseDao().save(t);
	}

	@Override
	public void update(T t) {
		getBaseDao().update(t);		
	}

	@Override
	public void delete(Serializable id) {
		getBaseDao().delete(id);
	}

	@Override
	public T findById(Serializable id) {
		return getBaseDao().findById(id);
	}

	@Override
	public List<T> findAll() {
		return getBaseDao().findAll();
	}
	
	@Override
	public T findSingleByHql(String hql, Object... params) {
		return getBaseDao().findSingleByHql(hql, params);
	}

	@Override
	public List<T> findListByHql(String hql, Object... params) {
		return getBaseDao().findListByHql(hql, params);
	}

	@Override
	public void executeUpdateHql(String hql, Object... params) {
		getBaseDao().executeUpdateHql(hql, params);
	}

	@Override
	public int getCount() {
		return getBaseDao().getCount();
	}

}
