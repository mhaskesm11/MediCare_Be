package com.pharma.medicare.request;

public class UserSignupRequest {
	
	private String userName;
	private String password;
	private String mode;
	private String fullName;
	private String contactNumber;
	private String email;
	private boolean approved;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	@Override
	public String toString() {
		return "UserSignupRequest [userName=" + userName + ", password=" + password + ", mode=" + mode + ", fullName="
				+ fullName + ", contactNumber=" + contactNumber + ", email=" + email + ", approved=" + approved + "]";
	}
	
	
}
