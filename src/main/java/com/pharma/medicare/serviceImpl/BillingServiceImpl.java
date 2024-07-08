package com.pharma.medicare.serviceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.BillingDetails;
import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.domain.ProductBillingId;
import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.repository.BillingDetailRepository;
import com.pharma.medicare.repository.CustomerDetailsRepository;
import com.pharma.medicare.repository.ProductBillingIdRepository;
import com.pharma.medicare.repository.ProductBillingRepository;
import com.pharma.medicare.repository.ProductStockRepository;
import com.pharma.medicare.request.BillingDataRequest;
import com.pharma.medicare.request.CustomerBillRequest;
import com.pharma.medicare.request.CustomerRequest;
import com.pharma.medicare.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

	private Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);

	@Autowired
	ProductBillingIdRepository productBillingIdRepository;

	@Autowired
	ProductBillingRepository productBillingRepository;

	@Autowired
	ProductStockRepository productStockRepository;
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	BillingDetailRepository billingDetailRepository;

	@Override
	public Long getProductSale(Long value) {
		LOGGER.info("Entry :: BillingServiceImpl :: getProductSale():" + value);
		Long totalSale = 0L;

		if (value == 1) {
			totalSale = productBillingIdRepository.showTodaysSale();
		} else if (value == 7) {
			totalSale = productBillingIdRepository.showWeekSale();
		} else if (value == 30) {
			totalSale = productBillingIdRepository.showMonthSale();
		}
		LOGGER.info("Exit :: BillingServiceImpl :: getProductSale():" + totalSale);
		return totalSale;
	}

	@Override
	public Long submitBillingDetails(BillingDataRequest billingDataRequest) {
		LOGGER.info("Entry :: BillingServiceImpl :: submitBillingDetails():" + billingDataRequest);
		Long response = 0L;
		try {
			ProductBillingId productBillingId = new ProductBillingId();
			BeanUtils.copyProperties(billingDataRequest.getUserData(), productBillingId);
			productBillingId = productBillingIdRepository.save(productBillingId);
			Long productBillId = productBillingId.getProductBillId();
			List<ProductStock> itemList = billingDataRequest.getItemData();
			for (ProductStock item : itemList) {
				productBillingRepository.insertBillingItem(productBillId, item.getProductName(), item.getQuantity(),
						item.getPrice());
				productStockRepository.minusItemsFromStocks(item.getQuantity(), item.getProductName());
			}
			response = 1L;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit :: BillingServiceImpl :: submitBillingDetails():" + response);
		return response;
	}

	@Override
	public Double getProductPrice(String productName) {
		LOGGER.info("Entry :: BillingServiceImpl :: getProductPrice():" + productName);
		Double response = 0d;
		Optional<ProductStock> optional = productStockRepository.findByProductName(productName);
		if (optional.isPresent()) {
			response = optional.get().getPrice();
		}
		LOGGER.info("Exit :: BillingServiceImpl :: getProductPrice():" + response);
		return response;
	}

	@Override
	public CustomerDetails addCustomerDetails(CustomerRequest customerRequest) {
		
		LOGGER.info("Entry :: BillingServiceImpl :: addCustomerDetails():" + customerRequest);
		CustomerDetails customerDetails=new CustomerDetails();
		Optional<CustomerDetails> optional = customerDetailsRepository.findByCustomerName(customerRequest.getCustomerName());
		if (optional.isPresent()) {
			customerDetails=optional.get();			
		}
		customerDetails.setCustomerAddedDate(Date.valueOf(LocalDate.now()));
		customerDetails.setIsActive(ServiceConstants.Y);
		BeanUtils.copyProperties(customerRequest, customerDetails);
		customerDetailsRepository.save(customerDetails);
		LOGGER.info("Exit :: BillingServiceImpl :: addCustomerDetails():" +customerDetails);
		return customerDetails;		
	}

	@Override
	public void addCustomerBillDetails(CustomerBillRequest customerBillRequest) {
		
		LOGGER.info("Entry :: BillingServiceImpl :: addCustomerDetails():" + customerBillRequest);
		BillingDetails billingDetails=new BillingDetails();		
		Optional<CustomerDetails> optional = customerDetailsRepository.findByCustomerName(customerBillRequest.getCustomerName());
		billingDetails.setCustomerId(optional.get().getCustomerId());
		billingDetails.setIsActive(ServiceConstants.Y);
		BeanUtils.copyProperties(customerBillRequest, billingDetails);
		billingDetailRepository.save(billingDetails);
		LOGGER.info("Exit :: BillingServiceImpl :: addCustomerDetails():" +billingDetails);
			}

}
