package com.facundoroman.freelancers_api.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre de usuario no puede estar vacio")
	
	@Column(unique = true, nullable = false)
	private String username;

	@NotBlank(message = "La contraseña no puede estar vacio")
	@Column(nullable = false)
	private String password;

	@Email(message = "El email debe tener un formato valido")
	@Column(unique = true, nullable = false)
	private String email;
	
	
	//Un User puede tener muchos Roles
	//Un Role puede ser asignado a muchos Users
	@ManyToMany(fetch = FetchType.EAGER) // EAGER para cargar los roles junto con el usuario
	@JoinTable(name = "user_roles", // Nombre de la tabla 
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id") 
	)
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User(String username, String password, String email, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	

	public Set<Role> getRoles() {
		
		return roles;
	}


	public void setUsername(String userName) {
		this.username = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", roles="
				+ roles.stream().map(Role::getName).collect(Collectors.joining(", ")) + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username)
				&& Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email);
	}

}
