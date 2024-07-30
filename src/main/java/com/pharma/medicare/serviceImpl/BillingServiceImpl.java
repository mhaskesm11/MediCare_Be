package com.pharma.medicare.serviceImpl;

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
import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.repository.BillingDetailRepository;
import com.pharma.medicare.repository.CustomerDetailsRepository;
import com.pharma.medicare.repository.ProductStockRepository;
import com.pharma.medicare.request.CustomerBillRequest;
import com.pharma.medicare.request.ProductSellingDetails;
import com.pharma.medicare.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

	private Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	ProductStockRepository productStockRepository;
	
	@Autowired
	BillingDetailRepository billingDetailRepository;

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
	public String addCustomerBillDetails(CustomerBillRequest customerBillRequest,String userName) {

		LOGGER.info("Entry :: BillingServiceImpl :: addCustomerDetails():" + customerBillRequest);
		BillingDetails billingDetails = new BillingDetails();
		String response = "";
		try {
			Optional<CustomerDetails> optional = customerDetailsRepository
					.findByCustomerName(customerBillRequest.getCustomerName());
			BeanUtils.copyProperties(customerBillRequest, billingDetails);
			if (optional.isPresent()) {
				billingDetails.setCustomerId(optional.get().getCustomerId());
			}
			billingDetails.setIsActive(ServiceConstants.Y);
			billingDetails.setCreatedBy(userName);
			billingDetails.setUpdatedBy(userName);
			billingDetailRepository.save(billingDetails);
			response = ServiceConstants.CUSTOMER_ADDED_SUCESSFULLY;

		} catch (Exception e) {
			response = ServiceConstants.CUSTOMER_NOT_ADDED;
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

}
