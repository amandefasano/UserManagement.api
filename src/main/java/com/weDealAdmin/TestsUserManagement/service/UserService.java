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
	
	public boolean create(UserCreateDto userCreateDto) 
	{
		return 0 != userRepository.create(
			userCreateDto.getUsername(),
			userCreateDto.getFirstName(),
			userCreateDto.getLastName(),
			userCreateDto.getEmail(),
			userCreateDto.getPassword()
		);
	}
	
	public boolean update(UserUpdateDto userUpdateDto) 
	{	
		return 0 != userRepository.update(
			userUpdateDto.getId(),
			userUpdateDto.getUsername(),
			userUpdateDto.getFirstName(),
			userUpdateDto.getLastName(),
			userUpdateDto.getEmail(),
			userUpdateDto.getPassword()
		);
	}
	
	public boolean delete(Long id) 
	{
		return 0 != userRepository.delete(id);
	}

}
