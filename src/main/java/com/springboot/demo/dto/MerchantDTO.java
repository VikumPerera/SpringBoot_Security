package com.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDTO {

	private Long id;
	private String name;
	private String code;
	private Long createdDate;
	private Long modifiedDate;

}
