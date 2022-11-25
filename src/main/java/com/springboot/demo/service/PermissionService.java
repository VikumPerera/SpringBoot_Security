package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.springboot.demo.dto.PermissionDTO;
import com.springboot.demo.exception.TransformerException;


public interface PermissionService {

	PermissionDTO savePermission(PermissionDTO permissionDTO) throws TransformerException;

	PermissionDTO getPermissionById(Long id) throws EntityNotFoundException, TransformerException;

	List<PermissionDTO> getAllPermissions() throws TransformerException;

}
