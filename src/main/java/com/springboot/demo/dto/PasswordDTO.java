package com.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {

	private String email;
	private String oldPassword;
	private String newPassword;
	
}
