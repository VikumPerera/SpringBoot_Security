package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.domain.PasswordResetToken;
@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);

	PasswordResetToken findByUserId(Long id);

}
