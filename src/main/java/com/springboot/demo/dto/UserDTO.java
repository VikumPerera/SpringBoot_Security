package com.springboot.demo.dto;

import java.util.List;

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
	private List<RoleDTO> roleDTOs;
	private Long createdDate;
	private Long modifiedDate;
	
}
