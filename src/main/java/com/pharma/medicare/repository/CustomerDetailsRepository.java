package com.pharma.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pharma.medicare.domain.CustomerDetails;
import com.pharma.medicare.request.PendingRequest;
import com.pharma.medicare.response.CustomerDetailsResponse;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
	@Query(value = "select * from txn_customer_details tcd where tcd.customer_name = ?1 ",
			nativeQuery = true)
	Optional<CustomerDetails> findByCustomerName(String customerName);
	
	@Query(value="select customer_id as customerId,customer_name as customerName,contact_number as contactNumber, customer_added_date as customerAddedDate, customer_address as customerAddress  from txn_customer_details",
			nativeQuery = true)
	List<CustomerDetailsResponse> findAllCustomerDetails();

	@Query(value = "select user_id as userId, full_name as fullName,user_name as userName, mode as mode from txn_user where approved=FALSE",nativeQuery = true)
	List<PendingRequest> getAllPendingRequests();
}
