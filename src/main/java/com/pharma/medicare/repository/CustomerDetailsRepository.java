package com.pharma.medicare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.medicare.domain.CustomerDetails;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {

	Optional<CustomerDetails> findByCustomerName(String customerName);

}
