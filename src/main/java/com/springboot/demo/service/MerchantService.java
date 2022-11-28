package com.springboot.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.springboot.demo.dto.MerchantDTO;
import com.springboot.demo.exception.TransformerException;


public interface MerchantService {

	MerchantDTO saveMerchant(MerchantDTO merchantDTO) throws TransformerException;

	MerchantDTO getMerchantById(Long id) throws EntityNotFoundException, TransformerException;

	List<MerchantDTO> getAllMerchants() throws TransformerException;

	MerchantDTO updateMerchantById(Long id, MerchantDTO departmerchantDTOment) throws TransformerException;

	void deleteMerchantById(Long id) throws TransformerException;

}
