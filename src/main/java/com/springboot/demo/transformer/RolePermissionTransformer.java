package com.springboot.demo.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.RolePermission;
import com.springboot.demo.dto.RolePermissionDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.util.SaasUtil;

@Component
public class RolePermissionTransformer extends AbstractTransformer<RolePermission, RolePermissionDTO> {
	
	@Autowired
	private PermissionTransformer permissionransformer;

	@Override
	public RolePermissionDTO transformDomainToDTO(RolePermission domain) throws TransformerException {
		RolePermissionDTO dto = new RolePermissionDTO();
		dto.setId(dto.getId());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		dto.setPermissionDTO(permissionransformer.transformDomainToDTO(domain.getPermission()));
		return dto;
	}

	@Override
	public RolePermission transformDTOToDomain(RolePermissionDTO dto) throws TransformerException {
		RolePermission domain = new RolePermission();
		if (null != dto.getId()) {
			domain.setId(dto.getId());
		}
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
		domain.setModifiedDate(SaasUtil.timeStampGenerator());
		domain.setPermission(permissionransformer.transformDTOToDomain(dto.getPermissionDTO()));
		return domain;
	}

	@Override
	public List<RolePermissionDTO> transformDomainToDTO(List<RolePermission> domains) throws TransformerException {
		List<RolePermissionDTO> dtos = new ArrayList<>();
        for (RolePermission domain : domains) {
            dtos.add(transformDomainToDTO(domain));
        }
        return dtos;
	}

	@Override
	public List<RolePermission> transformDTOToDomain(List<RolePermissionDTO> dtos) throws TransformerException {
		List<RolePermission> domains = new ArrayList<>();
        for (RolePermissionDTO dto : dtos) {
            domains.add(transformDTOToDomain(dto));
        }
        return domains;
	}
	
	

}
