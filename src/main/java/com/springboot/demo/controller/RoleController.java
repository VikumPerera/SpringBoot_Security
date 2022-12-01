package com.springboot.demo.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.dto.response.ResponseListDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/roles")
	public ResponseDTO<?> saveRole(@Valid @RequestBody RoleDTO roleDTO) throws TransformerException {
		
		ResponseDTO<RoleDTO> response = new ResponseDTO<>();
		response.setPayload(roleService.saveRole(roleDTO));
        return updateResponse(response);
	}

	@GetMapping("/roles/{id}")
	public ResponseDTO<?> getRoleById(@PathVariable Long id) throws EntityNotFoundException, TransformerException {

		ResponseDTO<RoleDTO> response = new ResponseDTO<>();
		response.setPayload(roleService.getRoleById(id));
		return updateResponse(response);
		
	}
	
	@GetMapping("/roles")
	public ResponseListDTO<?> getAllRoles() throws TransformerException {
		
		List<RoleDTO> roleDTOList = roleService.getAllRoles();
		ResponseListDTO<RoleDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(roleDTOList);
		response.setCount(roleDTOList.size());
		return updateResponse(response);

	}
	
	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
	private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}
