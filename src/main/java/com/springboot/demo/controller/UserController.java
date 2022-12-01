package com.springboot.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.domain.PasswordResetToken;
import com.springboot.demo.domain.VerificationToken;
import com.springboot.demo.dto.LoginRequestDTO;
import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.dto.response.ResponseListDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.event.RegistrationCompleteEvent;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.PasswordResetService;
import com.springboot.demo.service.UserService;
import com.springboot.demo.service.VerificationTokenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Autowired
	private PasswordResetService passwordResetService;
	
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
	
	@GetMapping("/verifyRegistration")
	public String verifyRegistration(@RequestParam String token) {
		String result = verificationTokenService.validateVerificationToken(token);
		if (result.equalsIgnoreCase("valid")) {
			return "User Verified Successfully...";
		}
		return "Bad User...";
	}
	
	@GetMapping("resendVerifyToken")
	public String resendVerificationToken(@RequestParam String oldToken, HttpServletRequest request) {
		VerificationToken verificationToken = verificationTokenService.generateNewVerificationToken(oldToken);
		resendVerificationTokenMail(verificationToken, request);
		return "Verification mail sent..";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) throws TransformerException {
		UserDTO userDTO = userService.findUserByEmail(passwordDTO.getEmail());
		if (null != userDTO) {
			PasswordResetToken passwordResetToken = passwordResetService.createToken(userDTO);
			sendPasswordResetTokenMail(passwordResetToken, request);
			return "Password resend link send to your mail..";
		}
		return "Invalid email..";
	}
	
	@PostMapping("/reset/password")
	public String savePassword(@RequestParam String token, @RequestBody PasswordDTO passwordDTO) {
		String result = passwordResetService.validatePasswordResetTokenAndSavePassword(token, passwordDTO);
		return result;
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestBody PasswordDTO passwordDTO) throws TransformerException {
		return userService.changePassword(passwordDTO);
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception{
		return ResponseEntity.ok(userService.getToken(loginRequestDTO));
    }
	
	@GetMapping("/users")
	public ResponseListDTO<?> getAllRoles() throws TransformerException {
		
		List<UserDTO> userDTOList = userService.getAllUsers();
		ResponseListDTO<UserDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(userDTOList);
		response.setCount(userDTOList.size());
		return updateResponse(response);

	}

	private void sendPasswordResetTokenMail(PasswordResetToken passwordResetToken, HttpServletRequest request) {
		log.info("Click the link to reset your password: {}", getApplicationUrl(request)+"/savePassword?token="+passwordResetToken.getToken());
	}

	private void resendVerificationTokenMail(VerificationToken verificationToken, HttpServletRequest request) {
		log.info("Click the link to verify your account: {}", getApplicationUrl(request)+"/verifyRegistration?token="+verificationToken.getToken());
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
	
	private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}
