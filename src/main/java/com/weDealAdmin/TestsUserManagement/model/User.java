package com.weDealAdmin.TestsUserManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

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
	@NotNull
	private String username;
	
	@Column
	private String firstname;
	
	@Column
	private String lastname;
	
	@Column
	@NotNull
	private String email;
	
	@Column
	@NotNull
	private String pw;
	
//	private CompanyTyped companyTyped;
//	private Role role;
//	private ArrayList<Kiosk> kiosks;
//	private ArrayList<Tag> tags;
//	private ArrayList<KiosksScope> kiosksScopes;
}
