package com.pharma.medicare.response;

public class UserLoginResponse {
	
	private String status;
	private UserResponse user;
	private AuthenticationResponse response;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	public AuthenticationResponse getResponse() {
		return response;
	}
	public void setResponse(AuthenticationResponse response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "UserLoginResponse [status=" + status + ", user=" + user + ", response=" + response + "]";
	}
	
}
