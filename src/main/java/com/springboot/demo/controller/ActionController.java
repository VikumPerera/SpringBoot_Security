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

import com.springboot.demo.dto.ActionDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.dto.response.ResponseListDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.ActionService;

@RestController
public class ActionController {
	
	@Autowired
	private ActionService actionService;
	
	@PostMapping("/actions")
	public ResponseDTO<?> saveAction(@Valid @RequestBody ActionDTO actionDTO) throws TransformerException {
		
		ResponseDTO<ActionDTO> response = new ResponseDTO<>();
		response.setPayload(actionService.saveAction(actionDTO));
        return updateResponse(response);
	}

	@GetMapping("/actions/{id}")
	public ResponseDTO<?> getActionById(@PathVariable Long id) throws EntityNotFoundException, TransformerException {

		ResponseDTO<ActionDTO> response = new ResponseDTO<>();
		response.setPayload(actionService.getActionById(id));
		return updateResponse(response);
		
	}
	
	@GetMapping("/actions")
	public ResponseListDTO<?> getAllActions() throws TransformerException {
		
		List<ActionDTO> actionDTOList = actionService.getAllActions();
		ResponseListDTO<ActionDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(actionDTOList);
		response.setCount(actionDTOList.size());
		return updateResponse(response);

	}
	
	private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}

	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}
