package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PresMarriage")
public class PresMarriageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PresName")
    private String presName;

    @Column(name = "SpouseName")
    private String spouseName;

    @Column(name = "PrAge")  // Ensure this matches the actual column name in your database
    private Long prAge;

    @Column(name = "SpAge")
    private Long spAge;

    @Column(name = "NrChildren")
    private Long nrChildren;

    @Column(name = "MarYear")
    private Long marYear;

    // Getters and Setters
    public String getPresName() {
        return presName;
    }

    public void setPresName(String presName) {
        this.presName = presName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public Long getPrAge() {
        return prAge;
    }

    public void setPrAge(Long prAge) {
        this.prAge = prAge;
    }

    public Long getSpAge() {
        return spAge;
    }

    public void setSpAge(Long spAge) {
        this.spAge = spAge;
    }

    public Long getNrChildren() {
        return nrChildren;
    }

    public void setNrChildren(Long nrChildren) {
        this.nrChildren = nrChildren;
    }

    public Long getMarYear() {
        return marYear;
    }

    public void setMarYear(Long marYear) {
        this.marYear = marYear;
    }
}
