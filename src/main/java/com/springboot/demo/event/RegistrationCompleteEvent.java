package com.springboot.demo.event;

import org.springframework.context.ApplicationEvent;

import com.springboot.demo.dto.UserDTO;

import lombok.Data;
@Data
public class RegistrationCompleteEvent extends ApplicationEvent{
	
	private UserDTO userDTO;
	private String applicationUrl;

	public RegistrationCompleteEvent(UserDTO userDTO, String applicationUrl) {
		super(userDTO);
		this.userDTO = userDTO;
		this.applicationUrl = applicationUrl;
	}

}
