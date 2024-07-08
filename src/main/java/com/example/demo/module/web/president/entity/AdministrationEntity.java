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
    @Index(name = "Administration_INDEX1", columnList = "AdminNr", unique = false)
})
public class AdministrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminNr", nullable = false)
    private Long adminNr;

    @Column(name = "PresName", nullable = false, length = 16, columnDefinition = "varchar(16) COLLATE SQL_Latin1_General_CP1_CI_AS")
    private String presName;

    @Column(name = "Yearinaugurated", nullable = false)
    private Long yearInaugurated;

    // Getters and Setters
    public Long getAdminNr() {
        return adminNr;
    }

    public void setAdminNr(Long adminNr) {
        this.adminNr = adminNr;
    }

    public String getPresName() {
        return presName;
    }

    public void setPresName(String presName) {
        this.presName = presName;
    }

    public Long getYearInaugurated() {
        return yearInaugurated;
    }

    public void setYearInaugurated(Long yearInaugurated) {
        this.yearInaugurated = yearInaugurated;
    }
}
