package com.pharma.medicare.response;

import com.pharma.medicare.domain.CustomerDetails;

public class AddCustomerResponse {
	
	private String message;
	private CustomerDetails customerDetail;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public CustomerDetails getCustomerDetail() {
		return customerDetail;
	}
	public void setCustomerDetail(CustomerDetails customerDetail) {
		this.customerDetail = customerDetail;
	}
	@Override
	public String toString() {
		return "AddCustomerResponse [message=" + message + ", customerDetail=" + customerDetail + "]";
	}
	
}
