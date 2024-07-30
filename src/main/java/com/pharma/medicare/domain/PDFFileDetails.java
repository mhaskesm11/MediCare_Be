package com.pharma.medicare.domain;

import java.sql.Date;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "txn_pdf_data")
public class PDFFileDetails extends AuditEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pdf_file_Id")
	private Long pdfFileId;
	
	@Column(name = "customer_Name")
	private String customerName;
	
	@Column(name = "file_Name")
	private String fileName;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "billing_date")
	private Date billingDate;

	@Column(name = "paid_type")
	private String paidType;
	
	@Lob
	@Column(name = "pdf_file_data")
    private byte[] pdfFileData;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Long getPdfFileId() {
		return pdfFileId;
	}

	public void setPdfFileId(Long pdfFileId) {
		this.pdfFileId = pdfFileId;
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

	public byte[] getPdfFileData() {
		return pdfFileData;
	}

	public void setPdfFileData(byte[] pdfFileData) {
		this.pdfFileData = pdfFileData;
	}

	@Override
	public String toString() {
		return "PDFFileDetails [pdfFileId=" + pdfFileId + ", customerName=" + customerName + ", fileName=" + fileName
				+ ", invoiceNumber=" + invoiceNumber + ", billingDate=" + billingDate + ", paidType=" + paidType
				+ ", pdfFileData=" + Arrays.toString(pdfFileData) + "]";
	}

}
