package com.microl.core;

import java.util.Map;

import com.microl.core.KeyValue;

public interface Cache<K, Cacheable> extends KeyValue<K, Cacheable> {
	Double hitmiss();
}
