package com.springboot.demo.exception.controller;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.demo.dto.response.MessageDTO;
import com.springboot.demo.dto.response.ResultsDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.exception.TransformerException;

@ControllerAdvice
public class GlobalControllerExceptionHandler{
	
	private static final String TRANSFORMER_EXCEPTION = "Error transforming object";
	
	@ExceptionHandler(value = { EntityNotFoundException.class })
	protected ResponseEntity<ResultsDTO> handleEntityNotFoundExceptions(Exception ex, HttpServletResponse httpServletResponse) {
		ResultsDTO response = new ResultsDTO();
		response.setMessage(new MessageDTO(ex.getMessage()));
		response.setResultStatus(ResultStatus.FAILED);
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		response.setHttpCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { TransformerException.class })
	protected ResponseEntity<ResultsDTO> handleTransformerException(Exception ex, HttpServletResponse httpServletResponse) {
		ResultsDTO response = new ResultsDTO();
		response.setMessage(new MessageDTO(TRANSFORMER_EXCEPTION));
		response.setResultStatus(ResultStatus.FAILED);
		response.setHttpStatus(HttpStatus.CONFLICT);
		response.setHttpCode(String.valueOf(HttpStatus.CONFLICT.value()));
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<ResultsDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse httpServletResponse) {
		ResultsDTO response = new ResultsDTO();
		response.setMessage(new MessageDTO(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
		response.setResultStatus(ResultStatus.FAILED);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setHttpCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { JsonProcessingException.class })
	protected ResponseEntity<ResultsDTO> handleJsonProcessingException(JsonProcessingException ex, HttpServletResponse httpServletResponse) {
		ResultsDTO response = new ResultsDTO();
		response.setMessage(new MessageDTO(ex.getMessage()));
		response.setResultStatus(ResultStatus.FAILED);
		response.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		response.setHttpCode(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(value = { AuthenticationException.class })
	protected ResponseEntity<ResultsDTO> handleAuthenticationExceptions(Exception ex, HttpServletResponse httpServletResponse) {
		ResultsDTO response = new ResultsDTO();
		response.setMessage(new MessageDTO(ex.getMessage()));
		response.setResultStatus(ResultStatus.FAILED);
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setHttpCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = { AccessDeniedException.class })
	protected ResponseEntity<ResultsDTO> handleAccessDeniedExceptions(Exception ex, HttpServletResponse httpServletResponse) {
		ResultsDTO response = new ResultsDTO();
		response.setMessage(new MessageDTO(ex.getMessage()));
		response.setResultStatus(ResultStatus.FAILED);
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setHttpCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
}
