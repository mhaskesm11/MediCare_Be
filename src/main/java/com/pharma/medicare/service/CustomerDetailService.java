package com.pharma.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.domain.PDFFileDetails;
import com.pharma.medicare.request.CustomerBillSearchRequest;
import com.pharma.medicare.request.CustomerRequest;
import com.pharma.medicare.response.CustomerBillSearchResponse;
import com.pharma.medicare.response.CustomerDetailsResponse;

@Service
public interface CustomerDetailService {

	List<CustomerDetailsResponse> getAllCustomerDetail();

	CustomerDetails addCustomerDetails(CustomerRequest customerRequest, String userName);

	CustomerBillSearchResponse searchAllCustomerSales(CustomerBillSearchRequest customerBillSearchRequest);

	String viewPdfDataByUsingInvoiceNumber(String invoiceNumber);

	String deleteCustomerBillDetails(String invoiceNumber);

	PDFFileDetails downloadCustomerBillByInvoiceNumber(String invoiceNumber);

}
