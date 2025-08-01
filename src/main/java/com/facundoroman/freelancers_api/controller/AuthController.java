package com.facundoroman.freelancers_api.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundoroman.freelancers_api.dto.LoginRequest;
import com.facundoroman.freelancers_api.model.User;
import com.facundoroman.freelancers_api.security.JwtUtils;
import com.facundoroman.freelancers_api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	   	private final UserService userService;
	   	private final AuthenticationManager authenticationManager;
	   	private final JwtUtils jwtUtils;
	   	
	    
	    public AuthController(UserService userService , AuthenticationManager authenticationManager, JwtUtils jwtUtils ) {
	        this.userService = userService;
	        this.authenticationManager = authenticationManager;
	        this.jwtUtils = jwtUtils;
	    }
	    
	    
	    @PostMapping("/register")
	    public ResponseEntity<String> RegisterUser(@RequestBody User user){
	    	userService.registerNewUser(user);
	    	return new ResponseEntity<>("Usuario registrado exitosamente!", HttpStatus.CREATED);
	    }
	    
	    @PostMapping("/login")
	    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
	        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getUsername(),
	                loginRequest.getPassword()
	            )
	        );

	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String token = jwtUtils.genereToken(userDetails);

	        Map<String, String> response = new HashMap<>();
	        response.put("token", token);

	        return ResponseEntity.ok(response);
	    }


}
