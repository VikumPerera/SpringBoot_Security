package com.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String matchingPassword;
	
}
