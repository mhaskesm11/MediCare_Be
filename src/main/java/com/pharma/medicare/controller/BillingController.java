package com.pharma.medicare.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.request.CustomerBillRequest;
import com.pharma.medicare.request.CustomerBillingRequests;
import com.pharma.medicare.request.PdfSaveRequest;
import com.pharma.medicare.service.BillingService;
import com.pharma.medicare.utility.CommonUtil;
import com.pharma.medicare.utility.PdfBillGenerator;

@RestController
@CrossOrigin
@RequestMapping("api/v1/bill")
public class BillingController extends BaseController {

	private Logger LOGGER = LoggerFactory.getLogger(BillingController.class);

	@Autowired
	BillingService billingService;
	
	@Autowired(required = true)
	PdfBillGenerator billGenerator;

	
	@GetMapping("last-invoice-number")
	public Long getLastInvoiceNumber() {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/last-invoice-number")));
		Long response=0L;
		try {
			response= billingService.getLastInvoiceNumber();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	@PostMapping("addcustomerbill")
	public String addCustomerBillingDetail(@RequestBody CustomerBillRequest customerBillRequest, HttpServletRequest request) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/addcustomerbill")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(customerBillRequest)));
		String response="";
		String userName = getUserNameFromHeader(request);
		try {
			response = billingService.addCustomerBillDetails(customerBillRequest,userName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
//	this controller are used for to generate the pdf.
	@PostMapping(value = "/generate-pdf")
	public ResponseEntity<String> generatePdf(@RequestBody CustomerBillingRequests customerBillingRequests,
			HttpServletRequest request) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/generate-pdf")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(customerBillingRequests)));

		String userName = getUserNameFromHeader(request);
		if (CommonUtil.isNotNull(customerBillingRequests.getMaterialSellingDetails())
				&& CommonUtil.isNotNull(customerBillingRequests.getCustomerName())) {
			billingService.modifiedProductStockAfterSelling(customerBillingRequests.getMaterialSellingDetails(),
					userName);
		}

		String base64String = billGenerator.generatePdf(customerBillingRequests, userName);
		String pdfFileName = billGenerator.createPdfName(customerBillingRequests);
		System.out.println(pdfFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=\"" + pdfFileName + "\"");
		return new ResponseEntity<>(base64String, headers, HttpStatus.OK);
	}
	
	@PostMapping("save-pdf")
	public String savePdfFileDetails(@RequestBody PdfSaveRequest pdfSaveRequest, HttpServletRequest request) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/save-pdf")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(pdfSaveRequest)));
		String response="";
		String userName = getUserNameFromHeader(request);
		try {
			response = billingService.saveGeneratedPdfFile(pdfSaveRequest,userName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	

}
