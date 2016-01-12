package com.zheng.weixin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zheng.weixin.dao.IBaseDao;
import com.zheng.weixin.dao.IWeixinQrDao;
import com.zheng.weixin.domain.WeixinQr;
import com.zheng.weixin.kit.WeixinQrKit;
import com.zheng.weixin.service.IWeixinQrService;

@Service
public class WeixinQrServiceImpl extends BaseServiceImpl<WeixinQr> implements
		IWeixinQrService {

	@Autowired
	private IWeixinQrDao weixinQrDao;

	@Override
	protected IBaseDao<WeixinQr> getBaseDao() {
		return weixinQrDao;
	}

	@Override
	public WeixinQr loadBySnum(Integer snum) {
		return weixinQrDao.loadBySnum(snum);
	}

	@Override
	public List<WeixinQr> loadForeverQr() {
		return weixinQrDao.loadForeverQr();
	}

	@Override
	public List<WeixinQr> loadTempQr() {
		return weixinQrDao.loadTempQr();
	}

	@Override
	public void save(WeixinQr qr) {
		qr.setStatus(1); // 暂时设置为1 status后面使用
		if (qr.getSnum() < WeixinQr.MAX_FOREVER_SNUM) { // 添加永久二维码
			WeixinQr dbQr = weixinQrDao.loadBySnum(qr.getSnum());
			// 根据场景值查找数据库中是否存在，如果存在不允许保存
			if (dbQr != null) {
				throw new RuntimeException("添加二维码失败，二维码场景值已经被使用!");
			}
			qr.setCreateTime(new Date());
			String ticket = WeixinQrKit.getForeverQrTicket(qr);
			if(StringUtils.isBlank(ticket)) {
				throw new RuntimeException("获取永久二维码ticket为空!");
			}
			qr.setTicket(ticket);
			weixinQrDao.save(qr);
		} else { // 添加临时二维码
			saveTempQr(qr);
		}

	}

	/**
	 * 添加临时二维码
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午10:54:44
	 * @param qr
	 * @param dbQr
	 */
	private void saveTempQr(WeixinQr qr) {
		WeixinQr dbQr = weixinQrDao.loadBySnum(qr.getSnum());
		if (dbQr != null) {
			// 如果数据库中存在场景值对应的临时二维码，判断该二维码是否过期
			// 判断临时二维码是否过期, 默认1分钟过期
			if (checkExpired(dbQr)) {
				dbQr.setCreateTime(new Date());
				dbQr.setMsg(qr.getMsg());
				dbQr.setName(qr.getName());
				dbQr.setQrData(qr.getQrData());
				dbQr.setSnum(qr.getSnum());
				dbQr.setStatus(qr.getStatus());
				dbQr.setType(qr.getType());
				String ticket = WeixinQrKit.getForeverQrTicket(dbQr);
				if(StringUtils.isBlank(ticket)) {
					throw new RuntimeException("获取临时二维码ticket为空!");
				}
				dbQr.setTicket(ticket);
				weixinQrDao.update(dbQr);
			}else {
				//重新指定一个新的场景值
				qr.setSnum((WeixinQr.MAX_FOREVER_SNUM + 1) + RandomUtils.nextInt());
				saveTempQr(qr);
			}
		} else {
			qr.setCreateTime(new Date());
			String ticket = WeixinQrKit.getForeverQrTicket(qr);
			if(StringUtils.isBlank(ticket)) {
				throw new RuntimeException("获取临时二维码ticket为空!");
			}
			qr.setTicket(ticket);
			weixinQrDao.save(qr);
		}

	}

	/**
	 * 判断二维码是否过期
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午10:54:34
	 * @param dbQr
	 * @return
	 */
	private boolean checkExpired(WeixinQr dbQr) {
		return (System.currentTimeMillis() - dbQr.getCreateTime().getTime()) / 1000 > 60;
	}

}
