package com.pharma.medicare.response;

public class UserLoginResponse {
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserLoginResponse [status=" + status + "]";
	}
	
	

}
