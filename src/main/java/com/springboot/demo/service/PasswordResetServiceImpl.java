package com.springboot.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.PasswordResetToken;
import com.springboot.demo.domain.User;
import com.springboot.demo.dto.PasswordDTO;
import com.springboot.demo.dto.UserDTO;
import com.springboot.demo.repository.PasswordResetRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.util.SaasUtil;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
	
	private static final Long EXPIRATION_TIME = 600000L;
	
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public PasswordResetToken createToken(UserDTO userDTO) {
		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = passwordResetRepository.findByUserId(userDTO.getId());
		
		if (null != passwordResetToken) {
			passwordResetToken.setToken(token);
			passwordResetToken.setExpirationTime(SaasUtil.timeStampGenerator()+EXPIRATION_TIME);
			return passwordResetRepository.saveAndFlush(passwordResetToken);
		}
		return passwordResetRepository.saveAndFlush(new PasswordResetToken(new User(userDTO.getId()), token));
	}

	@Override
	public String validatePasswordResetTokenAndSavePassword(String token, PasswordDTO passwordDTO) {
		PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);
		if (null == passwordResetToken) {
			return "Invalid Token";
		}
		if (passwordResetToken.getExpirationTime() > SaasUtil.timeStampGenerator()) {
			User user = passwordResetToken.getUser();
			user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
			userRepository.saveAndFlush(user);
			passwordResetToken.setExpirationTime(SaasUtil.timeStampGenerator());
			passwordResetRepository.saveAndFlush(passwordResetToken);
			return "Password reset successfully...";
		}
		return "Token Expired";
	}

}
