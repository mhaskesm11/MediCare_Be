package com.pharma.medicare.response;

public class PdfFileDataResponse {
	
	private String fileName;
	private String invoiceNumber;
	private Long PaidAmount;
	
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
	public Long getPaidAmount() {
		return PaidAmount;
	}
	public void setPaidAmount(Long paidAmount) {
		PaidAmount = paidAmount;
	}
	@Override
	public String toString() {
		return "PdfFileInterFace [fileName=" + fileName + ", invoiceNumber=" + invoiceNumber + ", PaidAmount="
				+ PaidAmount + "]";
	}
	

}
