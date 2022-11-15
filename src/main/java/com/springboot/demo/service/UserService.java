package com.springboot.demo.service;

import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.exception.TransformerException;

public interface UserService {

	UserDTO registerUser(UserDTO userDTO) throws TransformerException;

}
