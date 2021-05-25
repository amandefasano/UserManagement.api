package com.weDealAdmin.TestsUserManagement.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserUpdateDto {
	
	@NotNull
	private Long 	id;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String username;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
