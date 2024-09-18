package ca.testeshop.utils;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonUtils {
	
	JsonUtils() {
		
	}
	
	private static boolean shouldUseJackson(Class<?> _class) {
		if (_class.equals(Content.class)) {
			return true;
		}
		return false;
	}
	
	public static Object jsonToPojo(String json, Class<?> _class) throws Exception {
		if (shouldUseJackson(_class)) {
			// use jackson to deal with byte[] arrays
			return new ObjectMapper().readValue(json, _class);
		}
		return new Gson().fromJson(json, _class);
	}
	
	public static Object jsonToPojo(String json, Type type) {
		return new Gson().fromJson(json, type);
	}
	
	public static <T> T jsonToPojo(String json, TypeReference<T> type) throws Exception {
		return new ObjectMapper().readValue(json, type);
	}
	
	public static String pojoToJson(Object pojo) {
		return new Gson().toJson(pojo);
	}
}
