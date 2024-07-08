package com.example.demo.module.web.president.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.module.web.president.entity.AdministrationEntity;
import com.example.demo.module.web.president.repository.AdministrationRepository;

@Component
public class PresidentDomain {
    @Autowired
    private AdministrationRepository repository;

    public List<AdministrationEntity> getAllAdministrations() {
        return repository.findAll();
    }

}
