package ca.testeshop.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtils {
	
	JsonUtils() {
		
	}
	
	public static Object jsonToPojo(String json, Class<?> _class) {
		return new Gson().fromJson(json, _class);
	}
	
	public static Object jsonToPojo(String json, Type type) {
		return new Gson().fromJson(json, type);
	}
	
	public static String pojoToJson(Object pojo) {
		return new Gson().toJson(pojo);
	}
}
