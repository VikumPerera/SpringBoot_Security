package com.springboot.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.event.RegistrationCompleteEvent;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.TokenService;
import com.springboot.demo.service.UserService;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping("/register")
	public ResponseDTO<?> registerUser(@RequestBody UserDTO userDTO, final HttpServletRequest request) throws TransformerException {
		ResponseDTO<UserDTO> response = new ResponseDTO<>();
		userDTO = userService.registerUser(userDTO);
		response.setPayload(userDTO);
		publisher.publishEvent(new RegistrationCompleteEvent(userDTO, getApplicationUrl(request)));
        return updateResponse(response);
	}
	
	@GetMapping("verifyRegistration")
	public String verifyRegistration(@RequestParam String token) {
		String result = tokenService.validateVerificationToken(token);
		if (result.equalsIgnoreCase("valid")) {
			return "User Verified Successfully...";
		}
		return "Bad User...";
	}
	
	
	private String getApplicationUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}


	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}
