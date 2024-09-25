package com.example.demo.module.web.president.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // ป้องกัน SQL Injection โดยใช้ Prepared Statement
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        
        Integer userCount = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);
        
        if (userCount != null && userCount > 0) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }
}
