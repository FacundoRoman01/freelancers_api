package com.facundoroman.freelancers_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facundoroman.freelancers_api.model.Profesional;


@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

	List<Profesional> findByCiudadAndHabilidadesContaining (String Ciudad, String habilidad);
	
	Optional<Profesional> findByEmail(String email);
	
	
}
 