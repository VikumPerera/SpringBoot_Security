package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.exception.TransformerException;


public interface RoleService {

	RoleDTO saveRole(RoleDTO roleDTO) throws TransformerException;

	RoleDTO getRoleById(Long id) throws EntityNotFoundException, TransformerException;

	List<RoleDTO> getAllRoles() throws TransformerException;

}
