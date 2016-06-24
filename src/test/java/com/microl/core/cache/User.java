package com.microl.core.cache;

public class User implements Cacheable{
	private Long id;
	private String username;
	private String name;
	private String email;
	private UserProfile profile;
	
	public User(Long id, String username, String name, String email, UserProfile profile) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.profile = profile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
}
