package com.example.demo.module.web.president.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.module.web.president.entity.PresMarriageEntity;

@Repository
public interface PresMarriageRepository extends JpaRepository<PresMarriageEntity, String> {
}
