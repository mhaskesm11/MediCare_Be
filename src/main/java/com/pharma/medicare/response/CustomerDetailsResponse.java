package com.pharma.medicare.response;

import java.sql.Date;

public interface CustomerDetailsResponse {
	
	Long getCustomerId();
	String getCustomerName();
	String getContactNumber();
	Date getCustomerAddedDate();
	String getCustomerAddress();
	

}
