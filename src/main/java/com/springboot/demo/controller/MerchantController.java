package com.springboot.demo.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.dto.MerchantDTO;
import com.springboot.demo.dto.response.ResponseDTO;
import com.springboot.demo.dto.response.ResponseListDTO;
import com.springboot.demo.enums.ResultStatus;
import com.springboot.demo.exception.TransformerException;
import com.springboot.demo.service.MerchantService;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MerchantController {
	
	@Autowired
	private MerchantService merchantService;
	
	@PostMapping("/merchants")
	@PreAuthorize("hasAuthority('ADD_MERCHANT')")
	public ResponseDTO<?> saveMerchant(@Valid @RequestBody MerchantDTO merchantDTO) throws TransformerException {
		
		ResponseDTO<MerchantDTO> response = new ResponseDTO<>();
		response.setPayload(merchantService.saveMerchant(merchantDTO));
        return updateResponse(response);
	}

	@GetMapping("/merchants/{id}")
	@PreAuthorize("hasAuthority('VIEW_MERCHANT')")
	public ResponseDTO<?> getMerchantById(@PathVariable Long id) throws EntityNotFoundException, TransformerException {

		ResponseDTO<MerchantDTO> response = new ResponseDTO<>();
		response.setPayload(merchantService.getMerchantById(id));
		return updateResponse(response);
		
	}
	
	@GetMapping("/merchants")
	@PreAuthorize("hasAuthority('VIEW_LIST_MERCHANT')")
	public ResponseListDTO<?> getAllMerchants() throws TransformerException {
		
		List<MerchantDTO> merchantDTOList = merchantService.getAllMerchants();
		ResponseListDTO<MerchantDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(merchantDTOList);
		response.setCount(merchantDTOList.size());
		return updateResponse(response);

	}
	
	@PutMapping("/merchants/{id}")
	@PreAuthorize("hasAuthority('EDIT_MERCHANT')")
	public MerchantDTO updateMerchantById(@PathVariable Long id, @RequestBody MerchantDTO merchantDTO) throws TransformerException { 
		return merchantService.updateMerchantById(id, merchantDTO);
	}
	
	@DeleteMapping("/merchants/{id}")
	@PreAuthorize("hasAuthority('DELETE_MERCHANT')")
	public void getAllMerchants(@PathVariable Long id) throws TransformerException {
		merchantService.deleteMerchantById(id);
	}

	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
	private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}
