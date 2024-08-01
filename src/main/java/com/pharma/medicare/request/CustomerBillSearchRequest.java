package com.pharma.medicare.request;

import java.sql.Date;

public class CustomerBillSearchRequest {
	
	private String customerName;
	private String address;
	private String invoiceNumber;
	private String amountType;
	private Date billingDate ;
	private String saleType;
	private int limit;
	private int page;
	private String orderBy;
	private String orderDirection;
	private static final String DEFAULT_SORT = "Desc";
	private static final int DEFAULT_LIMIT = 10;
	private static final int DEFAULT_PAGE = 1;

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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public int getLimit() {
		if(limit==0) {
			limit=DEFAULT_LIMIT;
		}
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		if(page==0) {
			page=DEFAULT_PAGE;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOrderBy() {
		if(orderBy==null || orderBy.isEmpty()) {
			orderBy="user_id";
		}
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDirection() {
		if(orderDirection==null || orderDirection.isEmpty()) {
			orderDirection=DEFAULT_SORT;
		}
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public static String getDefaultSort() {
		return DEFAULT_SORT;
	}

	public static int getDefaultLimit() {
		return DEFAULT_LIMIT;
	}

	public static int getDefaultPage() {
		return DEFAULT_PAGE;
	}

	@Override
	public String toString() {
		return "CustomerBillSearchRequest [customerName=" + customerName + ", address=" + address + ", invoiceNumber="
				+ invoiceNumber + ", amountType=" + amountType + ", billingDate=" + billingDate + ", saleType="
				+ saleType + ", limit=" + limit + ", page=" + page + ", orderBy=" + orderBy + ", orderDirection="
				+ orderDirection + "]";
	}
	
}
