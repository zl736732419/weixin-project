package com.zheng.weixin.dto;

import java.util.List;

/**
 * 生成微信菜单的树形对象 微信菜单只有两级所以不需要采用树形结构构建菜单
 *
 * @author zhenglian
 * @data 2016年1月6日 下午10:38:51
 */
public class WeixinMenuDto implements Comparable<WeixinMenuDto> {
	private String name;
	private String type;
	private Integer orderNum;
	private String url;
	private String key;
	private String content;
	private Integer id;

	private List<WeixinMenuDto> sub_button;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public List<WeixinMenuDto> getSub_button() {
		return sub_button;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setSub_button(List<WeixinMenuDto> sub_button) {
		this.sub_button = sub_button;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int compareTo(WeixinMenuDto o) {
		if (this.orderNum > o.getOrderNum()) {
			return 1;
		} else if (this.orderNum == o.getOrderNum()) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof WeixinMenuDto)) {
			return false;
		}
		WeixinMenuDto dto = (WeixinMenuDto) obj;

		return this.getKey().equals(dto.getKey());
	}

}
