package com.microl.core.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.activity.InvalidActivityException;

import com.microl.core.LinkedKeyValueNode;



public class TimeLRUCache<K, Cacheable> extends ObjectCache<K, Cacheable>{
	
	private List<K> lruController = new LinkedList<K>();
	ArrayList<String> test = new ArrayList<String>();
	
	// the activated time. 
	private Integer unactivatedTime;
	private Integer timerScheduleTime;
	private Timer triggerRemoval = new Timer("removalTrigger");
	
	private Integer capacity;
	private Map<K, Cacheable> cache;
	
	private final Integer defaultTimerScheduleTime = 15000;
	
	
	
	public TimeLRUCache(Integer unactivatedTime, Integer capacity) {
		
	}

	public TimeLRUCache(Integer unactivatedTime, Integer capacity, Integer timerScheduleTime) {
		
	}
	
	
	

	@Override
	public Cacheable get(K key) {
		return null;
	}

	@Override
	public void put(K key, Cacheable value) throws InvalidActivityException {
		
	}
}
