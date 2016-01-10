package com.zheng.weixin.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.zheng.weixin.ctx.WeixinConstant;
import com.zheng.weixin.ctx.WeixinContext;
import com.zheng.weixin.dao.IWeixinMenuDao;
import com.zheng.weixin.domain.User;
import com.zheng.weixin.domain.WeixinMenu;
import com.zheng.weixin.json.TemplateMsg;
import com.zheng.weixin.json.WeixinUser;
import com.zheng.weixin.listener.SpringManager;
import com.zheng.weixin.service.IUserService;
import com.zheng.weixin.service.IWeixinUserService;

/**
 * 解析微信post过来的数据 响应的数据只能通过inputstream的方式来获取
 *
 * @author zhenglian
 * @data 2016年1月2日 上午9:20:17
 */
public class WeixinMessageKit {
	/**
	 * 文本消息列表
	 */
	public static Map<String, Object> textMsgs = new HashMap<>();
	static {
		textMsgs.put("hello", "world");
		textMsgs.put("123", "456");
		textMsgs.put("run", "good luck to you !");
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> req2Map(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String text = getMsgContent(request);
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for (Element element : elements) {
				map.put(element.getName(), element.getTextTrim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取微信服务器反馈的xml文本内容
	 *
	 * @author zhenglian
	 * @data 2016年1月2日 上午9:50:01
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static String getMsgContent(HttpServletRequest request)
			throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		return builder.toString();
	}
	
	/**
	 * 发送微信url授权认证请求
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 下午6:05:34
	 * @param url
	 * @param resp
	 * @throws IOException 
	 * 
	 */
	public static void sendWeixinAuth(String url, HttpServletResponse resp) throws IOException {
		String weixinUrl = WeixinConstant.AUTH_QUERY_CODE
				.replaceAll("APPID", WeixinContext.getInstance().getAppId())
				.replaceAll("REDIRECT_URI", URLEncoder.encode(url, "UTF-8"))
				.replaceAll("SCOPE", "snsapi_base")
				.replaceAll("state", "1");
		resp.sendRedirect(weixinUrl); // 请求微信端进行授权
	}
	
	/**
	 * 处理点击事件
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午4:19:46
	 * @param menu
	 * @param msgMap
	 * @return
	 */
	public static String handleClickEvent(Map<String, Object> msgMap) {
		String eventKey = (String)msgMap.get("EventKey");
		IWeixinMenuDao menuDao = (IWeixinMenuDao) SpringManager.getBean("weixinMenuDaoImpl", IWeixinMenuDao.class);
		//根据eventKey到数据库查询该菜单responseType
		WeixinMenu menu = menuDao.findByKey(eventKey);
		Integer responseType = menu.getResponseType();
		Map<String, Object> respMap = null;
		if(1 == responseType) { //返回content中的内容(构造一个文本消息返回)
			respMap = WeixinMessageCreateKit.createTextMsg(msgMap, menu.getContent());
		}
		String msg = WeixinMessageKit.map2Xml(respMap);
		return msg;
	}

	/**
	 * 根据map创建xml
	 *
	 * @author zhenglian
	 * @data 2016年1月2日 上午10:40:42
	 * @param respMsg
	 * @return
	 * @throws IOException
	 */
	public static String map2Xml(Map<String, Object> respMsg) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		Set<String> keys = respMsg.keySet();
		Object obj = null;
		for (String key : keys) {
			obj = respMsg.get(key);
			if(obj instanceof String) {
				String value = (String) obj;
				if(value.indexOf("<a") != -1) {
					root.addElement(key).addCDATA(value);
				}else {
					root.addElement(key).addText(value);
				}
			}
		}
		StringWriter writer = new StringWriter();
		XMLWriter xw = new XMLWriter(writer);
		xw.setEscapeText(false);
		try {
			xw.write(document);
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送模版消息
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 上午11:46:33
	 * @param msg
	 */
	public static void sendTemplateMsg(TemplateMsg msg) {
		String url = WeixinConstant.SEND_TEMPLATE_MSG.replaceAll(
				"ACCESS_TOKEN", WeixinContext.getInstance().getAccessToken().getAccess_token());
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse resp = null;
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json;charset=utf-8");
		String json = JsonKit.obj2Json(msg);
		System.out.println(json);
		StringEntity entity = new StringEntity(json, ContentType.create(
				"application/json", Charset.forName("UTF-8")));
		post.setEntity(entity);
		try {
			resp = client.execute(post);
			int sc = resp.getStatusLine().getStatusCode();
			if(sc >= 200 && sc < 300) {
				String result = EntityUtils.toString(resp.getEntity());
				System.out.println(result);
			}else {
				System.out.println("获取响应消息异常!状态码：" + sc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(resp != null) {
				try {
					resp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将url中accessToken赋值
	 *
	 * @author zhenglian
	 * @data 2016年1月3日 下午9:11:50
	 * @param url
	 * @return
	 */
	public static String replaceUrlAccessToken(String url) {
		url = url.replaceAll("ACCESS_TOKEN", WeixinContext.getInstance().getAccessToken().getAccess_token());
		return url;
	}

	/**
	 * 处理用户关注事件
	 * 根据openid关联本地用户
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 上午11:48:23
	 * @param msgMap
	 * @return
	 */
	public static String handleSubscribeEvent(Map<String, Object> msgMap) {
		String openId = (String) msgMap.get("FromUserName");
		IUserService userService = (IUserService) SpringManager.getBean("userServiceImpl", IUserService.class);
		IWeixinUserService weixinUserService = (IWeixinUserService) SpringManager.getBean("weixinUserServiceImpl", IWeixinUserService.class);
		User dbUser = userService.loadByOpenId(openId);
		if(dbUser == null) { //当前关注的用户不存在
			//获取微信端当前openid对应的用户信息
			WeixinUser wUser = weixinUserService.loadWeixinUserByOpenId(openId);
			dbUser = wUser.getUser();
			userService.save(dbUser);
		}else {
			dbUser.setStatus(1); //修改为已关注状态
			userService.update(dbUser);
		}
		String msg = null;
		//判断用户是否已经绑定
		if(dbUser.getBind() == 0) {
			msg = "<a href=\"http://zl123.zicp.net/weixin-project/user/bindNewUser\">欢迎您关注我们的微信公众帐号，点击链接绑定获得更佳体验!</a>";
		}else {
			msg = "<a href=\"http://www.baidu.com\">欢迎您关注我们的微信公众帐号，点击了解更多</a>";;
		}
		System.out.println("============用户：" + openId + "关注了微信公众号!");
		return WeixinMessageKit.map2Xml(WeixinMessageCreateKit.createTextMsg(msgMap, msg));
	}

	/**
	 * 处理用户取消关注
	 *
	 * @author zhenglian
	 * @data 2016年1月10日 下午12:13:27
	 * @param msgMap
	 */
	public static void handleUnSubscribeEvent(Map<String, Object> msgMap) {
		String openId = (String) msgMap.get("FromUserName");
		IUserService userService = (IUserService) SpringManager.getBean("userServiceImpl", IUserService.class);
		User dbUser = userService.loadByOpenId(openId);
		if(dbUser != null) {
			dbUser.setStatus(0);
			userService.update(dbUser);
			System.out.println("================用户：" + openId + "取消了对微信公众号的关注!");
		}
	}

}
