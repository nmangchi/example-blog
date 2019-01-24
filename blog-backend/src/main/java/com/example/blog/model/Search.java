package com.example.blog.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Search implements Serializable {
	private static final long serialVersionUID = 1L;
	private String[] keys;
	private String[] values;
	
	@SuppressWarnings("unchecked")
	public <T> T to(Class<T> clazz) {
		if (keys == null || keys.length == 0) {
			return null;
		}
		
		Object obj = null;
		try {
			Map<String, Object> fromMap = new HashMap<>();
			for (int i = 0, len = keys.length; i < len; i++) {
				String key = keys[i];
				if (key.contains(".")) {
					dotKeyToMap(key, values[i], fromMap);
				} else {
					fromMap.put(key, values[i]);
				}
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			obj = objectMapper.convertValue(fromMap, clazz);
			
			return (T) obj;
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void dotKeyToMap(String key, String value, Map<String, Object> rootMap) {
		String[] elements = key.split("\\.");
		Map<String, Object> childMap = rootMap;
		for (int i = 0, len = elements.length; i < len; i++) {
			key = elements[i];
			if (i == len - 1) {
				childMap.put(key, value);
			} else {
				childMap = getChildMap(key, childMap);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> getChildMap(String key, Map<String, Object> parentMap) {
		Map<String, Object> childMap = (Map<String, Object>) parentMap.get(key);
		if (childMap == null) {
			childMap = new HashMap<>();
			parentMap.put(key, childMap);
		}
		return childMap;
	}
}
