package com.weDealAdmin.TestsUserManagement.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public abstract class CompanyTyped {

	private Long id;
	private Company company;
	private ArrayList<Privilege> privileges;
}
