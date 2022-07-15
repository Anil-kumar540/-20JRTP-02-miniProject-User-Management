package com.poc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.user.entity.User;

public interface UserDtlsRepo extends JpaRepository<User, Integer> {

	public User getUserByEmailAndPassword(String emailId,String password);
	
	public User getUserByEmail(String emailId);
}
