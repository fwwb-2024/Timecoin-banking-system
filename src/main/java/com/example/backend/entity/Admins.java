package com.example.backend.entity;

import lombok.Data;

@Data
public class Admins {
    private Integer adminID;
    private String adminName;
    private String adminPassword;
    private String adminPreviliege;
    private String token;
}
