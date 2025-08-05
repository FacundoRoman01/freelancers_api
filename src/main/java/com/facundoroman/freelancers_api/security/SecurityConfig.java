package com.facundoroman.freelancers_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpMethod;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class SecurityConfig {


	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
	    return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
	}

	    // El constructor por defecto es suficiente
	    public SecurityConfig() {
	    }
//Para incriptar la contraseña
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) { 
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); 
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    
    
 
	

	// Protegiendo los endpoint --- privados y publicos 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtils jwtUtils, UserDetailsService userDetailsService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api/auth/**"
                ).permitAll()
                // Endpoint público para obtener la lista de todos los profesionales
                .requestMatchers(HttpMethod.GET, "/api/profesionales").permitAll()
                // Endpoint público para ver un profesional por su ID
                .requestMatchers(HttpMethod.GET, "/api/profesionales/{id}").permitAll()
                // Endpoint público para filtrar profesionales
                .requestMatchers(HttpMethod.GET, "/api/profesionales/filtrar").permitAll()
                
                // Endpoints privados que requieren autenticación
                .requestMatchers(HttpMethod.PUT, "/api/profesionales/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/profesionales/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/profesionales/**").authenticated()
                // El endpoint /me debe ser protegido
                .requestMatchers(HttpMethod.GET, "/api/profesionales/me").authenticated()
                // Cualquier otra petición no especificada requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}