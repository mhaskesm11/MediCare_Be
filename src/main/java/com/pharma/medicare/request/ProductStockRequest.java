package com.pharma.medicare.request;

public class ProductStockRequest {

	private String productName;
	private Long quantity;
	private Double price;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	@Override
	public String toString() {
		return "ProductStockRequest [productName=" + productName + ", quantity=" + quantity + ", price=" + price + "]";
	}
	
}
