package com.pharma.medicare.service;

import org.springframework.stereotype.Service;

import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserSignupResponse;

@Service
public interface UserService {

	UserLoginResponse userLogin(UserLoginRequest userRequest);

	UserSignupResponse userSignup(UserSignupRequest userRequest);

}
