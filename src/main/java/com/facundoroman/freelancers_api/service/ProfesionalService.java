package com.facundoroman.freelancers_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facundoroman.freelancers_api.exception.ResourceAlreadyExistsException;
import com.facundoroman.freelancers_api.exception.ResourceNotFoundException;
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
	    //  Verifica si el email ya existe
	    Optional<Profesional> existingProfesional = profesionalRepo.findByEmail(p.getEmail());
	    
	    if (existingProfesional.isPresent()) {
	        throw new ResourceAlreadyExistsException("Ya existe un profesional con el email: " + p.getEmail());
	    }
	    // Si el email no existe guarda el nuevo profesional
	    return profesionalRepo.save(p);
	}
	
	
	//buscar profesional por id
	public Profesional buscarProfesionalId(Long id) {
		
		return profesionalRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con ID: " + id));
		
	}
	
	
	
	//filtrar profesional por cuidad y habilidad
	
	public  List<Profesional> filtrarPorCiudadYHabilidad(String ciudad , String habilidad) {
		
		return profesionalRepo.findByCiudadAndHabilidadesContaining(ciudad, habilidad);
		
	}
	
	
	
	
	// NUEVO MÉTODO:
    // Permite encontrar un profesional por su email
    public Profesional findByEmail(String email) {
        return profesionalRepo.findByEmail(email)
                              .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con email: " + email));
    }
	
	

	
	//elimina profesional por id
	public Profesional eliminarProfesionalId(Long id) {
		
		profesionalRepo.deleteById(id);
		return null;
		
	}
	
	//actualiza profesional por id
	
	public Profesional actualizarProfesional(Long id, Profesional datosActualizados) {
	    // Lanzar ResourceNotFoundException si el profesional no existe
	    Profesional profesionalExistente = profesionalRepo.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con ID: " + id));

	    
	    if (!profesionalExistente.getEmail().equals(datosActualizados.getEmail())) {
	        Optional<Profesional> existingProfesionalWithNewEmail = profesionalRepo.findByEmail(datosActualizados.getEmail());
	   
	        if (existingProfesionalWithNewEmail.isPresent() && !existingProfesionalWithNewEmail.get().getId().equals(id)) {
	            throw new ResourceAlreadyExistsException("El nuevo email ya está en uso por otro profesional: " + datosActualizados.getEmail());
	        }
	    }

	 
	    profesionalExistente.setNombre(datosActualizados.getNombre());
	    profesionalExistente.setEspecialidad(datosActualizados.getEspecialidad());
	    profesionalExistente.setProvincia(datosActualizados.getProvincia());
	    profesionalExistente.setCiudad(datosActualizados.getCiudad());
	    profesionalExistente.setHabilidades(datosActualizados.getHabilidades());
	    profesionalExistente.setDescripcion(datosActualizados.getDescripcion());
	    profesionalExistente.setEmail(datosActualizados.getEmail()); 

	    return profesionalRepo.save(profesionalExistente);
	}
}
