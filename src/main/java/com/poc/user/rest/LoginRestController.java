package com.poc.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.user.bindings.LoginForm;
import com.poc.user.service.UserMgmtService;

@RestController
public class LoginRestController {
	
	@Autowired
	private UserMgmtService userService;
	
	@PostMapping("/login")
	public String loginUser(@RequestBody LoginForm loginForm) {
		return userService.logIn(loginForm);
	}

}
