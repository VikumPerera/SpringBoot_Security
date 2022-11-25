package com.springboot.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentDTO {

	private Long id;
	private String name;
	private String code;
	private Long createdDate;
	private Long modifiedDate;
    private List<PermissionDTO> permissionDTOs;

}
