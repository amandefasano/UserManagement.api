package com.weDealAdmin.TestsUserManagement.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class KiosksScope {

	private Long id;
	private String labelToDisplay;
	private User user;
	private ArrayList<Kiosk> kiosks;
	private ArrayList<Tag> tags;
}
