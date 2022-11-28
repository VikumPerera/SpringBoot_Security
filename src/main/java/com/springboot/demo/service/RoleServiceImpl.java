package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.constant.EntityNotFoundConstatnt;
import com.springboot.demo.domain.Role;
import com.springboot.demo.dto.PermissionDTO;
import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.PermissionRepository;
import com.springboot.demo.repository.RoleRepository;
import com.springboot.demo.transformer.RoleTransformer;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleTransformer roleTransformer;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	private final Logger LOGGER =LoggerFactory.getLogger(RoleService.class);

	@Override
	public RoleDTO saveRole(RoleDTO roleDTO) throws TransformerException {
		
		for (PermissionDTO permissionDTO : roleDTO.getPermissionDTOs()) {
			if (!permissionRepository.existsById(permissionDTO.getId())) {
				throw new EntityNotFoundException("Permission not found...");
			}
		}
		Role role = roleTransformer.transformDTOToDomain(roleDTO);
		role.getRolePermissions().stream().forEach(rolePermission -> rolePermission.setRole(role));
		return roleTransformer.transformDomainToDTO(roleRepository.saveAndFlush(role));
	}

	@Override
	public RoleDTO getRoleById(Long id) throws EntityNotFoundException, TransformerException {
		if (!roleRepository.existsById(id)) {
			throw new EntityNotFoundException(EntityNotFoundConstatnt.ROLE_NOT_FOUND + id);
		}
		return roleTransformer.transformDomainToDTO(roleRepository.findById(id).get());
	}

	@Override
	public List<RoleDTO> getAllRoles() throws TransformerException {
		return roleTransformer.transformDomainToDTO(roleRepository.findAll());
	}

}
