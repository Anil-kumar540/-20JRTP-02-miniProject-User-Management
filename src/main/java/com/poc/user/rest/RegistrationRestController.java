package com.poc.user.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.user.bindings.UserRegForm;
import com.poc.user.service.UserMgmtService;

@RestController
public class RegistrationRestController {

	@Autowired
	private UserMgmtService userService;

	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable("email") String email) {

		return userService.checkUniqEmail(email);
	}

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {

		return userService.loadCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable("countryId") Integer countryId) {

		return userService.loadStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable("stateId") Integer stateId) {

		return userService.loadCities(stateId);
	}

	@PostMapping("/user")
	public String registerUser(@RequestBody UserRegForm regForm) {

		return userService.registerUser(regForm);

	}

}
