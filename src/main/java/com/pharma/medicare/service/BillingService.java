package com.pharma.medicare.service;

import org.springframework.stereotype.Service;

import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.request.BillingDataRequest;
import com.pharma.medicare.request.CustomerBillRequest;
import com.pharma.medicare.request.CustomerRequest;

@Service
public interface BillingService {

	Long getProductSale(Long value);

	Long submitBillingDetails(BillingDataRequest billingDataRequest);

	Double getProductPrice(String productName);

	CustomerDetails addCustomerDetails(CustomerRequest customerRequest);

	void addCustomerBillDetails(CustomerBillRequest customerBillRequest);

}
