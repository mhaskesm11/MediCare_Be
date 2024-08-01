package com.pharma.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.medicare.request.CustomerBillRequest;
import com.pharma.medicare.request.PdfSaveRequest;
import com.pharma.medicare.request.ProductSellingDetails;

@Service
public interface BillingService {

	String addCustomerBillDetails(CustomerBillRequest customerBillRequest,String UserName);

	void modifiedProductStockAfterSelling(List<ProductSellingDetails> materialSellingDetails, String userName);

	Long getLastInvoiceNumber();
	
	String saveGeneratedPdfFile(PdfSaveRequest pdfSaveRequest, String userName);

}
