package com.pharma.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.medicare.request.PendingRequest;
@Service
public interface PendingRequestService {

	List<PendingRequest> getAllPendingRequests();

	String giveUserApproval(String userName);

	String rejectUserApproval(String userName);

}
