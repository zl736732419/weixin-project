package com.zheng.weixin.json;

/**
 * 微信上传media反馈的消息对象
 *
 * @author zhenglian
 * @data 2016年1月3日 上午10:16:31
 */
public class WeixinMedia {
	private String type;
	private String media_id;
	private String created_at;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
