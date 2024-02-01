package com.pharma.medicare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSignupResponse;
import com.pharma.medicare.service.UserService;
import com.pharma.medicare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

	private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	// signup new user
	@RequestMapping("signup")
	public UserSignupResponse signup(@RequestBody UserSignupRequest userRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/signup")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userRequest)));
		UserSignupResponse response = null;
		try {
			response = userService.userSignup(userRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// login window
	@RequestMapping("login")
	public UserLoginResponse login(@RequestBody UserLoginRequest userRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userRequest)));
		UserLoginResponse response = null;
		try {
			response = userService.userLogin(userRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

}
