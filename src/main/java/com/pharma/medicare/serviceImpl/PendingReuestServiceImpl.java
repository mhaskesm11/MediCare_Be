package com.pharma.medicare.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.User;
import com.pharma.medicare.repository.UserRepository;
import com.pharma.medicare.request.PendingRequest;
import com.pharma.medicare.service.PendingRequestService;
@Service
public class PendingReuestServiceImpl implements PendingRequestService {

	private Logger LOGGER = LoggerFactory.getLogger(PendingReuestServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public List<PendingRequest> getAllPendingRequests() {

		LOGGER.info("Entry :: PendingReuestServiceImpl :: getAllPendingRequests():");
		List<PendingRequest> allPendingRequests = userRepository.getAllPendingRequests();
		LOGGER.info("Exit :: PendingReuestServiceImpl :: getAllPendingRequests():" + allPendingRequests);

		return allPendingRequests;
	}

	@Override
	public String giveUserApproval(String userName) {
		LOGGER.info("Entry :: PendingReuestServiceImpl :: getAllPendingRequests():" + userName);
		Optional<User> optional = userRepository.findByUserName(userName);
		String response=null;
		if (optional.isPresent()) {
			User user=optional.get();
			user.setApproved(true);
			userRepository.save(user);
			response=ServiceConstants.USER_APPROVED;
		}
		LOGGER.info("Exit :: PendingReuestServiceImpl :: getAllPendingRequests():" + response);
		return response;
	}

	@Override
	public String rejectUserApproval(String userName) {
		LOGGER.info("Entry :: PendingReuestServiceImpl :: getAllPendingRequests():" + userName);
		Optional<User> optional = userRepository.findByUserName(userName);
		String response=null;
		if (optional.isPresent()) {
			User user=optional.get();
			userRepository.deleteById(user.getUserId());
			response=ServiceConstants.USER_REJECTED;
		}
		return response;
	}

}
