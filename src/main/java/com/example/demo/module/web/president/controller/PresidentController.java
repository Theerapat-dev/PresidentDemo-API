package com.example.demo.module.web.president.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.module.web.president.domain.PresidentDomain;
import com.example.demo.module.web.president.entity.AdministrationEntity;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class PresidentController {

    @Autowired
    private PresidentDomain domain;

    @GetMapping("/Administration")
    public ResponseEntity<List<AdministrationEntity>> getAllAdministrations() {
        List<AdministrationEntity> administrations = domain.getAllAdministrations();
        return new ResponseEntity<>(administrations, HttpStatus.OK);
    }
    
}
