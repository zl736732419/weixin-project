package com.zheng.weixin.service;

import java.util.List;

import com.zheng.weixin.domain.WeixinQr;

public interface IWeixinQrService extends IBaseService<WeixinQr> {

	/**
	 * 根据场景值获取二维码
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午10:16:03
	 * @param snum
	 * @return
	 */
	public WeixinQr loadBySnum(Integer snum);

	/**
	 * 查询永久二维码
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午10:15:35
	 * @return
	 */
	public List<WeixinQr> loadForeverQr();

	/**
	 * 查询临时二维码
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午10:15:53
	 * @return
	 */
	public List<WeixinQr> loadTempQr();
	
}
