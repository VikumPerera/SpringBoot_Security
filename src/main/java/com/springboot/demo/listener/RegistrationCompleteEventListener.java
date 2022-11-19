package com.springboot.demo.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.User;
import com.springboot.demo.domain.VerificationToken;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.event.RegistrationCompleteEvent;
import com.springboot.demo.service.VerificationTokenService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>{
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		//Create the Verification Token for the User with the link
		UserDTO userDTO = event.getUserDTO();
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(new User(userDTO.getId()), token);
		verificationToken = tokenService.saveVerificationToken(verificationToken);
		
		//Send mail to User
		String url = event.getApplicationUrl()+"/verifyRegistration?token="+token;
		log.info("Click the link to verify your account: {}", url);
	}

}
