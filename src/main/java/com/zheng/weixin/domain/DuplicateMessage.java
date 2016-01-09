package com.zheng.weixin.domain;

/**
 * 消息排重实体
 *
 * @author zhenglian
 * @data 2016年1月9日 下午6:03:13
 */
public class DuplicateMessage {
	private String fromUserName;
	private String createTime;
	private Long curTime;
	private String respMsg; // 返回给微信的响应消息

	public DuplicateMessage() {
	}

	public DuplicateMessage(String fromUserName, String createTime) {
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.curTime = System.currentTimeMillis();
		this.respMsg = null;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getCurTime() {
		return curTime;
	}

	public void setCurTime(Long curTime) {
		this.curTime = curTime;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (!(obj instanceof DuplicateMessage)) {
			return false;
		}
		DuplicateMessage msg = (DuplicateMessage) obj;
		return msg.getCreateTime().equals(this.createTime)
				&& msg.getFromUserName().equals(this.fromUserName);
	}

}