package com.example.parental_control_system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChildResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
}