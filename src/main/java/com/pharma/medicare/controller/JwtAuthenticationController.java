package com.pharma.medicare.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.config.JwtTokenUtil;
import com.pharma.medicare.request.JwtRequest;
import com.pharma.medicare.response.AuthenticationResponse;
import com.pharma.medicare.response.JwtResponse;
import com.pharma.medicare.service.UserService;


@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;
	
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

//	    @PostMapping("/refresh-token{token}")
//	    public AuthenticationResponse refreshToken(@PathVariable String token) {
//	        String username = jwtTokenUtil.getUsernameFromToken(token);
//	        if (jwtTokenUtil.validateToken(token, username)) {
//	            final String newToken = jwtTokenUtil.generateToken(username);
//	            return new AuthenticationResponse(newToken);
//	        } else {
//	            throw new RuntimeException("Invalid refresh token");
//	        }
//	    }
	    
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

//	@PostMapping("/authenticate")
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//		System.out.println("need to verify the user first : ");
//		
//		authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
//		
//		final UserDetails userDetails = userDetailsService
//				.loadUserByUsername(authenticationRequest.getUserName());
//		final String token = jwtTokenUtil.generateToken(userDetails);
//
//		return ResponseEntity.ok(new JwtResponse(token));
//	}
//
//	private void authenticate(String username, String password) throws Exception {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//		} catch (DisabledException e) {
//			throw new Exception("USER_DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//		}
//	}
	
}