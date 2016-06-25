package com.microl.core.enumeration;

public enum LRURemovalFlag {
	FULL("full"), 
	INTERVAL("interval");
	
	private String removalFlag;

	private LRURemovalFlag(String removalFlag) {
		this.removalFlag = removalFlag;
	}

	public String getRemovalFlag() {
		return removalFlag;
	}

	public void setRemovalFlag(String removalFlag) {
		this.removalFlag = removalFlag;
	}
}
