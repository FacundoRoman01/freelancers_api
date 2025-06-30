package com.facundoroman.freelancers_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Profesional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String especialidad;
	private String provincia;
	private String ciudad;

	@ElementCollection
	@CollectionTable(name = "profesional_habilidades", joinColumns = @JoinColumn(name = "profesional_id"))
	@Column(name = "habilidad") 
	private Set<String> habilidades;

	@Column(columnDefinition = "TEXT")
	private String descripcion;
	private String email;

	public Profesional() {
		this.habilidades = new HashSet<>();
	}

	public Profesional(String nombre, String especialidad, String provincia, String ciudad, Set<String> habilidades,
			String descripcion, String email) {
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.provincia = provincia;
		this.ciudad = ciudad;
		this.habilidades = (habilidades != null) ? new HashSet<>(habilidades) : new HashSet<>();
		this.descripcion = descripcion;
		this.email = email;
	}

	// getter
	public String getNombre() {
		return nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public Set<String> getHabilidades() {
		return habilidades;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getEmail() {
		return email;
	}

	// setter

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setCiudad(String ciudad) { 
		this.ciudad = ciudad;
	}

	public void setHabilidades(Set<String> habilidades) { 
		this.habilidades = (habilidades != null) ? new HashSet<>(habilidades) : new HashSet<>();
	}

	public void setDescripcion(String descripcion) { 
		this.descripcion = descripcion;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
