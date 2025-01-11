package com.pharma.medicare.response;

import com.pharma.medicare.domain.User;

public class UserModifiedResponse {
	
	private String message;
	private User user;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "UserModifiedResponse [message=" + message + ", user=" + user + "]";
	}

}
