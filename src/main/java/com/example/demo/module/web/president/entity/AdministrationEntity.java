package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administration")
public class AdministrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminNr")
    private Long adminNr;

    @Column(name = "PresName")
    private String presName;

    @Column(name = "Yearinaugurated")
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
