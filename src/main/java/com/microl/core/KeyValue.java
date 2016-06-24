package com.microl.core;

import javax.activity.InvalidActivityException;

public interface KeyValue<K, V> {
	Boolean isExisted(final K key);
	V get(final K key);
	void put(final K key, final V value) throws InvalidActivityException;
}
