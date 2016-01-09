package com.zheng.weixin.json;

import java.util.Map;

/**
 * 模版消息实体
 *
 * @author zhenglian
 * @data 2016年1月3日 上午11:44:11
 */
public class TemplateMsg {
	private String touser;
	private String template_id;
	private String url;
	private Map<String, Object> data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
