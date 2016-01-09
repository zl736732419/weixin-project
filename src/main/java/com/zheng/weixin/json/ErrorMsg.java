package com.zheng.weixin.json;

/**
 * 访问微信服务接口返回的错误信息实体
 *
 * @author zhenglian
 * @data 2015年12月27日 下午6:11:09
 */
public class ErrorMsg {
	private String errcode;
	private String errmsg;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
