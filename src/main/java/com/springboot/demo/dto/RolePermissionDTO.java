package com.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionDTO {

	private Long id;
	private Long createdDate;
	private Long modifiedDate;
    private PermissionDTO permissionDTO;
    private RoleDTO roleDTO;
	
	
}
