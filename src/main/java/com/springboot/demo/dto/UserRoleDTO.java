package com.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDTO {

	private Long id;
	private Long createdDate;
	private Long modifiedDate;
    private UserDTO userDTO;
    private RoleDTO roleDTO;
	
	
}
