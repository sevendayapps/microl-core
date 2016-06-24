package com.microl.core.cache;

import java.util.HashMap;
import java.util.Map;

import com.microl.core.LinkedNode;

public class LRUBasedCache<K, Cacheable> extends ObjectCache<K, Cacheable>{
	
	private Map<K, LinkedNode<K>> lruController = new HashMap<K, LinkedNode<K>>();
	private LinkedNode<K> head;
	private LinkedNode<K> end;
	
	private Integer capacity;
	private Map<K, Cacheable> cache;
	
	public LRUBasedCache(Integer capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public Cacheable get(final K key) {
		Cacheable item = null;
		if(cache.containsKey(key)) {
			super.setHitCounts(super.getHitCounts() + 1);
			LinkedNode<K> node = lruController.get(key);
			item = cache.get(key);
			moveNodeToHead(node);
		} else {
			super.setMissCounts(super.getMissCounts() + 1);
		}
		
		return item;
	}
	
	@Override
	public synchronized void put(final K key, final Cacheable value) {
		LinkedNode<K> newNode = new LinkedNode<K>(key);
		
		if(cache.size() >= capacity) {
			K lruKey = (K) end.getValue();
			cache.remove(lruKey);
			remove(end);
		} 

		setHead(newNode);
		cache.put(key, value);
	}
	
	private synchronized void remove(LinkedNode<K> node) {
		// if current node have a prev node, set next node of previous node to next node of current code
		// if current node is the first node, set head is the next node of current code.
		if(node.getPrev() != null) {
			node.getPrev().setNext(node.getNext());
		} else {
			head = node.getNext();
		}
		
		// if current node have a next node, set previous of the next node of current node is previous of current node
		// if current node is the last node, set end is the previous node of current node
		if(node.getNext() != null) {
			node.getNext().setPrev(node.getPrev());
		} else {
			end = node.getPrev();
		}
	}
	
	private synchronized void setHead(LinkedNode<K> node) {
		node.setNext(head);
		node.setPrev(null);
		
		if(head != null) {
			head.setPrev(node);
		}
		
		head = node;
		
		if(end == null) {
			end = head;
		}
	}
	
	private void moveNodeToHead(LinkedNode<K> node) {
		remove(node);
		setHead(node);
	}
}
