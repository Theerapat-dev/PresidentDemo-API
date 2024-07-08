package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "President")
public class PresidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PresName")
    private String PresName;

    @Column(name = "BirthYr")
    private Long BirthYr;
    @Column(name = "YreScrv")
    private Long YreScrv;

    
}
