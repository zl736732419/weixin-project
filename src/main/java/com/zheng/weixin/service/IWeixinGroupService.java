package com.zheng.weixin.service;

import java.util.List;

import com.zheng.weixin.domain.WeixinGroup;

/**
 * 微信用户分组业务接口 定义微信分组管理相关API
 *
 * @author zhenglian
 * @data 2016年1月3日 下午9:04:13
 */
public interface IWeixinGroupService {

	/**
	 * 创建分组
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:05:57
	 * @param group
	 * @return
	 */
	public String createWGroup(WeixinGroup group);

	/**
	 * 查询所有分组
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:06:27
	 * @return
	 */
	public List<WeixinGroup> findAllWGroups();

	/**
	 * 查询用户所属分组
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:06:57
	 * @param openId
	 * @return
	 */
	public WeixinGroup findUserWGroup(String openId);

	/**
	 * 修改分组名称
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:08:11
	 * @param gid
	 * @param name
	 * @return
	 */
	public String updateWGroupName(Integer gid, String name);

	/**
	 * 移动用户到某一个分组
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:09:07
	 * @param openId
	 * @param gid
	 * @return
	 */
	public String moveUserToWGroup(String openId, Integer gid);

	/**
	 * 移动一批用户到某一个分组
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:09:43
	 * @param openIds
	 * @param gid
	 * @return
	 */
	public String moveUsersToWGroup(List<String> openIds, Integer gid);

	/**
	 * 删除某一个分组
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:10:08
	 * @param gid
	 * @return
	 */
	public String deleteWGroup(Integer gid);

}
