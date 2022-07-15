package com.poc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.user.entity.Country_Master;

public interface CountryMasterRepo extends JpaRepository<Country_Master, Integer> {

}
