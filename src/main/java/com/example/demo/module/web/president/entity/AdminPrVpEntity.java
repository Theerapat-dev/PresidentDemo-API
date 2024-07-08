package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AdminiPrVp")
public class AdminPrVpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminNr")
    private Long id;

    @Column(name = "PresName")
    private String PresName;
    @Column(name = "VicePresName")
    private String VicePresName;
    
}
