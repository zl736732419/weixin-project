package com.zheng.weixin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 微信二维码
 *
 * @author zhenglian
 * @data 2016年1月11日 下午10:06:15
 */
@Entity
@Table(name = "t_weixinqr")
public class WeixinQr {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	private String name;
	private Integer type; // 微信二维码扫描后要做的业务类型
	private int status;
	private Integer snum; // 微信二维码场景值
	private String msg;
	private String qrData; // 扫描微信二维码后要发送到指定url的请求参数
	private Date createTime;
	private String ticket; // 二维码凭据

	public static final int MAX_FOREVER_SNUM = 100000; // 永久二维码最大场景值为100000
	public static final int TYPE_RESET_PASSWORD = 1; // 忘记密码，进行二维码扫描 为永久二维码
	public static final int TYPE_CHANGE_USER_GROUP = 2; // 修改用户分组 为永久二维码
	public static final int TYPE_USER_LOGIN = 3; // 用户登录
	public static final int TYPE_BIND_USER = 4; // 绑定用户
	
	public Date getCreateTime() {
		return createTime;
	}
	public String getId() {
		return id;
	}

	public String getMsg() {
		return msg;
	}

	public String getName() {
		return name;
	}

	public String getQrData() {
		return qrData;
	}

	public Integer getSnum() {
		return snum;
	}

	public int getStatus() {
		return status;
	}

	public String getTicket() {
		return ticket;
	}

	public Integer getType() {
		return type;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}

	public void setSnum(Integer snum) {
		this.snum = snum;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
