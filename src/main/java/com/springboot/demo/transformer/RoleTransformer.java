package com.springboot.demo.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.Role;
import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.util.SaasUtil;

@Component
public class RoleTransformer extends AbstractTransformer<Role, RoleDTO> {
	
	@Autowired
	private RolePermissionTransformer rolePermissionTransformer;

	@Override
	public RoleDTO transformDomainToDTO(Role domain) throws TransformerException {
		RoleDTO dto = new RoleDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		dto.setRolePermissionDTOs(rolePermissionTransformer.transformDomainToDTO(domain.getRolePermissions()));
		return dto;
	}

	@Override
	public com.springboot.demo.domain.Role transformDTOToDomain(RoleDTO dto) throws TransformerException {
		Role domain = new Role();
		if (null != dto.getId()) {
			domain.setId(dto.getId());
		}
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
		domain.setModifiedDate(SaasUtil.timeStampGenerator());
		domain.setRolePermissions(rolePermissionTransformer.transformDTOToDomain(dto.getRolePermissionDTOs()));
		return domain;
	}

}
