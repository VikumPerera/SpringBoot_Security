package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.springboot.demo.dto.ComponentDTO;
import com.springboot.demo.exception.TransformerException;


public interface ComponentService {

	ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException;

	ComponentDTO getComponentById(Long id) throws EntityNotFoundException, TransformerException;

	List<ComponentDTO> getAllComponents() throws TransformerException;

}
