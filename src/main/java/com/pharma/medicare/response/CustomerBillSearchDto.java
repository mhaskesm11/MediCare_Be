package com.pharma.medicare.response;

public class CustomerBillSearchDto {
	
	private Long customerId;
	private String customerName;
	private String address;
	private String contactNumber;
	private String invoiceNumber;
	private String paidAmount;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Override
	public String toString() {
		return "CustomerBillSearchDto [customerId=" + customerId + ", customerName=" + customerName + ", address="
				+ address + ", contactNumber=" + contactNumber + ", invoiceNumber=" + invoiceNumber + ", paidAmount="
				+ paidAmount + "]";
	}
	
}
