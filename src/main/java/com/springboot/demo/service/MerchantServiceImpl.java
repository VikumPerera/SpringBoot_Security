package com.springboot.demo.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.constant.EntityNotFoundConstatnt;
import com.springboot.demo.domain.Merchant;
import com.springboot.demo.dto.MerchantDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.MerchantRepository;
import com.springboot.demo.transformer.MerchantTransformer;
import com.springboot.demo.util.SaasUtil;

@Service
public class MerchantServiceImpl implements MerchantService {
	
	@Autowired
	MerchantRepository merchantRepository;
	
	@Autowired
	MerchantTransformer merchantTransformer;
	
	private final Logger LOGGER =LoggerFactory.getLogger(MerchantServiceImpl.class);

	@Override
	public MerchantDTO saveMerchant(MerchantDTO merchantDTO) throws TransformerException {
		Merchant merchant = merchantTransformer.transformDTOToDomain(merchantDTO);
		return merchantTransformer.transformDomainToDTO(merchantRepository.saveAndFlush(merchant));
	}

	@Override
	public MerchantDTO getMerchantById(Long id) throws EntityNotFoundException, TransformerException {
		if (!merchantRepository.existsById(id)) {
			throw new EntityNotFoundException(EntityNotFoundConstatnt.MERCHANT_NOT_FOUND + id);
		}
		return merchantTransformer.transformDomainToDTO(merchantRepository.findById(id).get());
	}

	@Override
	public List<MerchantDTO> getAllMerchants() throws TransformerException {
		return merchantTransformer.transformDomainToDTO(merchantRepository.findAll());
	}

	@Override
	public MerchantDTO updateMerchantById(Long id, MerchantDTO merchantDTO) throws TransformerException {
		Merchant savedMerchant = merchantRepository.findById(id).orElse(null);
		
		if (null != savedMerchant) {
			if (Objects.nonNull(merchantDTO.getName()) && !"".equalsIgnoreCase(merchantDTO.getName())) {
				savedMerchant.setName(merchantDTO.getName());
			}
			if (Objects.nonNull(merchantDTO.getCode()) && !"".equalsIgnoreCase(merchantDTO.getCode())) {
				savedMerchant.setCode(merchantDTO.getCode());
			}
			savedMerchant.setModifiedDate(SaasUtil.timeStampGenerator());
			return merchantTransformer.transformDomainToDTO(merchantRepository.saveAndFlush(savedMerchant));
			
		}
		
		return null;
	}

	@Override
	public void deleteMerchantById(Long id) {
		merchantRepository.deleteById(id);
	}


}
