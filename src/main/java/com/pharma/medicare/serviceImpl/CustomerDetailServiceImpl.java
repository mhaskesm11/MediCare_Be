package com.pharma.medicare.serviceImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.BillingDetails;
import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.domain.PDFFileDetails;
import com.pharma.medicare.repository.BillingDetailRepository;
import com.pharma.medicare.repository.CustomerDetailsRepository;
import com.pharma.medicare.repository.PDFFileDataRepository;
import com.pharma.medicare.request.CustomerBillSearchRequest;
import com.pharma.medicare.request.CustomerRequest;
import com.pharma.medicare.request.CustomerSearchRequest;
import com.pharma.medicare.response.CustomerAllDetailsResponse;
import com.pharma.medicare.response.CustomerBillSearchDto;
import com.pharma.medicare.response.CustomerBillSearchResponse;
import com.pharma.medicare.response.CustomerDetailsResponse;
import com.pharma.medicare.response.CustomerResponse;
import com.pharma.medicare.response.CustomerSearchDto;
import com.pharma.medicare.response.PdfFileDataResponse;
import com.pharma.medicare.service.CustomerDetailService;
import com.pharma.medicare.utility.CommonUtil;
import com.pharma.medicare.utility.PdfBillGenerator;

@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {

	private Logger LOGGER = LoggerFactory.getLogger(CustomerDetailServiceImpl.class);

	@Autowired
	CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	BillingDetailRepository billingDetailRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	PDFFileDataRepository pdfFileDataRepository;

	@Override
	public CustomerDetails addCustomerDetails(CustomerRequest customerRequest, String userName) {

		LOGGER.info("Entry :: CustomerDetailServiceImpl :: addCustomerDetails():" + customerRequest);
		CustomerDetails customerDetails = new CustomerDetails();

		if (customerRequest.getCustomerId() == 0) {

			Optional<CustomerDetails> optional = customerDetailsRepository
					.findByCustomerName(customerRequest.getCustomerName());
			if (optional.isPresent()) {
				CustomerDetails customer = optional.get();
				if (customer.getCustomerAddress().equals(customerRequest.getCustomerAddress())) {
					if (customer.getContactNumber().equals(customerRequest.getContactNumber())) {
						customer.setUpdatedBy(userName);
						customerDetails = customer;
					}
				}
				customerRequest.setCustomerId(customerDetails.getCustomerId());
			} else {
				BeanUtils.copyProperties(customerRequest, customerDetails);
				customerDetails.setIsActive("Y");
				customerDetails.setCreatedBy(userName);
				customerDetails.setCustomerAddedDate(Date.valueOf(LocalDate.now()));
			}

		} else {

			Optional<CustomerDetails> optional = customerDetailsRepository.findById(customerRequest.getCustomerId());
			if (optional.isPresent()) {
				customerDetails = optional.get();
				BeanUtils.copyProperties(customerRequest, customerDetails);
				customerDetails.setUpdatedBy(userName);
			}
		}
		customerDetails.setIsActive(ServiceConstants.Y);
		customerDetails.setCreatedBy(userName);
		customerDetails = customerDetailsRepository.saveAndFlush(customerDetails);
		LOGGER.info("Exit :: CustomerDetailServiceImpl :: addCustomerDetails():" + customerDetails);
		return customerDetails;
	}

	@Override
	public List<CustomerDetailsResponse> getAllCustomerDetail() {
		LOGGER.info("Entry :: CustomerDetailServiceImpl :: getAllCustomerDetail():");
		List<CustomerDetailsResponse> allCustomerDeatils = customerDetailsRepository.findAllCustomerDetails();

		return allCustomerDeatils;
	}

	String getDetailQuery = "SELECT tcd.customer_id, tcd.customer_name,tcd.customer_address,tcd.contact_number,tbd.invoice_number, "
			+ "tbd.paid_amount FROM txn_customer_details tcd join txn_billing_details tbd on tcd.customer_id=tbd.customer_id ";
	String getCount = "SELECT count(*) "
			+ " FROM txn_customer_details tcd join txn_billing_details tbd on tcd.customer_id=tbd.customer_id";

	@Override
	public CustomerBillSearchResponse searchAllCustomerSales(CustomerBillSearchRequest customerBillSearchRequest) {
		LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():" + customerBillSearchRequest);
		String queryResponse = "";
		String countResponse = "";
		String offSetQuery = "";
		StringBuilder stringBuilder = new StringBuilder();
		CustomerBillSearchResponse customerBillSearchResponse = new CustomerBillSearchResponse();

		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tcd.customer_name",
				customerBillSearchRequest.getCustomerName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tcd.customer_address",
				customerBillSearchRequest.getAddress());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tbd.invoice_number",
				customerBillSearchRequest.getInvoiceNumber());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tbd.paid_type",
				customerBillSearchRequest.getAmountType());
		CommonUtil.checkAppendConditionForDateComparison(stringBuilder, "tbd.created_date", customerBillSearchRequest.getSaleType());
		CommonUtil.checkAppendConditionForDateField(stringBuilder, " tbd.billing_date",
				customerBillSearchRequest.getBillingDate());
		int pagecount = customerBillSearchRequest.getPage() - 1;
		int offset = pagecount * customerBillSearchRequest.getLimit();

		if (!customerBillSearchRequest.getOrderBy().isEmpty()
				&& !customerBillSearchRequest.getOrderDirection().isEmpty()) {
			if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("customerName")) {
				offSetQuery = " ORDER BY tcd.customer_name " + customerBillSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerBillSearchRequest.getLimit();
			} else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("address")) {
				offSetQuery = " ORDER BY tcd.customer_address " + customerBillSearchRequest.getOrderDirection()
						+ " LIMIT " + offset + " , " + customerBillSearchRequest.getLimit();
			} else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("amountType")) {
				offSetQuery = " ORDER BY tbd.paid_type " + customerBillSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerBillSearchRequest.getLimit();
			}

			else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("invoiceNumber")) {
				offSetQuery = " ORDER BY tbd.invoice_number " + customerBillSearchRequest.getOrderDirection()
						+ " LIMIT " + offset + " , " + customerBillSearchRequest.getLimit();
			} else if (customerBillSearchRequest.getOrderBy().equalsIgnoreCase("paidAmount")) {
				offSetQuery = " ORDER BY tbd.paid_amount " + customerBillSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerBillSearchRequest.getLimit();
			} else {
				offSetQuery = " ORDER BY tbd.billing_id Desc LIMIT " + offset + " , "
						+ customerBillSearchRequest.getLimit();
			}

		}
		if (!stringBuilder.isEmpty()) {
			stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
			queryResponse = getDetailQuery + " where " + stringBuilder + " And tcd.is_active='Y' And tbd.is_active='Y' "
					+ offSetQuery;
		} else {
			queryResponse = getDetailQuery + " where tcd.is_active='Y' And tbd.is_active='Y' " + offSetQuery;
		}

		if (!stringBuilder.isEmpty()) {
			countResponse = getCount + " where " + stringBuilder + " And tcd.is_active='Y' And tbd.is_active='Y' ";
		} else {
			countResponse = getCount + " where tcd.is_active='Y' And tbd.is_active='Y' ";
		}

		List<CustomerBillSearchDto> searchResponse = jdbcTemplate.query(queryResponse,
				(rs, rowNum) -> userMapFields(rs));
		int totalCount = jdbcTemplate.queryForObject(countResponse.toString(), Integer.class);
		customerBillSearchResponse.setCount(Long.valueOf(totalCount));
		customerBillSearchResponse.setLimit(customerBillSearchRequest.getLimit());
		customerBillSearchResponse.setPage(customerBillSearchRequest.getPage());
		customerBillSearchResponse.setResultObject(searchResponse);
		LOGGER.info("Exit :: CustomerDetailServiceImpl :: searchAllCustomerSale():" + customerBillSearchResponse);
		return customerBillSearchResponse;

	}

	private CustomerBillSearchDto userMapFields(ResultSet rs) throws SQLException {
		CustomerBillSearchDto billSearchDto = new CustomerBillSearchDto();
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
		LOGGER.info("Entry :: CustomerDetailServiceImpl :: viewPdfDataByUsingInvoiceNumber():" + invoiceNumber);
		String response = "";
		try {
			byte[] pdfDataArray = pdfFileDataRepository.findByInvoiceNumber(invoiceNumber);
			response = PdfBillGenerator.generateBase64CodeString(pdfDataArray);

		} catch (Exception e) {

		}
		LOGGER.info("Exit :: CustomerDetailServiceImpl :: viewPdfDataByUsingInvoiceNumber():" + response);
		return response;
	}

	@Override
	public String deleteCustomerBillDetails(String invoiceNumber) {
		LOGGER.info("Entry :: CustomerDetailServiceImpl :: deleteCustomerBillDetails():" + invoiceNumber);
		String response = "";
		try {
			Optional<BillingDetails> customerBillDetail = billingDetailRepository
					.getBillingDetailByInvoiceNumber(invoiceNumber);
			Optional<PDFFileDetails> customerPdfFile = pdfFileDataRepository.getPdfFileByInvoiceNumber(invoiceNumber);
			if (customerBillDetail.isPresent() && customerPdfFile.isPresent()) {
				customerBillDetail.get().setIsActive("N");
				billingDetailRepository.saveAndFlush(customerBillDetail.get());
				customerPdfFile.get().setIsActive("N");
				pdfFileDataRepository.saveAndFlush(customerPdfFile.get());
				response = ServiceConstants.PDF_BILL_DELETED_SUCCESSFULLY;
			} else {
				response = ServiceConstants.PDF_NOT_BILL_DELETED_SUCCESSFULLY;
			}
		} catch (Exception e) {

		}
		LOGGER.info("Exit :: CustomerDetailServiceImpl :: deleteCustomerBillDetails():" + response);
		return response;
	}

	@Override
	public PDFFileDetails downloadCustomerBillByInvoiceNumber(String invoiceNumber) {

		LOGGER.info("Entry :: CustomerDetailServiceImpl :: downloadCustomerBillByInvoiceNumber():" + invoiceNumber);
		PDFFileDetails response = null;
		try {
			 Optional<PDFFileDetails> optional = pdfFileDataRepository.getPdfFileByInvoiceNumber(invoiceNumber);
			 if(optional.isPresent()) {
				 response=optional.get();
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit :: CustomerDetailServiceImpl :: downloadCustomerBillByInvoiceNumber():" + response);
		return response;
	}
	
	
	@Override
	public CustomerResponse searchAllCustomerDetails(CustomerSearchRequest customerSearchRequest , String userName) {
		
		String getDetailQuery = "select * from txn_customer_details tcd ";
		String getCount = "select count(*) from txn_customer_details tcd";
		LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():" + customerSearchRequest);
		String queryResponse = "";
		String countResponse = "";
		String offSetQuery = "";
		StringBuilder stringBuilder = new StringBuilder();
		CustomerResponse customerResponse = new CustomerResponse();

		CommonUtil.checkAppendConditionForStringField(stringBuilder, "tcd.customer_name",
				customerSearchRequest.getFirstName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, "tcd.customer_name",
				customerSearchRequest.getLastName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, "tcd.contact_number",
				customerSearchRequest.getContactNumber());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, "tcd.customer_address",
				customerSearchRequest.getAddress());
		CommonUtil.checkAppendConditionForDateField(stringBuilder, "tcd.customer_added_date",
				customerSearchRequest.getCustomerAddedDate());

		int pagecount = customerSearchRequest.getPage() - 1;
		int offset = pagecount * customerSearchRequest.getLimit();

		if (!customerSearchRequest.getOrderBy().isEmpty() && !customerSearchRequest.getOrderDirection().isEmpty()) {
			if (customerSearchRequest.getOrderBy().equalsIgnoreCase("firstName")) {
				offSetQuery = " ORDER BY tcd.customer_name " + customerSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerSearchRequest.getLimit();
			} else if (customerSearchRequest.getOrderBy().equalsIgnoreCase("lastName")) {
				offSetQuery = " ORDER BY tcd.customer_name " + customerSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerSearchRequest.getLimit();
			}else if (customerSearchRequest.getOrderBy().equalsIgnoreCase("customerAddedDate")) {
				offSetQuery = " ORDER BY tcd.customer_added_date " + customerSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerSearchRequest.getLimit();
			}
			else if (customerSearchRequest.getOrderBy().equalsIgnoreCase("contactNumber")) {
				offSetQuery = " ORDER BY tcd.contact_number " + customerSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerSearchRequest.getLimit();
			} else if (customerSearchRequest.getOrderBy().equalsIgnoreCase("address")) {
				offSetQuery = " ORDER BY tcd.customer_address " + customerSearchRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + customerSearchRequest.getLimit();
			} else {
				offSetQuery = " order by tcd.customer_id Desc LIMIT " + offset + " , " + customerSearchRequest.getLimit();
			}

		}
		if (!stringBuilder.isEmpty()) {
			stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
			queryResponse = getDetailQuery + " where " + stringBuilder + " AND tcd.is_active = 'Y' " + " " + offSetQuery;
		} else {
			queryResponse = getDetailQuery + " where tcd.is_active = 'Y' " + " " + offSetQuery;
		}

		if (!stringBuilder.isEmpty()) {
			countResponse = getCount + " where " + stringBuilder + " AND tcd.is_active = 'Y' ";
		} else {
			countResponse = getCount + " where tcd.is_active = 'Y' ";
		}

		List<CustomerSearchDto> customerSearchResponses = jdbcTemplate.query(queryResponse,
				(rs, rowNum) -> customerMapFields(rs));
		int totalCount = jdbcTemplate.queryForObject(countResponse.toString(), Integer.class);
		customerResponse.setCount(Long.valueOf(totalCount));
		customerResponse.setLimit(customerSearchRequest.getLimit());
		customerResponse.setPage(customerSearchRequest.getPage());
		customerResponse.setResultObject(customerSearchResponses);
		LOGGER.info("Exit :: StockServiceimpl :: getAllProductStock() :" + customerResponse);
		return customerResponse;
	}

	private CustomerSearchDto customerMapFields(ResultSet rs) throws SQLException {
		CustomerSearchDto customer = new CustomerSearchDto();
		customer.setCustomerId(rs.getLong("customer_id"));
		customer.setCustomerName(rs.getString("customer_name"));
		customer.setContactNumber(rs.getString("contact_number"));
		customer.setCustomerAddedDate(rs.getDate("customer_added_date"));
		customer.setCustomerAddress(rs.getString("customer_address"));
		return customer;
	}
	
	@Override
	public CustomerAllDetailsResponse getAllCustomerDetailsById(Long customerId) {
		
		CustomerAllDetailsResponse allDetailsResponse=new CustomerAllDetailsResponse(); 
		List<PdfFileDataResponse> list=new ArrayList<>();
		try {
			Optional<CustomerDetails> customerOptional = customerDetailsRepository
					.getCustomerDetailByCustomerId(customerId);
			if(customerOptional.isPresent()) {
				
				allDetailsResponse.setIsPresent("Y");
				
				List<PDFFileDetails> customerPdfFile = pdfFileDataRepository.
						getPdfFileByCustomerName(customerOptional.get().getCustomerName());
				 for (PDFFileDetails pdfFile : customerPdfFile) {
		                PdfFileDataResponse pdfResponse = new PdfFileDataResponse();
		                pdfResponse.setFileName(pdfFile.getFileName());
		                pdfResponse.setInvoiceNumber(pdfFile.getInvoiceNumber());
		                list.add(pdfResponse);
		            }
				
				List<BillingDetails> customerBills = billingDetailRepository.getCustomerBillByCustomerId(customerId);
				
				 for (PdfFileDataResponse pdfResponse : list) {
		                for (BillingDetails bill : customerBills) {
		                    if (pdfResponse.getInvoiceNumber().equals(bill.getInvoiceNumber())) {
		                        pdfResponse.setPaidAmount(bill.getPaidAmount());
		                        break; 
		                    }
		                }
		            }
				
			}else{
				allDetailsResponse.setIsPresent("N");
			}
			
		} catch (Exception e) {
			
		}
		allDetailsResponse.setCustomerPdfFileList(list);
		return allDetailsResponse;
	}

	@Transactional
	@Override
	public String deleteCustomerDetailsById(Long customerId, String userName) {
		LOGGER.info("Entry :: CustomerDetailServiceImpl :: deleteCustomerDetailsById(): " + customerId);
		String response = "";
		
		try {
			Optional<CustomerDetails> customerOptional = customerDetailsRepository
					.getCustomerDetailByCustomerId(customerId);
			String customerName="";
			if(customerOptional.isPresent()) {
				CustomerDetails customerDetails = customerOptional.get();
				customerDetails.setIsActive("N");
				customerDetails.setUpdatedBy(userName);
				customerName=customerDetails.getCustomerName();
				customerDetailsRepository.saveAndFlush(customerDetails);
			}
			if(CommonUtil.isNotNull(customerName)) {
				List<PDFFileDetails> customerPdfFile = pdfFileDataRepository.getPdfFileByCustomerName(customerName.toLowerCase());
				if (customerPdfFile != null && !customerPdfFile.isEmpty()) {
					pdfFileDataRepository.deleteAllPdfFilebyCustomerName(customerName.toLowerCase());		
				}
			}
			
			List<BillingDetails> customerBill = billingDetailRepository.getCustomerBillByCustomerId(customerId);
			
			if(customerBill!=null && !customerBill.isEmpty()) {
				billingDetailRepository.deleteAllCustomerBillDetails(customerId);
			}
			response=ServiceConstants.ALL_CUSTOMER_RELATED_DETAILS_ARE_DELETED;
			
		} catch (Exception e) {
			response=ServiceConstants.ALL_CUSTOMER_RELATED_DETAILS_NOT_DELETED;
		}
		LOGGER.info("Exit :: CustomerDetailServiceImpl :: deleteCustomerBillDetails(): " + response);
		return response;
	}
	

}
