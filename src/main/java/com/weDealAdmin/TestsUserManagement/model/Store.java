package com.weDealAdmin.TestsUserManagement.model;

import lombok.Data;

@Data
public class Store extends CompanyTyped {

	private String label;
	
	public Store (Long id, String label) {
		super();
		this.label = label;
	}
}
