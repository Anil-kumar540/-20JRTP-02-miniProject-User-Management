package com.poc.user.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_ACCOUNT")
public class User {

	@Id
	@GeneratedValue
	private Integer userId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	private String password;
	private long mobile;
	private String dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private String accStatus;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createdDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updatedDate;

}
