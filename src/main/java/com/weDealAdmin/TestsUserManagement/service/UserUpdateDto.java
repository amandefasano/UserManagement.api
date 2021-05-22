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
	@Size(min = 1, max = 150)
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String pw;

}
