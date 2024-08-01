package com.pharma.medicare.request;

import java.sql.Date;

public class CustomerBillRequest {
	
	private String customerName;
	private String invoiceNumber;
	private Date billingDate;
	private String paidType;
	private Long paidAmount;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	public String getPaidType() {
		return paidType;
	}
	public void setPaidType(String paidType) {
		this.paidType = paidType;
	}
	public Long getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Long paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Override
	public String toString() {
		return "CustomerBillRequest [customerName=" + customerName + ", invoiceNumber=" + invoiceNumber
				+ ", billingDate=" + billingDate + ", paidType=" + paidType + ", paidAmount=" + paidAmount + "]";
	}
	
	

}
