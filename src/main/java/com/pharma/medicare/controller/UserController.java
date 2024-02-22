package com.pharma.medicare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.request.UpdatePassWordRequest;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.PasswordResponse;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSignupResponse;
import com.pharma.medicare.service.UserService;
import com.pharma.medicare.utility.CommonUtil;

@RestController
@CrossOrigin(origins = "*")
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
	@PostMapping("login")
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
	
	// forgot password window
	@GetMapping("forgot/password/{email}")
	public PasswordResponse forgotUserPassword(@PathVariable String email) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/forgot/password")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(email)));
		PasswordResponse response = null;
		try {
			response = userService.forgotUserPassword(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}
	
	// password changes save window
		@PostMapping("update/password")
		public String userPasswordChangeSave(@RequestBody UpdatePassWordRequest updatePassWordRequest) {

			LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/update/password")));
			LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(updatePassWordRequest)));
			String response = null;
			try {
				response = userService.userPasswordChangeSave(updatePassWordRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
			return response;
		}

}
