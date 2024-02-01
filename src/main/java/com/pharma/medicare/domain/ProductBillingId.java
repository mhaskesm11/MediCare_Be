package com.pharma.medicare.domain;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_bill_id")
public class ProductBillingId {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_bill_id")
	private Long productBillId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "ref")
	private String ref;
	
	@Column(name = "total")
	private Double total;
	
	@Column(name = "date_time")
	private String date_time;

	public Long getProductBillId() {
		return productBillId;
	}

	public void setProductBillId(Long productBillId) {
		this.productBillId = productBillId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	@Override
	public String toString() {
		return "ProductBillingId [productBillId=" + productBillId + ", productName=" + productName + ", ref=" + ref
				+ ", total=" + total + ", date_time=" + date_time + "]";
	}
	
}
