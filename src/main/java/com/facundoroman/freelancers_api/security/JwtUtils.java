package com.facundoroman.freelancers_api.security;

import java.util.Date;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String jwtsecret;

	@Value("${jwt.expiration}")
	private Long jwtexpirationMs;
	
	

	// Obtener la clave para firmar el token
	private Key getSigningKey() {
		
		System.out.println("DEBUG: El valor de 'jwt.secret' es: '" + jwtsecret + "'");

		byte[] keyBytes = Decoders.BASE64.decode(jwtsecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Genera el token con el nombre de usuario 
	public String genereToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtexpirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
}
