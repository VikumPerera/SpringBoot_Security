package com.springboot.demo.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.Permission;
import com.springboot.demo.dto.PermissionDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.util.SaasUtil;

@Component
public class PermissionTransformer extends AbstractTransformer<Permission, PermissionDTO> {
	
	@Autowired
	private ActionTransformer actionTransformer;
	
	@Autowired
	private ComponentTransformer componentTransformer;

	@Override
	public PermissionDTO transformDomainToDTO(Permission domain) throws TransformerException {
		PermissionDTO dto = new PermissionDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setActionDTO(actionTransformer.transformDomainToDTO(domain.getAction()));
		dto.setComponentDTO(componentTransformer.transformDomainToDTO(domain.getComponent()));
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		return dto;
	}

	@Override
	public com.springboot.demo.domain.Permission transformDTOToDomain(PermissionDTO dto) throws TransformerException {
		Permission domain = new Permission();
		if (null != dto.getId()) {
			domain.setId(dto.getId());
		}
		domain.setCode(dto.getCode());
		domain.setAction(actionTransformer.transformDTOToDomain(dto.getActionDTO()));
		domain.setComponent(componentTransformer.transformDTOToDomain(dto.getComponentDTO()));
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
		domain.setModifiedDate(SaasUtil.timeStampGenerator());
		return domain;
	}

}
