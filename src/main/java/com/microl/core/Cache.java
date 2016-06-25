package com.microl.core;

import com.microl.core.KeyValue;

public interface Cache<K, Cacheable> extends KeyValue<K, Cacheable> {
	Double hitmiss();
	void refresh();
	void incrementHitCounts();
	void incrementMissCounts();
}
