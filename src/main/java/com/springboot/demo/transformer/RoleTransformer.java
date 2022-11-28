package com.springboot.demo.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.demo.domain.Permission;
import com.springboot.demo.domain.Role;
import com.springboot.demo.domain.RolePermission;
import com.springboot.demo.dto.PermissionDTO;
import com.springboot.demo.dto.RoleDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.PermissionRepository;
import com.springboot.demo.util.SaasUtil;

@Component
public class RoleTransformer extends AbstractTransformer<Role, RoleDTO> {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private PermissionTransformer permissionTransformer;

	@Override
	public RoleDTO transformDomainToDTO(Role domain) throws TransformerException {
		RoleDTO dto = new RoleDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		if (null != domain.getRolePermissions()) {
			List<Permission>  permissionList = new ArrayList<>();
			for (RolePermission rolePermission : domain.getRolePermissions()) {
				permissionList.add(rolePermission.getPermission());
			}
			dto.setPermissionDTOs(permissionTransformer.transformDomainToDTO(permissionList));
		}
		
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
		List<RolePermission>  rolePermissionList = new ArrayList<>();
		for (PermissionDTO permissionDTO : dto.getPermissionDTOs()) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermission(permissionRepository.findById(permissionDTO.getId()).get());
			rolePermission.setCreatedDate(null != permissionDTO.getCreatedDate() ? permissionDTO.getCreatedDate() : SaasUtil.timeStampGenerator());
			rolePermission.setModifiedDate(SaasUtil.timeStampGenerator());
			rolePermissionList.add(rolePermission);
		}
		domain.setRolePermissions(rolePermissionList);
		return domain;
	}

}
