package com.poc.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.user.entity.City_Master;

public interface CityMasterRepo extends JpaRepository<City_Master, Integer> {

	public List<City_Master> getCitiesByStateId(Integer stateId);

}
