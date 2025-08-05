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
	    
	    
	    //Endpoint para el registro de nuevos usuarios
		@PostMapping("/register")
		public ResponseEntity<Map<String, String>> RegisterUser(@RequestBody User user) {
			// Lógica para registrar al nuevo usuario
			userService.registerNewUser(user);
			
			// Creamos un mapa para devolver una respuesta en formato JSON
			Map<String, String> response = new HashMap<>();
			response.put("message", "Usuario registrado exitosamente!");
			
			// Devolvemos la respuesta con el mensaje y el estado HTTP 201 (Created)
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		
		
		 //Endpoint para la autenticación de usuarios
		@PostMapping("/login")
		public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
			// Autentica al usuario usando el AuthenticationManager
			org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(),
					loginRequest.getPassword()
				)
			);
			
			
			// Genera el token JWT para el usuario autenticado
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtUtils.genereToken(userDetails);

			// Prepara la respuesta con el token JWT en un objeto JSON
			Map<String, String> response = new HashMap<>();
			response.put("token", token);

			// Devuelve la respuesta con el token y el estado HTTP 200 (OK)
			return ResponseEntity.ok(response);
	    }


}
