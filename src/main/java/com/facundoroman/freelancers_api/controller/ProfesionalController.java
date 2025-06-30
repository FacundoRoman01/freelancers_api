package com.facundoroman.freelancers_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundoroman.freelancers_api.model.Profesional;
import com.facundoroman.freelancers_api.service.ProfesionalService;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {
	
	@Autowired 
	ProfesionalService profesionalService;
	

	@GetMapping
	public ResponseEntity<List<Profesional>> listarProfesionales() {
		List<Profesional> profesionales = profesionalService.listarProfesional();
		return new ResponseEntity<>(profesionales, HttpStatus.OK);
	}
	

	@PostMapping
	public ResponseEntity<Profesional> crearProfesional(@RequestBody Profesional profesional) {
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

	@PutMapping("/{id}") 
	public ResponseEntity<Profesional> actualizarProfesional(
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
	
	

}
