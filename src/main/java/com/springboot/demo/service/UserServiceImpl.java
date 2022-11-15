package com.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.User;
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
	

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws TransformerException {
		User user = userTransformer.transformDTOToDomain(userDTO);
		return userTransformer.transformDomainToDTO(userRepository.saveAndFlush(user));
	}

}
