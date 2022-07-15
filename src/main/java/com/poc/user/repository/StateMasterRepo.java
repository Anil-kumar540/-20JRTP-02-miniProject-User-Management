package com.poc.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.user.entity.State_Master;

public interface StateMasterRepo extends JpaRepository<State_Master, Integer> {
	//jpa will generate query and build method by method signature
	public List<State_Master>  getStatesByCountryId(Integer countryId);

}
