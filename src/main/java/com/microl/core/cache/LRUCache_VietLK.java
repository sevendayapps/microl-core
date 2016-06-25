package com.microl.core.cache;

import java.util.HashMap;
import java.util.Map;

import com.microl.core.LinkedNode;

public class LRUCache_VietLK<K, Cacheable> extends ObjectCache<K, Cacheable>{
	
	// the linked list
	private Map<K, LinkedNode<K>> lruController = new HashMap<K, LinkedNode<K>>();
	// first node of the linked list
	private LinkedNode<K> firstNode;
	// end node of the linked list
	private LinkedNode<K> lastNode;
	
	// the maximum size of the cache
	private Integer capacity;
	// the cache
	private Map<K, Cacheable> cache;
	
	public LRUCache_VietLK(Integer capacity) {
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
			K lruKey = (K) lastNode.getValue();
			cache.remove(lruKey);
			remove(lastNode);
		} 

		setHead(newNode);
		cache.put(key, value);
	}
	
	private synchronized void remove(LinkedNode<K> node) {
		// example : node 1 -> node 2 -> node 3
		// if current node is node 2 -> set next of node 1 is node 3 and set previous of node 3 is node 1.
		// if current node is node 1 (first node) -> set first node to node 2 and set previous of node 2 is null. 
		// if current node is node 3 (last node) -> set last node is node 2 and set next of node 2 is null.

		if(node.getPrev() != null) {
			node.getPrev().setNext(node.getNext());
		} else {
			firstNode = node.getNext();
		}
		
		if(node.getNext() != null) {
			node.getNext().setPrev(node.getPrev());
		} else {
			lastNode = node.getPrev();
		}
	}
	
	private synchronized void setHead(LinkedNode<K> node) {
		node.setNext(firstNode);
		node.setPrev(null);
		
		if(firstNode != null) {
			firstNode.setPrev(node);
		}
		
		firstNode = node;
		
		if(lastNode == null) {
			lastNode = lastNode;
		}
	}
	
	private void moveNodeToHead(LinkedNode<K> node) {
		remove(node);
		setHead(node);
	}
}
