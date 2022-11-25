package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.constant.EntityNotFoundConstatnt;
import com.springboot.demo.domain.Component;
import com.springboot.demo.dto.ComponentDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.ComponentRepository;
import com.springboot.demo.transformer.ComponentTransformer;

@Service
public class ComponentServiceImpl implements ComponentService {
	
	@Autowired
	ComponentRepository componentRepository;
	
	@Autowired
	ComponentTransformer componentTransformer;
	
	private final Logger LOGGER =LoggerFactory.getLogger(ComponentService.class);

	@Override
	public ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException {
		Component component = componentTransformer.transformDTOToDomain(componentDTO);
		return componentTransformer.transformDomainToDTO(componentRepository.saveAndFlush(component));
	}

	@Override
	public ComponentDTO getComponentById(Long id) throws EntityNotFoundException, TransformerException {
		if (!componentRepository.existsById(id)) {
			throw new EntityNotFoundException(EntityNotFoundConstatnt.COMPONENT_NOT_FOUND + id);
		}
		return componentTransformer.transformDomainToDTO(componentRepository.findById(id).get());
	}

	@Override
	public List<ComponentDTO> getAllComponents() throws TransformerException {
		return componentTransformer.transformDomainToDTO(componentRepository.findAll());
	}

}
