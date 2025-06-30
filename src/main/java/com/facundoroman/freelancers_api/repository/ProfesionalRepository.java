package com.facundoroman.freelancers_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facundoroman.freelancers_api.model.Profesional;


@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

}
