package com.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.User;
import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.transformer.UserTransformer;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserTransformer userTransformer;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

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

}
