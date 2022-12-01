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

import com.springboot.demo.dto.PermissionDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.dto.response.ResponseListDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.PermissionService;

@RestController
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@PostMapping("/permissions")
	public ResponseDTO<?> savePermission(@Valid @RequestBody PermissionDTO permissionDTO) throws TransformerException {
		
		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.savePermission(permissionDTO));
        return updateResponse(response);
	}

	@GetMapping("/permissions/{id}")
	public ResponseDTO<?> getPermissionById(@PathVariable Long id) throws EntityNotFoundException, TransformerException {

		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.getPermissionById(id));
		return updateResponse(response);
		
	}
	
	@GetMapping("/permissions")
	public ResponseListDTO<?> getAllPermissions() throws TransformerException {
		
		List<PermissionDTO> permissionDTOList = permissionService.getAllPermissions();
		ResponseListDTO<PermissionDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(permissionDTOList);
		response.setCount(permissionDTOList.size());
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
