package com.weDealAdmin.TestsUserManagement.exceptions;

import lombok.Data;

@Data
public class BadDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public BadDataException(String message) {
		this.message = message;
	}
}
