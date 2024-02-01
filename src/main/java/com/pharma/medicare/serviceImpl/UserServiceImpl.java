package com.pharma.medicare.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.User;
import com.pharma.medicare.repository.UserRepository;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSignupResponse;
import com.pharma.medicare.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public UserLoginResponse userLogin(UserLoginRequest userRequest) {

		LOGGER.info("Entry :: UserServiceImpl :: userLogin():" + userRequest);
		UserLoginResponse loginResponse = new UserLoginResponse();
		Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());

		if (optional.isPresent()) {
			User foundUser = optional.get();
			if (foundUser.getPassword().equals(userRequest.getPassword())
					&& foundUser.getMode().equals(userRequest.getMode().toUpperCase()) && foundUser.isApproved()) {

				loginResponse.setStatus(ServiceConstants.LOGIN_SUCCESSFUL);

			} else {
				if (!foundUser.getMode().equals(userRequest.getMode().toUpperCase())) {
					loginResponse.setStatus(ServiceConstants.PERMISSION_DENINED);
				}
				if (!foundUser.getPassword().equals(userRequest.getPassword())) {
					loginResponse.setStatus(ServiceConstants.INVALID_PASSWORD);
				}
				if (!foundUser.isApproved()) {
					loginResponse.setStatus(ServiceConstants.WAIT_APPROVAL);
				}

			}
		} else {
			loginResponse.setStatus(ServiceConstants.INVALID_USERNAME);
		}
		LOGGER.info("Exit :: UserServiceImpl :: userLogin():" + loginResponse);
		return loginResponse;

	}

	@Override
	public UserSignupResponse userSignup(UserSignupRequest userRequest) {

		UserSignupResponse response = new UserSignupResponse();
		Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());
		if (!optional.isPresent()) {
			User user = new User();
			BeanUtils.copyProperties(userRequest, user);
			user.setApproved(false);
			user.setMode(userRequest.getMode().toUpperCase());
			userRepository.save(user);
			response.setStatus(true);
			response.setStatusText("Signup Successful\nPlease wait for approval");
		} else {
			response.setStatus(false);
			response.setStatusText("Signup Failed\nUser Already Exists");
		}
		return response;
	}

}
