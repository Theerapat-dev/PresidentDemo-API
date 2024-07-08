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
    private String presName;

    @Column(name = "BirthYr")
    private Long birthYr;

    @Column(name = "YrsServ")  // Ensure this matches the actual column name in your database
    private Long yrsServ;

    @Column(name = "DeathAge")
    private Long deathAge;

    @Column(name = "Party")
    private String party;

    @Column(name = "StateBorn")
    private String stateBorn;

    // Getters and Setters
    public String getPresName() {
        return presName;
    }

    public void setPresName(String presName) {
        this.presName = presName;
    }

    public Long getBirthYr() {
        return birthYr;
    }

    public void setBirthYr(Long birthYr) {
        this.birthYr = birthYr;
    }

    public Long getYrsServ() {
        return yrsServ;
    }

    public void setYrsServ(Long yrsServ) {
        this.yrsServ = yrsServ;
    }

    public Long getDeathAge() {
        return deathAge;
    }

    public void setDeathAge(Long deathAge) {
        this.deathAge = deathAge;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getStateBorn() {
        return stateBorn;
    }

    public void setStateBorn(String stateBorn) {
        this.stateBorn = stateBorn;
    }
}
