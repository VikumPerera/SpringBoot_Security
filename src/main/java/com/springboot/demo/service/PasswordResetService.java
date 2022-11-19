package com.springboot.demo.service;

import com.springboot.demo.domain.PasswordResetToken;
import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.UserDTO;

public interface PasswordResetService {

	PasswordResetToken createToken(UserDTO userDTO);

	String validatePasswordResetTokenAndSavePassword(String token, PasswordDTO passwordDTO);

}
