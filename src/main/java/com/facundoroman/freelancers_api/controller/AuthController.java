package com.facundoroman.freelancers_api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundoroman.freelancers_api.model.User;
import com.facundoroman.freelancers_api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	   	private final UserService userService;

	    
	    public AuthController(UserService userService) {
	        this.userService = userService;
	    }
	    
	    
	    @PostMapping("/register")
	    public ResponseEntity<String> RegisterUser(@RequestBody User user){
	    	userService.registerNewUser(user);
	    	return new ResponseEntity<>("Usuario registrado exitosamente!", HttpStatus.CREATED);
	    }

}
