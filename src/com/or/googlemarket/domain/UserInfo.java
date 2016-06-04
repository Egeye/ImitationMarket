package com.or.googlemarket.domain;

/**
 * 用户信息
 * @author Octavio
 *
 */
public class UserInfo {
	private String name;
	private String url;
	private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserInfo(String name, String url, String email) {
		super();
		this.name = name;
		this.url = url;
		this.email = email;
	}
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", url=" + url + ", email=" + email + "]";
	}
	
}
