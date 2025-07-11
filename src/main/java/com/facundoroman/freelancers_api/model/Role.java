package com.facundoroman.freelancers_api.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre no puede estar vacio")
	@Column(unique = true, nullable = false)
	private String name;

	
	//contructor para jpa
	public Role() {

	}
	
	//contructor

	public Role(String name) {

		this.name = name;

	}

	
	
	//getter
	public Long getId() {

		return id;
	}

	public String getName() {
		return name;
	}

	
	//setter
	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Override 
    public String toString() {
       
        return "Role{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    @Override 
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false; // Si es nulo o de diferente clase, no son iguales
        Role role = (Role) o; 
        return Objects.equals(id, role.id) && 
               Objects.equals(name, role.name); 
    }

    @Override 
    public int hashCode() {
        return Objects.hash(id, name); // Genera un hash basado en los campos que definen la igualdad.
    }

}
