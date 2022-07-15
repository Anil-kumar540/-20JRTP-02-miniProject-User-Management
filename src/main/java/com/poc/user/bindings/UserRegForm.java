package com.poc.user.bindings;


import lombok.Data;

@Data
public class UserRegForm {
	
	private String firstName;
	private String lastName;
	private String email;
	private long mobile;
	private String dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;

}
