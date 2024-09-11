package com.pharma.medicare.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.config.JwtTokenUtil;
import com.pharma.medicare.request.JwtRequest;
import com.pharma.medicare.response.AuthenticationResponse;


@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	 @PostMapping("/authenticate")
	    public AuthenticationResponse authenticate(@RequestBody JwtRequest request) throws Exception {
	        try {
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
	            );
	        } catch (Exception e) {
	            throw new Exception("Invalid username or password", e);
	        }

	        final String token = jwtTokenUtil.generateToken(request.getUserName());
	        return new AuthenticationResponse(token);
	    }
	    
	    @PostMapping("/refresh")
	    public AuthenticationResponse refreshJwtToken(@RequestBody String token) {
	        String username = jwtTokenUtil.getUsernameFromToken(token);
	        if (jwtTokenUtil.validateToken(token, username)) {
	            final String newToken = jwtTokenUtil.generateToken(username);
	            return new AuthenticationResponse(newToken);
	        } else {
	            throw new RuntimeException("Invalid refresh token");
	        }
	    }
	
}