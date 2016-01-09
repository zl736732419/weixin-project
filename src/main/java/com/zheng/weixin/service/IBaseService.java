package com.zheng.weixin.service;

import java.io.Serializable;
import java.util.List;

/**
 * 通用业务层接口
 *
 * @author zhenglian
 * @time 2015年12月25日 下午12:18:39
 * @param <T>
 */
public interface IBaseService<T> {
	/**
	 * 保存对象
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:25:30
	 * 
	 * @param t
	 * @return
	 */
	public void save(T t);

	/**
	 * 更新对象
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:25:46
	 * 
	 * @param t
	 */
	public void update(T t);

	/**
	 * 删除指定id的对象
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:26:03
	 * 
	 * @param id
	 */
	public void delete(Serializable id);

	/**
	 * 根据id获取对象
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:26:36
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Serializable id);
	
	/**
	 * 查询所有实例
	 *
	 * @author zhenglian
	 * @data 2016年1月6日 下午10:37:49
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 根据hql查询单一结果对象
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:26:54
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T findSingleByHql(String hql, Object... params);

	/**
	 * 根据指定hql查询对象列表
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:28:02
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> findListByHql(String hql, Object... params);

	/**
	 * 执行更新操作
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:28:56
	 * 
	 * @param hql
	 * @param params
	 */
	public void executeUpdateHql(String hql, Object... params);

	/**
	 * 获取所有记录条数
	 * 
	 * @auther zhenglian
	 * @date 2015年12月25日 上午11:51:46
	 * 
	 * @return
	 */
	public int getCount();
}
