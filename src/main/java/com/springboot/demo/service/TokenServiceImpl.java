package com.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.User;
import com.springboot.demo.domain.VerificationToken;
import com.springboot.demo.repository.TokenRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.util.SaasUtil;

@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
		return tokenRepository.saveAndFlush(verificationToken);
	}

	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (null == verificationToken) {
			return "Invalid Token";
		}
		if (verificationToken.getExpirationTime() > SaasUtil.timeStampGenerator()) {
			User user = verificationToken.getUser();
			user.setEnabled(true);
			userRepository.saveAndFlush(user);
			return "valid";
		}
		return "Token Expired";
	}

	
	
	
}
