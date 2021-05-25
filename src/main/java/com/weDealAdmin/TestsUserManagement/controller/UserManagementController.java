package com.weDealAdmin.TestsUserManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("")
	public List<User> getUsers ()
	{
		List<User> users = userService.findAll();
		
		return users;
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser (@PathVariable("username") String username)
	{
		User user = userService.findByUsername(username);
		HttpStatus status = null != user ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		return ResponseEntity.status(status).body(user);
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(
		@Valid @RequestBody UserCreateDto userCreateDto
	) {	
		userService.create(userCreateDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update (
		@PathVariable("id") Long id, 
		@Valid @RequestBody UserUpdateDto userUpdateDto
	) {
		boolean success = userService.update(userUpdateDto);
		HttpStatus status = success ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		
		return ResponseEntity.status(status).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable("id") Long id)
	{
		boolean success = userService.delete(id);
		HttpStatus status = success ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		
		return ResponseEntity.status(status).build();
	}
}