package com.pharma.medicare.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.User;
import com.pharma.medicare.request.SearchUserRequest;
import com.pharma.medicare.request.UpdatePassWordRequest;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.PasswordResponse;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSearchResponse;
import com.pharma.medicare.response.UserSignupResponse;
import com.pharma.medicare.service.UserService;
import com.pharma.medicare.utility.CommonUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/user")
public class UserController extends BaseController {

	private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	// signup new user
	@PostMapping("signup")
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

	// search user (add user window)
	@PostMapping("search/all")
	public UserSearchResponse searchAllUsers(@RequestBody SearchUserRequest searchUserRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(searchUserRequest)));
		UserSearchResponse response = null;
		try {
			response = userService.searchAllUsers(searchUserRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	// add user (add user window)
	@PostMapping("save/user")
	public String addUserDetails(@RequestBody UserRequest userRequest, HttpServletRequest request) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userRequest)));
		String response = null;
		String userName=getUserNameFromHeader(request);
		User user = userService.addUserDetails(userRequest, userName);
		if (user != null) {
			response = ServiceConstants.USER_ADDED_SUCCESSFULLY;
		} else {
			response = ServiceConstants.USER_NOT_ADDED;
		}

		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
		
	// delete user (add user window)
	@DeleteMapping("delete/user{userId}")
	public String deleteUser(@PathVariable Long userId) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userId)));
		String response = null;
		Boolean user = userService.deleteUserByUserId(userId);
		if (user) {
			response = ServiceConstants.USER_DELETED_SUCCESSFULLY;
		} else {
			response = ServiceConstants.USER_NOT_DELETED;
		}

		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

}
