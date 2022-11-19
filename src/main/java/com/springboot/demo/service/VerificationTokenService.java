package com.springboot.demo.service;

import com.springboot.demo.domain.VerificationToken;

public interface VerificationTokenService {

	VerificationToken saveVerificationToken(VerificationToken verificationToken);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldToken);

	
}
