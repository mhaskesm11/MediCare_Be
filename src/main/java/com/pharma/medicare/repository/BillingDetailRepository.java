package com.pharma.medicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.medicare.domain.BillingDetails;

public interface BillingDetailRepository extends JpaRepository<BillingDetails, Long> {

}
