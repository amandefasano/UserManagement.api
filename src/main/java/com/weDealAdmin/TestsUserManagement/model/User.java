package com.weDealAdmin.TestsUserManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table (name = "users")
public class User 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(unique = true)
	@NotBlank
	private String username;
	
	@Column
	@NotBlank
	private String firstName;
	
	@Column
	@NotBlank
	private String lastName;
	
	@Column
	@NotBlank
	private String email;
	
	@Column
	@NotBlank
	private String password;
	
//	private CompanyTyped companyTyped;
//	private Role role;
//	private ArrayList<Kiosk> kiosks;
//	private ArrayList<Tag> tags;
//	private ArrayList<KiosksScope> kiosksScopes;
}
