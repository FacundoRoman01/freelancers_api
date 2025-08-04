package com.facundoroman.freelancers_api.controller;

import java.security.Principal;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault; 
import org.springframework.data.domain.Sort; 
import io.swagger.v3.oas.annotations.Parameter;


import com.facundoroman.freelancers_api.model.Profesional;
import com.facundoroman.freelancers_api.service.ProfesionalService;
import com.facundoroman.freelancers_api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {
	
	@Autowired 
	ProfesionalService profesionalService;
	
    @Autowired
    private UserService userService;
	

	@GetMapping
	public ResponseEntity<Page<Profesional>> listarProfesionales(
			 @Parameter(hidden = true) 
			@PageableDefault(size = 10, sort = "nombre")
			Pageable pageable
			
			) {
		
		Page<Profesional> profesionales = profesionalService.listarProfesional(pageable);
		return new ResponseEntity<>(profesionales, HttpStatus.OK);
	}
	

	@PostMapping
	public ResponseEntity<Profesional> crearProfesional(@Valid @RequestBody Profesional profesional) {
		Profesional nuevoProfesional = profesionalService.crearProfesional(profesional);

		return new ResponseEntity<>(nuevoProfesional, HttpStatus.CREATED); 
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Profesional> buscarProfesionalPorId(@PathVariable Long id){
		Profesional profesional = profesionalService.buscarProfesionalId(id);
		if (profesional != null) {
			return new ResponseEntity<>(profesional, HttpStatus.OK); 
		} else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
	}
	
	
	
	 // Nuevo endpoint para filtrar profesionales
    @GetMapping("/filtrar") 
    public ResponseEntity<List<Profesional>> filtrarPorCiudadYHabilidad(
            @RequestParam String ciudad,    
            @RequestParam String habilidad) { 

        
        List<Profesional> profesionales = profesionalService.filtrarPorCiudadYHabilidad(ciudad, habilidad);

       
        return new ResponseEntity<>(profesionales, HttpStatus.OK);
    }
	

	@PutMapping("/{id}") 
	public ResponseEntity<Profesional> actualizarProfesional(
		@Valid	
	    @PathVariable Long id, 
	    @RequestBody Profesional datosActualizados) { 
	    
		Profesional profesionalActualizado = profesionalService.actualizarProfesional(id, datosActualizados);
	    if (profesionalActualizado != null) {
	        return new ResponseEntity<>(profesionalActualizado, HttpStatus.OK); 
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    }
	}
	
	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarProfesional(@PathVariable Long id) { 
		profesionalService.eliminarProfesionalId(id);
	
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	
	
	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Profesional> getAuthenticatedProfesional(Principal principal) {
	    String username = principal.getName();
	    com.facundoroman.freelancers_api.model.User user = userService.findByUsername(username);
	    Profesional profesional = profesionalService.findByEmail(user.getEmail());
	    return ResponseEntity.ok(profesional);
	}

	

}
