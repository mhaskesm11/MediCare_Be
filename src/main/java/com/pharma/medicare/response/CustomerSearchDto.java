package com.pharma.medicare.response;

import java.sql.Date;

public class CustomerSearchDto {
	
	private Long customerId;
	private String customerName;
	private String customerAddress;
	private String contactNumber;
	private Date customerAddedDate;
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
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
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
	
	@Override
	public String toString() {
		return "CustomerSearchDto [customerId=" + customerId + ", customerName=" + customerName + ", customerAddress="
				+ customerAddress + ", contactNumber=" + contactNumber + ", customerAddedDate=" + customerAddedDate
				+ "]";
	}
	

}
