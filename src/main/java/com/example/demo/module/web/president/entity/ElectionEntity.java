package com.example.demo.module.web.president.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Election")
public class ElectionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ElectionYear")
    private Long id;

    @Column(name = "Candidate")
    private String Candidate;
    @Column(name = "Vote")
    private Long Votes;
    @Column(name = "WinnerLoserIndic")
    private String WinnerLoserIndic;

}
