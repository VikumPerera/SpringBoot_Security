package com.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDTO {

	private Long id;
	private String code;
    private ComponentDTO componentDTO;
    private ActionDTO actionDTO;
    private Long createdDate;
	private Long modifiedDate;
    
}
