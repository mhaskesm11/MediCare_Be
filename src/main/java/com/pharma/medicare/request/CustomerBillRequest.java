package com.pharma.medicare.request;

import java.sql.Date;

public class CustomerBillRequest {
	
	private Long customerId;
	private String invoiceNumber;
	private Date billingDate;
	private String paidType;
	private Long paidAmount;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
		return "CustomerBillRequest [customerId=" + customerId + ", invoiceNumber=" + invoiceNumber + ", billingDate="
				+ billingDate + ", paidType=" + paidType + ", paidAmount=" + paidAmount + "]";
	}
	
}
