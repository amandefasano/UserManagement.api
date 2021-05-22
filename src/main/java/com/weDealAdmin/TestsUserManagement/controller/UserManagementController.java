package com.weDealAdmin.TestsUserManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.weDealAdmin.TestsUserManagement.model.User;
import com.weDealAdmin.TestsUserManagement.service.UserCreateDto;
import com.weDealAdmin.TestsUserManagement.service.UserService;
import com.weDealAdmin.TestsUserManagement.service.UserUpdateDto;

@RestController
@RequestMapping("/users")
public class UserManagementController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/{username}")
	public User getUser (@PathVariable("username") String username)
	{
			return userService.findByUsername(username);
	}
	
	@GetMapping("")
	@CrossOrigin(origins = "*")
	public List<User> getUsers ()
	{
		List<User> users = userService.findAll();
		
		return users;
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(
		@Valid @RequestBody UserCreateDto userCreateDto
	) {	
		userService.create(userCreateDto);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update (
		@PathVariable("id") Long id, 
		@Valid @RequestBody UserUpdateDto userUpdateDto
	) {
		userUpdateDto.setId(id);
		userService.update(userUpdateDto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (@PathVariable("id") Long id)
	{
		userService.delete(id);
	}
}

