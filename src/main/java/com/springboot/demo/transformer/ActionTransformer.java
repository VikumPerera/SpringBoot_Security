package com.springboot.demo.transformer;

import org.springframework.stereotype.Component;

import com.springboot.demo.domain.Action;
import com.springboot.demo.dto.ActionDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.util.SaasUtil;

@Component
public class ActionTransformer extends AbstractTransformer<Action, ActionDTO> {

	@Override
	public ActionDTO transformDomainToDTO(Action domain) throws TransformerException {
		ActionDTO dto = new ActionDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		return dto;
	}

	@Override
	public Action transformDTOToDomain(ActionDTO dto) throws TransformerException {
		Action domain = new Action();
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
