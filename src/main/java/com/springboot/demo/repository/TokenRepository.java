package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.domain.VerificationToken;
@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);

}
