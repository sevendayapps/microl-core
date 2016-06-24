package com.microl.core.cache;

import java.util.Map;
import java.util.TreeMap;

import javax.activity.InvalidActivityException;

import com.microl.core.Cache;

public class ObjectCache<K, Cacheable> implements Cache<K, Cacheable>{
	
	private Long hitCounts;
	private Long missCounts;
	
	private Map<K, Cacheable> cache = new TreeMap<K, Cacheable>();
	
	public ObjectCache() {
		hitCounts = 0L;
		missCounts = 0L;
	}

	@Override
	public Boolean isExisted(K key) {
		if(cache.containsKey(key)) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	@Override
	public Cacheable get(K key) {
		if(isExisted(key)) {
			hitCounts++;
			return cache.get(key);
		}
		
		missCounts++;
		return null;
	}

	@Override
	public void put(K key, Cacheable value) throws InvalidActivityException {
		missCounts++;
		cache.put(key, value);
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

	public Map<K, Cacheable> getCache() {
		return cache;
	}

	public void setCache(Map<K, Cacheable> cache) {
		this.cache = cache;
	}
}
