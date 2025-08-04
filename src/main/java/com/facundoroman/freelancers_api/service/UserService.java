package com.facundoroman.freelancers_api.service;

import java.util.Collection;
import java.util.Collections;

import java.util.Set;
import java.util.stream.Collectors;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.facundoroman.freelancers_api.dto.UserDTO;
import com.facundoroman.freelancers_api.exception.ResourceAlreadyExistsException;
import com.facundoroman.freelancers_api.exception.ResourceNotFoundException;
import com.facundoroman.freelancers_api.model.Role;
import com.facundoroman.freelancers_api.model.User;
import com.facundoroman.freelancers_api.repository.RoleRepository;
import com.facundoroman.freelancers_api.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {
	
	 	private final UserRepository userRepository;
	    private final RoleRepository roleRepository;
	    private final PasswordEncoder passwordEncoder;

	    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	        this.passwordEncoder = passwordEncoder;
	    }
	    
	    
	    
	    /**
	     * Registra un nuevo usuario en la base de datos.
	     * Encripta la contraseña y asigna el rol "ROLE_USER" por defecto.
	     *
	     * @param user El objeto User con los datos del nuevo usuario (contraseña sin encriptar).
	     * @return El objeto User guardado.
	     * @throws ResourceAlreadyExistsException Si el nombre de usuario o el email ya existen. // ¡Excepción específica!
	     * @throws RuntimeException Si el rol "ROLE_USER" no se encuentra.
	     */
	    public User registerNewUser(User user) {
	    	
	        if (userRepository.existsByUsername(user.getUsername())) {
	            // Lanza ResourceAlreadyExistsException en lugar de RuntimeException
	            throw new ResourceAlreadyExistsException("El nombre de usuario '" + user.getUsername() + "' ya existe. Por favor, elige otro.");
	        }
	        
	        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
	            // Lanza ResourceAlreadyExistsException en lugar de RuntimeException
	            throw new ResourceAlreadyExistsException("El email '" + user.getEmail() + "' ya está registrado. Por favor, elige otro.");
	        }
	        

	        user.setPassword(passwordEncoder.encode(user.getPassword()));

	        Role userRole = roleRepository.findByName("ROLE_USER")
	                                      .orElseThrow(() -> new RuntimeException("El rol 'ROLE_USER' no existe en la base de datos. " +
	                                              "Por favor, créalo manualmente o asegura que tu inicializador de datos lo haga."));

	        user.setRoles(Collections.singleton(userRole));

	        return userRepository.save(user);
	    }

	    
	    
	    

	    /**
	     * Carga los detalles de un usuario por su nombre de usuario.
	     * Utilizado por Spring Security durante el proceso de autenticación.
	     *
	     * @param username El nombre de usuario.
	     * @return Un objeto UserDetails de Spring Security.
	     * @throws UsernameNotFoundException Si el usuario no es encontrado.
	     */
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findByUsername(username)
	                                  .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

	        return new org.springframework.security.core.userdetails.User(
	            user.getUsername(),
	            user.getPassword(),
	            mapRolesToAuthorities(user.getRoles())
	        );
	    }
	    
	    
	    
	    
	    /**
		 * NUEVO MÉTODO: Encuentra la entidad User completa por su nombre de usuario.
		 * Útil para obtener la información completa del usuario, como el email.
		 *
		 * @param username El nombre de usuario.
		 * @return La entidad User.
		 * @throws ResourceNotFoundException Si el usuario no es encontrado.
		 */
		public User findByUsername(String username) {
			return userRepository.findByUsername(username)
									.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
		}
		
		
		
		
		
		
		/**
		 * NUEVO MÉTODO: Encuentra un usuario y lo mapea a un DTO para una respuesta segura.
		 *
		 * @param username El nombre de usuario.
		 * @return Un objeto UserDTO con datos básicos del usuario.
		 * @throws ResourceNotFoundException Si el usuario no es encontrado.
		 */
		public UserDTO findUserDTOByUsername(String username) {
			User user = findByUsername(username);
			
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setUsername(user.getUsername());
			userDTO.setEmail(user.getEmail());
			userDTO.setRoles(user.getRoles().stream()
									.map(role -> role.getName())
									.collect(Collectors.toSet()));
			
			return userDTO;
		}
	    
	    
	    
	    
	    
	    

	    /**
	     * Mapea un conjunto de objetos Role (de tu modelo)
	     * a una colección de objetos GrantedAuthority (requerido por Spring Security).
	     *
	     * @param roles El conjunto de roles asociados al usuario.
	     * @return Una colección de GrantedAuthority.
	     */
	    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
	        if (roles == null) {
	            return Collections.emptyList();
	        }
	        return roles.stream()
	                    .map(role -> new SimpleGrantedAuthority(role.getName()))
	                    .collect(Collectors.toList());
	    }



}
