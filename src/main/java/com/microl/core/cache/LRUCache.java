package com.microl.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.activity.InvalidActivityException;

import com.microl.core.Cache;
import com.microl.core.enumeration.LRURemovalFlag;

public class LRUCache<K, V> implements Cache<K, V> {

	private Long hitCounts;
	private Long missCounts;
	
	private LinkedList<K> lruController;
	private Map<K, V> cache;
	private CacheProperties cacheProperties;
	private Integer capacity;
	
	
	private Integer refreshTime;
	private Integer lruRemovalTime;
	
	private Integer lruRemovalNumber;
	private Integer lruRemovalNumWhenFullInterval;
	private LRURemovalFlag lruRemovalFlag;
	
	private Timer refreshTrigger;
	private Timer lruRemovalTrigger;
	
	
	
	public LRUCache(Integer capacity) {
		initializeVariables(capacity);
		
		scheduleRefresh(this.refreshTime);
	}
	
	public LRUCache(Integer capacity, LRURemovalFlag lruRemovalFlag) {
		initializeVariables(capacity);
		this.lruRemovalFlag = lruRemovalFlag;
		
		if(lruRemovalFlag.equals(LRURemovalFlag.INTERVAL)) {
			scheduleLRURemoval(this.lruRemovalTime);
		}
		
		scheduleRefresh(this.refreshTime);
	}
	
	public LRUCache(Integer capacity, LRURemovalFlag lruRemovalFlag, Integer lruRemovalTime) {
		initializeVariables(capacity);
		this.lruRemovalFlag = lruRemovalFlag;
		
		if(lruRemovalFlag.equals(LRURemovalFlag.INTERVAL)) {
			scheduleLRURemoval(lruRemovalTime);
		}
		
		scheduleRefresh(this.refreshTime);
	}
	
	public LRUCache(Integer capacity, LRURemovalFlag lruRemovalFlag, Integer lruRemovalTime, Integer refreshTime) {
		this(capacity, lruRemovalFlag, lruRemovalTime);
		scheduleRefresh(refreshTime);
	}
	
	private void initializeVariables(Integer capacity) {
		this.hitCounts = 0L;
		this.missCounts = 0L;
		this.lruController = new LinkedList<K>();
		this.cache = new HashMap<K, V>();
		this.cacheProperties = new CacheProperties.CachePropertyBuilder().build();
		this.capacity = capacity;
		this.refreshTime = Integer.valueOf(cacheProperties.getProperty("default.refresh.time"));
		this.lruRemovalTime = Integer.valueOf(cacheProperties.getProperty("default.lru.removal.time"));
		this.lruRemovalNumber = Integer.valueOf(cacheProperties.getProperty("default.lru.removal.count"));
		this.lruRemovalNumWhenFullInterval = Integer.valueOf(cacheProperties.getProperty("interval.but.full.lru.removal.count"));
		this.lruRemovalFlag = LRURemovalFlag.FULL; 
		refreshTrigger = new Timer("refreshTrigger");
		lruRemovalTrigger = new Timer("lruRemovalTrigger");
	}
	
	@Override
	public V get(final K key) { 
		V item = null;
		
		if(cache.containsKey(key)) {
			
			new Runnable() {
				
				@Override
				public void run() {
					// move the current node of the key to first node
					lruController.remove(key);
					lruController.addFirst(key);
				}
			}.run();
			
			
			// get the V item from the cache 
			item = cache.get(key);
		}
		
		return item;
	}
	
	@Override
	public synchronized void put(final K key, final V value) throws InvalidActivityException {
		
		if(!value.getClass().isInstance(Cacheable.class)) {
			throw new InvalidActivityException("You are putting a non-Cacheable into a Cache. Please dont do that again.");
		}
		
		if(this.lruRemovalFlag.equals(LRURemovalFlag.FULL)) {
			// check the capacity
			if(this.cache.size() >= this.capacity) {
				removeLRUItems();
			}
		}
		
		if(this.cache.size() >= this.capacity) {
			removeLRUItems(this.lruRemovalNumWhenFullInterval);
		}
		
		// add the new item into LRU Controller and Cache
		lruController.addFirst(key);
		cache.put(key, value);
	}

	@Override
	public Boolean isExisted(K key) {
		if(cache.containsKey(key)) {
			incrementHitCounts();
			return Boolean.TRUE;
		}
		
		incrementMissCounts();
		return Boolean.FALSE;
	}

	@Override
	public Double hitmiss() {
		if(hitCounts > 0) {
			if(missCounts > 0) {
				return Double.valueOf((hitCounts.doubleValue() / missCounts.doubleValue())*100D);
			} 
			
			return Double.valueOf(hitCounts.doubleValue()*100D);
		}
		
		return Double.valueOf(0D);
	}

	@Override
	public synchronized void refresh() {
		lruController = new LinkedList<K>();
		cache = new HashMap<K, V>();
	}

	@Override
	public void incrementHitCounts() {
		hitCounts++;
	}

	@Override
	public void incrementMissCounts() {
		missCounts++;
	}
	
	private void scheduleLRURemoval(Integer lruRemovalTime) {
		lruRemovalTrigger.schedule(new TimerTask() {
			
			@Override
			public void run() {
				removeLRUItems();
			}
		}, lruRemovalTime !=null ? 
				lruRemovalTime : 
				this.lruRemovalTime);
	}
	
	private void scheduleRefresh(Integer refreshTime) {
		refreshTrigger.schedule(new TimerTask() {
			
			@Override
			public void run() {
				refresh();
			}
		}, refreshTime != null ?
				refreshTime :
				this.refreshTime);
	}
	
	private void removeLRUItems() {
		K lruKey = null;
		List<K> lruRemovalList = new ArrayList<K>();
		for(int i = 0 ; i < lruRemovalNumber; i++) {
			lruKey = lruController.getLast();
			lruRemovalList.add(lruKey);
			lruController.removeLast();
		}
		
		// remove from cache
		for(int i = 0; i < lruRemovalList.size(); i++) {
			cache.remove(lruRemovalList.get(i));
		}
	}
	
	private void removeLRUItems(Integer numberOfItems) {
		K lruKey = null;
		List<K> lruRemovalList = new ArrayList<K>();
		for(int i = 0 ; i < numberOfItems; i++) {
			lruKey = lruController.getLast();
			lruRemovalList.add(lruKey);
			lruController.removeLast();
		}
		
		// remove from cache
		for(int i = 0; i < lruRemovalList.size(); i++) {
			cache.remove(lruRemovalList.get(i));
		}
	}

	public Long getHitCounts() {
		return hitCounts;
	}

	public void setHitCounts(Long hitCounts) {
		this.hitCounts = hitCounts;
	}

	public Long getMissCounts() {
		return missCounts;
	}

	public void setMissCounts(Long missCounts) {
		this.missCounts = missCounts;
	}

	public Map<K, V> getCache() {
		return cache;
	}

	public void setCache(Map<K, V> cache) {
		this.cache = cache;
	}
}
