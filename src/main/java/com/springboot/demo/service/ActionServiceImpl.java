package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.constant.EntityNotFoundConstatnt;
import com.springboot.demo.domain.Action;
import com.springboot.demo.dto.ActionDTO;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.repository.ActionRepository;
import com.springboot.demo.transformer.ActionTransformer;

@Service
public class ActionServiceImpl implements ActionService {
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private ActionTransformer actionTransformer;
	
	private final Logger LOGGER =LoggerFactory.getLogger(ActionService.class);

	@Override
	public ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException {
		Action action = actionTransformer.transformDTOToDomain(actionDTO);
		return actionTransformer.transformDomainToDTO(actionRepository.saveAndFlush(action));
	}

	@Override
	public ActionDTO getActionById(Long id) throws EntityNotFoundException, TransformerException {
		if (!actionRepository.existsById(id)) {
			throw new EntityNotFoundException(EntityNotFoundConstatnt.ACTION_NOT_FOUND + id);
		}
		return actionTransformer.transformDomainToDTO(actionRepository.findById(id).get());
	}

	@Override
	public List<ActionDTO> getAllActions() throws TransformerException {
		return actionTransformer.transformDomainToDTO(actionRepository.findAll());
	}

}
