package com.example.parental_control_system.entity;

// ✅ Replace with your actual Role enum package
import jakarta.persistence.*;
import jakarta.persistence.*; // ✅ Correct for Spring Boot 3

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private com.example.parental_control_system.entity.Role role;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setUsername(String username) {
    }
}
