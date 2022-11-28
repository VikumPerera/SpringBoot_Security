package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.domain.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
