package com.zheng.weixin.json;

import com.zheng.weixin.domain.User;

/**
 * 定义微信服务器用户信息
 *
 * @author zhenglian
 * @data 2016年1月10日 上午11:42:22
 */
public class WeixinUser {
	private Integer subscribe;
	private String openid;
	private String nickname;
	private Integer sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private Long subscribe_time;
	private String unionid;
	private String remark;
	private String groupid;

	public Integer getSubscribe() {
		return subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public String getNickname() {
		return nickname;
	}


	public String getLanguage() {
		return language;
	}

	public String getCity() {
		return city;
	}

	public String getProvince() {
		return province;
	}

	public String getCountry() {
		return country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public Long getSubscribe_time() {
		return subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public String getRemark() {
		return remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public void setSubscribe_time(Long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * 根据微信用户创建本地数据库用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 上午11:46:13
	 * @return
	 */
	public User getUser() {
		User user = new User();
		user.setUsername(this.openid);
		user.setPassword(this.openid);
		user.setNickname(this.nickname);
		user.setOpenId(this.openid);
		String sex = null;
		if(1 == this.sex) {
			sex = "男";
		}else if(2 == this.sex) {
			sex = "女";
		}else if(0 == this.sex) {
			sex = "未知";
		}
		user.setSex(sex);
		user.setStatus(1);
		user.setBind(0);
		user.setHeadImgUrl(this.headimgurl);
		
		return user;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
}
