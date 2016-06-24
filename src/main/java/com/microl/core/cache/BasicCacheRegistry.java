package com.microl.core.cache;

import java.util.Map;
import java.util.TreeMap;

import javax.activity.InvalidActivityException;

import com.microl.core.Registry;

public class BasicCacheRegistry<String, Cache> implements Registry<String, Cache> {
	
	private Map<String, Cache> registry = new TreeMap<String, Cache>();

	@Override
	public Boolean isExisted(String key) {
		if(registry.containsKey(key)) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	@Override
	public Cache get(String key) {
		return registry.get(key);
	}

	@Override
	public void put(String key, Cache value) throws InvalidActivityException{
		// if a cache key is existed in registry, please throws the exception to inform the invoker that is an invalid 
		// action
		if(isExisted(key)) {
			throw new InvalidActivityException("The cache keyed " + key + " is existing in registry. Something maybe get wrong ?");
		}
		
		registry.put(key, value);
	}
	
}
