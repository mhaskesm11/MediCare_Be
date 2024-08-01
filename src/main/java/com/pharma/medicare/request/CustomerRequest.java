package com.pharma.medicare.request;

import java.sql.Date;

public class CustomerRequest {
	
	private Long customerId;
	private String customerName;
	private String contactNumber;
	private Date customerAddedDate;
	private String customerAddress;
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Date getCustomerAddedDate() {
		return customerAddedDate;
	}
	public void setCustomerAddedDate(Date customerAddedDate) {
		this.customerAddedDate = customerAddedDate;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	@Override
	public String toString() {
		return "CustomerRequest [customerId=" + customerId + ", customerName=" + customerName + ", contactNumber="
				+ contactNumber + ", customerAddedDate=" + customerAddedDate + ", customerAddress=" + customerAddress
				+ "]";
	}

	

}
