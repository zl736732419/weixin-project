package com.zheng.weixin.kit;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zheng.weixin.ctx.WeixinContext;
import com.zheng.weixin.json.ErrorMsg;

/**
 * 微信常用工具方法
 *
 * @author zhenglian
 * @data 2016年1月6日 下午9:23:37
 */
public class WeixinBasicKit {
	private static Logger logger = LoggerFactory
			.getLogger(WeixinBasicKit.class);

	/**
	 * 将字符串str进行sha1加密操作
	 *
	 * @author zhenglian
	 * @data 2015年12月27日 下午4:35:21
	 * @param str
	 * @return
	 */
	public static String sha1(String str) {
		StringBuilder builder = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("sha1");
			md.update(str.getBytes());
			byte[] bytes = md.digest();
			String s = null;
			for (byte b : bytes) {
				s = String.format("%02x", b);
				builder.append(s);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	/**
	 * 加载微信常用变量
	 *
	 * @author zhenglian
	 * @data 2016年1月6日 下午9:24:27
	 */
	public static void loadWeixinConfig() {
		Properties p = new Properties();
		try {
			p.load(WeixinBasicKit.class.getClassLoader().getResourceAsStream(
					"weixin.properties"));

			WeixinContext.getInstance().setAppId(p.getProperty("appId"));
			WeixinContext.getInstance()
					.setAppSecret(p.getProperty("appSecret"));
			WeixinContext.getInstance().setBaseUrl(p.getProperty("baseUrl"));
			WeixinContext.getInstance().setToken(p.getProperty("token"));
			logger.info("==================加载微信配置常用变量完成==============");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将url中的accessToken赋值
	 *
	 * @author zhenglian
	 * @data 2016年1月7日 下午10:53:59
	 * @param url
	 * @return
	 */
	public static String replaceAccessToken(String url) {
		url = url.replaceAll("ACCESS_TOKEN", WeixinContext.getInstance()
				.getAccessToken().getAccess_token());
		return url;
	}
}
