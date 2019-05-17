package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;

public class Event<T extends Enum<T>> {

	private T name;
	
	private final Map<String, Object> payload;
	
	public Event(T name) {
		this.name = name;
		this.payload = new HashMap<String, Object>();
	}
	
	public Event(T name, Map<String, Object> payload) {
		this.name = name;
		this.payload = payload;
	}
	
	public final T getName() {
		return name;
	}
	
	@SuppressWarnings("unchecked")
	public <U> U getField(String key, Class<U> clazz) {
		return (U) payload.get(key);
	}
	
	public void setField(String key, Object value) {
		payload.put(key, value);
	}
	
}
