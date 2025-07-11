package com.facundoroman.freelancers_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@SecurityScheme(
	    name = "basicAuth",
	    type = SecuritySchemeType.HTTP,
	    scheme = "basic"
	)
public class SecurityConfig {
	
//Para incriptar la contraseña
	   @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

//añadiendo usuarios y roles en memoria
	   @Bean
	    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

	        UserDetails admin = User.withUsername("admin")
	                                .password(passwordEncoder.encode("adminpass"))
	                                .roles("ADMIN")
	                                .build();

	        UserDetails user = User.withUsername("user")
	                                .password(passwordEncoder.encode("userpass"))
	                                .roles("USER")
	                                .build();

	        return new InMemoryUserDetailsManager(admin, user);
	    }

	   //Protejiendo los endpoint --- privados y publicos
	   @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers(
	                    "/swagger-ui/**",
	                    "/v3/api-docs/**",
	                    "/swagger-resources/**",
	                    "/swagger-resources/configuration/ui",
	                    "/swagger-resources/configuration/security",
	                    "/webjars/**",
	                    "/v3/api-docs.yaml"
	                ).permitAll()

	                .requestMatchers("/api/profesionales/**").hasRole("ADMIN")

	                .anyRequest().authenticated()
	            )
	            .httpBasic();

	        return http.build();
	    }
}