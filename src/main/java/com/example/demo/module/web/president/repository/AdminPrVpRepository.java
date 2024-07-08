package com.example.demo.module.web.president.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.module.web.president.entity.AdminPrVpEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AdminPrVpRepository extends  JpaRepository<AdminPrVpEntity, Long>{
 
}
