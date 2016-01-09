package com.zheng.weixin.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信的菜单实体
 *
 * @author zhenglian
 * @data 2016年1月6日 下午10:02:20
 */
@Entity
@Table(name = "t_menu")
public class WeixinMenu {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	/**
	 * 菜单类型
	 */
	private String type;
	/**
	 * 菜单key
	 */
	private String menuKey;
	private String url;
	private String content;
	/**
	 * 响应的类型 1 ：取content响应
	 */
	private Integer responseType;
	private Integer orderNum;

	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public String getName() {
		return name;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public Integer getParentId() {
		return parentId;
	}

	public Integer getResponseType() {
		return responseType;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
