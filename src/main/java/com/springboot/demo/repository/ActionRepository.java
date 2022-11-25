package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.domain.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

}
