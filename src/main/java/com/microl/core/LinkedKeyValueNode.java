package com.microl.core;

public class LinkedKeyValueNode<K, V> {
	private K key;
	private V value;
	private LinkedKeyValueNode<K, V> prev;
	private LinkedKeyValueNode<K, V> next;
	
	public LinkedKeyValueNode(final K key, final V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public LinkedKeyValueNode<K, V> getPrev() {
		return prev;
	}

	public void setPrev(LinkedKeyValueNode<K, V> prev) {
		this.prev = prev;
	}

	public LinkedKeyValueNode<K, V> getNext() {
		return next;
	}

	public void setNext(LinkedKeyValueNode<K, V> next) {
		this.next = next;
	}
}
