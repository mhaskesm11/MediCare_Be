package com.pharma.medicare.service;

import org.springframework.stereotype.Service;

import com.pharma.medicare.request.BillingDataRequest;

@Service
public interface BillingService {

	Long getProductSale(Long value);

	Long submitBillingDetails(BillingDataRequest billingDataRequest);

	Double getProductPrice(String productName);

}
