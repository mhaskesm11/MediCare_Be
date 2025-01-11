package com.pharma.medicare.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.controller.JwtAuthenticationController;
import com.pharma.medicare.domain.User;
import com.pharma.medicare.repository.UserRepository;
import com.pharma.medicare.request.SearchUserRequest;
import com.pharma.medicare.request.UpdatePassWordRequest;
import com.pharma.medicare.request.UserLoginRequest;
import com.pharma.medicare.request.UserRequest;
import com.pharma.medicare.request.UserSignupRequest;
import com.pharma.medicare.response.AuthenticationResponse;
import com.pharma.medicare.response.PasswordResponse;
import com.pharma.medicare.response.UserLoginResponse;
import com.pharma.medicare.response.UserResponse;
import com.pharma.medicare.response.UserSearchDto;
import com.pharma.medicare.response.UserSearchResponse;
import com.pharma.medicare.response.UserSignupResponse;
import com.pharma.medicare.service.UserService;
import com.pharma.medicare.utility.CommonUtil;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired(required=true)
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtAuthenticationController jwtAuthentication;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	
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
		UserResponse user=new UserResponse();

		if (optional.isPresent()) {
			User foundUser = optional.get();
			boolean passwordEqualityCheck=bCryptPasswordEncoder.matches(userRequest.getPassword(), foundUser.getPassword());
			if (passwordEqualityCheck) {
				loginResponse.setStatus(ServiceConstants.LOGIN_SUCCESSFUL);
				try {
					AuthenticationResponse jwtToken=jwtAuthentication.authenticate(userRequest);
					loginResponse.setResponse(jwtToken);
				} catch (Exception e) {
					e.printStackTrace();
				}
				BeanUtils.copyProperties(foundUser, user);
				loginResponse.setUser(user);

			} else {
				
				if (!passwordEqualityCheck) {
					loginResponse.setStatus(ServiceConstants.INVALID_PASSWORD);
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
		List<User> userList = userRepository.getAllUsers();
		List<String> usernames = userList.stream()
                .map(User :: getUserName)
                .collect(Collectors.toList());
		List<String> emails = userList.stream()
                .map(User :: getEmail)
                .collect(Collectors.toList());
		List<String> contactNumbers = userList.stream()
                .map(User :: getContactNumber)
                .collect(Collectors.toList());
		if(usernames.contains(userRequest.getUserName())) {
			response.setStatus(false);
			response.setStatusText("Signup Failed :: UserName Already Exists Try Other");
		}else if(emails.contains(userRequest.getEmail())) {
			response.setStatus(false);
			response.setStatusText("Signup Failed ::"
										+ " Email Already Exists Try Other");
		}else if(contactNumbers.contains(userRequest.getContactNumber())) {
			response.setStatus(false);
			response.setStatusText("Signup Failed ::" 
											+ " ContactNumber Already Exists Try Other");
		}else {
			User user = new User();
			BeanUtils.copyProperties(userRequest, user);
			user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setIsActive("Y");
			user.setCreatedBy(userRequest.getUserName()+" (self)");
			userRepository.save(user);
			response.setStatus(true);
			response.setStatusText(" Signup Successful ");	
		}
		
		return response;
	}

	@Override
	public PasswordResponse forgotUserPassword(String email) {
		Optional<User> optional = userRepository.findByEmail(email.toLowerCase());
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
			user.setUpdatedBy(updatePassWordRequest.getUserName());
			user.setPassword(bcryptEncoder.encode(updatePassWordRequest.getNewPassword()));
			userRepository.save(user);
			response=ServiceConstants.USER_PASSWORD_MODIFIED;
		}else {
			response=ServiceConstants.USER_PASSWORD_NOT_MODIFIED;
		}
		return response;
	}
	
	
	String getDetailQuery="select * from txn_user tu ";
	String getCount="select count(*) from txn_user tu";

	@Override
	public UserSearchResponse searchAllUsers(SearchUserRequest searchUserRequest) {
		
		LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():" +searchUserRequest);
		String queryResponse="";
		String countResponse="";
		String offSetQuery="";
		StringBuilder stringBuilder=new StringBuilder();
		UserSearchResponse UserSearchResponse=new UserSearchResponse();
		
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tu.first_name", searchUserRequest.getFirstName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tu.last_name", searchUserRequest.getLastName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tu.user_name", searchUserRequest.getUserName());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tu.email", searchUserRequest.getEmail());
		CommonUtil.checkAppendConditionForStringField(stringBuilder, " tu.contact_number", searchUserRequest.getContactNumber());
		
		int pagecount = searchUserRequest.getPage() - 1;
		int offset = pagecount * searchUserRequest.getLimit();
		
		if (!searchUserRequest.getOrderBy().isEmpty() && 
			 !searchUserRequest.getOrderDirection().isEmpty())
		{
			if (searchUserRequest.getOrderBy().equalsIgnoreCase("firstName"))
			{
				offSetQuery = " ORDER BY tu.first_name " + searchUserRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + searchUserRequest.getLimit() ;
			} 
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("lastName"))
			{
				offSetQuery = " ORDER BY tu.last_name " + searchUserRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + searchUserRequest.getLimit() ;
			} 
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("user_name"))
			{
				offSetQuery = " ORDER BY tu.user_name " + searchUserRequest.getOrderDirection() + " LIMIT " + offset
						+ " , " + searchUserRequest.getLimit() ;
			} 
			
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("email")) 
			{
				offSetQuery = " ORDER BY tu.email " + searchUserRequest.getOrderDirection() + " LIMIT " + offset
						+ " , " + searchUserRequest.getLimit() ;
			} 
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("contactNumber"))
			{
				offSetQuery = " ORDER BY tu.contact_number " + searchUserRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + searchUserRequest.getLimit() ;
			}
			else {
				offSetQuery = " order by tu.user_id Desc LIMIT " + offset + " , " + searchUserRequest.getLimit();
			}
			
		} 
		if (!stringBuilder.isEmpty()) {
			stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
			queryResponse = getDetailQuery + " where " + stringBuilder + " AND is_active = 'Y' " + " " + offSetQuery;
		} else {
			queryResponse = getDetailQuery + " where is_active = 'Y' " + " " + offSetQuery;
		}

		if (!stringBuilder.isEmpty()) {
			countResponse = getCount + " where " + stringBuilder + " AND is_active = 'Y' ";
		} else {
			countResponse = getCount + " where is_active = 'Y' ";
		}
		
		List<UserSearchDto> UserSearchResponses = jdbcTemplate.query(queryResponse,
				(rs, rowNum) -> userMapFields(rs));
		int totalCount = jdbcTemplate.queryForObject(countResponse.toString(), Integer.class);
		UserSearchResponse.setCount(Long.valueOf(totalCount));
		UserSearchResponse.setLimit(searchUserRequest.getLimit());
		UserSearchResponse.setPage(searchUserRequest.getPage());		
		UserSearchResponse.setResultObject(UserSearchResponses);
		LOGGER.info("Exit :: StockServiceimpl :: getAllProductStock():" + UserSearchResponse);
		return UserSearchResponse;
	}
	
	private UserSearchDto userMapFields(ResultSet rs) throws SQLException {
		UserSearchDto user=new UserSearchDto();
		user.setUserId(rs.getLong("user_id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setUserName(rs.getString("user_name"));
		user.setContactNumber(rs.getString("contact_number"));
		user.setEmail(rs.getString("email"));
		user.setApproved(rs.getBoolean("approved"));
		user.setMode(rs.getString("mode"));
			return user;
		}

		@Override
		public User addUserDetails(UserRequest userRequest,String userName) {
			LOGGER.info("Entry :: UserServiceImpl :: addUserDetails():" + userRequest);
			Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());
			User user = new User();
			if (optional.isPresent()) {
				user = optional.get();
				user.setUpdatedBy(userName);
			}
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setUserName(userRequest.getUserName());
			user.setCreatedBy(userName);
			if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
				user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
			}
			user.setContactNumber(userRequest.getContactNumber());
			user.setEmail(userRequest.getEmail());
			user.setIsActive("Y");
			userRepository.save(user);
			return user;
		}

	@Override
	public Boolean deleteUserByUserId(Long userId) {
		LOGGER.info("Entry :: UserServiceImpl :: deleteUserByUserId():" + userId);
		Optional<User> optional = userRepository.findByUserId(userId);
		Boolean response=false;
		if (optional.isPresent()) {
			userRepository.deleteById(userId);
			response=true;
		}
		return response;
	}

}
