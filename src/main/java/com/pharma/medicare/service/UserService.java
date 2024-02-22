package com.pharma.medicare.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.pharma.medicare.request.UpdatePassWordRequest;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.PasswordResponse;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSignupResponse;

@Service
public interface UserService {

	UserLoginResponse userLogin(UserLoginRequest userRequest);

	UserSignupResponse userSignup(UserSignupRequest userRequest);

	PasswordResponse forgotUserPassword(String email);

	String userPasswordChangeSave(UpdatePassWordRequest updatePassWordRequest);

	UserDetails loadUserByUsername(String username);

}