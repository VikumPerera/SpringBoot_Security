package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.constant.EntityNotFoundConstatnt;
import com.springboot.demo.domain.Permission;
import com.springboot.demo.dto.PermissionDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.ActionRepository;
import com.springboot.demo.repository.ComponentRepository;
import com.springboot.demo.repository.PermissionRepository;
import com.springboot.demo.transformer.PermissionTransformer;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private PermissionTransformer permissionTransformer;
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private ComponentRepository componentRepository;
	
	private final Logger LOGGER =LoggerFactory.getLogger(PermissionService.class);

	@Override
	public PermissionDTO savePermission(PermissionDTO permissionDTO) throws TransformerException {
		
		if (!actionRepository.existsById(permissionDTO.getActionDTO().getId())) {
			throw new EntityNotFoundException("Action not found...");
		}
		if (!componentRepository.existsById(permissionDTO.getActionDTO().getId())) {
			throw new EntityNotFoundException("Component not found...");
		}
		
		Permission permission = permissionTransformer.transformDTOToDomain(permissionDTO);
		return permissionTransformer.transformDomainToDTO(permissionRepository.saveAndFlush(permission));
	}

	@Override
	public PermissionDTO getPermissionById(Long id) throws EntityNotFoundException, TransformerException {
		if (!permissionRepository.existsById(id)) {
			throw new EntityNotFoundException(EntityNotFoundConstatnt.PERMISSION_NOT_FOUND + id);
		}
		return permissionTransformer.transformDomainToDTO(permissionRepository.findById(id).get());
	}

	@Override
	public List<PermissionDTO> getAllPermissions() throws TransformerException {
		return permissionTransformer.transformDomainToDTO(permissionRepository.findAll());
	}

}
