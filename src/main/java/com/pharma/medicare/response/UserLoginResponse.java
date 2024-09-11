package com.pharma.medicare.response;

public class UserLoginResponse {
	
	private String status;
	private String mode;
	private UserResponse user;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserLoginResponse [status=" + status + ", mode=" + mode + ", user=" + user + "]";
	}
}
