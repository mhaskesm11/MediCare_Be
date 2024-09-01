package com.pharma.medicare.serviceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.BillingDetails;
import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.domain.PDFFileDetails;
import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.repository.BillingDetailRepository;
import com.pharma.medicare.repository.CustomerDetailsRepository;
import com.pharma.medicare.repository.PDFFileDataRepository;
import com.pharma.medicare.repository.ProductStockRepository;
import com.pharma.medicare.request.CustomerBillRequest;
import com.pharma.medicare.request.PdfSaveRequest;
import com.pharma.medicare.request.ProductSellingDetails;
import com.pharma.medicare.service.BillingService;
import com.pharma.medicare.utility.CommonUtil;

@Service
public class BillingServiceImpl implements BillingService {

	private Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	ProductStockRepository productStockRepository;
	
	@Autowired
	BillingDetailRepository billingDetailRepository;
	
	@Autowired
	PDFFileDataRepository pdfFileSaveRepository;

//	@Override
//	public Long getProductSale(Long value) {
//		LOGGER.info("Entry :: BillingServiceImpl :: getProductSale():" + value);
//		Long totalSale = 0L;
//
//		if (value == 1) {
//			totalSale = productBillingIdRepository.showTodaysSale();
//		} else if (value == 7) {
//			totalSale = productBillingIdRepository.showWeekSale();
//		} else if (value == 30) {
//			totalSale = productBillingIdRepository.showMonthSale();
//		}
//		LOGGER.info("Exit :: BillingServiceImpl :: getProductSale():" + totalSale);
//		return totalSale;
//	}	

	@Override
	public String addCustomerBillDetails(CustomerBillRequest customerBillRequest, String userName) {

		LOGGER.info("Entry :: BillingServiceImpl :: addCustomerDetails():" + customerBillRequest);
		BillingDetails billingDetails = new BillingDetails();
		String response = "";
		try {
			Optional<CustomerDetails> optional = customerDetailsRepository
					.findByCustomerName(customerBillRequest.getCustomerName().toLowerCase());
			if (optional.isPresent()) {
				billingDetails.setCustomerId(optional.get().getCustomerId());
			}

			Optional<BillingDetails> billDetails = billingDetailRepository
					.getBillingDetailByInvoiceNumber(customerBillRequest.getInvoiceNumber());
			if (billDetails.isPresent()) {
				billingDetails = billDetails.get();
				billingDetails.setUpdatedBy(userName);
			} else {
				BeanUtils.copyProperties(customerBillRequest, billingDetails);
				billingDetails.setIsActive(ServiceConstants.Y);
				billingDetails.setCreatedBy(userName);
			}
			response = ServiceConstants.CUSTOMER_BILL_ADDED_SUCESSFULLY;
			billingDetailRepository.save(billingDetails);
		} catch (Exception e) {
			response = ServiceConstants.CUSTOMER_BILL_NOT_ADDED;
		}
		LOGGER.info("Exit :: BillingServiceImpl :: addCustomerDetails():" + billingDetails);
		return response;

	}

	@Override
	public void modifiedProductStockAfterSelling(List<ProductSellingDetails> materialSellingDetails,String userName) {
		LOGGER.info("Entry :: BillingServiceImpl :: modifiedProductStockAfterSelling():" + materialSellingDetails);
		for(int i=0;i<materialSellingDetails.size();i++) {
			ProductStock stock=new ProductStock();
			Optional<ProductStock> product = productStockRepository.findByProductName(materialSellingDetails.get(i).getProductName());
			if(product.isPresent()) {
				BeanUtils.copyProperties(product.get(), stock);
				stock.setProductId(product.get().getProductId());
				stock.setQuantity(product.get().getQuantity()-materialSellingDetails.get(i).getQuantity());
				stock.setUpdatedBy(userName);
				productStockRepository.saveAndFlush(stock);
			}
		}
		
	}


	@Override
	public Long getLastInvoiceNumber() {
		List<String> allInvoiceNumber=billingDetailRepository.findAllInvoiceNumber();
		if(allInvoiceNumber!=null && allInvoiceNumber.size()!=0) {
			List<Long> convertedNumbers = allInvoiceNumber.stream()
	                .map(number ->  number.substring(3))
	                .map(number -> {
	                    try {
	                        return Long.parseLong(number);
	                    } catch (NumberFormatException e) {
	                        System.out.println("Cannot convert " + number.substring(3) + " to a number");
	                        return null;
	                    }
	                })
	                .filter(num -> num != null)
	                .collect(Collectors.toList());
	        
	        Long maxNumber = Collections.max(convertedNumbers);
	        return maxNumber;
		}
		return 0L;
			
	}

	@Override
	public String saveGeneratedPdfFile(PdfSaveRequest pdfSaveRequest, String userName) {

		String response = "";
		try {
			Optional<PDFFileDetails> optional = pdfFileSaveRepository
					.getPdfFileByInvoiceNumber(pdfSaveRequest.getInvoiceNumber());
			PDFFileDetails fileDetails = new PDFFileDetails();

			if (optional.isPresent()) {
				PDFFileDetails pdfFileDetails = optional.get();
				BeanUtils.copyProperties(pdfFileDetails, fileDetails);
				fileDetails.setUpdatedBy(userName);
				response = ServiceConstants.PDF_ALREADY_EXIST;
			} else {

				fileDetails.setBillingDate(Date.valueOf(LocalDate.now()));
				fileDetails.setFileName(pdfSaveRequest.getFileName());
				fileDetails.setInvoiceNumber(pdfSaveRequest.getInvoiceNumber());
				fileDetails.setCustomerName(pdfSaveRequest.getCustomerName());
				fileDetails.setPaidType(pdfSaveRequest.getPaidType());
				if (CommonUtil.isNotNull(pdfSaveRequest.getBase64String())) {
					byte[] pdfData = converBase64StringIntoByteArray(pdfSaveRequest.getBase64String());
					fileDetails.setPdfFileData(pdfData);
				}
				fileDetails.setCreatedBy(userName);
				response = ServiceConstants.PDF_SAVE_SUCESSFULLY;
			}
			fileDetails.setIsActive("Y");
			pdfFileSaveRepository.save(fileDetails);

		} catch (Exception e) {
			response = ServiceConstants.PDF_NOT_SAVED_EXIST;
		}
		System.out.println("response : "+response);
		return response;
	}

	private byte[] converBase64StringIntoByteArray(String base64String) {
		
		byte[] pdfFile = Base64.getDecoder().decode(base64String);
		
		return pdfFile;
		
	}
	
}
