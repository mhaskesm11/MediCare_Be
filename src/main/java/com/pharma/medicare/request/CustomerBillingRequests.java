package com.pharma.medicare.request;

import java.util.List;

public class CustomerBillingRequests {
	
	private String customerName;
	private String mobileNumber;
	private String invoiceNumber;
	private String address;
	private String invoiceDate;
	private String amountType;
	private String totalAmountInWord;
	private String totalAmount;
	private List<ProductSellingDetails> materialSellingDetails;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getTotalAmountInWord() {
		return totalAmountInWord;
	}
	public void setTotalAmountInWord(String totalAmountInWord) {
		this.totalAmountInWord = totalAmountInWord;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<ProductSellingDetails> getMaterialSellingDetails() {
		return materialSellingDetails;
	}
	public void setMaterialSellingDetails(List<ProductSellingDetails> materialSellingDetails) {
		this.materialSellingDetails = materialSellingDetails;
	}
	@Override
	public String toString() {
		return "CustomerBillingRequests [customerName=" + customerName + ", mobileNumber=" + mobileNumber
				+ ", invoiceNumber=" + invoiceNumber + ", address=" + address + ", invoiceDate=" + invoiceDate
				+ ", amountType=" + amountType + ", totalAmountInWord=" + totalAmountInWord + ", totalAmount="
				+ totalAmount + ", materialSellingDetails=" + materialSellingDetails + "]";
	}
	
	
}
