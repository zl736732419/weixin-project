package com.zheng.weixin.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.zheng.weixin.dao.IBaseDao;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private Class<?> clazz;

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<?>) type.getActualTypeArguments()[0];
	}

	public SessionFactory getFactory() {
		return super.getSessionFactory();
	}

	@Autowired
	public void setFactory(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	public Session getSession() {
		return getFactory().getCurrentSession();
	}

	@Override
	public void save(T t) {
		getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findById(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Serializable id) {
		return (T) getHibernateTemplate().load(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return (List<T>) getHibernateTemplate().loadAll(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findSingleByHql(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findListByHql(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		return query.list();
	}

	@Override
	public void executeUpdateHql(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		query.executeUpdate();
	}
	
	@Override
	public void executeUpdateHql(String hql, String[] paramNames,
			Object... params) {
		Query query = getSession().createQuery(hql);
		if(paramNames != null && paramNames.length > 0) {
			String paramName = null;
			for(int i = 0; i < paramNames.length; i++) {
				paramName = paramNames[i];
				if(params[i] instanceof Collection) {
					Collection<?> c = (Collection<?>) params[i];
					query.setParameterList(paramName, c);
				}else {
					query.setParameter(paramName, params[i]);
				}
			}
		}
		
		query.executeUpdate();
	}
	
	@Override
	public int getCount() {
		return 0;
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public T loadBySn(String sn, Class<?> clazz) {
		String hql = "from " + clazz.getSimpleName() + " o where o.sn=?";
		Query query = getSession().createQuery(hql);
		return (T) findSingleByHql(hql, sn);
	}

	@Override
	public Integer getMaxOrder(Serializable parentId, Class<?> clazz) {
		String hql = "select max(o.orderNum) from " + clazz.getSimpleName() + " o where 1=1 ";
		if(parentId != null) {
			hql += " and o.parentId = " + parentId;
		}else {
			hql += " o.parentId is null";
		}
		Query query = getSession().createQuery(hql);
		Integer orderNum = (Integer) query.uniqueResult();
		if(orderNum == null) {
			orderNum = 0;
		}
		return orderNum;
	}

	@Override
	public Object loadObj(Serializable id, Class<?> clazz) {
		return getSession().load(clazz, id);
	}

	@Override
	public List<?> listObj(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		if(params != null && params.length > 0) {
			for(int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	@Override
	public Object loadObjByHql(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		if(params != null && params.length >= 0) {
			for(int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query.uniqueResult();
	}
}
