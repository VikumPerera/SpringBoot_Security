package com.springboot.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.User;
import com.springboot.demo.dto.JWTResponseDTO;
import com.springboot.demo.dto.LoginRequestDTO;
import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;
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
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTUtility jwtUtility;

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws TransformerException {
		User user = userTransformer.transformDTOToDomain(userDTO);
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
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				user.isEnabled(), 
				true, true, true, 
				new ArrayList<>());
	}

	public JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) {
		
		final UserDetails userDetails = loadUserByUsername(loginRequestDTO.getUserName());
		if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
			final String token = jwtUtility.generateToken(userDetails);
			return new JWTResponseDTO(token);
		}
		throw new BadCredentialsException("Invalid Credintials...");
	}

}
