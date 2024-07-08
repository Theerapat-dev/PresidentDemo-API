package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AdminPrVp")
public class AdminPrVpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminNr", nullable = false)
    private Long adminNr;

    @Column(name = "PresName", nullable = false)
    private String presName;
    
    @Column(name = "VicePresName", nullable = false)
    private String vicePresName;
    
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

    public String getVicePresName() {
        return vicePresName;
    }

    public void setVicePresName(String vicePresName) {
        this.vicePresName = vicePresName;
    }
}
