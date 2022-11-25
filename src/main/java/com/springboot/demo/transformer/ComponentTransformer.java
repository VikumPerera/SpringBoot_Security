package com.springboot.demo.transformer;

import org.springframework.stereotype.Component;

import com.springboot.demo.dto.ComponentDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.util.SaasUtil;

@Component
public class ComponentTransformer extends AbstractTransformer<com.springboot.demo.domain.Component, ComponentDTO> {

	@Override
	public ComponentDTO transformDomainToDTO(com.springboot.demo.domain.Component domain) throws TransformerException {
		ComponentDTO dto = new ComponentDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		return dto;
	}

	@Override
	public com.springboot.demo.domain.Component transformDTOToDomain(ComponentDTO dto) throws TransformerException {
		com.springboot.demo.domain.Component domain = new com.springboot.demo.domain.Component();
		if (null != dto.getId()) {
			domain.setId(dto.getId());
		}
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
		domain.setModifiedDate(SaasUtil.timeStampGenerator());
		return domain;
	}

}
