package com.example.demo.module.web.president.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.module.web.president.entity.AdminPrVpEntity;
import com.example.demo.module.web.president.entity.AdministrationEntity;
import com.example.demo.module.web.president.entity.ElectionEntity;
import com.example.demo.module.web.president.entity.PresHobbyEntity;
import com.example.demo.module.web.president.entity.PresidentEntity;
import com.example.demo.module.web.president.entity.PresMarriageEntity;
import com.example.demo.module.web.president.entity.StateEntity;
import com.example.demo.module.web.president.repository.AdministrationRepository;
import com.example.demo.module.web.president.repository.AdminPrVpRepository;
import com.example.demo.module.web.president.repository.ElectionRepository;
import com.example.demo.module.web.president.repository.PresHobbyRepository;
import com.example.demo.module.web.president.repository.PresidentRepository;
import com.example.demo.module.web.president.repository.PresMarriageRepository;
import com.example.demo.module.web.president.repository.StateRepository;

@Component
public class PresidentDomain {

    @Autowired
    private AdministrationRepository administrationRepository;

    @Autowired
    private AdminPrVpRepository adminPrVpRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private PresHobbyRepository presHobbyRepository;

    @Autowired
    private PresidentRepository presidentRepository;

    @Autowired
    private PresMarriageRepository presMarriageRepository;

    @Autowired
    private StateRepository stateRepository;

    public List<AdministrationEntity> getAllAdministrations() {
        return administrationRepository.findAll();
    }

    public List<AdminPrVpEntity> getAllAdminPrVp() {
        return adminPrVpRepository.findAll();
    }

    public List<ElectionEntity> getAllElection() {
        return electionRepository.findAll();
    }
    
    public List<PresHobbyEntity> getAllHobby() {
        return presHobbyRepository.findAll();
    }

    public List<PresidentEntity> getAllPresident() {
        return presidentRepository.findAll();
    }

    public List<PresMarriageEntity> getAllPresMarriage() {
        return presMarriageRepository.findAll();
    }
    public List<StateEntity> getAllState() {
        return stateRepository.findAll();
    }
}
