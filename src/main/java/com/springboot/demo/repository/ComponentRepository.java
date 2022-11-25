package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.domain.Component;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

}
