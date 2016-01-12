package com.zheng.weixin.kit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.zheng.weixin.ctx.WeixinConstant;
import com.zheng.weixin.domain.WeixinQr;

/**
 * 微信二维码工具类
 *
 * @author zhenglian
 * @data 2016年1月11日 下午11:22:00
 */
public class WeixinQrKit {

	/**
	 * 获取永久二维码的凭据
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午11:22:45
	 * @param qr
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getForeverQrTicket(WeixinQr qr) {
		String url = WeixinMessageKit.replaceUrlAccessToken(WeixinConstant.QUERY_QR_TICKET);
		String json = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+qr.getSnum()+"}}}";
		String content = WeixinReqKit.reqPostJson(url, json);
		if(WeixinReqKit.isReqSuc(content)) {
			try {
				String ticket = URLEncoder.encode((String)JsonKit.json2Map(content).get("ticket"), "UTF-8");
				return ticket;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}else {
			throw new RuntimeException("获取永久二维码ticket异常！");
		}
	}
	
	/**
	 * 获取临时二维码的凭据
	 *
	 * @author zhenglian
	 * @data 2016年1月11日 下午11:22:57
	 * @param qr
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getTempQrTicket(WeixinQr qr) {
		String url = WeixinMessageKit.replaceUrlAccessToken(WeixinConstant.QUERY_QR_TICKET);
		String json = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+qr.getSnum()+"}}}";
		String content = WeixinReqKit.reqPostJson(url, json);
		if(WeixinReqKit.isReqSuc(content)) {
			try {
				String ticket = URLEncoder.encode((String)JsonKit.json2Map(content).get("ticket"), "UTF-8");
				return ticket;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}else {
			throw new RuntimeException("获取永久二维码ticket异常！");
		}
	}
	
}
