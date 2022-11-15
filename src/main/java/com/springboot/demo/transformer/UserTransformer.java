package com.springboot.demo.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.User;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;

@Component
public class UserTransformer extends AbstractTransformer<User, UserDTO> {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO transformDomainToDTO(User domain) throws TransformerException {
		UserDTO dto = new UserDTO();
		dto.setId(domain.getId());
		dto.setEmail(domain.getEmail());
		dto.setFirstName(domain.getFirstName());
		dto.setLastName(domain.getLastName());
		return dto;
	}

	@Override
	public User transformDTOToDomain(UserDTO dto) throws TransformerException {
		User domain = new User();
		if (null != dto.getId()) {
			domain.setId(dto.getId());
		}
		domain.setFirstName(dto.getFirstName());
		domain.setLastName(dto.getLastName());
		domain.setEmail(dto.getEmail());
		domain.setPassword(passwordEncoder.encode(dto.getPassword()));
		return domain;
	}

	

}
