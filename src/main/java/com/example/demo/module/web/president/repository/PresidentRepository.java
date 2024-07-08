package com.example.demo.module.web.president.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.module.web.president.entity.PresidentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PresidentRepository extends  JpaRepository<PresidentEntity, String>{
 
}
