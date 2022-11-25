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

import com.springboot.demo.dto.ComponentDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.ComponentService;

@RestController
public class ComponentController {
	
	@Autowired
	private ComponentService componentService;
	
	@PostMapping("/components")
	public ResponseDTO<?> saveComponent(@Valid @RequestBody ComponentDTO componentDTO) throws TransformerException {
		
		ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
		response.setPayload(componentService.saveComponent(componentDTO));
        return updateResponse(response);
	}

	@GetMapping("/components/{id}")
	public ResponseDTO<?> getComponentById(@PathVariable Long id) throws EntityNotFoundException, TransformerException {

		ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
		response.setPayload(componentService.getComponentById(id));
		return updateResponse(response);
		
	}
	
	@GetMapping("/components")
	public List<ComponentDTO> getAllComponents() throws TransformerException {
		return componentService.getAllComponents();
	}
	
	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}
