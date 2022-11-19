package com.springboot.demo.service;

import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;

public interface UserService {

	UserDTO registerUser(UserDTO userDTO) throws TransformerException;

	UserDTO findUserByEmail(String email) throws TransformerException;

	String changePassword(PasswordDTO passwordDTO);

}
