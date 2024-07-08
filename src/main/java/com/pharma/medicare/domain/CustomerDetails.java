package com.pharma.medicare.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "txn_customer_details")
public class CustomerDetails extends AuditEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "customer_added_date")
	private Date customerAddedDate;

	@Column(name = "customer_address")
	private String customerAddress;

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

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Override
	public String toString() {
		return "CustomerDetails [customerId=" + customerId + ", customerName=" + customerName + ", contactNumber="
				+ contactNumber + ", customerAddedDate=" + customerAddedDate + ", customerAddress=" + customerAddress
				+ "]";
	}

	
}
