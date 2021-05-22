package com.weDealAdmin.TestsUserManagement.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Role {

	private Long id;
	private String name;
	private Company company;
	private ArrayList<Privilege> privileges;
}
