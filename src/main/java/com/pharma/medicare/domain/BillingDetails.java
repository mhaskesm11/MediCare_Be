package com.pharma.medicare.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "txn_billing_details")
public class BillingDetails extends AuditEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "billing_id")
	private Long billingId;
	
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "paid_amount")
	private Long paidAmount;

	@Column(name = "billing_date")
	private Date billingDate;

	@Column(name = "paid_type")
	private String paidType;

	public Long getBillingId() {
		return billingId;
	}

	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}

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

	public Long getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Long paidAmount) {
		this.paidAmount = paidAmount;
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

	@Override
	public String toString() {
		return "BillingDetails [billingId=" + billingId + ", customerId=" + customerId + ", invoiceNumber="
				+ invoiceNumber + ", paidAmount=" + paidAmount + ", billingDate=" + billingDate + ", paidType="
				+ paidType + "]";
	}

	

}
