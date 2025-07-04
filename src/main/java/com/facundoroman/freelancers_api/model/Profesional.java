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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Profesional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El nombre no puede estar vacio")
	@Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") 
	private String nombre;
	
	@NotBlank(message = "La especialida no puede estar vacio")
	private String especialidad;
	
	@NotBlank(message = "La provincia no puede estar vacio")
	private String provincia;
	
	@NotBlank(message = "La ciudad no puede estar vacio")
	private String ciudad;

	@ElementCollection
	@CollectionTable(name = "profesional_habilidades", joinColumns = @JoinColumn(name = "profesional_id"))
	@Column(name = "habilidad") 
	@NotEmpty(message = "La lista de habilidades no puede estar vacia")
	private Set<String> habilidades;

	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "La descripcion no puede estar vacia")
	@Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
	private String descripcion;
	

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato valido")
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
