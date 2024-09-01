package com.pharma.medicare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.domain.PDFFileDetails;
import com.pharma.medicare.request.CustomerBillSearchRequest;
import com.pharma.medicare.request.CustomerRequest;
import com.pharma.medicare.request.CustomerSearchRequest;
import com.pharma.medicare.response.CustomerAllDetailsResponse;
import com.pharma.medicare.response.CustomerBillSearchResponse;
import com.pharma.medicare.response.CustomerDetailsResponse;
import com.pharma.medicare.response.CustomerResponse;
import com.pharma.medicare.service.CustomerDetailService;
import com.pharma.medicare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customer")
public class CustomerDetatilController extends BaseController {

	private Logger LOGGER = LoggerFactory.getLogger(CustomerDetatilController.class);

	@Autowired
	CustomerDetailService customerDetailService;

	@PostMapping("addcustomer")
	public String addCustomer(@RequestBody CustomerRequest customerRequest, HttpServletRequest request) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/addcustomer")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(customerRequest)));
		String response = "";

		try {
			String userName = getUserNameFromHeader(request);
			CustomerDetails optional = customerDetailService.addCustomerDetails(customerRequest, userName);
			if (CommonUtil.isNotNull(optional)) {
				response = ServiceConstants.CUSTOMER_ADDED_SUCESSFULLY;
			} else {
				response = ServiceConstants.CUSTOMER_NOT_ADDED;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}	

	@GetMapping("customer-all")
	public List<CustomerDetailsResponse> getAllCustomerDetail() {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/customer-all")));
		List<CustomerDetailsResponse> response = null;
		try {
			response = customerDetailService.getAllCustomerDetail();

		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// search customer billing details
	@PostMapping("search-sale")
	public CustomerBillSearchResponse searchAllUsers(@RequestBody CustomerBillSearchRequest customerBillSearchRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/search/sale")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(customerBillSearchRequest)));
		CustomerBillSearchResponse response = null;
		try {
			response = customerDetailService.searchAllCustomerSales(customerBillSearchRequest);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	@PostMapping("search-all")
	public CustomerResponse searchAllCustomer(@RequestBody CustomerSearchRequest customerSearchRequest, HttpServletRequest request) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/search-all")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(customerSearchRequest)));
		CustomerResponse response = new CustomerResponse();
		try {
			String userName = getUserNameFromHeader(request);
			response = customerDetailService.searchAllCustomerDetails(customerSearchRequest, userName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	@GetMapping("view-pdf{invoiceNumber}")
	public String viewPdfDataByUsingInvoiceNumber(@PathVariable String invoiceNumber) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1//customer/all")));
		String response = "";
		try {
			response = customerDetailService.viewPdfDataByUsingInvoiceNumber(invoiceNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// download customer bill pdf by invoice number
	@GetMapping("download/{invoiceNumber}")
	public ResponseEntity<byte[]> downloadCustomerBillPdfByInvoiceNumber(@PathVariable String invoiceNumber) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/download")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(invoiceNumber)));
		byte[] response = null;
		String fileName="";

		try {
			 PDFFileDetails pdfFileDetails = customerDetailService.downloadCustomerBillByInvoiceNumber(invoiceNumber);
			 fileName=pdfFileDetails.getFileName();
			 response=pdfFileDetails.getPdfFileData();

		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=\"" + fileName  + "\"");

		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return  new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	@DeleteMapping("customer-bill{invoiceNumber}")
	public String deleteCustomerBillDetails(@PathVariable String invoiceNumber) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/customer-bill")));
		String response = "";
		try {
			response = customerDetailService.deleteCustomerBillDetails(invoiceNumber);

		} catch (Exception e) {
			e.printStackTrace();
			response = ServiceConstants.PDF_FILE_IS_NOT_DELETED;
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	@GetMapping("getdetails{customerId}")
	public CustomerAllDetailsResponse getCustomerDetails(@PathVariable Long customerId) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/getdetails")));
		CustomerAllDetailsResponse response = null;
		try {
			response = customerDetailService.getAllCustomerDetailsById(customerId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	@DeleteMapping("delete{customerId}")
	public String deleteCustomerDetails(@PathVariable Long customerId, HttpServletRequest request) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/customer/delete")));
		String response = "";
		String userName = getUserNameFromHeader(request);
		try {
			response = customerDetailService.deleteCustomerDetailsById(customerId, userName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

}
