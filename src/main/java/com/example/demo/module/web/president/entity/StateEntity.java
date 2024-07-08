package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "State")
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StateName")
    private String stateName;

    @Column(name = "AdminEntered")
    private Long adminEntered;

    @Column(name = "YearEntered")
    private Long yearEntered;

    // Getters and Setters
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getAdminEntered() {
        return adminEntered;
    }

    public void setAdminEntered(Long adminEntered) {
        this.adminEntered = adminEntered;
    }

    public Long getYearEntered() {
        return yearEntered;
    }

    public void setYearEntered(Long yearEntered) {
        this.yearEntered = yearEntered;
    }
}
