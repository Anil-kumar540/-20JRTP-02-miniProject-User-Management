package com.poc.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poc.user.service.UserMgmtService;

@RestController
public class ForgotPwdRestController {

	@Autowired
	private UserMgmtService userService;

	@GetMapping("/forgotPwd/{email}")
	public String forgetPwd(@PathVariable("email") String email) {

		return userService.getForgetPwd(email);
	}

}
