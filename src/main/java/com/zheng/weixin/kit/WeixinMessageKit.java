package com.zheng.weixin.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.zheng.weixin.domain.WeixinMenu;
import com.zheng.weixin.json.TemplateMsg;

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
	 * 处理点击事件
	 *
	 * @author zhenglian
	 * @data 2016年1月9日 下午4:19:46
	 * @param menu
	 * @param msgMap
	 * @return
	 */
	public static String handleClickEvent(WeixinMenu menu,
			Map<String, Object> msgMap) {
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
		Object value = null;
		for (String key : keys) {
			value = respMsg.get(key);
			if(value instanceof String) {
				root.addElement(key).addText((String) value);
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

}
