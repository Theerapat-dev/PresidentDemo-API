package com.example.demo.module.web.president.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.module.web.president.domain.PresidentDomain;
import com.example.demo.module.web.president.entity.AdministrationEntity;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class PresidentController {

    @Autowired
    private PresidentDomain Domain;

    @GetMapping("/get")
    public List<AdministrationEntity> postMethodName() {
        return PresidentDomain.get();
    }
    
}
