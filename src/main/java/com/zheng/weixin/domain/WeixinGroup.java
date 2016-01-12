package com.zheng.weixin.domain;

/**
 * 微信分组实体
 *
 * @author zhenglian
 * @data 2016年1月3日 下午9:03:10
 */
public class WeixinGroup {
	private Integer id;
	private String name;
	private Integer count;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
