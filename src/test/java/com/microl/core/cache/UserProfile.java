package com.microl.core.cache;

public class UserProfile implements Cacheable {
	private Long id;
	private Boolean gender;
	private String introduction;
	
	public UserProfile(Long id, Boolean gender, String introduction) {
		super();
		this.id = id;
		this.gender = gender;
		this.introduction = introduction;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	
}
