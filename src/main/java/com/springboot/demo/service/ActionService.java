package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.springboot.demo.dto.ActionDTO;
import com.springboot.demo.exception.TransformerException;


public interface ActionService {

	ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException;

	ActionDTO getActionById(Long id) throws EntityNotFoundException, TransformerException;

	List<ActionDTO> getAllActions() throws TransformerException;

}
