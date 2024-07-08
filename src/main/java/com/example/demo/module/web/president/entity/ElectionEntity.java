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
    private Long electionYear;

    @Column(name = "Candidate")
    private String candidate;

    @Column(name = "Votes")
    private Long votes;

    @Column(name = "WinnerLoserIndic")
    private String winnerLoserIndic;

    public Long getElectionYear() {
        return electionYear;
    }

    public void setElectionYear(Long electionYear) {
        this.electionYear = electionYear;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public String getWinnerLoserIndic() {
        return winnerLoserIndic;
    }

    public void setWinnerLoserIndic(String winnerLoserIndic) {
        this.winnerLoserIndic = winnerLoserIndic;
    }
}
