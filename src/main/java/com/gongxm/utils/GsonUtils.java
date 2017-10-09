package com.gongxm.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	private static Gson gson = new Gson();

	// 对象转json
	public static <T> String toJson(T t) {
		String json = gson.toJson(t);
		return json;
	}

	// json转对象
	public static <T> T fromJson(String json, Type type) {
		T t = gson.fromJson(json, type);
		return t;
	}
	
	//对象转json
	public static <T> String parseToJson(T t) {
		 Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()  
		            .create();  
		String json = gson.toJson(t);
		return json;
	}
}
