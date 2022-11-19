package com.springboot.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.domain.User;
import com.springboot.demo.domain.VerificationToken;
import com.springboot.demo.repository.VerificationTokenRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.util.SaasUtil;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
	
	private static final Long EXPIRATION_TIME = 600000L;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
		return verificationTokenRepository.saveAndFlush(verificationToken);
	}

	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
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

	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
		String newToken = UUID.randomUUID().toString();
		verificationToken.setToken(newToken);
		verificationToken.setExpirationTime(SaasUtil.timeStampGenerator()+EXPIRATION_TIME);
		return verificationTokenRepository.saveAndFlush(verificationToken);
	}

}
