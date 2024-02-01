package com.pharma.medicare.request;

public class UserLoginRequest {
	
	private String userName;
	private String password;
	private String mode;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "UserRequest [userName=" + userName + ", password=" + password + ", mode=" + mode + "]";
	}
	
	
}
