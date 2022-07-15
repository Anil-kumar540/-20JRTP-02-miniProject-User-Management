package com.poc.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.user.bindings.UnlockAccForm;
import com.poc.user.service.UserMgmtService;

@RestController
public class UnlockAccRestController {

	@Autowired
	private UserMgmtService userService;

	@PostMapping("/unlock")
	public String unlockAccount(@RequestBody UnlockAccForm unlockForm) {

		return userService.unlockAccount(unlockForm);
	}

}
