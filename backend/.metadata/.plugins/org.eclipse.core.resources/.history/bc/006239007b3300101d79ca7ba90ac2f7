package com.ramyakata.microservice.dao;

import com.ramyakata.microservice.dto.UserDto;
import com.ramyakata.microservice.entity.Users;
import com.ramyakata.microservice.exception.AuthException;

public interface AuthService {

	public Boolean insert(Users user) throws AuthException;

	String verify(Users user) throws AuthException;

	public UserDto getByUserName(String username) throws AuthException;
}
