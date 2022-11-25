package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.domain.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
