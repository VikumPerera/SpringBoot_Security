package com.springboot.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.User;
import com.springboot.demo.dto.JWTResponseDTO;
import com.springboot.demo.dto.LoginRequestDTO;
import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.RoleRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.transformer.UserTransformer;
import com.springboot.demo.util.JWTUtility;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserTransformer userTransformer;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	private RoleService roleService;

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws TransformerException {
		
		for (RoleDTO roleDTO : userDTO.getRoleDTOs()) {
			if (!roleRepository.existsById(roleDTO.getId())) {
				throw new EntityNotFoundException("Role not found...");
			}
		}
		User user = userTransformer.transformDTOToDomain(userDTO);
		user.getUserRoles().stream().forEach(userRole -> userRole.setUser(user));
		return userTransformer.transformDomainToDTO(userRepository.saveAndFlush(user));
	}


	@Override
	public UserDTO findUserByEmail(String email) throws TransformerException {
		User user = userRepository.findByEmail(email);
		if (null != user) {
			return userTransformer.transformDomainToDTO(user);
		}
		return null;
	}


	@Override
	public String changePassword(PasswordDTO passwordDTO) {
		User user = userRepository.findByEmail(passwordDTO.getEmail());
		if (null != user) {
			if (passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
				user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
				userRepository.saveAndFlush(user);
				return "Successfully changed the password...";
			}
			return "Invalid old password...";
		}
		return "Invalid user email...";
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email);
		
		if (null == user) {
			throw new UsernameNotFoundException("No user found");
		}
		

			try {
				return new org.springframework.security.core.userdetails.User(
						user.getEmail(), 
						user.getPassword(), 
						user.isEnabled(), 
						true, true, true, 
						getAuthority(user));
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		return null;
	}

	private Set<? extends GrantedAuthority> getAuthority(User user) throws EntityNotFoundException, TransformerException {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		RoleDTO roleDTO = roleService.getRoleById(user.getUserRoles().get(0).getRole().getId());
		
		roleDTO.getPermissionDTOs().forEach(permissionDTO -> {
    	  authorities.add(new SimpleGrantedAuthority(permissionDTO.getCode()));
      });
        return authorities;
	}


	public JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) {
		
		final UserDetails userDetails = loadUserByUsername(loginRequestDTO.getUserName());
		if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
			final String token = jwtUtility.generateToken(userDetails);
			return new JWTResponseDTO(token);
		}
		throw new BadCredentialsException("Invalid Credintials...");
	}


	@Override
	public List<UserDTO> getAllUsers() throws TransformerException {
		return userTransformer.transformDomainToDTO(userRepository.findAll());
	}

}
