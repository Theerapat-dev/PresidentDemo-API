package com.example.demo.module.web.president.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.module.web.president.domain.PresidentDomain;
import com.example.demo.module.web.president.entity.AdminPrVpEntity;
import com.example.demo.module.web.president.entity.AdministrationEntity;
import com.example.demo.module.web.president.entity.ElectionEntity;
import com.example.demo.module.web.president.entity.PresHobbyEntity;
import com.example.demo.module.web.president.entity.PresidentEntity;
import com.example.demo.module.web.president.entity.PresMarriageEntity;
import com.example.demo.module.web.president.entity.StateEntity;

@RestController
@RequestMapping("/api")
public class PresidentController {

    @Autowired
    private PresidentDomain domain;

    @GetMapping("/administrations")
    public ResponseEntity<List<AdministrationEntity>> getAllAdministrations() {
        List<AdministrationEntity> administrations = domain.getAllAdministrations();
        return new ResponseEntity<>(administrations, HttpStatus.OK);
    }

    @GetMapping("/adminprvp")
    public ResponseEntity<List<AdminPrVpEntity>> getAllAdminPrVp() {
        List<AdminPrVpEntity> adminprvp = domain.getAllAdminPrVp();
        return new ResponseEntity<>(adminprvp, HttpStatus.OK);
    }
    
    @GetMapping("/Election")
    public ResponseEntity<List<ElectionEntity>> getAllElection() {
        List<ElectionEntity> election = domain.getAllElection();
        return new ResponseEntity<>(election, HttpStatus.OK);
    }

    @GetMapping("/PresHobby")
    public ResponseEntity<List<PresHobbyEntity>> getAllHobby() {
        List<PresHobbyEntity> hobby = domain.getAllHobby();
        return new ResponseEntity<>(hobby, HttpStatus.OK);
    }

    @GetMapping("/President")
    public ResponseEntity<List<PresidentEntity>> getAllPresident() {
        List<PresidentEntity> president = domain.getAllPresident();
        return new ResponseEntity<>(president, HttpStatus.OK);
    }

    @GetMapping("/PresMarriage")
    public ResponseEntity<List<PresMarriageEntity>> getAllPresMarriage() {
        List<PresMarriageEntity> presMarriage = domain.getAllPresMarriage();
        return new ResponseEntity<>(presMarriage, HttpStatus.OK);
    }

    @GetMapping("/State")
    public ResponseEntity<List<StateEntity>> getAllState() {
        List<StateEntity> state = domain.getAllState();
        return new ResponseEntity<>(state, HttpStatus.OK);
    }
}
