package com.springboot.demo.transformer;

import org.springframework.stereotype.Component;

import com.springboot.demo.domain.Merchant;
import com.springboot.demo.dto.MerchantDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.util.SaasUtil;

@Component
public class MerchantTransformer extends AbstractTransformer<Merchant, MerchantDTO> {
	

	@Override
	public MerchantDTO transformDomainToDTO(Merchant domain) throws TransformerException {
		MerchantDTO dto = new MerchantDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		return dto;
	}

	@Override
	public Merchant transformDTOToDomain(MerchantDTO dto) throws TransformerException {
		Merchant domain = new Merchant();
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
