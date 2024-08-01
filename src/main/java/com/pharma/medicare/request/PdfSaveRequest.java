package com.pharma.medicare.request;

import java.sql.Date;

public class PdfSaveRequest {
	
	private Date billingDate ;
    private String customerName ;
    private String fileName ;
    private String invoiceNumber ;
    private String  paidType ;
    private String base64String;
	public Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPaidType() {
		return paidType;
	}
	public void setPaidType(String paidType) {
		this.paidType = paidType;
	}
	public String getBase64String() {
		return base64String;
	}
	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}
	@Override
	public String toString() {
		return "PdfSaveRequest [billingDate=" + billingDate + ", customerName=" + customerName + ", fileName="
				+ fileName + ", invoiceNumber=" + invoiceNumber + ", paidType=" + paidType + ", base64String="
				+ base64String + "]";
	}
	    
}
