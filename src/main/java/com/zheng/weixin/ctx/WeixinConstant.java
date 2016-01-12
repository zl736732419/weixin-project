package com.zheng.weixin.ctx;

/**
 * 微信服务接口的一些常量值
 *
 * @author zhenglian
 * @data 2015年12月27日 下午6:05:03
 */
public class WeixinConstant {
	/**
	 * 获取accessToken的url
	 */
	public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 自定义菜单
	 */
	public static String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 查询菜单
	 */
	public static String QUERY_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 查询用户信息
	 */
	public static String QUERY_USER = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	/**
	 * 获取微信二维码凭据
	 */
	public static String QUERY_QR_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取微信二维码
	 */
	public static String QUERY_QR = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	/**
	 * 查询授权code
	 */
	public static String AUTH_QUERY_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	/**
	 * 根据授权code获取AccessToken
	 */
	public static String AUTH_QUERY_ACCESS_TOKEN_BY_CODE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	/**
	 * 上传临时素材
	 */
	public static String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 下载临时素材
	 */
	public static String DOWNLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 发送模版消息
	 */
	public static String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 文本消息类型
	 */
	public static String MSG_TYPE_TEXT = "text";
	
	/**
	 * 图片消息类型
	 */
	public static String MSG_TYPE_IMAGE = "image";
	
	/**
	 * 语音消息类型
	 */
	public static String MSG_TYPE_VOICE = "voice";
	
	/**
	 * 视频消息类型
	 */
	public static String MSG_TYPE_VIDEO = "video";
	
	/**
	 * 小视频消息类型
	 */
	public static String MSG_TYPE_SHORTVIDEO = "shortvideo";
	
	/**
	 * 地理位置消息类型
	 */
	public static String MSG_TYPE_LOCATION = "location";

	/**
	 * 创建用户分组
	 */
	public static String GROUP_CREATE = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";

	/**
	 * 查询所有分组
	 */
	public static String GROUP_FIND_ALL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 查询用户所属分组
	 */
	public static String GROUP_FIND_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	
	/**
	 * 修改分组名
	 */
	public static String GROUP_UPDATE_NAME = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 移动用户分组
	 */
	public static String GROUP_MOVE_USER_TO_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 批量移动用户到分组
	 */
	public static String GROUP_MOVE_USERS_TO_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除指定分组
	 */
	public static String GROUP_DELETE = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";

}
