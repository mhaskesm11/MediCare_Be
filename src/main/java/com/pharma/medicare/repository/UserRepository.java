package com.pharma.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pharma.medicare.domain.User;
import com.pharma.medicare.request.PendingRequest;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);
	
	@Query(value = "select fullName,userName,mode from txn_user where approved=FALSE",nativeQuery = true)
	List<PendingRequest> getAllPendingRequests();
	
	

}
