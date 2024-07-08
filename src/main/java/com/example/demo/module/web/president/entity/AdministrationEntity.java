package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administration", schema = "Pres", indexes = {
    @Index(name = "Administration_INDEX1", columnList = "Adminnr", unique = false)
})
public class AdministrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Adminnr", nullable = false)
    private Float AdminNr;

    @Column(name = "Presname", nullable = false, length = 16, columnDefinition = "varchar(16) COLLATE SQL_Latin1_General_CP1_CI_AS")
    private String PresName;

    @Column(name = "Yearinaugurated", nullable = false)
    private Float  YearInaugurated;


}
