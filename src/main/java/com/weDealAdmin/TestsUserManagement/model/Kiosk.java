package com.weDealAdmin.TestsUserManagement.model;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Kiosk {

	private Long id;
	private String label;
	private Date installation;
	private ArrayList<Tag> tags;
}
