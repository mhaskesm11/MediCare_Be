package com.pharma.medicare.request;

import java.sql.Date;

public class ProductSellingDetails {
	
	private String productName;
	private String companyName;
	private Long quantity;
	private Double price;
	private Date expDate;
	private Double total;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "ProductSellingDetails [productName=" + productName + ", companyName=" + companyName + ", quantity="
				+ quantity + ", price=" + price + ", expDate=" + expDate + ", total=" + total + "]";
	}
	
}
