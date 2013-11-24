package com.github.cloudm.utils;

import net.sf.json.JSONObject;

/**
 * json解析公共�?
 * 
 * @author andy.wan
 *
 */
public class JsonUtils {

	
	/**
	 * json解析
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Object parseJson(Object json, String key) {
		return JSONObject.fromObject(json).get(key);
	}
	
	/**
	 * json解析
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String parseJson(Object json, String key1, String key2) {
		Object obj = parseJson(json, key1);
		return String.valueOf(parseJson(obj, key2));
	}
	
	/**
	 * paas解析meta专用
	 * 获取错误序列
	 * @param json
	 * @return
	 */
	public static int getErrorSeq(String json) {
		// String to json
		JSONObject jsonObj = JSONObject.fromObject(json);
		// 循环�?��
		boolean ok = true;
		// error seq
		int i = 1;
		while(ok) {
			// 获取参数json
			JSONObject paramJson = JSONObject.fromObject(jsonObj.get(i+""));
			if(paramJson.isNullObject()) {
				return i-1;
			}
			// 获取状�?
			String status = paramJson.getString("status");
			if(!("201".equals(status) || "200".equals(status))) {
				ok = false;
			} else {
				i++;
			}
		}
		return i;
	}
	
	public static void main(String[] args) {
		String json = "{\"1\":{\"status\":\"200\",\"vmId\":\"1dfds\"},\"2\":{\"volumeId\":\"##33s\",\"status\":\"201\"}}";
		int i = getErrorSeq(json);
		System.out.println(i);
	}
}
