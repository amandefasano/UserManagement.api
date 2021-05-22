package com.weDealAdmin.TestsUserManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//import com.weDealAdmin.TestsUserManagement.exceptions.ControllerExceptionHandler;
//import com.weDealAdmin.TestsUserManagement.exceptions.ControllerExceptionHandler.PathVariableIdEqualsZeroException;
import com.weDealAdmin.TestsUserManagement.model.User;
import com.weDealAdmin.TestsUserManagement.repository.UserRepository;

@Transactional
@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() 
	{
		return userRepository.findAll();
	}
	
	public User findByUsername(String username) 
	{
		return userRepository.findByUsername(username);
	}
	
	public void create(UserCreateDto userCreateDto) 
	{	
		String username = userCreateDto.getUsername();
		String firstname = userCreateDto.getFirstname();
		String lastname = userCreateDto.getLastname();
		String email = userCreateDto.getEmail();
		String password = userCreateDto.getPw();
		
		userRepository.create(
				username,
				firstname,
				lastname,
				email,
				password
			);
	}
	
	public void update(UserUpdateDto userUpdateDto) 
	{	
		userRepository.update(
			userUpdateDto.getId(),
			userUpdateDto.getUsername(),
			userUpdateDto.getFirstname(),
			userUpdateDto.getLastname(),
			userUpdateDto.getEmail(),
			userUpdateDto.getPw()
		);
	}
	
	public void delete(Long id) 
	{
		userRepository.delete(id);
	}

}
