package com.pharma.medicare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.config.JwtRequestFilter;

@RestController
@CrossOrigin
public class BaseController {
	
	@Autowired
	 private JwtRequestFilter jwtRequestFilter;



	public  String getUserNameFromHeader(HttpServletRequest request) {
		
		String token=request.getHeader("Authorization");
		String userName= jwtRequestFilter.getUsernameFromToken(token);
		System.out.println("username : "+userName);
		return userName;
		
	}

}
