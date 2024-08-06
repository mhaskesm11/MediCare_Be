package com.pharma.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pharma.medicare.domain.BillingDetails;

public interface BillingDetailRepository extends JpaRepository<BillingDetails, Long> {

	@Query
	(value="select tbd.invoice_number from txn_billing_details tbd" ,
			nativeQuery = true)
	List<String> findAllInvoiceNumber();

	@Query
	(value="select * from txn_billing_details tbd where tbd.invoice_number=?1" ,
			nativeQuery = true)
	Optional<BillingDetails> getBillingDetailByInvoiceNumber(String invoiceNumber);

}
