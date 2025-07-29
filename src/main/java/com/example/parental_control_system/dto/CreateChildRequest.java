package com.example.parental_control_system.dto;

import com.example.parental_control_system.entity.Child;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.xmlunit.util.Mapper;

import java.time.LocalDate;

@Data
public class CreateChildRequest {
    @NotBlank
    private String name;
    @Past
    private LocalDate birthDate;
}



