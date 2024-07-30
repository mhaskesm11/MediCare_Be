package com.pharma.medicare.serviceImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.repository.CustomerDetailsRepository;
import com.pharma.medicare.repository.PDFFileDataRepository;
import com.pharma.medicare.request.CustomerBillSearchRequest;
import com.pharma.medicare.request.CustomerRequest;
import com.pharma.medicare.response.CustomerBillSearchDto;
import com.pharma.medicare.response.CustomerBillSearchResponse;
import com.pharma.medicare.response.CustomerDetailsResponse;
import com.pharma.medicare.service.CustomerDetailService;
import com.pharma.medicare.utility.CommonUtil;
import com.pharma.medicare.utility.PdfBillGenerator;

@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {
	
	private Logger LOGGER = LoggerFactory.getLogger(CustomerDetailServiceImpl.class);
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PDFFileDataRepository pdfFileDataRepository;
	
	@Override
	public CustomerDetails addCustomerDetails(CustomerRequest customerRequest, String userName) {
		
		LOGGER.info("Entry :: BillingServiceImpl :: addCustomerDetails():" + customerRequest);
		CustomerDetails customerDetails=new CustomerDetails();
		
			if(customerRequest.getCustomerId()==0) {
				
				Optional<CustomerDetails> optional = customerDetailsRepository.findByCustomerName(customerRequest.getCustomerName());
				if(optional.isPresent()) {
					CustomerDetails	customer=optional.get();	
						if(customer.getCustomerAddress().equals(customerRequest.getCustomerAddress())) {
							if(customer.getContactNumber().equals(customerRequest.getContactNumber())) {
								customer.setUpdatedBy(userName);
								customerDetails=customer;
							}
						}
						customerRequest.setCustomerId(customerDetails.getCustomerId());
				}else {
					BeanUtils.copyProperties(customerRequest, customerDetails);
					customerDetails.setIsActive("Y");
					customerDetails.setCreatedBy(userName);
					customerDetails.setCustomerAddedDate(Date.valueOf(LocalDate.now()));
				}
				
			}else {
				
				Optional<CustomerDetails> optional = customerDetailsRepository.findById(customerRequest.getCustomerId());
				if(optional.isPresent()) {
						customerDetails=optional.get();	
						BeanUtils.copyProperties(customerRequest, customerDetails);
						customerDetails.setUpdatedBy(userName);
				}
			}
		customerDetails.setIsActive(ServiceConstants.Y);
		customerDetails.setCreatedBy(userName);
		customerDetailsRepository.save(customerDetails);
		LOGGER.info("Exit :: BillingServiceImpl :: addCustomerDetails():" +customerDetails);
		return customerDetails;		
	}
	
	@Override
	public List<CustomerDetailsResponse> getAllCustomerDetail() {
		LOGGER.info("Entry :: BillingServiceImpl :: getAllCustomerDetail():" );
		List<CustomerDetailsResponse> allCustomerDeatils = customerDetailsRepository.findAllCustomerDetails();
		
		return allCustomerDeatils;
	}

	
	String getDetailQuery="SELECT tcd.customer_id, tcd.customer_name,tcd.customer_address,tcd.contact_number,tbd.invoice_number, "
			+ "tbd.paid_amount FROM txn_customer_details tcd join txn_billing_details tbd on tcd.customer_id=tbd.customer_id ";
	String getCount="SELECT count(*) "
			+ " FROM txn_customer_details tcd join txn_billing_details tbd on tcd.customer_id=tbd.customer_id";
	
	@Override
	public CustomerBillSearchResponse searchAllCustomerSales(CustomerBillSearchRequest customerBillSearchRequest) {
		LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():" +customerBillSearchRequest);
		String queryResponse="";
		String countResponse="";
		String offSetQuery="";
		StringBuilder stringBuilder=new StringBuilder();
		CustomerBillSearchResponse customerBillSearchResponse=new CustomerBillSearchResponse();
		
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tcd.customer_name", customerBillSearchRequest.getCustomerName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tcd.customer_address", customerBillSearchRequest.getAddress());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tbd.invoice_number", customerBillSearchRequest.getInvoiceNumber());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tbd.paid_type", customerBillSearchRequest.getAmountType());
//		CommonUtil.checkAppendConditionForStringField(stringBuilder, "", customerBillSearchRequest.getSaleType());
		CommonUtil.checkAppendConditionForDateField(stringBuilder, " tbd.billing_date", customerBillSearchRequest.getBillingDate());
		int pagecount = customerBillSearchRequest.getPage() - 1;
		int offset = pagecount * customerBillSearchRequest.getLimit();
		
		if (!customerBillSearchRequest.getOrderBy().isEmpty() && 
			 !customerBillSearchRequest.getOrderDirection().isEmpty())
		{
			if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("customerName"))
			{
				offSetQuery = " ORDER BY tcd.customer_name " + customerBillSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerBillSearchRequest.getLimit() ;
			} 
			else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("address"))
			{
				offSetQuery = " ORDER BY tcd.customer_address " + customerBillSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerBillSearchRequest.getLimit() ;
			} 
			else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("amountType"))
			{
				offSetQuery = " ORDER BY tbd.paid_type " + customerBillSearchRequest.getOrderDirection() + " LIMIT " + offset
						+ " , " + customerBillSearchRequest.getLimit() ;
			} 
			
			else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("invoiceNumber")) 
			{
				offSetQuery = " ORDER BY tbd.invoice_number " + customerBillSearchRequest.getOrderDirection() + " LIMIT " + offset
						+ " , " + customerBillSearchRequest.getLimit() ;
			} 
			else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("paidAmount"))
			{
				offSetQuery = " ORDER BY tbd.paid_amount " + customerBillSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerBillSearchRequest.getLimit() ;
			}
			else {
				offSetQuery = " ORDER BY tcd.customer_id Desc LIMIT " + offset + " , " + customerBillSearchRequest.getLimit();
			}
			
		} 
		if (!stringBuilder.isEmpty()) {
			stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
			queryResponse = getDetailQuery + " where " + stringBuilder + " " + offSetQuery;
		} else {
			queryResponse = getDetailQuery +  offSetQuery;
		}

		if (!stringBuilder.isEmpty()) {
			countResponse = getCount + " where " + stringBuilder;
		} else {
			countResponse = getCount ;
		}
		
		List<CustomerBillSearchDto> searchResponse = jdbcTemplate.query(queryResponse,
				(rs, rowNum) -> userMapFields(rs));
		int totalCount = jdbcTemplate.queryForObject(countResponse.toString(), Integer.class);
		customerBillSearchResponse.setCount(Long.valueOf(totalCount));
		customerBillSearchResponse.setLimit(customerBillSearchRequest.getLimit());
		customerBillSearchResponse.setPage(customerBillSearchRequest.getPage());		
		customerBillSearchResponse.setResultObject(searchResponse);
		LOGGER.info("Exit :: StockServiceimpl :: getAllProductStock():" + customerBillSearchResponse);
		return customerBillSearchResponse;
		
	}

	private CustomerBillSearchDto userMapFields(ResultSet rs) throws SQLException {
		CustomerBillSearchDto billSearchDto=new CustomerBillSearchDto();
		billSearchDto.setCustomerId(rs.getLong("customer_id"));
		billSearchDto.setCustomerName(rs.getString("customer_name"));
		billSearchDto.setContactNumber(rs.getString("contact_number"));
		billSearchDto.setAddress(rs.getString("customer_address"));
		billSearchDto.setPaidAmount(rs.getString("paid_amount"));
		billSearchDto.setInvoiceNumber(rs.getString("invoice_number"));
		return billSearchDto;
	}

	
	@Override
	public String viewPdfDataByUsingInvoiceNumber(String invoiceNumber) {
		LOGGER.info("Entry :: StockServiceimpl :: viewPdfDataByUsingInvoiceNumber():" +invoiceNumber);
		String response="";
		try {
			byte[] pdfDataArray=pdfFileDataRepository.findByInvoiceNumber( invoiceNumber);
			response = PdfBillGenerator.generateBase64CodeString(pdfDataArray);
			
		} catch (Exception e) {
			
		}
		LOGGER.info("Exit :: StockServiceimpl :: viewPdfDataByUsingInvoiceNumber():" +response);
		return response;
	}

}
