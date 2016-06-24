package com.microl.core;

public class LinkedNode<V> {
	private V value;
	private LinkedNode<V> prev;
	private LinkedNode<V> next;
	
	public LinkedNode(V value) {
		this.value = value;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public LinkedNode<V> getPrev() {
		return prev;
	}

	public void setPrev(LinkedNode<V> prev) {
		this.prev = prev;
	}

	public LinkedNode<V> getNext() {
		return next;
	}

	public void setNext(LinkedNode<V> next) {
		this.next = next;
	}
}
