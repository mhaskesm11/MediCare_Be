package com.pharma.medicare.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.User;
import com.pharma.medicare.repository.UserRepository;
import com.pharma.medicare.request.UpdatePassWordRequest;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.PasswordResponse;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSignupResponse;
import com.pharma.medicare.service.UserService;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired(required=true)
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserName(username);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
				user.get().getPassword(), new ArrayList<>());
	}

	@Override
	public UserLoginResponse userLogin(UserLoginRequest userRequest) {

		LOGGER.info("Entry :: UserServiceImpl :: userLogin():" + userRequest);
		UserLoginResponse loginResponse = new UserLoginResponse();
		Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());

		if (optional.isPresent()) {
			User foundUser = optional.get();
			boolean passwordEqualityCheck=bCryptPasswordEncoder.matches(userRequest.getPassword(), foundUser.getPassword());
			if (passwordEqualityCheck && foundUser.isApproved()) {
				loginResponse.setMode(foundUser.getMode());
				loginResponse.setStatus(ServiceConstants.LOGIN_SUCCESSFUL);

			} else {
				
				if (!passwordEqualityCheck) {
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
			user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
			user.setFullName(userRequest.getFirstName() + " " + userRequest.getLastName());
			user.setApproved(false);
			user.setIsActive("Y");
			user.setCreatedBy("SELF");
			user.setMode("USER");
			userRepository.save(user);
			response.setStatus(true);
			response.setStatusText("Signup Successful"
					  + "Please wait for approval");
		} else {
			response.setStatus(false);
			response.setStatusText("Signup Failed\nUser Already Exists");
		}
		return response;
	}

	@Override
	public PasswordResponse forgotUserPassword(String email) {
		Optional<User> optional = userRepository.findByEmail(email);
		PasswordResponse response=new PasswordResponse();
		if(optional.isPresent()) {
			response.setResponse(ServiceConstants.USER_FOUND);
			response.setUser(optional.get());			
		}
		else {
			response.setResponse(ServiceConstants.USER_NOTFOUND);;
		}
		return response;
	}

	@Override
	public String userPasswordChangeSave(UpdatePassWordRequest updatePassWordRequest) {
		Optional<User> optional = userRepository.findByUserName(updatePassWordRequest.getUserName());
		String response=null;
		if(optional.isPresent()) {
			User user=new User();
			BeanUtils.copyProperties(optional.get(), user);
			user.setPassword(bcryptEncoder.encode(updatePassWordRequest.getNewPassword()));
			userRepository.save(user);
			response=ServiceConstants.USER_PASSWORD_MODIFIED;
		}else {
			response=ServiceConstants.USER_PASSWORD_NOT_MODIFIED;
		}
		return response;
	}

}
