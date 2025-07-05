package com.facundoroman.freelancers_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facundoroman.freelancers_api.model.Profesional;
import com.facundoroman.freelancers_api.repository.ProfesionalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class ProfesionalService {
	
	@Autowired
	private ProfesionalRepository profesionalRepo;
	
	//Buscar a todos los profesionales 
	public Page<Profesional> listarProfesional(Pageable pageable){
		return profesionalRepo.findAll(pageable);
	}
	
	
	//crear a un nuevo profesional
	public Profesional crearProfesional(Profesional p) {
		return profesionalRepo.save(p);
	}
	
	//buscar profesional por id
	public Profesional buscarProfesionalId(Long id) {
		
		return profesionalRepo.findById(id).orElse(null);
		
	}
	
	
	
	//filtrar profesional por cuidad y habilidad
	
	public  List<Profesional> filtrarPorCiudadYHabilidad(String ciudad , String habilidad) {
		
		return profesionalRepo.findByCiudadAndHabilidadesContaining(ciudad, habilidad);
		
	}
	
	
	
	
	
	
	
	//elimina profesional por id
	public Profesional eliminarProfesionalId(Long id) {
		
		profesionalRepo.deleteById(id);
		return null;
		
	}
	
	//actualiza profesional por id
	 public Profesional actualizarProfesional(Long id, Profesional datosActualizados) {
	   
	        Optional<Profesional> profesionalExistenteOptional = profesionalRepo.findById(id);

	       
	        if (profesionalExistenteOptional.isPresent()) {
	          
	            Profesional profesionalExistente = profesionalExistenteOptional.get();

	            profesionalExistente.setNombre(datosActualizados.getNombre());
	            profesionalExistente.setEspecialidad(datosActualizados.getEspecialidad());
	            profesionalExistente.setProvincia(datosActualizados.getProvincia());
	            profesionalExistente.setCiudad(datosActualizados.getCiudad());
	            profesionalExistente.setHabilidades(datosActualizados.getHabilidades()); 
	            profesionalExistente.setDescripcion(datosActualizados.getDescripcion());
	            profesionalExistente.setEmail(datosActualizados.getEmail());

	     
	            return profesionalRepo.save(profesionalExistente);
	        } else {
	       
	            return null;
	        }
	
	 }
}
