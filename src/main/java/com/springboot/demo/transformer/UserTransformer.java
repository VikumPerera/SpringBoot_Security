package com.springboot.demo.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.Role;
import com.springboot.demo.domain.User;
import com.springboot.demo.domain.UserRole;
import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.RoleRepository;
import com.springboot.demo.util.SaasUtil;

@Component
public class UserTransformer extends AbstractTransformer<User, UserDTO> {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleTransformer roleTransformer;

	@Override
	public UserDTO transformDomainToDTO(User domain) throws TransformerException {
		UserDTO dto = new UserDTO();
		dto.setId(domain.getId());
		dto.setEmail(domain.getEmail());
		dto.setFirstName(domain.getFirstName());
		dto.setLastName(domain.getLastName());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		if (null != domain.getUserRoles()) {
			List<Role>  roleList = new ArrayList<>();
			for (UserRole userRole : domain.getUserRoles()) {
				roleList.add(userRole.getRole());
			}
			dto.setRoleDTOs(roleTransformer.transformDomainToDTO(roleList));
		}
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
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
		domain.setModifiedDate(SaasUtil.timeStampGenerator());
		List<UserRole>  userRoleList = new ArrayList<>();
		for (RoleDTO roleDTO : dto.getRoleDTOs()) {
			UserRole userRole = new UserRole();
			userRole.setRole(roleRepository.findById(roleDTO.getId()).get());
			userRole.setCreatedDate(null != roleDTO.getCreatedDate() ? roleDTO.getCreatedDate() : SaasUtil.timeStampGenerator());
			userRole.setModifiedDate(SaasUtil.timeStampGenerator());
			userRoleList.add(userRole);
		}
		domain.setUserRoles(userRoleList);
		return domain;
	}

	

}
