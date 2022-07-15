package com.poc.user.service;

import java.util.Map;

import com.poc.user.bindings.LoginForm;
import com.poc.user.bindings.UnlockAccForm;
import com.poc.user.bindings.UserRegForm;

public interface UserMgmtService {

	// Login functionality method
	String logIn(LoginForm loginForm);

	// Registration methods
	String checkUniqEmail(String emailId);

	Map<Integer, String> loadCountries();

	Map<Integer, String> loadStates(Integer country_Id);

	Map<Integer, String> loadCities(Integer state_Id);

	String registerUser(UserRegForm regForm);

	// Unlock Account method
	String unlockAccount(UnlockAccForm unlockForm);

	// Forgot password method
	String getForgetPwd(String emailId);

}
