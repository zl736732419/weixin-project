package com.zheng.weixin.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * json工具类
 *
 * @author zhenglian
 * @data 2015年12月27日 下午7:06:39
 */
public class JsonKit {

	private static Gson gson = null;
	
	static {
		if(gson == null) {
			gson = new Gson();
		}
	}
	
	/**
	 * 将对象转化为json字符串
	 *
	 * @author zhenglian
	 * @data 2015年12月27日 下午7:07:57
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj) {
		String result = gson.toJson(obj);
		return result;
	}
	
	/**
	 * 将json字符串转化为指定的对象
	 *
	 * @author zhenglian
	 * @data 2015年12月27日 下午7:08:43
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		T t = gson.fromJson(json, clazz);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String json) {
		Map<String, Object> map = gson.fromJson(json, Map.class);
		return map;
	}
}
