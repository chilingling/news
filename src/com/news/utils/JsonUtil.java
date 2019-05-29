package com.news.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JsonUtil {
 
	public static String toJson(int status,String msg,Object data){
		String jsonString = "";
		Object dataJson = JSON.toJSON(data);
		JSONObject resData = new JSONObject();
		resData.put("status", status);
		resData.put("msg", msg);
		resData.put("data", data);
		try {
			jsonString = JSON.toJSONString(resData);
		}catch (Exception e){
			// handleException
			e.printStackTrace();
		}
		return jsonString;
	}

}